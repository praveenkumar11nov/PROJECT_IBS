package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.service.GenericService;

public interface AssetService extends GenericService<Asset> 
{
	public List<Asset> findAll();

	public List<Object[]> getAllAssetOnCatId(int assetCatId);

	public List<Map<String, Object>> setResponse();
	
	public List<Object[]> findAllAsset();

	public List<Object[]> getAllAssetOnLocId(int assetLocId);

	public List<Object[]> getAllAssetForAll();

	public List<Asset> getAssetsonCatIdAndLocId(int assetCatId, int assetLocId);

	public void setAssetStatus(int asset, String operation,
			HttpServletResponse response);
	
	public void uploadAssetOnId(int assetId, Blob assetDocument, String assetDocumentType);

	public List<Asset> getDataForViewReport();
}

