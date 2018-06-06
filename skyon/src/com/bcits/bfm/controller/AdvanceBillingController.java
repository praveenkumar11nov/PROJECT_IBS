package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.advanceBilling.AdvanceBillingService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class AdvanceBillingController {
	static Logger logger = LoggerFactory.getLogger(AdvanceBillingController.class);
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private AdvanceBillingService advanceBillingService;
	
	@Autowired
	ElectricityBillParameterService billParameterService;
	
	@Autowired
	TariffCalculationController calculationController;
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	ServiceParameterService serviceParameterService;

	@Autowired
	private CommonService commonService;

	@RequestMapping("/advanceBilling")
	public String accessCardsIndex(HttpServletRequest request, Model model) {
		logger.info("In Advance Billing Method");
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage Advance Billing", 2, request);

		return "electricityBills/advanceBilling";
	}
	@RequestMapping(value = "/advanceBills/approveBill/{abBillId}/{operation}/{totalBillAmount}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void tariffStatus(@PathVariable int elBillId,
			@PathVariable String operation,
			@PathVariable float totalBillAmount, HttpServletResponse response)
			throws IOException {
		logger.info(":::::::::::: Approve the Bill With ID ::::::::::::::::: "
				+ elBillId);
		logger.info("operation :::::::::: " + operation);
		logger.info("totalBillAmount :::: " + totalBillAmount);
		PrintWriter out = response.getWriter();

		if (operation.equalsIgnoreCase("Generated")) {
			if (totalBillAmount <= 0) {
				out.print("Bill Amount is 0.00 you can't approve");
			} else {
				advanceBillingService.setApproveBill(elBillId, operation,response);
			}
		} else {
			advanceBillingService.setApproveBill(elBillId, operation,response);
		}
	}

	@RequestMapping(value = "/advanceBills/approveBill/{elBillId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	 public void setBillStatus(@PathVariable int elBillId,@PathVariable String operation, HttpServletResponse response) {

	  if (operation.equalsIgnoreCase("activate"))
	  {
		  advanceBillingService.setApproveBill(elBillId, operation,response);
	  }
	 }
	
	@RequestMapping(value = "/advanceBills/billRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<AdvanceBill> readElBillData() {
		logger.info("In Advance Bill Read Method");
		List<AdvanceBill> billEntities = advanceBillingService.findAll();
		return billEntities;
	}
	
	@RequestMapping(value = "/advanceBills/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getBillsDataForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("AdvanceBill", attributeList, null));

		return set;
	}
	@RequestMapping(value = "/abBill/generateabBill", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<Object> generateAdvanceBill(HttpServletRequest req,HttpServletResponse response, @RequestBody String body) throws ParseException, JSONException {
		String serviceName = req.getParameter("serviceName");
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		String accountNo = req.getParameter("accountNo");
		int serviceID = Integer.parseInt(req.getParameter("serviceID"));
		Float units = Float.parseFloat(req.getParameter("units"));
		Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("presentdate"));
		Date previousDate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("previousdate"));

		logger.info(":::::::::serviceName::::::" + serviceName);
		logger.info(":::::::::accountId::::::" + accountId);
		logger.info(":::::::::accountNo::::::" + accountNo);
		logger.info(":::::::::presentdate::::::" + presentdate);
		logger.info(":::::::::units::::::" + units);
		logger.info(":::::::::serviceID::::::" + serviceID);
		logger.info(":::::::::previousDate::::::" + previousDate);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		List<ELRateMaster> elRateMasterList = new ArrayList<>();
		List<WTRateMaster> waterRateMasterList = new ArrayList<>();
		List<GasRateMaster> gasRateMasterList = new ArrayList<>();
		List<SolidWasteRateMaster> solidWasterRateMasterList = new ArrayList<>();

		if (serviceName.equalsIgnoreCase("Electricity")) {
			logger.info("::::::::::::: Electricity service type ::::::::::::: ");
			int tariffId = serviceMasterService.getServiceMasterByAccountNumber(accountId, serviceName);
			logger.info(":::::::::::::" + serviceName+ " Tairff id is ::::::::::::: " + tariffId);
			elRateMasterList = tariffCalculationService.getElectricityRateMaster(tariffId);
			Integer todT1 = 0;
			Integer todT2 = 0;
			Integer todT3 = 0;
			Float dgunit = 0f;
			saveElectricityBillParameters(elRateMasterList, accountId,serviceName, previousDate, presentdate, units);
		}

		if (serviceName.equalsIgnoreCase("Water")) {
			logger.info("::::::::::::: Water service type ::::::::::::: ");
			int waterTariffId = serviceMasterService.getWaterTariffId(accountId, serviceName);
			logger.info(":::::::::::::" + serviceName+ " Tairff id is ::::::::::::: " + waterTariffId);
			waterRateMasterList = tariffCalculationService.getWaterRateMaster(waterTariffId);
			 saveWaterBillParameters(waterRateMasterList,accountId,serviceName,previousDate,presentdate,units);
		}

		if (serviceName.equalsIgnoreCase("Gas")) {
			logger.info("::::::::::::: Gas service type ::::::::::::: ");
			int gasTariffId = serviceMasterService.getGasTariffId(accountId,
					serviceName);
			logger.info(":::::::::::::" + serviceName
					+ " Tairff id is ::::::::::::: " + gasTariffId);
			gasRateMasterList = tariffCalculationService
					.getGasRateMaster(gasTariffId);
			 saveGasBillParameters(gasRateMasterList,accountId,serviceName,previousDate,presentdate,units);

			if (serviceName.equalsIgnoreCase("Others")) {
				// saveOthersBillParameters(accountId,serviceName,previousDate,presentdate,units);
			}

			if (serviceName.equalsIgnoreCase("Solid Waste")) {
				logger.info("::::::::::::: Others service type ::::::::::::: ");
				int solidWasteTariffId = serviceMasterService
						.getSolidWasteTariffId(accountId, serviceName);
				logger.info(":::::::::::::" + serviceName
						+ " Tairff id is ::::::::::::: " + solidWasteTariffId);
				solidWasterRateMasterList = tariffCalculationService
						.getSolidWasteRateMaster(solidWasteTariffId);
				 saveSolidWasteBillParameters(solidWasterRateMasterList,accountId,serviceName,previousDate,presentdate,units);
			}

			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("added successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	
	
	private void saveElectricityBillParameters(
			List<ELRateMaster> elRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue)

	{
		HashMap<Object, Object> consolidatedBill = new LinkedHashMap<>();
		float ecAmount = 0;
		float taxAmount = 0;
		float solarRebate = 0;
		float totalAmount = 0;
		Float roundoffValue = 0f;
		float fsaAmount = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		float fcAmount = 0f;
		float octroiAmount = 0f;
		float dgAMount = 0f;
		float totalamountroundoff=0;
		Calendar c = Calendar.getInstance();
		c.setTime(currentBillDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(c.getTime());
		java.sql.Date billMonth = new java.sql.Date(c.getTime().getTime());
		logger.info("billMonth ::::::::::::::: " + billMonth);

		for (ELRateMaster elRateMaster : elRateMasterList) {
			if (elRateMaster.getRateName().equalsIgnoreCase("EC")) {
				consolidatedBill = calculationController.tariffAmount(elRateMaster, previousBillDate, currentBillDate,uomValue,0.0f,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						ecAmount = ecAmount + (Float) map.getValue();
					}
				}
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("Octroi")) {
				consolidatedBill = calculationController.tariffAmount(elRateMaster, previousBillDate, currentBillDate,uomValue,0.0f,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						octroiAmount = octroiAmount + (Float) map.getValue();
					}
				}
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("FC")) {
				ServiceMastersEntity mastersEntity1 = serviceMasterService.getServiceMasterServicType(accountID, typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities1 = serviceParameterService.findAllByParentId(mastersEntity1.getServiceMasterId());
				float fcUomValue = 0;
				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities1) {
					if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Connected KW")) {
						fcUomValue = Integer.parseInt(serviceParametersEntity.getServiceParameterValue());
					}
				}

				consolidatedBill = calculationController.tariffAmount(elRateMaster, previousBillDate, currentBillDate,fcUomValue,0.0f,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fcAmount = fcAmount + (Float) map.getValue();
						logger.info("fcAmount :::::::::: " + fcAmount);
					}
				}

			}
			if (elRateMaster.getRateName().equalsIgnoreCase("Solar Rebate")) {
				ServiceMastersEntity mastersEntity = serviceMasterService.getServiceMasterServicType(accountID, typeOfService);
				logger.info("accountID::::::::::::::::::::::::::::" + accountID);
				logger.info("typeOfService::::::::::::::::::::::::::::"+ typeOfService);
				List<ServiceParametersEntity> serviceParametersEntities = serviceParameterService.findAllByParentId(mastersEntity.getServiceMasterId());
				logger.info("serviceParametersEntities::::::::::::::::::::::::::::"+ serviceParametersEntities.get(0));

				for (ServiceParametersEntity serviceParametersEntity : serviceParametersEntities) {
					logger.info("serviceParametersEntity.getServiceParameterMaster().getSpmName()"+ serviceParametersEntity.getServiceParameterMaster().getSpmName());

					if (serviceParametersEntity.getServiceParameterMaster().getSpmName().equalsIgnoreCase("Solar Rebate")) {
						logger.info("::::::::::::::Solar Rebate  Applicable::::::::::::::");

						consolidatedBill = calculationController.tariffAmount(elRateMaster, previousBillDate,currentBillDate, ecAmount,0.0f,elRateMaster.getRateNameCategory());
						for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
							if (map.getKey().equals("total")) {
								solarRebate = solarRebate+ (Float) map.getValue();
								logger.info("solarRebate :::::::::: "+ solarRebate);
							}
						}

					} else {
						logger.info("::::::::::::::Solar Rebate Not Applicable::::::::::::::");
					}
				}
			}

			if (elRateMaster.getRateName().equalsIgnoreCase("FSA")) {

				consolidatedBill = calculationController.tariffAmount(elRateMaster, previousBillDate, currentBillDate,uomValue,0.0f,elRateMaster.getRateNameCategory());
				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("total")) {
						fsaAmount = fsaAmount + (Float) map.getValue();
					}
				}
			}

			logger.info("Total without round off "+ (ecAmount + taxAmount + rateOfInterest + fcAmount + octroiAmount + arrearsAmount + dgAMount - solarRebate));

			totalAmount = (ecAmount + taxAmount + rateOfInterest + fcAmount + octroiAmount + arrearsAmount + dgAMount - solarRebate);
			
			logger.info(":::::::::::::::::::totalAmount::::::::::::::::"+totalAmount);
			 totalamountroundoff = (float) Math.ceil(Math.round(totalAmount*100.0)/100.0);
			logger.info("totalamountroundoff :::::::::::::: " + totalamountroundoff);
		}
		AdvanceBill billEntity = saveBill(accountID, totalamountroundoff,previousBillDate, currentBillDate, typeOfService, arrearsAmount,uomValue);

	}
	
	
	private void saveWaterBillParameters(
			List<WTRateMaster> waterRateMasterList, int accountID,
			String typeOfService, Date previousBillDate, Date currentBillDate,
			Float uomValue ) 
	{
		float ecAmount = 0;
		float taxAmount = 0;
		float totalAmount=0;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		HashMap<String, Object> consolidatedBill = new LinkedHashMap<>();
		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase("Water charges")) 
			{
				logger.info(":::::::::::: In "+ waterRateMaster.getWtRateName() + "::::::::::");
				
				consolidatedBill =  calculationController.waterTariffAmount(waterRateMaster, previousBillDate,currentBillDate, uomValue);
				for (Entry<String, Object> map : consolidatedBill
						.entrySet()) {
					if (map.getKey().equals("total")) {
						ecAmount =  (Float) map.getValue();
					}
				}
				logger.info("ecAmount ::::::::: " + ecAmount);
				
			}
		}

		for (WTRateMaster waterRateMaster : waterRateMasterList) {
			if (waterRateMaster.getWtRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In "+ waterRateMaster.getWtRateName() + "::::::::::");
				
				consolidatedBill =  calculationController.waterTariffAmount(waterRateMaster, previousBillDate,currentBillDate, ecAmount);
				for (Entry<String, Object> map : consolidatedBill
						.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount =  (Float) map.getValue();
					}
				}
				logger.info("taxAmount ::::::::: " + taxAmount);
				
			}
			totalAmount = (ecAmount + taxAmount + arrearsAmount);
			logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "+ roundoffValue);
		}
		
		AdvanceBill billEntity = saveBill(accountID, totalAmount,previousBillDate, currentBillDate, typeOfService, arrearsAmount,uomValue);
	}

	private void saveGasBillParameters(List<GasRateMaster> gasRateMasterList,
			int accountID, String typeOfService, Date previousBillDate,
			Date currentBillDate, Float uomValue) {

		float ecAmount = 0;
		float taxAmount = 0;
		float totalAmount=0;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		HashMap<Object, Object> consolidateBill = new LinkedHashMap<>();
		
		
		List<ElectricityBillLineItemEntity> billLineItemEntities = new ArrayList<>();

		for (GasRateMaster gasRateMaster : gasRateMasterList) {
			if (gasRateMaster.getGasRateName().equalsIgnoreCase("Domestic")) 
			{
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()+ "::::::::::");
				
				consolidateBill = calculationController.gasTariffAmount(gasRateMaster,previousBillDate, currentBillDate, uomValue);
				for (Entry<Object, Object> map : consolidateBill
						.entrySet()) {
					if (map.getKey().equals("total")) {
						ecAmount = (Float) map.getValue();
					}
				}
				logger.info("ecAmount ::::::::: " + ecAmount);
			}
		}
		
		for (GasRateMaster gasRateMaster : gasRateMasterList)
		{
			if (gasRateMaster.getGasRateName().equalsIgnoreCase("Tax")) {
				logger.info(":::::::::::: In " + gasRateMaster.getGasRateName()+ "::::::::::");
				
				consolidateBill = calculationController.gasTariffAmount(gasRateMaster,previousBillDate, currentBillDate, ecAmount);
				for (Entry<Object, Object> map : consolidateBill
						.entrySet()) {
					if (map.getKey().equals("total")) {
						taxAmount = (Float) map.getValue();
					}
				}
				logger.info("taxAmount ::::::::: " + taxAmount);
		}
		totalAmount = (ecAmount + taxAmount + arrearsAmount+ rateOfInterest+ 0);
		}
		AdvanceBill billEntity = saveBill(accountID, totalAmount,previousBillDate, currentBillDate, typeOfService, arrearsAmount,uomValue);
	}
	
	private void saveSolidWasteBillParameters(
			List<SolidWasteRateMaster> solidWasterRateMasterList,
			int accountID, String typeOfService, Date previousBillDate,
			Date currentBillDate, Float uomValue) {

		float ecAmount = 0;
		float taxAmount = 0;
		float totalAmount=0;
		Float roundoffValue = 0f;
		float rateOfInterest = 0f;
		float arrearsAmount = 0f;
		for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase("Residential Collection")) {
				logger.info(":::::::::::: In "+ solidWasteRateMaster.getSolidWasteRateName()+ "::::::::::");
				
				ecAmount = ecAmount+ calculationController.solidWasteTariffAmount(solidWasteRateMaster, previousBillDate,currentBillDate, uomValue);
				logger.info("ecAmount ::::::::: " + ecAmount);
				
			}
		}

		for (SolidWasteRateMaster solidWasteRateMaster : solidWasterRateMasterList) {
			if (solidWasteRateMaster.getSolidWasteRateName().equalsIgnoreCase("Tax"))
			{
				logger.info(":::::::::::: In "+ solidWasteRateMaster.getSolidWasteRateName()+ "::::::::::");
				
				taxAmount = taxAmount+ calculationController.solidWasteTariffAmount(solidWasteRateMaster, previousBillDate,currentBillDate, ecAmount);
				logger.info("taxAmount ::::::::: " + taxAmount);
				
			}
			totalAmount = (ecAmount + taxAmount + arrearsAmount+ rateOfInterest+ 0);
			logger.info("::::::::::::::::::::::::: Round off value is ::::::::::::::: "+ roundoffValue);
		}
		
		AdvanceBill billEntity = saveBill(accountID, totalAmount,previousBillDate, currentBillDate, typeOfService, arrearsAmount,uomValue);
		
	}
	
	private AdvanceBill saveBill(int accountID, float totalAmount,Date previousBillDate, Date currentBillDate, String typeOfService,float arrearsAmount,Float units) {
		AdvanceBill billEntity = new AdvanceBill();
		billEntity.setAccountId(accountID);
		billEntity.setAbBillAmount((double) totalAmount);
		billEntity.setAbBillDate(new java.sql.Date(previousBillDate.getTime()));
		billEntity.setAbEndDate(new java.sql.Date(currentBillDate.getTime()));
		billEntity.setStatus("Generated");
		billEntity.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		billEntity.setTransactionCode("EL_ADP");
		billEntity.setUnits(units);
		LocalDate startDate = new LocalDate(previousBillDate);
		LocalDate endDate = new LocalDate(currentBillDate);
		Months months = Months.monthsBetween(startDate, endDate);
		int monthsInYear = months.getMonths();
		billEntity.setNoMonth(monthsInYear);
		billEntity.setTypeOfService(typeOfService);
		billEntity.setAbBillNo("AB" + billParameterService.getSequencyNumber());
		billEntity.setPostType("Advance Bill");
		advanceBillingService.save(billEntity);
		return null;
	}
	
	@RequestMapping(value = "/advanceBills/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNamesInLedger() {
		logger.info("In person data filter method");
		List<?> accountPersonList = advanceBillingService.findPersonForFilters();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = accountPersonList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String) values[1]);
			if (((String) values[2]) != null) {
				personName = personName.concat(" ");
				personName = personName.concat((String) values[2]);
			}

			map = new HashMap<String, Object>();
			map.put("personId", (Integer) values[0]);
			map.put("personName", personName);
			map.put("personType", (String) values[3]);
			map.put("personStyle", (String) values[4]);

			result.add(map);
		}

		return result;
	}
	@RequestMapping(value = "/bill/getAdvanceBillDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	List<?> getBillDate(HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		PrintWriter out;
		Date previousBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("billstartDate"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);

		int month1 = cal.get(Calendar.MONTH);
		int month = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		String typeOfService = req.getParameter("serviceName");
		int accountid = Integer.parseInt(req.getParameter("accountNo"));

		String monthString = new DateFormatSymbols().getMonths()[month - 1];
		List<Object> list = tariffCalculationService.getAdvanceBillOnDate(month, year,
				typeOfService, accountid);
		logger.info("" + list.size());
		if (list.size() > 0) {
			for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
				Object[] values = (Object[]) iterator.next();
				logger.info((String) values[0] + "type of service"
						+ (String) values[1] + "" + (String) values[2] + ""
						+ (Double) values[3]);

				try {
					out = response.getWriter();
					out.write("Bill of " + (String) values[0]
							+ " for Account No " + (String) values[1] + " of "
							+ monthString + " has been already Generated.\n"
							+ "Reference Bill No.-" + (String) values[2]
							+ " Bill Amount= " + (Double) values[3]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				out = response.getWriter();
				out.write("calculate");

			} catch (IOException e) {

				e.printStackTrace();
			}

			logger.info("Bill Has Not bee Calculated");
		}

		return null;
	}
	@RequestMapping(value = "/bill/getAdvanceBillPreviousDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	List<?> getPreviousBillDate(HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		PrintWriter out;
		
		String typeOfService = req.getParameter("serviceName");
		int accountId = Integer.parseInt(req.getParameter("accountNo"));

		
		return tariffCalculationService.getAdvanceBillPreviousDate(typeOfService, accountId);	}

}
