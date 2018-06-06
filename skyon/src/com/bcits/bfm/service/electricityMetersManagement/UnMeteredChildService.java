package com.bcits.bfm.service.electricityMetersManagement;

import java.util.List;

import com.bcits.bfm.model.OTConsumptions;
import com.bcits.bfm.service.GenericService;

public interface UnMeteredChildService extends GenericService<OTConsumptions>{

	List<OTConsumptions> findAll(int id);
	
	List<OTConsumptions> getChildId(int id);

	Double getEntityByAccountId(int accountId,String typeOfService);

	int getMaxConsuptionId(int installId);
	
}
