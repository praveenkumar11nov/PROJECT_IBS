package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.service.customerOccupancyManagement.FamilyPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.SessionData;

@Repository
public class FamilyPropertyServiceImpl extends GenericServiceImpl<FamilyProperty> implements FamilyPropertyService {

	@Autowired
	private PropertyService propertyService;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public FamilyProperty getFamilyPropertyObject(Map<String, Object> map, String type, FamilyProperty familyProperty,int personId)
	{
		//DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
		if(type == "update")
		{
			
			familyProperty.setFamilyPropertyId((Integer) map.get("familyPropertyId"));
			FamilyProperty familyPropertyRec = getFamilyPropertyBasedOnId((Integer)map.get("familyPropertyId"));
			familyProperty.setCreatedBy(familyPropertyRec.getCreatedBy());
			familyProperty.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			familyProperty.setStatus((String)map.get("status"));
			if(map.get("ownerId") instanceof java.lang.String){
				
				int ownerId = getOwnerIdBasedOnFamPropertyId((Integer) map.get("familyPropertyId"));
				familyProperty.setOwnerId(ownerId);
			}
			if(map.get("ownerId") instanceof java.lang.Integer){
				familyProperty.setOwnerId((Integer)map.get("ownerId"));
			}
			
		}
		else if (type == "save")
		{
			
			
			familyProperty.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			familyProperty.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			familyProperty.setStatus("Deactive");
			if(map.get("ownerId") instanceof java.lang.String){
			//familyProperty.setOwnerId((String)map.get("ownerId"));
		}
		if(map.get("ownerId") instanceof java.lang.Integer){
			familyProperty.setOwnerId((Integer)map.get("ownerId"));
		}
		}
		familyProperty.setPropertyId((Integer)map.get("propertyId"));
		int familyId = getFamilyIdBasedOnPersonId(personId);
		familyProperty.setFamilyId(familyId);
		familyProperty.setFpRelationship((String)map.get("fpRelationship"));
		familyProperty.setStartDate(ConvertDate.formattedDate((String)map.get("startDate")));
		familyProperty.setEndDate(ConvertDate.formattedDate((String)map.get("endDate")));
		
		return familyProperty;
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getOwnerIdBasedOnFamPropertyId(int famPropertyId) {
		List<Integer> ownerId =  entityManager.createNamedQuery("FamilyProperty.getOwnerIdBasedOnFamPropertyId",
				Integer.class)
				.setParameter("famPropertyId", famPropertyId)
				.getResultList();
		Iterator<Integer> it=ownerId.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public FamilyProperty getFamilyPropertyBasedOnId(int familyPropertyId) {
		
		return entityManager.createNamedQuery("FamilyProperty.getFamilyPropertyBasedOnId",FamilyProperty.class)
		.setParameter("familyPropertyId", familyPropertyId)		
		.getSingleResult();
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS) 
	 public int getFamilyIdBasedOnPersonId(int personId) 
	 {
	  return (Integer) entityManager.createNamedQuery("Family.getFamilyIdBasedOnPersonId")
	  .setParameter("personId", personId)
	  .getSingleResult();
	 }
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FamilyProperty> findAllFamilyPropertyBasedOnPersonID(int personId) {
		
		
		 return  entityManager.createNamedQuery("FamilyProperty.getAllPropertyOnPersonId",FamilyProperty.class)
				  .setParameter("personId", personId).getResultList();
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getProprtyIdBasedOnPropertyNo(int propertyNo) {
		List<Integer> proprtyId =  entityManager.createNamedQuery("Property.getProprtyIdBasedOnPropertyNo",
				Integer.class)
				.setParameter("propertyNo", propertyNo)
				.getResultList();
		Iterator<Integer> it=proprtyId.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FamilyProperty> findAll() {
		
		 return  entityManager.createNamedQuery("FamilyProperty.findAll",FamilyProperty.class)
				  .getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getPropertyNoBasedOnOwners() {
		 return  entityManager.createNamedQuery("FamilyProperty.getPropertyNoBasedOnOwners")
				  .getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getProprtyIdBasedOnFamPropertyId(int famPropertyId) {
		List<Integer> propertyId =  entityManager.createNamedQuery("FamilyProperty.getProprtyIdBasedOnFamPropertyId",
				Integer.class)
				.setParameter("famPropertyId", famPropertyId)
				.getResultList();
		Iterator<Integer> it=propertyId.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
	}
}
