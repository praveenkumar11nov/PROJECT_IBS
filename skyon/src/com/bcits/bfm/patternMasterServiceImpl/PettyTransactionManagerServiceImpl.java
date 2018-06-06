package com.bcits.bfm.patternMasterServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.patternMasterEntity.PettyTransactionManager;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.patternMasterService.PettyTransactionManagerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PettyTransactionManagerServiceImpl extends GenericServiceImpl<PettyTransactionManager> implements PettyTransactionManagerService{

	@Override
	public List<?> readAllProcessNames() {
		return readAllProcessNames(entityManager.createNamedQuery("TransactionManager.readAllProcessNames").getResultList());
	}
	private List<?> readAllProcessNames(List<?> resultList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> processMasterData = null;
		
		for (Iterator<?> iterator = resultList.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 processMasterData = new HashMap<String, Object>();
			 
			
			 processMasterData.put("processId",(Integer)values[0]);
			 processMasterData.put("processName",(String)values[1]);
		     result.add(processMasterData); 
		 }
		 return result;
	}
	@Override
	public List<?> getTransactionType() {
		return readAllPatternNames(entityManager.createNamedQuery("TransactionManager.readAllPatternNames").getResultList());
	}
	private List<?> readAllPatternNames(List<?> resultList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> processMasterData = null;
		
		for (Iterator<?> iterator = resultList.iterator(); iterator.hasNext();)
		{
			 final TransactionMaster values = (TransactionMaster) iterator.next();
			 processMasterData = new HashMap<String, Object>();
			 
			
			 processMasterData.put("tId",values.gettId());
			 processMasterData.put("name",values.getName());
		//	 processMasterData.put("processId",values.getProcessId());

			 
		     result.add(processMasterData); 
		 }
		 return result;
	}
	@Override
	public List<?> findall() {
		return readAllPatternName(entityManager.createNamedQuery("pettytransactionmanager.readAll").getResultList());
	}
	
	@SuppressWarnings("null")
	private List<?> readAllPatternName(List<?> resultList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> transactionMasterData = null;
		
		for (Iterator<?> iterator = resultList.iterator(); iterator.hasNext();)
		{
			 final PettyTransactionManager values = (PettyTransactionManager) iterator.next();
			 transactionMasterData.put("tId",values.getTransactionMaster().gettId());
			 transactionMasterData.put("name",values.getTransactionMaster().getProcessName());		
			 transactionMasterData.put("code",values.getTransactionMaster().getCode());
			 transactionMasterData.put("description",values.getTransactionMaster().getDescription());
			 transactionMasterData.put("level",values.getTransactionMaster().getLevel());
			 transactionMasterData.put("transactionStatus",values.getTransactionMaster().getTransactionStatus());

			// transactionMasterData.put("processId",values.getTransactionMaster().getProcessId());
		     result.add(transactionMasterData); 
		 }
		 return result;
	}
	
	@Override
	public List<?> readTransactionManager() {
		
		return readAllRequest(entityManager.createNamedQuery("TransactionManager.findTransaction").getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List readAllRequest(List<?> transactionManager)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object>transactionManagerData = null;
		for (Iterator<?> iterator = transactionManager.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 transactionManagerData = new HashMap<String, Object>();
			 transactionManagerData.put("transManageId", (Integer)values[0]);
			 transactionManagerData.put("tId",(Integer)values[1]);		
			 transactionManagerData.put("name",(String)values[2]);
			 transactionManagerData.put("transCode",(String)values[3]);
			 transactionManagerData.put("transLevel",(Integer)values[4]);
			 transactionManagerData.put("transDescription",(String)values[5]);
	
			
			 transactionManagerData.put("processName",(String)values[6]);
			// transactionManagerData.put("processId",(Integer)values[7]);
			
		     result.add(transactionManagerData); 
		 }
		 return result;
	}
}
