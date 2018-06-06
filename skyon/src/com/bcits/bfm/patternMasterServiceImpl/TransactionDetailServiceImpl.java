package com.bcits.bfm.patternMasterServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.patternMasterEntity.TransactionDetail;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.patternMasterService.TransactionDetailService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TransactionDetailServiceImpl extends GenericServiceImpl<TransactionDetail> implements TransactionDetailService{

	@SuppressWarnings("serial")
	@Override
	public List<?> findAll(int tId) {
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		@SuppressWarnings("unchecked")
		List<TransactionDetail> transctionDataList=entityManager.createNamedQuery("TransctionDetail.findAll").setParameter("tId", tId).getResultList();
		for (final TransactionDetail transctionDetail :transctionDataList)
		{
			result.add(new HashMap<String, Object>() {	 
				
				
				{  
					put("id", transctionDetail.getId());
					put("code",transctionDetail.getTransactioMaster().getCode());
					put("tId",transctionDetail.getTransactioMaster().gettId());
					put("dept_Name",transctionDetail.getTransactioMaster().getProcessName());
					put("dnId",transctionDetail.getDesignation().getDn_Id());
					put("dn_Name",transctionDetail.getDesignation().getDn_Name());
					put("lNum",transctionDetail.getlNum());
					put("dept_Id",transctionDetail.getDepartment().getDept_Id());
					put("dept_Name",transctionDetail.getDepartment().getDept_Name());
					
					
				}});
				
			}
			return result;	
	}

	@Override
	public List<?> finddataforNotification(int did) {
		return entityManager.createNamedQuery("Users.getDataForNotification").setParameter("did", did).getResultList();

		
	}

	@Override
	public List<?> getAllTransactionUniqueness() {
		return entityManager.createNamedQuery("TransactionMaster.findTransactionNameUniqueness").getResultList();
	}
}
