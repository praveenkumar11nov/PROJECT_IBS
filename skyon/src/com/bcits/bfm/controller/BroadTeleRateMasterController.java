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

import com.bcits.bfm.model.BroadTeleRateMaster;
import com.bcits.bfm.model.BroadTeleRateSlab;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.broadTeleTariffManagment.BroadTeleRateMasterService;
import com.bcits.bfm.service.broadTeleTariffManagment.BroadTeleRateSlabService;
import com.bcits.bfm.service.gasTariffManagment.GasRateMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasRateSlabService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class BroadTeleRateMasterController 
{
	static Logger logger = LoggerFactory.getLogger(BroadTeleRateMasterController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BroadTeleRateMasterService broadTeleRateMasterService;
	
	@Autowired
	private BroadTeleRateSlabService broadTeleRateSlabService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@RequestMapping(value = "/broadTeleRateMaster", method = RequestMethod.GET)
	public String gasRateMaster(@ModelAttribute("gasrateSlab") GasRateMaster gasRateSlabs,ModelMap model,HttpServletRequest request,Locale locale) 
	{
		logger.info(":::::::::::::::: In BroadBand and telephone rate master controller :::::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Broadband Telecom Rate Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		
		model.addAttribute("broadTeleTariffName", populateCategories1("Active", "Tariff Rate Node"));
		model.addAttribute("broadTeleRateName", messageSource.getMessage("broadTeleRateName", null, locale).split(","));
		model.addAttribute("broadTeleRateType", messageSource.getMessage("broadTeleRateType", null, locale).split(","));
		model.addAttribute("broadTeleRateUOM", messageSource.getMessage("broadTeleRateUOM", null, locale).split(","));
		model.addAttribute("status", messageSource.getMessage("value.broadTele.status", null, locale).split(","));
		model.addAttribute("broadTelSlabType", messageSource.getMessage("broadTelSlabType", null, locale).split(","));
		model.addAttribute("broadTelSlabRateType", messageSource.getMessage("broadTelSlabRateType", null, locale).split(","));
		return "broadTeleTariff/broadTeleTariffRateMaster";
	}
    private List<?> populateCategories1(String status, String tariffNodeType) 
    {
    List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
    Map<String, Object> mapResult = null;
    List<String> attributesList = new ArrayList<String>();
    attributesList.add("broadTeleTariffId");
    attributesList.add("broadTeleTariffName");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("status", status);
    params.put("broadTeleTariffNodetype", tariffNodeType);
    Map<String, Object> orderByList = new HashMap<String, Object>();
    orderByList.put("broadTeleTariffName", "ASC");
    
    List<?> categoriesList = commonService.selectQueryOrderBy("BroadTeleTariffMaster", attributesList, params, orderByList);
    
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
        attributesList.add("broadTeleTariffId");
        attributesList.add("broadTeleTariffName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("broadTeleTariffNodetype", tariffNodeType);
        Map<String, Object> orderByList = new HashMap<String, Object>();
        orderByList.put("broadTeleTariffName", "ASC");
        
        List<?> categoriesList = commonService.selectQueryOrderBy("BroadTeleTariffMaster", attributesList, params, orderByList);
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        mapResultSelect.put("broadTeleTariffId", 0);
        mapResultSelect.put("broadTeleTariffName", "Select");
		listResult.add(mapResultSelect);
		
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			mapResult = new HashMap<String, Object>();
			mapResult.put("broadTeleTariffId", values[0]);
			mapResult.put("broadTeleTariffName", values[1]);
			
			listResult.add(mapResult);
		}	
        return listResult;
    } 
    
 	@RequestMapping(value = "/broadTeleRateMaster/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createGasRateMaster(@ModelAttribute("broadTeleRatemaster") BroadTeleRateMaster broadTeleRateMaster, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
 		logger.info("::::::::::::::::::::::::Creating BroadBand Telephone Rate Master :::::::::::::::::::::::::");
 		broadTeleRateMaster.setStatus("Active");
 		broadTeleRateMaster.setBroadTeleValidFrom(dateTimeCalender.getDateToStore(req.getParameter("broadTeleValidFrom")));
 		broadTeleRateMaster.setBroadTeleValidTo(dateTimeCalender.getDateToStore(req.getParameter("broadTeleValidTo")));
 		broadTeleRateMasterService.save(broadTeleRateMaster);
		return broadTeleRateMaster;
	}
 	
    
	@RequestMapping(value = "/broadTeleRateMaster/read", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> readBroadTeleRateMaster(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		logger.info(":::::::::::::::::::::::: Reading BroadBand and Telephone Rate mastrer :::::::::::::::::::::::::");
		String showAll = req.getParameter("showAll");
		List<BroadTeleRateMaster> broadTeleRateMastersList = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		if(showAll == null)
		{
			broadTeleRateMastersList = broadTeleRateMasterService.findActive();
		    result = broadTeleRateMasterService.setResponse(broadTeleRateMastersList);
			logger.info("::::::::::::: reading active data in rate master table ::::::::::::::::::::" +broadTeleRateMastersList.size());
		}
		else 
		{
			broadTeleRateMastersList = broadTeleRateMasterService.findALL();
			result = broadTeleRateMasterService.setResponse(broadTeleRateMastersList);
			logger.info("::::::::::::: reading all data in rate master table ::::::::::::::::::::" +broadTeleRateMastersList.size());
		}
		return result;
	}
	
	@RequestMapping(value = "/broadTeleRateMaster/update", method = RequestMethod.GET)
	public @ResponseBody Object editGasRateMaster(@ModelAttribute("broadTeleRatemaster") BroadTeleRateMaster broadTeleRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss,HttpServletRequest req) throws ParseException
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+broadTeleRateMaster.getBroadTeleRmid()+" is updating");
		broadTeleRateMaster.setBroadTeleValidFrom(dateTimeCalender.getDateToStore(req.getParameter("broadTeleValidFrom")));
 		broadTeleRateMaster.setBroadTeleValidTo(dateTimeCalender.getDateToStore(req.getParameter("broadTeleValidTo")));
		broadTeleRateMasterService.update(broadTeleRateMaster);
		return broadTeleRateMaster;
	}
	
	@RequestMapping(value = "/broadTeleRateMaster/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteGasRateMaster(@ModelAttribute("broadTeleRatemaster") BroadTeleRateMaster broadTeleRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss)
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+broadTeleRateMaster.getBroadTeleRmid()+" is deleting");
		try{
			broadTeleRateMasterService.delete(broadTeleRateMaster.getBroadTeleRmid());
		}
		catch(Exception e){
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return broadTeleRateMaster;
	}
	
	@RequestMapping(value = "/broadTeleTariff/tariffStatus/{broadTeleRmid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void gasTariffStatus(@PathVariable int broadTeleRmid, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("Rate master Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+broadTeleRmid);
		
		if(operation.equalsIgnoreCase("activate"))
			broadTeleRateMasterService.setBroadTeleRateMasterStatus(broadTeleRmid, operation, response);

		else
			broadTeleRateMasterService.setBroadTeleRateMasterStatus(broadTeleRmid, operation, response);
	}
	
	@RequestMapping(value = "/broadTeleRateMaster/endDate/{broadTeleRmid}", method = { RequestMethod.GET, RequestMethod.POST })
	public void gasRateMasterEndDate(@PathVariable int broadTeleRmid, HttpServletResponse response)
	{		
		broadTeleRateMasterService.broadTeleRateMasterEndDate(broadTeleRmid, response);
	}
	
/****************************** Filters Data methods for water rate master ********************************/
	
	@RequestMapping(value = "/broadTeleTariff/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getContentsForFilter(@PathVariable String feild) 
	{
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(broadTeleRateMasterService.selectQuery("BroadTeleRateMaster", attributeList, null));
		return set;
	}
	@RequestMapping(value = "/broadTeleTariffNameToFilter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> tariffNameToFilter(@PathVariable String feild)
	{
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", "Active");
        params.put("broadTeleTariffNodetype", "Tariff Rate Node");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(broadTeleRateMasterService.selectQuery("BroadTeleTariffMaster", attributeList, params));
		return set;
	}
	
	@RequestMapping(value = "/broadTeleRateMaster/getToTariffMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getToTariffMasterComboBoxUrl() 
	{	
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listResult = (List<Map<String, Object>>) populateCategories("Active", "Tariff Rate Node");
		return listResult;
	}
	
	@RequestMapping(value = "/broadTeleTariff/getMaxDate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Date getMaxDate(HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException 
	{
		int wtTariffId = Integer.parseInt(req.getParameter("broadTeleTariffId"));
		String wtRateName = req.getParameter("broadTeleRateName");
		String wtRateType = req.getParameter("broadTeleRateType");
		logger.info("Search for date where tariff id ::::::::"+wtTariffId+" rate name ::::::::"+wtRateName+" rate type :::::::::"+wtRateType);
	
		List<String> attributesList = new ArrayList<String>();
         attributesList.add("broadTeleValidTo");
         
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("broadTeleRateName", wtRateName);
         params.put("broadTeleTariffId", wtTariffId);
         params.put("broadTeleRateType", wtRateType);
         
 	   	Date maxDate = commonService.getMaxDate("BroadTeleRateMaster", attributesList, params);
 		
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
	/****************************** end  Filters Data methods for BroadBand telephone rate master   ********************************/
	
	/****************************** For BroadBand Telephone Rate Slabs ********************************/

	@RequestMapping(value = "/tariff/broadTeleRateslab/read/{broadTeleRmid}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readRateSlab(@PathVariable int broadTeleRmid, final Locale locale) 
	{
		logger.info("::::::::::::::::::: Reading the Rate slab ::::::::::::::::::: ");
		try
		{
			List<BroadTeleRateSlab> broadTeleRateSlabsList = broadTeleRateSlabService.rateSlabListByParentID(broadTeleRmid);
			logger.info("::::::::::::::::::: Number of rate slabs for id  ::::::::::::::::::: "+broadTeleRmid+":: "+broadTeleRateSlabsList.size());
			return  broadTeleRateSlabService.setResponse(broadTeleRateSlabsList);
		}

		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/broadTeleRateslab/create/{broadTeleRmid}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createwWTRateSlab(@ModelAttribute("broadTelerateslab") BroadTeleRateSlab broadTeleRateSlab,BindingResult bindingResult,ModelMap model,@PathVariable int broadTeleRmid,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("::::::::::::::::::: Creating the Rate slab  ::::::::::::::::::: ");
		
		broadTeleRateSlab.setStatus("Active");
		broadTeleRateSlab.setBroadTelrmId(broadTeleRmid);
		String maxSlabToValue = req.getParameter("broadTeleMaxSlabToValue");
		
		 if(maxSlabToValue.trim().equals("true"))
            {
			 broadTeleRateSlab.setBroadTelSlabTo(999999f);
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+broadTeleRateSlab.getBroadTelSlabNo() +" Assign 999999 to slab to as max value");
            }
            else 
            {
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+broadTeleRateSlab.getBroadTelSlabNo() +" Dont assigne 999999 to slab to as max value");
			}
			
		 if(broadTeleRateSlab.getBroadTelSlabType().trim().equals("Not applicable"))
		 {
			 broadTeleRateSlab.setBroadTelSlabFrom(null);
			 broadTeleRateSlab.setBroadTelSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+broadTeleRateSlab.getBroadTelSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+broadTeleRateSlab.getBroadTelSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
	
		try
		{
		    broadTeleRateSlabService.save(broadTeleRateSlab);	
			List<BroadTeleRateSlab> broadTeleRateSlabsList = new ArrayList<>();
			broadTeleRateSlabsList.add(broadTeleRateSlab);
			return broadTeleRateSlabService.setResponse(broadTeleRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/broadTeleRateslab/update", method = RequestMethod.GET)
	public @ResponseBody
	Object editWaterRateSlab(@ModelAttribute("broadTeleRateSlab") BroadTeleRateSlab broadTeleRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
	{
		 logger.info("::::::::::::::::::: Updating the Rate slab ::::::::::::::::::: ");
		 if(broadTeleRateSlab.getBroadTelSlabType().trim().equals("Not applicable"))
		 {
			 broadTeleRateSlab.setBroadTelSlabFrom(null);
			 broadTeleRateSlab.setBroadTelSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+broadTeleRateSlab.getBroadTelSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+broadTeleRateSlab.getBroadTelSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
		broadTeleRateSlabService.update(broadTeleRateSlab);
		
		try
		{
			List<BroadTeleRateSlab> broadTeleRateSlabsList = new ArrayList<>();
			broadTeleRateSlabsList.add(broadTeleRateSlab);
			return broadTeleRateSlabService.setResponse(broadTeleRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/broadTeleRateslab/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteWaterRateSlab(@ModelAttribute("broadTeleRateslab") BroadTeleRateSlab broadTeleRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus ss,final Locale locale)
	{
		logger.info("::::::::::::::::::: Deleting the Rate slab ::::::::::::::::::: ");
		try{
			logger.info("Deleting Rate Slab with Id ::::::::::::::::: "+broadTeleRateSlab.getBroadTelersId());
			broadTeleRateSlabService.delete(broadTeleRateSlab.getBroadTelersId());
		}
		catch(Exception e)
		{
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		
		try
		{
			List<BroadTeleRateSlab> broadTeleRateSlabsList = new ArrayList<>();
			broadTeleRateSlabsList.add(broadTeleRateSlab);
			 return broadTeleRateSlabService.setResponse(broadTeleRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	 @RequestMapping(value = "/tariff/broadTeleRateSlabStatusUpdateFromInnerGrid/{broadTelersId}", method = { RequestMethod.GET, RequestMethod.POST })
	 public void waterRatesSlabStatusUpdateFromInnerGrid(@PathVariable int broadTelersId, HttpServletResponse response)
	 {  
		 broadTeleRateSlabService.updateslabStatus(broadTelersId, response);
	 }
	 
/*	 @RequestMapping(value = "/tariff/gasRateslab/rateSlabSplit", method = { RequestMethod.GET, RequestMethod.POST })
	 public @ResponseBody Object waterRateSlabSplit(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	 {  
		 logger.info("::::::::::::::::::: Split the rate slab ::::::::::::::::::: ");
		 
		 String gasrsidString = request.getParameter("hiddenwtrsId");
		 GasRateSlab gasRateSlab =  new GasRateSlab();
		 gasRateSlab.setGasSlabNo(Integer.parseInt(request.getParameter("gasSlabNo")));
		 gasRateSlab.setGasRate(Float.parseFloat(request.getParameter("gasRate")));
		 gasRateSlab.setGasSlabFrom(Float.parseFloat(request.getParameter("gasSlabFrom")));
		 gasRateSlab.setGasSlabTo(Float.parseFloat(request.getParameter("gasSlabTo")));
		 
		 GasRateSlab gasRateSlab2 = gasRateSlabService.findRateSlabByPrimayID(Integer.parseInt(gasrsidString));
		 gasRateSlab2.setGasSlabNo(gasRateSlab.getGasSlabNo());
		 gasRateSlab2.setGasRate(gasRateSlab.getGasRate());
		 gasRateSlab2.setGasSlabFrom(gasRateSlab.getGasSlabFrom());
		 gasRateSlab2.setGasSlabTo(gasRateSlab.getGasSlabTo());
		 
		 GasRateSlab gasRateSlab1 =  new GasRateSlab();
		 gasRateSlab1.setGasSlabNo(Integer.parseInt(request.getParameter("wtSlabNo1")));
		 gasRateSlab1.setGasRate(Float.parseFloat(request.getParameter("rate1")));
		 gasRateSlab1.setGasSlabFrom(Float.parseFloat(request.getParameter("slabFrom1")));
		 gasRateSlab1.setGasSlabTo(Float.parseFloat(request.getParameter("slabTo1")));
		 gasRateSlab1.setStatus("Active");
		 gasRateSlab1.setGasSlabType(gasRateSlab2.getGasSlabType());
		 gasRateSlab1.setGasSlabRateType(gasRateSlab2.getGasSlabRateType());
		 gasRateSlab1.setGasrmId(gasRateSlab2.getGasrmId());
		 
		 float form2SlabFrom = gasRateSlab.getGasSlabTo() +1;
		 
		 if(gasRateSlab1.getGasSlabFrom() != form2SlabFrom)
		 {
			    JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Form 2 Slab From shoub be Form1 Slab To +1");
				return errorResponse;
		 }
		 
		 gasRateSlabService.update(gasRateSlab2);
		 
		   List<GasRateSlab> gasRateSlabsList = gasRateSlabService.getElRateSlabGreaterThanUpdateSlabNoEq(gasRateSlab1.getGasSlabNo(),gasRateSlab1.getGasrsId());
			for (GasRateSlab gasRateSlab3 : gasRateSlabsList)
			{
				int updateSlabNo = gasRateSlab3.getGasSlabNo() + 1;
				gasRateSlab3.setGasSlabNo(updateSlabNo);
				gasRateSlabService.update(gasRateSlab3);
			}
		gasRateSlabService.save(gasRateSlab1);
		JsonResponse errorResponse = new JsonResponse();
		errorResponse.setStatus("SUCCESS");
		errorResponse.setResult("Two records split successfully");
		return errorResponse;
	 }
	 

	 @RequestMapping(value = "/tariff/gasRateslab/merge", method = {RequestMethod.GET, RequestMethod.POST })
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

			GasRateSlab previousRateSlab = new GasRateSlab();
			
			if(idValues.length == 2)
			{
				for(int i=0;i<idValues.length-1;i++)
				{
					previousRateSlab = gasRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
					GasRateSlab currentRateSlabs = gasRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
					
					GasRateMaster gasRateMaster = gasRateSlabService.getStatusofGasRateMaster(previousRateSlab.getGasrmId());
					
					if(gasRateMaster.getGasRateType().equals("Multi Slab"))
					{
						if((previousRateSlab.getGasSlabNo() + 1) == currentRateSlabs.getGasSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getGasSlabNo() - 1) == currentRateSlabs.getGasSlabNo())
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
					
					if(gasRateMaster.getGasRateType().equals("Multi Slab Telescopic"))
					{
						if((previousRateSlab.getGasSlabNo() + 1) == currentRateSlabs.getGasSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getGasSlabNo() - 1) == currentRateSlabs.getGasSlabNo())
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
		
		/*@RequestMapping(value = "/tariff/gasRateslab/getNewRate", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object getNewRate(HttpServletRequest request,HttpServletResponse response)
		{
			logger.info(":::::::::::::::::::: Getting New Rate :::::::::::::: ");
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

			Float newRate = Float.parseFloat(rate);
			if (newRate <= 0)
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Rate should not less than 1");
				return errorResponse;
			}
			
			GasRateSlab previousRateSlab = new GasRateSlab();
			for(int i=0;i<idValues.length-1;i++)
			{
				previousRateSlab = gasRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
				GasRateSlab currentRateSlabs = gasRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
				
				GasRateMaster gasRateMaster = gasRateSlabService.getStatusofGasRateMaster(previousRateSlab.getGasrmId());
				
				if(gasRateMaster.getGasRateType().equals("Multi Slab"))
				{
					if((previousRateSlab.getGasSlabNo() + 1) == currentRateSlabs.getGasSlabNo())
					{
						previousRateSlab.setGasSlabNo(currentRateSlabs.getGasSlabNo());;
						previousRateSlab.setGasRate(newRate);
						previousRateSlab.setGasSlabType(currentRateSlabs.getGasSlabType());
						previousRateSlab.setGasSlabRateType(currentRateSlabs.getGasSlabRateType());
						gasRateSlabService.update(previousRateSlab);
						gasRateSlabService.delete(currentRateSlabs.getGasrsId());
						
						List<GasRateSlab> wtRateSlabs = gasRateSlabService.getGasRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getGasSlabNo(),previousRateSlab.getGasrmId());
						for (GasRateSlab gasRateSlabs2 : wtRateSlabs)
						{
							int updateSlabNo = gasRateSlabs2.getGasSlabNo() - 1;
							gasRateSlabs2.setGasSlabNo(updateSlabNo);
							gasRateSlabService.update(gasRateSlabs2);
						}
						
					}
					
					else if((previousRateSlab.getGasSlabNo() - 1) == currentRateSlabs.getGasSlabNo())
					{
						currentRateSlabs.setGasSlabNo(previousRateSlab.getGasSlabNo());
						currentRateSlabs.setGasRate(newRate);
						currentRateSlabs.setGasSlabType(previousRateSlab.getGasSlabType());
						currentRateSlabs.setGasSlabRateType(previousRateSlab.getGasSlabRateType());
						gasRateSlabService.update(currentRateSlabs);
						gasRateSlabService.delete(previousRateSlab.getGasrsId());
						
						List<GasRateSlab> gasRateSlabs = gasRateSlabService.getGasRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getGasSlabNo(),previousRateSlab.getGasrsId());
						for (GasRateSlab gasRateSlabs2 : gasRateSlabs)
						{
							int updateSlabNo = gasRateSlabs2.getGasSlabNo() - 1;
							gasRateSlabs2.setGasrsId(updateSlabNo);
							gasRateSlabService.update(gasRateSlabs2);
						}
						
					}
				}
				
				if(gasRateMaster.getGasRateType().equals("Multi Slab Telescopic"))
				{
					if((previousRateSlab.getGasSlabNo() + 1) == currentRateSlabs.getGasSlabNo())
					{
						previousRateSlab.setGasSlabNo(currentRateSlabs.getGasSlabNo());;
						previousRateSlab.setGasRate(newRate);
						previousRateSlab.setGasSlabType(currentRateSlabs.getGasSlabType());
						previousRateSlab.setGasSlabRateType(currentRateSlabs.getGasSlabRateType());
						gasRateSlabService.update(previousRateSlab);
						gasRateSlabService.delete(currentRateSlabs.getGasrsId());
						
						List<GasRateSlab> gasRateSlab = gasRateSlabService.getGasRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getGasSlabNo(),previousRateSlab.getGasrmId());
						
						for (GasRateSlab gasRateSlabs2 : gasRateSlab)
						{
							int updateSlabNo = gasRateSlabs2.getGasSlabNo() - 1;
							gasRateSlabs2.setGasSlabNo(updateSlabNo);
							gasRateSlabService.update(gasRateSlabs2);
						}
					}
					
					else if((previousRateSlab.getGasSlabNo() - 1) == currentRateSlabs.getGasSlabNo())
					{
						currentRateSlabs.setGasSlabNo(previousRateSlab.getGasSlabNo());
						currentRateSlabs.setGasRate(newRate);
						currentRateSlabs.setGasSlabType(previousRateSlab.getGasSlabType());
						currentRateSlabs.setGasSlabRateType(previousRateSlab.getGasSlabRateType());
						gasRateSlabService.update(currentRateSlabs);
						gasRateSlabService.delete(previousRateSlab.getGasrsId());
						
						List<GasRateSlab> gasRateSlabs = gasRateSlabService.getGasRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getGasSlabNo(),previousRateSlab.getGasrsId());
						for (GasRateSlab gasRateSlabs2 : gasRateSlabs)
						{
							int updateSlabNo = gasRateSlabs2.getGasSlabNo() - 1;
							gasRateSlabs2.setGasrsId(updateSlabNo);
							gasRateSlabService.update(gasRateSlabs2);
						}
						
						
					}
				}
			}
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("SUCCESS");
			errorResponse.setResult("Two records merged successfully");
			return errorResponse;
		}*/

	
	
	
}
