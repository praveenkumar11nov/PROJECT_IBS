package com.bcits.bfm.serviceImpl.serviceMasterManagementImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.MeterHistoryEntity;
import com.bcits.bfm.service.serviceMasterManagement.MeterHistoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class MeterHistoryServiceImpl extends GenericServiceImpl<MeterHistoryEntity> implements MeterHistoryService  {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<MeterHistoryEntity> findAllById(int serviceMasterId) {
		return getAllDetailsList(entityManager.createNamedQuery("MeterHistoryEntity.findAllById").setParameter("serviceMasterId", serviceMasterId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<MeterHistoryEntity> meterHistoryList) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> meterHistoryMap = null;
		 for (Iterator<?> iterator = meterHistoryList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				meterHistoryMap = new HashMap<String, Object>();
								
				meterHistoryMap.put("meterHistoryId",(Integer)values[0]);
				meterHistoryMap.put("serviceMasterId",(Integer)values[1]);
				meterHistoryMap.put("meterSerialNo",(String)values[2]);				
				meterHistoryMap.put("meterFixedDate", (Date)values[3]);	
				meterHistoryMap.put("meterReleaseDate", (Date)values[4]);
				meterHistoryMap.put("intialReading", (double)values[5]);				
				meterHistoryMap.put("finalReading", (double)values[6]);
			
			result.add(meterHistoryMap);
	     }
      return result;
	}
	
}
