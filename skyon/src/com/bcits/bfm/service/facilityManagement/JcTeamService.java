package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JcTeam;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JcTeamDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JcTeamService extends GenericService<JcTeam> {

	/**
	 * Find all JcTeam entities.
	 * 
	 * @return List<JcTeam> all JcTeam entities
	 */
	public List<JcTeam> findAll();

	public List<?> readData(int jcId);

	public JcTeam setParameters(int jcId, JcTeam jobTeam, String userName,
			Map<String, Object> map);

	public Object deleteJobTeam(JcTeam jobTeam);

	public List<JcTeam> readJobTeam(int jcId);
}