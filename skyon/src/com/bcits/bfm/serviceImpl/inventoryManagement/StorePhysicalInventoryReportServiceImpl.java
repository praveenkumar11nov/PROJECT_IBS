package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StorePhysicalInventoryReport;
import com.bcits.bfm.service.inventoryManagement.StorePhysicalInventoryReportService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StorePhysicalInventoryReportServiceImpl extends GenericServiceImpl<StorePhysicalInventoryReport> implements StorePhysicalInventoryReportService
{
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StorePhysicalInventoryReport> getAllStorePhysicalInventoryReportsBasedOnSPIId(int spiId)
	{
		List<StorePhysicalInventoryReport> newList = new ArrayList<StorePhysicalInventoryReport>();
		List<StorePhysicalInventoryReport> list = entityManager.createNamedQuery("StorePhysicalInventoryReport.getAllStorePhysicalInventoryReportsBasedOnSPIId", StorePhysicalInventoryReport.class).setParameter("spiId", spiId).getResultList();
		for (Iterator<StorePhysicalInventoryReport> iterator = list.iterator(); iterator.hasNext();)
		{
			StorePhysicalInventoryReport storePhysicalInventoryReport = (StorePhysicalInventoryReport) iterator
					.next();
			storePhysicalInventoryReport.setUnavailableBalance(storePhysicalInventoryReport.getExpectedBalance() - storePhysicalInventoryReport.getAvailableBalance());
			storePhysicalInventoryReport.setStoreName(storePhysicalInventoryReport.getStoreMaster().getStoreName());
			storePhysicalInventoryReport.setStoreMaster(null);
			storePhysicalInventoryReport.setImName(storePhysicalInventoryReport.getItemMaster().getImName());
			storePhysicalInventoryReport.setItemMaster(null);
			storePhysicalInventoryReport.setUom(storePhysicalInventoryReport.getUnitOfMeasurement().getUom());
			storePhysicalInventoryReport.setUnitOfMeasurement(null);
			newList.add(storePhysicalInventoryReport);
		}
		return newList;
	}
}
