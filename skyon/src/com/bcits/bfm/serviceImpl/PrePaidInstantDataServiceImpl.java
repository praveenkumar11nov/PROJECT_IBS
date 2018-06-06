package com.bcits.bfm.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.PrePaidDailyData;
import com.bcits.bfm.service.PrePaidInstantDataService;

@Repository
public class PrePaidInstantDataServiceImpl extends GenericServiceImpl<PrePaidDailyData> implements PrePaidInstantDataService{

	@Override
	public List<?> SendingLowBalanceStatus() {
		try{
		return entityManager.createNamedQuery("PrePaidDailyData.lowBalanceStatus").getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getConsumption(Date fromdate, Date todate,String consumerid) {
		//System.out.println("123");
		 Calendar cal=Calendar.getInstance();
		 cal.setTime(fromdate);
		 //cal.getMinimum(arg0)
		 int month=cal.get(Calendar.MONTH);
		 int fromMonth=month+1;
		 int fromYear=cal.get(Calendar.YEAR);
		 cal.setTime(todate);
		 int month1=cal.get(Calendar.MONTH);
		 int toMonth=month1+1;
		 int toYear=cal.get(Calendar.YEAR);
		 String frDate=cal.getMinimalDaysInFirstWeek()+"/"+fromMonth+"/"+fromYear;
		 String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+toMonth+"/"+toYear;
	   try{
		   return entityManager.createNamedQuery("PrePaidDailyData.getConsumptionData").setParameter("fromDate", frDate).setParameter("toDate", toDate).setParameter("consumerid", consumerid).getResultList();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return null;
		
	}

	@Override
	public List<Object[]> getConsumptionDayWise(String fromdate, String todate) {
		try{
			return entityManager.createNamedQuery("PrePaidDailyData.getConsumptionDayWiseForAll").setParameter("fromDate", fromdate).setParameter("toDate", todate).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PrePaidDailyData getMaxReadingDate(String meterNo) {
		try{
			return (PrePaidDailyData) entityManager.createNamedQuery("PrePaidDailyData.getMAxReadingDate").setParameter("mtrSrNo", meterNo).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getConsumptionDetails(String fromDate, String toDate, String meterNumber) {
	  try{
		  return entityManager.createNamedQuery("PrePaidDailyData.getConsumptionData").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("consumerid", meterNumber).getResultList();
	  }catch(Exception e){
		  e.printStackTrace();
	  }
		return null;
	}

	@Override
	public String getMaxMinBalance(Date presentdate, String meterNumber) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentdate);
	 	  cal.add(Calendar.MONTH, -1);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String frDate1=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+frDate1+" "+toDate);
		try{
			return (String) entityManager.createNamedQuery("PrePaidDailyData.getMAxminBalance").setParameter("fromDate", frDate1).setParameter("toDate", toDate).setParameter("mtrSrNo", meterNumber).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getMaxMinBalance1(Date presentdate, String meterNumber) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentdate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+fromDate+" "+toDate);
		try{
			return (String) entityManager.createNamedQuery("PrePaidDailyData.getMAxminBalance").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("mtrSrNo", meterNumber).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object[]> getMinMaxDate(Date presentDate, String meterNumber) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentDate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+fromDate+" "+toDate);
		try{
			return (List<Object[]>) entityManager.createNamedQuery("PrePaidDailyData.getMAxminDate").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("mtrSrNo", meterNumber).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getRechargeAmt(Date presentDate, String meterNumber) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentDate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		  System.out.println(""+fromDate+" "+toDate);
		try{
			return  (String) entityManager.createNamedQuery("PrePaidDailyData.getRechargeAmt").setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("mtrSrNo", meterNumber).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getMaxBalance(Date maxDate, String meterNo) {
		String lastDate=new SimpleDateFormat("dd/MM/yyyy").format(maxDate);
		try{
			return  (String) entityManager.createNamedQuery("PrePaidDailyData.getminMaxBAL").setParameter("fromDate", lastDate).setParameter("mtrSrNo", meterNo).getSingleResult();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getMinBalance(Date minDate, String meterNo) {
		  Calendar cal=Calendar.getInstance();
	 	  cal.setTime(minDate);
	 	  cal.add(Calendar.DATE, -1);
	 	 String fromDate=new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
		try{
			return  (String) entityManager.createNamedQuery("PrePaidDailyData.getminMaxBAL").setParameter("fromDate", fromDate).setParameter("mtrSrNo", meterNo).getSingleResult();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PrePaidDailyData getConsumptionData(String fromDate, String meterNumber,int flag) {

		Date frmDate=null;
		try {
			frmDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(flag==1){
			Calendar cal=Calendar.getInstance();
			cal.setTime(frmDate);
			cal.add(Calendar.DATE, -1);
			cal.getTime();
			String previousDate=new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
			try{
				return (PrePaidDailyData) entityManager.createNamedQuery("PrePaidDailyData.getPreviousData").setParameter("fromDate", previousDate).setParameter("mtrSrNo", meterNumber).getSingleResult();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(flag==2){
			try{
				return (PrePaidDailyData) entityManager.createNamedQuery("PrePaidDailyData.getPreviousData").setParameter("fromDate", fromDate).setParameter("mtrSrNo", meterNumber).getSingleResult();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public long checkConsumptionAvailability(String mtrNo, Date presentdate) {
		Calendar cal=Calendar.getInstance();
	 	  cal.setTime(presentdate);
	 	  int month=cal.get(Calendar.MONTH);
	 	  int m1=month+1;
	 	  int year=cal.get(Calendar.YEAR);
		  String fromDate=cal.getMinimalDaysInFirstWeek()+"/"+m1+"/"+year;
		  String toDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+m1+"/"+year;
		 // System.out.println(""+fromDate+" "+toDate);
		try{
			return (long) entityManager.createNamedQuery("PrePaidDailyData.getCountofRecords").setParameter("mtrSrNo", mtrNo).setParameter("fromDate", fromDate).setParameter("toDate", toDate).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Date getminReadingDate(String mtrNo) {
		try{
			return (Date) entityManager.createNamedQuery("PrePaidDailyData.getminReadingDate").setParameter("mtrSrNo", mtrNo).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long checkDataExistence(String date, String metrno) {
		try{
			return (long) entityManager.createNamedQuery("PrePaidDailyData.checkPreviousData").setParameter("mtrSrNo", metrno).setParameter("fromDate", date).getSingleResult();
		}catch(Exception e){
			return 0;
		}
		
	}

	@Override
	public long GenusMtrNoNotGettingExistence(String date, String metrno) {
		try{
			return (long) entityManager.createNamedQuery("PrePaidDailyData.GenusMtrNoNotGettingExistence").setParameter("mtrSrNo", metrno).setParameter("fromDate", date).getSingleResult();
		}catch(Exception e){
			return 0;
		}
		
	}

}
