package com.bcits.bfm.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.OldMeterHistoryEntity;
import com.bcits.bfm.service.PrepaidMeterHistoryService;

@Repository
public class PrePaidMeterHistoryServiceImpl extends GenericServiceImpl<OldMeterHistoryEntity> implements PrepaidMeterHistoryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<?> findmeterHtryDetails() {
		
		List<Map<String, Object>> resultList=new ArrayList<>();
		Map<String, Object> map=null;
		List<OldMeterHistoryEntity> reHistoryEntities=entityManager.createNamedQuery("OldMeterHistoryEntity.readMeterData").getResultList();
		for (OldMeterHistoryEntity oldMeterHistoryEntity : reHistoryEntities) {
			map=new HashMap<>();
			map.put("meterHtryId", oldMeterHistoryEntity.getHid());
			map.put("propertyName", oldMeterHistoryEntity.getPropertyNo());
			map.put("personName", oldMeterHistoryEntity.getPersonName());
			map.put("meterNumber", oldMeterHistoryEntity.getMeterNumber());
			map.put("initialReading", oldMeterHistoryEntity.getInitailReading());
			map.put("readingDate", new SimpleDateFormat("dd-MM-yyyy").format(oldMeterHistoryEntity.getServiceStartDate()));
			map.put("dgReading", oldMeterHistoryEntity.getDgReading());
			map.put("initialBalnce", oldMeterHistoryEntity.getBalance());
			map.put("serviceEndDate", new SimpleDateFormat("dd-MM-yyyy").format(oldMeterHistoryEntity.getServiceEndDate()));
			map.put("consumerId", oldMeterHistoryEntity.getConsumerId());
			resultList.add(map);
		}
		return resultList;
	}

}
