package com.bcits.bfm.service.depositsImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
import com.bcits.bfm.model.DepositsLineItems;
import com.bcits.bfm.service.deposits.DepositsLineItemsService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class DepositsLineItemsServiceImpl extends GenericServiceImpl<DepositsLineItems> implements DepositsLineItemsService
{
	
	@Autowired
	private DepositsService depositsService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DepositsLineItems> findAll(int depositsId)
	{
		return getAllDetails(entityManager.createNamedQuery("DepositsLineItems.findAll").setParameter("depositsId", depositsId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("ddId", (Integer)values[0]);
				paymentMap.put("typeOfServiceDeposit", (String)values[1]);
				paymentMap.put("transactionCode", (String)values[2]);
				paymentMap.put("transactionName", (String)values[8]);
				paymentMap.put("category", (String)values[3]);
				paymentMap.put("amount", (Double)values[4]);
				paymentMap.put("collectionType", (String)values[5]);
				paymentMap.put("instrumentNo", (String)values[6]);
				paymentMap.put("instrumentDate", (Date)values[7]);
				paymentMap.put("paymentMode", (String)values[9]);
				paymentMap.put("bankName", (String)values[10]);
				paymentMap.put("depositsId", (Integer)values[11]);
				paymentMap.put("status", (String)values[12]);
				paymentMap.put("loadChangeUnits", (Double)values[13]);
				paymentMap.put("notes", (String)values[14]);
			
			result.add(paymentMap);
	     }
    return result;
	}
	
	@Override
	public List<?> getTransactionCodes() {
		return entityManager.createNamedQuery("DepositsLineItems.getTransactionCodes").getResultList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getTransactionCodeList(String typeOfService) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 for (Iterator<?> iterator = entityManager.createNamedQuery("DepositsLineItems.getTransactionCodeList").setParameter("typeOfService", typeOfService).getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("transactionCode", (String)values[0]);	
				serviceTypeMap.put("transactionName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
     return result;
	}

	@Override
	public double getAllLineItemsAmount(int depositsId) {
		try{
		return (double)entityManager.createNamedQuery("DepositsLineItems.getAllLineItemsAmount").setParameter("depositsId", depositsId).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public Deposits getDepositsObjByLineItemId(int ddId) {
		return (Deposits)entityManager.createNamedQuery("DepositsLineItems.getDepositsObjByLineItemId").setParameter("ddId", ddId).getSingleResult();
	}

	@Override
	public void refundAmountButtonClick(int ddId, HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();			
			DepositsLineItems depositsLineItem = find(ddId);
			Deposits deposits = depositsLineItem.getDeposits();
			if(depositsLineItem.getStatus()!=null && depositsLineItem.getStatus().equalsIgnoreCase("Refund")){
				out.write("Already deposit money refunded");
			}else {
				depositsLineItem.setStatus("Refund");
				deposits.setRefundAmount(deposits.getRefundAmount()+depositsLineItem.getAmount());
				deposits.setBalance(deposits.getTotalAmount()-deposits.getRefundAmount());
				update(depositsLineItem);
				
				out.write("Deposit money refunded succefully");
				depositsService.update(deposits);
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Integer> getSanctionedLoadDetails(int accountId) {
		Set<Integer> set = new HashSet<Integer>(entityManager.createNamedQuery("DepositsLineItems.getSanctionedLoadDetails").setParameter("accountId", accountId).getResultList());
		return set;
	}

	@Override
	public float getRateForDepositFromRateSlab(int accountId, String rateName) {
		return (Float)entityManager.createNamedQuery("DepositsLineItems.getRateForDepositFromRateSlab").setParameter("accountId", accountId).setParameter("rateName", rateName).getSingleResult();
	}

	@Override
	public int getServiceParameterObj(int accountId,String typeOfService, String spmName) {
		try{
			return (Integer)entityManager.createNamedQuery("DepositsLineItems.getServiceParameterObj").setParameter("accountId", accountId).setParameter("typeOfService", typeOfService).setParameter("spmName", spmName).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}
	
	/*@SuppressWarnings({ "rawtypes" })
	private List getSanctionedDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("spmId", (Integer)values[0]);
				paymentMap.put("serviceParameterValue", (String)values[1]);
				paymentMap.put("spmName", (String)values[2]);
			
			result.add(paymentMap);
	     }
    return result;
	}*/
}
