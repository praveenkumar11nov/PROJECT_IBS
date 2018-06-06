package com.bcits.bfm.service.transactionMaster;

import java.util.List;

import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.service.GenericService;

public interface TransactionMasterService extends GenericService<TransactionMasterEntity> {

	List<TransactionMasterEntity> findAll();
	List<TransactionMasterEntity> findAllTransaction();
	List<String> getTransationCodesByType(String string);
	String getTransationNameByCode(String transactionCode);
	List<String> getTransationNameByCodeWithoutTaxAndRoundOff(int billId);
	void setTallyStatusUpdate(int billId);
	void setReceiptTallyStatusUpdate(int paymentCollectionId);
	List<BillingPaymentsEntity> getFiftyRecord();
	void setTallyStatusUpDateforXML(int billId);
}
