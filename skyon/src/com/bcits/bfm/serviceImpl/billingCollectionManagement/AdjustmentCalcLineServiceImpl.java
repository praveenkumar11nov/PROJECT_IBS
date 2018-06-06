package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AdjustmentCalcLinesEntity;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentCalcLineService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdjustmentCalcLineServiceImpl extends GenericServiceImpl<AdjustmentCalcLinesEntity> implements AdjustmentCalcLineService  {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<AdjustmentCalcLinesEntity> findAllById(int adjustmentId) {
		return getAllDetails(entityManager.createNamedQuery("AdjustmentCalcLinesEntity.findAllById").setParameter("adjustmentId", adjustmentId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("calcLineId", (Integer)values[0]);
				paymentMap.put("transactionCode", (String)values[1]);
				paymentMap.put("amount", (Double)values[2]);
				paymentMap.put("adjustmentId", (Integer)values[3]);
				paymentMap.put("transactionName", (String)values[4]);
			
			result.add(paymentMap);
	     }
     return result;
	}

	@Override
	public List<?> getTransactionCodes(String typeOfService,int adjustmentId) {
		return entityManager.createNamedQuery("AdjustmentCalcLinesEntity.getTransactionCodes").setParameter("typeOfService", typeOfService).setParameter("adjustmentId", adjustmentId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdjustmentCalcLinesEntity> getLastAdjustmentData(int accountId,	String transactionCode,String adjustmentLedger) {
		return entityManager.createNamedQuery("AdjustmentCalcLinesEntity.getLastAdjustmentData").setParameter("accountId", accountId).setParameter("transactionCode", transactionCode).setParameter("adjustmentLedger", adjustmentLedger).getResultList();
	}

	@Override
	public double getTotalAmountBasedOnAdjustmentId(int adjustmentId) {
		return (double)entityManager.createNamedQuery("AdjustmentCalcLinesEntity.getTotalAmountBasedOnAdjustmentId").setParameter("adjustmentId", adjustmentId).getSingleResult();
	}
}
