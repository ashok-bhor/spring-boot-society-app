package com.javatpoint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.model.MaintenanceDashboardEntry;
import com.javatpoint.model.SocietyMaintenanceEntry;
import com.javatpoint.model.SocietyMaintenancePaidHistory;
import com.javatpoint.service.SocietyService;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class SocietyController {
//autowire the BooksService class
	@Autowired
	SocietyService serviceSociety;

//creating a get mapping that retrieves all the books detail from the database 
	@GetMapping("/getAllEntries")
	private List<SocietyMaintenanceEntry> getAllBooks() {
		return serviceSociety.getAllSocietyMaintenanceEntries();
	}

//creating a get mapping that retrieves the detail of a specific book
	@GetMapping("/getSme/{flatId}")
	private ResponseEntity<SocietyMaintenanceEntry> getSmeRecord(@PathVariable("flatId") int flatId) {
		ResponseEntity<SocietyMaintenanceEntry> entity =null;
		try {
			entity = serviceSociety.getSmeByIdResponse(flatId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("entity: "+entity);
		return entity; // return booksService.getBooksById(flatId);
	}


	@PostMapping("/smeSave")
	private ResponseEntity<SocietyMaintenanceEntry> saveEmeRecord(@RequestBody SocietyMaintenanceEntry sme) throws Exception {
		System.out.println("sme: "+sme);
		return serviceSociety.saveOrUpdate(sme);
	}
	
	@GetMapping("/getPaidAmountForYear")
    public ResponseEntity<Map<String, Double>> getPaidAmountForYear(
            @RequestParam int flatNumber, @RequestParam String year) {
        double amount = serviceSociety.getPaidAmountForYear(flatNumber, year);
        Map<String, Double> response = new HashMap<>();
        response.put("amount", amount);
        return ResponseEntity.ok(response);
    }
	
	
	@GetMapping("/getPaidHistory/{flatNumber}")
    public ResponseEntity<List<SocietyMaintenancePaidHistory>> getPaidHistory(
            @PathVariable int flatNumber) {
		
		return ResponseEntity.ok(serviceSociety.getPaidHistoryByFlatNumber(flatNumber));
    }
	
	@GetMapping("/getMaintenanceDashboardData/{flatNumber}")
    public ResponseEntity<MaintenanceDashboardEntry> getMaintenanceDashboardData(@PathVariable int flatNumber) throws Exception {
        System.out.println("SocietyController.getMaintenanceDashboardData()");
		MaintenanceDashboardEntry data = serviceSociety.getMaintenanceDashboardData(flatNumber);
		System.out.println("getMaintenanceDashboardData data: "+data);
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
	
}
