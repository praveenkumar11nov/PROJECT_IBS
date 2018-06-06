package com.bcits.bfm.serviceImpl.bankPayments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.BankRemittance;
import com.bcits.bfm.service.bankPayments.BankRemittanceService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class BankRemittanceServiceimpl extends GenericServiceImpl<BankRemittance> implements BankRemittanceService
{
	@SuppressWarnings("unchecked")
	@Override
	public List<BankRemittance> findAll() 
	{
		List<BankRemittance> bankRemittance = entityManager.createNamedQuery("BankRemittance.findAll").getResultList();
		return bankRemittance;
	}
	
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<BankRemittance> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final BankRemittance record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{				
				put("brId", record.getBrId());
	            put("bankName", record.getBankName());            	
	            put("accountNo", record.getAccountNo());				
	            put("txDate", record.getTxDate());
	            put("description", record.getDescription());
	            put("credit", record.getCredit()); 
	            put("status", record.getStatus());	 
	            put("createdBy", record.getCreatedBy());
	            put("lastUpdatedBy", record.getLastUpdatedBy());	            				
		   }});
	    }
		return response;
	}
}
