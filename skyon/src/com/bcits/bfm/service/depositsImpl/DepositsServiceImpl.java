package com.bcits.bfm.service.depositsImpl;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Deposits;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class DepositsServiceImpl extends GenericServiceImpl<Deposits> implements DepositsService
{

	@Autowired
	private PropertyService propertyService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Deposits> findAll() {
		return getAllDetails(entityManager.createNamedQuery("Deposits.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> depositsList){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> depositMap = null;
		 for (Iterator<?> iterator = depositsList.iterator(); iterator.hasNext();)
		 {
			 	final Object[] values = (Object[]) iterator.next();
			 	depositMap = new HashMap<String, Object>();
				
			 	depositMap.put("depositsId", (Integer)values[0]);
				depositMap.put("accountNo", (String)values[1]);
				depositMap.put("accountId", (Integer)values[2]);
				depositMap.put("propertyNo", (String)values[3]);
				depositMap.put("totalAmount", (Double)values[4]);
				depositMap.put("status", (String)values[5]);
				depositMap.put("depositType", (String)values[6]);
				depositMap.put("refundAmount", (Double)values[7]);
				depositMap.put("balance", (Double)values[8]);
				
				String personName = "";
				personName = personName.concat((String)values[9]);
				if(((String)values[10])!=null && !((String)values[10]).equals(""))
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[10]);
				}
				depositMap.put("personName", personName);
			
			result.add(depositMap);
		}
      return result;
	}
	
	@Override
	public void setDepositsStatus(int depositsId, String operation,HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			Deposits deposits = find(depositsId);
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Deposits.setDepositsStatus").setParameter("status", "Approved").setParameter("depositsId", depositsId).executeUpdate();
				out.write("Deposits details are approved");
			}
			else if(deposits.getStatus().equals("Posted")) {
				out.write("Deposits details are already posted");
			}else{
				entityManager.createNamedQuery("Deposits.setDepositsStatus").setParameter("status", "Posted").setParameter("depositsId", depositsId).executeUpdate();
				out.write("Deposits details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public List<?> getAllAccountNumbers() {
		return getAccountNumbersData(entityManager.createNamedQuery("Deposits.getAllAccountNumbers").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountNumbersData(List<?> accountNumbers){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> accountNumberMap = null;
		 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				accountNumberMap = new HashMap<String, Object>();
				
				accountNumberMap.put("accountId", (Integer)values[0]);
				accountNumberMap.put("accountNo", (String)values[1]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if(((String)values[4]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				
				accountNumberMap.put("personId", (Integer)values[2]);
				accountNumberMap.put("personName", personName);
				accountNumberMap.put("personType", (String)values[5]);
				accountNumberMap.put("personStyle", (String)values[6]);
				accountNumberMap.put("propertyNo", (String)values[7]);
			
			result.add(accountNumberMap);
	     }
     return result;
	}
	
	@Override
	public List<Deposits> getLastDepositTransactionData(int accountId) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("Deposits.commonFilterForAccountNumbersUrl").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForPropertyNoUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("Deposits.commonFilterForPropertyNoUrl").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Deposits> testUniqueAccount(int accountId){
		return entityManager.createNamedQuery("Deposits.testUniqueAccount").setParameter("accountId", accountId).getResultList();		
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("Deposits.findPersonForFilters").getResultList();
		return details;
	}

}
