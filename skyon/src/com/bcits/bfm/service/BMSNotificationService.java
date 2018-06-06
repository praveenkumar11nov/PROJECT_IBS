package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.BMSNotificationEntity;

public interface BMSNotificationService extends GenericService<BMSNotificationEntity>{

	List<?> readBMSNotificationData();

}
