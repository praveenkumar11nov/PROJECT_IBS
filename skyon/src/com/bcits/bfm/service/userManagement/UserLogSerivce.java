package com.bcits.bfm.service.userManagement;

import java.util.Date;
import java.util.List;

import com.bcits.bfm.model.UserLog;
import com.bcits.bfm.service.GenericService;

public interface UserLogSerivce  extends GenericService<UserLog> {
	
	public int updateUserDetails(String logoutmethod,String SessionId,Date SessionEnd, String duration);
	
	public abstract List<UserLog> findAllUserLogDetails(String userName);

	List<UserLog> findAll();

	public UserLog findbySessionId(String id);

	public Long getCount(String sessionId);
}