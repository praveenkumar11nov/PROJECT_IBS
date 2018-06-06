package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.service.billingCollectionManagement.PaymentSegmentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PaymentSegmentServiceImpl extends GenericServiceImpl<PaymentSegmentsEntity> implements PaymentSegmentService  {

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSegmentsEntity> findAll() {
		return entityManager.createNamedQuery("PaymentSegmentsEntity.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<PaymentSegmentsEntity> findAllById(int paymentCollectionId) {
		return getAllDetails(entityManager.createNamedQuery("PaymentSegmentsEntity.findAllById").setParameter("paymentCollectionId", paymentCollectionId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentSegmentMap = null;
		 for (Object o:meterStatusEntities)			{
			 PaymentSegmentsEntity segmentsEntity=(PaymentSegmentsEntity)o;
				paymentSegmentMap = new HashMap<String, Object>();
				
				paymentSegmentMap.put("paymentSegmentId", segmentsEntity.getPaymentSegmentId());
				paymentSegmentMap.put("paymentCollectionId", segmentsEntity.getBillingPaymentsEntity().getPaymentCollectionId());
				paymentSegmentMap.put("billSegment",segmentsEntity.getBillSegment());
				paymentSegmentMap.put("billReferenceNo", segmentsEntity.getBillReferenceNo());
				paymentSegmentMap.put("amount", segmentsEntity.getAmount());
				paymentSegmentMap.put("postedToLedger", segmentsEntity.getPostedToLedger());
				paymentSegmentMap.put("postedLedgerDate", segmentsEntity.getPostedLedgerDate());
				paymentSegmentMap.put("status", segmentsEntity.getStatus());
				paymentSegmentMap.put("createdBy",segmentsEntity.getCreatedBy());
				paymentSegmentMap.put("billMonth",segmentsEntity.getBillMonth());
			
			result.add(paymentSegmentMap);
	     }
      return result;
	}
	
	@Override
	public ElectricityLedgerEntity getAccountDetails(int accountId,String typeOfService){
		try{
			return entityManager.createNamedQuery("PaymentSegmentsEntity.getAccountDetails",ElectricityLedgerEntity.class).setParameter("accountId", accountId).setParameter("ledgerType", typeOfService+" Ledger").setMaxResults(1).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public List<ElectricityBillEntity> getAccountDetailsBasedOnDeposit(int accountId){
		return entityManager.createNamedQuery("PaymentSegmentsEntity.getAccountDetailsBasedOnDeposit",ElectricityBillEntity.class).setParameter("accountId", accountId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdvanceBill> getAccountDetailsBasedOnAdvanceBill(int accountId){
		return entityManager.createNamedQuery("PaymentSegmentsEntity.getAccountDetailsBasedOnAdvanceBill").setParameter("accountId", accountId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSegmentsEntity> findAllByCollectionId(int paymentCollectionId) {
		
		return entityManager.createNamedQuery("PaymentSegmentsEntity.findAllById").setParameter("paymentCollectionId", paymentCollectionId).getResultList();
	}
}
