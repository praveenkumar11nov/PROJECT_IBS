package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ConciergeVendors;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class ConciergeVendorServiceImpl extends GenericServiceImpl<ConciergeVendors> implements ConciergeVendorsService{

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ConciergeVendors getConciergeVendorsObject(Map<String, Object> map, String type, ConciergeVendors conciergeVendors ,int personId)
	{
		
		if (type == "save")
		{
			conciergeVendors.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			conciergeVendors.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			/*vendors.setStatus((String)map.get("status"));*/
			conciergeVendors.setStatus("Inactive");
		}
		conciergeVendors.setPersonId(personId);
		conciergeVendors.setCstNo((String)map.get("cstNo"));
		conciergeVendors.setStateTaxNo((String)map.get("stateTaxNo"));
		conciergeVendors.setServiceTaxNo((String)map.get("serviceTaxNo"));
		//conciergeVendors.setRequestcounter("counter");
		
		return conciergeVendors;
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public ConciergeVendors getCsVendorIdBasedOnPersonId(int personid) {
		
		return  entityManager.createNamedQuery("ConciergeVendors.getCsVendorIdBasedOnPersonId",ConciergeVendors.class)
				.setParameter("personid", personid)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int getPersonIdBasedOnVendorId(int vendorId) {
		 List<Integer> personId =  entityManager.createNamedQuery("ConciergeVendors.getPersonIdBasedOnVendorId",
					Integer.class)
					.setParameter("vendorId", vendorId)
					.getResultList();
			Iterator<Integer> it=personId.iterator();
			while(it.hasNext()){
				
				return (int) it.next();
			}
			return 0;
	}

	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getResponse(int personId) {
		@SuppressWarnings("unchecked")
		List<ConciergeVendors> list =entityManager.createNamedQuery("ConciergeVendors.findAll").setParameter("personId", personId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final ConciergeVendors record : list) {
			response.add(new HashMap<String, Object>() {{
				put("csvId", record.getCsvId());
				put("personId", record.getPersonId());
				if( record.getCstNo() == null ){
					put("cstNo", "None");
				}
				else
				put("cstNo", record.getCstNo());
				if( record.getStateTaxNo() == null ){
					put("stateTaxNo", "None");
				}
				else
				put("stateTaxNo", record.getStateTaxNo());
				if( record.getServiceTaxNo() == null ){
					put("serviceTaxNo","None");
				}
				else
				put("serviceTaxNo", record.getServiceTaxNo());
				put("status", record.getStatus());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
			}});
		}
		return response;
	}
	}
	
