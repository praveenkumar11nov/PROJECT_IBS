package com.bcits.bfm.serviceImpl.facilityManagement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ToolMaster;
import com.bcits.bfm.service.facilityManagement.ToolMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * ToolMaster entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .ToolMaster
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ToolMasterServiceImpl extends GenericServiceImpl<ToolMaster> implements ToolMasterService {
	
	private static final Log logger = LogFactory.getLog(ToolMasterService.class);	

	/**
	 * Find all ToolMaster entities.
	 * 
	 * @return List<ToolMaster> all ToolMaster entities
	 */
	@SuppressWarnings("unchecked")
	public List<ToolMaster> findAll() {
		logger.info("finding all ToolMaster instances");
		try {
			final String queryString = "select model from ToolMaster model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	@Override
	public ToolMaster setParameters(ToolMaster toolMaster,String userName, Map<String, Object> map) {
		toolMaster.setTmName(map.get("tmName").toString().toUpperCase());
		toolMaster.setDescription((String) map.get("description"));
		toolMaster.setTmQuantity(Integer.parseInt(map.get("tmQuantity").toString()));
		if(map.get("createdBy")==null)
			toolMaster.setCreatedBy(userName);
		else
			toolMaster.setCreatedBy(userName);
		toolMaster.setLastUpdatedBy(userName);		
		return toolMaster;
	}



	@Override
	public List<?> readData() {
		List<Map<String, Object>> tm= new ArrayList<Map<String, Object>>();
		for (final ToolMaster record :findAll()) {
			tm.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("tmId", record.getTmId());
					put("tmName", record.getTmName());
					put("description", record.getDescription());
					put("tmQuantity",record.getTmQuantity());	
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDt()));
				}
			});
		}
		return tm;	
	}

	@Override
	public void deleteToolMaster(ToolMaster toolMaster) {
		entityManager.remove(entityManager.contains(toolMaster) ? toolMaster : entityManager.merge(toolMaster));
	}

	@Override
	public Integer getQuantityBasedOnName(String toolname) {
		return entityManager.createNamedQuery("ToolMaster.getQuantityBasedOnName",Integer.class).setParameter("toolname",toolname).getSingleResult();
	}

}