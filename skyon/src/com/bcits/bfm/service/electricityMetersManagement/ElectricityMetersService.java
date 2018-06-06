package com.bcits.bfm.service.electricityMetersManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.service.GenericService;

public interface ElectricityMetersService extends GenericService<ElectricityMetersEntity> {

	List<?> findALL();
	
	
	List<ElectricityMetersEntity> findALLBillMeters();
	void setMetersStatus(int elrmid, String operation, HttpServletResponse response);
	List<?> findPersonForFilters();
	ElectricityMetersEntity getMeterByMeterSerialNo(String mtrSrNo);
	ElectricityMetersEntity getMeter(int accountId, String typeOfService);
	List<?> proPertyName();
}
