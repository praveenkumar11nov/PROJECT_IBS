package com.bcits.bfm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.PrePaidPaymentEntity;
import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.PrePaidMeterService;
import com.bcits.bfm.service.PrepaidPaymentAdjustmentService;
import com.bcits.bfm.service.PrepaidRechargeService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyAdjustmentPostService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyInvoicePostService;
import com.bcits.bfm.service.postInvoiceToTallyServiceImpl.TallyAllBillPostServiceImpl;
import com.bcits.bfm.serviceImpl.TallyPostingPrePaidRecieptsService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class PrepaidTallyPostingController {

	 final static Logger logger = LoggerFactory.getLogger(PrepaidTallyPostingController.class);

	 DateTimeCalender dateTimeCalender=new DateTimeCalender();
	  @Autowired
	  private BreadCrumbTreeService breadCrumbService;
	  
	    @Autowired
	    private TallyAdjustmentPostService tallyAdjustmentPostService;
	    
	    @Autowired
		private CommonService commonService;
		
		@Autowired
		private AdjustmentService adjustmentService; 
		
		@Autowired
		private PropertyService propertyService;
		
		@Autowired
		private ContactService contactService;

		@Autowired
		private AddressService addressService;
	  
	  
	    @Autowired
	    private PrePaidMeterService prepaidMeterService;
	  
	    @Autowired
	    private AccountService accountService;
	   
	    @Autowired
	    private PrepaidRechargeService prepaidRechargeService;
	    
	    @Autowired
	    private PrepaidPaymentAdjustmentService prepaidPaymentAdjustmentService;
	    
	    @Autowired
	    private TallyPostingPrePaidRecieptsService tallyPostingPrePaidRecieptsService;
	    
	    @Autowired
	    private TallyInvoicePostService tallyInvoicePostService;
	    
	    @Autowired
		private TallyAllBillPostServiceImpl tallyAllBillPostServiceImpl;
	    
	    @Autowired
		private PaymentService paymentService;
	  
	    @PersistenceContext(unitName="bfm")
	    protected EntityManager entityManager;
	    
	    @RequestMapping(value="/prepaidPaymentsHistory/exportToXml",method={RequestMethod.POST,RequestMethod.GET})
	    public void exportToXml(HttpServletRequest request,HttpServletResponse response) throws ParseException, JAXBException, IOException{
	    	logger.info("export to XML Methode");
	     String currentMonth=request.getParameter("fromDate");
	     Date fromMonth=new SimpleDateFormat("MMM yyyy").parse(currentMonth);
	     List<PrepaidRechargeEntity> prEntities=prepaidRechargeService.getAllRecords(fromMonth);
	     logger.info("size of data  "+prEntities.size());
	     List<Map<String, Object>> mapsList = new ArrayList<>();
	     String xmlstr="";
	     for (PrepaidRechargeEntity prepaidRechargeEntity : prEntities) {
	    	 String receiptNumber=null;
			  String bankAcNumberLedger=null;
			  String bankName=null;
			  String paymentMode=null;
			  Date receiptDate=null;
			  double checkAmount=0.0;
			  Date cashReceiptDate=null;
			  
			int ppRechargeId=prepaidRechargeEntity.getPreRechargeId();
			PrepaidRechargeEntity prepaidRechargeEntity2=prepaidRechargeService.find(ppRechargeId);
			 cashReceiptDate=prepaidRechargeEntity2.getTxn_Date();
			 receiptNumber=prepaidRechargeEntity2.getTxn_ID();
			 receiptDate=prepaidRechargeEntity2.getTxn_Date();
			 bankName=prepaidRechargeEntity2.getBankName();
			 paymentMode=prepaidRechargeEntity2.getModeOfPayment();
			 bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
			 checkAmount=prepaidRechargeEntity2.getRechargeAmount();
			 
			 Map<String, Object> map=new LinkedHashMap<>();
			 Object[] obj;
			 if(receiptNumber.startsWith("AD")){
				  obj=prepaidPaymentAdjustmentService.getInstrumentIDNo(receiptNumber);
				 if(obj[1]!=null){
				 map.put("instrumentDate",new  SimpleDateFormat("YYYYMMdd").format((java.sql.Date)obj[1] ));
				 }
				 if(obj[0]!=null){
				 map.put("instrumentNo", obj[0]);
				 }
			 }else{
				 map.put("instrumentDate", new  SimpleDateFormat("YYYYMMdd").format(prepaidRechargeEntity2.getTxn_Date()));
				 map.put("instrumentNo", prepaidRechargeEntity2.getMerchantId());
			 }
			 map.put("propertyNo", propertyService.find(accountService.find(prepaidRechargeEntity2.getAccountId()).getPropertyId()).getProperty_No());
			 map.put("billSegmentCircle", "Utility Charges");
			 map.put("billAmount", prepaidRechargeEntity2.getRechargeAmount());
			 mapsList.add(map);
		String str=tallyPostingPrePaidRecieptsService.generateXML(mapsList,receiptNumber,cashReceiptDate,receiptDate,paymentMode,bankAcNumberLedger,checkAmount,bankName,ppRechargeId);
			if(str!=null){
				String tallystatus="XML Generated";
				prepaidRechargeService.upDateTallyStatus(ppRechargeId,tallystatus);
			}
			xmlstr=xmlstr+str+"\n";
		}
	     FileUtils.writeStringToFile(new File("C:/BFM_TallyResponse/TallyReponse.xml"), xmlstr);
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
	    }
	    /*@RequestMapping(value="/prepaidPaymentsHistory/exportToXml",method={RequestMethod.POST,RequestMethod.GET})
	    public void exportToXml(HttpServletRequest request,HttpServletResponse response) throws ParseException, JAXBException, IOException{
	    	logger.info("export to XML Methode");
	     String currentMonth=request.getParameter("fromDate");
	     Date fromMonth=new SimpleDateFormat("MMM yyyy").parse(currentMonth);
	     List<PrepaidRechargeEntity> prEntities=prepaidRechargeService.getAllRecords(fromMonth);
	     logger.info("size of data  "+prEntities.size());
	     List<Map<String, Object>> mapsList = new ArrayList<>();
	     String xmlstr="";
	     for (PrepaidRechargeEntity prepaidRechargeEntity : prEntities) {
	    	
			int ppRechargeId=prepaidRechargeEntity.getPreRechargeId();
			PrepaidRechargeEntity prepaidRechargeEntity2=prepaidRechargeService.find(ppRechargeId);
			 Map<String, Object> map=new LinkedHashMap<>();
			 cashReceiptDate=prepaidRechargeEntity2.getTxn_Date();
			 receiptNumber=prepaidRechargeEntity2.getTxn_ID();
			 receiptDate=prepaidRechargeEntity2.getTxn_Date();
			 bankName=prepaidRechargeEntity2.getBankName();
			 paymentMode=prepaidRechargeEntity2.getModeOfPayment();
			 bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
			 checkAmount=prepaidRechargeEntity2.getRechargeAmount();
			 map.put("propertyNumber", propertyService.find(accountService.find(prepaidRechargeEntity2.getAccountId()).getPropertyId()).getProperty_No());
			 map.put("rechargedAmount", prepaidRechargeEntity2.getRechargeAmount());
			 map.put("adjustmentDate", prepaidRechargeEntity2.getTxn_Date());
			 map.put("adjustmentType", prepaidRechargeEntity2.getModeOfPayment());
			 mapsList.add(map);
		String str=tallyPostingPrePaidRecieptsService.generateXML(mapsList,ppRechargeId);
			if(str!=null){
				String tallystatus="XML Generated";
				prepaidRechargeService.upDateTallyStatus(ppRechargeId,tallystatus);
			}
			xmlstr=xmlstr+str;
		}
	     FileUtils.writeStringToFile(new File("C:/BFM_TallyResponse/TallyReponse.xml"), xmlstr);
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
	    }*/
	    
	  /* @RequestMapping(value="/prepaidPaymentHistory/postCollectionDataTally",method={RequestMethod.POST,RequestMethod.GET})
	   public @ResponseBody String singleTallyPosting(@RequestParam("paymentCollectionId") int paymentCollectionId) throws Exception{
		   
		   List<Map<String, Object>> mapsList = new ArrayList<>();
			PrepaidRechargeEntity prepaidRechargeEntity2=prepaidRechargeService.find(paymentCollectionId);
			Date cashReceiptDate=prepaidRechargeEntity2.getTxn_Date();
			String receiptNumber=prepaidRechargeEntity2.getTxn_ID();
			Date receiptDate=prepaidRechargeEntity2.getTxn_Date();
			String bankName=prepaidRechargeEntity2.getBankName();
			String paymentMode=prepaidRechargeEntity2.getModeOfPayment();
			String bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
			double checkAmount=prepaidRechargeEntity2.getRechargeAmount();
			 
			 Map<String, Object> map=new LinkedHashMap<>();
			 Object[] obj;
			 if(receiptNumber.startsWith("AD")){
				  obj=prepaidPaymentAdjustmentService.getInstrumentIDNo(receiptNumber);
				 if(obj[1]!=null){
				 map.put("instrumentDate",new SimpleDateFormat("YYYYMMdd").format((Date)obj[1]) );
				 }
				 map.put("instrumentNumber", obj[0]);
				
			 }else{
				 map.put("instrumentDate", new SimpleDateFormat("YYYYMMdd").format(prepaidRechargeEntity2.getTxn_Date()));
				 map.put("instrumentNumber", prepaidRechargeEntity2.getMerchantId());
			 }
			 map.put("propertyNo", propertyService.find(accountService.find(prepaidRechargeEntity2.getAccountId()).getPropertyId()).getProperty_No());
			 map.put("billSegmentCircle", "Prepaid Charges");
			 map.put("billAmount", prepaidRechargeEntity2.getRechargeAmount());
			 mapsList.add(map);
			
			 String str=tallyInvoicePostService.reponsePostReceiptToTally(mapsList, receiptDate, receiptNumber,bankAcNumberLedger,
     		   		bankName,paymentMode,paymentCollectionId,checkAmount,cashReceiptDate);
         logger.info("str  "+str);
         if(str.equalsIgnoreCase("Receipt Successfully Posted To Tally")){
         	//transactionMasterService.setReceiptTallyStatusUpdate(paymentCollectionId);
         }

         return "("+new StringBuilder(receiptNumber).toString()+") "+str;
	   }*/
	   
	   
	   @RequestMapping(value = "/prepaidPaymentHistory/postCollectionDataTally", method = {RequestMethod.POST,RequestMethod.GET})
	   public @ResponseBody String postAdjustmentToTally(@RequestParam("paymentCollectionId") int paymentCollectionId) throws Exception{
			 
			 logger.info("inside adjustment post to tally method=========="+paymentCollectionId);
			 
			/*String response= tallyAdjustmentPostService.reponsePostAdjustmentToTally(paymentCollectionId);
			if(response.equalsIgnoreCase("Payment Successfully Posted To Tally"))
			{
				String tallyStatus="Posted";
				prepaidRechargeService.upDateTallyStatus(paymentCollectionId,tallyStatus);
				
			}
			 
			 return response;*/
			 PrepaidRechargeEntity prepaidRechargeEntity=prepaidRechargeService.find(paymentCollectionId);
			 Date cashReceiptDate=prepaidRechargeEntity.getTxn_Date();
			String receiptNumber=prepaidRechargeEntity.getTxn_ID();
			Date receiptDate=prepaidRechargeEntity.getTxn_Date();
			String bankName=prepaidRechargeEntity.getBankName();
			String paymentMode=prepaidRechargeEntity.getModeOfPayment();
			String bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
			double checkAmount=prepaidRechargeEntity.getRechargeAmount();
			  List<Map<String, Object>> mapsList = new ArrayList<>();
			 Map<String, Object> map=new LinkedHashMap<>();
			 Object[] obj;
			 if(receiptNumber.startsWith("AD")){
				  obj=prepaidPaymentAdjustmentService.getInstrumentIDNo(receiptNumber);
				  System.err.println("instrumentNumber "+obj[0]);
				 if(obj[1]!=null){
				 map.put("instrumentDate",new  SimpleDateFormat("YYYYMMdd").format((java.sql.Date)obj[1] ));
				 }
				 if(obj[0]!=null){
					 map.put("instrumentNumber", obj[0]);
					 }else{
						 map.put("instrumentNumber", "");
					 }
			 }else{
				 map.put("instrumentDate", new  SimpleDateFormat("YYYYMMdd").format(prepaidRechargeEntity.getTxn_Date()));
				 map.put("instrumentNumber", prepaidRechargeEntity.getMerchantId());
			 }
			 map.put("accountNo", propertyService.find(accountService.find(prepaidRechargeEntity.getAccountId()).getPropertyId()).getProperty_No());
			 map.put("billSegmentCircle", "Utility Charges");
			 map.put("billAmount", prepaidRechargeEntity.getRechargeAmount());
			 mapsList.add(map);
		  
	           String str=tallyInvoicePostService.reponsePostReceiptToTally(mapsList, receiptDate, receiptNumber,bankAcNumberLedger,
	        		   		bankName,paymentMode,paymentCollectionId,checkAmount,cashReceiptDate);

	            if(str.equalsIgnoreCase("Receipt Successfully Posted To Tally")){
	            	String tallyStatus="Posted";
					prepaidRechargeService.upDateTallyStatus(paymentCollectionId,tallyStatus);
	            }

	            return "("+new StringBuilder(receiptNumber).toString()+") "+str;

			 
		 }
	   
	  @RequestMapping(value="/prepaidPaymentHtry/postAllPaymentsToTally",method={RequestMethod.POST,RequestMethod.GET})
	   public @ResponseBody String postAllPayemntsToTally(HttpServletRequest request) throws ParseException{
		   logger.info("postAllPaymetsToTally Methode  ");
		 String fromdate=request.getParameter("fromDate");
		 SimpleDateFormat dateFormat=new SimpleDateFormat("MMM yyyy");
		 Date presentDate=null;
		 if(fromdate!=null){
			 presentDate=dateFormat.parse(fromdate);
		 }
		 HttpSession session=request.getSession(false);
		 logger.info("----------- Post all paymets to tally those approved ----------------------");
		 List<Map<String,Object>> result=  tallyAllBillPostServiceImpl.post(session,presentDate);
		 int count=0;
	   	 for (Map<String, Object> map : result) {
		    	
	   	  for (Map.Entry<String, Object> entry : map.entrySet()) {
	   	        if(entry.getKey().equalsIgnoreCase("success"))
	   	        {
	   	        	count++;
	   	        Object value = entry.getValue();
	   	        
	   	        }
	   	        if(entry.getKey().equalsIgnoreCase("server"))
	   	        {
	   	        	return "Please start 'TallyERP9' server and load company";
	   	        }
	   	        
	   	      
	   	    
	   	  }
	   	 }
	   	
		if(count>0)
		{
			return count+ "Receipt Successfully Posted To Tally";
		}
		//return null;
		
		return "no! Receipt Posted To Tally";
		
	   }
	   
	   
	  /*============================Export to XML for CAM=================================================*/
	/*  @RequestMapping(value="/CashManagementCAMPaymentsHistory/exportToXml",method={RequestMethod.POST,RequestMethod.GET})
	  public void CashManagementexportToXml(HttpServletRequest request,HttpServletResponse response) throws ParseException, JAXBException, IOException
	  {
		  logger.info("===============================CashManagementExportToXml====================================");
		  String currentMonth=request.getParameter("fromDate");
		  Date fromMonth=new SimpleDateFormat("MMM yyyy").parse(currentMonth);
		  logger.info("===============================fromMonth="+fromMonth+"====================================");

		  List<BillingPaymentsEntity> camEntities=paymentService.getAllRecordsForCAM(fromMonth);
		  logger.info("=========================Size Of Data = "+camEntities.size()+"================================");

		  List<Map<String, Object>> mapsList = new ArrayList<>();
		  String xmlstr="";
		  for (BillingPaymentsEntity billingPaymentsEntity : camEntities)
		  {
			  String receiptNumber		= null;
			  String bankAcNumberLedger	= null;
			  String bankName			= null;
			  String paymentMode		= null;
			  Date receiptDate			= null;
			  double checkAmount		= 0.0;
			  Date cashReceiptDate		= null;

			  int paymentCollectionId=billingPaymentsEntity.getPaymentCollectionId();
			  BillingPaymentsEntity billingPaymentsEntity1 = paymentService.find(paymentCollectionId);

			  cashReceiptDate	= billingPaymentsEntity1.getPaymentDate(); 
			  receiptNumber		= billingPaymentsEntity1.getReceiptNo();
			  receiptDate		= billingPaymentsEntity1.getPaymentDate();
			  bankName			= billingPaymentsEntity1.getBankName();
			  paymentMode		= billingPaymentsEntity1.getPaymentMode();
			  checkAmount		= billingPaymentsEntity1.getPaymentAmount();
			  bankAcNumberLedger = ResourceBundle.getBundle("application").getString("bankLedger");

			  Map<String, Object> map=new LinkedHashMap<>();
			  
			  	System.err.println(billingPaymentsEntity1.getPaymentDate());
			  	System.err.println(billingPaymentsEntity1.getInstrumentNo());
			  map.put("instrumentDate", new  SimpleDateFormat("YYYYMMdd").format(billingPaymentsEntity1.getPaymentDate()));
			  map.put("instrumentNo", billingPaymentsEntity1.getInstrumentNo());
			  map.put("propertyNo", billingPaymentsEntity1.getProperty_No());
			  map.put("billSegmentCircle", "Utility Charges");
			  map.put("billAmount", billingPaymentsEntity1.getPaymentAmount());
			  mapsList.add(map);

			  String str=tallyPostingPrePaidRecieptsService.generateXML(mapsList,receiptNumber,cashReceiptDate,receiptDate,paymentMode,
					  bankAcNumberLedger,checkAmount,bankName,billingPaymentsEntity1.getPaymentCollectionId());
			  if(str!=null)
			  {
				  String tallystatus="XML Generated";
				  paymentService.upDateTallyStatus(billingPaymentsEntity1.getPaymentCollectionId(),tallystatus);
			  }
			  xmlstr=xmlstr+str+"\n";
		  }
		  FileUtils.writeStringToFile(new File("C:/BFM_TallyResponse/CAMpayments.xml"), xmlstr);
		  File file=new File("C:/BFM_TallyResponse/CAMpayments.xml");
		  ServletOutputStream servletOutputStream;

		  servletOutputStream = response.getOutputStream();
		  response.setContentType("application/vnd.ms-xml");
		  response.setHeader("Content-Disposition", "inline;filename=\"CAMpayments.xml"+"\"");
		  FileInputStream input = new FileInputStream(file);
		  IOUtils.copy(input, servletOutputStream);
		  servletOutputStream.flush();
		  servletOutputStream.close();
	  }*/
	  /*==================================================================================================*/
}
