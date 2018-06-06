package com.bcits.bfm.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class IncomeTrackerController {
	private static final Log logger = LogFactory.getLog(IncomeTrackerController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private PaymentService paymentService;
	
	
   @RequestMapping(value="/getIncomeTrackerReport")
	public String getIncomeTrackerReport(Model model,HttpServletRequest request)
	{
		logger.info("******Inside a Request Method***************"+request.getParameter("jspId"));
		
		model.addAttribute("data", request.getParameter("jspId"));
		logger.info("::::::::jspid::::::"+request.getParameter("jspId"));
		int id=Integer.parseInt(request.getParameter("jspId"));
		if(id==1)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Payment Gateway Transaction", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "billingPayments/incomeTracker";
		}
		if(id==2)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Interest Accured Till Date", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "billingPayments/interestAccured";
		}
		if(id==3)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Payment Gateway Transaction", 3, request);			
			return "billingPayments/incomeTracker";
		}
		if(id==4)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("All Closed On-Behalf of Ticket", 3, request);			
			return "com/comAllRecords";
		}
		if(id==5)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Member Income Due List", 3, request);			
			return "billingPayments/incomeTracker";
			
		}
		if(id==6)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Member Recivable By Account", 3, request);			
			return "billingPayments/interestAccured";
			
		}
		if(id==7)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Resident Payment Posting Monthwise", 3, request);			
			return "billingPayments/interestAccured";
		}
		if(id==8)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker Reports", 2, request);
			breadCrumbService.addNode("Refundable Deposit", 3, request);			
			return "billingPayments/incomeTracker";
		}
		if(id==9)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker", 2, request);
			breadCrumbService.addNode("Miscellenous Income", 3, request);
			
			return "billingPayments/incomeTracker";
		}
		if(id==10)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker", 2, request);
			breadCrumbService.addNode("Open Resident Invoice", 3, request);
			
			return "billingPayments/interestAccured";
		}
		if(id==11)
		{
			model.addAttribute("ViewName", "Income Tracker");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Income Tracker", 1, request);
			breadCrumbService.addNode("Income Tracker", 2, request);
			breadCrumbService.addNode("Resident Due", 3, request);
			
			return "billingPayments/paymentDue";
		}
		return "billingPayments/interestAccured";
	}	
	
	
	@RequestMapping(value="/incomeTracker/readIncomeTrackerData/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readBillAllData(@PathVariable int data)
	{
		
		
		logger.info("****************Bill Reports Inside Read**************"+data);
		
		List<Map<String, Object>> allComData=new ArrayList<>();   
		if(data==1)
		{
			
			logger.info("*********Income Tracker Report One**********");
			
			logger.info("********inside a Income Tracker Report*****************");

			List<?> allpaymentdetails=paymentService.getAllPaymentDetail();
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		if(data==3)
		{
			
			logger.info("*********Income Tracker Report One**********");
			
			logger.info("********inside a Income Tracker Report*****************");

			List<?> allpaymentdetails=paymentService.getAllPaymentDetailforPaymentGateway();
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		
		if(data==5)
		{
			
			logger.info("*********Income Tracker Report Five**********");
			
			logger.info("********inside a Income Tracker Five*****************");

			List<?> allpaymentdetails=paymentService.getAllBillDetailsWithoutmonth();
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		if(data==8)
		{
			
			logger.info("*********Income Tracker Report Nine**********");
			
			logger.info("********inside a Income Tracker Report*****************");

			List<?> allpaymentdetails=paymentService.getAllDepositDetails();
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		if(data==9)
		{
			
			logger.info("*********Income Tracker Report Nine**********");
			
			logger.info("********inside a Income Tracker Report*****************");

			List<?> allpaymentdetails=paymentService.getAllPaymentDetailforOtherServices();
			logger.info("**********Bill Detail size********");
			return allpaymentdetails;
		}
		
		return null;
	}
	
	@RequestMapping(value="/interestTracker/readIncomeTrackerData/{month}/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readInterstTrackerData(@PathVariable String month,@PathVariable int data) throws ParseException
	{
		
		logger.info("month:::::::::::::::::"+month+"data"+data);
		
		
		logger.info("****************Bill Reports Inside Read**************"+data);
		
		List<Map<String, Object>> allComData=new ArrayList<>();   
		if(data==2)
		{
			
			logger.info("*********Income Tracker Report One**********");
			
			logger.info("********inside a Income Tracker Report*****************");
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		if(data==6)
		{
			
			logger.info("*********Income Tracker Report six**********");
			
			logger.info("********inside a Income Tracker Report*****************");
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			List<?> allpaymentdetails=paymentService.getAllBillPaymentDetailAccountWise(monthToShow);
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		
		if(data==7)
		{
			
			logger.info("*********Income Tracker Report One**********");
			
			logger.info("********inside a Income Tracker Report*****************");
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		if(data==10)
		{
			
			logger.info("*********Income Tracker Report Ten**********");
			
			logger.info("********inside a Income Tracker Report*****************");
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			List<?> allpaymentdetails=paymentService.getAllOpenResidentInvoice(monthToShow);
			logger.info("**********Bill Detail size********");


			return allpaymentdetails;
		}
		
		return null;
	}
	
	@RequestMapping(value="/paymentDue/readIncomeTrackerData/{month}/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readpaymentDueData(@PathVariable String month,@PathVariable int data) throws ParseException
	{

		if(data==11)
		{
			
			logger.info("*********Income Tracker Report Eleven**********");
			
			logger.info("********Inside a Payment t*****************");
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			List<?> allpaymentdetails=paymentService.getAllOpenPaymentDueData(monthToShow);
			logger.info("**********Bill Detail size********");
			return allpaymentdetails;
		}
	
	return null;
	}
	
	
	@RequestMapping(value = "/incomeTracker/exportExcel/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	public String getPrimaryBillingReport(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws IOException
	{
		
		ServletOutputStream servletOutputStream=null;
		
		if(id==1)
		{
			
			logger.info("*********Income Tracker Report Two**********");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
						
						
						
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	
				    	header.createCell(0).setCellValue("Sl NO");
				        header.createCell(1).setCellValue("Property No");
				        header.createCell(2).setCellValue("Person Name");
				        header.createCell(3).setCellValue("Account No");
				        header.createCell(4).setCellValue("Block"); 
				        header.createCell(5).setCellValue("Recipt No"); 
				        header.createCell(6).setCellValue("Trancation No");
				        header.createCell(7).setCellValue("Amount");
				        header.createCell(8).setCellValue("Payment Mode");
				        header.createCell(9).setCellValue("Payment Date");
				        
				        for(int j = 0; j<=9; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
			
				        
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllPaymentDetail();
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("property_No"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
							row.createCell(0).setCellValue(String.valueOf(count));
				        	
				        	
								row.createCell(1).setCellValue((String) map.get("property_No"));						
								
								row.createCell(2).setCellValue((String) map.get("personName"));				
								
								row.createCell(3).setCellValue((String) map.get("accountNo"));
									
								if(map.containsKey("blockName")){
									row.createCell(4).setCellValue((String) map.get("blockName"));
									}
								if(map.containsKey("receiptNo")){
									row.createCell(5).setCellValue((String) map.get("receiptNo"));
									}
								if(map.containsKey("instrumentNo")){
									row.createCell(6).setCellValue((String) map.get("instrumentNo"));
									}
								if(map.containsKey("paymentAmount")){
									row.createCell(7).setCellValue((Double) map.get("paymentAmount"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								if(map.containsKey("paymentMode")){
									row.createCell(8).setCellValue((String) map.get("paymentMode"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
								if(map.containsKey("instrumentDate")){
									DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
									
									//Date today = Calendar.getInstance().setTime((Date)map.get("instrumentDate"));
									Calendar c=Calendar.getInstance();
									c.setTime((Date)map.get("instrumentDate"));
									String reportDate = df.format(c.getTime());
									logger.info("reportDate:::::::::::"+reportDate);
									row.createCell(9).setCellValue((String)(reportDate));
									}
							
						count++;
				        }
				
				        
				    	
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
				        
				       

						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Payment Gateway NEFT Txn.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}

		if(id==3)
		{
			
			logger.info("*********Income Tracker Report Two**********");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
						
						
						
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	
				    	
				    	header.createCell(0).setCellValue("Sl NO");
				        header.createCell(1).setCellValue("Property No");
				        header.createCell(2).setCellValue("Person Name");
				        header.createCell(3).setCellValue("Account No");
				        header.createCell(4).setCellValue("Block"); 
				        header.createCell(5).setCellValue("Recipt No"); 
				        header.createCell(6).setCellValue("Trancation No");
				        header.createCell(7).setCellValue("Amount");
				        header.createCell(8).setCellValue("Payment Mode");				        
				        
				        for(int j = 0; j<=8; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
			
				        
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllPaymentDetail();
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
							row.createCell(0).setCellValue(String.valueOf(count));
				        	
				        	
								row.createCell(1).setCellValue((String) map.get("propertyno"));						
								
								row.createCell(2).setCellValue((String) map.get("personName"));				
								
								row.createCell(3).setCellValue((String) map.get("accountNo"));
									
								if(map.containsKey("blockName")){
									row.createCell(4).setCellValue((String) map.get("blockName"));
									}
								if(map.containsKey("receiptNo")){
									row.createCell(5).setCellValue((String) map.get("receiptNo"));
									}
								if(map.containsKey("instrumentNo")){
									row.createCell(6).setCellValue((String) map.get("instrumentNo"));
									}
								if(map.containsKey("paymentAmount")){
									row.createCell(7).setCellValue((Double) map.get("paymentAmount"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								if(map.containsKey("paymentMode")){
									row.createCell(8).setCellValue((String) map.get("paymentMode"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
							
						count++;
				        }
				
				        
				    	
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
				        
				       

						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Payment Gateway  Txn.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}
		
	//	
		if(id==5)
		{
			
			logger.info("*********Income Tracker Report Two**********");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
						
						
						
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	
				    	
				    	header.createCell(0).setCellValue("Sl NO");
				        header.createCell(1).setCellValue("Property No");
				        header.createCell(2).setCellValue("Person Name");
				        header.createCell(3).setCellValue("Account No");
				        header.createCell(4).setCellValue("Block"); 
				        header.createCell(5).setCellValue("Type Of Service"); 
				        //header.createCell(6).setCellValue("Trancation No");
				        header.createCell(6).setCellValue("Bill Amount");
				        header.createCell(7).setCellValue("Arrears Amount");
				        header.createCell(8).setCellValue("Total Amount");
				        
				        
				        for(int j = 0; j<=8; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
			
				        
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllBillDetailsWithoutmonth();
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
							row.createCell(0).setCellValue(String.valueOf(count));
				        	
				        	
								row.createCell(1).setCellValue((String) map.get("property_No"));						
								
								row.createCell(2).setCellValue((String) map.get("personName"));				
								
								row.createCell(3).setCellValue((String) map.get("accountNo"));
									
								if(map.containsKey("blockName")){
									row.createCell(4).setCellValue((String) map.get("blockName"));
									}
								if(map.containsKey("typeOfService")){
									row.createCell(5).setCellValue((String) map.get("typeOfService"));
									}
								/*if(map.containsKey("instrumentNo")){
									row.createCell(6).setCellValue((String) map.get("instrumentNo"));
									}*/
								if(map.containsKey("billAmount")){
									row.createCell(6).setCellValue((Double) map.get("billAmount"));
									}else{
										row.createCell(6).setCellValue(0);	
									}
								if(map.containsKey("arrearsAmount")){
									row.createCell(7).setCellValue((Double) map.get("arrearsAmount"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								if(map.containsKey("netAmount")){
									row.createCell(8).setCellValue((Double) map.get("netAmount"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
						count++;
				        }
				
				        
				    	
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
				        
				       

						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Member Income Due List.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}	
		
		//
		if(id==8)
		{
			
			logger.info("*********Income Tracker Report Two**********");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
						
						
						
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	header.createCell(0).setCellValue("Sl NO");
				        header.createCell(1).setCellValue("Property No");
				        header.createCell(2).setCellValue("Person Name");
				        header.createCell(3).setCellValue("Account No");
				        header.createCell(4).setCellValue("Block"); 
				        header.createCell(5).setCellValue("Bank Name"); 
				        header.createCell(6).setCellValue("Trancation No");
				        header.createCell(7).setCellValue("Payment Mode");
				        header.createCell(8).setCellValue("Refunded Amount");
				        header.createCell(9).setCellValue("Deposit Amount");
				        				        
				        
				        for(int j = 0; j<=9; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
			
				        
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllDepositDetails();
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
							row.createCell(0).setCellValue(String.valueOf(count));
				        	
				        	
								row.createCell(1).setCellValue((String) map.get("property_No"));						
								
								row.createCell(2).setCellValue((String) map.get("personName"));				
								
								row.createCell(3).setCellValue((String) map.get("accountNo"));
									
								if(map.containsKey("blockName")){
									row.createCell(4).setCellValue((String) map.get("blockName"));
									}
								if(map.containsKey("bankName")){
									row.createCell(5).setCellValue((String) map.get("bankName"));
									}
								if(map.containsKey("instrumentNo")){
									row.createCell(6).setCellValue((String) map.get("instrumentNo"));
									}
								if(map.containsKey("paymentMode")){
									row.createCell(7).setCellValue((String) map.get("paymentMode"));
									}
								if(map.containsKey("refundAmount")){
									row.createCell(8).setCellValue((Double) map.get("refundAmount"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
								if(map.containsKey("totalAmount")){
									row.createCell(8).setCellValue((Double) map.get("totalAmount"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
							
						count++;
				        }
				
				        
				    	
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
				        
				       

						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Refundable Deposit  Txn.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}
		
		if(id==9)
		{
			
			logger.info("*********Income Tracker Report Two**********");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
						
						
						
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	header.createCell(0).setCellValue("Sl NO");				       
				        header.createCell(1).setCellValue("Person Name");
				        header.createCell(2).setCellValue("Recipt No");
				        header.createCell(3).setCellValue("Transaction No"); 
				        header.createCell(4).setCellValue("Amount");				        
				        header.createCell(5).setCellValue("Payment Mode");
				        header.createCell(6).setCellValue("Bank Name");
				       
				        for(int j = 0; j<=6; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllPaymentDetailforOtherServices();
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
							row.createCell(0).setCellValue(String.valueOf(count));
				        	
								row.createCell(1).setCellValue((String) map.get("personName"));	
								row.createCell(2).setCellValue((String) map.get("receiptNo"));
								if(map.containsKey("instrumentNo")){
									row.createCell(3).setCellValue((String) map.get("instrumentNo"));
									}
								if(map.containsKey("paymentAmount")){
									row.createCell(4).setCellValue((Double) map.get("paymentAmount"));
									}else{
										row.createCell(4).setCellValue(0);	
									}
								if(map.containsKey("paymentMode")){
									row.createCell(5).setCellValue((String) map.get("paymentMode"));
									}
								if(map.containsKey("bankName")){
									row.createCell(6).setCellValue((String) map.get("bankName"));
									}
						
						count++;
				        }
				
				        
				    	
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
				        
				       

						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Miscellenous Income.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}
		//paymentService.getAllPaymentDetailforOtherServices();
		return null;
	}
//	/interestTracker/readIncomeTrackerData/{month}/{data}
	

	@RequestMapping(value = "/interestTracker/exportExcel/{id}/{month}", method = {RequestMethod.POST,RequestMethod.GET})
	public String getPrimaryInterestReport(HttpServletRequest request,HttpServletResponse response,@PathVariable int id,@PathVariable String month) throws IOException, ParseException
	{
		
		ServletOutputStream servletOutputStream=null;
		
		logger.info("*********Income Tracker Report Two**********");
		if(id==2)
		{
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
					
		Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
		
		//List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
					
					
			        logger.info("*****monthToShow***********"+monthToShow);
			        String sheetName = "Templates";//name of sheet

			    	XSSFWorkbook wb = new XSSFWorkbook();
			    	XSSFSheet sheet = wb.createSheet(sheetName) ;
			    	
			    	XSSFRow header = sheet.createRow(0);    	
			    	
			    	XSSFCellStyle style = wb.createCellStyle();
			    	style.setWrapText(true);
			    	XSSFFont font = wb.createFont();
			    	font.setFontName(HSSFFont.FONT_ARIAL);
			    	font.setFontHeightInPoints((short)10);
			    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			    	style.setFont(font);
			    	header.createCell(0).setCellValue("Sl NO");
			        header.createCell(1).setCellValue("Property No");
			        header.createCell(2).setCellValue("Customer Name");
			        header.createCell(3).setCellValue("Account No");			        
			        header.createCell(4).setCellValue("Service Type"); 
			        header.createCell(5).setCellValue("Principal Amount");
			        header.createCell(6).setCellValue("Arrears Amount");
			        header.createCell(7).setCellValue("Total Due");
			       
			        				        
			        
			        for(int j = 0; j<=7; j++){
			    		header.getCell(j).setCellStyle(style);
			            sheet.setColumnWidth(j, 8000); 
			            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			        }
			        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllBillPaymentDetail(monthToShow);
			       logger.info("result::::::::"+result.size());
			        int count = 1;
			        for (Map<String, Object> map : result) {
			        	System.out.println(":::::::::::::::::::000"+map.get("propertyNo"));
			        	System.out.println(map.get("accountNo"));
			        	XSSFRow row = sheet.createRow(count);
						row.createCell(0).setCellValue(String.valueOf(count));
			        	
			        	
							row.createCell(1).setCellValue((String) map.get("propertyNo"));						
							
							row.createCell(2).setCellValue((String) map.get("personName"));				
							
							row.createCell(3).setCellValue((String) map.get("accountNo"));
								
							if(map.containsKey("typeOfService")){
								row.createCell(4).setCellValue((String) map.get("typeOfService"));
								}
							
							if(map.containsKey("billAmount")){
								row.createCell(5).setCellValue((Double) map.get("billAmount"));
								}else{
									row.createCell(5).setCellValue(0);	
								}
							if(map.containsKey("arrearsAmount")){
								row.createCell(6).setCellValue((Double) map.get("arrearsAmount"));
								}else{
									row.createCell(6).setCellValue(0);	
								}
							if(map.containsKey("netAmount")){
								row.createCell(7).setCellValue((Double) map.get("netAmount"));
								}else{
									row.createCell(7).setCellValue(0);	
								}
						
					count++;
			        }
			
			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	wb.write(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
					servletOutputStream = response.getOutputStream();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "inline;filename=\"Accured Interest Till Date.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
	}
		
		if(id==6)
		{
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
					
		Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
		//List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
					
					
			        logger.info("*****person Size in Vehicle Owner Contact Type***********");
			        String sheetName = "Templates";//name of sheet

			    	XSSFWorkbook wb = new XSSFWorkbook();
			    	XSSFSheet sheet = wb.createSheet(sheetName) ;
			    	
			    	XSSFRow header = sheet.createRow(0);    	
			    	
			    	XSSFCellStyle style = wb.createCellStyle();
			    	style.setWrapText(true);
			    	XSSFFont font = wb.createFont();
			    	font.setFontName(HSSFFont.FONT_ARIAL);
			    	font.setFontHeightInPoints((short)10);
			    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			    	style.setFont(font);
			    	header.createCell(0).setCellValue("Sl NO");
			    	header.createCell(1).setCellValue("Block");
			        header.createCell(2).setCellValue("Property No");
			        header.createCell(3).setCellValue("Customer Name");
			        header.createCell(4).setCellValue("Account No");			        
			        header.createCell(5).setCellValue("Service Type"); 
			        header.createCell(6).setCellValue("Bill Amount");
			        header.createCell(7).setCellValue("Payment Amount");
			        header.createCell(8).setCellValue("Due Amount");
			       
			        				        
			        
			        for(int j = 0; j<=8; j++){
			    		header.getCell(j).setCellStyle(style);
			            sheet.setColumnWidth(j, 8000); 
			            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			        }
			        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllBillPaymentDetailAccountWise(monthToShow);
			        int count = 1;
			        for (Map<String, Object> map : result) {
			        	System.out.println(":::::::::::::::::::000"+map.get("propertyNo"));
			        	System.out.println(map.get("accountNo"));
			        	XSSFRow row = sheet.createRow(count);
			        	
			        	
						    row.createCell(0).setCellValue(String.valueOf(count));
						    row.createCell(1).setCellValue((String) map.get("block"));	
							row.createCell(2).setCellValue((String) map.get("propertyNo"));
							row.createCell(3).setCellValue((String) map.get("personName"));	
							row.createCell(4).setCellValue((String) map.get("accountNo"));
							if(map.containsKey("typeOfService")){
								row.createCell(5).setCellValue((String) map.get("typeOfService"));
								}
							
							if(map.containsKey("billAmount")){
								row.createCell(6).setCellValue((Double) map.get("billAmount"));
								}else{
									row.createCell(6).setCellValue(0);	
								}
							if(map.containsKey("dueAmount")){
								row.createCell(7).setCellValue((Double) map.get("dueAmount"));
								}else{
									row.createCell(7).setCellValue(0);	
								}
							
							if(map.containsKey("totalbillamount")){
								row.createCell(8).setCellValue((Double) map.get("totalbillamount"));
								}else{
									row.createCell(8).setCellValue(0);	
								}

						
					count++;
			        }
			
			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	wb.write(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
					servletOutputStream = response.getOutputStream();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "inline;filename=\"Member Recivable By Account.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
	}
		//getAllBillPaymentDetail(monthToShow);
		
		if(id==7){
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			//List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	header.createCell(0).setCellValue("Sl NO");
				    	header.createCell(1).setCellValue("Block");
				        header.createCell(2).setCellValue("Property No");
				        header.createCell(3).setCellValue("Customer Name");
				        header.createCell(4).setCellValue("Account No");			        
				        header.createCell(5).setCellValue("Service Type"); 
				        header.createCell(6).setCellValue("Bill Amount");
				        header.createCell(7).setCellValue("Arrear Amount");
				        header.createCell(8).setCellValue("Total Amount");
				        for(int j = 0; j<=8; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllBillPaymentDetail(monthToShow);
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("propertyNo"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
				        	
	
				        	
				        	
			  row.createCell(0).setCellValue(String.valueOf(count));
			  row.createCell(1).setCellValue((String) map.get("blocks"));	
			  row.createCell(2).setCellValue((String) map.get("propertyNo"));
			  row.createCell(3).setCellValue((String) map.get("personName"));	
			  row.createCell(4).setCellValue((String) map.get("accountNo"));
								if(map.containsKey("typeOfService")){
									row.createCell(5).setCellValue((String) map.get("typeOfService"));
									}
								
								if(map.containsKey("billAmount")){
									row.createCell(6).setCellValue((Double) map.get("billAmount"));
									}else{
										row.createCell(6).setCellValue(0);	
									}
								if(map.containsKey("arrearsAmount")){
									row.createCell(7).setCellValue((Double) map.get("arrearsAmount"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								
								if(map.containsKey("netAmount")){
									row.createCell(8).setCellValue((Double) map.get("netAmount"));
									}else{
										row.createCell(8).setCellValue(0);	
									}

							
						count++;
				        }
				
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Resident Payment Posting MonthWise.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}
		
		//
		
		if(id==10){
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			//List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
				        logger.info("*****person Size in Vehicle Owner Contact Type***********");
				        String sheetName = "Templates";//name of sheet

				    	XSSFWorkbook wb = new XSSFWorkbook();
				    	XSSFSheet sheet = wb.createSheet(sheetName) ;
				    	
				    	XSSFRow header = sheet.createRow(0);    	
				    	
				    	XSSFCellStyle style = wb.createCellStyle();
				    	style.setWrapText(true);
				    	XSSFFont font = wb.createFont();
				    	font.setFontName(HSSFFont.FONT_ARIAL);
				    	font.setFontHeightInPoints((short)10);
				    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				    	style.setFont(font);
				    	header.createCell(0).setCellValue("Sl NO");
				    	header.createCell(1).setCellValue("Block");
				        header.createCell(2).setCellValue("Property No");
				        header.createCell(3).setCellValue("Customer Name");
				        header.createCell(4).setCellValue("Account No");			        
				        header.createCell(5).setCellValue("Service Type");
				        header.createCell(6).setCellValue("Bill Date");
				        header.createCell(7).setCellValue("Bill Due Date");
				        header.createCell(8).setCellValue("Bill Amount");
				        header.createCell(9).setCellValue("Payment Amount");
				        header.createCell(10).setCellValue("Due Amount");
				        for(int j = 0; j<=10; j++){
				    		header.getCell(j).setCellStyle(style);
				            sheet.setColumnWidth(j, 8000); 
				            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
				        }
				        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllOpenResidentInvoice(monthToShow);
				        int count = 1;
				        for (Map<String, Object> map : result) {
				        	System.out.println(":::::::::::::::::::000"+map.get("propertyNo"));
				        	System.out.println(map.get("accountNo"));
				        	XSSFRow row = sheet.createRow(count);
			      	
			  row.createCell(0).setCellValue(String.valueOf(count));
			  row.createCell(1).setCellValue((String) map.get("blocks"));	
			  row.createCell(2).setCellValue((String) map.get("propertyNo"));
			  row.createCell(3).setCellValue((String) map.get("personName"));	
			  row.createCell(4).setCellValue((String) map.get("accountNo"));
								if(map.containsKey("typeOfService")){
									row.createCell(5).setCellValue((String) map.get("typeOfService"));
									}
								
								if(map.containsKey("billDate")){
									row.createCell(6).setCellValue((String) map.get("billDate"));
									}
								
								if(map.containsKey("billdueDate")){
									row.createCell(7).setCellValue((String) map.get("billdueDate"));
									}
								
								if(map.containsKey("totalbillamount")){
									row.createCell(8).setCellValue((Double) map.get("totalbillamount"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
								if(map.containsKey("paymentAmount")){
									row.createCell(9).setCellValue((Integer) map.get("paymentAmount"));
									}else{
										row.createCell(9).setCellValue(0);	
									}
								
								if(map.containsKey("dueAmount")){
									row.createCell(10).setCellValue((Double) map.get("dueAmount"));
									}else{
										row.createCell(10).setCellValue(0);	
									}

							
						count++;
				        }
				
				    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
				    	wb.write(fileOut);
				    	fileOut.flush();
				    	fileOut.close();
						servletOutputStream = response.getOutputStream();
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "inline;filename=\"Open Resident Invoice.xlsx"+"\"");
						FileInputStream input = new FileInputStream(fileName);
						IOUtils.copy(input, servletOutputStream);
						//servletOutputStream.w
						servletOutputStream.flush();
						servletOutputStream.close();
		}
		
		
		
		return null;
	}
	
	@RequestMapping(value = "/paymentDue/exportExcel/{id}/{month}", method = {RequestMethod.POST,RequestMethod.GET})
	public String getDueReport(HttpServletRequest request,HttpServletResponse response,@PathVariable int id,@PathVariable String month) throws IOException, ParseException
	{
		
		ServletOutputStream servletOutputStream=null;
		
		logger.info("*********Income Tracker Report Two**********");
		if(id==11)
		{
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
					
		Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
		
		//List<?> allpaymentdetails=paymentService.getAllBillPaymentDetail(monthToShow);
					
					
			        logger.info("*****monthToShow***********"+monthToShow);
			        String sheetName = "Templates";//name of sheet

			    	XSSFWorkbook wb = new XSSFWorkbook();
			    	XSSFSheet sheet = wb.createSheet(sheetName) ;
			    	
			    	XSSFRow header = sheet.createRow(0);    	
			    	
			    	XSSFCellStyle style = wb.createCellStyle();
			    	style.setWrapText(true);
			    	XSSFFont font = wb.createFont();
			    	font.setFontName(HSSFFont.FONT_ARIAL);
			    	font.setFontHeightInPoints((short)10);
			    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			    	style.setFont(font);
			    	header.createCell(0).setCellValue("Sl NO");
			    	header.createCell(1).setCellValue("Block");
			        header.createCell(2).setCellValue("Property No");
			        header.createCell(3).setCellValue("Customer Name");
			        header.createCell(4).setCellValue("Account No");			        
			        header.createCell(5).setCellValue("Service Type"); 
			        header.createCell(6).setCellValue("Due Amount");
			    
			       
			        				        
			        
			        for(int j = 0; j<=6; j++){
			    		header.getCell(j).setCellStyle(style);
			            sheet.setColumnWidth(j, 8000); 
			            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			        }
			        List<Map<String, Object>> result=(List<Map<String, Object>>) paymentService.getAllOpenPaymentDueData(monthToShow);
			       logger.info("result::::::::"+result.size());
			        int count = 1;
			        for (Map<String, Object> map : result) {
			        	System.out.println(":::::::::::::::::::000"+map.get("propertyNo"));
			        	System.out.println(map.get("accountNo"));
			        	XSSFRow row = sheet.createRow(count);
						row.createCell(0).setCellValue(String.valueOf(count));
			        	
						   row.createCell(1).setCellValue((String) map.get("blocks"));
							row.createCell(2).setCellValue((String) map.get("propertyNo"));						
							
							row.createCell(3).setCellValue((String) map.get("personName"));				
							
							row.createCell(4).setCellValue((String) map.get("accountNo"));
								
							if(map.containsKey("typeOfService")){
								row.createCell(5).setCellValue((String) map.get("typeOfService"));
								}
							
							if(map.containsKey("dueAmount")){
								row.createCell(6).setCellValue((Double) map.get("dueAmount"));
								}else{
									row.createCell(6).setCellValue(0);	
								}
							
							
					count++;
			        }
			
			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	wb.write(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
					servletOutputStream = response.getOutputStream();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "inline;filename=\"Payment Due Month Wise.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
	}
		return null;
	}
}
