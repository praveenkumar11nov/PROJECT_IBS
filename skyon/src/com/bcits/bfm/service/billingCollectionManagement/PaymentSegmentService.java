package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.service.GenericService;

public interface PaymentSegmentService extends GenericService<PaymentSegmentsEntity> {

	List<PaymentSegmentsEntity> findAll();
	
	List<PaymentSegmentsEntity> findAllById(int paymentCollectionId);
	
	ElectricityLedgerEntity getAccountDetails(int accountId,String typeOfService);

	List<ElectricityBillEntity> getAccountDetailsBasedOnDeposit(int accountId);

	List<AdvanceBill> getAccountDetailsBasedOnAdvanceBill(
			int accountId);

	List<PaymentSegmentsEntity> findAllByCollectionId(int paymentCollectionId);
}
