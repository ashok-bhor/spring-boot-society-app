package com.abpoint.service;

import java.util.HashMap;
import java.util.Map;

public class DashboardConstants {

    // Header Constants
    public static final String FLAT_TYPE_AND_ANNUAL_MAINTENANCE_HEADER = "Flat Type - Annual Maintenance";
    public static final String TOTAL_PAID_TILL_NOW_HEADER = "Total Paid Till Now";
    public static final String PAID_TILL_YEAR_HEADER = "Paid Till Year";
    public static final String TOTAL_OUTSTANDING_HEADER = "Total Outstanding";
    public static final String EXTRA_CHARGES_HEADER = "Extra Charges";
    public static final String MAINTENANCE_OUTSTANDING_HEADER = "Maintenance Outstanding";

    // Card Color Constants
    public static final String FLAT_TYPE_AND_ANNUAL_MAINTENANCE_COLOR = "#95a5a6";
    public static final String TOTAL_PAID_TILL_NOW_COLOR = "#9b59b6";
    public static final String PAID_TILL_YEAR_COLOR = "#16a085";
    public static final String TOTAL_OUTSTANDING_COLOR = "#2ecc71";
    public static final String EXTRA_CHARGES_COLOR = "#3498db";
    public static final String MAINTENANCE_OUTSTANDING_COLOR = "#e74c3c";

    // Map for header and color pairs (Optional)
    public static final Map<String, String> HEADER_TO_COLOR_MAP = new HashMap<>();

    static {
        HEADER_TO_COLOR_MAP.put(FLAT_TYPE_AND_ANNUAL_MAINTENANCE_HEADER, FLAT_TYPE_AND_ANNUAL_MAINTENANCE_COLOR);
        HEADER_TO_COLOR_MAP.put(TOTAL_PAID_TILL_NOW_HEADER, TOTAL_PAID_TILL_NOW_COLOR);
        HEADER_TO_COLOR_MAP.put(PAID_TILL_YEAR_HEADER, PAID_TILL_YEAR_COLOR);
        HEADER_TO_COLOR_MAP.put(TOTAL_OUTSTANDING_HEADER, TOTAL_OUTSTANDING_COLOR);
        HEADER_TO_COLOR_MAP.put(EXTRA_CHARGES_HEADER, EXTRA_CHARGES_COLOR);
        HEADER_TO_COLOR_MAP.put(MAINTENANCE_OUTSTANDING_HEADER, MAINTENANCE_OUTSTANDING_COLOR);
    }
}