package com.bcits.bfm.serviceImpl.userManagement;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Messages;
import com.bcits.bfm.service.userManagement.MessagesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class MessagesServiceImpl extends GenericServiceImpl<Messages>
implements MessagesService  {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Messages> getUserInboxMsg(String usrId, String msgStatus) {
		
		return entityManager.createNamedQuery("Messages.userInbox")
				.setParameter("usrId", usrId) .setParameter("msgStatus", msgStatus)
				.getResultList();
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Messages> getAll() {
		return entityManager.createNamedQuery("Messages.findAll").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateStatusAsTrash(int msgId, String msgStatus) {
		
		return  entityManager.createNamedQuery("Messages.updateInboxAsTrash")
				.setParameter("msgId", msgId) .setParameter("msgStatus", msgStatus)
				.executeUpdate();
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getReadStatusBasedOnId(int msgId) {
		
		return entityManager.createNamedQuery("Messages.getReadStatusBasedOnId" , Integer.class)
				.setParameter("msgId", msgId)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateReadStatus(int msgId , int status) {
		
		return  entityManager.createNamedQuery("Messages.updateReadStatus")
				.setParameter("msgId", msgId) 
				.setParameter("status", status)
				.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Messages> saveMessageList(List<Messages> msg) {
		BfmLogger.logger.info("saving "+msg+" instance");
		try {
			this.entityManager.persist(msg);
			BfmLogger.logger.info(msg+" saved successful");
			return msg;
			
		} catch (RuntimeException re) {
			BfmLogger.logger.error("saving "+msg+" failed", re);
			throw re;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> getFromUsers(String userId, String msgStatus) {
		return entityManager.createNamedQuery("Messages.getFromUsers" , String.class)
				.setParameter("userId", userId)
				.setParameter("msgStatus", msgStatus)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> getToUsers(String userId, String msgStatus) {
		return entityManager.createNamedQuery("Messages.getToUsers" , String.class)
				.setParameter("userId", userId)
				.setParameter("msgStatus", msgStatus)
				.getResultList();
	}

	/*@Override
	public int getMsgId(int userId, String fromUser, String msgStatus) {
		
		List<Integer> userId1 = entityManager.createNamedQuery("Users.getLoginUserId")
				.setParameter("userName", userName).getResultList();
		
		Iterator it=userId1.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
	}*/

	/*@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public void updateInboxMessages(int userId, String fromUser,
			String msgStatus) {
		
		entityManager.createNamedQuery("Messages.updateInboxMsg")
		.setParameter("usrId", userId) .setParameter("fromuser", fromUser) .setParameter("msgStatus", msgStatus)
		.getResultList();
		
	}
	*/

}
