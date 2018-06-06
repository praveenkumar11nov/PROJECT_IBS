package com.bcits.bfm.serviceImpl.commonAreaMaintenance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.CamSubLedgerEntity;
import com.bcits.bfm.service.commonAreaMaintenance.CamSubLedgerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CamSubLedgerServiceImpl extends GenericServiceImpl<CamSubLedgerEntity> implements CamSubLedgerService {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamSubLedgerEntity> findAllById(int camLedgerid) {  
		return getAllDetails(entityManager.createNamedQuery("CamSubLedgerEntity.findAllById").setParameter("camLedgerid", camLedgerid).getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> subLedgerEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> subLedgerMap = null;
		 for (Iterator<?> iterator = subLedgerEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				subLedgerMap = new HashMap<String, Object>();				
				
				subLedgerMap.put("camSubLedgerid", (Integer)values[0]);	
				subLedgerMap.put("amount",(Double)values[1]);
				subLedgerMap.put("balanceAmount",(Double)values[2]);
				subLedgerMap.put("transactionCode", (String)values[3]);
				subLedgerMap.put("transactionName", (String)values[4]);
				subLedgerMap.put("camLedgerid", (Integer)values[5]);
			
			result.add(subLedgerMap);
	     }
      return result;
	}
	
	@Override
	public void updateAmountInCamLedger(double amount,double balanceAmount,int camLedgerid){
		entityManager.createNamedQuery("CamSubLedgerEntity.updateAmountInCamLedger").setParameter("amount", amount).setParameter("balanceAmount", balanceAmount).setParameter("camLedgerid", camLedgerid).executeUpdate();
	}
	
	@Override
	public List<?> transactionCodeList(String camHeadProperty)
	{
		return entityManager.createNamedQuery("CamSubLedgerEntity.transactionCodeList").setParameter("camHeadProperty", camHeadProperty).getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getLastTransactionCamLedgerId(int camLedgerid) {
		return (Integer)entityManager.createNamedQuery("CamSubLedgerEntity.getLastTransactionCamLedgerId").setParameter("camLedgerid", camLedgerid).getSingleResult();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public CamSubLedgerEntity getLastCamSubLedger(int camLedgerid,String transactionCode) {  
		return (CamSubLedgerEntity) entityManager.createNamedQuery("CamSubLedgerEntity.getLastCamSubLedger").setParameter("camLedgerid", camLedgerid).setParameter("transactionCode", transactionCode).getSingleResult();
	}
	
	@Override
	public BigDecimal getLastLedgerId(String camHeadProperty){
		//String queryString = "SELECT cl.camLedgerid  FROM (SELECT cl.camLedgerid, rank() over (order by cl.camLedgerid desc) rnk FROM CamLedgerEntity cl WHERE cl.camHeadProperty="+camHeadProperty+") WHERE rnk = 2";
		String queryString = "SELECT CL_ID  FROM (SELECT CL_ID, rank() over (order by CL_ID desc) rnk FROM CAM_LEDGER WHERE CAM_HEADS='"+camHeadProperty+"') WHERE rnk = 2";
		final Query query = this.entityManager.createNativeQuery(queryString);
		return (BigDecimal) query.getSingleResult();
	}

	@Override
	public double getTotalSubLedgerAmountBasedOnCamLedgerId(int camLedgerid) {
		return (double)entityManager.createNamedQuery("CamSubLedgerEntity.getTotalSubLedgerAmountBasedOnCamLedgerId").setParameter("camLedgerid", camLedgerid).getSingleResult();
	}
}
