package com.bcits.bfm.controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.BillingAndPaymentHelpDesk;
import com.bcits.bfm.service.BillingAndPaymentHelpDeskService;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.bea.xml.stream.samples.Parse;

@Controller
public class BillingAndPaymentHelpDeskController {

	private static final Log logger = LogFactory.getLog(BillingAndPaymentHelpDeskController.class);
	
	@Autowired
	private BillingAndPaymentHelpDeskService bHelpDeskService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@RequestMapping(value="/helpdesk")
	public String helpDeskIndex(Model model,HttpServletRequest request){
		//logger.info("in side helpDeskIndex******************************************");
		model.addAttribute("ViewName", "Meter Assignment");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Prepaid Billing", 1, request);
		breadCrumbService.addNode("Manage Meter Assignment", 2, request);
		model.addAttribute("bHelpDesk", new BillingAndPaymentHelpDesk());
		return "BillingAndPaymentHelpDesk";
	}
	
	@RequestMapping(value="/billingAndPaymentHelpDesk/helpDesk", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<?> readData(){
		return bHelpDeskService.getAllData();
	}
	
	
	@RequestMapping(value="/saveHelpDeskData/{urId}", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Object update(@PathVariable int urId,HttpServletRequest request) throws ParseException
	{
		logger.info("in side update methode*****************");
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		
		BillingAndPaymentHelpDesk bHelpDesk=bHelpDeskService.find(urId);		
		bHelpDesk.setHelpTopic(request.getParameter("string"));
		bHelpDesk.setOtherHelpTopic(request.getParameter("otherTopics"));
		bHelpDesk.setIssue_Details(request.getParameter("details"));
		bHelpDesk.setStatus(request.getParameter("status"));
		bHelpDesk.setRemarks(request.getParameter("remarks"));
		if(request.getParameter("responseDate")!=""){
			Date d1=dateFormat.parse(request.getParameter("responseDate"));
			bHelpDesk.setReSolvedDate(d1);
			}
		
		bHelpDeskService.update(bHelpDesk);
		return null;
	}
	
	@RequestMapping(value = "/QueryTemplate/QueryTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
   public String exportCVSVisitorFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
	 logger.info("hi_________________________----------------");
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> queryTemplateEntities = bHelpDeskService.fetchData();
	
               
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
    	
        header.createCell(0).setCellValue("Person Name");
        header.createCell(1).setCellValue("Property Number");
        header.createCell(2).setCellValue("Query Topics");
        header.createCell(3).setCellValue("Other Query Topics");
        header.createCell(4).setCellValue("Issue Details");
        header.createCell(5).setCellValue("Status");
        header.createCell(6).setCellValue("Remarks");
        header.createCell(7).setCellValue("Created Date");
        header.createCell(8).setCellValue("Resolved Date");
        header.createCell(9).setCellValue("Customer Mobile Number");
        header.createCell(10).setCellValue("Customer Email ID");
        
       
       
        for(int j = 0; j<=6; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
        }
        
        int count = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
    	for (Object[] queryDetailsEntity :queryTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		
    		row.createCell(0).setCellValue((String)queryDetailsEntity[2]);
    		row.createCell(1).setCellValue((String)queryDetailsEntity[1]);
    		
    		
    	    row.createCell(2).setCellValue((String)queryDetailsEntity[3]);
    		
    		row.createCell(3).setCellValue((String)queryDetailsEntity[5]);
    		row.createCell(4).setCellValue((String)queryDetailsEntity[4]);
    		row.createCell(5).setCellValue((String)queryDetailsEntity[7]);
    		if(((Timestamp)queryDetailsEntity[6])!=null){
    	    row.createCell(7).setCellValue(sdf.format((Timestamp)queryDetailsEntity[6]));
    		}else{
    			row.createCell(7).setCellValue("");
    		}
    		
    		if(queryDetailsEntity[9]!=null){
    	    row.createCell(8).setCellValue(sdf.format((Timestamp)queryDetailsEntity[9]));
    		}else{
    			row.createCell(8).setCellValue("");
    		}
    	    row.createCell(9).setCellValue((String)queryDetailsEntity[10]);
    	    row.createCell(10).setCellValue((String)queryDetailsEntity[11]);
    	    row.createCell(6).setCellValue((String)queryDetailsEntity[8]);
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"Billing and Payment QueryDetails.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
}
