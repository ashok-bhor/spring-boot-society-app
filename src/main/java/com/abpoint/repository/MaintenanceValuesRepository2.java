package com.abpoint.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.abpoint.config.DatabaseConfig;

@Repository
public class MaintenanceValuesRepository2 {

	 @Autowired
	    private DatabaseConfig databaseConfig;

	    @Autowired
	    private NamedParameterJdbcTemplate jdbcTemplate;

	    public Map<String, Object> findYearAndExtraAmount(double receivedTillNow, String flatType) {
	        String tableName = databaseConfig.getSchema() + "." + databaseConfig.getMaintenanceTable();

	        String sql = "SELECT financial_year, cumulative_sum, (:receivedTillNow - cumulative_sum) AS extra_amount " +
	                     "FROM ( " +
	                     "  SELECT financial_year, SUM(maintenance_value) OVER (ORDER BY financial_year) AS cumulative_sum " +
	                     "  FROM " + tableName + " " +
	                     "  WHERE flat_type = :flatType " +
	                     ") AS subquery " +
	                     "WHERE cumulative_sum <= :receivedTillNow " +
	                     "ORDER BY cumulative_sum DESC " +
	                     "LIMIT 1";
	        
	        

	        MapSqlParameterSource params = new MapSqlParameterSource()
	            .addValue("receivedTillNow", receivedTillNow)
	            .addValue("flatType", flatType);

	        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, params);

	        // Handle case where no results are returned
	        if (results.isEmpty()) {
	            return null; // or handle this case as needed
	        }

	        // Assuming you expect exactly one result, return the first item
	        return results.get(0);
	    }
    
}
