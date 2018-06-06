package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.SmsEmailForLowBal;

public interface LowBalanceStoreService extends GenericService<SmsEmailForLowBal>{

	
	public List<SmsEmailForLowBal> getAllData(String meterNo,String txnID);
	public List<SmsEmailForLowBal> getAllData1(String meterNo,String txnID);
	public List<SmsEmailForLowBal> getAllData2(String meterNo,String txnID);
	public int getEstateManager();
	
}
