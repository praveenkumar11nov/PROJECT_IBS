package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.DomesticProperty;
import com.bcits.bfm.service.customerOccupancyManagement.DomesticPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.SessionData;

@Repository
public class DomesticPropertyServiceImpl extends GenericServiceImpl<DomesticProperty> implements DomesticPropertyService{

	@Autowired
	private PropertyService propertyService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findAllDomesticPropertyBasedOnPersonID(int personId) {
		
		return  entityManager.createNamedQuery("DomesticProperty.getAllPropertyOnPersonId",DomesticProperty.class)
				  .setParameter("personId", personId).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public DomesticProperty getDomesticPropertyObject(Map<String, Object> map,
			String type, DomesticProperty domesticProperty, int personId) {
		
				//DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
			if(type == "update")
			{
				
				domesticProperty.setDomasticPropertyId((Integer) map.get("domasticPropertyId"));
				/*domesticProperty.setPropertyId(getProprtyIdBasedOnPropertyNo((Integer)map.get("propertyNo")));*/
				DomesticProperty domesticPropertyRecord = getDomesticPropertyInstanceById((Integer) map.get("domasticPropertyId"));
				domesticProperty.setCreatedBy(domesticPropertyRecord.getCreatedBy());
				domesticProperty.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				domesticProperty.setStatus((String)map.get("status"));
				
				/*DomesticProperty domesticProperty1 = find(domesticProperty.getDomasticPropertyId());
				
				Property property = propertyService.find(domesticProperty1.getPropertyId());
				
				if((map.get("propertyId") instanceof java.lang.Integer) && ((map.get("propertyNo") instanceof java.lang.String)))
				{
					String propertyNo = (String)map.get("propertyNo");
					if((propertyNo.length() > 0) && (!(propertyNo.equalsIgnoreCase("Select"))))
					{
						if(((domesticProperty1.getPropertyId()) != ((Integer)map.get("propertyId"))) && (String.valueOf((property.getProperty_No())).equalsIgnoreCase((String)map.get("propertyNo"))))
						{
							BfmLogger.logger.info("Property no not selected");
						}

						else
						{
							domesticProperty.setPropertyNo((String)map.get("propertyNo"));
							domesticProperty.setPropertyId((Integer)map.get("propertyId"));
						}
					}	
				}*/
			}
			
			else if (type == "save")
			{
				
				domesticProperty.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				domesticProperty.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				domesticProperty.setStatus("Deactive");
				
			}
			
			domesticProperty.setPropertyId((Integer)map.get("propertyId"));
			int domesticId = getDometsicIdBasedOnPersonId(personId);
			
			domesticProperty.setDomasticId(domesticId);
			domesticProperty.setWorkNature((String)map.get("workNature"));
			domesticProperty.setStartDate(ConvertDate.formattedDate((String)map.get("startDate")));
			//domesticProperty.setStartDate(dateTimeCalender.getDate1((String)map.get("startDate")));
			domesticProperty.setEndDate(ConvertDate.formattedDate((String)map.get("endDate")));
			domesticProperty.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			
			return domesticProperty;
			
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
		public int getDometsicIdBasedOnPersonId(int personId) {
			return  (Integer)entityManager.createNamedQuery("Domestic.getDometsicIdBasedOnPersonId",
					Integer.class)
					.setParameter("personId", personId)
					  .getSingleResult();
		}

		
		@Override
		@Transactional(propagation = Propagation.SUPPORTS)
		public DomesticProperty getDomesticPropertyInstanceById(int domesticPropertyId) {
			return entityManager.createNamedQuery("DomesticProperty.getDomesticPropertyInstanceById",
					DomesticProperty.class)
					.setParameter("domesticPropertyId", domesticPropertyId)
					.getSingleResult();
		}

		@Override
		@Transactional(propagation = Propagation.SUPPORTS) 
		 public int getDomesticIdBasedOnPersonId(int personId) 
		 {
		  return (Integer) entityManager.createNamedQuery("Domestic.getDomesticIdBasedOnPersonId")
		  .setParameter("personId", personId)
		  .getSingleResult();
		 }
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation=Propagation.REQUIRED)
		public List<DomesticProperty> findAll() {
			try {
				final String queryString = "select model from DomesticProperty model";
				Query query = entityManager.createQuery(queryString);
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}
		}

		
		@Override
		@Transactional(propagation = Propagation.SUPPORTS)
		public int getProprtyIdBasedOnDomesticPropertyId(int domesticPropertyId) {
			List<Integer> propertyId =  entityManager.createNamedQuery("DomesticProperty.getProprtyIdBasedOnDomesticPropertyId",
					Integer.class)
					.setParameter("domesticPropertyId", domesticPropertyId)
					.getResultList();
			Iterator<Integer> it=propertyId.iterator();
			while(it.hasNext()){
				
				return (int) it.next();
			}
			return 0;
		}
}
 