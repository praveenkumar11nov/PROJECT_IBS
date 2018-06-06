package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.TicketDeptTransferEntity;
import com.bcits.bfm.service.helpDeskManagement.OpenNewTicketService;
import com.bcits.bfm.service.helpDeskManagement.TicketDeptTransferService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TicketDeptTransferServiceImpl extends GenericServiceImpl<TicketDeptTransferEntity> implements TicketDeptTransferService  {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private OpenNewTicketService openNewTicketService;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketDeptTransferEntity> findAllById(int ticketId) {
		return getAllDetailsList(entityManager.createNamedQuery("TicketDeptTransferEntity.findAllById").setParameter("ticketId", ticketId).getResultList());
	}
	
	private List<TicketDeptTransferEntity> selectedList(List<TicketDeptTransferEntity> list) 
	{
		List<TicketDeptTransferEntity> listNew = new ArrayList<TicketDeptTransferEntity>();
		for (Iterator<TicketDeptTransferEntity> iterator = list.iterator(); iterator.hasNext();) {
			TicketDeptTransferEntity ticketDeptTransferEntity = (TicketDeptTransferEntity) iterator.next();
			ticketDeptTransferEntity.setOpenNewTicketEntity(null);
			ticketDeptTransferEntity.setDepartmentObj(null);
			ticketDeptTransferEntity.setPrevDepartmentObj(null);
			listNew.add(ticketDeptTransferEntity);
		}
		return listNew;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketDeptTransferEntity> findAllData() {
		return getAllDetailsList(entityManager.createNamedQuery("TicketDeptTransferEntity.findAllData").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> deptEntities) {
		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> deptMap = null;
		 for (Iterator<?> iterator = deptEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				deptMap = new HashMap<String, Object>();	
				
				if(values[19]!=null && ((String)values[19]).trim().equalsIgnoreCase("Common Area")){
					
					deptMap.put("personId","");				
					deptMap.put("personName","");
				}else{
					deptMap.put("personId",(Integer)values[20]);
					
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId((Integer)values[20]);
					 
					 String personName = "";
					 personName = personName.concat((String)personNameData[0]);
					 if(personNameData[1]!= null){
					 	personName = personName.concat(" ");
						personName = personName.concat((String)personNameData[1]);
					 }
						
					 deptMap.put("personName",personName);					
				}
				
				deptMap.put("deptTransId",(Integer)values[0]);
				deptMap.put("ticketId",(Integer)values[1]);
				deptMap.put("dept_Id",(Integer)values[2]);
				deptMap.put("comments", (String)values[3]);
				deptMap.put("prevDeptId",(Integer)values[4]);
				deptMap.put("transferDate",(Timestamp)values[5]);
				deptMap.put("createdBy",(String)values[6]);
				deptMap.put("lastUpdatedBy",(String)values[7]);
				deptMap.put("lastUpdatedDT",(Timestamp)values[8]);
				deptMap.put("dept_Name",(String)values[9]);
				deptMap.put("pevDept_Name",(String)values[10]);
				deptMap.put("ticketNumber", (String)values[11]);	
				deptMap.put("priorityLevel",(String)values[12]);
				deptMap.put("issueSubject", (String)values[13]);	
				deptMap.put("issueDetails",(String)values[14]);
				deptMap.put("ticketStatus",(String)values[15]);
				deptMap.put("ticketCreatedDate", (Timestamp)values[16]);
				deptMap.put("topicId", (Integer)values[17]);
				deptMap.put("topicName", (String)values[18]);
				deptMap.put("typeOfTicket", (String)values[19]);
			
			result.add(deptMap);
	     }
     return result;
	}

	@Override
	public void deptIdUpdate(int ticketId, int dept_Id) {
		entityManager.createNamedQuery("TicketDeptTransferEntity.deptIdUpdate").setParameter("dept_Id",dept_Id).setParameter("ticketId", ticketId).executeUpdate();
	}
	
	@Override
	public void helpTopicUpdate(int ticketId, int topicId) {
		entityManager.createNamedQuery("TicketDeptTransferEntity.helpTopicUpdate").setParameter("topicId",topicId).setParameter("ticketId", ticketId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketDeptTransferEntity> findAllByAscOrder(int ticketId) {
		return selectedList(entityManager.createNamedQuery("TicketDeptTransferEntity.findAllByAscOrder").setParameter("ticketId", ticketId).getResultList());
	}
	
	@Override
	public void updateTicketDeptAcceptanceStatus(int ticketId) {
		entityManager.createNamedQuery("TicketDeptTransferEntity.updateTicketDeptAcceptanceStatus").setParameter("ticketId", ticketId).executeUpdate();		
	}
	
	@Override
	public void updateTicketStatus(int ticketId) {
		entityManager.createNamedQuery("TicketDeptTransferEntity.updateTicketStatus").setParameter("ticketId", ticketId).executeUpdate();		
	}
	
	@Override
	public void updateTicketLastResponse(int ticketId) {
		entityManager.createNamedQuery("TicketDeptTransferEntity.updateTicketLastResponse").setParameter("ticketId", ticketId).executeUpdate();		
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TicketDeptTransferEntity> getDeptTransferSearchByMonth(
			Date fromDate, Date toDate) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = formatter.format(fromDate);
		String s2 = formatter.format(toDate);
		System.out.println("from Date"+s1);
		System.out.println("to Date"+s2);
		return getAllDetailsList(entityManager.createNamedQuery("TicketDeptTransferEntity.findAllDataByMonth").setParameter("fromDate", s1).setParameter("toDate", s2).getResultList());
	}
}
