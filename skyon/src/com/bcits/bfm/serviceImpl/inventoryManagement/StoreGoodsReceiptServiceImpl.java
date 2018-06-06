package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreCategory;
import com.bcits.bfm.model.StoreGoodsReceipt;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class StoreGoodsReceiptServiceImpl extends GenericServiceImpl<StoreGoodsReceipt> implements StoreGoodsReceiptService
{
	@Resource
	private DateTimeCalender dateTimeCalender;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreGoodsReceipt> getRequiredStoreGoodsReceipts()
	{
		List<StoreGoodsReceipt> requiredStoreGoodsReceiptList = new ArrayList<StoreGoodsReceipt>();
		List<?> storeReceiptGoodsList = entityManager.createNamedQuery("StoreGoodsReceipt.getRequiredStoreGoodsReceipts").getResultList();
		
		for (Iterator<?> i = storeReceiptGoodsList.iterator(); i.hasNext();) 
		{
			StoreGoodsReceipt storeGoodsReceipt = new StoreGoodsReceipt();
			final Object[] values = (Object[]) i.next();
			storeGoodsReceipt.setStgrId((Integer)values[0]);
			storeGoodsReceipt.setPoRecDt((Timestamp)values[1]);
			
			if(storeGoodsReceipt.getPoRecDt() != null)
			{
				storeGoodsReceipt.setPoRecDate(dateTimeCalender.getDateFromString(storeGoodsReceipt.getPoRecDt().toString()));
				storeGoodsReceipt.setPoRecTime(dateTimeCalender.getTimeFromString(storeGoodsReceipt.getPoRecDt().toString()));
			}
			
			storeGoodsReceipt.setDrGroupId((Integer)values[2]);
			storeGoodsReceipt.setReceivedByStaffId((Integer)values[3]);
			storeGoodsReceipt.setCheckedByStaffId((Integer)values[4]);
			storeGoodsReceipt.setLedgerUpdateDt((Timestamp)values[5]);
			storeGoodsReceipt.setCreatedBy((String)values[6]);
			storeGoodsReceipt.setLastUpdatedBy((String)values[7]);
			storeGoodsReceipt.setLastUpdatedDt((Timestamp)values[8]);
			storeGoodsReceipt.setShippingDocumentNumber((String)values[9]);
			storeGoodsReceipt.setVcId((Integer)values[10]);
			storeGoodsReceipt.setStoreId((Integer)values[11]);
			storeGoodsReceipt.setContractName((String)values[12]);
			storeGoodsReceipt.setStoreName((String)values[13]);

			String receivedByStaffName = (String)values[14];
			if((String)values[15] != null)
			{
				receivedByStaffName = receivedByStaffName.concat(" ");
				receivedByStaffName = receivedByStaffName.concat((String)values[15]);
			}
			storeGoodsReceipt.setReceivedByStaffName(receivedByStaffName);
			
			String checkedByStaffName = (String)values[16];
			if((String)values[17] != null)
			{
				checkedByStaffName = checkedByStaffName.concat(" ");
				checkedByStaffName = checkedByStaffName.concat((String)values[17]);
			}
			storeGoodsReceipt.setCheckedByStaffName(checkedByStaffName);
			
			storeGoodsReceipt.setContractNo((String)values[18]);
			storeGoodsReceipt.setStoreLocation((String)values[19]);
			
			requiredStoreGoodsReceiptList.add(storeGoodsReceipt);
		}
		
		return requiredStoreGoodsReceiptList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getItemsBasedOnVendorContractId(int vcId)
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getItemsBasedOnVendorContractId").setParameter("vcId", vcId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> populateCustomCategories()
	{
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        Map<String, Object> mapResult = null;
        
        mapResultSelect.put("value", 0);
        mapResultSelect.put("text", "Select");
        
        listResult.add(mapResultSelect);
        
        List<VendorContracts> categoriesList = entityManager.createNamedQuery("StoreGoodsReceipt.getVendorContractsWhoseItemsReqTypesAreAssetsAndConsumables").getResultList();
        for (Iterator<VendorContracts> iterator = categoriesList.iterator(); iterator.hasNext();)
		{
			VendorContracts vendorContracts = (VendorContracts) iterator.next();
			
			mapResult = new HashMap<String, Object>();
			mapResult.put("value", vendorContracts.getVcId());
			mapResult.put("text", vendorContracts.getContractName());
			
			listResult.add(mapResult);
			
		}

        return listResult;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<VendorContracts> getAllRequiredVCWhosHasChildrenVCLI()
	{
		return entityManager.createNamedQuery("VendorContracts.getAllRequiredVCWhosHasChildrenVCLI", VendorContracts.class).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getRequiredContractDetails()
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getRequiredContractDetails").getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllRequiredItemsFieldsForStoreMovement()
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getAllRequiredItemsFieldsForStoreMovement").getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getRequiredStoreFields(int vcId, int imId)
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getRequiredStoreFields").setParameter("vcId", vcId).setParameter("imId", imId).getResultList();
	}

	@Override
	public void updateLedgerDate(int stgrId, Timestamp ledgerUpdateDt)
	{
		entityManager.createNamedQuery("StoreGoodsReceipt.updateLedgerDate")
		.setParameter("stgrId", stgrId)
		.setParameter("ledgerUpdateDt", ledgerUpdateDt).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getRequiredStoreItems(int storeId, int imId)
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getRequiredStoreItems").setParameter("storeId", storeId).setParameter("imId", imId).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<VendorContracts> getAllRequiredVCWhosHasChildrenVCLIIncludingDuplicates()
	{
		return entityManager.createNamedQuery("VendorContracts.getAllRequiredVCWhosHasChildrenVCLIIncludingDuplicates", VendorContracts.class).getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getDataForTree()
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getDataForTree").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreCategory> findAllForTreeByStoreCategoryId(String storeCategoryId)
	{
		List<StoreCategory> inventorylist = null;
    	
        if (storeCategoryId == null) {
        	storeCategoryId="1";
        	inventorylist = entityManager.createNamedQuery("StoreCategory.findAllForTreeByStoreCategoryId",StoreCategory.class).setParameter("parentId", storeCategoryId).getResultList();
     		
        } else {
        	inventorylist = entityManager.createNamedQuery("StoreCategory.findAllForTreeByStoreCategoryId", StoreCategory.class).setParameter("parentId", storeCategoryId).getResultList();
     		
        }
       return inventorylist;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getRequiredContractDetailsBasedOnStoreIdAndImId(int storeId,
			int imId)
	{
		return entityManager.createNamedQuery("StoreGoodsReceipt.getRequiredContractDetailsBasedOnStoreIdAndImId").setParameter("storeId", storeId).setParameter("imId", imId).getResultList();
	}
	
}
