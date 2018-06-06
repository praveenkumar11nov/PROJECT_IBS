package com.bcits.bfm.bms.service;

import java.util.List;

import com.bcits.bfm.model.TrendLog;

public interface TrendLogService extends GenericBMSService<TrendLog>{

	List<TrendLog> read();


}
