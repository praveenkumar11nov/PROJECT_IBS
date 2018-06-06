package com.bcits.bfm.serviceImpl.transactionMasterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TransactionMasterServiceImpl extends GenericServiceImpl<TransactionMasterEntity> implements TransactionMasterService   {

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionMasterEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("TransactionMasterEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> transactionMasterMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				transactionMasterMap = new HashMap<String, Object>();
				
				transactionMasterMap.put("transactionId", (Integer)values[0]);
				transactionMasterMap.put("transactionName", (String)values[1]);
				transactionMasterMap.put("description", (String)values[2]);
				transactionMasterMap.put("createdBy", (String)values[3]);
				transactionMasterMap.put("typeOfService", (String)values[4]);
				transactionMasterMap.put("transactionCode", (String)values[5]);
				transactionMasterMap.put("calculationBasis", (String)values[6]);
				transactionMasterMap.put("groupType", (String)values[7]);
				transactionMasterMap.put("camRate", (Double)values[8]);

			
			result.add(transactionMasterMap);
	     }
      return result;
	}

	@Override
	public List<String> getTransationCodesByType(String typeOfService) {
		return entityManager.createNamedQuery("TransactionMasterEntity.getTransationCodesByType",String.class).setParameter("typeOfService", typeOfService).getResultList();
	}

	@Override
	public String getTransationNameByCode(String transactionCode) {
		return entityManager.createNamedQuery("TransactionMasterEntity.getTransationNameByCode",String.class).setParameter("transactionCode", transactionCode).setMaxResults(1).getSingleResult();
	}
	

	@Override
	public void setTallyStatusUpdate(int billId) {
		//UPDATE ElectricityBillEntity e SET e.tallyStatus = 'Posted' WHERE e.elBillId=:billId
		System.out.println(":::::::::::billId::::::::::"+billId);
		entityManager.createQuery("UPDATE ElectricityBillEntity e SET e.tallyStatus='Posted' WHERE e.elBillId="+billId+"").executeUpdate();
		
		
		//entityManager.createNamedQuery("ElectricityBillEntity.updateTallyStatus").setParameter("billId", billId).executeUpdate();
	}

	@Override
	public void setReceiptTallyStatusUpdate(int paymentCollectionId) {
		entityManager.createQuery("UPDATE BillingPaymentsEntity e SET e.tallyStatus='Posted' WHERE e.paymentCollectionId="+paymentCollectionId+"").executeUpdate();
		
	}

	@Override
	public List<String> getTransationNameByCodeWithoutTaxAndRoundOff(int elBillId) {
		
		return entityManager.createNamedQuery("TransactionMasterEntity.getTransationNameByCode",String.class).setParameter("elBillId", elBillId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionMasterEntity> findAllTransaction() {
	
		return entityManager.createNamedQuery("TransactionMasterEntity.findAllTransaction").getResultList();
	}

	@Override
	public List<BillingPaymentsEntity> getFiftyRecord() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("BillingPaymentsEntity.findFiftyRecordsForTally").setParameter("tallyStatus", "Not Posted").setMaxResults(50).getResultList();
	}
	
	@Override
	public void setTallyStatusUpDateforXML(int billId) {
		System.out.println(":::::::::::billId::::::::::"+billId);
		entityManager.createQuery("UPDATE ElectricityBillEntity e SET e.tallyStatus='XML Generated' WHERE e.elBillId="+billId+"").executeUpdate();
		
	}

}
