package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ELTodRates;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.tariffManagement.ELRateMasterService;
import com.bcits.bfm.service.tariffManagement.ELRateSlabService;
import com.bcits.bfm.service.tariffManagement.ELTODRatesService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.tariffManagement.EL_Tariff_MasterServices;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class RateMasterController
{
	static Logger logger = LoggerFactory.getLogger(RateMasterController.class);

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private ELRateMasterService elRateMasterService;
	
	@Autowired
	private ELRateSlabService elRateSlabService;
	
	@Autowired
	private ELTODRatesService eltodRatesService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private JsonResponse errorResponse;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private ELTariffMasterService elTariffMasterService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	 private EL_Tariff_MasterServices tariffMasterService;

	   @RequestMapping(value = "/rateMaster", method = RequestMethod.GET)
		public String rateMaster(@ModelAttribute("elrateSlab") ELRateSlabs elRateSlabs,ModelMap model,HttpServletRequest request,Locale locale) 
		{
			logger.info("In Rate Master Method");
			model.addAttribute("ViewName", " Tariff");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Tariff", 1, request);
			breadCrumbService.addNode("Manage ElectricityRate Master", 2, request);
			model.addAttribute("username",SessionData.getUserDetails().get("userID"));
			model.addAttribute("RateSlab", commonController.populateCategories("elrsId", "slabsNo", "ELRateSlabs"));
			model.addAttribute("rateTypes", messageSource.getMessage("rateType", null, locale).split(","));
			model.addAttribute("rateUOM", messageSource.getMessage("rateUOM", null, locale).split(","));
			model.addAttribute("status", messageSource.getMessage("value.rateMaster.status", null, locale).split(","));
			model.addAttribute("rateName", messageSource.getMessage("rateName", null, locale).split(","));
			model.addAttribute("todType", messageSource.getMessage("todType", null, locale).split(","));
			model.addAttribute("slabType", messageSource.getMessage("slabType", null, locale).split(","));
			model.addAttribute("slabRateType", messageSource.getMessage("slabRateType", null, locale).split(","));
			model.addAttribute("todRateType", messageSource.getMessage("todRateType", null, locale).split(","));
			return "tariff/rateMaster";
		}

	    private List<?> populateCategories(String status, String tariffNodeType) 
	    {
	        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
	        Map<String, Object> mapResult = null;
	        
	        List<String> attributesList = new ArrayList<String>();
	        attributesList.add("elTariffID");
	        attributesList.add("tariffName");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("status", status);
	        params.put("tariffNodetype", tariffNodeType);
	        
	        Map<String, Object> orderByList = new HashMap<String, Object>();
	        orderByList.put("tariffName", "ASC");
	        
	        List<?> categoriesList = commonService.selectQueryOrderBy("ELTariffMaster", attributesList, params, orderByList);
	        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
	        mapResultSelect.put("elTariffID", 0);
	        mapResultSelect.put("tariffName", "Select");
			listResult.add(mapResultSelect);
			
			for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
			{
				final Object[] values = (Object[]) i.next();
				
				mapResult = new HashMap<String, Object>();
				mapResult.put("elTariffID", values[0]);
				mapResult.put("tariffName", values[1]);
				
				listResult.add(mapResult);
			}	
	        return listResult;
	    } 
	    
	 	@RequestMapping(value = "/elRateMaster/create", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody
		Object updateUsers(@ModelAttribute("elratemaster") ELRateMaster elRateMaster, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
		{
			elRateMaster.setStatus("Active");		
			elRateMaster.setValidFrom(dateTimeCalender.getDateToStore(req.getParameter("validFrom")));
			elRateMaster.setValidTo(dateTimeCalender.getDateToStore(req.getParameter("validTo")));
			final String tariffName = elRateMasterService.getTariffMasterByTariffId(elRateMaster.getElTariffID());
			elRateMaster.setTariffName(tariffName);
			elRateMasterService.save(elRateMaster);
			return elRateMaster;
		}
		
		@RequestMapping(value = "/elRateMaster/read", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<Map<String, Object>> readELRateMaster(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
		{
			String showAll = req.getParameter("showAll");
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			if(showAll == null)
			{
				List<?> rateMasterList = elRateMasterService.findALL();
				 Map<String, Object> rateMaster = null;
				 for (Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						rateMaster = new HashMap<String, Object>();
						rateMaster.put("stateName", (String)values[0]);
						rateMaster.put("tariffName", (String)values[1]);
						rateMaster.put("elrmid", (Integer)values[2]);
						rateMaster.put("rateName", (String)values[4]);
						rateMaster.put("rateDescription", (String)values[5]);
						rateMaster.put("rateType", (String)values[6]);
						rateMaster.put("validFrom", (Date)values[7]);
						rateMaster.put("validTo", (Date)values[8]);
						rateMaster.put("rateUOM", (String)values[9]);
						rateMaster.put("minRate", (Float)values[10]);
						rateMaster.put("status", (String)values[11]);
						rateMaster.put("maxRate", (Float)values[12]);
						rateMaster.put("todType", (String)values[13]);
						rateMaster.put("elTariffID", (Integer)values[14]);
						rateMaster.put("rateNameCategory", (String)values[15]);
						result.add(rateMaster);
				 }
			}
			else 
			{

				List<?> rateMasterList = elRateMasterService.findALL1();
				 Map<String, Object> rateMaster = null;
				 for (Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
						final Object[] values = (Object[]) iterator.next();
						rateMaster = new HashMap<String, Object>();
						rateMaster.put("stateName", (String)values[0]);
						rateMaster.put("tariffName", (String)values[1]);
						rateMaster.put("elrmid", (Integer)values[2]);
						rateMaster.put("rateName", (String)values[4]);
						rateMaster.put("rateDescription", (String)values[5]);
						rateMaster.put("rateType", (String)values[6]);
						rateMaster.put("validFrom", (Date)values[7]);
						rateMaster.put("validTo", (Date)values[8]);
						rateMaster.put("rateUOM", (String)values[9]);
						rateMaster.put("minRate", (Float)values[10]);
						rateMaster.put("status", (String)values[11]);
						rateMaster.put("maxRate", (Float)values[12]);
						rateMaster.put("todType", (String)values[13]);
						rateMaster.put("elTariffID", (Integer)values[14]);
						rateMaster.put("rateNameCategory", (String)values[15]);
						result.add(rateMaster);
				 }
				}
			return result;
		}

		@RequestMapping(value = "/tariff/tariffStatus/{elrmid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
		public void tariffStatus(@PathVariable int elrmid, @PathVariable String operation, HttpServletResponse response)
		{		
			logger.info("Rate master Activate and Deactivate method");
			logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+elrmid);
			
			if(operation.equalsIgnoreCase("activate"))
				elRateMasterService.setRateMasterStatus(elrmid, operation, response);

			else
				elRateMasterService.setRateMasterStatus(elrmid, operation, response);
		}
		
		@RequestMapping(value = "/elRateMaster/destroy", method = RequestMethod.GET)
		public @ResponseBody Object deleteRateMaster(@ModelAttribute("elratemaster") ELRateMaster elRateMaster,BindingResult bindingResult,ModelMap model, SessionStatus ss)
		{
			logger.info("Rate Master Delete method");
			logger.info("The Rate Master withd Id :::::::::::::: "+elRateMaster.getElrmid()+" is deleting");
			try{
				elRateMasterService.delete(elRateMaster.getElrmid());
			}
			catch(Exception e){
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return elRateMaster;
		}
		
		@RequestMapping(value = "/elRateMaster/update", method = RequestMethod.GET)
		public @ResponseBody
		Object editRateMaster(@ModelAttribute("elratemaster") ELRateMaster elRateMaster, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException
		{
			logger.info("Rate Master Update method");
			logger.info("The Rate Master withd Id :::::::::::::: "+elRateMaster.getElrmid()+" is updating");
			elRateMaster.setValidFrom(dateTimeCalender.getDateToStore(req.getParameter("validFrom")));
			elRateMaster.setValidTo(dateTimeCalender.getDateToStore(req.getParameter("validTo")));
			elRateMasterService.update(elRateMaster);
			return elRateMaster;
		}
		
		
		/****************************** Filters Data methods ********************************/
		
		@RequestMapping(value = "/tariff/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody
		Set<?> getContentsForFilter(@PathVariable String feild) {
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set=new HashSet<Object>(elRateMasterService.selectQuery("ELRateMaster", attributeList, null));
			
			return set;
		}
		@RequestMapping(value = "/tariffNameToFilter/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody
		Set<?> tariffNameToFilter(@PathVariable String feild)
		{
			Map<String, Object> params = new HashMap<String, Object>();
	        params.put("status", "Active");
	        params.put("tariffNodetype", "Tariff Rate Node");
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set=new HashSet<Object>(elRateMasterService.selectQuery("ELTariffMaster", attributeList, params));
			return set;
		}
		
		@RequestMapping(value = "/tariff/filter/states", method = RequestMethod.GET)
		public @ResponseBody List<?> stateNameToFilter()
		{
			List<String> statNames = elTariffMasterService.getStateName();
			return statNames;
		}
		
		@RequestMapping(value = "/elRateMaster/getToTariffMasterComboBoxUrl", method = RequestMethod.GET)
		public @ResponseBody List<Map<String, Object>> getToTariffMasterComboBoxUrl() 
		{	
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> listResult =  (List<Map<String, Object>>) populateCategories("Active", "Tariff Rate Node");
			return listResult;
		}

		@RequestMapping(value = "/stateName/getAllStateName", method = RequestMethod.GET)
		public @ResponseBody List<?> getAllStateName() 
		{
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<?> deptList = elTariffMasterService.getAllStates();		
			Map<String, Object> deptMap = null;
			for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();			
	 	       	deptMap = new HashMap<String, Object>();
	 	       	deptMap.put("stateName", (String)values[1]);
	 	       	result.add(deptMap);
	 	    }
			 return result;
		}
		@RequestMapping(value = "/tariffMasters/getAllTariffMasters", method = RequestMethod.GET)
		public @ResponseBody List<?> getAllTariffMasters() 
		{
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<?> deptList = elTariffMasterService.getAllTariffMasters();		
			Map<String, Object> deptMap = new HashMap<>();
			deptMap.put("elTariffID", 0);
			deptMap.put("tariffName", "Select");
			for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();			
	 	       	deptMap = new TreeMap<String, Object>();
	 	     	deptMap.put("elTariffID", (Integer)values[0]);
	 	       	deptMap.put("tariffName", (String)values[1]);
	 	       	result.add(deptMap);
	 	    }
			 return result;
		}
		
		@RequestMapping(value = "/rateNames/getAlltariffNames/{stateName}", method = RequestMethod.GET)
		public @ResponseBody List<Map<String, Object>> getAlltariffNames(@PathVariable String stateName) 
		{
			logger.info("::::::::::: Getting all tariff corresponding to State Name is :::"+stateName);
			List<Map<String, Object>> listResult = new ArrayList<>();
			Map<String, Object> mapResult = new HashMap<>();
			List<ELTariffMaster> elTariffMasters = elTariffMasterService.getAllTariffNodes(stateName);
			for (ELTariffMaster elTariffMaster : elTariffMasters) 
			{
				mapResult = new HashMap<String, Object>();
				mapResult.put("elTariffID", elTariffMaster.getElTariffID());
				mapResult.put("tariffName", elTariffMaster.getTariffName());
				listResult.add(mapResult);
			}
			return listResult;
	}

		@RequestMapping(value = "/tariff/getMaxDate", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Map<String, Object> updateUsers(HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException 
		{	int elTariffID = Integer.parseInt(req.getParameter("elTariffID"));
		String rateName = req.getParameter("rateName");
		String stateName = req.getParameter("stateName");
		String category = req.getParameter("category"); 
		logger.info("Search for date where tariff id ::::::::"+elTariffID+" rate name ::::::::"+rateName+"stateName ::::::::::: "+stateName);
		Date maxDate = elRateMasterService.getMaxDate(stateName.trim(),rateName.trim(), elTariffID,category);
		logger.info("Max date is :::::::::::::::::::::: "+maxDate);
		Map<String, Object> payRollMap = new HashMap<>();
		if(maxDate==null)
		{
			payRollMap.put("minDate", new SimpleDateFormat("yyyy-MM-dd").format(new DateTime().minusMonths(1).toDate()));
		}else {
			payRollMap.put("minDate", maxDate);
		}
		 return payRollMap;}
		
		/****************************** For EL Rate Slabs ********************************/

		@RequestMapping(value = "/tariff/elrateslab/read/{elRateSlabID}",  method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object readElRateSlab(@PathVariable int elRateSlabID, final Locale locale) 
		{
			logger.info("::::::::::::::::::: Reading the Rate slab with id ::::::::::::::::::: "+elRateSlabID);
			try
			{
				List<ELRateSlabs> elRateSlabsList =  elRateSlabService.findRateSlabByID(elRateSlabID);
				logger.info("::::::::::::::::::: Number of rate slabs for id  ::::::::::::::::::: "+elRateSlabID+":: "+elRateSlabsList.size());
				
				  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				  for (final ELRateSlabs elRateSlabs : elRateSlabsList) 
				  {
						           result.add(new HashMap<String, Object>() 
						            {
										private static final long serialVersionUID = 1L;
									{
									 if((elRateSlabs.getSlabFrom()) == null)
									 {
										 put("elrmId",elRateSlabs.getElrmId());
										 put("elrsId",elRateSlabs.getElrsId());
										 put("slabsNo",elRateSlabs.getSlabsNo());
							             put("slabType", elRateSlabs.getSlabType());
							             put("slabRateType",elRateSlabs.getSlabRateType());
							             put("rate", elRateSlabs.getRate());
							             put("status", elRateSlabs.getStatus());
							             put("createdBy", elRateSlabs.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs.getLastUpdatedDT());
										 put("dummySlabTo", "NA");
										 put("dummySlabFrom", "NA");
									 }
									 else
									{
										 put("elrmId",elRateSlabs.getElrmId());
										 put("elrsId",elRateSlabs.getElrsId());
										 put("slabsNo",elRateSlabs.getSlabsNo());
							             put("slabType", elRateSlabs.getSlabType());
							             put("slabFrom",elRateSlabs.getSlabFrom());
							             put("slabTo", elRateSlabs.getSlabTo());
							             if(elRateSlabs.getSlabTo() == 999999)
							             {
							            	 put("dummySlabTo", "Max");
							            	 put("dummySlabFrom", elRateSlabs.getSlabFrom());
							             }
							             else
							             {
							            	 put("dummySlabTo", elRateSlabs.getSlabTo());
							            	 put("dummySlabFrom", elRateSlabs.getSlabFrom());
							             }
							             put("slabRateType",elRateSlabs.getSlabRateType());
							             put("rate", elRateSlabs.getRate());
							             put("status", elRateSlabs.getStatus());
							             put("createdBy", elRateSlabs.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs.getLastUpdatedDT());
									}
						            }});
				  }
				  
				 return result;
			}

			catch (Exception e)
			{
				e.printStackTrace();
				return errorResponse.throwException(locale);
			}
		}
		
		@RequestMapping(value = "/tariff/elrateslab/create/{elRateMasterId}", method = {RequestMethod.GET, RequestMethod.POST })
		@Transactional
		public @ResponseBody
		Object createELRateSlab(@ModelAttribute("elrateslab") ELRateSlabs elRateSlabs,BindingResult bindingResult,ModelMap model,@PathVariable int elRateMasterId,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
		{
			try
			{
			elRateSlabs.setStatus("Active");
			elRateSlabs.setElrmId(elRateMasterId);
			String maxSlabToValue = req.getParameter("maxSlabToValue");
			
			 if(maxSlabToValue.trim().equals("true"))
	            {
	            	elRateSlabs.setSlabTo(999999f);
	            	logger.info("Maxmium Slab To option ::::::::::::::::: "+elRateSlabs.getSlabsNo() +" Assign 999999 to slab to as max value");
	            }
	            else 
	            {
	            	logger.info("Maxmium Slab To option ::::::::::::::::: "+elRateSlabs.getSlabsNo() +" Dont assigne 999999 to slab to as max value");
				}
				
			 if(elRateSlabs.getSlabType().trim().equals("Not applicable"))
			 {
				 elRateSlabs.setSlabFrom(null);
				 elRateSlabs.setSlabTo(null);
				 logger.info("Slab type ::::::::::::::::: "+elRateSlabs.getSlabType() +" Assign 'NULL' to slab from and slab to");
			 }
			 else
			 {
				 logger.info("Slab type ::::::::::::::::: "+elRateSlabs.getSlabType() +" dont assign 'NULL' to slab from and slab to");
			 }
			ELRateMaster elRateMaster = elRateMasterService.getStatusofELRateMaster(elRateSlabs.getElrmId());
			

			 /****************************** for single slab date validation ********************************/
			if(elRateMaster.getRateType().equals("Single Slab"))
			{
				logger.info("::::::::::::: Adding record to single slab ::::::::::::::::::");
				List<ELRateSlabs> rateSlabs = elRateSlabService.findRateSlabByID(elRateSlabs.getElrmId());
				
				if(rateSlabs.size() == 1)
				{
					logger.info("Error occured here..............");
					JsonResponse errorResponse = new JsonResponse();
					errorResponse.setStatus("SINGLESLAB");
					errorResponse.setResult("Only one Rate Slab allowed for Single Slab Rate Master.");
					return errorResponse;
				}
				if(rateSlabs.isEmpty())
				{
					elRateSlabService.save(elRateSlabs);
				}
			}
			 /****************************** for multislab date validation ********************************/
			if(elRateMaster.getRateType().equals("Multi Slab"))
			{
				logger.info("::::::::::::: Adding record to multi slab ::::::::::::::::::");
				int maxSlabNumber = elRateSlabService.getMaxSlabNumber(elRateSlabs.getElrmId());

				if(maxSlabNumber == 0)
				{
					if(elRateSlabs.getSlabsNo() != 1)
					{
						JsonResponse errorResponse = new JsonResponse();
	     				errorResponse.setStatus("SINGLESLAB");
	     				errorResponse.setResult("For first record slab number start with 1");
	     				return errorResponse;
	     				
					}
					if(elRateSlabs.getSlabFrom()!= 0)
					{
						JsonResponse errorResponse = new JsonResponse();
	     				errorResponse.setStatus("SINGLESLAB");
	     				errorResponse.setResult("For first record slab from start with 1");
	     				return errorResponse;
					}
					elRateSlabService.save(elRateSlabs);
				}
			}
			 /****************************** for multislab telescopic slab date validation ********************************/
			if(elRateMaster.getRateType().equals("Multi Slab Telescopic"))
			{
				logger.info("::::::::::::: Adding record to Multi Slab Telescopic ::::::::::::::::::");
				 int maxSlabNumber = elRateSlabService.getMaxSlabNumber(elRateSlabs.getElrmId());
				 String checkSlabNoToIns = slabNumberComparison(elRateSlabs, maxSlabNumber);
				 String rateSlabErrorMsg = slabsMultiSlabTelescopic(elRateSlabs);
				 
				if(elRateSlabs.getSlabFrom()!=0)
				{
					JsonResponse errorResponse = new JsonResponse();
					errorResponse.setStatus("SINGLESLAB");
					errorResponse.setResult("Slab From always start with 0 for Multi Slab Telescopic");
					return errorResponse;
				}

                if(checkSlabNoToIns != null)
                {
     				JsonResponse errorResponse = new JsonResponse();
     				errorResponse.setStatus("SINGLESLAB");
     				errorResponse.setResult(checkSlabNoToIns);
     				return errorResponse;
                }
                
                if(rateSlabErrorMsg != null)
                {
     				JsonResponse errorResponse = new JsonResponse();
     				errorResponse.setStatus("SINGLESLAB");
     				errorResponse.setResult(rateSlabErrorMsg);
     				return errorResponse;
                }
			}
			elRateSlabService.update(elRateSlabs);
			
		
				List<ELRateSlabs> elRateSlabsList = new ArrayList<>();
				elRateSlabsList.add(elRateSlabs);
				
				  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				  for (final ELRateSlabs elRateSlabs1 : elRateSlabsList) 
				  {
						           result.add(new HashMap<String, Object>() 
						            {
										private static final long serialVersionUID = 1L;

									{
									 if((elRateSlabs1.getSlabFrom()) == null)
									 {
										 put("elrmId",elRateSlabs1.getElrmId());
										 put("elrsId",elRateSlabs1.getElrsId());
										 put("slabsNo",elRateSlabs1.getSlabsNo());
							             put("slabType", elRateSlabs1.getSlabType());
							             put("slabRateType",elRateSlabs1.getSlabRateType());
							             put("rate", elRateSlabs1.getRate());
							             put("status", elRateSlabs1.getStatus());
							             put("createdBy", elRateSlabs1.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs1.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs1.getLastUpdatedDT());
										 put("dummySlabTo", "NA");
										 put("dummySlabFrom", "NA");
									 }
									 else
									{
										 put("elrmId",elRateSlabs1.getElrmId());
										 put("elrsId",elRateSlabs1.getElrsId());
										 put("slabsNo",elRateSlabs1.getSlabsNo());
							             put("slabType", elRateSlabs1.getSlabType());
							             put("slabFrom",elRateSlabs1.getSlabFrom());
							             put("slabTo", elRateSlabs1.getSlabTo());
							             if(elRateSlabs1.getSlabTo() == 999999)
							             {
							            	 put("dummySlabTo", "Max");
							            	 put("dummySlabFrom", elRateSlabs1.getSlabFrom());
							             }
							             else
							             {
							            	 put("dummySlabTo", elRateSlabs1.getSlabTo());
							            	 put("dummySlabFrom", elRateSlabs1.getSlabFrom());
							             }
							             put("slabRateType",elRateSlabs1.getSlabRateType());
							             put("rate", elRateSlabs1.getRate());
							             put("status", elRateSlabs1.getStatus());
							             put("createdBy", elRateSlabs1.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs1.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs1.getLastUpdatedDT());
									}
						            }});
				  }
				  
				 return result;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return errorResponse.throwException(locale);
			}
		
		}
		
		@RequestMapping(value = "/tariff/elrateslab/update", method = RequestMethod.GET)
		public @ResponseBody
		Object editRateSlab(@ModelAttribute("elrateslab") ELRateSlabs elRateSlabs,BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {
			
			 String maxSlabToValue = req.getParameter("maxSlabToValue");
			 HttpSession session=req.getSession(false);
			 String userName=(String) session.getAttribute("userId");
			 elRateSlabs.setCreatedBy(userName);
			 
			 if(elRateSlabs.getSlabType().trim().equals("Not applicable"))
			 {
				 elRateSlabs.setSlabFrom(null);
				 elRateSlabs.setSlabTo(null);
				 logger.info("Slab type ::::::::::::::::: "+elRateSlabs.getSlabType() +" Assign 'NULL' to slab from and slab to");
			 }
			 else
			 {
				 logger.info("Slab type ::::::::::::::::: "+elRateSlabs.getSlabType() +" dont assign 'NULL' to slab from and slab to");
			 }
			 if(maxSlabToValue!=null)
			 {
				 if(maxSlabToValue.trim().equals("true"))
		            {
		            	elRateSlabs.setSlabTo(999999f);
		            	logger.info("Maxmium Slab To option ::::::::::::::::: "+elRateSlabs.getSlabsNo() +" Assign 999999 to slab to as max value");
		            }
		            else 
		            {
		            	logger.info("Maxmium Slab To option ::::::::::::::::: "+elRateSlabs.getSlabsNo() +" Dont assigne 999999 to slab to as max value");
					}
			 }
			elRateSlabService.update(elRateSlabs);
			try
			{
				List<ELRateSlabs> elRateSlabsList = new ArrayList<>();
				elRateSlabsList.add(elRateSlabs);
				
				  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				  for (final ELRateSlabs elRateSlabs1 : elRateSlabsList) 
				  {
						           result.add(new HashMap<String, Object>() 
						            {
								private static final long serialVersionUID = 1L;
									{
									 if((elRateSlabs1.getSlabFrom()) == null)
									 {
										 put("elrmId",elRateSlabs1.getElrmId());
										 put("elrsId",elRateSlabs1.getElrsId());
										 put("slabsNo",elRateSlabs1.getSlabsNo());
							             put("slabType", elRateSlabs1.getSlabType());
							             put("slabRateType",elRateSlabs1.getSlabRateType());
							             put("rate", elRateSlabs1.getRate());
							             put("status", elRateSlabs1.getStatus());
							             put("createdBy", elRateSlabs1.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs1.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs1.getLastUpdatedDT());
										 put("dummySlabTo", "NA");
										 put("dummySlabFrom", "NA");
									 }
									 else
									{
										 put("elrmId",elRateSlabs1.getElrmId());
										 put("elrsId",elRateSlabs1.getElrsId());
										 put("slabsNo",elRateSlabs1.getSlabsNo());
							             put("slabType", elRateSlabs1.getSlabType());
							             put("slabFrom",elRateSlabs1.getSlabFrom());
							             put("slabTo", elRateSlabs1.getSlabTo());
							             if(elRateSlabs1.getSlabTo() == 999999)
							             {
							            	 put("dummySlabTo", "Max");
							            	 put("dummySlabFrom", elRateSlabs1.getSlabFrom());
							             }
							             else
							             {
							            	 put("dummySlabTo", elRateSlabs1.getSlabTo());
							            	 put("dummySlabFrom", elRateSlabs1.getSlabFrom());
							             }
							             put("slabRateType",elRateSlabs1.getSlabRateType());
							             put("rate", elRateSlabs1.getRate());
							             put("status", elRateSlabs1.getStatus());
							             put("createdBy", elRateSlabs1.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs1.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs1.getLastUpdatedDT());
									}
						            }});
				  }
				  
				 return result;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return errorResponse.throwException(locale);
			}
		}
		
		@RequestMapping(value = "/tariff/elrateslab/destroy", method = RequestMethod.GET)
		public @ResponseBody Object deleteRateSlab(@ModelAttribute("elrateslab") ELRateSlabs elRateSlabs,BindingResult bindingResult,ModelMap model,SessionStatus ss,final Locale locale)
		{
			
			try{
				logger.info("Deleting Rate Slab with Id ::::::::::::::::: "+elRateSlabs.getElrsId());
				elRateSlabService.delete(elRateSlabs.getElrsId());
			}
			catch(Exception e)
			{
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			try
			{
				List<ELRateSlabs> elRateSlabsList = new ArrayList<>();
				elRateSlabsList.add(elRateSlabs);
				
				  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				  for (final ELRateSlabs elRateSlabs1 : elRateSlabsList) 
				  {
						           result.add(new HashMap<String, Object>() 
						            {
										private static final long serialVersionUID = 1L;

									{
									 if((elRateSlabs1.getSlabFrom()) == null)
									 {
										 put("elrmId",elRateSlabs1.getElrmId());
										 put("elrsId",elRateSlabs1.getElrsId());
										 put("slabsNo",elRateSlabs1.getSlabsNo());
							             put("slabType", elRateSlabs1.getSlabType());
							             put("slabRateType",elRateSlabs1.getSlabRateType());
							             put("rate", elRateSlabs1.getRate());
							             put("status", elRateSlabs1.getStatus());
							             put("createdBy", elRateSlabs1.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs1.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs1.getLastUpdatedDT());
										 put("dummySlabTo", "NA");
										 put("dummySlabFrom", "NA");
									 }
									 else
									{
										 put("elrmId",elRateSlabs1.getElrmId());
										 put("elrsId",elRateSlabs1.getElrsId());
										 put("slabsNo",elRateSlabs1.getSlabsNo());
							             put("slabType", elRateSlabs1.getSlabType());
							             put("slabFrom",elRateSlabs1.getSlabFrom());
							             put("slabTo", elRateSlabs1.getSlabTo());
							             if(elRateSlabs1.getSlabTo() == 999999)
							             {
							            	 put("dummySlabTo", "Max");
							            	 put("dummySlabFrom", elRateSlabs1.getSlabFrom());
							             }
							             else
							             {
							            	 put("dummySlabTo", elRateSlabs1.getSlabTo());
							            	 put("dummySlabFrom", elRateSlabs1.getSlabFrom());
							             }
							             put("slabRateType",elRateSlabs1.getSlabRateType());
							             put("rate", elRateSlabs1.getRate());
							             put("status", elRateSlabs1.getStatus());
							             put("createdBy", elRateSlabs1.getCreatedBy());
							             put("lastUpdatedBy", elRateSlabs1.getLastUpdatedBy());
							             put("lastUpdatedDT", elRateSlabs1.getLastUpdatedDT());
									}
						            }});
				  }
				  
				 return result;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.info("---------- Error While retriving ----------------------");
				return errorResponse.throwException(locale);
			}
		}
		
		@RequestMapping(value = "/tariff/elrateslab/merge", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object merge(HttpServletRequest request,HttpServletResponse response)
		{
			String id = request.getParameter("elrsIds");
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

			ELRateSlabs previousRateSlab = new ELRateSlabs();
			
			if(idValues.length == 2)
			{
				for(int i=0;i<idValues.length-1;i++)
				{
					previousRateSlab = elRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
					ELRateSlabs currentRateSlabs = elRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
					
					ELRateMaster elRateMaster = elRateMasterService.getStatusofELRateMaster(previousRateSlab.getElrmId());
					
					if(elRateMaster.getRateType().equals("Multi Slab"))
					{
						if((previousRateSlab.getSlabsNo() + 1) == currentRateSlabs.getSlabsNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getSlabsNo() - 1) == currentRateSlabs.getSlabsNo())
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
					
					if(elRateMaster.getRateType().equals("Multi Slab Telescopic"))
					{
						if((previousRateSlab.getSlabsNo() + 1) == currentRateSlabs.getSlabsNo())
						{
							JsonResponse errorResponse = new JsonResponse();
							errorResponse.setStatus("INPUT");
							return errorResponse;
						}
						
						else if((previousRateSlab.getSlabsNo() - 1) == currentRateSlabs.getSlabsNo())
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
		
		@RequestMapping(value = "/tariff/elrateslab/getNewRate", method = {RequestMethod.GET, RequestMethod.POST })
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
			if (newRate <= 0) {
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("SINGLESLAB");
				errorResponse.setResult("Rate should not less than 1");
				return errorResponse;
			}
			
			ELRateSlabs previousRateSlab = new ELRateSlabs();
			for(int i=0;i<idValues.length-1;i++)
			{
				previousRateSlab = elRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i]));
				ELRateSlabs currentRateSlabs = elRateSlabService.findRateSlabByPrimayID(Integer.parseInt(idValues[i+1]));
				
				ELRateMaster elRateMaster = elRateMasterService.getStatusofELRateMaster(previousRateSlab.getElrmId());
				
				if(elRateMaster.getRateType().equals("Multi Slab"))
				{
					if((previousRateSlab.getSlabsNo() + 1) == currentRateSlabs.getSlabsNo())
					{
						previousRateSlab.setSlabTo(currentRateSlabs.getSlabTo());
						previousRateSlab.setRate(newRate);
						previousRateSlab.setSlabType(currentRateSlabs.getSlabType());
						previousRateSlab.setSlabRateType(currentRateSlabs.getSlabRateType());
						elRateSlabService.update(previousRateSlab);
						elRateSlabService.delete(currentRateSlabs.getElrsId());
						
						List<ELRateSlabs> elRateSlabs = elRateSlabService.getElRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSlabsNo(),previousRateSlab.getElrmId());
						for (ELRateSlabs elRateSlabs2 : elRateSlabs)
						{
							int updateSlabNo = elRateSlabs2.getSlabsNo() - 1;
							elRateSlabs2.setSlabsNo(updateSlabNo);
							elRateSlabService.update(elRateSlabs2);
						}
					}
					
					else if((previousRateSlab.getSlabsNo() - 1) == currentRateSlabs.getSlabsNo())
					{
						currentRateSlabs.setSlabTo(previousRateSlab.getSlabTo());
						currentRateSlabs.setRate(newRate);
						currentRateSlabs.setSlabType(previousRateSlab.getSlabType());
						currentRateSlabs.setSlabRateType(previousRateSlab.getSlabRateType());
						elRateSlabService.update(currentRateSlabs);
						elRateSlabService.delete(previousRateSlab.getElrsId());
						
						List<ELRateSlabs> elRateSlabs = elRateSlabService.getElRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSlabsNo(),previousRateSlab.getElrmId());
						for (ELRateSlabs elRateSlabs2 : elRateSlabs)
						{
							int updateSlabNo = elRateSlabs2.getSlabsNo() - 1;
							elRateSlabs2.setSlabsNo(updateSlabNo);
							elRateSlabService.update(elRateSlabs2);
						}
					}
				}
				
				if(elRateMaster.getRateType().equals("Multi Slab Telescopic"))
				{
					if((previousRateSlab.getSlabsNo() + 1) == currentRateSlabs.getSlabsNo())
					{
						previousRateSlab.setSlabTo(currentRateSlabs.getSlabTo());
						previousRateSlab.setRate(newRate);
						previousRateSlab.setSlabType(currentRateSlabs.getSlabType());
						previousRateSlab.setSlabRateType(currentRateSlabs.getSlabRateType());
						elRateSlabService.update(previousRateSlab);
						elRateSlabService.delete(currentRateSlabs.getElrsId());
						
						List<ELRateSlabs> elRateSlabs = elRateSlabService.getElRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSlabsNo(),previousRateSlab.getElrmId());
						
						for (ELRateSlabs elRateSlabs2 : elRateSlabs)
						{
							int updateSlabNo = elRateSlabs2.getSlabsNo() - 1;
							elRateSlabs2.setSlabsNo(updateSlabNo);
							elRateSlabService.update(elRateSlabs2);
						}
					}
					
					else if((previousRateSlab.getSlabsNo() - 1) == currentRateSlabs.getSlabsNo())
					{
						currentRateSlabs.setSlabTo(previousRateSlab.getSlabTo());
						currentRateSlabs.setRate(newRate);
						currentRateSlabs.setSlabType(previousRateSlab.getSlabType());
						currentRateSlabs.setSlabRateType(previousRateSlab.getSlabRateType());
						elRateSlabService.update(currentRateSlabs);
						elRateSlabService.delete(previousRateSlab.getElrsId());
						
						List<ELRateSlabs> elRateSlabs = elRateSlabService.getElRateSlabGreaterThanUpdateSlabNo(previousRateSlab.getSlabsNo(),previousRateSlab.getElrmId());
						for (ELRateSlabs elRateSlabs2 : elRateSlabs)
						{
							int updateSlabNo = elRateSlabs2.getSlabsNo() - 1;
							elRateSlabs2.setSlabsNo(updateSlabNo);
							elRateSlabService.update(elRateSlabs2);
						}
						
					}
				}
			}
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("SUCCESS");
			errorResponse.setResult("Two records merged successfully");
			return errorResponse;
		}
		
		 @RequestMapping(value = "/tariff/elRateSlabStatusUpdateFromInnerGrid/{slabID}", method = { RequestMethod.GET, RequestMethod.POST })
		 public void elRatesSlabStatusUpdateFromInnerGrid(@PathVariable int slabID, HttpServletResponse response)
		 {  
			 elRateSlabService.updateslabStatus(slabID, response);
		 }
		 
		 @RequestMapping(value = "/tariff/elrateslab/rateSlabSplit", method = { RequestMethod.GET, RequestMethod.POST })
		 public @ResponseBody Object rateSlabSplit(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
		 {  
			 String elrsidString = request.getParameter("hiddenelrsId");
			 ELRateSlabs elRateSlabs =  new ELRateSlabs();
			 elRateSlabs.setSlabsNo(Integer.parseInt(request.getParameter("slabsNo")));
			 elRateSlabs.setRate(Integer.parseInt(request.getParameter("rate")));
			 elRateSlabs.setSlabFrom(Float.parseFloat(request.getParameter("slabFrom")));
			 elRateSlabs.setSlabTo(Float.parseFloat(request.getParameter("slabTo")));
			 
			 ELRateSlabs elRateSlabs2 = elRateSlabService.findRateSlabByPrimayID(Integer.parseInt(elrsidString));
			 elRateSlabs2.setSlabsNo(elRateSlabs.getSlabsNo());
			 elRateSlabs2.setRate(elRateSlabs.getRate());
			 elRateSlabs2.setSlabFrom(elRateSlabs.getSlabFrom());
			 elRateSlabs2.setSlabTo(elRateSlabs.getSlabTo());
			 
			 ELRateSlabs elRateSlabs1 =  new ELRateSlabs();
			 elRateSlabs1.setSlabsNo(Integer.parseInt(request.getParameter("slabsNo1")));
			 elRateSlabs1.setRate(Integer.parseInt(request.getParameter("rate1")));
			 elRateSlabs1.setSlabFrom(Float.parseFloat(request.getParameter("slabFrom1")));
			 elRateSlabs1.setSlabTo(Float.parseFloat(request.getParameter("slabTo1")));
			 elRateSlabs1.setStatus("Active");
			 elRateSlabs1.setSlabType(elRateSlabs2.getSlabType());
			 elRateSlabs1.setSlabRateType(elRateSlabs2.getSlabRateType());
			 elRateSlabs1.setElrmId(elRateSlabs2.getElrmId());
			 
			 float form2SlabFrom = elRateSlabs.getSlabTo() +1;
			 
			 if(elRateSlabs1.getSlabFrom() != form2SlabFrom)
			 {
				   JsonResponse errorResponse = new JsonResponse();
					errorResponse.setStatus("SINGLESLAB");
					errorResponse.setResult("Form 2 Slab From shoub be Form1 Slab To +1");
					return errorResponse;
			 }
			 
			 elRateSlabService.update(elRateSlabs2);
			 
			   List<ELRateSlabs> elRateSlabsList = elRateSlabService.getElRateSlabGreaterThanUpdateSlabNoEq(elRateSlabs1.getSlabsNo(),elRateSlabs1.getElrmId());
				for (ELRateSlabs elRateSlabsSplit : elRateSlabsList)
				{
					logger.info("Elrate slab id is =>"+elRateSlabsSplit.getSlabsNo());
					int updateSlabNo = elRateSlabsSplit.getSlabsNo() + 1;
					logger.info("updateSlabNo =>"+updateSlabNo);
					elRateSlabsSplit.setSlabsNo(updateSlabNo);
					elRateSlabService.update(elRateSlabsSplit);
				}
			elRateSlabService.save(elRateSlabs1);
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("SUCCESS");
			errorResponse.setResult("Two records split successfully");
			return errorResponse;
		 }
		
		 /****************************** For EL TOD Rates ********************************/
			
			@RequestMapping(value = "/tariff/elTODRate/create", method = {RequestMethod.GET, RequestMethod.POST })
			public @ResponseBody
			Object createELTODRate(@ModelAttribute("eltodrates") ELTodRates elTodRates, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
			{
				logger.info("In Create EL RateS lab Method");
				java.util.Date fromdate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("fromTime"));
				java.util.Date todate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("toTime"));
				
				Timestamp fromTime = new Timestamp(fromdate.getTime());
				Timestamp toTime = new Timestamp(todate.getTime());
				
				elTodRates.setFromTime(fromTime);
				elTodRates.setToTime(toTime);
				elTodRates.setTodValidFrom(dateTimeCalender.getDateToStore(req.getParameter("todValidFrom")));
				elTodRates.setTodValidTo(dateTimeCalender.getDateToStore(req.getParameter("todValidTo")));
				elTodRates.setStatus("Active");
				elTodRates.setElRateSlabs(elRateSlabService.findRateSlabByPrimayID(elTodRates.getElrsId()));
				
				Date maxDate = eltodRatesService.getRateSlabs(elTodRates.getElrsId());
				Timestamp maxTimeStamp = eltodRatesService.getmaxTimeStamp(elTodRates.getElrsId());
				int maxIncrementalRate = eltodRatesService.getMaxIncrementalRate(elTodRates.getElrsId());
				
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
					 logger.info("timestamp = >"+timestamp);
					 logger.info("elTodRates.getFromTime() =>"+elTodRates.getFromTime());
					 
					eltodRatesService.update(elTodRates);
					
					List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
						  for (final ELTodRates todRates : eltodRatesService.findALL(elTodRates.getElrsId())) 
						  {
								           result.add(new HashMap<String, Object>() 
								            {/**
												 * 
												 */
												private static final long serialVersionUID = 1L;

											{
								             put("eltiId", todRates.getEltiId());
								             put("elrsId",todRates.getElrsId());
								             put("fromTime", ConvertDate.TimeStampString(todRates.getFromTime()) );
								             put("toTime", ConvertDate.TimeStampString(todRates.getToTime()) );
								             put("incrementalRate", todRates.getIncrementalRate());
								             put("todValidFrom", todRates.getTodValidFrom());             
								             put("todValidTo", todRates.getTodValidTo());
								             put("todRates", todRates.getStatus());
								             put("createdBy", todRates.getCreatedBy());
								             put("status", todRates.getStatus());
								             put("lastUpdatedDT", todRates.getLastUpdatedDT()); 
								             put("lastUpdatedBy", todRates.getLastUpdatedBy());
								             put("elRateSlabs", todRates.getElRateSlabs());
								             put("todRateType", todRates.getTodRateType());
								             
								            }});
							 
						  }
					 
					  return result;	
				}
				else
				{
					eltodRatesService.update(elTodRates);
				}
				
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
					  for (final ELTodRates todRates : eltodRatesService.findALL(elTodRates.getElrsId())) 
					  {
							           result.add(new HashMap<String, Object>() 
							            {/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

										{
							             put("eltiId", todRates.getEltiId());
							             put("elrsId",todRates.getElrsId());
							             put("fromTime", ConvertDate.TimeStampString(todRates.getFromTime()) );
							             put("toTime", ConvertDate.TimeStampString(todRates.getToTime()) );
							             put("incrementalRate", todRates.getIncrementalRate());
							             put("todValidFrom", todRates.getTodValidFrom());             
							             put("todValidTo", todRates.getTodValidTo());
							             put("todRates", todRates.getStatus());
							             put("createdBy", todRates.getCreatedBy());
							             put("status", todRates.getStatus());
							             put("lastUpdatedDT", todRates.getLastUpdatedDT()); 
							             put("lastUpdatedBy", todRates.getLastUpdatedBy());
							             put("elRateSlabs", todRates.getElRateSlabs());
							             put("todRateType", todRates.getTodRateType());
							             
							            }});
						 
					  }
				 
				  return result;	
				
			}

			@RequestMapping(value = "/tariff/elTODRate/read/{elrmid}",  method = { RequestMethod.GET, RequestMethod.POST })
			public @ResponseBody List<?> elTODRates(@PathVariable int elrmid,final Locale locale) 
			{
				logger.info("::::::::::::::::::::::: Reading tod rates :::::::::::: with parent id ::"+elrmid);
				  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				  List<ELRateSlabs> elRateSlabsList = elRateSlabService.findRateSlabByID(elrmid);
				  for (ELRateSlabs elRateSlabs : elRateSlabsList)
				  {
					  for (final ELTodRates todRates : eltodRatesService.findALL(elRateSlabs.getElrsId())) 
					  {
							           result.add(new HashMap<String, Object>() 
							            {/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

										{
							             put("eltiId", todRates.getEltiId());
							             put("elrsId",todRates.getElrsId());
							             put("fromTime", ConvertDate.TimeStampString(todRates.getFromTime()) );
							             put("toTime", ConvertDate.TimeStampString(todRates.getToTime()) );
							             put("incrementalRate", todRates.getIncrementalRate());
							             put("todValidFrom", todRates.getTodValidFrom());             
							             put("todValidTo", todRates.getTodValidTo());
							             put("todRates", todRates.getStatus());
							             put("createdBy", todRates.getCreatedBy());
							             put("status", todRates.getStatus());
							             put("lastUpdatedDT", todRates.getLastUpdatedDT()); 
							             put("lastUpdatedBy", todRates.getLastUpdatedBy());
							             put("elRateSlabs", todRates.getElRateSlabs());
							             put("todRateType", todRates.getTodRateType());
							             
							            }});
						 
					  }
				 }
				  return result;	
			}
			
			@RequestMapping(value = "/tariff/elTODRate/update", method = RequestMethod.GET)
			public @ResponseBody
			Object editTODRates(@ModelAttribute("eltodrates") ELTodRates elTodRates, BindingResult bindingResult,ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {
				java.util.Date fromdate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("fromTime"));
				java.util.Date todate = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(req.getParameter("toTime"));
				Timestamp fromTime = new Timestamp(fromdate.getTime());
				Timestamp toTime = new Timestamp(todate.getTime());
				elTodRates.setFromTime(fromTime);
				elTodRates.setToTime(toTime);
				elTodRates.setTodValidFrom(dateTimeCalender.getDateToStore(req.getParameter("todValidFrom")));
				elTodRates.setTodValidTo(dateTimeCalender.getDateToStore(req.getParameter("todValidTo")));
				eltodRatesService.update(elTodRates);
				return elTodRates;
			}
			
			@RequestMapping(value = "/tariff/elTODRate/destroy", method = RequestMethod.GET)
			public @ResponseBody Object deleteTODRates(@ModelAttribute("eltodrates") ELTodRates elTodRates, BindingResult bindingResult,ModelMap model,SessionStatus ss) {
				
				try{
					eltodRatesService.delete(elTodRates.getEltiId());
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
			
			@RequestMapping(value = "/tariff/elTODRate/categories/{elrmid}",  method = {RequestMethod.GET, RequestMethod.POST})
		    public @ResponseBody List<?> categories(@PathVariable int elrmid,final Locale locale) 
		    {
				List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
			    Map<String, Object> mapResultSelect = new HashMap<String, Object>();
			    Map<String, Object> mapResult = null;
			    List<String> attributesList = new ArrayList<String>();
			    attributesList.add("elrsId");
			    attributesList.add("slabsNo");
			    
			    Map<String, Object> params = new HashMap<String, Object>();
		        params.put("elrmId", elrmid);
		        Map<String, Object> orderByList = new HashMap<String, Object>();
		        orderByList.put("slabsNo", "ASC");
		        
			    List<?> categoriesList = (List<?>) commonService.selectQueryOrderBy("ELRateSlabs", attributesList, params,orderByList);

			    mapResultSelect.put("elrsId", 0);
			    mapResultSelect.put("slabsNo", "Select");
				listResult.add(mapResultSelect);
				for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
				{
					final Object[] values = (Object[]) i.next();
					mapResult = new HashMap<String, Object>();
					mapResult.put("elrsId", values[0]);
					mapResult.put("slabsNo", values[1]);
					listResult.add(mapResult);
				}	
			    return listResult;
		    }
			
			 @RequestMapping(value = "/tariff/elTODRatesStatusUpdateFromInnerGrid/{eltiId}", method = { RequestMethod.GET, RequestMethod.POST })
			 public void elTODRatesStatusUpdateFromInnerGrid(@PathVariable int eltiId, HttpServletResponse response)
			 {  
				 eltodRatesService.updateslabStatus(eltiId, response);
			 }	
/**************************************************  Methods for date,slab campariaion  ***********************************************/ 
		 
		 public String dateCamparisonTODRatesTimeStamp(ELTodRates elTodRates,Timestamp maxTimestamp)
		 {
			 long t=maxTimestamp.getTime();
			 Timestamp tomorrow= new Timestamp(t+(60*1000));
			 
			    if(elTodRates.getToTime().before(elTodRates.getFromTime()))
			    {
			    	return "To time should be greater than form";
			    }
			 
				if(elTodRates.getTodValidFrom().before(maxTimestamp))
				{
					return "Your start Time should be greater than "+new SimpleDateFormat("h:mm").format(maxTimestamp);
				}
				boolean b = (tomorrow.compareTo(elTodRates.getFromTime())==0);
				if(b)
				{
					logger.info("you can insert time stamp........");
				}
				else 
				{
					logger.info("Error from  else loop");
					return "Your Start Time must be "+new SimpleDateFormat("h:mm").format(tomorrow);
				}
			return null;
		 }
		 public String dateCamparisonRateMaster(ELRateMaster elRateMaster,Date maxDate)
		 {
			    Date tomorrow = new Date(maxDate.getTime() + (1000 * 60 * 60 * 24));
				if(elRateMaster.getValidFrom().before(maxDate))
				{
					return "Your start date should be greater than "+maxDate;
				}
				boolean b = DateUtils.isSameDay(tomorrow, elRateMaster.getValidFrom());
				if(b)
				{
					logger.info("you can insert date");
				}
				else 
				{
					return "Your start date must be "+new SimpleDateFormat("dd/MM/yyyy").format(tomorrow);
				}
			return null;
		 }
		 
		 public String slabsComparison(ELRateSlabs elRateSlabs ,float maxSlabTo)
		 {
			   logger.info("maxSlabTo =>"+maxSlabTo);
			   
			    float nextSlabFrom = maxSlabTo+1;
				if(elRateSlabs.getSlabTo() <= elRateSlabs.getSlabFrom())
				{
					return "Slab To always greater than Slab From";
				}
				
				if(elRateSlabs.getSlabFrom()<= maxSlabTo)
				{
				return "Slab From always should be greater than "+maxSlabTo;					
				}
				logger.info("elRateSlabs.getSlabFrom() "+elRateSlabs.getSlabFrom());
				logger.info("nextSlabFrom "+nextSlabFrom);
				if(elRateSlabs.getSlabFrom() != nextSlabFrom)
				 {
					   return "Slab From should be start with "+nextSlabFrom;
				 }
				
				if((elRateSlabs.getSlabFrom() > 999999) || (elRateSlabs.getSlabTo() > 999999) )
				{
					return "Slab TO "+ 999999 +" should be the last record.";
				}
				
				return null;
		 }

		 public String slabNumberComparison(ELRateSlabs elRateSlabs ,int maxSlabTo)
		 {
			   int nextSlabNo = maxSlabTo+1;
			   if(elRateSlabs.getSlabsNo()!= nextSlabNo)
			   {
				   return "Next slab number should start with "+nextSlabNo;  
			   }
				return null;
		 }
		 
		 public String slabsMultiSlabTelescopic(ELRateSlabs elRateSlabs)
		 {
				if((elRateSlabs.getSlabFrom() > 999999) || (elRateSlabs.getSlabTo() > 999999))
				{
					return "Slab TO "+ 999999 +" should be the last record.";
				}
				return null;
		 }
		 @RequestMapping(value = "/ratMaster/claculateDGUnit", method = { RequestMethod.GET,
					RequestMethod.POST })
			@ResponseBody
			public HashMap<Object, Object> calculateDGunit(HttpServletRequest request,
					HttpServletResponse response, @RequestBody String body)
					throws ParseException, JSONException {
						
			 Integer operationalCost = Integer.parseInt(request.getParameter("operationalCost"));
			 Integer depriciationCost = Integer.parseInt(request.getParameter("depriciationCost"));
			 Integer fuleCost = Integer.parseInt(request.getParameter("fuleCost"));
			 Integer maintainanceCost = Integer.parseInt(request.getParameter("maintainanceCost"));
			 Integer unitConsumed = Integer.parseInt(request.getParameter("unitConsumed"));
			 
			 elRateMasterService.UpdateDgUnit(operationalCost,depriciationCost,fuleCost,maintainanceCost/*,validTo,validfrom*/,unitConsumed);
			 PrintWriter out;
			 try {
				out=response.getWriter();
				out.write("Calculated successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 return null;
			 
		 }
		 
		 @RequestMapping(value = "/ratMaster/showDGUnit", method = { RequestMethod.GET, RequestMethod.POST })
			public @ResponseBody List<Map<String, Object>> readELRateMasterDGUnit(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
			{
				
				List<ELRateMaster> elRateMasters = null;
				List<Map<String, Object>> result;
				
					  elRateMasters = elRateMasterService.findDGUnits();
					  logger.info("::::::::::::: reading active data in rate master table ::::::::::::::::::::" +elRateMasters.size());
					   result = new ArrayList<Map<String, Object>>();
					  for (final ELRateMaster elRateMaster : elRateMasters) 
					  {
						   final ELTariffMaster tariff1 = tariffMasterService.getNodeDetails(elRateMaster.getElTariffID());
							        result.add(new HashMap<String, Object>() 
							            {
									 private static final long serialVersionUID = 1L;
										{
										 put("stateName",tariff1.getStateName());
										 put("tariffName",tariff1.getTariffName());
							             put("elrmid", elRateMaster.getElrmid());
							             put("elTariffID",elRateMaster.getElTariffID());
							             put("rateName", elRateMaster.getRateName());
							             put("rateDescription",elRateMaster.getRateDescription());
							             put("rateType", elRateMaster.getRateType());
							             put("validFrom", elRateMaster.getValidFrom());             
							             put("validTo", elRateMaster.getValidTo());
							             put("rateUOM", elRateMaster.getRateUOM());
							             put("minRate", elRateMaster.getMinRate());
							             put("status", elRateMaster.getStatus());
							             put("maxRate", elRateMaster.getMaxRate()); 
							             put("todType", elRateMaster.getTodType());
							            }});
					  }
				
				
				return result;
			} 
		 
	}
