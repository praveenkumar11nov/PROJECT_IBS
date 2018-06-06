package com.bcits.bfm.bms.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.bms.service.TrendLogValueService;
import com.bcits.bfm.model.TrendLogValue;

@Repository
public class TrendLogValueServiceImpl extends GenericBMSServiceImpl<TrendLogValue> implements TrendLogValueService{

	@Override
	public List<?> getMaxTimeStampBasedOnCurrentData(int montOne, int year,
			int day, int trendLogId) {
		
		return entityManager.createNamedQuery("TrendLogValue.getMaxTimeStampBasedOnCurrentData").setParameter("montOne",montOne).setParameter("year",year).setParameter("day",day).setParameter("trendLogId",trendLogId).getResultList();
	}

}
