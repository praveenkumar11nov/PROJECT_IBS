package com.bcits.bfm.service.userManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.service.GenericService;

public interface GroupsService extends GenericService<Groups> {

	// get all Groupss
	public List<Groups> getAll();

	public int count();

	public List<Groups> getByName(String gname);

	public List<Groups> checkNameExistence(Groups groups);

	public List<UserGroup> findAll();

	public List<UserGroup> findById(int id);

	public boolean checkGroupUnique(Groups groups, String type);

	public Groups getGroupObject(Map<String, Object> map, String type,Groups groups);

	public void AddUser(UserGroup ug);

	public void DeleteUser(int groupId, int userId);
	
	public void setGroupStatus(int grId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);

	public List<Groups> getGroupNames();

	public List<Groups> findAllWithoutInvalidDefault();

	public List<Groups> getAllActiveGroups();

	public List<Integer> getGroupIdBasedOnGroupName(String string);
	
	
	
}