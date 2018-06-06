package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AdjustmentComponentsEntity;
import com.bcits.bfm.model.PaymentAdjustmentControlEntity;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentControlService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdjustmentControlServiceImpl extends GenericServiceImpl<PaymentAdjustmentControlEntity> implements AdjustmentControlService {

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentAdjustmentControlEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("PaymentAdjustmentControlEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> adjustmentEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = adjustmentEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("pacId", (Integer)values[0]);
				paymentMap.put("pacDate", (Date)values[1]);
				paymentMap.put("typeOfService", (String)values[2]);
				paymentMap.put("status", (String)values[3]);
				paymentMap.put("accountId", (Integer)values[4]);
				paymentMap.put("accountNo", (String)values[5]);
			
			result.add(paymentMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentAdjustmentControlEntity> getAdjustmentId() {
		return entityManager.createNamedQuery("PaymentAdjustmentControlEntity.getAdjustmentId").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdjustmentComponentsEntity> getAdjusmentCalcItemList(int adjustmentId) {
		return entityManager.createNamedQuery("PaymentAdjustmentControlEntity.getAdjusmentCalcItemList").setParameter("adjustmentId", adjustmentId).getResultList();
	}
	
	@Override
	public List<?> getAllPaidAccountNumbers() {
		return getAccountNumbersData(entityManager.createNamedQuery("PaymentAdjustmentControlEntity.getAllPaidAccountNumbers").getResultList());
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
			
			result.add(accountNumberMap);
	     }
     return result;
	}

	@Override
	public void setAdjustmentControlStatus(int pacId, String operation,	HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			PaymentAdjustmentControlEntity adjustmentControlEntity = find(pacId);
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("PaymentAdjustmentControlEntity.setAdjustmentControlStatus").setParameter("status", "Approved").setParameter("pacId", pacId).executeUpdate();
				out.write("Payment adjustment control details are approved");
			}
			else if(adjustmentControlEntity.getStatus().equals("Posted")) {
				out.write("Payment adjustment control details are already posted");
			}else{
				entityManager.createNamedQuery("PaymentAdjustmentControlEntity.setAdjustmentControlStatus").setParameter("status", "Posted").setParameter("pacId", pacId).executeUpdate();
				out.write("Payment adjustment control details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<?> findServiceTypes() {
		List<?> serviceTypeList = entityManager.createNamedQuery("PaymentAdjustmentControlEntity.findServiceTypes").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> propertyNumberMap = null;
		for (Iterator<?> iterator = serviceTypeList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				propertyNumberMap = new HashMap<String, Object>();				
								
				propertyNumberMap.put("accountId", (Integer)values[0]);	
				propertyNumberMap.put("accountNo", (String)values[1]);	
				propertyNumberMap.put("typeOfService",(String)values[2]);				
			
			result.add(propertyNumberMap);
	     }
     return result;
	}

}
