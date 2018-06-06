package com.bcits.bfm.serviceImpl.commonAreaMaintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CamChargesEntity;
import com.bcits.bfm.service.commonAreaMaintenance.CamChargesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CamChargesServiceImpl extends GenericServiceImpl<CamChargesEntity>implements CamChargesService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CamChargesEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("CamChargesEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> camChargesEntity){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> settingsMap = null;
		 for (Iterator<?> iterator = camChargesEntity.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				settingsMap = new HashMap<String, Object>();
				
				settingsMap.put("camId", (Integer)values[0]);
				settingsMap.put("camChargesBasedOn", (String)values[1]);
				settingsMap.put("rateForFlat", (Integer)values[2]);
				settingsMap.put("rateForSqft", (Integer)values[3]);

			result.add(settingsMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CamChargesEntity> findAllData() {
		return entityManager.createNamedQuery("CamChargesEntity.findAllData").getResultList();
	}

}
