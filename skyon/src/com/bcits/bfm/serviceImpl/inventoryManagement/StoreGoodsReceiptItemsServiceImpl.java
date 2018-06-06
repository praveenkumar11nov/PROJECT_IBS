package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreGoodsReceiptItems;
import com.bcits.bfm.service.VendorsManagement.RequisitionDetailsService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptItemsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class StoreGoodsReceiptItemsServiceImpl extends GenericServiceImpl<StoreGoodsReceiptItems> implements StoreGoodsReceiptItemsService
{
	@Autowired
	private DateTimeCalender dateTimeCalender;
	
	@Autowired 
	private RequisitionDetailsService requisitionDetailsService;
	
	@Autowired
	private UomService uomService;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreGoodsReceiptItems> findAllStoreGoodsReceiptItems(int stgrId)
	{
		List<StoreGoodsReceiptItems> list = entityManager.createNamedQuery("StoreGoodsReceiptItems.findAllStoreGoodsReceiptItems").setParameter("stgrId", stgrId).getResultList();
		return getAllStoreGoodsReceiptItemsRequiredFields(list);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	private List<StoreGoodsReceiptItems> getAllStoreGoodsReceiptItemsRequiredFields(
			List<StoreGoodsReceiptItems> list)
	{
		List<StoreGoodsReceiptItems> newStoreGoodsReceiptItemsList = new ArrayList<StoreGoodsReceiptItems>();
		for (Iterator<StoreGoodsReceiptItems> iterator = list.iterator(); iterator.hasNext();)
		{
			StoreGoodsReceiptItems storeGoodsReceiptItems = (StoreGoodsReceiptItems) iterator
					.next();
			try{
				if(storeGoodsReceiptItems.getItemExpiryDate() != null)
					storeGoodsReceiptItems.setItemExpiryDate(dateTimeCalender.getDateFromString(storeGoodsReceiptItems.getItemExpiryDate().toString()));
				if(storeGoodsReceiptItems.getItemManufactureDate() != null)
					storeGoodsReceiptItems.setItemManufactureDate(dateTimeCalender.getDateFromString(storeGoodsReceiptItems.getItemManufactureDate().toString()));
				if(storeGoodsReceiptItems.getWarrantyValidTill() != null)
					storeGoodsReceiptItems.setWarrantyValidTill(dateTimeCalender.getDateFromString(storeGoodsReceiptItems.getWarrantyValidTill().toString()));
			}catch(Exception e){
				
			}			
			
			storeGoodsReceiptItems.setImName(storeGoodsReceiptItems.getItemMaster().getImName());
			storeGoodsReceiptItems.setImType(storeGoodsReceiptItems.getItemMaster().getImType());
			storeGoodsReceiptItems.setItemMaster(null);
			
			storeGoodsReceiptItems.setUomPurchase(storeGoodsReceiptItems.getUnitOfMeasurement().getUom());
			storeGoodsReceiptItems.setUomIssue(storeGoodsReceiptItems.getUnitOfMeasurement().getCode());
			storeGoodsReceiptItems.setUnitOfMeasurement(null);
			
			
			newStoreGoodsReceiptItemsList.add(storeGoodsReceiptItems);
			
		}
		return newStoreGoodsReceiptItemsList;
	}

	@Override
	public void updateRequiredFields(int sgriId, double itemQuantity)
	{
		entityManager.createNamedQuery("StoreGoodsReceiptItems.updateRequiredFields")
			.setParameter("sgriId", sgriId)
			.setParameter("itemQuantity", itemQuantity)
			.setParameter("receiptType", "Partially Transferred")
			.executeUpdate();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String setStoreGoodsReceiptItemsStatus(int sgriId, Timestamp activationDt)
	{
		entityManager.createNamedQuery("StoreGoodsReceiptItems.setStoreGoodsReceiptItemsStatus").setParameter("sgriId", sgriId).setParameter("activationDt", activationDt).executeUpdate();
		return "Item is activated and updated into Ledger";
	}

}
