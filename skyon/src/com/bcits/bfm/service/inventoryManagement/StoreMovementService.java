package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.StoreMovement;
import com.bcits.bfm.service.GenericService;

public interface StoreMovementService extends GenericService<StoreMovement>
{
	List<StoreMovement> getAllStoreMovementsRequiredFields();
	void setStoreMovementStatus(int stmId, HttpServletResponse response);
}