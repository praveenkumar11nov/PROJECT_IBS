package com.bcits.bfm.service.VendorsManagement;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.service.GenericService;

public interface RequisitionDetailsService extends GenericService<RequisitionDetails>
{
	public List<RequisitionDetails> findAll();
	public List<Requisition> findId();
	public List<?> findRequisitionDetailsBasedOnId(int id);
	public RequisitionDetails getBeanObject(Map<String, Object> map, String string,RequisitionDetails requisitionDetails);	
	public List<RequisitionDetails> findReqDetailsBasedOnReqId(int reqid);	
	public String getReqTypeBasedOnReqId(int reqid);
	
	public List<RequisitionDetails> findAllRequiredRequisitionDetailsFields(int reqId);
	
	public List<?> getDesignation(int deptId);
	
}
