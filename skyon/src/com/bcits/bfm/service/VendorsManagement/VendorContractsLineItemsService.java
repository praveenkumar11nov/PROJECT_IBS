package com.bcits.bfm.service.VendorsManagement;

import java.util.List;
import com.bcits.bfm.model.VendorContractLineitems;
import com.bcits.bfm.service.GenericService;

public interface VendorContractsLineItemsService extends GenericService<VendorContractLineitems> 
{
	public List<VendorContractLineitems> findAll();
	public List<?> setResponse();
	List<VendorContractLineitems> getAllVCLineItemsBasedOnVcId(int vcId);
	
	public String getReqType(int reqid);
}
