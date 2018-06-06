package com.bcits.bfm.service.deposits;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Deposits;
import com.bcits.bfm.model.DepositsLineItems;
import com.bcits.bfm.service.GenericService;

public interface DepositsLineItemsService extends GenericService<DepositsLineItems>
{
	List<DepositsLineItems> findAll(int depositsId);

	List<?> getTransactionCodes();

	List<?> getTransactionCodeList(String serviceType);

	double getAllLineItemsAmount(int depositsId);

	Deposits getDepositsObjByLineItemId(int ddId);

	void refundAmountButtonClick(int ddId, HttpServletResponse response);

	Set<Integer> getSanctionedLoadDetails(int accountId);

	float getRateForDepositFromRateSlab(int accountId, String string);

	int getServiceParameterObj(int accountId, String typeOfServiceDeposit,String spmName);
}
