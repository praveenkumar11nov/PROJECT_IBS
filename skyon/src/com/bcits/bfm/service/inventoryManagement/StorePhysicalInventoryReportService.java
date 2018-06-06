package com.bcits.bfm.service.inventoryManagement;

import java.util.List;

import com.bcits.bfm.model.StorePhysicalInventoryReport;
import com.bcits.bfm.service.GenericService;

public interface StorePhysicalInventoryReportService extends GenericService<StorePhysicalInventoryReport>
{
	List<StorePhysicalInventoryReport> getAllStorePhysicalInventoryReportsBasedOnSPIId(int spiId);
}