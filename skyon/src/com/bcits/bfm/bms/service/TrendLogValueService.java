package com.bcits.bfm.bms.service;

import java.util.List;

import com.bcits.bfm.model.TrendLogValue;

public interface TrendLogValueService extends GenericBMSService<TrendLogValue> {

	List<?> getMaxTimeStampBasedOnCurrentData(int montOne, int year, int day,
			int trendLogId);

}
