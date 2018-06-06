package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.Notification;

public interface NotificationService extends GenericService<Notification>{



	List<?> findAll(String userId);

	void updateReadStatusAsOne(int msgId, int i);

	List<Notification> getUserNotificationMsg(String userId);

	List<Notification> getUserInboxMsg(String userId, int read_status);

}
