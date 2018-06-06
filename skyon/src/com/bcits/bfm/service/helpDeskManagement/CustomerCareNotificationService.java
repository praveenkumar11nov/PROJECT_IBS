package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.CustomerCareNotification;
import com.bcits.bfm.service.GenericService;

public interface CustomerCareNotificationService extends  GenericService<CustomerCareNotification> {
	
	CustomerCareNotification getObject(Map<String, Object> map, String type,CustomerCareNotification customerCareNotification, HttpServletRequest request);

	List<?> getNotificationObject();
}

