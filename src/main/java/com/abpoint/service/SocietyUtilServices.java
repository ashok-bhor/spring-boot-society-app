package com.abpoint.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abpoint.model.ExtraChargesEntry;
import com.abpoint.model.GridData;
import com.abpoint.repository.ExtraChargesEntryRepository;
import com.abpoint.repository.MaintenanceMasterEntryRepo;

//defining the business logic
@Service
public class SocietyUtilServices {

	@Autowired
	ExtraChargesEntryRepository extraChargesEntryRepository;

	@Autowired
	MaintenanceMasterEntryRepo maintenanceMasterEntryRepo;

	@Autowired
	SocietyService serviceSociety;
	
	public double getExtraCharges(int flatNumber) {

		Optional<Double> extraCharges = Optional
				.ofNullable(extraChargesEntryRepository.sumChargedAmountByFlatNumber(flatNumber));

		return extraCharges.orElse(0.0);
	}

	public ResponseEntity<List<ExtraChargesEntry>> getExtraChargeEntry(int flatNumber) {
		Optional<List<ExtraChargesEntry>> extraCharges = Optional
				.ofNullable(extraChargesEntryRepository.findByFlatNumber(flatNumber));

		return ResponseEntity.ok().body(extraCharges.orElse(null));
	}

	public ResponseEntity<List<GridData>> getGridDataList() {
		return ResponseEntity.ok().body(maintenanceMasterEntryRepo.fetchGridData());
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