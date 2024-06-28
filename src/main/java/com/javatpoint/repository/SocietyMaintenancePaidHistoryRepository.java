package com.javatpoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javatpoint.model.SocietyMaintenancePaidHistory;

public interface SocietyMaintenancePaidHistoryRepository extends JpaRepository<SocietyMaintenancePaidHistory, Long> {

	@Query("SELECT SUM(s.amount) FROM SocietyMaintenancePaidHistory s WHERE s.flatNumber = :flatNumber AND s.year = :year")
    Double getPaidAmountForYear(@Param("flatNumber") int flatNumber, @Param("year") String year);
	
	List<SocietyMaintenancePaidHistory> findByFlatNumber(int flatNumber);
	
}
