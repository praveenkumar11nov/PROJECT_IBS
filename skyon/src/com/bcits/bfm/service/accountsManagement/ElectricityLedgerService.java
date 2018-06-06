package com.bcits.bfm.service.accountsManagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.LedgerViewEntity;
import com.bcits.bfm.service.GenericService;
import com.bcits.bfm.util.DataSourceRequest;
import com.bcits.bfm.util.DataSourceResult;

public interface ElectricityLedgerService extends  GenericService<ElectricityLedgerEntity> {

	List<ElectricityLedgerEntity> findALL();
	void setElectricityLedgerStatus(int elrmid, String operation, HttpServletResponse response);
	Long getLedgerSequence(int accountId);
	Integer getLastLedgerTransactionAmount(int accountId, String typeOfService);
	ElectricityLedgerEntity getPreviousLedger(int accountID,Date previousBillDate,String transactionCode);
	String getPropertyNoForTenant(int personId);
	String getPropertyNoForOwner(int personId);
	Integer getLastLedgerBillAreears(int accountId,String typeOfService);
	Set<String> commonFilterForAccountNumbersUrl();
	Set<String> commonFilterForPropertyNumbersUrl();
	ElectricityLedgerEntity getPreviousArrearsLedger(int accountID,Date previousBillDate, String transactionCode);
	BigDecimal getLastArrearsLedgerBasedOnPayment(int accountID,Date previousBillDate, String transactionCode);
	List<ElectricityLedgerEntity> getPreviousLedgerPayments(int accountID,Date previousBillDate, String transactionCode);
	List<?> findPersonForFilters();
	List<ElectricityLedgerEntity> getTeleBroadbandLedgerDetails(int accountId, String string);
	int getLastLedgerBasedOnAccountId(int accountId);
	List<ElectricityLedgerEntity> searchLedgerDataByMonth(Date fromDateVal,Date toDateVal);
	DataSourceResult getList(DataSourceRequest request);
	
	List<LedgerViewEntity> getLedgerViewByAccountId(int accountId,Date fromDate,Date toDate);
}
