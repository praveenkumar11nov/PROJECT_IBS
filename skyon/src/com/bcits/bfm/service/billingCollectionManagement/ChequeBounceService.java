package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ChequeBounceEntity;
import com.bcits.bfm.service.GenericService;

public interface ChequeBounceService extends GenericService<ChequeBounceEntity> {

	List<?> findAll();

	List<String> getAllReceiptNos(int receiptNo);

	void changeChequeBounceStatus(int chequeBounceId, HttpServletResponse response);

	List<?> getChequeDetailsBasedOnChequeNumber(String chequeNo, String bankName, String receiptNo);

	List<Integer> getPaymentIdBasedOnChequeBounce(ChequeBounceEntity bounceEntity);

	void updateChequeBounceDetailsStatusBasedOnBillsPosting(int accountId);
	
	List<Object[]> getAll();

}
