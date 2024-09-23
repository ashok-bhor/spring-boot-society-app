package com.abpoint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Value("${db.schema}")
    private String schema;

    @Value("${db.table.society_maintenance_values_year_wise}")
    private String maintenanceTable;

    public String getSchema() {
        return schema;
    }

    public String getMaintenanceTable() {
        return maintenanceTable;
    }
}

