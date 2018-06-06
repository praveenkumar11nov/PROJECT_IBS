package com.bcits.bfm.service.billingCollectionManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AdjustmentMasterEntity;
import com.bcits.bfm.service.GenericService;

public interface AdjustmentMasterService extends GenericService<AdjustmentMasterEntity>{

	List<?> findALL();

	void adjustmentMasterStatus(int id, String operation,
			HttpServletResponse response);

	List<?> getAllAdjustmentName();

}
