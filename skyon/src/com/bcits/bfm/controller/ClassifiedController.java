package com.bcits.bfm.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Classified;
import com.bcits.bfm.model.ClassifiedEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.ClassifiedEntitySerive;
import com.bcits.bfm.service.ClassifiedService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;


@Controller
public class ClassifiedController {

	@Autowired
	private ClassifiedService classifiedService;

	@Autowired
	private ClassifiedEntitySerive classifiedEntitySerive;
	
	@Autowired
	private TariffCalculationService trCalculationService;
	
	@Autowired
	private AccountService accountService;
	

	@RequestMapping(value = "/classified")
	public String indexInvoices(Model model, HttpServletRequest request) {
		System.out.println("Coming to ClassifiedController");
		
		return "classified";
	}

	@RequestMapping(value="/classified/getAllMobileNumbers",method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<?> getMobileNumbers(){
		
		List<Contact> list=classifiedEntitySerive.getMobilenumbers();
		List<Map<String, Object>> reList=new ArrayList<>();
		Map<String, Object> map=null;
		for(Iterator<Contact> iterator=list.iterator();iterator.hasNext();){
			Contact contact= iterator.next();
			map=new HashMap<>();
			map.put("personId", contact.getPersonId());
			if(contact.getContactPrimary().equalsIgnoreCase("Yes"))
			if(contact.getContactType().equalsIgnoreCase("Mobile")){
				String mobile=contact.getContactContent();
				map.put("mobile_No",mobile);
				//System.out.println(mobile);
			}
			
			reList.add(map);
		}
		
		return reList;
	}
	
	@RequestMapping(value="/classified/getEmailIdforFilter",method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<?> getEmailIdforFilter(){
		
		List<Contact> list1=classifiedEntitySerive.geEmailId(); 
		List<Map<String, Object>> reList=new ArrayList<>();
		Map<String, Object> map=null;
		
		for(Iterator<Contact> iterator=list1.iterator();iterator.hasNext();){
			Contact contact= iterator.next();
			map=new HashMap<>();
			map.put("personId", contact.getPersonId());
			if(contact.getContactPrimary().equalsIgnoreCase("Yes"))
			if(contact.getContactType().equalsIgnoreCase("Email")){
				String email=contact.getContactContent();
				map.put("emailId",email);
				//System.out.println(mobile);
			}
			
			reList.add(map);
		}
		return reList;
	}
	
	
	@RequestMapping(value = "/classifiedResult")
	public String redirectToJspPage(){
			
	          	return "classifiedResult";	
	}
	
	// Create Classified
	@RequestMapping(value = "/classified/createUrl", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ClassifiedEntity createClassified(HttpServletRequest req)  {
		System.out.println("classified save called in Controller ");
		//req.getParameter("");
		ClassifiedEntity entity=new ClassifiedEntity();
		String proprtyNo=trCalculationService.getpropertyNoOnPropertyId(Integer.parseInt(req.getParameter("propertyName")));
	     List<Person>	person=classifiedEntitySerive.getPersonName(Integer.parseInt(req.getParameter("personName")));
	     
	     String p1=null;
	     String p2=null;
	     for(Iterator<?> iterator=person.iterator(); iterator.hasNext();){
	    	 final Object[]   objects= (Object[]) iterator.next();
	    	 p1=objects[0].toString();
	    	 p2=objects[1].toString();
	     }
	      System.out.println(req.getParameter("mobile_No"));
	      ClassifiedEntity classifiedEntity=new ClassifiedEntity();
	      classifiedEntity.setProperty_No(proprtyNo);
	      classifiedEntity.setCustomer_Name(p1+" "+p2);
	      classifiedEntity.setMobile_No(req.getParameter("mobile_No"));
	      classifiedEntity.setEmailId(req.getParameter("emailId"));
	      classifiedEntity.setInformation(req.getParameter("information"));
	     
		return  classifiedEntitySerive.save(classifiedEntity);
	}

	// Read Classified
	@RequestMapping(value = "/classified/readUrl", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> readClassified(
			HttpServletRequest req) {
		return classifiedEntitySerive.getData();

	}

	
	// Read Classified data whose status is checked
	@RequestMapping(value = "/classifiedResult/readUrl", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> readClassifiedCheckedData() {
		
		System.out.println("coming to /readClassifiedCheckedData ");
		return classifiedService.findAllCheckedData();

	}
	
	
	
	
	
	
	
	
	
	// Updating CheckBox value
	@RequestMapping(value = "/updateClassifiedData", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String updateClassified(@RequestParam("prePaidId") int prePaidId,
			@RequestParam("checkBoxVal") String checkBoxVal,@ModelAttribute("classified") Classified classified,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale,
			HttpServletRequest req) throws InterruptedException {
		System.out.println("classified update called in Controller ");
		System.out.println("classified_ID : "+prePaidId);
		System.out.println("checkBoxVal : "+checkBoxVal);
		// classified = classifiedService.find(prePaidId);
		ClassifiedEntity classifiedEntity=classifiedEntitySerive.find(prePaidId);
		classified.setApartmentNo(classifiedEntity.getProperty_No());
		classified.setSellerName(classifiedEntity.getCustomer_Name());
		classified.setPhoneNo(classifiedEntity.getMobile_No());
		classified.setEmailId(classifiedEntity.getEmailId());
		classified.setDescription(classifiedEntity.getInformation());
		classified.setStatus(checkBoxVal);
		classifiedService.save(classified);

		return "classified";
	}

	@RequestMapping(value = "/ConsumerTemplate/ClassifiedTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportAccountStatement(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xls";
		HttpSession session = request.getSession(); 
		String username= (String) session.getAttribute("userName");
		/*Person person = generalNotificationService.getPersonBasedOnMailId(username);
		int accountId=accountService.getAccountIdUsingPersonId(person.getPersonId());*/
		List<Object[]> resultlist=classifiedService.exportToExcel();
		
        String sheetName = "Templates";//name of sheet
    	HSSFWorkbook wb = new HSSFWorkbook();
    	HSSFSheet sheet = wb.createSheet(sheetName) ;
    	HSSFRow header = sheet.createRow(0);    	
    	HSSFCellStyle style = wb.createCellStyle();
    	style.setWrapText(true);
    	HSSFFont font = wb.createFont();
    	font.setFontName(HSSFFont.FONT_ARIAL);
    	font.setFontHeightInPoints((short)10);
    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    	style.setFont(font);
    	
    	header.createCell(0).setCellValue("Property Number");
        header.createCell(1).setCellValue("Customer Number");
        header.createCell(2).setCellValue("Email ID");
        header.createCell(3).setCellValue("Phone Number");
        header.createCell(4).setCellValue("Information");
     
    
        for(int j = 0; j<=4; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
        }
        
        int count = 1;
           for (Object[] object:resultlist) {
	         HSSFRow row = sheet.createRow(count);
   	            if(object[1]!=null){
		            row.createCell(0).setCellValue((String)object[1]);
   	            }
   	            if(object[2]!=null){
		            row.createCell(1).setCellValue((String)object[2]);
   	            }
   	            
   	            if(object[3]!=null){
		            row.createCell(2).setCellValue((String)object[3]);
   	            }
   	            
   	            if(object[4]!=null){
		            row.createCell(3).setCellValue((String)object[4]);
   	            }
   	            
   	            if(object[5]!=null){
   	               row.createCell(4).setCellValue((String)object[5]);
   	            }
		    
		    
		    count++;
	}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"Classified Details.xls"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
}
