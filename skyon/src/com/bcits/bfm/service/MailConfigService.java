package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.MailMaster;


public interface MailConfigService extends GenericService<MailMaster>{
	
	

	List<?> findAll();

	int getServiceStatus(String value);
	MailMaster getMailMasterByService(String serviceType);
	

}
