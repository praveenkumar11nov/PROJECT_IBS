package com.bcits.bfm.service.VendorsManagement;

import java.sql.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.VendorInvoices;
import com.bcits.bfm.service.GenericService;

public interface VendorInvoicesService extends GenericService<VendorInvoices>
{
	public List<VendorInvoices> findAll();
	public List<?> setResponse();
	public List<Object[]> findAllInvoices();
	public void setVendorInvoiceStatus(int viId, String operation,HttpServletResponse response, MessageSource messageSource,Locale locale);
	@SuppressWarnings("rawtypes")
	public List getAllVcLineItemsId();
	public Date getVendorContractStartDateBasedOnVcId(int vcid);
	public List<VendorInvoices> findById(int viId);
}
