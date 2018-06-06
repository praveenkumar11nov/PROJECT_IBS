package com.bcits.bfm.service.VendorsManagement;

import java.util.Date;
import java.util.List;

import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.service.GenericService;

public interface ItemMasterService extends GenericService<ItemMaster> 
{
	public List<ItemMaster> findAll();
	public List<?> setResponse();
	@SuppressWarnings("rawtypes")
	public List getMaxCount();
	public List<?> getRequiredFields();
	public List<Object[]> getAllAssetSpares(int assetId);	
	public List<?> getItemNames();	
	public List<?> getDetailsFromLedgerAndItemMasterForCronScheduler();
	public int updateReorderLevelStatus(int id);
	public List<Object[]> getGraph(String type);	
	public List<?> getReqId(int storeId);
	public List<Object[]> getReqDetails(int reqId);	
	public List<Object[]> getGraph1(int id,String type);
	//public List<?> checkItemIdExistenceFromReqDetails(int itemId,int uomId);
	public List<ItemMaster> findItemTypes();
}
