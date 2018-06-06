package com.bcits.bfm.service.commonAreaMaintenance;

import java.util.List;

import com.bcits.bfm.model.CamHeadsEntity;
import com.bcits.bfm.service.GenericService;

public interface CamHeadsService extends  GenericService<CamHeadsEntity> {
	
	List<CamHeadsEntity> findAllById(int ccId);

	List<CamHeadsEntity> getHeadData(int ccId);

	Double getHeadDataAmount(int ccId);
}
