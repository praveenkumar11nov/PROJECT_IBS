package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AdjustmentComponentsEntity;
import com.bcits.bfm.model.PaymentAdjustmentControlEntity;
import com.bcits.bfm.service.GenericService;

public interface AdjustmentControlService extends GenericService<PaymentAdjustmentControlEntity> {

	List<PaymentAdjustmentControlEntity> findAll();

	List<PaymentAdjustmentControlEntity> getAdjustmentId();

	List<AdjustmentComponentsEntity> getAdjusmentCalcItemList(int pacId);

	List<?> getAllPaidAccountNumbers();

	void setAdjustmentControlStatus(int pacId, String operation,
			HttpServletResponse response);

	List<?> findServiceTypes();

}
