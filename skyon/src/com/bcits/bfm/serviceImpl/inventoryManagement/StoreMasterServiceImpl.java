package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.CheckChildEntries;

@Repository
public class StoreMasterServiceImpl extends GenericServiceImpl<StoreMaster> implements StoreMasterService
{
	@Autowired
	private CheckChildEntries checkChildEntries;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreMaster> getAllStoresRequiredFields()
	{
		List<StoreMaster> finalStoresList = new ArrayList<StoreMaster>();
		List<?> storesList = entityManager.createNamedQuery("StoreMaster.getAllStoresRequiredFields").getResultList();
		
		for (Iterator<?> i = storesList.iterator(); i.hasNext();) 
		{
			StoreMaster storeMaster = new StoreMaster();
			final Object[] values = (Object[]) i.next();
			storeMaster.setStoreId((Integer)values[0]);
			storeMaster.setStoreName((String)values[1]);
			storeMaster.setStoreInchargeStaffId((Integer)values[2]);
			storeMaster.setStoreLocation((String)values[3]);
			storeMaster.setStoreStatus((String)values[4]);
			storeMaster.setCreatedBy((String)values[5]);
			storeMaster.setLastUpdatedBy((String)values[6]);
			storeMaster.setLastUpdatedDt((Timestamp)values[7]);


			String storeInchargeStaffName = (String)values[8];
			if((String)values[9] != null)
			{
				storeInchargeStaffName = storeInchargeStaffName.concat(" ");
				storeInchargeStaffName = storeInchargeStaffName.concat((String)values[9]);
			}
			storeMaster.setStoreInchargeStaffName(storeInchargeStaffName);
			
			finalStoresList.add(storeMaster);
		}
		
		return finalStoresList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String setStoreStatus(int storeId, String operation, HttpServletResponse response) throws SQLException
	{
		if(operation.equalsIgnoreCase("activate"))
		{
			entityManager.createNamedQuery("StoreMaster.setStoreStatus").setParameter("storeStatus", "Active").setParameter("storeId", storeId).executeUpdate();
			return "Store Activated";
		}
		else
		{
			String parentClasses = "STORE_MASTER";
			Set<String> entrySet = checkChildEntries.getChildEntries("STORE_ID", storeId, parentClasses);
			if(entrySet.size() > 0)
			{
				String classNames = "";
				for (Iterator<String> iterator = entrySet.iterator(); iterator
						.hasNext();)
				{
					String string = (String) iterator.next();
					classNames = classNames.concat(string);
					classNames = classNames.concat(", ");
				}
				classNames = classNames.substring(0, classNames.length() - 2);
				
				String[] str = classNames.split(",");
				String finalString = "";
				for (int i = 0; i < str.length; i++)
				{
					String[] finalStrArr = str[i].split("_");
					String finalStr = "";
					for (String string : finalStrArr)
					{
						finalStr = finalStr.concat(WordUtils.capitalizeFully(string.trim()));
						finalStr = finalStr.concat(" ");
					}
					
					finalString = finalString.concat((i+1)+". "+finalStr.trim());
					finalString = finalString.concat("<br/>");
				}
				
				return "Cannot deactivate Store since this store is already used in the following places<br/>"+finalString;
				
			
			}
			else
			{
				entityManager.createNamedQuery("StoreMaster.setStoreStatus").setParameter("storeStatus", "Inactive").setParameter("storeId", storeId).executeUpdate();
				return "Store Deactivated";
			}
			
		}	
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getStoresBasedOnContractExistence()
	{
		return entityManager.createNamedQuery("StoreMaster.getStoresBasedOnContractExistence").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getStoresBasedOnContractExistenceForAdjustment()
	{
		return entityManager.createNamedQuery("StoreMaster.getStoresBasedOnContractExistenceForAdjustment").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getStoreNamesForFilterList()
	{
		return entityManager.createNamedQuery("StoreMaster.getStoreNamesForFilterList", String.class).getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getstoreInchargeStaffNamesFilterList()
	{
		return entityManager.createNamedQuery("StoreMaster.getstoreInchargeStaffNamesFilterList").getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getStoreLocationFilterUrl() 
	{
		return entityManager.createNamedQuery("StoreMaster.getStoreLocationFilterUrl", String.class).getResultList();
	}
}
