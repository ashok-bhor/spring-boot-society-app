package com.javatpoint.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javatpoint.model.SocietyMaintenanceEntry;
import com.javatpoint.model.SocietyMaintenancePaidHistory;
@Repository
public interface SocietyMaintenanceEntryRepository extends CrudRepository<SocietyMaintenanceEntry, Integer>
{
	
}
