package com.bcits.bfm.service.VendorsManagement;

import java.util.List;

import com.bcits.bfm.model.VendorIncidents;
import com.bcits.bfm.service.GenericService;

public interface VendorIncidentService extends GenericService<VendorIncidents> 
{
	public List<VendorIncidents> findAll();
	public List<?> setResponse();
	public int updateVendorIncidentsStatus(int id, String newStatus);
	public Integer getContractSLA(int vcId);
}
