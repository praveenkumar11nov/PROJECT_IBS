package com.bcits.bfm.service.electricityMetersManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricityMeterParametersEntity;
import com.bcits.bfm.service.GenericService;

public interface ElectricityMeterParametersService extends GenericService<ElectricityMeterParametersEntity> {

	List<ElectricityMeterParametersEntity> findALL();
	void setParameterStatus(int elMeterParameterId, String operation, HttpServletResponse response);
	List<ElectricityMeterParametersEntity> findAllById(int elMeterId);
	void updateParameterStatusFromInnerGrid(int accountId,
			HttpServletResponse response);
	List<?> getMeterParameterNamesList(String typeOfService,int meterId);
}
