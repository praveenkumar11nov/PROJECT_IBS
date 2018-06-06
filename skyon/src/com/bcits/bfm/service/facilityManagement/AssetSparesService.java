package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.AssetSpares;
import com.bcits.bfm.service.GenericService;

public interface AssetSparesService extends GenericService<AssetSpares> 
{

	List<?> setResponse(int assetId);

	List<AssetSpares> getAssetSparesBasedOnId(int asId);
}

