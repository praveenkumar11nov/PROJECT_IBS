package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreMovement;
import com.bcits.bfm.service.VendorsManagement.RequisitionDetailsService;
import com.bcits.bfm.service.inventoryManagement.StoreMovementService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class StoreMovementServiceImpl extends GenericServiceImpl<StoreMovement> implements StoreMovementService
{
	@Autowired
	private DateTimeCalender dateTimeCalender;
	@Autowired 
	private RequisitionDetailsService requisitionDetailsService;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreMovement> getAllStoreMovementsRequiredFields()
	{
		List<?> storeMovementList = entityManager.createNamedQuery("StoreMovement.findAllStoreMovements").getResultList();
		List<StoreMovement> newStoreMovementsList = new ArrayList<StoreMovement>();
		StoreMovement storeMovement = null;
		for (Iterator<?> i = storeMovementList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			storeMovement = new StoreMovement();
			storeMovement.setStmId((Integer)values[0]);
			storeMovement.setToStoreId((Integer)values[1]);
			storeMovement.setFromStoreId((Integer)values[2]);
			storeMovement.setVcId((Integer)values[3]);
			storeMovement.setImId((Integer)values[4]);
			storeMovement.setUomId((Integer)values[5]);
			storeMovement.setItemQuantity((Double)values[6]);
			storeMovement.setItemManufacturer((String)values[7]);
			if((Date)values[8] != null)
				storeMovement.setItemExpiryDate(dateTimeCalender.getDateFromString(((Date)values[8]).toString()));
			if((Date)values[9] != null)
				storeMovement.setWarrantyValidTill(dateTimeCalender.getDateFromString(((Date)values[9]).toString()));
			storeMovement.setSpecialInstructions((String)values[10]);
			storeMovement.setPartNo((String)values[11]);
			storeMovement.setCreatedBy((String)values[12]);
			storeMovement.setLastUpdatedBy((String)values[13]);
			storeMovement.setLastUpdatedDt((Timestamp)values[14]);
			//storeMovement.setStmStatus((String)values[15]);
			storeMovement.setToStoreName((String)values[15]);
			storeMovement.setFromStoreName((String)values[16]);
			storeMovement.setContractName((String)values[17]);
			storeMovement.setImName((String)values[18]);
			storeMovement.setUom((String)values[19]);
			
			storeMovement.setToStoreLocation((String)values[20]);
			storeMovement.setFromStoreLocation((String)values[21]);
			storeMovement.setImType((String)values[22]);
			storeMovement.setImGroup((String)values[23]);
			storeMovement.setCode((String)values[24]);
			storeMovement.setBaseUom((String)values[25]);
			
			newStoreMovementsList.add(storeMovement);
		}
		return newStoreMovementsList;
	}

	@Override
	public void setStoreMovementStatus(int stmId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();

			entityManager.createNamedQuery("StoreMovement.setStoreMovementStatus").setParameter("stmId", stmId).executeUpdate();

			out.write("Item is activated and updated into Ledger");
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
