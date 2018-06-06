package com.bcits.bfm.amr.service;

import java.util.List;

import com.bcits.bfm.model.AMRDataEntity;
import com.bcits.bfm.service.GenericService;

public interface AMREntityService extends GenericService<AMRDataEntity>{

	List<?> getAMRAccountEntityDetails();
	List<AMRDataEntity> getAMRAccountDetails(String strDate, String pesentDate);
	List<AMRDataEntity> getAccountDetailsOnPropertyId(int propid, String strDate, String pesentDate);

}
