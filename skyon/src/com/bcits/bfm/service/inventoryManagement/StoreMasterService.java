package com.bcits.bfm.service.inventoryManagement;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.service.GenericService;

public interface StoreMasterService extends GenericService<StoreMaster>
{
	String setStoreStatus(int storeId, String operation,
			HttpServletResponse response) throws SQLException;

	List<?> getStoresBasedOnContractExistence();

	List<StoreMaster> getAllStoresRequiredFields();

	List<?> getStoresBasedOnContractExistenceForAdjustment();

	List<String> getStoreNamesForFilterList();

	List<?> getstoreInchargeStaffNamesFilterList();

	List<String> getStoreLocationFilterUrl();
}