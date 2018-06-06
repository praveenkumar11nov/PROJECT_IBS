package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class BillingParameterMasterController extends FilterUnit{
	
	static Logger logger = LoggerFactory.getLogger(BillingParameterMasterController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	BillingParameterMasterService bpmService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService;
	
	@Resource
	private ValidationUtil validationUtil;
	
	@RequestMapping(value = "/billingparameter")
	public String service(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Masters", 1, request);
		breadCrumbService.addNode("Manage Billing Parameter Master", 2, request);
		return "servicetasks/billingparameter";
	}
	
	@RequestMapping(value = "/billingparameter/read", method = RequestMethod.POST)
	public @ResponseBody List<?> read() {
		logger.info("In Bills Parameter Read Method");
		return bpmService.findAll();
	}

	@RequestMapping(value = "/billingParameterMaster/billingMaster/{bvmId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	  public void metersStatus(@PathVariable int bvmId,@PathVariable String operation, HttpServletResponse response) {
	   
	   logger.info("In Service  Status Change Method");
	   if (operation.equalsIgnoreCase("activate"))
		   bpmService.setBillingParameterStatus(bvmId,operation, response);
	   else
		   bpmService.setBillingParameterStatus(bvmId,operation, response);
	  }
 
	@SuppressWarnings("serial")
	@RequestMapping(value = "/billingparameter/saveorupadteordelete/{action}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	List<?> saveServiceParameterMaster(@ModelAttribute("billingParameterMaster") BillParameterMasterEntity billingParameterMaster,
			BindingResult bindingResult, @PathVariable String action) {
		if (action.equalsIgnoreCase("create")) {
			billingParameterMaster.setStatus("Inactive");
			bpmService.save(billingParameterMaster);
		} else if (action.equalsIgnoreCase("update")) {
			bpmService.update(billingParameterMaster);
		} else {
			/*bpmService.delete(billingParameterMaster.getBvmId());*/
		}
		List<BillParameterMasterEntity> billParameterMasterEntitiesList = new ArrayList<>();
		billParameterMasterEntitiesList.add(billingParameterMaster);
		//return bpmService.setResponse();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final BillParameterMasterEntity record : billParameterMasterEntitiesList) {
			result.add(new HashMap<String, Object>() {
				{
					put("bvmId", record.getBvmId());
					put("serviceType", record.getServiceType());
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDT", record.getLastUpdatedDT());
					put("bvmDataType", record.getBvmDataType());
					put("bvmDescription", record.getBvmDescription());
					put("bvmName", record.getBvmName());
					put("bvmSequence", record.getBvmSequence());
					put("status", record.getStatus());
				}
			});

		}
		return result;
	}
	
	@RequestMapping(value = "/billingparameter/billParameterDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteBillParameter(@ModelAttribute("billParameterMasterEntity") BillParameterMasterEntity billParameterMasterEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In delete bill parameter destroy method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if (billParameterMasterEntity.getStatus().equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				/**
					 * 
					 */
				private static final long serialVersionUID = 1L;

				{
					put("AciveParameterDestroyError", messageSource.getMessage("ServiceParameterMaster.AciveParameterDestroyError", null,
							locale));
				}
			};
			errorResponse.setStatus("AciveParameterDestroyError");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		} else {
			try {
				bpmService.delete(billParameterMasterEntity.getBvmId());
			} catch (Exception e) {

				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return billParameterMasterEntity;
		}
	}
	
	@RequestMapping(value = "/billingparameter/getUniqueParamName/{className}/{attribute}/{attribute1}/{ServiceType}", method = { RequestMethod.GET,	RequestMethod.POST })
	public @ResponseBody List<?> getUniqueParamName(@PathVariable String className,@PathVariable String attribute,@PathVariable String attribute1,@PathVariable String ServiceType) throws Exception {
		logger.info("In billing parameter getUniqueParamName method");
		return bpmService.getUniqueParamName(className,attribute,attribute1,ServiceType);
	}
	
	@RequestMapping(value = "/billingparameter/getMeterFixedDate/{className}/{attribute}/{attribute1}/{ServiceType}/{attribute2}/{accounId}", method = { RequestMethod.GET,	RequestMethod.POST })
	public @ResponseBody java.sql.Date getMeterFixedDate(@PathVariable String className,@PathVariable String attribute,@PathVariable String attribute1,@PathVariable String ServiceType,@PathVariable String attribute2,@PathVariable int accounId) throws Exception {
		logger.info("In billing parameter getUniqueParamName method");
		
		return (java.sql.Date) bpmService.getMeterFixedDate(className,attribute,attribute1,ServiceType,attribute2,accounId);
	}
	// ****************************** Billing Parameter Master Filters Data methods ********************************/

		@RequestMapping(value = "/billingParameterMaster/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> billingParameterMaster(@PathVariable String feild) {
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("BillParameterMasterEntity",attributeList, null));
			
			return set;
		}
		
		@RequestMapping(value = "/billingTemplate/billingTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportFileBill(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
	       
			List<BillParameterMasterEntity> billTemplateEntities =bpmService.findAllBill();
		
	               
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
	        header.createCell(1).setCellValue("Sequence");
	        header.createCell(2).setCellValue("Data Type");
	        header.createCell(3).setCellValue("Parameter Name");
	        header.createCell(4).setCellValue("Description");
	        header.createCell(5).setCellValue("Status");       
	        
	    
	        for(int j = 0; j<=5; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:F200"));
	        }
	        
	        int count = 1;
	        //XSSFCell cell = null;
	    	for (BillParameterMasterEntity billParameter :billTemplateEntities ) {
	    		
	    		XSSFRow row = sheet.createRow(count);
	    		
	    		String str="";
	    		 if(billParameter.getServiceType()!=null){
	  				str=billParameter.getServiceType();
	  			}
	              else{
	              	str="";
	              }
	    		row.createCell(0).setCellValue(str);
	    		
	    		if(Integer.toString(billParameter.getBvmSequence())!=null){
	  				str=Integer.toString(billParameter.getBvmSequence());
	  			}
	              else{
	              	str="";
	              }
	    		
	    		row.createCell(1).setCellValue(str);
	    		if(billParameter.getBvmDataType()!=null){
	  				str=billParameter.getBvmDataType();
	  			}
	              else{
	              	str="";
	              }
	    		row.createCell(2).setCellValue(str);
	    		if(billParameter.getBvmName()!=null){
	  				str=billParameter.getBvmName();
	  			}
	              else{
	              	str="";
	              }
	    		row.createCell(3).setCellValue(str);
	    		if(billParameter.getBvmDescription()!=null){
	  				str=billParameter.getBvmDescription();
	  			}
	              else{
	              	str="";
	              }
	    		row.createCell(4).setCellValue(str);
	    		if(billParameter.getStatus()!=null){
	  				str=billParameter.getStatus();
	  			}
	              else{
	              	str="";
	              }
	    		row.createCell(5).setCellValue(str);
	    		
	    		
	    		count ++;
			}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"BillingParameterTemplates.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
		@RequestMapping(value = "/billingPdfTemplate/billingPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportFileBillPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
	        
	       
			List<BillParameterMasterEntity> billPdfTemplateEntities =bpmService.findAllBill();
		
	              
	      
	        Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        document.open();
	      
           
            PdfPTable table = new PdfPTable(6);
            Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
            Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
            
            table.addCell(new Paragraph("Service Type",font1));
            table.addCell(new Paragraph("Sequence",font1));
            table.addCell(new Paragraph("Data Type",font1));
            table.addCell(new Paragraph("Parameter Name",font1));
            table.addCell(new Paragraph("Description",font1));
            table.addCell(new Paragraph("Status",font1));
	        //XSSFCell cell = null;
	    	for (BillParameterMasterEntity billParameter :billPdfTemplateEntities ) {
	    		
	    		String str="";
	    		 if(billParameter.getServiceType()!=null){
	  				str=billParameter.getServiceType();
	  			}
	              else{
	              	str="";
	              }
	        PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
	        if(Integer.toString(billParameter.getBvmSequence())!=null){
  				str=Integer.toString(billParameter.getBvmSequence());
  			}
              else{
              	str="";
              }
    		
	   
            PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
            if(billParameter.getBvmDataType()!=null){
  				str=billParameter.getBvmDataType();
  			}
              else{
              	str="";
              }
        
            PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
            if(billParameter.getBvmName()!=null){
  				str=billParameter.getBvmName();
  			}
              else{
              	str="";
              }
           
            PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
            if(billParameter.getBvmDescription()!=null){
  				str=billParameter.getBvmDescription();
  			}
              else{
              	str="";
              }
            
       
            PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
            if(billParameter.getStatus()!=null){
  				str=billParameter.getStatus();
  			}
              else{
              	str="";
              }
          
            PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
           

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.setWidthPercentage(100);
            float[] columnWidths = {7f, 5f, 6f, 8f, 8f, 5f};

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
			response.setHeader("Content-Disposition", "inline;filename=\"BillingParameterTemplates.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
		

}
