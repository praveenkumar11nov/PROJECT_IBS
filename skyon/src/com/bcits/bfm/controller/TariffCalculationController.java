package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.CommonServicesRateSlab;
import com.bcits.bfm.model.ContractDemand;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.model.GenericClassForTodCalculation;
import com.bcits.bfm.model.GenericParameterForCalculation;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.util.DateTimeCalender;

@Controller
public class TariffCalculationController
{
	static Logger logger = LoggerFactory.getLogger(TariffCalculationController.class);
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;
	
	@Autowired
	private MessageSource messageSource;
	Locale locale;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@RequestMapping(value = "/tariffCalc", method = RequestMethod.GET)
	public String getRateDetail(ModelMap model, HttpServletRequest request) {
		model.addAttribute("rateName", new String[]{"EC","FC"});
		return "tariff/tariffCalculator";
	}

	@RequestMapping(value = "/tariff/readtariffname", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readTariffName(HttpServletRequest req) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Object[] tariffcal : tariffCalculationService.getTariffNameCal())
		{
			  result.add(new HashMap<String, Object>()
					  {
				private static final long serialVersionUID = 1L;
				{
					put("elTariffID", (Integer) tariffcal[0]);
					put("tariffName", (String) tariffcal[1]);
				}
			});
		}
		return result;
	}
	
	@RequestMapping(value = "/tariff/readrateuom", method = RequestMethod.GET)
	public @ResponseBody
	
	List<?> readRateUom(HttpServletRequest req,HttpServletResponse res) {

		String ratename=req.getParameter("ratename");
		
		 int elTariffID=Integer.parseInt(req.getParameter("elTariffID"));
		
	List<ELRateMaster>	 tariffcal = tariffCalculationService.getRateUomCal(ratename,elTariffID);
	    
	PrintWriter out=null;
	String rateuom=null;
	int count=0;
for (ELRateMaster elRateMaster : tariffcal) {
	if(count<1){
	rateuom=	elRateMaster.getRateUOM();
	logger.info("rateuom---------"+rateuom);
	try
	{
	out = res.getWriter();
	} catch (IOException e) 
	{
		e.printStackTrace();
	}
	out.write(rateuom);
	req.setAttribute("rateuom", rateuom);
	count++;
	}
 }
		return null;
	}
	
	@RequestMapping(value = "/tariff/readratename", method = RequestMethod.GET)
	 public @ResponseBody
	 List<?> readRateName(HttpServletRequest req) {
	       
	  int elTariffID=Integer.parseInt(req.getParameter("elTariffID"));
	  logger.info("elTariffID....."+elTariffID);
	  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	  for (final Object[] tariffcal : tariffCalculationService.getRateNameCal(elTariffID)) {
	   result.add(new HashMap<String, Object>()
		{
	   
		private static final long serialVersionUID = 1L;
		{
	     put("elTariffID", (Integer) tariffcal[0]);
	     put("rateName", (String) tariffcal[1]);
	    }
	   });
	  }
	  return result;
	 }
	
	@RequestMapping(value = "/tariff/readratenametod", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readRateNameTod(HttpServletRequest req) 
	{
		  int elTariffID=Integer.parseInt(req.getParameter("elTariffID"));
		  logger.info("elTariffID....."+elTariffID);
		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		  for (final Object[] tariffcal : tariffCalculationService.getRateNameCal(elTariffID)) 
		  {
			  logger.info("------------------ "+tariffCalculationService.getRateNameCal(elTariffID).size());
		   result.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
		     put("elTariffID", (Integer) tariffcal[0]);
		     put("rateName", (String) tariffcal[1]);
		    }
		   });
		  }
		  return result;
	}
	@RequestMapping(value = "/tariff/readtariffnametod", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readTariffNameTod(HttpServletRequest req) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Object[] tariffcal : tariffCalculationService.getTariffNameCalTod()) {
			result.add(new HashMap<String, Object>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("elTariffID", (Integer) tariffcal[0]);
					put("tariffName", (String) tariffcal[1]);
				}
			});
		}
		return result;
	}
	
	/* ....................... Tariff calculation ........................... */
	
	@RequestMapping(value = "/tariffCalc/getAmount",  method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> calculateTariff(HttpServletRequest req, HttpServletResponse response,@RequestBody String body) throws ParseException, JSONException 
	{
		logger.info(":::::::::::::::::: In tariff calculation method :::::::::::::::::::::: ");
		
		 String tariffName;
		 String rateName;
		 Float uomValue;
		 Date fromDate;
		 Date toDate;
		 Float extraAmount = (float) 0;
		 @SuppressWarnings("unused")
		HashMap<Object, Object> consolidatedBill = null;
		 HashMap<Object, Object> consolidatedBill1 = null;
		 
		 if(body.length() == 0)
		 {
			    logger.info(":::::::::::::::::::::::::: JSON object is NULL ::::::::::::::::::::");
			     tariffName= req.getParameter("tariffName");
				 rateName = req.getParameter("rateName");
				 uomValue = Float.parseFloat(req.getParameter("unit"));
				 fromDate= (dateTimeCalender.getdateFormat(req.getParameter("validFrom")));
				 toDate= (dateTimeCalender.getdateFormat(req.getParameter("validTo")));
				 extraAmount = Float.parseFloat(req.getParameter("extraAmount"));
		 }
		 else 
		 {
			     logger.info(":::::::::::::::::::::::::: JSON object is NOT NULL ::::::::::::::::::::");
			     JSONObject jsonObj = new JSONObject(body);
		    	 tariffName= jsonObj.getString("tariffName");
				 rateName = jsonObj.getString("rateName");
				 uomValue = Float.parseFloat(jsonObj.getString("unit"));
				 fromDate= (dateTimeCalender.getdateFormat(jsonObj.getString("validFrom")));
				 toDate= (dateTimeCalender.getdateFormat(jsonObj.getString("validTo")));
				 //extraAmount = Float.parseFloat(jsonObj.getString("extraAmount"));
			
		 }
		 
		if(extraAmount == 0)
		{
			GenericParameterForCalculation calculation = new GenericParameterForCalculation(tariffName, rateName, uomValue, fromDate,toDate);
			logger.info("Initializing the parent type");
			logger.info("Tariff Name :::::::::::::::::::: "+calculation.getTariffName());
			logger.info("Rate Name :::::::::::::::::::::: "+calculation.getRateName());
			logger.info("UOM value :::::::::::::::::::::: "+calculation.getUomValue());
			logger.info("Start Date ::::::::::::::::::::: "+calculation.getStartDate());
			logger.info("End Date ::::::::::::::::::::::: "+calculation.getEndDate());
			consolidatedBill = tariffCalculation(calculation,response);
			//consolidatedBill1 = tariffCalculation1(calculation);
		}
		else
		{
			ContractDemand contractDemand = new ContractDemand(tariffName, rateName, uomValue, fromDate,toDate,extraAmount);
		
			logger.info("Initializing the sub type");
			logger.info("Tariff Name :::::::::::::::::::: "+contractDemand.getTariffName());
			logger.info("Rate Name :::::::::::::::::::::: "+contractDemand.getRateName());
			logger.info("UOM value :::::::::::::::::::::: "+contractDemand.getUomValue());
			logger.info("Start Date ::::::::::::::::::::: "+contractDemand.getStartDate());
			logger.info("End Date ::::::::::::::::::::::: "+contractDemand.getEndDate());
			logger.info("Contract Demand :::::::::::::::: "+contractDemand.getContratDemand());
			
			consolidatedBill = tariffCalculation(contractDemand,response);
			//consolidatedBill1 = tariffCalculation1(contractDemand);
		}
		return consolidatedBill1;
	}

	private HashMap<Object, Object> tariffCalculation(GenericParameterForCalculation calculation,HttpServletResponse response)
	{
		PrintWriter out = null;
		Float totalAmount = 0.0f;
		Float totalAmount1 = 0.0f;
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		Float uomValue;
        float total = 0;
		LocalDate fromDate = new LocalDate(calculation.getStartDate());
		LocalDate toDate = new LocalDate(calculation.getEndDate());
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();       
		Calendar cal = Calendar.getInstance();
		cal.setTime(calculation.getStartDate());
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		HashMap<Object, Object> consolidatedBill = null;
		 
		try
		{
		out = response.getWriter();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		ELTariffMaster elTariffMaster = tariffCalculationService.getTariffMasterByName(calculation.getTariffName());
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elTariffMaster.getElTariffID(),calculation.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(calculation.getStartDate()) <=0 ))
	  {
		  out.print("Start Date is not inside the tariff dates"+"</br>");
      }
	  else if (calculation.getEndDate().compareTo(maxDate) > 0)
	  {
		  out.print("End Date is not inside the tariff dates"+"</br>");
      }
	  else
	  {
		  List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elTariffMaster.getElTariffID(),calculation.getRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		  }
		  else
		  {
			  
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster : elRateMasterList) 
			 {
				  uomValue =  calculation.getUomValue() / netMonth;
				  if((calculation.getStartDate().compareTo(elRateMaster.getValidFrom()) >=0) && (calculation.getEndDate().compareTo(elRateMaster.getValidTo())<=0))
					{
			   		    logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		    
			   		    out.print("Tariff Valid From :"+elRateMaster.getValidFrom()+" Up to Valid To :"+elRateMaster.getValidTo()+"</br>");
			   		    String rateType = null;
			   		    float rate = 0;
			   		    Float netMonths = null;
			   		    Float months;
			   		    int days;
			   	        Float powerFactorLagging = 0.0f;
			   	        Float consumptionUnitsFlotValue = 0.0f;
			   	        Float dcRate = 0.0f;
			   	        Float rate1 = 0.0f;
			   	        Float billableUnits = 0.0f;
			   	        Float rate2 = 0.0f;
			   	        Float uomValueDC = 0.0f;
			   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),uomValue);
			   			 
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("months"))
			   				{
			   					months=(Float)map.getValue();
			   					out.print("Months :"+months+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("days"))
			   				{
			   					days=(int)map.getValue();
			   					out.print("days after month:"+days+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("netMonth"))
			   				{
			   					netMonths=(Float)map.getValue();
			   					out.print("Total number of  billable month are :"+netMonths+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("rateType"))
			   				{
			   					rateType = (String)map.getValue();
			   				}
			   				if(map.getKey().equals("rate"))
			   				{
			   					rate = (Float)map.getValue();
			   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
			   				}
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }
			   			if(rateType.trim().equalsIgnoreCase("Rupees"))
	   					{
	   						out.print("("+uomValue+"X"+rate+")"+" X"+(netMonths)+" = "+((uomValue * (rate)))*netMonths +"</br>");
	   					}
	   					if(rateType.trim().equalsIgnoreCase("Paise"))
	   					{
	   						out.print("("+uomValue+"X"+rate/100+")"+" X"+(netMonths)+" = "+((uomValue * (rate/100)))*netMonths +"</br>");
	   					}
	   					if(rateType.trim().equalsIgnoreCase("Percentage"))
	   					{
	   						out.print("("+uomValue+"X"+rate/100+")"+" X"+(netMonths)+" = "+((uomValue * (rate/100)))*netMonths +"</br>");
	   					}
	   					if(rateType.trim().equalsIgnoreCase("Multiplier"))
	   					{
	   						out.print("("+uomValue+"X"+rate+")"+" X"+(netMonths)+" = "+((uomValue * (rate)))*netMonths +"</br>");
	   					}
	   					
	   					out.print("Total Amount is (In Rupees ) :"+totalAmount+"</br>");
	   					out.print("------------------------------------------------------"+"</br>");
			   		 }
			   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");

						float slabDifference1 =0.0f;
						float lastSlabTo = 0.0f; 
						LocalDate fromDate1 =  new LocalDate(calculation.getStartDate());
					    LocalDate toDate1 = new LocalDate(calculation.getEndDate());
					    PeriodType monthDay1 = PeriodType.yearMonthDay().withYearsRemoved();
					    Period difference1 = new Period(fromDate1, toDate1, monthDay1);
					    float billableMonths1 = difference1.getMonths();
					    int daysAfterMonth1 = difference1.getDays();
					    
					     Calendar cal1 = Calendar.getInstance();
						 cal1.setTime(calculation.getEndDate());
						 float daysToMonths1 = (float)daysAfterMonth1 / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
						 BigDecimal bd1 = new BigDecimal(daysToMonths1 + billableMonths1);
						 bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
						 float netMonth1 = bd1.floatValue();
						 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
						 
						List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());
						for (ELRateSlabs elRateSlabs : elRateSlabsList)
						  {
						    if(lastSlabTo == 0)
						    {
						     slabDifference1 = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
						     lastSlabTo = elRateSlabs.getSlabTo();
						    }
						    else
						    {
						    	slabDifference1 = (elRateSlabs.getSlabTo() - lastSlabTo);
						    	lastSlabTo = elRateSlabs.getSlabTo();
						    }
							if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
							{
								if(uomValue > slabDifference1)
								{
									uomValue = uomValue - slabDifference1;
									totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate())) *netMonth1;
									
									consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs.getRate());
									consolidateBill.put("slabDifference", slabDifference1);
									
									out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference1 * (elRateSlabs.getRate())))*netMonth1 +"</br>");
								}
								else
								{
									if(uomValue > 0)
									{
										totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()))* netMonth1;
										consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
										consolidateBill.put("rate", elRateSlabs.getRate());
										consolidateBill.put("uomValue", uomValue);
										out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth1 +"</br>");
										uomValue = uomValue - slabDifference1;
									}
								}
							}
							if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
							{
								if(uomValue > slabDifference1)
								{
									uomValue = uomValue - slabDifference1;
									totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate()/100)) *netMonth1;
									consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs.getRate());
									consolidateBill.put("slabDifference", slabDifference1);
									out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference1 * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
								}
								else
								{
									if(uomValue > 0)
									{
										totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()/100))* netMonth1;
										consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
										consolidateBill.put("rate", elRateSlabs.getRate());
										consolidateBill.put("uomValue", uomValue);
										out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
										uomValue = uomValue - slabDifference1;
									}
								}
							}
							if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
							{
								if(uomValue > slabDifference1)
								{
									uomValue = uomValue - slabDifference1;
									totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate()/100)) *netMonth1;
									consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs.getRate()/100);
									consolidateBill.put("slabDifference", slabDifference1);
									out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference1 * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
								}
								else
								{
									if(uomValue > 0)
									{
										totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()/100))* netMonth1;
										consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
										consolidateBill.put("rate", elRateSlabs.getRate()/100);
										consolidateBill.put("uomValue", uomValue);
										out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
										uomValue = uomValue - slabDifference1;
									}
								}
							}
							
							if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
							{
								if(uomValue > slabDifference1)
								{
									uomValue = uomValue - slabDifference1;
									totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate())) *netMonth1;
									consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
									consolidateBill.put("rate", elRateSlabs.getRate());
									consolidateBill.put("slabDifference", slabDifference1);
									out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference1 * (elRateSlabs.getRate())))*netMonth1 +"</br>");
								}
								else
								{
									if(uomValue > 0)
									{
										totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()))* netMonth1;
										consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
										consolidateBill.put("rate", elRateSlabs.getRate());
										consolidateBill.put("uomValue", uomValue);
										out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth1 +"</br>");
										uomValue = uomValue - slabDifference1;
									}
								}
							}
							consolidateBill.put("months", billableMonths1);
							consolidateBill.put("days", daysAfterMonth1);
							consolidateBill.put("netMonth", netMonth1);
						  }
						if(elRateMaster.getMaxRate()!=0)
						{
							  totalAmount1 = (totalAmount1 > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount1;
							  consolidateBill.put("total", totalAmount1);
						}
						else
						{
							  totalAmount1 = (totalAmount1 > elRateMaster.getMinRate()) ? totalAmount1 : elRateMaster.getMinRate();
							  consolidateBill.put("total", totalAmount1);
						}
						
						out.print("Total Amount is (In Rupees ) :"+totalAmount1+"</br>");
	   					out.print("------------------------------------------------------"+"</br>"); 
			   		 }
			   		
			   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			   			
			   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				logger.info("Key "+map.getKey() +" Value "+map.getValue());
			   				if(map.getKey().equals("months"))
			   				{
			   					months=(Float)map.getValue();
			   					out.print("Months :"+months+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("days"))
			   				{
			   					days=(int)map.getValue();
			   					out.print("days after month:"+days+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("netMonth"))
			   				{
			   					netMonths=(Float)map.getValue();
			   					out.print("Total number of  billable month are :"+netMonths+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("rateType"))
			   				{
			   					rateType = (String)map.getValue();
			   				}
			   				if(map.getKey().equals("rate"))
			   				{
			   					rate = (Float)map.getValue();
			   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
			   				}
			   				if(map.getKey().equals("total"))
			   				{
			   					total = (Float)map.getValue();
			   				}
			   		    }
			   			if(rateType.trim().equalsIgnoreCase("Rupees"))
	   					{
	   						totalAmount = totalAmount + ( rate)* netMonths;
	   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
	   					}
	   					if(rateType.trim().equalsIgnoreCase("Paise"))
	   					{
	   						totalAmount = totalAmount + ((rate/100))* netMonths;
	   						out.print("("+netMonths+")"+" X"+(rate/100)+" = "+(((rate/100)))*netMonths +"</br>");
	   					}
	   					if(rateType.trim().equalsIgnoreCase("Percentage"))
	   					{
	   						totalAmount = totalAmount + ((rate/100))* netMonths;
	   						out.print("("+netMonths+")"+" X"+(rate/100)+" = "+(((rate/100)))*netMonths +"</br>");
	   					}
	   					if(rateType.trim().equalsIgnoreCase("Multiplier"))
	   					{
	   						totalAmount = totalAmount + ((rate))* netMonths;
	   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
	   					}
	   					if(elRateMaster.getMaxRate()!=0)
	   					{
	   						 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
	   					}
	   					else
	   					{
	   						  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
	   					}
	   					out.print("Total Amount is (In Rupees ) :"+total+"</br>");
	   					out.print("------------------------------------------------------"+"</br>");
			   		 }
			   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		{
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			   		}
			   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),calculation.getUomValue());
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("months"))
			   				{
			   					months=(Float)map.getValue();
			   					out.print("Months :"+months+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("days"))
			   				{
			   					days=(int)map.getValue();
			   					out.print("days after month:"+days+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("netMonth"))
			   				{
			   					netMonths=(Float)map.getValue();
			   					out.print("Total number of  billable month are :"+netMonths+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("rateType"))
			   				{
			   					rateType = (String)map.getValue();
			   				}
			   				if(map.getKey().equals("rate"))
			   				{
			   					rate = (Float)map.getValue();
			   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
			   				}
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }
			   			
			   			out.print("Phase is "+calculation.getUomValue()+"</br>");
			   			if(rateType.trim().equalsIgnoreCase("Rupees"))
	   					{
	   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
	   					}
			   			
			   			
			   			out.print("Total Amount is (In Rupees ) :"+totalAmount+"</br>");
	   					out.print("------------------------------------------------------"+"</br>");
			   		}
			   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating Demand  charges ::::::::::::::::::::");
			   			ContractDemand contractDemand = null;
			   			if(calculation instanceof ContractDemand)
			   			{
			   				contractDemand = (ContractDemand) calculation;
			   			}
			   			consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				logger.info("Key  "+map.getKey() +" Value "+map.getValue());
			   				if(map.getKey().equals("months"))
			   				{
			   					months=(Float)map.getValue();
			   					out.print("Months :"+months+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("days"))
			   				{
			   					days=(int)map.getValue();
			   					out.print("days after month:"+days+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("netMonth"))
			   				{
			   					netMonths=(Float)map.getValue();
			   					out.print("Total number of  billable month are :"+netMonths+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("rateType"))
			   				{
			   					rateType = (String)map.getValue();
			   				}
			   				if(map.getKey().equals("rate"))
			   				{
			   					rate = (Float)map.getValue();
			   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("total"))
			   				{
			   					total = (Float)map.getValue();
			   				}
			   				
			   				if(map.getKey().equals("dcRate"))
			   				{
			   					dcRate = (Float)map.getValue();
			   				}
			   				if(map.getKey().equals("rate1"))
			   				{
			   					rate1 = (Float)map.getValue();
			   				}
			   				if(map.getKey().equals("billableUnits"))
			   				{
			   					billableUnits  = (Float)map.getValue();
			   				}
			   				 
			   				if(map.getKey().equals("rate2"))
			   				{
			   					rate2   = (Float)map.getValue();
			   				}
			   				if(map.getKey().equals("uomValue"))
			   				{
			   					uomValueDC   = (Float)map.getValue();
			   				}
			   		    }
			   			if(rateType.trim().equalsIgnoreCase("Multiplier"))
	   					{
	   						if(rate1 == 0.0)
	   						{
	   							//out.print("("+dcRate+" X"+billableUnits+" X"+rate1+")"+" X"+(netMonth)+" = "+((dcRate * billableUnits* (rate1)))*netMonth +"</br>");
	   							out.print("("+dcRate+" X"+uomValueDC+" X"+rate2+")"+" X"+(netMonth)+" = "+((dcRate * uomValueDC* (rate2)))*netMonth +"</br>");
	   						}
	   						else
	   						{
	   							out.print("("+dcRate+" X"+billableUnits+" X"+rate1+")"+" X"+(netMonth)+" = "+((dcRate * billableUnits* (rate1)))*netMonth +"</br>");
	   							out.print("("+dcRate+" X"+uomValueDC+" X"+rate2+")"+" X"+(netMonth)+" = "+((dcRate * uomValueDC* (rate2)))*netMonth +"</br>");
	   						}
	   					}
			   			out.print("Total Amount is (In Rupees ) :"+total+"</br>");
	   					out.print("------------------------------------------------------"+"</br>");
			   		}
			   		
			   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating power factor penalty ::::::::::::::::::::");
			   			ContractDemand contractDemand = null;
			   			if(calculation instanceof ContractDemand)
			   			{
			   				contractDemand = (ContractDemand) calculation;
			   			}
			   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
			            
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("months"))
			   				{
			   					months=(Float)map.getValue();
			   					out.print("Months :"+months+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("days"))
			   				{
			   					days=(int)map.getValue();
			   					out.print("days after month:"+days+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("netMonth"))
			   				{
			   					netMonths=(Float)map.getValue();
			   					out.print("Total number of  billable month are :"+netMonths+"</br>");
			   				}
			   				
			   				if(map.getKey().equals("rateType"))
			   				{
			   					rateType = (String)map.getValue();
			   				}
			   				if(map.getKey().equals("rate"))
			   				{
			   					rate = (Float)map.getValue();
			   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
			   				}
			   				if(map.getKey().equals("total"))
			   				{
			   					total = (Float)map.getValue();
			   				}
			   				if(map.getKey().equals("powerFactorLagging"))
			   				{
			   					powerFactorLagging = (Float)map.getValue();
			   				}
			   				if(map.getKey().equals("consumptionUnitsFlotValue"))
			   				{
			   					consumptionUnitsFlotValue = (Float)map.getValue();
			   				}
			   				
			   		    }
			   		    out.print("Recorded Power Factor : "+uomValue+"</br>");
			   		    float roundedPF = roundOff(uomValue, 2);
			            out.print("Recorded Power Factor after roundoff up to 2 decimal points: "+roundedPF+"</br>");
			   			out.print("Power Factor Lagging: "+powerFactorLagging+"</br>");
			   			out.print("Power Factor Amount :"+"("+consumptionUnitsFlotValue + " X "+"("+powerFactorLagging+" X "+100+" X "+rate/100+ ")"+")"+" X "+netMonth+" = "+total+"</br>");
			   			
			   			out.print("Total Amount is (In Rupees ) :"+total+"</br>");
	   					out.print("------------------------------------------------------"+"</br>");
			   		}
			   		
		            }
				  else
					{
						if(calculation.getStartDate().compareTo(elRateMaster.getValidTo())<=0)
						{
							if((calculation.getEndDate().compareTo(elRateMaster.getValidFrom())>= 0) && (elRateMaster.getValidTo().compareTo(calculation.getEndDate())<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = calculation.getUomValue() / netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(calculation.getStartDate());
		 							  endDate = new LocalDate(elRateMaster.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster.getValidTo());
		 							  lastUpdatedDate =elRateMaster.getValidTo();
		 							  loopCount = loopCount +1;
								}
								
					   		    out.print("Tariff Valid From :"+elRateMaster.getValidFrom()+" Up to Valid To :"+elRateMaster.getValidTo()+"</br>");

					   		    String rateType = null;
					   		    float rate = 0;
					   		    Float netMonths = 0.0f;
					   		    Float months;
					   		    int days;
					   	        Float powerFactorLagging = 0.0f;
					   	        Float consumptionUnitsFlotValue = 0.0f;
					   	        Float dcRate = 0.0f;
					   	        Float rate1 = 0.0f;
					   	        Float billableUnits = 0.0f;
					   	        Float rate2 = 0.0f;
					   	        Float uomValueDC = 0.0f;
					   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			
					   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("months"))
					   				{
					   					months=(Float)map.getValue();
					   					out.print("Months :"+months+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("days"))
					   				{
					   					days=(int)map.getValue();
					   					out.print("days after month:"+days+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("netMonth"))
					   				{
					   					netMonths=(Float)map.getValue();
					   					out.print("Total number of  billable month are :"+netMonths+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("rateType"))
					   				{
					   					rateType = (String)map.getValue();
					   				}
					   				if(map.getKey().equals("rate"))
					   				{
					   					rate = (Float)map.getValue();
					   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
					   				}
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   			
					   			if(rateType.trim().equalsIgnoreCase("Rupees"))
			   					{
			   						out.print("("+uomValue+"X"+rate+")"+" X"+(netMonths)+" = "+((uomValue * (rate)))*netMonths +"</br>");
			   					}
			   					if(rateType.trim().equalsIgnoreCase("Paise"))
			   					{
			   						out.print("("+uomValue+"X"+rate/100+")"+" X"+(netMonths)+" = "+((uomValue * (rate/100)))*netMonths +"</br>");
			   					}
			   					if(rateType.trim().equalsIgnoreCase("Percentage"))
			   					{
			   						out.print("("+uomValue+"X"+rate/100+")"+" X"+(netMonths)+" = "+((uomValue * (rate/100)))*netMonths +"</br>");
			   					}
			   					if(rateType.trim().equalsIgnoreCase("Multiplier"))
			   					{
			   						out.print("("+uomValue+"X"+rate+")"+" X"+(netMonths)+" = "+((uomValue * (rate)))*netMonths +"</br>");
			   					}
			   					out.print("Total Amount is (In Rupees ) :"+totalAmount+"</br>");
			   					out.print("------------------------------------------------------"+"</br>");
					   		 }
					   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
								float slabDifference1 =0.0f;
								float lastSlabTo = 0.0f; 
								LocalDate fromDate1 =  new LocalDate(startDate);
							    LocalDate toDate1 = new LocalDate(endDate);
							    PeriodType monthDay1 = PeriodType.yearMonthDay().withYearsRemoved();
							    Period difference1 = new Period(fromDate1, toDate1, monthDay1);
							    float billableMonths1 = difference1.getMonths();
							    int daysAfterMonth1 = difference1.getDays();
							    
							     Calendar cal1 = Calendar.getInstance();
								 cal1.setTime(calculation.getEndDate());
								 float daysToMonths1 = (float)daysAfterMonth1 / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
								 BigDecimal bd1 = new BigDecimal(daysToMonths1 + billableMonths1);
								 bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
								 float netMonth1 = bd1.floatValue();
								 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
								 
								List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());
								for (ELRateSlabs elRateSlabs : elRateSlabsList)
								  {
								    if(lastSlabTo == 0)
								    {
								     slabDifference1 = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
								     lastSlabTo = elRateSlabs.getSlabTo();
								    }
								    else
								    {
								    	slabDifference1 = (elRateSlabs.getSlabTo() - lastSlabTo);
								    	lastSlabTo = elRateSlabs.getSlabTo();
								    }
									if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
									{
										if(uomValue > slabDifference1)
										{
											uomValue = uomValue - slabDifference1;
											totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate())) *netMonth1;
											
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("slabDifference", slabDifference1);
											
											out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference1 * (elRateSlabs.getRate())))*netMonth1 +"</br>");
										}
										else
										{
											if(uomValue > 0)
											{
												totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()))* netMonth1;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("uomValue", uomValue);
												out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth1 +"</br>");
												uomValue = uomValue - slabDifference1;
											}
										}
									}
									if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
									{
										if(uomValue > slabDifference1)
										{
											uomValue = uomValue - slabDifference1;
											totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate()/100)) *netMonth1;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("slabDifference", slabDifference1);
											out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference1 * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
										}
										else
										{
											if(uomValue > 0)
											{
												totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()/100))* netMonth1;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("uomValue", uomValue);
												out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
												uomValue = uomValue - slabDifference1;
											}
										}
									}
									if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
									{
										if(uomValue > slabDifference1)
										{
											uomValue = uomValue - slabDifference1;
											totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate()/100)) *netMonth1;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate()/100);
											consolidateBill.put("slabDifference", slabDifference1);
											out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference1 * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
										}
										else
										{
											if(uomValue > 0)
											{
												totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()/100))* netMonth1;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate()/100);
												consolidateBill.put("uomValue", uomValue);
												out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
												uomValue = uomValue - slabDifference1;
											}
										}
									}
									
									if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
									{
										if(uomValue > slabDifference1)
										{
											uomValue = uomValue - slabDifference1;
											totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate())) *netMonth1;
											consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
											consolidateBill.put("rate", elRateSlabs.getRate());
											consolidateBill.put("slabDifference", slabDifference1);
											out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference1 * (elRateSlabs.getRate())))*netMonth1 +"</br>");
										}
										else
										{
											if(uomValue > 0)
											{
												totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()))* netMonth1;
												consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
												consolidateBill.put("rate", elRateSlabs.getRate());
												consolidateBill.put("uomValue", uomValue);
												out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth1 +"</br>");
												uomValue = uomValue - slabDifference1;
											}
										}
									}
									consolidateBill.put("months", billableMonths1);
									consolidateBill.put("days", daysAfterMonth1);
									consolidateBill.put("netMonth", netMonth1);
								  }
								if(elRateMaster.getMaxRate()!=0)
								{
									  totalAmount1 = (totalAmount1 > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount1;
									  consolidateBill.put("total", totalAmount1);
								}
								else
								{
									  totalAmount1 = (totalAmount1 > elRateMaster.getMinRate()) ? totalAmount1 : elRateMaster.getMinRate();
									  consolidateBill.put("total", totalAmount1);
								}
								out.print("Total Amount is (In Rupees ) :"+totalAmount1+"</br>");
			   					out.print("------------------------------------------------------"+"</br>"); 
					   		 }
					   		
					   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
					   			
					   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				logger.info("Key "+map.getKey() +" Value "+map.getValue());
					   				if(map.getKey().equals("months"))
					   				{
					   					months=(Float)map.getValue();
					   					out.print("Months :"+months+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("days"))
					   				{
					   					days=(int)map.getValue();
					   					out.print("days after month:"+days+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("netMonth"))
					   				{
					   					netMonths=(Float)map.getValue();
					   					out.print("Total number of  billable month are :"+netMonths+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("rateType"))
					   				{
					   					rateType = (String)map.getValue();
					   				}
					   				if(map.getKey().equals("rate"))
					   				{
					   					rate = (Float)map.getValue();
					   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
					   				}
					   				if(map.getKey().equals("total"))
					   				{
					   					total = (Float)map.getValue();
					   				}
					   		    }
					   			if(rateType.trim().equalsIgnoreCase("Rupees"))
			   					{
			   						totalAmount = totalAmount + ( rate)* netMonths;
			   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
			   					}
			   					if(rateType.trim().equalsIgnoreCase("Paise"))
			   					{
			   						totalAmount = totalAmount + ((rate/100))* netMonths;
			   						out.print("("+netMonths+")"+" X"+(rate/100)+" = "+(((rate/100)))*netMonths +"</br>");
			   					}
			   					if(rateType.trim().equalsIgnoreCase("Percentage"))
			   					{
			   						totalAmount = totalAmount + ((rate/100))* netMonths;
			   						out.print("("+netMonths+")"+" X"+(rate/100)+" = "+(((rate/100)))*netMonths +"</br>");
			   					}
			   					if(rateType.trim().equalsIgnoreCase("Multiplier"))
			   					{
			   						totalAmount = totalAmount + ((rate))* netMonths;
			   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
			   					}
			   					if(elRateMaster.getMaxRate()!=0)
			   					{
			   						 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
			   					}
			   					else
			   					{
			   						  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
			   					}
			   					out.print("Total Amount is (In Rupees ) :"+total+"</br>");
			   					out.print("------------------------------------------------------"+"</br>");
					   		 }
					   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		{
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
					   		}
					   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
					   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),calculation.getUomValue());
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("months"))
					   				{
					   					months=(Float)map.getValue();
					   					out.print("Months :"+months+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("days"))
					   				{
					   					days=(int)map.getValue();
					   					out.print("days after month:"+days+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("netMonth"))
					   				{
					   					netMonths=(Float)map.getValue();
					   					out.print("Total number of  billable month are :"+netMonths+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("rateType"))
					   				{
					   					rateType = (String)map.getValue();
					   				}
					   				if(map.getKey().equals("rate"))
					   				{
					   					rate = (Float)map.getValue();
					   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
					   				}
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   			
					   			out.print("Phase is "+calculation.getUomValue()+"</br>");
					   			if(rateType.trim().equalsIgnoreCase("Rupees"))
			   					{
			   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
			   					}
					   			out.print("Total Amount is (In Rupees ) :"+totalAmount+"</br>");
			   					out.print("------------------------------------------------------"+"</br>");
					   		}
					   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating Demand  charges ::::::::::::::::::::");
					   			ContractDemand contractDemand = null;
					   			if(calculation instanceof ContractDemand)
					   			{
					   				contractDemand = (ContractDemand) calculation;
					   			}
					   			consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("months"))
					   				{
					   					months=(Float)map.getValue();
					   					out.print("Months :"+months+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("days"))
					   				{
					   					days=(int)map.getValue();
					   					out.print("days after month:"+days+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("netMonth"))
					   				{
					   					netMonths=(Float)map.getValue();
					   					out.print("Total number of  billable month are :"+netMonths+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("rateType"))
					   				{
					   					rateType = (String)map.getValue();
					   				}
					   				if(map.getKey().equals("rate"))
					   				{
					   					rate = (Float)map.getValue();
					   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("total"))
					   				{
					   					total = (Float)map.getValue();
					   				}
					   				
					   				if(map.getKey().equals("dcRate"))
					   				{
					   					dcRate = (Float)map.getValue();
					   				}
					   				if(map.getKey().equals("rate1"))
					   				{
					   					rate1 = (Float)map.getValue();
					   				}
					   				if(map.getKey().equals("billableUnits"))
					   				{
					   					billableUnits  = (Float)map.getValue();
					   				}
					   				 
					   				if(map.getKey().equals("rate2"))
					   				{
					   					rate2   = (Float)map.getValue();
					   				}
					   				if(map.getKey().equals("uomValue"))
					   				{
					   					uomValueDC   = (Float)map.getValue();
					   				}
					   		    }
					   			if(rateType.trim().equalsIgnoreCase("Multiplier"))
			   					{
					   				
					   				if(rate1 == 0.0)
			   						{
			   							//out.print("("+dcRate+" X"+billableUnits+" X"+rate1+")"+" X"+(netMonth)+" = "+((dcRate * billableUnits* (rate1)))*netMonth +"</br>");
			   							out.print("("+dcRate+" X"+uomValueDC+" X"+rate2+")"+" X"+(netMonth)+" = "+((dcRate * uomValueDC* (rate2)))*netMonth +"</br>");
			   						}
			   						else
			   						{
			   							out.print("("+dcRate+" X"+billableUnits+" X"+rate1+")"+" X"+(netMonth)+" = "+((dcRate * billableUnits* (rate1)))*netMonth +"</br>");
			   							out.print("("+dcRate+" X"+uomValueDC+" X"+rate2+")"+" X"+(netMonth)+" = "+((dcRate * uomValueDC* (rate2)))*netMonth +"</br>");
			   						}
			   					}
					   			out.print("Total Amount is (In Rupees ) :"+total+"</br>");
			   					out.print("------------------------------------------------------"+"</br>");
					   		}
					   		
					   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating power factor penalty ::::::::::::::::::::");
					   			ContractDemand contractDemand = null;
					   			if(calculation instanceof ContractDemand)
					   			{
					   				contractDemand = (ContractDemand) calculation;
					   			}
					   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,contractDemand.getContratDemand());
					            
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("months"))
					   				{
					   					months=(Float)map.getValue();
					   					out.print("Months :"+months+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("days"))
					   				{
					   					days=(int)map.getValue();
					   					out.print("days after month:"+days+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("netMonth"))
					   				{
					   					netMonths=(Float)map.getValue();
					   					out.print("Total number of  billable month are :"+netMonths+"</br>");
					   				}
					   				
					   				if(map.getKey().equals("rateType"))
					   				{
					   					rateType = (String)map.getValue();
					   				}
					   				if(map.getKey().equals("rate"))
					   				{
					   					rate = (Float)map.getValue();
					   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
					   				}
					   				if(map.getKey().equals("total"))
					   				{
					   					total = (Float)map.getValue();
					   				}
					   				if(map.getKey().equals("powerFactorLagging"))
					   				{
					   					powerFactorLagging = (Float)map.getValue();
					   				}
					   				if(map.getKey().equals("consumptionUnitsFlotValue"))
					   				{
					   					consumptionUnitsFlotValue = (Float)map.getValue();
					   				}
					   				
					   		    }
					   		    out.print("Recorded Power Factor : "+uomValue+"</br>");
					   		    float roundedPF = roundOff(uomValue, 2);
					            out.print("Recorded Power Factor after roundoff up to 2 decimal points: "+roundedPF+"</br>");
					   			out.print("Power Factor Lagging: "+powerFactorLagging+"</br>");
					   			out.print("Power Factor Amount :"+"("+consumptionUnitsFlotValue + " X "+"("+powerFactorLagging+" X "+100+" X "+rate/100+ ")"+")"+" X "+netMonth+" = "+total+"</br>");
					   			
					   			out.print("Total Amount is (In Rupees ) :"+total+"</br>");
			   					out.print("------------------------------------------------------"+"</br>");
					   		}
					   		
	  						}
							else
	  						{
	 							
	 							if(calculation.getEndDate().compareTo(elRateMaster.getValidFrom())>=0 && calculation.getEndDate().compareTo(elRateMaster.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(calculation.getEndDate());
	  	 							 
	 					   		    out.print("Tariff Valid From :"+elRateMaster.getValidFrom()+" Up to Valid To :"+elRateMaster.getValidTo()+"</br>");
	 					   		 
						   		    String rateType = null;
						   		    float rate = 0;
						   		    Float netMonths = null;
						   		    Float months;
						   		    int days;
						   	        Float powerFactorLagging = 0.0f;
						   	        Float consumptionUnitsFlotValue = 0.0f;
						   	        Float dcRate = 0.0f;
						   	        Float rate1 = 0.0f;
						   	        Float billableUnits = 0.0f;
						   	        Float rate2 = null;
						   	        Float uomValueDC = 0.0f;
						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			
						   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("months"))
						   				{
						   					months=(Float)map.getValue();
						   					out.print("Months :"+months+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("days"))
						   				{
						   					days=(int)map.getValue();
						   					out.print("days after month:"+days+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("netMonth"))
						   				{
						   					netMonths=(Float)map.getValue();
						   					out.print("Total number of  billable month are :"+netMonths+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("rateType"))
						   				{
						   					rateType = (String)map.getValue();
						   				}
						   				if(map.getKey().equals("rate"))
						   				{
						   					rate = (Float)map.getValue();
						   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
						   				}
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				
						   		    }
						   			if(rateType.trim().equalsIgnoreCase("Rupees"))
				   					{
				   						out.print("("+uomValue+"X"+rate+")"+" X"+(netMonths)+" = "+((uomValue * (rate)))*netMonths +"</br>");
				   					}
				   					if(rateType.trim().equalsIgnoreCase("Paise"))
				   					{
				   						out.print("("+uomValue+"X"+rate/100+")"+" X"+(netMonths)+" = "+((uomValue * (rate/100)))*netMonths +"</br>");
				   					}
				   					if(rateType.trim().equalsIgnoreCase("Percentage"))
				   					{
				   						out.print("("+uomValue+"X"+rate/100+")"+" X"+(netMonths)+" = "+((uomValue * (rate/100)))*netMonths +"</br>");
				   					}
				   					if(rateType.trim().equalsIgnoreCase("Multiplier"))
				   					{
				   						out.print("("+uomValue+"X"+rate+")"+" X"+(netMonths)+" = "+((uomValue * (rate)))*netMonths +"</br>");
				   					}
				   					out.print("Total Amount is (In Rupees ) :"+totalAmount+"</br>");
				   					out.print("------------------------------------------------------"+"</br>");
						   		 }
						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
										float slabDifference1 =0.0f;
										float lastSlabTo = 0.0f; 
										LocalDate fromDate1 =  new LocalDate(startDate);
									    LocalDate toDate1 = new LocalDate(endDate);
									    PeriodType monthDay1 = PeriodType.yearMonthDay().withYearsRemoved();
									    Period difference1 = new Period(fromDate1, toDate1, monthDay1);
									    float billableMonths1 = difference1.getMonths();
									    int daysAfterMonth1 = difference1.getDays();
									    
									     Calendar cal1 = Calendar.getInstance();
										 cal1.setTime(calculation.getEndDate());
										 float daysToMonths1 = (float)daysAfterMonth1 / cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
										 BigDecimal bd1 = new BigDecimal(daysToMonths1 + billableMonths1);
										 bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
										 float netMonth1 = bd1.floatValue();
										 HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
										 
										List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());
										for (ELRateSlabs elRateSlabs : elRateSlabsList)
										  {
										    if(lastSlabTo == 0)
										    {
										     slabDifference1 = (elRateSlabs.getSlabTo() - elRateSlabs.getSlabFrom());
										     lastSlabTo = elRateSlabs.getSlabTo();
										    }
										    else
										    {
										    	slabDifference1 = (elRateSlabs.getSlabTo() - lastSlabTo);
										    	lastSlabTo = elRateSlabs.getSlabTo();
										    }
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Rupees",null, locale)))
											{
												if(uomValue > slabDifference1)
												{
													uomValue = uomValue - slabDifference1;
													totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate())) *netMonth1;
													
													consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
													consolidateBill.put("rate", elRateSlabs.getRate());
													consolidateBill.put("slabDifference", slabDifference1);
													
													out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference1 * (elRateSlabs.getRate())))*netMonth1 +"</br>");
												}
												else
												{
													if(uomValue > 0)
													{
														totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()))* netMonth1;
														consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
														consolidateBill.put("rate", elRateSlabs.getRate());
														consolidateBill.put("uomValue", uomValue);
														out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth1 +"</br>");
														uomValue = uomValue - slabDifference1;
													}
												}
											}
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Paise",null, locale)))
											{
												if(uomValue > slabDifference1)
												{
													uomValue = uomValue - slabDifference1;
													totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate()/100)) *netMonth1;
													consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
													consolidateBill.put("rate", elRateSlabs.getRate());
													consolidateBill.put("slabDifference", slabDifference1);
													out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference1 * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
												}
												else
												{
													if(uomValue > 0)
													{
														totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()/100))* netMonth1;
														consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
														consolidateBill.put("rate", elRateSlabs.getRate());
														consolidateBill.put("uomValue", uomValue);
														out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
														uomValue = uomValue - slabDifference1;
													}
												}
											}
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												if(uomValue > slabDifference1)
												{
													uomValue = uomValue - slabDifference1;
													totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate()/100)) *netMonth1;
													consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
													consolidateBill.put("rate", elRateSlabs.getRate()/100);
													consolidateBill.put("slabDifference", slabDifference1);
													out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((slabDifference1 * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
												}
												else
												{
													if(uomValue > 0)
													{
														totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()/100))* netMonth1;
														consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
														consolidateBill.put("rate", elRateSlabs.getRate()/100);
														consolidateBill.put("uomValue", uomValue);
														out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate()/100)+" = "+((uomValue * (elRateSlabs.getRate()/100)))*netMonth1 +"</br>");
														uomValue = uomValue - slabDifference1;
													}
												}
											}
											
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Multiplier",null, locale)))
											{
												if(uomValue > slabDifference1)
												{
													uomValue = uomValue - slabDifference1;
													totalAmount1 = totalAmount1 + (slabDifference1 * (elRateSlabs.getRate())) *netMonth1;
													consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
													consolidateBill.put("rate", elRateSlabs.getRate());
													consolidateBill.put("slabDifference", slabDifference1);
													out.write("("+slabDifference1+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((slabDifference1 * (elRateSlabs.getRate())))*netMonth1 +"</br>");
												}
												else
												{
													if(uomValue > 0)
													{
														totalAmount1 = totalAmount1 + (uomValue * (elRateSlabs.getRate()))* netMonth1;
														consolidateBill.put("rateType", elRateSlabs.getSlabRateType());
														consolidateBill.put("rate", elRateSlabs.getRate());
														consolidateBill.put("uomValue", uomValue);
														out.write("("+uomValue+"X"+netMonth1+")"+" X"+(elRateSlabs.getRate())+" = "+((uomValue * (elRateSlabs.getRate())))*netMonth1 +"</br>");
														uomValue = uomValue - slabDifference1;
													}
												}
											}
											consolidateBill.put("months", billableMonths1);
											consolidateBill.put("days", daysAfterMonth1);
											consolidateBill.put("netMonth", netMonth1);
										  }
										if(elRateMaster.getMaxRate()!=0)
										{
											  totalAmount1 = (totalAmount1 > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount1;
											  consolidateBill.put("total", totalAmount1);
										}
										else
										{
											  totalAmount1 = (totalAmount1 > elRateMaster.getMinRate()) ? totalAmount1 : elRateMaster.getMinRate();
											  consolidateBill.put("total", totalAmount1);
										}
										out.print("Total Amount is (In Rupees ) :"+totalAmount1+"</br>");
					   					out.print("------------------------------------------------------"+"</br>"); 
							   		 }
						   		
						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
						   			
						   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				logger.info("Key "+map.getKey() +" Value "+map.getValue());
						   				if(map.getKey().equals("months"))
						   				{
						   					months=(Float)map.getValue();
						   					out.print("Months :"+months+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("days"))
						   				{
						   					days=(int)map.getValue();
						   					out.print("days after month:"+days+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("netMonth"))
						   				{
						   					netMonths=(Float)map.getValue();
						   					out.print("Total number of  billable month are :"+netMonths+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("rateType"))
						   				{
						   					rateType = (String)map.getValue();
						   				}
						   				if(map.getKey().equals("rate"))
						   				{
						   					rate = (Float)map.getValue();
						   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
						   				}
						   				if(map.getKey().equals("total"))
						   				{
						   					total = (Float)map.getValue();
						   				}
						   		    }
						   			if(rateType.trim().equalsIgnoreCase("Rupees"))
				   					{
				   						totalAmount = totalAmount + ( rate)* netMonths;
				   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
				   					}
				   					if(rateType.trim().equalsIgnoreCase("Paise"))
				   					{
				   						totalAmount = totalAmount + ((rate/100))* netMonths;
				   						out.print("("+netMonths+")"+" X"+(rate/100)+" = "+(((rate/100)))*netMonths +"</br>");
				   					}
				   					if(rateType.trim().equalsIgnoreCase("Percentage"))
				   					{
				   						totalAmount = totalAmount + ((rate/100))* netMonths;
				   						out.print("("+netMonths+")"+" X"+(rate/100)+" = "+(((rate/100)))*netMonths +"</br>");
				   					}
				   					if(rateType.trim().equalsIgnoreCase("Multiplier"))
				   					{
				   						totalAmount = totalAmount + ((rate))* netMonths;
				   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
				   					}
				   					if(elRateMaster.getMaxRate()!=0)
				   					{
				   						 totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
				   					}
				   					else
				   					{
				   						  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
				   					}
				   					out.print("Total Amount is (In Rupees ) :"+total+"</br>");
				   					out.print("------------------------------------------------------"+"</br>");
						   		 }
						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		{
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
						   		}
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
						   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),calculation.getUomValue());
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("months"))
						   				{
						   					months=(Float)map.getValue();
						   					out.print("Months :"+months+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("days"))
						   				{
						   					days=(int)map.getValue();
						   					out.print("days after month:"+days+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("netMonth"))
						   				{
						   					netMonths=(Float)map.getValue();
						   					out.print("Total number of  billable month are :"+netMonths+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("rateType"))
						   				{
						   					rateType = (String)map.getValue();
						   				}
						   				if(map.getKey().equals("rate"))
						   				{
						   					rate = (Float)map.getValue();
						   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
						   				}
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   			
						   			out.print("Phase is "+calculation.getUomValue()+"</br>");
						   			if(rateType.trim().equalsIgnoreCase("Rupees"))
				   					{
				   						out.print("("+netMonths+")"+" X"+(rate)+" = "+(((rate)))*netMonths +"</br>");
				   					}
						   			out.print("Total Amount is (In Rupees ) :"+totalAmount+"</br>");
				   					out.print("------------------------------------------------------"+"</br>");
						   		}
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating Demand  charges ::::::::::::::::::::");
						   			ContractDemand contractDemand = null;
						   			if(calculation instanceof ContractDemand)
						   			{
						   				contractDemand = (ContractDemand) calculation;
						   			}
						   			consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("months"))
						   				{
						   					months=(Float)map.getValue();
						   					out.print("Months :"+months+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("days"))
						   				{
						   					days=(int)map.getValue();
						   					out.print("days after month:"+days+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("netMonth"))
						   				{
						   					netMonths=(Float)map.getValue();
						   					out.print("Total number of  billable month are :"+netMonths+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("rateType"))
						   				{
						   					rateType = (String)map.getValue();
						   				}
						   				if(map.getKey().equals("rate"))
						   				{
						   					rate = (Float)map.getValue();
						   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("total"))
						   				{
						   					total = (Float)map.getValue();
						   				}
						   				
						   				if(map.getKey().equals("dcRate"))
						   				{
						   					dcRate = (Float)map.getValue();
						   				}
						   				if(map.getKey().equals("rate1"))
						   				{
						   					rate1 = (Float)map.getValue();
						   				}
						   				if(map.getKey().equals("billableUnits"))
						   				{
						   					billableUnits  = (Float)map.getValue();
						   				}
						   				 
						   				if(map.getKey().equals("rate2"))
						   				{
						   					rate2   = (Float)map.getValue();
						   				}
						   				if(map.getKey().equals("uomValue"))
						   				{
						   					uomValueDC   = (Float)map.getValue();
						   				}
						   		    }
						   			if(rateType.trim().equalsIgnoreCase("Multiplier"))
				   					{
						   				if(rate1 == 0.0)
				   						{
				   							//out.print("("+dcRate+" X"+billableUnits+" X"+rate1+")"+" X"+(netMonth)+" = "+((dcRate * billableUnits* (rate1)))*netMonth +"</br>");
				   							out.print("("+dcRate+" X"+uomValueDC+" X"+rate2+")"+" X"+(netMonth)+" = "+((dcRate * uomValueDC* (rate2)))*netMonth +"</br>");
				   						}
				   						else
				   						{
				   							out.print("("+dcRate+" X"+billableUnits+" X"+rate1+")"+" X"+(netMonth)+" = "+((dcRate * billableUnits* (rate1)))*netMonth +"</br>");
				   							out.print("("+dcRate+" X"+uomValueDC+" X"+rate2+")"+" X"+(netMonth)+" = "+((dcRate * uomValueDC* (rate2)))*netMonth +"</br>");
				   						}
				   					}
						   			out.print("Total Amount is (In Rupees ) :"+total+"</br>");
				   					out.print("------------------------------------------------------"+"</br>");
						   		}
						   		
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating power factor penalty ::::::::::::::::::::");
						   			ContractDemand contractDemand = null;
						   			if(calculation instanceof ContractDemand)
						   			{
						   				contractDemand = (ContractDemand) calculation;
						   			}
						   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,contractDemand.getContratDemand());
						            
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("months"))
						   				{
						   					months=(Float)map.getValue();
						   					out.print("Months :"+months+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("days"))
						   				{
						   					days=(int)map.getValue();
						   					out.print("days after month:"+days+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("netMonth"))
						   				{
						   					netMonths=(Float)map.getValue();
						   					out.print("Total number of  billable month are :"+netMonths+"</br>");
						   				}
						   				
						   				if(map.getKey().equals("rateType"))
						   				{
						   					rateType = (String)map.getValue();
						   				}
						   				if(map.getKey().equals("rate"))
						   				{
						   					rate = (Float)map.getValue();
						   					out.print("The Rate is ( in "+rateType+"): "+(Float)map.getValue()+"</br>");
						   				}
						   				if(map.getKey().equals("total"))
						   				{
						   					total = (Float)map.getValue();
						   				}
						   				if(map.getKey().equals("powerFactorLagging"))
						   				{
						   					powerFactorLagging = (Float)map.getValue();
						   				}
						   				if(map.getKey().equals("consumptionUnitsFlotValue"))
						   				{
						   					consumptionUnitsFlotValue = (Float)map.getValue();
						   				}
						   				
						   		    }
						   		    out.print("Recorded Power Factor : "+uomValue+"</br>");
						   		    float roundedPF = roundOff(uomValue, 2);
						            out.print("Recorded Power Factor after roundoff up to 2 decimal points: "+roundedPF+"</br>");
						   			out.print("Power Factor Lagging: "+powerFactorLagging+"</br>");
						   			out.print("Power Factor Amount :"+"("+consumptionUnitsFlotValue + " X "+"("+powerFactorLagging+" X "+100+" X "+rate/100+ ")"+")"+" X "+netMonth+" = "+total+"</br>");
						   			
						   			out.print("Total Amount is (In Rupees ) :"+total+"</br>");
				   					out.print("------------------------------------------------------"+"</br>");
						   		}
	 								
	  							}
	  						}
						}
					}
			 }
	       }
	  }
	 return null;
	}

	@RequestMapping(value = "/tariffCalc/gettodAmount", method = RequestMethod.GET)
	public @ResponseBody
	List<?> calculateTodTariff(HttpServletRequest req, HttpServletResponse response) throws ParseException, JSONException, IOException 
	{
		String tariffName= req.getParameter("tariffName");
		String rateName = req.getParameter("rateName");
		
		Integer consumptionsUnitsT1 = Integer.parseInt(req.getParameter("t1unit"));
		Integer consumptionsUnitsT2  = Integer.parseInt(req.getParameter("t2unit"));
		Integer consumptionsUnitsT3  = Integer.parseInt(req.getParameter("t3unit"));
		Date fromDate= (dateTimeCalender.getdateFormat(req.getParameter("validFrom")));
		Date toDate= (dateTimeCalender.getdateFormat(req.getParameter("validTo")));

		logger.info(""+tariffName);
		logger.info(""+rateName);
		logger.info(""+consumptionsUnitsT1);
		logger.info(""+consumptionsUnitsT2);
		logger.info(""+consumptionsUnitsT3);
		logger.info(""+fromDate);
		logger.info(""+toDate);
		
		GenericClassForTodCalculation<String, String, Integer, Integer,Integer,Date, Date> calucaltion=new GenericClassForTodCalculation<>();
		
		calucaltion.setConsumptionsUnitsT1(consumptionsUnitsT1);
		calucaltion.setConsumptionsUnitsT2(consumptionsUnitsT2);
		calucaltion.setConsumptionsUnitsT3(consumptionsUnitsT3);
        calucaltion.setRateName(rateName);
        calucaltion.setTariffName(tariffName);
        calucaltion.setStartDate(fromDate);
        calucaltion.setEndDate(toDate);
	    tariffCalculationService.tariffTodCalculation(calucaltion,response);
		logger.info("in controller");
		return null;
}
	@RequestMapping(value = "/tariffCalc/getTaxdAmount", method = RequestMethod.GET)
	public @ResponseBody
	List<?> calculateTax(HttpServletRequest req, HttpServletResponse response) throws ParseException, JSONException, IOException 
	{
		PrintWriter out = null;
		double taxAmount = Double.parseDouble(req.getParameter("taxAmount"));
		float taxPercentage  = Float.parseFloat(req.getParameter("taxPercentage"));
		try
		{
		out = response.getWriter();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		double totalTaxAmoount = taxCalculation(taxAmount,taxPercentage);
		out.write("Tax Amount is :"+totalTaxAmoount);
		
		return null;
	}
	
	public double taxCalculation(double taxAmount,float tax)
	{
		logger.info("tax "+tax);
		
		double netTax=0;
		BigDecimal bigDecimal = new BigDecimal(tax/100);
		bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);
		double taxPer = bigDecimal.doubleValue();
		netTax = taxAmount * taxPer;
		
		return netTax;
	}
	@RequestMapping(value = "/tariffCalc/getrebateAmount", method = RequestMethod.GET)
	public @ResponseBody
	List<?> calculateRebate(HttpServletRequest req, HttpServletResponse response) throws ParseException, JSONException, IOException 
	{
	
		Integer rebateAmount = Integer.parseInt(req.getParameter("rebateAmount"));
		Integer rebatePercentage  = Integer.parseInt(req.getParameter("rebatePercentage"));
		logger.info("rebateamount"+rebateAmount);
		logger.info("rebatepercentage"+rebatePercentage);
		return null;
	}
	
	public static float roundOff(float val, int places)
	{
		 BigDecimal bigDecima = new BigDecimal(val);
         bigDecima = bigDecima.setScale(places, BigDecimal.ROUND_HALF_UP);
         return  bigDecima.floatValue();
	}
	/*:::::::::::::::::::::::::::::::::::: Code minimization tariff calculation ::::::::::::::::::::::*/
	@SuppressWarnings("unused")
	private HashMap<Object, Object> tariffCalculation1(GenericParameterForCalculation calculation) 
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		Float uomValue;
		LocalDate fromDate = new LocalDate(calculation.getStartDate());
		LocalDate toDate = new LocalDate(calculation.getEndDate());
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(calculation.getStartDate());
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		logger.info("netMonth "+netMonth);
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		
		float totalAmount = 0;
		 
		ELTariffMaster elTariffMaster = tariffCalculationService.getTariffMasterByName(calculation.getTariffName());
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elTariffMaster.getElTariffID(),calculation.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(calculation.getStartDate()) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (calculation.getEndDate().compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elTariffMaster.getElTariffID(),calculation.getRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster : elRateMasterList) 
			 {
				  uomValue =  calculation.getUomValue() / netMonth;
				  if((calculation.getStartDate().compareTo(elRateMaster.getValidFrom()) >=0) && (calculation.getEndDate().compareTo(elRateMaster.getValidTo())<=0))
					{
			   		    logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		    
			   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		
			   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		{
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			   		}
			   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster, calculation.getStartDate(), calculation.getEndDate(),calculation.getUomValue());
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		}
			   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating Demand  charges ::::::::::::::::::::");
			   			ContractDemand contractDemand = null;
			   			if(calculation instanceof ContractDemand)
			   			{
			   				contractDemand = (ContractDemand) calculation;
			   			}
			   			consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		}
			   		
			   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating power factor penalty ::::::::::::::::::::");
			   			ContractDemand contractDemand = null;
			   			if(calculation instanceof ContractDemand)
			   			{
			   				contractDemand = (ContractDemand) calculation;
			   			}
			   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
		           }
				  else
					{
						if(calculation.getStartDate().compareTo(elRateMaster.getValidTo())<=0)
						{
							if((calculation.getEndDate().compareTo(elRateMaster.getValidFrom())>= 0) && (elRateMaster.getValidTo().compareTo(calculation.getEndDate())<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = calculation.getUomValue() / netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(calculation.getStartDate());
		 							  endDate = new LocalDate(elRateMaster.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster.getValidTo());
		 							  lastUpdatedDate =elRateMaster.getValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		 }
					   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   		 }
					   		
					   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		 }
					   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		{
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
					   		}
					   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
					   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),calculation.getUomValue());
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		}
					   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating Demand  charges ::::::::::::::::::::");
					   			ContractDemand contractDemand = null;
					   			if(calculation instanceof ContractDemand)
					   			{
					   				contractDemand = (ContractDemand) calculation;
					   			}
					   			consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		}
					   		
					   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating power factor penalty ::::::::::::::::::::");
					   			ContractDemand contractDemand = null;
					   			if(calculation instanceof ContractDemand)
					   			{
					   				contractDemand = (ContractDemand) calculation;
					   			}
					   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,contractDemand.getContratDemand());
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		}
  	  					}
							else
	  						{
	 							if(calculation.getEndDate().compareTo(elRateMaster.getValidFrom())>=0 && calculation.getEndDate().compareTo(elRateMaster.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(calculation.getEndDate());
						   		  
						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		 }
						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
						   		
						   		 if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		 }
						   		if(elRateMaster.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		{
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
						   		}
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
						   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),calculation.getUomValue());
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		}
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating Demand  charges ::::::::::::::::::::");
						   			ContractDemand contractDemand = null;
						   			if(calculation instanceof ContractDemand)
						   			{
						   				contractDemand = (ContractDemand) calculation;
						   			}
						   			consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, contractDemand.getStartDate(), contractDemand.getEndDate(),uomValue,contractDemand.getContratDemand());
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		}
						   		
						   		if(elRateMaster.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating power factor penalty ::::::::::::::::::::");
						   			ContractDemand contractDemand = null;
						   			if(calculation instanceof ContractDemand)
						   			{
						   				contractDemand = (ContractDemand) calculation;
						   			}
						   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,contractDemand.getContratDemand());
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
			    			   		}
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  consolidatedBill.put("totalAmount", totalAmount);
	 return consolidatedBill;
	}
	
	public HashMap<Object, Object> tariffAmount(ELRateMaster elRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue,float extraParameter,String category) 
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String slabString=null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		String tariffChange = null;
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate1(elRateMaster.getElTariffID(),elRateMaster.getRateName(),category);
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName1(elRateMaster.getElTariffID(),elRateMaster.getRateName(),category);
		  if(billableMonths == 0)
		  {
		  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		   for (ELRateMaster elRateMaster1 : elRateMasterList) 
			{
			  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
			  {
			   	 logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			   		
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			   			logger.info("uom value:::::::::::::::::::"+uomValue);
			   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		{
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			   		}
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
			   		{
			   			logger.info("------------------- MDI calculation -------------------");
			   			consolidatedBill = tariffCalculationService.mdiCalculation(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }
			   			//consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
			   		{
			   			logger.info("------------------- PF Penalty calculation -------------------");
			   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParameter,uomValue);
			   		}
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.fixedSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			  }
			  else
				{
					if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
					{
						if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
						{
							
						}
						else
  						{
 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
  							{
 								
  							}
  						}
					}
				}	
			} 
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		 // uomValue =  uomValue/ netMonth; 
		  for (ELRateMaster elRateMaster1 : elRateMasterList) 
			 {
		    	if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.fixedSlab",null, locale).trim())){
		    		 uomValue =  uomValue* netMonth;
		    	}else{
		    		logger.info("netMonth ---------------------- "+netMonth);
		    		
		    		/* uomValue =  uomValue/ netMonth; */
		    	}
				 
				  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
					{
					  
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		    
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.fixedSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: rateType.fixedSlab  ::::::::::::::::::::");
			   			 logger.info("::::::::::uomValue::::::::::::"+uomValue);
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			   		
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			   			logger.info("uomValue ----------------- "+uomValue);
			   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("slabString"))
			   				{
			   					slabString = slabString +(String)map.getValue();
			   				}
			   		    }	
			   		 }
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		{
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			   		}
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				
			   		    }	
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
			   		{
			   			logger.info("------------------- MDI calculation -------------------");
			   			//consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
			   			consolidatedBill = tariffCalculationService.mdiCalculation(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
			   		{
			   			logger.info("------------------- PF Penalty calculation -------------------");
			   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParameter,uomValue);
			   		}

		           }
				  else
					{
					   tariffChange = tariffChange +"  Valid From "+elRateMaster1.getValidFrom()+" To "+elRateMaster1.getValidTo()+'\n';					  
					   logger.info("tariffChange -------------- "+tariffChange);
						if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
						{
							if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								/*uomValue = uomValue/ netMonth;*/
								uomValue = uomValue+0;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				
					   		    }	
					   		 }
					   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("slabString"))
					   				{
					   					slabString = slabString +(String)map.getValue();
					   				}
					   		    }
					   		 }
					   		
					   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("slabString"))
					   				{
					   					slabString = slabString +(String)map.getValue();
					   				}
					   		    }	
					   		 }
					   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		{
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
					   		}
					   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
					   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				
					   		    }	
					   		}
					   		
					   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
					   		{
					   			logger.info("------------------- MDI calculation -------------------");
					   			//consolidatedBill = tariffCalculationService.dcAmount(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
					   			consolidatedBill = tariffCalculationService.mdiCalculation(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   		}
					   		
					   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
					   		{
					   			logger.info("------------------- PF Penalty calculation -------------------");
					   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParameter,uomValue);
					   		}

  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster1.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlab(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("slabString"))
						   				{
						   					slabString = slabString +(String)map.getValue();
						   				}
						   		    }	
						   		 }
						   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlab(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("slabString"))
						   				{
						   					slabString = slabString +(String)map.getValue();
						   				}
						   		    }
						   		 }
						   		
						   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlab(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		 }
						   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		{
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
						   		}
						   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
						   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		}
						   		
						   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
						   		{
						   			logger.info("------------------- MDI calculation -------------------");
						   			consolidatedBill = tariffCalculationService.mdiCalculation(elRateMaster, previousBillDate,currentBillDate,uomValue,extraParameter);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		}
						   		
						   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
						   		{
						   			logger.info("------------------- PF Penalty calculation -------------------");
						   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParameter,uomValue);
						   		}

	  							}
	  						}
						}
					}
			    }
	       }
	  }
	 
	  consolidatedBill.put("totalAmount", totalAmount);
	  consolidatedBill.put("slabString", slabString);
	  consolidatedBill.put("tariffChange", tariffChange);
	 return consolidatedBill;
	}

	public HashMap<String, Object> waterTariffAmount(WTRateMaster waterRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue) 
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String wtSlabString=null;
		HashMap<String, Object> consolidatedBill = new LinkedHashMap<>();
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		logger.info("netMonth :::::::::::::::::::::: "+netMonth);
		
		List<Map<String, Object>> dateList = tariffCalculationService.getWaterMinMaxDate(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());

		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	 else
	  {
		  List<WTRateMaster> gasRateMasterList = tariffCalculationService.getWaterRateMasterByIdName(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
			  for (WTRateMaster waterRateMaster1 : gasRateMasterList) 
				{
				  if((previousBillDate.compareTo(waterRateMaster1.getWtValidFrom()) >=0) && (currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0))
				  {
				   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
				   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<String, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("totalAmount"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   				if(map.getKey().equals("wtSlabString"))
				   				{
				   					wtSlabString = wtSlabString +map.getValue();
				   				}
				   		    }	
				   		 }
					   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			for (Entry<String, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("totalAmount"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		 }
				  }
				  else
					{
						if(previousBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
						{
							if((currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>= 0) && (waterRateMaster1.getWtValidTo().compareTo(currentBillDate)<=0))
							{

								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<String, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("totalAmount"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("wtSlabString"))
					   				{
					   					wtSlabString = wtSlabString +map.getValue();
					   				}
					   		    }
					   			
					   		 }
					   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   			
						   		 }
							}
							else
							{
								if(currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>=0 && currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
								{

	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(waterRateMaster1.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("wtSlabString"))
						   				{
						   					wtSlabString = wtSlabString +map.getValue();
						   				}
						   		    }
						   		 }
						   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
							   			for (Entry<String, Object> map : consolidatedBill.entrySet())
							   		    {
							   				if(map.getKey().equals("totalAmount"))
							   				{
							   					totalAmount = totalAmount +(Float)map.getValue();
							   				}
							   		    }
							   		 }
								}
							}
						}
					}	
				} 
		  }
		  else
	      {
		 // uomValue = uomValue/ netMonth;
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (WTRateMaster waterRateMaster1 : gasRateMasterList) 
			 {
				 /* uomValue =  uomValue/ netMonth;*/
				  if((previousBillDate.compareTo(waterRateMaster1.getWtValidFrom()) >=0) && (currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<String, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("totalAmount"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("wtSlabString"))
			   				{
			   					wtSlabString = wtSlabString +map.getValue();
			   				}
			   		    }
			   		 }
				   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			consolidatedBill =  tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<String, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("totalAmount"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   		    }
				   		 }

		           }
				  else
					{
						if(previousBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
						{
							logger.info("uomValue last------------------------ "+uomValue);
							if((currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>= 0) && (waterRateMaster1.getWtValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								/*uomValue = uomValue/ netMonth;*/
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill =  tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<String, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("totalAmount"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("wtSlabString"))
					   				{
					   					wtSlabString = wtSlabString +map.getValue();
					   				}
					   		    }
					   		 }
					   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill =  tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
  	  					      }
							else
	  						{
	 							if(currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>=0 && currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(waterRateMaster1.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("wtSlabString"))
						   				{
						   					wtSlabString = wtSlabString +map.getValue();
						   				}
						   		    }
						   		 }
						   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
							   			for (Entry<String, Object> map : consolidatedBill.entrySet())
							   		    {
							   				if(map.getKey().equals("totalAmount"))
							   				{
							   					totalAmount = totalAmount +(Float)map.getValue();
							   				}
							   				if(map.getKey().equals("wtSlabString"))
							   				{
							   					wtSlabString = wtSlabString +map.getValue();
							   				}
							   		    }
							   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  consolidatedBill.put("wtSlabString", wtSlabString);
	  consolidatedBill.put("total", totalAmount);
	 return consolidatedBill;
	}

	public HashMap<Object, Object> gasTariffAmount(GasRateMaster gasRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue)
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String gaString=null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		List<Map<String, Object>> dateList = tariffCalculationService.getGasMinMaxDate(gasRateMaster.getGasTariffId(),gasRateMaster.getGasRateName());

		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	 else
	  {
		  List<GasRateMaster> gasRateMasterList = tariffCalculationService.getGasRateMasterByIdName(gasRateMaster.getGasTariffId(),gasRateMaster.getGasRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
			  for(GasRateMaster gasRateMaster1 : gasRateMasterList)  
				{
				  if((previousBillDate.compareTo(gasRateMaster1.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0))
				  {
				   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
				   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("total"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   				if(map.getKey().equals("gaString"))
				   				{
				   					gaString = gaString +(String)map.getValue();
				   				}
				   		    }
				   		 }
				   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
				   		 }
				  }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>= 0) && (gasRateMaster1.getGasValidTo().compareTo(currentBillDate)<=0))
							{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("gaString"))
					   				{
					   					gaString = gaString +(String)map.getValue();
					   				}
					   		    }
					   		 }
					   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
					   		 }
							}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster1.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("gaString"))
						   				{
						   					gaString = gaString +(String)map.getValue();
						   				}
						   		    }
						   		 }
						   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
	  							}
	  						}
						}
					}	
				} 
		  }
	else
	  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		//  uomValue =  uomValue/ netMonth;
		    for (GasRateMaster gasRateMaster1 : gasRateMasterList) 
			 {
				  if((previousBillDate.compareTo(gasRateMaster1.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("gaString"))
			   				{
			   					gaString = gaString +(String)map.getValue();
			   				}
			   		    }
			   		 }
			   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("total"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   		    }
			   			
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>= 0) && (gasRateMaster1.getGasValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								/*uomValue = uomValue/ netMonth;*/
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("gaString"))
					   				{
					   					gaString = gaString +(String)map.getValue();
					   				}
					   		    }
					   		 }
					   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   			
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster1.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("gaString"))
						   				{
						   					gaString = gaString +(String)map.getValue();
						   				}
						   		    }
						   		 }
						   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  consolidatedBill.put("total", totalAmount);
	  consolidatedBill.put("gaString", gaString);
	 return consolidatedBill;
	}

	public Float solidWasteTariffAmount(SolidWasteRateMaster solidWasteRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue)
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		 
		List<Map<String, Object>> dateList = tariffCalculationService.getSolidWasteMinMaxDate(solidWasteRateMaster.getSolidWasteTariffId(),solidWasteRateMaster.getSolidWasteRateName());

		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	 else
	  {
		  List<SolidWasteRateMaster> solidWasteRateMasterList = tariffCalculationService.getSolidWasteRateMasterByIdName(solidWasteRateMaster.getSolidWasteTariffId(),solidWasteRateMaster.getSolidWasteRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
			  for (SolidWasteRateMaster solidWasteRateMaster1 : solidWasteRateMasterList) 
				{
				  if((previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom()) >=0) && (currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0))
				  {

				   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
				   		 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				   			totalAmount = tariffCalculationService.solidWasteTariffCalculationMultiSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
				   		 }
				   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			 totalAmount = tariffCalculationService.solidWasteTariffCalculationSingleSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			
				   		 }
				   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						{
							logger.info(":::::::::::::::::::: Rate Master if of type Range Slab ::::::::::::::::::::");
							totalAmount = tariffCalculationService.solidWasteTariffCalculationRangeSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
						}
				  }
				  else
					{
						if(previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
						{
							if((currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>= 0) && (solidWasteRateMaster1.getSolidWasteValidTo().compareTo(currentBillDate)<=0))
							{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			totalAmount = tariffCalculationService.solidWasteTariffCalculationMultiSlab(solidWasteRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
					   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 totalAmount = tariffCalculationService.solidWasteTariffCalculationSingleSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
					   		 }
					   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							{
								logger.info(":::::::::::::::::::: Rate Master if of type Range Slab ::::::::::::::::::::");
								totalAmount = tariffCalculationService.solidWasteTariffCalculationRangeSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
							}
							}
							else
	  						{
	 							if(currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>=0 && currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			totalAmount = tariffCalculationService.solidWasteTariffCalculationMultiSlab(solidWasteRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   		 }
						   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 totalAmount = tariffCalculationService.solidWasteTariffCalculationSingleSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
						   		 }
						   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
								{
									logger.info(":::::::::::::::::::: Rate Master if of type Range Slab ::::::::::::::::::::");
									totalAmount = tariffCalculationService.solidWasteTariffCalculationRangeSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
								}
	  						  }
	  						}
						}
					}	
				} 
			  
		  }
	else
	  {
		 // uomValue =  uomValue / netMonth;
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (SolidWasteRateMaster solidWasteRateMaster1 : solidWasteRateMasterList) 
			 {
				/*  uomValue =  uomValue / netMonth;*/
				  if((previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom()) >=0) && (currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			totalAmount = tariffCalculationService.solidWasteTariffCalculationMultiSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
			   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 totalAmount = tariffCalculationService.solidWasteTariffCalculationSingleSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			
			   		 }
			   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					{
						logger.info(":::::::::::::::::::: Rate Master if of type Range Slab ::::::::::::::::::::");
						totalAmount = tariffCalculationService.solidWasteTariffCalculationRangeSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
					}

		           }
				  else
					{
						if(previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
						{
							if((currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>= 0) && (solidWasteRateMaster1.getSolidWasteValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								/*uomValue = uomValue/ netMonth;*/
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			totalAmount = tariffCalculationService.solidWasteTariffCalculationMultiSlab(solidWasteRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
					   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 totalAmount = tariffCalculationService.solidWasteTariffCalculationSingleSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>=0 && currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			totalAmount = tariffCalculationService.solidWasteTariffCalculationMultiSlab(solidWasteRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   		 }
						   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 totalAmount = tariffCalculationService.solidWasteTariffCalculationSingleSlab(solidWasteRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	 return totalAmount;
	
	}

	public Float commonServiceTariffAmount(CommonServicesRateMaster commonServiceRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue)
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		 
		List<Map<String, Object>> dateList = tariffCalculationService.getCommonServiceMinMaxDate(commonServiceRateMaster.getCsTariffId(),commonServiceRateMaster.getCsRateName());

		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	 else
	  {
		  List<CommonServicesRateMaster> commonServiceRateMasterList = tariffCalculationService.getCommonServiceMasterByIdName(commonServiceRateMaster.getCsTariffId(),commonServiceRateMaster.getCsRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		  }
		  else
	  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (CommonServicesRateMaster commonServiceRateMaster1 : commonServiceRateMasterList) 
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(commonServiceRateMaster1.getCsValidFrom()) >=0) && (currentBillDate.compareTo(commonServiceRateMaster1.getCsValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(commonServiceRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServiceRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
			   		if(commonServiceRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServiceRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			
			   		 }

		           }
				  else
					{
						if(previousBillDate.compareTo(commonServiceRateMaster1.getCsValidTo())<=0)
						{
							if((currentBillDate.compareTo(commonServiceRateMaster1.getCsValidFrom())>= 0) && (commonServiceRateMaster1.getCsValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(commonServiceRateMaster1.getCsValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =commonServiceRateMaster1.getCsValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(commonServiceRateMaster1.getCsValidTo());
		 							  lastUpdatedDate =commonServiceRateMaster1.getCsValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(commonServiceRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServiceRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
					   		if(commonServiceRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServiceRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(commonServiceRateMaster1.getCsValidFrom())>=0 && currentBillDate.compareTo(commonServiceRateMaster1.getCsValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(commonServiceRateMaster1.getCsValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(commonServiceRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServiceRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   		 }
						   		if(commonServiceRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServiceRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServiceRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	 return totalAmount;
	}

	public String tariffAmountSlab(ELRateMaster elRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue)
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		String consolidatedBill = null;
		
		logger.info("netMonth :::::::::::::::::::::: "+netMonth);
		 
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		   for (ELRateMaster elRateMaster1 : elRateMasterList) 
			{
			  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
			  {

			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill +tariffCalculationService.tariffCalculationMultiSlabString(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
			  }
			  else
				{
					if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
					{
						if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
						{
							
						}
						else
  						{
 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
  							{
 								
  							}
  						}
					}
				}	
			} 
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster1 : elRateMasterList) 
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		logger.info("elRateMaster1.getRateType() ============================ "+elRateMaster1.getRateType());
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabString(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
						{
							if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 							  loopCount = loopCount +1;
								}
					   	
					   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabString(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster1.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		
						   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabString(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  return consolidatedBill;
	}

	public HashMap<Object, Object> interstAmountForMonthWise(ELRateMaster elRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue) 
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		   List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster1 : elRateMasterList) 
			 {
				  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
					{
					    logger.info("previousBillDate ----------------------- "+previousBillDate);
					    logger.info("currentBillDate ----------------------- "+currentBillDate);
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);
						PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
						Period difference = new Period(fromDate, toDate, monthDay);
						float billableMonths = difference.getMonths();
						int daysAfterMonth = difference.getDays();
						Calendar cal = Calendar.getInstance();
						cal.setTime(previousBillDate);
						float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						float netMonth = daysToMonths + billableMonths;
					    logger.info("----------- Number of interest Month are ------------ "+netMonth);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				 			List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
							for (ELRateSlabs elRateSlabs : elRateSlabsList)
							  {
								if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
									totalAmount = totalAmount +((elRateSlabs.getRate()/100) *uomValue*1);
								}
							  }
							
							if(elRateMaster.getMaxRate()!=0)
							{
								  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
						{
							if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 							  loopCount = loopCount +1;
								}
								PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
								Period difference = new Period(startDate, endDate, monthDay);
								float billableMonths = difference.getMonths();
								int daysAfterMonth = difference.getDays();
								Calendar cal = Calendar.getInstance();
								cal.setTime(previousBillDate);
								float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								float netMonth = daysToMonths + billableMonths;
								logger.info("----------- Number of interest Month are ------------ "+netMonth);
					   		 
					   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						 			List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
									for (ELRateSlabs elRateSlabs : elRateSlabsList)
									  {
										if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
											totalAmount = totalAmount +((elRateSlabs.getRate()/100) *uomValue*1);
										}
									  }
									
									if(elRateMaster.getMaxRate()!=0)
									{
										  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster1.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
									Period difference = new Period(startDate, endDate, monthDay);
									float billableMonths = difference.getMonths();
									int daysAfterMonth = difference.getDays();
									Calendar cal = Calendar.getInstance();
									cal.setTime(previousBillDate);
									float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
									float netMonth = daysToMonths + billableMonths;
									    logger.info("----------- Number of interest Month are ------------ "+netMonth);
						   		  
						   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							 			List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
										for (ELRateSlabs elRateSlabs : elRateSlabsList)
										  {
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
												totalAmount = totalAmount +((elRateSlabs.getRate()/100) *uomValue*1);
											}
										  }
										
										if(elRateMaster.getMaxRate()!=0)
										{
											  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}
	
	public HashMap<Object, Object> interstAmount(ELRateMaster elRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue) 
	{

		logger.info("previousBillDate -------------------------- "+previousBillDate);
		logger.info("currentBillDate -------------------------- "+currentBillDate);
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		logger.info(":::::::::::elrateMaster"+elRateMaster.getRateName());
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		   List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster1 : elRateMasterList) 
			 {
				  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);
					    Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("----------- Number of interest days are ------------ "+days);
					    
						PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
						Period difference = new Period(fromDate, toDate, monthDay);
						float billableMonths = difference.getMonths();
						int daysAfterMonth = difference.getDays();
						Calendar cal = Calendar.getInstance();
						cal.setTime(previousBillDate);
						float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						float netMonth = daysToMonths + billableMonths;
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 logger.info(":::::::::::::"+elRateMaster1.getRateType()+"::::::::::"+messageSource.getMessage("rateType.singleSlab",null, locale).trim());
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				 			List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
							for (ELRateSlabs elRateSlabs : elRateSlabsList)
							  {
								if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
									logger.info("(elRateSlabs.getRate()/100) -------------------------- "+(elRateSlabs.getRate()/100));
									logger.info("uomValue-------------------------- "+uomValue);
									logger.info("netMonth-------------------------- "+netMonth);
									logger.info("totalAmount-------------------------- "+totalAmount);
									totalAmount = totalAmount +((elRateSlabs.getRate()/100) *uomValue*netMonth);
									logger.info("totalAmount-------------------------- "+totalAmount);
								}
							  }
							
							if(elRateMaster.getMaxRate()!=0)
							{
								  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}else{
			   			logger.info("else block");
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
						{
							if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 							  loopCount = loopCount +1;
								}
								
								    Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;
								    logger.info("----------- Number of interest days are ------------ "+days);
								    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
									Period difference = new Period(startDate, endDate, monthDay);
									float billableMonths = difference.getMonths();
									int daysAfterMonth = difference.getDays();
									Calendar cal = Calendar.getInstance();
									cal.setTime(previousBillDate);
									float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
									float netMonth = daysToMonths + billableMonths;
					   		 
					   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						 			List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
									for (ELRateSlabs elRateSlabs : elRateSlabsList)
									  {
										if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
											totalAmount = totalAmount +((elRateSlabs.getRate()/100) *uomValue*netMonth);
										}
									  }
									
									if(elRateMaster.getMaxRate()!=0)
									{
										  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else{
										  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster1.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							    Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;
									    logger.info("----------- Number of interest days are ------------ "+days);
									    PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(startDate, endDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(previousBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths + billableMonths;
						   		  
						   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							 			List<ELRateSlabs> elRateSlabsList = tariffCalculationService.getELRateSlabListByRateMasterId(elRateMaster.getElrmid());  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
										for (ELRateSlabs elRateSlabs : elRateSlabsList)
										  {
											if(elRateSlabs.getSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												logger.info(" The rate type is :::::::::::::::::::: "+elRateSlabs.getSlabRateType()+"elRateSlabs.getRate() "+elRateSlabs.getRate());
												totalAmount = totalAmount +((elRateSlabs.getRate()/100) *uomValue*netMonth);
											}
										  }
										
										if(elRateMaster.getMaxRate()!=0)
										{
											  totalAmount = (totalAmount > elRateMaster.getMaxRate()) ? elRateMaster.getMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > elRateMaster.getMinRate()) ? totalAmount : elRateMaster.getMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}

	public String tariffAmountWaterSlab(WTRateMaster wtRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue) 
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		String consolidatedBill = null;
		List<Map<String, Object>> dateList = tariffCalculationService.getWaterMinMaxDate(wtRateMaster.getWtTariffId(),wtRateMaster.getWtRateName());
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<WTRateMaster> wtRateMasterList = tariffCalculationService.getWaterRateMasterByIdName(wtRateMaster.getWtTariffId(),wtRateMaster.getWtRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		   for (WTRateMaster wtRateMaster1 : wtRateMasterList) 
			{
			  if((previousBillDate.compareTo(wtRateMaster1.getWtValidFrom()) >=0) && (currentBillDate.compareTo(wtRateMaster1.getWtValidTo())<=0))
			  {
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(wtRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill +tariffCalculationService.tariffCalculationMultiSlabStringWater(wtRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
			  }
			  else
				{
					if(previousBillDate.compareTo(wtRateMaster1.getWtValidTo())<=0)
					{
						if((currentBillDate.compareTo(wtRateMaster1.getWtValidFrom())>= 0) && (wtRateMaster1.getWtValidTo().compareTo(currentBillDate)<=0))
						{
							
						}
						else
  						{
 							if(currentBillDate.compareTo(wtRateMaster1.getWtValidFrom())>=0 && currentBillDate.compareTo(wtRateMaster1.getWtValidTo())<=0)
  							{
 								
  							}
  						}
					}
				}	
			} 
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		   for (WTRateMaster wtRateMaster1 : wtRateMasterList)  
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(wtRateMaster1.getWtValidFrom()) >=0) && (currentBillDate.compareTo(wtRateMaster1.getWtValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(wtRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringWater(wtRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(wtRateMaster1.getWtValidTo())<=0)
						{
							if((currentBillDate.compareTo(wtRateMaster1.getWtValidFrom())>= 0) && (wtRateMaster1.getWtValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(wtRateMaster1.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =wtRateMaster1.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(wtRateMaster1.getWtValidTo());
		 							  lastUpdatedDate =wtRateMaster1.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
					   	
					   		 if(wtRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringWater(wtRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(wtRateMaster1.getWtValidFrom())>=0 && currentBillDate.compareTo(wtRateMaster1.getWtValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(wtRateMaster1.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		
						   		 if(wtRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringWater(wtRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  return consolidatedBill;
	}

	public String tariffAmountGasSlab(GasRateMaster gasRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		String consolidatedBill = null;
		List<Map<String, Object>> dateList = tariffCalculationService.getGasMinMaxDate(gasRateMaster.getGasTariffId(),gasRateMaster.getGasRateName());
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<GasRateMaster> gasRateMasterList = tariffCalculationService.getGasRateMasterByIdName(gasRateMaster.getGasTariffId(),gasRateMaster.getGasRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		   for (GasRateMaster gasRateMaster1 : gasRateMasterList) 
			{
			  if((previousBillDate.compareTo(gasRateMaster1.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0))
			  {
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill +tariffCalculationService.tariffCalculationMultiSlabStringGas(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
			  }
			  else
				{
					if(previousBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
					{
						if((currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>= 0) && (gasRateMaster1.getGasValidTo().compareTo(currentBillDate)<=0))
						{
							
						}
						else
  						{
 							if(currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
  							{
 								
  							}
  						}
					}
				}	
			} 
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		   for (GasRateMaster gasRateMaster1 : gasRateMasterList)  
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(gasRateMaster1.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringGas(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>= 0) && (gasRateMaster1.getGasValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
					   	
					   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringGas(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster1.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		
						   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringGas(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  return consolidatedBill;
	
	}

	public String tariffAmountSlabAvgCount(ELRateMaster elRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue,int avgCount) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths+avgCount;
		String consolidatedBill = null;
		
		logger.info("netMonth :::::::::::::::::::::: "+netMonth);
		 
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		   for (ELRateMaster elRateMaster1 : elRateMasterList) 
			{
			  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
			  {

			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill +tariffCalculationService.tariffCalculationMultiSlabStringAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   		 }
			  }
			  else
				{
					if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
					{
						if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
						{
							
						}
						else
  						{
 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
  							{
 								
  							}
  						}
					}
				}	
			} 
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster1 : elRateMasterList) 
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		logger.info("elRateMaster1.getRateType() ============================ "+elRateMaster1.getRateType());
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
						{
							if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 							  loopCount = loopCount +1;
								}
					   	
					   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster1.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		
						   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = consolidatedBill+ tariffCalculationService.tariffCalculationMultiSlabStringAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  return consolidatedBill;
	
	}

	public HashMap<Object, Object> tariffAmountAvgCount(ELRateMaster elRateMaster, Date previousBillDate,Date currentBillDate, int avgCount,Float uomValue,Float extraParametar,String category) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths+avgCount;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		List<Map<String, Object>> dateList = tariffCalculationService.getMinMaxDate(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<ELRateMaster> elRateMasterList = tariffCalculationService.getRateMasterByIdName(elRateMaster.getElTariffID(),elRateMaster.getRateName());
		  if(billableMonths == 0)
		  {
		  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
		   for (ELRateMaster elRateMaster1 : elRateMasterList) 
			{
			  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
			  {
			   		 logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationSingleSlabAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlabAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlabAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		{
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			   		}
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		}
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
			   		{
			   			logger.info("------------------- MDI calculation -------------------");
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
			   		{
			   			logger.info("------------------- PF Penalty calculation -------------------");
			   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParametar,uomValue);
			   		}
			  }
			  else
				{
					if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
					{
						if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
						{
							
						}
						else
  						{
 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
  							{
 								
  							}
  						}
					}
				}	
			} 
		  }
		  else
		  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (ELRateMaster elRateMaster1 : elRateMasterList) 
			 {
				 // uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(elRateMaster1.getValidFrom()) >=0) && (currentBillDate.compareTo(elRateMaster1.getValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		    
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlabAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlabAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		
			   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlabAvgCount(elRateMaster1, previousBillDate, currentBillDate,uomValue,avgCount);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		 }
			   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		{
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
			   		}
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
			   		{
			   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
			   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }	
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
			   		{
			   			logger.info("------------------- MDI calculation -------------------");
			   		}
			   		
			   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
			   		{
			   			logger.info("------------------- PF Penalty calculation -------------------");
			   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParametar,uomValue);
			   		}

		           }
				  else
					{
						if(previousBillDate.compareTo(elRateMaster1.getValidTo())<=0)
						{
							if((currentBillDate.compareTo(elRateMaster1.getValidFrom())>= 0) && (elRateMaster1.getValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(elRateMaster1.getValidTo());
		 							  lastUpdatedDate =elRateMaster1.getValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlabAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		 }
					   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlabAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   		 }
					   		
					   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlabAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		 }
					   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		{
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
					   		}
					   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
					   		{
					   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
					   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }	
					   		}
					   		
					   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
					   		{
					   			logger.info("------------------- MDI calculation -------------------");
					   		}
					   		
					   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
					   		{
					   			logger.info("------------------- PF Penalty calculation -------------------");
					   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParametar,uomValue);
					   		}

  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(elRateMaster1.getValidFrom())>=0 && currentBillDate.compareTo(elRateMaster1.getValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(elRateMaster1.getValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 consolidatedBill = tariffCalculationService.tariffCalculationSingleSlabAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		 }
						   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.tariffCalculationMultiSlabAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
						   		
						   		 if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.rangeSlab",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Range Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.tariffCalculationRangeSlabAvgCount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue,avgCount);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		 }
						   		if(elRateMaster1.getRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlabTelescopic",null, locale).trim()) && (!elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		{
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab Telescopic  ::::::::::::::::::::");	
						   		}
						   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()))
						   		{
						   			logger.info(":::::::::::::::::::: Calculating disconnection and reconnection charges ::::::::::::::::::::");	
						   			consolidatedBill = tariffCalculationService.drAmount(elRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }	
						   		}
						   		
						   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()))
						   		{
						   			logger.info("------------------- MDI calculation -------------------");
						   		}
						   		
						   		if(elRateMaster1.getRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim()))
						   		{
						   			logger.info("------------------- PF Penalty calculation -------------------");
						   			consolidatedBill = tariffCalculationService.powerFactorPenalty(elRateMaster, previousBillDate, currentBillDate,extraParametar,uomValue);
						   		}

	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  consolidatedBill.put("totalAmount", totalAmount);
	 return consolidatedBill;
	
	}

	
	public HashMap<Object, Object> interstAmountWaterForMonthWise(WTRateMaster waterRateMaster,Date previousBillDate, Date currentBillDate, float taxAmount) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getWaterMinMaxDate(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		   List<WTRateMaster> waterRateMasterList = tariffCalculationService.getWaterRateMasterByIdName(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (WTRateMaster wtRateMaster : waterRateMasterList) 
			 {
				  if((previousBillDate.compareTo(wtRateMaster.getWtValidFrom()) >=0) && (currentBillDate.compareTo(wtRateMaster.getWtValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);
						
						PeriodType monthDay = PeriodType.yearMonthDay()
								.withYearsRemoved();
						Period difference = new Period(fromDate, toDate, monthDay);
						float billableMonths = difference.getMonths();
						int daysAfterMonth = difference.getDays();
						Calendar cal = Calendar.getInstance();
						cal.setTime(previousBillDate);
						float daysToMonths = (float) daysAfterMonth
								/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						float netMonth = daysToMonths + billableMonths;
						
					   /* Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);*/
					    logger.info("----------- Number of interest month are ------------ "+netMonth);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			List<WTRateSlabs> wtRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster.getWtrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
							for (WTRateSlabs wtRateSlabs : wtRateSlabsList)
							  {
								if(wtRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((wtRateSlabs.getWtRate()/100) *taxAmount*netMonth);
									
								}
							  }
							
							if(wtRateMaster.getWtMaxRate()!=0)
							{
								  totalAmount = (totalAmount > wtRateMaster.getWtMaxRate()) ? wtRateMaster.getWtMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > wtRateMaster.getWtMinRate()) ? totalAmount : wtRateMaster.getWtMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(wtRateMaster.getWtValidTo())<=0)
						{
							if((currentBillDate.compareTo(wtRateMaster.getWtValidFrom())>= 0) && (wtRateMaster.getWtValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(wtRateMaster.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =wtRateMaster.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(wtRateMaster.getWtValidTo());
		 							  lastUpdatedDate =wtRateMaster.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
								
								PeriodType monthDay = PeriodType.yearMonthDay()
										.withYearsRemoved();
								Period difference = new Period(startDate, endDate, monthDay);
								float billableMonths = difference.getMonths();
								int daysAfterMonth = difference.getDays();
								Calendar cal = Calendar.getInstance();
								cal.setTime(previousBillDate);
								float daysToMonths = (float) daysAfterMonth
										/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								float netMonth = daysToMonths + billableMonths;
								
								    /*Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;*/
								    logger.info("----------- Number of interest month are ------------ "+netMonth);
					   		 
					   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<WTRateSlabs> wtRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster.getWtrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
									for (WTRateSlabs wtRateSlabs : wtRateSlabsList)
									  {
										if(wtRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((wtRateSlabs.getWtRate()/100) *taxAmount*netMonth);
										}
									  }
									
									if(wtRateMaster.getWtMaxRate()!=0)
									{
										  totalAmount = (totalAmount > wtRateMaster.getWtMaxRate()) ? wtRateMaster.getWtMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > wtRateMaster.getWtMinRate()) ? totalAmount : wtRateMaster.getWtMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(wtRateMaster.getWtValidFrom())>=0 && currentBillDate.compareTo(wtRateMaster.getWtValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(wtRateMaster.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
									Period difference = new Period(startDate, endDate, monthDay);
									float billableMonths = difference.getMonths();
									int daysAfterMonth = difference.getDays();
									Calendar cal = Calendar.getInstance();
									cal.setTime(previousBillDate);
									float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
									float netMonth = daysToMonths + billableMonths;
									
	  	 							 /*
	  	 							    Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;*/
									    logger.info("----------- Number of interest month are ------------ "+netMonth);
						   		  
						   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<WTRateSlabs> wtRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster.getWtrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
										for (WTRateSlabs wtRateSlabs : wtRateSlabsList)
										  {
											if(wtRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((wtRateSlabs.getWtRate()/100) *taxAmount*netMonth);
											}
										  }
										
										if(wtRateMaster.getWtMaxRate()!=0)
										{
											  totalAmount = (totalAmount > wtRateMaster.getWtMaxRate()) ? wtRateMaster.getWtMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > wtRateMaster.getWtMinRate()) ? totalAmount : wtRateMaster.getWtMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}

	public HashMap<Object, Object> interstAmountWater(WTRateMaster waterRateMaster,Date previousBillDate, Date currentBillDate, float taxAmount) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getWaterMinMaxDate(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		   List<WTRateMaster> waterRateMasterList = tariffCalculationService.getWaterRateMasterByIdName(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());
		   logger.info("waterRateMasterList ---------- "+waterRateMasterList.size());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (WTRateMaster wtRateMaster : waterRateMasterList) 
			 {
				  if((previousBillDate.compareTo(wtRateMaster.getWtValidFrom()) >=0) && (currentBillDate.compareTo(wtRateMaster.getWtValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);
                       
					    Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);
					    logger.info("----------- Number of interest days are ------------ "+days);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			List<WTRateSlabs> wtRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster.getWtrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
				   			
							for (WTRateSlabs wtRateSlabs : wtRateSlabsList)
							  {
								if(wtRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((wtRateSlabs.getWtRate()/100) * rateForInterest*taxAmount*days);
								}
							  }
							
							if(wtRateMaster.getWtMaxRate()!=0)
							{
								  totalAmount = (totalAmount > wtRateMaster.getWtMaxRate()) ? wtRateMaster.getWtMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > wtRateMaster.getWtMinRate()) ? totalAmount : wtRateMaster.getWtMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(wtRateMaster.getWtValidTo())<=0)
						{
							if((currentBillDate.compareTo(wtRateMaster.getWtValidFrom())>= 0) && (wtRateMaster.getWtValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(wtRateMaster.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =wtRateMaster.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(wtRateMaster.getWtValidTo());
		 							  lastUpdatedDate =wtRateMaster.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
								
								    Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;
								    logger.info("----------- Number of interest days are ------------ "+days);
					   		 
					   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<WTRateSlabs> wtRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster.getWtrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
									for (WTRateSlabs wtRateSlabs : wtRateSlabsList)
									  {
										if(wtRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((wtRateSlabs.getWtRate()/100) * rateForInterest*taxAmount*days);
										}
									  }
									
									if(wtRateMaster.getWtMaxRate()!=0)
									{
										  totalAmount = (totalAmount > wtRateMaster.getWtMaxRate()) ? wtRateMaster.getWtMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > wtRateMaster.getWtMinRate()) ? totalAmount : wtRateMaster.getWtMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(wtRateMaster.getWtValidFrom())>=0 && currentBillDate.compareTo(wtRateMaster.getWtValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(wtRateMaster.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							    Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;
									    logger.info("----------- Number of interest days are ------------ "+days);
						   		  
						   		if(wtRateMaster.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !wtRateMaster.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<WTRateSlabs> wtRateSlabsList = entityManager.createNamedQuery("WTRateSlabs.getWTRateSlabListByRateMasterId",WTRateSlabs.class).setParameter("wtrmid", wtRateMaster.getWtrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
										for (WTRateSlabs wtRateSlabs : wtRateSlabsList)
										  {
											if(wtRateSlabs.getWtSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((wtRateSlabs.getWtRate()/100) * rateForInterest*taxAmount*days);
											}
										  }
										logger.info("totalAmount ----------- "+totalAmount);
										if(wtRateMaster.getWtMaxRate()!=0)
										{
											  totalAmount = (totalAmount > wtRateMaster.getWtMaxRate()) ? wtRateMaster.getWtMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > wtRateMaster.getWtMinRate()) ? totalAmount : wtRateMaster.getWtMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}

	
	public HashMap<Object, Object> interstAmountGasForMonthWise(GasRateMaster gasRateMaster1, Date previousBillDate, Date currentBillDate, float taxAmount) 
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getGasMinMaxDate(gasRateMaster1.getGasTariffId(),gasRateMaster1.getGasRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<GasRateMaster> gasRateMasterList = tariffCalculationService.getGasRateMasterByIdName(gasRateMaster1.getGasTariffId(),gasRateMaster1.getGasRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (GasRateMaster gasRateMaster : gasRateMasterList) 
			 {
				  if((previousBillDate.compareTo(gasRateMaster.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster.getGasValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);
						
							PeriodType monthDay = PeriodType.yearMonthDay()
									.withYearsRemoved();
							Period difference = new Period(fromDate, toDate, monthDay);
							float billableMonths = difference.getMonths();
							int daysAfterMonth = difference.getDays();
							Calendar cal = Calendar.getInstance();
							cal.setTime(previousBillDate);
							float daysToMonths = (float) daysAfterMonth
									/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							float netMonth = daysToMonths + billableMonths;
						
/*
					    Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);*/
					    logger.info("----------- Number of interest month are ------------ "+netMonth);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlabs.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmid", gasRateMaster.getGasrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
							for (GasRateSlab gasRateSlabs : gasRateSlabsList)
							  {
								if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((gasRateSlabs.getGasRate()/100) *taxAmount*netMonth);
									
								}
							  }
							
							if(gasRateMaster.getGasMaxRate()!=0)
							{
								  totalAmount = (totalAmount > gasRateMaster.getGasMaxRate()) ? gasRateMaster.getGasMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > gasRateMaster.getGasMinRate()) ? totalAmount : gasRateMaster.getGasMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster.getGasValidFrom())>= 0) && (gasRateMaster.getGasValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
								
								PeriodType monthDay = PeriodType.yearMonthDay()
										.withYearsRemoved();
								Period difference = new Period(startDate, endDate, monthDay);
								float billableMonths = difference.getMonths();
								int daysAfterMonth = difference.getDays();
								Calendar cal = Calendar.getInstance();
								cal.setTime(previousBillDate);
								float daysToMonths = (float) daysAfterMonth
										/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								float netMonth = daysToMonths + billableMonths;
								
								  /*  Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;*/
								    logger.info("----------- Number of interest months are ------------ "+netMonth);
					   		 
							if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlabs.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmid", gasRateMaster.getGasrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
									for (GasRateSlab gasRateSlabs : gasRateSlabsList)
									  {
										if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((gasRateSlabs.getGasRate()/100) *taxAmount*netMonth);
										}
									  }
									
									if(gasRateMaster.getGasMaxRate()!=0)
									{
										  totalAmount = (totalAmount > gasRateMaster.getGasMaxRate()) ? gasRateMaster.getGasMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > gasRateMaster.getGasMinRate()) ? totalAmount : gasRateMaster.getGasMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							PeriodType monthDay = PeriodType.yearMonthDay()
	  										.withYearsRemoved();
	  								Period difference = new Period(startDate, endDate, monthDay);
	  								float billableMonths = difference.getMonths();
	  								int daysAfterMonth = difference.getDays();
	  								Calendar cal = Calendar.getInstance();
	  								cal.setTime(previousBillDate);
	  								float daysToMonths = (float) daysAfterMonth
	  										/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	  								float netMonth = daysToMonths + billableMonths; 
	  	 							   /* Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;*/
									    logger.info("----------- Number of interest month are ------------ "+netMonth);
						   		  
									 if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		     {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlabs.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmid", gasRateMaster.getGasrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
										for (GasRateSlab gasRateSlabs : gasRateSlabsList)
										  {
											if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((gasRateSlabs.getGasRate()/100) *taxAmount*netMonth);
											}
										  }
										
										if(gasRateMaster.getGasMaxRate()!=0)
										{
											  totalAmount = (totalAmount > gasRateMaster.getGasMaxRate()) ? gasRateMaster.getGasMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > gasRateMaster.getGasMinRate()) ? totalAmount : gasRateMaster.getGasMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}
	
	public HashMap<Object, Object> interstAmountGas(GasRateMaster gasRateMaster1, Date previousBillDate, Date currentBillDate, float taxAmount) 
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getGasMinMaxDate(gasRateMaster1.getGasTariffId(),gasRateMaster1.getGasRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<GasRateMaster> gasRateMasterList = tariffCalculationService.getGasRateMasterByIdName(gasRateMaster1.getGasTariffId(),gasRateMaster1.getGasRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (GasRateMaster gasRateMaster : gasRateMasterList) 
			 {
				  if((previousBillDate.compareTo(gasRateMaster.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster.getGasValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);

					    Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);
					    logger.info("----------- Number of interest days are ------------ "+days);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlabs.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmid", gasRateMaster.getGasrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
							for (GasRateSlab gasRateSlabs : gasRateSlabsList)
							  {
								if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((gasRateSlabs.getGasRate()/100) * rateForInterest*taxAmount*days);
									
								}
							  }
							
							if(gasRateMaster.getGasMaxRate()!=0)
							{
								  totalAmount = (totalAmount > gasRateMaster.getGasMaxRate()) ? gasRateMaster.getGasMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > gasRateMaster.getGasMinRate()) ? totalAmount : gasRateMaster.getGasMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster.getGasValidFrom())>= 0) && (gasRateMaster.getGasValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
								
								    Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;
								    logger.info("----------- Number of interest days are ------------ "+days);
					   		 
							if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlabs.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmid", gasRateMaster.getGasrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
									for (GasRateSlab gasRateSlabs : gasRateSlabsList)
									  {
										if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((gasRateSlabs.getGasRate()/100) * rateForInterest*taxAmount*days);
										}
									  }
									
									if(gasRateMaster.getGasMaxRate()!=0)
									{
										  totalAmount = (totalAmount > gasRateMaster.getGasMaxRate()) ? gasRateMaster.getGasMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > gasRateMaster.getGasMinRate()) ? totalAmount : gasRateMaster.getGasMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							    Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;
									    logger.info("----------- Number of interest days are ------------ "+days);
						   		  
									 if(gasRateMaster.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		     {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<GasRateSlab> gasRateSlabsList = entityManager.createNamedQuery("GasRateSlabs.getGasRateSlabListByRateMasterId",GasRateSlab.class).setParameter("gasrmid", gasRateMaster.getGasrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();  //entityManager.createNamedQuery("ELRateSlabs.getELRateSlabListByRateMasterId",ELRateSlabs.class).setParameter("elrmId", elRateMaster.getElrmid()).getResultList();
										for (GasRateSlab gasRateSlabs : gasRateSlabsList)
										  {
											if(gasRateSlabs.getGasSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((gasRateSlabs.getGasRate()/100) * rateForInterest*taxAmount*days);
											}
										  }
										
										if(gasRateMaster.getGasMaxRate()!=0)
										{
											  totalAmount = (totalAmount > gasRateMaster.getGasMaxRate()) ? gasRateMaster.getGasMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > gasRateMaster.getGasMinRate()) ? totalAmount : gasRateMaster.getGasMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}
	
	public HashMap<Object, Object> interstAmountSolidWasteForMonthWise(SolidWasteRateMaster solidWasteRateMaster, Date previousBillDate, Date currentBillDate, float taxAmount) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getSolidWasteMinMaxDate(solidWasteRateMaster.getSolidWasteTariffId(),solidWasteRateMaster.getSolidWasteRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<SolidWasteRateMaster> solidWasteRateMasterList = tariffCalculationService.getSolidWasteRateMasterByIdName(solidWasteRateMaster.getSolidWasteTariffId(),solidWasteRateMaster.getSolidWasteRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (SolidWasteRateMaster solidWasteRateMaster1 : solidWasteRateMasterList) 
			 {
				  if((previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom()) >=0) && (currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);

						PeriodType monthDay = PeriodType.yearMonthDay()
								.withYearsRemoved();
						Period difference = new Period(fromDate, toDate, monthDay);
						float billableMonths = difference.getMonths();
						int daysAfterMonth = difference.getDays();
						Calendar cal = Calendar.getInstance();
						cal.setTime(previousBillDate);
						float daysToMonths = (float) daysAfterMonth
								/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						float netMonth = daysToMonths + billableMonths;
					    /*Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);*/
					    logger.info("----------- Number of interest month are ------------ "+netMonth);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
							for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
							  {
								if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((solidWasteRateSlabs.getSolidWasteRate()/100) *taxAmount*netMonth);
									
								}
							  }
							
							if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
							{
								  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
						{
							if((currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>= 0) && (solidWasteRateMaster1.getSolidWasteValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 							  loopCount = loopCount +1;
								}
								

								PeriodType monthDay = PeriodType.yearMonthDay()
										.withYearsRemoved();
								Period difference = new Period(startDate, endDate, monthDay);
								float billableMonths = difference.getMonths();
								int daysAfterMonth = difference.getDays();
								Calendar cal = Calendar.getInstance();
								cal.setTime(previousBillDate);
								float daysToMonths = (float) daysAfterMonth
										/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								float netMonth = daysToMonths + billableMonths;
/*								    Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;*/
								    logger.info("----------- Number of interest month are ------------ "+netMonth);
					   		 
							if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
									for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
									  {
										if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((solidWasteRateSlabs.getSolidWasteRate()/100) *taxAmount*netMonth);
										}
									  }
									
									if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
									{
										  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>=0 && currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);

	 								PeriodType monthDay = PeriodType.yearMonthDay()
	 										.withYearsRemoved();
	 								Period difference = new Period(startDate, endDate, monthDay);
	 								float billableMonths = difference.getMonths();
	 								int daysAfterMonth = difference.getDays();
	 								Calendar cal = Calendar.getInstance();
	 								cal.setTime(previousBillDate);
	 								float daysToMonths = (float) daysAfterMonth
	 										/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	 								float netMonth = daysToMonths + billableMonths;
	  	 							    /*Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;*/
									    logger.info("----------- Number of interest month are ------------ "+netMonth);
						   		  
									 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		     {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
										for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
										  {
											if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((solidWasteRateSlabs.getSolidWasteRate()/100) *taxAmount*netMonth);
											}
										  }
										
										if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
										{
											  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	
	}
	
	
	
	
	
	
	
	

	public HashMap<Object, Object> interstAmountSolidWaste(SolidWasteRateMaster solidWasteRateMaster, Date previousBillDate, Date currentBillDate, float taxAmount) {

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getSolidWasteMinMaxDate(solidWasteRateMaster.getSolidWasteTariffId(),solidWasteRateMaster.getSolidWasteRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<SolidWasteRateMaster> solidWasteRateMasterList = tariffCalculationService.getSolidWasteRateMasterByIdName(solidWasteRateMaster.getSolidWasteTariffId(),solidWasteRateMaster.getSolidWasteRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (SolidWasteRateMaster solidWasteRateMaster1 : solidWasteRateMasterList) 
			 {
				  if((previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom()) >=0) && (currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);

					    Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);
					    logger.info("----------- Number of interest days are ------------ "+days);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
							for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
							  {
								if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((solidWasteRateSlabs.getSolidWasteRate()/100) * rateForInterest*taxAmount*days);
									
								}
							  }
							
							if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
							{
								  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
						{
							if((currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>= 0) && (solidWasteRateMaster1.getSolidWasteValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidTo());
		 							  lastUpdatedDate =solidWasteRateMaster1.getSolidWasteValidTo();
		 							  loopCount = loopCount +1;
								}
								
								    Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;
								    logger.info("----------- Number of interest days are ------------ "+days);
					   		 
							if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
									for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
									  {
										if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((solidWasteRateSlabs.getSolidWasteRate()/100) * rateForInterest*taxAmount*days);
										}
									  }
									
									if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
									{
										  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidFrom())>=0 && currentBillDate.compareTo(solidWasteRateMaster1.getSolidWasteValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(solidWasteRateMaster1.getSolidWasteValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							    Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;
									    logger.info("----------- Number of interest days are ------------ "+days);
						   		  
									 if(solidWasteRateMaster1.getSolidWasteRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !solidWasteRateMaster1.getSolidWasteRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		     {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<SolidWasteRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", solidWasteRateMaster1.getSolidWasteRmId()).getResultList();
										for (SolidWasteRateSlab solidWasteRateSlabs : solidWasteRateSlabsList)
										  {
											if(solidWasteRateSlabs.getSolidWasteSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((solidWasteRateSlabs.getSolidWasteRate()/100) * rateForInterest*taxAmount*days);
											}
										  }
										
										if(solidWasteRateMaster1.getSolidWasteMaxRate()!=0)
										{
											  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMaxRate()) ? solidWasteRateMaster1.getSolidWasteMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > solidWasteRateMaster1.getSolidWasteMinRate()) ? totalAmount : solidWasteRateMaster1.getSolidWasteMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	
	}

	public HashMap<String, Object> tariffAmountAvgCountWater(WTRateMaster waterRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount)
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String wtSlabString=null;
		HashMap<String, Object> consolidatedBill = new LinkedHashMap<>();
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		logger.info("netMonth :::::::::::::::::::::: "+netMonth);
		
		List<Map<String, Object>> dateList = tariffCalculationService.getWaterMinMaxDate(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());

		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
		 
      }
	 else
	  {
		  List<WTRateMaster> gasRateMasterList = tariffCalculationService.getWaterRateMasterByIdName(waterRateMaster.getWtTariffId(),waterRateMaster.getWtRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
			  for (WTRateMaster waterRateMaster1 : gasRateMasterList) 
				{
				  if((previousBillDate.compareTo(waterRateMaster1.getWtValidFrom()) >=0) && (currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0))
				  {
				   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
				   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<String, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("totalAmount"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   				if(map.getKey().equals("wtSlabString"))
				   				{
				   					wtSlabString = wtSlabString +map.getValue();
				   				}
				   		    }
				   		 }
					   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			for (Entry<String, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("totalAmount"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   		 }
				  }
				  else
					{
						if(previousBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
						{
							if((currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>= 0) && (waterRateMaster1.getWtValidTo().compareTo(currentBillDate)<=0))
							{

								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<String, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("totalAmount"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("wtSlabString"))
					   				{
					   					wtSlabString = wtSlabString +map.getValue();
					   				}
					   		    }
					   		 }
					   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
							}
							else
							{
								if(currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>=0 && currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
								{

	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(waterRateMaster1.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("wtSlabString"))
						   				{
						   					wtSlabString = wtSlabString +map.getValue();
						   				}
						   		    }
						   		 }
						   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
							   			for (Entry<String, Object> map : consolidatedBill.entrySet())
							   		    {
							   				if(map.getKey().equals("totalAmount"))
							   				{
							   					totalAmount = totalAmount +(Float)map.getValue();
							   				}
							   		    }
							   		 }
								}
							}
						}
					}	
				} 
		  }
		  else
	      {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (WTRateMaster waterRateMaster1 : gasRateMasterList) 
			 {
				 // uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(waterRateMaster1.getWtValidFrom()) >=0) && (currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<String, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("totalAmount"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("wtSlabString"))
			   				{
			   					wtSlabString = wtSlabString +map.getValue();
			   				}
			   		    }
			   			
			   		 }
				   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<String, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("totalAmount"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   		    }
				   		 }

		           }
				  else
					{
						if(previousBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
						{
							if((currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>= 0) && (waterRateMaster1.getWtValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(waterRateMaster1.getWtValidTo());
		 							  lastUpdatedDate =waterRateMaster1.getWtValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<String, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("totalAmount"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("wtSlabString"))
					   				{
					   					wtSlabString = wtSlabString +map.getValue();
					   				}
					   		    }
					   		 }
					   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(waterRateMaster1.getWtValidFrom())>=0 && currentBillDate.compareTo(waterRateMaster1.getWtValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(waterRateMaster1.getWtValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.waterTariffCalculationMultiSlab(waterRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<String, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("totalAmount"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("wtSlabString"))
						   				{
						   					wtSlabString = wtSlabString +map.getValue();
						   				}
						   		    }
						   		 }
						   		if(waterRateMaster1.getWtRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !waterRateMaster1.getWtRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
							   		 {
							   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			consolidatedBill = tariffCalculationService.waterTariffCalculationSingleSlab(waterRateMaster1, previousBillDate, currentBillDate,uomValue);
								   			for (Entry<String, Object> map : consolidatedBill.entrySet())
								   		    {
								   				if(map.getKey().equals("totalAmount"))
								   				{
								   					totalAmount = totalAmount +(Float)map.getValue();
								   				}
								   				
								   		    }
							   			
							   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  
	  consolidatedBill.put("wtSlabString", wtSlabString);
	  consolidatedBill.put("total", totalAmount);
	  return consolidatedBill;
	}

	public HashMap<Object, Object> tariffAmountAvgCountGas(GasRateMaster gasRateMaster, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount)
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		String gaString=null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		List<Map<String, Object>> dateList = tariffCalculationService.getGasMinMaxDate(gasRateMaster.getGasTariffId(),gasRateMaster.getGasRateName());

		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	 else
	  {
		  List<GasRateMaster> gasRateMasterList = tariffCalculationService.getGasRateMasterByIdName(gasRateMaster.getGasTariffId(),gasRateMaster.getGasRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
			  for(GasRateMaster gasRateMaster1 : gasRateMasterList)  
				{
				  if((previousBillDate.compareTo(gasRateMaster1.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0))
				  {
				   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
				   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("total"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   				if(map.getKey().equals("gaString"))
				   				{
				   					gaString = gaString +(String)map.getValue();
				   				}
				   		    }
				   		 }
				   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
				   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
				   		    {
				   				if(map.getKey().equals("total"))
				   				{
				   					totalAmount = totalAmount +(Float)map.getValue();
				   				}
				   		    }
				   		 }
				  }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>= 0) && (gasRateMaster1.getGasValidTo().compareTo(currentBillDate)<=0))
							{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("gaString"))
					   				{
					   					gaString = gaString +(String)map.getValue();
					   				}
					   		    }
					   		 }
					   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   		 }
							}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster1.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("gaString"))
						   				{
						   					gaString = gaString +(String)map.getValue();
						   				}
						   		    }
						   		 }
						   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
	  							}
	  						}
						}
					}	
				} 
		  }
	else
	  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (GasRateMaster gasRateMaster1 : gasRateMasterList) 
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(gasRateMaster1.getGasValidFrom()) >=0) && (currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   				if(map.getKey().equals("gaString"))
			   				{
			   					gaString = gaString +(String)map.getValue();
			   				}
			   		    }
			   		 }
			   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
			   		    {
			   				if(map.getKey().equals("total"))
			   				{
			   					totalAmount = totalAmount +(Float)map.getValue();
			   				}
			   		    }
			   			
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
						{
							if((currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>= 0) && (gasRateMaster1.getGasValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(gasRateMaster1.getGasValidTo());
		 							  lastUpdatedDate =gasRateMaster1.getGasValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   				if(map.getKey().equals("gaString"))
					   				{
					   					gaString = gaString +(String)map.getValue();
					   				}
					   		    }
					   		 }
					   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
					   		    {
					   				if(map.getKey().equals("total"))
					   				{
					   					totalAmount = totalAmount +(Float)map.getValue();
					   				}
					   		    }
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(gasRateMaster1.getGasValidFrom())>=0 && currentBillDate.compareTo(gasRateMaster1.getGasValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(gasRateMaster1.getGasValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationMultiSlab(gasRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   				if(map.getKey().equals("gaString"))
						   				{
						   					gaString = gaString +(String)map.getValue();
						   				}
						   		    }
						   		 }
						   		if(gasRateMaster1.getGasRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !gasRateMaster1.getGasRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			consolidatedBill = tariffCalculationService.gasTariffCalculationSingleSlab(gasRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			for (Entry<Object, Object> map : consolidatedBill.entrySet())
						   		    {
						   				if(map.getKey().equals("total"))
						   				{
						   					totalAmount = totalAmount +(Float)map.getValue();
						   				}
						   		    }
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	  HashMap<Object, Object> consolidatedBill1 = new LinkedHashMap<>();
	  consolidatedBill1.put("total", totalAmount);
	  return consolidatedBill1;
	}
	
	
	public HashMap<Object, Object> interstAmountCommonServiceForMonthWise(CommonServicesRateMaster commMaster,Date previousBillDate, Date currentBillDate, float taxAmount)
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getCommonServiceMinMaxDate(commMaster.getCsTariffId(),commMaster.getCsRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<CommonServicesRateMaster> commonServicesRateMasters = tariffCalculationService.getCommonServiceMasterByIdName(commMaster.getCsTariffId(),commMaster.getCsRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (CommonServicesRateMaster commonServicesRateMaster : commonServicesRateMasters) 
			 {
				  if((previousBillDate.compareTo(commonServicesRateMaster.getCsValidFrom()) >=0) && (currentBillDate.compareTo(commonServicesRateMaster.getCsValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);

						PeriodType monthDay = PeriodType.yearMonthDay()
								.withYearsRemoved();
						Period difference = new Period(fromDate, toDate, monthDay);
						float billableMonths = difference.getMonths();
						int daysAfterMonth = difference.getDays();
						Calendar cal = Calendar.getInstance();
						cal.setTime(previousBillDate);
						float daysToMonths = (float) daysAfterMonth
								/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						float netMonth = daysToMonths + billableMonths;
					   /* Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);*/
					    logger.info("----------- Number of interest month are ------------ "+netMonth);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   		//	List<CsRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", commonServicesRateMaster.getCsRmId()).getResultList();
				   			List<CommonServicesRateSlab> commonServicesRateSlabs = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServicesRateMaster.getCsRmId()).getResultList();
							for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
							  {
								if(commonServicesRateSlab.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((commonServicesRateSlab.getCsRate()/100) *taxAmount*netMonth);
									
								}
							  }
							
							if(commonServicesRateMaster.getCsMaxRate()!=0)
							{
								  totalAmount = (totalAmount > commonServicesRateMaster.getCsMaxRate()) ? commonServicesRateMaster.getCsMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > commonServicesRateMaster.getCsMinRate()) ? totalAmount : commonServicesRateMaster.getCsMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(commonServicesRateMaster.getCsValidTo())<=0)
						{
							if((currentBillDate.compareTo(commonServicesRateMaster.getCsValidFrom())>= 0) && (commonServicesRateMaster.getCsValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(commonServicesRateMaster.getCsValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =commonServicesRateMaster.getCsValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(commonServicesRateMaster.getCsValidTo());
		 							  lastUpdatedDate =commonServicesRateMaster.getCsValidTo();
		 							  loopCount = loopCount +1;
								}
								
								PeriodType monthDay = PeriodType.yearMonthDay()
										.withYearsRemoved();
								Period difference = new Period(startDate, endDate, monthDay);
								float billableMonths = difference.getMonths();
								int daysAfterMonth = difference.getDays();
								Calendar cal = Calendar.getInstance();
								cal.setTime(previousBillDate);
								float daysToMonths = (float) daysAfterMonth
										/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								float netMonth = daysToMonths + billableMonths;
								 /*   Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;*/
								    logger.info("----------- Number of interest month are ------------ "+netMonth);
					   		 
							if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<CommonServicesRateSlab> commonServicesRateSlabs = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServicesRateMaster.getCsRmId()).getResultList();
									for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
									  {
										if(commonServicesRateSlab.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((commonServicesRateSlab.getCsRate()/100) *taxAmount*netMonth);
										}
									  }
									
									if(commonServicesRateMaster.getCsMaxRate()!=0)
									{
										  totalAmount = (totalAmount > commonServicesRateMaster.getCsMaxRate()) ? commonServicesRateMaster.getCsMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > commonServicesRateMaster.getCsMinRate()) ? totalAmount : commonServicesRateMaster.getCsMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(commonServicesRateMaster.getCsValidFrom())>=0 && currentBillDate.compareTo(commonServicesRateMaster.getCsValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(commonServicesRateMaster.getCsValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							PeriodType monthDay = PeriodType.yearMonthDay()
	  	 									.withYearsRemoved();
	  	 							Period difference = new Period(startDate, endDate , monthDay);
	  	 							float billableMonths = difference.getMonths();
	  	 							int daysAfterMonth = difference.getDays();
	  	 							Calendar cal = Calendar.getInstance();
	  	 							cal.setTime(previousBillDate);
	  	 							float daysToMonths = (float) daysAfterMonth
	  	 									/ cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	  	 							float netMonth = daysToMonths + billableMonths;
	  	 							  /*  Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;*/
									    logger.info("----------- Number of interest days are ------------ "+netMonth);
						   		  
									 if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		     {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<CommonServicesRateSlab> commonServicesRateSlabs = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServicesRateMaster.getCsRmId()).getResultList();
										for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
										  {
											if(commonServicesRateSlab.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((commonServicesRateSlab.getCsRate()/100) * taxAmount*netMonth);
											}
										  }
										
										if(commonServicesRateMaster.getCsMaxRate()!=0)
										{
											  totalAmount = (totalAmount > commonServicesRateMaster.getCsMaxRate()) ? commonServicesRateMaster.getCsMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > commonServicesRateMaster.getCsMinRate()) ? totalAmount : commonServicesRateMaster.getCsMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}

	public HashMap<Object, Object> interstAmountCommonService(CommonServicesRateMaster commMaster,Date previousBillDate, Date currentBillDate, float taxAmount)
	{
		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
	    
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float totalAmount = 0;
		
		List<Map<String, Object>> dateList = tariffCalculationService.getCommonServiceMinMaxDate(commMaster.getCsTariffId(),commMaster.getCsRateName());
		
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	  else
	  {
		  List<CommonServicesRateMaster> commonServicesRateMasters = tariffCalculationService.getCommonServiceMasterByIdName(commMaster.getCsTariffId(),commMaster.getCsRateName());
		   logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (CommonServicesRateMaster commonServicesRateMaster : commonServicesRateMasters) 
			 {
				  if((previousBillDate.compareTo(commonServicesRateMaster.getCsValidFrom()) >=0) && (currentBillDate.compareTo(commonServicesRateMaster.getCsValidTo())<=0))
					{
						LocalDate fromDate = new LocalDate(previousBillDate);
						LocalDate toDate = new LocalDate(currentBillDate);

					    Days d = Days.daysBetween(fromDate,toDate);
					    int days = d.getDays();
					    int year = toDate.getYear();
					    LocalDate ld = new LocalDate(year,1,1);
					    int daysInYear = Days.daysBetween(ld,ld.plusYears(1)).getDays();
					    float rateForInterest = (float)12/daysInYear;
					    logger.info("------- daysInYear "+ daysInYear);
					    logger.info("----------- Number of interest days are ------------ "+days);
					    
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
				   		logger.info(":::::::::::::::::::: tariff calculataion  single tariff ::::::::::::::::::::");
				   		if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   		//	List<CsRateSlab> solidWasteRateSlabsList = entityManager.createNamedQuery("SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId",SolidWasteRateSlab.class).setParameter("solidWasteRmId", commonServicesRateMaster.getCsRmId()).getResultList();
				   			List<CommonServicesRateSlab> commonServicesRateSlabs = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServicesRateMaster.getCsRmId()).getResultList();
							for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
							  {
								if(commonServicesRateSlab.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
								{
									totalAmount = totalAmount +((commonServicesRateSlab.getCsRate()/100) * rateForInterest*taxAmount*days);
									
								}
							  }
							
							if(commonServicesRateMaster.getCsMaxRate()!=0)
							{
								  totalAmount = (totalAmount > commonServicesRateMaster.getCsMaxRate()) ? commonServicesRateMaster.getCsMaxRate() : totalAmount;
								  consolidatedBill.put("total", totalAmount);
							}
							else
							{
								  totalAmount = (totalAmount > commonServicesRateMaster.getCsMinRate()) ? totalAmount : commonServicesRateMaster.getCsMinRate();
								  consolidatedBill.put("total", totalAmount);
							}
				   		 }
			   		}
		           }
				  else
					{
						if(previousBillDate.compareTo(commonServicesRateMaster.getCsValidTo())<=0)
						{
							if((currentBillDate.compareTo(commonServicesRateMaster.getCsValidFrom())>= 0) && (commonServicesRateMaster.getCsValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(commonServicesRateMaster.getCsValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =commonServicesRateMaster.getCsValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(commonServicesRateMaster.getCsValidTo());
		 							  lastUpdatedDate =commonServicesRateMaster.getCsValidTo();
		 							  loopCount = loopCount +1;
								}
								
								    Days d = Days.daysBetween(startDate,endDate);
								    int days = d.getDays();
								    
								    int year = endDate.getYear();
								    logger.info("-------- year -------- "+year);
								    LocalDate ld = new LocalDate(year,1,1);
								    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
								    float rateForInterest = (float)12/daysInYear;
								    logger.info("----------- Number of interest days are ------------ "+days);
					   		 
							if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");

						   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			List<CommonServicesRateSlab> commonServicesRateSlabs = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServicesRateMaster.getCsRmId()).getResultList();
									for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
									  {
										if(commonServicesRateSlab.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
										{
											totalAmount = totalAmount +((commonServicesRateSlab.getCsRate()/100) * rateForInterest*taxAmount*days);
										}
									  }
									
									if(commonServicesRateMaster.getCsMaxRate()!=0)
									{
										  totalAmount = (totalAmount > commonServicesRateMaster.getCsMaxRate()) ? commonServicesRateMaster.getCsMaxRate() : totalAmount;
										  consolidatedBill.put("total", totalAmount);
									}
									else
									{
										  totalAmount = (totalAmount > commonServicesRateMaster.getCsMinRate()) ? totalAmount : commonServicesRateMaster.getCsMinRate();
										  consolidatedBill.put("total", totalAmount);
									}
						   		 
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(commonServicesRateMaster.getCsValidFrom())>=0 && currentBillDate.compareTo(commonServicesRateMaster.getCsValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(commonServicesRateMaster.getCsValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
	  	 							 
	  	 							    Days d = Days.daysBetween(startDate,endDate);
									    int days = d.getDays();
									    int year = endDate.getYear();
									    logger.info("-------- year -------- "+year);
									    LocalDate ld = new LocalDate(year,1,1);
									    int daysInYear =Days.daysBetween(ld,ld.plusYears(1)).getDays();
									    float rateForInterest = (float)12/daysInYear;
									    logger.info("----------- Number of interest days are ------------ "+days);
						   		  
									 if(commonServicesRateMaster.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		     {
							   			logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
							   			List<CommonServicesRateSlab> commonServicesRateSlabs = entityManager.createNamedQuery("CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId",CommonServicesRateSlab.class).setParameter("csRmId", commonServicesRateMaster.getCsRmId()).getResultList();
										for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabs)
										  {
											if(commonServicesRateSlab.getCsSlabRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateSlabType.Percentage",null, locale)))
											{
												totalAmount = totalAmount +((commonServicesRateSlab.getCsRate()/100) * rateForInterest*taxAmount*days);
											}
										  }
										
										if(commonServicesRateMaster.getCsMaxRate()!=0)
										{
											  totalAmount = (totalAmount > commonServicesRateMaster.getCsMaxRate()) ? commonServicesRateMaster.getCsMaxRate() : totalAmount;
											  consolidatedBill.put("total", totalAmount);
										}
										else
										{
											  totalAmount = (totalAmount > commonServicesRateMaster.getCsMinRate()) ? totalAmount : commonServicesRateMaster.getCsMinRate();
											  consolidatedBill.put("total", totalAmount);
										}
						   		   }
	  							}
	  						}
						}
					}
			   }
	  }
	 return consolidatedBill;
	}

	public float otherServicesTaxAmount(CommonServicesRateMaster commonServicesRateMaster,java.sql.Date previousBillDate, java.sql.Date currentBillDate,float uomValue)
	{

		int loopCount = 0;
		Date lastUpdatedDate = null;
		Date minDate = null;
		Date maxDate = null;
		LocalDate fromDate = new LocalDate(previousBillDate);
		LocalDate toDate = new LocalDate(currentBillDate);
		PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
		Period difference = new Period(fromDate, toDate, monthDay);
		float billableMonths = difference.getMonths();
		int daysAfterMonth = difference.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		float netMonth = daysToMonths + billableMonths;
		float totalAmount = 0;
		List<Map<String, Object>> dateList = tariffCalculationService.getCommonServiceMinMaxDate(commonServicesRateMaster.getCsTariffId(), commonServicesRateMaster.getCsRateName());
		for (Map<String, Object> date : dateList)
		{
			for (Entry<String, Object> map : date.entrySet()) 
			{
				if(map.getKey().equalsIgnoreCase("validFrom"))
				{
					minDate = (Date) map.getValue();
				}
				if(map.getKey().equalsIgnoreCase("validTo"))
				{
					maxDate = (Date) map.getValue();
				}
			}
		}
		
	  if (!(minDate.compareTo(previousBillDate) <=0 ))
	  {
		  logger.info("::::::::::::::::::::: Start Date is not inside the tariff dates ::::::::::::::");
      }
	  else if (currentBillDate.compareTo(maxDate) > 0)
	  {
		  logger.info("::::::::::::::::::::: End Date is not inside the tariff dates ::::::::::::::");
      }
	 else
	  {
		 List<CommonServicesRateMaster> commonServicesRateMasters = tariffCalculationService.getCommonServiceMasterByIdName(commonServicesRateMaster.getCsTariffId(), commonServicesRateMaster.getCsRateName());
		  if(billableMonths == 0)
		  {
			  logger.info(":::::::::::::::::::: tariff calculataion less than one month ::::::::::::::::::::");
			  for(CommonServicesRateMaster commonServicesRateMaster1 : commonServicesRateMasters)  
				{
				  if((previousBillDate.compareTo(commonServicesRateMaster1.getCsValidFrom()) >=0) && (currentBillDate.compareTo(commonServicesRateMaster1.getCsValidTo())<=0))
				  {
				   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
				   		 if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
				   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
				   		 }
				   		if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
				   		 {
				   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
				   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
				   		 }
				  }
				  else
					{
						if(previousBillDate.compareTo(commonServicesRateMaster1.getCsValidTo())<=0)
						{
							if((currentBillDate.compareTo(commonServicesRateMaster1.getCsValidFrom())>= 0) && (commonServicesRateMaster1.getCsValidTo().compareTo(currentBillDate)<=0))
							{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(commonServicesRateMaster1.getCsValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =commonServicesRateMaster1.getCsValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(commonServicesRateMaster1.getCsValidTo());
		 							  lastUpdatedDate =commonServicesRateMaster1.getCsValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServicesRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
					   		if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
					   		 }
							}
							else
	  						{
	 							if(currentBillDate.compareTo(commonServicesRateMaster1.getCsValidFrom())>=0 && currentBillDate.compareTo(commonServicesRateMaster1.getCsValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(commonServicesRateMaster1.getCsValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServicesRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   		 }
						   		if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
						   		 }
	  							}
	  						}
						}
					}	
				} 
		  }
	else
	  {
		  logger.info(":::::::::::::::::::: Tariff calculation for more than one month ::::::::::::::::::::");
		    for (CommonServicesRateMaster commonServicesRateMaster1 : commonServicesRateMasters) 
			 {
				  uomValue =  uomValue/ netMonth;
				  if((previousBillDate.compareTo(commonServicesRateMaster1.getCsValidFrom()) >=0) && (currentBillDate.compareTo(commonServicesRateMaster1.getCsValidTo())<=0))
					{
			   		 logger.info(":::::::::::::::::::: tariff calculataion for more than one month single tariff ::::::::::::::::::::");
			   		 if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
			   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
			   		 }
			   		if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
			   		 {
			   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
			   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
			   			
			   		 }
		           }
				  else
					{
						if(previousBillDate.compareTo(commonServicesRateMaster1.getCsValidTo())<=0)
						{
							if((currentBillDate.compareTo(commonServicesRateMaster1.getCsValidFrom())>= 0) && (commonServicesRateMaster1.getCsValidTo().compareTo(currentBillDate)<=0))
	  						{
								logger.info(":::::::::::::::::::: tariff calculataion for more than one month multiple tariff ::::::::::::::::::::");
								
								LocalDate startDate;
								LocalDate endDate;
								uomValue = uomValue/ netMonth;
								
								if(loopCount == 0)
								{
									  startDate = new LocalDate(previousBillDate);
		 							  endDate = new LocalDate(commonServicesRateMaster1.getCsValidTo());
		 							  endDate = endDate.plusDays(1);
		 							  lastUpdatedDate =commonServicesRateMaster1.getCsValidTo();
		 						      loopCount = loopCount +1;
								}
								else 
								{
							       	  startDate = new LocalDate(lastUpdatedDate);
		 							  endDate = new LocalDate(commonServicesRateMaster1.getCsValidTo());
		 							  lastUpdatedDate =commonServicesRateMaster1.getCsValidTo();
		 							  loopCount = loopCount +1;
								}
					   		 
					   		 if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
					   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServicesRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
					   		 }
					   		if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
					   		 {
					   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
					   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
					   			
					   		 }
  	  					}
							else
	  						{
	 							if(currentBillDate.compareTo(commonServicesRateMaster1.getCsValidFrom())>=0 && currentBillDate.compareTo(commonServicesRateMaster1.getCsValidTo())<=0)
	  							{
	 								 logger.info(":::::::::::::::::::: For last tariff ::::::::::::::::::::");
	 								 LocalDate startDate = new LocalDate(commonServicesRateMaster1.getCsValidFrom());
	  	 							 LocalDate endDate = new LocalDate(currentBillDate);
						   		  
						   		 if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.multiSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			logger.info(":::::::::::::::::::: Rate Master is of Multi Slab  ::::::::::::::::::::");
						   			totalAmount = tariffCalculationService.commonServicesTariffCalculationMultiSlab(commonServicesRateMaster1, startDate.toDateTimeAtStartOfDay().toDate(), endDate.toDateTimeAtStartOfDay().toDate(),uomValue);
						   		 }
						   		if(commonServicesRateMaster1.getCsRateType().trim().equalsIgnoreCase(messageSource.getMessage("rateType.singleSlab",null, locale).trim()) && (!commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DR",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.DC",null, locale).trim()) && !commonServicesRateMaster1.getCsRateName().trim().equalsIgnoreCase(messageSource.getMessage("rateNameType.pfPenalty",null, locale).trim())))
						   		 {
						   			 logger.info(":::::::::::::::::::: Rate Master is of type Single Slab  ::::::::::::::::::::");
						   			 totalAmount = tariffCalculationService.commonServicesTariffCalculationSingleSlab(commonServicesRateMaster1, previousBillDate, currentBillDate,uomValue);
						   			
						   		 }
	  							}
	  						}
						}
					}
			    }
	       }
	  }
	 return totalAmount;
	}
}
	
