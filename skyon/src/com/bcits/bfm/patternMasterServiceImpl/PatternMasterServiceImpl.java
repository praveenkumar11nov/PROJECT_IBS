package com.bcits.bfm.patternMasterServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.patternMasterService.PatternMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

public class PatternMasterServiceImpl extends GenericServiceImpl<TransactionMaster> implements PatternMasterService {
	
	
	
	@SuppressWarnings("rawtypes")
	private List readTransactionMaster(List<?> transactionMaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> transactionMasterData = null;
		for (Iterator<?> iterator = transactionMaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 transactionMasterData = new HashMap<String, Object>();
			/* String s1=(String)values[1];
			 String s2=s1.substring(0, 1).toUpperCase()+s1.substring(s1.length()-1).toUpperCase()+"_"+(Integer)values[0];*/
			 
			 transactionMasterData.put("tId", (Integer)values[0]);
			 transactionMasterData.put("name",(String)values[1]);		
			 transactionMasterData.put("code",(String)values[2]);
			 transactionMasterData.put("description", (String)values[3]);
			 transactionMasterData.put("level", (Integer)values[4]);
			 transactionMasterData.put("transactionStatus", (String)values[5]);
			 transactionMasterData.put("processId", (Integer)values[6]);
			 transactionMasterData.put("processName", (String)values[7]);
		     result.add(transactionMasterData); 
		 }
		 return result;
	}

	@Override
	public List<?> exicuteDistinctDepartmentsQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionMaster> findAll() {
		return readTransactionMaster(entityManager.createNamedQuery("TransactionMaster.findAlls").getResultList());
	}

	@Override
	public void updateDepartmentStatus(int deptId, String operation,
			HttpServletResponse response, MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public String executeGetDeptNameBasedOnId(int deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAllDepartment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionMaster> findAllTransactionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTransactionStatus(int tId, String operation,
			HttpServletResponse response, MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<?> findLevel(int tId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> readTransactionNames(int officeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findAllDesignations(int tId, int officeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> readProcessIdForUniqueness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> readDesignamtionNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
