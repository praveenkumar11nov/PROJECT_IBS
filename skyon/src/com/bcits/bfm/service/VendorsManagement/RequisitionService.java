package com.bcits.bfm.service.VendorsManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.service.GenericService;

public interface RequisitionService extends GenericService<Requisition> 
{
	public List<?> setResponse();
	public List<Requisition> findAll();
	//public Requisition getBeanObject(Map<String, Object> map, String string,Requisition requisition);	
	public List<?> findVendors();
	public void setRequisitionStatus(int reqId, String operation,HttpServletResponse response, MessageSource messageSource,Locale locale);	
	public List<?> getDepartments();
	public List<?> getRequisitionName();	
	public List<Requisition> getRequisitionDetails();
	public List<?> getReqTypeBasedOnReqId(int reqid);
	List<Requisition> findAllRequisitionRequiredFields();
	public int getReqIdFromChild(int idVal);
	public List<?> getmanpowerrequisition();
	public List<?> getManpowerRequisition();
	public List<?> getManpowerRequisitionDetails(int reqId);
	public List<?> getManpowerRequisitionVC();
}
