package com.abpoint.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abpoint.model.ActionedPaymentsDetails;
import com.abpoint.model.ApprovalPendingPayment;
import com.abpoint.model.SocietyMaintenanceEntry;
import com.abpoint.repository.ActionedPaymentsDetailsRepository;
import com.abpoint.repository.ApprovalPendingPaymentRepository;
import com.abpoint.repository.MaintenanceMasterEntryRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentApprovalService {

	private static final Logger log = LoggerFactory.getLogger(PaymentApprovalService.class);

	@Autowired
	ApprovalPendingPaymentRepository repositoryApprovalPendingPayment;

	@Autowired
	ActionedPaymentsDetailsRepository repositoryActionedPayment;

	@Autowired
	MaintenanceMasterEntryRepo maintenanceMasterEntryRepo;

	@Autowired
	SocietyService societyService;

	public ResponseEntity<SocietyMaintenanceEntry> saveToPendingPayments(ApprovalPendingPayment approvalPendingPayment)
			throws Exception {

		int flatNumber = approvalPendingPayment.getFlatNumber();
		ApprovalPendingPayment approvalPendingPaymentExisting =null;
		SocietyMaintenanceEntry entry = null;
		Long savedId = null;

		try { // Check if an entry with the same parameters already exists
			Optional<ApprovalPendingPayment> existingEntry = repositoryApprovalPendingPayment.findByParameters(
					approvalPendingPayment.getFlatNumber(), approvalPendingPayment.getTransactionId(),
					approvalPendingPayment.getAmount(), approvalPendingPayment.getPaymentMethod(),
					approvalPendingPayment.getDate());

			if (existingEntry.isPresent()) { // Entry already exists, handle accordingly
				log.error("Entry with flat number " + flatNumber + " and transaction ID "
						+ approvalPendingPayment.getTransactionId() + " already exists.");
				approvalPendingPaymentExisting = existingEntry.get();
				return ResponseEntity.status(HttpStatus.CONFLICT).body(entry); // 409 Conflict
			}
		} catch (Exception e) {
			log.error("Error while saving Pending Payment.");
			e.printStackTrace();
			entry = societyService.getSmeByIdResponse(flatNumber);
			return ResponseEntity.badRequest().body(entry);
		}

		try {
			ApprovalPendingPayment savedForApprovalPayment = repositoryApprovalPendingPayment
					.save(approvalPendingPayment);
			savedId = savedForApprovalPayment.getId();
			entry = societyService.getSmeByIdResponse(flatNumber);
			entry.setId(savedId);
		} catch (Exception e) {
			System.out.println("Error while saving Pending Payment.");
			e.printStackTrace();
			entry = societyService.getSmeByIdResponse(flatNumber);
			return ResponseEntity.badRequest().body(entry);
		}
		System.out.println("Pending Payment saved successfully.");
		return ResponseEntity.ok(entry);

	}

	public ResponseEntity<List<ApprovalPendingPayment>> getUnapprovedPayments(int flatNumber) {

		List<ApprovalPendingPayment> list = null;
		try {
			list = repositoryApprovalPendingPayment.findByFlatNumber(flatNumber);
		} catch (Exception e) {
			System.out.println("Error while fetching list of Pending Payment.");
			e.printStackTrace();
			return ResponseEntity.badRequest().body(list);
		}
		System.out.println("Fetching list of Pending Payments was successfully.");
		return ResponseEntity.ok(list);
	}

	public ActionedPaymentsDetails rejectPayment(Long id, Map<String, String> actionDetails) {
		return actionPayment(id, actionDetails);
	}

	public ActionedPaymentsDetails actionPayment(Long id, Map<String, String> actionDetails) {

		String action = actionDetails.get("action");
		String actionedBy = actionDetails.get("actionedBy");
		String actionedTimestamp = actionDetails.get("actionedTimestamp");
		String actionersUserAgentDetails = actionDetails.get("actionersUserAgentDetails");
		String rejectReason = "";

		if (action.equalsIgnoreCase("Rejected"))
			rejectReason = actionDetails.get("rejectReason");

		ActionedPaymentsDetails actionedPayment = null;

		Optional<ApprovalPendingPayment> pendingPaymentOptional = repositoryApprovalPendingPayment.findById(id);

		if (pendingPaymentOptional.isEmpty()) {
			System.out.println("The payment id passed is not present in Unapproved list. Payment id: " + id);
			return null;
		}
		ApprovalPendingPayment pendingPayment = pendingPaymentOptional.get();

		System.out.println("Payment with found with Id : " + id);

		try {
			actionedPayment = new ActionedPaymentsDetails(pendingPayment, action, rejectReason, actionedBy,
					actionedTimestamp, actionersUserAgentDetails);

			repositoryActionedPayment.save(actionedPayment);

			repositoryApprovalPendingPayment.deleteById(id);
		} catch (Exception e) {
			System.out.println("Error while saving Rejected Payment.");
			e.printStackTrace();
			return null;

		}
		System.out.println("Actioned successfully: " + actionedPayment);
		return actionedPayment;
	}

	public ResponseEntity<List<ActionedPaymentsDetails>> getRejectedPayments(int flatNumber) {
		List<ActionedPaymentsDetails> list = null;
		try {
			list = repositoryActionedPayment.findByFlatNumberAndAction(flatNumber, "Rejected");
		} catch (Exception e) {
			System.out.println("Error while fetching list of Pending Payment.");
			e.printStackTrace();
			return ResponseEntity.badRequest().body(list);
		}
		System.out.println("Fetching list of Pending Payments was successfully.");
		return ResponseEntity.ok(list);
	}

	public ResponseEntity<List<ApprovalPendingPayment>> getAllUnapprovedPayments() {
		List<ApprovalPendingPayment> list = null;
		try {
			list = repositoryApprovalPendingPayment.findAll();
		} catch (Exception e) {
			System.out.println("Error while fetching complete list of Pending Payment.");
			e.printStackTrace();
			return ResponseEntity.badRequest().body(list);
		}
		System.out.println("Fetching complete list of Pending Payments was successfully.");
		return ResponseEntity.ok(list);
	}

}
