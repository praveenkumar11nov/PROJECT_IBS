package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.VendorIncidents;
import com.bcits.bfm.service.VendorsManagement.VendorIncidentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class VendorIncidentServiceImpl extends GenericServiceImpl<VendorIncidents> implements VendorIncidentService
{
	static Logger logger = LoggerFactory.getLogger(VendorIncidentServiceImpl.class);
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorIncidents> findAll() 
	{
		return entityManager.createNamedQuery("VendorIncidents.findAll").getResultList();
	}	
	
	/****** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		
		List<VendorIncidents> list =  entityManager.createNamedQuery("VendorIncidents.findAllList").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();   
		
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
		
			response.add(new HashMap<String, Object>() 
			{{
				final Object[] values = (Object[])i.next();

				put("vcSlaId", (Integer)values[0]);
				put("vcId", (Integer)values[1]);
				put("contractName",(String)values[2]);
				put("vendorInvoiceDetails", (String)values[3]+","+(String)values[2]);
				
				java.util.Date incidentDtUtil =  (Date)values[4];
				java.sql.Date incidentDtSql = new java.sql.Date(incidentDtUtil.getTime());
				
				put("incidentDt", incidentDtSql); 
				put("incidentDescription", (String)values[5]); 
				put("expectedSla", (Integer)values[6]); 
				put("slaReached", (Integer)values[7]); 
				put("slaComments", (String)values[8]);
				put("slaStatus", (String)values[9]);
				put("slaAlertedDt", (Date)values[10]);
            	logger.info(" Contents sent to Vendor Incidents Jsp From VendorIncidentsServiceImpl");
			}});
		}
		
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateVendorIncidentsStatus(int id, String newStatus) 
	{
		return entityManager.createNamedQuery("VendorIncidents.updateStatus").setParameter("id", id).setParameter("newStatus", newStatus).executeUpdate();
	}

	@Override
	public Integer getContractSLA(int vcId) 
	{
		/*int slaVal = 0;
		List list = (Integer)entityManager.createNamedQuery("VendorIncidents.getSLAForSelectedContract").setParameter("vcId", vcId).getSingleResult();
		Iterator<?> it = list.iterator();
		while(it.hasNext())
		{
			slaVal = (Integer) it.next();
		}*/
		return (Integer)entityManager.createNamedQuery("VendorIncidents.getSLAForSelectedContract").setParameter("vcId", vcId).getSingleResult();
	}
}
