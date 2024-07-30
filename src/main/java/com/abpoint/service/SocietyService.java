package com.abpoint.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.MaintenanceDashboardCard;
import com.abpoint.model.MaintenanceDashboardEntry;
import com.abpoint.model.MaintenanceMasterEntry;
import com.abpoint.model.SocietyMaintenanceEntry;
import com.abpoint.model.SocietyMaintenancePaidHistory;
import com.abpoint.repository.ExtraChargesEntryRepository;
import com.abpoint.repository.FlatTypeFlatNumberMapRepository;
import com.abpoint.repository.MaintenanceMasterEntryRepo;
import com.abpoint.repository.MaintenanceValuesRepository;
import com.abpoint.repository.SocietyMaintenanceEntryRepository;
import com.abpoint.repository.SocietyMaintenancePaidHistoryRepository;

//defining the business logic
@Service
public class SocietyService {
	@Autowired
	private SocietyMaintenanceEntryRepository societyMaintenanceEntryRepository;

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
	SocietyUtilServices societyUtilServices;

//getting all books record by using the method findaAll() of CrudRepository
	public List<SocietyMaintenanceEntry> getAllSocietyMaintenanceEntries() {
		List<SocietyMaintenanceEntry> sme = new ArrayList<SocietyMaintenanceEntry>();
		societyMaintenanceEntryRepository.findAll().forEach(sme1 -> sme.add(sme1));
		return sme;
	}

	void updateMasterEntry(int flatNumber, double amountToDeduct) throws Exception {
		try {
			// MAINTENANCEMASTERENTRY
			Optional<MaintenanceMasterEntry> masterEntry = Optional.ofNullable(getMasterEntry(flatNumber));

			if (masterEntry.isPresent()) {
				MaintenanceMasterEntry masterEntryExisting = masterEntry.get();
				Double recievedTillNow = Optional.ofNullable(repositoryMaintenancePaidHistory.getPaidAmountTillNow(flatNumber)).orElse(0.0);

				String flatType = getFlatType(flatNumber);
				String latestFinancialYear = getLatestYear();
				double totalOutstanding = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber)
						- recievedTillNow;
				Date lastestDateUndeleted = repositoryMaintenancePaidHistory.findLatestReceivedDate(flatNumber);
				System.out.println("recievedTillNow----------:" + recievedTillNow);

				System.out.println("totalOutstanding---------:" + totalOutstanding);
				
				masterEntryExisting.getCurrentYear();

				masterEntryExisting.setTotalOutstanding(totalOutstanding);
				masterEntryExisting.setReceivedTillNow(recievedTillNow);
				masterEntryExisting.setLastRecievedDate(lastestDateUndeleted);

				repositoryMaintenanceMasterEntry.save(masterEntryExisting);

				String nearestYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
				masterEntryExisting.setCurrentYear(nearestYear);
				repositoryMaintenanceMasterEntry.save(masterEntryExisting);
			}
		} catch (Exception e) {
			throw new Exception("Error occured while updating MasterEntry. " + e.getMessage());
		}
	}

	public ResponseEntity<String> editPaidHistory(SocietyMaintenancePaidHistory societyMaintenancePaidHistory)
			throws Exception {

		Long id = societyMaintenancePaidHistory.getId();
		
		try {
			Optional<SocietyMaintenancePaidHistory> smphExistingOptional = Optional.ofNullable(repositoryMaintenancePaidHistory.findByIdAndDeletedFalse(id));
			
			if (smphExistingOptional.isPresent()) {
				SocietyMaintenancePaidHistory societyMaintenancePaidHistoryExisting = smphExistingOptional.get();
				if (societyMaintenancePaidHistory.equals(societyMaintenancePaidHistoryExisting)) {
					String msg = "Tried to save the existing record";
					System.out.println(msg);
					return ResponseEntity.ok(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Error while checking exsiting entry!");
		}
		
		
		try {
			softDeletePaidHistory(id, "Edited");
			repositoryMaintenancePaidHistory.save(societyMaintenancePaidHistory);
			System.out.println("Successfully saved updated paid history.");
			
			updateMasterEntry(societyMaintenancePaidHistory.getFlatNumber(), societyMaintenancePaidHistory.getAmount());

			System.out.println("Successfully updated the master data.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Error while saving edited entry!");
		}

		return ResponseEntity.ok().body("Successfully edited.");
	}

	public ResponseEntity<String> softDeletePaidHistory(Long id, String deleteReason) throws Exception {
		System.out.println("SocietyService.softDeletePaidHistory()");
		String msg = "Error while deleting entry!";
		try {
			Optional<SocietyMaintenancePaidHistory> optionalHistory = repositoryMaintenancePaidHistory.findById(id);
			// Update Paid history table
			if (optionalHistory.isPresent()) {
				SocietyMaintenancePaidHistory history = optionalHistory.get();
				int flatNumber = history.getFlatNumber();
				double amountToDeduct = history.getAmount();
				history.setDeleted(true);
				history.setDeletedReason(deleteReason);
				repositoryMaintenancePaidHistory.save(history);

				updateMasterEntry(flatNumber, amountToDeduct);

				return ResponseEntity.ok().body("Successfully deleted.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(msg);
		}

		return ResponseEntity.badRequest().body(msg);// Or throw an exception if required
	}

	// saving a specific record by using the method save() of CrudRepository
	public ResponseEntity<SocietyMaintenanceEntry> saveOrUpdate(SocietyMaintenanceEntry sme) throws Exception {

		System.out.println("In Saving : " + sme);
		double receivedTillNowToUpdate = sme.getReceivedTillNow(); // If the record is new else new value be calculated

		int flatNumber = sme.getFlatNumber();// .
		String flatType = getFlatType(flatNumber);// .
		String latestFinancialYear = getLatestYear();// .
		String nearestYear = getNearestYear(latestFinancialYear, flatNumber, flatType);// .

		double annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);// .
		double outstandingMaintenance = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);// .
		double paidAmountForYear = 0;
		String financialYearOfDate = societyUtilServices.getFinancialYearOfDate(sme.getDate());// .
		SocietyMaintenancePaidHistory maintenancePaidHistory = new SocietyMaintenancePaidHistory();// .

		maintenancePaidHistory.setFlatType(flatType);
		maintenancePaidHistory.setFlatNumber(flatNumber);
		maintenancePaidHistory.setAmount(sme.getAmount());
		maintenancePaidHistory.setTransactionId(sme.getTransactionId());
		maintenancePaidHistory.setDate(sme.getDate());
		maintenancePaidHistory.setAnnualMaintenance(annualMaintenance);
		// maintenancePaidHistory.setYear(sme.getYear());
		maintenancePaidHistory.setYear(financialYearOfDate);
		maintenancePaidHistory.setPaymentMethod(sme.getPaymentMethod());
		maintenancePaidHistory.setVerified(sme.isVerified());

		try {
			paidAmountForYear = getPaidAmountForYear(flatNumber, sme.getYear());// .
		} catch (Exception e) {
			e.printStackTrace();
		}
		double outstandingTillFinancialYear = getOutstandingTillFinancialYear(getLatestYear(), flatType, flatNumber);// .
		// Optional<SocietyMaintenanceEntry> smeExistingOptional =
		// Optional.ofNullable(getSmeById(sme.getFlatNumber()));
		Optional<MaintenanceMasterEntry> masterEntry = Optional.ofNullable(getMasterEntry(sme.getFlatNumber()));

		double outstandingToUpdate = 0.0;
		double extraCharges = 0.0;

		try {
			// At first check any extra charges, if charges are zero the outstanding will be
			// zero
			extraCharges = societyUtilServices.getExtraCharges(flatNumber);
		} catch (Exception e) {
			System.out.println("Error while fetching extra charges");
		}

		if (masterEntry.isPresent()) {
			MaintenanceMasterEntry masterEntryExisting = masterEntry.get();
			// Received Till Now
			double receivedTillNowExist = masterEntryExisting.getReceivedTillNow();
			receivedTillNowToUpdate = sme.getAmount() + receivedTillNowExist;

			// OutStanding is outstandingMaintenance-> calculated maintenance
			outstandingToUpdate = outstandingMaintenance - (sme.getPreviousOutstanding() + sme.getAmount());

			// Paid amount In year
			System.out.println("paidAmountForYear:" + paidAmountForYear);
			System.out.println("smeExisting paidAmountForYear: " + sme.getPaidInYear());
			sme.setPaidInYear(paidAmountForYear + sme.getAmount());

			sme.setPreviousOutstanding(outstandingToUpdate);

			System.out.println("After change: " + sme);
		} else {
			receivedTillNowToUpdate = sme.getAmount();
			outstandingToUpdate = outstandingTillFinancialYear - sme.getAmount();

			sme.setPaidInYear(sme.getAmount());
			sme.setPreviousOutstanding(outstandingMaintenance - sme.getAmount());
			System.out.println("sme: " + sme);
			System.out.println("New Id to be created. SME Record: " + sme);
		}

		// Add any extra charges
		outstandingToUpdate = outstandingToUpdate + extraCharges;

		// Paid till Year
		sme.setPaidTillYear(nearestYear);
		sme.setAnnualMaintenance(annualMaintenance);
		sme.setFlatType(getFlatType(flatNumber));
		sme.setReceivedTillNow(receivedTillNowToUpdate);
		sme.setYear(financialYearOfDate);
		
		
		//double extraCharges = extraChargesEntryRepository.sumChargedAmountByFlatNumber(flatNumber);
		MaintenanceMasterEntry maintenanceMasterEntry = new MaintenanceMasterEntry();
		maintenanceMasterEntry.setFlatNumber(flatNumber);
		maintenanceMasterEntry.setCurrentYear(nearestYear);
		maintenanceMasterEntry.setLastRecievedDate(sme.getDate());
		maintenanceMasterEntry.setReceivedTillNow(receivedTillNowToUpdate);
		maintenanceMasterEntry.setChargedAmount(extraCharges);
		maintenanceMasterEntry.setTotalOutstanding(outstandingToUpdate);

		// Not needed
		try {
			System.out.println("Saving data to societyRepositoryEntry ");
			societyMaintenanceEntryRepository.save(sme);
		} catch (Exception e) {
			System.out.println("Error Occured while updating societyRepositoryEntry");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sme);
		}
		try {
			System.out.println("Saving data to maintenanceMasterEntry ");
			repositoryMaintenanceMasterEntry.save(maintenanceMasterEntry);
		} catch (Exception e) {
			System.out.println("Error Occured while updating maintenanceMasterEntry");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sme);
		}
		try {
			System.out.println("Saving data to maintenancePaidHistory ");
			repositoryMaintenancePaidHistory.save(maintenancePaidHistory);

		} catch (Exception e) {
			System.out.println("Error Occured while updating maintenancePaidHistory");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sme);
		}

		return ResponseEntity.ok(sme);
	}

	// saving a specific record by using the method save() of CrudRepository
	public ResponseEntity<SocietyMaintenanceEntry> saveOrUpdateOld(SocietyMaintenanceEntry sme) throws Exception {

		System.out.println("In saveOrUpdate.Saving : " + sme);
		double receivedTillNowToUpdate = sme.getReceivedTillNow(); // If the record is new else new value be calculated

		int flatNumber = sme.getFlatNumber();
		String flatType = getFlatType(flatNumber);
		String latestFinancialYear = getLatestYear();
		String nearestYear = getNearestYear(latestFinancialYear, flatNumber, flatType);

		double annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);
		double outstandingMaintenance = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
		double paidAmountForYear = 0;
		String financialYearOfDate = societyUtilServices.getFinancialYearOfDate(sme.getDate());
		SocietyMaintenancePaidHistory maintenancePaidHistory = new SocietyMaintenancePaidHistory();

		maintenancePaidHistory.setFlatType(flatType);
		maintenancePaidHistory.setFlatNumber(flatNumber);
		maintenancePaidHistory.setAmount(sme.getAmount());
		maintenancePaidHistory.setTransactionId(sme.getTransactionId());
		maintenancePaidHistory.setDate(sme.getDate());
		maintenancePaidHistory.setAnnualMaintenance(annualMaintenance);
		// maintenancePaidHistory.setYear(sme.getYear());
		maintenancePaidHistory.setYear(financialYearOfDate);
		maintenancePaidHistory.setPaymentMethod(sme.getPaymentMethod());
		maintenancePaidHistory.setVerified(sme.isVerified());

		try {
			paidAmountForYear = getPaidAmountForYear(flatNumber, sme.getYear());
		} catch (Exception e) {
			e.printStackTrace();
		}
		double outstandingTillFinancialYear = getOutstandingTillFinancialYear(getLatestYear(), flatType, flatNumber);
		Optional<SocietyMaintenanceEntry> smeExistingOptional = Optional.ofNullable(getSmeById(sme.getFlatNumber()));

		double outstandingToUpdate = 0.0;
		double extraCharges = 0.0;

		try {
			// At first check any extra charges, if charges are zero the outstanding will be
			// zero
			extraCharges = societyUtilServices.getExtraCharges(flatNumber);
		} catch (Exception e) {
			System.out.println("Error while fetching extra charges");
		}

		if (smeExistingOptional.isPresent()) {
			SocietyMaintenanceEntry smeExisting = smeExistingOptional.get();
			// Received Till Now
			double receivedTillNowExist = smeExisting.getReceivedTillNow();
			receivedTillNowToUpdate = sme.getAmount() + receivedTillNowExist;

			// OutStanding is outstandingMaintenance-> calculated maintenance
			outstandingToUpdate = outstandingMaintenance - (sme.getPreviousOutstanding() + sme.getAmount());

			// Paid amount In year
			System.out.println("paidAmountForYear:" + paidAmountForYear);
			System.out.println("smeExisting paidAmountForYear: " + sme.getPaidInYear());
			sme.setPaidInYear(paidAmountForYear + sme.getAmount());

			sme.setPreviousOutstanding(outstandingToUpdate);

			System.out.println("After change: " + sme);
		} else {
			receivedTillNowToUpdate = sme.getAmount();
			outstandingToUpdate = outstandingTillFinancialYear - sme.getAmount();

			sme.setPaidInYear(sme.getAmount());
			sme.setPreviousOutstanding(outstandingMaintenance - sme.getAmount());
			System.out.println("sme: " + sme);
			System.out.println("New Id to be created. SME Record: " + sme);
		}

		// Add any extra charges
		outstandingToUpdate = outstandingToUpdate + extraCharges;

		// Paid till Year
		sme.setPaidTillYear(nearestYear);
		sme.setAnnualMaintenance(annualMaintenance);
		sme.setFlatType(getFlatType(flatNumber));
		sme.setReceivedTillNow(receivedTillNowToUpdate);
		sme.setYear(financialYearOfDate);

		MaintenanceMasterEntry maintenanceMasterEntry = new MaintenanceMasterEntry();
		maintenanceMasterEntry.setFlatNumber(flatNumber);
		maintenanceMasterEntry.setCurrentYear(nearestYear);
		maintenanceMasterEntry.setLastRecievedDate(sme.getDate());
		maintenanceMasterEntry.setReceivedTillNow(receivedTillNowToUpdate);
		maintenanceMasterEntry.setChargedAmount(0.0);
		maintenanceMasterEntry.setTotalOutstanding(outstandingToUpdate);

		try {
			System.out.println("Saving data to societyRepositoryEntry ");
			societyMaintenanceEntryRepository.save(sme);
		} catch (Exception e) {
			System.out.println("Error Occured while updating societyRepositoryEntry");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sme);
		}
		try {
			System.out.println("Saving data to maintenanceMasterEntry ");
			repositoryMaintenanceMasterEntry.save(maintenanceMasterEntry);
		} catch (Exception e) {
			System.out.println("Error Occured while updating maintenanceMasterEntry");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sme);
		}
		try {
			System.out.println("Saving data to maintenancePaidHistory ");
			repositoryMaintenancePaidHistory.save(maintenancePaidHistory);

		} catch (Exception e) {
			System.out.println("Error Occured while updating maintenancePaidHistory");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sme);
		}

		return ResponseEntity.ok(sme);
	}

	
	public List<MaintenanceDashboardCard> getMaintenanceDashboardCardData(int flatNumber) throws Exception {
		
	        return DashboardMapper.convertToDashboardCards(getMaintenanceDashboardData(flatNumber));
		
	}
	
	// Method to fetch maintenance dashboard data
	public MaintenanceDashboardEntry getMaintenanceDashboardData(int flatNumber) throws Exception {

		String latestFinancialYear, flatType, paidTillYear = null;

		double totalPaid, maintenanceOutstanding, annualMaintenance, extraCharges, totalOutstanding = 0.0;
		MaintenanceDashboardEntry maintenanceDashboardEntry = null;
		try {
			latestFinancialYear = getLatestYear();
			flatType = getFlatType(flatNumber);
			totalPaid = getReceivedTillNowByFlatNumber(flatNumber);
			paidTillYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
			maintenanceOutstanding = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
			annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);
			extraCharges = societyUtilServices.getExtraCharges(flatNumber);
			totalOutstanding = maintenanceOutstanding + extraCharges;

			maintenanceDashboardEntry = new MaintenanceDashboardEntry();
			maintenanceDashboardEntry.setFlatType(flatType);
			maintenanceDashboardEntry.setTotalPaid(totalPaid);
			maintenanceDashboardEntry.setExtraCharges(extraCharges);
			maintenanceDashboardEntry.setPaidTillYear(paidTillYear);
			maintenanceDashboardEntry.setMaintenanceOutstanding(maintenanceOutstanding);
			maintenanceDashboardEntry.setAnnualMaintenance(annualMaintenance);
			maintenanceDashboardEntry.setTotalOutstanding(totalOutstanding);
		} catch (Exception e) {
			System.out.println("Error while getting dashboard data.");
			return maintenanceDashboardEntry;
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

		System.out.println("historyList:" + historyList);

		// Sort the list by year
		historyList.sort(Comparator.comparing(SocietyMaintenancePaidHistory::getYear));

		System.out.println("Sorted list: " + historyList);

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

		double receivedTillNow = getReceivedTillNowByFlatNumber(flatNumber);

		Optional<Double> expectedBalanceOptional = Optional.ofNullable(
				repositoryMaintenanceValues.sumMaintenanceValueByYearAndFlatType(latestFinancialYear, flatType));

		double expectedBalance = expectedBalanceOptional.orElse(0.0); // Default value if sum is null

		if (expectedBalance == 0) {
			throw new Exception("Error in calculating expectedBalanceOptional");
		}

		if (receivedTillNow != 0) {

			if (receivedTillNow >= expectedBalance) {

				return latestFinancialYear;

			} else {
				return getRespectiveNearestYear(flatType, receivedTillNow, flatNumber);
			}
		} else {
			Optional<String> firstYearOptional = Optional.of(repositoryMaintenanceValues.findFirstYear());
			return firstYearOptional.get();
		}
	}

	private String getRespectiveNearestYear(String flatType, double receivedTillNow, int flatNumber) throws Exception {
		Map<String, Object> result = getRespectiveFinancialYearAndExtraAmount(flatType, receivedTillNow, flatNumber);

		return (String) result.get("financialYear");
	}

	public Map<String, Object> getRespectiveFinancialYearAndExtraAmount(String flatType, double receivedTillNow,
			int flatNumber) throws Exception {
		Map<String, Object> result = repositoryMaintenanceValues.findYearAndExtraAmount(receivedTillNow, flatType);

		if (result != null && !result.isEmpty()) {
			String financialYear = (String) result.get("financialYear");
			double cumulativeSum = ((Number) result.get("cumulativeSum")).doubleValue();
			double extraAmount = ((Number) result.get("extraAmount")).doubleValue();
			System.out.println("Financial Year: " + financialYear);
			System.out.println("Cumulative Sum : " + cumulativeSum);
			System.out.println("Extra Amount: " + extraAmount);
		} else {
			String financialYear = getFirstYear();
			double cumulativeSum = receivedTillNow;
			double extraAmount = getOutstandingTillFinancialYear(financialYear, flatType, flatNumber);
			result.put("financialYear", financialYear);
			result.put("cumulativeSum", cumulativeSum);
			result.put("extraAmount", extraAmount);
			System.out.println("No matching financial year found");
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

	// getting a specific record by using the method findById() of CrudRepository
	public SocietyMaintenanceEntry getSmeById(int id) throws Exception {
		System.out.println("In getSmeById.Getting Id : " + id);
		Optional<SocietyMaintenanceEntry> smeOptional = societyMaintenanceEntryRepository.findById(id);

		return smeOptional.orElse(null);
	}

	public MaintenanceMasterEntry getMasterEntry(int id) throws Exception {
		System.out.println("In getSmeById.Getting Id : " + id);
		Optional<MaintenanceMasterEntry> masterEntry = repositoryMaintenanceMasterEntry.findById(id);

		return masterEntry.orElse(null);
	}

	public ExtraChargesEntry addEntry(ExtraChargesEntry entry) {

		return extraChargesEntryRepository.save(entry);
	}

	public double getAnnualMaintenanceByFlatNumber(int flatNumber) throws Exception {

		Optional<Double> annualMaintenanceOptional = repositoryFlatNumberMap
				.getAnnualMaintenanceByFlatNumber(flatNumber);

		if (annualMaintenanceOptional.isPresent()) {
			return annualMaintenanceOptional.get();
		} else
			throw new Exception("Error while getting the annual maintenance");
	}

//deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(int id) {
		societyMaintenanceEntryRepository.deleteById(id);
	}

//updating a record
	public void update(SocietyMaintenanceEntry sme, int bookid) {
		societyMaintenanceEntryRepository.save(sme);
	}

	public ResponseEntity<ExtraChargesEntry> saveExtraCharges(ExtraChargesEntry exe) throws Exception {

		try {
			extraChargesEntryRepository.save(exe);
		} catch (Exception e) {
			System.out.println("Error while saving extra charges.");
			return ResponseEntity.badRequest().body(exe);
		}

		Optional<MaintenanceMasterEntry> optionalMme = null;
		try {
			optionalMme = repositoryMaintenanceMasterEntry.findById(exe.getFlatNumber());
		} catch (Exception e) {
			System.out.println("Error while getting MaintenanceMasterEntry for flat number:" + exe.getFlatNumber());
			return ResponseEntity.badRequest().body(exe);
		}

		try {
			if (optionalMme.isPresent()) {
				System.out.println("Updating extra charges...");

				MaintenanceMasterEntry maintenanceMasterEntry = optionalMme.get();
				double updateChargedExtraAmount = maintenanceMasterEntry.getChargedAmount() + exe.getAmount();
				double updateTotalOutstanding = maintenanceMasterEntry.getTotalOutstanding() + exe.getAmount();

				maintenanceMasterEntry.setChargedAmount(updateChargedExtraAmount);
				maintenanceMasterEntry.setTotalOutstanding(updateTotalOutstanding);
				repositoryMaintenanceMasterEntry.save(maintenanceMasterEntry);
			}
		} catch (Exception e) {
			System.out.println("Error while Updating extra charges...");
			return ResponseEntity.badRequest().body(exe);
		}

		return ResponseEntity.ok().body(exe);
	}

	public ResponseEntity<SocietyMaintenanceEntry> getSmeByIdResponse(int flatId) throws Exception {
		// Create a new instance of SocietyMaintenanceEntry
		SocietyMaintenanceEntry societyMaintenanceEntry = new SocietyMaintenanceEntry();

		String getFlatType = getFlatType(flatId);
		String year = getNearestYear(getLatestYear(), flatId, getFlatType);
		double previousOutstanding = getOutstandingTillFinancialYear(getLatestYear(), getFlatType, flatId);
		double extraCharges = societyUtilServices.getExtraCharges(flatId);
		double totalOutstanding = previousOutstanding + extraCharges;
		double annualMaintenance = getAnnualMaintenanceByFlatNumber(flatId);
		double receivedTillNow = getReceivedTillNowByFlatNumber(flatId);
		double paidInYear = getPaidAmountForYear(flatId, year);
		// Set values using setter methods
		societyMaintenanceEntry.setFlatNumber(flatId);
		societyMaintenanceEntry.setYear(year);
		societyMaintenanceEntry.setFlatType(getFlatType);
		societyMaintenanceEntry.setPreviousOutstanding(totalOutstanding);
		societyMaintenanceEntry.setAnnualMaintenance(annualMaintenance);
		societyMaintenanceEntry.setReceivedTillNow(receivedTillNow);
		societyMaintenanceEntry.setPaidTillYear(year);
		societyMaintenanceEntry.setPaidInYear(paidInYear);

		return ResponseEntity.ok().body(societyMaintenanceEntry);
	}
}