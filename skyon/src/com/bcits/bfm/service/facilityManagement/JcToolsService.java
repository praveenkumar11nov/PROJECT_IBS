package com.bcits.bfm.service.facilityManagement;


import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JcTools;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JcToolsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JcToolsService extends GenericService<JcTools> {
	/**
	 * Find all JcTools entities.
	 * 
	 * @return List<JcTools> all JcTools entities
	 */
	public List<JcTools> findAll();

	public List<?> readData(int jcId);

	public Object deleteJcTools(JcTools jcTools);

	public JcTools setParameters(int jcId, JcTools jcTools, String userName,
			Map<String, Object> map);

	public List<JcTools> readJobTools(int jcId);
}