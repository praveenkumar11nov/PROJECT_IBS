package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.service.OnlinePaymentTransactionsService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class OnlineDashBoardTransactionController {
	
	static Logger logger = LoggerFactory
			.getLogger(OnlineTransactionController.class);
	@Autowired
	private OnlinePaymentTransactionsService onlinePaymentTransactionsService;
	
	
	
/***********************************************Dash Board Transaction**************************************************************/
	
	@RequestMapping(value="/paytmOnlineTransaction",method={RequestMethod.GET,RequestMethod.POST})
	public String paytmTransactionMethod(ModelMap model)
	{
		model.addAttribute("ViewName", "Paytm Transactions");
		return "paytmTransaction";
	}
	
	@RequestMapping(value="/payUOnlineTransaction",method={RequestMethod.GET,RequestMethod.POST})
	public String payUTransactionMethod(ModelMap model)
	{ model.addAttribute("ViewName", "PayU Transactions");
		return "payUTransaction";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paytmTransactionReadUrl/byMonth/{from}/{to}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readPaytmRecords(@PathVariable String from ,@PathVariable String to) throws ParseException
	{
    	logger.info("In Online Transaction payment by month ======================");
    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
    	Date dateFrom = formatter.parse(from);
    	Date dateTo = formatter.parse(to);
    	String fromDate=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
    	Calendar c = Calendar.getInstance();
    	c.setTime(dateTo);
    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    	String toDate=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
		List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate,toDate);
		for (final Object[] obj : onlinePaymentTransaction) {
			if(((String)obj[13]).startsWith("S"))
			{
			result.add(new HashMap<String, Object>() {
				{
					put("serviceType", (String)obj[1]);                            
				    put("transactionDate",  DateFormatUtils.format((Date) obj[4], 
							"dd/MM/yyyy"));                                        
				    put("paymentStatus", (String)obj[3]);                          
				    put("transactionAmount", (String)obj[5]);                     
				    
				    //put("payumoneyId",(String)obj[0]);
				   put("TransactionId",(String)obj[2]);                            
				   put("personName",(String)obj[7]+" "+(String)obj[8]);           
				   put("accountNo",(String)obj[9]);                               
				   put("propertyNo",(String)obj[6]);                              
				   put("onlineid",(Integer)obj[10]);                              
				   put("orderId",(String)obj[11]);                                
				   put("failedReason",(String)obj[12]);                            
				   put("merchantId",(String)obj[13]);                             
				}
			});
			
			}

		}
		return result;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payUTransactionReadUrl/byMonth/{from}/{to}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readPayURecords(@PathVariable String from ,@PathVariable String to) throws ParseException
	{
    	logger.info("In Online Transaction payment by month ======================");
    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
    	Date dateFrom = formatter.parse(from);
    	Date dateTo = formatter.parse(to);
    	String fromDate=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
    	Calendar c = Calendar.getInstance();
    	c.setTime(dateTo);
    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    	String toDate=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
		List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate,toDate);
		for (final Object[] obj : onlinePaymentTransaction) {
			if(((String)obj[13]).startsWith("n"))
			{
			result.add(new HashMap<String, Object>() {
				{
				   put("serviceType", (String)obj[1]);                            
				   put("transactionDate",  DateFormatUtils.format((Date) obj[4],"dd/MM/yyyy"));                                       
			
				   put("paymentStatus", (String)obj[3]);                          
				   put("transactionAmount", (String)obj[5]);                      
				    
				   put("payumoneyId",(String)obj[0]);
				   put("TransactionId",(String)obj[2]);                           
				   put("personName",(String)obj[7]+" "+(String)obj[8]);           
				   put("accountNo",(String)obj[9]);                              
				   put("propertyNo",(String)obj[6]);                               
				   put("onlineid",(Integer)obj[10]);                              
				   put("orderId",(String)obj[11]);                                 
				   put("failedReason",(String)obj[12]);                            
				   put("merchantId",(String)obj[13]);                             
				}
			});
			
			}

		}
		return result;
	}
	
	
	/*++++++++++++++++++++++++++++++++++++++ Export to Excel for Online Transaction(PAYTM)(Vijju)  ++++++++++++++++++++++++++++++++++++++*/
	 
	 @RequestMapping(value = "/onlineTransaction/exportonlineTransactionDataToExxcel/{fromDate}/{toDate}", method = {RequestMethod.POST,RequestMethod.GET})
		   public @ResponseBody List<?> exportTransactionMethod(@PathVariable String fromDate ,@PathVariable String toDate,HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
				
				
			 logger.info("---------------Hello Excel  ----------------");
		 System.out.println("Exporting Online Transaction Details");
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+".xlsx";
		        
			
		               
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
		    	
		        header.createCell(0).setCellValue("Transaction Date");
		        header.createCell(1).setCellValue("Account No");
		        header.createCell(2).setCellValue("Person Name");
		        header.createCell(3).setCellValue("Property No");
		        header.createCell(4).setCellValue("Transaction Id");
		        header.createCell(5).setCellValue("Transaction Amount");
		        header.createCell(6).setCellValue("Service Type");
		        header.createCell(7).setCellValue("Payment Status");
		        header.createCell(8).setCellValue("Merchant Id");
		        header.createCell(9).setCellValue("Order Id");
		        header.createCell(10).setCellValue("Failed Reason");
		        
		   
		        for(int j = 0; j<=10; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
		        }
		        
		        int count = 1;
		        
		        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
		    	Date dateFrom = formatter.parse(fromDate);
		    	Date dateTo = formatter.parse(toDate);
		    	String fromDate1=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
		    	Calendar c = Calendar.getInstance();
		    	c.setTime(dateTo);
		    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		    	String toDate1=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
				
		    	@SuppressWarnings("unchecked")
			
		    	List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate1,toDate1);
				for (final Object[] obj : onlinePaymentTransaction) {
				
					if(((String)obj[13]).startsWith("S"))
					{
			        	XSSFRow row = sheet.createRow(count);
			   
			    		row.createCell(0).setCellValue((String)DateFormatUtils.format((Date) obj[4],"dd/MM/yyyy"));
			    		row.createCell(1).setCellValue((String)obj[9]);
			    		
			    		row.createCell(2).setCellValue((String)obj[7]+" "+(String)obj[8]);
			    		
			    		row.createCell(3).setCellValue((String)obj[6]);
			    		
			    		row.createCell(4).setCellValue((String)obj[2]);
			    		
			    		row.createCell(5).setCellValue((String)obj[5]);
			    		
			    		row.createCell(6).setCellValue((String)obj[1]);
			    		
			    		row.createCell(7).setCellValue((String)obj[3]);
			    		
			    		row.createCell(8).setCellValue((String)obj[13]);
			    		
			    		row.createCell(9).setCellValue((String)obj[11]);
			    		
			    		row.createCell(10).setCellValue((String)obj[12]);
			    		count++;
				}
		    		
				}
		    	
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"TransactionData"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
	 
	 
/*++++++++++++++++++++++++++++++++++++++ Export to PDF for Online Transaction(PAYTM)(Vijju)  ++++++++++++++++++++++++++++++++++++++*/

	 @RequestMapping(value="/onlineTransaction/exportonlineTransactionDataToPDF/{fromPdf}/{toPdf}",method={RequestMethod.POST,RequestMethod.GET})
	   public @ResponseBody List<?> exportTransactionPDFMethod(@PathVariable String fromPdf ,@PathVariable String toPdf,HttpServletRequest request,HttpServletResponse response) throws ParseException, DocumentException, MalformedURLException, IOException{
			
		 logger.info("in side Export PDF");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
			
		 
			
			 Document document = new Document(PageSize.A4, 20, 20, 50, 50);
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     PdfWriter writer=PdfWriter.getInstance(document, baos);
		     Rectangle rect = new Rectangle(30, 30, 300, 810);
		     writer.setBoxSize("art", rect);
		     
		   
		        document.open(); 
		        Paragraph p = new Paragraph(" ");
		        p.setAlignment(Element.ALIGN_CENTER);
		        document.add(p);
		       
		        
		        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
				    	Date dateFrom = formatter.parse(fromPdf);
				    	Date dateTo = formatter.parse(toPdf);
				    	String fromDate1=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
				    	Calendar c = Calendar.getInstance();
				    	c.setTime(dateTo);
				    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				    	String toDate1=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
						
				    	@SuppressWarnings("unchecked")
					
				    	List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate1,toDate1);
						
			
		        document.add(Chunk.SPACETABBING);
		        PdfPTable table = new PdfPTable(11);
		        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
		        
		        table.addCell(new Paragraph("Transaction Date",font1));
		        table.addCell(new Paragraph("Account No",font1));
		        table.addCell(new Paragraph("Person Name",font1));
		        table.addCell(new Paragraph("Property No",font1));
		        table.addCell(new Paragraph("Transaction Id",font1));
		        table.addCell(new Paragraph("Transaction Amount",font1));
		        table.addCell(new Paragraph("Service Type",font1));
		        table.addCell(new Paragraph("Payment Status",font1));
		        table.addCell(new Paragraph("Merchant Id",font1));
		        table.addCell(new Paragraph("Order Id",font1));
		        table.addCell(new Paragraph("Failed Reason",font1));
		        
		       
		
		        for (final Object[] obj : onlinePaymentTransaction) {
					
					if(((String)obj[13]).startsWith("S"))
					{
					
		    		
		        PdfPCell cell1 = new PdfPCell(new Paragraph((String)DateFormatUtils.format((Date) obj[4],"dd/MM/yyyy"),font3));
		        PdfPCell cell2 = new PdfPCell(new Paragraph((String)obj[9],font3));
		        PdfPCell cell3 = new PdfPCell(new Paragraph((String)obj[7]+" "+(String)obj[8],font3));
		        PdfPCell cell4 = new PdfPCell(new Paragraph((String)obj[6],font3));
		        PdfPCell cell5 = new PdfPCell(new Paragraph((String)obj[2],font3));
		        PdfPCell cell6 = new PdfPCell(new Paragraph((String)obj[5],font3));
		        PdfPCell cell7 = new PdfPCell(new Paragraph((String)obj[1],font3));
		        PdfPCell cell8 = new PdfPCell(new Paragraph((String)obj[3],font3));
		        PdfPCell cell9 = new PdfPCell(new Paragraph((String)obj[13],font3));
		        PdfPCell cell10 = new PdfPCell(new Paragraph((String)obj[11],font3));
		        PdfPCell cell11 = new PdfPCell(new Paragraph((String)obj[12],font3));
		        
		        table.addCell(cell1);
		        table.addCell(cell2);
		        table.addCell(cell3);
		        table.addCell(cell4);
		        table.addCell(cell5);
		        table.addCell(cell6);
		        table.addCell(cell7);
		        table.addCell(cell8);
		        table.addCell(cell9);
		        table.addCell(cell10);
		        table.addCell(cell11);
		    
		        
		        table.setWidthPercentage(100);
		        float[] columnWidths = {8f,10f,9f,9f,10f,8f,8f,10f,10f,8f,10f};

		        table.setWidths(columnWidths);
		    		
				}
		        
		        }
		         document.add(table);
		        document.close();

		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	baos.writeTo(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline;filename=\"PayU Transaction.pdf"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
			return null;
		}
	 
/*++++++++++++++++++++++++++++++++++++++ Export to Excel for Online Transaction(PAYU)(Vijju)  ++++++++++++++++++++++++++++++++++++++*/
	 
	 @RequestMapping(value = "/onlineTransaction/exportonlineTransactionDataToExxcelForPayU/{fromPayuDate}/{topayuDate}", method = {RequestMethod.POST,RequestMethod.GET})
		   public @ResponseBody List<?> exportTransactionMethodForPayu(@PathVariable String fromPayuDate ,@PathVariable String topayuDate,HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
				
				
			 logger.info("---------------Hello Excel  ----------------");
		 System.out.println("Exporting Online Transaction Details");
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+".xlsx";
		        
			
		               
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
		    	
		        header.createCell(0).setCellValue("Transaction Date");
		        header.createCell(1).setCellValue("Account No");
		        header.createCell(2).setCellValue("Person Name");
		        header.createCell(3).setCellValue("Property No");
		        header.createCell(4).setCellValue("Transaction Id");
		        header.createCell(5).setCellValue("payumoney Id");
		        header.createCell(6).setCellValue("Transaction Amount");
		        header.createCell(7).setCellValue("Service Type");
		        header.createCell(8).setCellValue("Payment Status");
		        header.createCell(9).setCellValue("Merchant Id");
		        header.createCell(10).setCellValue("Order Id");
		        header.createCell(11).setCellValue("Failed Reason");
		        
		   
		        for(int j = 0; j<=11; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
		        }
		        
		        int count = 1;
		        
		        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
		    	Date dateFrom = formatter.parse(fromPayuDate);
		    	Date dateTo = formatter.parse(topayuDate);
		    	String fromDate1=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
		    	Calendar c = Calendar.getInstance();
		    	c.setTime(dateTo);
		    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		    	String toDate1=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
				
		    	@SuppressWarnings("unchecked")
			
		    	List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate1,toDate1);
				for (final Object[] obj : onlinePaymentTransaction) {
				
					if(((String)obj[13]).startsWith("n"))
					{
			        	XSSFRow row = sheet.createRow(count);
			   
			    		row.createCell(0).setCellValue((String)DateFormatUtils.format((Date) obj[4],"dd/MM/yyyy"));
			    		row.createCell(1).setCellValue((String)obj[9]);
			    		
			    		row.createCell(2).setCellValue((String)obj[7]+" "+(String)obj[8]);
			    		
			    		row.createCell(3).setCellValue((String)obj[6]);
			    		
			    		row.createCell(4).setCellValue((String)obj[2]);
			    		 
			    		row.createCell(5).setCellValue((String)obj[0]);
			    		
			    		row.createCell(6).setCellValue((String)obj[5]);
			    		
			    		row.createCell(7).setCellValue((String)obj[1]);
			    		
			    		row.createCell(8).setCellValue((String)obj[3]);
			    		
			    		row.createCell(9).setCellValue((String)obj[13]);
			    		
			    		row.createCell(10).setCellValue((String)obj[11]);
			    		
			    		row.createCell(11).setCellValue((String)obj[12]);
			    		count++;
				}
		    		
				}
		    	
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"TransactionDataPayU"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
	 
	 
/*++++++++++++++++++++++++++++++++++++++ Export to PDF for Online Transaction(PAYU)(Vijju)  ++++++++++++++++++++++++++++++++++++++*/

	 @RequestMapping(value="/onlineTransaction/exportonlineTransactionDataToPDFForPayU/{fromPdf3}/{toPdf3}",method={RequestMethod.POST,RequestMethod.GET})
	   public @ResponseBody List<?> exportTransactionPDFMethodForPayU(@PathVariable String fromPdf3 ,@PathVariable String toPdf3,HttpServletRequest request,HttpServletResponse response) throws ParseException, DocumentException, MalformedURLException, IOException{
			
		 logger.info("in side Export PDF");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
			
		 
			
			 Document document = new Document(PageSize.A4, 20, 20, 50, 50);
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     PdfWriter writer=PdfWriter.getInstance(document, baos);
		     Rectangle rect = new Rectangle(30, 30, 300, 810);
		     writer.setBoxSize("art", rect);
		     
		   
		        document.open(); 
		        Paragraph p = new Paragraph(" ");
		        p.setAlignment(Element.ALIGN_CENTER);
		        document.add(p);
		       
		        
		        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				    	SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
				    	Date dateFrom = formatter.parse(fromPdf3);
				    	Date dateTo = formatter.parse(toPdf3);
				    	String fromDate1=DateFormatUtils.format((Date)dateFrom,"yyyy/MM/dd");
				    	Calendar c = Calendar.getInstance();
				    	c.setTime(dateTo);
				    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				    	String toDate1=DateFormatUtils.format((Date)c.getTime(),"yyyy/MM/dd");
						
				    	@SuppressWarnings("unchecked")
					
				    	List<Object[]> onlinePaymentTransaction=(List<Object[]>) onlinePaymentTransactionsService.getTransactionDetailsByAccountId(fromDate1,toDate1);
						
			
		        document.add(Chunk.SPACETABBING);
		        PdfPTable table = new PdfPTable(12);
		        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
		        
		        table.addCell(new Paragraph("Transaction Date",font1));
		        table.addCell(new Paragraph("Account No",font1));
		        table.addCell(new Paragraph("Person Name",font1));
		        table.addCell(new Paragraph("Property No",font1));
		        table.addCell(new Paragraph("Transaction Id",font1));
		        table.addCell(new Paragraph("PayuMoney Id",font1));
		        table.addCell(new Paragraph("Transaction Amount",font1));
		        table.addCell(new Paragraph("Service Type",font1));
		        table.addCell(new Paragraph("Payment Status",font1));
		        table.addCell(new Paragraph("Merchant Id",font1));
		        table.addCell(new Paragraph("Order Id",font1));
		        table.addCell(new Paragraph("Failed Reason",font1));
		        
		       
		
		        for (final Object[] obj : onlinePaymentTransaction) {
					
					if(((String)obj[13]).startsWith("n"))
					{
					
		    		
		        PdfPCell cell1 = new PdfPCell(new Paragraph((String)DateFormatUtils.format((Date) obj[4],"dd/MM/yyyy"),font3));
		        PdfPCell cell2 = new PdfPCell(new Paragraph((String)obj[9],font3));
		        PdfPCell cell3 = new PdfPCell(new Paragraph((String)obj[7]+" "+(String)obj[8],font3));
		        PdfPCell cell4 = new PdfPCell(new Paragraph((String)obj[6],font3));
		        PdfPCell cell5 = new PdfPCell(new Paragraph((String)obj[2],font3));
		        PdfPCell cell6 = new PdfPCell(new Paragraph((String)obj[0],font3));
		        PdfPCell cell7 = new PdfPCell(new Paragraph((String)obj[5],font3));
		        PdfPCell cell8 = new PdfPCell(new Paragraph((String)obj[1],font3));
		        PdfPCell cell9 = new PdfPCell(new Paragraph((String)obj[3],font3));
		        PdfPCell cell10 = new PdfPCell(new Paragraph((String)obj[13],font3));
		        PdfPCell cell11 = new PdfPCell(new Paragraph((String)obj[11],font3));
		        PdfPCell cell12 = new PdfPCell(new Paragraph((String)obj[12],font3));
		        
		        table.addCell(cell1);
		        table.addCell(cell2);
		        table.addCell(cell3);
		        table.addCell(cell4);
		        table.addCell(cell5);
		        table.addCell(cell6);
		        table.addCell(cell7);
		        table.addCell(cell8);
		        table.addCell(cell9);
		        table.addCell(cell10);
		        table.addCell(cell11);
		        table.addCell(cell12);
		    
		        
		        table.setWidthPercentage(100);
		        float[] columnWidths = {8f,9f,8f,8f,9f,8f,8f,9f,9f,8f,8f,8f};

		        table.setWidths(columnWidths);
		    		
				}
		        
		        }
		         document.add(table);
		        document.close();

		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	baos.writeTo(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline;filename=\"PayUPdf.pdf"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
			return null;
		}
	
	
}
