package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.JcObjectives;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.service.facilityManagement.JcObjectivesService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JcObjectives entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .JcObjectives
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JcObjectivesServiceImpl extends GenericServiceImpl<JcObjectives> implements JcObjectivesService {
	private static final Log logger = LogFactory.getLog(JcObjectivesService.class);
	
	@Autowired
	private JobCardsService jobCardsService;
	/**
	 * Find all JcObjectives entities.
	 * 
	 * @return List<JcObjectives> all JcObjectives entities
	 */
	@SuppressWarnings("unchecked")
	public List<JcObjectives> findAll() {
		logger.info("finding all JcObjectives instances");
		try {
			final String queryString = "select model from JcObjectives model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JcObjectives> readJobObjective(int jcId) {
		List<JcObjectives> jcObjectives = entityManager.createNamedQuery("JobCards.readJobObjective").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcObjectives;
	}

	@Override
	public List<?> readData(int jcId) {			
		List<Map<String, Object>> jcObjectives = new ArrayList<Map<String, Object>>();
		for (final JcObjectives record :readJobObjective(jcId)) {
			jcObjectives.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jcoId", record.getJcoId());
					//put("jobCards", record.getJobCards().getJcId());
					put("jcObjective", record.getJcObjective());
					put("jcObjectiveAch", record.getJcObjectiveAch());											
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return jcObjectives;		
	}

	@Override
	public void setStatus(int jcoId, String operation,HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();	
		if(operation.equalsIgnoreCase("NO")){
			
			JcObjectives jcObjectives=find(jcoId);
			if(jcObjectives.getJobCards().getStatus().equalsIgnoreCase("INIT")){
				out.write("Job Card Not Yet Opened");
			}else{
				entityManager.createNamedQuery("JobCards.UpdateJobObjectiveStatus").setParameter("Status", "YES").setParameter("jcoId", jcoId).executeUpdate();
				out.write("Objective Achieved");
			}			
		}else{
			out.write("Objective Already Achieved");
		}	
		
	}

	@Override
	public JcObjectives setParameters(int jcId,JcObjectives jobObjective,String userName,Map<String, Object> map) {
		jobObjective.setJobCards(jobCardsService.find(jcId));
		jobObjective.setJcObjective((String) map.get("jcObjective"));
		jobObjective.setJcObjectiveAch((String) map.get("jcObjectiveAch"));
		jobObjective.setCreatedBy(userName);
		jobObjective.setLastUpdatedBy(userName);		
		return jobObjective;
	}

	@Override
	public Object deleteJobObjective(JcObjectives jcObjectives) {
		logger.info("finding all JobObjective instances");
		try {
			final String queryString = "DELETE JcObjectives WHERE jcoId="+jcObjectives.getJcoId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}	
}