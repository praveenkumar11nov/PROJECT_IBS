package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.AccessPoints;
import com.bcits.bfm.model.AccessRepository;
import com.bcits.bfm.service.GenericService;

public interface AccessPointsService extends GenericService<AccessPoints> 
{

	public List<AccessPoints> findAll();
	
	public List<String> findAllApcode();
	
	public int getAccesspointIdBasedOnName(String apName);
	
	public List<AccessPoints> getPointName(int apId);
	
}

