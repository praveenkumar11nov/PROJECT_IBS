package com.bcits.bfm.service.userManagement;

import java.util.List;

import com.bcits.bfm.model.Permission;
import com.bcits.bfm.service.GenericService;

public interface PermissionService extends GenericService<Permission> {

	// find product by id
	public Permission findById(int id);

	public List<Permission> getAll();

	// geting permission id
	public List<Permission> getAllPerm(int roleId);

	// get task id
	public List<Permission> getTaskId();

	public int count();

	// get permission count depending on task id
	public int getPermissionCount(int tkId);

	public List<Permission> getAll(int tkId);

}