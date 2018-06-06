package com.bcits.bfm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Notification;
import com.bcits.bfm.service.NotificationService;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class NotificationServiceImpl extends GenericServiceImpl<Notification> implements NotificationService {

	@Override
	public List<?> findAll(String userId) {
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		List<Notification> notificationData=entityManager.createNamedQuery("Notification.findAll").setParameter("userId", userId).getResultList();
		for(final Notification notfication:notificationData)
		{
			result.add(new HashMap<String,Object>(){
				
				{
					put("cpNotId",notfication.getCpNotId());
					put("subject",notfication.getSubject());
					put("message",notfication.getMessage());
					put("lastUpdatedDate",ConvertDate.TimeStampString(notfication.getLastUpdatedDate()));
					put("fromUser",notfication.getFromUser());
					
				}
			});
			
		}
		
		return result;
	}

	@Override
	public void updateReadStatusAsOne(int msgId, int i) {
		// TODO Auto-generated method stub
		
		entityManager.createNamedQuery("Notification.UpdateNotificationStatus").setParameter("msgId", msgId).setParameter("i", i).executeUpdate();
	}

	@Override
	public List<Notification> getUserNotificationMsg(String userId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Notification.userInbox").setParameter("userId", userId).getResultList();
	}

	@Override
	public List<Notification> getUserInboxMsg(String userId, int read_status) {
		
		return entityManager.createNamedQuery("Notification.unReadMessages").setParameter("userId", userId).setParameter("read_status", read_status).getResultList();
	}

	

	
}
