package com.bcits.bfm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyAdjustmentPostService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyInvoicePostService;
import com.bcits.bfm.service.postInvoiceToTallyServiceImpl.PaymentsTallyAllBillPostServiceImpl;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.serviceImpl.BillingInvoicesForCAMXML;
import com.bcits.bfm.serviceImpl.BillingInvoicesXML;
import com.tally.billinginvoices.BillingInvoices;
import com.tally.billinginvoices.BillingInvoicesForCAM;

@Controller
public class TallyXMLController {
	
	Logger logger=LoggerFactory.getLogger(TallyXMLController.class);

	@Autowired
	private ElectricityBillsService electricityBillsService;
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private ElectricityBillLineItemService electricityBillLineItemService;
	
	@Autowired
	private PaymentsTallyAllBillPostServiceImpl paymentsTallyAllBillPostServiceImpl;
	
	@Autowired
    private TallyAdjustmentPostService tallyAdjustmentPostService;
	
	@RequestMapping(value = "/electricityBills/exportToXML", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String tallyXMLForm(HttpServletRequest req, HttpServletResponse response, @RequestBody String body) throws Exception{
		
		String serviceType1 = req.getParameter("serviceType");
		String presentdate = req.getParameter("presentdate");
		logger.info("service type====" + serviceType1);
		logger.info("presentdate====" + presentdate);
		Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
		HttpSession session = req.getSession(false);
		 List<Map<String,Object>> reportList=new ArrayList<Map<String,Object>>();
		
		  List<ElectricityBillEntity> objList=electricityBillsService.getAllRecordsForTally(serviceType1,monthDate);
		 int size=objList.size();
		  System.out.println("size of object list=========================="+size);
		  try{
			  Thread.sleep(5000);
		  }
		  catch(Exception e)
		  {
			  
		  }
		  String XMLSales="";
			
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
  			/*if (serviceType.equalsIgnoreCase("Electricity")) {
  				salesLedger = "Electricity Collection";
  				arrearsLedge= "Electricity Arrears";
  				
  			}*/
  			if (serviceType.equalsIgnoreCase("CAM")) {
  				salesLedger = "Maintenance Charges (CAM)";
  				
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

  			String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
			//List<Map<String, Object>>  propertyMap1=tariffCalculationService.getTallyDetailData(billId);
			
			String autoIncrementString = this.getNewString(billId);
			String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
			String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";
			
			String str=null;
			if(serviceType.equalsIgnoreCase("CAM")){
				BillingInvoicesForCAMXML billInvoiceforCam=new BillingInvoicesForCAMXML();
				List<Map<String,Object>> lineItemList=electricityBillLineItemService.findAllDetailsForCamPosting(billId);
				str=billInvoiceforCam.createBillInvoicesForCAM1(mapsList,remoteId,voucherKey,billId,
						billDate,salesLedger,billNumber,tallyCompanyName,basicDateOfInvoice,propertyMap,serviceType,accountNo,arrearsLedge,lineItemList,propertyNumber);
				//logger.info("in side reponsePostInvoiceToTally **************str  ="+str);
				logger.info("in side CAM ");
				if(str!=null){
					transactionMasterService.setTallyStatusUpDateforXML(billId);
				}
				XMLSales=XMLSales+str+"\n";
				
			}
			else{

				BillingInvoicesXML billInvoice=new BillingInvoicesXML();
				List<Map<String,Object>> lineItemList=electricityBillLineItemService.findAllDetailsForCamPosting(billId);
				if(!(lineItemList.isEmpty())){
			
				 str=billInvoice.createBillInvoicesforEB(mapsList,remoteId,voucherKey,billId,
						billDate,salesLedger,billNumber,tallyCompanyName,basicDateOfInvoice,propertyMap,serviceType,accountNo,arrearsLedge,lineItemList);
				 if(str!=null){
					 transactionMasterService.setTallyStatusUpDateforXML(billId);
					}
				//logger.info("Electricity response str**************** "+str );
				 logger.info("in side EB");
				 XMLSales=XMLSales+str+"\n";
				}
				
			}
				
			
			}	
    logger.info("******************************************** XML Code ********************************************");		
    logger.info(XMLSales);	
			FileUtils.writeStringToFile(new File("C:/BFM_TallyResponse/TallyReponse.xml"), XMLSales);
			
			File file=new File("C:/BFM_TallyResponse/TallyReponse.xml");
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-xml");
			response.setHeader("Content-Disposition", "inline;filename=\"TallyResponse.xml"+"\"");
			FileInputStream input = new FileInputStream(file);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
		return null;
	}
	private String getNewString(int billId2) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(billId2);
	String autoIncrementString = sb.toString();
	
	 return autoIncrementString;
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
	
	
	/*----------------------------Cash Management--------------------------------------*/
	          /************Payemnts**************/
	
	@RequestMapping(value="/billingPayments/GenerateXmlTallyPosting",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String paymentsTallyPostingXml(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		logger.info("in side paymentsTallyPostingXml methode");
		String month=request.getParameter("month");
		Date monthDate=new SimpleDateFormat("MMM yyyy").parse(month);
		String str=paymentsTallyAllBillPostServiceImpl.generatexmlForPaymentsPosting(monthDate);
		FileUtils.writeStringToFile(new File("C:/BFM_TallyResponse/TallyReponse.xml"), str);
		File file=new File("C:/BFM_TallyResponse/TallyReponse.xml");
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-xml");
		response.setHeader("Content-Disposition", "inline;filename=\"TallyResponse.xml"+"\"");
		FileInputStream input = new FileInputStream(file);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		return null;
	}
	
	
	/***************************PaymentAdjustments*****************************************/
	
	@RequestMapping(value="/PaymentAdjustment/GenerateXmlTallyPosting",method={RequestMethod.POST,RequestMethod.GET})
	public  String generateAdjustmentToTally(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		logger.info("in side generateAdjustmentToTally");
		String month=request.getParameter("month");
		Date monthDate=new SimpleDateFormat("MMM yyyy").parse(month);
		String xmlfile=tallyAdjustmentPostService.generateXmlAgustmentPosting(monthDate);
		FileUtils.writeStringToFile(new File("C:/BFM_TallyResponse/TallyReponse.xml"), xmlfile);
		File file=new File("C:/BFM_TallyResponse/TallyReponse.xml");
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-xml");
		response.setHeader("Content-Disposition", "inline;filename=\"TallyResponse.xml"+"\"");
		FileInputStream input = new FileInputStream(file);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		return null;
	}
}
