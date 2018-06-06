package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bcits.bfm.model.ServiceBooking;
import com.bcits.bfm.service.GenericService;

@Service
public interface ServiceBookingService  extends GenericService<ServiceBooking>{

	List<ServiceBooking> findAllData();

	List<ServiceBooking> findAll(String createdBy);

	void setItemStatus(int bId, String operation, HttpServletResponse response,
			MessageSource messageSource, Locale locale);

	void setItemStatuss(int bId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);

	void updateserviceStatus( String serviceStatus);

	void updateserviceStatusBook(String serviceStatus);

	void updateserviceStatuss(String string);

	void updateserviceStatusBooks(String string);

}
