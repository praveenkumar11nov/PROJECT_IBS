package com.bcits.bfm.service.deposits;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Deposits;
import com.bcits.bfm.service.GenericService;

public interface DepositsService extends GenericService<Deposits>
{

	List<Deposits> findAll();
	void setDepositsStatus(int depositsId, String operation,HttpServletResponse response);
	List<?> getAllAccountNumbers();
	List<Deposits> getLastDepositTransactionData(int accountId);
	Set<String> commonFilterForAccountNumbersUrl();
	Set<String> commonFilterForPropertyNoUrl();
	List<Deposits> testUniqueAccount(int accountId);
	List<?> findPersonForFilters();

}
