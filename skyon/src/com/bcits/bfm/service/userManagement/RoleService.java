package com.bcits.bfm.service.userManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.service.GenericService;

/**
 * Service Classes which consists of abstract methods
 * Module: User Management
 * 
 * @author Shashidhar Bemalgi
 * @version %I%, %G%
 * @since 0.1
 */
public interface RoleService extends GenericService<Role>
{
	/**
	* @return Role Object
	*/
	public List<Role> getAll();
	
	
	/**
	* Reading Role details based on roleId
	* @param i roleId
	* @return
	*/
	public Role findById(int i);
	
	
	/**
	* Role Object with new role details to persist/merge the role details 
	* @param map Details of Role
	* @param type String as Save/Update
	* @param role Model Class
	* @return role Object
	*/
	public Role getRoleObject(Map<String, Object> map, String type, Role role);
		
	/**
	* Checking RoleName Existence 
	* @param role Model Class
	* @param type String as Save/update
	* @return boolen true/false
	*/
	public boolean roleNameExistence(Role role, String type);
	
	/**
	* For Ativating & De-activating the role status 
	* @param rlId roleID 
	* @param operation save(persist)/update(merge)
	* @param response none
	* @param messageSource to read from property file
	* @param locale System locale
	*/
	public void setRoleStatus(int rlId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	
	/**
	* Role Object 
	* @return role object
	*/
	public List<Role> findAllWithoutInvalidDefault();
	
	/**
	* To Read Only active roles
	* @return active roles object
	*/
	public List<Role> getAllActiveRoles();
	
	/**
	* To get roleId based on roleName 
	* @param string roleName
	* @return roleId
	*/
	public List<Integer> getRoleIdBasedOnRoleName(String string);
	
	/**
	* read total records	 
	* @return
	*/
	public int count();
	
	
	/**
	* @param id roleId
	* @return userId
	*/
	public List<?> getUserIdBasedOnRoleId(int id);
	
	/**
	* Read rolename based on roleId	 
	* @param id roleId
	* @return roleName
	*/
	public String getRoleNameBasedOnId(int id);
	
	/**
	* To read usernames based on userId
	* @param id userId
	* @return username
	*/
	public List<String> getUserNamesBasedOnUserId(int id);


	public List<Role> getAll(int rid);	
}