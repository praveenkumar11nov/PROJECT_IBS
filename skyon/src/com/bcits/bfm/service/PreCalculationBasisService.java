package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.PrepaidCalculationBasisEntity;

public interface PreCalculationBasisService extends GenericService<PrepaidCalculationBasisEntity>{

	List<PrepaidCalculationBasisEntity> readData(int serviceId);
	List<?> getData(int serviceID);
}
