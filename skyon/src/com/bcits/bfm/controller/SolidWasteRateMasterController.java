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

import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteRateMasterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteRateSlabService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class SolidWasteRateMasterController 
{
	static Logger logger = LoggerFactory.getLogger(SolidWasteRateMasterController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SolidWasteRateMasterService solidWasteRateMasterService;
	
	@Autowired
	private SolidWasteRateSlabService solidWasteRateSlabService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	@RequestMapping(value = "/solidWasteRateMaster", method = RequestMethod.GET)
	public String solidWasteRateMaster(@ModelAttribute("solidWasterateSlab") SolidWasteRateMaster solidWasteRateSlabs,ModelMap model,HttpServletRequest request,Locale locale) 
	{
		logger.info(":::::::::::::::: In Solid Waste rate master controller :::::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Waste Rate Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
	
		model.addAttribute("solidWasteTariffName", populateCategories1("Active", "Tariff Rate Node"));
		model.addAttribute("solidWasteRateName", messageSource.getMessage("solidWasteRateName", null, locale).split(","));
		model.addAttribute("solidWasteRateType", messageSource.getMessage("solidWasteRateType", null, locale).split(","));
		model.addAttribute("solidWasteRateUOM", messageSource.getMessage("solidWasteRateUOM", null, locale).split(","));
		model.addAttribute("status", messageSource.getMessage("value.solidWaste.status", null, locale).split(","));
		model.addAttribute("solidWasteSlabType", messageSource.getMessage("solidWasteSlabType", null, locale).split(","));
		model.addAttribute("solidWasteSlabRateType", messageSource.getMessage("solidWasteSlabRateType", null, locale).split(","));
		return "solidWasteTariff/solidWasterRateMaster";
	}
	
    private List<?> populateCategories1(String status, String tariffNodeType) 
    {
    List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
    Map<String, Object> mapResult = null;
    List<String> attributesList = new ArrayList<String>();
    attributesList.add("solidWasteTariffId");
    attributesList.add("solidWasteTariffName");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("status", status);
    params.put("solidWastetariffNodetype", tariffNodeType);
    Map<String, Object> orderByList = new HashMap<String, Object>();
    orderByList.put("solidWasteTariffName", "ASC");
    
    List<?> categoriesList = commonService.selectQueryOrderBy("SolidWasteTariffMaster", attributesList, params, orderByList);
    
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
        attributesList.add("solidWasteTariffId");
        attributesList.add("solidWasteTariffName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("solidWastetariffNodetype", tariffNodeType);
        Map<String, Object> orderByList = new HashMap<String, Object>();
        orderByList.put("solidWasteTariffName", "ASC");
        
        List<?> categoriesList = commonService.selectQueryOrderBy("SolidWasteTariffMaster", attributesList, params, orderByList);
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        mapResultSelect.put("solidWasteTariffId", 0);
        mapResultSelect.put("solidWasteTariffName", "Select");
		listResult.add(mapResultSelect);
		
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			mapResult = new HashMap<String, Object>();
			mapResult.put("solidWasteTariffId", values[0]);
			mapResult.put("solidWasteTariffName", values[1]);
			
			listResult.add(mapResult);
		}	
        return listResult;
    } 
    
 	@RequestMapping(value = "/solidWasteRateMaster/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> createSolidWasteRateMaster(@ModelAttribute("solidWasteRatemaster") SolidWasteRateMaster solidWasteRateMaster, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
 		logger.info("::::::::::::::::::::::::Creating Solid Waster Rate mastrer :::::::::::::::::::::::::");
 		solidWasteRateMaster.setStatus("Active");
 		solidWasteRateMaster.setSolidWasteValidFrom(dateTimeCalender.getDateToStore(req.getParameter("solidWasteValidFrom")));
 		solidWasteRateMaster.setSolidWasteValidTo(dateTimeCalender.getDateToStore(req.getParameter("solidWasteValidTo")));
 		/*logger.info("tariff name :"+solidWasteRateMaster.getSolidWasteTariffName());
 		logger.info("rate name :"+solidWasteRateMaster.getSolidWasteRateName());
 		logger.info("rate des "+solidWasteRateMaster.getSolidWasteRateDescription());
 		logger.info("rate type :"+solidWasteRateMaster.getSolidWasteRateType());
 		logger.info("Rate UOM :"+solidWasteRateMaster.getSolidWasteRateUOM());
 		logger.info("vallid from :"+solidWasteRateMaster.getSolidWasteValidFrom());
 		logger.info("valid to :"+solidWasteRateMaster.getSolidWasteValidTo());
 		logger.info("Min "+solidWasteRateMaster.getSolidWasteMinRate());
 		logger.info("Max :"+solidWasteRateMaster.getSolidWasteMaxRate());*/
 		solidWasteRateMasterService.save(solidWasteRateMaster);
 		List<SolidWasteRateMaster> solidWasteRateMastersList = new ArrayList<>();
 		solidWasteRateMastersList.add(solidWasteRateMaster);
	    return solidWasteRateMasterService.setResponse(solidWasteRateMastersList);
	}
 	
	@RequestMapping(value = "/solidWasteRateMaster/read", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> readSolidWasteRateMaster(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		logger.info(":::::::::::::::::::::::: Reading SolidWaste Rate mastrer :::::::::::::::::::::::::");
		String showAll = req.getParameter("showAll");
		logger.info(":::::::::::::::::::::::: Show all option is ::::::::::::::::::::::::: " +showAll);
		List<SolidWasteRateMaster> solidWasteRateMastersList = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		if(showAll == null)
		{
			  solidWasteRateMastersList = solidWasteRateMasterService.findActive();
		      logger.info("::::::::::::: reading active data in rate master table ::::::::::::::::::::" +solidWasteRateMastersList.size());
		      result = solidWasteRateMasterService.setResponse(solidWasteRateMastersList);
			
		}
		else 
		{
			solidWasteRateMastersList = solidWasteRateMasterService.findALL();
			logger.info("::::::::::::: reading all data in rate master table ::::::::::::::::::::" +solidWasteRateMastersList.size());
			result = solidWasteRateMasterService.setResponse(solidWasteRateMastersList);
			
		}
		return result;
	}
	
	@RequestMapping(value = "/solidWasteRateMaster/update", method = RequestMethod.GET)
	public @ResponseBody Object editSolidWasteRateMaster(@ModelAttribute("solidWasteratemaster") SolidWasteRateMaster solidWasteRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss,HttpServletRequest req) throws ParseException
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+solidWasteRateMaster.getSolidWasteRmId()+" is updating");
		solidWasteRateMaster.setSolidWasteValidFrom(dateTimeCalender.getDateToStore(req.getParameter("solidWasteValidFrom")));
		solidWasteRateMaster.setSolidWasteValidTo(dateTimeCalender.getDateToStore(req.getParameter("solidWasteValidTo")));
		solidWasteRateMasterService.update(solidWasteRateMaster);
		List<SolidWasteRateMaster> solidWasteRateMastersList = new ArrayList<>();
 		solidWasteRateMastersList.add(solidWasteRateMaster);
	    return solidWasteRateMasterService.setResponse(solidWasteRateMastersList);
	}
	
	@RequestMapping(value = "/solidWasteRateMaster/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteSolidWasteRateMaster(@ModelAttribute("solidWasteRatemaster") SolidWasteRateMaster solidWasteRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss)
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+solidWasteRateMaster.getSolidWasteRmId()+" is deleting");
		try{
			solidWasteRateMasterService.delete(solidWasteRateMaster.getSolidWasteRmId());
			List<SolidWasteRateMaster> solidWasteRateMastersList = new ArrayList<>();
	 		solidWasteRateMastersList.add(solidWasteRateMaster);
		    return solidWasteRateMasterService.setResponse(solidWasteRateMastersList);
		}
		catch(Exception e)
		{
			logger.info(":::::::::::: Exception while deleting the rate master :::::::::::::");
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
	}
	
	@RequestMapping(value = "/solidWasteTariff/tariffStatus/{solidWasteRmId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void solidWasteTariffStatus(@PathVariable int solidWasteRmId, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("Rate master Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+solidWasteRmId);
		
		if(operation.equalsIgnoreCase("activate"))
			solidWasteRateMasterService.setSolidWasteRateMasterStatus(solidWasteRmId, operation, response);

		else
			solidWasteRateMasterService.setSolidWasteRateMasterStatus(solidWasteRmId, operation, response);
	}
	
	@RequestMapping(value = "/solidWasteRateMaster/endDate/{solidWasteRmId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void solidWasteRateMasterEndDate(@PathVariable int solidWasteRmId, HttpServletResponse response)
	{		
		//serviceMasterService.serviceEndDateUpdate(solidWasteRmId, response);
		solidWasteRateMasterService.solidWasteRateMasterEndDate(solidWasteRmId, response);
	}
	
/****************************** Filters Data methods for water rate master ********************************/
	
	@RequestMapping(value = "/solidWasteTariff/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getContentsForFilter(@PathVariable String feild) 
	{
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(solidWasteRateMasterService.selectQuery("SolidWasteRateMaster", attributeList, null));
		return set;
	}
	@RequestMapping(value = "/solidWasteTariffNameToFilter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> tariffNameToFilter(@PathVariable String feild)
	{
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("status",  "Active");
        params.put("solidWastetariffNodetype", "Tariff Rate Node");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(solidWasteRateMasterService.selectQuery("SolidWasteTariffMaster", attributeList, params));
		return set;
	}
	
	@RequestMapping(value = "/solidWasteRateMaster/getToTariffMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getToTariffMasterComboBoxUrl() 
	{	
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listResult =   (List<Map<String, Object>>) populateCategories("Active", "Tariff Rate Node");
		return listResult;
	}
	
	@RequestMapping(value = "/solidWasteTariff/getMaxDate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Date getMaxDate(HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException 
	{
		int wtTariffId = Integer.parseInt(req.getParameter("solidWasteTariffId"));
		String wtRateName = req.getParameter("solidWasteRateName");
		String wtRateType = req.getParameter("solidWasteRateType");
		logger.info("Search for date where tariff id ::::::::"+wtTariffId+" rate name ::::::::"+wtRateName+" rate type :::::::::"+wtRateType);
	
		List<String> attributesList = new ArrayList<String>();
         attributesList.add("solidWasteValidTo");
         
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("solidWasteRateName", wtRateName);
         params.put("solidWasteTariffId", wtTariffId);
         params.put("solidWasteRateType", wtRateType);
         
 	   	Date maxDate = commonService.getMaxDate("SolidWasteRateMaster", attributesList, params);
 		
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

	@RequestMapping(value = "/tariff/solidWasteRateslab/read/{solidWasteRmId}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readRateSlab(@PathVariable int solidWasteRmId, final Locale locale) 
	{
		logger.info("::::::::::::::::::: Reading the Rate slab ::::::::::::::::::: ");
		try
		{
			List<SolidWasteRateSlab> solidWasteRateSlabsList = solidWasteRateSlabService.rateSlabListByParentID(solidWasteRmId);
			logger.info("::::::::::::::::::: Number of rate slabs for id  ::::::::::::::::::: "+solidWasteRmId+":: "+solidWasteRateSlabsList.size());
			return  solidWasteRateSlabService.setResponse(solidWasteRateSlabsList);
		}

		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/solidWasteRateslab/create/{solidWasteRmId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createwWTRateSlab(@ModelAttribute("solidWasterateslab") SolidWasteRateSlab solidWasteRateSlab,BindingResult bindingResult,ModelMap model,@PathVariable int solidWasteRmId,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("::::::::::::::::::: Creating the Rate slab  ::::::::::::::::::: ");
		logger.info("solidWasteRmId ::::::::: "+solidWasteRmId);
		solidWasteRateSlab.setStatus("Active");
		solidWasteRateSlab.setSolidWasteRmId(solidWasteRmId);;
		String maxSlabToValue = req.getParameter("solidWasteMaxSlabToValue");
		
		 if(maxSlabToValue.trim().equals("true"))
            {
			 solidWasteRateSlab.setSolidWasteSlabTo(999999f);
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteSlabNo() +" Assign 999999 to slab to as max value");
            }
            else 
            {
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteSlabNo() +" Dont assigne 999999 to slab to as max value");
			}
			
		 if(solidWasteRateSlab.getSolidWasteSlabType().trim().equals("Not applicable"))
		 {
			 solidWasteRateSlab.setSolidWasteSlabFrom(null);
			 solidWasteRateSlab.setSolidWasteSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
	
		try
		{
		    solidWasteRateSlabService.save(solidWasteRateSlab);	
			List<SolidWasteRateSlab> solidWasteRateSlabsList = new ArrayList<>();
			solidWasteRateSlabsList.add(solidWasteRateSlab);
			return solidWasteRateSlabService.setResponse(solidWasteRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/solidWasteRateslab/update", method = RequestMethod.GET)
	public @ResponseBody
	Object editWaterRateSlab(@ModelAttribute("solidWasteRateslab") SolidWasteRateSlab solidWasteRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
	{
		 logger.info("::::::::::::::::::: Updating the Rate slab ::::::::::::::::::: ");
		 if(solidWasteRateSlab.getSolidWasteSlabType().trim().equals("Not applicable"))
		 {
			 solidWasteRateSlab.setSolidWasteSlabFrom(null);
			 solidWasteRateSlab.setSolidWasteSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
		solidWasteRateSlabService.update(solidWasteRateSlab);
		
		try
		{
			List<SolidWasteRateSlab> solidWasteRateSlabsList = new ArrayList<>();
			solidWasteRateSlabsList.add(solidWasteRateSlab);
			return solidWasteRateSlabService.setResponse(solidWasteRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/solidWasteRateslab/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteWaterRateSlab(@ModelAttribute("solidWasteRateslab") SolidWasteRateSlab solidWasteRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus ss,final Locale locale)
	{
		logger.info("::::::::::::::::::: Deleting the Rate slab ::::::::::::::::::: ");
		try{
			logger.info("Deleting Rate Slab with Id ::::::::::::::::: "+solidWasteRateSlab.getSolidWasteRsId());
			solidWasteRateSlabService.delete(solidWasteRateSlab.getSolidWasteRsId());
		}
		catch(Exception e)
		{
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		
		try
		{
			List<SolidWasteRateSlab> solidWasteRateSlabsList = new ArrayList<>();
			solidWasteRateSlabsList.add(solidWasteRateSlab);
			return solidWasteRateSlabService.setResponse(solidWasteRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	 @RequestMapping(value = "/tariff/solidWasteRateSlabStatusUpdateFromInnerGrid/{solidWasteRsId}", method = { RequestMethod.GET, RequestMethod.POST })
	 public void waterRatesSlabStatusUpdateFromInnerGrid(@PathVariable int solidWasteRsId, HttpServletResponse response)
	 {  
		 solidWasteRateSlabService.updateslabStatus(solidWasteRsId, response);
	 }
	 
	 
	 @RequestMapping(value = "/tariff/solidWasteRateslab/rateSlabSplit", method = { RequestMethod.GET, RequestMethod.POST })
	 public @ResponseBody Object waterRateSlabSplit(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	 {  
		 logger.info("::::::::::::::::::: Split the rate slab ::::::::::::::::::: ");
		 
		 String solidWastersidString = request.getParameter("hiddenwtrsId");
		 SolidWasteRateSlab solidWasteRateSlab =  new SolidWasteRateSlab();
		 solidWasteRateSlab.setSolidWasteSlabNo(Integer.parseInt(request.getParameter("solidWasteSlabNo")));
		 solidWasteRateSlab.setSolidWasteRate(Float.parseFloat(request.getParameter("solidWasteRate")));
		 solidWasteRateSlab.setSolidWasteSlabFrom(Float.parseFloat(request.getParameter("solidWasteSlabFrom")));
		 solidWasteRateSlab.setSolidWasteSlabTo(Float.parseFloat(request.getParameter("solidWasteSlabTo")));
		 
		 SolidWasteRateSlab solidWasteRateSlab2 = solidWasteRateSlabService.findRateSlabByPrimayID(Integer.parseInt(solidWastersidString));
		 solidWasteRateSlab2.setSolidWasteSlabNo(solidWasteRateSlab.getSolidWasteSlabNo());
		 solidWasteRateSlab2.setSolidWasteRate(solidWasteRateSlab.getSolidWasteRate());
		 solidWasteRateSlab2.setSolidWasteSlabFrom(solidWasteRateSlab.getSolidWasteSlabFrom());
		 solidWasteRateSlab2.setSolidWasteSlabTo(solidWasteRateSlab.getSolidWasteSlabTo());
		 
		 SolidWasteRateSlab solidWasteRateSlab1 =  new SolidWasteRateSlab();
		 solidWasteRateSlab1.setSolidWasteSlabNo(Integer.parseInt(request.getParameter("wtSlabNo1")));
		 solidWasteRateSlab1.setSolidWasteRate(Float.parseFloat(request.getParameter("rate1")));
		 solidWasteRateSlab1.setSolidWasteSlabFrom(Float.parseFloat(request.getParameter("slabFrom1")));
		 solidWasteRateSlab1.setSolidWasteSlabTo(Float.parseFloat(request.getParameter("slabTo1")));
		 solidWasteRateSlab1.setStatus("Active");
		 solidWasteRateSlab1.setSolidWasteSlabType(solidWasteRateSlab2.getSolidWasteSlabType());
		 solidWasteRateSlab1.setSolidWasteSlabRateType(solidWasteRateSlab2.getSolidWasteSlabRateType());
		 solidWasteRateSlab1.setSolidWasteRmId(solidWasteRateSlab2.getSolidWasteRmId());
		 
		 float form2SlabFrom = solidWasteRateSlab.getSolidWasteSlabTo() +1;
		 
		 if(solidWasteRateSlab1.getSolidWasteSlabFrom() != form2SlabFrom)
		 {
			    JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Form 2 Slab From shoub be Form1 Slab To +1");
				return errorResponse;
		 }
		 
		 solidWasteRateSlabService.update(solidWasteRateSlab2);
		 
		   List<SolidWasteRateSlab> solidWasteRateSlabsList = solidWasteRateSlabService.getElRateSlabGreaterThanUpdateSlabNoEq(solidWasteRateSlab1.getSolidWasteSlabNo(),solidWasteRateSlab1.getSolidWasteRsId());
			for (SolidWasteRateSlab solidWasteRateSlab3 : solidWasteRateSlabsList)
			{
				int updateSlabNo = solidWasteRateSlab3.getSolidWasteSlabNo() + 1;
				solidWasteRateSlab3.setSolidWasteSlabNo(updateSlabNo);
				solidWasteRateSlabService.update(solidWasteRateSlab3);
			}
		solidWasteRateSlabService.save(solidWasteRateSlab1);
		JsonResponse errorResponse = new JsonResponse();
		errorResponse.setStatus("SUCCESS");
		errorResponse.setResult("Two records split successfully");
		return errorResponse;
	 }
	 

	 @RequestMapping(value = "/tariff/solidWasteRateslab/merge", method = {RequestMethod.GET, RequestMethod.POST })
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

			SolidWasteRateSlab previousRateSlab = new SolidWasteRateSlab();
			
			if(idValues.length == 2)
			{
				for(int i=0;i<idValues.length-1;i++)
				{
					previousRateSlab = solidWasteRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
					SolidWasteRateSlab currentRateSlabs = solidWasteRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
					
					SolidWasteRateMaster solidWasteRateMaster = solidWasteRateSlabService.getStatusofSolidWasteRateMaster(previousRateSlab.getSolidWasteRmId());
					
					if(solidWasteRateMaster.getSolidWasteRateType().equals("Multi Slab"))
					{
						if((previousRateSlab.getSolidWasteSlabNo() + 1) == currentRateSlabs.getSolidWasteSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getSolidWasteSlabNo() - 1) == currentRateSlabs.getSolidWasteSlabNo())
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
					
					if(solidWasteRateMaster.getSolidWasteRateType().equals("Multi Slab Telescopic"))
					{
						if((previousRateSlab.getSolidWasteSlabNo() + 1) == currentRateSlabs.getSolidWasteSlabNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getSolidWasteSlabNo() - 1) == currentRateSlabs.getSolidWasteSlabNo())
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
		
		@RequestMapping(value = "/tariff/solidWasteRateslab/getNewRate", method = {RequestMethod.GET, RequestMethod.POST })
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
			
			SolidWasteRateSlab previousRateSlab = new SolidWasteRateSlab();
			for(int i=0;i<idValues.length-1;i++)
			{
				previousRateSlab = solidWasteRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
				SolidWasteRateSlab currentRateSlabs = solidWasteRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
				
				SolidWasteRateMaster solidWasteRateMaster = solidWasteRateSlabService.getStatusofSolidWasteRateMaster(previousRateSlab.getSolidWasteRmId());
				
				if(solidWasteRateMaster.getSolidWasteRateType().equals("Multi Slab"))
				{
					if((previousRateSlab.getSolidWasteSlabNo() + 1) == currentRateSlabs.getSolidWasteSlabNo())
					{
						previousRateSlab.setSolidWasteSlabTo(currentRateSlabs.getSolidWasteSlabTo());
						previousRateSlab.setSolidWasteRate(newRate);
						previousRateSlab.setSolidWasteSlabType(currentRateSlabs.getSolidWasteSlabType());
						previousRateSlab.setSolidWasteSlabRateType(currentRateSlabs.getSolidWasteSlabRateType());
						solidWasteRateSlabService.update(previousRateSlab);
						solidWasteRateSlabService.delete(currentRateSlabs.getSolidWasteRsId());
						
						List<SolidWasteRateSlab> wtRateSlabs = solidWasteRateSlabService.getSolidWasteRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSolidWasteSlabNo(),previousRateSlab.getSolidWasteRmId());
						for (SolidWasteRateSlab solidWasteRateSlabs2 : wtRateSlabs)
						{
							int updateSlabNo = solidWasteRateSlabs2.getSolidWasteSlabNo() - 1;
							solidWasteRateSlabs2.setSolidWasteSlabNo(updateSlabNo);
							solidWasteRateSlabService.update(solidWasteRateSlabs2);
						}
						
					}
					
					else if((previousRateSlab.getSolidWasteSlabNo() - 1) == currentRateSlabs.getSolidWasteSlabNo())
					{
						currentRateSlabs.setSolidWasteSlabTo(previousRateSlab.getSolidWasteSlabTo());
						currentRateSlabs.setSolidWasteRate(newRate);
						currentRateSlabs.setSolidWasteSlabType(previousRateSlab.getSolidWasteSlabType());
						currentRateSlabs.setSolidWasteSlabRateType(previousRateSlab.getSolidWasteSlabRateType());
						solidWasteRateSlabService.update(currentRateSlabs);
						solidWasteRateSlabService.delete(previousRateSlab.getSolidWasteRsId());
						
						List<SolidWasteRateSlab> solidWasteRateSlabs = solidWasteRateSlabService.getSolidWasteRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSolidWasteSlabNo(),previousRateSlab.getSolidWasteRsId());
						for (SolidWasteRateSlab solidWasteRateSlabs2 : solidWasteRateSlabs)
						{
							int updateSlabNo = solidWasteRateSlabs2.getSolidWasteSlabNo() - 1;
							solidWasteRateSlabs2.setSolidWasteRsId(updateSlabNo);
							solidWasteRateSlabService.update(solidWasteRateSlabs2);
						}
						
					}
				}
				
				if(solidWasteRateMaster.getSolidWasteRateType().equals("Multi Slab Telescopic"))
				{
					if((previousRateSlab.getSolidWasteSlabNo() + 1) == currentRateSlabs.getSolidWasteSlabNo())
					{
						previousRateSlab.setSolidWasteSlabTo(currentRateSlabs.getSolidWasteSlabTo());
						previousRateSlab.setSolidWasteRate(newRate);
						previousRateSlab.setSolidWasteSlabType(currentRateSlabs.getSolidWasteSlabType());
						previousRateSlab.setSolidWasteSlabRateType(currentRateSlabs.getSolidWasteSlabRateType());
						solidWasteRateSlabService.update(previousRateSlab);
						solidWasteRateSlabService.delete(currentRateSlabs.getSolidWasteRsId());
						
						List<SolidWasteRateSlab> solidWasteRateSlab = solidWasteRateSlabService.getSolidWasteRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSolidWasteSlabNo(),previousRateSlab.getSolidWasteRmId());
						
						for (SolidWasteRateSlab solidWasteRateSlabs2 : solidWasteRateSlab)
						{
							int updateSlabNo = solidWasteRateSlabs2.getSolidWasteSlabNo() - 1;
							solidWasteRateSlabs2.setSolidWasteSlabNo(updateSlabNo);
							solidWasteRateSlabService.update(solidWasteRateSlabs2);
						}
					}
					
					else if((previousRateSlab.getSolidWasteSlabNo() - 1) == currentRateSlabs.getSolidWasteSlabNo())
					{
						currentRateSlabs.setSolidWasteSlabTo(previousRateSlab.getSolidWasteSlabTo());
						currentRateSlabs.setSolidWasteRate(newRate);
						currentRateSlabs.setSolidWasteSlabType(previousRateSlab.getSolidWasteSlabType());
						currentRateSlabs.setSolidWasteSlabRateType(previousRateSlab.getSolidWasteSlabRateType());
						solidWasteRateSlabService.update(currentRateSlabs);
						solidWasteRateSlabService.delete(previousRateSlab.getSolidWasteRsId());
						
						List<SolidWasteRateSlab> solidWasteRateSlabs = solidWasteRateSlabService.getSolidWasteRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSolidWasteSlabNo(),previousRateSlab.getSolidWasteRsId());
						for (SolidWasteRateSlab solidWasteRateSlabs2 : solidWasteRateSlabs)
						{
							int updateSlabNo = solidWasteRateSlabs2.getSolidWasteSlabNo() - 1;
							solidWasteRateSlabs2.setSolidWasteRsId(updateSlabNo);
							solidWasteRateSlabService.update(solidWasteRateSlabs2);
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
