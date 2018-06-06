package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreItemLedgerDetails;
import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;


@Repository
public class StoreItemLedgerServiceImpl extends GenericServiceImpl<StoreItemLedger> implements StoreItemLedgerService
{
	@Autowired
	private DateTimeCalender dateTimeCalender;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreItemLedger> findAllStoreItemLedgers()
	{
		List<StoreItemLedger> finalStoreItemLedgerList = new ArrayList<StoreItemLedger>();
		
		List<?> storeItemLedgerList = entityManager.createNamedQuery("StoreItemLedger.findAllRequiredStoreItemLedgers").getResultList();
		
		for (Iterator<?> i = storeItemLedgerList.iterator(); i.hasNext();) 
		{
			StoreItemLedger storeItemLedger = new StoreItemLedger();
			final Object[] values = (Object[]) i.next();
			
			storeItemLedger.setSilId((Integer) values[0]);
			storeItemLedger.setStoreId((Integer) values[1]);
			storeItemLedger.setImId((Integer) values[2]);
			storeItemLedger.setSilDt((Timestamp) values[3]);
			storeItemLedger.setImUom((Integer) values[4]);
			storeItemLedger.setImBalance((Double) values[5]);
			storeItemLedger.setStoreEntryFrom((String) values[6]);
			storeItemLedger.setCreatedBy((String) values[7]);
			storeItemLedger.setLastUpdatedBy((String) values[8]);
			storeItemLedger.setLastUpdatedDt((Timestamp) values[9]);
			storeItemLedger.setStoreName((String) values[10]);
			storeItemLedger.setImName((String) values[11]);
			storeItemLedger.setUom((String) values[12]);
			storeItemLedger.setStoreLocation((String) values[13]);
			storeItemLedger.setImType((String) values[14]);
		
			finalStoreItemLedgerList.add(storeItemLedger);
		}
		
		return finalStoreItemLedgerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreItemLedger> findByUOM(ItemMaster record,int storeId) {
		
		return entityManager.createNamedQuery("StoreItemLedger.findByUOM").setParameter("itemMaster", record).setParameter("storeId", storeId).getResultList();
	}	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreItemLedger> findByItemMaster(ItemMaster itemMaster) {
		
		return entityManager.createNamedQuery("StoreItemLedger.findByItemMaster").setParameter("itemMaster", itemMaster).getResultList();
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreMaster> findRequiredAllStoresFromItemLedger()
	{
		return entityManager.createNamedQuery("StoreItemLedger.findRequiredAllStoresFromItemLedger", StoreMaster.class).getResultList();
	}

	@Override
	public void updateItems(StoreItemLedger sil, int storeId, int imId, Double availableBalance) {
		
		StoreItemLedger storeitem=new StoreItemLedger();
		Set<StoreItemLedgerDetails> storeItemLedgerDetailsSet = new HashSet<StoreItemLedgerDetails>();
		
		storeitem.setCreatedBy(sil.getCreatedBy());
		storeitem.setImBalance(availableBalance);
		storeitem.setImId(sil.getImId());
		storeitem.setImUom(sil.getImUom());
		storeitem.setStoreId(sil.getStoreId());
		storeitem.setLastUpdatedBy(sil.getLastUpdatedBy());
		storeitem.setSilDt(sil.getSilDt());
		storeitem.setSilId(sil.getSilId());
		storeItemLedgerDetailsSet.add(getstoreItemLedgerDetails(sil.getImUom(), "Reset By Survey", availableBalance));
		storeitem.setStoreItemLedgerDetailsSet(storeItemLedgerDetailsSet);
		update(storeitem);
	}
	
	private StoreItemLedgerDetails getstoreItemLedgerDetails(int uomId,String transactionType, Double finalQuantity)
	{
		StoreItemLedgerDetails storeItemLedgerDetails = new StoreItemLedgerDetails();
		storeItemLedgerDetails.setTransactionType(transactionType);
		storeItemLedgerDetails.setUomId(uomId);				
		
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		
		storeItemLedgerDetails.setQuantity(Double.parseDouble(decimalFormat.format(finalQuantity)));
		storeItemLedgerDetails.setStatus("Updated");		
		
		return storeItemLedgerDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreItemLedger> getBanceQuantity(int storeId, int itemId, int uomId) {
		
	   return entityManager.createNamedQuery("StoreItemLedger.getBanceQuantity").setParameter("itemMaster", itemId).setParameter("storeId", storeId).setParameter("imUom",uomId ).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreItemLedger> getStoreItemLedgerList(int itemId) {
		return entityManager.createNamedQuery("StoreItemLedger.getStoreItemLedgerList").setParameter("itemId", itemId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllItemLedgers() {
	
		return entityManager.createNamedQuery("StoreItemLedger.findAllRequiredStoreItemLedgers").getResultList();
	}
}
