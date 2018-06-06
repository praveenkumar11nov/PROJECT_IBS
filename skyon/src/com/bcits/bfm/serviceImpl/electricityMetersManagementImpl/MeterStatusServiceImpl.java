package com.bcits.bfm.serviceImpl.electricityMetersManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.MeterStatusEntity;
import com.bcits.bfm.service.electricityMetersManagement.MeterStatusService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class MeterStatusServiceImpl extends GenericServiceImpl<MeterStatusEntity> implements MeterStatusService   {

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterStatusEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("MeterStatusEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> meterStatusMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				meterStatusMap = new HashMap<String, Object>();
				
				meterStatusMap.put("meterStatusId", (Integer)values[0]);
				meterStatusMap.put("meterStatus", (String)values[1]);
			
			result.add(meterStatusMap);
	     }
      return result;
	}

}
