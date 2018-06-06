package com.bcits.bfm.service.facilityManagement;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.model.JobNotification;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JobNotificationDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JobNotificationService extends GenericService<JobNotification> {	

	
	/**
	 * Find all JobNotification entities.
	 * 
	 * @return List<JobNotification> all JobNotification entities
	 */
	public List<JobNotification> findAll();

	public List<?> readData(int jcId);
	
	public List<JobNotification> readJobNotification(int jcId);

	public Object getAllMembers(int jcId);

	public JobCards getAllMembersGroups(int jcId);

	public JobNotification setParameters(int jcId,
			JobNotification jobNotification, String userName,
			Map<String, Object> map);

	public Object deleteJobObjective(JobNotification jobNotification);

	void setStatus(Integer jnId, String operation, HttpServletResponse response,
			Locale locale) throws IOException;
}