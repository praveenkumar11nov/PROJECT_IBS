package com.bcits.bfm.service.deposits;

import java.util.List;

import com.bcits.bfm.model.DepositsRefundEntity;
import com.bcits.bfm.service.GenericService;

public interface DepositsRefundService extends GenericService<DepositsRefundEntity>
{

	List<DepositsRefundEntity> findAll(int depositsId);
	
}
