package com.abpoint.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.GridData;
import com.abpoint.repository.ExtraChargesEntryRepository;
import com.abpoint.repository.MaintenanceMasterEntryRepo;
import com.abpoint.service.reciept.GenerateReceiptService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
//defining the business logic
@Service
public class SocietyUtilServices {

	private static final Logger logger = LoggerFactory.getLogger(SocietyUtilServices.class);

	@Autowired
	ExtraChargesEntryRepository extraChargesEntryRepository;

	@Autowired
	MaintenanceMasterEntryRepo maintenanceMasterEntryRepo;

	@Autowired
	SocietyService serviceSociety;
	
	
	public ResponseEntity<List<GridData>> getGridDataList() {
		List<GridData> lstData = maintenanceMasterEntryRepo.fetchGridData();
		logger.info("lstData: "+lstData);
		return ResponseEntity.ok().body(lstData);
	}

	public String getFinancialYearOfDate(Date date) throws Exception {
		
		int startYear=0, endYear=0;
		try {
			if (date == null) {
				throw new IllegalArgumentException("Date cannot be null.");
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);

			

			// Financial year starts from April 1st
			if (month >= Calendar.APRIL) {
				startYear = year;
				endYear = year + 1;
			} else {
				startYear = year - 1;
				endYear = year;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting financial year for given date");
		}
		return startYear + "-" + String.valueOf(endYear).substring(2);
	}

	public ResponseEntity<String> getFlatType(int flatNumber) throws Exception {
		Optional<String> flatType = Optional
				.ofNullable(serviceSociety.getFlatType(flatNumber));

		return ResponseEntity.ok().body(flatType.get());
	}

}