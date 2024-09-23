package com.abpoint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abpoint.extra.charges.service.ExtraChargesServices;
import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.ExtraChargesEntryForAllFlats;
import com.abpoint.model.ExtraChargesRequest;
import com.abpoint.service.SocietyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "https://d3c7-103-39-244-148.ngrok-free.app")
@Controller
public class ExtraChargesController {
	
	private static final Logger log = LoggerFactory.getLogger(ExtraChargesController.class);
	@Autowired
	SocietyService serviceSociety;

	@Autowired
	ExtraChargesServices extraChargesServices;

	@PostMapping("/saveExtraCharges")
	public ResponseEntity<ExtraChargesEntry> saveExtraCharges(@RequestBody ExtraChargesEntry ece) throws Exception {
		log.info("saveExtraCharges: " + ece);

		return extraChargesServices.saveUpdateExtraCharges(ece, 0.0);
	}

	@PostMapping("/saveExtraChargesForAll")
	public ResponseEntity<Map<String, String>> saveExtraChargesForAll(@RequestBody ExtraChargesEntryForAllFlats ece) throws Exception {
	    String msg = extraChargesServices.saveUpdateExtraChargesForAll(ece);

	    // Create a response body as a map
	    Map<String, String> responseBody = new HashMap<>();
	    responseBody.put("message", msg);

	    // Return the response body with status OK
	    return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	@PutMapping("/saveEditedChanges")
	public ResponseEntity<ExtraChargesEntry> saveEditedChanges(@RequestBody ExtraChargesRequest request)
			throws Exception {
		log.info("request: " +request);
		ExtraChargesEntry ece = request.getEce();
		Double oldCharges = request.getOldCharges(); // Handle oldCharges from the request body

		log.info("Old Charges: " + oldCharges);
		log.info("saveEditedChanges: " + ece);
		return extraChargesServices.saveUpdateExtraCharges(ece, oldCharges);
	}

	@DeleteMapping("/deleteExtraCharges")
	public ResponseEntity<String> deleteExtraCharges(@RequestParam Long chargeId,
			@RequestBody Map<String, String> reasonDetails) throws Exception {
		log.info("deleteExtraCharges: " + reasonDetails);
		ResponseEntity<String> response = extraChargesServices.deleteExtraCharges(chargeId, reasonDetails);
		log.info("response.getBody():  " + response.getBody());
		// Call the service to perform deletion
		return response;
	}

	@GetMapping("/getExtraCharges/{flatNumber}")
	public double getExtraCharges(@PathVariable int flatNumber) {

		double extra = extraChargesServices.getExtraCharges(flatNumber);

		log.info("Extra charges are:" + extra);
		return extra;
	}

	@GetMapping("/getAllExtraCharges")
	public ResponseEntity<List<ExtraChargesEntry>> getAllExtraCharges() {
		return extraChargesServices.getAllExtraCharges();
	}

	@GetMapping("/getExtraChargeEntry")
	public ResponseEntity<List<ExtraChargesEntry>> getExtraChargeEntry(@RequestParam int flatNumber) {
		return extraChargesServices.getExtraChargeEntry(flatNumber);
	}

}
