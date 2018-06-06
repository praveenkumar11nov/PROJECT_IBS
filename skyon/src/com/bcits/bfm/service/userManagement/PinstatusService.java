package com.bcits.bfm.service.userManagement;

import com.bcits.bfm.model.Pinstatus;
import com.bcits.bfm.service.GenericService;

public interface PinstatusService extends GenericService<Pinstatus> 
{
	public String checkUser(String userid, int pinstatus);
	public String checkUserExists(String userid, int pinstatus);
	public String getUserTheme(String userId);
}
