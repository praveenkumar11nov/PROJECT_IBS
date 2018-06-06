package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.PaymentSegmentCalcLines;
import com.bcits.bfm.service.billingCollectionManagement.PaymentSegmentCalcLinesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PaymentSegmentCalcLinesServiceImpl extends GenericServiceImpl<PaymentSegmentCalcLines> implements PaymentSegmentCalcLinesService  {

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityBillLineItemEntity> getLineItemData(int elBillId) {
		return entityManager.createNamedQuery("PaymentSegmentCalcLines.getLineItemData").setParameter("elBillId", elBillId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<PaymentSegmentCalcLines> findAllById(int paymentCollectionId) {
		return getAllDetails(entityManager.createNamedQuery("PaymentSegmentCalcLines.findAllById").setParameter("paymentCollectionId", paymentCollectionId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentSegmentMap = null;
		 for (Object o:meterStatusEntities)			{
			 PaymentSegmentCalcLines paymentSegmentCalcLines=(PaymentSegmentCalcLines)o;
				paymentSegmentMap = new HashMap<String, Object>();
				
				paymentSegmentMap.put("calcLinesId", paymentSegmentCalcLines.getCalcLinesId());
				paymentSegmentMap.put("paymentSegmentId", paymentSegmentCalcLines.getPaymentSegmentsEntity().getPaymentSegmentId());
				paymentSegmentMap.put("transactionCode", paymentSegmentCalcLines.getTransactionCode());
				paymentSegmentMap.put("transactionName", getTransactionName(paymentSegmentCalcLines.getTransactionCode()));
				paymentSegmentMap.put("amount",paymentSegmentCalcLines.getAmount());
				paymentSegmentMap.put("billSegment", paymentSegmentCalcLines.getPaymentSegmentsEntity().getBillSegment());
				paymentSegmentMap.put("billReferenceNo", paymentSegmentCalcLines.getPaymentSegmentsEntity().getBillReferenceNo());
			
			result.add(paymentSegmentMap);
	     }
      return result;
	}

	@Override
	public String getTransactionName(String transactionCode) {
		return (String)entityManager.createNamedQuery("PaymentSegmentCalcLines.getTransactionName").setParameter("transactionCode", transactionCode).getSingleResult();
	}
}
