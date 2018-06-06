package com.bcits.bfm.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.waterTariffManagement.WTRateMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTRateSlabService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class WaterRateMasterController
{
	static Logger logger = LoggerFactory.getLogger(WaterRateMasterController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private WTRateMasterService wtRateMasterService;
	
	@Autowired
	private WTRateSlabService wtRateSlabService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@RequestMapping(value = "/wtRateMaster", method = RequestMethod.GET)
	public String waterRateMaster(@ModelAttribute("wtrateSlab") WTRateSlabs wtRateSlabs,ModelMap model,HttpServletRequest request,Locale locale) 
	{
		logger.info(":::::::::::::::: In water rate master controller :::::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Water Rate Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		
		model.addAttribute("wtTariffName", populateCategories1("Active", "Tariff Rate Node"));
		model.addAttribute("waterRateName", messageSource.getMessage("wtRateName", null, locale).split(","));
		model.addAttribute("waterRateType", messageSource.getMessage("wtRateType", null, locale).split(","));
		model.addAttribute("waterRateUOM", messageSource.getMessage("wtRateUOM", null, locale).split(","));
		model.addAttribute("wtRateMasterStatus", messageSource.getMessage("value.water.status", null, locale).split(","));
		model.addAttribute("waterSlabType", messageSource.getMessage("wtSlabType", null, locale).split(","));
		model.addAttribute("waterSlabRateType", messageSource.getMessage("wtSlabRateType", null, locale).split(","));
		return "waterTariff/waterRateMaster";
	}
	
    private List<?> populateCategories1(String status, String tariffNodeType) 
    {
    List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
    Map<String, Object> mapResult = null;
    List<String> attributesList = new ArrayList<String>();
    attributesList.add("wtTariffId");
    attributesList.add("tariffName");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("status", status);
    params.put("tariffNodetype", tariffNodeType);
    Map<String, Object> orderByList = new HashMap<String, Object>();
    orderByList.put("tariffName", "ASC");
    
    List<?> categoriesList = commonService.selectQueryOrderBy("WTTariffMaster", attributesList, params, orderByList);
    
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
        attributesList.add("wtTariffId");
        attributesList.add("tariffName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("tariffNodetype", tariffNodeType);
        
        Map<String, Object> orderByList = new HashMap<String, Object>();
        orderByList.put("tariffName", "ASC");
        
        List<?> categoriesList = commonService.selectQueryOrderBy("WTTariffMaster", attributesList, params, orderByList);
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        mapResultSelect.put("wtTariffId", 0);
        mapResultSelect.put("wtTariffName", "Select");
		listResult.add(mapResultSelect);
		
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			mapResult = new HashMap<String, Object>();
			mapResult.put("wtTariffId", values[0]);
			mapResult.put("wtTariffName", values[1]);
			
			listResult.add(mapResult);
		}	
        return listResult;
    } 
    
 	@RequestMapping(value = "/waterRateMaster/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createWaterRateMaster(@ModelAttribute("waterRatemaster") WTRateMaster wtRateMaster, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{

 		wtRateMaster.setWtRateMasterStatus("Active");
 		wtRateMaster.setWtValidFrom(dateTimeCalender.getDateToStore(req.getParameter("wtValidFrom")));;
 		wtRateMaster.setWtValidTo(dateTimeCalender.getDateToStore(req.getParameter("wtValidTo")));
 	//	wtRateMaster.setWtCreatedBy((String) SessionData.getUserDetails().get("userID"));
 	//	wtRateMaster.setWtLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
 		
 		/* List<String> attributesList = new ArrayList<String>();
         attributesList.add("wtValidTo");
         
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("wtRateName", wtRateMaster.getWtRateName());
         params.put("wtTariffId", wtRateMaster.getWtTariffId());
         params.put("wtRateType", wtRateMaster.getWtRateType());
         */
         /*	Date maxDate = commonService.getMaxDate("WTRateMaster", attributesList, params);
		
		if(maxDate == null)
		{
			wtRateMasterService.save(wtRateMaster);
		}
		else
		{
		String dateErrorMsg = dateCamparisonRateMaster(wtRateMaster, maxDate);
		if(dateErrorMsg!=null)
		{
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("SINGLESLAB");
			errorResponse.setResult(dateErrorMsg);
			return errorResponse;
		}
		}*/
 		wtRateMasterService.save(wtRateMaster);
		return wtRateMaster;
	}
 	
	@RequestMapping(value = "/waterRateMaster/read", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> readWaterRateMaster(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		String showAll = req.getParameter("showAll");
		List<WTRateMaster> wtRateMastersList = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		if(showAll == null)
		{
		      wtRateMastersList = wtRateMasterService.findActive();
		      result = wtRateMasterService.setResponse(wtRateMastersList);
			  logger.info("::::::::::::: reading active data in rate master table ::::::::::::::::::::" +wtRateMastersList.size());
		}
		else 
		{
			wtRateMastersList = wtRateMasterService.findALL();
			result = wtRateMasterService.setResponse(wtRateMastersList);
			logger.info("::::::::::::: reading all data in rate master table ::::::::::::::::::::" +wtRateMastersList.size());
		}
		return result;
	}
	
	@RequestMapping(value = "/waterRateMaster/update", method = RequestMethod.GET)
	public @ResponseBody Object editWaterRateMaster(@ModelAttribute("wtratemaster") WTRateMaster wtRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss,HttpServletRequest req) throws ParseException
	{
		logger.info("Rate Master Update method");
		logger.info("The Rate Master withd Id :::::::::::::: "+wtRateMaster.getWtrmid()+" is updating");
		wtRateMaster.setWtValidFrom(dateTimeCalender.getDateToStore(req.getParameter("wtValidFrom")));;
		wtRateMaster.setWtValidTo(dateTimeCalender.getDateToStore(req.getParameter("wtValidTo")));;
		wtRateMasterService.update(wtRateMaster);
		return wtRateMaster;
	}
	
	@RequestMapping(value = "/waterRateMaster/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteWaterRateMaster(@ModelAttribute("waterRatemaster") WTRateMaster wtRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss)
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+wtRateMaster.getWtrmid()+" is deleting");
		try{
			wtRateMasterService.delete(wtRateMaster.getWtrmid());
		}
		catch(Exception e){
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return wtRateMaster;
	}
	
	@RequestMapping(value = "/waterTariff/tariffStatus/{wtrmid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void waterTariffStatus(@PathVariable int wtrmid, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("Rate master Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+wtrmid);
		
		if(operation.equalsIgnoreCase("activate"))
			wtRateMasterService.setWaterRateMasterStatus(wtrmid, operation, response);

		else
			wtRateMasterService.setWaterRateMasterStatus(wtrmid, operation, response);
	}
	
	/****************************** Filters Data methods for water rate master ********************************/
	
	@RequestMapping(value = "/waterTariff/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getContentsForFilter(@PathVariable String feild) 
	{
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(wtRateMasterService.selectQuery("WTRateMaster", attributeList, null));
		return set;
	}
	@RequestMapping(value = "/wtTariffNameToFilter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> tariffNameToFilter(@PathVariable String feild)
	{
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", "Active");
        params.put("tariffNodetype", "Tariff Rate Node");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(wtRateMasterService.selectQuery("WTTariffMaster", attributeList, params));
		return set;
	}
	
	@RequestMapping(value = "/waterRateMaster/getToTariffMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getToTariffMasterComboBoxUrl() 
	{	
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listResult =   (List<Map<String, Object>>) populateCategories("Active", "Tariff Rate Node");
		return listResult;
	}
	
	@RequestMapping(value = "/waterTariff/getMaxDate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Date getMaxDate(HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException 
	{
		int wtTariffId = Integer.parseInt(req.getParameter("wtTariffId"));
		String wtRateName = req.getParameter("wtRateName");
		String wtRateType = req.getParameter("wtRateType");
		logger.info("Search for date where tariff id ::::::::"+wtTariffId+" rate name ::::::::"+wtRateName+" rate type :::::::::"+wtRateType);
	
		List<String> attributesList = new ArrayList<String>();
         attributesList.add("wtValidTo");
         
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("wtRateName", wtRateName);
         params.put("wtTariffId", wtTariffId);
         params.put("wtRateType", wtRateType);
         
 	   	Date maxDate = commonService.getMaxDate("WTRateMaster", attributesList, params);
 		
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
	/****************************** end  Filters Data methods for water rate master   ********************************/
	
	/****************************** For EL Rate Slabs ********************************/

	@RequestMapping(value = "/tariff/waterRateslab/read/{wtrmid}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readWTRateSlab(@PathVariable int wtrmid, final Locale locale) 
	{
		logger.info("::::::::::::::::::: Reading the Rate slab ::::::::::::::::::: ");
		try
		{
			List<WTRateSlabs> wtRateSlabsList = wtRateSlabService.rateSlabListByParentID(wtrmid);
			logger.info("::::::::::::::::::: Number of rate slabs for id  ::::::::::::::::::: "+wtrmid+":: "+wtRateSlabsList.size());
			return  wtRateSlabService.setResponse(wtRateSlabsList);
		}

		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/waterRateslab/create/{wtrmid}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createwWTRateSlab(@ModelAttribute("wtrateslab") WTRateSlabs wtRateSlabs,BindingResult bindingResult,ModelMap model,@PathVariable int wtrmid,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("::::::::::::::::::: Creating the Rate slab  ::::::::::::::::::: ");
		
		wtRateSlabs.setWtRateSlabStatus("Active");
		wtRateSlabs.setWtrmid(wtrmid);
		String maxSlabToValue = req.getParameter("waterMaxSlabToValue");
		
		 if(maxSlabToValue.trim().equals("true"))
            {
			   wtRateSlabs.setWtSlabTo(999999f);
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+wtRateSlabs.getWtSlabNo() +" Assign 999999 to slab to as max value");
            }
            else 
            {
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+wtRateSlabs.getWtSlabNo() +" Dont assigne 999999 to slab to as max value");
			}
			
		 if(wtRateSlabs.getWtSlabType().trim().equals("Not applicable"))
		 {
			 wtRateSlabs.setWtSlabFrom(null);
			 wtRateSlabs.setWtSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+wtRateSlabs.getWtSlabType() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+wtRateSlabs.getWtSlabType() +" dont assign 'NULL' to slab from and slab to");
		 }
	
		try
		{
		    wtRateSlabService.save(wtRateSlabs);	
			List<WTRateSlabs> wtRateSlabsList = new ArrayList<>();
			wtRateSlabsList.add(wtRateSlabs);
			return wtRateSlabService.setResponse(wtRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
		
	}
	
	@RequestMapping(value = "/tariff/waterRateslab/update", method = RequestMethod.GET)
	public @ResponseBody
	Object editWaterRateSlab(@ModelAttribute("waterRateslab") WTRateSlabs wtRateSlabs,BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
	{
		 logger.info("::::::::::::::::::: Updating the Rate slab ::::::::::::::::::: ");
		 if(wtRateSlabs.getWtSlabType().trim().equals("Not applicable"))
		 {
			 wtRateSlabs.setWtSlabFrom(null);
			 wtRateSlabs.setWtSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+wtRateSlabs.getWtSlabType() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+ wtRateSlabs.getWtSlabType()+" dont assign 'NULL' to slab from and slab to");
		 }
		
		wtRateSlabService.update(wtRateSlabs);
		
		try
		{
			List<WTRateSlabs> wtRateSlabsList = new ArrayList<>();
			wtRateSlabsList.add(wtRateSlabs);
			return wtRateSlabService.setResponse(wtRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/waterRateslab/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteWaterRateSlab(@ModelAttribute("waterRateslab") WTRateSlabs wtRateSlabs,BindingResult bindingResult,ModelMap model,SessionStatus ss,final Locale locale)
	{
		logger.info("::::::::::::::::::: Deleting the Rate slab ::::::::::::::::::: ");
		try{
			logger.info("Deleting Rate Slab with Id ::::::::::::::::: "+wtRateSlabs.getWtrsId());
			wtRateSlabService.delete(wtRateSlabs.getWtrsId());
		}
		catch(Exception e)
		{
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		
		try
		{
			List<WTRateSlabs> wtRateSlabsList = new ArrayList<>();
			wtRateSlabsList.add(wtRateSlabs);
			 return wtRateSlabService.setResponse(wtRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	 @RequestMapping(value = "/tariff/waterRateSlabStatusUpdateFromInnerGrid/{wtrsId}", method = { RequestMethod.GET, RequestMethod.POST })
	 public void waterRatesSlabStatusUpdateFromInnerGrid(@PathVariable int wtrsId, HttpServletResponse response)
	 {  
		 logger.info("::::::::::::::::::: Changing the status of rate slab with Id ::::::::::::::::::: "+wtrsId);
		 wtRateSlabService.updateslabStatus(wtrsId, response);
	 }
	 
	 @RequestMapping(value = "/tariff/waterRateslab/rateSlabSplit", method = { RequestMethod.GET, RequestMethod.POST })
	 public @ResponseBody Object waterRateSlabSplit(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	 {  
		 logger.info("::::::::::::::::::: Split the rate slab ::::::::::::::::::: ");
		 String wlrsidString = request.getParameter("hiddenwtrsId");
		 WTRateSlabs wtRateSlabs =  new WTRateSlabs();
		 wtRateSlabs.setWtSlabNo(Integer.parseInt(request.getParameter("wtSlabNo")));
		 wtRateSlabs.setWtRate(Integer.parseInt(request.getParameter("wtRate")));
		 wtRateSlabs.setWtSlabFrom(Float.parseFloat(request.getParameter("wtSlabFrom")));
		 wtRateSlabs.setWtSlabTo(Float.parseFloat(request.getParameter("wtSlabTo")));
		 
		 WTRateSlabs wtRateSlabs2 = wtRateSlabService.findRateSlabByPrimayID(Integer.parseInt(wlrsidString));
		 wtRateSlabs2.setWtSlabNo(wtRateSlabs.getWtSlabNo());
		 wtRateSlabs2.setWtRate(wtRateSlabs.getWtRate());
		 wtRateSlabs2.setWtSlabFrom(wtRateSlabs.getWtSlabFrom());
		 wtRateSlabs2.setWtSlabTo(wtRateSlabs.getWtSlabTo());
		 
		 WTRateSlabs wtRateSlabs1 =  new WTRateSlabs();
		 wtRateSlabs1.setWtSlabNo(Integer.parseInt(request.getParameter("wtSlabNo1")));
		 wtRateSlabs1.setWtRate(Integer.parseInt(request.getParameter("rate1")));
		 wtRateSlabs1.setWtSlabFrom(Float.parseFloat(request.getParameter("slabFrom1")));
		 wtRateSlabs1.setWtSlabTo(Float.parseFloat(request.getParameter("slabTo1")));
		 wtRateSlabs1.setWtRateSlabStatus("Active");
		 wtRateSlabs1.setWtSlabType(wtRateSlabs2.getWtSlabType());
		 wtRateSlabs1.setWtSlabRateType(wtRateSlabs2.getWtSlabRateType());
		 wtRateSlabs1.setWtrmid(wtRateSlabs2.getWtrmid());
		 
		 float form2SlabFrom = wtRateSlabs.getWtSlabTo() +1;
		 
		 if(wtRateSlabs1.getWtSlabFrom() != form2SlabFrom)
		 {
			    JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Form 2 Slab From shoub be Form1 Slab To +1");
				return errorResponse;
		 }
		 
		 wtRateSlabService.update(wtRateSlabs2);
		 
		   List<WTRateSlabs> wtRateSlabsList = wtRateSlabService.getElRateSlabGreaterThanUpdateSlabNoEq(wtRateSlabs1.getWtSlabNo(),wtRateSlabs1.getWtrmid());
			for (WTRateSlabs wtRateSlabsSplit : wtRateSlabsList)
			{
				int updateSlabNo = wtRateSlabsSplit.getWtSlabNo() + 1;
				wtRateSlabsSplit.setWtSlabNo(updateSlabNo);
				wtRateSlabService.update(wtRateSlabsSplit);
			}
		wtRateSlabService.save(wtRateSlabs1);
		JsonResponse errorResponse = new JsonResponse();
		errorResponse.setStatus("SUCCESS");
		errorResponse.setResult("Two records split successfully");
		return errorResponse;
	 }
	 
		@RequestMapping(value = "/tariff/waterRateslab/merge", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object merge(HttpServletRequest request,HttpServletResponse response)
		{
			String id = request.getParameter("wtrsIds");
			logger.info(":::::::::::::::::::: The Id's to be merged is :::::::::::::: "+id);
			String idValues[]=id.split(",");
			Arrays.sort(idValues);
			
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			
			if(idValues.length < 2)
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Select atleast two check boxes");
				return errorResponse;
			}

			WTRateSlabs previousRateSlab = new WTRateSlabs();
			
			if(idValues.length == 2)
			{
				for(int i=0;i<idValues.length-1;i++)
				{
					previousRateSlab = wtRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
					WTRateSlabs currentRateSlabs = wtRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
					
					WTRateMaster wtRateMaster = wtRateMasterService.getStatusofWTRateMaster(previousRateSlab.getWtrmid());
					
					if(wtRateMaster.getWtRateType().equals("Multi Slab"))
					{
						if((previousRateSlab.getWtSlabNo() + 1) == currentRateSlabs.getWtSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getWtSlabNo() - 1) == currentRateSlabs.getWtSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						else 
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("SINGLESLAB");
							errorResponse.setResult("Select only consequent checkboxes");
							return errorResponse;
						}
					}
					
					if(wtRateMaster.getWtRateType().equals("Multi Slab Telescopic"))
					{
						if((previousRateSlab.getWtSlabNo() + 1) == currentRateSlabs.getWtSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getWtSlabNo() - 1) == currentRateSlabs.getWtSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						else 
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("SINGLESLAB");
							errorResponse.setResult("Select only consequent checkboxes");
							return errorResponse;
						}
					}
					
				}
			}
			else 
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Maximum  only two check box allowed to merge");
				return errorResponse;
			}

			return null;
		}
		
		@RequestMapping(value = "/tariff/waterRateslab/getNewRate", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object getNewRate(HttpServletRequest request,HttpServletResponse response)
		{
			String id = (String) request.getSession().getAttribute("id");
			String idValues[]=id.split(",");
			Arrays.sort(idValues);
			String rate = request.getParameter("newrate");
			
			if(rate.equals(""))
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Rate should not be empty");
				return errorResponse;
			}

			int newRate = Integer.parseInt(rate);
			if (newRate <= 0)
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Rate should not less than 1");
				return errorResponse;
			}
			
			WTRateSlabs previousRateSlab = new WTRateSlabs();
			for(int i=0;i<idValues.length-1;i++)
			{
				previousRateSlab = wtRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
				WTRateSlabs currentRateSlabs = wtRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
				
				WTRateMaster wtRateMaster = wtRateMasterService.getStatusofWTRateMaster(previousRateSlab.getWtrmid());
				
				if(wtRateMaster.getWtRateType().equals("Multi Slab"))
				{
					if((previousRateSlab.getWtSlabNo() + 1) == currentRateSlabs.getWtSlabNo())
					{
						previousRateSlab.setWtSlabTo(currentRateSlabs.getWtSlabTo());
						previousRateSlab.setWtRate(newRate);
						previousRateSlab.setWtSlabType(currentRateSlabs.getWtSlabType());
						previousRateSlab.setWtSlabRateType(currentRateSlabs.getWtSlabRateType());
						wtRateSlabService.update(previousRateSlab);
						wtRateSlabService.delete(currentRateSlabs.getWtrsId());
						
						List<WTRateSlabs> wtRateSlabs = wtRateSlabService.getWTRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getWtSlabNo(),previousRateSlab.getWtrmid());
						for (WTRateSlabs wtRateSlabs2 : wtRateSlabs)
						{
							int updateSlabNo = wtRateSlabs2.getWtSlabNo() - 1;
							wtRateSlabs2.setWtSlabNo(updateSlabNo);
							wtRateSlabService.update(wtRateSlabs2);
						}
						
					}
					
					else if((previousRateSlab.getWtSlabNo() - 1) == currentRateSlabs.getWtSlabNo())
					{
						currentRateSlabs.setWtSlabNo(previousRateSlab.getWtSlabNo());
						currentRateSlabs.setWtRate(newRate);
						currentRateSlabs.setWtSlabType(previousRateSlab.getWtSlabType());
						currentRateSlabs.setWtSlabRateType(previousRateSlab.getWtSlabRateType());
						wtRateSlabService.update(currentRateSlabs);
						wtRateSlabService.delete(previousRateSlab.getWtrsId());
						
						List<WTRateSlabs> wtRateSlabs = wtRateSlabService.getWTRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getWtSlabNo(),previousRateSlab.getWtrmid());
						for (WTRateSlabs wtRateSlabs2 : wtRateSlabs)
						{
							int updateSlabNo = wtRateSlabs2.getWtSlabNo() - 1;
							wtRateSlabs2.setWtSlabNo(updateSlabNo);
							wtRateSlabService.update(wtRateSlabs2);
						}
						
					}
				}
				
				if(wtRateMaster.getWtRateType().equals("Multi Slab Telescopic"))
				{
					if((previousRateSlab.getWtSlabNo() + 1) == currentRateSlabs.getWtSlabNo())
					{
						previousRateSlab.setWtSlabTo(currentRateSlabs.getWtSlabTo());
						previousRateSlab.setWtRate(newRate);
						previousRateSlab.setWtSlabType(currentRateSlabs.getWtSlabType());
						previousRateSlab.setWtSlabRateType(currentRateSlabs.getWtSlabRateType());
						wtRateSlabService.update(previousRateSlab);
						wtRateSlabService.delete(currentRateSlabs.getWtrsId());
						
						List<WTRateSlabs> wtRateSlabs = wtRateSlabService.getWTRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getWtSlabNo(),previousRateSlab.getWtrmid());
						
						for (WTRateSlabs wtRateSlabs2 : wtRateSlabs)
						{
							int updateSlabNo = wtRateSlabs2.getWtSlabNo() - 1;
							wtRateSlabs2.setWtSlabNo(updateSlabNo);
							wtRateSlabService.update(wtRateSlabs2);
						}
					}
					
					else if((previousRateSlab.getWtSlabNo() - 1) == currentRateSlabs.getWtSlabNo())
					{
						currentRateSlabs.setWtSlabTo(previousRateSlab.getWtSlabTo());
						currentRateSlabs.setWtRate(newRate);
						currentRateSlabs.setWtSlabType(previousRateSlab.getWtSlabType());
						currentRateSlabs.setWtSlabRateType(previousRateSlab.getWtSlabRateType());
						wtRateSlabService.update(currentRateSlabs);
						wtRateSlabService.delete(previousRateSlab.getWtrsId());
						
						List<WTRateSlabs> wtRateSlabs = wtRateSlabService.getWTRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getWtSlabNo(),previousRateSlab.getWtrmid());
						for (WTRateSlabs wtRateSlabs2 : wtRateSlabs)
						{
							int updateSlabNo = wtRateSlabs2.getWtSlabNo() - 1;
							wtRateSlabs2.setWtSlabNo(updateSlabNo);
							wtRateSlabService.update(wtRateSlabs2);
						}
						
					}
				}
			}
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("SUCCESS");
			errorResponse.setResult("Two records merged successfully");
			return errorResponse;
		}
}
