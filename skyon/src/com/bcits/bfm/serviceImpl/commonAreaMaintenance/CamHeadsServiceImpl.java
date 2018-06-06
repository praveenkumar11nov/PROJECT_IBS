package com.bcits.bfm.serviceImpl.commonAreaMaintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.CamHeadsEntity;
import com.bcits.bfm.service.commonAreaMaintenance.CamHeadsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CamHeadsServiceImpl extends GenericServiceImpl<CamHeadsEntity> implements CamHeadsService {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamHeadsEntity> findAllById(int ccId) {  
		return getAllDetails(entityManager.createNamedQuery("CamHeadsEntity.findAllById").setParameter("ccId", ccId).getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamHeadsEntity> getHeadData(int ccId) {  
		return entityManager.createNamedQuery("CamHeadsEntity.getHeadData").setParameter("ccId", ccId).getResultList();
	}

	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> subLedgerEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> subLedgerMap = null;
		 for (Iterator<?> iterator = subLedgerEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				subLedgerMap = new HashMap<String, Object>();				
				
				subLedgerMap.put("camHeadId", (Integer)values[0]);	
				subLedgerMap.put("groupName",(String)values[1]);
				subLedgerMap.put("transactionCode",(String)values[2]);
				subLedgerMap.put("calculationBasis", (String)values[3]);
				subLedgerMap.put("amount", (Double)values[4]);
				subLedgerMap.put("ccId", (Integer)values[5]);
				subLedgerMap.put("transactionName", (String)values[6]);
			
			result.add(subLedgerMap);
	     }
      return result;
	}
	
	@Override
	public Double getHeadDataAmount(int ccId){
		return (Double)entityManager.createNamedQuery("CamHeadsEntity.getHeadDataAmount").setParameter("ccId", ccId).getSingleResult();
	}
	
}
