package com.bcits.bfm.service.electricityMetersManagement;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricityMeterLocationEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.service.GenericService;

public interface ElectricityMeterLocationsService extends GenericService<ElectricityMeterLocationEntity> {

	List<ElectricityMeterLocationEntity> findALL();
	void setLocationStatus(int elMeterLocationId, String operation, HttpServletResponse response);
	List<ElectricityMeterLocationEntity> findAllById(int elMeterId);
	List<String> getAllServiceTypes();
	void releaseMeterStatusClick(int elMeterLocationId,Date releaseDate,double finalReading,HttpServletResponse response);
	List<?> getAllAccuntNumbers();
	List<ServiceMastersEntity> getServiceMasterObj(int accountId,String typeOfServiceForMeters);
}
