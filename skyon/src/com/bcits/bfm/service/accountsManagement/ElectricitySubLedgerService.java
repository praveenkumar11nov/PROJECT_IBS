package com.bcits.bfm.service.accountsManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.service.GenericService;

public interface ElectricitySubLedgerService extends  GenericService<ElectricitySubLedgerEntity> {
	List<ElectricitySubLedgerEntity> findALL();
	void setElectricityLedgerStatus(int elrmid, String operation, HttpServletResponse response);
	List<ElectricitySubLedgerEntity> findAllById(int elLedgerid);
	void updateSubLedgerStatusFromInnerGrid(int elSubLedgerid,
			HttpServletResponse response);
	List<String> getTransactionCodesForCollections(String typeOfService);
	Double getLastBalanceAmount();
	List<ElectricitySubLedgerEntity> getLastSubLedger(int elLedgerid,String transactionCode);
	List<String> getTransactionCodesForElectricity(String typeOfService);
	List<String> getTransactionCodesForGas(String typeOfService);
	List<String> getTransactionCodesForSolidWaste(String typeOfService);
	List<String> getTransactionCodesForWater(String typeOfService);
	List<String> getTransactionCodesForOthers(String typeOfService);
	List<String> getTransactionCodesForCam(String typeOfService);
	List<String> getTransactionCodesForTb(String typeOfService);
	List<ElectricitySubLedgerEntity> findAllById1(int elLedgerid);
	List<TransactionMasterEntity> getTransactionCodes(String typeOfService);
	List<TransactionMasterEntity> getTransactionMasterForCam(String typeOfService);
	
}
