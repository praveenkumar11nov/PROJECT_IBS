package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.HelpTopicEntity;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.helpDeskManagement.HelpTopicService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class HelpTopicServiceImpl extends GenericServiceImpl<HelpTopicEntity> implements HelpTopicService  {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private UsersService usersService;

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<HelpTopicEntity> findAll() {
		return getAllDetailsList(entityManager.createNamedQuery("HelpTopicEntity.findAll").getResultList());
	}
	
	@Override
	public void setHelpTopicStatus(int topicId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("HelpTopicEntity.setHelpTopicStatus").setParameter("status", "Active").setParameter("topicId", topicId).executeUpdate();
				out.write("Help Topic active");
			}
			else
			{
				entityManager.createNamedQuery("HelpTopicEntity.setHelpTopicStatus").setParameter("status", "Inactive").setParameter("topicId", topicId).executeUpdate();
				out.write("Help Topic inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> topicEntities) {		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> helpTopicMap = null;
		 for (Iterator<?> iterator = topicEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				helpTopicMap = new HashMap<String, Object>();
				
				String userNameLevel1 = "";
				userNameLevel1 = userNameLevel1.concat((String)values[10]);
				if((String)values[11] != null)
				{
					userNameLevel1 = userNameLevel1.concat(" ");
					userNameLevel1 = userNameLevel1.concat((String)values[11]);
				}
				
				String userNameLevel2 = "";
				userNameLevel2 = userNameLevel2.concat((String)values[12]);
				if((String)values[13] != null)
				{
					userNameLevel2 = userNameLevel2.concat(" ");
					userNameLevel2 = userNameLevel2.concat((String)values[13]);
				}
				
				String userNameLevel3 = "";
				userNameLevel3 = userNameLevel3.concat((String)values[14]);
				if((String)values[15] != null)
				{
					userNameLevel3 = userNameLevel3.concat(" ");
					userNameLevel3 = userNameLevel3.concat((String)values[15]);
				}			
				
				helpTopicMap.put("userNameLevel1",userNameLevel1);
				helpTopicMap.put("userNameLevel2",userNameLevel2);
				helpTopicMap.put("userNameLevel3",userNameLevel3);				
				helpTopicMap.put("level1User", (Integer)values[7]);	
				helpTopicMap.put("level2User", (Integer)values[8]);
				helpTopicMap.put("level3User", (Integer)values[9]);				
				helpTopicMap.put("topicId", (Integer)values[0]);
				helpTopicMap.put("topicName", (String)values[1]);
				helpTopicMap.put("topicDesc", (String)values[2]);
				helpTopicMap.put("normalSLA", (Integer)values[3]);
				helpTopicMap.put("level1SLA",(Integer)values[4]);
				helpTopicMap.put("level2SLA",(Integer)values[5]);
				helpTopicMap.put("level3SLA",(Integer)values[6]);
				helpTopicMap.put("createdDate", (Timestamp)values[16]);	
				helpTopicMap.put("status", (String)values[17]);
				helpTopicMap.put("createdBy", (String)values[18]);
				helpTopicMap.put("lastUpdatedBy", (String)values[19]);
				helpTopicMap.put("lastUpdatedDT", (Timestamp)values[20]);
				helpTopicMap.put("dept_Id", (Integer)values[21]);
				helpTopicMap.put("dept_Name", (String)values[22]);
				
				List<Map<String, Object>> notifiedUserList = null;
				if(values[23]!=null){
					notifiedUserList = new ArrayList<Map<String, Object>>();
					String[] notifiedUserIdArray = ((String)values[23]).split(",");
					String userNames = "";
					List<String> list = Arrays.asList(notifiedUserIdArray);
					Map<String, Object> userMap = null;
					for (String str : list) {
						
						userMap = new HashMap<String, Object>();
						Users u = usersService.find(Integer.parseInt(str));
						String lastName = "";
						if(u.getPerson().getLastName()!=null){
							lastName = " "+u.getPerson().getLastName();
						}
						userNames+=u.getPerson().getFirstName().concat(lastName)+" ,";
						
						userMap.put("urId",Integer.parseInt(str));				
						userMap.put("notifiedUser",u.getPerson().getFirstName().concat(lastName));
						
						notifiedUserList.add(userMap);
					}
					helpTopicMap.put("level1NotifiedUserNames",userNames);
					helpTopicMap.put("level1NotifiedUsersDummy",notifiedUserList);
					helpTopicMap.put("level1NotifiedUsers",notifiedUserList);
				}else{
					helpTopicMap.put("level1NotifiedUserNames","");
				}
				
				if(values[24]!=null){
					notifiedUserList = new ArrayList<Map<String, Object>>();
					String[] notifiedUserIdArray = ((String)values[24]).split(",");
					String userNames = "";
					List<String> list = Arrays.asList(notifiedUserIdArray);
					Map<String, Object> userMap = null;
					for (String str : list) {
						
						userMap = new HashMap<String, Object>();
						Users u = usersService.find(Integer.parseInt(str));
						String lastName = "";
						if(u.getPerson().getLastName()!=null){
							lastName = " "+u.getPerson().getLastName();
						}
						userNames+=u.getPerson().getFirstName().concat(lastName)+" ,";
						
						userMap.put("urId",Integer.parseInt(str));				
						userMap.put("notifiedUser",u.getPerson().getFirstName().concat(lastName));
						
						notifiedUserList.add(userMap);
					}
					helpTopicMap.put("level2NotifiedUserNames",userNames);
					helpTopicMap.put("level2NotifiedUsersDummy",notifiedUserList);
					helpTopicMap.put("level2NotifiedUsers",notifiedUserList);
				}else{
					helpTopicMap.put("level2NotifiedUserNames","");
				}
				
				if(values[25]!=null){
					notifiedUserList = new ArrayList<Map<String, Object>>();
					String[] notifiedUserIdArray = ((String)values[25]).split(",");
					String userNames = "";
					List<String> list = Arrays.asList(notifiedUserIdArray);
					Map<String, Object> userMap = null;
					for (String str : list) {
						
						userMap = new HashMap<String, Object>();
						Users u = usersService.find(Integer.parseInt(str));
						String lastName = "";
						if(u.getPerson().getLastName()!=null){
							lastName = " "+u.getPerson().getLastName();
						}
						userNames+=u.getPerson().getFirstName().concat(lastName)+" ,";
						
						userMap.put("urId",Integer.parseInt(str));				
						userMap.put("notifiedUser",u.getPerson().getFirstName().concat(lastName));
						
						notifiedUserList.add(userMap);
					}
					helpTopicMap.put("level3NotifiedUserNames",userNames);
					helpTopicMap.put("level3NotifiedUsersDummy",notifiedUserList);
					helpTopicMap.put("level3NotifiedUsers",notifiedUserList);
				}else{
					helpTopicMap.put("level3NotifiedUserNames","");
				}
			
			result.add(helpTopicMap);
	     }
       return result;
	}
	
	@Override
	public List<?> findUsers() {
		return entityManager.createNamedQuery("HelpTopicEntity.findUsers").getResultList();
	}
	
	@Override
	public List<?> selectDistinctQueryForUsers(String className,String relationObject) {
		final StringBuffer queryString = new StringBuffer("SELECT DISTINCT p.firstName FROM ");
		
		queryString.append(className+" ht INNER JOIN ht.");
	    queryString.append(relationObject+" u ");
	    queryString.append("INNER JOIN u.person p");
	    final Query query = this.entityManager.createQuery(queryString.toString());
	
	    return (List<?>)query.getResultList();		
	}
	
	@Override
	public List<?> getDistinctHelpTopics()
	{
		return entityManager.createNamedQuery("HelpTopicEntity.getDistinctHelpTopics").getResultList();
	}
}
