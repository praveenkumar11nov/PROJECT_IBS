package com.bcits.bfm.service.depositsImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.DepositsRefundEntity;
import com.bcits.bfm.service.deposits.DepositsRefundService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class DepositsRefundServiceImpl extends GenericServiceImpl<DepositsRefundEntity> implements DepositsRefundService
{
	@SuppressWarnings("unchecked")
	@Override
	public List<DepositsRefundEntity> findAll(int depositsId)
	{
		return getAllDetails(entityManager.createNamedQuery("DepositsRefundEntity.findAll").setParameter("depositsId", depositsId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("refundId", (Integer)values[0]);
				paymentMap.put("refundAmount", (Double)values[1]);
				paymentMap.put("loadChangeUnits", (Double)values[2]);
				paymentMap.put("notes", (String)values[3]);
				paymentMap.put("paymentThrough", (String)values[4]);
				paymentMap.put("instrumentNo", (String)values[5]);
				paymentMap.put("instrumentDate", (Date)values[6]);
				paymentMap.put("bankName", (String)values[7]);
				paymentMap.put("depositsId", (Integer)values[8]);
				paymentMap.put("refundDate", (Date)values[9]);
			
			result.add(paymentMap);
	     }
    return result;
	}
	
}
