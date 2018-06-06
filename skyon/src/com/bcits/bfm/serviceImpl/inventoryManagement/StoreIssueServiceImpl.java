package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreIssue;
import com.bcits.bfm.service.inventoryManagement.StoreIssueService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class StoreIssueServiceImpl extends GenericServiceImpl<StoreIssue> implements StoreIssueService
{
	@Autowired
	private DateTimeCalender dateTimeCalender;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreIssue> findAllStoreIssues()
	{
		return entityManager.createNamedQuery("StoreIssue.findAllStoreIssues", StoreIssue.class).getResultList();
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreIssue> getAllStoresRequiredFields(List<StoreIssue> list)
	{
		List<StoreIssue> finalList = new ArrayList<StoreIssue>();
		for (Iterator<StoreIssue> iterator = list.iterator(); iterator.hasNext();)
		{
			StoreIssue storeIssue = (StoreIssue) iterator.next();
			
			if(storeIssue.getStriDt() != null)
			{
				storeIssue.setStriDate(dateTimeCalender.getDateFromString(storeIssue.getStriDt().toString()));
				storeIssue.setStriTime(dateTimeCalender.getTimeFromString(storeIssue.getStriDt().toString()));
			}
			
			storeIssue.setJobNo(storeIssue.getJcMaterials().getJobCards().getJobNo());
			storeIssue.setJcId(storeIssue.getJcMaterials().getJcmId());
			storeIssue.setJcMaterials(null);
			storeIssue.setStoreName(storeIssue.getStoreMaster().getStoreName());
			storeIssue.setStoreMaster(null);
			storeIssue.setContractName(storeIssue.getVendorContracts().getContractName());
			storeIssue.setVendorContracts(null);
			storeIssue.setImName(storeIssue.getItemMaster().getImName());
			storeIssue.setItemMaster(null);
			storeIssue.setUom(storeIssue.getUnitOfMeasurement().getUom());
			storeIssue.setUnitOfMeasurement(null);
			finalList.add(storeIssue);
		}
		return finalList;
	}
}
