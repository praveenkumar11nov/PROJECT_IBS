package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ClubHouse;
import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.ClubHouseService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ClubHouseServiceImpl  extends GenericServiceImpl<ClubHouse> implements ClubHouseService{

	@Override
	public List<ClubHouse> findAllData() {
	
		return getStoreData(entityManager.createNamedQuery("ClubHouse.findAllData").getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List getStoreData(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final ClubHouse values = (ClubHouse) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId", values.getsId());
			
			 storeData.put("serviceName",values.getServiceName());		
			 storeData.put("serviceDesc",values.getServiceDesc());
			
		     result.add(storeData); 
		 }
		 return result;
	}


	@Override
	public Object findServiceName(int sId) {
		
		return entityManager.createNamedQuery("ClubHouse.findServiceName").setParameter("sId",sId).getSingleResult();
	}

}
