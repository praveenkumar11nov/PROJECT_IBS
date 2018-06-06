package com.bcits.bfm.serviceImpl.commonAreaMaintenance;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.CamChargesEntity;
import com.bcits.bfm.model.CamLedgerEntity;
import com.bcits.bfm.service.commonAreaMaintenance.CamLedgerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CamLedgerServiceImpl extends GenericServiceImpl<CamLedgerEntity> implements CamLedgerService {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamLedgerEntity> findALL() {
		return getAllDetails(entityManager.createNamedQuery("CamLedgerEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAllDetails(List<?> ledgerList){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> camLedgerMap = null;
		 for (Iterator<?> iterator = ledgerList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				camLedgerMap = new HashMap<String, Object>();
								
				camLedgerMap.put("camLedgerid", (Integer)values[0]);	
				camLedgerMap.put("camHeadProperty", (String)values[1]);
				camLedgerMap.put("calculationBased", (String)values[2]);	
				camLedgerMap.put("postReference", (String)values[3]);
				camLedgerMap.put("transactionCode", (String)values[4]);
				camLedgerMap.put("creditAmount", (Double)values[5]);
				camLedgerMap.put("balance", (Double)values[6]);	
				camLedgerMap.put("postedBillDate", (Timestamp)values[7]);
				camLedgerMap.put("headBalance", (Double)values[8]);	
				camLedgerMap.put("transactionName", (String)values[9]);
				camLedgerMap.put("postType", (String)values[10]);	
				camLedgerMap.put("postedToBill", (String)values[11]);
				camLedgerMap.put("ledgerDate", (Date)values[12]);
				camLedgerMap.put("status", (String)values[13]);
				camLedgerMap.put("debitAmount", (Double)values[14]);
						
			result.add(camLedgerMap);
	     }
      return result;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Long getCamLedgerSequence(String camHeadProperty) {
		return (Long)entityManager.createNamedQuery("CamLedgerEntity.getCamLedgerSequence").setParameter("camHeadProperty", camHeadProperty).getSingleResult();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getLastTransactionCamLedgerId(String transactionCode) {
		try{
			return (Integer)entityManager.createNamedQuery("CamLedgerEntity.getLastTransactionCamLedgerId").setParameter("transactionCode", transactionCode).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamLedgerEntity> getAllData() {
		return entityManager.createNamedQuery("CamLedgerEntity.getAllData").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamLedgerEntity> getCamHeadTest(String camHeadProperty) {
		return entityManager.createNamedQuery("CamLedgerEntity.getCamHeadTest").setParameter("camHeadProperty", camHeadProperty).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamLedgerEntity> findAllLedgerBasedOnCCID(int ccId){
		return entityManager.createNamedQuery("CamLedgerEntity.findAllLedgerBasedOnCCID").setParameter("ccId", ccId).getResultList();
	}

	@Override
	public void camLedgerStatusUpdate(int camLedgerid,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();			
			CamLedgerEntity ledgerEntity = find(camLedgerid);			
			if(ledgerEntity.getStatus().equalsIgnoreCase("Created"))
			{
				ledgerEntity.setStatus("Approved");		
				update(ledgerEntity);
				out.write("CAM ledger approved");
			}else{
				out.write("CAM ledger already approved");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findAllIds(int camLedgerid) {
		return entityManager.createNamedQuery("CamLedgerEntity.findAllIds").setParameter("camLedgerid", camLedgerid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CamChargesEntity> getCamSetting() {
		return entityManager.createNamedQuery("CamLedgerEntity.getCamSetting").getResultList();
	}

	@Override
	public int getLastCamLedgerId() {
		try{
			return (Integer)entityManager.createNamedQuery("CamLedgerEntity.getLastCamLedgerId").getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForCAMTransactions() {
		return new HashSet<String>(entityManager.createNamedQuery("CamLedgerEntity.commonFilterForCAMTransactions").getResultList());
	}

}
