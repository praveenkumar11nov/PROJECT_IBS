package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.VendorRequests;
import com.bcits.bfm.service.VendorsManagement.VendorRequestService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class VendorRequestServiceImpl extends GenericServiceImpl<VendorRequests> implements VendorRequestService
{
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorRequests> findAll() 
	{
		return entityManager.createNamedQuery("VendorRequests.findAll").getResultList();
	}
	
	/****** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		

		List<VendorRequests> list =entityManager.createNamedQuery("VendorRequests.findAllList").getResultList();

		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
		
			response.add(new HashMap<String, Object>() 
			{{
				final Object[] values = (Object[])i.next();

				int vcid = (Integer)values[0];				
				if(vcid == 1)
				{
					put("vendorInvoiceDetails", "");					
				}
				else
				{
					put("vendorInvoiceDetails", (String)values[1]+" "+(String)values[2]);
				}	
				put("vrId", (Integer)values[3]);
            	put("vendorId", (Integer)values[4]);   
            	
            	if((String)values[6]!=null)
            	{
            		put("vendorName", (String)values[5]+" "+(String)values[6]);	            		
            	}
            	else if((String)values[6] == null)
            	{
            		put("vendorName", (String)values[5]);
            	}
            	put("vcId", (Integer)values[0]);
            	put("requestType", (String)values[7]);
            	put("requestNote", (String)values[8]);
            	put("replyNote", (String)values[9]);
            	put("status", (String)values[10]);
            	put("createdBy", (String)values[11]);
            	put("lastUpdatedBy", (String)values[12]);
            	put("lastUpdatedDt", (Timestamp)values[13]);				
		   }});
	    }
		
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateVendorRequestStatus(int id, String newStatus) 
	{
		return entityManager.createNamedQuery("VendorRequests.updateVendorRequestStatus").setParameter("id", id).setParameter("newStatus", newStatus).executeUpdate();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateVendorRequestStatus(int id, String newStatus,String replyNote) 
	{
		return entityManager.createNamedQuery("VendorRequests.updateVendorRequestStatus").setParameter("id", id).setParameter("newStatus", newStatus).setParameter("replyNote", replyNote).executeUpdate();
	}
}
