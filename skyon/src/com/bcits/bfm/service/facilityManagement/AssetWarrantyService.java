package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.AssetWarranty;
import com.bcits.bfm.service.GenericService;

public interface AssetWarrantyService extends GenericService<AssetWarranty> 
{
	public List<AssetWarranty> getAllFields(int assetId);
	public List<?> setResponse(int assetId);
	public void warrantyJCCron();
}

