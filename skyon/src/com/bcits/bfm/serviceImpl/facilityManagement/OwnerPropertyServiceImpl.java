package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class OwnerPropertyServiceImpl extends GenericServiceImpl<OwnerProperty> implements OwnerPropertyService{
	
	@Autowired
	private PropertyService propertyService;
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.facilityManagement.OwnerPropertyService#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<OwnerProperty> findAll() {
		try {
			final String queryString = "select model from OwnerProperty model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
		throw re;
		}
	}	
	
	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public OwnerProperty getOwnerPropertyObject(Map<String, Object> map, String type, OwnerProperty ownerProperty,int personId)
	{
		DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
		if(type == "update")
		{
			ownerProperty.setOwnerPropertyId((Integer) map.get("ownerPropertyId"));
			ownerProperty.setCreatedBy((String) map.get("createdBy"));
			//ownerProperty.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			
			OwnerProperty ownerProperty1 = find(ownerProperty.getOwnerPropertyId());
			
			Property property = propertyService.find(ownerProperty1.getPropertyId());
			
			/*if((map.get("propertyId") instanceof java.lang.Integer) && ((map.get("propertyNo") instanceof java.lang.String)))
			{
				String propertyNo = (String)map.get("propertyNo");
				if((propertyNo.length() > 0) && (!(propertyNo.equalsIgnoreCase("Select"))))
				{
					if(((ownerProperty1.getPropertyId()) != ((Integer)map.get("propertyId"))) && (String.valueOf((property.getProperty_No())).equalsIgnoreCase((String)map.get("propertyNo"))))
					{
						BfmLogger.logger.info("Property no not selected");
					}

					else
					{
						//ownerProperty.setPropertyNo((String)map.get("propertyNo"));
						ownerProperty.setPropertyId((Integer)map.get("propertyId"));
					}
				}	
			}*/
		}
		else if (type == "save")
		{
			ownerProperty.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			ownerProperty.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			ownerProperty.setStatus("Deactive");
		}
		ownerProperty.setPropertyId((Integer)map.get("propertyId"));
		ownerProperty.setPrimaryOwner((String)map.get("primaryOwner"));
		//ownerProperty.setPropertyId((Integer) map.get("propertyId"));
		ownerProperty.setVisitorRegReq((String)map.get("visitorRegReq"));
		if((String)map.get("propertyAquiredDate")!=null)
		{
			java.sql.Date d=dateTimeCalender.getDate1((String)map.get("propertyAquiredDate"));
			
			Calendar c=Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, 1);
			ownerProperty.setPropertyAquiredDate(new java.sql.Date(c.getTimeInMillis()));
			
		}
		//if((String)map.get("propertyRelingDate")!=null)
		//	ownerProperty.setPropertyRelingDate(dateTimeCalender.getDate1((String)map.get("propertyRelingDate")));
		ownerProperty.setStatus((String)map.get("status"));
		ownerProperty.setResidential((String)map.get("residential"));
		int ownerId = getOwnerIdBasedOnPersonId(personId);
		ownerProperty.setOwnerId(ownerId);
		ownerProperty.setDrGroupId((long)personId);
		ownerProperty.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		return ownerProperty;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS) 
	 public Integer getOwnerIdBasedOnPersonId(int personId) 
	 {
	  return (Integer) entityManager.createNamedQuery("Owner.getOwnerIdOnPersonId")
	  .setParameter("personId", personId)
	  .getSingleResult();
	 }

	@Override
	public List<?> findAllOwnerPropertyBasedOnPersonID(int personId) {
		// TODO Auto-generated method stub
		 return  getAllPropertyofOwner(entityManager.createNamedQuery("OwnerProperty.getAllPropertyOnPersonId")
				  .setParameter("personId", personId).getResultList());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS) 
	public int getPropertyOwnerShipStatus(int propertyId) 
	{
		// TODO Auto-generated method stub
		return ((Number)entityManager.createNamedQuery("OwnerProperty.getPropertyOwnerShipStatus")
				  .setParameter("propertyId", propertyId).getSingleResult()).intValue();
	}

	@Override
	public int checkPropertyAssigned(int proptertyId, int ownerId) 
	{
		// TODO Auto-generated method stub
		return ((Number)entityManager.createNamedQuery("OwnerProperty.checkPropertyAssigned")
				  .setParameter("propertyId", proptertyId).setParameter("ownerId", ownerId).getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OwnerProperty> getOwnerType(Property propertyId) {
		return entityManager.createNamedQuery("OwnerProperty.getOwnerType").setParameter("property", propertyId).getResultList();		
	}
	
	 @SuppressWarnings({ "unchecked", "serial" })
	List<?> getAllPropertyofOwner(List<?> list)
	{
		 List<Map<String, Object>> tenantProperty =  new ArrayList<Map<String, Object>>(); 
			for (final OwnerProperty record : (List<OwnerProperty>)list ) 
	        {
				tenantProperty.add(new HashMap<String, Object>() {{
					put("ownerPropertyId" ,record.getOwnerPropertyId());
					put("propertyId" ,record.getPropertyId());
					put("primaryOwner" ,record.getPrimaryOwner());
					put("propertyNo" ,propertyService.getProprtyNoBasedOnPropertyId(record.getPropertyId()));
					put("status" ,record.getStatus());
					put("visitorRegReq",record.getVisitorRegReq());
						if(record.getProperty().getTenancyHandoverDate()!=null){
							put("propertyPossessionDate",record.getProperty().getTenancyHandoverDate());
						}
					
					put("propertyAquiredDate",record.getPropertyAquiredDate());
					put("propertyRelingDate" , record.getPropertyRelingDate());
					put("createdBy" , record.getCreatedBy());
					put("residential" , record.getResidential());
					put("blockId",record.getProperty().getBlockId());
					put("blockName",record.getProperty().getBlocks().getBlockName());
				}});
	        }
			
			return tenantProperty;
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OwnerProperty> getOwnerPropertyBasedOnPropertyIdAndOwnerId(
			int propertyId) {
		
		return entityManager.createNamedQuery("OwnerProperty.getOwnerPropertyBasedOnPropertyIdAndOwnerId").setParameter("propertyId", propertyId).getResultList();		

	}

	@Override
	public List<Object[]> getData() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("OwnerProperty.findData").getResultList();
	}
	
	
	
}