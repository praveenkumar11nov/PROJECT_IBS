package com.bcits.bfm.serviceImpl.facilityManagement;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.JcLabourtasks;
import com.bcits.bfm.service.facilityManagement.JcLabourtasksService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JcLabourtasks entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .JcLabourtasks
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JcLabourtasksServiceImpl extends GenericServiceImpl<JcLabourtasks> implements JcLabourtasksService {
	
	private static final Log logger = LogFactory.getLog(JcLabourtasksServiceImpl.class);	
	@Autowired
	private JobCardsService jobCardsService;
	
	/**
	 * Find all JcLabourtasks entities.
	 * 
	 * @return List<JcLabourtasks> all JcLabourtasks entities
	 */
	@SuppressWarnings("unchecked")
	public List<JcLabourtasks> findAll() {
		logger.info("finding all JcLabourtasks instances");
		try {
			final String queryString = "select model from JcLabourtasks model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jcLabourTasks = new ArrayList<Map<String, Object>>();
		for (final JcLabourtasks record :readJobLabourTask(jcId)) {
			jcLabourTasks.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jclId", record.getJclId());
					put("jobCards", record.getJobCards().getJcId());
					put("jclType", record.getJclType());
					put("jclLabourdesc", record.getJclLabourdesc());											
					put("jclHours", record.getJclHours());											
					put("jclBillable", record.getJclBillable());											
					put("jclRate", record.getJclRate());											
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return jcLabourTasks;
	}

	public List<JcLabourtasks> readJobLabourTask(int jcId) {
		@SuppressWarnings("unchecked")
		List<JcLabourtasks> jcLabourtasks = entityManager.createNamedQuery("JobCards.readJobLabourTask").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcLabourtasks;
	}

	@Override
	public JcLabourtasks setParameters(int jcId, JcLabourtasks jcLabourtasks,String userName, Map<String, Object> map) {
		jcLabourtasks.setCreatedBy(userName);
		jcLabourtasks.setJclBillable((String) map.get("jclBillable"));
		jcLabourtasks.setJclHours(map.get("jclHours").toString());
		jcLabourtasks.setJclLabourdesc((String) map.get("jclLabourdesc"));
		jcLabourtasks.setJclRate(Integer.parseInt(map.get("jclRate").toString()));
		jcLabourtasks.setJclType((String) map.get("jclType"));
		jcLabourtasks.setJobCards(jobCardsService.find(jcId));
		jcLabourtasks.setLastUpdatedBy(userName);		
		return jcLabourtasks;
	}

	@Override
	public Object deleteJcLabourtasks(JcLabourtasks jcLabourtasks) {
		logger.info("finding all JcLabourtasks instances");
		try {
			final String queryString = "DELETE JcLabourtasks WHERE jclId="+jcLabourtasks.getJclId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}	
	}

	@Override
	public List<?> readData() {
		List<Map<String, Object>> jcLabourTasks = new ArrayList<Map<String, Object>>();
		jcLabourTasks.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("taskId", 12);
					put("title", "Electrical");
					put("description", "Repair Bulb");
					put("isAllDay", "Daily");											
					put("recurrenceRule", "Nothing");											
					put("recurrenceId", 12);											
					put("recurrenceException", "No");											
					put("ownerId", 12);
					put("start", ConvertDate.TimeStampString(new Timestamp(new Date().getTime())));
					put("end",ConvertDate.TimeStampString(new Timestamp(new Date().getTime())));
				}
		});
		
		return jcLabourTasks;
	}
}