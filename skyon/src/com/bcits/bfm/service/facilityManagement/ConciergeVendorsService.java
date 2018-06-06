package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.ConciergeVendors;
import com.bcits.bfm.service.GenericService;

public interface ConciergeVendorsService extends GenericService<ConciergeVendors>  {
	
	public ConciergeVendors getConciergeVendorsObject(Map<String, Object> map, String type, ConciergeVendors conciergeVendors ,int personId);

	ConciergeVendors getCsVendorIdBasedOnPersonId( int personid );
	
	int getPersonIdBasedOnVendorId(int vendorId);
	
	public List<?> getResponse(int personId);
}
