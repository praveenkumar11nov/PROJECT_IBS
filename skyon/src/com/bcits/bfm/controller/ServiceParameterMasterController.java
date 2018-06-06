package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.FRChange;
import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.serviceMasterManagement.FRChangeService;
import com.bcits.bfm.util.DateTimeCalender;
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
public class ServiceParameterMasterController extends FilterUnit {

	static Logger logger = LoggerFactory.getLogger(ServiceParameterMasterController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	ServiceParameterMasterService spmService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private FRChangeService frChangeService;
	
	@Autowired
	private ElectricityBillsService electricityBillsService;
	
	@Resource
	private ValidationUtil validationUtil;

	@RequestMapping(value = "/servicetasks")
	public String service(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Masters", 1, request);
		breadCrumbService.addNode("Manage Service Parameter Master", 2, request);
		return "servicetasks/servicetasks";
	}
	
	@RequestMapping(value = "/serviceprocess")
	public String serviceMaster(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Service Master");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service Master", 1, request);
		return "serviceprocess/serviceprocess";
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/servicetasks/read", method = RequestMethod.POST)
	public @ResponseBody List<?> read() {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final ServiceParameterMaster record : spmService.findAll()) {
			result.add(new HashMap<String, Object>() {
				{
					put("spmId", record.getSpmId());
					put("serviceType", record.getServiceType());
					put("createdBy", record.getCreatedBy());
					put("lastupdatedBy", record.getLastupdatedBy());
					put("lastupdatedDt", record.getLastupdatedDt());
					put("spmdataType", record.getSpmdataType());
					put("spmDescription", record.getSpmDescription());
					put("spmName", record.getSpmName());
					put("spmSequence", record.getSpmSequence());
					put("status", record.getStatus());
				}
			});

		}
		return result;
	}

	@RequestMapping(value = "/serviceParameterMaster/serviceMaster/{spmId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	  public void metersStatus(@PathVariable int spmId,@PathVariable String operation, HttpServletResponse response) {
	   
	   logger.info("In Service  Status Change Method");
	   if (operation.equalsIgnoreCase("activate"))
		   spmService.setServiceParameterStatus(spmId,operation, response);
	   else
		   spmService.setServiceParameterStatus(spmId,operation, response);
	  }
	
	@RequestMapping(value = "/servicetasks/saveorupadteordelete/{action}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	List<?> saveServiceParameterMaster(
			@ModelAttribute("serviceParameterMaster") ServiceParameterMaster serviceParameterMaster,
			BindingResult bindingResult, @PathVariable String action) {
		if (action.equalsIgnoreCase("create")) {
			serviceParameterMaster.setStatus("Inactive");
			spmService.save(serviceParameterMaster);
		} else if (action.equalsIgnoreCase("update")) {
			spmService.update(serviceParameterMaster);
		} else {
			/*spmService.delete(serviceParameterMaster.getSpmId());*/
		}

		return spmService.setResponse();
	}
	
	@RequestMapping(value = "/servicetasks/serviceParameterDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteServiceParameter(	@ModelAttribute("serviceParameterMaster") ServiceParameterMaster serviceParameterMaster,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In delete service parameter destroy method");
		JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		if (serviceParameterMaster.getStatus().equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				/**
					 * 
					 */
				private static final long serialVersionUID = 1L;
				{
					put("AciveParameterDestroyError", messageSource.getMessage("ServiceParameterMaster.AciveParameterDestroyError", null, locale));
				}
			};
			errorResponse.setStatus("AciveParameterDestroyError");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		} else {
			try {
				spmService.delete(serviceParameterMaster.getSpmId());
			} catch (Exception e) {

				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return serviceParameterMaster;
		}
	}
	
	@RequestMapping(value="/servicetasks/serviceParamName" ,method=RequestMethod.GET)
	public List<?> getServiceParameterName(HttpServletResponse response,HttpServletRequest request) throws IOException, JSONException{
		String serviceType=request.getParameter("serviceType");
		logger.info("Service Type is..");
		List<?> list= spmService.getServiceParameterName(serviceType);
		PrintWriter out=null;
		JSONArray array=new JSONArray(list);
		response.setContentType("application/json");
		logger.info("Converting list type  to JSON Array type");
		out=response.getWriter();
		out.print(array); 
	    return null;
	}
	
	@RequestMapping(value="/servicetasks/saveServiceType", method=RequestMethod.POST)
	public void saveParameterValue(HttpServletResponse response,HttpServletRequest request){
		
		logger.info("inside parameterform page");
		String parameterList=request.getParameter("parameterVlaue");
		logger.info("parameter name is:::"+parameterList);
	}
	
	// ****************************** Service Parameter Master Filters Data methods ********************************/

			@RequestMapping(value = "/serviceParameterMaster/filter/{feild}", method = RequestMethod.GET)
			public @ResponseBody Set<?> serviceParameterMaster(@PathVariable String feild) {
				List<String> attributeList = new ArrayList<String>();
				attributeList.add(feild);
				HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("ServiceParameterMaster",attributeList, null));
				
				return set;
			}
			
			

			
			
// ****************************** FinalReading Read & Create ********************************//

	@RequestMapping("/frChanges")
	public String frChangeIndex(HttpServletRequest request, Model model) {
		logger.info("In frChangeIndex method");
		model.addAttribute("ViewName", "Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Masters", 1, request);
		breadCrumbService.addNode("Manage FRChange", 2, request);
		return "servicetasks/frChange";
	}
	
	@RequestMapping(value = "/FRChange/FRChangeReadUrl", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readFRChangeDetails() {
		logger.info("In read FRChange Details method");
		List<?>  fRChange= frChangeService.findAll();
		return fRChange;
	}
	
	@RequestMapping(value = "/FRChange/FRChangeCreateUrl", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveFRChangeDetails(@ModelAttribute("fRChange") FRChange fRChange,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception {

		logger.info(" In save fRChange details method ");
		
		logger.info("Present DG Reading :::::::::"+req.getParameter("presentReadingDg"));
		fRChange.setBillDate(dateTimeCalender.getDateToStore(req.getParameter("billDate")));
		
		frChangeService.save(fRChange);		
		 return fRChange;	
	}
	
	@RequestMapping(value = "/FRChange/FRChangeUpdateUrl", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object editFRChangeDetails(@ModelAttribute("fRChange") FRChange fRChange,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception {

		logger.info(" In edit fRChange details method ");
		fRChange.setBillDate(frChangeService.find(fRChange.getFrId()).getBillDate());
		fRChange.setPresentReading(frChangeService.find(fRChange.getFrId()).getPresentReading());
		
		frChangeService.update(fRChange);		
		 return fRChange;	
	}
	
	@RequestMapping(value = "/FRChange/FRChangeDestroyUrl", method = RequestMethod.GET)
	public @ResponseBody Object deleteFRchangeDetails(@ModelAttribute("fRChange") FRChange frChange,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

		logger.info("In delete fr change details method");
		JsonResponse errorResponse = new JsonResponse();		
		try {
			frChangeService.delete(frChange.getFrId());
			ss.setComplete();
			return readFRChangeDetails();
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
	}
		
	@RequestMapping(value = "/FRChange/readAccountNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readAccountNumber(){
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = frChangeService.findAccountNumbers().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				paymentMap.put("personName", personName);
				paymentMap.put("accountId", (Integer)values[0]);
				paymentMap.put("accountNo", (String)values[1]);
				paymentMap.put("personId", (Integer)values[2]);
				paymentMap.put("personType", (String)values[5]);
				paymentMap.put("personStyle", (String)values[6]);
			
			result.add(paymentMap);
	     }
     return result;
	}
	
	@RequestMapping(value = "/FRChange/serviceTypeComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> readServiceType()
	{		
		return frChangeService.findServiceType();
	           
	}
	
	@RequestMapping(value = "/frChange/getBillDateAndPreReading", method = RequestMethod.GET)
	public @ResponseBody Object readBillDateAndPreReading(HttpServletRequest req)
	{		
		int acId=Integer.parseInt(req.getParameter("accountId"));
		String serviceType=req.getParameter("serviceType").toString();
		int elBillId = frChangeService.findBillDateAndPreReading(acId,serviceType);
		ElectricityBillEntity billEntity = electricityBillsService.find(elBillId);
		
		String presentReading = frChangeService.getPresentReading(elBillId);
		String dgpresentReading = frChangeService.getDGPresentReading(elBillId);
		
		Map<String, Object> parameterMap = null;
		parameterMap = new HashMap<String, Object>();	
		
		parameterMap.put("billDate", DateFormatUtils.format(billEntity.getBillDate(), "dd/MM/yyyy"));
		parameterMap.put("elBillParameterValue", presentReading);
		parameterMap.put("dgpresentReading", dgpresentReading);
		return parameterMap;
	}
	
	// ****************************** FR Change Filter Data methods ********************************/

		@RequestMapping(value = "/FRChange/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> getFRChangeContentsForFilter(@PathVariable String feild) {
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("FRChange", attributeList, null));

			return set;
		}
		
		@RequestMapping(value = "/FRChange/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForAccountNumbersUrl() {
			return frChangeService.commonFilterForAccountNumbersUrl();
		}
		
		@RequestMapping(value = "/FRChange/getPersonListForFileter", method = RequestMethod.GET)
		public @ResponseBody List<?> readPersonNamesInLedger() {
			logger.info("In person data filter method");
			List<?> accountPersonList = frChangeService.findPersonForFilters();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			for (Iterator<?> i = accountPersonList.iterator(); i.hasNext();) {
				final Object[] values = (Object[]) i.next();
				String personName = "";
				personName = personName.concat((String) values[1]);
				if (((String) values[2]) != null) {
					personName = personName.concat(" ");
					personName = personName.concat((String) values[2]);
				}

				map = new HashMap<String, Object>();
				map.put("personId", (Integer) values[0]);
				map.put("personName", personName);
				map.put("personType", (String) values[3]);
				map.put("personStyle", (String) values[4]);

				result.add(map);
			}

			return result;
		}
		
		@RequestMapping(value = "/serviceTemplate/serviceTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportFileService(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
	       
			List<ServiceParameterMaster> serviceTemplateEntities =spmService.findAll();
		
	               
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
	    	for (ServiceParameterMaster service :serviceTemplateEntities) {
	    		
	    		XSSFRow row = sheet.createRow(count);
	    		
	    		 String str="";
	    		 if(service.getServiceType()!=null){
	  				str=service.getServiceType();
	  			}
	              else{
	              	str="";
	              }

	    		row.createCell(0).setCellValue(str);
	    		
	    		if(Integer.toString(service.getSpmSequence())!=null){
	  				str=Integer.toString(service.getSpmSequence());
	  			}
	              else{
	              	str="";
	              }
	    		
	    		row.createCell(1).setCellValue(str);
	    		if(service.getSpmdataType()!=null){
	  				str=service.getSpmdataType();
	  			}
	              else{
	              	str="";
	              }
	    		
	    	    row.createCell(2).setCellValue(str);
	    	    if(service.getSpmName()!=null){
	  				str=service.getSpmName();
	  			}
	              else{
	              	str="";
	              }
	    		
	    		row.createCell(3).setCellValue(str);
	    		if(service.getSpmDescription()!=null){
	  				str=service.getSpmDescription();
	  			}
	              else{
	              	str="";
	              }
	    		row.createCell(4).setCellValue(str);
	    		if(service.getStatus()!=null){
	  				str=service.getStatus();
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
			response.setHeader("Content-Disposition", "inline;filename=\"ServiceParameterTemplates.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
		@RequestMapping(value = "/pdfTemplate/pdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportFilePdfService(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
	        
	       
			List<ServiceParameterMaster> serviceTemplateEntities =spmService.findAll();
		
	              
	      
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
	    	for (ServiceParameterMaster service :serviceTemplateEntities) {
	    		
	    		String str="";
	    		 if(service.getServiceType()!=null){
	  				str=service.getServiceType();
	  			}
	              else{
	              	str="";
	              }

	    		
	        
	        PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
	        if(Integer.toString(service.getSpmSequence())!=null){
  				str=Integer.toString(service.getSpmSequence());
  			}
              else{
              	str="";
              }
	   
            PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
            if(service.getSpmdataType()!=null){
  				str=service.getSpmdataType();
  			}
              else{
              	str="";
              }
    		
        
            PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
            if(service.getSpmName()!=null){
  				str=service.getSpmName();
  			}
              else{
              	str="";
              }
    		
           
            PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
            if(service.getSpmDescription()!=null){
  				str=service.getSpmDescription();
  			}
              else{
              	str="";
              }
       
            PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
            if(service.getStatus()!=null){
  				str=service.getStatus();
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
			response.setHeader("Content-Disposition", "inline;filename=\"ServiceParameterTemplates.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
}
