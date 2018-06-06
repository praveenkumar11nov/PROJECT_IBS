package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.AssetCategoryTree;
import com.bcits.bfm.service.GenericService;

public interface AssetCategoryTreeService extends GenericService<AssetCategoryTree> {

	public List<AssetCategoryTree> findAllOnParentId(Integer assetcatId);
	 
	public List<AssetCategoryTree> findAllOnAssetCatId(int assetCatId);

	public List<AssetCategoryTree> findIdByParent(int parentId, String assetcatText);
}
