package com.abpoint.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abpoint.model.FlatTypeFlatNumberMap;

@Repository
public interface FlatTypeFlatNumberMapRepository extends CrudRepository<FlatTypeFlatNumberMap, Integer> {

	/* String getFlatTypeByFlatNumber(int flatNumber); */

	@Query("SELECT f.annualMaintenance FROM FlatTypeFlatNumberMap f WHERE f.flatNumber = :flatNumber")
	Optional<Double> getAnnualMaintenanceByFlatNumber(@Param("flatNumber") int flatNumber);

	@Query("SELECT f.flatType FROM FlatTypeFlatNumberMap f WHERE f.flatNumber = :flatNumber")
	Optional<String> getFlatTypeByFlatNumber(@Param("flatNumber") int flatNumber);

}
