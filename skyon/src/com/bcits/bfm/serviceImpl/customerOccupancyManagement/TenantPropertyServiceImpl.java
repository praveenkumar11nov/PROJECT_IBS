package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


//import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class TenantPropertyServiceImpl extends GenericServiceImpl<TenantProperty> implements TenantPropertyService {

	@Autowired
	private PropertyService propertyService;	
	@Autowired
	private TenantSevice tenantService;		
	@Autowired
	private PropertyService propertySevice;	
	
	/*Used By Parking Management*/
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<TenantProperty> findAll() {
		try {
			final String queryString = "select model from TenantProperty model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findAllTenantPropertyBasedOnPersonID(int personId) {
		
		return  entityManager.createNamedQuery("TenantProperty.getAllPropertyOnPersonId",TenantProperty.class)
				  .setParameter("personId", personId).getResultList();
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public TenantProperty getTenantPropertyBasedOnId(int tenantPropertyId) {
		
		return entityManager.createNamedQuery("TenantProperty.getTenantPropertyBasedOnId",TenantProperty.class)
		.setParameter("tenantPropertyId", tenantPropertyId)		
		.getSingleResult();
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public TenantProperty getTenantPropertyObject(Map<String, Object> map, HttpServletRequest request ,String type, TenantProperty tenantProperty,int personId)
	{
		//DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		if(type == "update")
		{
			tenantProperty.setTenantPropertyId(Integer.parseInt(map.get("tenantPropertyId").toString()));
			tenantProperty.setGroupId((Integer) map.get("groupId"));
			TenantProperty tenantPropertyRec = getTenantPropertyBasedOnId((Integer)map.get("tenantPropertyId"));
			tenantProperty.setCreatedBy(tenantPropertyRec.getCreatedBy());
			tenantProperty.setStatus((String)map.get("status"));
			
		}
		else if (type == "save")
		{
			
			tenantProperty.setCreatedBy(userName);
			tenantProperty.setStatus("Deactive");
			
			
		}
		tenantProperty.setPropertyId((Integer)map.get("propertyId"));
		int tenantId = getTenantIdBasedOnPersonId(personId);
		tenantProperty.setTenantId(tenantService.find(tenantId));
		//tenantProperty.setStartDate(dateTimeCalender.getDate1((String)map.get("startDate")));
		tenantProperty.setStartDate(ConvertDate.formattedDate((String)map.get("startDate")));
		tenantProperty.setEndDate(ConvertDate.formattedDate((String)map.get("endDate")));
		//tenantProperty.setEndDate(dateTimeCalender.getDate1((String)map.get("endDate")));
		tenantProperty.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		tenantProperty.setLastUpdatedBy(userName);
		return tenantProperty;
		
		
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
	 public int getTenantIdBasedOnPersonId(int personId) 
	 {
	  return (Integer) entityManager.createNamedQuery("Tenant.getTenantIdBasedOnPersonId")
	  .setParameter("personId", personId)
	  .getSingleResult();
	 }

	@SuppressWarnings("unchecked")
	@Override
	public List<TenantProperty> findTenantPropertyBasedOnProperty(Property record) {
		return entityManager.createNamedQuery("TenantProperty.findTenantPropertyBasedOnProperty").setParameter("property", record.getPropertyId()).getResultList();	
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getProprtyIdBasedOntenPropertyId(int tenId) {
		List<Integer> tentId =  entityManager.createNamedQuery("tenProperty.getProprtyIdBasedOntenPropertyId",
				Integer.class)
				.setParameter("tenId", tenId)
				.getResultList();
		Iterator<Integer> it=tentId.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
	}

}
