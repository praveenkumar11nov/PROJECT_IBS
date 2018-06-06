package com.bcits.bfm.service.electricityBillsManagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.service.GenericService;

public interface ElectricityBillParameterService extends  GenericService<ElectricityBillParametersEntity> {

	List<ElectricityBillParametersEntity> findALL();
	void setBillParameterStatus(int elBillParameterId, String operation, HttpServletResponse response);
	List<ElectricityBillParametersEntity> findAllById(int elBillId);
	BigDecimal getSequencyNumber();
	Integer getCamParameter(String bvmName);
	List<String> getAverageUnits(int elBillId, int bvmId, Date lastYear,Date currentBillDate);
	String getAverageAmountValue(int bpmId,int elBillId);
	String getParameterValue(int elBillId, String typeOfService,String paraMeterName);
	List<Integer> getBillParametersByBillId(int elBillId);
	BillParameterMasterEntity getMasterObjBasedOnName(String bvmName,String serviceType);
	List<ElectricityBillParametersEntity> findByBillId(int elBillId);
	
}
