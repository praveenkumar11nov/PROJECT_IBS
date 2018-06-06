package com.bcits.bfm.patternMasterService;

import java.util.List;

import com.bcits.bfm.patternMasterEntity.TransactionDetail;
import com.bcits.bfm.service.GenericService;

public interface TransactionDetailService extends GenericService<TransactionDetail>{

	List<?> findAll(int tId);

	public List<?>  finddataforNotification(int did);

	List<?> getAllTransactionUniqueness();

}
