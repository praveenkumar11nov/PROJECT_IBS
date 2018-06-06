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
import java.util.Map.Entry;
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
public class GetXmlFileController 
{
		
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
		
		@RequestMapping(value = "/getXmlFile/exportToXML", method = { RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public String getXmlFile(HttpServletRequest req, HttpServletResponse response, @RequestBody String body) throws Exception
		{
			String salesLedger  = "Maintenance Charges (CAM)";
			String serviceType  = "CAM";
			String tallyCompanyName = ResourceBundle.getBundle("application").getString("tallyCompanyName");
			String XMLSales = "";
			String str = "";
			String fromDate = req.getParameter("fromDate");
			String toDate   = req.getParameter("toDate");
			System.out.println("****************** fromDate="+fromDate+" & toDate="+toDate+" ****************************");
//************************************************************* Inserted for generate XML for Prepaid *****************************************			  
			  List<Map<String, String>> meterList=electricityBillsService.generateAllDetails();
			  
			  for (Map<String, String> map : meterList) 
			  {  
				  String sum_cum_fixed_charge_DG = electricityBillsService.getPrepaidDailyData(map.get("meterNo"),fromDate,toDate);
				  double camBill = Double.parseDouble(sum_cum_fixed_charge_DG)/1.15;
				  double sbCess =  (camBill*0.5)/100;
				  double kkCess =  (camBill*0.5)/100;
				  double sTax   =  (camBill*14)/100;
				  double totalAmount = camBill + sbCess + kkCess + sTax; //(equal to sum_cum_fixed_charge_DG)
				  
				  String personName = map.get("personName");
				  String meterNo    = map.get("meterNo");
				  String propertyNo = map.get("propertyNo");
				  String accountNo  = map.get("accountNo");
				  String remoteId   = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+accountNo+"0";
				  String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+accountNo+"0";
			/*	  
				 System.out.println("");
				 System.out.println("serviceType="+serviceType+" & salesLedger="+salesLedger+" & tallyCompanyName="+tallyCompanyName+
						 		" & personName="+personName+" & accountNo="+accountNo+" & meterNo="+meterNo+" & propertyNo="+propertyNo+
				  				" & totalAmount(camBill="+camBill+" + sbCess="+sbCess+" + kkCess="+kkCess+" + sTax="+sTax+")="+totalAmount+
				  				" ie,(S_CFC_DG="+sum_cum_fixed_charge_DG+")   & remoteId=["+remoteId+"] & voucherKey=["+voucherKey+"]");
			*/ 
				 BillingInvoicesForCAMXML billInvoiceforCam = new BillingInvoicesForCAMXML();
				 str = billInvoiceforCam.createXMLforPrepaidBill(serviceType,salesLedger,tallyCompanyName,personName,accountNo,meterNo,
						 										 propertyNo,totalAmount,camBill,sbCess,kkCess,sTax,remoteId,voucherKey,fromDate,toDate);
				 XMLSales=XMLSales+str+"\n";
			}
			//System.out.println("*********************************************************************************************");
			//System.out.println("XML Code=====>  "+XMLSales); 
			FileUtils.writeStringToFile(new File("C:/SkyOn_TallyResponse/CAM_TallyReponse"+fromDate+".xml"), XMLSales);
					
			File file=new File("C:/SkyOn_TallyResponse/CAM_TallyReponse"+fromDate+".xml");
			ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-xml");
			response.setHeader("Content-Disposition", "inline;filename=\"CAM_TallyReponse"+fromDate+".xml"+"\"");
			FileInputStream input = new FileInputStream(file);
			IOUtils.copy(input, servletOutputStream);
	
			servletOutputStream.flush();
			servletOutputStream.close();

			return null;
	}
}
