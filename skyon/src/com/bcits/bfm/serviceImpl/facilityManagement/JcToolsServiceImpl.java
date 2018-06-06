package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.JcTools;
import com.bcits.bfm.service.facilityManagement.JcToolsService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.ToolMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JcTools entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .JcTools
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JcToolsServiceImpl extends GenericServiceImpl<JcTools> implements JcToolsService {
	
	private static final Log logger = LogFactory.getLog(JcToolsService.class);
	@Autowired
	private JobCardsService jobCardsService;
	@Autowired
	private ToolMasterService toolMasterService;
	/**
	 * Find all JcTools entities.
	 * 
	 * @return List<JcTools> all JcTools entities
	 */
	@SuppressWarnings("unchecked")
	public List<JcTools> findAll() {
		logger.info("finding all JcTools instances");
		try {
			final String queryString = "select model from JcTools model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jcTools = new ArrayList<Map<String, Object>>();
		for (final JcTools record :readJobTools(jcId)) {
			jcTools.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jctoolId", record.getJctoolId());
					put("jobCards", record.getJobCards().getJcId());
					put("quantity", record.getQuantity());
					put("toolMaster", record.getToolMaster().getTmName());	
					put("toolMasterId", record.getToolMaster().getTmId());					
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return jcTools;	
	}
	
	public List<JcTools> readJobTools(int jcId) {
		@SuppressWarnings("unchecked")
		List<JcTools> jcTools = entityManager.createNamedQuery("JobCards.readJobTools").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jcTools;
	}
	@Override
	public Object deleteJcTools(JcTools jcTools) {
		logger.info("finding all JcTools instances");
		try {
			final String queryString = "DELETE JcTools WHERE jctoolId="+jcTools.getJctoolId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}	
	}
	@Override
	public JcTools setParameters(int jcId, JcTools jcTools, String userName,Map<String, Object> map) {
		if(map.get("createdBy")==null||map.get("createdBy")=="")
			jcTools.setCreatedBy(userName);
		jcTools.setQuantity((String) map.get("quantity"));
		jcTools.setJobCards(jobCardsService.find(jcId));
		jcTools.setLastUpdatedBy(userName);
		if(map.get("toolMaster") instanceof String){
			jcTools.setToolMaster(toolMasterService.find(Integer.parseInt(map.get("toolMasterId").toString())));
		}else{
			jcTools.setToolMaster(toolMasterService.find(Integer.parseInt(map.get("toolMaster").toString())));
		}
		return jcTools;
	}	
}