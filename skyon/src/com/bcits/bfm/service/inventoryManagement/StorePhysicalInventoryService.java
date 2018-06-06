package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import com.bcits.bfm.model.StorePhysicalInventory;
import com.bcits.bfm.service.GenericService;

public interface StorePhysicalInventoryService extends GenericService<StorePhysicalInventory>
{
	List<StorePhysicalInventory> findAllStorePhysicalInventory();
}