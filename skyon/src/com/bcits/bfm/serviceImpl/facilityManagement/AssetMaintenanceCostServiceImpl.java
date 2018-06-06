package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AssetMaintenanceCost;
import com.bcits.bfm.model.AssetServiceHistory;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceCostService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetMaintenanceCostServiceImpl extends GenericServiceImpl<AssetMaintenanceCost> implements AssetMaintenanceCostService
{

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetMaintenanceCost> getAllFields(int assetId) {
		return entityManager.createNamedQuery("AssetMaintenanceCost.getAllField").setParameter("assetId", assetId).getResultList();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> setResponse(int assetId) {

		List<AssetMaintenanceCost> list =entityManager.createNamedQuery("AssetMaintenanceCost.findAll").setParameter("assetId", assetId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final AssetMaintenanceCost record :list) {
			response.add(new HashMap<String, Object>() {{
				put("amcId", record.getAmcId());
				//put("ashId", record.getAshId());
				java.util.Date amcDateUtil = record.getAmcDate();
				java.sql.Date amcDateSql = new java.sql.Date(amcDateUtil.getTime());
				put("amcDate", amcDateSql);
				
				put("costIncurred", record.getCostIncurred());
				put("mcType", record.getMcType());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getUpdatedBy());
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
		return response;
	}
	
	public AssetMaintenanceCost mantenanceCostSetter(AssetServiceHistory assetServiceHistory,HttpServletRequest req,String action){
		AssetMaintenanceCost assetMaintenanceCost = new AssetMaintenanceCost();
		if(StringUtils.equalsIgnoreCase(action, "update") && req.getParameter("amcId")!=null)
			assetMaintenanceCost.setAmcId(Integer.parseInt(req.getParameter("amcId")));
		assetMaintenanceCost.setAmcDate(assetServiceHistory.getAshDate());
		//assetMaintenanceCost.setAshId(assetServiceHistory.getAshId());
		assetMaintenanceCost.setCostIncurred(Integer.parseInt(req.getParameter("costIncurred")));
		assetMaintenanceCost.setMcType(assetServiceHistory.getServiceType());
		assetMaintenanceCost.setAssetServiceHistory(assetServiceHistory);
		return assetMaintenanceCost;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetMaintenanceCost> getAmListBasedOnAmId(int amcId) {
		return entityManager.createNamedQuery("AssetMaintenanceCost.getAmListBasedOnAmId").setParameter("amcId", amcId).getResultList();
	}

	@Override
	public void deleteAssetBasedOnAmcId(String parameter) {
		
		 entityManager.createNamedQuery("AssetMaintenanceCost.deleteAssetBasedOnAmcId").setParameter("amcId", parameter).executeUpdate();
		

	}

}
