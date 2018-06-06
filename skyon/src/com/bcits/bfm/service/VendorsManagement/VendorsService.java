package com.bcits.bfm.service.VendorsManagement;

import java.util.List;
import java.util.Map;




//import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.GenericService;

public interface VendorsService extends GenericService<Vendors>
{
	public List<Vendors> findAll();
	public Vendors getVendorInstanceBasedOnVendorId(int vendorId);	
	public List<Vendors> findAllVendorBasedOnPersonID(int personId);	
	public Vendors getVendorsObject(Map<String, Object> map, String type,Vendors vendors, int personType);
	public List<?> setResponse();
	
	@SuppressWarnings("rawtypes")
	public List findVendorIdBasedOnpersonId(int personId);
	public int updateVendorStatus(int id, String newStatus);
	
	public List<?> getVendorResponse(int personId);
}