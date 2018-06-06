/*package com.bcits.bfm.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.ElectricitySubLedgerEntity;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.commonAreaMaintenance.CamLedgerService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.InterestSettingService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Controller
public class CAMBillsControllerBackup07092017 {
	
	static Logger logger = LoggerFactory.getLogger(CAMBillsControllerBackup07092017.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
    ElectricityBillParameterService billParameterService; 
	
	@Autowired
	ElectricityBillsService electricityBillsService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private CamConsolidationService camConsolidationService;
	
	@Autowired
	private ElectricityLedgerService electricityLedgerService;
	
	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;
	
	@Autowired
	ElectricityBillLineItemService electricityBillLineItemService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private InterestSettingService interestSettingService;
	
	@Autowired
	private BillingParameterMasterService parameterMasterService;
	
	@Autowired
	private CamLedgerService camLedgerService;
	
	@Autowired
	private BillController billController;
	
	@Autowired
	TariffCalculationService tariffCalculationService;

	@RequestMapping(value = "/camBills/generateCAMBills", method = { RequestMethod.GET, RequestMethod.POST })
   	public @ResponseBody Object generateCAMBills(HttpServletRequest request) throws ParseException
   	{
		System.out.println("CAM bill Methodddddddddddddddddddddddd..........");
		String configName = "CAM Charges";
		String status = "Active";
		String camSetting = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("camSetting ==================== "+camSetting);
		
		logger.info("In Generate Cam Bills Method");
		logger.info("Account id"+request.getParameter("accountId"));
		logger.info("previousCamDate======"+request.getParameter("previousCamDate"));
		logger.info("camBillDate======"+request.getParameter("camBillDate"));
		logger.info("propertyId========"+request.getParameter("propertyId"));
		logger.info("flat type========"+request.getParameter("flatsType"));
		Date previousCamDate=null;
		if(request.getParameter("flatsType").equals("Specific")) {
			synchronized (this) {
				{

					String typeOfService = "CAM";

					String allPropertyId = request.getParameter("propertyId");
					String[] propertyId = allPropertyId.split(",");
					for (int j = 0; j < propertyId.length; j++) {
						int propid = Integer.parseInt(propertyId[j]);
						Integer accountId = accountService
								.findAccountNumberBasedOnId(propid);
						logger.info("========================accountMumber"
								+ accountId);
						String previousBillstatus = tariffCalculationService.getCamPreviousBillstatusSpecific(accountId);
						if (!previousBillstatus.equalsIgnoreCase("Generated") && !previousBillstatus.equalsIgnoreCase("Approved")) {
							List<ElectricityBillEntity> billentity = electricityBillsService.findBasedOnAccountId(accountId,typeOfService);
							int size = billentity.size();
							java.sql.Date fromDate = null;
							if (size == 0) 
							{
								logger.info("*********************Size="+size+" > IF block*******************"); 
								int serviceMasterId = camConsolidationService.getServiceMasterObj(accountId, "CAM");

								if (serviceMasterId == 0) 
								{
									logger.info("*********************serviceMasterId="+serviceMasterId+" IF block*******************"); 
								} 
								else 
								{
									logger.info("*********************serviceMasterId="+serviceMasterId+" else block*******************"); 
										Date serviceStartDate = serviceMasterService.find(serviceMasterId).getServiceStartDate();
										Date xyz = new SimpleDateFormat("dd/MM/yyyy").parse("01/08/2017");//1st August 2017
									
										if(serviceStartDate.compareTo(xyz)<0)
										{ 
											fromDate = new java.sql.Date(xyz.getTime());
											logger.info("*********************fromDate="+fromDate+" IF block*******************"); 
										}
										else
										{
											fromDate = serviceMasterService.find(serviceMasterId).getServiceStartDate();
											logger.info("*********************fromDate="+fromDate+" else block*******************"); 
										}
								}
							} 
							else 
							{
								logger.info("*********************Size="+size+" > Else block*******************"); 
								for (ElectricityBillEntity electricityBillEntity : billentity) 
								{
									logger.info(":::::::::::"+ electricityBillEntity.getBillNo()+ electricityBillEntity.getBillDate());
									fromDate = electricityBillEntity.getBillDate();
									logger.info("*********************fromDate="+fromDate+" else(size!=0) block*******************"); 

								}
							}
							Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("camBillDate").trim());
							java.sql.Date toDate = new java.sql.Date(currentBillDate.getTime());
							if (fromDate != null) 
							{
								int noOfDays = getNoDaysDiffForValidation(fromDate, toDate);
								if (noOfDays > 1) 
								{

									Account account = accountService
											.find(accountId);
									ElectricityBillEntity billEntity = new ElectricityBillEntity();

									billEntity.setAccountId(accountId);
									billEntity.setTypeOfService("CAM");
									billEntity.setBillAmount(0.0);
									billEntity
											.setElBillDate(new java.sql.Timestamp(
													currentBillDate.getTime()));
									//billEntity.setPostType("Bill");

									String configName1 = "Due Days";
									String status1 = "Active";
									String dueDays = electricityBillsService
											.getBillingConfigValue(configName1,
													status1);
									String camBillGenerationtype = getBillinConfigValue(
											"CAM Bill Generation", status1);
									int days = 0;
									if (dueDays != null) {
										days = Integer.parseInt(dueDays);
									}
									Date billDueDate = null;
									previousCamDate = fromDate;
									if (camBillGenerationtype
											.equalsIgnoreCase("Pre Bill Generation")) {

										billEntity
												.setBillDate(new java.sql.Date(
														currentBillDate
																.getTime()));
										billDueDate = addDays(previousCamDate,
												days);
										logger.info("Current Bill Settings ::::::::"
												+ billDueDate);
										billEntity.setPostType("Pre Bill");
									} else {
										billDueDate = addDays(currentBillDate,
												days);
										logger.info("Current Bill Settings ::else::::::"
												+ billDueDate);
										billEntity
												.setBillDate(new java.sql.Date(
														currentBillDate
																.getTime()));
										billEntity.setPostType("Post Bill");
									}

									logger.info("Current Bill Settings :::outside:::::"
											+ billDueDate);
									billEntity
											.setBillDueDate(new java.sql.Date(
													billDueDate.getTime()));
									billEntity.setBillMonth(new java.sql.Date(
											currentBillDate.getTime()));
									billEntity.setStatus("Generated");
									billEntity.setBillType("Normal");
									billEntity
											.setCreatedBy((String) SessionData
													.getUserDetails().get(
															"userID"));
									billEntity
											.setLastUpdatedBy((String) SessionData
													.getUserDetails().get(
															"userID"));
									billEntity
											.setLastUpdatedDT(new java.sql.Timestamp(
													new Date().getTime()));
									billEntity.setBillNo("BILL"
											+ billParameterService
													.getSequencyNumber());
									billEntity.setAvgAmount(0.0);
									billEntity.setFromDate(fromDate);
									billEntity
											.setArrearsAmount(getLastBillArrearsAmount(
													"ARREARS", accountId, "CAM"));
									double netAmount=(billEntity.getBillAmount())+ (billEntity.getArrearsAmount())- (billEntity.getAdvanceClearedAmount());
									billEntity.setNetAmount(netAmount);
									storeArrears("ARREARS", billEntity);
									
									billEntity.setLatePaymentAmount(0.0);

									electricityBillsService.save(billEntity);

									int totalSqft = camConsolidationService
											.getAreaOfProperty(account
													.getPropertyId());
									int noOfParkingSlots = camConsolidationService
											.getNoOfParkingSlots(account
													.getPropertyId());

									if (camSetting.equals("Flat")) {
										saveBillLineItemsCodeFlat(billEntity,
												totalSqft, noOfParkingSlots);
									} else if (camSetting.equals("PSF")) {
										saveBillLineItemsCodePSF(billEntity,
												totalSqft, noOfParkingSlots);
									}
									Locale locale = null;
									billController.getBillDoc(billEntity.getElBillId(), locale);

								}
							}
							else
							{
								logger.info("Bill for Account Id:"+accountId +"has not been posted");
							}
						}

					}

					return null;
				}
			}
		}
				
    	if(request.getParameter("flatsType").equals("All")){
    		
    		List<Integer> accountIdList = camConsolidationService.findAllAccountsOfCamService();
    		for (Integer accountId : accountIdList) {
    			
    			
    			int lastBillId = camConsolidationService.getLastBillObj(accountId,"CAM","Bill");
    			Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("camBillDate").trim());
    			
    			java.sql.Date fromDate;
    			java.sql.Date toDate = new java.sql.Date(currentBillDate.getTime());
        		if(lastBillId!=0){
        			fromDate = electricityBillsService.find(lastBillId).getBillDate();
        		}else{
        			int serviceMasterId = camConsolidationService.getServiceMasterObj(accountId,"CAM");
        			//fromDate = serviceMasterService.find(serviceMasterId).getServiceStartDate();

					Date serviceStartDate = serviceMasterService.find(serviceMasterId).getServiceStartDate();
					Date xyz = new SimpleDateFormat("dd/MM/yyyy").parse("01/08/2017");//1st August 2017
				
					if(serviceStartDate.compareTo(xyz)<0)
					{ 
						fromDate = new java.sql.Date(xyz.getTime());
					}
					else
					{
						fromDate = serviceMasterService.find(serviceMasterId).getServiceStartDate();
					}
				

			
        		}
    			
        		int noOfDays = getNoDaysDiffForValidation(fromDate,toDate);
        		
        		if(noOfDays>=1){
        			Account account = accountService.find(accountId);
        			ElectricityBillEntity billEntity = new ElectricityBillEntity();
                	
                	billEntity.setAccountId(accountId);
                	billEntity.setTypeOfService("CAM");
                	billEntity.setBillAmount(0.0);
            		billEntity.setElBillDate(new java.sql.Timestamp(currentBillDate.getTime()));
            		//billEntity.setPostType("Bill");
            	
            		String configName1 = "Due Days";
            		String status1 = "Active";
            		String dueDays = electricityBillsService.getBillingConfigValue(configName1,status1); 
            		String camBillGenerationtype=getBillinConfigValue("CAM Bill Generation", status1);
            		int days=0;
            		if(dueDays!=null)
            		{
            			days = Integer.parseInt(dueDays);
            		}
            		Date billDueDate = null;
            		previousCamDate=fromDate;
    				if(camBillGenerationtype.equalsIgnoreCase("Pre Bill Generation")){
    					
    					billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
    				billDueDate=addDays(previousCamDate, days);	
    				logger.info("Current Bill Settings ::::::::"+billDueDate);
    				billEntity.setPostType("Pre Bill");
    				}else{
    					billDueDate=addDays(currentBillDate, days);
    					logger.info("Current Bill Settings ::else::::::"+billDueDate);
    					billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
    					billEntity.setPostType("Post Bill");
    				}
    				
    				logger.info("Current Bill Settings :::outside:::::"+billDueDate);
    		        billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
            		billEntity.setBillMonth(new java.sql.Date(currentBillDate.getTime()));
            		billEntity.setStatus("Generated");
            		billEntity.setBillType("Normal");
            		billEntity.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
            		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
            		billEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
            		billEntity.setBillNo("BILL"+billParameterService.getSequencyNumber());
            		billEntity.setAvgAmount(0.0);
            		billEntity.setFromDate(fromDate);
            		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS", accountId, "CAM"));
            		billEntity.setNetAmount((billEntity.getBillAmount())+(billEntity.getArrearsAmount())-(billEntity.getAdvanceClearedAmount()));
            		storeArrears("ARREARS",billEntity);
            		
            		electricityBillsService.save(billEntity);
            		
            		int totalSqft = camConsolidationService.getAreaOfProperty(account.getPropertyId());
            		int noOfParkingSlots = camConsolidationService.getNoOfParkingSlots(account.getPropertyId());
            		
            		if(camSetting.equals("Flat")){
            			saveBillLineItemsCodeFlat(billEntity,totalSqft,noOfParkingSlots);
            		} else if(camSetting.equals("PSF")){
            			saveBillLineItemsCodePSF(billEntity,totalSqft,noOfParkingSlots);
            		}
            		Locale locale = null;
            		billController.getBillDoc(billEntity.getElBillId(),locale);
        		}
    			
    		}
    		
    	}
    	else if(request.getParameter("flatsType").equals("Specific")){
    	previousCamDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("camBillDate").trim());
    		int accountId = Integer.parseInt(request.getParameter("accountId"));
    		int lastBillId = camConsolidationService.getLastBillObj(accountId,"CAM","Bill");
			Date currentBillDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("camBillDate").trim());
			java.sql.Date fromDate;
			java.sql.Date toDate = new java.sql.Date(currentBillDate.getTime());
    		if(lastBillId!=0){
    			fromDate = electricityBillsService.find(lastBillId).getBillDate();
    		}else{
    			int serviceMasterId = camConsolidationService.getServiceMasterObj(accountId,"CAM");
    			fromDate = serviceMasterService.find(serviceMasterId).getServiceStartDate();
    		}
			
    		int noOfDays = getNoDaysDiffForValidation(fromDate,toDate);
    		if(noOfDays>=1){
    			ElectricityBillEntity billEntity = new ElectricityBillEntity();
            	
            	billEntity.setAccountId(accountId);
            	billEntity.setTypeOfService("CAM");
            	billEntity.setBillAmount(0.0);
        		billEntity.setElBillDate(new java.sql.Timestamp(currentBillDate.getTime()));
        		
        		
        		String configName1 = "Due Days";
        		String status1 = "Active";
        		String dueDays = electricityBillsService.getBillingConfigValue(configName1,status1); 
        		String camBillGenerationtype=getBillinConfigValue("CAM Bill Generation", status1);        		
        		logger.info("camBillGenerationtype::::::::::::::"+camBillGenerationtype);
        		int days=0;
        		if(dueDays!=null)
        		{
        			days = Integer.parseInt(dueDays);
        		}
        		Date billDueDate = null;
        				
        				if(camBillGenerationtype.equalsIgnoreCase("Pre Bill Generation")){
        					
        				
        				billDueDate=addDays(previousCamDate, days);	
        				billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
        				billEntity.setPostType("Pre Bill");
        				logger.info("Current Bill Settings ::::::::"+billDueDate);	
        				}else{
        					billDueDate=addDays(currentBillDate, days);
        					billEntity.setBillDate(new java.sql.Date(currentBillDate.getTime()));
        					logger.info("Current Bill Settings ::else::::::"+billDueDate);
        					billEntity.setPostType("Post Bill");
        				}
        				
        				logger.info("Current Bill Settings :::outside:::::"+billDueDate);
        		billEntity.setBillDueDate(new java.sql.Date(billDueDate.getTime()));
        		billEntity.setBillMonth(new java.sql.Date(currentBillDate.getTime()));
        		billEntity.setStatus("Generated");
        		billEntity.setBillType("Normal");
        		billEntity.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
        		billEntity.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
        		billEntity.setLastUpdatedDT(new java.sql.Timestamp(new Date().getTime()));
        		billEntity.setBillNo("BILL"+billParameterService.getSequencyNumber());
        		billEntity.setAvgAmount(0.0);
        		billEntity.setFromDate(fromDate);
        		billEntity.setArrearsAmount(getLastBillArrearsAmount("ARREARS", accountId, "CAM"));
        		billEntity.setNetAmount((billEntity.getBillAmount())+(billEntity.getArrearsAmount())-(billEntity.getAdvanceClearedAmount()));
        		storeArrears("ARREARS",billEntity);
        		
        		electricityBillsService.save(billEntity);
        		
        		int totalSqft = camConsolidationService.getAreaOfProperty(Integer.parseInt(request.getParameter("propertyId")));
        		int noOfParkingSlots = camConsolidationService.getNoOfParkingSlots(Integer.parseInt(request.getParameter("propertyId")));
        		
        		if(camSetting.equals("Flat")){
        			saveBillLineItemsCodeFlat(billEntity,totalSqft,noOfParkingSlots);
        		} else if(camSetting.equals("PSF")){
        			saveBillLineItemsCodePSF(billEntity,totalSqft,noOfParkingSlots);
        		}
        		
        		Locale locale = null;
        		billController.getBillDoc(billEntity.getElBillId(),locale);
    		}
    	}  		
    	
   		return null;
   	}
	
	public void saveBillLineItemsCodeFlat(ElectricityBillEntity billEntity,int totalSqft,int noOfParkingSlots){
		
		List<TransactionMasterEntity> transactionMasterList = electricitySubLedgerService.getTransactionMasterForCam("CAM");
				
		double serviceTaxAmount = 0.0;
		double camServiceTaxRate = 0.0;
		double ecamServiceTaxRate = 0.0;
		double shecamServiceTaxRate = 0.0;
		double camSWBCessRate = 0.0;
		double camInterestRate = 0.0;
		double camKrishikalyanTax=0.0;
		List<ElectricityBillLineItemEntity> batchLineItemList = new ArrayList<ElectricityBillLineItemEntity>();
		List<ElectricityBillParametersEntity> batchParamterList = new ArrayList<ElectricityBillParametersEntity>();
		for (TransactionMasterEntity masterEntity : transactionMasterList) {
			if(masterEntity.getTransactionCode().equals("CAM_SERVICE_TAX")){
				camServiceTaxRate = masterEntity.getCamRate();
			} else if(masterEntity.getTransactionCode().equals("CAM_ECESS")){
				ecamServiceTaxRate = masterEntity.getCamRate();
			} else if(masterEntity.getTransactionCode().equals("CAM_SHECESS")){
				shecamServiceTaxRate = masterEntity.getCamRate();
			} else if(masterEntity.getTransactionCode().equals("CAM_INTEREST")){
				camInterestRate = masterEntity.getCamRate();
			} else if(masterEntity.getTransactionCode().equals("CAM_SWB_CESS_TAX")){
				camSWBCessRate = masterEntity.getCamRate();
			}else if(masterEntity.getTransactionCode().equals("CAM_KRISHI_KALYAN_CESS_TAX")){
				camKrishikalyanTax = masterEntity.getCamRate();
				System.err.println("camKrishikalyanTax::::::::::"+camKrishikalyanTax);
			}else if(masterEntity.getTransactionCode().equals("CAM_PARKING_SLOT")){
				
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				billLineItemEntity.setTransactionCode(masterEntity.getTransactionCode());
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				double camAmount = masterEntity.getCamRate()*noOfParkingSlots*getNoMonthsDiff(billEntity);
				billLineItemEntity.setBalanceAmount(Math.round(camAmount*100.0)/100.0);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				batchLineItemList.add(billLineItemEntity);
				electricityBillLineItemService.save(billLineItemEntity);
				
			} else{
				
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				billLineItemEntity.setTransactionCode(masterEntity.getTransactionCode());
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				double camAmount = ((masterEntity.getCamRate()*getNoMonthsDiff(billEntity))/getTotalNoDaysGivenMonths(billEntity))*getNoDaysDiff(billEntity);
				billLineItemEntity.setBalanceAmount(Math.round(camAmount*100.0)/100.0);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				batchLineItemList.add(billLineItemEntity);
				electricityBillLineItemService.save(billLineItemEntity);
				
			}
			
			ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
			
			String transactionName = camConsolidationService.getTransactionNameBasedOnCode(masterEntity.getTransactionCode());
			billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
			billParametersEntity.setElBillParameterValue(""+masterEntity.getCamRate());
			billParametersEntity.setStatus("Active");
			billParametersEntity.setElectricityBillEntity(billEntity);
			batchParamterList.add(billParametersEntity);
			billParameterService.save(billParametersEntity);
			
		}
		//electricityBillLineItemService.batchSave(batchLineItemList);
		//billParameterService.batchSave(batchParamterList);
		
		ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
		
		billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("Total sqft")));
		billParametersEntity.setElBillParameterValue(""+totalSqft);
		billParametersEntity.setStatus("Active");
		billParametersEntity.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntity);
		
		ElectricityBillParametersEntity billParametersEntityNoOfParkingSlots = new ElectricityBillParametersEntity();
		
		billParametersEntityNoOfParkingSlots.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("No of parking slots")));
		billParametersEntityNoOfParkingSlots.setElBillParameterValue(""+noOfParkingSlots);
		billParametersEntityNoOfParkingSlots.setStatus("Active");
		billParametersEntityNoOfParkingSlots.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntityNoOfParkingSlots);
		
		ElectricityBillParametersEntity billParametersEntityNoOfDays = new ElectricityBillParametersEntity();
		
		billParametersEntityNoOfDays.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("No of days")));
		billParametersEntityNoOfDays.setElBillParameterValue(""+getNoDaysDiff(billEntity));
		billParametersEntityNoOfDays.setStatus("Active");
		billParametersEntityNoOfDays.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntityNoOfDays);
		
		ElectricityBillParametersEntity billParametersEntityBillBasis = new ElectricityBillParametersEntity();
		
		billParametersEntityBillBasis.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("Bill Basis")));
		String configName = "CAM Charges";
		String status = "Active";
		String camSetting = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("camSetting ==================== "+camSetting);
		billParametersEntityBillBasis.setElBillParameterValue(camSetting);
		billParametersEntityBillBasis.setStatus("Active");
		billParametersEntityBillBasis.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntityBillBasis);
		
		HashMap<Object, Object> consolidatedBill = ineterestCalculation(billEntity.getAccountId(),"CAM",billEntity.getFromDate(),billEntity.getBillDate(),camInterestRate);
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
		
		double totalLineItemAmount = camConsolidationService.getTotalBillLineItemAmount(billEntity.getElBillId());
		double totalValue = 0.0; 
		double camAmount = 0.0;
		double swbCess = 0;
		double kkCess = 0;
		double educationCess = 0;
		double shEducationCess = 0;
		
		logger.info("::::::::::totalLineItemAmount::::::::::::"+totalLineItemAmount);
		if(totalLineItemAmount >5000){
		
		if(camServiceTaxRate>0){
			ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
			
			billLineItemEntity.setTransactionCode("CAM_SERVICE_TAX");
			billLineItemEntity.setCreditAmount(0);
			billLineItemEntity.setDebitAmount(0);
			totalValue+=totalLineItemAmount;
			camAmount = totalLineItemAmount*(camServiceTaxRate/100);
			serviceTaxAmount = camAmount;
			totalValue+=camAmount;
			billLineItemEntity.setBalanceAmount(camAmount);
			billLineItemEntity.setElectricityBillEntity(billEntity);
			billLineItemEntity.setStatus("Approved");
			electricityBillLineItemService.save(billLineItemEntity); 
		}
		
		
		if(camSWBCessRate>0){
			ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
			
			billLineItemEntity.setTransactionCode("CAM_SWB_CESS_TAX");
			billLineItemEntity.setCreditAmount(0);
			billLineItemEntity.setDebitAmount(0);
			//totalValue+=totalLineItemAmount;
			camAmount = totalLineItemAmount*(camSWBCessRate/100);
			swbCess = camAmount;
			totalValue+=camAmount;
			billLineItemEntity.setBalanceAmount(camAmount);
			billLineItemEntity.setElectricityBillEntity(billEntity);
			billLineItemEntity.setStatus("Approved");
			electricityBillLineItemService.save(billLineItemEntity); 
		}
		if(camKrishikalyanTax>0){
			ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
			
			billLineItemEntity.setTransactionCode("CAM_KRISHI_KALYAN_CESS_TAX");
			billLineItemEntity.setCreditAmount(0);
			billLineItemEntity.setDebitAmount(0);
			//totalValue+=totalLineItemAmount;
			camAmount = totalLineItemAmount*(camKrishikalyanTax/100);
			
			System.err.println("camAmount"+camAmount);
			kkCess = totalLineItemAmount*(camKrishikalyanTax/100);
			totalValue+=camAmount;
			billLineItemEntity.setBalanceAmount(kkCess);
			billLineItemEntity.setElectricityBillEntity(billEntity);
			billLineItemEntity.setStatus("Approved");
			electricityBillLineItemService.save(billLineItemEntity); 
		}
		
		
		if(ecamServiceTaxRate>0){
			ElectricityBillLineItemEntity billLineItemEntityEducationCess = new ElectricityBillLineItemEntity();
			
			billLineItemEntityEducationCess.setTransactionCode("CAM_ECESS");
			billLineItemEntityEducationCess.setCreditAmount(0);
			billLineItemEntityEducationCess.setDebitAmount(0);
			educationCess = camAmount*(ecamServiceTaxRate/100);
			totalValue+=educationCess;
			billLineItemEntityEducationCess.setBalanceAmount(educationCess);
			billLineItemEntityEducationCess.setElectricityBillEntity(billEntity);
			billLineItemEntityEducationCess.setStatus("Approved");
			electricityBillLineItemService.save(billLineItemEntityEducationCess);
		}
		
		if(shecamServiceTaxRate>0){
			ElectricityBillLineItemEntity billLineItemEntityShEducationCess = new ElectricityBillLineItemEntity();
			
			billLineItemEntityShEducationCess.setTransactionCode("CAM_SHECESS");
			billLineItemEntityShEducationCess.setCreditAmount(0);
			billLineItemEntityShEducationCess.setDebitAmount(0);
			shEducationCess = camAmount*(shecamServiceTaxRate/100);
			totalValue+=shEducationCess;
			billLineItemEntityShEducationCess.setBalanceAmount(shEducationCess);
			billLineItemEntityShEducationCess.setElectricityBillEntity(billEntity);
			billLineItemEntityShEducationCess.setStatus("Approved");
			electricityBillLineItemService.save(billLineItemEntityShEducationCess); 
		}
		}else{
			logger.info("No Service Tax To be Calculated::::::::::");
		}
		//double test1 = Math.ceil(totalValue*100.0/100.0);
		double test2 = Math.round(totalValue);
		double roundOff = 0.0;
		try{
			roundOff = Math.round((test2-totalValue)*100.0)/100.0;
		}catch(Exception e){
			roundOff = 0.0;
		}
		double roundOffValue = 0.0;
		
		if(roundOff!=0.0){
			ElectricityBillLineItemEntity billLineItemEntityRoundOff = new ElectricityBillLineItemEntity();		
			billLineItemEntityRoundOff.setTransactionCode("CAM_ROF");
			billLineItemEntityRoundOff.setBalanceAmount(roundOff);
			billLineItemEntityRoundOff.setStatus("Active");
			roundOffValue=roundOff;
			billLineItemEntityRoundOff.setElectricityBillEntity(billEntity);		
			electricityBillLineItemService.save(billLineItemEntityRoundOff); 
		}
		
		billEntity.setBillAmount(totalLineItemAmount+serviceTaxAmount+roundOffValue+educationCess+shEducationCess+swbCess+kkCess);
		billEntity.setNetAmount(billEntity.getArrearsAmount()+billEntity.getBillAmount());
		
		electricityBillsService.update(billEntity);
		
	}
	
	public void saveBillLineItemsCodePSF(ElectricityBillEntity billEntity,int totalSqft,int noOfParkingSlots){
		//Code for calculating with GST
		List<TransactionMasterEntity> transactionMasterList = electricitySubLedgerService.getTransactionMasterForCam("CAM");
		
		double serviceTaxAmount = 0.0;
		double camServiceTaxRate = 0.0;
		double ecamServiceTaxRate = 0.0;
		double shecamServiceTaxRate = 0.0;
		double camSWBCessRate = 0.0;
		double camInterestRate = 0.0;
		double camKrishikalyanTax = 0.0;
		double kkCess = 0;
//***************************************************************** GST ***********************************************************************
		double sgstRate = 0.0;
		double cgstRate = 0.0;
		double cgstgstLate = 0.0;
		double sgstgstLate = 0.0;
		double gstlatePayment=0.0;
//*********************************************************************************************************************************************
		List<ElectricityBillLineItemEntity> batchLineItemList = new ArrayList<ElectricityBillLineItemEntity>();
		List<ElectricityBillParametersEntity> batchParamterList = new ArrayList<ElectricityBillParametersEntity>();
		for (TransactionMasterEntity masterEntity : transactionMasterList) 
		{
			System.out.println("***********************************************"+masterEntity.getTransactionCode()+"***********************************************");
		    if(masterEntity.getTransactionCode().equals("CAM_SERVICE_TAX")){
				camServiceTaxRate = masterEntity.getCamRate();
			} else if(masterEntity.getTransactionCode().equals("CAM_ECESS")){
				ecamServiceTaxRate = masterEntity.getCamRate();
			} else if(masterEntity.getTransactionCode().equals("CAM_SHECESS")){
				shecamServiceTaxRate = masterEntity.getCamRate();
			}  else if(masterEntity.getTransactionCode().equals("CAM_SWB_CESS_TAX")){
				camSWBCessRate = masterEntity.getCamRate();
			}else if(masterEntity.getTransactionCode().equals("CAM_KRISHI_KALYAN_CESS_TAX")){
				camKrishikalyanTax = masterEntity.getCamRate();
			}else  
//***************************************************************** GST ***********************************************************************	 
			if(masterEntity.getTransactionCode().equals("CAM_INTEREST"))
			{
					camInterestRate = masterEntity.getCamRate();
			}
			else if(masterEntity.getTransactionCode().equals("CAM_SGST_TAX"))
			{
				 sgstRate = masterEntity.getCamRate();  
			}
			else if(masterEntity.getTransactionCode().equals("CAM_CGST_TAX"))
			{
				cgstRate = masterEntity.getCamRate();   
			}
			else if(masterEntity.getTransactionCode().equals("CAM_CGST_Late_Pay"))
			{
				cgstgstLate = masterEntity.getCamRate();   
			}
			else if(masterEntity.getTransactionCode().equals("CAM_SGST_Late_Pay"))
			{
				sgstgstLate = masterEntity.getCamRate();   
			}
//*********************************************************************************************************************************************
		else if(masterEntity.getTransactionCode().equals("CAM_PARKING_SLOT")){
				
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				billLineItemEntity.setTransactionCode(masterEntity.getTransactionCode());
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				double camAmount = masterEntity.getCamRate()*noOfParkingSlots*getNoMonthsDiff(billEntity);
				billLineItemEntity.setBalanceAmount(Math.round(camAmount*100.0)/100.0);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				batchLineItemList.add(billLineItemEntity);
				electricityBillLineItemService.save(billLineItemEntity);
				
			} 
			else
			{
				  System.out.println("****************************************"+masterEntity.getTransactionCode());
				  ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				  billLineItemEntity.setTransactionCode(masterEntity.getTransactionCode());
				  billLineItemEntity.setCreditAmount(0);
				  billLineItemEntity.setDebitAmount(0);
				  //double camAmount = ((masterEntity.getCamRate()*getNoMonthsDiff(billEntity))/getTotalNoDaysGivenMonths(billEntity))*getNoDaysDiff(billEntity)*totalSqft;
				  
				  double camAmount = ((masterEntity.getCamRate()/365)*12)*getNoDaysDiff(billEntity)*totalSqft;
				  
				  logger.info("Math.round(camAmount*100.0)/100.0 "+Math.round(camAmount*100.0)/100.0);
				  billLineItemEntity.setBalanceAmount(Math.round(camAmount*100.0)/100.0);
				  billLineItemEntity.setElectricityBillEntity(billEntity);
				  billLineItemEntity.setStatus("Approved");
				  batchLineItemList.add(billLineItemEntity);
				  electricityBillLineItemService.save(billLineItemEntity);			
			}

				ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
			
				String transactionName = camConsolidationService.getTransactionNameBasedOnCode(masterEntity.getTransactionCode());
				System.out.println("********************************** " + transactionName + " ***************************************"); 
				billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter(transactionName.trim())));
				billParametersEntity.setElBillParameterValue(""+masterEntity.getCamRate());
				billParametersEntity.setStatus("Active");
				billParametersEntity.setElectricityBillEntity(billEntity);
				batchParamterList.add(billParametersEntity);
				billParameterService.save(billParametersEntity);
			
			
		}
		//electricityBillLineItemService.batchSave(batchLineItemList);
		//billParameterService.batchSave(batchParamterList);
		
		ElectricityBillParametersEntity billParametersEntity = new ElectricityBillParametersEntity();
		
		billParametersEntity.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("Total sqft")));
		billParametersEntity.setElBillParameterValue(""+totalSqft);
		billParametersEntity.setStatus("Active");
		billParametersEntity.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntity);
		
		ElectricityBillParametersEntity billParametersEntityNoOfParkingSlots = new ElectricityBillParametersEntity();
		
		billParametersEntityNoOfParkingSlots.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("No of parking slots")));
		billParametersEntityNoOfParkingSlots.setElBillParameterValue(""+noOfParkingSlots);
		billParametersEntityNoOfParkingSlots.setStatus("Active");
		billParametersEntityNoOfParkingSlots.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntityNoOfParkingSlots);
		
		ElectricityBillParametersEntity billParametersEntityNoOfDays = new ElectricityBillParametersEntity();
		
		billParametersEntityNoOfDays.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("No of days")));
		billParametersEntityNoOfDays.setElBillParameterValue(""+getNoDaysDiff(billEntity));
		billParametersEntityNoOfDays.setStatus("Active");
		billParametersEntityNoOfDays.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntityNoOfDays);
		
		ElectricityBillParametersEntity billParametersEntityBillBasis = new ElectricityBillParametersEntity();
		
		billParametersEntityBillBasis.setBillParameterMasterEntity(parameterMasterService.find(billParameterService.getCamParameter("Bill Basis")));
		String configName = "CAM Charges";
		String status = "Active";
		String camSetting = electricityBillsService.getBillingConfigValue(configName,status);
		logger.info("camSetting ==================== "+camSetting);
		billParametersEntityBillBasis.setElBillParameterValue(camSetting);
		billParametersEntityBillBasis.setStatus("Active");
		billParametersEntityBillBasis.setElectricityBillEntity(billEntity);
		billParameterService.save(billParametersEntityBillBasis);
		System.err.println(billEntity.getAccountId() +" "+billEntity.getFromDate() +" "+ billEntity.getBillDate()+" "+ camInterestRate);
		HashMap<Object, Object> consolidatedBill = ineterestCalculation(billEntity.getAccountId(),"CAM",billEntity.getFromDate(),billEntity.getBillDate(),camInterestRate);
		float interestOnArrearsAmount = 0.0f;
		
		Double previousbalance=getpreviouslatepaymentAmount(billEntity.getAccountId(),"CAM");
		interestOnArrearsAmount=previousbalance.floatValue();
		for (Entry<Object, Object> map : consolidatedBill.entrySet()) {
			if (map.getKey().equals("interestOnArrearsAmount")) {
				interestOnArrearsAmount =  (float)map.getValue();
			}
		}
		System.out.println("*************************** interestOnArrearsAmount= "+interestOnArrearsAmount+" *************************");
		if(interestOnArrearsAmount>0)
		{
			ElectricityBillLineItemEntity billLineItemEntityInterest = new ElectricityBillLineItemEntity();
			billLineItemEntityInterest.setTransactionCode("CAM_INTEREST");
			billLineItemEntityInterest.setBalanceAmount(interestOnArrearsAmount);
			
			System.err.println(":::::interestOnArrearsAmount::::::'"+interestOnArrearsAmount);
			billLineItemEntityInterest.setStatus("Active");
			billLineItemEntityInterest.setElectricityBillEntity(billEntity);
			
			electricityBillLineItemService.save(billLineItemEntityInterest);
		}
		
		double totalLineItemAmount = camConsolidationService.getTotalBillLineItemAmount(billEntity.getElBillId());
		System.err.println("totalLineItemAmount camConsolidationService"+totalLineItemAmount);
		double totalValue = 0.0; 
		double camAmount = 0.0;
		double swbCess = 0;
		double educationCess = 0;
		double shEducationCess = 0;	
//********************************************************* GST *******************************************************		
		double cgstCess = 0;
		double sgstCess = 0;
		double lateGSTCess = 0;
//************************************************************************************************************************
		String configName1 = "CAM Service Tax Amount";
		String status1 = "Active";
		String serviceTaxExeAmount = electricityBillsService.getBillingConfigValue(configName1,status1); 
		double serviceTaxExe=Double.valueOf(serviceTaxExeAmount);
		logger.info("serviceTaxExe::::::::::"+serviceTaxExe);
		
		double checkAmount=0;
		double bankcharge=0;
		double previouslatepaymentamount=0;
		float totalbankcharge=0;
		Float latePaymentAnount=0f;
		
		List<?> checkbouncedetail=tariffCalculationService.getcheckbounceDetail(billEntity.getAccountId());	
        for (Iterator iterator = checkbouncedetail.iterator(); iterator.hasNext();) {
			Object values[] = (Object[]) iterator.next();
			if (((String) values[4]).equalsIgnoreCase("CAM Ledger")) {
				checkAmount = checkAmount+(Double) values[1];
				bankcharge = bankcharge+(Double) values[2];
				previouslatepaymentamount = previouslatepaymentamount+(Double) values[3];
				latePaymentAnount = (float) (latePaymentAnount + previouslatepaymentamount);
				totalbankcharge = (float) (checkAmount + bankcharge+latePaymentAnount);
				logger.info("checkAmount:::::::::" + checkAmount
						+ ":::::::::cbankcharge" + bankcharge
						+ "previouslatepaymentamount"
				
						+ previouslatepaymentamount);
			}
            }
				if(latePaymentAnount>0){
				ElectricityBillLineItemEntity latePaymentCharges = new ElectricityBillLineItemEntity();
				
				latePaymentCharges.setBalanceAmount(latePaymentAnount);
				latePaymentCharges.setTransactionCode("CAM_INTEREST");
				latePaymentCharges.setCreditAmount(0);
				latePaymentCharges.setDebitAmount(0);
				latePaymentCharges.setElectricityBillEntity(billEntity);
				latePaymentCharges.setStatus("Not Approved");
				latePaymentCharges.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				latePaymentCharges.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				latePaymentCharges.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				electricityBillLineItemService.save(latePaymentCharges);
				
				}
				//billLineItemEntities.add(latePaymentCharges);
				if(totalbankcharge>0){
				ElectricityBillLineItemEntity checkbounceAmount = new ElectricityBillLineItemEntity();
				checkbounceAmount.setBalanceAmount(totalbankcharge);
				checkbounceAmount.setTransactionCode("EL_CP");
				checkbounceAmount.setCreditAmount(0);
				checkbounceAmount.setDebitAmount(0);
				checkbounceAmount.setElectricityBillEntity(billEntity);
				checkbounceAmount.setStatus("Not Approved");
				checkbounceAmount.setCreatedBy((String) SessionData
						.getUserDetails().get("userID"));
				checkbounceAmount.setLastUpdatedBy((String) SessionData
						.getUserDetails().get("userID"));
				checkbounceAmount.setLastUpdatedDT(new java.sql.Timestamp(
						new Date().getTime()));
				//billLineItemEntities.add(checkbounceAmount);
				
				electricityBillLineItemService.save(checkbounceAmount);
			
				}
		
		
        
        
		if (totalLineItemAmount > serviceTaxExe) {
			if (camServiceTaxRate > 0) {
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();

				billLineItemEntity.setTransactionCode("CAM_SERVICE_TAX");
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				totalValue += totalLineItemAmount;
				camAmount = totalLineItemAmount * (camServiceTaxRate / 100);
				System.err.println("serviceTaxAmount "+camAmount);
				serviceTaxAmount = camAmount;
				totalValue += camAmount;
				billLineItemEntity.setBalanceAmount(camAmount);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				electricityBillLineItemService.save(billLineItemEntity);
			}
			if (camSWBCessRate > 0) {
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();

				billLineItemEntity.setTransactionCode("CAM_SWB_CESS_TAX");
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				//totalValue+=totalLineItemAmount;
				camAmount = totalLineItemAmount * (camSWBCessRate / 100);
				swbCess = camAmount;
				totalValue += camAmount;
				billLineItemEntity.setBalanceAmount(camAmount);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				electricityBillLineItemService.save(billLineItemEntity);
			}
			if(camKrishikalyanTax>0){
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				billLineItemEntity.setTransactionCode("CAM_KRISHI_KALYAN_CESS_TAX");
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				//totalValue+=totalLineItemAmount;
				camAmount = totalLineItemAmount*(camKrishikalyanTax/100);
				
				kkCess = totalLineItemAmount*(camKrishikalyanTax/100);
				totalValue+=camAmount;
				billLineItemEntity.setBalanceAmount(kkCess);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				electricityBillLineItemService.save(billLineItemEntity); 
			}
			
			if (ecamServiceTaxRate > 0) {
				ElectricityBillLineItemEntity billLineItemEntityEducationCess = new ElectricityBillLineItemEntity();

				billLineItemEntityEducationCess.setTransactionCode("CAM_ECESS");
				billLineItemEntityEducationCess.setCreditAmount(0);
				billLineItemEntityEducationCess.setDebitAmount(0);
				educationCess = camAmount * (ecamServiceTaxRate / 100);
				totalValue += educationCess;
				billLineItemEntityEducationCess.setBalanceAmount(educationCess);
				billLineItemEntityEducationCess
						.setElectricityBillEntity(billEntity);
				billLineItemEntityEducationCess.setStatus("Approved");
				electricityBillLineItemService
						.save(billLineItemEntityEducationCess);
			}
			if (shecamServiceTaxRate > 0) {
				ElectricityBillLineItemEntity billLineItemEntityShEducationCess = new ElectricityBillLineItemEntity();

				billLineItemEntityShEducationCess
						.setTransactionCode("CAM_SHECESS");
				billLineItemEntityShEducationCess.setCreditAmount(0);
				billLineItemEntityShEducationCess.setDebitAmount(0);
				shEducationCess = camAmount * (shecamServiceTaxRate / 100);
				totalValue += shEducationCess;
				billLineItemEntityShEducationCess
						.setBalanceAmount(shEducationCess);
				billLineItemEntityShEducationCess
						.setElectricityBillEntity(billEntity);
				billLineItemEntityShEducationCess.setStatus("Approved");
				electricityBillLineItemService
						.save(billLineItemEntityShEducationCess);
			}   
//***************************************************************** GST ***********************************************************************
		
			if(cgstRate>0){
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				billLineItemEntity.setTransactionCode("CAM_CGST_TAX");
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				//totalValue+=totalLineItemAmount;
				camAmount = totalLineItemAmount*(cgstRate/100);
				
				cgstCess = camAmount;
				totalValue+=camAmount;
				billLineItemEntity.setBalanceAmount(cgstCess);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				electricityBillLineItemService.save(billLineItemEntity); 
			}
			if(sgstRate>0){
				ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				billLineItemEntity.setTransactionCode("CAM_SGST_TAX");
				billLineItemEntity.setCreditAmount(0);
				billLineItemEntity.setDebitAmount(0);
				//totalValue+=totalLineItemAmount;
				camAmount = totalLineItemAmount*(cgstRate/100);
				
				sgstCess = camAmount;
				totalValue+=camAmount;
				billLineItemEntity.setBalanceAmount(sgstCess);
				billLineItemEntity.setElectricityBillEntity(billEntity);
				billLineItemEntity.setStatus("Approved");
				electricityBillLineItemService.save(billLineItemEntity); 
			}
			System.out.println("****************************************"+sgstgstLate+cgstgstLate);
			if(sgstgstLate>0)
			{    
				if(interestOnArrearsAmount>0)
				{
				  ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				  billLineItemEntity.setTransactionCode("CAM_SGST_Late_Pay");
				  billLineItemEntity.setCreditAmount(0);
				  billLineItemEntity.setDebitAmount(0);
				  //totalValue+=totalLineItemAmount;
				  camAmount = interestOnArrearsAmount*(sgstgstLate/100);
				
				  lateGSTCess = camAmount;
				  gstlatePayment+=camAmount;
				  totalValue+=camAmount;
				  billLineItemEntity.setBalanceAmount(lateGSTCess);
				  billLineItemEntity.setElectricityBillEntity(billEntity);
				  billLineItemEntity.setStatus("Approved");
				  electricityBillLineItemService.save(billLineItemEntity); 
				}
				else {
				  ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				  billLineItemEntity.setTransactionCode("CAM_SGST_Late_Pay");
				  billLineItemEntity.setCreditAmount(0);
				  billLineItemEntity.setDebitAmount(0);
				  //totalValue+=totalLineItemAmount;
				  //camAmount = interestOnArrearsAmount*(gstLate/100);
				
				  lateGSTCess = 0.0;
				  //totalValue+=camAmount;
				  //gstlatePayment+=camAmount;
				  billLineItemEntity.setBalanceAmount(lateGSTCess);
				  billLineItemEntity.setElectricityBillEntity(billEntity);
				  billLineItemEntity.setStatus("Approved");
				  electricityBillLineItemService.save(billLineItemEntity); 
				}
			}
			if(cgstgstLate>0)
			{    
				if(interestOnArrearsAmount>0)
				{
				  ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				  billLineItemEntity.setTransactionCode("CAM_CGST_Late_Pay");
				  billLineItemEntity.setCreditAmount(0);
				  billLineItemEntity.setDebitAmount(0);
				  //totalValue+=totalLineItemAmount;
				  camAmount = interestOnArrearsAmount*(cgstgstLate/100);
				
				  lateGSTCess = camAmount;
				  gstlatePayment+=camAmount;
				  totalValue+=camAmount;
				  billLineItemEntity.setBalanceAmount(lateGSTCess);
				  billLineItemEntity.setElectricityBillEntity(billEntity);
				  billLineItemEntity.setStatus("Approved");
				  electricityBillLineItemService.save(billLineItemEntity); 
				}
				else {
				  ElectricityBillLineItemEntity billLineItemEntity = new ElectricityBillLineItemEntity();
				
				  billLineItemEntity.setTransactionCode("CAM_CGST_Late_Pay");
				  billLineItemEntity.setCreditAmount(0);
				  billLineItemEntity.setDebitAmount(0);
				  //totalValue+=totalLineItemAmount;
				  //camAmount = interestOnArrearsAmount*(gstLate/100);
				
				  lateGSTCess = 0.0;
				  //totalValue+=camAmount;
				  //gstlatePayment+=camAmount;
				  billLineItemEntity.setBalanceAmount(lateGSTCess);
				  billLineItemEntity.setElectricityBillEntity(billEntity);
				  billLineItemEntity.setStatus("Approved");
				  electricityBillLineItemService.save(billLineItemEntity); 
				}
			}
			
//*************************************************************************************************************************************		
		}else{
			logger.info("No service Tax TO be Calculated");
		}
		//double test1 = Math.ceil(totalValue*100.0/100.0);
		double test2 = Math.round(totalValue);
		double roundOff = 0.0;
		try{
			roundOff = Math.round((test2-totalValue)*100.0)/100.0;
		}catch(Exception e){
			roundOff = 0.0;
		}
		double roundOffValue = 0.0;
				
		if(roundOff!=0.0){
			ElectricityBillLineItemEntity billLineItemEntityRoundOff = new ElectricityBillLineItemEntity();		
			billLineItemEntityRoundOff.setTransactionCode("CAM_ROF");
			billLineItemEntityRoundOff.setBalanceAmount(roundOff);
			billLineItemEntityRoundOff.setStatus("Active");
			roundOffValue=roundOff;
			billLineItemEntityRoundOff.setElectricityBillEntity(billEntity);		
			electricityBillLineItemService.save(billLineItemEntityRoundOff); 
		}
				
		System.out.println("gstlatePayment "+gstlatePayment);
         billEntity.setBillAmount(totalLineItemAmount+roundOffValue+cgstCess+sgstCess+totalbankcharge+interestOnArrearsAmount+gstlatePayment);
		billEntity.setNetAmount(billEntity.getArrearsAmount()+billEntity.getBillAmount());
		double netamount=billEntity.getArrearsAmount()+billEntity.getBillAmount();
		if(netamount<0){
			billEntity.setLatePaymentAmount(0.0);
		}else{
		billEntity.setLatePaymentAmount(billController.latepaymentAmountCam(billEntity.getArrearsAmount()+billEntity.getBillAmount(),billEntity, camInterestRate));
		}
		electricityBillsService.update(billEntity);
		
	
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
	
	public static Date addDays(Date date, int days)
	 {
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days);
	        return cal.getTime();
	 }
	
	public int getNoMonthsDiff(ElectricityBillEntity billEntity){
			Date currentBillDate = (Date)billEntity.getBillDate();
		    Date lastBillDate = (Date)billEntity.getFromDate();
		    
		    Calendar c1 = Calendar.getInstance(); 
			c1.setTime(lastBillDate); 
			c1.add(Calendar.DATE, 1);
			lastBillDate = c1.getTime();
		    
		    Calendar startCalendar = new GregorianCalendar();
		    startCalendar.setTime(currentBillDate);
		    Calendar endCalendar = new GregorianCalendar();
		    endCalendar.setTime(lastBillDate);

		    int diffYear = startCalendar.get(Calendar.YEAR) - endCalendar.get(Calendar.YEAR);
		    int diffMonth = diffYear * 12 + startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH);
		
		return diffMonth+1;
	}
	
	public int getNoDaysDiff(ElectricityBillEntity billEntity){
		Date currentBillDate = (Date)billEntity.getBillDate();
	    Date lastBillDate = (Date)billEntity.getFromDate();
	    Days days = Days.daysBetween(new DateTime(lastBillDate),new DateTime(currentBillDate));
	
	return days.getDays();
	}
	
	public int getNoDaysDiffForValidation(Date fromDate,Date toDate){
		Date currentBillDate = (Date)toDate;
	    Date lastBillDate = (Date)fromDate;
	    Days days = Days.daysBetween(new DateTime(lastBillDate),new DateTime(currentBillDate));
	
	return days.getDays();
	}
	
	public int getTotalNoDaysGivenMonths(ElectricityBillEntity billEntity){
		
		Date billDate = billEntity.getBillDate();
		Date fromDate = billEntity.getFromDate();
		
		Calendar billCalendar = Calendar.getInstance();
		billCalendar.setTime(billDate);
		int maxDays = billCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		billCalendar.set(Calendar.DAY_OF_MONTH, maxDays);
		
		Calendar c1 = Calendar.getInstance(); 
		c1.setTime(fromDate); 
		c1.add(Calendar.DATE, 1);
		fromDate = c1.getTime();
		
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(fromDate);
		fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		Days days = Days.daysBetween(new DateTime(fromCalendar.getTime()),new DateTime(billCalendar.getTime()));
		
	return days.getDays()+1;
	}
	
	public HashMap<Object, Object> ineterestCalculation(int accountID,String typeOfService, Date previousBillDate, Date currentBillDate,double camInterestRate){

		
		logger.info("::::::::::::::: In interest on calculation CAM :::::::::::::::");
		HashMap<Object, Object> intersts = new LinkedHashMap<>();
		float interestOnArrearsAmount = 0f;
		float rateOfInterestL = 0;
		float rateOfInterest = 0f;
		double interestRate = camInterestRate / 100;

		
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

			}
			

			
			
	
 * 
 * 
 * 		Montwise Calculation Starts
 * 
 * 
 * 
 *                       
			
			
			else {

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

					} 
				
				 * 
				 * 
				 * 
				 * 
				 * 	Last Ledger Is Bill
					
					
					else {
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
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(previouBillEntity.getBillDueDate());
							logger.info("::::::::::"+previouBillEntity.getBillDueDate());
							cal1.add(Calendar.MONTH, -1);//cal.add(Calendar.DATE, 30);
							Date previousBilldte = cal1.getTime();
							
							logger.info("previouBillEntity.getBillAmount() "+ previouBillEntity.getArrearsAmount());
							LocalDate startDate = null;
							LocalDate endDate = null;
							startDate = new LocalDate(currentBillDate);
							endDate = new LocalDate(previousBilldte);
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
							
							System.err.println("::::::::::::::rateOfInterestL:::::::::::::"+rateOfInterestL);
							logger.info("rateOfInterestL ---------- "+ rateOfInterestL);
							logger.info("previouBillEntity.getBillAmount() ---------- "+ previouBillEntity.getBillAmount());
							float arrersAmount = (float) (previouBillEntity.getBillAmount() - (rateOfInterestL));
							
							System.err.println("::::::::::::::rateOfInterestL:::::::::::::"+rateOfInterestL);

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
	 private String getBillinConfigValue(String configName1,String status1){
		   
	        return electricityBillsService.getBillingConfigValue(configName1,status1);
	     }
	 public Double getpreviouslatepaymentAmount(int accountId, String string) {
		 ElectricityBillEntity previouBillEntity = null;
		 double previouslatepaymentAmount=0;
			BigDecimal test = camConsolidationService.getPreviousBill(accountId, string, "Bill");
			if (test != null) {
				int billId = test.intValue();
				previouBillEntity = electricityBillsService.find(billId);
				if(!previouBillEntity.getStatus().equalsIgnoreCase("Paid")){
					try{
						previouslatepaymentAmount=previouBillEntity.getLatePaymentAmount();
					}catch(NullPointerException e){
						previouslatepaymentAmount=0;
					}
					
				}else{
					previouslatepaymentAmount=0;
				}
			}
			
			
			return previouslatepaymentAmount;
		}
}
*/