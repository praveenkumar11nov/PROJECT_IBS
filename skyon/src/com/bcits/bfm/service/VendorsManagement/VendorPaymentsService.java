package com.bcits.bfm.service.VendorsManagement;

import java.util.List;

import com.bcits.bfm.model.VendorLineItems;
import com.bcits.bfm.model.VendorPayments;
import com.bcits.bfm.service.GenericService;

public interface VendorPaymentsService extends GenericService<VendorPayments>
{
	public List<VendorPayments> findAll();
	public List<?> setResponse();
	public List<VendorPayments> findVendorPaymentsBasedOnVendorInvoice(int viId);
}
