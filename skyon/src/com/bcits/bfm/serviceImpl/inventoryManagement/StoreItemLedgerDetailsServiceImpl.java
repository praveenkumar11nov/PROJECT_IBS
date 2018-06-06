package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreItemLedgerDetails;
import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerDetailsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StoreItemLedgerDetailsServiceImpl extends GenericServiceImpl<StoreItemLedgerDetails> implements StoreItemLedgerDetailsService
{
	@Autowired
	private UomService uomService;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreItemLedgerDetails> getAllStoreItemLedgerDetailsBasedOnSILId(int silId)
	{
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		
		List<StoreItemLedgerDetails> finalList = new ArrayList<StoreItemLedgerDetails>();
		List<StoreItemLedgerDetails> list = entityManager.createNamedQuery("StoreItemLedgerDetails.getAllStoreItemLedgerDetailsBasedOnSILId", StoreItemLedgerDetails.class).setParameter("silId", silId).getResultList();
		
		for (Iterator<StoreItemLedgerDetails> iterator = list.iterator(); iterator.hasNext();)
		{
			StoreItemLedgerDetails storeItemLedgerDetails = (StoreItemLedgerDetails) iterator
					.next();
			UnitOfMeasurement unitOfMeasurement = uomService.find(storeItemLedgerDetails.getUomId());
			if(unitOfMeasurement.getBaseUom().equalsIgnoreCase("Yes"))
			{
				String finalString = storeItemLedgerDetails.getQuantity().toString().concat(" * 1 = ")+storeItemLedgerDetails.getQuantity().toString();
				storeItemLedgerDetails.setQuantityPerBaseUom(finalString);
			}
			else
			{
				Double quantity = Double.parseDouble(decimalFormat.format(storeItemLedgerDetails.getQuantity() * (unitOfMeasurement.getUomConversion())));
				String finalString = storeItemLedgerDetails.getQuantity().toString().concat(" * ") + (unitOfMeasurement.getUomConversion() +" = "+quantity.toString());
				storeItemLedgerDetails.setQuantityPerBaseUom(finalString);
			}
			finalList.add(storeItemLedgerDetails);
		}
		return finalList;
	}
	

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public StoreItemLedger getObject(int storeId, int imId) {
		
		return entityManager.createNamedQuery("StoreItemLedger.getObject",StoreItemLedger.class).setParameter("storeMaster",storeId).setParameter("itemMaster",imId).getResultList().get(0);
	}

}
