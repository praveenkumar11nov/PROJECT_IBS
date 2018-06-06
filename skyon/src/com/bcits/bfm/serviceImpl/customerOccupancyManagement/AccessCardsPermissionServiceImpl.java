package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AccessCardsPermission;
import com.bcits.bfm.model.Area;
import com.bcits.bfm.model.Personnel;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardsPermissionService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class AccessCardsPermissionServiceImpl extends GenericServiceImpl<AccessCardsPermission> implements AccessCardsPermissionService 
{
	@Autowired
	PersonService personService;

	@PersistenceContext(unitName="MSSQLDataSourceAccessCards")
	private EntityManager msSQLentityManager;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessCardsPermission> findAll() 
	{
		return entityManager.createNamedQuery("AccessCardsPermissions.findAll").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findOnacId(int acId) {
		List<AccessCardsPermission> permissionList =  entityManager.createNamedQuery("AccessCardsPermissions.findOnacId").setParameter("acId", acId).getResultList();
		return getDetailedPermissionList(permissionList,acId);
	}

	@SuppressWarnings({ "unchecked", "serial" })
	private List<?> getDetailedPermissionList(List<AccessCardsPermission> permissionList,Integer acId) 
	{

		List<Map<String, Object>> permissionsMap =  new ArrayList<Map<String, Object>>();    
         
		if(permissionList.isEmpty())
		{
		final List<Personnel> personList=msSQLentityManager.createQuery("SELECT  p FROM Personnel p WHERE p.objectIdLo="+acId).getResultList();
		if(personList.size()>0 && !personList.isEmpty())
		{
	    permissionsMap.add(new HashMap<String, Object>(){
	    	{
	    		List<Area> repository=msSQLentityManager.createQuery("SELECT a FROM Area a WHERE a.objectIdLo="+personList.get(0).getValueLo()).getResultList();
	    		
	    		if(!(repository.isEmpty())){
	    		put("acPointName",repository.get(0).getDescription());
	    		}
	    		if(personList.get(0).getState()==1)
	    		put("status","Active");
	    		else
	    		put("status","InActive");
	    		
	    		 java.util.Date acpStartDate = personList.get(0).getActivationDate();
			     java.sql.Date acpStartDateSql = new java.sql.Date(acpStartDate.getTime());
			     put("acpStartDate", acpStartDateSql);
				
			     java.util.Date acpEndDateUtil = personList.get(0).getExpirationDate();
			     java.sql.Date acpEndDateSql = new java.sql.Date(acpEndDateUtil.getTime());
			     put("acpEndDate", acpEndDateSql);
	    		

	    	}
	    });
		}
		}
		
		
		for (final AccessCardsPermission record : permissionList) 
		{
			permissionsMap.add(new HashMap<String, Object>() {{    
			put("acpId", record.getAcpId());
			put("acId", record.getAcId());
			put("arId",record.getArId());
			
			java.util.Date acpStartDate = record.getAcpStartDate();
		     java.sql.Date acpStartDateSql = new java.sql.Date(acpStartDate.getTime());
		     put("acpStartDate", acpStartDateSql);
			
		     java.util.Date acpEndDateUtil = record.getAcpEndDate();
		     java.sql.Date acpEndDateSql = new java.sql.Date(acpEndDateUtil.getTime());
		     put("acpEndDate", acpEndDateSql);
			
			//put("acpStartDate",record.getAcpStartDate());
			//put("acpEndDate",record.getAcpEndDate());
			put("acPointName",record.getAr().getApName());
			put("status",record.getStatus());
			put("createdBy",record.getCreatedBy());
			put("lastUpdatedBy",record.getLastUpdatedBy());
			}});
		}
		return permissionsMap;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public AccessCardsPermission getaccessCardsPermisssionObject(
			Map<String, Object> map, String type,
			AccessCardsPermission accessCardsPermisssion) 
	{
		if(type == "update")
		{
			accessCardsPermisssion.setAcpId((Integer)map.get("acpId"));
			accessCardsPermisssion.setCreatedBy((String)map.get("createdBy"));
			//accessCardsPermisssion.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		else if (type == "save")
		{
			//accessCardsPermisssion.setCreatedBy((String) SessionData.getUserDetails().get("userID"));	
		}
		accessCardsPermisssion.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		accessCardsPermisssion.setAcId((Integer)map.get("acId"));
		accessCardsPermisssion.setAcpStartDate(dateTimeCalender.getDate1((String) map.get("acpStartDate")));
		accessCardsPermisssion.setAcpEndDate(dateTimeCalender.getDate1((String)map.get("acpEndDate")));
		
		accessCardsPermisssion.setLastUpdateDt(new Timestamp(new Date().getTime()));
		accessCardsPermisssion.setArId((Integer)map.get("arId"));
		accessCardsPermisssion.setStatus((String)map.get("status"));
		return accessCardsPermisssion;
	}

}
