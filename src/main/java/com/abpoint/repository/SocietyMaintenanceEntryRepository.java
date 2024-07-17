package com.abpoint.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abpoint.model.SocietyMaintenanceEntry;
@Repository
public interface SocietyMaintenanceEntryRepository extends CrudRepository<SocietyMaintenanceEntry, Integer>
{
	
}
