package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.service.GenericService;

public interface AssetOwnerShipService extends GenericService<AssetOwnerShip> 
{

	List<Object[]> getByAllField();

	List<Map<String, Object>> setResponse();
	
	List<Object[]> findAllOwnership();
}

