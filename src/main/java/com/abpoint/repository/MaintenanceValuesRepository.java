package com.abpoint.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abpoint.model.SocietyMaintenanceValuesYearWise;

public interface MaintenanceValuesRepository extends JpaRepository<SocietyMaintenanceValuesYearWise, Long> {

	@Query("SELECT SUM(m.maintenanceValue) FROM SocietyMaintenanceValuesYearWise m WHERE m.financialYear <= :financialYear AND m.flatType = :flatType")
	Double sumMaintenanceValueByYearAndFlatType(@Param("financialYear") String financialYear,
			@Param("flatType") String flatType);

	@Query("SELECT m.financialYear FROM SocietyMaintenanceValuesYearWise m WHERE m.recordNum = (SELECT MAX(mv.recordNum) FROM SocietyMaintenanceValuesYearWise mv)")
	String findLatestYear();

	@Query("SELECT m.financialYear FROM SocietyMaintenanceValuesYearWise m WHERE m.recordNum = (SELECT MIN(mv.recordNum) FROM SocietyMaintenanceValuesYearWise mv)")
	String findFirstYear();

	@Query("SELECT m.financialYear FROM SocietyMaintenanceValuesYearWise m where m.recordNum = :recordNum")
	String getYearByRecordNum(int recordNum);

//	String findYearForReceivedTillNow(@Param("receivedTillNow") double receivedTillNow, @Param("flatType") String flatType);

	@Query(value = "SELECT financialYear, cumulativeSum, (?1 - cumulativeSum) AS extraAmount " + "FROM ( "
			+ "  SELECT m.financialYear, SUM(m.maintenanceValue) OVER (ORDER BY m.financialYear) AS cumulativeSum "
			+ "  FROM society_maintenance_values_year_wise m " + "  WHERE m.flatType = ?2 " + ") AS subquery "
			+ "WHERE cumulativeSum <= ?1 " + "ORDER BY cumulativeSum DESC " + "LIMIT 1", nativeQuery = true)
	Map<String, Object> findYearAndExtraAmount(double receivedTillNow, String flatType);
}
