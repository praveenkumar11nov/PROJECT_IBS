package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.service.GenericService;

public interface ServiceParameterMasterService extends GenericService<ServiceParameterMaster> {
	public List<ServiceParameterMaster> findAll();
	List<Map<String, Object>> setResponse();
	public abstract ServiceParameterMaster getBeanObject(Map<String, Object> map);
	public abstract void setServiceParameterStatus(int spmId, String operation, HttpServletResponse response);
	public List<?> getServiceParameterName(String serviceType);
	public List<?> getServiceParameterId(String spmName);
	
}
