package com.bcits.bfm.service.serviceMasterManagement;

import java.util.List;

import com.bcits.bfm.model.MeterHistoryEntity;
import com.bcits.bfm.service.GenericService;

public interface MeterHistoryService extends GenericService<MeterHistoryEntity> {
	
	List<MeterHistoryEntity> findAllById(int serviceMasterId);
}
