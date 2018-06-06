package com.bcits.bfm.serviceImpl.transactionMasterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.InterestSettingsEntity;
import com.bcits.bfm.service.transactionMaster.InterestSettingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class InterestSettingServiceImpl extends GenericServiceImpl<InterestSettingsEntity> implements InterestSettingService   {

	@SuppressWarnings("unchecked")
	@Override
	public List<InterestSettingsEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("InterestSettingsEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> settingsMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				settingsMap = new HashMap<String, Object>();
				
				settingsMap.put("settingId", (Integer)values[0]);
				settingsMap.put("interestBasedOn", (String)values[1]);
				settingsMap.put("status", (String)values[2]);
			
			result.add(settingsMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InterestSettingsEntity> findAllData() {
		return entityManager.createNamedQuery("InterestSettingsEntity.findAllData").getResultList();
	}
}
