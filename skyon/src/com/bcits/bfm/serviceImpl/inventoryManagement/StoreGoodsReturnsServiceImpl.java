package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreGoodsReturns;
import com.bcits.bfm.service.facilityManagement.JcMaterialsService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReturnsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class StoreGoodsReturnsServiceImpl extends GenericServiceImpl<StoreGoodsReturns> implements StoreGoodsReturnsService
{
	@Resource
	private DateTimeCalender dateTimeCalender;
	@Autowired
	private JcMaterialsService jcMaterialsService;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findAllStoreGoodsReturns()
	{
		return entityManager.createNamedQuery("StoreGoodsReturns.findAllStoreGoodsReturns").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreGoodsReturns> getAllStoreGoodsReturnsRequiredFields(
			List<?> list)
	{
		List<StoreGoodsReturns> newStoreGoodsReturnsList = new ArrayList<StoreGoodsReturns>();
		StoreGoodsReturns storeGoodsReturns = null;
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			storeGoodsReturns = new StoreGoodsReturns();
			storeGoodsReturns.setSgrId((Integer)values[0]);
			storeGoodsReturns.setStoreId((Integer)values[1]);
			storeGoodsReturns.setImId((Integer)values[2]);
			storeGoodsReturns.setUomId((Integer)values[3]);
			storeGoodsReturns.setReturnedToVendorId((Integer)values[4]);
			storeGoodsReturns.setReturnedByStaffId((Integer)values[5]);
			storeGoodsReturns.setItemReturnQuantity((Double)values[6]);
			storeGoodsReturns.setReasonForReturn((String)values[7]);
			if((Date)values[8] != null)
			storeGoodsReturns.setDateOfReturn((Date)values[8]);
			storeGoodsReturns.setCreatedBy((String)values[9]);
			storeGoodsReturns.setLastUpdatedBy((String)values[10]);
			storeGoodsReturns.setLastUpdatedDt((Timestamp)values[11]);
			storeGoodsReturns.setStoreName((String)values[12]);
			storeGoodsReturns.setImName((String)values[13]);
			storeGoodsReturns.setUom((String)values[14]);
			
			String returnedToVendorName = "";
			returnedToVendorName = (String)values[16];
			if((String)values[17] != null)
			{
				returnedToVendorName = returnedToVendorName.concat(" ");
				returnedToVendorName = returnedToVendorName.concat((String)values[17]);
			}
			storeGoodsReturns.setReturnedToVendorName(returnedToVendorName);
			
			String returnedByStaffName = "";
			returnedByStaffName = (String)values[19];
			if((String)values[20] != null)
			{
				returnedByStaffName = returnedByStaffName.concat(" ");
				returnedByStaffName = returnedByStaffName.concat((String)values[20]);
			}
			storeGoodsReturns.setReturnedByStaffName(returnedByStaffName);
			
			storeGoodsReturns.setJcId((Integer)values[21]);
			storeGoodsReturns.setJobNo((String)values[22]);
			
			newStoreGoodsReturnsList.add(storeGoodsReturns);
		}
		return newStoreGoodsReturnsList;
	}

	@Override
	public String setStoreGoodsReturnStatus(int sgrId)
	{
		entityManager.createNamedQuery("StoreGoodsReturns.setStoreGoodsReturnStatus").setParameter("sgrId", sgrId).executeUpdate();
		return "Item returned successfully and updated into Ledger";
	}
}
