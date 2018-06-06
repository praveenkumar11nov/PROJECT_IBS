package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.GenericService;

public interface StoreMasterService extends GenericService<StoreMasterEntity>{

	public List<StoreMasterEntity> findAllData();

	public void setstoreStatus(int sId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);

	public List<StoreMasterEntity> findAllStoreNames();

	public List<StoreMasterEntity> findAll();

	public List<StoreMasterEntity> findAllLoginDetails(String username, String password);

	public List<StoreMasterEntity> findStoreMobile(int sId);

	
	

	
	

}
