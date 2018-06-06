package com.bcits.bfm.serviceImpl.userManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
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

import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;


/**
 * Implementation Class for implementing methods from service class
 * Module: User Management
 * 
 * @author Shashidhar Bemalgi
 * @version %I%, %G%
 * @since 0.1
 */
@Repository
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService 
{
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RoleServiceImpl.class);
	
	@Autowired
	private UserRoleService userRoleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.RoleService#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> findAllWithoutInvalidDefault() {
		return entityManager.createNamedQuery("Role.findAllWithoutInvalidDefault").getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.RoleService#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getAll() {
		return entityManager.createNamedQuery("Role.findAll").getResultList();
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.RoleService#findById(int)
	 */
	@Override
	public Role findById(int i) {
		Role r = entityManager.find(Role.class, i);
		return r;
	}

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public Role getRoleObject(Map<String, Object> map, String type, Role role) {
		role = getBeanObject(map, type, role);
		return role;
	}

	/**
	 * @param map Details of Role object in key-value pairs
	 * @param type String value Save/Update
	 * @param role Model Details
	 * @return role object
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private Role getBeanObject(Map<String, Object> map, String type, Role role) 
	{	
		
		String userName = (String) SessionData.getUserDetails().get("userID");
	if(type == "save")
	{
		
		role.setRlStatus("Inactive");
		role.setCreatedBy(userName);
	}
		
	else
	{
		role.setRlId((Integer)map.get("rlId"));
		role.setRlStatus((String)map.get("rlStatus"));
		role.setCreatedBy((String)map.get("createdBy"));
	}
		
	
	
	role.setLastUpdatedBy(userName);
	role.setLastUpdatedDate((new Timestamp(new Date().getTime())));
	String desc=(String) map.get("rlDescription");
	String desc_trim=desc.trim();
	role.setRlDescription(desc_trim);
	role.setRlName(WordUtils.capitalizeFully((String) map.get("rlName")));
	return role;
	}
	
	/* (non-Javadoc)
	 * For Checking Rolename Uniqueness
	 * @see com.bcits.bfm.service.userManagement.RoleService#roleNameExistence(com.bcits.bfm.model.Role, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public boolean roleNameExistence(Role r, String type) {
		List<String> roleNames = entityManager
				.createNamedQuery("Role.RoleNameExistence")
				.setParameter("RoleName", r.getRlName()).getResultList();
		
		if (type == "update") {
			if ((roleNames.size() > 0)) {
				if (roleNames.get(0).equals(
						find(r.getRlId()).getRlName()))
					return true;
				else
					return false;
			} else
				return true;

		} else {
			if ((roleNames.size() > 0))
				return false;
			else
				return true;
		}

	}

	
	/* (non-Javadoc)
	 * For Defining the status
	 * @see com.bcits.bfm.service.userManagement.RoleService#setRoleStatus(int, java.lang.String, javax.servlet.http.HttpServletResponse, org.springframework.context.MessageSource, java.util.Locale)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setRoleStatus(int rlId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			if(operation.equalsIgnoreCase("activate"))
			{

				entityManager.createNamedQuery("Role.UpdateStatus").setParameter("rlStatus", "Active").setParameter("rlId", rlId).executeUpdate();
				out.write("Role Activated");
			}
			else
			{
			   entityManager.createNamedQuery("Role.UpdateStatus").setParameter("rlStatus", "Inactive").setParameter("rlId", rlId).executeUpdate();
			   out.write("Role Deactivated");				
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * For Reading active roles
	 * @see com.bcits.bfm.service.userManagement.RoleService#getAllActiveRoles()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Role> getAllActiveRoles() {
		return entityManager.createNamedQuery("Role.getAllActiveRoles").getResultList();
				
	}

	/* (non-Javadoc)
	 * To get roleId based on roleName
	 * @see com.bcits.bfm.service.userManagement.RoleService#getRoleIdBasedOnRoleName(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getRoleIdBasedOnRoleName(String rlName)
	{
		return entityManager.createNamedQuery("Role.getRoleIdBasedOnRoleName",
				Integer.class)
				.setParameter("rlName", rlName)
				.getResultList();
	}
	
	/* (non-Javadoc)
	 * To read size of all the records from roles
	 * @see com.bcits.bfm.service.userManagement.RoleService#count()
	 */
	public int count() {
		return getAll().size();
	}
	
	/* (non-Javadoc)
	 * To read users based on RoleId
	 * @see com.bcits.bfm.service.userManagement.RoleService#getUserIdBasedOnRoleId(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getUserIdBasedOnRoleId(int id) {
		return entityManager.createNamedQuery("UserRole.getUserIdBasedOnRoleId").setParameter("id",id).getResultList();
				
	}

	/* (non-Javadoc)
	 * Getting RoleNAme on roleId
	 * @see com.bcits.bfm.service.userManagement.RoleService#getRoleNameBasedOnId(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getRoleNameBasedOnId(int id) {
		return (String) entityManager.createNamedQuery("Role.getRoleNameBasedOnId")
				.setParameter("id",id)
				.getSingleResult();
	}

	/* (non-Javadoc)
	 * Reading usernames based on userId
	 * @see com.bcits.bfm.service.userManagement.RoleService#getUserNamesBasedOnUserId(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getUserNamesBasedOnUserId(int id) 
	{
		return entityManager.createNamedQuery("Role.getUserNamesBasedOnUserId").setParameter("id", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll(int uid) {
		
		return entityManager.createNamedQuery("Role.findAllRole").setParameter("uid", uid).getResultList();
	}
	

}
