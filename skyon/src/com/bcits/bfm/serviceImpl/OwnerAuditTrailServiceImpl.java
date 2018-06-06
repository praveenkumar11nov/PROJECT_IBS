package com.bcits.bfm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.OwnerAuditTrail;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.OwnerAuditTrailService;

@Repository
public class OwnerAuditTrailServiceImpl extends GenericServiceImpl<OwnerAuditTrail> implements OwnerAuditTrailService {

	@Override
	public Person getPersonObjectBasedOnID(int personId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Person.getPersonObjectBasedOnIDNew",Person.class).setParameter("personId", personId).getSingleResult();
	}

	@Override
	public List<?> getAllData() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		List<OwnerAuditTrail> data=entityManager.createNamedQuery("OwnerAudit.findAllData").getResultList();
		
		for(final OwnerAuditTrail auditTrail:data)
		{
			result.add(new HashMap<String, Object>(){
				{
				
					put("id",auditTrail.getOwnerId());
					put("ownerName",auditTrail.getOwnerName());
					put("owner_current",auditTrail.getOwner_current());
					put("owner_previous",auditTrail.getOwner_previous());
					put("updated_field",auditTrail.getUpdated_field());
					put("lastUpdatedBy",auditTrail.getLastUpdatedBy());
					put("lastUpdatedDate",auditTrail.getLastUpdatedDate());
					
				}
			});
			
		}
		return result;
	}

	@Override
	public List<OwnerAuditTrail> getFiltterdata() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("OwnerAllFiltern.readAllDataFilter").getResultList();
	}

	

}
