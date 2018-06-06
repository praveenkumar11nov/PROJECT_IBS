package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.AssetServiceHistory;
import com.bcits.bfm.service.GenericService;

public interface AssetServiceHistoryService extends GenericService<AssetServiceHistory> 
{

	List<Map<String, Object>> setResponse();
	
	List<Object[]> findAll();

}

