package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
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

import com.bcits.bfm.beans.BillingWizardBean;
import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.BillingWizardEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricityMeterLocationEntity;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.MeterHistoryEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.billingWizard.BillingWizardService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterLocationsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.serviceMasterManagement.MeterHistoryService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.servicePoints.ServicePointService;
import com.bcits.bfm.service.serviceRoute.ServiceRouteService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Billing Wizard Management 
 * Use Case : Billing Wizard
 * 
 * @author Ravi Shankar Reddy
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class BillingWizardManagementController {
	
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

	private static final Log logger = LogFactory.getLog(BillingWizardManagementController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BillingWizardService billingWizardService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ServicePointService servicePointService;
	
	@Autowired
	private ElectricityMetersService metersService;
	
	@Autowired
	private ServiceParameterMasterService parameterMasterService;
	
	@Autowired
	private ServiceRouteService serviceRouteService;
	
	@Autowired
	private ElectricityMetersService electricityMetersService;
	
	@Autowired
	private ElectricityMeterLocationsService meterLocationsService;
	
	@Autowired
	private ServiceParameterService serviceParameterService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private ElectricityLedgerService electricityLedgerService;
	
	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;
	
	@Autowired
	private MeterHistoryService meterHistoryService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(value = "/billingWizard", method = RequestMethod.GET)
	public String billingWizard(@ModelAttribute("billingWizardBean") BillingWizardBean billingWizardBean,ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException	{
		
		logger.info("In billing wizard method");
		model.addAttribute("ViewName", "Account");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Account", 1, request);
		breadCrumbService.addNode("Manage Service Onboard Wizard", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		
		return "billingWizard/billingWizard";
	}
	
	@RequestMapping(value = "/billingWizard/billingWizardDataRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readBillingWizardData() {
		
		logger.info("In read billing wizard data method");
		List<?> wizardEntities = billingWizardService.findALL();
		return wizardEntities;
	}
	
	/*@RequestMapping(value = "/billingWizardCreate", method = {RequestMethod.GET,RequestMethod.POST})
	public String getMethodBillingWizardData(@ModelAttribute("billingWizardBean") BillingWizardBean billingWizardBean,BindingResult result, ModelMap model,HttpServletRequest request) throws ParseException {
		
		logger.info(" ------------------------ Calling  billingWizardCreate get method while browser refersh ----------------");
		return "billingWizard/billingWizard";
	}

	*/	
	
	@RequestMapping(value = "/billingWizardCreate", method = RequestMethod.POST)
	public String saveBillingWizardData(@ModelAttribute("billingWizardBean") BillingWizardBean billingWizardBean,BindingResult result, ModelMap model,HttpServletRequest request) throws ParseException 
	{

		logger.info("In billing wizard data create method");
 
		BillingWizardEntity billingWizardEntity = new BillingWizardEntity();
		
		Account account=new Account();

//		List<Account> uniqueAccount = accountService.findUniqueAccountNo(billingWizardBean.getAccountNo(),billingWizardBean.getPropertyId());

		List<Account> uniqueAccount = accountService.testUniqueAccount(billingWizardBean.getAccountNo(),billingWizardBean.getPropertyId());

		
		if(uniqueAccount.isEmpty()){
			/*-------------------------- check duplicate account number while creating new cam account ---------------------*/
				String checkCamDuplicate = "SELECT a.ACCOUNT_ID,s.SERVICE_TYPE,s.STATUS as serviceStatus,a.STATUS AS accStatus,a.ACCOUNT_NO FROM ACCOUNT a,SERVICE_MASTER s WHERE a.ACCOUNT_ID=s.ACCOUNT_ID AND s.SERVICE_TYPE='CAM' AND a.ACCOUNT_NO='"+billingWizardBean.getAccountNo()+"'";
				logger.info("checkCamDuplicate="+checkCamDuplicate);
				List<?> CamDuplicateAcc = entityManager.createNativeQuery(checkCamDuplicate).getResultList();
				logger.info("CamDuplicateAcc.size="+CamDuplicateAcc.size()); 
				if(CamDuplicateAcc.size()>0){
					logger.info(billingWizardBean.getAccountNo()+" Already Exists in DB! Kindly refresh or logout!");
					model.addAttribute("error",billingWizardBean.getAccountNo()+" Already Exists in DB! Kindly refresh or logout!");
					return "billingWizard/billingWizard";
				}
			/*-------------------------------------------------------------------------------------------------------------*/
			account.setAccountNo(billingWizardBean.getAccountNo());
			account.setAccountType("Services");
			account.setPersonId(billingWizardBean.getPersonId()); 
			account.setPropertyId(billingWizardBean.getPropertyId());
			account.setAccountStatus("Inactive");
			
			accountService.save(account);
			
			billingWizardEntity.setAccountObj(account);
		}else{
			account=uniqueAccount.get(0);
			billingWizardEntity.setAccountObj(account);
		}
		
		ServiceMastersEntity mastersEntity = new ServiceMastersEntity();
		mastersEntity.setTypeOfService(billingWizardBean.getServiceType());
		
		if(billingWizardBean.getServiceType().equals("Electricity")){
			mastersEntity.setElTariffID(billingWizardBean.getElTariffID());
			mastersEntity.setTodApplicable(billingWizardBean.getTodApplicable());
			SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed2 = format2.parse(request.getParameter("fixedDate"));
			 java.sql.Date fixedDate = new java.sql.Date(parsed2.getTime());
			 mastersEntity.setServiceStartDate(fixedDate);
		}
		
		if(billingWizardBean.getServiceType().equals("Gas")){
			mastersEntity.setGaTariffID(billingWizardBean.getElTariffID());
			mastersEntity.setTodApplicable("No");
			
		}
		
		if(billingWizardBean.getServiceType().equals("Water")){
			mastersEntity.setWtTariffID(billingWizardBean.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(billingWizardBean.getServiceType().equals("Solid Waste")){
			mastersEntity.setSwTariffID(billingWizardBean.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(billingWizardBean.getServiceType().equals("Telephone Broadband")){
			mastersEntity.setBroadTeleTariffId(billingWizardBean.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(billingWizardBean.getServiceType().equals("Others")){
			mastersEntity.setOthersTariffID(billingWizardBean.getElTariffID());
			mastersEntity.setTodApplicable("No");
		}
		
		if(billingWizardBean.getServiceType().equals("CAM")){
			mastersEntity.setTodApplicable("No");
		}		
		
		mastersEntity.setAccountObj(account);
		mastersEntity.setStatus("Inactive");
		
		
		System.out.println("saving data for ServiceMaster table.......................");
		serviceMasterService.save(mastersEntity);
/*		
		ServiceParametersEntity parametersEntity = null;
		  List<?> str=new LinkedList<Object>();
		  List<?> spmIdList=new LinkedList<Object>();
		  int count=0;
		  str=parameterMasterService.getServiceParameterName(billingWizardBean.getServiceType());
		  for (Iterator<?> iterator = str.iterator(); iterator.hasNext();) {
		   String object = (String) iterator.next();
		   spmIdList=parameterMasterService.getServiceParameterId(object);
		  
		   for (Object spmId : spmIdList) {
				   
				    parametersEntity = new ServiceParametersEntity();
				    parametersEntity.setServiceMastersEntity(mastersEntity);
				    parametersEntity.setServiceParameterDataType(parameterMasterService.find((Integer)spmId).getSpmdataType());
					parametersEntity.setServiceParameterSequence(parameterMasterService.find((Integer)spmId).getSpmSequence());
					
					HashMap<Integer,String> map=new HashMap<Integer,String>();
					map.put((Integer)spmId,billingWizardBean.getParameterValue().get(count));
					Set<Map.Entry<Integer, String>> set = map.entrySet();
					logger.info("showing parameter name with and without null value!!");
					for (Map.Entry<Integer, String> mapSet :set)
					{
						if(!mapSet.getValue().isEmpty())
						{
							logger.info("Saving parameter name having no null value!!");
							map.remove(mapSet.getKey());
							parametersEntity.setSpmId((Integer)mapSet.getKey());
						    parametersEntity.setServiceParameterValue((String)mapSet.getValue());
						    parametersEntity.setStatus("Inactive");
						    serviceParameterService.save(parametersEntity);
						}
					}
				    count++;
		   		}
		  }
		ElectricityMetersEntity electricityMetersEntity=null;
		if(billingWizardBean.getServiceMetered().equals("Yes")){
			electricityMetersEntity=electricityMetersService.find(billingWizardBean.getElMeterId());
			electricityMetersEntity.setAccount(account);
			//electricityMetersEntity.setPointEntity(pointEntity);
			electricityMetersEntity.setTypeOfServiceForMeters(billingWizardBean.getServiceType());
			electricityMetersService.update(electricityMetersEntity);
			
			ElectricityMeterLocationEntity electricityMeterLocationEntity=new ElectricityMeterLocationEntity();
			
			SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		    java.util.Date parsed2 = format2.parse(request.getParameter("fixedDate"));
		    java.sql.Date fixedDate = new java.sql.Date(parsed2.getTime());
			electricityMeterLocationEntity.setMeterFixedDate(fixedDate);
			
			electricityMeterLocationEntity.setMeterFixedBy(billingWizardBean.getFixedBy());
			electricityMeterLocationEntity.setLocationStatus("In Service");
			electricityMeterLocationEntity.setElectricityMetersEntity(electricityMetersEntity);
		    electricityMeterLocationEntity.setAccount(account);
			
			meterLocationsService.save(electricityMeterLocationEntity);
			
			MeterHistoryEntity meterHistoryEntity = new MeterHistoryEntity();
			
			meterHistoryEntity.setElectricityMetersEntity(electricityMetersEntity);
			meterHistoryEntity.setServiceMastersEntity(mastersEntity);
			meterHistoryEntity.setElectricityMeterLocationEntity(electricityMeterLocationEntity);
			
			meterHistoryService.save(meterHistoryEntity);
		}

*/		
		try {
			
			billingWizardEntity.setServiceMastersEntity(mastersEntity);
		//  billingWizardEntity.setMetersEntity(electricityMetersEntity);
		    billingWizardEntity.setServiceMetered("NO");
			billingWizardEntity.setStatus("Created");
	        billingWizardService.save(billingWizardEntity);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
        return "billingWizard/billingWizard";
      //  return "redirect:billingWizardCreate";
	}

	
	@RequestMapping(value = "/billingWizard/readMeterNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readMeterNumbers(HttpServletRequest req) {
	String serviceType = (req.getParameter("service"));
	logger.info("service::::::::::"+serviceType);
		return billingWizardService.findMeterNumbers(serviceType);
	}
	
	@RequestMapping(value="/billingWizard/accountNumber" ,method=RequestMethod.GET)
	public List<?> getAccountInfo(HttpServletResponse response,HttpServletRequest request) throws IOException, JSONException{
		int personId=Integer.parseInt(request.getParameter("personId"));
		int propertyId = Integer.parseInt(request.getParameter("propertyId"));
		logger.info("Person Id  is.."+personId);
		List<?> list= accountService.getAccountNumber(personId,propertyId);
		PrintWriter out=null;
		JSONArray array=new JSONArray(list);
		response.setContentType("application/json");
		logger.info("Converting list type  to JSON Array type "+array);
		out=response.getWriter();
		out.print(array);
	    return null;
	}
	@RequestMapping(value="/billingWizard/autogenerateaccno" ,method=RequestMethod.GET)
	public String getAutoGeneratedAccountNumber(HttpServletResponse response,HttpServletRequest request) throws Exception{
		logger.info("Person Id  is..");
		String obj= genrateAccountNumber();
		logger.info("Generated account number is ! "+obj);
		PrintWriter out=null;
		out=response.getWriter();
		out.print(obj);
	    return null;
	}
	
	@RequestMapping(value = "/billingWizard/approvedAccountNumber/{wizardId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void approvedAccountNumber(@PathVariable int wizardId, HttpServletResponse response) throws IOException
	{		
		Date possessionDate = billingWizardService.getPossessionDate(wizardId);
		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
		List<String> transactionCodeList=null;
		BillingWizardEntity wizardEntity = billingWizardService.find(wizardId);
		String typeOfService = wizardEntity.getServiceMastersEntity().getTypeOfService();
		PrintWriter out = response.getWriter();
		
		if(possessionDate!=null)
		{
			ServiceMastersEntity mastersEntity = wizardEntity.getServiceMastersEntity();
			mastersEntity.setServiceStartDate(new java.sql.Date(possessionDate.getTime()));
			serviceMasterService.update(mastersEntity);
			
			if(wizardEntity.getStatus().equals("Created")){
				
				ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(wizardEntity.getAccountObj().getAccountId()).intValue())+1);
				ledgerEntity.setAccountId(wizardEntity.getAccountObj().getAccountId());
				ledgerEntity.setLedgerType(typeOfService+" Ledger");
				ledgerEntity.setPostType("INIT");
				if(typeOfService.equals("Electricity")){
					ledgerEntity.setTransactionCode("EL");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForElectricity(typeOfService);
				}
				if(typeOfService.equals("Gas")){
					ledgerEntity.setTransactionCode("GA");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForGas(typeOfService);
				}
				if(typeOfService.equals("Solid Waste")){
					ledgerEntity.setTransactionCode("SW");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForSolidWaste(typeOfService);
				}
				if(typeOfService.equals("Water")){
					ledgerEntity.setTransactionCode("WT");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForWater(typeOfService);
				}
				if(typeOfService.equals("Others")){
					ledgerEntity.setTransactionCode("OT");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForOthers(typeOfService);
				}
				if(typeOfService.equals("CAM")){
					ledgerEntity.setTransactionCode("CAM");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForCam(typeOfService);
				}
				if(typeOfService.equals("Telephone Broadband")){
					ledgerEntity.setTransactionCode("TEL");
					transactionCodeList = electricitySubLedgerService.getTransactionCodesForTb(typeOfService);
				}
				
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				
				Calendar calLast = Calendar.getInstance();
				int lastYear = calLast.get(Calendar.YEAR)-1;

				ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
				ledgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
				ledgerEntity.setAmount(0.00);
				ledgerEntity.setBalance(0.00);
				ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
				ledgerEntity.setStatus("Approved");
				
				electricityLedgerService.save(ledgerEntity);
				List<ElectricitySubLedgerEntity> batchSubLedgerList = new ArrayList<ElectricitySubLedgerEntity>();
			    for (String code : transactionCodeList) {
			    	ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();
			    	subLedgerEntity.setTransactionCode(code);
			    	subLedgerEntity.setAmount(0.00);
			    	subLedgerEntity.setBalanceAmount(0.00);
			    	subLedgerEntity.setStatus("Approved");
			    	subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
			    	batchSubLedgerList.add(subLedgerEntity);
			    	electricitySubLedgerService.save(subLedgerEntity);
				}
				billingWizardService.approvedAccountNumber(wizardId, response);
				
			}else{
				billingWizardService.approvedAccountNumber(wizardId, response);
			}
			
		}else{
			out.write("Tenancy Handover Date is Null");
			logger.info("possessionDate is null");
		}
	}
	
	@RequestMapping(value = "/billingWizard/readServiceRouteNames", method = RequestMethod.GET)
	public @ResponseBody List<?> readServiceRouteNames() {		
		 return billingWizardService.getServiceRouteNames();
	}		
	
	@RequestMapping(value = "/billingWizard/readServiceSubRouteNames", method = RequestMethod.GET)
	public @ResponseBody List<?> readServiceSubRouteNames() {		
		return billingWizardService.findServiceSubRouteNames();
	}	
	
	public String genrateAccountNumber() throws Exception {  
		/*Random generator = new Random();  
		generator.setSeed(System.currentTimeMillis());  
		   
		int num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		throw new Exception("Unable to generate PIN at this time..");  
		}  
		}  
		return "AC"+num; */ 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int nextSeqVal = accountService.autoGeneratedAccountNumber();
		
		return "AC"+year+""+nextSeqVal; 		
		}
	
	@RequestMapping(value = "/billingWizard/commonFilterForWizaradUrl/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getBillingWizardContentsForFilter(@PathVariable String feild) {
		logger.info("In service on board use case filters Method");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("BillingWizardEntity",attributeList, null));
				
		return set;
	}

	@RequestMapping(value = "/billingWizard/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> commonFilterForAccountNumbersUrl() {
		return billingWizardService.commonFilterForAccountNumbersUrl();
	}

	@RequestMapping(value = "/billingWizard/commonFilterForPropertyNoUrl", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> commonFilterForPropertyNoUrl() {
		return billingWizardService.commonFilterForPropertyNoUrl();
	}
	
	@RequestMapping(value = "/billingWizard/accountCheck/{service}/{accountNumber}", method = RequestMethod.GET)
	public @ResponseBody Object accountCheck(@PathVariable String service,@PathVariable String accountNumber) {
		List<Integer> serviceMasterList = billingWizardService.accountCheck(service,accountNumber);
		
		String result = "";
		if(serviceMasterList.isEmpty()){
			result=result+"true";
		}else{
			result=result+"false";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/billingWizard/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNamesInAccount() {
		logger.info("In person data filter method");
		List<?> accountPersonList = billingWizardService.findPersonForFilters();
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
	
}
