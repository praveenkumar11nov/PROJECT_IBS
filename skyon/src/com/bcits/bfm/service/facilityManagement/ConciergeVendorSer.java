package com.bcits.bfm.service.facilityManagement;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ConciergeVendorServices;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.GenericService;

@Repository
public interface ConciergeVendorSer extends GenericService<ConciergeVendorServices>{
	
	
	public ConciergeVendorServices getVendorServiceObject(Map<String, Object> map, String type,ConciergeVendorServices conciergeVendorServices,HttpServletRequest request) throws ParseException;

	public List<?> getVendorServices();
	
	void vendorServiceStatus(int vsId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	
	List<Person> getVendorNames();
	
	List<String> getServiceNames();
	
	List<Integer> getServiceIdsBasedOnVendorId(int cvId);
	
	List<ConciergeVendorServices> findAll();

	void updateServiceEndDate(int vendorServiceId, Date serviceEndDate);
	
	int getVendorServiceId( int csvId,int cssId );

	

}

