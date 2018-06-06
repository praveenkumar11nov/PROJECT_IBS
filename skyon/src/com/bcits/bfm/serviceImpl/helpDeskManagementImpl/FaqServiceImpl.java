package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.sql.Blob;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Faq;
import com.bcits.bfm.service.helpDeskManagement.FaqService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * A data access object (DAO) providing persistence and search support for Faq
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see .Faq
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FaqServiceImpl extends GenericServiceImpl<Faq> implements FaqService {
	
	private static final Log logger = LogFactory.getLog(FaqServiceImpl.class);
	
	/**
	 * Find all Faq entities.
	 * 
	 * @return List<Faq> all Faq entities
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Faq> findAll() {
		logger.info("finding all Faq instances");
		try {
			final String queryString = "select model from Faq model order by model.faqId desc";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public void updateDocument(int faqId, Blob faqDocument) {
		entityManager.createNamedQuery("Faq.updateDocument").setParameter("faqId", faqId).setParameter("faqDocument", faqDocument).executeUpdate();
		
	}	
}