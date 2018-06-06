package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AssetSpares;
import com.bcits.bfm.service.facilityManagement.AssetSparesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetSparesServiceImpl extends GenericServiceImpl<AssetSpares> implements AssetSparesService
{

	@SuppressWarnings("serial")
	@Override
	public List<?> setResponse(int assetId) {
		@SuppressWarnings("unchecked")
		List<AssetSpares> list =entityManager.createNamedQuery("AssetSpares.findAll").setParameter("assetId", assetId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final AssetSpares record : list) {
			response.add(new HashMap<String, Object>() {{
				put("asId", record.getAsId());
				put("imId", record.getItemMaster().getImId());
				put("imName", record.getItemMaster().getImName());
				put("commonGrouping", record.getItemMaster().getImGroup());
				//put("partName", record.getPartName());
				put("partDescription", record.getItemMaster().getImDescription());
				put("partMake", record.getPartMake());
				put("partModelNumber", record.getPartModelNumber());
				
				java.util.Date partMakeUtil = record.getPartYear();
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				java.sql.Date partMakeSql = new java.sql.Date(partMakeUtil.getTime());
				
				put("partYear", partMakeSql);
				put("partSlNumber", record.getPartSlNumber());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSpares> getAssetSparesBasedOnId(int asId) {
		return entityManager.createNamedQuery("AssetSpares.getAssetSparesBasedOnId").setParameter("asId", asId).getResultList();
	}

}
