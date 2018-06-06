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
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.gasTariffManagment.GasRateMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasRateSlabService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class GasRateMasterController
{
	static Logger logger = LoggerFactory.getLogger(GasRateMasterController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private GasRateMasterService gasRateMasterService;
	
	@Autowired
	private GasRateSlabService gasRateSlabService;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@RequestMapping(value = "/gasRateMaster", method = RequestMethod.GET)
	public String gasRateMaster(@ModelAttribute("gasrateSlab") GasRateMaster gasRateSlabs,ModelMap model,HttpServletRequest request,Locale locale) 
	{
		logger.info(":::::::::::::::: In water rate master controller :::::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Gas Rate Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		
		model.addAttribute("gasTariffName", populateCategories1("Active", "Tariff Rate Node"));
		model.addAttribute("gasRateName", messageSource.getMessage("gasRateName", null, locale).split(","));
		model.addAttribute("gasRateType", messageSource.getMessage("gasRateType", null, locale).split(","));
		model.addAttribute("gasRateUOM", messageSource.getMessage("gasRateUOM", null, locale).split(","));
		model.addAttribute("status", messageSource.getMessage("value.gas.status", null, locale).split(","));
		model.addAttribute("gasSlabType", messageSource.getMessage("gasSlabType", null, locale).split(","));
		model.addAttribute("gasSlabRateType", messageSource.getMessage("gasSlabRateType", null, locale).split(","));
		return "gasTariff/gasRateMaster";
	}
	
    private List<?> populateCategories1(String status, String tariffNodeType) 
    {
    List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
    Map<String, Object> mapResult = null;
    List<String> attributesList = new ArrayList<String>();
    attributesList.add("gasTariffId");
    attributesList.add("gastariffName");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("gasStatus", status);
    params.put("gastariffNodetype", tariffNodeType);
    Map<String, Object> orderByList = new HashMap<String, Object>();
    orderByList.put("gastariffName", "ASC");
    
    List<?> categoriesList = commonService.selectQueryOrderBy("GasTariffMaster", attributesList, params, orderByList);
    
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
        attributesList.add("gasTariffId");
        attributesList.add("gastariffName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gasStatus", status);
        params.put("gastariffNodetype", tariffNodeType);
        Map<String, Object> orderByList = new HashMap<String, Object>();
        orderByList.put("gastariffName", "ASC");
        
        List<?> categoriesList = commonService.selectQueryOrderBy("GasTariffMaster", attributesList, params, orderByList);
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        mapResultSelect.put("gasTariffId", 0);
        mapResultSelect.put("gasTariffName", "Select");
		listResult.add(mapResultSelect);
		
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			mapResult = new HashMap<String, Object>();
			mapResult.put("gasTariffId", values[0]);
			mapResult.put("gasTariffName", values[1]);
			
			listResult.add(mapResult);
		}	
        return listResult;
    } 
    
 	@RequestMapping(value = "/gasRateMaster/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createGasRateMaster(@ModelAttribute("gasRatemaster") GasRateMaster gasRateMaster, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
 		logger.info("::::::::::::::::::::::::Creating Gas Rate mastrer :::::::::::::::::::::::::");
 		gasRateMaster.setStatus("Active");
 		gasRateMaster.setGasValidFrom(dateTimeCalender.getDateToStore(req.getParameter("gasValidFrom")));
 		gasRateMaster.setGasValidTo(dateTimeCalender.getDateToStore(req.getParameter("gasValidTo")));
 		gasRateMasterService.save(gasRateMaster);
		return gasRateMaster;
	}
 	
	@RequestMapping(value = "/gasRateMaster/read", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> readGasRateMaster(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		logger.info(":::::::::::::::::::::::: Reading Gas Rate mastrer :::::::::::::::::::::::::");
		String showAll = req.getParameter("showAll");
		List<GasRateMaster> gasRateMastersList = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		if(showAll == null)
		{
			gasRateMastersList = gasRateMasterService.findActive();
		      result = gasRateMasterService.setResponse(gasRateMastersList);
			  logger.info("::::::::::::: reading active data in rate master table ::::::::::::::::::::" +gasRateMastersList.size());
		}
		else 
		{
			gasRateMastersList = gasRateMasterService.findALL();
			result = gasRateMasterService.setResponse(gasRateMastersList);
			logger.info("::::::::::::: reading all data in rate master table ::::::::::::::::::::" +gasRateMastersList.size());
		}
		return result;
	}
	
	@RequestMapping(value = "/gasRateMaster/update", method = RequestMethod.GET)
	public @ResponseBody Object editGasRateMaster(@ModelAttribute("gasratemaster") GasRateMaster gasRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss,HttpServletRequest req) throws ParseException
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+gasRateMaster.getGasrmid()+" is updating");
		gasRateMaster.setGasValidFrom(dateTimeCalender.getDateToStore(req.getParameter("gasValidFrom")));
		gasRateMaster.setGasValidTo(dateTimeCalender.getDateToStore(req.getParameter("gasValidTo")));
		gasRateMasterService.update(gasRateMaster);
		return gasRateMaster;
	}
	
	@RequestMapping(value = "/gasRateMaster/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteGasRateMaster(@ModelAttribute("gasRatemaster") GasRateMaster gasRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss)
	{
		logger.info("The Rate Master withd Id :::::::::::::: "+gasRateMaster.getGasrmid()+" is deleting");
		try{
			gasRateMasterService.delete(gasRateMaster.getGasrmid());
		}
		catch(Exception e){
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return gasRateMaster;
	}
	
	@RequestMapping(value = "/gasTariff/tariffStatus/{gasrmid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void gasTariffStatus(@PathVariable int gasrmid, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("Rate master Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+gasrmid);
		
		if(operation.equalsIgnoreCase("activate"))
			gasRateMasterService.setGasRateMasterStatus(gasrmid, operation, response);

		else
			gasRateMasterService.setGasRateMasterStatus(gasrmid, operation, response);
	}
	
	@RequestMapping(value = "/gasRateMaster/endDate/{gasrmid}", method = { RequestMethod.GET, RequestMethod.POST })
	public void gasRateMasterEndDate(@PathVariable int gasrmid, HttpServletResponse response)
	{		
		//serviceMasterService.serviceEndDateUpdate(gasrmid, response);
		gasRateMasterService.gasRateMasterEndDate(gasrmid, response);
	}
	
/****************************** Filters Data methods for water rate master ********************************/
	
	@RequestMapping(value = "/gasTariff/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getContentsForFilter(@PathVariable String feild) 
	{
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(gasRateMasterService.selectQuery("GasRateMaster", attributeList, null));
		return set;
	}
	@RequestMapping(value = "/gasTariffNameToFilter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> tariffNameToFilter(@PathVariable String feild)
	{
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("gasStatus",  "Active");
        params.put("gastariffNodetype", "Tariff Rate Node");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(gasRateMasterService.selectQuery("GasTariffMaster", attributeList, params));
		return set;
	}
	
	@RequestMapping(value = "/gasRateMaster/getToTariffMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getToTariffMasterComboBoxUrl() 
	{	
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listResult =   (List<Map<String, Object>>) populateCategories("Active", "Tariff Rate Node");
		return listResult;
	}
	
	@RequestMapping(value = "/gasTariff/getMaxDate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Date getMaxDate(HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException 
	{
		int wtTariffId = Integer.parseInt(req.getParameter("gasTariffId"));
		String wtRateName = req.getParameter("gasRateName");
		String wtRateType = req.getParameter("gasRateType");
		logger.info("Search for date where tariff id ::::::::"+wtTariffId+" rate name ::::::::"+wtRateName+" rate type :::::::::"+wtRateType);
	
		List<String> attributesList = new ArrayList<String>();
         attributesList.add("gasValidTo");
         
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("gasRateName", wtRateName);
         params.put("gasTariffId", wtTariffId);
         params.put("gasRateType", wtRateType);
         
 	   	Date maxDate = commonService.getMaxDate("GasRateMaster", attributesList, params);
 		
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

	@RequestMapping(value = "/tariff/gasRateslab/read/{gasrmid}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readRateSlab(@PathVariable int gasrmid, final Locale locale) 
	{
		logger.info("::::::::::::::::::: Reading the Rate slab ::::::::::::::::::: ");
		try
		{
			List<GasRateSlab> gasRateSlabsList = gasRateSlabService.rateSlabListByParentID(gasrmid);
			logger.info("::::::::::::::::::: Number of rate slabs for id  ::::::::::::::::::: "+gasrmid+":: "+gasRateSlabsList.size());
			return  gasRateSlabService.setResponse(gasRateSlabsList);
		}

		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/gasRateslab/create/{gasrmid}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createwWTRateSlab(@ModelAttribute("gasrateslab") GasRateSlab gasRateSlab,BindingResult bindingResult,ModelMap model,@PathVariable int gasrmid,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("::::::::::::::::::: Creating the Rate slab  ::::::::::::::::::: ");
		gasRateSlab.setStatus("Active");
		gasRateSlab.setGasrmId(gasrmid);
		String maxSlabToValue = req.getParameter("gasMaxSlabToValue");
		
		 if(maxSlabToValue.trim().equals("true"))
            {
			 gasRateSlab.setGasSlabTo(999999f);
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+gasRateSlab.getGasSlabNo() +" Assign 999999 to slab to as max value");
            }
            else 
            {
            	logger.info("Maxmium Slab To option ::::::::::::::::: "+gasRateSlab.getGasSlabNo() +" Dont assigne 999999 to slab to as max value");
			}
			
		 if(gasRateSlab.getGasSlabType().trim().equals("Not applicable"))
		 {
			 gasRateSlab.setGasSlabFrom(null);
			 gasRateSlab.setGasSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+gasRateSlab.getGasSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+gasRateSlab.getGasSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
	
		try
		{
		    gasRateSlabService.save(gasRateSlab);	
			List<GasRateSlab> gasRateSlabsList = new ArrayList<>();
			gasRateSlabsList.add(gasRateSlab);
			return gasRateSlabService.setResponse(gasRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/gasRateslab/update", method = RequestMethod.GET)
	public @ResponseBody
	Object editWaterRateSlab(@ModelAttribute("gasRateslab") GasRateSlab gasRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
	{
		 logger.info("::::::::::::::::::: Updating the Rate slab ::::::::::::::::::: ");
		 if(gasRateSlab.getGasSlabType().trim().equals("Not applicable"))
		 {
			 gasRateSlab.setGasSlabFrom(null);
			 gasRateSlab.setGasSlabTo(null);
			 logger.info("Slab type ::::::::::::::::: "+gasRateSlab.getGasSlabNo() +" Assign 'NULL' to slab from and slab to");
		 }
		 else
		 {
			 logger.info("Slab type ::::::::::::::::: "+gasRateSlab.getGasSlabNo() +" dont assign 'NULL' to slab from and slab to");
		 }
		gasRateSlabService.update(gasRateSlab);
		
		try
		{
			List<GasRateSlab> gasRateSlabsList = new ArrayList<>();
			gasRateSlabsList.add(gasRateSlab);
			return gasRateSlabService.setResponse(gasRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/tariff/gasRateslab/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteWaterRateSlab(@ModelAttribute("gasRateslab") GasRateSlab gasRateSlab,BindingResult bindingResult,ModelMap model,SessionStatus ss,final Locale locale)
	{
		logger.info("::::::::::::::::::: Deleting the Rate slab ::::::::::::::::::: ");
		try{
			logger.info("Deleting Rate Slab with Id ::::::::::::::::: "+gasRateSlab.getGasrsId());
			gasRateSlabService.delete(gasRateSlab.getGasrsId());
		}
		catch(Exception e)
		{
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		
		try
		{
			List<GasRateSlab> gasRateSlabsList = new ArrayList<>();
			gasRateSlabsList.add(gasRateSlab);
			 return gasRateSlabService.setResponse(gasRateSlabsList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JsonResponse errorResponse = new JsonResponse();
			return errorResponse.throwException(locale);
		}
	}
	
	 @RequestMapping(value = "/tariff/gasRateSlabStatusUpdateFromInnerGrid/{gasrsId}", method = { RequestMethod.GET, RequestMethod.POST })
	 public void waterRatesSlabStatusUpdateFromInnerGrid(@PathVariable int gasrsId, HttpServletResponse response)
	 {  
		 gasRateSlabService.updateslabStatus(gasrsId, response);
	 }
	 
	 @RequestMapping(value = "/tariff/gasRateslab/rateSlabSplit", method = { RequestMethod.GET, RequestMethod.POST })
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
		
		@RequestMapping(value = "/tariff/gasRateslab/getNewRate", method = {RequestMethod.GET, RequestMethod.POST })
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
						previousRateSlab.setGasSlabTo(currentRateSlabs.getGasSlabTo());
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
						currentRateSlabs.setGasSlabTo(previousRateSlab.getGasSlabTo());
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
						previousRateSlab.setGasSlabTo(currentRateSlabs.getGasSlabTo());
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
						currentRateSlabs.setGasSlabTo(previousRateSlab.getGasSlabTo());
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
		}


}
