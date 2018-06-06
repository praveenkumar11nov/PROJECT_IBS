package com.bcits.bfm.service.userManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.Permission;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.Tasks;
import com.bcits.bfm.service.GenericService;

public interface TaskDefService extends GenericService<Tasks> {

	public Tasks update(Map<String, Object> map, Tasks tasks);

	public Tasks findById(int tkId);

	public List<Tasks> getAll();

	public List<Tasks> getTaskId();

	public int count();

	public List<Role> getRoleNames(int taskId);

	public List<Permission> getTaskRolesBasedOntaskId(int taskId);

	public Tasks getChildObjects(Map<String, Object> map, String type,
			Tasks tasks);

	/** Used for insertion and updation purposes */
	public Tasks getBeanObject(Map<String, Object> map, String type, Tasks tasks);

}