package com.abpoint.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abpoint.model.ActionedPaymentsDetails;
import com.abpoint.model.ApprovalPendingPayment;
import com.abpoint.model.SocietyMaintenanceEntry;
import com.abpoint.service.PaymentApprovalService;
import com.abpoint.service.SocietyService;
import com.abpoint.service.SocietyUtilServices;
import com.abpoint.service.reciept.GenerateReceiptService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "https://d3c7-103-39-244-148.ngrok-free.app")
@Controller
@Slf4j
public class ActionController {

	private static final Logger log = LoggerFactory.getLogger(ActionController.class);

	@Autowired
	SocietyService serviceSociety;

	@Autowired
	SocietyUtilServices societyUtilServices;
	
	@Autowired
	PaymentApprovalService approvalService;

	@Autowired
	GenerateReceiptService genrateRecieptService;

	@PostMapping("/savePaymentForApproval")
	private ResponseEntity<SocietyMaintenanceEntry> savePaymentForApproval(@RequestBody ApprovalPendingPayment pendingPayment) throws Exception {
		 log.info("In controller: savePaymentForApproval. Request body: "+pendingPayment);
		return approvalService.saveToPendingPayments(pendingPayment);
	}

	@GetMapping("/getUnapprovedPayments")
	private ResponseEntity<List<ApprovalPendingPayment>> getUnapprovedPayments(int flatNumber) throws Exception {
		log.info("In controller: getUnapprovedPayments");
		return approvalService.getUnapprovedPayments(flatNumber);
	}
	
	@GetMapping("/getAllUnapprovedPayments")
	private ResponseEntity<List<ApprovalPendingPayment>> getAllUnapprovedPayments() throws Exception {
		log.info("In controller: getUnapprovedPayments");
		return approvalService.getAllUnapprovedPayments();
	}
	
	@GetMapping("/getRejectedPayments/{flatNumber}")
	private ResponseEntity<List<ActionedPaymentsDetails>> getRejectedPayments(@PathVariable("flatNumber") int flatNumber)throws Exception {
		log.info("In controller: getRejectedPayments");
		return approvalService.getRejectedPayments(flatNumber);
	}
	
	@PostMapping("/approvePayment/{id}")
	private ResponseEntity<ApprovalPendingPayment> approvePayment(@PathVariable("id") Long id,@RequestBody Map<String, String> actionDetails)throws Exception {
		log.info("In controller: approvePayment");
		return serviceSociety.saveAsApproved(id,actionDetails);
	}
	
	@PostMapping("/rejectPayment/{id}")
	private ResponseEntity<ActionedPaymentsDetails> rejectPayment(@PathVariable("id") Long id,@RequestBody Map<String, String> rejectionDetails)throws Exception {
		log.info("In controller: rejectPayment");
		return ResponseEntity.ok(approvalService.actionPayment(id,rejectionDetails));
	}
	
	
	@GetMapping("/downloadPdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable("id") Long id){//,@RequestBody Map<String, String> downloadDetails)throws Exception {

        try {
        	log.info("In controller: downloadPdf");

            byte[] pdfBytes = genrateRecieptService.pdfGenerator();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set("Content-Disposition", "inline; filename=filled_Receipt_format.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
