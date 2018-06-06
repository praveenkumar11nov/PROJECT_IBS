package com.bcits.bfm.serviceImpl;

import java.sql.Blob;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bcits.bfm.model.PhotoEvent;
import com.bcits.bfm.service.PhotoEventService;

@Repository
public class PhotoEventServiceImpl extends GenericServiceImpl<PhotoEvent> implements PhotoEventService{

	private static final Log logger = LogFactory.getLog(PhotoEvent.class);
	@Override
	public List<PhotoEvent> findAllPhotoevent() {
		logger.info("finding all PhotoEvent instances");
		try {
			final String queryString = "select model from PhotoEvent model order by model.peId desc";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	@Override
	public void addDocument(int peId, Blob blob) {
		// TODO Auto-generated method stub
		
	}
	

}
