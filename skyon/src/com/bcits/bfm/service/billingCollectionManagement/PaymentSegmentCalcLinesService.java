package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.PaymentSegmentCalcLines;
import com.bcits.bfm.service.GenericService;

public interface PaymentSegmentCalcLinesService extends GenericService<PaymentSegmentCalcLines> {

	List<ElectricityBillLineItemEntity> getLineItemData(int elBillId);

	List<PaymentSegmentCalcLines> findAllById(int paymentCollectionId);

	String getTransactionName(String transactionCode);
}
