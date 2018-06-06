package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ItemMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.ItemMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ItemMasterServiceImp extends GenericServiceImpl<ItemMasterEntity> implements ItemMasterService{

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMasterEntity> findAllData() {
		return getItemData(entityManager.createNamedQuery("ItemMasterEntity.findAllData").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getItemData(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final ItemMasterEntity values = (ItemMasterEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("gid", values.getGid());
			 storeData.put("sId", values.getsId());
			 storeData.put("itemName",values.getItemName());		
			 storeData.put("price",values.getPrice());		
			 storeData.put("unitOfMeasure",values.getUnitOfMeasure());		
			 storeData.put("discount",values.getDiscount());
			 storeData.put("category",values.getCategory());
			 storeData.put("itemReview",values.getItemReview());
			 storeData.put("description",values.getDescription());
			 storeData.put("storeName",values.getStoreMasterEntity().getStoreName());
			  result.add(storeData); 
		 }
		 return result;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadImageOnId(int gid,Blob itemImage) {
		entityManager.createNamedQuery("Item.uploadImageOnId")
				.setParameter("gid", gid)
				.setParameter("itemImage", itemImage)
				.setParameter("lastUpdatedDate", (new Timestamp(new Date().getTime())))
				.executeUpdate();
		}

	@Override
	public Blob getImage(int gid) {
				
			return	(Blob) entityManager.createNamedQuery("ItemMaster.getImage", Blob.class)
			    .setParameter("gid", gid)
			    .getSingleResult();
	}

	@Override
	public void uploadAssetOnId(int gid, Blob itemImage, String docType) {
		entityManager.createNamedQuery("Items.uploadItemsOnId")
		.setParameter("gid", gid)
		.setParameter("itemImage", itemImage)
		.setParameter("docType", docType)
		.executeUpdate();
	}

	
	@Override
	 @Transactional(propagation = Propagation.REQUIRED)
	 public void updateVisitorImage(int gid,Blob blob) 
	 {
	  entityManager.createNamedQuery("Itemmaster.updateVisitorImage")
	  .setParameter("vmId", gid).setParameter("itemImage", blob)
	  .executeUpdate();
	 }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List findAllDatas(int sid) {
		return getItemDatas(entityManager.createNamedQuery("ItemMasterEntity.findAllDatas").setParameter("sid", sid).getResultList());
	}
	
	

	@SuppressWarnings("rawtypes")
	private List getItemDatas(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final ItemMasterEntity values = (ItemMasterEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("gid", values.getGid());
			 storeData.put("sId", values.getsId());
			 storeData.put("itemName",values.getItemName());		
			 storeData.put("price",values.getPrice());		
			 storeData.put("unitOfMeasure",values.getUnitOfMeasure());		
			 storeData.put("discount",values.getDiscount());
			 storeData.put("category",values.getCategory());
			 storeData.put("itemReview",values.getItemReview());
			 storeData.put("description",values.getDescription());
			 storeData.put("storeName",values.getStoreMasterEntity().getStoreName());
			  result.add(storeData); 
		 }
		 return result;
	}

}



