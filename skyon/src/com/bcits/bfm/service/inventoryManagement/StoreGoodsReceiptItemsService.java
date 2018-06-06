package com.bcits.bfm.service.inventoryManagement;

import java.sql.Timestamp;
import java.util.List;

import com.bcits.bfm.model.StoreGoodsReceiptItems;
import com.bcits.bfm.service.GenericService;

public interface StoreGoodsReceiptItemsService extends GenericService<StoreGoodsReceiptItems>
{
	List<StoreGoodsReceiptItems> findAllStoreGoodsReceiptItems(int stgrId);

	String setStoreGoodsReceiptItemsStatus(int sgriId, Timestamp activationDt);

	void updateRequiredFields(int sgriId, double itemQuantity);

}