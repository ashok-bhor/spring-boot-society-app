package com.abpoint.extra.charges.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abpoint.model.DeletedExtraCharges;
import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.ExtraChargesEntryForAllFlats;
import com.abpoint.model.MaintenanceMasterEntry;
import com.abpoint.repository.DeleteExtraChargesRepository;
import com.abpoint.repository.ExtraChargesEntryRepository;
import com.abpoint.repository.MaintenanceMasterEntryRepo;
import com.abpoint.service.PaymentApprovalService;

import lombok.extern.slf4j.Slf4j;

//defining the business logic
@Slf4j
@Service
public class ExtraChargesServices {

	@Autowired
	private MaintenanceMasterEntryRepo repositoryMaintenanceMasterEntry;

	@Autowired
	ExtraChargesEntryRepository extraChargesEntryRepository;

	@Autowired
	DeleteExtraChargesRepository deleteExtraChargesRepository;

	private static final Logger logger = LoggerFactory.getLogger(ExtraChargesServices.class);

	@Transactional
	public String saveUpdateExtraChargesForAll(ExtraChargesEntryForAllFlats listExtraCharges) {

		try {
			List<Integer> flatList = extraChargesEntryRepository.findDistinctFlatNumbersFromFlat();
			logger.info("Flat list fetched. Total flats are: "+flatList.size());
			

			for (int flatNumber : flatList) {
				ExtraChargesEntry extraCharges = new ExtraChargesEntry();

				extraCharges.setChargedAmount(listExtraCharges.getChargedAmount());
				extraCharges.setComments(listExtraCharges.getComments());
				extraCharges.setReason(listExtraCharges.getReason());
				extraCharges.setFlatNumber(flatNumber);
				saveUpdateExtraCharges(extraCharges, 0.0); //0.0. is bcz old charges are not applicable for this request
			}
		} catch (Exception e) {
			logger.error("Error while adding extra charges.", e);
			return "Failed to add extra charges for all flats.";
		}
		return "Extra charges added for all flats.";
	}

	@Transactional
	public ResponseEntity<ExtraChargesEntry> saveUpdateExtraCharges(ExtraChargesEntry editedExtraCharges,
			Double oldCharges) {
		try {
			Optional<MaintenanceMasterEntry> optionalMme = repositoryMaintenanceMasterEntry
					.findById(editedExtraCharges.getFlatNumber());
			if (optionalMme.isPresent()) {
				updateMaintenanceMasterEntry(optionalMme.get(), editedExtraCharges, oldCharges);
			} else {
				createNewMaintenanceMasterEntry(editedExtraCharges);
			}
			extraChargesEntryRepository.save(editedExtraCharges);
			logger.info("Added extra charges entry for flat number {}", editedExtraCharges.getFlatNumber());
		} catch (Exception e) {
			logger.error("Error while saving extra charges.", e);
			return ResponseEntity.badRequest().body(editedExtraCharges);
		}
		return ResponseEntity.ok().body(editedExtraCharges);
	}

	private void updateMaintenanceMasterEntry(MaintenanceMasterEntry entry, ExtraChargesEntry editedExtraCharges,
			Double oldCharges) {

		double existingChargedExtraAmount = entry.getChargedAmount();
		double toUpdateChargedAmount = existingChargedExtraAmount - oldCharges + editedExtraCharges.getChargedAmount();
		//double updateTotalOutstanding = entry.getTotalOutstanding() - oldCharges + editedExtraCharges.getChargedAmount();
		entry.setChargedAmount(toUpdateChargedAmount);
		//entry.setTotalOutstanding(updateTotalOutstanding);
		repositoryMaintenanceMasterEntry.save(entry);
		logger.info("Updated extra charges to exsiting master maintenance for flat number: {}",
				editedExtraCharges.getFlatNumber());
	}

	private void createNewMaintenanceMasterEntry(ExtraChargesEntry charges) {
		MaintenanceMasterEntry entry = new MaintenanceMasterEntry();
		double updateTotalOutstanding = charges.getChargedAmount();
		entry.setFlatNumber(charges.getFlatNumber());
		entry.setChargedAmount(updateTotalOutstanding);
		//entry.setTotalOutstanding(updateTotalOutstanding);
		repositoryMaintenanceMasterEntry.save(entry);
		logger.info("MaintenanceMasterEntry was not found, added new entry in master for flat number: {}",
				charges.getFlatNumber());
	}

	public ResponseEntity<String> deleteExtraCharges(Long chargeId, Map<String, String> reasonDetails) {

		ExtraChargesEntry extraCharges = null;
		DeletedExtraCharges deletedExtraCharges = null;
		String reasonSelet = reasonDetails.get("deleteReasonSelect");
		;
		String reasonOther = reasonDetails.get("deleteReasonOther");
		try {
			// Step 0: Check if ExtraChargesEntry exists and fill deleteExtraChargeEntry
			extraCharges = extraChargesEntryRepository.getOne(chargeId);
			deletedExtraCharges = new DeletedExtraCharges(extraCharges, reasonSelet, reasonOther);
		} catch (Exception e) {
			logger.error("Error while getting extra charges entry: {}", e.getMessage(), e);
			return ResponseEntity.notFound().build();
		}

		try {
			// Step 1: Check if MaintenanceMasterEntry exists
			Optional<MaintenanceMasterEntry> optionalMme = repositoryMaintenanceMasterEntry
					.findById(deletedExtraCharges.getFlatNumber());
			if (!optionalMme.isPresent()) {
				logger.warn("MaintenanceMasterEntry not found for flat number: {}",
						deletedExtraCharges.getFlatNumber());
				return ResponseEntity.notFound().build();
			}

			// Step 2: Delete extra charges entry
			try {
				extraChargesEntryRepository.deleteById(deletedExtraCharges.getId());
				deleteExtraChargesRepository.save(deletedExtraCharges);
			} catch (Exception e) {
				logger.error("Error while deleting extra charges entry: {}", e.getMessage(), e);
				return ResponseEntity.status(500).body("Error while deleting extra charges.");
			}

			// Step 3: Update MaintenanceMasterEntry
			try {
				if (optionalMme.isPresent()) {
					MaintenanceMasterEntry maintenanceMasterEntry = optionalMme.get();
					double updatedChargedAmount = maintenanceMasterEntry.getChargedAmount()
							- deletedExtraCharges.getAmount();
					/*
					 * double updatedTotalOutstanding = maintenanceMasterEntry.getTotalOutstanding()
					 * - deletedExtraCharges.getAmount();
					 */

					maintenanceMasterEntry.setChargedAmount(updatedChargedAmount);
					//maintenanceMasterEntry.setTotalOutstanding(updatedTotalOutstanding);
					repositoryMaintenanceMasterEntry.save(maintenanceMasterEntry);
				}
			} catch (Exception e) {
				logger.error("Error while deleting charges from MaintenanceMasterEntry: {}", e.getMessage(), e);
				return ResponseEntity.status(500).body("Error while  deleting charges from MaintenanceMasterEntry.");
			}

			return ResponseEntity.ok("Extra charges deleted successfully.");
		} catch (Exception e) {
			logger.error("Failed to delete charges. expected error occurred: {}", e.getMessage(), e);
			return ResponseEntity.status(500).body("Failed to delete charges. An unexpected error occurred.");
		}
	}

	public double getExtraCharges(int flatNumber) {

		Optional<Double> extraCharges = Optional
				.ofNullable(extraChargesEntryRepository.sumChargedAmountByFlatNumber(flatNumber));

		return extraCharges.orElse(0.0);
	}

	public ResponseEntity<List<ExtraChargesEntry>> getExtraChargeEntry(int flatNumber) {
		Optional<List<ExtraChargesEntry>> extraCharges = Optional
				.ofNullable(extraChargesEntryRepository.findByFlatNumber(flatNumber));

		return ResponseEntity.ok().body(extraCharges.orElse(null));
	}

	public ResponseEntity<List<ExtraChargesEntry>> getAllExtraCharges() {
		Optional<List<ExtraChargesEntry>> extraCharges = Optional.ofNullable(extraChargesEntryRepository.findAll());
		return ResponseEntity.ok().body(extraCharges.orElse(null));
	}

}