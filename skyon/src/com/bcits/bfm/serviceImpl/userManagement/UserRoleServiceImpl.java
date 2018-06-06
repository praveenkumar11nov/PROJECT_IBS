package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

/**
 * UserRoleService implementation class for its interface 
 * @author Shashidhar Bemalgi
 *
 */
@Repository
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements
		UserRoleService {

	/* (non-Javadoc)
	 * read userrole on id
	 * @see com.bcits.bfm.service.userManagement.UserRoleService#findById(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public UserRole findById(int id) {
		UserRole r = entityManager.find(UserRole.class, id);
		return r;
	}

	/*
	 * To read roleId based on userId
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserRoleService#getRlId(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<UserRole> getRlId(int urId) {
		return entityManager.createNamedQuery("UserRole.getUrRlId")
				.setParameter("roleId", urId).getResultList();
	}

	/*
	 * To check user existence in role
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserRoleService#checkUser(int,
	 * int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String checkUser(int id, int roleId) {
		BfmLogger.logger.info("coming to checkUser");
		List<UserRole> list = entityManager.createNamedQuery("Role.CheckUser")
				.setParameter("id", id).setParameter("roleId", roleId)
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
	 * For assigning User to Role
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserRoleService#AddUser(com.
	 * bcits.bfm.model.UserRole)
	 */
	@Override
	public void AddUser(UserRole ug) {
		entityManager.persist(ug);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserRoleService#DeleteUser(int,
	 * int)
	 */
	@Override
	public void DeleteUser(int roleId, int userId) {
		entityManager.createNamedQuery("Role.DeleteUser")
				.setParameter("roleId", roleId).setParameter("userId", userId)
				.executeUpdate();
	}

	/*
	 * Read role based on groupId
	 * (non-Javadoc)
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.UserRoleService#SelectRole(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List SelectRole(int gid) {
		List g = entityManager.createNamedQuery("SelectRole")
				.setParameter("gid", gid).getResultList();
		BfmLogger.logger.info("Inside====>" + g);
		return g;
	}
	
	
	/*
	* To read list of roles based on userId
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserRoleService#getRoleNamelist(int)
	*/
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<UserRole> getRoleNamelist(int userID) {

		return entityManager.createNamedQuery("rolesDetailsById")
				.setParameter("userID", userID).getResultList();

	}

	/*
	* Users based on userId & groupId
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserRoleService#getUserRoleInstanceBasedOnUserIdAndRoleId(int, int)
	*/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<UserRole> getUserRoleInstanceBasedOnUserIdAndRoleId(int urId,int uro_Rl_Id)
	{
		return entityManager.createNamedQuery("UserRole.getUserRoleInstanceBasedOnUserIdAndRoleId",UserRole.class).setParameter("uro_Ur_Id", urId)
				.setParameter("uro_Rl_Id", uro_Rl_Id).getResultList();
	}

	/*
	* To read all active users
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserRoleService#getActiveUsers()
	*/
	@Override
	public List<Role> getActiveUsers() {
		return entityManager.createNamedQuery("Role.getActiveRoles",Role.class).getResultList();
	}

	/*
	* Read roles based on roleId 
	* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserRoleService#setAllPrevRolesBasedOnRoleId(int)
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int setAllPrevRolesBasedOnRoleId(int rlId)
	{
		 return entityManager.createNamedQuery("UserRole.setAllPrevRolesBasedOnRoleId").setParameter("roleId", rlId).executeUpdate();
	}
	
	/* (non-Javadoc)
	* @see com.bcits.bfm.service.userManagement.UserRoleService#removeAllRedundantUserRoleRecords()
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeAllRedundantUserRoleRecords()
	{
		 entityManager.createNamedQuery("UserRole.removeAllRedundantUserRoleRecords")
				 .executeUpdate();
	}
}
