package com.bcits.bfm.service.facilityManagement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.JcObjectives;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JcObjectivesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JcObjectivesService extends GenericService<JcObjectives> {

	/**
	 * Find all JcObjectives entities.
	 * 
	 * @return List<JcObjectives> all JcObjectives entities
	 */
	public List<JcObjectives> findAll();	

	List<JcObjectives> readJobObjective(int jcId);

	List<?> readData(int jcId);

	public void setStatus(int jcId, String operation,HttpServletResponse response) throws IOException;	

	JcObjectives setParameters(int jcId, JcObjectives jobObjective, String userName, Map<String, Object> map);

	public Object deleteJobObjective(JcObjectives jcObjectives);

}