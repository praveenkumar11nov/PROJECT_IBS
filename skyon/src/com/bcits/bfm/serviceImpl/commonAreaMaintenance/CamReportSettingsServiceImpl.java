package com.bcits.bfm.serviceImpl.commonAreaMaintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CamReportSettingsEntity;
import com.bcits.bfm.service.commonAreaMaintenance.CamReportSettingsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CamReportSettingsServiceImpl extends GenericServiceImpl<CamReportSettingsEntity>implements CamReportSettingsService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CamReportSettingsEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("CamReportSettingsEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> camChargesEntity){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> settingsMap = null;
		 for (Iterator<?> iterator = camChargesEntity.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				settingsMap = new HashMap<String, Object>();
				
				settingsMap.put("reportSettingId", (Integer)values[0]);
				settingsMap.put("particularShown", (String)values[1]);

			result.add(settingsMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CamReportSettingsEntity> findAllData() {
		return entityManager.createNamedQuery("CamReportSettingsEntity.findAllData").getResultList();
	}

}
