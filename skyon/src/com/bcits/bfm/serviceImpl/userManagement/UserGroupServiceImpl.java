package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;





import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.service.userManagement.UserGroupService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * UserGroupServiceImpl an Implementation class for its interface 
 * @author Shashidhar Bemalgi
 *
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserGroupServiceImpl extends GenericServiceImpl<UserGroup> implements UserGroupService 
{
	
	/* (non-Javadoc)
	 * read usergroup on id
	 * @see com.bcits.bfm.service.userManagement.UserGroupService#findById(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserGroup> findById(int id) {
		List<UserGroup> st1 = entityManager
				.createNamedQuery("ManageUserGroups.findById")
				.setParameter("UG_GR_ID", id).getResultList();
		return st1;
	}

	/*
	 * For Checking user existence to particular group
	 * (non-Javadoc) 
	 * @see com.bcits.bfm.serviceImpl.userManagement.UserGroupService#checkUser(int,
	 * int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String checkUser(int id, int gropuid) {
		List<UserGroup> list = entityManager.createNamedQuery("CheckUser")
				.setParameter("id", id).setParameter("groupid", gropuid)
				.getResultList();
		String status = "";
		if (list.size() > 0) {
			status = "Exists";
		} else {
			status = "NotExists";
		}
		return status;
	}

	/*
	 * For Assigning the user to group
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserGroupService#AddUser(com
	 * .bcits.bfm.model.UserGroup)
	 */
	@Override
	public void AddUser(UserGroup ug) {
		entityManager.persist(ug);
	}

	/*
	 * For Unassigning the user from group
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserGroupService#DeleteUser(int,
	 * int)
	 */
	@Override
	public void DeleteUser(int groupId, int userId) {
		entityManager.createNamedQuery("DeleteUser")
				.setParameter("groupId", groupId)
				.setParameter("userId", userId).executeUpdate();
	}

	/*
	 * For updating the usergroup with userId,groupId,Status
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserGroupService#UpdateUser(
	 * java.lang.String, int, int)
	 */
	@Override
	public String UpdateUser(String status, int groupId, int userId) {
		entityManager.createNamedQuery("UpdateUser")
				.setParameter("status", status)
				.setParameter("groupId", groupId)
				.setParameter("userId", userId).executeUpdate();
		return status;
	}

	/*
	 * For selecting the group on groupId & userId
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserGroupService#SelectGroup
	 * (int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List SelectGroup(int gid) {
		List g = entityManager.createNamedQuery("SelectGroup")
				.setParameter("gid", gid).getResultList();
		return g;
	}
	
	/*
	 * To read group names 
	 * (non-Javadoc)
	 * @see com.bcits.bfm.service.userManagement.UserGroupService#getGroupNamelist(int)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<UserGroup> getGroupNamelist(int userID) {

		return entityManager.createNamedQuery("allDetailsById")
				.setParameter("userID", userID).getResultList();

	}
	
	/*
	 * Read users with userId & groupId
	 * (non-Javadoc)
	 * @see com.bcits.bfm.service.userManagement.UserGroupService#findUsersIdName()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List findUsersIdName() {
		return entityManager.createNamedQuery("Users.getUserIdName").getResultList();
	}

	/*
	* Read usergroup detials based on userId and groupId
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserGroupService#getUserGroupInstanceBasedOnUserIdAndGroupId(int, int)
	*/
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<UserGroup> getUserGroupInstanceBasedOnUserIdAndGroupId(int urId,
			int UG_GR_ID)
	{
		return entityManager.createNamedQuery("UserGroup.getUserGroupInstanceBasedOnUserIdAndGroupId",
				UserGroup.class)
				.setParameter("UG_UR_ID", urId)
				.setParameter("UG_GR_ID", UG_GR_ID)
				.getResultList();
	}

	/*
	 * For Reading active groups
	 * (non-Javadoc)
	 * @see com.bcits.bfm.service.userManagement.UserGroupService#getActiveGroups()
	 */
	@Override
	public List<Groups> getActiveGroups() {
			return entityManager.createNamedQuery("Group.getActiveGroups",Groups.class).getResultList();
		}
	
	
	/*
	* Reading all the groups based on groupId
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserGroupService#setAllPrevGroupsBasedOnGroupId(int)
	*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int setAllPrevGroupsBasedOnGroupId(int gr_id)
	{
		 return entityManager.createNamedQuery("UserGroup.setAllPrevGroupsBasedOnGroupId")
				 .setParameter("groupId", gr_id)
				 .executeUpdate();
	}
	
	/*
	* 
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserGroupService#removeAllRedundantUserGroupRecords()
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeAllRedundantUserGroupRecords()
	{
		 entityManager.createNamedQuery("UserGroup.removeAllRedundantUserGroupRecords")
				 .executeUpdate();
	}
}
