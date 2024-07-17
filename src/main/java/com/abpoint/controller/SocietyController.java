package com.abpoint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.GridData;
import com.abpoint.model.MaintenanceDashboardEntry;
import com.abpoint.model.SocietyMaintenanceEntry;
import com.abpoint.model.SocietyMaintenancePaidHistory;
import com.abpoint.service.SocietyService;
import com.abpoint.service.SocietyUtilServices;

//@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class SocietyController {
//autowire the BooksService class
	@Autowired
	SocietyService serviceSociety;

	@Autowired
	SocietyUtilServices societyUtilServices;

	@GetMapping("/healthStatus")
	private String getHeathStatus() {
		return "Society App is up and running.";
	}

	@PostMapping("/smeSave")
	private ResponseEntity<SocietyMaintenanceEntry> saveSmeRecord(@RequestBody SocietyMaintenanceEntry sme)
			throws Exception {
		System.out.println("sme: " + sme);
		return serviceSociety.saveOrUpdate(sme);
	}

	@PostMapping("/saveExtraCharges")
	private ResponseEntity<ExtraChargesEntry> saveExtraCharges(@RequestBody ExtraChargesEntry ece) throws Exception {
		System.out.println("ece: " + ece);
		return serviceSociety.saveExtraCharges(ece);
	}
	@GetMapping("/index")
    public String index() {
        return "index"; // This should match the name of your HTML file in the templates folder
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
		System.out.println("SocietyController.getMaintenanceDashboardData()");
		MaintenanceDashboardEntry data = serviceSociety.getMaintenanceDashboardData(flatNumber);
		System.out.println("getMaintenanceDashboardData data: " + data);
		return ResponseEntity.ok(data);
	}

//creating put mapping that updates the book detail 
	@PutMapping("/smeUpdate")
	private ResponseEntity<SocietyMaintenanceEntry> update(@RequestBody SocietyMaintenanceEntry sme) throws Exception {
		return serviceSociety.saveOrUpdate(sme);
	}

	@GetMapping("/paidAmountInYear")
	public double getPaidAmount(@RequestParam int flatNumber, @RequestParam String year) {
		return serviceSociety.getPaidAmountForYear(flatNumber, year);
	}

	@GetMapping("/getExtraCharges/{flatNumber}")
	public double getExtraCharges(@PathVariable int flatNumber) {

		double extra = societyUtilServices.getExtraCharges(flatNumber);

		System.out.println("Extra charges are:" + extra);
		return extra;
	}

	@DeleteMapping("/deletePaymentHistory/{id}")
	public ResponseEntity<String> deletePaymentHistory(@PathVariable Long id,
			@RequestParam(required = false) String deleteReason) throws Exception {

		return serviceSociety.softDeletePaidHistory(id, deleteReason);
	}

	@GetMapping("/getSme/{flatId}")
	private ResponseEntity<SocietyMaintenanceEntry> getSmeRecord(@PathVariable("flatId") int flatId) {
		ResponseEntity<SocietyMaintenanceEntry> entity = null;
		try {
			entity = serviceSociety.getSmeByIdResponse(flatId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("entity: " + entity);
		return entity; // return booksService.getBooksById(flatId);
	}

	@PutMapping("/editPaymentHistory")
	public ResponseEntity<String> deletePaymentHistory(@RequestBody SocietyMaintenancePaidHistory paidHistory)
			throws Exception {

		return serviceSociety.editPaidHistory(paidHistory);
	}

	@GetMapping("/getExtraChargeEntry")
	public ResponseEntity<List<ExtraChargesEntry>> getExtraChargeEntry(@RequestParam int flatNumber) {
		return societyUtilServices.getExtraChargeEntry(flatNumber);
	}

	@GetMapping("/getOutstandingList")
	public ResponseEntity<List<GridData>> getGridData() {
		System.out.println("In getOutstandingList");
		return societyUtilServices.getGridDataList();
	}
}
