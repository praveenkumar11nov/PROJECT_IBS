package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;


import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Handbook;
import com.bcits.bfm.service.helpDeskManagement.HandbookService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * A data access object (DAO) providing persistence and search support for
 * Handbook entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .Handbook
 * @author MyEclipse Persistence Tools
 */
@Repository
public class HandbookServiceImpl extends GenericServiceImpl<Handbook> implements HandbookService {
	
	private static final Log logger = LogFactory.getLog(HandbookServiceImpl.class);

	/**
	 * Find all Handbook entities.
	 * 
	 * @return List<Handbook> all Handbook entities
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Handbook> findAll() {
		logger.info("finding all Handbook instances");
		try {
			final String queryString = "select model from Handbook model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

}