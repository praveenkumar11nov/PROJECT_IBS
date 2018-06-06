package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.ConciergeServices;
import com.bcits.bfm.model.CsVendorServiceCharges;
import com.bcits.bfm.service.GenericService;
import com.bcits.bfm.model.ConciergeVendorServices;

public interface CsVendorServiceChargeService extends GenericService<CsVendorServiceCharges> {
	
	public List<?> getResponse(int vsId);
	
	/*List<CsVendorServiceCharges> getVendorService();*/

	public ConciergeServices getVendorServiceBasedOnServiceChargeID(int vscId);

	List<ConciergeVendorServices> getVendorService(int vsId);
	
	List<Integer> getDistinctServiceId();
	
	List<CsVendorServiceCharges> findAll();
	
	CsVendorServiceCharges getVendorRate(int vsId,String rateType);
	
	CsVendorServiceCharges getServiceChargeBasedOnId(int vscId);
	
	List<CsVendorServiceCharges> getChargeObjectExceptId(int vscId);
	
	List<CsVendorServiceCharges> getServiceChargeBasedOnVendorServiceId(int vsId);
	
	int getVedorRateBasedOnVendorRateType(int vscId);
}
