package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.JcNotes;
import com.bcits.bfm.service.facilityManagement.JcNotesService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JcNotes entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .JcNotes
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JcNotesServiceImlp extends GenericServiceImpl<JcNotes> implements JcNotesService {
	
	private static final Log logger = LogFactory.getLog(JcNotesService.class);	 
	@Autowired
	private JobCardsService jobCardsService;
	/**
	 * Find all JcNotes entities.
	 * 
	 * @return List<JcNotes> all JcNotes entities
	 */
	@SuppressWarnings("unchecked")
	public List<JcNotes> findAll() {
		logger.info("finding all JcNotes instances");
		try {
			final String queryString = "select model from JcNotes model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jcNotes = new ArrayList<Map<String, Object>>();
		for (final JcNotes record :readJobNotes(jcId) ) {
			jcNotes.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jcnId", record.getJcnId());
					//put("jobCards", record.getJobCards().getJcId());
					put("notes", record.getNotes());
					put("jcnDt",ConvertDate.TimeStampString(record.getJcnDt()));					
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return jcNotes;	
	}

	public List<JcNotes> readJobNotes(int jcId) {
		@SuppressWarnings("unchecked")
		List<JcNotes> jcNotes = entityManager.createNamedQuery("JobCards.readJobNotes").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcNotes;
	}

	@Override
	public JcNotes setParameters(int jcId, JcNotes jobNotes, String userName,Map<String, Object> map) {
		if(map.get("createdBy")==null)
			jobNotes.setCreatedBy(userName);
		jobNotes.setJcnDt(ConvertDate.formattedDate((map.get("jcnDt").toString())));
		jobNotes.setJobCards(jobCardsService.find(jcId));
		jobNotes.setLastUpdatedBy(userName);
		jobNotes.setNotes((String) map.get("notes"));
		return jobNotes;
	}

	@Override
	public Object delteJobNotes(JcNotes jobNotes) {
		logger.info("finding all JcNotes instances");
		try {
			final String queryString = "DELETE JcNotes WHERE jcnId="+jobNotes.getJcnId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
}