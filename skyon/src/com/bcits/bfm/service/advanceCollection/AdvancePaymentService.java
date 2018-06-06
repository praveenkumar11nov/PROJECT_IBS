package com.bcits.bfm.service.advanceCollection;

import java.util.List;

import com.bcits.bfm.model.AdvancePaymentEntity;
import com.bcits.bfm.service.GenericService;

public interface AdvancePaymentService extends GenericService<AdvancePaymentEntity> {

	List<AdvancePaymentEntity> findAll(int advPayId);

}
