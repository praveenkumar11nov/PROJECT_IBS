package com.bcits.bfm.service.VendorsManagement;
import java.util.List;

import com.bcits.bfm.model.StoreGoodsReceiptItems;
import com.bcits.bfm.model.VendorLineItems;
import com.bcits.bfm.service.GenericService;

public interface VendorLineItemsService extends GenericService<VendorLineItems> 
{
	public List<VendorLineItems> findAll();
	/*public List<?> setResponse();*/
	public List<VendorLineItems> findVendorLineItemsBasedOnVendorInvoice(int viId);
	public int getStoreIdBasedOnVcId(int vcid);	
	public List<StoreGoodsReceiptItems> getStorGoodreceiptData(int storeid);
	//public List getCount();
}
