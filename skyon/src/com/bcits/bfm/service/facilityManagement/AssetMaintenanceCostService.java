package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.AssetMaintenanceCost;
import com.bcits.bfm.model.AssetServiceHistory;
import com.bcits.bfm.service.GenericService;

public interface AssetMaintenanceCostService extends GenericService<AssetMaintenanceCost> 
{

	public List<AssetMaintenanceCost> getAllFields(int assetId);

	public List<?> setResponse(int assetId);

	public AssetMaintenanceCost mantenanceCostSetter(AssetServiceHistory assetServiceHistory, HttpServletRequest req,String string);

	public List<AssetMaintenanceCost> getAmListBasedOnAmId(int amcId);

	public void deleteAssetBasedOnAmcId(String parameter);
}

