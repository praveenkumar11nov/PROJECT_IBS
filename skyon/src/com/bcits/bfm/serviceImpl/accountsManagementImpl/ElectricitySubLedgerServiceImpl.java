package com.bcits.bfm.serviceImpl.accountsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ElectricitySubLedgerServiceImpl extends GenericServiceImpl<ElectricitySubLedgerEntity> implements ElectricitySubLedgerService {

	@Override
	@SuppressWarnings("unchecked")
	public List<ElectricitySubLedgerEntity> findALL() {
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.findAll").getResultList();
	}

	@Override
	public void setElectricityLedgerStatus(int elLedgerid, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricitySubLedgerEntity.setELRateMasterStatus").setParameter("status", "Active").setParameter("elSubLedgerid", elLedgerid).executeUpdate();
				out.write("Ledger active");
			}
			else
			{
				entityManager.createNamedQuery("ElectricitySubLedgerEntity.setELRateMasterStatus").setParameter("status", "Inactive").setParameter("elSubLedgerid", elLedgerid).executeUpdate();
				out.write("Ledger inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricitySubLedgerEntity> findAllById(int elLedgerid) {  
		return getAllDetails(entityManager.createNamedQuery("ElectricitySubLedgerEntity.findAllById").setParameter("elLedgerid", elLedgerid).getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricitySubLedgerEntity> getLastSubLedger(int elLedgerid,String transactionCode) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getLastSubLedger").setParameter("elLedgerid", elLedgerid).setParameter("transactionCode", transactionCode).getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> subLedgerEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> subLedgerMap = null;
		 for (Iterator<?> iterator = subLedgerEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				subLedgerMap = new HashMap<String, Object>();				
				
				subLedgerMap.put("elSubLedgerid", (Integer)values[0]);	
				subLedgerMap.put("transactionCode",(String)values[1]);
				subLedgerMap.put("amount",(Double)values[2]);
				subLedgerMap.put("balanceAmount",(Double)values[3]);
				subLedgerMap.put("transactionName",(String)values[4]);
			
			result.add(subLedgerMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForCollections(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForCollections").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@Override
	public void updateSubLedgerStatusFromInnerGrid(int elSubLedgerid, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			ElectricitySubLedgerEntity subLedgerEntity = find(elSubLedgerid);
			
			if(subLedgerEntity.getStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("ElectricitySubLedgerEntity.updateSubLedgerStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("elSubLedgerid", elSubLedgerid).executeUpdate();
				out.write("Sub Ledger In-Active");
			}
			else
			{
				entityManager.createNamedQuery("ElectricitySubLedgerEntity.updateSubLedgerStatusFromInnerGrid").setParameter("status", "Active").setParameter("elSubLedgerid", elSubLedgerid).executeUpdate();
				out.write("Sub Ledger Active");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public Double getLastBalanceAmount(){
		return (Double)entityManager.createNamedQuery("ElectricitySubLedgerEntity.getLastBalanceAmount").getSingleResult(); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForElectricity(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForElectricity").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForGas(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForGas").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForSolidWaste(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForSolidWaste").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForWater(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForWater").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForOthers(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForOthers").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForCam(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForCam").setParameter("typeOfService", typeOfService).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTransactionCodesForTb(String typeOfService) {  
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodesForTb").setParameter("typeOfService", typeOfService).getResultList();
	}

	@Override
	public List<ElectricitySubLedgerEntity> findAllById1(int elLedgerid) {
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.findAllById1",ElectricitySubLedgerEntity.class).setParameter("elLedgerid", elLedgerid).getResultList();
	}

	@Override
	public List<TransactionMasterEntity> getTransactionCodes( String typeOfService) {
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionCodes",TransactionMasterEntity.class).setParameter("typeOfService", typeOfService).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionMasterEntity> getTransactionMasterForCam(String typeOfService) {
		return entityManager.createNamedQuery("ElectricitySubLedgerEntity.getTransactionMasterForCam").setParameter("typeOfService", typeOfService).getResultList();
	}

}
