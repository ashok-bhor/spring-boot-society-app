package com.abpoint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.abpoint.model.GridData;
import com.abpoint.model.MaintenanceDashboardCard;
import com.abpoint.model.MaintenanceDashboardEntry;
import com.abpoint.model.SocietyMaintenanceEntry;
import com.abpoint.model.SocietyMaintenancePaidHistory;
import com.abpoint.service.SocietyService;
import com.abpoint.service.SocietyUtilServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "https://d3c7-103-39-244-148.ngrok-free.app")
@Controller
public class SocietyController {

	private static final Logger log = LoggerFactory.getLogger(SocietyController.class);

	 @Autowired
	    private Environment environment;
	 
	@Autowired
	SocietyService serviceSociety;

	@Autowired
	SocietyUtilServices societyUtilServices;

	@GetMapping("/healthStatus")
	private String getHeathStatus() {
		return "Society App is up and running.";
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "Access denied.");
		}

		return "login";
	}

	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
		// Fetch the role if the user is authenticated
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication != null && authentication.isAuthenticated()
						&& authentication.getPrincipal() instanceof User) {
					User user = (User) authentication.getPrincipal();
					String role = user.getAuthorities().iterator().next().getAuthority();
					String splitted = role.split("_")[1];
					log.info("splitted::: "+splitted);

					session.setAttribute("sessionRole", splitted);
					
				}
				
				String[] activeProfiles = environment.getActiveProfiles();
		        String activeProfile = (activeProfiles.length > 0) ? activeProfiles[0] : "default";
		        session.setAttribute("activeProfile", activeProfile);
				
	    return "index"; // The name of your Thymeleaf template for the homepage
	}


	@GetMapping("/getPaidAmountForYear")
	public ResponseEntity<Map<String, Double>> getPaidAmountForYear(@RequestParam int flatNumber,
			@RequestParam String year) {
		double amount = serviceSociety.getPaidAmountForYear(flatNumber, year);
		Map<String, Double> response = new HashMap<>();
		response.put("amount", amount);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getPaidHistory/{flatNumber}")
	public ResponseEntity<List<SocietyMaintenancePaidHistory>> getPaidHistory(@PathVariable int flatNumber) {

		return ResponseEntity.ok(serviceSociety.getPaidHistoryByFlatNumber(flatNumber));
	}

	@GetMapping("/getMaintenanceDashboardData/{flatNumber}")
	public ResponseEntity<MaintenanceDashboardEntry> getMaintenanceDashboardData(@PathVariable int flatNumber)
			throws Exception {
		log.info("SocietyController.getMaintenanceDashboardData()");
		MaintenanceDashboardEntry data = serviceSociety.getMaintenanceDashboardData(flatNumber);
		log.info("getMaintenanceDashboardData data: " + data);
		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/getMaintenanceDashboardCard/{flatNumber}")
	public ResponseEntity<List<MaintenanceDashboardCard>> getMaintenanceDashboardCard(@PathVariable int flatNumber)
			throws Exception {
		log.info("SocietyController.getMaintenanceDashboardCard()");
		List<MaintenanceDashboardCard> data = serviceSociety.getMaintenanceDashboardCardData(flatNumber);
		log.info("getMaintenanceDashboardCard data: " + data);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/getFlatType/{flatNumber}")
	public ResponseEntity<String> getFlatType(@PathVariable int flatNumber) throws Exception {

		ResponseEntity<String> flatType = societyUtilServices.getFlatType(flatNumber);

		log.info("Flat type is:" + flatType);
		return flatType;
	}
	

	@DeleteMapping("/deletePaymentHistory/{id}")
	public ResponseEntity<String> deletePaymentHistory(@PathVariable Long id,
			@RequestParam(required = false) String deleteReason) throws Exception {

		return serviceSociety.softDeletePaidHistory(id, deleteReason);
	}

	@GetMapping("/getEntry/{flatId}")
	private ResponseEntity<SocietyMaintenanceEntry> getMaintenanceEntry(@PathVariable("flatId") int flatId) {
		SocietyMaintenanceEntry entity = null;
		try {
			entity = serviceSociety.getSmeByIdResponse(flatId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("entity: " + entity);
		return ResponseEntity.ok(entity);
	}

	@PutMapping("/editPaymentHistory")
	public ResponseEntity<String> editPaymentHistory(@RequestBody SocietyMaintenancePaidHistory paidHistory)
			throws Exception {

		return serviceSociety.editPaidHistory(paidHistory);
	}

	@GetMapping("/getOutstandingList")
	public ResponseEntity<List<GridData>> getGridData() {
		log.info("In getOutstandingList");
		return societyUtilServices.getGridDataList();
	}
}
