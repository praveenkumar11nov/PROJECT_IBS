package com.bcits.bfm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.PrepaidServiceMaster;

public interface PrepaidServiceMasterSs extends GenericService<PrepaidServiceMaster>{

	
	List<?> getAllServiceNames();
	List<?> readAllServices();
	void setServiceStatus(int serviceId, String operation, HttpServletResponse response);
	public List<Map<String, Object>> getMinMaxDate(int serviceId);
}
