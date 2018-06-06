package com.bcits.bfm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.LedgerViewEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.util.DataSourceRequest;
import com.bcits.bfm.util.DataSourceResult;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;


@Controller
public class LedgerManagementController {
	
	static Logger logger = LoggerFactory.getLogger(LedgerManagementController.class);

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private ElectricityLedgerService electricityLedgerService;
	
	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;
	
	@Autowired
	private Validator validator;
	
	@Resource
	private JsonResponse errorResponse;
	
	@Resource
	private ValidationUtil validationUtil;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PersonService personService;
	
	
		
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String accounts(ModelMap model, HttpServletRequest request) {
		logger.info("In accounts  Method");
		model.addAttribute("ViewName", "Accounts");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Accounts", 1, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "accounts/accounts";
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String accountMenu(ModelMap model,HttpServletRequest request, final Locale locale) 
	{
		logger.info("In accountMenu  Method");
		model.addAttribute("ViewName", "Account");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Account", 1, request);
		breadCrumbService.addNode("Manage Accounts", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("accountType", commonController.populateCategories("accountType", locale));
		return "accounts/account";
	}

	@RequestMapping("/electrictyLedger")
	public String electrictyLedger(HttpServletRequest request,Model model)
	{
		logger.info("In Electricity Ledger Method");
		model.addAttribute("ViewName", "Account");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Account", 1, request);
		breadCrumbService.addNode("Manage Ledgers", 2, request);
		return "accounts/electricityLedger";
	}
	
	/**
	 * Use Case: ----------------------------------------- Manage Accounts --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexAccount", method = RequestMethod.GET)
	public String indexAccount(ModelMap model, HttpServletRequest request, final Locale locale) 
	{
		model.addAttribute("ViewName", "Manage Accounts");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Inventory Management", 1, request);
		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("accountType", commonController.populateCategories("accountType", locale));
		return "inventory/account";
	}
	
	@RequestMapping(value = "/account/readPerson", method = RequestMethod.POST)
	public @ResponseBody Object readPerson(final Locale locale) 
	{
		try
		{
			return accountService.findAllPersons();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/account/read/{personId}", method = RequestMethod.POST)
	public @ResponseBody Object readAccount(@PathVariable int personId, final Locale locale) 
	{
		try
		{
			return accountService.findAllAccounts(personId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/account/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyPersonAccount(@PathVariable String operation, @ModelAttribute("account") Account account, SessionStatus sessionStatus, final Locale locale)
	{
		try
		{	
			if((validationUtil.customValidateModelObject(account, null, locale)) != null)
				return validationUtil.customValidateModelObject(account, null, locale);
			
			return accountModification(operation, account);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	@RequestMapping(value = "/account/modify/{operation}/{personId}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyAccount(@PathVariable String operation, @PathVariable int personId, @ModelAttribute("account") Account account, SessionStatus sessionStatus, final Locale locale)
	{
		try
		{	
			account.setPersonId(personId);
			
			if((validationUtil.customValidateModelObject(account, null, locale)) != null)
				return validationUtil.customValidateModelObject(account, null, locale);
			
			return accountModification(operation, account);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	private Account accountModification(String operation, Account account)
	{
		account.setPersonName(null);
		account.setPersonStyle(null);
		account.setPersonType(null);
		account.setAllAccountNos(null);
		account.setNoOfAccounts(0);
		account.setAccountStatusAllCheck(null);
		
		if(operation.equals("create"))
			accountService.save(account);
		else
			accountService.update(account);
		
		return account;
	}
	
	@RequestMapping(value = "/account/accountStatus/{personId}/{operation}", method = { RequestMethod.GET, RequestMethod.POST })
	public void accountStatus(@PathVariable int personId, @PathVariable String operation, HttpServletResponse response)
	{		
		accountService.setAccountStatus(personId, operation, response);
	}
	
	@RequestMapping(value = "/account/accountStatusUpdateFromInnerGrid/{accountId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void accountStatusUpdateFromInnerGrid(@PathVariable int accountId, HttpServletResponse response)
	{		
		accountService.updateAccountStatusFromInnerGrid(accountId, response);
	}
	
	@RequestMapping(value = "/account/categories", method = RequestMethod.GET)
    public @ResponseBody List<?> categories(final Locale locale) {
    	
		String[] str = messageSource.getMessage("accountPersonType", null, locale).split(",");
		
		List<String> list = new ArrayList<String>();
		
		Collections.addAll(list, str);
		
    	List<Map<String, String>> categoriesList = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;
		
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			
			map = new HashMap<String, String>();
			map.put("personType", string);

			categoriesList.add(map);
		}

		return categoriesList;
    }
	
	@RequestMapping(value = "/account/getAccountPersonListBasedOnPersonType/{personType}", method = RequestMethod.GET)
	public @ResponseBody List<?> getPersonListBasedOnPersonType(@PathVariable String personType) 
	{

		List<?> personList = personService.getAllAccountPersonDetailsBasedOnPersonType(personType);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = null;

		for (Iterator<?> i = personList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();

			String personName = "";
			personName = personName.concat((String)values[4]);
			if(((String)values[6]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[6]);
			}

			map = new HashMap<String, Object>();
			map.put("personId", (Integer)values[0]);
			map.put("personName", personName);
			map.put("personType", (String)values[1]);
			map.put("personStyle", (String)values[2]);

			result.add(map);
		}

		return result;
	} 
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
 //****************************** Ledger Read and Create methods ********************************//
    
    @RequestMapping(value = "/ledgers/ledgerRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DataSourceResult readLedgerData(@RequestBody DataSourceRequest request,HttpServletRequest req)
	{
    	logger.info("In Electricity Ledger read Method");
		//List<ElectricityLedgerEntity> elLedgers = electricityLedgerService.findALL();
		return electricityLedgerService.getList(request);
	}
    
    @RequestMapping(value = "/ledgers/ledgerStatus/{elLedgerid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void tariffLedgerStatus(@PathVariable int elLedgerid, @PathVariable String operation, HttpServletResponse response)
	{		
    	logger.info("In Electricity Ledger Status Change Method");
		if(operation.equalsIgnoreCase("activate"))
			electricityLedgerService.setElectricityLedgerStatus(elLedgerid, operation, response);

		else
			electricityLedgerService.setElectricityLedgerStatus(elLedgerid, operation, response);
	}
    
    @RequestMapping(value = "/ledgers/ledgerCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveLedger(@ModelAttribute("ledgerEntity")ElectricityLedgerEntity ledgerEntity, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException {

    	ledgerEntity.setStatus("Inactive");	
    	ledgerEntity.setLedgerDate(dateTimeCalender.getDateToStore(req.getParameter("ledgerDate")));
    	//ledgerEntity.setPostedBillDate(dateTimeCalender.getDateToStore(req.getParameter("postedBillDate")));
    	ledgerEntity.setPostedGlDate(dateTimeCalender.getDateToStore(req.getParameter("postedGlDate")));
    	
    	electricityLedgerService.save(ledgerEntity);  
		return ledgerEntity;  
	}
    
    //****************************** Sub Ledger Read and Create methods ********************************//
    
    @RequestMapping(value = "/ledgers/subLedgerRead/{elLedgerid}", method = { RequestMethod.GET, RequestMethod.POST })
   	public @ResponseBody List<ElectricitySubLedgerEntity> readSubLedger(@PathVariable int elLedgerid) {
       	logger.info("In Electricity sub Ledger read Method");
   		List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById(elLedgerid);
   		return subLedgerEntities;
   	}
    
    @RequestMapping(value = "/ledgers/subLedgerCreate/{elLedgerid}", method = { RequestMethod.GET, RequestMethod.POST })
  	public @ResponseBody
  	Object saveSubLedger(@ModelAttribute("subLedgerEntity") ElectricitySubLedgerEntity subLedgerEntity, BindingResult bindingResult,@PathVariable int elLedgerid,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException {

    	subLedgerEntity.setStatus("Inactive");/*
    	subLedgerEntity.setElLedgerid(elLedgerid);*/
      	
    	electricitySubLedgerService.save(subLedgerEntity);  
  		return subLedgerEntity;  
  	}
    
    @RequestMapping(value = "/ledgers/subLedgerStatusUpdateFromInnerGrid/{elSubLedgerid}", method = { RequestMethod.GET, RequestMethod.POST })
	public void subLedgerStatusUpdateFromInnerGrid(@PathVariable int elSubLedgerid, HttpServletResponse response)
	{		
    	electricitySubLedgerService.updateSubLedgerStatusFromInnerGrid(elSubLedgerid, response);
	}
    
   //****************************** Ledger Filters Data methods ********************************//
	
	@RequestMapping(value = "/ledger/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getLedgerContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(commonService.selectQuery("ElectricityLedgerEntity", attributeList, null));
		
		return set;
	}
		
	  //****************************** SubLedger Filters Data methods ********************************//
	
		@RequestMapping(value = "/subLedger/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody
		Set<?> getSubLedgerContentsForFilter(@PathVariable String feild) {
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set=new HashSet<Object>(commonService.selectQuery("ElectricitySubLedgerEntity", attributeList, null));
			
			return set;
		}
		
		@RequestMapping(value = "/ledger/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForAccountNumbersUrl() {
			return electricityLedgerService.commonFilterForAccountNumbersUrl();
		}
		
		@RequestMapping(value = "/ledger/commonFilterForPropertyNumbersUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForPropertyNumbersUrl() {
			return electricityLedgerService.commonFilterForPropertyNumbersUrl();
		}
		
		@RequestMapping(value = "/account/getPersonListForFileter", method = RequestMethod.GET)
		public @ResponseBody List<?> readPersonNames() 
		{
	    	logger.info("In person data filter method");
			List<?> accountPersonList = accountService.findPersonForFilters();
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
		
		  @RequestMapping(value = "/ledgers/getPersonListForFileter", method = RequestMethod.GET)
			public @ResponseBody List<?> readPersonNamesInLedger() 
			{
		    	logger.info("In person data filter method");
				List<?> accountPersonList = electricityLedgerService.findPersonForFilters();
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
		  
		@RequestMapping(value = "/Ledger/searchLedgerDataByMonth", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody List<?> searchLedgerDataByMonth(HttpServletRequest req) {
			
			String fromDate = "";
			String toDate = "";
			if(req.getParameter("fromDate")!=null){
				fromDate = req.getParameter("fromDate");
			}
			if(req.getParameter("toDate")!=null){
				toDate = req.getParameter("toDate");
			}
			List<ElectricityLedgerEntity> elLedgers = new ArrayList<>();
			try {
				Date fromDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
				Date toDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
				elLedgers = electricityLedgerService.searchLedgerDataByMonth(fromDateVal,toDateVal);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return elLedgers;
		}	  
/*=========================================by deepak singh===================================================*/
		@RequestMapping(value="/ledgers/ledgerReadUrl", method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> readLedgerByAccountId(HttpServletRequest request) throws ParseException
		{
			
			//String propetyNo=req
			System.out.println("coming to read ledger using account id ");
			int propertyId=Integer.parseInt(request.getParameter("propertyNo"));
			String fromDate= request.getParameter("fromdate");
			String presentdate=request.getParameter("presentdate");
			SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
			Date todate = formatter.parse(presentdate);
			Date fromdate=formatter.parse(fromDate);
			System.out.println("date from to date"+fromDate+" "+presentdate);
			System.out.println("usernmae+++++++++++++++++++++"+propertyId);
			int accountId=accountService.findAccountNumberBasedOnId(propertyId);
			System.out.println("account id++++++++++"+accountId);
			List<LedgerViewEntity> list= new ArrayList<LedgerViewEntity>();
			list=electricityLedgerService.getLedgerViewByAccountId(accountId,fromdate,todate);
			List<Map<String,Object>> maplist=new ArrayList<Map<String,Object>>();
			System.out.println("list size++++++++++"+list.size());
			Map<String,Object> map=null;
			for (LedgerViewEntity ledgerView:list)
			{
				map = new HashMap<String, Object>();
				map.put("accountNo", ledgerView.getAccountNo());
				map.put("personName", ledgerView.getPersonName());
				map.put("transactionSequence", ledgerView.getTransactionSequence());
				map.put("property_No", ledgerView.getProperty_No());
				map.put("postType", ledgerView.getPostType());
				map.put("transactionName", ledgerView.getTransactionName());
				map.put("amount", ledgerView.getAmount());
				map.put("balance", ledgerView.getBalance());
				map.put("ledgerType", ledgerView.getLedgerType());
				map.put("ledgerPeriod", ledgerView.getLedgerPeriod());
				map.put("ledgerDate", ledgerView.getLedgerDate());
				map.put("postedBillDate", ledgerView.getPostedBillDate());
				map.put("postReference", ledgerView.getPostReference());
				map.put("remarks", ledgerView.getRemarks());
				map.put("elLedgerid", ledgerView.getElLedgerid());
				
				maplist.add(map);
			}
			
			return maplist;
			
		}

}
