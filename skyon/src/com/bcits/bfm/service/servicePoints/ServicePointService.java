package com.bcits.bfm.service.servicePoints;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ServicePointEntity;
import com.bcits.bfm.service.GenericService;

public interface ServicePointService extends  GenericService<ServicePointEntity> {

	List<ServicePointEntity> findALL(int srId);
	void setServicePointStatus(int elrmid, String operation, HttpServletResponse response);
	List<String> getAllPropertyNumbers();
	List<String> getAllBlockNames();
	List<ServicePointEntity> findAllServicePoints();
}
