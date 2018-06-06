package com.bcits.bfm.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.BankRemittance;
import com.bcits.bfm.model.BankStatement;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.bankPayments.BankRemittanceService;
import com.bcits.bfm.service.bankPayments.BankStatementService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class BankPaymentsController 
{
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BankStatementService bankStatementService;

	@Autowired
	private BankRemittanceService bankRemittanceService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	//FOR BANK_STATEMENTS CONTROLLER
	@RequestMapping(value = "/bankStatements")
	public String bankStatementsIndex(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Cash Management");	
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Cash Management", 1, request);
		breadCrumbService.addNode("Manage Bank Statements", 2, request);
		return "bankPayments/bankstatement";
	}

	//Read Function
	@RequestMapping(value = "/bankStatement/readbankStatementUrl", method = RequestMethod.POST)
	public @ResponseBody List<?> readbankStatementUrl()
	{
		return bankStatementService.setResponse();
	} 

	//Create Function
	@RequestMapping(value = "/bankStatement/createbankStatementUrl", method = RequestMethod.GET)
	public @ResponseBody Object createbankStatementUrl(Map<String, Object> map,@ModelAttribute("bankStatement")BankStatement bankStatement, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) 
	{	
		bankStatementService.save(bankStatement);
		return bankStatementService.setResponse();	
	}

	//Update Function
	@RequestMapping(value = "/bankStatement/updatebankStatementUrl", method = RequestMethod.GET)
	public @ResponseBody Object updatebankStatementUrl(Map<String, Object> map,@ModelAttribute("bankStatement")BankStatement bankStatement, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
	{				
		bankStatementService.update(bankStatement);
		return bankStatementService.setResponse();
	}

	//Destroy Function
	@RequestMapping(value = "/bankStatement/destroybankStatementUrl", method = RequestMethod.GET)
	public @ResponseBody BankStatement deleteBankStatement(@ModelAttribute("bankStatement")BankStatement bankStatement, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) 
	{
		bankStatementService.delete(bankStatement.getBsId());
		return bankStatement;
	}

	//Filter's
	@RequestMapping(value = "/bankStatement/commonFilterForBankStatement/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getOpenTicketContentsForFilter(@PathVariable String feild) 
	{	
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("BankStatement",attributeList, null));
		return set;
	}

	@RequestMapping(value = "/bankStatement/commonFilterForAcccountNo", method = RequestMethod.GET)
	public @ResponseBody Set<?> getAccountNoFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (BankStatement bs : bankStatementService.findAll())
		{	
			Long l = bs.getAccountNo();
			String acc = l.toString();
			result.add(acc);		    	
		}
		return result;
	}	
	@RequestMapping(value = "/bankStatement/commonFilterForCredit", method = RequestMethod.GET)
	public @ResponseBody Set<?> getCreditFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (BankStatement bs : bankStatementService.findAll())
		{	
			Double l = bs.getCredit();
			String credit = l.toString();
			result.add(credit);		    	
		}
		return result;
	}
	@RequestMapping(value = "/bankStatement/commonFilterForDebit", method = RequestMethod.GET)
	public @ResponseBody Set<?> getDebitFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (BankStatement bs : bankStatementService.findAll())
		{	
			Double l = bs.getDebit();
			String debit = l.toString();
			result.add(debit);		    	
		}
		return result;
	}
	@RequestMapping(value = "/bankStatement/commonFilterForBalance", method = RequestMethod.GET)
	public @ResponseBody Set<?> getBalanceFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (BankStatement bs : bankStatementService.findAll())
		{	
			Double l = bs.getBalance();
			String debit = l.toString();
			result.add(debit);		    	
		}
		return result;
	}
	//End For BANK_STATEMENTS CONTROLLER

	//FOR BANK_REMITTANCE CONTROLLER
	@RequestMapping(value = "/bankRemittance")
	public String bankRemittanceIndex(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Cash Management");	
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Cash Management", 1, request);
		breadCrumbService.addNode("Manage Bank Remittance", 2, request);
		return "bankPayments/bankRemittance";
	}

	//Read Bank_Remittance
	@RequestMapping(value = "/bankRemittance/readbankRemittanceUrl", method = RequestMethod.POST)
	public @ResponseBody List<?> readbankRemittanceUrl()
	{
		return bankRemittanceService.setResponse();
	} 

	//Create Bank_Remittance
	@RequestMapping(value = "/bankRemittance/createbankRemittanceUrl", method = RequestMethod.GET)
	public @ResponseBody Object createbankRemittanceUrl(Map<String, Object> map,@ModelAttribute("bankRemittance")BankRemittance bankRemittance, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException 
	{	
		String dc = bankRemittance.getDescription();
		String dc_trim = dc.trim();
		bankRemittance.setDescription(dc_trim);
		bankRemittance.setTxDate(dateTimeCalender.getDateToStore(req.getParameter("txDate")));

		bankRemittanceService.save(bankRemittance);
		return bankRemittanceService.setResponse();	
	}

	//Update Bank_Remittance
	@RequestMapping(value = "/bankRemittance/updatebankRemittanceUrl", method = RequestMethod.GET)
	public @ResponseBody Object updatebankRemittanceUrl(Map<String, Object> map,@ModelAttribute("bankRemittance")BankRemittance bankRemittance, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException 
	{				
		bankRemittance.setTxDate(dateTimeCalender.getDateToStore(req.getParameter("txDate")));
		bankRemittanceService.update(bankRemittance);
		return bankRemittanceService.setResponse();
	}

	//Delete Bank_Remittance
	@RequestMapping(value = "/bankRemittance/destroybankRemittanceUrl", method = RequestMethod.GET)
	public @ResponseBody BankRemittance deleteBankStatement(@ModelAttribute("bankRemittance")BankRemittance bankRemittance, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) 
	{
		bankRemittanceService.delete(bankRemittance.getBrId());
		return bankRemittance;
	}	

	//Filter's for BANK_REMITTANCE
	@RequestMapping(value = "/bankRemittance/commonFilterForBankRemittance/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> commonFilterForBankRemittance(@PathVariable String feild) 
	{	
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("BankRemittance",attributeList, null));
		return set;
	}

	@RequestMapping(value = "/bankRemittance/commonFilterForBankRemittanceAcccountNo", method = RequestMethod.GET)
	public @ResponseBody Set<?> getbankRemittanceAccNoFilter()
	{ 
		Set<String> result = new HashSet<String>();
		for (BankRemittance br : bankRemittanceService.findAll())
		{	
			Long l = br.getAccountNo();
			String acc = l.toString();
			result.add(acc);		    	
		}
		return result;
	}
	@RequestMapping(value = "/bankRemittance/commonFilterForBankRemittanceCredit", method = RequestMethod.GET)
	public @ResponseBody Set<?> commonFilterBankRemittanceForCredit()
	{ 
		Set<String> result = new HashSet<String>();
		for (BankRemittance br : bankRemittanceService.findAll())
		{	
			Double l = br.getCredit();
			String credit = l.toString();
			result.add(credit);		    	
		}
		return result;
	}
	//END FOR BANK_REMITTANCE CONTROLLER
	
	@SuppressWarnings({ "rawtypes", "unused" })
	//Excel File Import For Bank_Statements
	@RequestMapping(value = "/bankStatement/upload", method = RequestMethod.POST)
	public @ResponseBody void uploadAssetDocument(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ParseException 
	{	
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rows = sheet.rowIterator();			
		int i=0;		
		List<BankStatement> bankStatements = new ArrayList<BankStatement>();		
		while(rows.hasNext())
		{
			XSSFRow row = ((XSSFRow) rows.next());
            Iterator cells = row.cellIterator();
     
            BankStatement bs = new BankStatement();
            if(row.getRowNum()==0){
				   continue; //just skip the rows if row number is 0 or 1
				  }
            //Bank Name
            if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
            	bs.setBankName(row.getCell(0).getStringCellValue());
            }
            else{
            	i=1;
            }
            
            //Account Number
            if(row.getCell(1)!=null){
            	BigDecimal b1;
            	b1 = new BigDecimal(row.getCell(1).getNumericCellValue());
            	Long acc_no = b1.longValue();
            	bs.setAccountNo(acc_no);
            }
            else{
            	i=1;
            }
            
            //TxtDate
            String dateVal = row.getCell(2).getDateCellValue().toString();
            if(!StringUtils.isEmpty(dateVal)){
            	
            	DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            	Date date1 = (Date)formatter.parse(dateVal);
            	java.util.Date dbdate=new java.sql.Date(date1.getTime());
            	bs.setTxDate(dbdate);
            }
            
            //Description
            if(!StringUtils.isEmpty(row.getCell(3).getStringCellValue())){
            	bs.setDescription(row.getCell(3).getStringCellValue());
            }
            else{
            	i=1;
            }
            
            //Credit    
            if( row.getCell(4)!=null){
            	double credit = Double.valueOf(row.getCell(4).getNumericCellValue());
            	bs.setCredit(credit);
            }
            else{
            	i=1;
            }
            
            //Debit
            if(row.getCell(5)!=null){
            	double debitVal = Double.valueOf(row.getCell(5).getNumericCellValue());
            	bs.setDebit(debitVal);
            }
            else{
            	i=1;
            }
            
            //Balance 
            if(row.getCell(6)!=null){
            	double balanceVal = Double.valueOf(row.getCell(6).getNumericCellValue());
            	bs.setBalance(balanceVal);
            }
            else{
            	i=1;
            }
            
            //Status
            if(!StringUtils.isEmpty(row.getCell(7).getStringCellValue())){
            	bs.setStatus(row.getCell(7).getStringCellValue());
            }
            else{
            	i=1;
            }
        	if(bankStatements.contains(bs)){
        		i=1;
        	}else
        		bankStatements.add(bs);
		}
		
		String responseVal = null;
		if(i==0)
		{			
			for (BankStatement bankStatement : bankStatements) {
				bankStatementService.save(bankStatement);
				responseVal = "Success";				
			}
		}
		else
		{
			responseVal = "Failure";
			throw new Error("SSSSSSSSSSSSSSSSSSSSSSS");
			
		}
		if(responseVal.equalsIgnoreCase("Success"))
		{
			//model.addAttribute("yes","successImport");
		}
		else if(responseVal.equalsIgnoreCase("Failure"))
		{
			//model.addAttribute("no","failureImport");
			throw new Error("SSSSSSSSSSSSSSSSSSSSSSS");
			
		}		
}
	
	//Excel File Import For Bank_Remmittance
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/bankRemittance/upload", method = RequestMethod.POST)
	public @ResponseBody void importBankRemittanceExcel(@RequestParam MultipartFile files,	HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ParseException {
	
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rows = sheet.rowIterator();	
		
		int i=0;
		
		List<BankRemittance> bankRemittances = new ArrayList<BankRemittance>();		
		while(rows.hasNext())
		{
			XSSFRow row = ((XSSFRow) rows.next());
            Iterator cells = row.cellIterator();
            
            BankRemittance br = new BankRemittance();
            if(row.getRowNum()==0)
            {
			   continue; //just skip the rows if row number is 0 or 1
			}
            //Bank Name
            if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
            	br.setBankName(row.getCell(0).getStringCellValue());
            }
            else{
            	i=1;
            }
            
            //Account Number
            if(row.getCell(1)!=null){
            	BigDecimal b1;
            	b1 = new BigDecimal(row.getCell(1).getNumericCellValue());
            	Long acc_no = b1.longValue();
            	br.setAccountNo(acc_no);
            	/*Long accVal = Long.valueOf(row.getCell(1).getCellType());
            	br.setAccountNo(accVal);*/
            }
            else{
            	i=1;
            }
            
            //TxtDate
            String dateVal = row.getCell(2).getDateCellValue().toString();
            if(!StringUtils.isEmpty(dateVal)){
            	
            	DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            	Date date1 = (Date)formatter.parse(dateVal);
            	br.setTxDate(new java.sql.Date(date1.getTime()));
            }
            /*String dateVal = row.getCell(2).getDateCellValue().toString();
            if(!StringUtils.isEmpty(dateVal)){            	
            	java.util.Date d= new Date();            	
            	br.setTxDate(new Date(d.getTime()));
            }
            else{
            	i=1;
            }*/
            
            //Description
            if(!StringUtils.isEmpty(row.getCell(3).getStringCellValue())){
            	br.setDescription(row.getCell(3).getStringCellValue());
            }
            else{
            	i=1;
            }
            
            
            //Credit
           // int credit = (int) row.getCell(4).getNumericCellValue();    
            if( row.getCell(4)!=null){
            	double credit = Double.valueOf(row.getCell(4).getNumericCellValue());
            	//double creditVal = Double.parseDouble(credit);
            	br.setCredit(credit);
            }
            else{
            	i=1;
            }
            
            //Status
            if(!StringUtils.isEmpty(row.getCell(5).getStringCellValue())){
            	br.setStatus(row.getCell(5).getStringCellValue());
            }
            else{
            	i=1;
            }            
        	if(bankRemittances.contains(br)){
        		i=1;
        	}else
        		bankRemittances.add(br);
		}		
		String responseVal = null;
		if(i==0){
			
			for (BankRemittance bremitance : bankRemittances) {
				bankRemittanceService.save(bremitance);
				responseVal = "Success";				
			}
		}
		else
		{
			responseVal = "Failure";
			throw new Error("SSSSSSSSSSSSSSSSSSSSSSS");
			
		}
		if(responseVal.equalsIgnoreCase("Success"))
		{
			//model.addAttribute("yes","successImport");
		}
		else if(responseVal.equalsIgnoreCase("Failure"))
		{
			//model.addAttribute("no","failureImport");
			throw new Error("SSSSSSSSSSSSSSSSSSSSSSS");
			
		}		
		//model.addAttribute("message","response");
}
}
