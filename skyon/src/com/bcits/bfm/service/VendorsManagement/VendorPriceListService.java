package com.bcits.bfm.service.VendorsManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.VendorPriceList;
import com.bcits.bfm.service.GenericService;

public interface VendorPriceListService extends GenericService<VendorPriceList>
{	
	public List<VendorPriceList> findAll();
	public VendorPriceList getBeanObject(Map<String, Object> map, String string,VendorPriceList vendorPriceList);
	public void setVendorPriceListStatus(int vpId, String operation,HttpServletResponse response, MessageSource messageSource,Locale locale);
	public List<?> setResponse();
}
