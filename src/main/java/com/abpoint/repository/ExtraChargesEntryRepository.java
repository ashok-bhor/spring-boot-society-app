package com.abpoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abpoint.model.ExtraChargesEntry;

@Repository
public interface ExtraChargesEntryRepository extends JpaRepository<ExtraChargesEntry, Long> {
	// Method to sum chargedAmount by flatNumber
	@Query("SELECT COALESCE(SUM(e.chargedAmount), 0) FROM ExtraChargesEntry e WHERE e.flatNumber = :flatNumber")
	double sumChargedAmountByFlatNumber(int flatNumber);
	
	List<ExtraChargesEntry> findByFlatNumber(int flatNumber);
	
	 // Method to get distinct flat numbers from Flat table
    @Query("SELECT DISTINCT f.flatNumber FROM FlatTypeFlatNumberMap f")
    List<Integer> findDistinctFlatNumbersFromFlat();
}
