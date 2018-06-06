package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.Arms;
import com.bcits.bfm.service.GenericService;

public interface ArmsService extends GenericService<Arms>
{

	public List<Arms> findAllArmsBasedOnPersonID(int personId);

	public Integer getArmsIdBasedOnCreatedByAndLastUpdatedDt(String createdBy,
			Timestamp lastUpdatedDt);

	public Arms getArmsObject(Map<String, Object> map, String type, Arms arms);

}