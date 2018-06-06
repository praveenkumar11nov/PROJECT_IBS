package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.service.GenericService;

public interface BillingParameterMasterService extends GenericService<BillParameterMasterEntity>{
	public List<BillParameterMasterEntity> findAll();
	public List<BillParameterMasterEntity> findAllBill();
	List<BillParameterMasterEntity> setResponse();
	public abstract BillParameterMasterEntity getBeanObject(Map<String, Object> map);
	public abstract void setBillingParameterStatus(int bpmId, String operation, HttpServletResponse response);
	List<Object[]> getNameandValue(int elBillId);
	public List<BillParameterMasterEntity> getBillParameterMasterByServiceType(String typeOfService);
	public Integer getServicMasterId(String typeOfService, String string);
	public List<String> getParameterName(String string);
	public BillParameterMasterEntity getBillParameterMasterById(int bvmId);
	public List<?> getUniqueParamName(String className, String attribute, String attribute1,String serviceType);
	public Object getMeterFixedDate(String className, String attribute,
			String attribute1, String serviceType, String attribute2,
			int accounId);
	
}
