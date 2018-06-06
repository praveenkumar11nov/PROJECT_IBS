package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.ConciergeServices;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.GenericService;

public interface ConciergeService extends GenericService<ConciergeServices> {
	
	public List<?> getResponse();
	
	public List<ConciergeServices> getAll();
	
	String getServiceNameBasedOnId(int cssId);
	
	List<String> getGroupNames();
	
	List<String> getServiceNames();
	
	void setCsServiceStatus(int cssId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	
	int getServiceIdBasedOnName(String serviceName);
	
	public void updateServiceEndDate(int serviceId, java.sql.Date serviceEndDate);
	
	List<Integer> getDistinctCssIds();
	
	
	
}
