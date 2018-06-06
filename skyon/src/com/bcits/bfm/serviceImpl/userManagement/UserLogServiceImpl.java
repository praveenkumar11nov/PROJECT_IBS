package com.bcits.bfm.serviceImpl.userManagement;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.UserLog;
import com.bcits.bfm.service.userManagement.UserLogSerivce;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserLogServiceImpl extends GenericServiceImpl<UserLog> implements
		UserLogSerivce {
	private static final Log logger = LogFactory
			.getLog(UserLogServiceImpl.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserLog> findAll() {
		logger.info("finding all UserLog instances");
		try {
			final String queryString = "select model from UserLog model ORDER BY model.ulId DESC";
			Query query = entityManager.createQuery(queryString);
			List<UserLog> ul=query.getResultList();
			return ul;
		} catch (RuntimeException re) {
			logger.error("Find UserLog Details Failed", re);
			throw re;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<UserLog> findAllUserLogDetails(String userName) {
		logger.info("finding all UserLog instances");

		String name = (String) SessionData.getUserDetails().get("userID");
		userName = name;
		try {
			return entityManager.createNamedQuery("getAllUserDetails")
					.setParameter("userName", userName).setMaxResults(17).getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateUserDetails(String logoutmethod, String SessionId,
			Date SessionEnd,String duration) {
		int i = entityManager.createNamedQuery("updateUserdetails")
				.setParameter("logoutmethod", logoutmethod)
				.setParameter("sessionEnd", SessionEnd)
				.setParameter("sessionId", SessionId)
				.setParameter("duration", duration).executeUpdate();
		
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserLog findbySessionId(String id) {
		final String queryString = "select model from UserLog model where model.ulSessionId=:ulSessionId";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("ulSessionId", id);
		List<UserLog> ul=query.getResultList();
		if(ul.size()>0){
			return ul.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Long getCount(String sessionId) {
		try {
			return entityManager.createNamedQuery("UserLog.getCount",Long.class)
					.setParameter("userName",sessionId).getSingleResult();
		} catch (RuntimeException re) {
			logger.error("get Count failed", re);
			throw re;
		}
	}

}