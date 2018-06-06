package com.bcits.bfm.service.inventoryManagement;

import java.sql.Timestamp;
import java.util.List;

import com.bcits.bfm.model.StoreCategory;
import com.bcits.bfm.model.StoreGoodsReceipt;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.service.GenericService;

public interface StoreGoodsReceiptService extends GenericService<StoreGoodsReceipt>
{
	List<?> getItemsBasedOnVendorContractId(int vcId);
	List<?> populateCustomCategories();
	List<StoreGoodsReceipt> getRequiredStoreGoodsReceipts();
	List<VendorContracts> getAllRequiredVCWhosHasChildrenVCLI();
	List<?> getRequiredContractDetails();
	List<?> getAllRequiredItemsFieldsForStoreMovement();
	List<?> getRequiredStoreFields(int vcId, int imId);
	void updateLedgerDate(int stgrId, Timestamp ledgerUpdateDt);
	List<?> getRequiredStoreItems(int storeId, int imId);
	List<VendorContracts> getAllRequiredVCWhosHasChildrenVCLIIncludingDuplicates();
	List<StoreCategory> findAllForTreeByStoreCategoryId(String parentId);
	List<?> getDataForTree();
	List<?> getRequiredContractDetailsBasedOnStoreIdAndImId(int storeId, int imId);
}