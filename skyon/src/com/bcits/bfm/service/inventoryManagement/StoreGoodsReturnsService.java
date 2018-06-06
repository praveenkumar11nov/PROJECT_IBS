package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import com.bcits.bfm.model.StoreGoodsReturns;
import com.bcits.bfm.service.GenericService;

public interface StoreGoodsReturnsService extends GenericService<StoreGoodsReturns>
{
	List<?> findAllStoreGoodsReturns();
	List<StoreGoodsReturns> getAllStoreGoodsReturnsRequiredFields(List<?> list);
	String setStoreGoodsReturnStatus(int sgrId);
}