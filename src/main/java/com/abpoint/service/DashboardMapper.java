package com.abpoint.service;

import java.util.ArrayList;
import java.util.List;

import com.abpoint.model.MaintenanceDashboardCard;
import com.abpoint.model.MaintenanceDashboardEntry;

public class DashboardMapper {

    // Method to convert MaintenanceDashboardEntry to a list of MaintenanceDashboardCard
    public static List<MaintenanceDashboardCard> convertToDashboardCards(MaintenanceDashboardEntry entry) {
        List<MaintenanceDashboardCard> cards = new ArrayList<>();

        // Define card colors
        final String FLAT_TYPE_COLOR = "#95a5a6";
        final String TOTAL_PAID_COLOR = "#9b59b6";
        final String PAID_TILL_YEAR_COLOR = "#16a085";
        final String TOTAL_OUTSTANDING_COLOR = "#2ecc71";
        final String EXTRA_CHARGES_COLOR = "#3498db";
        final String MAINTENANCE_OUTSTANDING_COLOR = "#e74c3c";

        // Create card for Flat Type & Annual Maintenance
        cards.add(createCard("Flat Type : Annual Maintenance",
                entry.getFlatType() + " : ₹ " + entry.getAnnualMaintenance(),
                FLAT_TYPE_COLOR, false));

        // Create card for Total Paid Till Now
        cards.add(createCard("Total Paid Till Now",
                "₹ " + entry.getTotalPaid(),
                TOTAL_PAID_COLOR, false));

        // Create card for Paid Till Year
        cards.add(createCard("Paid Till Year",
                entry.getPaidTillYear(),
                PAID_TILL_YEAR_COLOR, false));

        // Create card for Total Outstanding
        cards.add(createCard("Total Outstanding",
                "₹ " + entry.getTotalOutstanding(),
                TOTAL_OUTSTANDING_COLOR, false));

        // Create card for Extra Charges
        cards.add(createCard("Extra Charges",
                "₹ " + entry.getExtraCharges(),
                EXTRA_CHARGES_COLOR, true)); // Assuming extraChargesLink is true for this card

        // Create card for Maintenance Outstanding
        cards.add(createCard("Maintenance Outstanding",
                "₹ " + entry.getMaintenanceOutstanding(),
                MAINTENANCE_OUTSTANDING_COLOR, false));

        return cards;
    }

    // Helper method to create a MaintenanceDashboardCard
    private static MaintenanceDashboardCard createCard(String header, String value, String color, boolean extraChargesLink) {
        MaintenanceDashboardCard card = new MaintenanceDashboardCard();
        card.setHeader(header);
        card.setValue(value);
        card.setCardColor(color);
        card.setExtraChargesLink(extraChargesLink);
        return card;
    }
}
