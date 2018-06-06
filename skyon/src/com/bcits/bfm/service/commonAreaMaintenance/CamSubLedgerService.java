package com.bcits.bfm.service.commonAreaMaintenance;

import java.math.BigDecimal;
import java.util.List;

import com.bcits.bfm.model.CamSubLedgerEntity;
import com.bcits.bfm.service.GenericService;

public interface CamSubLedgerService extends  GenericService<CamSubLedgerEntity> {
	List<CamSubLedgerEntity> findAllById(int camLedgerid);

	void updateAmountInCamLedger(double amount, double balanceAmount,int camLedgerid);

	List<?> transactionCodeList(String camHeadProperty);

	Integer getLastTransactionCamLedgerId(int camLedgerid);

	CamSubLedgerEntity getLastCamSubLedger(int camLedgerid,String transactionCode);

	BigDecimal getLastLedgerId(String camHeadProperty);

	double getTotalSubLedgerAmountBasedOnCamLedgerId(int camLedgerid);
}
