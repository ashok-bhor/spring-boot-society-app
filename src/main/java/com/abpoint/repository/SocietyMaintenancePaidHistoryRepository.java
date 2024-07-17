package com.abpoint.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abpoint.model.SocietyMaintenancePaidHistory;

public interface SocietyMaintenancePaidHistoryRepository extends JpaRepository<SocietyMaintenancePaidHistory, Long> {

	@Query("SELECT SUM(s.amount) FROM SocietyMaintenancePaidHistory s WHERE s.flatNumber = :flatNumber AND s.year = :year AND s.deleted = false")
	Double getPaidAmountForYear(@Param("flatNumber") int flatNumber, @Param("year") String year);

	
	@Query("SELECT SUM(s.amount) FROM SocietyMaintenancePaidHistory s WHERE s.flatNumber = :flatNumber AND s.deleted = false")
	Double getPaidAmountTillNow(@Param("flatNumber") int flatNumber);
	
	List<SocietyMaintenancePaidHistory> findByFlatNumberAndDeletedFalse(int flatNumber);
	
	SocietyMaintenancePaidHistory findByIdAndDeletedFalse(Long id);
	
	

	@Query("SELECT MAX(s.date) FROM SocietyMaintenancePaidHistory s WHERE s.flatNumber = :flatNumber AND s.deleted = false")
	Date findLatestReceivedDate(@Param("flatNumber") int flatNumber);

}
