package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.VendorLineItems;
import com.bcits.bfm.model.VendorPayments;
import com.bcits.bfm.service.VendorsManagement.VendorPaymentsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class VendorPaymentsServiceImpl extends GenericServiceImpl<VendorPayments> implements VendorPaymentsService
{
	static Logger logger = LoggerFactory.getLogger(VendorPaymentsServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorPayments> findAll() 
	{
		return entityManager.createNamedQuery("VendorPayments.findAll").getResultList();
	}
	
	/****** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<VendorPayments> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final VendorPayments record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				
				put("vipId", record.getVipId());
            	put("viId", record.getViId());
            	java.util.Date viDateUtil = record.getVipDt();
				java.sql.Date viDateSql = new java.sql.Date(viDateUtil.getTime());            	
            	
            	put("vipDt", viDateSql);
            	put("vendorInvoiceNoTitle",record.getVendorInvoices().getInvoiceNo()+" "+record.getVendorInvoices().getInvoiceTitle());
            	put("vipPayType", record.getVipPayType());
            	put("vipPayamount", record.getVipPayamount());
            	put("vipPayBy", record.getVipPayBy());
            	put("vipPaydetails", record.getVipPaydetails());
            	put("vipNotes",record.getVipNotes());
            	put("createdBy", record.getCreatedBy());
            	put("lastUpdatedBy", record.getLastUpdatedBy());
            	put("lastUpdatedDt", record.getLastUpdatedDt());
				
            	logger.info(" Contents sent to VendorPaymentsLineItems Jsp From VendorPaymentsServiceImpl");
			}});
		}
		return response;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VendorPayments> findVendorPaymentsBasedOnVendorInvoice(int viId) 
	{
		return entityManager.createNamedQuery("VendorPayments.findVendorPaymentsBasedOnVendorInvoice").setParameter("viId",viId).getResultList();
	}
}
