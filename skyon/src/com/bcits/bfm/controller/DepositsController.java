package com.bcits.bfm.controller;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Deposits;
import com.bcits.bfm.model.DepositsLineItems;
import com.bcits.bfm.model.DepositsRefundEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.deposits.DepositsLineItemsService;
import com.bcits.bfm.service.deposits.DepositsRefundService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class DepositsController 
{
	private static final Log logger = LogFactory.getLog(DepositsController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private DepositsService depositsService;
	
	@Autowired
	private ServiceParameterService serviceParameterService;
	
	@Autowired
	AdjustmentService adjustmentService;
	
	@Autowired
	DepositsLineItemsService depositsLineItemsService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DepositsRefundService depositsRefundService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(value = "/deposits", method = RequestMethod.GET)
	public String deposits(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		logger.info("---------- In Deposits controller -------------------");
		model.addAttribute("ViewName", "Account");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Account", 1, request);
		breadCrumbService.addNode("Manage Deposits", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "deposits/addDeposits";
	}
	
	@RequestMapping(value = "/deposits/depositsCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createDeposits(@ModelAttribute("deposits") Deposits deposits, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Deposits save method ---------------");
		deposits.setStatus("Generated");
		deposits.setDepositType("Old");
		deposits.setAccountObj(accountService.find(deposits.getAccountId()));
		
		depositsService.save(deposits);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("depositsId", deposits.getDepositsId());
		parameterMap.put("accountId", deposits.getAccountObj().getAccountId());
		parameterMap.put("accountNo",deposits.getAccountObj().getAccountNo());
		parameterMap.put("totalAmount",deposits.getTotalAmount());
		
		return parameterMap;
	}
	
	@RequestMapping(value = "/deposits/depositsRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object readDeposits(@ModelAttribute("deposits") Deposits deposits, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Deposits read method ---------------");
		List<Deposits> depositsList = depositsService.findAll();
		return depositsList;
	}
	
	@RequestMapping(value = "/deposits/depositsUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editAdjustmentDetails(@ModelAttribute("deposits") Deposits deposits,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In edit adjustments details method");
		
		validator.validate(deposits, bindingResult);
		deposits.setAccountObj(accountService.find(deposits.getAccountId()));
		depositsService.update(deposits);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("depositsId", deposits.getDepositsId());
		parameterMap.put("accountId", deposits.getAccountObj().getAccountId());
		parameterMap.put("accountNo",deposits.getAccountObj().getAccountNo());
		parameterMap.put("totalAmount",deposits.getTotalAmount());
		
		return parameterMap;
	}

	@RequestMapping(value = "/deposits/depositsDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteAdjustmentsDetsails(@ModelAttribute("deposits") Deposits deposits,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete adjustment details method");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			depositsService.delete(deposits.getDepositsId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return deposits;
	}

	@RequestMapping(value = "/deposits/getAllAccountNumbers", method = RequestMethod.GET)
		public @ResponseBody List<?> getAllAccountNumbers() {
			return depositsService.getAllAccountNumbers();
		}
	
	@RequestMapping(value = "/deposits/setDepositsStatus/{depositsId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setBillStatus(@PathVariable int depositsId,@PathVariable String operation, HttpServletResponse response)
	{

		if (operation.equalsIgnoreCase("activate"))
		{
			depositsService.setDepositsStatus(depositsId, operation, response);
		} 
		else 
		{
			depositsService.setDepositsStatus(depositsId, operation, response);
		}
	}
	
	// ******************************* Deposit use case filters method *****************************************/
	
		@RequestMapping(value = "/deposits/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForAccountNumbersUrl() {
			return depositsService.commonFilterForAccountNumbersUrl();
		}
		
		@RequestMapping(value = "/deposits/commonFilterForPropertyNoUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForPropertyNoUrl() {
			return depositsService.commonFilterForPropertyNoUrl();
		}
	
	 // ****************************** Deposit Line  Items Read,Save,Update and Delete methods ********************************/
	
	@RequestMapping(value = "/depositsDetails/depositsDetailsRead/{depositsId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object readDepositsLineItems(@ModelAttribute("depositsLineItems") DepositsLineItems depositsLineItems,@PathVariable int depositsId, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- DepositsLineItems read method  with Id ---------------"+depositsId);
		List<DepositsLineItems> depositsLineItemsList = depositsLineItemsService.findAll(depositsId);
		return depositsLineItemsList;
	}
	
	@RequestMapping(value = "/depositsDetails/depositLineItemCreate/{depositsId}", method = RequestMethod.GET)
  	public @ResponseBody Object saveDepositLineItem(@ModelAttribute("depositsLineItems") DepositsLineItems depositsLineItems,BindingResult bindingResult,@PathVariable int depositsId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		logger.info("In save deposit line items method");
  		Deposits depositEntity = depositsService.find(depositsId);
  		int accountId = depositEntity.getAccountObj().getAccountId();
  		
  		if(depositsLineItems.getLoadEnhanced().equals("Amount On Enhanced Load")){
  			double sanctionedLoad = Double.parseDouble(depositsLineItems.getSancationedLoad());
  			double loadChange = 0.0;
  			if(depositsLineItems.getNewSancationedLoad() >= sanctionedLoad){
  				loadChange = depositsLineItems.getNewSancationedLoad()-sanctionedLoad;
  				
  				float rate = 0;
  				if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KW")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KW");
  					int serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,depositsLineItems.getTypeOfServiceDeposit(),"Sanctioned KW");
  					if(serviceParameterId!=0){
  						ServiceParametersEntity parametersEntity = serviceParameterService.find(serviceParameterId);
  						parametersEntity.setServiceParameterValue(""+depositsLineItems.getNewSancationedLoad());  		  		
  		  		  		serviceParameterService.update(parametersEntity);
  					}
  				}else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KVA")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KVA");
  					int serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,depositsLineItems.getTypeOfServiceDeposit(),"Sanctioned KVA");
  					if(serviceParameterId!=0){
  						ServiceParametersEntity parametersEntity = serviceParameterService.find(serviceParameterId);
  						parametersEntity.setServiceParameterValue(""+depositsLineItems.getNewSancationedLoad());  		  		
  		  		  		serviceParameterService.update(parametersEntity);
  					}
  				} else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned HP")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On HP");
  					int serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,depositsLineItems.getTypeOfServiceDeposit(),"Sanctioned HP");
  					if(serviceParameterId!=0){
  						ServiceParametersEntity parametersEntity = serviceParameterService.find(serviceParameterId);
  						parametersEntity.setServiceParameterValue(""+depositsLineItems.getNewSancationedLoad());  		  		
  		  		  		serviceParameterService.update(parametersEntity);
  					}
  				}
  				
  				depositsLineItems.setAmount(rate*loadChange);
  				depositsLineItems.setLoadChangeUnits(loadChange);
  				String notes =depositsLineItems.getNewSancationedLoad()+" (New "+depositsLineItems.getSancationedLoadName()+") - "+sanctionedLoad+" (Old "+depositsLineItems.getSancationedLoadName()+") = "+loadChange+" (Load Change)";
  				depositsLineItems.setNotes(notes); 
  				
  				depositsLineItems.setDeposits(depositEntity);
  		  		depositsLineItems.setCategory("Deposit");
  		  		depositsLineItems.setCollectionType("Manual");
  		  		if(!depositsLineItems.getPaymentMode().equals("Cash")){
  		  			depositsLineItems.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
  				}
  		  		
  		  		validator.validate(depositsLineItems, bindingResult);
  		  		depositEntity.setTotalAmount(depositEntity.getTotalAmount()+depositsLineItems.getAmount());
  		  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  		  		depositsService.update(depositEntity);
  				
  			}else{
  				loadChange = sanctionedLoad-depositsLineItems.getNewSancationedLoad();

  				float rate = 0;
  				if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KW")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KW");
  				}else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KVA")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KVA");
  				} else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned HP")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On HP");
  				}
  				
  				depositsLineItems.setAmount(rate*loadChange);
  				depositsLineItems.setLoadChangeUnits(loadChange);
  				String notes =sanctionedLoad+" (Old "+depositsLineItems.getSancationedLoadName()+") - "+depositsLineItems.getNewSancationedLoad()+" (New "+depositsLineItems.getSancationedLoadName()+") = "+loadChange+" (Load Change)";
  				depositsLineItems.setNotes(notes); 
  				
  				depositsLineItems.setDeposits(depositEntity);
  				depositsLineItems.setStatus("Refund");
  		  		depositsLineItems.setCategory("Deposit");
  		  		depositsLineItems.setCollectionType("Manual");
  		  		if(!depositsLineItems.getPaymentMode().equals("Cash")){
  		  			depositsLineItems.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
  				}
  		  		
  		  		validator.validate(depositsLineItems, bindingResult);
  		  		depositEntity.setRefundAmount(depositsLineItems.getAmount());
  		  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  		  		depositsService.update(depositEntity);
  			}
  		}else{
  			
  			depositsLineItems.setDeposits(depositEntity);
  	  		depositsLineItems.setCategory("Deposit");
  	  		depositsLineItems.setCollectionType("Manual");
  	  		if(!depositsLineItems.getPaymentMode().equals("Cash")){
  	  			depositsLineItems.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
  			}
  	  		
  	  		validator.validate(depositsLineItems, bindingResult);
  	  		depositEntity.setTotalAmount(depositEntity.getTotalAmount()+depositsLineItems.getAmount());
  	  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  	  		depositsService.update(depositEntity);  			
  		}
  		
  		depositsLineItemsService.save(depositsLineItems);
  		
  		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("ddId", depositsLineItems.getDdId());
		parameterMap.put("typeOfServiceDeposit", depositsLineItems.getTypeOfServiceDeposit());
		parameterMap.put("transactionCode",depositsLineItems.getTransactionCode());
		parameterMap.put("transactionName",depositsLineItems.getTransactionName());
		parameterMap.put("category",depositsLineItems.getCategory());
		parameterMap.put("amount", depositsLineItems.getAmount());
		parameterMap.put("collectionType", depositsLineItems.getCollectionType());
		parameterMap.put("instrumentNo", depositsLineItems.getInstrumentNo());
		parameterMap.put("instrumentDate", depositsLineItems.getInstrumentDate());
		
		return parameterMap;
  	}
  	
  	@RequestMapping(value = "/depositsDetails/depositLineItemUpdate/{depositsId}", method = RequestMethod.GET)
  	public @ResponseBody Object editDepositLineItem(@ModelAttribute("depositsLineItems") DepositsLineItems depositsLineItems,BindingResult bindingResult,@PathVariable int depositsId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		logger.info("In edit deposit line item method");
  		
  		Deposits depositEntity = depositsService.find(depositsId);
  		int accountId = depositEntity.getAccountObj().getAccountId();
  		
  		if(depositsLineItems.getLoadEnhanced().equals("Amount On Enhanced Load")){
  			double sanctionedLoad = Double.parseDouble(depositsLineItems.getSancationedLoad());
  			double loadChange = 0.0;
  			if(depositsLineItems.getNewSancationedLoad() >= sanctionedLoad){
  				loadChange = depositsLineItems.getNewSancationedLoad()-sanctionedLoad;
  				
  				float rate = 0;
  				int serviceParameterId;
  				ServiceParametersEntity parametersEntity = null;
  				if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KW")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KW");
  					serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,depositsLineItems.getTypeOfServiceDeposit(),"Sanctioned KW");
  					if(serviceParameterId!=0){
  						parametersEntity = serviceParameterService.find(serviceParameterId);
  					}
  				}else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KVA")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KVA");
  					serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,depositsLineItems.getTypeOfServiceDeposit(),"Sanctioned KVA");
  					if(serviceParameterId!=0){
  						parametersEntity = serviceParameterService.find(serviceParameterId);
  					}
  				} else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned HP")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On HP");
  					serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,depositsLineItems.getTypeOfServiceDeposit(),"Sanctioned HP");
  					if(serviceParameterId!=0){
  						parametersEntity = serviceParameterService.find(serviceParameterId);
  					}
  				}
  				
  				depositsLineItems.setAmount(rate*loadChange);
  				depositsLineItems.setLoadChangeUnits(loadChange);
  				String notes =depositsLineItems.getNewSancationedLoad()+" (New "+depositsLineItems.getSancationedLoadName()+") - "+sanctionedLoad+" (Old "+depositsLineItems.getSancationedLoadName()+") = "+loadChange+" (Load Change)";
  				depositsLineItems.setNotes(notes); 
  				
  				depositsLineItems.setDeposits(depositEntity);
  		  		
  		  		validator.validate(depositsLineItems, bindingResult);
  		  		double totalDepositAmount = depositsLineItemsService.getAllLineItemsAmount(depositsId);
  		  		depositEntity.setTotalAmount(totalDepositAmount);
  		  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  		  		depositsService.update(depositEntity);
  		  		
  		  		parametersEntity.setServiceParameterValue(""+depositsLineItems.getNewSancationedLoad());
		  		
		  		serviceParameterService.update(parametersEntity);
  				
  			}else{
  				loadChange = sanctionedLoad-depositsLineItems.getNewSancationedLoad();

  				float rate = 0;
  				if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KW")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KW");
  				}else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned KVA")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KVA");
  				} else if(depositsLineItems.getSancationedLoadName().trim().equals("Sanctioned HP")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On HP");
  				}
  				
  				depositsLineItems.setAmount(rate*loadChange);
  				depositsLineItems.setLoadChangeUnits(loadChange);
  				String notes =sanctionedLoad+" (Old "+depositsLineItems.getSancationedLoadName()+") - "+depositsLineItems.getNewSancationedLoad()+" (New "+depositsLineItems.getSancationedLoadName()+") = "+loadChange+" (Load Change)";
  				depositsLineItems.setNotes(notes); 
  				
  				depositsLineItems.setDeposits(depositEntity);
  				depositsLineItems.setStatus("Refund");
  		  		
  		  		validator.validate(depositsLineItems, bindingResult);
  		  		depositEntity.setRefundAmount(depositsLineItems.getAmount());
  		  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  		  		depositsService.update(depositEntity);
  			}
  		}else{
  			
  			depositsLineItems.setDeposits(depositEntity);
  	  		
  	  		validator.validate(depositsLineItems, bindingResult);
  	  		depositEntity.setTotalAmount(depositEntity.getTotalAmount()+depositsLineItems.getAmount());
  	  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  	  		depositsService.update(depositEntity);  			
  		}
  		
  		depositsLineItemsService.update(depositsLineItems);
  		
  		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("ddId", depositsLineItems.getDdId());
		parameterMap.put("typeOfServiceDeposit", depositsLineItems.getTypeOfServiceDeposit());
		parameterMap.put("transactionCode",depositsLineItems.getTransactionCode());
		parameterMap.put("transactionName",depositsLineItems.getTransactionName());
		parameterMap.put("category",depositsLineItems.getCategory());
		parameterMap.put("amount", depositsLineItems.getAmount());
		parameterMap.put("collectionType", depositsLineItems.getCollectionType());
		parameterMap.put("instrumentNo", depositsLineItems.getInstrumentNo());
		parameterMap.put("instrumentDate", depositsLineItems.getInstrumentDate());
		
		return parameterMap;
  	}

  	@RequestMapping(value = "/depositsDetails/depositLineItemDestroy", method = RequestMethod.GET)
  	public @ResponseBody Object deleteDepositLineItem(@ModelAttribute("depositsLineItems") DepositsLineItems depositsLineItems,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
  		logger.info("In delete deposit line item method");
  		JsonResponse errorResponse = new JsonResponse();
  		
  		Deposits depositEntity = depositsLineItemsService.getDepositsObjByLineItemId(depositsLineItems.getDdId());
  		try {
  			
  			depositsLineItemsService.delete(depositsLineItems.getDdId());
  			
  		} catch (Exception e) {
  			errorResponse.setStatus("CHILD");
  			return errorResponse;
  		}
  		
		double totalDepositAmount = depositsLineItemsService.getAllLineItemsAmount(depositEntity.getDepositsId());
	  	depositEntity.setTotalAmount(totalDepositAmount);
	  	depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
	  		
	  	depositsService.update(depositEntity);
  		ss.setComplete();
  		return depositsLineItems;
  	}
	
	@RequestMapping(value = "/deposits/getTransactionCodes", method = RequestMethod.GET)
	public @ResponseBody List<?> getTransactionCodes() 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<?> transactionCodeList = depositsLineItemsService.getTransactionCodes();
		Map<String, Object> codeMap = null;
		for (Iterator<?> iterator = transactionCodeList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			codeMap = new HashMap<String, Object>();
			codeMap.put("transactionCode", (String)values[0]);
			codeMap.put("transactionName", (String)values[1]);
			codeMap.put("typeOfService", (String)values[2]);
 	       	result.add(codeMap);
 	     }
        return result;
	}
	
	@RequestMapping(value = "/deposits/getTransactionCodes/{serviceType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getTransactio0nCodeList(@PathVariable String serviceType)
	{		
		return depositsLineItemsService.getTransactionCodeList(serviceType);
	}
	
	@RequestMapping(value = "/deposits/refundAmountButtonClick/{ddId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void refundAmountButtonClick(@PathVariable int ddId, HttpServletResponse response)
	{	
		depositsLineItemsService.refundAmountButtonClick(ddId, response);
	}
	
	@RequestMapping(value = "/deposits/getSanctionedLoadDetails/{accountId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getSanctionedLoadDetails(@PathVariable int accountId){	
		Set<Integer> set = depositsLineItemsService.getSanctionedLoadDetails(accountId);
		Map<String, Object> parameterMap = null;
		for (Integer integer : set) {
			ServiceParametersEntity entity = serviceParameterService.find(integer);
			parameterMap = new HashMap<String, Object>();	
			parameterMap.put("serviceParameterValue", entity.getServiceParameterValue());
			parameterMap.put("spmName", entity.getServiceParameterMaster().getSpmName());
		}
		return parameterMap;
	}
	
	@RequestMapping(value = "/deposits/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNamesInLedger() {
		logger.info("In person data filter method");
		List<?> accountPersonList = depositsService.findPersonForFilters();
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
	
	// ****************************** Deposits Refund Read,Save,Update and Delete methods ********************************/
	
		@RequestMapping(value = "/depositsDetails/depositRefundRead/{depositsId}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object readDepositsRefund(@ModelAttribute("depositsRefundEntity") DepositsRefundEntity depositsRefundEntity,@PathVariable int depositsId, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
		{
			logger.info(" Deposits Refund read method  with Id : "+depositsId);
			List<DepositsRefundEntity> depositsRefundEntities = depositsRefundService.findAll(depositsId);
			return depositsRefundEntities;
		}
	
	@RequestMapping(value = "/depositsDetails/depositRefundCreate/{depositsId}", method = RequestMethod.GET)
  	public @ResponseBody Object saveDepositRefund(@ModelAttribute("depositsRefundEntity") DepositsRefundEntity depositsRefundEntity,BindingResult bindingResult,@PathVariable int depositsId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		logger.info("In save deposit line items method");
  		Deposits depositEntity = depositsService.find(depositsId);
  		int accountId = depositEntity.getAccountObj().getAccountId();
  		
  		if(depositsRefundEntity.getRefundType().trim().equals("Refund Amount On Enhanced Load")){
  			double sanctionedLoad = Double.parseDouble(depositsRefundEntity.getSancationedLoadRefund());
  			double loadChange = 0.0;
  			if(depositsRefundEntity.getNewSancationedLoadRefund() >= sanctionedLoad){
  				loadChange = depositsRefundEntity.getNewSancationedLoadRefund()-sanctionedLoad;
  				
  				float rate = 0;
  				if(depositsRefundEntity.getSancationedLoadName().trim().equals("Sanctioned KW")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KW");
  				}else if(depositsRefundEntity.getSancationedLoadName().trim().equals("Sanctioned KVA")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KVA");
  				} else if(depositsRefundEntity.getSancationedLoadName().trim().equals("Sanctioned HP")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On HP");
  				}
  				
  				depositsRefundEntity.setRefundAmount(rate*loadChange);
  				depositsRefundEntity.setLoadChangeUnits(loadChange);
  				String notes =depositsRefundEntity.getNewSancationedLoadRefund()+" (New "+depositsRefundEntity.getSancationedLoadName()+") - "+sanctionedLoad+" (Old "+depositsRefundEntity.getSancationedLoadName()+") = "+loadChange+" (Load Change)";
  				depositsRefundEntity.setNotes(notes); 
  				
  				depositsRefundEntity.setDeposits(depositEntity);
  				depositsRefundEntity.setRefundDate(new Date(new java.util.Date().getTime()));
  		  		if(depositsRefundEntity.getPaymentThrough().equals("By Cheque")){
  		  			depositsRefundEntity.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
  				}
  		  		
  		  		validator.validate(depositsRefundEntity, bindingResult);
  		  		depositEntity.setRefundAmount(depositEntity.getRefundAmount()+depositsRefundEntity.getRefundAmount());
		  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  		  		depositsService.update(depositEntity);
  				
  			}else{
  				loadChange = sanctionedLoad-depositsRefundEntity.getNewSancationedLoadRefund();

  				float rate = 0;
  				int serviceParameterId;
  				ServiceParametersEntity parametersEntity = null;
  				if(depositsRefundEntity.getSancationedLoadName().trim().equals("Sanctioned KW")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KW");
  					serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,"Electricity","Sanctioned KW");
  					if(serviceParameterId!=0){
  						parametersEntity = serviceParameterService.find(serviceParameterId);
  					}
  				}else if(depositsRefundEntity.getSancationedLoadName().trim().equals("Sanctioned KVA")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On KVA");
  					serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,"Electricity","Sanctioned KVA");
  					if(serviceParameterId!=0){
  						parametersEntity = serviceParameterService.find(serviceParameterId);
  					}
  				} else if(depositsRefundEntity.getSancationedLoadName().trim().equals("Sanctioned HP")){
  					rate = depositsLineItemsService.getRateForDepositFromRateSlab(accountId,"Deposit Load On HP");
  					serviceParameterId = depositsLineItemsService.getServiceParameterObj(accountId,"Electricity","Sanctioned HP");
  					if(serviceParameterId!=0){
  						parametersEntity = serviceParameterService.find(serviceParameterId);
  					}
  				}
  				
  				depositsRefundEntity.setRefundAmount(rate*loadChange);
  				depositsRefundEntity.setLoadChangeUnits(loadChange);
  				String notes =sanctionedLoad+" (Old "+depositsRefundEntity.getSancationedLoadName()+") - "+depositsRefundEntity.getNewSancationedLoadRefund()+" (New "+depositsRefundEntity.getSancationedLoadName()+") = "+loadChange+" (Load Change)";
  				depositsRefundEntity.setNotes(notes); 
  				
  				depositsRefundEntity.setDeposits(depositEntity);
  				depositsRefundEntity.setRefundDate(new Date(new java.util.Date().getTime()));
  				depositsRefundEntity.setStatus("Refund");
  		  		if(depositsRefundEntity.getPaymentThrough().equals("By Cheque")){
  		  			depositsRefundEntity.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
  				}
  		  		
  		  		validator.validate(depositsRefundEntity, bindingResult);
  		  		depositEntity.setRefundAmount(depositEntity.getRefundAmount()+depositsRefundEntity.getRefundAmount());
  		  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  		  		depositsService.update(depositEntity);
  		  		
  		  		parametersEntity.setServiceParameterValue(""+depositsRefundEntity.getNewSancationedLoadRefund());
	  		
  		  		serviceParameterService.update(parametersEntity);
  			}
  		}else{
  			
  			depositsRefundEntity.setDeposits(depositEntity);
  			depositsRefundEntity.setRefundDate(new Date(new java.util.Date().getTime()));
  	  		if(depositsRefundEntity.getPaymentThrough().equals("By Cheque")){
  	  			depositsRefundEntity.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
  			}
  	  		
  	  		validator.validate(depositsRefundEntity, bindingResult);
  	  		depositEntity.setRefundAmount(depositsRefundEntity.getRefundAmount()+depositEntity.getRefundAmount());
  	  		depositEntity.setBalance(depositEntity.getTotalAmount()-depositEntity.getRefundAmount());
  	  		depositsService.update(depositEntity);  			
  		}
  		
  		depositsRefundService.save(depositsRefundEntity);
  		
  		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("refundId", depositsRefundEntity.getRefundId());
		parameterMap.put("loadChangeUnits",depositsRefundEntity.getLoadChangeUnits());
		parameterMap.put("refundDate",depositsRefundEntity.getRefundDate());
		parameterMap.put("refundAmount", depositsRefundEntity.getRefundAmount());
		parameterMap.put("paymentThrough", depositsRefundEntity.getPaymentThrough());
		parameterMap.put("instrumentNo", depositsRefundEntity.getInstrumentNo());
		parameterMap.put("instrumentDate", depositsRefundEntity.getInstrumentDate());
		
		return parameterMap;
  	}
}
