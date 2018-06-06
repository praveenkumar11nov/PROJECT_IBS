package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.JobTypes;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * A data access object (DAO) providing persistence and search support for
 * JobTypes entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .JobTypes
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JobTypesServiceImpl extends GenericServiceImpl<JobTypes> implements
		JobTypesService {
	private static final Log logger = LogFactory.getLog(JobTypesService.class);


	/**
	 * Find all JobTypes entities.
	 * 
	 * @return List<JobTypes> all JobTypes entities
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<JobTypes> findAll() {
		logger.info("Finding Job Type Details");
		return entityManager.createNamedQuery("JobTypes.findAllJobTypesList").getResultList();
	}

	@Override
	public JobTypes getBeanObject(Map<String, Object> map) {
		logger.error("Job Types Setting Details Started");
		JobTypes jt = new JobTypes();
		jt.setJtType(((String) map.get("jtType")).toUpperCase());
		jt.setJtDescription((String) map.get("jtDescription"));
		jt.setJtSla(Integer.parseInt(map.get("jtSla").toString()));
		logger.error("Job Types Setting Details Completed");
		return jt;
	}
}