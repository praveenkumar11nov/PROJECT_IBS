package com.bcits.bfm.bms.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.bms.service.TrendLogService;
import com.bcits.bfm.model.TrendLog;

@Repository
public class TrendLogServiceImpl extends GenericBMSServiceImpl<TrendLog> implements TrendLogService{

	@Override
	public List<TrendLog> read() {
		return entityManager.createNamedQuery("TrendLog.findAll",TrendLog.class).getResultList();
	}

	
}
