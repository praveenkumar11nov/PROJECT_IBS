package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class VendorsServiceImpl extends GenericServiceImpl<Vendors> implements VendorsService
{
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Vendors> findAll() 
	{
		List<Vendors> vendors = entityManager.createNamedQuery("Vendors.findAll").getResultList();
		return vendors;
	}	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Vendors getVendorInstanceBasedOnVendorId(int vendorId)
	{
		return entityManager.createNamedQuery("Vendors.getVendorInstanceBasedOnVendorId",Vendors.class)
		.setParameter("vendorId", vendorId)
		.getSingleResult();		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Vendors> findAllVendorBasedOnPersonID(int personId) 
	{		
		return entityManager.createNamedQuery("Vendors.findAllVendorBasedOnPersonID",Vendors.class).setParameter("personId", personId).getResultList();
	}	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Vendors getVendorsObject(Map<String, Object> map, String type, Vendors vendors ,int personId)
	{
		List id = findVendorIdBasedOnpersonId(personId);
		Iterator<Integer> id1 = id.iterator();
		int vendorId = 0;
		String cno = "";
		while(id1.hasNext())
		{		
			vendorId = id1.next();
		}
		String vendorStatus = "";
		DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
		if(type == "update")
		{
			vendors.setVendorId(vendorId);
			Vendors VendorRec = getVendorInstanceBasedOnVendorId(vendorId);			
			vendorStatus = VendorRec.getStatus();
			vendors.setCreatedBy(VendorRec.getCreatedBy());
			vendors.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			vendors.setStatus(vendorStatus);
		}
		else if (type == "save")
		{					
			vendors.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			vendors.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			vendors.setStatus("Created");
		}
		vendors.setPersonId(personId);
		vendors.setPanNo((String)map.get("panNo"));
		vendors.setCstNo((String)map.get("cstNo"));
		vendors.setStateTaxNo((String)map.get("stateTaxNo"));
		vendors.setServiceTaxNo((String)map.get("serviceTaxNo"));
		
		return vendors;
	}
	
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<Vendors> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Vendors record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				 put("vendorId", record.getVendorId());
	             put("personId", record.getPerson().getPersonId());
	             put("firstName", record.getPerson().getFirstName());
	             put("panNo", record.getPanNo());
	             put("cstNo", record.getCstNo());
	             put("stateTaxNo", record.getStateTaxNo());
	             put("serviceTaxNo", record.getServiceTaxNo());
	             put("serviceTaxNo", record.getStatus());
	             put("createdBy", record.getCreatedBy());
	             put("lastUpdatedBy", record.getLastUpdatedBy());
	             put("lastUpdatedDt", record.getLastUpdatedDt());
			}});
		}
		return response;
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	@Transactional(propagation = Propagation.REQUIRED)
	public List findVendorIdBasedOnpersonId(int personId) 
	{
		List id = entityManager.createNamedQuery("Vendors.findSingleVendorBasedOnPersonId").setParameter("personId", personId).getResultList();
		Iterator maxId = id.iterator();
		while (maxId.hasNext()) 
		{
			System.out.println("*************************^^^^^^^!!!!!!!maxId.next()\n" + maxId.next());
		}
		return id;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateVendorStatus(int vendorid, String vendorStatus) 
	{
		return entityManager.createNamedQuery("Vendors.updateVendorStatus").setParameter("vendorid", vendorid).setParameter("vendorStatus", vendorStatus).executeUpdate();
	}
	
	
	/*******************************************************************************************/
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getVendorResponse(int personId) {
		@SuppressWarnings("unchecked")
		List<Vendors> list =entityManager.createNamedQuery("VendorsDetails.findAll").setParameter("personId", personId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Vendors record : list) {
			response.add(new HashMap<String, Object>() {{
				put("vendorId", record.getVendorId());
				put("personId", record.getPersonId());
				put("panNo",record.getPanNo());
				put("cstNo", record.getCstNo());
				put("stateTaxNo", record.getStateTaxNo());
				put("serviceTaxNo", record.getServiceTaxNo());
				put("status", record.getStatus());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
			}});
		}
		return response;
	}
	/*******************************************************************************************/
}
