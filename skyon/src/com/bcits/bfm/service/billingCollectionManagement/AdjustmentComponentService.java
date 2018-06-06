package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import com.bcits.bfm.model.AdjustmentComponentsEntity;
import com.bcits.bfm.service.GenericService;

public interface AdjustmentComponentService extends GenericService<AdjustmentComponentsEntity> {

	List<AdjustmentComponentsEntity> findAllById(int pacId);

	List<?> getTransactionCodes(String typeOfService);
}
