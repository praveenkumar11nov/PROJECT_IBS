package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.ToolMaster;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for ToolMasterDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ToolMasterService extends GenericService<ToolMaster> {	
	/**
	 * Find all ToolMaster entities.
	 * 
	 * @return List<ToolMaster> all ToolMaster entities
	 */
	public List<ToolMaster> findAll();

	public ToolMaster setParameters(ToolMaster toolMaster,
			String userName, Map<String, Object> map);

	public List<?> readData();

	public void deleteToolMaster(ToolMaster toolMaster);

	public Integer getQuantityBasedOnName(String toolname);	
	
}