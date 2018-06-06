package com.bcits.bfm.service.VendorsManagement;

import java.util.List;

import com.bcits.bfm.model.VendorRequests;
import com.bcits.bfm.service.GenericService;

public interface VendorRequestService extends GenericService<VendorRequests>
{
	public List<VendorRequests> findAll();
	public List<?> setResponse();
	public int updateVendorRequestStatus(int id, String newStatus);
	public int updateVendorRequestStatus(int id, String newStatus,String replyNote);
}
