package com.bcits.bfm.serviceImpl.facilityManagement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CostCenter;
import com.bcits.bfm.service.facilityManagement.CostCenterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class CostCenterServiceImpl extends GenericServiceImpl<CostCenter> implements CostCenterService
{

	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public List<?> setResponse() {
		List<CostCenter> list =entityManager.createNamedQuery("CostCenter.findAllList").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final CostCenter record :list) {
			response.add(new HashMap<String, Object>() {{
				put("ccId", record.getCcId());
				put("name", record.getName());
				put("description", record.getDescription());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
		return response;
	}

	
}
