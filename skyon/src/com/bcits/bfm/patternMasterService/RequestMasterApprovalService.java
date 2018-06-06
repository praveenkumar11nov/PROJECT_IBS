package com.bcits.bfm.patternMasterService;

import java.util.List;


import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.service.GenericService;

public interface RequestMasterApprovalService extends GenericService<TransactionMaster>{

	List<?> findAllApprovalRequset(String userName);

}
