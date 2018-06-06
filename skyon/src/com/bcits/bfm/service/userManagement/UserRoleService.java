package com.bcits.bfm.service.userManagement;

import java.util.List;



import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.service.GenericService;

public interface UserRoleService extends GenericService<UserRole> {

	public UserRole findById(int id);

	/*public List<UserRole> getAll();*/

	public List<UserRole> getRlId(int urId);

	public String checkUser(int id, int roleId);

	public void AddUser(UserRole ug);

	public void DeleteUser(int roleId, int userId);

	@SuppressWarnings("rawtypes")
	public List SelectRole(int gid);
	public List<UserRole> getRoleNamelist(int userID);

	public List<UserRole> getUserRoleInstanceBasedOnUserIdAndRoleId(int urId,
			int uro_Rl_Id);
	
	public List<Role> getActiveUsers();

	public int setAllPrevRolesBasedOnRoleId(int rlId);

	public void removeAllRedundantUserRoleRecords();	

}