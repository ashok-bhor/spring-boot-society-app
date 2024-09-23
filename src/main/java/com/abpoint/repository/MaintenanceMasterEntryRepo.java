package com.abpoint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abpoint.model.GridData;
import com.abpoint.model.MaintenanceMasterEntry;
@Repository
public interface MaintenanceMasterEntryRepo extends JpaRepository<MaintenanceMasterEntry, Integer> {

	@Query("select sme.receivedTillNow from MaintenanceMasterEntry sme WHERE sme.flatNumber = :flatNumber")
	Optional<Double> getReceivedTillNowByFlatNumber(@Param("flatNumber") int flatNumber);
	
	@Modifying
	@Query("UPDATE MaintenanceMasterEntry sme SET sme.receivedTillNow = :receivedTillNow WHERE sme.flatNumber = :flatNumber")
	void updateReceivedTillNow(@Param("flatNumber") int flatNumber, @Param("receivedTillNow") double receivedTillNow);
	
	// Method to fetch flatNumber and outstandingAmount for all entries
    @Query("SELECT NEW com.abpoint.model.GridData(m.flatNumber, (m.totalOutstanding +m.chargedAmount)) FROM MaintenanceMasterEntry m")
    List<GridData> fetchGridData();
    
    @Query("select sme.totalOutstanding from MaintenanceMasterEntry sme WHERE sme.flatNumber = :flatNumber")
	Optional<Double> getTotalOutstandingByFlatNumber(@Param("flatNumber") int flatNumber);
}
