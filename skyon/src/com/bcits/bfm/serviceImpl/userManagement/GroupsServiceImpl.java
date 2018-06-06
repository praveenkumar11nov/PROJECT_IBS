package com.bcits.bfm.serviceImpl.userManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.service.userManagement.UserGroupService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.SessionData;

@Repository
public class GroupsServiceImpl extends GenericServiceImpl<Groups> implements
		GroupsService {
	private static final Log logger = LogFactory.getLog(GroupsServiceImpl.class);
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Groups> findAllWithoutInvalidDefault() {
		return entityManager.createNamedQuery("Groups.findAllWithoutInvalidDefault").getResultList();
	}
	

	/** 
     * this method is used to find all records  from the database
     * @return         : returns all records of group
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Groups> getAll() {
		List<Groups> al = null;
		try {
			BfmLogger.logger.info("inside try block");
			al = entityManager.createNamedQuery("Groups.findAll")
					.getResultList();
		} catch (Exception e) {
		}
		return al;
	}

	/** 
     * this method is used to count the no of groups available in database
     * @return         : returns counted value
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Override
	public int count() {
		List al = entityManager.createNamedQuery("Groups.count")
				.getResultList();
		int val = Integer.parseInt(al.get(0).toString());
		logger.info("size " + val);
		return val;
	}

	/** 
     * this method is used to find group name  from the database
     * @param gname    : gname
     * @return         : returns group record
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Groups> getByName(String gname) {
		return entityManager.createNamedQuery("Groups.groupName")
				.setParameter("Gname", gname).getResultList();
	}

	/** 
     * this method is used to find list of group name from the database
     * @return         : returns list of group name
     * @since           1.0
     */
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Groups> getGroupNames(){
		
		return entityManager.createNamedQuery("GroupNames").getResultList();
	}
	
	
	
	/** 
     * this method is used to find all records of groups from the database
     * @return         : returns list of groups
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<UserGroup> findAll() {
		List<UserGroup> st1 = entityManager.createNamedQuery(
				"ManageUserGroups.findAll").getResultList();
		logger.info("From User_ Group Class");
		return st1;
	}

	@SuppressWarnings("unchecked")
	public List<Groups> checkNameExistence(Groups groups) {
		return entityManager.createNamedQuery("Groups.CheckNameExistence")
				.setParameter("gName", groups.getGr_name()).getResultList();
	}

	/** 
     * this method is used to find record based on Id from the database
     * @param id :id
     * @return         : returns group record
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserGroup> findById(int id) {
		List<UserGroup> st1 = entityManager
				.createNamedQuery("ManageUserGroups.findById")
				.setParameter("UG_GR_ID", id).getResultList();
		return st1;
	}

	/** 
     * this method is used to check the uniqueness of the group from the database
     * @param groupsName:groupsName
     * @return         : returns boolean value(true/false) 
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public boolean checkGroupUnique(Groups groups, String type) {
		List<String> list_groups = entityManager
				.createNamedQuery("Groups.CheckGroup")
				.setParameter("groupsName", groups.getGr_name())
				.getResultList();
		if (type == "update") {
			if ((list_groups.size() > 0)) {
				if (list_groups.get(0).equals(
						find(groups.getGr_id()).getGr_name()))
					return true;
				else
					return false;
			} else
				return true;

		} else {
			if ((list_groups.size() > 0))
				return false;
			else
				return true;
		}

	}

	/** 
     * this method is used to find list of Group details from the database
     * @return         : returns details of groups
     * @since           1.0
     */
	public Groups getGroupObject(Map<String, Object> map, String type,
			Groups groups) {

		groups = getBeanObject(map, type, groups);

		return groups;
	}


	@Transactional(propagation = Propagation.NEVER)
	private Groups getBeanObject(Map<String, Object> map, String type,
			Groups groups) {
String username=(String)SessionData.getUserDetails().get("userID");
		if (type.equals("update")) {
			groups.setGr_status(((String)map.get("gr_status")));
			
			groups.setGr_id((Integer) map.get("gr_id"));
			groups.setCreated_by((String)map.get("created_by"));
			groups.setLast_Updated_by(username);
		}

		if (type.equals("save")) {
			groups.setGr_status("Inactive");
			groups.setCreated_by(username);
			groups.setLast_Updated_by(username);
		}
		groups.setGr_name(WordUtils.capitalizeFully((String) map.get("gr_name")));
		if(map.get("gr_description")==null){
		}
		else{
			String desc=(String) map.get("gr_description");
			String desc_trim=desc.trim();
		groups.setGr_description(desc_trim);
		}
		return groups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.GroupsService#AddUser(com.bcits
	 * .bfm.model.UserGroup)
	 */
	@Override
	public void AddUser(UserGroup ug) {
		entityManager.persist(ug);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.GroupsService#DeleteUser(int,
	 * int)
	 */
	@Override
	public void DeleteUser(int groupId, int userId) {
		entityManager.createNamedQuery("DeleteUser")
				.setParameter("groupId", groupId)
				.setParameter("userId", userId).executeUpdate();
	}
	
	/** 
     * this method is used to update the status of group  based on groupId and store in database
     * @param     gr_name:gr_name
     * @since            1.0
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setGroupStatus(int grId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Groups.UpdateStatus").setParameter("gr_status", "Active").setParameter("grId", grId).executeUpdate();
				out.write("Group Activated");
			}
			else
			{
				entityManager.createNamedQuery("Groups.UpdateStatus").setParameter("gr_status", "Inactive").setParameter("grId", grId).executeUpdate();
				out.write("Group Deactivated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 
     * this method is used to find list of all activated groups from the database
     * @return           : returns  list of all activated group
     * @since            1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Groups> getAllActiveGroups() {
		return entityManager.createNamedQuery("Groups.getAllActiveGroups").getResultList();
				
	}

	/** 
     * this method is used to find groupId based on group name from the database
     * @param     gr_name:gr_name
     * @return           : returns  groupId
     * @since            1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getGroupIdBasedOnGroupName(String gr_name)
	{
		return entityManager.createNamedQuery("Groups.getGroupIdBasedOnGroupName",
				Integer.class)
				.setParameter("gr_name", gr_name)
				.getResultList();
	}
	
}
