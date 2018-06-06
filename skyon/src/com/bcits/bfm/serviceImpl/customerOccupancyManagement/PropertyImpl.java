package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Blob;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class PropertyImpl extends GenericServiceImpl<Property> implements
		PropertyService {
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Property> findAllforParking() {
		try {
			return entityManager.createNamedQuery("Property.findAllforParking").getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getIDProperty(String propertyName){
		
		return entityManager.createNamedQuery("Property.findpropertyId").setParameter("propertyName", propertyName).getFirstResult();
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Property> findAll() {

		List<Property> list_property = null;
		try {
			list_property = entityManager.createNamedQuery("Property.findAll").getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
		
		return list_property;
		//return getAllPropetyList(list_property);
	}
	
	
	@SuppressWarnings({ "serial", "unused", "unchecked", "rawtypes" })
	public List getAllPropetyList(List<Property> propertyList) 
	{
		
		List<Map<String, Object>> allPropertyDetailsList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> propertyMap =  new ArrayList<Map<String, Object>>();    

		for (final Property property : propertyList) 
		{
			propertyMap.add(new HashMap<String, Object>() {{    
			put("propertyId", property.getPropertyId());	
			put("projectId", property.getProject().getProjectId());
			put("projectName", property.getProject().getProjectName());
			put("blocks", property.getBlocks().getBlockId());
			put("blockName", property.getBlocks().getBlockName());
			put("propertyType", property.getPropertyType());
			put("areaType", property.getAreaType());
			put("area", property.getArea());
			put("property_No", property.getProperty_No());
			put("property_Floor", property.getProperty_Floor());
			put("status",property.getStatus());
			put("tenancyHandoverDate",property.getTenancyHandoverDate());
			put("propertyBillable",property.getPropertyBillable());
			put("no_of_ParkingSlots",property.getNo_of_ParkingSlots());
			put("createdBy", property.getCreatedBy());
			put("lastUpdatedBy",property.getLastUpdatedBy());
			put("drGroupId",property.getDrGroupId());
			}});
			
		}
		
		
		return propertyMap;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Property> getAll() 
	{
		return entityManager.createNamedQuery("Property.findAll")
				.getResultList();
	}

	/******************************used by controller***************/
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getPropertyId(String property_No) {

		return entityManager.createNamedQuery("Property.findpropertyIdBasedOnPropertyNo",Integer.class)
				.setParameter("property_No", property_No).getResultList();
	}
	/******************************used by controller***************/
	/******************************************************************/
	
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String getProprtyNoBasedOnPropertyId(int propertyId) {
		List<String> proprtyNo =  entityManager.createNamedQuery("Property.getProprtyNoBasedOnPropertyId",
				String.class)
				.setParameter("propertyId", propertyId)
				.getResultList();
		
		Iterator it=proprtyNo.iterator();
		while(it.hasNext()){
			
			return  (String) it.next();
		}
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Property> getPropertyNo() {
		
		List<Property> details =  entityManager.createNamedQuery("Property.getPropertyNo",Property.class)
				.setParameter("owner", "Owner")
				.setParameter("tenant", "Tenant")
				.getResultList();
		
		return details;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Property> getPropertyNoBasedOnTenant() {
		
		List<Property> details =  entityManager.createNamedQuery("Property.getPropertyNoBasedOnTenant",Property.class)
				.setParameter("owner", "Owner")
				.setParameter("tenant", "Tenant")
				.getResultList();
		
		return details;
	}

	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Property> getPropertyNameForFilter(){
		
		return entityManager.createNamedQuery("Property.propertyNameForFilter").getResultList();
	}

	@Override
	public Property getBeanObject(Property property, String type,
			Map<String, Object> map)
	{
		// TODO Auto-generated method stub
		if(type.equalsIgnoreCase("save"))
		{
			//property.setArea(Integer.parseInt((String)map.get("area")));
			property.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			property.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		else
		{
			//property.setArea((Integer)map.get("area"));
			property.setCreatedBy((String)map.get("createdBy"));
			property.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		
		if(map.get("area") instanceof java.lang.String)
		{
			property.setArea(Integer.parseInt((String)map.get("area")));
		}
		else
		{
			property.setArea((Integer)map.get("area"));
		}
		
		property.setProjectId((Integer)map.get("projectId"));
		property.setPropertyType((String) map.get("propertyType"));
		property.setAreaType((String)map.get("areaType"));
		
		property.setProperty_No((String) map.get("property_No"));
		property.setProperty_Floor((Integer) map.get("property_Floor"));
		property.setStatus((String) map.get("status"));
		String dt = (String) map.get("tenancyHandoverDate");// 2013-11-12T08:06:58.838Z
		if(property.getStatus().equalsIgnoreCase("Sold"))
		{	
			
			try {
				property.setTenancyHandoverDate(dateTimeCalender.kendoDateIssue((String) map.get("tenancyHandoverDate")));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			property.setTenancyHandoverDate(null);
		}
		property.setNo_of_ParkingSlots((Integer) map.get("no_of_ParkingSlots"));
		property.setPropertyBillable((String) map.get("propertyBillable"));
		return property;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getFamilyMembersBasedOnPersonId(int personId) 
	{
		List<?> list =  entityManager.createNamedQuery("Property.getFamilyMembersBasedOnPersonId").setParameter("personId", personId).getResultList();
		
		List<Map<String, Object>> familyMembersList = new ArrayList<Map<String, Object>>();

		Map<String, Object> detailsMap = null;

		for (Iterator i = list.iterator(); i.hasNext();) 
		{
			detailsMap = new HashMap<String, Object>();

			final Object[] values = (Object[]) i.next();
			int fpersonId = (Integer)values[0];
			String mobileNumber = "";
			@SuppressWarnings("unchecked")
			List<Contact> contactList  = entityManager.createNamedQuery("Property.getPersonContactDetails").setParameter("personId", fpersonId).getResultList();
			if(contactList.size() > 0 && StringUtils.equalsIgnoreCase(contactList.get(0).getContactPrimary(), "Yes"))
			{
				for(Contact c :contactList)
				{
					mobileNumber = mobileNumber +"/"+ c.getContactContent();
				}
				
			}
			mobileNumber = mobileNumber.startsWith("/") ? mobileNumber.substring(1) : mobileNumber;
			if(mobileNumber == "")
			{
				mobileNumber = "No contact details";
			}
			String personName = "";
			personName = (String)values[1];
			if(((String)values[4]) != null)
			   {
			    personName = personName.concat(" ");
			    personName = personName.concat((String)values[4]);
			   }
			  if(((String)values[2]) != null)
			   {
			    personName = personName.concat(" ");
			    personName = personName.concat((String)values[2]);
			   }
			  
			detailsMap.put("personId", (Integer)values[0]);
			detailsMap.put("personName", personName);
			//detailsMap.put("lastName", (String)values[2]);
			detailsMap.put("relationShip", (String)values[3]);
			detailsMap.put("contactContent", mobileNumber);
			familyMembersList.add(detailsMap);
		}	
		
		return familyMembersList;
	}

	@Override
	public String getPropertyNameBasedOnPropertyId(int propertyId) {
		List<String> propertyNames =  entityManager.createNamedQuery("Property.getPropertyNameBasedOnPropertyId",
				String.class)
				.setParameter("propertyId", propertyId )
				.getResultList();

		Iterator<String> it=propertyNames.iterator();
		while(it.hasNext()){

			return (String) it.next();
		}
		return null;
		
	}

	@Override
	public int getBlockIdBasedOnPropertyId(int propertyId) {
		List<Integer> propertyNames =  entityManager.createNamedQuery("Property.getBlockIdBasedOnPropertyId",
				Integer.class)
				.setParameter("propertyId", propertyId )
				.getResultList();

		Iterator<Integer> it=propertyNames.iterator();
		while(it.hasNext()){

			return (int) it.next();
		}
		return 0;
}

	@Override
	public void uploadPropertyOnId(int propertyId, Blob propertyDocument, String docType) {
		entityManager.createNamedQuery("Property.uploadPropertyOnId")
		.setParameter("propertyId", propertyId)
		.setParameter("propertyDocument", propertyDocument)
		.setParameter("docType", docType)
		.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> getPropertyListBasedOnPropertyNo(String propertyNo) {
		
		return entityManager.createNamedQuery("Property.getPropertyListBasedOnPropertyNo").setParameter("propertyNo", propertyNo ).getResultList();
	}

	@Override
	public List<?> getPossesionDate(int propertyId) {
	
		return entityManager.createNamedQuery("Property.getPossesionDate").setParameter("propertyId", propertyId ).getResultList();
	}

	@Override
	public List<Property>  findAllNotification() {
		List<Property> list_property = null;
		try {
			list_property = entityManager.createNamedQuery("Property.findAllNotification").getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
		
		return list_property;
	}
	
	
	@Override
	public Property getPropertyEntityBasedOnProID(int propertyId){
		Property property = null;
		try {
			property = (Property) entityManager.createNamedQuery("Property.getPropertyByPropertyID").setParameter("propertyId",propertyId).getSingleResult();
		} catch (RuntimeException re) {
			throw re;
		}
		
		return property;
	}
	
	
}
