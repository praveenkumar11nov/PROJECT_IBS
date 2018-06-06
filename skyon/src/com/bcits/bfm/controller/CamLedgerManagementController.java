package com.bcits.bfm.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.CamChargesEntity;
import com.bcits.bfm.model.CamConsolidationEntity;
import com.bcits.bfm.model.CamHeadsEntity;
import com.bcits.bfm.model.CamLedgerEntity;
import com.bcits.bfm.model.CamReportSettingsEntity;
import com.bcits.bfm.model.CamSubLedgerEntity;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.commonAreaMaintenance.CamChargesService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.commonAreaMaintenance.CamHeadsService;
import com.bcits.bfm.service.commonAreaMaintenance.CamLedgerService;
import com.bcits.bfm.service.commonAreaMaintenance.CamReportSettingsService;
import com.bcits.bfm.service.commonAreaMaintenance.CamSubLedgerService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.transactionMaster.InterestSettingService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class CamLedgerManagementController {
	
	static Logger logger = LoggerFactory.getLogger(CamLedgerManagementController.class);

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private CamLedgerService camLedgerService;
	
	@Autowired
	private CamSubLedgerService camSubLedgerService;
	
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
	private CamConsolidationService camConsolidationService;
	
	@Autowired
	private CamHeadsService camHeadsService;
	
	@Autowired
	ElectricityBillsService electricityBillsService;
	
	@Autowired
	ElectricityBillLineItemService electricityBillLineItemService;
	
	@Autowired
	BillingParameterMasterService billingParameterMasterService;
	
	@Autowired
    ElectricityBillParameterService billParameterService;  
	
	@Autowired
	private BillingParameterMasterService parameterMasterService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ElectricityLedgerService electricityLedgerService;
	
	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private InterestSettingService interestSettingService;
	
	@Autowired
	TariffCalculationController calculationController;
	
	@Autowired
	private CamReportSettingsService camReportSettingsService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CamChargesService camChargesService;
		
	@RequestMapping(value = "/camLedger", method = RequestMethod.GET)
	public String camLedger(ModelMap model, HttpServletRequest request) {
		logger.info("In Cam Ledger Method");
		model.addAttribute("ViewName", "Common Area Maintainence");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Common Area Maintainence", 1, request);
		breadCrumbService.addNode("Manage Cam Ledger", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "cam/camLedger";
	}
	
	@RequestMapping(value = "/camConsolidation", method = RequestMethod.GET)
	public String camConsolidation(ModelMap model, HttpServletRequest request) {
		logger.info("In Cam Consolidation Method");
		model.addAttribute("ViewName", "Common Area Maintainence");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Common Area Maintainence", 1, request);
		breadCrumbService.addNode("Manage CAM Bils", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "cam/camConsolidation";
	}
	
 //****************************** Common Area Maintenance  Ledger Read and Create methods ********************************//
    
    @RequestMapping(value = "/camLedgers/camLedgerRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CamLedgerEntity> readCamLedgerData()
	{
    	logger.info("In Cam Ledger read Method");
		List<CamLedgerEntity> camLedgerEntities = camLedgerService.findALL();
		return camLedgerEntities;
	}
    
    @RequestMapping(value = "/camLedgers/camLedgerCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveLedger(@ModelAttribute("camLedgerEntity") CamLedgerEntity camLedgerEntity, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException {
    	List<CamChargesEntity> camSetting = camLedgerService.getCamSetting();
    	if(camSetting.get(0).getCamChargesBasedOn().equals("Actual")){
    		
    		int lastLedgerId =0;
        	try{
        		lastLedgerId = camLedgerService.getLastCamLedgerId();
        	}catch(Exception e){
        		e.printStackTrace();
        		lastLedgerId=0;
        	}
        	double lastBalance = 0.0;
        	if(lastLedgerId!=0){
        		CamLedgerEntity ledgerEntity = camLedgerService.find(lastLedgerId);
        		lastBalance=ledgerEntity.getBalance();
        	}
    		
    		int maxLedgerId =0;
        	try{
        		maxLedgerId = camLedgerService.getLastTransactionCamLedgerId(camLedgerEntity.getTransactionCode());
        	}catch(Exception e){
        		e.printStackTrace();
        		maxLedgerId=0;
        	}
        	double lastHeadBalance=0.0;
        	if(maxLedgerId!=0){
        		CamLedgerEntity ledgerEntitySubHead = camLedgerService.find(maxLedgerId);
        		lastHeadBalance=ledgerEntitySubHead.getHeadBalance();
        	}
        	camLedgerEntity.setPostedToBill("No");
        	camLedgerEntity.setCcId(0);
        	camLedgerEntity.setLedgerDate(dateTimeCalender.getDateToStore(req.getParameter("ledgerDate")));
        	camLedgerEntity.setStatus("Created");
        	camLedgerEntity.setCalculationBased(camSetting.get(0).getCamChargesBasedOn());
        	camLedgerEntity.setCreditAmount(0);
        	camLedgerEntity.setBalance(lastBalance+camLedgerEntity.getDebitAmount());
        	camLedgerEntity.setHeadBalance(lastHeadBalance+camLedgerEntity.getDebitAmount());
        	
        	camLedgerService.save(camLedgerEntity);
    	}else if(camSetting.get(0).getCamChargesBasedOn().equals("Fixed")){
    		
    		int lastLedgerId =0;
        	try{
        		lastLedgerId = camLedgerService.getLastCamLedgerId();
        	}catch(Exception e){
        		e.printStackTrace();
        		lastLedgerId=0;
        	}
        	double lastBalance = 0.0;
        	if(lastLedgerId!=0){
        		CamLedgerEntity ledgerEntity = camLedgerService.find(lastLedgerId);
        		lastBalance=ledgerEntity.getBalance();
        	}
        	
        	double totalAmountBasedOnFixedFlatRate = camConsolidationService.getNoOfFlats()*camSetting.get(0).getRateForFlat();
        	double totalAmountBasedOnFixedSqftRate = camConsolidationService.getTotalSQFT()*camSetting.get(0).getRateForSqft();
    		
    		camLedgerEntity.setPostedToBill("No");
        	camLedgerEntity.setCcId(0);
        	camLedgerEntity.setLedgerDate(dateTimeCalender.getDateToStore(req.getParameter("ledgerDate")));
        	camLedgerEntity.setStatus("Created");
        	camLedgerEntity.setCalculationBased(camSetting.get(0).getCamChargesBasedOn());
        	camLedgerEntity.setCreditAmount(0);
        	camLedgerEntity.setDebitAmount(totalAmountBasedOnFixedFlatRate+totalAmountBasedOnFixedSqftRate);
        	camLedgerEntity.setBalance(lastBalance+camLedgerEntity.getDebitAmount());
        	camLedgerEntity.setHeadBalance(0);
        	
        	camLedgerService.save(camLedgerEntity);
    	}
    	return camLedgerEntity;
	}
    
    @RequestMapping(value = "/camLedgers/camLedgerDestroyUrl", method = RequestMethod.GET)
	public @ResponseBody Object deleteCamLedger(@ModelAttribute("camLedgerEntity") CamLedgerEntity camLedgerEntity,	BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In cam ledger destroy method");
		JsonResponse errorResponse = new JsonResponse();
		try {
			camLedgerService.delete(camLedgerEntity.getCamLedgerid());
			ss.setComplete();
			return camLedgerService.findALL();
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
	}
    
    @RequestMapping(value = "/camLedgers/camLedgerStatusUpdate/{camLedgerid}", method = {RequestMethod.GET, RequestMethod.POST })
	public void camLedgerStatusUpdate(@PathVariable int camLedgerid, HttpServletResponse response) {
		
		logger.info("In cam ledger status change method");
		camLedgerService.camLedgerStatusUpdate(camLedgerid,response);	
	}
    
    @RequestMapping(value = "/camLedgers/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getCAMLedgerContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(commonService.selectQuery("CamLedgerEntity", attributeList, null));
		
		return set;
	}
    
    @RequestMapping(value = "/camLedgers/commonFilterForCAMTransactions", method = RequestMethod.GET)
	public @ResponseBody Set<String> commonFilterForCAMTransactions() {
		return camLedgerService.commonFilterForCAMTransactions();
	}
    
    //****************************** Cam Sub Ledger Read and Create methods ********************************//
    
    @RequestMapping(value = "/camLedgers/subLedgerRead/{elLedgerid}", method = { RequestMethod.GET, RequestMethod.POST })
   	public @ResponseBody List<CamSubLedgerEntity> readSubLedger(@PathVariable int elLedgerid) {
       	logger.info("In Cam sub Ledger read Method");
   		List<CamSubLedgerEntity> subLedgerEntities = camSubLedgerService.findAllById(elLedgerid);
   		return subLedgerEntities;
   	}
    
   /* @RequestMapping(value = "/camLedgers/subLedgerCreate/{camLedgerid}", method = { RequestMethod.GET, RequestMethod.POST })
  	public @ResponseBody
  	Object saveSubLedger(@ModelAttribute("camSubLedgerEntity") CamSubLedgerEntity camSubLedgerEntity, BindingResult bindingResult,@PathVariable int camLedgerid,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException {

    	//int lastTransactionCamLedgerId = camSubLedgerService.getLastTransactionCamLedgerId(camLedgerid);
    	CamLedgerEntity camLedgerEntity = camLedgerService.find(camLedgerid);
    	
    	List<CamLedgerEntity> list = camLedgerService.getCamHeadTest(camLedgerEntity.getCamHeadProperty());
    	
    	if(list.size()<2){
    		camSubLedgerEntity.setAmount(0.0);
    	}else{
    		BigDecimal lastTransactionCamLedgerId=camSubLedgerService.getLastLedgerId(camLedgerEntity.getCamHeadProperty());
    		CamSubLedgerEntity lastTransactionCamSubLedger = camSubLedgerService.getLastCamSubLedger(lastTransactionCamLedgerId.intValue(),camSubLedgerEntity.getTransactionCode());
    		camSubLedgerEntity.setAmount(camSubLedgerEntity.getAmount()+lastTransactionCamSubLedger.getAmount());
    	}
    	
    	camSubLedgerEntity.setStatus("Approved");
    	camSubLedgerEntity.setCamLedgerEntity(camLedgerService.find(camLedgerid));
    	camSubLedgerEntity.setBalanceAmount(camSubLedgerEntity.getAmount());
    	
    	camSubLedgerService.save(camSubLedgerEntity);  
    	
    	double totalSubLedgerAmount = camSubLedgerService.getTotalSubLedgerAmountBasedOnCamLedgerId(camLedgerid);
    	camLedgerEntity.setAmount(totalSubLedgerAmount);
    	camLedgerEntity.setBalance(camLedgerEntity.getBalance()+camSubLedgerEntity.getAmount());
    	
    	camLedgerService.update(camLedgerEntity);
    	
  		return camSubLedgerEntity;  
  	}
    
    @RequestMapping(value = "/camLedgers/subLedgerUpdateUrl/{camLedgerid}", method = { RequestMethod.GET, RequestMethod.POST })
  	public @ResponseBody Object updateSubLedger(@ModelAttribute("camSubLedgerEntity") CamSubLedgerEntity camSubLedgerEntity, BindingResult bindingResult,@PathVariable int camLedgerid,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException {

    	CamLedgerEntity camLedgerEntity = camLedgerService.find(camLedgerid); 
    	
    	camSubLedgerEntity.setBalanceAmount(camSubLedgerEntity.getAmount());
    	camSubLedgerEntity.setCamLedgerEntity(camLedgerEntity);
    	camSubLedgerEntity.setStatus("Approved");
      	
    	camSubLedgerService.update(camSubLedgerEntity);  
    	
    	double totalSubLedgerAmount = camSubLedgerService.getTotalSubLedgerAmountBasedOnCamLedgerId(camLedgerid);
    	BigDecimal lastTransactionCamLedgerId=camSubLedgerService.getLastLedgerId(camLedgerEntity.getCamHeadProperty());
    	
    	camLedgerEntity.setAmount(totalSubLedgerAmount);
    	camLedgerEntity.setBalance(camLedgerService.find(lastTransactionCamLedgerId.intValue()).getBalance()+totalSubLedgerAmount);
    	
    	camLedgerService.update(camLedgerEntity);
    	
  		return camSubLedgerEntity;  
  	}
    
    @RequestMapping(value = "/camLedgers/subLedgerDestroyUrl", method = RequestMethod.GET)
	public @ResponseBody Object deleteCamSubLedger(@ModelAttribute("camSubLedgerEntity") CamSubLedgerEntity camSubLedgerEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In cam sub ledger destroy method");
		CamLedgerEntity camLedgerEntity = camLedgerService.find(camSubLedgerEntity.getCamLedgerid());
		JsonResponse errorResponse = new JsonResponse();
		try {
			camSubLedgerService.delete(camSubLedgerEntity.getCamSubLedgerid());
			
			camLedgerEntity.setAmount(camLedgerEntity.getAmount() - camSubLedgerEntity.getAmount());
			camLedgerEntity.setBalance(camLedgerEntity.getBalance() - camSubLedgerEntity.getAmount());
			
			camLedgerService.update(camLedgerEntity);			
			ss.setComplete();
			return camSubLedgerEntity;
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
	}*/
    
   //****************************** Ledger Filters Data methods ********************************//
	
	/*@RequestMapping(value = "/ledger/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getLedgerContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(commonService.selectQuery("ElectricityLedgerEntity", attributeList, null));
		
		return set;
	}*/
		
	  //****************************** SubLedger Filters Data methods ********************************//
	
		/*@RequestMapping(value = "/subLedger/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody
		Set<?> getSubLedgerContentsForFilter(@PathVariable String feild) {
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set=new HashSet<Object>(commonService.selectQuery("ElectricitySubLedgerEntity", attributeList, null));
			
			return set;
		}*/
    
    @RequestMapping(value = "/camLedgers/transactionCodeList/{camHeadProperty}", method = RequestMethod.GET)
	public @ResponseBody List<?> transactionCodeList(@PathVariable String camHeadProperty) 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<?> codeList = camSubLedgerService.transactionCodeList(camHeadProperty);
		Map<String, Object> deptMap = null;
		for (Iterator<?> iterator = codeList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
 	       	deptMap = new HashMap<String, Object>();
 	       	deptMap.put("transactionCode", (String)values[0]);
 	       	deptMap.put("transactionName", (String)values[1]);
 	       	result.add(deptMap);
 	     }
        return result;
	}
	
//****************************** Common Area Maintenance Consolidations Read and Create methods ********************************//
    
    @RequestMapping(value = "/camConsolidation/camConsolidationRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CamConsolidationEntity> readCamConsolidationData()
	{
    	logger.info("In Cam Ledger read Method");
		List<CamConsolidationEntity> consolidationEntities = camConsolidationService.findALL();
		return consolidationEntities;
	}
    
    @RequestMapping(value = "/camConsolidation/camConsolidationCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object saveCamConsolidation(@ModelAttribute("consolidationEntity") CamConsolidationEntity consolidationEntity, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException {

    	java.sql.Date fromDate;
    	java.sql.Date toDate;
    	Calendar cal = Calendar.getInstance();
    	
    	List<Integer> camConsolidationIdList = camConsolidationService.isEmptyCamConsolidationEntity();
    	if(camConsolidationIdList.isEmpty()){
    		if(camConsolidationService.getFromDateFromCamLedger()!=null){
    			fromDate = camConsolidationService.getFromDateFromCamLedger();
    		}else{
    			fromDate = dateTimeCalender.getDateToStore(req.getParameter("fromDate"));
    		}
    		if(consolidationEntity.getChargesType().equals("Custom")){
    			toDate = dateTimeCalender.getDateToStore(req.getParameter("toDate"));
    		}else if(consolidationEntity.getChargesType().equals("Monthly")){
    		    cal.setTime(fromDate);
    		    cal.add(Calendar.MONTH, 1);
    			toDate = new java.sql.Date(cal.getTime().getTime());
    		}else if(consolidationEntity.getChargesType().equals("Quarterly")){
    			cal.setTime(fromDate);
    		    cal.add(Calendar.MONTH, 3);
    			toDate = new java.sql.Date(cal.getTime().getTime());
    		}else {
    			cal.setTime(fromDate);
    		    cal.add(Calendar.MONTH, 12);
    			toDate = new java.sql.Date(cal.getTime().getTime());
    		}
    		
    	}else{
    		CamConsolidationEntity camConsolidationEntity = camConsolidationService.find(camConsolidationService.getCamConsolidationMaxId());
    		fromDate = camConsolidationEntity.getToDate();
    		if(consolidationEntity.getChargesType().equals("Custom")){
    			toDate = dateTimeCalender.getDateToStore(req.getParameter("toDate"));
    		}else if(consolidationEntity.getChargesType().equals("Monthly")){
    		    cal.setTime(fromDate);
    		    cal.add(Calendar.MONTH, 1);
    			toDate = new java.sql.Date(cal.getTime().getTime());
    			java.sql.Date todayDate = new java.sql.Date(new Date().getTime());
    			if(toDate.equals(todayDate)||toDate.after(todayDate)){
    				toDate = new java.sql.Date(new Date().getTime());
    			}
    		}else if(consolidationEntity.getChargesType().equals("Quarterly")){
    			cal.setTime(fromDate);
    		    cal.add(Calendar.MONTH, 3);
    			toDate = new java.sql.Date(cal.getTime().getTime());
    			java.sql.Date todayDate = new java.sql.Date(new Date().getTime());
    			if(toDate.equals(todayDate)||toDate.after(todayDate)){
    				toDate = new java.sql.Date(new Date().getTime());
    			}
    		}else {
    			cal.setTime(fromDate);
    		    cal.add(Calendar.MONTH, 12);
    			toDate = new java.sql.Date(cal.getTime().getTime());
    			java.sql.Date todayDate = new java.sql.Date(new Date().getTime());
    			if(toDate.equals(todayDate)||toDate.after(todayDate)){
    				toDate = new java.sql.Date(new Date().getTime());
    			}
    		}
    	}
    	
    	consolidationEntity.setNoOfFlats(camConsolidationService.getNoOfFlats().intValue());
    	consolidationEntity.setTotalSqft(camConsolidationService.getTotalSQFT().intValue());
    	consolidationEntity.setNoFlatsBilled(0);
    	consolidationEntity.setFromDate(fromDate);
    	consolidationEntity.setToDate(toDate);
    	consolidationEntity.setStatus("Created");	
    	
		List<CamLedgerEntity> camLedgerList = camConsolidationService.getHeadsData(fromDate, toDate);
		consolidationEntity.setBlanceAmount(0);
		consolidationEntity.setPaidAmount(0);
		
		camConsolidationService.save(consolidationEntity); 
		
		double amountOfFlatBasis=0;
		double amountOfSQFTBasis=0;
		double amountOfRebateBasis=0;
		
		for (CamLedgerEntity camLedgerEntity : camLedgerList) {
			
			if(camLedgerEntity.getCalculationBased().equals("Actual")){
				CamHeadsEntity camHeadsEntity = new CamHeadsEntity();

				camHeadsEntity.setGroupName(camLedgerEntity.getCamHeadProperty());
				camHeadsEntity.setTransactionCode(camLedgerEntity.getTransactionCode());
				camHeadsEntity.setCalculationBasis(camConsolidationService.getCalcBasis(camLedgerEntity.getTransactionCode()));
				camHeadsEntity.setAmount(camLedgerEntity.getDebitAmount());

				if (camConsolidationService.getCalcBasis(camLedgerEntity.getTransactionCode()).equals("Actual")) {
					if(camLedgerEntity.getTransactionCode().trim().equals("CAM_SOLAR_REBATE")){
						amountOfRebateBasis+=camLedgerEntity.getDebitAmount();
					}else{
						amountOfFlatBasis += camLedgerEntity.getDebitAmount();
					}
				}

				if (camConsolidationService.getCalcBasis(camLedgerEntity.getTransactionCode()).equals("Actual Per Sqft")) {
					amountOfSQFTBasis += camLedgerEntity.getDebitAmount();
				}
				camHeadsEntity.setCamConsolidationEntity(consolidationEntity);

				camHeadsService.save(camHeadsEntity);
				camLedgerEntity.setCcId(consolidationEntity.getCcId());
				camLedgerService.update(camLedgerEntity);
				
				consolidationEntity.setRatePerFlat(Math.ceil((amountOfFlatBasis)/(camConsolidationService.getNoOfFlats().intValue())));
				consolidationEntity.setRatePerSqft((amountOfSQFTBasis)/(camConsolidationService.getTotalSQFT().intValue()));
				if(amountOfRebateBasis>0){
					consolidationEntity.setRebateRate(Math.round((amountOfRebateBasis)/(camConsolidationService.getNoOfFlats().intValue())));
				}else{
					consolidationEntity.setRebateRate(0);
				}
				
				consolidationEntity.setBilled(amountOfFlatBasis+amountOfSQFTBasis);
				consolidationEntity.setToBeBilled(consolidationEntity.getBilled());
	    		consolidationEntity.setBlanceAmount(amountOfFlatBasis+amountOfSQFTBasis);
	    		
				/*if(!consolidationEntity.getCalculationBased().equals("Actual")){
		    		if(consolidationEntity.getFixedPerSqft()>0){
		    			consolidationEntity.setBilled(amountOfFlatBasis+amountOfSQFTBasis);
		    			consolidationEntity.setToBeBilled(amountOfFlatBasis+(consolidationEntity.getFixedPerSqft()*consolidationEntity.getTotalSqft()));
		    			consolidationEntity.setBlanceAmount(amountOfFlatBasis+(consolidationEntity.getFixedPerSqft()*consolidationEntity.getTotalSqft()));
		    		}
		    	}else{
		    		consolidationEntity.setBilled(amountOfFlatBasis+amountOfSQFTBasis);
		    		consolidationEntity.setBlanceAmount(amountOfFlatBasis+amountOfSQFTBasis);
		    	}*/
				
				
			}else if(camLedgerEntity.getCalculationBased().equals("Fixed")){
				CamHeadsEntity camHeadsEntity = new CamHeadsEntity();

				camHeadsEntity.setGroupName(camLedgerEntity.getCamHeadProperty());
				camHeadsEntity.setTransactionCode(camLedgerEntity.getTransactionCode());
				camHeadsEntity.setCalculationBasis(camConsolidationService.getCalcBasis(camLedgerEntity.getTransactionCode()));
				camHeadsEntity.setAmount(camLedgerEntity.getDebitAmount());
				camHeadsEntity.setCamConsolidationEntity(consolidationEntity);

				camHeadsService.save(camHeadsEntity);
				camLedgerEntity.setCcId(consolidationEntity.getCcId());
				camLedgerService.update(camLedgerEntity);
				
				double fixedFlatRate = Math.ceil(camLedgerService.getCamSetting().get(0).getRateForFlat());
				double fixedSqftRate = camLedgerService.getCamSetting().get(0).getRateForSqft();
				
				consolidationEntity.setRatePerFlat(fixedFlatRate);
				consolidationEntity.setRatePerSqft(fixedSqftRate);
				consolidationEntity.setRebateRate(0);
				
				consolidationEntity.setBilled(0);
    			consolidationEntity.setToBeBilled((consolidationEntity.getNoOfFlats()*fixedFlatRate)+(consolidationEntity.getTotalSqft()*fixedSqftRate));
    			consolidationEntity.setBlanceAmount(consolidationEntity.getToBeBilled());
				
			}
		}
		
		camConsolidationService.update(consolidationEntity); 
		
    	return consolidationEntity;
	}
    
    @RequestMapping(value = "/camConsolidation/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getConsolidationContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set=new HashSet<Object>(commonService.selectQuery("CamConsolidationEntity", attributeList, null));
		
		return set;
	}
    
    // ****************************** Common Area Maintenance Consolidations Heads Read and Create methods ********************************//
    
    @RequestMapping(value = "/camConsolidation/camConsolidationHeadsRead/{ccId}", method = { RequestMethod.GET, RequestMethod.POST })
   	public @ResponseBody List<CamHeadsEntity> readCamConsolidationHeads(@PathVariable int ccId)
   	{
       	logger.info("In Cam Ledger read Method");
   		List<CamHeadsEntity> headsEntities = camHeadsService.findAllById(ccId);
   		return headsEntities;
   	}
    
    @RequestMapping(value = "/camConsolidation/postTheBillData", method = { RequestMethod.GET, RequestMethod.POST })
   	public @ResponseBody Object postTheBillData(HttpServletRequest request)
   	{
    	CamConsolidationEntity consolidationEntity = camConsolidationService.find(Integer.parseInt(request.getParameter("ccId")));
    	if(request.getParameter("flatsType").equals("All")){
    		
    		List<Integer> accountIdList = camConsolidationService.findAllAccountsOfCamService();
    		for (Integer accountId : accountIdList) {
    			
    			ElectricityBillEntity billEntity = new ElectricityBillEntity();
    			Account account = accountService.find(accountId);
            	
            	Date currentBillDate =new Date();
            	billEntity.setAccountId(accountId);
            	billEntity.setTypeOfService("CAM");
            	
            	double amountOfFlat = consolidationEntity.getRatePerFlat();
            	double amountOfrebate = consolidationEntity.getRebateRate();
            	double amountOfSQFT=0.0;
            	if(consolidationEntity.getFixedPerSqft()>0 && consolidationEntity.getToBeBilled()>0){
            		amountOfSQFT=consolidationEntity.getFixedPerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId());
            	}else {
            		amountOfSQFT=consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId());
				}
            	
            	billEntity.setBillAmount((double) Math.round(amountOfFlat+amountOfSQFT-amountOfrebate));
        		billEntity.setElBillDate(new java.sql.Timestamp(currentBillDate.getTime()));
        		billEntity.setPostType("Bill");
        		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
        		Date billDueDate = addDays(currentBillDate, 15);
        		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
        		billEntity.setBillMonth(new java.sql.Date(currentBillDate.getTime()));
        		billEntity.setStatus("Generated");
        		billEntity.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
        		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
        		billEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
        		billEntity.setBillType("Normal");
        		billEntity.setAvgAmount(0.0);
        		int lastBillId = camConsolidationService.getLastBillObj(accountId,"CAM","Bill");
        		if(lastBillId!=0){
        			billEntity.setFromDate(electricityBillsService.find(lastBillId).getBillDate());
        		}else{
        			int serviceMasterId = camConsolidationService.getServiceMasterObj(accountId,"CAM");
        			billEntity.setFromDate(serviceMasterService.find(serviceMasterId).getServiceStartDate());
        		}
        		billEntity.setBillNo("BILL"+billParameterService.getSequencyNumber());
        		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS", accountId, "CAM"));
        		billEntity.setNetAmount((billEntity.getBillAmount())+(billEntity.getArrearsAmount())-(billEntity.getAdvanceClearedAmount()));
        		storeArrears("ARREARS",billEntity);
        		
        		electricityBillsService.save(billEntity);
        		
        		double totalLineItemAmount = 0.0;
        		double totalRebateAmount = 0.0;
        		List<CamHeadsEntity> headsList = camHeadsService.getHeadData(Integer.parseInt(request.getParameter("ccId")));
        		for (CamHeadsEntity camHeadsEntity : headsList) {
        			ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
        			ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
        			
        			billLineItemEntity.setTransactionCode(camHeadsEntity.getTransactionCode());
        			
        			if(camHeadsEntity.getCalculationBasis().equals("Fixed")){
        				
        				ElectricityBillLineItemEntity billLineItemEntityFixed1 = new ElectricityBillLineItemEntity();
            			ElectricityBillParametersEntity billParametersEntityFixed1 = new ElectricityBillParametersEntity();
        				
            			billLineItemEntityFixed1.setTransactionCode(camHeadsEntity.getTransactionCode());
        				String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
        				billLineItemEntityFixed1.setBalanceAmount(consolidationEntity.getRatePerFlat()+camConsolidationService.getAreaOfProperty(account.getPropertyId())*consolidationEntity.getRatePerSqft());
        				billParametersEntityFixed1.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
        				billParametersEntityFixed1.setElBillParameterValue("1");
        				
        				billLineItemEntityFixed1.setStatus("Active");
        				billParametersEntityFixed1.setStatus("Active");
            			
        				billLineItemEntityFixed1.setElectricityBillEntity(billEntity);
            			billParametersEntityFixed1.setElectricityBillEntity(billEntity);
            			
            			electricityBillLineItemService.save(billLineItemEntityFixed1);
            			billParameterService.save(billParametersEntityFixed1);
            			
            			CamLedgerEntity camLedgerEntity = new CamLedgerEntity();
            			
            			
            			camLedgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
            			camLedgerEntity.setCamHeadProperty(camHeadsEntity.getGroupName());
            			camLedgerEntity.setTransactionCode(camHeadsEntity.getTransactionCode());
            			List<CamChargesEntity> camSetting = camLedgerService.getCamSetting();
            			camLedgerEntity.setCalculationBased(camSetting.get(0).getCamChargesBasedOn());
            			camLedgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
            			camLedgerEntity.setPostedToBill("Yes");
            			camLedgerEntity.setCreditAmount(consolidationEntity.getRatePerFlat()+camConsolidationService.getAreaOfProperty(account.getPropertyId())*consolidationEntity.getRatePerSqft());
            			camLedgerEntity.setDebitAmount(0);
            			camLedgerEntity.setBalance(camLedgerService.find(camLedgerService.getLastCamLedgerId()).getBalance()-camLedgerEntity.getCreditAmount());
            			camLedgerEntity.setHeadBalance(0);
            			camLedgerEntity.setStatus("Posted");
            			
            			camLedgerService.save(camLedgerEntity);
        				
        			}else{
        				
        				if(camHeadsEntity.getCalculationBasis().equals("Actual")){
        					
        					if(camHeadsEntity.getTransactionCode().trim().equals("CAM_SOLAR_REBATE")){
        						String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
        						totalRebateAmount+=consolidationEntity.getRebateRate();
        						totalLineItemAmount+=consolidationEntity.getRebateRate();
                				billLineItemEntity.setBalanceAmount(-consolidationEntity.getRebateRate());
                				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
                				billParametersEntity.setElBillParameterValue("1");
        					}else{
        						String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
                				totalLineItemAmount+=consolidationEntity.getRatePerFlat();
                				billLineItemEntity.setBalanceAmount(consolidationEntity.getRatePerFlat());
                				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
                				billParametersEntity.setElBillParameterValue("1");
        					}
            			}else{
            				if(consolidationEntity.getFixedPerSqft()>0 && consolidationEntity.getToBeBilled()>0){
            					String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
            					totalLineItemAmount+=consolidationEntity.getFixedPerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId());
            					billLineItemEntity.setBalanceAmount(consolidationEntity.getFixedPerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId()));
                				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
                				billParametersEntity.setElBillParameterValue(Double.toString(consolidationEntity.getFixedPerSqft()));
                        	}else {
                        		String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
                        		totalLineItemAmount+=consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId());
                        		billLineItemEntity.setBalanceAmount(consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId()));
                				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
                				billParametersEntity.setElBillParameterValue(Double.toString(camHeadsEntity.getCamConsolidationEntity().getRatePerSqft()));
            				}
            			}
            			
            			billLineItemEntity.setStatus("Active");
            			billParametersEntity.setStatus("Active");
            			
            			billLineItemEntity.setElectricityBillEntity(billEntity);
            			billParametersEntity.setElectricityBillEntity(billEntity);
            			
            			electricityBillLineItemService.save(billLineItemEntity);
            			billParameterService.save(billParametersEntity);
            			
            			CamLedgerEntity camLedgerEntity = new CamLedgerEntity();
            			
            			double test11 = Math.ceil(Math.round(totalLineItemAmount*100.0)/100.0);
            			double test22 = Math.round(totalLineItemAmount*100.0)/100.0;
            			double roundOff1 = Math.round((test11-test22)*100.0)/100.0;
            			double roundOffValue1 = 0.0;
            			if(roundOff1>0){
            				roundOffValue1=roundOff1;
            			}
            			
            			camLedgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
            			camLedgerEntity.setCamHeadProperty(camHeadsEntity.getGroupName());
            			camLedgerEntity.setTransactionCode(camHeadsEntity.getTransactionCode());
            			List<CamChargesEntity> camSetting = camLedgerService.getCamSetting();
            			camLedgerEntity.setCalculationBased(camSetting.get(0).getCamChargesBasedOn());
            			camLedgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
            			camLedgerEntity.setPostedToBill("Yes");
            			if(camHeadsEntity.getCalculationBasis().equals("Actual")){
            				if(camHeadsEntity.getTransactionCode().trim().equals("CAM_SOLAR_REBATE")){
            					camLedgerEntity.setCreditAmount(consolidationEntity.getRebateRate());
            				}else{
            					camLedgerEntity.setCreditAmount(consolidationEntity.getRatePerFlat());
            				}
            			}else{
            				camLedgerEntity.setCreditAmount(consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(account.getPropertyId())+roundOffValue1);
            			}
            			camLedgerEntity.setDebitAmount(0);
            			camLedgerEntity.setBalance(camLedgerService.find(camLedgerService.getLastCamLedgerId()).getBalance()-camLedgerEntity.getCreditAmount());
            			int ledgerIdBasedOnSubHead = camLedgerService.getLastTransactionCamLedgerId(camHeadsEntity.getTransactionCode());
            			camLedgerEntity.setHeadBalance(camLedgerService.find(ledgerIdBasedOnSubHead).getHeadBalance()-camLedgerEntity.getCreditAmount());
            			camLedgerEntity.setStatus("Posted");
            			
            			camLedgerService.save(camLedgerEntity);
        			}
        			
        		}
        		
        		ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
        		
        		billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("Total sqft")));
        		billParametersEntity.setElBillParameterValue(Double.toString(camConsolidationService.getAreaOfProperty(account.getPropertyId())));
        		billParametersEntity.setElectricityBillEntity(billEntity);
        		billParametersEntity.setStatus("Active");
        		billParameterService.save(billParametersEntity);
        		
        		HashMap<Object, Object> consolidatedBill = ineterestCalculation(accountId,"CAM",billEntity.getFromDate(),billEntity.getBillDate());
    			float interestOnArrearsAmount = 0.0f;

				for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
					if (map.getKey().equals("interestOnArrearsAmount")) {
						interestOnArrearsAmount =  (float)map.getValue();
					}
				}
				
				if(interestOnArrearsAmount>0){
					ElectricityBillLineItemEntity billLineItemEntityInterest = new ElectricityBillLineItemEntity();
	    			billLineItemEntityInterest.setTransactionCode("CAM_INTEREST");
	    			billLineItemEntityInterest.setBalanceAmount(interestOnArrearsAmount);
	    			billLineItemEntityInterest.setStatus("Active");
	    			billLineItemEntityInterest.setElectricityBillEntity(billEntity);
					
					electricityBillLineItemService.save(billLineItemEntityInterest);
				}
				
				totalLineItemAmount+=interestOnArrearsAmount;
        		
        		double test1 = Math.ceil(Math.round(totalLineItemAmount*100.0)/100.0);
    			double test2 = Math.round(totalLineItemAmount*100.0)/100.0;
    			double roundOff = Math.round((test1-test2)*100.0)/100.0;
    			double roundOffValue = 0.0;
    			if(roundOff>0){
    				ElectricityBillLineItemEntity billLineItemEntityRoundOff = new ElectricityBillLineItemEntity();
    				
    				billLineItemEntityRoundOff.setTransactionCode("CAM_ROF");
    				billLineItemEntityRoundOff.setBalanceAmount(roundOff);
    				billLineItemEntityRoundOff.setStatus("Active");
    				roundOffValue=roundOff;
    				billLineItemEntityRoundOff.setElectricityBillEntity(billEntity);
    				
    				electricityBillLineItemService.save(billLineItemEntityRoundOff);    				
    			}
        		
    			billEntity.setBillAmount(amountOfFlat+amountOfSQFT+roundOffValue+interestOnArrearsAmount-totalRebateAmount);
    			billEntity.setNetAmount((billEntity.getBillAmount())+(billEntity.getArrearsAmount())-(billEntity.getAdvanceClearedAmount()));
    			
    			electricityBillsService.update(billEntity);
    			
        		consolidationEntity.setNoFlatsBilled(consolidationEntity.getNoFlatsBilled()+1);
            	consolidationEntity.setPaidAmount(consolidationEntity.getPaidAmount()+amountOfFlat+amountOfSQFT+roundOffValue-totalRebateAmount);
            	consolidationEntity.setBlanceAmount(consolidationEntity.getBlanceAmount()-consolidationEntity.getPaidAmount());
                        	
            	camConsolidationService.update(consolidationEntity);
			}
    		
    	}else if(request.getParameter("flatsType").equals("Specific")){
    		
    		ElectricityBillEntity billEntity = new ElectricityBillEntity();
        	
        	Date currentBillDate =new Date();
        	int accountId = Integer.parseInt(request.getParameter("accountId"));
        	billEntity.setAccountId(accountId);
        	billEntity.setTypeOfService("CAM");
        	
        	double amountOfFlat=consolidationEntity.getRatePerFlat();
        	double amountOfrebate = consolidationEntity.getRebateRate();
        	double amountOfSQFT=0.0;
        	if(consolidationEntity.getFixedPerSqft()>0 && consolidationEntity.getToBeBilled()>0){
        		amountOfSQFT=consolidationEntity.getFixedPerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")));
        	}else {
        		amountOfSQFT=consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")));
			}
        	
        	billEntity.setBillAmount((double) Math.round(amountOfFlat+amountOfSQFT-amountOfrebate));
    		billEntity.setElBillDate(new java.sql.Timestamp(currentBillDate.getTime()));
    		billEntity.setPostType("Bill");
    		billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
    		Date billDueDate = addDays(currentBillDate, 15);
    		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
    		billEntity.setBillMonth(new java.sql.Date(currentBillDate.getTime()));
    		billEntity.setStatus("Generated");
    		billEntity.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
    		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
    		billEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
    		billEntity.setBillNo("BILL"+billParameterService.getSequencyNumber());
    		billEntity.setAvgAmount(0.0);
    		int lastBillId = camConsolidationService.getLastBillObj(accountId,"CAM","Bill");
    		if(lastBillId!=0){
    			billEntity.setFromDate(electricityBillsService.find(lastBillId).getFromDate());
    		}else{
    			int serviceMasterId = camConsolidationService.getServiceMasterObj(accountId,"CAM");
    			billEntity.setFromDate(serviceMasterService.find(serviceMasterId).getServiceStartDate());
    		}
    		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS", accountId, "CAM"));
    		billEntity.setNetAmount((billEntity.getBillAmount())+(billEntity.getArrearsAmount())-(billEntity.getAdvanceClearedAmount()));
    		storeArrears("ARREARS",billEntity);
    		
    		electricityBillsService.save(billEntity);
    		
    		double totalLineItemAmount = 0.0;
    		double totalRebateAmount = 0.0;
    		List<CamHeadsEntity> headsList = camHeadsService.getHeadData(Integer.parseInt(request.getParameter("ccId")));
    		for (CamHeadsEntity camHeadsEntity : headsList) {
    			ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
    			ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
    			
    			billLineItemEntity.setTransactionCode(camHeadsEntity.getTransactionCode());
    			
    			if(camHeadsEntity.getCalculationBasis().equals("Fixed")){
    				
    				ElectricityBillLineItemEntity billLineItemEntityFixed1 = new ElectricityBillLineItemEntity();
        			ElectricityBillParametersEntity billParametersEntityFixed1 = new ElectricityBillParametersEntity();
    				
        			billLineItemEntityFixed1.setTransactionCode(camHeadsEntity.getTransactionCode());
    				String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
    				billLineItemEntityFixed1.setBalanceAmount(consolidationEntity.getRatePerFlat()+camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")))*consolidationEntity.getRatePerSqft());
    				billParametersEntityFixed1.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
    				billParametersEntityFixed1.setElBillParameterValue("1");
    				
    				billLineItemEntityFixed1.setStatus("Active");
    				billParametersEntityFixed1.setStatus("Active");
        			
    				billLineItemEntityFixed1.setElectricityBillEntity(billEntity);
        			billParametersEntityFixed1.setElectricityBillEntity(billEntity);
        			
        			electricityBillLineItemService.save(billLineItemEntityFixed1);
        			billParameterService.save(billParametersEntityFixed1);
        			
        			CamLedgerEntity camLedgerEntity = new CamLedgerEntity();
        			
        			
        			camLedgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
        			camLedgerEntity.setCamHeadProperty(camHeadsEntity.getGroupName());
        			camLedgerEntity.setTransactionCode(camHeadsEntity.getTransactionCode());
        			List<CamChargesEntity> camSetting = camLedgerService.getCamSetting();
        			camLedgerEntity.setCalculationBased(camSetting.get(0).getCamChargesBasedOn());
        			camLedgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
        			camLedgerEntity.setPostedToBill("Yes");
        			camLedgerEntity.setCreditAmount(consolidationEntity.getRatePerFlat()+camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")))*consolidationEntity.getRatePerSqft());
        			camLedgerEntity.setDebitAmount(0);
        			camLedgerEntity.setBalance(camLedgerService.find(camLedgerService.getLastCamLedgerId()).getBalance()-camLedgerEntity.getCreditAmount());
        			camLedgerEntity.setHeadBalance(0);
        			camLedgerEntity.setStatus("Posted");
        			
        			camLedgerService.save(camLedgerEntity);
    				
    			}else{
    				
    				if(camHeadsEntity.getCalculationBasis().equals("Actual")){
    					if(camHeadsEntity.getTransactionCode().trim().equals("CAM_SOLAR_REBATE")){
    						String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
    						totalRebateAmount+=consolidationEntity.getRebateRate();
    						totalLineItemAmount+=consolidationEntity.getRebateRate();
            				billLineItemEntity.setBalanceAmount(-consolidationEntity.getRebateRate());
            				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
            				billParametersEntity.setElBillParameterValue("1");
    					}else{
    						String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
            				totalLineItemAmount+=consolidationEntity.getRatePerFlat();
            				billLineItemEntity.setBalanceAmount(consolidationEntity.getRatePerFlat());
            				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
            				billParametersEntity.setElBillParameterValue("1");
    					}
        			}else{
        				if(consolidationEntity.getFixedPerSqft()>0 && consolidationEntity.getToBeBilled()>0){
        					String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
        					totalLineItemAmount+=consolidationEntity.getFixedPerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")));
        					billLineItemEntity.setBalanceAmount(consolidationEntity.getFixedPerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId"))));
            				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
            				billParametersEntity.setElBillParameterValue(Double.toString(consolidationEntity.getFixedPerSqft()));
                    	}else {
                    		String transactionName = camConsolidationService.getTransactionNameBasedOnCode(camHeadsEntity.getTransactionCode());
                    		totalLineItemAmount+=consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")));
                    		billLineItemEntity.setBalanceAmount(consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId"))));
            				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
            				billParametersEntity.setElBillParameterValue(Double.toString(camHeadsEntity.getCamConsolidationEntity().getRatePerSqft()));
        				}
        			}
        			
        			billLineItemEntity.setStatus("Active");
        			billParametersEntity.setStatus("Active");
        			
        			billLineItemEntity.setElectricityBillEntity(billEntity);
        			billParametersEntity.setElectricityBillEntity(billEntity);
        			
        			electricityBillLineItemService.save(billLineItemEntity);
        			billParameterService.save(billParametersEntity);
        			
        			CamLedgerEntity camLedgerEntity = new CamLedgerEntity();
        			
        			double test11 = Math.ceil(Math.round(totalLineItemAmount*100.0)/100.0);
        			double test22 = Math.round(totalLineItemAmount*100.0)/100.0;
        			double roundOff1 = Math.round((test11-test22)*100.0)/100.0;
        			double roundOffValue1 = 0.0;
        			if(roundOff1>0){
        				roundOffValue1=roundOff1;
        			}
        			
        			camLedgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
        			camLedgerEntity.setCamHeadProperty(camHeadsEntity.getGroupName());
        			camLedgerEntity.setTransactionCode(camHeadsEntity.getTransactionCode());
        			List<CamChargesEntity> camSetting = camLedgerService.getCamSetting();
        			camLedgerEntity.setCalculationBased(camSetting.get(0).getCamChargesBasedOn());
        			camLedgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
        			camLedgerEntity.setPostedToBill("Yes");
        			if(camHeadsEntity.getCalculationBasis().equals("Actual")){
        				if(camHeadsEntity.getTransactionCode().trim().equals("CAM_SOLAR_REBATE")){
        					camLedgerEntity.setCreditAmount(consolidationEntity.getRebateRate());
        				}else{
        					camLedgerEntity.setCreditAmount(consolidationEntity.getRatePerFlat());
        				}
        			}else{
        				camLedgerEntity.setCreditAmount(consolidationEntity.getRatePerSqft()*camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")))+roundOffValue1);
        			}
        			camLedgerEntity.setDebitAmount(0);
        			camLedgerEntity.setBalance(camLedgerService.find(camLedgerService.getLastCamLedgerId()).getBalance()-camLedgerEntity.getCreditAmount());
        			int ledgerIdBasedOnSubHead = camLedgerService.getLastTransactionCamLedgerId(camHeadsEntity.getTransactionCode());
        			camLedgerEntity.setHeadBalance(camLedgerService.find(ledgerIdBasedOnSubHead).getHeadBalance()-camLedgerEntity.getCreditAmount());
        			camLedgerEntity.setStatus("Posted");
        			
        			camLedgerService.save(camLedgerEntity);
    			}
    			
    			
    		}
    		
    		ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
    		
    		billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("Total sqft")));
    		billParametersEntity.setElBillParameterValue(Double.toString(camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")))));
    		billParametersEntity.setElectricityBillEntity(billEntity);
    		billParametersEntity.setStatus("Active");
    		billParameterService.save(billParametersEntity);
    		
    		HashMap<Object, Object> consolidatedBill = ineterestCalculation(accountId,"CAM",billEntity.getFromDate(),billEntity.getBillDate());
			float interestOnArrearsAmount = 0.0f;

			for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
				if (map.getKey().equals("interestOnArrearsAmount")) {
					interestOnArrearsAmount =  (float)map.getValue();
				}
			}
			
			if(interestOnArrearsAmount>0){
				ElectricityBillLineItemEntity billLineItemEntityInterest = new ElectricityBillLineItemEntity();
    			billLineItemEntityInterest.setTransactionCode("CAM_INTEREST");
    			billLineItemEntityInterest.setBalanceAmount(interestOnArrearsAmount);
    			billLineItemEntityInterest.setStatus("Active");
    			billLineItemEntityInterest.setElectricityBillEntity(billEntity);
				
				electricityBillLineItemService.save(billLineItemEntityInterest);
			}
    		
			totalLineItemAmount+=interestOnArrearsAmount;
			
    		double test1 = Math.ceil(Math.round(totalLineItemAmount*100.0)/100.0);
			double test2 = Math.round(totalLineItemAmount*100.0)/100.0;
			double roundOff = Math.round((test1-test2)*100.0)/100.0;
			double roundOffValue = 0.0;
			if(roundOff>0){
				ElectricityBillLineItemEntity billLineItemEntityRoundOff = new ElectricityBillLineItemEntity();
				
				billLineItemEntityRoundOff.setTransactionCode("CAM_ROF");
				billLineItemEntityRoundOff.setBalanceAmount(roundOff);
				billLineItemEntityRoundOff.setStatus("Active");
				roundOffValue=roundOff;
				billLineItemEntityRoundOff.setElectricityBillEntity(billEntity);
				
				electricityBillLineItemService.save(billLineItemEntityRoundOff);    				
			}
			
			billEntity.setBillAmount(amountOfFlat+amountOfSQFT+roundOffValue+interestOnArrearsAmount-totalRebateAmount);
			billEntity.setNetAmount((billEntity.getBillAmount())+(billEntity.getArrearsAmount())-(billEntity.getAdvanceClearedAmount()));
			
			electricityBillsService.update(billEntity);
			
    		consolidationEntity.setNoFlatsBilled(consolidationEntity.getNoFlatsBilled()+1);
        	consolidationEntity.setPaidAmount(consolidationEntity.getPaidAmount()+amountOfFlat+amountOfSQFT+roundOffValue);
        	consolidationEntity.setBlanceAmount(consolidationEntity.getBlanceAmount()-consolidationEntity.getPaidAmount());
                    	
        	camConsolidationService.update(consolidationEntity);
    		}  		
    	
   		return null;
   	}
    
    public static Date addDays(Date date, int days)
	 {
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days);
	        return cal.getTime();
	 }
    
    @RequestMapping(value = "/camConsolidationBills/setCamBillsStatus/{ccId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setCamBillsStatus(@PathVariable int ccId,@PathVariable String operation, HttpServletResponse response) {
		
		logger.info("In help topic status change method");
		if (operation.equalsIgnoreCase("activate")){
			camConsolidationService.setCamBillsStatus(ccId, operation, response);
		}else{
			camConsolidationService.setCamBillsStatus(ccId, operation, response);
		}
	}
    
    @RequestMapping(value = "/camConsolidation/readAccountNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readAccountNumbers() {
		return camConsolidationService.readAccountNumbers();
	}
    
    
    // ****************************** CAM Charges settings ********************************//

	
	@RequestMapping(value = "/camChargesSetting", method = RequestMethod.GET)
	public String camChargesIndex(ModelMap model, HttpServletRequest request) 
	{
		logger.info("In Cam Charger Method");
		model.addAttribute("ViewName", "Bill Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bill Generation", 1, request);
		
		breadCrumbService.addNode("Manage Cam Charger Settings", 2, request);
		return "cam/camChargesSetting";
	}
	
	@RequestMapping(value = "/camReportSettings", method = RequestMethod.GET)
	public String camReportSettings(ModelMap model, HttpServletRequest request) 
	{
		logger.info("In Cam Report Settings Method");
		model.addAttribute("ViewName", "Bill Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bill Generation", 1, request);
		
		breadCrumbService.addNode("Manage Cam Report Settings", 2, request);
		return "cam/camReportSettings";
	}
   
	@RequestMapping(value = "/camchargerSettings/camchargerSettingsRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CamChargesEntity> readCamChargesSettings()
	{
		logger.info("In read camCharges settings method");
		List<CamChargesEntity> camChargesEntity = camChargesService.findAll();
		return camChargesEntity;
	}

	@RequestMapping(value = "/camchargerSettings/camchargerSettingsCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object camChargesSettingsCreate(@ModelAttribute("camChargesEntity") CamChargesEntity camChargesEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

		logger.info("In save camCharges settings method");
		List<CamChargesEntity> list = camChargesService.findAllData();
		for (CamChargesEntity entity : list) {
			camChargesService.delete(entity.getCamId());
		}
		validator.validate(camChargesEntity, bindingResult);
		camChargesService.save(camChargesEntity);
		sessionStatus.setComplete();
		return camChargesEntity;
	}
	
	@RequestMapping(value = "/camReportSettings/camReportSettingsRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CamReportSettingsEntity> readCamReportSettings()
	{
		logger.info("In read camCharges settings method");
		List<CamReportSettingsEntity> CamReportSettingsEntity = camReportSettingsService.findAll();
		return CamReportSettingsEntity;
	}

	@RequestMapping(value = "/camReportSettings/camReportSettingsCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object camReportSettingsCreate(@ModelAttribute("camReportSettingsEntity") CamReportSettingsEntity camReportSettingsEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

		logger.info("In save cam Report settings method");
		List<CamReportSettingsEntity> list = camReportSettingsService.findAllData();
		for (CamReportSettingsEntity entity : list) {
			camReportSettingsService.delete(entity.getReportSettingId());
		}
		validator.validate(camReportSettingsEntity, bindingResult);
		camReportSettingsService.save(camReportSettingsEntity);
		sessionStatus.setComplete();
		return camReportSettingsEntity;
	}
	
	private double getLastBillArrearsAmount(String postType, int accounId,String typeOfService) {
		ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(electricityLedgerService.getLastLedgerBillAreears(accounId, typeOfService));
		double arrearsAmount = lastTransactionLedgerEntity.getBalance();
		return arrearsAmount;
	}
	
	private void storeArrears(String postType, ElectricityBillEntity billEntity) {

		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();

		int lastTransactionLedgerId = electricityLedgerService.getLastLedgerBillAreears(billEntity.getAccountId(),billEntity.getTypeOfService());

		ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billEntity.getAccountId()).intValue()) + 1);
		ledgerEntity.setAccountId(billEntity.getAccountId());
		String typeOfService = billEntity.getTypeOfService();
		ledgerEntity.setLedgerType(typeOfService + " Ledger");
		ledgerEntity.setPostType("ARREARS");
		if (typeOfService.equals("Electricity")) {
			ledgerEntity.setTransactionCode("EL");
		}
		if (typeOfService.equals("Gas")) {
			ledgerEntity.setTransactionCode("GA");
		}
		if (typeOfService.equals("Solid Waste")) {
			ledgerEntity.setTransactionCode("SW");
		}
		if (typeOfService.equals("Water")) {
			ledgerEntity.setTransactionCode("WT");
		}
		if (typeOfService.equals("Others")) {
			ledgerEntity.setTransactionCode("OT");
		}
		if (typeOfService.equals("CAM")) {
			ledgerEntity.setTransactionCode("CAM");
		}
		if (typeOfService.equals("Telephone Broadband")) {
			ledgerEntity.setTransactionCode("TEL");
		}

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		Calendar calLast = Calendar.getInstance();
		int lastYear = calLast.get(Calendar.YEAR)-1;

		ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
		ledgerEntity.setPostReference(billEntity.getBillNo());
		ledgerEntity.setLedgerDate(billEntity.getBillDate());
		ledgerEntity.setAmount(billEntity.getArrearsAmount());
		ledgerEntity.setBalance(billEntity.getArrearsAmount());
		ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
		ledgerEntity.setStatus("Approved");

		electricityLedgerService.save(ledgerEntity);

		List<ElectricitySubLedgerEntity> lastTransactionSubLedger = electricitySubLedgerService.getLastSubLedger(lastTransactionLedgerId,ledgerEntity.getTransactionCode());

		for (ElectricitySubLedgerEntity electricitySubLedgerEntity : lastTransactionSubLedger) {

			ElectricitySubLedgerEntity subLedgerEntity = new ElectricitySubLedgerEntity();

			subLedgerEntity.setTransactionCode(electricitySubLedgerEntity.getTransactionCode());
			subLedgerEntity.setAmount(0);
			subLedgerEntity.setBalanceAmount(electricitySubLedgerEntity.getBalanceAmount());
			subLedgerEntity.setStatus("Approved");

			subLedgerEntity.setElectricityLedgerEntity(ledgerEntity);
			electricitySubLedgerService.save(subLedgerEntity);
		}
	}
	
	public HashMap<Object, Object> ineterestCalculation(int accountID,String typeOfService, Date previousBillDate, Date currentBillDate){
		
		logger.info("::::::::::::::: In interest on calculation Solid Waste :::::::::::::::");
		HashMap<Object, Object> intersts = new LinkedHashMap<>();
		float interestOnArrearsAmount = 0f;
		float rateOfInterestL = 0;
		float rateOfInterest = 0f;
		final Locale locale = null;
		
		String str = messageSource.getMessage("camInterestRate", null, locale);
		double interestRate = Double.parseDouble(str) / 100;

		/*List<InterestSettingsEntity> interestSettingsEntities = interestSettingService.findAllData();
		String interestType = null;
		for (InterestSettingsEntity interestSettingsEntity : interestSettingsEntities) {
			interestType = interestSettingsEntity.getInterestBasedOn();
			logger.info(" Interest Calculation base on -->" + interestType);
		}*/
		String configName = "Interest Calculation";
		String status = "Active";
		String interestType = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("interestType ==================== "+interestType);

		if (interestType != null) {
			if (interestType.trim().equalsIgnoreCase("Daywise")) {
				ElectricityBillEntity billEntity = null;
				BigDecimal test = camConsolidationService.getPreviousBill(accountID, typeOfService, "Bill");
				if (test != null) {
					int billId = test.intValue();
					billEntity = electricityBillsService.find(billId);
				}
				if (billEntity != null) {
					String transactionCode = "CAM";
					ElectricityLedgerEntity electricityLedgerEntity = null;
					if (camConsolidationService.getPreviousLedger(accountID,transactionCode) != null) {
						int ledgerId = camConsolidationService.getPreviousLedger(accountID, transactionCode).intValue();
						electricityLedgerEntity = electricityLedgerService.find(ledgerId);
					}

					if (electricityLedgerEntity.getPostType().trim().equalsIgnoreCase("PAYMENT")) {

						logger.info("electricityLedgerEntity.getPostType ---- in IF "+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(electricityLedgerEntity.getPostedBillDate()).toDateMidnight().toDate();
						Date billDueDate = new LocalDate(billEntity.getBillDueDate()).toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount()+ ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(ledgerEntityPayments.getPostedBillDate());
										endDate = new LocalDate(billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths+ billableMonths;

										int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID,previousBillDate,transactionCode).intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

											if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ billEntity.getArrearsAmount());
										float arrersAmount = (float) (billEntity.getArrearsAmount() - (rateOfInterestL));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}

							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount() + ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(ledgerEntityPayments.getPostedBillDate());
										endDate = new LocalDate(billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths+ billableMonths;

										int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID,previousBillDate,transactionCode).intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

											if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ billEntity.getArrearsAmount());
										float arrersAmount = (float) (billEntity.getArrearsAmount() - (rateOfInterestL));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "+ billEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getBillAmount() + ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(ledgerEntityPayments.getPostedBillDate());
										endDate = new LocalDate(billEntity.getBillDueDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths + billableMonths;

										for (ElectricityBillLineItemEntity billLineItemEntity : billEntity.getBillLineItemEntities()) {

											if (billLineItemEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity.getBalanceAmount();
											}
										}
										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ billEntity.getArrearsAmount());
										float arrersAmount = (float) (billEntity.getArrearsAmount() - (rateOfInterestL));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "+ billEntity.getArrearsAmount());
							if (billEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (billEntity.getArrearsAmount() + ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(ledgerEntityPayments.getPostedBillDate());
										endDate = new LocalDate(billEntity.getBillDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths + billableMonths;

										int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID,previousBillDate,transactionCode).intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

											if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ billEntity.getArrearsAmount());
										float arrersAmount = (float) (billEntity.getArrearsAmount() - (rateOfInterestL));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}
							if (billEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else {
						logger.info("electricityLedgerEntity.getPostType ---- in Else "+ electricityLedgerEntity.getPostType());
						if (billEntity.getArrearsAmount() > 0) {

							logger.info("billEntity.getArrearsAmount() "+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDate());
							logger.info("----------------------startDate------------------ "+ startDate);
							logger.info("----------------------endDate------------------"+ endDate);
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("----------------------days------------------"+ days);
							PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
							Period difference = new Period(endDate, startDate,monthDay);
							float billableMonths = difference.getMonths();
							int daysAfterMonth = difference.getDays();
							Calendar cal = Calendar.getInstance();
							cal.setTime(currentBillDate);
							float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							float netMonth = daysToMonths + billableMonths;
							logger.info("------------------- net month "+ netMonth);
							logger.info("============ Arrears Amount interst days ========= "+ days);

							int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID, previousBillDate,transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

								if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
								}
							}
							logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
							logger.info("previouBillEntity.getArrearsAmount() ---------- "+ billEntity.getArrearsAmount());
							float arrersAmount = (float) (billEntity.getArrearsAmount() - (rateOfInterestL));
							interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
							intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
							logger.info("#################################-->"+ interestOnArrearsAmount);

						}
						if (billEntity.getBillAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "+ billEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(billEntity.getBillDueDate());
							logger.info("----------------------startDate------------------ "+ startDate);
							logger.info("----------------------endDate------------------"+ endDate);
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("----------------------days------------------"+ days);
							PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
							Period difference = new Period(endDate, startDate,monthDay);
							float billableMonths = difference.getMonths();
							int daysAfterMonth = difference.getDays();
							Calendar cal = Calendar.getInstance();
							cal.setTime(currentBillDate);
							float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							float netMonth = daysToMonths + billableMonths;
							logger.info("------------------- net month "+ netMonth);
							logger.info("============ Arrears Amount interst days ========= "+ days);

							int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID, previousBillDate,transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

								if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
								}
							}
							logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
							logger.info("previouBillEntity.getArrearsAmount() ---------- "+ billEntity.getArrearsAmount());
							float arrersAmount = (float) (billEntity.getArrearsAmount() - (rateOfInterestL));
							interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
							intersts.put("interestOnArrearsAmount",	interestOnArrearsAmount);
							logger.info("#################################-->"+ interestOnArrearsAmount);

						}
					}
				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}

			} else {

				logger.info("--------------------- Interest calculation based on ----"+ interestType);
				ElectricityBillEntity previouBillEntity = null;
				BigDecimal test = camConsolidationService.getPreviousBill(accountID, typeOfService, "Bill");
				if (test != null) {
					int billId = test.intValue();
					previouBillEntity = electricityBillsService.find(billId);
				}
				if (previouBillEntity != null) {
					String transactionCode = "CAM";
					ElectricityLedgerEntity electricityLedgerEntity = null;
					if (camConsolidationService.getPreviousLedger(accountID,transactionCode) != null) {
						int ledgerId = camConsolidationService.getPreviousLedger(accountID, transactionCode).intValue();
						electricityLedgerEntity = electricityLedgerService.find(ledgerId);
					}
					if (electricityLedgerEntity != null && electricityLedgerEntity.getPostType().trim().equalsIgnoreCase("PAYMENT")) {
						logger.info("electricityLedgerEntity.getPostType ---- in IF "+ electricityLedgerEntity.getPostType());
						Date ledgerPostDate = new LocalDate(electricityLedgerEntity.getPostedBillDate()).toDateMidnight().toDate();
						Date billDueDate = new LocalDate(previouBillEntity.getBillDueDate()).toDateMidnight().toDate();
						if ((billDueDate.compareTo(ledgerPostDate)) == 0) {
							logger.info("======== payment done on same date of due date ===============");
							logger.info("billEntity.getArrearsAmount() "+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount() + ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(currentBillDate);
										endDate = new LocalDate(previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths+ billableMonths;

										int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID,previousBillDate,transactionCode).intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

											if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
											}
										}
										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ previouBillEntity.getArrearsAmount());
										float arrersAmount = (float) (previouBillEntity.getArrearsAmount() - (rateOfInterestL));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}

							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						} else if (ledgerPostDate.compareTo(billDueDate) > 0) {
							logger.info("======== payment done on after due date ===============");

							if (previouBillEntity.getArrearsAmount() > 0) {
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getArrearsAmount() + ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(currentBillDate);
										endDate = new LocalDate(previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths + billableMonths;

										int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID,previousBillDate,transactionCode).intValue();
										ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
										logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
										List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
										for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

											if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
											}
										}
										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ previouBillEntity.getArrearsAmount());
										float arrersAmount = (float) (previouBillEntity.getArrearsAmount() - (rateOfInterestL));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("billEntity.getBillAmount() "+ previouBillEntity.getBillAmount());
								List<ElectricityLedgerEntity> ledgerEntities = camConsolidationService.getPreviousLedgerPayments(accountID,transactionCode);
								logger.info("========= Number of payments done in last month ============= "+ ledgerEntities.size());
								for (ElectricityLedgerEntity ledgerEntityPayments : ledgerEntities) {
									if (previouBillEntity.getBillAmount() + ledgerEntityPayments.getAmount() == 0) {
										LocalDate startDate = null;
										LocalDate endDate = null;
										startDate = new LocalDate(currentBillDate);
										endDate = new LocalDate(previouBillEntity.getBillDate());
										Days d = Days.daysBetween(startDate,endDate);
										int days = d.getDays();
										logger.info("============ Arrears Amount interst days ========= "+ days);
										PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
										Period difference = new Period(endDate,startDate, monthDay);
										float billableMonths = difference.getMonths();
										int daysAfterMonth = difference.getDays();
										Calendar cal = Calendar.getInstance();
										cal.setTime(currentBillDate);
										float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
										float netMonth = daysToMonths + billableMonths;

										for (ElectricityBillLineItemEntity billLineItemEntity : previouBillEntity.getBillLineItemEntities()) {

											if (billLineItemEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
												rateOfInterest = (float) billLineItemEntity.getBalanceAmount();
											}
										}

										logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
										logger.info("previouBillEntity.getArrearsAmount() ---------- "+ previouBillEntity.getArrearsAmount());
										float arrersAmount = (float) (previouBillEntity.getArrearsAmount() - (rateOfInterest));
										interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
										intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
										logger.info("#################################-->"+ interestOnArrearsAmount);
									}
								}
							}
						} else {
							logger.info("======== payment done on before due date ===============");
							logger.info("billEntity.getArrearsAmount() "+ previouBillEntity.getArrearsAmount());
							if (previouBillEntity.getArrearsAmount() > 0) {
							}
							if (previouBillEntity.getBillAmount() > 0) {
								logger.info("============ Payment done within due date so no interest for present bill amount =============");
							}
						}

					} else {
						if (previouBillEntity.getArrearsAmount() > 0) {
							logger.info("billEntity.getArrearsAmount() "+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(previouBillEntity.getBillDate());
							logger.info("----------------------startDate------------------ "+ startDate);
							logger.info("----------------------endDate------------------"+ endDate);
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("----------------------days------------------"+ days);
							PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
							Period difference = new Period(endDate, startDate,monthDay);
							float billableMonths = difference.getMonths();
							int daysAfterMonth = difference.getDays();
							Calendar cal = Calendar.getInstance();
							cal.setTime(currentBillDate);
							float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							float netMonth = daysToMonths + billableMonths;
							logger.info("------------------- net month "+ netMonth);

							logger.info("============ Arrears Amount interst days ========= "+ days);

							int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID, previousBillDate,transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

								if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
								}
							}
							logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
							logger.info("previouBillEntity.getArrearsAmount() ---------- "+ previouBillEntity.getArrearsAmount());
							float arrersAmount = (float) (previouBillEntity.getArrearsAmount() - (rateOfInterestL));
							interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
							intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
							logger.info("#################################-->"+ interestOnArrearsAmount);
						}
						if (previouBillEntity.getBillAmount() > 0) {
							logger.info("previouBillEntity.getBillAmount() "+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(previouBillEntity.getBillDate());
							logger.info("----------------------startDate------------------ "+ startDate);
							logger.info("----------------------endDate------------------"+ endDate);
							Days d = Days.daysBetween(startDate, endDate);
							int days = d.getDays();
							logger.info("----------------------days------------------"+ days);
							PeriodType monthDay = PeriodType.yearMonthDay().withYearsRemoved();
							Period difference = new Period(endDate, startDate,monthDay);
							float billableMonths = difference.getMonths();
							int daysAfterMonth = difference.getDays();
							Calendar cal = Calendar.getInstance();
							cal.setTime(currentBillDate);
							float daysToMonths = (float) daysAfterMonth / cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							float netMonth = daysToMonths + billableMonths;
							logger.info("------------------- net month "+ netMonth);

							logger.info("============ Arrears Amount interst days ========= "+ days);

							int lastLedgerId = electricityLedgerService.getLastArrearsLedgerBasedOnPayment(accountID, previousBillDate,transactionCode).intValue();
							ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(lastLedgerId);
							logger.info("ledgerEntity ---------- "+ ledgerEntity.getPostType());
							List<ElectricitySubLedgerEntity> subLedgerEntities = electricitySubLedgerService.findAllById1(ledgerEntity.getElLedgerid());
							for (ElectricitySubLedgerEntity electricitySubLedgerEntity : subLedgerEntities) {

								if (electricitySubLedgerEntity.getTransactionCode().trim().equalsIgnoreCase("CAM_INTEREST")) {
									rateOfInterestL = (float) electricitySubLedgerEntity.getBalanceAmount();
								}
							}
							logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
							logger.info("previouBillEntity.getBillAmount() ---------- "+ previouBillEntity.getBillAmount());
							float arrersAmount = (float) (previouBillEntity.getBillAmount() - (rateOfInterestL));

							interestOnArrearsAmount = (float) ((arrersAmount * interestRate) * netMonth);
							intersts.put("interestOnArrearsAmount",interestOnArrearsAmount);
							logger.info("#################################-->"+ interestOnArrearsAmount);
						}
					}

				} else {
					logger.info("------------------ First Month Bill -----------------------");
				}
			}
		}
		return intersts;
	}
    
}
