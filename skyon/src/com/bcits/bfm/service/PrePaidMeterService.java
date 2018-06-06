package com.bcits.bfm.service;

import java.util.Date;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.PrePaidMeters;
import com.bcits.bfm.model.PrepaidRechargeEntity;

public interface PrePaidMeterService extends GenericService<PrePaidMeters>{

	public List<?> getConsumerIDs();
	public String getPropertyNo(int propertyId);
	public String getBlockName(int blockId);
	public List<?> FindAll();
	public int accountId(int propertyId);
	public List<?> getOwnerName(int personId);
	public List<String> getMeterNumber();
	public int getPersonId(int propertyId);
	public int getPropertyId(String mtr);
	public List<?> getAllProp(int blockId);
	//public List<?> getTentantName(int propertyId,int personId);
	public String getMtrNo(int propid);
	public double getInitialReading(String mtrSrNo);
	public List<String> getMeterNumber(int prepaidId);
	public Object[] getserviceStartDate(int propId);
	public List<?> getPropOnlyforChargesCalcu(int blockId);
	public List<?> getPropForMailBill(int blockId);
	public long getConsumerNumber(String consumerId);
	public List<String> getConsumerIdNumber(int prepaidId);
	public List<?> getGenusData(int blockId);
	public long getmetersDetails(int personId, int propertyId);


}
