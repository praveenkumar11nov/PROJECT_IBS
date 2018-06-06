package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.OpenNewTicketEntity;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.helpDeskManagement.OpenNewTicketService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class OpenNewTicketServiceImpl extends GenericServiceImpl<OpenNewTicketEntity> implements OpenNewTicketService  {
	
	@Autowired
	private PropertyService propertyService;

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<OpenNewTicketEntity> findALL() {
		return getAllDetailsList(entityManager.createNamedQuery("OpenNewTicketEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> ticketEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> openTicketMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				openTicketMap = new HashMap<String, Object>();		
				
				if(values[22]!=null && ((String)values[22]).trim().equalsIgnoreCase("Common Area")){
					openTicketMap.put("personId","");
					openTicketMap.put("personName","");					
					openTicketMap.put("propertyId", "");	
					openTicketMap.put("property_No","");
					openTicketMap.put("blockId","");
					openTicketMap.put("blockName","");

				}else{
					
					 openTicketMap.put("personId", (Integer)values[23]);
					 
					 Object[] personNameData = getPersonNameBasedOnPersonId((Integer)values[23]);
					 
					 String personName = "";
					 personName = personName.concat((String)personNameData[0]);
					 if(personNameData[1]!= null){
					 	personName = personName.concat(" ");
						personName = personName.concat((String)personNameData[1]);
					 }
						
					openTicketMap.put("personName",personName);					
					openTicketMap.put("propertyId", (Integer)values[24]);	
					Object propertyData[] = getPropertyDataBasedOnPropertyId((Integer)values[24]);
					openTicketMap.put("property_No",(String)propertyData[0]);
					openTicketMap.put("blockId",(Integer)propertyData[1]);
					openTicketMap.put("blockName",(String)propertyData[2]);
				}
				
				openTicketMap.put("ticketId",(Integer)values[0]);
				openTicketMap.put("ticketNumber", (String)values[1]);
				openTicketMap.put("dept_Id", (Integer)values[2]);
				openTicketMap.put("ipAddress", (String)values[3]);
				openTicketMap.put("topicId", (Integer)values[4]);
				openTicketMap.put("issueSubject", (String)values[5]);
				openTicketMap.put("issueDetails", (String)values[6]);
				openTicketMap.put("priorityLevel",(String)values[7]);
				openTicketMap.put("lastResonse", (Timestamp)values[8]);
				openTicketMap.put("ticketCreatedDate", (Timestamp)values[9]);
				openTicketMap.put("ticketClosedDate", (Timestamp)values[10]);
				openTicketMap.put("ticketReopenDate", (Timestamp)values[11]);
				openTicketMap.put("ticketAssignedDate", (Timestamp)values[12]);		
				openTicketMap.put("ticketUpdateDate",(Timestamp)values[13]);			
				openTicketMap.put("ticketStatus", (String)values[14]);
				openTicketMap.put("deptAcceptanceStatus",(String)values[15]);
				openTicketMap.put("createdBy", (String)values[16]);
				openTicketMap.put("lastUpdatedBy", (String)values[17]);
				openTicketMap.put("lastUpdatedDT", (Timestamp)values[18]);				
				openTicketMap.put("dept_Name",(String)values[19]);		
				openTicketMap.put("topicName", (String)values[20]);
				openTicketMap.put("deptAcceptedDate", (Timestamp)values[21]);
				openTicketMap.put("typeOfTicket", (String)values[22]);
				
			result.add(openTicketMap);
	     }
      return result;
	}

	@Override
	public Object[] getPropertyDataBasedOnPropertyId(int propertyId) {
		try{
			return (Object[])entityManager.createNamedQuery("OpenNewTicketEntity.getPropertyDataBasedOnPropertyId").setParameter("propertyId", propertyId).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public void ticketStatusUpdateFromInnerGrid(int ticketId,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("ticketStatus");
			OpenNewTicketEntity openNewTicketEntity = find(ticketId);			
			if(openNewTicketEntity.getTicketStatus().equalsIgnoreCase("Closed"))
			{
			   openNewTicketEntity.setTicketReopenDate(new Timestamp(new java.util.Date().getTime()));				
               entityManager.createNamedQuery("OpenNewTicketEntity.ticketStatusUpdateFromInnerGrid").setParameter("ticketStatus", "Re-Open").setParameter("ticketId", ticketId).executeUpdate();
			   out.write("Ticket Re-open");
			}
			else if(openNewTicketEntity.getTicketStatus().equalsIgnoreCase("Re-Open")){
				out.write("Already Ticket status Re-open");
			}else {
				out.write("Only closed tickets can re-open");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<OwnerProperty> findAllPropertyPersonOwnerList() {
		return entityManager.createNamedQuery("OpenNewTicketEntity.findAllPropertyPersonOwnerList").getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<TenantProperty> findAllPropertyPersonTenantList() {
		return entityManager.createNamedQuery("OpenNewTicketEntity.findAllPropertyPersonTenantList").getResultList();
	}

	@Override
	public Person getPersons(int personId) {
		return entityManager.createNamedQuery("OpenNewTicketEntity.getPersons",Person.class).setParameter("personId", personId).getSingleResult();
	}
	
	@Override
	public String getDepartements(int dept_Id) {
		return (String)entityManager.createNamedQuery("OpenNewTicketEntity.getDepartements").setParameter("dept_Id", dept_Id).getSingleResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<OpenNewTicketEntity> findAllTicketsBasedOnDept(int dept_Id,int urId) {
		return getAllDetailsList(entityManager.createNamedQuery("OpenNewTicketEntity.findAllTicketsBasedOnDept").setParameter("dept_Id", dept_Id).getResultList());
	}

	@Override
	public void departementTicketAcceptanceStatusAsAccept(int ticketId,	HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("deptAcceptanceStatus");

			OpenNewTicketEntity openNewTicketEntity = find(ticketId);
			
			if(openNewTicketEntity.getDeptAcceptanceStatus()==null || openNewTicketEntity.getDeptAcceptanceStatus().equals(""))
			{
				entityManager.createNamedQuery("OpenNewTicketEntity.departementTicketAcceptanceStatus").setParameter("deptAcceptanceStatus", "Accepted").setParameter("ticketId", ticketId).executeUpdate();
				entityManager.createNamedQuery("OpenNewTicketEntity.deptAcceptedDate").setParameter("deptAcceptedDate", new Timestamp(new Date().getTime())).setParameter("ticketId", ticketId).executeUpdate();
				out.write("Departement Accepted");
			}else if(openNewTicketEntity.getDeptAcceptanceStatus().equals("Rejected")) {
				out.write("You can't accept");
			}else{
				out.write("Departement Already Accepted");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	@Override
	public void departementTicketAcceptanceStatusAsReject(int ticketId,	HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("deptAcceptanceStatus");
			OpenNewTicketEntity openNewTicketEntity = find(ticketId);
			
			if(openNewTicketEntity.getDeptAcceptanceStatus()==null || openNewTicketEntity.getDeptAcceptanceStatus().equals(""))
			{
				entityManager.createNamedQuery("OpenNewTicketEntity.departementTicketAcceptanceStatus").setParameter("deptAcceptanceStatus", "Rejected").setParameter("ticketId", ticketId).executeUpdate();
				out.write("Departement Rejected");
			}else if(openNewTicketEntity.getDeptAcceptanceStatus().equals("Accepted")) {
				out.write("You can't reject,because once department accepted you can not reject");
			}else{
				out.write("Departement Already Rejected");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	@Override
	public String ticketStatusUpdateAsClose(int ticketId, HttpServletResponse response) {
		String statusOperation="";
		try
		{
			PrintWriter out = response.getWriter();			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("ticketStatus");
			attributesList.add("ticketClosedDate");
			OpenNewTicketEntity openNewTicketEntity = find(ticketId);			
			if(openNewTicketEntity.getDeptAcceptanceStatus()==null || openNewTicketEntity.getDeptAcceptanceStatus().equals("Rejected")){
				   out.write("You can't clsoe ticket,because departement not accepted");
				   statusOperation="Fail";
			   
			}else {				
				if(openNewTicketEntity.getTicketStatus()==null || openNewTicketEntity.getTicketStatus().equals("")|| !openNewTicketEntity.getTicketStatus().equals("Closed"))
				   {
					  entityManager.createNamedQuery("OpenNewTicketEntity.ticketStatusUpdateAsClose").setParameter("ticketStatus", "Closed").setParameter("ticketId", ticketId).executeUpdate();
					  entityManager.createNamedQuery("OpenNewTicketEntity.ticketUpdateClosedDate").setParameter("ticketClosedDate", new Timestamp(new java.util.Date().getTime())).setParameter("ticketId", ticketId).executeUpdate();
					  statusOperation="Success";
					  out.write("Ticket Closed");					  
				   }else {
					statusOperation="Fail";
					out.write("Ticket Already Closed");
		    		}				
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		return statusOperation;
	}

	@Override
	public void updateAssignTicketDate(int ticketId) {
		entityManager.createNamedQuery("OpenNewTicketEntity.updateAssignTicketDate").setParameter("ticketAssignedDate",new Timestamp(new java.util.Date().getTime())).setParameter("ticketId", ticketId).executeUpdate();			
	}

	@Override
	public void lastResponseDateUpdate(int ticketId) {		
		entityManager.createNamedQuery("OpenNewTicketEntity.lastResponseDateUpdate").setParameter("lastResonse", new Timestamp(new java.util.Date().getTime())).setParameter("ticketId", ticketId).executeUpdate();		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> findAllTicketsBasedOnResponseTime() {
		return entityManager.createNamedQuery("OpenNewTicketEntity.findAllTicketsBasedOnResponseTime").getResultList();
	}
	
	@Override
	public void updateTicketStatusAsAssigned(int ticketId) {
		entityManager.createNamedQuery("OpenNewTicketEntity.ticketStatusUpdateAsClose").setParameter("ticketStatus","Assigned").setParameter("ticketId", ticketId).executeUpdate();			
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<OpenNewTicketEntity> findAllTicketsBasedOnUrId(int urId) {
		return getAllDetailsList(entityManager.createNamedQuery("OpenNewTicketEntity.findAllTicketsBasedOnUrId").setParameter("urId", urId).getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRoleName(int urId) {
		return entityManager.createNamedQuery("OpenNewTicketEntity.getRoleName").setParameter("urId", urId).getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("OpenNewTicketEntity.findPersonForFilters").getResultList();
		return details;
	}

	@Override
	public List<?> selectDistinctQuery(String className, String relationObject, String attribute) {
		final StringBuffer queryString = new StringBuffer("SELECT DISTINCT u."+attribute+" FROM ");
	    queryString.append(className).append(" o ");
	    queryString.append("INNER JOIN o.");
	    queryString.append(relationObject).append(" u ");	
	    final Query query = this.entityManager.createQuery(queryString.toString());
		return (List<?>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getPersonsList(int deptId) {
		return entityManager.createNamedQuery("OpenNewTicketEntity.getPersonsList").setParameter("deptId", deptId).getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllBlockNames()
	{
		return entityManager.createNamedQuery("OpenNewTicketEntity.getAllBlockNames").getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getTowerNames(){
		
		List<?> towerNamesList = entityManager.createNamedQuery("OpenNewTicketEntity.getTowerNames").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> towerNameMap = null;
		for (Iterator<?> iterator = towerNamesList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				towerNameMap = new HashMap<String, Object>();				
								
				towerNameMap.put("blockId", (Integer)values[0]);
				towerNameMap.put("blockName",(String)values[1]);				
			
			result.add(towerNameMap);
	     }
     return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> findPropertyNames(){
		
		List<?> propertyNumbersList = entityManager.createNamedQuery("OpenNewTicketEntity.readPropertyNames").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> propertyNumberMap = null;
		for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				propertyNumberMap = new HashMap<String, Object>();				
								
				propertyNumberMap.put("blockId", (Integer)values[0]);	
				propertyNumberMap.put("propertyId",(Integer)values[1]);
				propertyNumberMap.put("property_No", (String)values[2]);	
				propertyNumberMap.put("blockName",(String)values[3]);				
			
			result.add(propertyNumberMap);
	     }
     return result;
	}

	@Override
	public String getUrLoginNameBasedOnMailId(String email) {
		
		return (String)entityManager.createNamedQuery("OpenNewTicketEntity.getUrLoginNameBasedOnMailId").setParameter("email", email).setMaxResults(1).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OpenNewTicketEntity> findALLForPerson(int personId) {
		
		return getAllDetailsListPerson(entityManager.createNamedQuery("OpenNewTicketEntity.findAllForPerson").setParameter("personId", personId).getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsListPerson(List<?> ticketEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> openTicketMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				openTicketMap = new HashMap<String, Object>();				
				openTicketMap.put("personId",(Integer)values[3]);				
				String personName = "";
				personName = personName.concat((String)values[26]);
				if((String)values[27] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[27]);
				}
				
				openTicketMap.put("personName",personName);					
				openTicketMap.put("propertyId", (Integer)values[2]);	
				openTicketMap.put("property_No",(String)values[21]);
				openTicketMap.put("dept_Id", (Integer)values[24]);	
				openTicketMap.put("dept_Name",(String)values[25]);
				openTicketMap.put("blockId",(Integer)values[22]);
				openTicketMap.put("blockName",(String)values[23]);
				openTicketMap.put("ticketId",(Integer)values[0]);
				openTicketMap.put("deptAcceptanceStatus",(String)values[17]);
				openTicketMap.put("ticketNumber", (String)values[1]);
				openTicketMap.put("ipAddress", (String)values[5]);
				openTicketMap.put("topicId", (Integer)values[6]);
				openTicketMap.put("topicName", (String)values[28]);
				openTicketMap.put("issueSubject", (String)values[7]);
				openTicketMap.put("issueDetails", (String)values[8]);			
				openTicketMap.put("priorityLevel",(String)values[9]);
				openTicketMap.put("lastResonse", (Timestamp)values[10]);
				openTicketMap.put("ticketCreatedDate", (Timestamp)values[11]);
				openTicketMap.put("ticketClosedDate", (Timestamp)values[12]);
				openTicketMap.put("ticketReopenDate", (Timestamp)values[13]);
				openTicketMap.put("ticketAssignedDate", (Timestamp)values[14]);		
				openTicketMap.put("ticketUpdateDate",(Timestamp)values[15]);			
				openTicketMap.put("ticketStatus", (String)values[16]);
				openTicketMap.put("createdBy", (String)values[18]);
				openTicketMap.put("lastUpdatedBy", (String)values[19]);
				openTicketMap.put("lastUpdatedDT", (Timestamp)values[20]);
				openTicketMap.put("deptAcceptedDate", (Timestamp)values[29]);
				openTicketMap.put("typeOfTicket", (String)values[30]);
			
			result.add(openTicketMap);
	     }
      return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findALLTicket() {
		return entityManager.createNamedQuery("OpenNewTicketEntity.findAll").getResultList();
	}

	@Override
	public List<?> findCloseTicketReports() {
		return getAllClosedReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReportsBasedOnStatus").setParameter("status", "Closed").getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getAllClosedReports(List<?> ticketEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> openTicketMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				openTicketMap = new HashMap<String, Object>();				
				
				openTicketMap.put("ticketNumber", (String)values[0]);
				String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
				if((Timestamp)values[3]!=null){
					String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
					openTicketMap.put("ticketClosedDate", tClosedDate);
				}else{
					openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
				}
				if((Timestamp)values[9]!=null){
					String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
					openTicketMap.put("lastUpdatedDT", luDate);
				}else{
					openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
				}
			//	String	tuDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[4]);
				
					openTicketMap.put("ticketCreatedDate", tcDate);
				openTicketMap.put("issueSubject", (String)values[1]);
				//openTicketMap.put("ticketCreatedDate", (Timestamp)values[2]);
				//.put("ticketClosedDate", (Timestamp)values[3]);
				//.put("ticketUpdateDate",(Timestamp)values[4]);
				openTicketMap.put("ticketStatus", (String)values[6]);
				openTicketMap.put("createdBy", (String)values[7]);
				//openTicketMap.put("lastUpdatedDT", (Timestamp)values[9]);
				openTicketMap.put("propertyNo",(String)values[10]);
				openTicketMap.put("category",(String)values[11]);//deptName
				openTicketMap.put("subcategory", (String)values[12]);//topicName
				openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
				//String serviceassigned=getserviceAssigned((Integer)values[14]);
				List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
				System.out.println("ticketno:::::::::::"+(String)values[0]);
				System.out.println("::::::::::ticketsnote:::::::::::"+ticketsnote.size());
				if(ticketsnote.size()>0){
					openTicketMap.put("drivenBy", ticketsnote.get(0));
					openTicketMap.put("lastnotes", ticketsnote.get(1));
					//openTicketMap.put("issueSubject", ticketsnote.get(1));
				}
				
				//openTicketMap.put("drivenBy", serviceassigned);
				
			
			result.add(openTicketMap);
	     }
      return result;
	}

	@SuppressWarnings("rawtypes")
	private List<?> getsericeServiceAssigneResponse(Integer ticketId) {
		List<String> result=new ArrayList<>();
		List<?> resonse=entityManager.createNamedQuery("HelpDeskEntity.findassignedNotes").setParameter("ticketId",  ticketId).getResultList();
		for (Iterator iterator = resonse.iterator(); iterator.hasNext();) {
			Object[] string = (Object[]) iterator.next();
			result.add((String)string[0]);
			result.add((String)string[1]);
		}
		return result;
	}

	@Override
	public List<?> findAllTicketReports() {
	return	getAllReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReports").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllReports(List<?> ticketEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> openTicketMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				openTicketMap = new HashMap<String, Object>();				
				
				openTicketMap.put("ticketNumber", (String)values[0]);
				openTicketMap.put("issueSubject", (String)values[1]);
			String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
			if((Timestamp)values[3]!=null){
				String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
				openTicketMap.put("ticketClosedDate", tClosedDate);
			}else{
				openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
			}
			if((Timestamp)values[9]!=null){
				String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
				openTicketMap.put("lastUpdatedDT", luDate);
			}else{
				openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
			}
		//	String	tuDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[4]);
			
				openTicketMap.put("ticketCreatedDate", tcDate);
				
				openTicketMap.put("ticketUpdateDate",(Timestamp)values[4]);
				openTicketMap.put("ticketStatus", (String)values[6]);
				openTicketMap.put("createdBy", (String)values[7]);
				
				openTicketMap.put("propertyNo",(String)values[10]);
				openTicketMap.put("category",(String)values[11]);//deptName
				openTicketMap.put("subcategory", (String)values[12]);//topicName
				openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
				/*String serviceassigned=getserviceAssigned((Integer)values[14]);*/
				List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
				if(ticketsnote.size()>0){
					openTicketMap.put("drivenBy", ticketsnote.get(0));
					//openTicketMap.put("lastnotes", ticketsnote.get(1));
					//openTicketMap.put("issueSubject", ticketsnote.get(1));
				}
				/*openTicketMap.put("drivenBy", serviceassigned);*/
				
			
			result.add(openTicketMap);
	     }
      return result;
	}
	
	public String getserviceAssigned(Integer ticketId){
		String assigned=null;
		
		try {
			assigned=(String) entityManager.createNamedQuery("HelpDeskEntity.findassigne").setParameter("ticketId",  ticketId).getSingleResult();
		System.out.println(":::::::::bill:::::::::::"+assigned);
		} catch (NoResultException e) {
		}
		if(assigned==null){
			assigned="NA";
		}else{
			assigned=assigned;
		}
	return assigned;
	}

	@Override
	public List<?> findOpenTicketReports() {
		 return getAllOpenReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReportsBasedOnStatus").setParameter("status", "Open").getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getAllOpenReports(List<?> ticketEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> openTicketMap = null;
		 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				openTicketMap = new HashMap<String, Object>();				
				
				openTicketMap.put("ticketNumber", (String)values[0]);
				//openTicketMap.put("issueSubject", (String)values[1]);
				//openTicketMap.put("ticketCreatedDate", (Timestamp)values[2]);
				//openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
				//openTicketMap.put("ticketUpdateDate",(Timestamp)values[4]);
				String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
				if((Timestamp)values[3]!=null){
					String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
					openTicketMap.put("ticketClosedDate", tClosedDate);
				}else{
					openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
				}
				if((Timestamp)values[9]!=null){
					String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
					openTicketMap.put("lastUpdatedDT", luDate);
				}else{
					openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
				}
			//	String	tuDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[4]);
				
					openTicketMap.put("ticketCreatedDate", tcDate);
				openTicketMap.put("ticketStatus", (String)values[6]);
				openTicketMap.put("createdBy", (String)values[7]);
				//openTicketMap.put("lastUpdatedDT", (Timestamp)values[9]);
				openTicketMap.put("propertyNo",(String)values[10]);
				openTicketMap.put("category",(String)values[11]);//deptName
				openTicketMap.put("subcategory", (String)values[12]);//topicName
				openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
				//String serviceassigned=getserviceAssigned((Integer)values[14]);
				List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
				System.out.println("ticketno:::::::::::"+(String)values[0]);
				System.out.println("::::::::::ticketsnote:::::::::::"+ticketsnote.size());
				if(ticketsnote.size()>0){
					openTicketMap.put("drivenBy", ticketsnote.get(0));
					openTicketMap.put("lastnotes", ticketsnote.get(1));
					//openTicketMap.put("issueSubject", ticketsnote.get(1));
				}
				
				//openTicketMap.put("drivenBy", serviceassigned);
				
			
			result.add(openTicketMap);
	     }
      return result;
	}

	@Override
	public List<?> findClosedOnBehalf() {
		
		 return getAllClosedOnbeHalfReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReportsBasedOnBehalf").setParameter("status", "Closed").getResultList());
			}
			@SuppressWarnings("rawtypes")
			private List getAllClosedOnbeHalfReports(List<?> ticketEntities){
				
				 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				 Map<String, Object> openTicketMap = null;
				 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						openTicketMap = new HashMap<String, Object>();				
						
						openTicketMap.put("ticketNumber", (String)values[0]);
						//openTicketMap.put("issueSubject", (String)values[1]);
						
						String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
						if((Timestamp)values[3]!=null){
							String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
							openTicketMap.put("ticketClosedDate", tClosedDate);
						}else{
							openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						}
						if((Timestamp)values[9]!=null){
							String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
							openTicketMap.put("lastUpdatedDT", luDate);
						}else{
							openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
						}
						openTicketMap.put("ticketCreatedDate", tcDate);
						//openTicketMap.put("ticketCreatedDate", (Timestamp)values[2]);
						//openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						//openTicketMap.put("ticketUpdateDate",(Timestamp)values[4]);
						openTicketMap.put("ticketStatus", (String)values[6]);
						openTicketMap.put("createdBy", (String)values[7]);
						//openTicketMap.put("lastUpdatedDT", (Timestamp)values[9]);
						openTicketMap.put("propertyNo",(String)values[10]);
						openTicketMap.put("category",(String)values[11]);//deptName
						openTicketMap.put("subcategory", (String)values[12]);//topicName
						openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
						//String serviceassigned=getserviceAssigned((Integer)values[14]);
						List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
                        String personName = "";
						personName = personName.concat((String)values[15]);
						if((String)values[16] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)values[16]);
						}
						String onbehalfof="Ticket Posted By Helpdesk GA On Behalf of ".concat(personName);
						openTicketMap.put("onBeHalfOf", onbehalfof);
						
						if(ticketsnote.size()>0){
							openTicketMap.put("drivenBy", ticketsnote.get(0));
							openTicketMap.put("lastnotes", ticketsnote.get(1));
							//openTicketMap.put("issueSubject", ticketsnote.get(1));
						}
						
						//openTicketMap.put("drivenBy", serviceassigned);
						
					
					result.add(openTicketMap);
			     }
		      return result;
			}

			@Override
			public List<?> findOpenOnBehalf() {
				 return getAlOpenOnbeHalfReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReportsBasedOnBehalf").setParameter("status", "Open").getResultList());
			}
			@SuppressWarnings("rawtypes")
			private List getAlOpenOnbeHalfReports(List<?> ticketEntities){
				
				 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				 Map<String, Object> openTicketMap = null;
				 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						openTicketMap = new HashMap<String, Object>();				
						
						openTicketMap.put("ticketNumber", (String)values[0]);
						//openTicketMap.put("issueSubject", (String)values[1]);
						String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
						if((Timestamp)values[3]!=null){
							String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
							openTicketMap.put("ticketClosedDate", tClosedDate);
						}else{
							openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						}
						if((Timestamp)values[9]!=null){
							String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
							openTicketMap.put("lastUpdatedDT", luDate);
						}else{
							openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
						}
						openTicketMap.put("ticketCreatedDate", tcDate);
						
						
						//openTicketMap.put("ticketCreatedDate", (Timestamp)values[2]);
						//openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						//openTicketMap.put("ticketUpdateDate",(Timestamp)values[4]);
						openTicketMap.put("ticketStatus", (String)values[6]);
						openTicketMap.put("createdBy", (String)values[7]);
						//openTicketMap.put("lastUpdatedDT", (Timestamp)values[9]);
						openTicketMap.put("propertyNo",(String)values[10]);
						openTicketMap.put("category",(String)values[11]);//deptName
						openTicketMap.put("subcategory", (String)values[12]);//topicName
						openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
						//String serviceassigned=getserviceAssigned((Integer)values[14]);
						
						String personName = "";
						
						personName = personName.concat((String)values[15]);
						if((String)values[16] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)values[16]);
						}
						String onbehalfof="Ticket Posted By Helpdesk GA On Behalf of ".concat(personName);
						openTicketMap.put("onBeHalfOf", onbehalfof);
						
						List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
						System.out.println("ticketno:::::::::::"+(String)values[0]);
						System.out.println("::::::::::ticketsnote:::::::::::"+ticketsnote.size());
						if(ticketsnote.size()>0){
							openTicketMap.put("drivenBy", ticketsnote.get(0));
							openTicketMap.put("lastnotes", ticketsnote.get(1));
							//openTicketMap.put("issueSubject", ticketsnote.get(1));
						}
						
						//openTicketMap.put("drivenBy", serviceassigned);
						
					
					result.add(openTicketMap);
			     }
		      return result;
			}

			@Override
			public List<?> findAllPersonalOpenTicket() {
				return getAlOpenPersonalReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReportPersonal").setParameter("status", "Open").getResultList());
			}
			@SuppressWarnings("rawtypes")
			private List getAlOpenPersonalReports(List<?> ticketEntities){
				
				 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				 Map<String, Object> openTicketMap = null;
				 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						openTicketMap = new HashMap<String, Object>();				
						
						openTicketMap.put("ticketNumber", (String)values[0]);
						String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
						if((Timestamp)values[3]!=null){
							String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
							openTicketMap.put("ticketClosedDate", tClosedDate);
						}else{
							openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						}
						if((Timestamp)values[9]!=null){
							String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
							openTicketMap.put("lastUpdatedDT", luDate);
						}else{
							openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
						}
						openTicketMap.put("ticketCreatedDate", tcDate);
						
						//openTicketMap.put("issueSubject", (String)values[1]);
						//openTicketMap.put("ticketCreatedDate", (Timestamp)values[2]);
						//openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						//openTicketMap.put("ticketUpdateDate",(Timestamp)values[4]);
						openTicketMap.put("ticketStatus", (String)values[6]);
						openTicketMap.put("createdBy", (String)values[7]);
						//openTicketMap.put("lastUpdatedDT", (Timestamp)values[9]);
						openTicketMap.put("propertyNo",(String)values[10]);
						openTicketMap.put("category",(String)values[11]);//deptName
						openTicketMap.put("subcategory", (String)values[12]);//topicName
						openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
						//String serviceassigned=getserviceAssigned((Integer)values[14]);
						
						String personName = "";
						
						personName = personName.concat((String)values[15]);
						if((String)values[16] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)values[16]);
						}
						String onbehalfof="Ticket Posted By Helpdesk GA On Behalf of ".concat(personName);
						openTicketMap.put("onBeHalfOf", onbehalfof);
						
						List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
						System.out.println("ticketno:::::::::::"+(String)values[0]);
						System.out.println("::::::::::ticketsnote:::::::::::"+ticketsnote.size());
						if(ticketsnote.size()>0){
							openTicketMap.put("drivenBy", ticketsnote.get(0));
							openTicketMap.put("lastnotes", ticketsnote.get(1));
							//openTicketMap.put("issueSubject", ticketsnote.get(1));
						}
						
						//openTicketMap.put("drivenBy", serviceassigned);
						
					
					result.add(openTicketMap);
			     }
		      return result;
			}

			@Override
			public List<?> findAllClosedPersonalTicket() {
				return getAllClosedPersonalReports(entityManager.createNamedQuery("OpenNewTicketEntity.getTicketReportPersonal").setParameter("status", "Closed").getResultList());
			}
			@SuppressWarnings("rawtypes")
			private List getAllClosedPersonalReports(List<?> ticketEntities){
				
				 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				 Map<String, Object> openTicketMap = null;
				 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						openTicketMap = new HashMap<String, Object>();				
						
						openTicketMap.put("ticketNumber", (String)values[0]);
						String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[2]);
						if((Timestamp)values[3]!=null){
							String	tClosedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);
							openTicketMap.put("ticketClosedDate", tClosedDate);
						}else{
							openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						}
						if((Timestamp)values[9]!=null){
							String	luDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[9]);
							openTicketMap.put("lastUpdatedDT", luDate);
						}else{
							openTicketMap.put("lastUpdatedDT",(Timestamp)values[9]);
						}
						openTicketMap.put("ticketCreatedDate", tcDate);
						
						//openTicketMap.put("issueSubject", (String)values[1]);
						//openTicketMap.put("ticketCreatedDate", (Timestamp)values[2]);
						//openTicketMap.put("ticketClosedDate", (Timestamp)values[3]);
						//openTicketMap.put("ticketUpdateDate",(Timestamp)values[4]);
						openTicketMap.put("ticketStatus", (String)values[6]);
						openTicketMap.put("createdBy", (String)values[7]);
						//openTicketMap.put("lastUpdatedDT", (Timestamp)values[9]);
						openTicketMap.put("propertyNo",(String)values[10]);
						openTicketMap.put("category",(String)values[11]);//deptName
						openTicketMap.put("subcategory", (String)values[12]);//topicName
						openTicketMap.put("dayIssue", (Integer)values[13]);//normalsla
						//String serviceassigned=getserviceAssigned((Integer)values[14]);
						
						String personName = "";
						
						personName = personName.concat((String)values[15]);
						if((String)values[16] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)values[16]);
						}
						String onbehalfof="Ticket Posted By Helpdesk GA On Behalf of ".concat(personName);
						openTicketMap.put("onBeHalfOf", onbehalfof);
						
						List<?> ticketsnote=getsericeServiceAssigneResponse((Integer)values[14]);
						System.out.println("ticketno:::::::::::"+(String)values[0]);
						System.out.println("::::::::::ticketsnote:::::::::::"+ticketsnote.size());
						if(ticketsnote.size()>0){
							openTicketMap.put("drivenBy", ticketsnote.get(0));
							openTicketMap.put("lastnotes", ticketsnote.get(1));
							//openTicketMap.put("issueSubject", ticketsnote.get(1));
						}
						
						//openTicketMap.put("drivenBy", serviceassigned);
						
					
					result.add(openTicketMap);
			     }
		      return result;
			}

			@Override
			public List<?> findTicketSummary() {
				return getTicketSummary(entityManager.createNamedQuery("OpenNewTicketEntity.findTicketSummary").getResultList());

			}
			
			@SuppressWarnings("rawtypes")
			private List getTicketSummary(List<?> ticketSummary){
				
				 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				 Map<String, Object> openTicketMap = null;
				 for (Iterator<?> iterator = ticketSummary.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						openTicketMap = new HashMap<String, Object>();				
						
						openTicketMap.put("categoryOwner","Helpdesk GA");
						openTicketMap.put("category", (String)values[1]);
						Long openCount=	entityManager.createNamedQuery("OpenNewTicketEntity.getCountForOpenTickets",Long.class).setParameter("status", "Open").setParameter("topicId", (Integer)values[0]).getSingleResult();
						Long closedCount=entityManager.createNamedQuery("OpenNewTicketEntity.getCountForOpenTickets",Long.class).setParameter("status", "Closed").setParameter("topicId", (Integer)values[0]).getSingleResult();
						if(closedCount!=null){
						openTicketMap.put("closedCount",closedCount);	
						}
						if(openCount!=null){
						openTicketMap.put("openCount", openCount);	
						}
					result.add(openTicketMap);
			     }
		      return result;
			}

			@Override
			public String getTopicNameBasedOnTopicId(int topicId) {
				return (String)entityManager.createNamedQuery("OpenNewTicketEntity.getTopicNameBasedOnTopicId").setParameter("topicId", topicId).getSingleResult();
			}

			@Override
			public Object[] getPersonNameBasedOnPersonId(int personId) {
				try{
					return (Object[])entityManager.createNamedQuery("OpenNewTicketEntity.getPersonNameBasedOnPersonId").setParameter("personId", personId).getSingleResult();
				}catch(Exception e){
					return null;
				}
			}

			@Override
			public int getUserIdBasedOnPersonId(int personId) {
				try{
					return (int)entityManager.createNamedQuery("OpenNewTicketEntity.getUserIdBasedOnPersonId").setParameter("personId", personId).getSingleResult();
				}catch(Exception e){
					return 0;
				}
			}
			@SuppressWarnings("unchecked")
			@Override
			@Transactional(propagation=Propagation.SUPPORTS)
			public List<OpenNewTicketEntity> getOpenNewTicketSearchByMonth(java.util.Date fromDate,
					java.util.Date toDate) {
				Format formatter = new SimpleDateFormat("yyyy-MM-dd");
				String s1 = formatter.format(fromDate);
				String s2 = formatter.format(toDate);
				System.out.println("from Date"+s1);
				System.out.println("to Date"+s2);
				return getAllDetailsList(entityManager.createNamedQuery("OpenNewTicketEntity.getAllSearchByMonth").setParameter("fromDate", s1).setParameter("toDate", s2).getResultList());
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public List<OpenNewTicketEntity> findAllData() {
				return entityManager.createNamedQuery("OpenNewTicketEntity.findAll").getResultList();
			}

			@Override
			public int autoGeneratedTicketNumber() {
				return ((BigDecimal)entityManager.createNativeQuery("SELECT HELPDESK_TICKET_NUM_SEQ.nextval FROM dual").getSingleResult()).intValue();
			}

}
