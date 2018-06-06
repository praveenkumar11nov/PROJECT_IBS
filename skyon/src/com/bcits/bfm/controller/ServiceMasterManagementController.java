package com.bcits.bfm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.MeterHistoryEntity;
import com.bcits.bfm.model.ServiceAccountEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.serviceMasterManagement.MeterHistoryService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceAccountService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class ServiceMasterManagementController {
	
	static Logger logger = LoggerFactory.getLogger(ServiceMasterManagementController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private ElectricityBillsService electricityBillsService;

	@Autowired
	private ElectricityBillLineItemService electricityBillLineItemService;

	@Autowired
	private ElectricityBillParameterService electricityBillParameterService;

	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ServiceParameterService serviceParameterService;
	
	@Autowired
	private ServiceAccountService serviceAccountService;
	
	@Autowired
	private MeterHistoryService meterHistoryService;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/serviceMasters", method = RequestMethod.GET)
	public String serviceMasters(ModelMap model, HttpServletRequest request) {
		logger.info("In serviceMasters  Method");
		model.addAttribute("ViewName", "Service Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service Masters", 1, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "serviceMasters/serviceMasters";
	}

	@RequestMapping("/serviceMaster")
	public String servicePoint(HttpServletRequest request, Model model) {
		logger.info("In serviceMaster Method");
		model.addAttribute("ViewName", "Service");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service", 1, request);
		breadCrumbService.addNode("Manage Service Master", 2, request);
		model.addAttribute("TariffMaster", commonController.populateCategories("elTariffID", "tariffName", "ELTariffMaster"));
		model.addAttribute("serviceParameterNames", commonController.populateCategories("spmId", "spmName", "ServiceParameterMaster"));
		model.addAttribute("accounts", commonController.populateCategories("accountId", "accountNo", "Account"));
		
		return "serviceMasters/serviceMaster";
	}

	// ****************************** Service Masters Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/serviceMasters/ServiceMasterRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServiceMastersEntity> readServiceMasterData() {
		
		logger.info("In readServiceMasterData Method");
		List<ServiceMastersEntity> mastersEntities = serviceMasterService.findALL();
		return mastersEntities;
	}

	@RequestMapping(value = "/serviceMasters/serviceMasterStatus/{serviceMasterId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void serviceMasterStatus(@PathVariable int serviceMasterId,@PathVariable String operation, HttpServletResponse response) {
		logger.info("In serviceMaster Status Change Method");
		if (operation.equalsIgnoreCase("activate"))
			serviceMasterService.setServiceMasterStatus(serviceMasterId,operation, response);
		else
			serviceMasterService.setServiceMasterStatus(serviceMasterId,operation, response);
	}

	@RequestMapping(value = "/serviceMasters/ServiceMasterCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServiceMaster(@ModelAttribute("mastersEntity") ServiceMastersEntity mastersEntity,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws ParseException {

		logger.info("In Service Master Create Method");
		mastersEntity.setStatus("Inactive");
		mastersEntity.setAccountObj(accountService.find(mastersEntity.getAccountId()));
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Electricity")){
			mastersEntity.setElTariffID(mastersEntity.getElTariffID());
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Gas")){
			mastersEntity.setGaTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Water")){
			mastersEntity.setWtTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Solid Waste")){
			mastersEntity.setSwTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Others")){
			mastersEntity.setOthersTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		mastersEntity.setServiceStartDate(dateTimeCalender.getDateToStore(req.getParameter("serviceStartDate")));

		serviceMasterService.save(mastersEntity);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("serviceMasterId", mastersEntity.getServiceMasterId());
		parameterMap.put("todApplicable", mastersEntity.getTodApplicable());
		parameterMap.put("accountId", mastersEntity.getAccountObj().getAccountId());
		parameterMap.put("elTariffID",mastersEntity.getElTariffID());
		parameterMap.put("typeOfService",mastersEntity.getTypeOfService());
		parameterMap.put("serviceStartDate",mastersEntity.getServiceStartDate());
		parameterMap.put("serviceEndDate", mastersEntity.getServiceEndDate());
		parameterMap.put("status", mastersEntity.getStatus());
		parameterMap.put("createdBy", mastersEntity.getCreatedBy());
		parameterMap.put("lastUpdatedBy", mastersEntity.getLastUpdatedBy());
		parameterMap.put("lastUpdatedDT", mastersEntity.getLastUpdatedDT());
		
		return parameterMap;
	}

	@RequestMapping(value = "/serviceMasters/ServiceMasterUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editServiceMasters(@ModelAttribute("mastersEntity") ServiceMastersEntity mastersEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In Service Master Update Method");
		mastersEntity.setServiceStartDate(dateTimeCalender.getDateToStore(req.getParameter("serviceStartDate")));
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Electricity")){
			mastersEntity.setElTariffID(mastersEntity.getElTariffID());
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Gas")){
			mastersEntity.setGaTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Water")){
			mastersEntity.setWtTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Solid Waste")){
			mastersEntity.setSwTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(mastersEntity.getTypeOfService()!=null && mastersEntity.getTypeOfService().equals("Others")){
			mastersEntity.setOthersTariffID(mastersEntity.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		ServiceMastersEntity temp=serviceMasterService.find(mastersEntity.getServiceMasterId());
		mastersEntity.setServiceEndDate(temp.getServiceEndDate());
		mastersEntity.setAccountObj(accountService.find(mastersEntity.getAccountId()));
		serviceMasterService.update(mastersEntity);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("serviceMasterId", mastersEntity.getServiceMasterId());	
		parameterMap.put("elTariffID",mastersEntity.getElTariffID());
		parameterMap.put("todApplicable", mastersEntity.getTodApplicable());
		parameterMap.put("typeOfService",mastersEntity.getTypeOfService());
		parameterMap.put("serviceStartDate",mastersEntity.getServiceStartDate());
		parameterMap.put("serviceEndDate", mastersEntity.getServiceEndDate());
		parameterMap.put("status", mastersEntity.getStatus());
		parameterMap.put("createdBy", mastersEntity.getCreatedBy());
		parameterMap.put("lastUpdatedBy", mastersEntity.getLastUpdatedBy());
		parameterMap.put("lastUpdatedDT", mastersEntity.getLastUpdatedDT());
		return parameterMap;
	}

	@RequestMapping(value = "/serviceMasters/ServiceMasterDestroy", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteServiceMasters(@ModelAttribute("mastersEntity") ServiceMastersEntity mastersEntity,	BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Service Master Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if(mastersEntity.getStatus().equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
			{
			  /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
				put("AciveServiceMasterDestroyError", messageSource.getMessage("ServiceMastersEntity.AciveServiceMasterDestroyError", null, locale));
			  }
			};
			errorResponse.setStatus("AciveServiceMasterDestroyError");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;				
		}else{
		try {
			serviceMasterService.delete(mastersEntity.getServiceMasterId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return mastersEntity;
		}
	}
	
	@RequestMapping(value = "/serviceMasters/serviceEndDateUpdate/{serviceMasterId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void serviceEndDateUpdate(@PathVariable int serviceMasterId, HttpServletResponse response)
	{		
		serviceMasterService.serviceEndDateUpdate(serviceMasterId, response);
	}

	// ****************************** ServiceMaster Filters Data methods ********************************/

	@RequestMapping(value = "/serviceMasters/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getServiceMasterContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ServiceMastersEntity",attributeList, null));
		
		return set;
	}

	// ****************************** Service Master Parameter Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/serviceMasters/parameterRead/{serviceMasterId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServiceParametersEntity> readServiceMasterParameters(@PathVariable int serviceMasterId) {
		logger.info("In Service Master Parameter read Method");
		List<ServiceParametersEntity> serviceParametersEntities = serviceParameterService.findAllById(serviceMasterId);
		return serviceParametersEntities;
	}

	@RequestMapping(value = "/serviceMasters/parameterCreate/{serviceMasterId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServiceMasterParameters(@ModelAttribute("serviceParametersEntity") ServiceParametersEntity serviceParametersEntity,BindingResult result, @PathVariable int serviceMasterId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale)throws ParseException {

		logger.info("In Service Master Parameter Create Method");
		serviceParametersEntity.setStatus("Inactive");
		serviceParametersEntity.setServiceMastersEntity(serviceMasterService.find(serviceMasterId));
		serviceParametersEntity.setServiceParameterDataType(serviceParameterService.getServiceDataType(serviceParametersEntity.getSpmId()).getSpmdataType());
		
		serviceParameterService.save(serviceParametersEntity);
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("serviceParameterId", serviceParametersEntity.getServiceParameterId());
		parameterMap.put("serviceMasterId", serviceParametersEntity.getServiceMastersEntity().getServiceMasterId());
		parameterMap.put("spmId",serviceParametersEntity.getSpmId());
		parameterMap.put("serviceParameterDataType",serviceParametersEntity.getServiceParameterDataType());
		parameterMap.put("serviceParameterValue",serviceParametersEntity.getServiceParameterValue());
		parameterMap.put("serviceParameterSequence", serviceParametersEntity.getServiceParameterSequence());
		parameterMap.put("status", serviceParametersEntity.getStatus());
		parameterMap.put("createdBy", serviceParametersEntity.getCreatedBy());
		parameterMap.put("lastUpdatedBy", serviceParametersEntity.getLastUpdatedBy());
		parameterMap.put("lastUpdatedDT", serviceParametersEntity.getLastUpdatedDT());
		return parameterMap;
	}

	@RequestMapping(value = "/serviceMasters/parameterUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editParameters(@ModelAttribute("serviceParametersEntity") ServiceParametersEntity serviceParametersEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In Service Master Parameter Update Method");
		serviceParametersEntity.setServiceMastersEntity(serviceMasterService.find(serviceParametersEntity.getServiceMasterId()));
		serviceParameterService.update(serviceParametersEntity);
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("serviceParameterId", serviceParametersEntity.getServiceParameterId());	
		parameterMap.put("spmId",serviceParametersEntity.getSpmId());
		parameterMap.put("serviceParameterDataType",serviceParametersEntity.getServiceParameterDataType());
		parameterMap.put("serviceParameterValue",serviceParametersEntity.getServiceParameterValue());
		parameterMap.put("serviceParameterSequence", serviceParametersEntity.getServiceParameterSequence());
		parameterMap.put("status", serviceParametersEntity.getStatus());
		parameterMap.put("createdBy", serviceParametersEntity.getCreatedBy());
		parameterMap.put("lastUpdatedBy", serviceParametersEntity.getLastUpdatedBy());
		parameterMap.put("lastUpdatedDT", serviceParametersEntity.getLastUpdatedDT());
		return parameterMap;
	}

	@RequestMapping(value = "/serviceMasters/parameterDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteParameter(@ModelAttribute("serviceParametersEntity") ServiceParametersEntity serviceParametersEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Service Master Parameter Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if(serviceParametersEntity.getStatus().equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
			{
			  /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
				put("AciveParameterDestroyError", messageSource.getMessage("ServiceParametersEntity.AciveParameterDestroyError", null, locale));
			  }
			};
			errorResponse.setStatus("AciveParameterDestroyError");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;				
		}else{
		try {
			serviceParameterService.delete(serviceParametersEntity.getServiceParameterId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return serviceParametersEntity;
		}
	}
	
	@RequestMapping(value = "/serviceMasters/parameterUpdateFromInnerGrid/{serviceParameterId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void parameterStatusUpdateFromInnerGrid(@PathVariable int serviceParameterId, HttpServletResponse response)
	{		
		serviceParameterService.updateParameterStatusFromInnerGrid(serviceParameterId, response);
	}
	
	// ****************************** Service Master Filter Data methods ********************************/

	@RequestMapping(value = "/serviceParameters/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getServiceParameterContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ServiceParametersEntity",attributeList, null));
		
		return set;
	}

	// ****************************** Service Master Account Read,Create,Update,Delete methods ********************************//

	@RequestMapping(value = "/serviceMasters/serviceAccountReadUrl/{serviceMasterId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServiceAccountEntity> readServiceAccounts(@PathVariable int serviceMasterId) {
		logger.info("In Service Account Read Method");
		List<ServiceAccountEntity> serviceAccountEntities = serviceAccountService.findAllById(serviceMasterId);
		return serviceAccountEntities;
	}

	@RequestMapping(value = "/serviceMasters/serviceAccountCreate/{serviceMasterId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServiceAccounts(@ModelAttribute("serviceAccountEntity") ServiceAccountEntity serviceAccountEntity,BindingResult bindingResult, @PathVariable int serviceMasterId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

		logger.info("In Service Account Create Method");
		serviceAccountEntity.setStatus("Inactive");
		serviceAccountEntity.setServiceMasterId(serviceMasterId);
		serviceAccountEntity.setAccountId(serviceAccountService.getServiceAccount(serviceAccountEntity.getServiceMasterId()).getAccountObj().getAccountId());
		serviceAccountEntity.setLedgerStartDate(dateTimeCalender.getDateToStore(req.getParameter("ledgerStartDate")));

		serviceAccountService.save(serviceAccountEntity);
		
		return serviceAccountEntity;
	}

	@RequestMapping(value = "/serviceMasters/serviceAccountUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editAccounts(@ModelAttribute("serviceAccountEntity") ServiceAccountEntity serviceAccountEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In Service Account Update Method");
		serviceAccountEntity.setLedgerStartDate(dateTimeCalender.getDateToStore(req.getParameter("ledgerStartDate")));
		ServiceAccountEntity temp =serviceAccountService.find(serviceAccountEntity.getServiceAccoutId());
		serviceAccountEntity.setLedgerEndDate(temp.getLedgerEndDate());
		
		serviceAccountService.update(serviceAccountEntity);
		
		return serviceAccountEntity;
	}

	@RequestMapping(value = "/serviceMasters/serviceAccountDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteServiceAccount(@ModelAttribute("serviceAccountEntity") ServiceAccountEntity serviceAccountEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In Service Account Destroy Method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if(serviceAccountEntity.getStatus().equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>()
			{
			  /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
				put("AciveServiceAccountDestroyError", messageSource.getMessage("ServiceAccountEntity.AciveServiceAccountDestroyError", null, locale));
			  }
			};
			errorResponse.setStatus("AciveServiceAccountDestroyError");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;				
		}else{
		try {
			serviceAccountService.delete(serviceAccountEntity.getServiceAccoutId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return serviceAccountEntity;
		}
	}
	
	@RequestMapping(value = "/serviceMasters/serviceAccountUpdateFromInnerGrid/{serviceAccoutId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void equipmentStatusUpdateFromInnerGrid(@PathVariable int serviceAccoutId, HttpServletResponse response)
	{		
		serviceAccountService.updateAccountStatusFromInnerGrid(serviceAccoutId, response);
	}
	
	@RequestMapping(value = "/serviceMasters/ledgerEndDateUpdate/{serviceAccoutId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void ledgerEndDateUpdate(@PathVariable int serviceAccoutId, HttpServletResponse response)
	{		
		serviceAccountService.ledgerEndDateUpdate(serviceAccoutId, response);
	}

	// ****************************** Service Account Filter Data methods ********************************/

	@RequestMapping(value = "/serviceAccountss/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getServiceAccountsContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ServiceAccountEntity",attributeList, null));
		
		return set;
	} 
	
	// ****************************** Service Master Jsp Editor Data methods ********************************/
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/serviceMasters/readAccountNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readAccountNumber()
	{		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Account record : serviceMasterService.findAccountNumbers()) {
	            result.add(new HashMap<String, Object>() 
	            {{
	            	put("accountId", record.getAccountId());
	            	put("personId", record.getPersonId());
	            	put("personName", record.getPersonName());
	            	put("accountNo", record.getAccountNo());
	            	put("accountType", record.getAccountType());
	            }});
	        }
	        return result;
	}
	
	@RequestMapping(value = "/serviceMasters/getPersonListBasedOnAccountNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersons() 
	{

		Set<?> accountPersonList = serviceMasterService.findPersons();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = null;

		for (Iterator<?> i = accountPersonList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();

			String personName = "";
			personName = personName.concat((String)values[1]);
			if(((String)values[2]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[2]);
			}

			map = new HashMap<String, Object>();
			//map.put("accountId", (Integer)values[1]);
			map.put("personId", (Integer)values[0]);
			map.put("personName", personName);
			map.put("personType", (String)values[3]);
			map.put("personStyle", (String)values[4]);

			result.add(map);
		}

		return result;
	}
	
	@RequestMapping(value = "/serviceMasters/getPersonListBasedOnAccountIDForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNames() 
	{
		List<?> accountPersonList = serviceMasterService.findPersonBasedOnAccountIdForFilters();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = accountPersonList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();

			String personName = "";
			personName = personName.concat((String)values[1]);
			if(((String)values[2]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[2]);
			}

			map = new HashMap<String, Object>();
			map.put("personId", (Integer)values[0]);
			map.put("personName", personName);
			map.put("personType", (String)values[3]);
			map.put("personStyle", (String)values[4]);

			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/serviceMasters/electricityTariffList/{serviceType}/{todtype}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> electricityTariffList(@PathVariable String serviceType,@PathVariable String todtype)
	{		
		logger.info("todtype:::::::::::"+todtype);
		return serviceMasterService.getElectricityTariffList(serviceType,todtype);
	}
	
	@RequestMapping(value = "/serviceMasters/gasTariffList/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> gasTariffList(@PathVariable String serviceType)
	{		
		return serviceMasterService.getGasTariffList();
	}
	
	@RequestMapping(value = "/serviceMasters/waterTariffList/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> waterTariffList(@PathVariable String serviceType)
	{		
		return serviceMasterService.getWaterTariffList();
	}
	
	@RequestMapping(value = "/serviceMasters/solidWasteTariffList/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> solidWasteTariffList(@PathVariable String serviceType)
	{		
		return serviceMasterService.getSolidWasteTariffList();
	}
	
	@RequestMapping(value = "/serviceMasters/othersTariffList/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> othersTariffList(@PathVariable String serviceType)
	{		
		return serviceMasterService.getOthersTariffList();
	}
	
	@RequestMapping(value = "/serviceMasters/broadBandTariffList/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> broadBandTariffList(@PathVariable String serviceType)
	{		
		return serviceMasterService.getBroadBandTariffList();
	}
	
	@RequestMapping(value = "/serviceMasters/tariffList", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> allTariffList()
	{		
		return serviceMasterService.allTariffList();
	}
	
	@RequestMapping(value = "/serviceMasters/serviceParameterList/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> serviceParameterList(@PathVariable String serviceType)
	{		
		return serviceMasterService.serviceParameterList(serviceType);
	}
	
	// ****************************** Meter History Read,Create,Update,Delete methods ********************************//

		@RequestMapping(value = "/serviceMasters/meterHistoryRead/{serviceMasterId}", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<MeterHistoryEntity> meterHistoryRead(@PathVariable int serviceMasterId) {
			logger.info("In Meter History Read Method");
			List<MeterHistoryEntity> meterHistoryEntities = meterHistoryService.findAllById(serviceMasterId);
			return meterHistoryEntities;
		}
}
