package com.bcits.bfm.serviceImpl.userManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ManPowerApproval;
import com.bcits.bfm.service.userManagement.ManPowerApprovalService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ManpowerApprovalServiceImpl extends GenericServiceImpl<ManPowerApproval> implements ManPowerApprovalService{

@Override
	public List<?> readAllUserDetails(int urId) {
		return readDetails(entityManager.createNamedQuery("Users.getAllUsersDetails").setParameter("urId", urId).getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List readDetails(List<?> transactionMaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> transactionMasterData = null;
		for (Iterator<?> iterator = transactionMaster.iterator(); iterator.hasNext();)
		{
			 final ManPowerApproval values = (ManPowerApproval) iterator.next();
			 transactionMasterData = new HashMap<String, Object>();
			 
			 transactionMasterData.put("app_id", values.getApp_id());
			 transactionMasterData.put("approvedBy", values.getApprovedBy());
			 transactionMasterData.put("approveddate", values.getApproveddate());
			 transactionMasterData.put("status", values.getStatus());
			
		     result.add(transactionMasterData); 
		 }
		 return result;
	}
	@Override
	public int getpersonId(int urId) {
		
		return (int) entityManager.createNamedQuery("Users.personId").setParameter("urId", urId).getSingleResult();
	}
	@Override
	public int getreqInLevel(int personid) {
		
		return (int) entityManager.createNamedQuery("Users.getreqInLevel").setParameter("personid",personid).getSingleResult();
	}
	@Override
	public int getLevel(int personid) {
		return (int) entityManager.createNamedQuery("Users.getLevel").setParameter("personid",personid).getSingleResult();
	}
	@Override
	public void updateReqInLevel(int personid,int reqInLevel) {
		entityManager.createNamedQuery("Users.updateReqInLevel").setParameter("personid",personid).setParameter("reqInLevel", reqInLevel).executeUpdate();
		
	}
	@Override
	public void updateUserStatus(int urId,String status) {
		entityManager.createNamedQuery("Users.updateUserStatus").setParameter("status",status).setParameter("urId", urId).executeUpdate();
		
	}
	@Override
	public void deleteApprovalData(int urId) {
		entityManager.createNamedQuery("Users.deleteApprovalData").setParameter("urId", urId).executeUpdate();
		
	}
	@SuppressWarnings("rawtypes")
	@Override
	public int getTransactionId() {
		List results = entityManager.createNamedQuery("TransactionManager.getTransactionId",Integer.class).setParameter("process","Manpower process").getResultList();
		  if(results.isEmpty()){
		   return 0;
		  }
		  return (int)results.get(0);
	}
	
	@Override
	public void updatePersonTransId(int urId, int transId) {
		int level=entityManager.createNamedQuery("TransactionManager.getTransactionLevel",Integer.class).setParameter("transId", transId).getSingleResult();
		int levels=0;
		if(level!=0){
			levels=1;
		}
		entityManager.createNamedQuery("Person.updateTransId").setParameter("urId", urId).setParameter("reqInLevel", levels).setParameter("transId",transId).executeUpdate();
		
	}
	
	
}
