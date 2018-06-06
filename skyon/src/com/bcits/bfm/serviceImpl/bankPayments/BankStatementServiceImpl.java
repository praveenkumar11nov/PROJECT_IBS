package com.bcits.bfm.serviceImpl.bankPayments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bcits.bfm.model.BankStatement;
import com.bcits.bfm.service.bankPayments.BankStatementService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class BankStatementServiceImpl extends GenericServiceImpl<BankStatement> implements BankStatementService
{
	@SuppressWarnings("unchecked")
	@Override
	public List<BankStatement> findAll() 
	{
		List<BankStatement> bankStatement = entityManager.createNamedQuery("BankStatement.findAll").getResultList();
		return bankStatement;
	}
	
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<BankStatement> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final BankStatement record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{				
				put("bsId", record.getBsId());
	            put("bankName", record.getBankName());            	
	            put("accountNo", record.getAccountNo());
	            
	            java.util.Date dt = record.getTxDate();
				java.sql.Date amcDateSql = new java.sql.Date(dt.getTime());
				
	            put("txDate", amcDateSql);
	            put("description", record.getDescription());
	            put("credit", record.getCredit());
	            put("debit", record.getDebit());	            
	            put("balance", record.getBalance());	 
	            put("status", record.getStatus());	 
	            put("createdBy", record.getCreatedBy());
	            put("lastUpdatedBy", record.getLastUpdatedBy());	            				
		   }});
	    }
		return response;
	}

}
