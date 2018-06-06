package com.bcits.bfm.service.advanceCollectionImpl;

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

import com.bcits.bfm.model.AdvanceCollectionEntity;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdvanceCollectionServiceImpl extends GenericServiceImpl<AdvanceCollectionEntity> implements AdvanceCollectionService
{
	@Autowired
	private PropertyService propertyService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdvanceCollectionEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("AdvanceCollectionEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> advanceCollectionList){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = advanceCollectionList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();		
				
				paymentMap.put("advCollId", (Integer)values[0]);
				paymentMap.put("accountNo", (String)values[1]);
				paymentMap.put("accountId", (Integer)values[2]);
				paymentMap.put("propertyNo", (String)values[3]);
				paymentMap.put("totalAmount", (Double)values[4]);
				paymentMap.put("status", (String)values[5]);
				paymentMap.put("amountCleared",(Double)values[6]);
				paymentMap.put("balanceAmount",(Double)values[7]);
				paymentMap.put("transactionName",(String)values[8]);

				String personName = "";
				personName = personName.concat((String)values[9]);
				if((String)values[10] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[10]);
				}
				paymentMap.put("personName", personName);
				
							
			result.add(paymentMap);
	     }
      return result;
	}
	
	@Override
	public List<?> getAllAccountNumbers() {
		return getAccountNumbersData(entityManager.createNamedQuery("AdvanceCollectionEntity.getAllAccountNumbers").getResultList());
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
	public void setAdvanceCollectionStatus(int advCollId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			AdvanceCollectionEntity advanceCollectionEntity = find(advCollId);
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Deposits.setDepositsStatus").setParameter("status", "Approved").setParameter("advCollId", advCollId).executeUpdate();
				out.write("Advance Collection details are approved");
			}
			else if(advanceCollectionEntity.getStatus().equals("Posted")) {
				out.write("Advance Collection details are already posted");
			}else{
				entityManager.createNamedQuery("Deposits.setDepositsStatus").setParameter("status", "Posted").setParameter("advCollId", advCollId).executeUpdate();
				out.write("Advance Collection details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("AdvanceCollectionEntity.commonFilterForAccountNumbersUrl").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForPropertyNoUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("AdvanceCollectionEntity.commonFilterForPropertyNoUrl").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForTransactionNameUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("AdvanceCollectionEntity.commonFilterForTransactionNameUrl").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdvanceCollectionEntity> testUniqueAccount(int accountId){
		return entityManager.createNamedQuery("AdvanceCollectionEntity.testUniqueAccount").setParameter("accountId", accountId).getResultList();		
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("AdvanceCollectionEntity.findPersonForFilters").getResultList();
		return details;
	}

}
