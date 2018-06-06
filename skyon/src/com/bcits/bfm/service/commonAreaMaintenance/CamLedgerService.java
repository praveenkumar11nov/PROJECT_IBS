package com.bcits.bfm.service.commonAreaMaintenance;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.CamChargesEntity;
import com.bcits.bfm.model.CamLedgerEntity;
import com.bcits.bfm.service.GenericService;

public interface CamLedgerService extends  GenericService<CamLedgerEntity> {

	List<CamLedgerEntity> findALL();
	Long getCamLedgerSequence(String camHeadProperty);
	Integer getLastTransactionCamLedgerId(String transactionCode);
	List<CamLedgerEntity> getAllData();
	List<CamLedgerEntity> getCamHeadTest(String camHeadProperty);
	List<CamLedgerEntity> findAllLedgerBasedOnCCID(int ccId);
	void camLedgerStatusUpdate(int camLedgerid, HttpServletResponse response);
	List<Integer> findAllIds(int camLedgerid);
	List<CamChargesEntity> getCamSetting();
	int getLastCamLedgerId();
	Set<String> commonFilterForCAMTransactions();
}
