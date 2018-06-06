package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.service.GenericService;

public interface StoreItemLedgerService extends GenericService<StoreItemLedger>
{
	List<StoreItemLedger> findAllStoreItemLedgers();
	List<StoreItemLedger> findByUOM(ItemMaster itemMaster, int storeId);
	List<StoreItemLedger> findByItemMaster(ItemMaster itemMaster);
	List<StoreMaster> findRequiredAllStoresFromItemLedger();
	void updateItems(StoreItemLedger silId, int i, int j, Double availableBalance);
	List<StoreItemLedger> getBanceQuantity(int storeId, int itemId, int uomId);
	List<StoreItemLedger> getStoreItemLedgerList(int itemId);
	List<Object[]> findAllItemLedgers();
}