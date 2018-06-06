package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.StoreAdjustments;
import com.bcits.bfm.service.GenericService;

public interface StoreAdjustmentsService extends GenericService<StoreAdjustments>
{
	List<StoreAdjustments> findAllStoreAdjustments();
	List<StoreAdjustments> getAllStoreAdjustmentsRequiredFields(List<StoreAdjustments> list);
	void setstoreAdjustmentStatus(int saId, HttpServletResponse response);
}