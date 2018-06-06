package com.bcits.bfm.service.advanceCollectionImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AdvancePaymentEntity;
import com.bcits.bfm.service.advanceCollection.AdvancePaymentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdvancePaymentServiceImpl extends GenericServiceImpl<AdvancePaymentEntity> implements AdvancePaymentService
{
	@SuppressWarnings("unchecked")
	@Override
	public List<AdvancePaymentEntity> findAll(int advPayId) {
		return getAllDetails(entityManager.createNamedQuery("AdvancePaymentEntity.findAll").setParameter("advPayId", advPayId).getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("advPayId", (Integer)values[0]);
				paymentMap.put("receiptNo", (String)values[1]);
				paymentMap.put("amount", (Double)values[2]);
				paymentMap.put("advPaymentDate", (Date)values[3]);
				paymentMap.put("advCollId", (Integer)values[4]);
			
			result.add(paymentMap);
	     }
    return result;
	}

}
