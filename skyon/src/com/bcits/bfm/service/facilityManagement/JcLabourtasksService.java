package com.bcits.bfm.service.facilityManagement;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JcLabourtasks;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JcLabourtasksDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JcLabourtasksService extends GenericService<JcLabourtasks> {	

	/**
	 * Find all JcLabourtasks entities.
	 * 
	 * @return List<JcLabourtasks> all JcLabourtasks entities
	 */
	public List<JcLabourtasks> findAll();

	public List<?> readData(int jcId);

	public JcLabourtasks setParameters(int jcId, JcLabourtasks jcLabourtasks,
			String userName, Map<String, Object> map);

	public Object deleteJcLabourtasks(JcLabourtasks jcLabourtasks);

	public List<?> readData();

	public List<JcLabourtasks> readJobLabourTask(int jcId);

}