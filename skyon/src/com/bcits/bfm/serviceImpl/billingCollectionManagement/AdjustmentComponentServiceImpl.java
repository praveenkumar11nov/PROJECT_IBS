package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AdjustmentComponentsEntity;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentComponentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdjustmentComponentServiceImpl extends GenericServiceImpl<AdjustmentComponentsEntity> implements AdjustmentComponentService  {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<AdjustmentComponentsEntity> findAllById(int pacId) {
		return getAllDetails(entityManager.createNamedQuery("AdjustmentComponentsEntity.findAllById").setParameter("pacId", pacId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> calcLineEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = calcLineEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("adjComId", (Integer)values[0]);
				paymentMap.put("transactionCode", (String)values[1]);
				paymentMap.put("pacId", (Integer)values[2]);
				paymentMap.put("transactionName", (String)values[3]);
			
			result.add(paymentMap);
	     }
     return result;
	}

	@Override
	public List<?> getTransactionCodes(String typeOfService) {
		return entityManager.createNamedQuery("AdjustmentComponentsEntity.getTransactionCodes").setParameter("typeOfService", typeOfService).getResultList();
	}
}
