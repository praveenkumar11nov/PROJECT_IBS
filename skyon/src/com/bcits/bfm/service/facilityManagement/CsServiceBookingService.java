package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.CsServiceBooking;
import com.bcits.bfm.model.CsVendorServiceCharges;
import com.bcits.bfm.service.GenericService;

public interface CsServiceBookingService extends GenericService<CsServiceBooking>  {
	
	
	CsServiceBooking getBookingServiceObject(Map<String, Object> map, String type, CsServiceBooking csServiceBooking ,HttpServletRequest request);
	
	 List<?> getBookedServices();
	 
	 List<CsServiceBooking> findAll();
	 
	 void setServiceBookingStatus(int sbId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	 

}
