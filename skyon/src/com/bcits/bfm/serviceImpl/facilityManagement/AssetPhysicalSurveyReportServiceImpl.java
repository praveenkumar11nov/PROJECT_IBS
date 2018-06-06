package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetPhysicalSurveyReport;
import com.bcits.bfm.service.facilityManagement.AssetPhysicalSurveyReportService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetPhysicalSurveyReportServiceImpl extends GenericServiceImpl<AssetPhysicalSurveyReport> implements AssetPhysicalSurveyReportService
{

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse(int apsmId) {
		List<AssetPhysicalSurveyReport> physicalSurveyReportList =  entityManager.createNamedQuery("AssetPhysicalSurveyReport.findAll").setParameter("apsmId", apsmId).getResultList();		
		List<Map<String, Object>> selectedFieldResponse = new ArrayList<Map<String, Object>>();    
		for (final AssetPhysicalSurveyReport record: physicalSurveyReportList) {
			selectedFieldResponse.add(new HashMap<String, Object>() {{
				put("apId", record.getApId());
				put("apsmId", record.getApsmId());
				put("assetId", record.getAssetId());
				put("assetName", record.getAsset().getAssetName());

				put("assetCategoryTree", record.getAsset().getAssetCategoryTree().getTreeHierarchy());
				put("assetLocationTree", record.getAsset().getAssetLocationTree().getTreeHierarchy());
				put("assetCondition", record.getAssetCondition());
				put("assetNotes",record.getAssetNotes());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
				
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
		
		
		
		return selectedFieldResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetPhysicalSurveyReport> checkChildForPhySurvey(int apsmId) {
		return  entityManager.createNamedQuery("AssetPhysicalSurveyReport.checkChildStatus").setParameter("apsmId", apsmId).getResultList();	
	}


	

}
