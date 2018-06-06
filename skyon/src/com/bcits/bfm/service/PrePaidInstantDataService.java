package com.bcits.bfm.service;

import java.util.Date;
import java.util.List;

import com.bcits.bfm.model.PrePaidDailyData;

public interface PrePaidInstantDataService extends GenericService<PrePaidDailyData>{

	
	public List<?> SendingLowBalanceStatus();

	public List<Object[]> getConsumption(Date fromdate, Date todate,String consumerid);
	public List<Object[]> getConsumptionDayWise(String fromdate, String todate);
	public PrePaidDailyData getMaxReadingDate(String meterNo);

	public List<Object[]> getConsumptionDetails(String fromDate, String toDate, String meterNumber);
	public String getMaxMinBalance(Date presentDate, String meterNumber);
	public String getMaxMinBalance1(Date presentDate, String meterNumber);
	public List<Object[]> getMinMaxDate(Date presentDate, String meterNumber);
	public String getRechargeAmt(Date presentDate, String meterNumber);

	public String getMaxBalance(Date maxDate, String meterNo);

	public String getMinBalance(Date minDate, String meterNo);

	public PrePaidDailyData getConsumptionData(String fromDate, String meterNumber, int flag);

	public long checkConsumptionAvailability(String mtrNo, Date presentdate);

	public Date getminReadingDate(String mtrNo);

	public long checkDataExistence(String date, String string);

	public long GenusMtrNoNotGettingExistence(String date, String string);
	
}
