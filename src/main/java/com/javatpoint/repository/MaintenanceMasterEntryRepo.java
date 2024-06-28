package com.javatpoint.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//repository that extends CrudRepository
import com.javatpoint.model.Books;
import com.javatpoint.model.MaintenanceMasterEntry;
import com.javatpoint.model.SocietyMaintenanceEntry;
@Repository
public interface MaintenanceMasterEntryRepo extends CrudRepository<MaintenanceMasterEntry, Integer> {

	@Query("select sme.receivedTillNow from MaintenanceMasterEntry sme WHERE sme.flatNumber = :flatNumber")
	Optional<Double> getReceivedTillNowByFlatNumber(@Param("flatNumber") int flatNumber);
	
	@Modifying
	@Query("UPDATE MaintenanceMasterEntry sme SET sme.receivedTillNow = :receivedTillNow WHERE sme.flatNumber = :flatNumber")
	void updateReceivedTillNow(@Param("flatNumber") int flatNumber, @Param("receivedTillNow") double receivedTillNow);
}
