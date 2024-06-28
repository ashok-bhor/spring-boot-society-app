package com.javatpoint.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.javatpoint.model.MaintenanceDashboardEntry;
import com.javatpoint.model.MaintenanceMasterEntry;
import com.javatpoint.model.SocietyMaintenanceEntry;
import com.javatpoint.model.SocietyMaintenancePaidHistory;
import com.javatpoint.repository.FlatTypeFlatNumberMapRepository;
import com.javatpoint.repository.MaintenanceMasterEntryRepo;
import com.javatpoint.repository.MaintenanceValuesRepository;
import com.javatpoint.repository.SocietyMaintenanceEntryRepository;
import com.javatpoint.repository.SocietyMaintenancePaidHistoryRepository;

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


//getting all books record by using the method findaAll() of CrudRepository
	public List<SocietyMaintenanceEntry> getAllSocietyMaintenanceEntries() {
		List<SocietyMaintenanceEntry> sme = new ArrayList<SocietyMaintenanceEntry>();
		societyMaintenanceEntryRepository.findAll().forEach(sme1 -> sme.add(sme1));
		return sme;
	}

	// saving a specific record by using the method save() of CrudRepository
	public ResponseEntity<SocietyMaintenanceEntry> saveOrUpdate(SocietyMaintenanceEntry sme) throws Exception {

		System.out.println("In saveOrUpdate.Saving : " + sme);
		double receivedTillNowToUpdate = sme.getReceivedTillNow(); // If the record is new else new value be calculated

		int flatNumber = sme.getFlatNumber();
		String flatType = getFlatType(flatNumber);
		String latestFinancialYear = getLatestYear();
		String nearestYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
		double annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);
		double outstandingMaintenance = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
		double paidAmountForYear = 0;

		SocietyMaintenancePaidHistory maintenancePaidHistory = new SocietyMaintenancePaidHistory();

		maintenancePaidHistory.setFlatType(flatType);
		maintenancePaidHistory.setFlatNumber(flatNumber);
		maintenancePaidHistory.setAmount(sme.getAmount());
		maintenancePaidHistory.setTransactionId(sme.getTransactionId());
		maintenancePaidHistory.setDate(sme.getDate());
		maintenancePaidHistory.setAnnualMaintenance(annualMaintenance);
		maintenancePaidHistory.setYear(sme.getYear());
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

		if (smeExistingOptional.isPresent()) {
			SocietyMaintenanceEntry smeExisting = smeExistingOptional.get();
			// Received Till Now
			double receivedTillNowExist = smeExisting.getReceivedTillNow();
			receivedTillNowToUpdate = sme.getAmount() + receivedTillNowExist;

			// OutStanding
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
		// Paid till Year
		sme.setPaidTillYear(nearestYear);
		sme.setAnnualMaintenance(annualMaintenance);
		sme.setFlatType(getFlatType(flatNumber));
		sme.setReceivedTillNow(receivedTillNowToUpdate);

		MaintenanceMasterEntry maintenanceMasterEntry = new MaintenanceMasterEntry();
		maintenanceMasterEntry.setFlatNumber(flatNumber);
		maintenanceMasterEntry.setCurrentYear(nearestYear);
		maintenanceMasterEntry.setLastRecievedDate(sme.getDate());
		maintenanceMasterEntry.setReceivedTillNow(receivedTillNowToUpdate);
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

	// Method to fetch maintenance dashboard data
	public MaintenanceDashboardEntry getMaintenanceDashboardData(int flatNumber) throws Exception {

		String latestFinancialYear, flatType, paidTillYear = null;

		double totalPaid, outstanding, annualMaintenance = 0.0;
		MaintenanceDashboardEntry maintenanceDashboardEntry = null;
		try {
			latestFinancialYear = getLatestYear();
			flatType = getFlatType(flatNumber);
			totalPaid = getReceivedTillNowByFlatNumber(flatNumber);
			paidTillYear = getNearestYear(latestFinancialYear, flatNumber, flatType);
			outstanding = getOutstandingTillFinancialYear(latestFinancialYear, flatType, flatNumber);
			annualMaintenance = getAnnualMaintenanceByFlatNumber(flatNumber);
			
			maintenanceDashboardEntry = new MaintenanceDashboardEntry();
			maintenanceDashboardEntry.setFlatType(flatType);
			maintenanceDashboardEntry.setTotalPaid(totalPaid);
			maintenanceDashboardEntry.setPaidTillYear(paidTillYear);
			maintenanceDashboardEntry.setOutstanding(outstanding);
			maintenanceDashboardEntry.setAnnualMaintenance(annualMaintenance);
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
		List<SocietyMaintenancePaidHistory> historyList = repositoryMaintenancePaidHistory.findByFlatNumber(flatNumber);

		// Sort the list by year
		historyList.sort(Comparator.comparing(SocietyMaintenancePaidHistory::getYear));

		System.out.println("Sorted list: " + historyList);

		return historyList;
	}

	String getFlatType(int flatNumber) throws Exception {
		Optional<String> latestYearOptional = repositoryFlatNumberMap.getFlatTypeByFlatNumber(flatNumber);
		if (latestYearOptional.isPresent())
			throw new Exception("Error in getting Flat type");
		return latestYearOptional.get();
	}

	String getFirstYear() throws Exception {
		Optional<String> latestYearOptional = Optional.of(repositoryMaintenanceValues.findFirstYear());
		if (latestYearOptional.isPresent())
			throw new Exception("Error in getting First Year");
		return latestYearOptional.get();
	}

	String getLatestYear() throws Exception {
		Optional<String> latestYearOptional = Optional.of(repositoryMaintenanceValues.findLatestYear());
		if (latestYearOptional.isPresent())
			throw new Exception("Error in getting Latest Year");
		return latestYearOptional.get();
	}

	public String getNearestYear(String latestFinancialYear, int flatNumber, String flatType) throws Exception {

		double receivedTillNow = getReceivedTillNowByFlatNumber(flatNumber);
		// getOutstandingTillFinancialYear(getLatestFinancialYear, flatType,
		// flatNumber);

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

//getting a specific record by using the method findById() of CrudRepository
	public ResponseEntity<SocietyMaintenanceEntry> getSmeByIdResponse(int id) throws Exception {
		System.out.println("In getSmeById. Getting Id : " + id);
		Optional<SocietyMaintenanceEntry> smeOptional = societyMaintenanceEntryRepository.findById(id);

		double annualMaintenance = getAnnualMaintenanceByFlatNumber(id);
		SocietyMaintenanceEntry sme = null;
		String latestFinancialYear = getLatestYear();
		String flatType = getFlatType(id);
		String nearestYear = getNearestYear(latestFinancialYear, id, flatType);
		if (smeOptional.isPresent()) {
			sme = smeOptional.get();
			sme.setAnnualMaintenance(annualMaintenance);
			sme.setReceivedTillNow(getReceivedTillNowByFlatNumber(id));

			// Need not to be shown on screen
			sme.setDate(null);
			sme.setTransactionId("");
			sme.setPaymentMethod("");
			sme.setAmount(0.0);
			sme.setVerified(false);
			sme.setFlatType(getFlatType(id));
			sme.setPaidTillYear(nearestYear);
			return ResponseEntity.ok(sme); // Return 200 OK with the found entity
		} else {
			System.out.println("Flat ID provided is not present.");
			sme = new SocietyMaintenanceEntry();
			sme.setYear(getFirstYear());
			sme.setFlatType(getFlatType(id));
			sme.setAnnualMaintenance(getAnnualMaintenanceByFlatNumber(id));
			sme.setPreviousOutstanding(getOutstandingTillFinancialYear(getLatestYear(), getFlatType(id), id));
			sme.setReceivedTillNow(0);
			sme.setPaidTillYear("-");
			sme.setPaidInYear(0.0);
			sme.setPaymentMethod(null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(sme); // Return 404 Not Found
		}

	}

	// getting a specific record by using the method findById() of CrudRepository
	public SocietyMaintenanceEntry getSmeById(int id) throws Exception {
		System.out.println("In getSmeById.Getting Id : " + id);
		Optional<SocietyMaintenanceEntry> smeOptional = societyMaintenanceEntryRepository.findById(id);

		/*
		 * double annualMaintenance = getAnnualMaintenanceByFlatNumber(id); if
		 * (smeOptional.isPresent()) { sme = smeOptional.get();
		 * sme.setAnnualMaintenance(annualMaintenance);
		 * sme.setReceivedTillNow(getReceivedTillNowByFlatNumber(id)); } else {
		 * System.out.println("Flat ID provided is not present."); sme = new
		 * SocietyMaintenanceEntry(); sme.setYear(getFirstYear());
		 * sme.setFlatType(getFlatType(id));
		 * sme.setAnnualMaintenance(getAnnualMaintenanceByFlatNumber(id));
		 * sme.setPreviousOutstanding(getOutstandingTillFinancialYear(getLatestYear(),
		 * getFlatType(id), id)); sme.setPaidTillYear(0.0); sme.setReceivedTillNow(0);
		 * sme.setPaidInYear(0.0); sme.setPaymentMethod(null); }
		 */
		return smeOptional.orElse(null);
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
}