package com.bcits.bfm.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.BankTransactionHistory;
import com.bcits.bfm.service.BankTxnHstyservice;
import com.itextpdf.tool.xml.css.CSS.Value;
/**
 * **
 * 
 * @author vema
 *
 */
@Controller
public class BankTransactionHistoryController {

	private static final Log logger = LogFactory.getLog(BankTransactionHistoryController.class);
	
	@Autowired
   private BankTxnHstyservice bankTxnHstyservice;
	
	@RequestMapping(value="getdata")
	public String uploadonlinePayUdata(){
		logger.info("in side onlinePayTM------");
		return "servicetasks/onlinePaymentReconciliation";
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/BankTransactionHistory/uploadPayTimeTransactionHistory", method = RequestMethod.POST)
	public @ResponseBody
	void uploadTransactionDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
		logger.info("--------------- In TransactionHistory Batch file method -------------------");
		
			HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream()); //Read the Excel Workbook in a instance object    
			HSSFSheet sheet = workbook.getSheetAt(0);
			//XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			//XSSFSheet sheet = workbook.getSheetAt(0);
			
			Iterator rows = sheet.rowIterator();
			int i = 0;
		
			List<BankTransactionHistory> batchUpdates = new ArrayList<BankTransactionHistory>();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				//XSSFRow row = ((XSSFRow) rows.next());
				
			    
			  if((row.getRowNum()==0)||(row.getRowNum()==1)||(row.getRowNum()==2)){
				  
				continue;
			  }
			  BankTransactionHistory bankHistory = new BankTransactionHistory();
				  String   txnId=row.getCell(0).getStringCellValue();
			       logger.info("transaction  id-----------------"+row.getCell(0));
			       System.out.println(txnId);
			       if (!StringUtils.isEmpty(txnId)) {
						bankHistory.setTxn_ID(txnId);
					} else {
						logger.info("FAILED");
						i = 1;
					}

			       String   mid=row.getCell(1).getStringCellValue();
			       if (!StringUtils.isEmpty(mid)) {
						bankHistory.setMid(mid);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			       String  txn_Date =row.getCell(2).getStringCellValue();
			       if (txn_Date!=null) {
			    	   try{
			    	   Date date=formatter.parse(txn_Date);
						
						bankHistory.setTxn_Date(date);
			    	   }catch(Exception e){}
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   txn_Channel=row.getCell(3).getStringCellValue();
			       if (!StringUtils.isEmpty(txn_Channel)) {
						bankHistory.setTxn_Channel(txn_Channel);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   txn_Amount=row.getCell(4).getStringCellValue();
			       if (!StringUtils.isEmpty(txn_Amount)) {
						bankHistory.setTxn_Amount(txn_Amount);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   currency_Type=row.getCell(5).getStringCellValue();
			       if (!StringUtils.isEmpty(currency_Type)) {
						bankHistory.setCurrency_Type(currency_Type);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   order_Id=row.getCell(6).getStringCellValue();
			       if (!StringUtils.isEmpty(order_Id)) {
						bankHistory.setOrder_Id(order_Id);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   response_Code=row.getCell(7).getStringCellValue();
			       if (!StringUtils.isEmpty(response_Code)) {
						bankHistory.setResponse_Code(response_Code);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   payment_Mode=row.getCell(8).getStringCellValue();
			       if (!StringUtils.isEmpty(payment_Mode)) {
						bankHistory.setPayment_Mode(payment_Mode);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   bank_Txn_ID=row.getCell(9).getStringCellValue();
			       if (!StringUtils.isEmpty(bank_Txn_ID)) {
						bankHistory.setBank_Txn_ID(bank_Txn_ID);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   cust_ID=row.getCell(10).getStringCellValue();
			       if (!StringUtils.isEmpty(cust_ID)) {
						bankHistory.setCust_ID(cust_ID);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   CC_DC_Last4=row.getCell(11).getStringCellValue();
			       if (!StringUtils.isEmpty(CC_DC_Last4)) {
						bankHistory.setCC_DC_Last4(CC_DC_Last4);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   issuing_Bank=row.getCell(12).getStringCellValue();
			       if (!StringUtils.isEmpty(issuing_Bank)) {
						bankHistory.setIssuing_Bank(issuing_Bank);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   bank_gateway=row.getCell(13).getStringCellValue();
			       if (!StringUtils.isEmpty(bank_gateway)) {
						bankHistory.setBank_gateway(bank_gateway);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   status=row.getCell(14).getStringCellValue();
			       if (!StringUtils.isEmpty(status)) {
						bankHistory.setStatus(status);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   settled_Amt=row.getCell(15).getStringCellValue();
			       if (!StringUtils.isEmpty(settled_Amt)) {
						bankHistory.setSettled_Amt(settled_Amt);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   status_type=row.getCell(16).getStringCellValue();
			       if (!StringUtils.isEmpty(status_type)) {
						bankHistory.setStatus_type(status_type);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   commission_Amt=row.getCell(17).getStringCellValue();
			       if (!StringUtils.isEmpty(commission_Amt)) {
						bankHistory.setCommission_Amt(commission_Amt);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   service_Tax=row.getCell(18).getStringCellValue();
			       if (!StringUtils.isEmpty(service_Tax)) {
						bankHistory.setService_Tax(service_Tax);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   settled_Date=row.getCell(19).getStringCellValue();
			       System.out.println(settled_Date);
			        if(settled_Date!=null){
			        	 try{
						bankHistory.setSettled_Date(formatter.parse(settled_Date));
			        	 }catch(Exception e){
			        		// e.printStackTrace();
			        	 }
					} else {
						logger.info("FAILED");
						i = 1;
					}
			      
			       
			       String   website=row.getCell(20).getStringCellValue(); 
			       if (!StringUtils.isEmpty(website)) {
						bankHistory.setWebsite(website);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       
			       String   base_Amount=row.getCell(21).getStringCellValue();
			       if (!StringUtils.isEmpty(base_Amount)) {
						bankHistory.setBase_Amount(base_Amount);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String   txn_Updated_Date=row.getCell(24).getStringCellValue();
			       if(txn_Updated_Date!=null){
			        	 try{
			        		 bankHistory.setTxn_Updated_Date(formatter.parse(txn_Updated_Date));
			        	 }catch(Exception e){
			        		// e.printStackTrace();
			        	 }
			       }
					 else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       String accountid=bankTxnHstyservice.getAccountID(cust_ID);
			       System.out.println("accountid*******************"+accountid);
				     bankHistory.setAccount_Id(accountid);
				batchUpdates.add(bankHistory);
				System.out.println(batchUpdates);
				
				bankTxnHstyservice.save(bankHistory);
				
			}
			
			  }
				  
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/BankTransactionHistory/uploadPayUTransactionHistory", method = RequestMethod.POST)
	public @ResponseBody
	void uploadPayUTransactionDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
		logger.info("--------------- In TransactionHistory Batch file method -------------------");
		
		HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream()); //Read the Excel Workbook in a instance object    
			HSSFSheet sheet = workbook.getSheetAt(0);
			//XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			//XSSFSheet sheet = workbook.getSheetAt(0);
			
			Iterator rows = sheet.rowIterator();
			int i = 0;
		
			List<BankTransactionHistory> batchUpdates = new ArrayList<BankTransactionHistory>();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				//XSSFRow row = ((XSSFRow) rows.next());
				
			    
			  if(row.getRowNum()==0){
				continue;
			  }
			  
			  BankTransactionHistory bankHistory = new BankTransactionHistory();
			  
			  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			  String   customerName=row.getCell(5).getStringCellValue();
			  String   customerMail=row.getCell(6).getStringCellValue();
			   String accountId=bankTxnHstyservice.getAccId(customerName, customerMail);
			   logger.info("accountId  := "+accountId);        
			   
			  if(accountId!=null){
				  bankHistory.setAccount_Id(accountId);
			  }
			 
			  if(accountId!=null){
			  int accid=Integer.parseInt(accountId);
			  
			  System.out.println("*************"+accid+"********************");
			  String accountNum=bankTxnHstyservice.getAccountNo(accid);
			  System.out.println("accountNum ********"+accountNum);
			   if(accountNum!=null){
				  bankHistory.setCust_ID(accountNum);
			      }
			  }
			  int  bankId=(int) row.getCell(0).getNumericCellValue();
			  System.out.println("bankId ="+bankId);
			  /*String bank_txn_Id=Double.toString(bankId);
			  System.out.println("bank_txn_Id ="+bank_txn_Id);*/
			  String bankTxnID=String.valueOf(bankId);
			  System.out.println("bankTxnID =="+bankTxnID);
			  if(bankTxnID!=null){
				  bankHistory.setBank_Txn_ID(bankTxnID);
			  }
			  
				  String   addonDate=row.getCell(2).getStringCellValue();
			       logger.info("transaction  date-----------------"+row.getCell(2));
			      
			       if (addonDate!="") {
			    	   try{
						Date d1=(Date)formatter.parse(addonDate);
						bankHistory.setTxn_Date(d1);
						
			    	   }catch(Exception e){
			    		   e.printStackTrace();
			    	   }
					} else {
						logger.info("FAILED");
						i = 1;
					}

			       
			       if (row.getCell(3)!=null) {
			    	   try{
			    		   String   succeDate=row.getCell(3).getStringCellValue();
							Date d1=formatter.parse(succeDate);
							bankHistory.setTxn_Updated_Date(d1);
				    	   }catch(Exception e){
				    		   e.printStackTrace();
				    	   }
					} else {
						logger.info("FAILED");
						i = 1;
					}
			      
			     
			       if (row.getCell(4)!=null) {
			    	   String  MTID =row.getCell(4).getStringCellValue();
			    	   bankHistory.setTxn_ID(MTID);
			    	  
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       
			       if (row.getCell(8)!=null) {
			    	   String   payStatus=row.getCell(8).getStringCellValue();
						bankHistory.setStatus(payStatus);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       
			       if (row.getCell(9)!=null) {
			    	   double   settleAmt=row.getCell(9).getNumericCellValue();
			    	   String settleAmt1=Double.toString(settleAmt);
					bankHistory.setSettled_Amt(settleAmt1);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			     
			       
			     
			       if (row.getCell(13)!=null) {
			    	   double   convFee=row.getCell(13).getNumericCellValue();
			    	   String convfeee=Double.toString(convFee);
						bankHistory.setService_Tax(convfeee);
					} else {
						logger.info("FAILED");
						i = 1;
					}
			       
			       
			       System.out.println("Settled DATE-------------------"+row.getCell(10));
			       if (row.getCell(10)!=null) {
			    	   try{
			    		   DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			    		   Date   settDate=row.getCell(10).getDateCellValue();
							//String d1=formatter1.format(settDate);
							  System.out.println("Settled DATE1-------------------"+settDate);
							bankHistory.setSettled_Date(settDate);
							
				    	   }catch(Exception e){
				    		   e.printStackTrace();
				    	   }
					}  else {
						logger.info("FAILED");
						i = 1;
					}
			      
				bankTxnHstyservice.save(bankHistory);
				
			}
			  
             
			  
			  }
	
	@RequestMapping(value="/onlinePayTm_PayU/reConciliationDetails/{presentdatePost}/{todatePost}", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getMonthWiseData(HttpServletRequest request,@PathVariable String presentdatePost,@PathVariable String todatePost) throws ParseException{
		
		logger.info("in side get-----  getMonthWiseData------------");
		/* String presentdate=request.getParameter("presentdatePost");*/
		 System.out.println(presentdatePost);
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = formatter.parse(presentdatePost);
			Date date1 = formatter.parse(todatePost);
			System.out.println("*date1***********"+date1);
		return bankTxnHstyservice.getAllByMonth(date,date1);
		
	}
	
	@RequestMapping(value="/onlinePayment/readUrl", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<?> getPaytimeDetails(){
		logger.info("in side read methode");
	List<?> data=bankTxnHstyservice.readAllPayTimeData();
	System.out.println("controller "+data);
		return data;
	}
	/*
	@RequestMapping(value="/onlinePayment/readUrl", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<?> getPropertyNoForFilter(){
		
		return bankTxnHstyservice.getPropertyNumForFilter();
	}*/
}
