package com.bcits.bfm.service;

import java.util.Date;
import java.util.List;

import com.bcits.bfm.model.PrepaidCalcuStoreEntity;

public interface PrepaidCalcuStoreService  extends GenericService<PrepaidCalcuStoreEntity>{

 public	List<PrepaidCalcuStoreEntity> getAllStoreData();
 public List<Object[]> getPersonandProp(String meterNo);
 
 public Date getMAxDate(String meterNo,String serviceName);
public List<?> getSumofAllCharges(Date presentdate, int propertyId);
public double getSumofAmt(Date presentdate, int propertyId);
public List<Object[]> getMinMaxDate(Date presentdate, String meterNo, int propid);
public long checkDataExistence(int propId, String readingDate,String serviceName);
}
