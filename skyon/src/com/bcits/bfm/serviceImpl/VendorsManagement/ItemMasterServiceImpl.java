package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ItemMasterServiceImpl extends GenericServiceImpl<ItemMaster> implements ItemMasterService
{
	static Logger logger = LoggerFactory.getLogger(ItemMasterServiceImpl.class);
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ItemMaster> findAll() 
	{
		List<ItemMaster> itemMaster = entityManager.createNamedQuery("ItemMaster.findAll").getResultList();
		return itemMaster;
	}

	// For Sending the response to jsp
	@SuppressWarnings("serial")
	@Override
	public List<?> setResponse() 
	{
		List<ItemMaster> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final ItemMaster record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				put("imId", record.getImId());
            	put("imName", record.getImName());
            	put("imGroup", record.getImGroup());
            	put("imType", record.getImType());
            	put("imPurchaseUom", record.getImPurchaseUom());
            	put("uomClass", record.getUomClass());            	
            	put("imDescription", record.getImDescription());
            	put("imUOM_Usable", record.getImUOM_Usable());
            	put("imOptimal_Stock", record.getImOptimal_Stock());
            	put("imUomIssue", record.getImUomIssue());
            	put("createdBy", record.getCreatedBy());
            	put("lastUpdatedBy", record.getLastUpdatedBy()); 
            	put("reorderLevel",record.getReorderLevel());
            	
            	java.util.Date dt1 = record.getImCreatedDate();
            	java.sql.Date dt2 = new java.sql.Date(dt1.getTime());
            	
            	put("imCreatedDate",dt2);
			}});
		}
		return response;
	}
	// End for sending the response to jsp
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List getMaxCount()
	{
		List id = entityManager.createNamedQuery("ItemMaster.getIdMaxCount").getResultList();
		Iterator maxId = id.iterator();
		while (maxId.hasNext()) 
		{
			logger.info("maxId.next()\n" + maxId.next());
		}
		return id;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getRequiredFields()
	{
		return entityManager.createNamedQuery("ItemMaster.getRequiredFields").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllAssetSpares(int assetId) {
		return entityManager.createNamedQuery("ItemMaster.getAllAssetSpares").setParameter("assetId", assetId).getResultList();
	}
	
	@Override
	public List<?> getItemNames() {
		return entityManager.createNamedQuery("ItemMaster.getItemNames").getResultList();
	}

	@Override
	public List<?> getDetailsFromLedgerAndItemMasterForCronScheduler()
	{	
		return entityManager.createNamedQuery("ItemMaster.getDetailsFromLedgerAndItemMasterForCronScheduler").getResultList();
	}

	@Override
	public int updateReorderLevelStatus(int id) 
	{
		return entityManager.createNamedQuery("ItemMaster.updateReorderLevelStatus").setParameter("id", id).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getGraph(String type) 
	{
		return entityManager.createNamedQuery("Item.ReadCreditAndDebitDataForGraph").setParameter("type", type).getResultList();
	}
	
	@Override
	public List<?> getReqId(int storeId) 
	{	
		return entityManager.createNamedQuery("Item.getReqId").setParameter("storeId", storeId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getReqDetails(int reqId) 
	{	
		return entityManager.createNamedQuery("Item.getAllDetails").setParameter("reqId", reqId).getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getGraph1(int id,String type) 
	{	
		return entityManager.createNamedQuery("ItemMaster.readCreditAndDebitDataBasedOnItemId").setParameter("id", id).setParameter("type", type) .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMaster> findItemTypes() {
		return entityManager.createNamedQuery("ItemMaster.findItemTypes").getResultList();

	}

	/*@Override
	public List<?> checkItemIdExistenceFromReqDetails(int itemId,int uomId) 
	{
		return entityManager.createNamedQuery("ItemMaster.checkItemIdExistenceFromReqDetails").setParameter("itemId", itemId).setParameter("uomId", uomId).getResultList();
	}*/
}
