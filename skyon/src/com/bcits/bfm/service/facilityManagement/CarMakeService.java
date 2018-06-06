package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Set;

import com.bcits.bfm.model.CarMake;
import com.bcits.bfm.service.GenericService;

public interface CarMakeService extends GenericService<CarMake> {
	
	public List<CarMake> findAll();

	public CarMake findbyName(String string);
}