package com.bcits.bfm.service.inventoryManagement;

import com.bcits.bfm.model.StoreCategory;
import com.bcits.bfm.service.GenericService;

public interface StoreCategoryService extends GenericService<StoreCategory>
{
	void deleteTreeDate();

	void deleteSelectedValue(String storecategoryid, String storeNameselected,
			String parent);
}