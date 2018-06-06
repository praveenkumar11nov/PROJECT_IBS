package com.bcits.bfm.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bcits.bfm.model.BankTransactionHistory;

public interface BankTxnHstyservice extends GenericService<BankTransactionHistory>{

	public List<?>  readAllPayTimeData();
	public String getAccId(String customerName,String customerMail);
	
	public String getAccountID(String accountNo);
	
	public List<?> getAllByMonth(Date fromDate,Date toDate) ;
	
	public String getAccountNo(int accountId);
	
	/*public List<?> getPropertyNumForFilter();*/
}
