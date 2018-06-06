package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import com.bcits.bfm.model.AdjustmentCalcLinesEntity;
import com.bcits.bfm.service.GenericService;

public interface AdjustmentCalcLineService extends GenericService<AdjustmentCalcLinesEntity> {

	List<AdjustmentCalcLinesEntity> findAllById(int adjustmentId);

	List<?> getTransactionCodes(String typeOfService,int adjustmentId);

	List<AdjustmentCalcLinesEntity> getLastAdjustmentData(int accountId,String transactionCode,String adjustmentLedger);

	double getTotalAmountBasedOnAdjustmentId(int adjustmentId);
}
