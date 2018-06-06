package com.bcits.bfm.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.GenusMtrNoNotGetting;
import com.bcits.bfm.service.ConsumptionNotGettingMtrService;

@Repository
public class ConsumptionNotGettingMtrServiceImpl extends GenericServiceImpl<GenusMtrNoNotGetting> implements ConsumptionNotGettingMtrService{

	@Override
	public List<?> getAllMeterBlankData(String readingdate) 
	{
		String Query = "SELECT * FROM CONSUMPTION_NOT_GETTINGMTR "
				+ " WHERE TO_DATE(READING_DATE, 'dd/MM/yyyy')=TO_DATE('"+readingdate+"','dd/MM/yyyy') "
				+ " ORDER BY TO_DATE(READING_DATE, 'dd/MM/yyyy') ";
		
		return entityManager.createNativeQuery(Query).getResultList();
	}

}
