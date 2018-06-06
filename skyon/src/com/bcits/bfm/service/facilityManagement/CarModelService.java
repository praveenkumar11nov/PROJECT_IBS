package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.CarModel;
import com.bcits.bfm.service.GenericService;

public interface CarModelService extends GenericService<CarModel> {
	
	public List<CarModel> findAll();
}