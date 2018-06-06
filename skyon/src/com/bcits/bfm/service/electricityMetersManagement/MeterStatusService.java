package com.bcits.bfm.service.electricityMetersManagement;

import java.util.List;

import com.bcits.bfm.model.MeterStatusEntity;
import com.bcits.bfm.service.GenericService;

public interface MeterStatusService extends GenericService<MeterStatusEntity> {

	List<MeterStatusEntity> findAll();
}
