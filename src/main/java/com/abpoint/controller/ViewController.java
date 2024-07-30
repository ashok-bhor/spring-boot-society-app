package com.abpoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
