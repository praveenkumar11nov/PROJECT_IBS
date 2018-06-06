package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StorePhysicalInventory;
import com.bcits.bfm.model.StorePhysicalInventoryReport;
import com.bcits.bfm.service.inventoryManagement.StorePhysicalInventoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StorePhysicalInventoryServiceImpl extends GenericServiceImpl<StorePhysicalInventory> implements StorePhysicalInventoryService
{
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StorePhysicalInventory> findAllStorePhysicalInventory()
	{
		List<StorePhysicalInventory> newList = new ArrayList<StorePhysicalInventory>();
		List<StorePhysicalInventory> list = entityManager.createNamedQuery("StorePhysicalInventory.findAllStorePhysicalInventory", StorePhysicalInventory.class).getResultList();
		
		for (Iterator<StorePhysicalInventory> iterator = list.iterator(); iterator.hasNext();)
		{
			StorePhysicalInventory storePhysicalInventory = (StorePhysicalInventory) iterator
					.next();
			
			Set<StorePhysicalInventoryReport> storePhysicalInventoryReportsSet = storePhysicalInventory.getStorePhysicalInventoryReportsSet();
			
			String str = "";
			
			for (Iterator<StorePhysicalInventoryReport> iterator2 = storePhysicalInventoryReportsSet
					.iterator(); iterator2.hasNext();)
			{
				StorePhysicalInventoryReport storePhysicalInventoryReport = (StorePhysicalInventoryReport) iterator2
						.next();
				str = str.concat("I-"+storePhysicalInventoryReport.getImId()+"-"+storePhysicalInventoryReport.getStoreId());
				str = str.concat(",");
			}
			
			
			storePhysicalInventory.setCategoryIds(str);
			storePhysicalInventory.setStorePhysicalInventoryReportsSet(null);
			newList.add(storePhysicalInventory);
		}
		
		return newList;
	}

}
