package com.bcits.bfm.patternMasterService;

import java.util.List;

import com.bcits.bfm.patternMasterEntity.TransactionDetail;
import com.bcits.bfm.service.GenericService;

public interface PatternMasterDetailsService extends GenericService<TransactionDetail>{

	List<?> findAll(int tId);

	void findLevel(int tId);

	List<?> getAllDesignationId(int tId);
}
