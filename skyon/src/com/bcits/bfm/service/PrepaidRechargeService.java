package com.bcits.bfm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.PrepaidRechargeEntity;


public interface PrepaidRechargeService extends GenericService<PrepaidRechargeEntity>{

	List<?> getpersonDetails(Integer propertyId);

	String getMeterSerialNo(String propertyId);

	int getAccountId(Integer cbIdValue);

	List<?> findall(Integer accountId);

	int getIdBasedOnTxnId(String txnId);
	
	List<PrepaidRechargeEntity> getDataByaccountId(int accountId);
    List<PrepaidRechargeEntity> getPaymentHistory();
    List<PrepaidRechargeEntity> getPaymentHistoryByMonth(Date fromdate,Date todate);

	List<PrepaidRechargeEntity> getAllRecords(Date fromMonth);
	
	void upDateTallyStatus(int ppRechargeId, String tallystatus);

	List<Map<String, Object>> getTallyAdjustmentDetailData(int preRechargeId);

	List<PrepaidRechargeEntity> getFiftyRecords(Date fromDate);

	List<?> readAllData();

	
	

	

}
