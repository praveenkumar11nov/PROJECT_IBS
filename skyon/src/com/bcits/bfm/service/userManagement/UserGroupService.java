package com.bcits.bfm.service.userManagement;

import java.util.List;



import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;

/**
 * UserGroupService interface Class
 * @author Shashidhar Bemalgi
 *
 */
public interface UserGroupService extends GenericService<UserGroup>
{	
	public List<Users> findUsersIdName();

	/**
	* Reading Group details based on groupId
	* @param i groupId
	* @return
	*/
	public List<UserGroup> findById(int id);
	

	/**
	* Checking user existence based on groupId
	* @param id
	* @param gropuid
	* @return
	*/
	public String checkUser(int id, int gropuid);

	
	/**
	* For Assigning User to groups
	* @param ug Usergroup Object
	*/
	public void AddUser(UserGroup ug);

	
	/**
	* for Unassigning the user from group
	* @param groupId
	* @param userId
	*/
	public void DeleteUser(int groupId, int userId);

	
	/**
	* For upadating active/inactive users
	* @param status active/inactive
	* @param groupId
	* @param userId
	* @return
	*/
	public String UpdateUser(String status, int groupId, int userId);
	
	/**
	* To read active groups
	* @return group object
	*/
	public List<Groups> getActiveGroups();	

	/**
	* To select group based on groupId
	* @param gid groupId
	* @return
	*/
	@SuppressWarnings("rawtypes")
	public List SelectGroup(int gid);
	
	/**
	* To read groupnames based on userId
	* @param userID
	* @return Usergroup object
	*/
	public List<UserGroup> getGroupNamelist(int userID);	

	/**
	* @param urId userId
	* @param UG_GR_ID usergroupGroupId
	* @return Usergroup object
	*/
	public List<UserGroup> getUserGroupInstanceBasedOnUserIdAndGroupId(int urId,int UG_GR_ID);

	
	/**
	* Reading group names based on groupId
	* @param gr_id groupId
	* @return
	*/
	public int setAllPrevGroupsBasedOnGroupId(int gr_id);

	
	/**
	* Removing all reduduntUserGroups
	*/
	public void removeAllRedundantUserGroupRecords();
}