package com.bcits.bfm.serviceImpl.inventoryManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreAdjustments;
import com.bcits.bfm.service.inventoryManagement.StoreAdjustmentsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class StoreAdjustmentsServiceImpl extends GenericServiceImpl<StoreAdjustments> implements StoreAdjustmentsService
{
	@Resource
	private DateTimeCalender dateTimeCalender;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreAdjustments> findAllStoreAdjustments()
	{
		return entityManager.createNamedQuery("StoreAdjustments.findAllStoreAdjustments", StoreAdjustments.class).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StoreAdjustments> getAllStoreAdjustmentsRequiredFields(
			List<StoreAdjustments> list)
	{
		List<StoreAdjustments> newStoreAdjustmentsList = new ArrayList<StoreAdjustments>();
		for (Iterator<StoreAdjustments> iterator = list.iterator(); iterator.hasNext();)
		{
			StoreAdjustments storeAdjustments = (StoreAdjustments) iterator
					.next();

			if(storeAdjustments.getSaDt() != null)
			{
				storeAdjustments.setSaDate(dateTimeCalender.getDateFromString(storeAdjustments.getSaDt().toString()));
				storeAdjustments.setSaTime(dateTimeCalender.getTimeFromString(storeAdjustments.getSaDt().toString()));
			}
			
			storeAdjustments.setUom(storeAdjustments.getUnitOfMeasurement().getUom());
			storeAdjustments.setUnitOfMeasurement(null);
			
			storeAdjustments.setImName(storeAdjustments.getItemMaster().getImName());
			storeAdjustments.setItemMaster(null);
			
			storeAdjustments.setContractName(storeAdjustments.getVendorContracts().getContractName());
			storeAdjustments.setVendorContracts(null);
			
			storeAdjustments.setStoreName(storeAdjustments.getStoreMaster().getStoreName());
			storeAdjustments.setStoreMaster(null);
			
			String approvedByStaffName = "";
			approvedByStaffName = storeAdjustments.getUsers().getPerson().getFirstName();
			if((storeAdjustments.getUsers().getPerson().getLastName()) != null)
			{
				approvedByStaffName = approvedByStaffName.concat(" ");
				approvedByStaffName = approvedByStaffName.concat(storeAdjustments.getUsers().getPerson().getLastName());
			}
			
			storeAdjustments.setPersonName(approvedByStaffName);
			storeAdjustments.setUsers(null);
			
			newStoreAdjustmentsList.add(storeAdjustments);
			
		}
		return newStoreAdjustmentsList;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void setstoreAdjustmentStatus(int saId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();

			entityManager.createNamedQuery("StoreAdjustments.setstoreAdjustmentStatus").setParameter("saId", saId).executeUpdate();

			out.write("Item adjustment is activated and updated into Ledger");
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
