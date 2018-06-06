package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreItemLedgerDetails;
import com.bcits.bfm.service.GenericService;

public interface StoreItemLedgerDetailsService extends GenericService<StoreItemLedgerDetails>
{

	List<StoreItemLedgerDetails> getAllStoreItemLedgerDetailsBasedOnSILId(int silId);

	StoreItemLedger getObject(int storeId, int imId);



}