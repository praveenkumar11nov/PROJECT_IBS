package com.bcits.bfm.service.serviceMasterManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.service.GenericService;

public interface ServiceParameterService extends GenericService<ServiceParametersEntity> {

	List<ServiceParametersEntity> findALL();
	List<ServiceParametersEntity> findAllById(int serviceMasterId);
	void updateParameterStatusFromInnerGrid(int serviceParameterId,HttpServletResponse response);
	ServiceParameterMaster getServiceDataType(int spmId);
	List<Object[]> getNameandValue(int serviceMasterId);
	List<ServiceParametersEntity> findAllByParentId(int serviceMasterId);
	int getSequenceForAverageUnits(int serviceID);
	int getServiceParameterMasterId(String string);
	List<ServiceParametersEntity> findAllByParentIdForDG(int serviceMasterId);
}
