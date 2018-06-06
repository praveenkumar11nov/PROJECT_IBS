package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.VendorInvoices;
import com.bcits.bfm.service.VendorsManagement.VendorInvoicesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class VendorInvoicesServiceImpl extends GenericServiceImpl<VendorInvoices> implements VendorInvoicesService
{
	static Logger logger = LoggerFactory.getLogger(VendorInvoicesServiceImpl.class);

	@Resource
	private DateTimeCalender dateTimeCalender;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorInvoices> findAll() 
	{
		return entityManager.createNamedQuery("VendorInvoices.findAll").getResultList();
	}
	
	
	/****** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		
		List<VendorInvoices> list =  entityManager.createNamedQuery("VendorInvoices.findAllList").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
		
			response.add(new HashMap<String, Object>() 
			{{
				final Object[] values = (Object[])i.next();

				put("viId", (Integer)values[0]);
				put("vcId", (Integer)values[1]);
				put("contractName",(String)values[2]);
				put("vendorInvoiceDetails", (String)values[3]+","+(String)values[2]);
				put("invoiceNo", (String)values[4]);				
				java.util.Date invoiceDtUtil =  (java.util.Date)values[5];
				java.sql.Date invoiceDtSql = new java.sql.Date(invoiceDtUtil.getTime());
				put("invoiceDt", invoiceDtSql);
				put("invoiceTitle", (String)values[6]);
				put("description", (String)values[7]);
				put("status", (String)values[8]);
            	put("createdBy", (String)values[9]);
            	put("lastUpdatedBy", (String)values[10]);
            	put("lastUpdatedDt", (Timestamp)values[11]);            	
            	logger.info(" Contents sent to Vendor Invoices Jsp From VendorInvoiceServiceImpl");
			}});
		}
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setVendorInvoiceStatus(int viId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			PrintWriter out = response.getWriter();			
			if(operation.equalsIgnoreCase("approved"))
			{
				entityManager.createNamedQuery("VendorInvoice.UpdateStatus").setParameter("status", "Approved").setParameter("viId", viId).executeUpdate();
				out.write("Vendor-Invoice Approved");
			}
			else
			{
				entityManager.createNamedQuery("VendorInvoice.UpdateStatus").setParameter("status", "Open").setParameter("viId", viId).executeUpdate();
				out.write("Vendor-Invoice Opened");				
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllVcLineItemsId()
	{	
		return entityManager.createNamedQuery("VendorInvoices.readAllVCLineitemsId").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date getVendorContractStartDateBasedOnVcId(int vcid) 
	{
		Date dateVal = null;
		@SuppressWarnings("rawtypes")
		List list = entityManager.createNamedQuery("VendorInvoices.getVendorContractStartDateBasedOnVcId").setParameter("vcid", vcid).getResultList();
		Iterator<Date> it = list.iterator();
		while(it.hasNext())
		{
			dateVal = it.next();
		}
		return dateVal;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<VendorInvoices> findById(int viId) {
		return  entityManager.createNamedQuery("VendorInvoices.findById").setParameter("viId", viId).getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllInvoices() {
		
		return entityManager.createNamedQuery("VendorInvoices.findAllList").getResultList();
	}
	
}
