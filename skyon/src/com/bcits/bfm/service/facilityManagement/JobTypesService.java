package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JobTypes;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JobTypesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JobTypesService extends GenericService<JobTypes> {
	/**
	 * Find all JobTypes entities.
	 * 
	 * @return List<JobTypes> all JobTypes entities
	 */
	public List<JobTypes> findAll();

	/**
	 * @param map
	 * @return
	 */
	public JobTypes getBeanObject(Map<String, Object> map);


}