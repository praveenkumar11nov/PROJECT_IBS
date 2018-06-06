package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.AssetMaintenance;
import com.bcits.bfm.service.GenericService;

public interface AssetMaintenanceService extends GenericService<AssetMaintenance> 
{

	public List<AssetMaintenance> getAllFields(int assetId);

	public List<?> setResponse(int assetId);
}

