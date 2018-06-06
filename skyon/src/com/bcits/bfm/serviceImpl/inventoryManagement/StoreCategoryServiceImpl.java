package com.bcits.bfm.serviceImpl.inventoryManagement;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreCategory;
import com.bcits.bfm.service.inventoryManagement.StoreCategoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StoreCategoryServiceImpl extends GenericServiceImpl<StoreCategory> implements StoreCategoryService
{
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void deleteTreeDate()
	{
		entityManager.createNamedQuery("StoreCategory.deleteTreeDate").executeUpdate();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void deleteSelectedValue(String storecategoryid,String storeNameselected, String parent) {
		
		entityManager.createNamedQuery("StoreCategory.deleteSelectedValue").setParameter("storecategoryid",storecategoryid ).setParameter("storeNameselected", storeNameselected).setParameter("parent", parent).executeUpdate();

		
	}
}
