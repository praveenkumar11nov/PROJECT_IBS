package com.bcits.bfm.serviceImpl;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.PrepaidCalcuStoreEntity;
import com.bcits.bfm.service.PrepaidCalcuStoreService;

@Repository
public class PrepaidCalcuStoreImpl extends GenericServiceImpl<PrepaidCalcuStoreEntity> implements PrepaidCalcuStoreService{

	@Override
	public List<PrepaidCalcuStoreEntity> getAllStoreData() {
	try{
		return entityManager.createNamedQuery("PrepaidCalcuStoreEntity.featchData").getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
		return null;
	}

	@Override
	public List<Object[]> getPersonandProp(String meterNo) {
		try{
			return entityManager.createNamedQuery("PrePaidMeters.fetch").setParameter("meterNumber", meterNo).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Date getMAxDate(String meterNo,String serviceName) {
		try{
			return (Date) entityManager.createNamedQuery("PrepaidCalcuStoreEntity.getMaxDate").setParameter("meterNo", meterNo).setParameter("serviceName", serviceName).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getSumofAllCharges(Date presentdate, int propertyId) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentdate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+fromDate+" "+toDate);
	try{
		return entityManager.createNamedQuery("PrepaidCalcuStoreEntity.getALLCalcuData").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("propertyId", propertyId).getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
		return null;
	}

	@Override
	public double getSumofAmt(Date presentdate, int propertyId) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentdate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+fromDate+" "+toDate);
	try{
		return (double) entityManager.createNamedQuery("PrepaidCalcuStoreEntity.getsumAmt").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("propertyId", propertyId).getSingleResult();
	}catch(Exception e){
		e.printStackTrace();
	}
		return 0;
	}

	@Override
	public List<Object[]> getMinMaxDate(Date presentdate, String meterNo, int propid) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentdate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+fromDate+" "+toDate);
		try{
			return entityManager.createNamedQuery("PrepaidCalcuStoreEntity.getminMaxDate").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("propertyId", propid).setParameter("meterNo", meterNo).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long checkDataExistence(int propId, String readingDate,String serviceName) {
		try{
		return (long) entityManager.createNamedQuery("PrepaidCalcuStoreEntity.getExistence").setParameter("propertyId", propId).setParameter("readingDate", readingDate).setParameter("serviceName", serviceName).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

}
