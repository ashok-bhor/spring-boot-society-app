package com.abpoint.model.reciept;

import java.sql.Date;
import java.util.List;

public class ChargeSchema {
	
	private List<ChargeDetail> listChargeDetails;
     private boolean schemaStatus;
     private Date createdOn;
     private Date activeFrom;
     private Date activeTill;
}

