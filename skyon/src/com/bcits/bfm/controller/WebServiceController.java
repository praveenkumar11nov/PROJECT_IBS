package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.AdvanceCollectionEntity;
import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Deposits;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityMeterParametersEntity;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.model.MeterParameterMaster;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterParametersService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.facilityManagement.MeterParameterMasterService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.google.gson.Gson;

@Controller
public class WebServiceController 
{
	private static final Log logger = LogFactory.getLog(WebServiceController.class);
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private ElectricityBillLineItemService billLineItemService;
	
	@Autowired
	BillGenerationController billGenerationController;
	
	@Autowired
	private ElectricityBillParameterService electricityBillParameterService;
	
	@Autowired
	private ElectricityMeterParametersService electricityMeterParametersService;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	BillingParameterMasterService billingParameterMasterService;
	
	@Autowired
	ElectricityBillsService electricityBillsService;
	
	@Autowired
	ElectricityBillParameterService billParameterService;

	@Autowired
	AddressService addressService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired 
	private ELTariffMasterService elTariffMasterService;
	
	@Autowired
	private WTTariffMasterService wtTariffMasterService;
	
	@Autowired
	private GasTariffMasterService gasTariffMasterService;

	@Autowired
	private SolidWasteTariffMasterService solidWasteTariffMasterService;
	
	@Autowired
	private DepositsService depositsService;
	
	@Autowired
	private ServiceParameterService serviceParameterService;
	
	@Autowired
	private AdvanceCollectionService advanceCollectionService;
	
	@Autowired
	private ServiceParameterMasterService serviceParameterMasterService;
	
	@Autowired
	private MeterParameterMasterService meterParameterMasterService;
	
	@Autowired
	private BillingParameterMasterService  billParameterMasterService;
	
	@PersistenceContext(unitName="bfm")
    protected EntityManager entityManager;
	
	
	@RequestMapping(value = "/webservice/accountNumberAutocomplete", method = RequestMethod.GET)
	public @ResponseBody List<?> accountNumberAutocomplete() {
		logger.info("---------- Web service method for getting accounts ------------------");
		return tariffCalculationService.getAllAccuntNumbers();
	}
	
	@RequestMapping(value = "/webservice/getServiceName/{accountId}", method = {RequestMethod.GET, RequestMethod.POST })
	 public @ResponseBody
	 List<?> getServiceNames(HttpServletRequest req,@PathVariable int accountId) {
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 for (final Object[] tariffcal : tariffCalculationService.getServiceName(accountId)) 
		  {
			 if(!((tariffcal[1].equals("Solid Waste")) || (tariffcal[1].equals("Others"))))
			    {
				   Map<String, Object> map = new HashMap<>();
				   map.put("serviceMasterId", (Integer) tariffcal[0]);
				   map.put("typeOfService", (String) tariffcal[1]);
				   result.add(map);
			    } 
		  }
		 return result;
		 }
	
	@RequestMapping(value = "/webservice/getunits", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public List<Object> getunits(@RequestBody String body) throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In webservice getunits Method :::::::::::::::::::::");
		String presentMeterStaus = null;
		Float averageUnits = 0f;
		Float presentUOMReading = 0f;
		Float uomValue = 0.0f;
		Float previousUOMReading = null;
		Float meterConstant = null;
		int serviceID = 0;
		Date currentBillDate = null;
		String serviceType = null;
		int accountId = 0;
		JSONArray jsonArray = new JSONArray(body);
		for (int i = 0; i < jsonArray.length(); i++)
		  {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(jsonObject.get("presentStaus")!=null)
			{
				presentMeterStaus = jsonObject.getString("presentStaus");
			}
			
			if(jsonObject.get("previousReading")!=null)
			{
				previousUOMReading = (float) jsonObject.getDouble("previousReading");
			}
			
			if(jsonObject.get("meterFactor")!=null)
			{
				meterConstant = (float) jsonObject.getDouble("meterFactor");
			}
			
			if(jsonObject.get("meterFactor")!=null)
			{
				serviceID = jsonObject.getInt("serviceId");
			}
			
			if(jsonObject.get("presentbilldate")!=null)
			{
				currentBillDate =  new SimpleDateFormat("dd/MM/yyyy").parse(jsonObject.getString("presentbilldate"));
			}
			
			if(jsonObject.get("serviceType")!=null)
			{
				serviceType =  jsonObject.getString("serviceType");
			}
			
			if(jsonObject.get("accountId")!=null)
			{
				accountId =  jsonObject.getInt("accountId");
			}
		  }	
		
		if (previousUOMReading.equals("") || previousUOMReading.equals(null)) {
			logger.info("::::::::::::::::::: Present reading is null ::::::::::::::::::");
			presentUOMReading = 0f;
		} else {
			logger.info("::::::::::::::::::: Present reading is Not null ::::::::::::::::::");
			presentUOMReading = previousUOMReading;
		}

		if (meterConstant == 1) {
			logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
			uomValue = presentUOMReading - previousUOMReading;
		} else if (meterConstant < 1) {
			logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
			uomValue = presentUOMReading - previousUOMReading;
			uomValue = uomValue - billGenerationController.getMeterConstant(meterConstant);
		} else if (meterConstant > 1) {
			logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
			uomValue = presentUOMReading - previousUOMReading;
			uomValue = uomValue - billGenerationController.getMeterConstant(meterConstant);
		}

		if (presentMeterStaus.equalsIgnoreCase("Door Lock")
				|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
				|| presentMeterStaus.equalsIgnoreCase("Direct Connection")
				|| presentMeterStaus.equalsIgnoreCase("Burnt")
				|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
			logger.info(presentMeterStaus);
			uomValue = averageUnits;
		}
		Float avgUnit = 0.0f;
		List<String> serviceParameterMasterValueList = serviceMasterService.getServiceParameterValueBasedOnMasterId(serviceID);
		if(serviceParameterMasterValueList.isEmpty()){
			avgUnit = billGenerationController.getAvgUnit(currentBillDate, serviceType, accountId);
			logger.info(":::::::::::::::avgUnit::::::::::::"+avgUnit);
            
		}else{
			
			 avgUnit=Float.parseFloat(serviceParameterMasterValueList.get(0));
			 logger.info(":::::::::::::::avgUnit::::::::::::"+avgUnit);
		}
		List<Object> list = new ArrayList<Object>();

		if (uomValue < 0) {
			list.add("Present");
		} else {
			list.add(uomValue);
		}
		list.add(avgUnit);

		return list;
	}
	
	@RequestMapping(value = "/webservice/getAvgAlert", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public HashMap<Object, Object> getAvgAlert(HttpServletResponse response,@RequestBody String body)throws ParseException, JSONException {
		logger.info("::::::::::::::::::: In webservice getAvgAlert Method :::::::::::::::::::::");
		Date currentBillDate = null;
		String serviceType = null;
		int accountId = 0;
		int unit = 0;
		JSONArray jsonArray = new JSONArray(body);
		for (int i = 0; i < jsonArray.length(); i++)
		  {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if(jsonObject.get("presentbilldate")!=null)
			{
				currentBillDate =  new SimpleDateFormat("dd/MM/yyyy").parse(jsonObject.getString("presentbilldate"));
			}
			
			if(jsonObject.get("serviceType")!=null)
			{
				serviceType =  jsonObject.getString("serviceType");
			}
			
			if(jsonObject.get("accountId")!=null)
			{
				accountId =  jsonObject.getInt("accountId");
			}
			
			if(jsonObject.get("unit")!=null)
			{
				unit =  jsonObject.getInt("unit");
			}
		  }	
		
		Float uomValue = 0.0f;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(currentBillDate);
		cal1.add(Calendar.YEAR, -1);
		Date lastYear = cal1.getTime();
		int bvmId = billingParameterMasterService.getServicMasterId(serviceType, "Units");
		List<ElectricityBillEntity> billIdList = electricityBillsService
				.getBillEntityByAccountId(accountId, serviceType);
		List<String> listValus = new ArrayList<>();
		float avgUnits=0;
        if(!billIdList.isEmpty())
        {
    		for (ElectricityBillEntity electricityBillEntity : billIdList) {
    			listValus = billParameterService.getAverageUnits(electricityBillEntity.getElBillId(), bvmId, lastYear,currentBillDate);
    			for (String string : listValus) {
    				avgUnits +=Float.parseFloat(string);
    			}
    		}
    		uomValue = avgUnits /billIdList.size();
        	
        }
	
		float uomPercentage = (25 * uomValue) / 100;
		float subNoormal = uomValue - uomPercentage;
		float abNoormal = uomValue + uomPercentage;
		
		String alert = "Normal";
		if (unit <= subNoormal) {
			logger.info("subNoormal calculation");
			alert = "You are Going To Generate Subnormal Bill";
		} else if (unit >= abNoormal) {
			logger.info("Abnormal calculation");
			alert = "You are Going to Generate Abnormal Bill";
		} else {
			logger.info("Normal Bill Calculation");
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(alert);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	 @RequestMapping(value = "/webservice/getPreviousParameters/{accountId}/{serviceType}/{serviceId}", method = {RequestMethod.GET, RequestMethod.POST })
	 public @ResponseBody Map<Object, Object> getPreviousParameters(HttpServletRequest req,@PathVariable int accountId,@PathVariable String serviceType,@PathVariable int serviceId) 
	 {
	  Map<Object, Object> parameters = new LinkedHashMap<>();
	  parameters= tariffCalculationService.getServiceStatusCheck(accountId,serviceType, serviceId);
	  String typesofmeter = tariffCalculationService.getMeterType(accountId,serviceType);
	  
	    Object  dgApplicable = "No";
	    Object  todApplicable ="No";
		List<Object> dglist = tariffCalculationService.todApplicable(serviceType, accountId);
		logger.info("::::::::::dglist.get(1);::::::::"+dglist.get(1));
		dgApplicable = dglist.get(1);
		todApplicable = dglist.get(0);
		if(dglist.isEmpty())
		{
			parameters.put("dgApplicable", dgApplicable);
			parameters.put("todApplicable", todApplicable);
		}
		else {
			parameters.put("dgApplicable", dglist.get(1));
			parameters.put("todApplicable", dglist.get(0));
		}
		
		if(!(serviceType.equalsIgnoreCase("Others")||serviceType.equalsIgnoreCase("Solid Waste")))
		{
			  if (typesofmeter.trim().equalsIgnoreCase("Trivector Meter"))
			  {
			   parameters.put("pfApplicable", "Yes");
			  }
			  else {
			   parameters.put("pfApplicable", "No");
			  }
		}
		
		Float avgUnit = 0.0f;
		List<String> serviceParameterMasterValueList = serviceMasterService.getServiceParameterValueBasedOnMasterId(serviceId);
		if(serviceParameterMasterValueList.isEmpty()){
			avgUnit = billGenerationController.getAvgUnit(new Date(), serviceType, accountId);
			 parameters.put("avgUnit", avgUnit);
			logger.info(":::::::::::::::avgUnit::::::::::::"+avgUnit);
            
		}else{
			
			 avgUnit=Float.parseFloat(serviceParameterMasterValueList.get(0));
			 parameters.put("avgUnit", avgUnit);
			 logger.info(":::::::::::::::avgUnit::::::::::::"+avgUnit);
		}
	 
	  return parameters;
	 }
	
	@RequestMapping(value = "/webservice/generateBill", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody HashMap<String, Object> getGenerateBill(HttpServletRequest req,HttpServletResponse response,@RequestBody String body) 
	{

		logger.info("::::::::::::::::::: In Bill Generation Method :::::::::::::::::::::");
		HashMap<String, Object> billDetails = new HashMap<>();
		String presentMeterStaus = null;
		String previousMeterStaus = null;
		String bill = null;
		String typeOfService = null;
		String billType = "Normal";
		Float presentUOMReading = 0f;
		Float previousUOMReading = 0f;
		Float meterConstant = 0f;
		float uomValue =  0f;
		float pfReading = 0;
		float connectedLoad = 0;
		Float previousDgreading = 0f;
		Float presentDgReading = 0f;
		Float dgmeterconstant = 0f;
		Double avgUnits = 0d;
		Integer avgCount = 0;
		int accountID=0;
		PrintWriter out = null;
		Object dgApplicable = "No";
		Date currentBillDate=new Date();
		Date previousBillDate = null;
		Float dgunit = 0f;
		Integer todT1 = 0;
		Integer todT2 = 0;
		Integer todT3 = 0;
	    Object todApplicable = null;
	    String meterchangeUnit="Unit Changed:";
		try {
			JSONArray jsonArray = new JSONArray(body);
			for (int i = 0; i < jsonArray.length(); i++)
			  {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.get("accountId")!=null)
				{
					accountID = jsonObject.getInt("accountId");
				}
				if(jsonObject.get("serviceType")!=null)
				{
					typeOfService = jsonObject.getString("serviceType");
				}
				
				if(jsonObject.get("presentbilldate")!=null)
				{
					//currentBillDate =  new SimpleDateFormat("dd/MM/yyyy").parse(jsonObject.getString("presentbilldate"));
					//	currentBillDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(jsonObject.getString("presentbilldate"));
				}
				
				List<Object> dglist = tariffCalculationService.todApplicable(typeOfService, accountID);
				dgApplicable = dglist.get(1);
				if (dgApplicable != null) {
					if (dgApplicable.equals("Yes")) 
					{
						if(jsonObject.get("previousDgreading")!=null)
						{
							previousDgreading = (float) jsonObject.getDouble("previousDgreading");
							logger.info(":::::::::::::presentDgReading  :::::::::::::"+previousDgreading);
						}
						
						if(jsonObject.get("presentDgReading")!=null)
						{
							presentDgReading = (float) jsonObject.getDouble("presentDgReading");
							logger.info(":::::::::::::presentDgReading  :::::::::::::"+presentDgReading);
						}
						
						if(jsonObject.get("dgmeterconstant")!=null)
						{
							dgmeterconstant = (float) jsonObject.getDouble("dgmeterconstant");
							logger.info(":::::::::::::dgmeterconstant  :::::::::::::"+dgmeterconstant);
						}
						
						if (dgApplicable.equals("Yes")) {

							dgunit = Float.parseFloat(jsonObject.get("dgUnit").toString());
							float dgUomValue = 0;
							if (dgmeterconstant == 1) {
								logger.info("::::::::::::: Meter constant is Normal :::::::::::::");
								dgUomValue = presentDgReading - previousDgreading;
							} else if (dgmeterconstant < 1) {
								logger.info("::::::::::::: Meter constant is Slow :::::::::::::");
								dgUomValue = presentDgReading - previousDgreading;
								dgUomValue = dgUomValue - billGenerationController.getMeterConstant(dgmeterconstant);
							} else if (dgmeterconstant > 1) {
								logger.info("::::::::::::: Meter constant is Fast :::::::::::::");
								dgUomValue = presentDgReading - previousDgreading;
								dgUomValue = dgUomValue - billGenerationController.getMeterConstant(dgmeterconstant);
							}

							dgunit = dgUomValue;
							logger.info(":::::::::::::dgunit  :::::::::::::"+dgunit);
						}
					}
				}else{
					dgApplicable="NO";
				}
//				DG applicable end
				
				
				List<Object> list = tariffCalculationService.todApplicable(typeOfService, accountID);
				 todApplicable = list.get(0);
				if (todApplicable.equals("Yes")) {
					todT1 = jsonObject.getInt("presentTod1");
					todT2 = jsonObject.getInt("presentTod2");
					todT3 = jsonObject.getInt("presentTod3");
					logger.info(" todT1::" + todT1 + " todT2::" + todT2+ " todT3::" + todT3);
				}
				else
				{
					logger.info(":::::::::Tod Is not Applicable::::::::::");
				}
				
				
				if(jsonObject.get("presentReading")!=null)
				{
					presentUOMReading = (float) jsonObject.getDouble("presentReading");
				}
				
				if(jsonObject.get("presentStaus")!=null)
				{
					presentMeterStaus =  jsonObject.getString("presentStaus");
				}
				
				if(jsonObject.get("previousStaus")!=null)
				{
					previousMeterStaus =  jsonObject.getString("previousStaus");
				}
				
				if(jsonObject.get("postType")!=null)
				{
					bill = jsonObject.getString("postType");
					if (bill.equalsIgnoreCase("Normal Bill")) {
						bill = "Bill";
					} else {
						bill = "Provisional Bill";
					}
				}
				
				if(jsonObject.get("previousReading")!=null)
				{
					previousUOMReading = (float) jsonObject.getDouble("previousReading");
				}
				
				if(jsonObject.get("meterFactor")!=null)
				{
					meterConstant = (float) jsonObject.getDouble("meterFactor");
				}
				
				if(jsonObject.get("previousbillDate")!=null)
				{
					previousBillDate =  new SimpleDateFormat("dd/MM/yyyy").parse(jsonObject.getString("previousbillDate"));
				}
				
				if (jsonObject.get("sWasteDate").equals("") || jsonObject.get("sWasteDate").equals(null)) {
				} else
				{
					previousBillDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(jsonObject.get("sWasteDate").toString());
				}
				if(jsonObject.get("units")!=null)
				{
					if(presentMeterStaus.equalsIgnoreCase("Meter Change")){
						String strDate = new SimpleDateFormat("YYYY-MM-dd")
						.format(previousBillDate);
						String pesentDate = new SimpleDateFormat("YYYY-MM-dd")
						.format(currentBillDate);
							Map<Object, Object>  list1=tariffCalculationService.getNewMeterDetails(pesentDate, strDate,typeOfService, accountID, previousUOMReading, presentUOMReading);
							Map<Object, Object>  units=new HashMap<>();
							for (Entry<Object, Object> map : list1
									.entrySet()) {
								if (map.getKey().equals("Units")) {
									
									logger.info("::::::::::map.getKey()::::::::::"+map.getKey()+"::::::::::map.getValue::::::::::"+map.getValue());
									Double double1 = (Double) map.getValue();
									uomValue=double1.floatValue();
								}else if(map.getKey().equals("meterConstant")){
									units.put(map.getKey(), map.getValue());
								}else{
									logger.info("::::::::::map.getKey()::::::::::"+map.getKey()+"::::::::::map.getValue::::::::::"+map.getValue());
									if(meterchangeUnit.equalsIgnoreCase("Unit Changed:")){
										meterchangeUnit=meterchangeUnit+ map.getValue();
									}
									else{
										meterchangeUnit=meterchangeUnit.concat(",")+ map.getValue();
									}
									logger.info(":::::::::::meterchangeUnit::::::"+meterchangeUnit);
								}
							}
					}else{
						uomValue = (float) jsonObject.getDouble("units");
					}
					
				}
				
				if (jsonObject.get("pfReading").equals("") || jsonObject.get("pfReading").equals(null)) {
				} else
				{
					pfReading = (float) jsonObject.getDouble("pfReading");
				}
				
				if (jsonObject.get("connectedLoad").equals("") || jsonObject.get("connectedLoad").equals(null)) {
				} else
				{
					connectedLoad = (float) jsonObject.getDouble("connectedLoad");
				}
				
			  }
             
			logger.info("currentBillDate ----------------- "+currentBillDate);
			LocalDate fromDate = new LocalDate(previousBillDate);
			LocalDate toDate = new LocalDate(currentBillDate);
			Integer billableDays = Days.daysBetween(fromDate, toDate).getDays();
			logger.info("::::::::::::: Total number of billable days are  :::::::::::::::::::: "+ billableDays);

			List<ELRateMaster> elRateMasterList = new ArrayList<>();
			List<WTRateMaster> waterRateMasterList = new ArrayList<>();
			List<GasRateMaster> gasRateMasterList = new ArrayList<>();
			List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();

			
			if (typeOfService.equalsIgnoreCase("Electricity")) {
				logger.info("::::::::::::: Electricity service type ::::::::::::: ");
				int tariffId = serviceMasterService.getServiceMasterByAccountNumber(accountID, typeOfService);
				logger.info(":::::::::::::" + typeOfService+ " Tairff id is ::::::::::::: " + tariffId);
				elRateMasterList = tariffCalculationService.getElectricityRateMaster(tariffId);
				List<Object> list = tariffCalculationService.todApplicable(typeOfService, accountID);
				logger.info(":::::::::::::previousDgreading" + previousDgreading+ " presentDgReading, ::::::::::::: " + presentDgReading+":::::::::: dgmeterconstant"+ dgmeterconstant);
				billDetails=billGenerationController.saveElectricityBillParameters(elRateMasterList, accountID,
						typeOfService, previousBillDate, currentBillDate, uomValue,
						billableDays, presentMeterStaus, previousMeterStaus,
						meterConstant, presentUOMReading, previousUOMReading,
						todT1, todT2, todT3, dgunit, todApplicable, dgApplicable,
						previousDgreading, presentDgReading, dgmeterconstant,
						avgUnits, avgCount, billType, pfReading, connectedLoad,
						bill,meterchangeUnit);
			}
			
			if (typeOfService.equalsIgnoreCase("Water")) {
				logger.info("::::::::::::: Water service type ::::::::::::: ");
				int waterTariffId = serviceMasterService.getWaterTariffId(accountID, typeOfService);
				logger.info(":::::::::::::" + typeOfService+ " Tairff id is ::::::::::::: " + waterTariffId);
				waterRateMasterList = tariffCalculationService.getWaterRateMaster(waterTariffId);
				billDetails = billGenerationController.saveWaterBillParameters(waterRateMasterList, accountID,
						typeOfService, previousBillDate, currentBillDate, uomValue,
						billableDays, presentMeterStaus, previousMeterStaus,
						meterConstant, presentUOMReading, previousUOMReading,
						avgUnits, avgCount, billType, bill,meterchangeUnit);
			}

			if (typeOfService.equalsIgnoreCase("Gas")) {
				logger.info("::::::::::::: Gas service type ::::::::::::: "+ accountID + " ---------------- " + typeOfService);
				int gasTariffId = serviceMasterService.getGasTariffId(accountID,typeOfService);
				logger.info(":::::::::::::" + typeOfService+ " Tairff id is ::::::::::::: " + gasTariffId);
				gasRateMasterList = tariffCalculationService.getGasRateMaster(gasTariffId);
				billDetails = billGenerationController.saveGasBillParameters(gasRateMasterList, accountID, typeOfService,previousBillDate, currentBillDate, uomValue, billableDays,
						presentMeterStaus, previousMeterStaus, meterConstant,
						presentUOMReading, previousUOMReading, avgUnits, avgCount,
						billType, bill,meterchangeUnit);
			}
			
			if (typeOfService.equalsIgnoreCase("Others")) {
				String postType = "Bill";
				logger.info("::::::::::::: Othres service type ::::::::::::: ");
				billGenerationController.saveOthersBillParameters(accountID, typeOfService, postType,previousBillDate, currentBillDate, avgUnits, avgCount);
			}
			
			if (typeOfService.equalsIgnoreCase("Solid Waste")) {
				logger.info("::::::::::::: Solid Waste service type ::::::::::::: "+ accountID + "----- " + typeOfService);
				int solidWasteTariffId = serviceMasterService.getSolidWasteTariffId(accountID, typeOfService);
				logger.info(":::::::::::::" + typeOfService+ " Tairff id is ::::::::::::: " + solidWasteTariffId);
				solidWasterRateMasterList = tariffCalculationService.getSolidWasteRateMaster(solidWasteTariffId);
				billDetails = billGenerationController.saveSolidWasteBillParameters(solidWasterRateMasterList, accountID,
						typeOfService, previousBillDate, currentBillDate,
						presentUOMReading, billableDays, presentMeterStaus,
						previousMeterStaus, meterConstant, presentUOMReading,
						previousUOMReading, avgUnits, avgCount, billType, bill);
			}
			
			if (presentMeterStaus != null) {
				if (presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus.equalsIgnoreCase("Meter Not Reading")
						|| presentMeterStaus.equalsIgnoreCase("Door Lock")
						|| presentMeterStaus.equalsIgnoreCase("Burnt")
						|| presentMeterStaus.equalsIgnoreCase("Sticky")) {
					presentUOMReading = previousUOMReading;

					logger.info(":::::::::::::: Average consumption units  ::::::::::::: "+ uomValue);
				}
			}
		} 		
		catch (Exception e) {
			try {
				out = response.getWriter();
				out.write("There seems to be Some Problem Out there.Please Try Again");
				e.printStackTrace();
				return null;
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		return billDetails;
	}
	
	@RequestMapping(value = "/webservice/getbilltable/{elBillId}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Map<String, Object> detailedBillPopup(@PathVariable int elBillId){

		Address address = null;
		Contact contactMob  = null;
		Contact contactEmail = null;
		String tariffName = "Tariff";
		String billType = "";
		String postType = "";
		Map<String, Object> billDetails = new HashMap<>();
		ElectricityBillEntity electricityBillEntity = electricityBillsService.find(elBillId);
		billDetails.put("currentBillDate", electricityBillEntity.getBillDate());
		billDetails.put("totalAmount", electricityBillEntity.getNetAmount());
		billDetails.put("billDueDate", electricityBillEntity.getBillDueDate());
		billDetails.put("arrearsAmount", electricityBillEntity.getArrearsAmount());
		billDetails.put("previousBillDate", electricityBillEntity.getFromDate());
		
		if(electricityBillEntity!=null){
			
			billType = electricityBillEntity.getBillType();
			postType = electricityBillEntity.getPostType();
			
			String addrQuery = "select obj from Address obj where obj.personId="+electricityBillEntity.getAccountObj().getPerson().getPersonId()
					+" and obj.addressPrimary='Yes'";
			address = addressService.getSingleResult(addrQuery);

			String mobileQuery = "select obj from Contact obj where obj.personId="+electricityBillEntity.getAccountObj().getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
			contactMob = contactService.getSingleResult(mobileQuery);
			String emailQuery = "select obj from Contact obj where obj.personId="+electricityBillEntity.getAccountObj().getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Email'";
			contactEmail = contactService.getSingleResult(emailQuery);


			String spmQuery = "select spm from ServiceParameterMaster spm where spm.serviceType='"+electricityBillEntity.getTypeOfService()+"' order by spm.spmSequence";

			String mpmQuery = "select mpm from MeterParameterMaster mpm where mpm.mpmserviceType='"+electricityBillEntity.getTypeOfService()+"' order by mpm.mpmSequence";

			String bpmQuery = "select bpm from BillParameterMasterEntity bpm where bpm.serviceType='"+electricityBillEntity.getTypeOfService()+"' order by bpm.bvmSequence";

			String lineItems ="select bli from ElectricityBillLineItemEntity bli where bli.electricityBillEntity.elBillId="+elBillId;
			
			String depositsQuery ="select d FROM Deposits d WHERE d.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId();
			
			String advanceBillQuery ="select ac FROM AdvanceCollectionEntity ac WHERE ac.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId();
			
			List<Deposits> depositsList = depositsService.executeSimpleQuery(depositsQuery);
			String  depositsAmount = "";
			if(!depositsList.isEmpty()){
				depositsAmount+=depositsList.get(0).getTotalAmount();
			}else{
				depositsAmount+=0;
			}
			billDetails.put("depositsAmount", depositsAmount);

			List<AdvanceCollectionEntity> advanceCollectionList = advanceCollectionService.executeSimpleQuery(advanceBillQuery);
			String  advanceBillAmount = "";
			if(!advanceCollectionList.isEmpty()){
				advanceBillAmount+=advanceCollectionList.get(0).getBalanceAmount();
			}else{
				advanceBillAmount+=0;
			}
			billDetails.put("advanceBillAmount", advanceBillAmount);
			List<ServiceParameterMaster> spmList = serviceParameterMasterService.executeSimpleQuery(spmQuery);

			if(spmList.size()>0){
				for(ServiceParameterMaster entity : spmList){
					if(entity!=null)
					{
						ServiceParametersEntity serviceParametersEntity = serviceParameterService.getSingleResult("select obj from ServiceParametersEntity obj where obj.spmId="+entity.getSpmId()+" and obj.serviceMastersEntity.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId());
						if(serviceParametersEntity!=null)
						{
							billDetails.put(entity.getSpmName(), serviceParametersEntity.getServiceParameterValue());
						}else {
							billDetails.put(entity.getSpmName(), "0.00");
						}
						
					}
				}
			}
			List<MeterParameterMaster> mpmList = meterParameterMasterService.executeSimpleQuery(mpmQuery);

			if(mpmList.size()>0){
				for(MeterParameterMaster entity : mpmList){
					if(entity!=null)
					{
						List<ElectricityMeterParametersEntity> electricityMeterParametersEntity = electricityMeterParametersService.executeSimpleQuery("select o from ElectricityMeterParametersEntity o where o.mpmId="+entity.getMpmId());
						{
							if(electricityMeterParametersEntity.size()>0)
							{
								for (ElectricityMeterParametersEntity electricityMeterParametersEntity2 : electricityMeterParametersEntity) 
								{
									billDetails.put(entity.getMpmName(), electricityMeterParametersEntity2.getElMeterParameterValue());
								}
							}
							else {
								billDetails.put(entity.getMpmName(), "0.0");
							}
						}
					}
				}
			}

			List<BillParameterMasterEntity> bpmList = billParameterMasterService.executeSimpleQuery(bpmQuery);

			if(bpmList.size()>0){
				for(BillParameterMasterEntity entity : bpmList){
					if(entity!=null)
					{
						ElectricityBillParametersEntity billingParameterMaster = electricityBillParameterService.getSingleResult("select obj from ElectricityBillParametersEntity obj where obj.billParameterMasterEntity.bvmId="+entity.getBvmId()+" and obj.electricityBillEntity.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and obj.electricityBillEntity.elBillId="+elBillId);
						if(billingParameterMaster!=null)
						{
							billDetails.put(entity.getBvmName(), billingParameterMaster.getElBillParameterValue());
						}else {
							billDetails.put(entity.getBvmName(),"0.00");
						}
						
						
					}
                  }
			}
			List<ElectricityBillLineItemEntity> bliList = billLineItemService.executeSimpleQuery(lineItems);
			if(bliList.size()>0){
				for (ElectricityBillLineItemEntity electricityBillLineItemEntity : bliList) {

					List<TransactionMasterEntity> listTM =  transactionMasterService.executeSimpleQuery("select o from TransactionMasterEntity o where o.transactionCode='"+electricityBillLineItemEntity.getTransactionCode()+"'");
					if(!listTM.isEmpty())
					{
						for (TransactionMasterEntity transactionMasterEntity : listTM)
						{
							billDetails.put(transactionMasterEntity.getTransactionName(), electricityBillLineItemEntity.getBalanceAmount());
						}
					}
				}
			}

			List<ServiceMastersEntity> serviceMastersList = serviceMasterService.executeSimpleQuery("select obj from ServiceMastersEntity obj where obj.accountObj.accountId="+electricityBillEntity.getAccountObj().getAccountId()+" and obj.typeOfService='"+electricityBillEntity.getTypeOfService()+"'");

			if(!serviceMastersList.isEmpty() && serviceMastersList.size()>0){ 
				for (ServiceMastersEntity serviceMastersEntity : serviceMastersList) {
					if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Electricity")){
						ELTariffMaster eltariffMaster = elTariffMasterService.getSingleResult("select o from ELTariffMaster o where o.elTariffID="+serviceMastersEntity.getElTariffID());
						if(eltariffMaster!=null)
							tariffName = eltariffMaster.getTariffName(); 
					}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Gas")){
						GasTariffMaster gasTariffMaster = gasTariffMasterService.getSingleResult("select o from GasTariffMaster o where o.gasTariffId="+serviceMastersEntity.getGaTariffID());
						if(gasTariffMaster!=null)
							tariffName = gasTariffMaster.getGastariffName();
					}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Water")){
						List<WTTariffMaster> wttariffMaster = wtTariffMasterService.executeSimpleQuery("select o from WTTariffMaster o where o.wtTariffId="+serviceMastersEntity.getWtTariffID());
						System.out.println(">>>>>>>>>>>>>>"+"select o from WTTariffMaster o where o.wtTariffID="+serviceMastersEntity.getWtTariffID());
						System.out.println(wttariffMaster);
						if(wttariffMaster!=null && wttariffMaster.size()>0){
							System.out.println("-->"+wttariffMaster.get(0).getTariffName());
							tariffName = wttariffMaster.get(0).getTariffName();
						}
					}else if(serviceMastersEntity.getTypeOfService().equalsIgnoreCase("Solid Waste")){
						SolidWasteTariffMaster solidWasteTariffMaster = solidWasteTariffMasterService.getSingleResult("select o from SolidWasteTariffMaster o where o.solidWasteTariffId="+serviceMastersEntity.getSwTariffID());
						if(solidWasteTariffMaster!=null)
							tariffName = solidWasteTariffMaster.getSolidWasteTariffName();
					}
				}
			}


String mobile = "";
String email = "";
String address1 = "";

if(address!=null && contactEmail!=null && contactMob!=null){
	email = contactEmail.getContactContent();
	mobile = contactMob.getContactContent();
	address1 = address.getAddress1();
	billDetails.put("address", address1);
	billDetails.put("email", email);
	billDetails.put("mobile", mobile);
}else{
} }
		billDetails.put("accountID", electricityBillEntity.getAccountObj().getAccountNo());
		billDetails.put("personName", electricityBillEntity.getAccountObj().getPerson().getFirstName()+" "+ electricityBillEntity.getAccountObj().getPerson().getLastName());
		billDetails.put("typeOfService", electricityBillEntity.getTypeOfService());
		billDetails.put("tariffName", tariffName);
		billDetails.put("billType", billType);
		billDetails.put("postType", postType);
		return billDetails;
	}
	
	
	@RequestMapping(value = "webservice/mobileLogin" , method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String mobileLogin( HttpServletRequest request,HttpServletResponse response,@RequestBody String body) throws IOException, JSONException {
	String userName = null;
	String password = null;
	JSONArray jsonArray = new JSONArray(body);
	for (int i = 0; i < jsonArray.length(); i++)
	  {
		
		JSONObject jsonObject = jsonArray.getJSONObject(i);
		if(jsonObject.get("userMailId")!=null)
		{
			userName = jsonObject.getString("userMailId");
		}
		
		if(jsonObject.get("password")!=null)
		{
			password = jsonObject.getString("password");
		}
		logger.info("userName ------"+userName +"password -------- "+password);
}	
	LdapBusinessModel businessModel = new LdapBusinessModel();
	String status = null;
	if(userName!=null && password!=null)
	{
		 status=businessModel.loginCheck(userName, password);
	}
	System.out.println(status);
	if(status == null){
	status="invalid";
	}
	else{
	if(status == ""){
		status="invalid";
	    }
	   else{
		   
		status="success";
		System.out.println("status value="+status);
	  }
	}

	return status;
	}
}

