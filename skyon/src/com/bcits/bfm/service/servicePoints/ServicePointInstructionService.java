package com.bcits.bfm.service.servicePoints;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ServicePointInstructionsEntity;
import com.bcits.bfm.service.GenericService;

public interface ServicePointInstructionService extends  GenericService<ServicePointInstructionsEntity>{

	List<ServicePointInstructionsEntity> findALL();
	void setServicePointStatus(int elrmid, String operation, HttpServletResponse response);
	List<ServicePointInstructionsEntity> findAllById(int srId);
	void updateInstructionStatusFromInnerGrid(int spInstructionId,
			HttpServletResponse response);
}
