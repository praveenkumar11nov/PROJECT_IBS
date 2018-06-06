package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.PrePaidPaymentEntity;

public interface PrepaidPaymentAdjustmentService extends GenericService<PrePaidPaymentEntity> {
	
	public List<?> ReadPropertys();
	public List<?> readMeters();
    public List<?> getAllData();
	public Object[] getInstrumentIDNo(String receiptNumber);
	public String getConsumerId(int parseInt);
    
}
