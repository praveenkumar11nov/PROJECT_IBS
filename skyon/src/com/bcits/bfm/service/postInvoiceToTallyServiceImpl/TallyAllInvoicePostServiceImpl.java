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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyInvoicePostService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.tally.billinginvoices.BillingInvoices;
import com.tally.billinginvoices.BillingInvoicesForCAM;


public class TallyAllInvoicePostServiceImpl {
	
	Logger logger=Logger.getLogger(TallyAllInvoicePostServiceImpl.class);
	@Autowired
	private ElectricityBillsService electricityBillsService;
	
	@Autowired
	private TariffCalculationService tariffCalculationService;
	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private TallyInvoicePostService tallyInvoicePostService;
	@Autowired
	private ElectricityBillLineItemService electricityBillLineItemService;


	  ArrayList<Map<String,Object>> arraylist=new ArrayList<Map<String,Object>>();
		Map<String,Object> hashmap=new HashMap<String, Object>();
		HttpSession session=null;
		int size=0;
		int count= 0;
		
		int invoiceCount=0;
		//List<Map<String,Object>> reportList=new ArrayList<Map<String,Object>>();
		Map<String,Object>  obj=null;
		
		/* Inner class Defination */
	  @SuppressWarnings("rawtypes")
	private class PostalltooTally implements Callable<Map<String,Object>> {
		  
		  Logger logger=Logger.getLogger(PostalltooTally.class);
			
		  
		  private List<Map<String, Object>> mapsList;
		  private String billNumber;
		  private String billDate;
		  private String salesLedger;
		  private int billId;
		  private Date basicDateOfInvoice;
		  private String serviceType;
		  private String accountNo;
		  private String arrearsLedge;
		  private String propertyNumber;
		
		  
		  
			
			
			public PostalltooTally(List<Map<String, Object>> mapsList,
				String billNumber, String billDate, String salesLedger,
				int billId, Date basicDateOfInvoice, String serviceType,
				String accountNo, String arrearsLedge,String propertyNumber) {
			// TODO Auto-generated constructor stub
				
			this.mapsList=mapsList;
			this.billNumber=billNumber;
			this.billDate=billDate;
			this.salesLedger=salesLedger;
			this.billId=billId;
			this.basicDateOfInvoice=basicDateOfInvoice;
			this.serviceType=serviceType;
			this.accountNo=accountNo;
			this.arrearsLedge=arrearsLedge;
			this.propertyNumber=propertyNumber;
				
		}





			@Override
			public Map<String,Object> call() throws Exception {
				
				String response ="";

				try {
					logger.info("Inside run method try block=======billId=>"+billId);
					
					response = reponsePostInvoiceToTally(
					mapsList, billNumber, billDate, salesLedger, billId,
					basicDateOfInvoice, serviceType, accountNo,arrearsLedge,propertyNumber);
					
					System.out.println("===================response"+response);
					
					++count;
					if(count>size)
					{
						count=0;
					}
					session.setAttribute("progress",count*100/size);
					
					
					if(response.equalsIgnoreCase("Bill Successfully Posted To Tally"))
					{
						transactionMasterService.setTallyStatusUpdate(billId);
						obj=new HashMap<String,Object>();
						obj.put("success", billId);
						//reportList.add(obj);
						
					}
					else if (response.equalsIgnoreCase("CAM Successfully Posted To Tally")) {
						transactionMasterService.setTallyStatusUpdate(billId);
						obj=new HashMap<String,Object>();
						obj.put("success", billId);
					}

					else if(response.contains("Please start 'TallyERP9' server and load company"))
					{
						obj=new HashMap<String,Object>();
						obj.put("server", "Please start 'TallyERP9' server and load company");
						//return obj;
					}
					else if(response.contains("Could not set 'SVCurrentCompany' "))
					{
						obj=new HashMap<String,Object>();
						obj.put("server", response);
						//return obj;
					}
					else
					{
						obj=new HashMap<String,Object>();
						obj.put("failure", response);
						//reportList.add(obj);
					}
						
					
					} catch (Exception e) {
						obj=new HashMap<String,Object>();
						obj.put("failure", response);
						//reportList.add(obj);
						
					}
				return obj;
			
			
			}





			private String reponsePostInvoiceToTally(
					List<Map<String, Object>> mapsList, String billNumber,
					String billDate, String salesLedger, int billId,
					Date basicDateOfInvoice, String serviceType,
					String accountNo, String arrearsLedge,String propertyNumber) throws Exception {
				// TODO Auto-generated method stub
				String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
				String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
				String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
				String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
				String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
				List<Map<String, Object>>  propertyMap=tariffCalculationService.getTallyDetailData(billId);
				
				String autoIncrementString = this.getNewString(billId);
				String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
				String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";
				
				String str=null;
				if(serviceType.equalsIgnoreCase("CAM")){
					BillingInvoicesForCAM billInvoiceforCam=new BillingInvoicesForCAM();
					List<Map<String,Object>> lineItemList=electricityBillLineItemService.findAllDetailsForCamPosting(billId);
					str=billInvoiceforCam.createBillInvoicesForCAM(mapsList,remoteId,voucherKey,billId,
							billDate,salesLedger,billNumber,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword,
							tallyCompanyName,basicDateOfInvoice,propertyMap,serviceType,accountNo,arrearsLedge,lineItemList,propertyNumber);
					
					
				}
				else{

					BillingInvoices billInvoice=new BillingInvoices();
				/*
					 str=billInvoice.createBillInvoices(mapsList,remoteId,voucherKey,billId,
							billDate,salesLedger,billNumber,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword,
							tallyCompanyName,basicDateOfInvoice,propertyMap,serviceType,accountNo,arrearsLedge);
					*/
					
				}

			
				
				return str;
			}





			private String getNewString(int billId2) {
				
				StringBuilder sb = new StringBuilder();
				sb.append("");
				sb.append(billId2);
			String autoIncrementString = sb.toString();
			
			 return autoIncrementString;
			}		

		  }
	  
	  private org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor taskExecutor;  
	  
		public TallyAllInvoicePostServiceImpl(org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor taskExecutor) {
			this.taskExecutor = taskExecutor;
		}
		
	
	  @SuppressWarnings("unchecked")
	public List<Map<String,Object>> post(HttpSession session,String serviceType1,java.util.Date currentMonth) {
		  
		  count=0;
		  size=0;
		  session.setAttribute("progress", 0);
		  logger.info("=============inside post method"+count);
		  logger.info("inside post method======"+size);
		
		  List<Map<String,Object>> reportList=new ArrayList<Map<String,Object>>();
		  this.session=session;
		  List<ElectricityBillEntity> objList=electricityBillsService.getFiftyRecordsForTally(serviceType1,currentMonth);
		  size=objList.size();
		  System.out.println("size of object list=========================="+size);
			
			for (ElectricityBillEntity electricityBillEntity : objList) {
				
				int billId = 0;
				List<Map<String, Object>> mapsList = new ArrayList<>();
               billId = electricityBillEntity.getElBillId();
               ElectricityBillEntity billEntity = electricityBillsService.find(billId);
               Map<String, Object> map = new LinkedHashMap<>();
   			   map.put("totalAmount", billEntity.getBillAmount());
   			   map.put("arrearsAmount", billEntity.getArrearsAmount());
   			   mapsList.add(map);
   			   
   			Date basicDateOfInvoice = billEntity.getBillDate();
   			String billNumber = billEntity.getBillNo();
   			String billDate = new SimpleDateFormat("YYYYMMdd").format(billEntity
   					.getBillDate());
   			String serviceType = billEntity.getTypeOfService();
   			String accountNo = billEntity.getAccountObj().getAccountNo();

   			String salesLedger = null  ;
   			String arrearsLedge = null;
   			if (serviceType.equalsIgnoreCase("Electricity")) {
   				salesLedger = "Electricity Collection";
   				arrearsLedge= "Electricity Arrears";
   				
   			}
   			if (serviceType.equalsIgnoreCase("CAM")) {
   				//salesLedger = "Maintenance Charges (CAM)";
   				
   			}
   			
   			String propertyNumber=null;
   			String firstName=null;
   			String lastName=null;
   			String personAccountNo=null;
   			
   			List<Map<String, Object>>  propertyMap=tariffCalculationService.getTallyDetailData(billId);
   			List<List<String>>  acccountDetails=this.getAccountDetails(propertyMap);
   			for (int i = 0; i < acccountDetails.size(); i++) {
   				personAccountNo=acccountDetails.get(i).get(0);
   				firstName=acccountDetails.get(i).get(1);
   				lastName=acccountDetails.get(i).get(2);
   				propertyNumber=acccountDetails.get(i).get(3);
   			}

		
				
				FutureTask task;
				try
				{
					logger.info("inside for loop====of post method========="+session.getAttribute("progress")+"===count"+count+"===+"+size);
					
					
					
					logger.info("Waiting...!");
					
					//Thread.sleep(3000);
					 task = new FutureTask(new PostalltooTally(mapsList, billNumber, billDate, salesLedger, billId,
								basicDateOfInvoice, serviceType, accountNo,arrearsLedge,propertyNumber));
					
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
					
					System.out.println("================serverValidationMap"+serverValidationMap);
					String responseKey=  (String) serverValidationMap.get("server");
					if(responseKey!=null)
					{
					System.out.println("=============================responseKey"+responseKey);
					if(responseKey.equalsIgnoreCase("Please start 'TallyERP9' server and load company"))
					{
						return reportList;
					}
					}
					logger.info("after execute method");
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
				
			}	

			return reportList;
	  }
	  
	  public List<List<String>> getAccountDetails(
				List<Map<String, Object>> propertyMap) {
			String accountNo=null;
			String firstName=null;
			String lastName=null;
			String propertyNumber=null;
			String key=null;
			Object value=null;
			List<List<String>> listItems=new ArrayList<List<String>>();
			for (Map<String,Object> accountDetails : propertyMap) {
				List<String> list=new ArrayList<String>();
				for (Map.Entry<String, Object> entry : accountDetails.entrySet()){
					key = entry.getKey();
					value = entry.getValue();
					if(key.equalsIgnoreCase("accountNo")){
						accountNo=(String)value;
						accountNo=StringEscapeUtils.escapeXml(accountNo);
					}
					if(key.equalsIgnoreCase("firstName")){
						firstName=(String)value;
						firstName=StringEscapeUtils.escapeXml(firstName);
					}
					if(key.equalsIgnoreCase("lastName")){
						lastName=(String)value;
						lastName=StringEscapeUtils.escapeXml(lastName);
					}
					if(key.equalsIgnoreCase("propertyNumber")){
						propertyNumber=(String)value;
						propertyNumber=StringEscapeUtils.escapeXml(propertyNumber);
					}
				}
				list.add(accountNo);
				list.add(firstName);
				list.add(lastName);
				list.add(propertyNumber);
				listItems.add(list);
				
			}
			return listItems;
		}
	  

}
