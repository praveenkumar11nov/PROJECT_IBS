package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ClubHouse;
import com.bcits.bfm.model.ClubHouseBooking;
import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.ClubHouseBookingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ClubHouseBookingServiceImpl extends GenericServiceImpl<ClubHouseBooking> implements ClubHouseBookingService{

	@SuppressWarnings("unchecked")
	@Override
	public List<ClubHouse> findAll() {
		// TODO Auto-generated method stub
		return getStorenames(entityManager.createNamedQuery("ClubHouse.findAllServiceNames").getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List getStorenames(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId",(Integer) values[0]);
			 storeData.put("serviceName",(String)values[1]);		
			
		     result.add(storeData); 
		 }
		 return result;
	}
	@Override
	public List<ClubHouseBooking> findAllData() {
	
		return getServiceData(entityManager.createNamedQuery("ClubHouseBooking.findAllData").getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List getServiceData(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final  ClubHouseBooking values = (ClubHouseBooking) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId", values.getsId());
			 storeData.put("sbId", values.getSbId());
			 storeData.put("serviceName",values.getClubHouse().getServiceName());		
			 storeData.put("serviceDesc",values.getServiceDesc());
			 storeData.put("serviceAction",values.getServiceAction());
			 storeData.put("status",values.getStatus());
			 storeData.put("serviceDesc",values.getServiceDesc());
			
		     result.add(storeData); 
		 }
		 return result;
	}

	@Override
	public List<ClubHouseBooking> findServiceName(int sId) {
		return getStorenames(entityManager.createNamedQuery("ClubHouse.findAllService").setParameter("sId", sId).getResultList());
	}

}
