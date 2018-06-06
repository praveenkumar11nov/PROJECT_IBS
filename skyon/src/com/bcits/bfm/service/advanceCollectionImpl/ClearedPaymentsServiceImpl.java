package com.bcits.bfm.service.advanceCollectionImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ClearedPayments;
import com.bcits.bfm.service.advanceCollection.ClearedPaymentsService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ClearedPaymentsServiceImpl extends GenericServiceImpl<ClearedPayments> implements ClearedPaymentsService
{

	@Autowired
	AdjustmentService adjustmentService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClearedPayments> findAll(int advCollId)
	{
		return getAllDetails(entityManager.createNamedQuery("ClearedPayments.findAll").setParameter("advCollId", advCollId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				if((Integer)values[1]==0){
					
				}else{
					paymentMap.put("cpId", (Integer)values[0]);
					paymentMap.put("adjId", (Integer)values[1]);
					//PaymentAdjustmentEntity adjustmentEntity = adjustmentService.find((Integer)values[1]);
					paymentMap.put("adjNo", (String)values[6]);
					paymentMap.put("billId", (String)values[2]);
					paymentMap.put("amount", (Double)values[3]);
					paymentMap.put("clearedDate", (Date)values[4]);
					paymentMap.put("advCollId", (Integer)values[5]);
				}
			
			result.add(paymentMap);
	     }
    return result;
	}

}
