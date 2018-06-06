package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import com.bcits.bfm.model.StoreIssue;
import com.bcits.bfm.service.GenericService;

public interface StoreIssueService extends GenericService<StoreIssue>
{
	List<StoreIssue> findAllStoreIssues();

	List<StoreIssue> getAllStoresRequiredFields(List<StoreIssue> list);
}