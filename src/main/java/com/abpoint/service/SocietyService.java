package com.abpoint.service;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abpoint.extra.charges.service.ExtraChargesServices;
import com.abpoint.model.ApprovalPendingPayment;
import com.abpoint.model.DeletedExtraCharges;
import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.MaintenanceDashboardCard;
import com.abpoint.model.MaintenanceDashboardEntry;
import com.abpoint.model.MaintenanceMasterEntry;
import com.abpoint.model.SocietyMaintenanceEntry;
import com.abpoint.model.SocietyMaintenancePaidHistory;
import com.abpoint.repository.ApprovalPendingPaymentRepository;
import com.abpoint.repository.DeleteExtraChargesRepository;
import com.abpoint.repository.ExtraChargesEntryRepository;
import com.abpoint.repository.FlatTypeFlatNumberMapRepository;
import com.abpoint.repository.MaintenanceMasterEntryRepo;
import com.abpoint.repository.MaintenanceValuesRepository;
import com.abpoint.repository.MaintenanceValuesRepository2;
import com.abpoint.repository.SocietyMaintenancePaidHistoryRepository;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
//defining the business logic
@Service
public class SocietyService {
	
	@Autowired
	private SocietyMaintenancePaidHistoryRepository repositoryMaintenancePaidHistory;

	@Autowired
	private MaintenanceMasterEntryRepo repositoryMaintenanceMasterEntry;

	@Autowired
	private MaintenanceValuesRepository repositoryMaintenanceValues;

	@Autowired
	private FlatTypeFlatNumberMapRepository repositoryFlatNumberMap;

	@Autowired
	ExtraChargesEntryRepository extraChargesEntryRepository;

	@Autowired
	DeleteExtraChargesRepository deleteExtraChargesRepository;

	@Autowired
	SocietyUtilServices societyUtilServices;

	@Autowired
	ApprovalPendingPaymentRepository approvalPendingPaymentRepository;

	@Autowired
	PaymentApprovalService approvalService;
	
	@Autowired
	ExtraChargesServices extraChargesServices;

	@Autowired
	MaintenanceValuesRepository2 maintenanceValuesRepository2;
	
	private static final Logger logger = LoggerFactory.getLogger(SocietyService.class);

	@Transactional
	public void updateMasterEntry(int flatNumber, double amountToDeduct) {
		try {
			// Retrieve the existing master entry
			MaintenanceMasterEntry masterEntryExisting = getMasterEntry(flatNumber);

			if (masterEntryExisting != null) {
				// Calculate received amount
				double receivedTillNow = Optional
						.ofNullable(repositoryMaintenancePaidHistory.getPaidAmountTillNow(flatNumber)).orElse(0.0);

				//receivedTillNow = receivedTillNow-amountToDeduct;
		
				// Retrieve necessary data
				String flatType = getFlatType(flatNumber);
				String latestFinancialYear = getLatestYear();
				double outstandingTillDate = masterEntryExisting.getTotalOutstanding(); // getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
				double totalOutstanding = outstandingTillDate - amountToDeduct;
				Date latestDateUndeleted = repositoryMaintenancePaidHistory.findLatestReceivedDate(flatNumber);

				// Update master entry
				masterEntryExisting.setTotalOutstanding(totalOutstanding);
				masterEntryExisting.setReceivedTillNow(receivedTillNow);
				masterEntryExisting.setLastRecievedDate(latestDateUndeleted);

				// Save updated master entry
				repositoryMaintenanceMasterEntry.save(masterEntryExisting);

				// Set and update current year
				String nearestYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
				masterEntryExisting.setCurrentYear(nearestYear);
				repositoryMaintenanceMasterEntry.save(masterEntryExisting);

				logger.info(
						"Updated master entry for flat number: {}. Total Outstanding: {}, Received Till Now: {}, Last Received Date: {}",
						flatNumber, totalOutstanding, receivedTillNow, latestDateUndeleted);
			} else {
				logger.warn("No master entry found for flat number: {}", flatNumber);
			}
		} catch (Exception e) {
			logger.error("Error occurred while updating master entry for flat number: {}. Error: {}", flatNumber,
					e.getMessage(), e);
			throw new RuntimeException("Error occurred while updating MasterEntry. " + e.getMessage(), e);
		}
	}


	@Transactional
	public ResponseEntity<String> editPaidHistory(SocietyMaintenancePaidHistory societyMaintenancePaidHistory) {
		Long id = societyMaintenancePaidHistory.getId();
		try {
			// Check if the record already exists and is not deleted
			Optional<SocietyMaintenancePaidHistory> existingHistoryOptional = Optional
					.ofNullable(repositoryMaintenancePaidHistory.findByIdAndDeletedFalse(id));

			if (existingHistoryOptional.isPresent()) {
				SocietyMaintenancePaidHistory existingHistory = existingHistoryOptional.get();
				// Check if the new data is identical to the existing data
				if (societyMaintenancePaidHistory.equals(existingHistory)) {
					String msg = "Tried to save the existing record.";
					logger.info(msg);
					return ResponseEntity.ok(msg);
				}
			}
		} catch (Exception e) {
			logger.error("Error while checking existing entry!", e);
			return ResponseEntity.badRequest().body("Error while checking existing entry!");
		}

		try {
			// Soft delete the old record and save the new record
			softDeletePaidHistory(id, "Edited");
			repositoryMaintenancePaidHistory.save(societyMaintenancePaidHistory);
			logger.info("Successfully saved updated paid history.");

			// Update the master entry
			updateMasterEntry(societyMaintenancePaidHistory.getFlatNumber(), societyMaintenancePaidHistory.getAmount());
			logger.info("Successfully updated the master data.");
		} catch (Exception e) {
			logger.error("Error while saving edited entry!", e);
			return ResponseEntity.badRequest().body("Error while saving edited entry!");
		}

		return ResponseEntity.ok("Successfully edited.");
	}

	@Transactional
	public ResponseEntity<String> softDeletePaidHistory(Long id, String deleteReason) {
	    String msg = "Error while deleting entry!";
	    logger.info("Attempting to soft delete entry with ID: {}", id);

	    try {
	        Optional<SocietyMaintenancePaidHistory> optionalHistory = repositoryMaintenancePaidHistory.findById(id);

	        if (optionalHistory.isPresent()) {
	            SocietyMaintenancePaidHistory history = optionalHistory.get();
	            int flatNumber = history.getFlatNumber();
	            double amountToDeduct = history.getAmount();

	            logger.info("Entry found for ID: {}. Flat Number: {}, Amount: {}", id, flatNumber, amountToDeduct);

	            history.setDeleted(true);
	            history.setDeletedReason(deleteReason);
	            repositoryMaintenancePaidHistory.save(history);

	            logger.info("Entry with ID: {} marked as deleted. Reason: {}", id, deleteReason);

	            updateMasterEntry(flatNumber, -amountToDeduct);

	            logger.info("Master entry updated for Flat Number: {}. Amount deducted: {}", flatNumber, amountToDeduct);

	            return ResponseEntity.ok("Successfully deleted.");
	        } else {
	            logger.warn("No entry found for ID: {}", id);
	        }
	    } catch (Exception e) {
	        logger.error("Error while deleting entry with ID {}: {}", id, e.getMessage(), e);
	    }

	    logger.error("Failed to delete entry with ID: {}", id);
	    return ResponseEntity.badRequest().body(msg);
	}


	@Transactional
	public ResponseEntity<ApprovalPendingPayment> saveAsApproved(Long id, Map<String, String> actionDetails)
			throws Exception {

		Optional<ApprovalPendingPayment> pendingPaymentOptional = approvalPendingPaymentRepository.findById(id);

		if (pendingPaymentOptional.isEmpty()) {
			logger.warn("The payment id passed is not present in Unapproved list. Payment id: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		ApprovalPendingPayment pendingPayment = pendingPaymentOptional.get();
		logger.info("Payment found with Id: {}. Approving details: {}", id, pendingPayment);

		Optional<SocietyMaintenancePaidHistory> existingEntry =null;
		try { 
			// Check if an entry with the same parameters already exists
			existingEntry= repositoryMaintenancePaidHistory.findHistoryByParameters(
					pendingPayment.getFlatNumber(), pendingPayment.getTransactionId(),
					pendingPayment.getAmount(), pendingPayment.getPaymentMethod(),
					pendingPayment.getDate());

			if (existingEntry.isPresent()) { // Entry already exists, handle accordingly
				logger.error("Entry with flat number " + pendingPayment.getFlatNumber() + " and transaction ID "
						+ pendingPayment.getTransactionId() + " already exists.");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(pendingPayment); // 409 Conflict
			}
		} catch (Exception e) {
			logger.error("Error while saving Pending Payment.");
			e.printStackTrace();
			return ResponseEntity.badRequest().body(pendingPayment);
		}

		int flatNumber = pendingPayment.getFlatNumber();
		String flatType = getFlatType(flatNumber);
		String latestFinancialYear = getLatestYear();
		String nearestYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
		double receivedTillNowToUpdate = getReceivedTillNowByFlatNumber(flatNumber);
		double annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);
		double outstandingTillFinancialYear = getOutstandingTillFinancialYear(latestFinancialYear, flatType,
				flatNumber);
		String financialYearOfDate = societyUtilServices.getFinancialYearOfDate(pendingPayment.getDate());

		SocietyMaintenancePaidHistory maintenancePaidHistory = new SocietyMaintenancePaidHistory();
		maintenancePaidHistory.setId(id);
		maintenancePaidHistory.setFlatType(flatType);
		maintenancePaidHistory.setFlatNumber(flatNumber);
		maintenancePaidHistory.setAmount(pendingPayment.getAmount());
		maintenancePaidHistory.setTransactionId(pendingPayment.getTransactionId());
		maintenancePaidHistory.setDate(pendingPayment.getDate());
		maintenancePaidHistory.setAnnualMaintenance(annualMaintenance);
		maintenancePaidHistory.setYear(financialYearOfDate);
		maintenancePaidHistory.setPaymentMethod(pendingPayment.getPaymentMethod());

		Optional<MaintenanceMasterEntry> masterEntryOptional = Optional.ofNullable(getMasterEntry(flatNumber));

		double outstandingToUpdate;
		double extraCharges = 0.0;

		if (masterEntryOptional.isPresent()) {
			MaintenanceMasterEntry masterEntryExisting = masterEntryOptional.get();
			logger.info("Existing Master entry fetched: "+ masterEntryExisting);
			double receivedTillNowExist = masterEntryExisting.getReceivedTillNow();
			receivedTillNowToUpdate = pendingPayment.getAmount() + receivedTillNowExist;
			outstandingToUpdate = outstandingTillFinancialYear - pendingPayment.getAmount();
			extraCharges=masterEntryExisting.getChargedAmount();
		} else {
			logger.info("No exisiting master entry found for flat number: "+ flatNumber);
			receivedTillNowToUpdate = pendingPayment.getAmount();
			outstandingToUpdate = outstandingTillFinancialYear - pendingPayment.getAmount();
		}

		outstandingToUpdate += extraCharges;

		MaintenanceMasterEntry maintenanceMasterEntry = new MaintenanceMasterEntry();
		
		maintenanceMasterEntry.setFlatNumber(flatNumber);
		maintenanceMasterEntry.setCurrentYear(nearestYear);
		maintenanceMasterEntry.setLastRecievedDate(pendingPayment.getDate());
		maintenanceMasterEntry.setReceivedTillNow(receivedTillNowToUpdate);
		maintenanceMasterEntry.setChargedAmount(extraCharges);
		maintenanceMasterEntry.setTotalOutstanding(outstandingToUpdate);

		try {
			logger.info("Saving data to maintenanceMasterEntry");
			repositoryMaintenanceMasterEntry.save(maintenanceMasterEntry);
		} catch (Exception e) {
			logger.error("Error occurred while updating maintenanceMasterEntry: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pendingPayment);
		}

		try {
			logger.info("Saving data to maintenancePaidHistory");
			repositoryMaintenancePaidHistory.save(maintenancePaidHistory);
		} catch (Exception e) {
			logger.error("Error occurred while updating maintenancePaidHistory: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pendingPayment);
		}

		try {
			logger.info("Removing data from approval pending payments");
			approvalService.actionPayment(id, actionDetails);
		} catch (Exception e) {
			logger.error("Error occurred while removing data from approval pending payments: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pendingPayment);
		}

		return ResponseEntity.ok(pendingPayment);
	}

	public List<MaintenanceDashboardCard> getMaintenanceDashboardCardData(int flatNumber) {
		try {
			return DashboardMapper.convertToDashboardCards(getMaintenanceDashboardData(flatNumber));
		} catch (Exception e) {
			logger.error("Error while fetching maintenance dashboard card data for flat number: {}", flatNumber, e);
			// Return an empty list or handle as appropriate
			return Collections.emptyList();
		}
	}

	public MaintenanceDashboardEntry getMaintenanceDashboardData(int flatNumber) {
		String latestFinancialYear;
		String flatType;
		String paidTillYear;
		double totalPaid;
		double maintenanceOutstanding;
		double existingMaintenanceOutstanding;
		double annualMaintenance;
		double extraCharges;
		double totalOutstanding = 0.0;
		MaintenanceDashboardEntry maintenanceDashboardEntry = new MaintenanceDashboardEntry();

		try {
			latestFinancialYear = getLatestYear();
			flatType = getFlatType(flatNumber);
			totalPaid = getReceivedTillNowByFlatNumber(flatNumber);
			paidTillYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
			existingMaintenanceOutstanding = getTotalOutstandingByFlatNumber(flatNumber);
			
			//If existingMaintenanceOutstanding zero means either it is completely paid or nothing has paid.
			if (existingMaintenanceOutstanding==0) 
				maintenanceOutstanding = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
			else
				maintenanceOutstanding = existingMaintenanceOutstanding;
			
			annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);
			extraCharges = extraChargesServices.getExtraCharges(flatNumber);
			totalOutstanding = maintenanceOutstanding + extraCharges;

			maintenanceDashboardEntry.setFlatType(flatType);
			maintenanceDashboardEntry.setTotalPaid(totalPaid);
			maintenanceDashboardEntry.setExtraCharges(extraCharges);
			maintenanceDashboardEntry.setPaidTillYear(paidTillYear);
			maintenanceDashboardEntry.setMaintenanceOutstanding(maintenanceOutstanding);
			maintenanceDashboardEntry.setAnnualMaintenance(annualMaintenance);
			maintenanceDashboardEntry.setTotalOutstanding(totalOutstanding);
		} catch (Exception e) {
			logger.error("Error while getting dashboard data for flat number: {}", flatNumber, e);
		}

		return maintenanceDashboardEntry;
	}

	public double getPaidAmountForYear(int flatNumber, String year) {

		Optional<Double> paidAmountForYearOptional = Optional
				.ofNullable(repositoryMaintenancePaidHistory.getPaidAmountForYear(flatNumber, year));

		return paidAmountForYearOptional.orElse(0.0);
	}

	public List<SocietyMaintenancePaidHistory> getPaidHistoryByFlatNumber(int flatNumber) {
		List<SocietyMaintenancePaidHistory> historyList = repositoryMaintenancePaidHistory
				.findByFlatNumberAndDeletedFalse(flatNumber);

		// Sort the list by year
		historyList.sort(Comparator.comparing(SocietyMaintenancePaidHistory::getYear));

		logger.info("Sorted history list for flat number {}: {}", flatNumber, historyList);

		return historyList;
	}

	String getFlatType(int flatNumber) throws Exception {
		Optional<String> latestYearOptional = repositoryFlatNumberMap.getFlatTypeByFlatNumber(flatNumber);
		if (!latestYearOptional.isPresent())
			throw new Exception("Error in getting Flat type");
		return latestYearOptional.get();
	}

	String getFirstYear() throws Exception {
		Optional<String> latestYearOptional = Optional.of(repositoryMaintenanceValues.findFirstYear());
		if (!latestYearOptional.isPresent())
			throw new Exception("Error in getting First Year");
		return latestYearOptional.get();
	}

	String getLatestYear() throws Exception {
		Optional<String> latestYearOptional = Optional.of(repositoryMaintenanceValues.findLatestYear());
		if (!latestYearOptional.isPresent())
			throw new Exception("Error in getting Latest Year");
		return latestYearOptional.get();
	}

	public String getNearestYear(String latestFinancialYear, int flatNumber, String flatType) throws Exception {
		try {
			double receivedTillNow = getReceivedTillNowByFlatNumber(flatNumber);

			Optional<Double> expectedBalanceOptional = Optional.ofNullable(
					repositoryMaintenanceValues.sumMaintenanceValueByYearAndFlatType(latestFinancialYear, flatType));

			double expectedBalance = expectedBalanceOptional.orElse(0.0); // Default value if sum is null

			if (expectedBalance == 0) {
				throw new Exception("Expected balance is zero for the given year and flat type.");
			}

			if (receivedTillNow >= expectedBalance) {
				return latestFinancialYear;
			} else {
				return getRespectiveNearestYear(flatType, receivedTillNow, flatNumber);
			}
		} catch (Exception e) {
			// Log the exception for debugging purposes
			logger.error("Error while calculating the nearest year for flat number {} and flat type {}: {}", flatNumber,
					flatType, e.getMessage());
			throw new Exception("Error while determining the nearest year", e);
		}
	}

	private String getRespectiveNearestYear(String flatType, double receivedTillNow, int flatNumber) throws Exception {
		Map<String, Object> result = getRespectiveFinancialYearAndExtraAmount(flatType, receivedTillNow, flatNumber);

		return (String) result.get("financialYear");
	}

	public Map<String, Object> getRespectiveFinancialYearAndExtraAmount(String flatType, double receivedTillNow,
			int flatNumber) throws Exception {
		// Initialize result map
		Map<String, Object> result = new HashMap();

		try {
			// Fetch the respective financial year and extra amount
			Map<String, Object> fetchedData = maintenanceValuesRepository2.findYearAndExtraAmount(receivedTillNow,
					flatType);

			if (fetchedData != null && !fetchedData.isEmpty()) {
				String financialYear = (String) fetchedData.get("financial_year");
				double cumulativeSum = ((Number) fetchedData.get("cumulative_sum")).doubleValue();
				double extraAmount = ((Number) fetchedData.get("extra_amount")).doubleValue();

				// Log the values
				logger.info("Financial Year: {}", financialYear);
				logger.info("Cumulative Sum: {}", cumulativeSum);
				logger.info("Extra Amount: {}", extraAmount);

				result.put("financialYear", financialYear);
				result.put("cumulativeSum", cumulativeSum);
				result.put("extraAmount", extraAmount);
			} else {
				// Default values when no data is found
				String financialYear = getFirstYear();
				double cumulativeSum = receivedTillNow;
				double extraAmount = getOutstandingTillFinancialYear(financialYear, flatType, flatNumber);

				result.put("financialYear", financialYear);
				result.put("cumulativeSum", cumulativeSum);
				result.put("extraAmount", extraAmount);

				// Log the absence of matching data
				logger.warn("No matching financial year found for flatType: {} and receivedTillNow: {}", flatType,
						receivedTillNow);
			}
		} catch (Exception e) {
			// Log and throw a custom exception
			logger.error("Error while fetching financial year and extra amount", e);
			throw new Exception("Error while fetching financial year and extra amount", e);
		}

		return result;
	}

	public double getOutstandingTillFinancialYear(String financialYear, String flatType, int flatNumber) {

		Optional<Double> expectedBalanceOptional = Optional
				.of(repositoryMaintenanceValues.sumMaintenanceValueByYearAndFlatType(financialYear, flatType));

		double expectedBalance = expectedBalanceOptional.orElse(0.0); // Default value if sum is null
		double actualPayment = getReceivedTillNowByFlatNumber(flatNumber);

		return expectedBalance - actualPayment;
	}

	private double getReceivedTillNowByFlatNumber(int flatNumber) {

		Optional<Double> receivedTillNowOptional = repositoryMaintenanceMasterEntry
				.getReceivedTillNowByFlatNumber(flatNumber);
		return receivedTillNowOptional.orElse(0.0);
	}
	
	private double getTotalOutstandingByFlatNumber(int flatNumber) {

		Optional<Double> totalOutstanding = repositoryMaintenanceMasterEntry
				.getTotalOutstandingByFlatNumber(flatNumber);
		return totalOutstanding.orElse(0.0);
	}

	public MaintenanceMasterEntry getMasterEntry(int id) {
		logger.info("Fetching MaintenanceMasterEntry with ID: {}", id);
		Optional<MaintenanceMasterEntry> masterEntry = repositoryMaintenanceMasterEntry.findById(id);

		return masterEntry.orElse(null);
	}

	public ExtraChargesEntry addEntry(ExtraChargesEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Entry cannot be null");
		}
		logger.info("Adding ExtraChargesEntry: {}", entry);
		return extraChargesEntryRepository.save(entry);
	}

	public double getAnnualMaintenanceByFlatNumber(int flatNumber) {
		logger.info("Fetching annual maintenance for flat number: {}", flatNumber);
		return repositoryFlatNumberMap.getAnnualMaintenanceByFlatNumber(flatNumber).orElseThrow(
				() -> new NoSuchElementException("No annual maintenance found for flat number: " + flatNumber));
	}


	

	public SocietyMaintenanceEntry getSmeByIdResponse(int flatId) throws Exception {

		SocietyMaintenanceEntry entry = getMaintenanceEntry(flatId);

		return entry;
	}

	private SocietyMaintenanceEntry getMaintenanceEntry(int flatNumber) throws Exception {
		// Fetch the flat type and annual maintenance for the flat number
		String flatType = getFlatType(flatNumber);
		double annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);

		// Retrieve the optional master entry from the repository
		Optional<MaintenanceMasterEntry> optionalMasterEntry = repositoryMaintenanceMasterEntry.findById(flatNumber);

		SocietyMaintenanceEntry entry;

		// Check if the master entry is present
		if (optionalMasterEntry.isPresent()) {
			// Use the master entry to create the SocietyMaintenanceEntry
			MaintenanceMasterEntry masterEntry = optionalMasterEntry.get();
			entry = new SocietyMaintenanceEntry(masterEntry);
		} else {
			// Master entry not found, perform additional processing
			try {
				String latestFinancialYear = getLatestYear();
				double totalOutstanding = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
				double receivedTillNow = getReceivedTillNowByFlatNumber(flatNumber);
				String paidTillYear = getNearestYear(latestFinancialYear, flatNumber, flatType);

				entry = new SocietyMaintenanceEntry();
				entry.setFlatNumber(flatNumber);
				entry.setPaidTillYear(paidTillYear);
				entry.setFlatType(flatType);
				entry.setTotaloutstandingAmount(totalOutstanding);
				entry.setAnnualMaintenance(annualMaintenance);
				entry.setReceivedTillNow(receivedTillNow);
			} catch (Exception e) {
				// Log the error and throw a custom exception
				logger.error("Error while calculating maintenance entry for flat number: {}", flatNumber, e);
				throw new Exception("Error while calculating maintenance entry", e);
			}
		}

		// Set common properties
		entry.setFlatType(flatType);
		entry.setAnnualMaintenance(annualMaintenance);

		// Log the details
		logger.info("Generating updated details for Master Maintenance: {}", entry);

		return entry;
	}

}