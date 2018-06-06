package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.MeterParameterMaster;
import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.model.TransactionMasterEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Billing Master 
 * Use Case : Transaction Master
 * 
 * @author Ravi Shankar Reddy
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class TransactionMasterController {

static Logger logger = LoggerFactory.getLogger(TransactionMasterController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping("/transactionMaster")
	public String transactionMaster(HttpServletRequest request, Model model) {
		logger.info("In transaction master method");
		model.addAttribute("ViewName", "Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Masters", 1, request);
		breadCrumbService.addNode("Manage Transaction Master", 2, request);
		return "transactionMaster/transactionMaster";
	}
	
	 // ****************************** Meter Status Read,Create,Delete methods ********************************//

	@RequestMapping(value = "/transactionMaster/transactionMasterRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<TransactionMasterEntity> readMeterStatus() {
		logger.info("In read meter status method");
		List<TransactionMasterEntity> transactionMasterEntities = transactionMasterService.findAll();
		return transactionMasterEntities;
	}

	@RequestMapping(value = "/transactionMaster/transactionMasterCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object transactionMasterCreate(@ModelAttribute("transactionMasterEntity") TransactionMasterEntity transactionMasterEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

		logger.info("In save meter status method");
		
		if(!transactionMasterEntity.getTypeOfService().equalsIgnoreCase("CAM")){
			transactionMasterEntity.setCalculationBasis("Actual");
			transactionMasterEntity.setCamRate(0);
		}
		
		validator.validate(transactionMasterEntity, bindingResult);
		transactionMasterService.save(transactionMasterEntity);
		sessionStatus.setComplete();
		return transactionMasterEntity;
		
	}
	
	@RequestMapping(value = "/transactionMaster/transactionMasterUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editMeterStatus(@ModelAttribute("transactionMasterEntity") TransactionMasterEntity transactionMasterEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In edit meter status method");
		
		validator.validate(transactionMasterEntity, bindingResult);
		transactionMasterService.update(transactionMasterEntity);
		
		return transactionMasterEntity;
	}

	@RequestMapping(value = "/transactionMaster/transactionMasterDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteDepartmentAccessSettingsRead(@ModelAttribute("transactionMasterEntity") TransactionMasterEntity transactionMasterEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete meter status method");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			transactionMasterService.delete(transactionMasterEntity.getTransactionId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return transactionMasterEntity;
	}
	
	// ****************************** Transaction Master Filters Data methods ********************************/

			@RequestMapping(value = "/transactionMaster/filter/{feild}", method = RequestMethod.GET)
			public @ResponseBody Set<?> getOpenTicketContentsForFilter(@PathVariable String feild) {
				logger.info("In  transaction master use case filters Method");
				List<String> attributeList = new ArrayList<String>();
				attributeList.add(feild);
				HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("TransactionMasterEntity",attributeList, null));
				
				return set;
			}
			
			@RequestMapping(value = "/transactionTemplate/transactionTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
			public String exportFileTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
				
				
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
		        
		       
				List<TransactionMasterEntity> transactionTemplateEntities =transactionMasterService.findAllTransaction();
			
		               
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
		    	
		    	header.createCell(0).setCellValue("Service Type");
		        header.createCell(1).setCellValue("Group");
		        header.createCell(2).setCellValue("Calculation Basis");
		        header.createCell(3).setCellValue("Transaction Name");
		        header.createCell(4).setCellValue("Transaction Code");
		        header.createCell(5).setCellValue("CAM Rate"); 
		        header.createCell(6).setCellValue("Description"); 
		       
		        for(int j = 0; j<=6; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:G200"));
		        }
		        
		        int count = 1;
		        //XSSFCell cell = null;
		    	for (TransactionMasterEntity transaction :transactionTemplateEntities) {
		    		
		    		XSSFRow row = sheet.createRow(count);
		    		
		    		//int value = count+1;

		    		row.createCell(0).setCellValue(transaction.getTypeOfService());
		    		
		    		row.createCell(1).setCellValue(transaction.getGroupType());
		    		
		    	    row.createCell(2).setCellValue(transaction.getCalculationBasis());
		    		
		    		row.createCell(3).setCellValue(transaction.getTransactionName());
		    		row.createCell(4).setCellValue(transaction.getTransactionCode());
		    		row.createCell(5).setCellValue(transaction.getCamRate());
		    		row.createCell(6).setCellValue(transaction.getDescription());
		    		
		    		count ++;
				}
		    	
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"TransactionMasterTemplates.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
			
			@RequestMapping(value = "/transactionPdfTemplate/transactionPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
			public String exportFileTrnsactionMaster(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
				
				
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
		        
		       
				 
				List<TransactionMasterEntity> transactionTemplateEntities =transactionMasterService.findAllTransaction();
			
		              
		      
		        Document document = new Document();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        PdfWriter.getInstance(document, baos);
		        document.open();
		      
		       
		        PdfPTable table = new PdfPTable(7);
		        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
		        
		        table.addCell(new Paragraph("Service Type",font1));
		        table.addCell(new Paragraph("Group",font1));
		        table.addCell(new Paragraph("Calculation Basis",font1));
		        table.addCell(new Paragraph("Transaction Name",font1));
		        table.addCell(new Paragraph("Transaction Code",font1));
		        table.addCell(new Paragraph("CAM Rate",font1));
		        table.addCell(new Paragraph("Description",font1));
		        //XSSFCell cell = null;
		    	for (TransactionMasterEntity transaction :transactionTemplateEntities ) {
		    		
		        
		        PdfPCell cell1 = new PdfPCell(new Paragraph(transaction.getTypeOfService(),font3));
		   
		        PdfPCell cell2 = new PdfPCell(new Paragraph(transaction.getGroupType(),font3));
		    
		        PdfPCell cell3 = new PdfPCell(new Paragraph(transaction.getCalculationBasis(),font3));
		       
		        PdfPCell cell4 = new PdfPCell(new Paragraph(transaction.getTransactionName(),font3));
		   
		        PdfPCell cell5 = new PdfPCell(new Paragraph(transaction.getTransactionCode(),font3));
		        String str="";
		        double d=transaction.getCamRate();
		 
		        str=Double.toString(d);
		      
		        PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
		       
		        PdfPCell cell7 = new PdfPCell(new Paragraph(transaction.getDescription(),font3));
		       

		        table.addCell(cell1);
		        table.addCell(cell2);
		        table.addCell(cell3);
		        table.addCell(cell4);
		        table.addCell(cell5);
		        table.addCell(cell6);
		        table.addCell(cell7);
		        table.setWidthPercentage(100);
		        float[] columnWidths = {7f, 8f, 10f, 10f, 10f, 10f, 8f};

		        table.setWidths(columnWidths);
		    		
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
				response.setHeader("Content-Disposition", "inline;filename=\"TransactionMasterTemplates.pdf"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
			
}
