package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AssetMaintenance;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetMaintenanceServiceImpl extends GenericServiceImpl<AssetMaintenance> implements AssetMaintenanceService
{
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetMaintenance> getAllFields(int assetId) {
		return entityManager.createNamedQuery("AssetMaintenance.getAllField").setParameter("assetId", assetId).getResultList();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> setResponse(int assetId) {

		List<AssetMaintenance> list =entityManager.createNamedQuery("AssetMaintenance.findAll").setParameter("assetId", assetId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final AssetMaintenance record :list) {
			response.add(new HashMap<String, Object>() {{
				put("ampId", record.getAmpId());
				put("assetId", record.getAssetId());
				put("assetName", record.getAsset().getAssetName());
				if(record.getLastMaintained()!=null){
					java.util.Date lastMaintainedUtil = record.getLastMaintained();
					java.sql.Date lastMaintainedSql = new java.sql.Date(lastMaintainedUtil.getTime());
					put("lastMaintained", lastMaintainedSql);
				}else{
					put("lastMaintained", "");
				}
				put("maintainenceType", record.getMaintainenceType());
				put("maintenanceDescription", record.getMaintenanceDescription());
				put("periodicity", record.getPeriodicity());
				put("createdBy", record.getCreatedBy());
				put("updatedBy", record.getUpdatedBy());
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
		return response;
	}

}
