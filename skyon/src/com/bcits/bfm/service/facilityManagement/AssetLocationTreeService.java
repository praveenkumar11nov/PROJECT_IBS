package com.bcits.bfm.service.facilityManagement;

import java.util.List;



import com.bcits.bfm.model.AssetLocationTree;
import com.bcits.bfm.service.GenericService;

public interface AssetLocationTreeService extends GenericService<AssetLocationTree> {
	public List<AssetLocationTree> findAllOnParentId(Integer assetcatId);

	public List<AssetLocationTree> findAllOnAssetLocId(int assetLocId);

	public List<AssetLocationTree> findIdByParent(Integer assetlocId,
			String nodename);

	public List<String> findAllLocationType();
}
