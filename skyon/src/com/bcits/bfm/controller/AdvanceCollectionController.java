package com.bcits.bfm.controller;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bcits.bfm.model.AdvanceCollectionEntity;
import com.bcits.bfm.model.AdvancePaymentEntity;
import com.bcits.bfm.model.ClearedPayments;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.advanceCollection.AdvancePaymentService;
import com.bcits.bfm.service.advanceCollection.ClearedPaymentsService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class AdvanceCollectionController
{
	static Logger logger = LoggerFactory.getLogger(AdvanceCollectionController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AdvanceCollectionService advanceCollectionService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private AdvancePaymentService advancePaymentService;
	
	@Autowired 
	private ClearedPaymentsService clearedPaymentsService;
	
	
	@RequestMapping(value = "/advanceCollection", method = RequestMethod.GET)
	public String advanceCollection(ModelMap model, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		logger.info("---------- In Advance Collection controller -------------------");
		model.addAttribute("ViewName", "Account");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Account", 1, request);
		breadCrumbService.addNode("Manage Advance Collection", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "advanceCollection/advanceCollection";
	}
	
	@RequestMapping(value = "/advanceCollection/advanceCollectionCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createAdvanceCollection(@ModelAttribute("collectionEntity") AdvanceCollectionEntity collectionEntity, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Advance Collection save method ---------------");
		collectionEntity.setStatus("Generated");
		collectionEntity.setAccountObj(accountService.find(collectionEntity.getAccountId()));
		
		advanceCollectionService.save(collectionEntity);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("advCollId", collectionEntity.getAdvCollId());
		parameterMap.put("accountId", collectionEntity.getAccountObj().getAccountId());
		parameterMap.put("accountNo",collectionEntity.getAccountObj().getAccountNo());
		parameterMap.put("totalAmount",collectionEntity.getTotalAmount());
		parameterMap.put("amountCleared",collectionEntity.getAmountCleared());
		parameterMap.put("balanceAmount",collectionEntity.getBalanceAmount());
		parameterMap.put("transactionCode",collectionEntity.getTransactionCode());
		return parameterMap;
	}
	
	@RequestMapping(value = "/advanceCollection/advanceCollectionRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object readAdvanceCollection(@ModelAttribute("collectionEntity") AdvanceCollectionEntity collectionEntity, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Advance Collection read method ---------------");
		List<AdvanceCollectionEntity> advanceCollectionList = advanceCollectionService.findAll();
		return advanceCollectionList;
	}
	
	@RequestMapping(value = "/advanceCollection/advanceCollectionUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editAdvanceCollection(@ModelAttribute("collectionEntity") AdvanceCollectionEntity collectionEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("---------------- Advance Collection edit method ---------------");
		
		validator.validate(collectionEntity, bindingResult);
		collectionEntity.setAccountObj(accountService.find(collectionEntity.getAccountId()));
		advanceCollectionService.update(collectionEntity);
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("advCollId", collectionEntity.getAdvCollId());
		parameterMap.put("accountId", collectionEntity.getAccountObj().getAccountId());
		parameterMap.put("accountNo",collectionEntity.getAccountObj().getAccountNo());
		parameterMap.put("totalAmount",collectionEntity.getTotalAmount());
		parameterMap.put("amountCleared",collectionEntity.getAmountCleared());
		parameterMap.put("balanceAmount",collectionEntity.getBalanceAmount());
		parameterMap.put("transactionCode",collectionEntity.getTransactionCode());
		
		return parameterMap;
	}
	
	@RequestMapping(value = "/advanceCollection/advanceCollectionDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteAdvanceCollection(@ModelAttribute("collectionEntity") AdvanceCollectionEntity collectionEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("---------------- Advance Collection delete method ---------------");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			advanceCollectionService.delete(collectionEntity.getAdvCollId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return collectionEntity;
	}
	
	@RequestMapping(value = "/advanceCollection/getAllAccountNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> getAllAccountNumbers() {
		return advanceCollectionService.getAllAccountNumbers();
	}
	
	@RequestMapping(value = "/advanceCollection/setDepositsStatus/{advCollId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setBillStatus(@PathVariable int advCollId,@PathVariable String operation, HttpServletResponse response)
	{

		if (operation.equalsIgnoreCase("activate"))
		{
			advanceCollectionService.setAdvanceCollectionStatus(advCollId, operation, response);
		} 
		else 
		{
			advanceCollectionService.setAdvanceCollectionStatus(advCollId, operation, response);
		}
	}
	// ******************************* Advance collection use case filters method *****************************************/
	
	@RequestMapping(value = "/advanceCollection/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForAccountNumbersUrl() {
		return advanceCollectionService.commonFilterForAccountNumbersUrl();
	}
	
	@RequestMapping(value = "/advanceCollection/commonFilterForPropertyNoUrl", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForPropertyNoUrl() {
		return advanceCollectionService.commonFilterForPropertyNoUrl();
	}
	
	@RequestMapping(value = "/advanceCollection/commonFilterForTransactionNameUrl", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForTransactionNameUrl() {
		return advanceCollectionService.commonFilterForTransactionNameUrl();
	}
	
	
	 // ****************************** Advance payment Read,Save,Update and Delete methods ********************************/
	
	@RequestMapping(value = "/advancePayment/advancePaymentRead/{advPayId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object readAdvancePayment(@ModelAttribute("paymentEntity") AdvancePaymentEntity paymentEntity,@PathVariable int advPayId, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Advance Payment read method  with Id ---------------"+advPayId);
		List<AdvancePaymentEntity> advancePaymentEntitiesList = advancePaymentService.findAll(advPayId);
		return advancePaymentEntitiesList;
	}
	
	@RequestMapping(value = "/advancePayment/advancePaymentCreate/{advCollId}", method = RequestMethod.GET)
  	public @ResponseBody Object saveAdvancePayment(@ModelAttribute("paymentEntity") AdvancePaymentEntity paymentEntity,BindingResult bindingResult,@PathVariable int advCollId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		paymentEntity.setAdvanceCollectionEntity(advanceCollectionService.find(advCollId));
  		paymentEntity.setAdvPaymentDate(dateTimeCalender.getDateToStore(req.getParameter("advPaymentDate")));
  		advancePaymentService.save(paymentEntity);
		return paymentEntity;
  	}
	
  	@RequestMapping(value = "/advancePayment/advancePaymentUpdate/{advPayId}", method = RequestMethod.GET)
  	public @ResponseBody Object editAdvancePayment(@ModelAttribute("paymentEntity") AdvancePaymentEntity paymentEntity,BindingResult bindingResult,@PathVariable int advPayId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		logger.info("---------------- Advance Payment edit method  with Id ---------------"+advPayId);
  		
  		paymentEntity.setAdvanceCollectionEntity(advanceCollectionService.find(advPayId));;  		
  		validator.validate(paymentEntity, bindingResult);
  		advancePaymentService.update(paymentEntity);
  		
  		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("advPayId", paymentEntity.getAdvPayId());
		parameterMap.put("receiptNo", paymentEntity.getReceiptNo());
		parameterMap.put("amount", paymentEntity.getAmount());
		parameterMap.put("advPaymentDate", paymentEntity.getAdvPaymentDate());
		
		return parameterMap;
  	}

  	@RequestMapping(value = "/advancePayment/advancePaymentDestroy", method = RequestMethod.GET)
  	public @ResponseBody Object deleteAdvancePayment(@ModelAttribute("paymentEntity") AdvancePaymentEntity paymentEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
  		logger.info("---------------- Advance Payment delete method  with Id ---------------");
  		JsonResponse errorResponse = new JsonResponse();
  		
  		try {
  			advancePaymentService.delete(paymentEntity.getAdvPayId());
  		} catch (Exception e) {
  			errorResponse.setStatus("CHILD");
  			return errorResponse;
  		}
  		ss.setComplete();
  		return paymentEntity;
  	}
  	 // ****************************** Cleared payment Read,Save,Update and Delete methods ********************************/
  	
	@RequestMapping(value = "/clearedPayment/clearedPaymentRead/{advCollId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object readClreadPayment(@ModelAttribute("clearedPayments") ClearedPayments clearedPayments,@PathVariable int advCollId, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Advance Payment read method  with Id ---------------"+advCollId);
		List<ClearedPayments> clearedPaymentsList = clearedPaymentsService.findAll(advCollId);
		return clearedPaymentsList;
	}
	
	@RequestMapping(value = "/clearedPayment/clearedPaymentCreate/{advCollId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createClearedPayment(@ModelAttribute("clearedPayments") ClearedPayments clearedPayments, BindingResult bindingResult,@PathVariable int advCollId, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
		logger.info("---------------- Cleared Payment create method  with Id ---------------"+advCollId);
		//clearedPayments.setAdvanceCollectionEntity(advanceCollectionService.find(advCollId));
		clearedPayments.setClearedDate(dateTimeCalender.getDateToStore(req.getParameter("clearedDate")));
		AdvanceCollectionEntity advanceCollectionEntity = advanceCollectionService.find(advCollId);
		advanceCollectionEntity.setAmountCleared(advanceCollectionEntity.getAmountCleared()+clearedPayments.getAmount());
		advanceCollectionEntity.setBalanceAmount(advanceCollectionEntity.getTotalAmount() - advanceCollectionEntity.getAmountCleared());
		clearedPayments.setAdvanceCollectionEntity(advanceCollectionEntity);
		advanceCollectionService.update(advanceCollectionEntity);
  		clearedPaymentsService.save(clearedPayments);
  		
  		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("cpId", clearedPayments.getCpId());
		parameterMap.put("adjId", clearedPayments.getPaymentAdjustmentEntity().getAdjustmentId());
		parameterMap.put("billId",clearedPayments.getBillId());
		parameterMap.put("amount",clearedPayments.getAmount());
		parameterMap.put("clearedDate",clearedPayments.getClearedDate());
		
		return parameterMap;
	}
	
  	@RequestMapping(value = "/clearedPayment/advanceCollectionUpdate/{advCollId}", method = RequestMethod.GET)
  	public @ResponseBody Object editClreadPayment(@ModelAttribute("clearedPayments") ClearedPayments clearedPayments,BindingResult bindingResult,@PathVariable int advCollId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		clearedPayments.setAdvanceCollectionEntity(advanceCollectionService.find(advCollId)); 		
  		validator.validate(clearedPayments, bindingResult);
  		clearedPaymentsService.update(clearedPayments);
  		
  		Map<String, Object> parameterMap = new HashMap<String, Object>();	
		parameterMap.put("cpId", clearedPayments.getCpId());
		parameterMap.put("adjId", clearedPayments.getPaymentAdjustmentEntity().getAdjustmentId());
		parameterMap.put("amount", clearedPayments.getAmount());
		parameterMap.put("billId", clearedPayments.getBillId());
		parameterMap.put("clearedDate", clearedPayments.getClearedDate());
		
		return parameterMap;
  	}

  	@RequestMapping(value = "/clearedPayment/clearedPaymentDestroy", method = RequestMethod.GET)
  	public @ResponseBody Object deleteClreadPayment(@ModelAttribute("clearedPayments") ClearedPayments clearedPayments,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
  		JsonResponse errorResponse = new JsonResponse();
  		try {
  			clearedPaymentsService.delete(clearedPayments.getCpId());
  		} catch (Exception e) {
  			errorResponse.setStatus("CHILD");
  			return errorResponse;
  		}
  		ss.setComplete();
  		return clearedPayments;
  	}
  	
  	@RequestMapping(value = "/advanceCollection/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNamesInLedger() 
	{
    	logger.info("In person data filter method");
		List<?> accountPersonList = advanceCollectionService.findPersonForFilters();
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

}
