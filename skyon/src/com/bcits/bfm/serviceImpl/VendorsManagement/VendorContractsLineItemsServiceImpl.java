package com.bcits.bfm.serviceImpl.VendorsManagement;

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

import com.bcits.bfm.model.VendorContractLineitems;
import com.bcits.bfm.service.VendorsManagement.VendorContractsLineItemsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class VendorContractsLineItemsServiceImpl extends GenericServiceImpl<VendorContractLineitems> implements VendorContractsLineItemsService
{
	static Logger logger = LoggerFactory.getLogger(UomServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorContractLineitems> findAll() 
	{
		List<VendorContractLineitems> vcLineItems = entityManager.createNamedQuery("VendorContractLineitems.findAll").getResultList();
		return vcLineItems;
	}
	
	/****** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<VendorContractLineitems> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final VendorContractLineitems record : list)
		{
			
			response.add(new HashMap<String, Object>() 
			{{
				put("vclId", record.getVclId());
            	put("vcId", record.getVcId());
            	put("imId", record.getImId());
            	put("imName",record.getItemMaster().getImName());
            	put("vclSlno", record.getVclSlno());
            	put("quantity", record.getQuantity());
            	put("rate", record.getQuantity());
            	put("amount", record.getAmount());
            	put("createdBy", record.getCreatedBy());
            	put("lastUpdatedBy", record.getLastUpdatedBy());
            	put("lastUpdatedDt", record.getLastUpdatedDt());
            	logger.info(" Contents sent to VendorContractsLineItems Jsp From VendorContractsLineItems");
			}});
		}
		return response;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorContractLineitems> getAllVCLineItemsBasedOnVcId(int vcId)
	{
		return entityManager.createNamedQuery("VendorContractLineitems.getAllVCLineItemsBasedOnVcId", VendorContractLineitems.class).setParameter("vcId", vcId).getResultList();
	}

	@Override
	public String getReqType(int reqid)
	{
		String st = "";
		List list = entityManager.createNamedQuery("Requisition.getReqType").setParameter("reqid", reqid).getResultList();
		Iterator<String> it = list.iterator();		
		while(it.hasNext())
		{
			st = it.next();
		}
		logger.info("*************************************************\nFROM IMPL\n");
		return st;
	}

}
