package com.bcits.bfm.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.CommonServicesRateSlab;
import com.bcits.bfm.model.CommonServicesTodRates;
import com.bcits.bfm.model.ELTodRates;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceRateSlabService;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTODRatesService;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServicesRateMasterService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class CommonServicesRateMasterController
{

	static Logger logger = LoggerFactory.getLogger(CommonServicesRateMasterController.class);
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CommonServicesRateMasterService commonServicesRateMasterService;
	
	@Autowired
	private CommonServiceRateSlabService commonServiceRateSlabService;
	
	@Autowired
	private CommonServiceTODRatesService commonServiceTODRatesService;
	
	@Autowired
	private CommonController commonController;
	
	@RequestMapping(value = "/commonServiceRateMaster", method = RequestMethod.GET)
	public String commonServicesRateMaster(@ModelAttribute("commonServicesWasterateSlab") CommonServicesRateMaster commonServicesRateMaster,ModelMap model,HttpServletRequest request,Locale locale) 
	{
		logger.info(":::::::::::::::: In Solid Waste rate master controller :::::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Common Service Rate Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		
		model.addAttribute("csTariffName", populateCategories1("Active", "Tariff Rate Node"));
		model.addAttribute("csRateName", messageSource.getMessage("csRateName", null, locale).split(","));
		model.addAttribute("csRateType", messageSource.getMessage("csRateType", null, locale).split(","));
		model.addAttribute("csRateUOM", messageSource.getMessage("csRateUOM", null, locale).split(","));
		model.addAttribute("csTodType", messageSource.getMessage("csTodType", null, locale).split(","));
		model.addAttribute("status", messageSource.getMessage("value.commonservices.status", null, locale).split(","));
		model.addAttribute("csSlabType", messageSource.getMessage("csSlabType", null, locale).split(","));
		model.addAttribute("csSlabRateType", messageSource.getMessage("csSlabRateType", null, locale).split(","));
		model.addAttribute("csTodRateType", messageSource.getMessage("csTodRateType", null, locale).split(","));
		model.addAttribute("RateSlab", commonController.populateCategories("elrsId", "slabsNo", "ELRateSlabs"));
		
		return "commonServicesTariff/commonServicesRateMaster";
	}
	
    private List<?> populateCategories1(String status, String tariffNodeType) 
    {
    List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
    Map<String, Object> mapResult = null;
    List<String> attributesList = new ArrayList<String>();
    attributesList.add("csTariffID");
    attributesList.add("csTariffName");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("status", status);
    params.put("csTariffNodetype", tariffNodeType);
    Map<String, Object> orderByList = new HashMap<String, Object>();
    orderByList.put("csTariffNodetype", "ASC");
    
    List<?> categoriesList = commonService.selectQueryOrderBy("CommonTariffMaster", attributesList, params, orderByList);
    
    mapResultSelect.put("value", 0);
    mapResultSelect.put("text", "Select");
	
	listResult.add(mapResultSelect);
	
	for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
	{
		final Object[] values = (Object[]) i.next();
		
		mapResult = new HashMap<String, Object>();
		mapResult.put("value", values[0]);
		mapResult.put("text", values[1]);
		
		listResult.add(mapResult);
	}	
    return listResult;
    }
    
    private List<?> populateCategories(String status, String tariffNodeType) 
    {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
        Map<String, Object> mapResult = null;
        
        List<String> attributesList = new ArrayList<String>();
        attributesList.add("csTariffID");
        attributesList.add("csTariffName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("csTariffNodetype", tariffNodeType);
        Map<String, Object> orderByList = new HashMap<String, Object>();
        orderByList.put("csTariffNodetype", "ASC");
        
        List<?> categoriesList = commonService.selectQueryOrderBy("CommonTariffMaster", attributesList, params, orderByList);
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        mapResultSelect.put("csTariffId", 0);
        mapResultSelect.put("csTariffName", "Select");
		listResult.add(mapResultSelect);
		
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			mapResult = new HashMap<String, Object>();
			mapResult.put("csTariffId", values[0]);
			mapResult.put("csTariffName", values[1]);
			
			listResult.add(mapResult);
		}	
        return listResult;
    } 
    
 	@RequestMapping(value = "/commonServiceRateMaster/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> createCommonServicesRateMaster(@ModelAttribute("commonServiceRatemaster") CommonServicesRateMaster commonServicesRateMaster, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
 		logger.info("::::::::::::::::::::::::Creating Solid Waster Rate mastrer :::::::::::::::::::::::::");
 		commonServicesRateMaster.setStatus("Active");
 		commonServicesRateMaster.setCsValidFrom(dateTimeCalender.getDateToStore(req.getParameter("csValidFrom")));
 		commonServicesRateMaster.setCsValidTo(dateTimeCalender.getDateToStore(req.getParameter("csValidTo")));
 		commonServicesRateMasterService.save(commonServicesRateMaster);
 		List<CommonServicesRateMaster> commonServicesRateMastersList = new ArrayList<>();
 		commonServicesRateMastersList.add(commonServicesRateMaster);
	    return commonServicesRateMasterService.setResponse(commonServicesRateMastersList);
	}
 	
	@RequestMapping(value = "/commonServiceRateMaster/read", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readCommonServicesRateMaster(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		logger.info(":::::::::::::::::::::::: Reading CommonServices Rate mastrer :::::::::::::::::::::::::");
		String showAll = req.getParameter("showAll");
		logger.info(":::::::::::::::::::::::: Show all option is ::::::::::::::::::::::::: " +showAll);
		List<CommonServicesRateMaster> commonServicesRateMastersList = null;
		List<?> result = new ArrayList<Map<String, Object>>();;
		if(showAll == null)
		{
			  commonServicesRateMastersList = commonServicesRateMasterService.findActive();
		      logger.info("::::::::::::: reading active data in rate master table ::::::::::::::::::::" +commonServicesRateMastersList.size());
		      result = commonServicesRateMasterService.setResponse(commonServicesRateMastersList);
		}
		else 
		{
			commonServicesRateMastersList = commonServicesRateMasterService.findALL();
			logger.info("::::::::::::: reading all data in rate master table ::::::::::::::::::::" +commonServicesRateMastersList.size());
			result = commonServicesRateMasterService.setResponse(commonServicesRateMastersList);
			
		}
		return result;
	}
	
	@RequestMapping(value = "/commonServiceRateMaster/update", method = RequestMethod.GET)
	public @ResponseBody Object editCommonServicesRateMaster(@ModelAttribute("commonServicesRateMaster") CommonServicesRateMaster commonServicesRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss,HttpServletRequest req) throws ParseException
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+commonServicesRateMaster.getCsRmId()+" is updating");
		commonServicesRateMaster.setCsValidFrom(dateTimeCalender.getDateToStore(req.getParameter("csValidFrom")));;
		commonServicesRateMaster.setCsValidTo(dateTimeCalender.getDateToStore(req.getParameter("csValidTo")));
		commonServicesRateMasterService.update(commonServicesRateMaster);
		List<CommonServicesRateMaster> commonServicesRateMastersList = new ArrayList<>();
 		commonServicesRateMastersList.add(commonServicesRateMaster);
	    return commonServicesRateMasterService.setResponse(commonServicesRateMastersList);
	}
	
	@RequestMapping(value = "/commonServiceRateMaster/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteCommonServicesRateMaster(@ModelAttribute("commonServicesRateMaster") CommonServicesRateMaster commonServicesRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss)
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+commonServicesRateMaster.getCsRmId()+" is deleting");
		try{
			commonServicesRateMasterService.delete(commonServicesRateMaster.getCsRmId());
			List<CommonServicesRateMaster> commonServicesRateMastersList = new ArrayList<>();
	 		commonServicesRateMastersList.add(commonServicesRateMaster);
		    return commonServicesRateMasterService.setResponse(commonServicesRateMastersList);
		}
		catch(Exception e)
		{
			logger.info(":::::::::::: Exception while deleting the rate master :::::::::::::");
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
	}
	
	@RequestMapping(value = "/commonServiceTariff/tariffStatus/{csRmId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void commonServiceTariffStatus(@PathVariable int csRmId, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("Rate master Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+csRmId);
		
		if(operation.equalsIgnoreCase("activate"))
		{
			commonServicesRateMasterService.setCommonServicesRateMasterStatus(csRmId, operation, response);
		}
		else
		{
			commonServicesRateMasterService.setCommonServicesRateMasterStatus(csRmId, operation, response);
		}
	}
	
	@RequestMapping(value = "/commonServiceRateMaster/endDate/{csRmId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void commonServicesRateMasterEndDate(@PathVariable int csRmId, HttpServletResponse response)
	{		
		//serviceMasterService.serviceEndDateUpdate(commonServiceRmId, response);
		commonServicesRateMasterService.commonServicesRateMasterEndDate(csRmId, response);
	}
	
	
	@RequestMapping(value = "/commonServiceRateMaster/getToTariffMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getToTariffMasterComboBoxUrl() 
	{	
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listResult =   (List<Map<String, Object>>) populateCategories("Active", "Tariff Rate Node");
		return listResult;
	}
	
	@RequestMapping(value = "/commonServiceTariff/getMaxDate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Date getMaxDate(HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException 
	{
		int wtTariffId = Integer.parseInt(req.getParameter("csTariffId"));
		String wtRateName = req.getParameter("csRateName");
		String wtRateType = req.getParameter("csRateType");
		logger.info("Search for date where tariff id ::::::::"+wtTariffId+" rate name ::::::::"+wtRateName+" rate type :::::::::"+wtRateType);
	
		List<String> attributesList = new ArrayList<String>();
         attributesList.add("csValidTo");
         
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("csRateName", wtRateName);
         params.put("csTariffId", wtTariffId);
         params.put("csRateType", wtRateType);
         
 	   	Date maxDate = commonService.getMaxDate("CommonServicesRateMaster", attributesList, params);
 		
 		logger.info("Max date is :::::::::::::::::::::: "+maxDate);
		Date nextFromDate = null;
		if(maxDate!= null)
		{
			nextFromDate = new Date(maxDate.getTime() + (1000 * 60 * 60 * 24));
			logger.info("Next From Date is ::::::::::::::::::: "+nextFromDate);
			return nextFromDate;
		}
		return nextFromDate;
	}
/****************************** Filters Data methods for water rate master ********************************/
	
	/****************************** For EL Rate Slabs ********************************/

	@RequestMapping(value = "/tariff/commonServiceRateslab/read/{csRmId}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readRateSlab(@PathVariable int csRmId, final Locale locale) 
	{
		logger.info("::::::::::::::::::: Reading the Rate slab ::::::::::::::::::: ");
		try
		{
			List<CommonServicesRateSlab> commonServiceRateSlabsList = commonServiceRateSlabService.rateSlabListByParentID(csRmId);
			logger.info("::::::::::::::::::: Number of rate slabs for id  ::::::::::::::::::: "+csRmId+":: "+commonServiceRateSlabsList.size());
			return  commonServiceRateSlabService.setResponse(commonServiceRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/commonServiceRateslab/create/{csRmId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createwWTRateSlab(@ModelAttribute("commonServicerateslab") CommonServicesRateSlab commonServiceRateSlab,BindingResult bindingResult,ModelMap model,@PathVariable int csRmId,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("::::::::::::::::::: Creating the Rate slab  ::::::::::::::::::: ");
		
		commonServiceRateSlab.setStatus("Active");
		commonServiceRateSlab.setCsRmId(csRmId);;
		String maxSlabToValue = req.getParameter("csMaxSlabToValue");
		
		 if(maxSlabToValue.trim().equals("true"))
            {
			 commonServiceRateSlab.setCsSlabTo(999999f);
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+commonServiceRateSlab.getCsSlabNo() +" Assign 999999 to slab to as max value");
            }
            else 
            {
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+commonServiceRateSlab.getCsSlabNo() +" Dont assigne 999999 to slab to as max value");
			}
			
		 if(commonServiceRateSlab.getCsSlabType().trim().equals("Not applicable"))
		 {
			 commonServiceRateSlab.setCsSlabFrom(null);
			 commonServiceRateSlab.setCsSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+commonServiceRateSlab.getCsSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+commonServiceRateSlab.getCsSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
	
		try
		{
		    commonServiceRateSlabService.save(commonServiceRateSlab);	
			List<CommonServicesRateSlab> commonServiceRateSlabsList = new ArrayList<>();
			commonServiceRateSlabsList.add(commonServiceRateSlab);
			return commonServiceRateSlabService.setResponse(commonServiceRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/commonServiceRateslab/update", method = RequestMethod.GET)
	public @ResponseBody Object editWaterRateSlab(@ModelAttribute("commonServiceRateslab") CommonServicesRateSlab commonServiceRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
	{
		 logger.info("::::::::::::::::::: Updating the Rate slab ::::::::::::::::::: ");
		 List<CommonServicesRateSlab> commonServiceRateSlabsList = new ArrayList<>();
		 if(commonServiceRateSlab.getCsSlabType().trim().equals("Not applicable"))
		 {
			 commonServiceRateSlab.setCsSlabFrom(null);
			 commonServiceRateSlab.setCsSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+commonServiceRateSlab.getCsSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+commonServiceRateSlab.getCsSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
		try
		{
			logger.info("::::::::::::::::::: In Try Block  ::::::::::::::::::: ");
			logger.info("commonServiceRateSlab.getCsRsId() "+commonServiceRateSlab.getCsRsId());
			logger.info("commonServiceRateSlab.getCsSlabNo() "+commonServiceRateSlab.getCsSlabNo());
			logger.info("commonServiceRateSlab.getCsDummySlabFrom() "+commonServiceRateSlab.getCsDummySlabFrom());
			logger.info("commonServiceRateSlab.getCsDummySlabTo() "+commonServiceRateSlab.getCsDummySlabTo());
			logger.info("commonServiceRateSlab.getCsDummySlabTo() "+commonServiceRateSlab.getCsDummySlabTo());
			
			commonServiceRateSlabService.update(commonServiceRateSlab);
			commonServiceRateSlabsList.add(commonServiceRateSlab);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
		return commonServiceRateSlabService.setResponse(commonServiceRateSlabsList);
	}
	
	@RequestMapping(value = "/tariff/commonServiceRateslab/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteWaterRateSlab(@ModelAttribute("commonServiceRateslab") CommonServicesRateSlab commonServiceRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus ss,final Locale locale)
	{
		logger.info("::::::::::::::::::: Deleting the Rate slab ::::::::::::::::::: ");
		try{
			logger.info("Deleting Rate Slab with Id ::::::::::::::::: "+commonServiceRateSlab.getCsRsId());
			commonServiceRateSlabService.delete(commonServiceRateSlab.getCsRsId());
		}
		catch(Exception e)
		{
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		
		try
		{
			List<CommonServicesRateSlab> commonServiceRateSlabsList = new ArrayList<>();
			commonServiceRateSlabsList.add(commonServiceRateSlab);
			return commonServiceRateSlabService.setResponse(commonServiceRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	 @RequestMapping(value = "/tariff/commonServiceRateSlabStatusUpdateFromInnerGrid/{csRsId}", method = { RequestMethod.GET, RequestMethod.POST })
	 public void waterRatesSlabStatusUpdateFromInnerGrid(@PathVariable int csRsId, HttpServletResponse response)
	 {  
		 commonServiceRateSlabService.updateslabStatus(csRsId, response);
	 }

	 /* ====================================== TOD operation starts from here =======================================*/
	 
 
		@RequestMapping(value = "/tariff/commonServiceTODRate/read/{csRmId}",  method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<?> commonServiceTODRates(@PathVariable int csRmId,final Locale locale) 
		{
			  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			  List<CommonServicesRateSlab> commonServicesRateSlabList = commonServiceRateSlabService.findRateSlabByID(csRmId);
			  for (CommonServicesRateSlab commonServicesRateSlab : commonServicesRateSlabList)
			  {
				  for (final CommonServicesTodRates commonServicesTodRates : commonServiceTODRatesService.findALL(commonServicesRateSlab.getCsRsId())) 
				  {
						           result.add(new HashMap<String, Object>() 
						            {/**
										 * 
										 */
										private static final long serialVersionUID = 1L;

									{
						             put("cstiId", commonServicesTodRates.getCstiId());
						             put("csRsId",commonServicesTodRates.getCsRsId());
						             put("fromTime", ConvertDate.TimeStampString(commonServicesTodRates.getFromTime()) );
						             put("toTime", ConvertDate.TimeStampString(commonServicesTodRates.getToTime()) );
						             put("incrementalRate", commonServicesTodRates.getIncrementalRate());
						             put("todValidFrom", commonServicesTodRates.getTodValidFrom());             
						             put("todValidTo", commonServicesTodRates.getTodValidTo());
						             put("createdBy", commonServicesTodRates.getCreatedBy());
						             put("status", commonServicesTodRates.getStatus());
						             put("csTodRateType", commonServicesTodRates.getCsTodRateType());
						             put("lastUpdatedDT", commonServicesTodRates.getLastUpdatedDT()); 
						             put("lastUpdatedBy", commonServicesTodRates.getLastUpdatedBy());
						            }});
					 
				  }
			 }
			  return result;	
		}
		
		@RequestMapping(value = "/tariff/commonServiceTODRate/create", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody
		Object createCSTODRate(@ModelAttribute("commonServicesTodRates") CommonServicesTodRates commonServicesTodRates, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
		{
			logger.info(":::::::::::::::::::: In Create TOD Rate Method ::::::::::::::::::::::::");
			java.util.Date fromdate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("fromTime"));
			java.util.Date todate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("toTime"));
			
			Timestamp fromTime = new Timestamp(fromdate.getTime());
			Timestamp toTime = new Timestamp(todate.getTime());
			
			commonServicesTodRates.setFromTime(fromTime);
			commonServicesTodRates.setToTime(toTime);
			commonServicesTodRates.setTodValidFrom(dateTimeCalender.getDateToStore(req.getParameter("todValidFrom")));
			commonServicesTodRates.setTodValidTo(dateTimeCalender.getDateToStore(req.getParameter("todValidTo")));
			commonServicesTodRates.setStatus("Active");
		//	commonServicesTodRates.setCsrsId(Integer.parseInt(req.getParameter("csRsId")));
			
			//commonServicesTodRates.setCommonServicesRateSlab(commonServiceRateSlabService.findRateSlabByPrimayID(commonServicesTodRates.getCsrsId()));
			
			/*Date maxDate = eltodRatesService.getRateSlabs(commonServicesTodRates.getElrsId());
			Timestamp maxTimeStamp = eltodRatesService.getmaxTimeStamp(commonServicesTodRates.getElrsId());
			int maxIncrementalRate = eltodRatesService.getMaxIncrementalRate(commonServicesTodRates.getElrsId());
			
			if(maxDate == null || maxTimeStamp == null || maxIncrementalRate == 0 )
			{
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(elTodRates.getFromTime());       // set cal to date
				 cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
				 cal.set(Calendar.MINUTE, 0);                 // set minute in hour
				 cal.set(Calendar.SECOND, 0);                 // set second in minute
				 cal.set(Calendar.MILLISECOND, 0);            // set millis in second
				 Date zeroedDate = cal.getTime(); 
				 
				 Timestamp timestamp = new Timestamp(zeroedDate.getTime());
 				 eltodRatesService.save(elTodRates);
			 	 return elTodRates;
			}
			else
			{
				eltodRatesService.save(commonServicesTodRates);
			}*/
			commonServiceTODRatesService.save(commonServicesTodRates);
			return commonServicesTodRates;
		}
		
		@RequestMapping(value = "/tariff/commonServiceTODRate/update", method = RequestMethod.GET)
		public @ResponseBody Object editTODRates(@ModelAttribute("commonServicesTodRates") CommonServicesTodRates commonServicesTodRates, BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
		{
			
			java.util.Date fromdate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("fromTime"));
			java.util.Date todate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("toTime"));
			Timestamp fromTime = new Timestamp(fromdate.getTime());
			Timestamp toTime = new Timestamp(todate.getTime());
			commonServicesTodRates.setFromTime(fromTime);
			commonServicesTodRates.setToTime(toTime);
			commonServicesTodRates.setTodValidFrom(dateTimeCalender.getDateToStore(req.getParameter("todValidFrom")));
			commonServicesTodRates.setTodValidTo(dateTimeCalender.getDateToStore(req.getParameter("todValidTo")));
			commonServiceTODRatesService.update(commonServicesTodRates);
			return commonServicesTodRates;
		}
		
		@RequestMapping(value = "/tariff/commonServiceTODRate/destroy", method = RequestMethod.GET)
		public @ResponseBody Object deleteTODRates(@ModelAttribute("eltodrates") ELTodRates elTodRates, BindingResult bindingResult,ModelMap model,SessionStatus ss) {
			
			try{
				commonServiceTODRatesService.delete(elTodRates.getEltiId());
			}
			catch(Exception e)
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				return errorResponse;
			}
			ss.setComplete();
			return elTodRates;
		}
		
		@RequestMapping(value = "/tariff/commonServiceTODRate/categories/{csRmId}",  method = {RequestMethod.GET, RequestMethod.POST})
	    public @ResponseBody List<?> categories(@PathVariable int csRmId,final Locale locale) 
	    {
			List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
		    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
		    Map<String, Object> mapResult = null;
		    List<String> attributesList = new ArrayList<String>();
		    attributesList.add("csRsId");
		    attributesList.add("csSlabNo");
		    
		    Map<String, Object> params = new HashMap<String, Object>();
	        params.put("csRmId", csRmId);
	        
	        Map<String, Object> orderByList = new HashMap<String, Object>();
	        orderByList.put("csSlabNo", "ASC");
		    
		    List<?> categoriesList = (List<?>) commonService.selectQueryOrderBy("CommonServicesRateSlab", attributesList, params,orderByList);

		    mapResultSelect.put("csRsId", 0);
		    mapResultSelect.put("csSlabNo", "Select");
			listResult.add(mapResultSelect);
			for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
			{
				final Object[] values = (Object[]) i.next();
				mapResult = new HashMap<String, Object>();
				mapResult.put("csRsId", values[0]);
				mapResult.put("csSlabNo", values[1]);
				listResult.add(mapResult);
			}	
		    return listResult;
	    }
		
		 @RequestMapping(value = "/tariff/commonServiceTODRatesStatusUpdateFromInnerGrid/{eltiId}", method = { RequestMethod.GET, RequestMethod.POST })
		 public void elTODRatesStatusUpdateFromInnerGrid(@PathVariable int eltiId, HttpServletResponse response)
		 {  
			 commonServiceTODRatesService.updateslabStatus(eltiId, response);
		 }	
	
}
