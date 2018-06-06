package com.bcits.bfm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bcits.bfm.model.OnlinePaymentTransactions;

public interface OnlinePaymentTransactionsService extends GenericService<OnlinePaymentTransactions>{
	
	//List<OnlinePaymentTransactions> getAllTransactions();
	List<?> getTransactionDetailsByAccountId(String from ,String to);
	
	Set<String> commonFilterForAccountNumbersUrl();
	Set<String> commonFilterForPropertyNumbersUrl();
	Set<String> commonFilterForPaymentStatusUrl();
	Set<String> commonFilterForPayUMoneyIdUrl();
	Set<String> commonFilterForTransactionIdUrl();
	Set<String> commonFilterForServiceTypeUrl();

	public Map<String,Object> getTransactionDetails();

}
