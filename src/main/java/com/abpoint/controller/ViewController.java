package com.abpoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "https://d3c7-103-39-244-148.ngrok-free.app")
@Controller
public class ViewController {

    @GetMapping("/maintenanceDashboard")
    public String maintenanceDashboard() {
        return "maintenanceDashboard";
    }

    @GetMapping("/maintenance-entry")
    public String maintenanceEntry() {
        return "maintenance-entry";
    }

    @GetMapping("/add-extra-charges")
    public String addExtraCharges() {
        return "add-extra-charges";
    }

    @GetMapping("/grid-view")
    public String gridView() {
        return "grid-view";
    }
    
    @GetMapping("/unapproved-payments")
    public String showUnapprovedPayments() {
        // Load and add necessary data to the model
        return "unapproved-payments"; // Thymeleaf template name
    }
    @GetMapping("/maintenance-charges")
    public String maintenanceCharges() {
        // Load and add necessary data to the model
        return "maintenance-charges"; // Thymeleaf template name
    }
}
