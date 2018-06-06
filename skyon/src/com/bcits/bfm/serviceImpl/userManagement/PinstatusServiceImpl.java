package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Pinstatus;
import com.bcits.bfm.service.userManagement.PinstatusService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PinstatusServiceImpl extends GenericServiceImpl<Pinstatus>  implements PinstatusService
{
	@SuppressWarnings("unchecked")
	public String checkUser(String userid, int pinstatus) {
		List<Pinstatus> list = entityManager.createNamedQuery("Pinstatus.getStatus")
				.setParameter("userid", userid)
				.getResultList();
		int status = 2;
		if (list.size() > 0) {
			status = list.get(0).getPinstatus();
		} else {
			status = 0;
		}
		return status+"";
	}

	@SuppressWarnings("unchecked")
	public String checkUserExists(String userid, int pinstatus) {
		List<Pinstatus> list = entityManager.createNamedQuery("Pinstatus.checkUserExists")
				.setParameter("userid", userid)
				.getResultList();
		String status = "";
		if (list.size() > 0) {
			status = "Exists";
		} else {
			status = "NotExists";
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUserTheme(String userId) 
	{
		List<String> list =   entityManager.createQuery("SELECT a.theme FROM Pinstatus a WHERE a.userid='"+userId+"'").getResultList();
		if(!list.isEmpty())
		{
			return list.get(0);
		}
		else
		{
			return "default";
		}
		
	}
	

}
