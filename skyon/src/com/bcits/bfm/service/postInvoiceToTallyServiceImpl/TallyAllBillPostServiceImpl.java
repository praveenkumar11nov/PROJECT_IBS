package com.bcits.bfm.service.postInvoiceToTallyServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.bcits.bfm.service.PrepaidPaymentAdjustmentService;
import com.bcits.bfm.service.PrepaidRechargeService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.tally.billreceipt.BillReceiptPostSkyon;
import com.tally.billreceipt.CashReceiptTally;


public class TallyAllBillPostServiceImpl {
	
	
	
	Logger logger=Logger.getLogger(TallyAllInvoicePostServiceImpl.class);
	@Autowired
	private ElectricityBillsService electricityBillsService;
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	@Autowired
	private TransactionMasterService transactionMasterService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private AccountService accountService;
	
	@Autowired
    private PrepaidPaymentAdjustmentService prepaidPaymentAdjustmentService;
	
	@Autowired
	private PrepaidRechargeService prepaidRechargeService;


	  ArrayList<Map<String,Object>> arraylist=new ArrayList<Map<String,Object>>();
		Map<String,Object> hashmap=new HashMap<String, Object>();
		//HttpSession session=null;
		int size=0;
		int count= 0;
		int invoiceCount=0;
		Map<String,Object>  obj=null;
		
	/* **************************Inner class Defination *********************************/
	private class PostalltooTally implements Callable<Map<String,Object>> {
		 
		@SuppressWarnings("unused")
		Logger logger=Logger.getLogger(PostalltooTally.class);
		
		private List<Map<String, Object>> mapsList;
		private Date receiptDate;
		private String receiptNumber;
		private String bankAcNumberLedger; 
		private String bankName; 
		private String paymentMode;
		private int ppRechargeId;
		private double checkAmount;
		private Date cashReceiptDate;
			
		
		  
	

			public PostalltooTally(List<Map<String, Object>> mapsList,
					Date receiptDate, String receiptNumber,
					String bankAcNumberLedger, String bankName, String paymentMode,
					int ppRechargeId, double checkAmount,
					Date cashReceiptDate) {
				
				this.mapsList=mapsList;
				this.receiptDate=receiptDate;
				this.receiptNumber=receiptNumber;
				this.bankAcNumberLedger=bankAcNumberLedger;
				this.bankName=bankName;
				this.paymentMode=paymentMode;
				this.ppRechargeId=ppRechargeId;
				this.checkAmount=checkAmount;
				this.cashReceiptDate=cashReceiptDate;
			
		}

		@Override
		public Map<String, Object> call() throws Exception {
			String response = "";
			try {
				response = reponsePostReceiptToTally(mapsList, receiptDate,
						receiptNumber, bankAcNumberLedger, bankName,
						paymentMode, ppRechargeId, checkAmount,
						cashReceiptDate);
				++count;
				if (count > size) {
					count = 0;
				}
				
				//session.setAttribute("progress", count * 100 / size);
				logger.info("response==================="+response);
				if (response.equalsIgnoreCase("Receipt Successfully Posted To Tally")) {
					String tallyStatus="Posted";
					prepaidRechargeService.upDateTallyStatus(ppRechargeId,tallyStatus);
					obj = new HashMap<String, Object>();
					obj.put("success", ppRechargeId);
					
				}else if (
					response.contains("Please start 'TallyERP9' server and load company")) {
					obj = new HashMap<String, Object>();
					obj.put("server","Please start 'TallyERP9' server and load company");

				}else if (
					response.contains("Could not set 'SVCurrentCompany' ")) {
					obj = new HashMap<String, Object>();
					obj.put("server", response);

				}else {
					obj = new HashMap<String, Object>();
					obj.put("failure", response);
				}
			}catch (Exception e) {
				obj = new HashMap<String, Object>();
				obj.put("failure", response);
			}
			return obj;
		}
	}
	  
	  private org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor taskExecutor;  
	  
		public TallyAllBillPostServiceImpl(org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor taskExecutor) {
			this.taskExecutor = taskExecutor;
		}
		
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,Object>> post(HttpSession session,Date fromDate) {
		  
		  count=0;
		  size=0;
		  logger.info("*****Number of loop*******"+count+"*****and Size ****"+size);
		  List<Map<String,Object>> reportList=new ArrayList<Map<String,Object>>();
		  //List<BillingPaymentsEntity> objList=transactionMasterService.getFiftyRecord();
		  List<PrepaidRechargeEntity> objList=prepaidRechargeService.getFiftyRecords(fromDate);
		
		  FutureTask task=null;
		  
				try
				{
					  size=objList.size();
					  for (PrepaidRechargeEntity prepaidRechargeEntity : objList) {
					    	 String receiptNumber=null;
							  String bankAcNumberLedger=null;
							  String bankName=null;
							  String paymentMode=null;
							  List<Map<String, Object>> mapsList = new ArrayList<>();
							  int ppRechargeId=prepaidRechargeEntity.getPreRechargeId();
							  Date receiptDate=null;
							  double checkAmount=0.0;
							  Date cashReceiptDate=null;
							  try{
							
							PrepaidRechargeEntity prepaidRechargeEntity2=prepaidRechargeService.find(ppRechargeId);
							 cashReceiptDate=prepaidRechargeEntity2.getTxn_Date();
							 receiptNumber=prepaidRechargeEntity2.getTxn_ID();
							 receiptDate=prepaidRechargeEntity2.getTxn_Date();
							 bankName=prepaidRechargeEntity2.getBankName();
							 paymentMode=prepaidRechargeEntity2.getModeOfPayment();
							 bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
							 checkAmount=prepaidRechargeEntity2.getRechargeAmount();
							 
							 Map<String, Object> map=new LinkedHashMap<>();
							 Object[] obj;
							 if(receiptNumber.startsWith("AD")){
								  obj=prepaidPaymentAdjustmentService.getInstrumentIDNo(receiptNumber);
								 if(obj[1]!=null){
								 map.put("instrumentDate",new  SimpleDateFormat("YYYYMMdd").format((java.sql.Date)obj[1] ));
								 }
								 map.put("instrumentNumber", obj[0]);
							 }else{
								 map.put("instrumentDate", new  SimpleDateFormat("YYYYMMdd").format(prepaidRechargeEntity2.getTxn_Date()));
								 map.put("instrumentNumber", prepaidRechargeEntity2.getMerchantId());
							 }
		//					 map.put("propertyNo", propertyService.find(accountService.find(prepaidRechargeEntity2.getAccountId()).getPropertyId()).getProperty_No());
							 map.put("accountNo", propertyService.find(accountService.find(prepaidRechargeEntity2.getAccountId()).getPropertyId()).getProperty_No());
							 map.put("billSegmentCircle", "Utility Charges");
							 map.put("billAmount", prepaidRechargeEntity2.getRechargeAmount());
							 mapsList.add(map);
						  
						   
				    }
						  catch(Exception e)
						  {
							 logger.info("Exception  message"+e.getMessage());
						  }
						  
					logger.info("inside for loop====of post method========="+"===count"+count+"===+"+size);
					logger.info("Waiting...!");
					//Thread.sleep(30'00);
					
					 task = new FutureTask(new PostalltooTally(mapsList, receiptDate, receiptNumber,bankAcNumberLedger,
		        		   		bankName,paymentMode,ppRechargeId,checkAmount,cashReceiptDate));
					taskExecutor.execute(task);
					reportList.add((Map<String, Object>) task.get());
					
					 for (Map<String, Object> map1 : reportList) {
					    	
				    	  for (Map.Entry<String, Object> entry : map1.entrySet()) {
				    	       
				    	        if(entry.getKey().equalsIgnoreCase("server"))
				    	        {
				    	        	return reportList;
				    	        }
				    	    
				    	  }
				    	 }
					
					Map<String,Object> serverValidationMap=(Map<String, Object>) task.get();
					
					
					String responseKey=  (String) serverValidationMap.get("server");
					if(responseKey!=null)
					{
					
					if(responseKey.equalsIgnoreCase("Please start 'TallyERP9' server and load company"))
					{
						return reportList;
					}
					}
					logger.info("after execute method");
				}
			}
				catch(Exception e)
				{
					logger.info("inside post method catch block");
					e.printStackTrace();;
				}
				
				finally{
					task=null;
					objList=null;
				}
				
		
				logger.info("after execute method"+reportList);
			return reportList;
	  }
	  
	  

		public String reponsePostReceiptToTally(List<Map<String, Object>> mapsList,Date receiptDate, String receiptNumber,
				String bankAcNumberLedger,String bankName,String paymentMode,
				int ppRechargeId,double checkAmount,Date cashReceiptDate) throws Exception {
			
			
			String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
			String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
			String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
			String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
			String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
			String bankLedger=ResourceBundle.getBundle("application").getString("bankLedger");
			
			String cashReptDate=null;
			String recDate=null;
			String branchName=null;
			if(receiptDate != null){
				recDate=new SimpleDateFormat("YYYYMMdd").format(receiptDate);
			}
			if(cashReceiptDate != null){
				cashReptDate=new SimpleDateFormat("YYYYMMdd").format(cashReceiptDate);
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(ppRechargeId);
			String autoId = sb.toString();
			if(bankName !=null){
				branchName = bankName;
			}
			String voucherType = "Receipt";
			String tansferMode = null;
			String remoteId = "6dd-00000034" + autoId;
			String voucherKey = "a31f:00000028" + autoId;
			BillReceiptPostSkyon receiptTally = new BillReceiptPostSkyon();
			//CashReceiptTally cashReceiptTally=new CashReceiptTally();
		
			String catagoryName="Delhi Elctricity Departments";
			String state="Karnataka";
			String transactionType2=null;
			String receiptResponse=null;
			
			System.out.println("Payement mode is========>"+paymentMode);//
			if(paymentMode.equalsIgnoreCase("Cheque")){
				String transactionType ="Cheque/DD";
				if (transactionType.equalsIgnoreCase("Cheque/DD")) {
					tansferMode = "Cheque/DD";
				}
				receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
						recDate, voucherType, paymentMode,
						ppRechargeId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
			
			if(paymentMode.equalsIgnoreCase("AdvancePayment")){
				String transactionType ="AdvancePayment";
				if (transactionType.equalsIgnoreCase("AdvancePayment")) {
					tansferMode = "AdvancePayment";
				}
				receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
						recDate, voucherType, paymentMode,
						ppRechargeId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
			if(paymentMode.equalsIgnoreCase("RTGS/NEFT")){
				String transactionType ="Inter Bank Transfer";
				if (transactionType.equalsIgnoreCase("Inter Bank Transfer")) {
					tansferMode = "NEFT";
				}
				receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
						recDate, voucherType, paymentMode,
						ppRechargeId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
			/*if(paymentMode.equalsIgnoreCase("Cash")){
				String transactionType = "Cash";
				if (transactionType.equalsIgnoreCase("Cash")) {
					tansferMode = "Cash";
				}
				receiptResponse=cashReceiptTally.createCashReceipt(remoteId, voucherKey,mapsList,
						cashReptDate, voucherType, paymentMode,
						ppRechargeId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}*/if(paymentMode.equalsIgnoreCase("DD")){
				String transactionType = "Cheque/DD";
				if (transactionType.equalsIgnoreCase("Cheque/DD")) {
					tansferMode = "Cheque/DD";
				}
				receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
						recDate, voucherType, paymentMode,
						ppRechargeId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}if(paymentMode.equalsIgnoreCase("NB") || paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("PPI") || paymentMode.equalsIgnoreCase("Online")){
				String transactionType = "Others";
				if (transactionType.equalsIgnoreCase("Others")) {
					bankName="";
					branchName="";
					tansferMode = "Others";
				}
				paymentMode="Online";
				double rechargedAmt=checkAmount+12;
				receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
						recDate, voucherType, paymentMode,
						ppRechargeId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,rechargedAmt,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
		return receiptResponse;
			
			
	}
}
