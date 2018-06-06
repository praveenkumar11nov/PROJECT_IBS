package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.TicketAssignEntity;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.helpDeskManagement.TicketAssignService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TicketAssignServiceImpl extends GenericServiceImpl<TicketAssignEntity> implements TicketAssignService  {
	
	@Autowired
	private PropertyService propertyService;

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
    @SuppressWarnings("unchecked")
	public List<TicketAssignEntity> findAllById(int ticketId) {
		return getAllDetailsList(entityManager.createNamedQuery("TicketAssignEntity.findAllById").setParameter("ticketId", ticketId).getResultList());
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketAssignEntity> findAllByAscOrder(int ticketId) {
		return selectedList(entityManager.createNamedQuery("TicketAssignEntity.findAllByAscOrder",TicketAssignEntity.class).setParameter("ticketId", ticketId).getResultList());
	}
	
	private List<TicketAssignEntity> selectedList(List<TicketAssignEntity> list) 
	{
		List<TicketAssignEntity> listNew = new ArrayList<TicketAssignEntity>();
		for (Iterator<TicketAssignEntity> iterator = list.iterator(); iterator.hasNext();) {
			TicketAssignEntity ticketAssignEntity = (TicketAssignEntity) iterator.next();
			ticketAssignEntity.setOpenNewTicketEntity(null);
			ticketAssignEntity.setUsersObj(null);
			listNew.add(ticketAssignEntity);
		}
		return listNew;
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> ticketEntities) 
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> assignTicketDetailsMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
			final Object[] values = (Object[]) iterator.next();
			assignTicketDetailsMap = new HashMap<String, Object>();   				
			assignTicketDetailsMap.put("personId",(Integer)values[10]);				
			String userName = "";
			userName = userName.concat((String)values[8]);
			if((String)values[9] != null)
			{
				userName = userName.concat(" ");
				userName = userName.concat((String)values[9]);
			}			
			assignTicketDetailsMap.put("userName",userName);
			assignTicketDetailsMap.put("urId", (Integer)values[2]);	
			assignTicketDetailsMap.put("assignId", (Integer)values[0]);
			assignTicketDetailsMap.put("ticketId", (Integer)values[1]);
			assignTicketDetailsMap.put("assignComments",(String)values[3]);
			assignTicketDetailsMap.put("assignDate", (Timestamp)values[4]);			
			assignTicketDetailsMap.put("createdBy", (String)values[5]);
			assignTicketDetailsMap.put("lastUpdatedBy", (String)values[6]);
			assignTicketDetailsMap.put("lastUpdatedDT", (Timestamp)values[7]);
			
			result.add(assignTicketDetailsMap);
		     }
	     return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Users> findUsers(int deptId) {
		return getAllUserDetailsList(entityManager.createNamedQuery("TicketAssignEntity.findUsers").setParameter("deptId", deptId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllUserDetailsList(List<?> deptEntities) {
		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> userstMap = null;
		 for (Iterator<?> iterator = deptEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				userstMap = new HashMap<String, Object>();													
				String str="";
				if((String)values[6] !=null){
					str=(String)values[6];
				}				
				userstMap.put("dept_Id", (Integer)values[0]);
				userstMap.put("personId", (Integer)values[1]);
				userstMap.put("urId", (Integer)values[2]);
				userstMap.put("urLoginName", (String)values[3]);
				userstMap.put("userName", (String)values[5]+str);
				userstMap.put("designation",(String)values[4]);
			
			result.add(userstMap);
	     }
     return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketAssignEntity> findAllData() {
		return getAllEscalatedTicketDetails(entityManager.createNamedQuery("TicketAssignEntity.findAllData").getResultList());
	}
	
	@Override
	public List<?> findAllEscalatedReports() {
		return getAllEscalatedTicketDetails(entityManager.createNamedQuery("TicketAssignEntity.findAllData").getResultList());

	}
	
	@SuppressWarnings("rawtypes")
	private List getAllEscalatedTicketDetails(List<?> ticketEntities) 
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> escalationTicketDetailsMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
			final Object[] values = (Object[]) iterator.next();
			escalationTicketDetailsMap = new HashMap<String, Object>();  
			escalationTicketDetailsMap.put("personId",(Integer)values[10]);				
			String userName = "";
			userName = userName.concat((String)values[8]);
			if((String)values[9] != null)
			{
				userName = userName.concat(" ");
				userName = userName.concat((String)values[9]);
			}			
			String personName = "";
			personName = personName.concat((String)values[12]);
			if((String)values[13] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[13]);
			}			
			escalationTicketDetailsMap.put("personName",personName);
			escalationTicketDetailsMap.put("ticketNumber", (String)values[11]);	
			escalationTicketDetailsMap.put("priorityLevel", (String)values[14]);
			escalationTicketDetailsMap.put("issueSubject", (String)values[15]);
			escalationTicketDetailsMap.put("issueDetails",(String)values[16]);
			escalationTicketDetailsMap.put("ticketStatus", (String)values[17]);			
			escalationTicketDetailsMap.put("ticketCreatedDate", (Timestamp)values[18]);			
			escalationTicketDetailsMap.put("userName",userName);
			escalationTicketDetailsMap.put("urId", (Integer)values[2]);	
			escalationTicketDetailsMap.put("assignId", (Integer)values[0]);
			escalationTicketDetailsMap.put("ticketId", (Integer)values[1]);
			escalationTicketDetailsMap.put("assignComments",(String)values[3]);
			escalationTicketDetailsMap.put("assignDate", (Timestamp)values[4]);	
			escalationTicketDetailsMap.put("levelOFSLA",(Integer)values[19]);
			
			if(values[20]!=null && ((String)values[20]).trim().equalsIgnoreCase("Common Area")){
				escalationTicketDetailsMap.put("propertyId", "");	
				escalationTicketDetailsMap.put("property_No","");
			}else{
				escalationTicketDetailsMap.put("propertyId", (Integer)values[21]);	
				Property property = propertyService.find((Integer)values[21]);
				escalationTicketDetailsMap.put("property_No",property.getProperty_No());
			}
			
			result.add(escalationTicketDetailsMap);
		     }
	     return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAll() {
		return entityManager.createNamedQuery("TicketAssignEntity.findAllData").getResultList();
	}


	
}
