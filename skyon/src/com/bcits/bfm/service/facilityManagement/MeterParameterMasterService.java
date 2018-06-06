package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.MeterParameterMaster;
import com.bcits.bfm.service.GenericService;

public interface MeterParameterMasterService extends GenericService<MeterParameterMaster> {
	public List<MeterParameterMaster> findAll();
	List<Map<String, Object>> setResponse();
	public abstract MeterParameterMaster getBeanObject(Map<String, Object> map);
	public abstract void setMeterParameterStatus(int mpmId, String operation, HttpServletResponse response);
	public List<Object[]> getNameandValue(int elMeterId);
	public String getMeterParameter(int accountId, int elMeterId,String typeOfServiceForMeters, String string);
}
