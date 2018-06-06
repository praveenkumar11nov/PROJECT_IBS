package com.bcits.bfm.controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.PrePaidPaymentEntity;
import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.PrePaidMeterService;
import com.bcits.bfm.service.PrepaidPaymentAdjustmentService;
import com.bcits.bfm.service.PrepaidRechargeService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.advanceCollection.AdvancePaymentService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentCalcLineService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentSegmentCalcLinesService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentSegmentService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.deposits.DepositsLineItemsService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.NumberToWord;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class PrePaidPaymentController {
 final static Logger logger = LoggerFactory.getLogger(PrePaidPaymentController.class);
 
 @Autowired
 private PrepaidPaymentAdjustmentService prepaidPaymentAdjustmentService;
 
 DateTimeCalender dateTimeCalender=new DateTimeCalender();
  @Autowired
  private BreadCrumbTreeService breadCrumbService;
  
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
  
    @PersistenceContext(unitName="bfm")
    protected EntityManager entityManager;
 
 @RequestMapping(value="/PrePaidPaymentAdjustment")
 public String adjustmentIndex(Model model,HttpServletRequest request)
 {
	 logger.info("in side adjustmentIndex");
	    model.addAttribute("ViewName", "Adjustments");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Prepaid Billing", 1, request);
		breadCrumbService.addNode("Manage Adjustments", 2, request);
	 return "prePaidpayment";
 }
 
 @RequestMapping(value="/prePaidPayment/getPropertyNamesUrl", method={RequestMethod.GET,RequestMethod.POST})
 public @ResponseBody List<?> readPropNums(){
	 
	 List<Map<String, Object>> resultList=new ArrayList<>();
	 Map<String, Object> mapList=null;
	 List<?> adjustList=prepaidPaymentAdjustmentService.ReadPropertys();
	 for(Iterator<?> iterator=adjustList.iterator();iterator.hasNext();){
		 final Object[] value=(Object[]) iterator.next();
		 mapList=new HashMap<>();
		 mapList.put("propertyId", value[0]);
		 mapList.put("property_No", value[1]);
		 resultList.add(mapList);
	 }
	 
	 return resultList;
 }
 
 @RequestMapping(value="/prePaidPayment/getMeterNumberUrl", method={RequestMethod.GET,RequestMethod.POST})
 public @ResponseBody List<?> readMeterNums(){
	 
	 List<Map<String, Object>> resultList=new ArrayList<>();
	 Map<String, Object> mapList=null;
	 List<?> adjustList=prepaidPaymentAdjustmentService.readMeters();
	 for(Iterator<?> iterator=adjustList.iterator();iterator.hasNext();){
		 final Object[] value=(Object[]) iterator.next();
		 mapList=new HashMap<>();
		// mapList.put("propertyId", value[0]);
		 mapList.put("consumerId", value[1]);
		 mapList.put("propertyId", value[2]);
		 resultList.add(mapList);
	 }
	 
	 return resultList;
 }
 
 @RequestMapping(value="/prePaidpaymentAdjustments/prePaidpaymentAdjustmentCreate",method={RequestMethod.GET,RequestMethod.POST})
 public @ResponseBody PrePaidPaymentEntity createMethode(HttpServletRequest request) throws Exception{
	 
	logger.info("in side AdjustmentcreateMethode");

	 System.out.println("PropertyNum " +	request.getParameter("property_No"));
      PrePaidPaymentEntity prPaymentEntity=new PrePaidPaymentEntity();
      
      prPaymentEntity.setPropertyId(Integer.parseInt(request.getParameter("property_No")));
      prPaymentEntity.setConsumerId(prepaidPaymentAdjustmentService.getConsumerId(Integer.parseInt(request.getParameter("property_No"))));
      prPaymentEntity.setMeterNumber(request.getParameter("consumerId"));
      prPaymentEntity.setAdjustmentAmount(Double.parseDouble(request.getParameter("adjustmentAmount")));
      prPaymentEntity.setAdjustmentDate(dateTimeCalender.getDateToStore(request.getParameter("adjustmentDate")));
      prPaymentEntity.setAdjustmentType(request.getParameter("paymentMode"));
      prPaymentEntity.setRemarks(request.getParameter("remarks"));
      prPaymentEntity.setStatus(request.getParameter("status"));
      String adnum=genrateAdjustmentNumber();
      prPaymentEntity.setAdjustmentNo(adnum);
      prPaymentEntity.setInstrumentDate(dateTimeCalender.getDateToStore(request.getParameter("instrumentDate")));
      prPaymentEntity.setInstrumentNo(request.getParameter("instrumentNo"));
     // System.out.println(" bankName" +request.getParameter("bankName"));
      if(request.getParameter("bankName")!=""){
      prPaymentEntity.setBankName(request.getParameter("bankName"));
      }else if(request.getParameter("paymentMode").equals("Online")){
    	  prPaymentEntity.setBankName("Paytm Gateway");
      }
      prepaidPaymentAdjustmentService.save(prPaymentEntity);
	if(request.getParameter("status").equalsIgnoreCase("Cleared")){
		 PrepaidRechargeEntity prepaidRechargeEntity=new PrepaidRechargeEntity();
		  prepaidRechargeEntity.setConsumer_Id(prepaidPaymentAdjustmentService.getConsumerId(Integer.parseInt(request.getParameter("property_No"))));
		  prepaidRechargeEntity.setMeterNumber(request.getParameter("consumerId"));
		  prepaidRechargeEntity.setRechargeAmount(Double.parseDouble(request.getParameter("adjustmentAmount")));
		  prepaidRechargeEntity.setStatus("Token Not Generated");
		  prepaidRechargeEntity.setTxn_ID(adnum);
		  prepaidRechargeEntity.setTxn_Date(dateTimeCalender.getDateToStore(request.getParameter("adjustmentDate")));
		  prepaidRechargeEntity.setTypeOfService("Electricity");
		  int propId=Integer.parseInt(request.getParameter("property_No"));
		  
		  int accId=prepaidMeterService.accountId(propId);
		  prepaidRechargeEntity.setAccountId(accId);
		  List<?> qList=entityManager.createQuery("select p.firstName,p.lastName,p.fatherName from Person p,PrePaidMeters pp where p.personId=pp.personId AND pp.propertyId ="+propId+"").getResultList();
		    for(Iterator<?> iterator2=qList.iterator();iterator2.hasNext();){
		    	final Object[] vaObjects=(Object[]) iterator2.next();
		    	System.out.println("personName"+ vaObjects[0]+""+vaObjects[1]);
		    	 prepaidRechargeEntity.setConsumer_Name(vaObjects[0]+""+vaObjects[1]);
		    	 prepaidRechargeEntity.setFather_Name(vaObjects[2].toString());
		    }
		    prepaidRechargeEntity.setModeOfPayment(request.getParameter("paymentMode"));
		    if(request.getParameter("bankName")!=""){
		    prepaidRechargeEntity.setBankName(request.getParameter("bankName"));
		    }else if(request.getParameter("paymentMode").equals("Online")){
		    	prepaidRechargeEntity.setBankName("Paytm Gateway");
		    }
		    prepaidRechargeEntity.setTallyStatus("Not Posted");
		    prepaidRechargeService.save(prepaidRechargeEntity);
		  }
	
	return prPaymentEntity;
 }
 
 public String genrateAdjustmentNumber() throws Exception {  
		/*Random generator = new Random();  
		generator.setSeed(System.currentTimeMillis());  
		   
		int num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		throw new Exception("Unable to generate PIN at this time..");  
		}  
		}  
		return "AD"+num;*/ 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int nextSeqVal = adjustmentService.autoGeneratedAdjustmentNumber();	 
		
		return "AD"+year+nextSeqVal;		   
	}
 
	@RequestMapping(value = "/prePaidpaymentAdjustments/readUrl", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> prePaidReadAdjustments() {
		logger.info("In read adjustments method");
		//List<PaymentAdjustmentEntity> adjustmentEntities = adjustmentService.findAll();
	List<?>	adjustmentEntities=prepaidPaymentAdjustmentService.getAllData();
	//System.out.println(adjustmentEntities);
		
		return adjustmentEntities;
	}
	
	@RequestMapping(value="/prePaidpaymentAdjustments/paymentAdjustmentUpdate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object upDatePayAdjustment(HttpServletRequest request) throws ParseException{
		System.out.println("in side upDate Methode");
		int aId=Integer.parseInt(request.getParameter("adjustmentId"));
		
		PrePaidPaymentEntity prPaidPaymentEntity=prepaidPaymentAdjustmentService.find(aId);
		  prPaidPaymentEntity.setStatus(request.getParameter("status"));
		  if(prPaidPaymentEntity.getAdjustmentAmount()!=Double.parseDouble(request.getParameter("adjustmentAmount"))){
		  prPaidPaymentEntity.setAdjustmentAmount(Double.parseDouble(request.getParameter("adjustmentAmount")));
		  }
		  if(prPaidPaymentEntity.getAdjustmentDate()!=dateTimeCalender.getDateToStore(request.getParameter("adjustmentDate"))){
		  prPaidPaymentEntity.setAdjustmentDate(dateTimeCalender.getDateToStore(request.getParameter("adjustmentDate")));
		  }
		 
		  if(!prPaidPaymentEntity.getRemarks().equals(request.getParameter("remarks"))){
		  prPaidPaymentEntity.setRemarks(request.getParameter("remarks"));
		  }
		  PrepaidRechargeEntity prepaidRechargeEntity=new PrepaidRechargeEntity();
		  if(prPaidPaymentEntity.getStatus().equals("Cleared")){
		  prepaidRechargeEntity.setConsumer_Id(prPaidPaymentEntity.getConsumerId());
		  prepaidRechargeEntity.setMeterNumber(prPaidPaymentEntity.getMeterNumber());
		  prepaidRechargeEntity.setRechargeAmount(prPaidPaymentEntity.getAdjustmentAmount());
		  prepaidRechargeEntity.setStatus("Token Not Generated");
		  prepaidRechargeEntity.setTxn_ID(prPaidPaymentEntity.getAdjustmentNo());
		  prepaidRechargeEntity.setTxn_Date(prPaidPaymentEntity.getAdjustmentDate());
		  prepaidRechargeEntity.setTypeOfService("Electricity");
		  int propId=prPaidPaymentEntity.getPropertyId();
		  
		  int accId=prepaidMeterService.accountId(propId);
		  prepaidRechargeEntity.setAccountId(accId);
		  List<?> qList=entityManager.createQuery("select p.firstName,p.lastName,p.fatherName from Person p,PrePaidMeters pp where p.personId=pp.personId AND pp.propertyId ="+propId+"").getResultList();
		    for(Iterator<?> iterator2=qList.iterator();iterator2.hasNext();){
		    	final Object[] vaObjects=(Object[]) iterator2.next();
		    	System.out.println("personName"+ vaObjects[0]+""+vaObjects[1]);
		    	 prepaidRechargeEntity.setConsumer_Name(vaObjects[0]+""+vaObjects[1]);
		    	 prepaidRechargeEntity.setFather_Name(vaObjects[2].toString());
		    }
		    prepaidRechargeEntity.setModeOfPayment(prPaidPaymentEntity.getAdjustmentType());
		    prepaidRechargeEntity.setBankName(prPaidPaymentEntity.getBankName());
		    prepaidRechargeEntity.setTallyStatus("Not Posted");
		    prepaidRechargeService.save(prepaidRechargeEntity);
		  }
		  
		 
		return prepaidPaymentAdjustmentService.update(prPaidPaymentEntity);
	}
	
	@RequestMapping(value="/prePaidpaymentAdjustments/destroyUrl",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object deleteAdjustPayDetsails(@ModelAttribute("prPaidPaymentEntity") PrePaidPaymentEntity prPaidPaymentEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete deleteAdjustPayDetsails");
		//JsonResponse errorResponse = new JsonResponse();
		
		try {
			prepaidPaymentAdjustmentService.delete(prPaidPaymentEntity.getAdjustmentId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ss.setComplete();
		return prPaidPaymentEntity;
	}
	
	
	/**********************************************************prepaid Payemnt History**************************/
	
	@RequestMapping(value="/paymentHistory")
	public String getPyamentHistory(Model model,HttpServletRequest request){
		logger.info("in side Payment History ");
		model.addAttribute("ViewName", "Payment History");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Prepaid Billing", 1, request);
		breadCrumbService.addNode("Manage Payment History", 2, request);
		return "prepaidPaymentsHistory";
	}
	
	@RequestMapping(value = "/paymentHistory/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getPrePaidBillsDataForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("PrepaidRechargeEntity", attributeList, null));

		return set;
	}
	
	@RequestMapping(value="/prepaidPaymentsHistory/readUrl",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readPaymentHistory(){
		logger.info("in side paymentHistory Methode");
	    List<Map<String, Object>> resultList=new ArrayList<>();
	    Map<String, Object> mapList=null;
	    List<PrepaidRechargeEntity>	prepaidRechargeEntity=prepaidRechargeService.getPaymentHistory();
	    System.out.println("list size ** "+prepaidRechargeEntity.size());
	    for (PrepaidRechargeEntity prepaidRechargeEntity2 : prepaidRechargeEntity) {
		  mapList=new HashMap<>();
		  mapList.put("paymentCollectionId", prepaidRechargeEntity2.getPreRechargeId());
	      int propertyId=prepaidMeterService.getPropertyId(prepaidRechargeEntity2.getConsumer_Id());
	      System.out.println("propID "+propertyId);
		  mapList.put("property_No", prepaidMeterService.getPropertyNo(propertyId));
		  mapList.put("personName", prepaidRechargeEntity2.getConsumer_Name());
		  mapList.put("consumerId", prepaidRechargeEntity2.getConsumer_Id());
		  mapList.put("meterNo", prepaidRechargeEntity2.getMeterNumber());
		  mapList.put("receiptNo", prepaidRechargeEntity2.getTxn_ID());
		  mapList.put("receiptDate", prepaidRechargeEntity2.getTxn_Date());
		  mapList.put("rechargedAmount", (double)prepaidRechargeEntity2.getRechargeAmount());
		  if(prepaidRechargeEntity2.getModeOfPayment()!=null){
		  if(!(prepaidRechargeEntity2.getModeOfPayment().equals("NB") || prepaidRechargeEntity2.getModeOfPayment().equals("DC") || prepaidRechargeEntity2.getModeOfPayment().equals("CC") || prepaidRechargeEntity2.getModeOfPayment().equals("PPI"))){
				//System.out.println(prepaidRechargeEntity2.getModeOfPayment());
				mapList.put("paymentMode", prepaidRechargeEntity2.getModeOfPayment());
			}else{
				mapList.put("paymentMode","Online");
			}
		  }
		  mapList.put("instrumentDate", prepaidRechargeEntity2.getTxn_Date());
		  mapList.put("status", prepaidRechargeEntity2.getStatus());
		  mapList.put("tallyStatus", prepaidRechargeEntity2.getTallyStatus());
		 /* if(prepaidRechargeEntity2.getBankName()!=null){
			  
		  mapList.put("bankName", prepaidRechargeEntity2.getBankName());
		  }*/
		  resultList.add(mapList);
	   }
	    
		return resultList;
	    }
	
	@RequestMapping(value="/prepaidPaymentsHistory/searchprepaidPaymentsHistoryByDate", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> searchPaymentHistoryByDate(HttpServletRequest request) throws ParseException{
		logger.info("in side searchPaymentHistoryByDate methode");
		logger.info(request.getParameter("fromDate")); 
		logger.info(request.getParameter("toDate"));
		String fromDate=request.getParameter("fromDate");
		String toDate=request.getParameter("toDate");
		Date fromdate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
		Date todate=new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
		
		 List<Map<String, Object>> resultList=new ArrayList<>();
		    Map<String, Object> mapList=null;
		    List<PrepaidRechargeEntity>	prepaidRechargeEntity=prepaidRechargeService.getPaymentHistoryByMonth(fromdate, todate);
		    System.out.println("list size ** "+prepaidRechargeEntity.size());
		    for (PrepaidRechargeEntity prepaidRechargeEntity2 : prepaidRechargeEntity) {
			  mapList=new HashMap<>();
			  mapList.put("paymentCollectionId", prepaidRechargeEntity2.getPreRechargeId());
		      int propertyId=prepaidMeterService.getPropertyId(prepaidRechargeEntity2.getConsumer_Id());
		      System.out.println("propID "+propertyId);
			  mapList.put("property_No", prepaidMeterService.getPropertyNo(propertyId));
			  mapList.put("personName", prepaidRechargeEntity2.getConsumer_Name());
			  mapList.put("consumerId", prepaidRechargeEntity2.getConsumer_Id());
			  mapList.put("receiptNo", prepaidRechargeEntity2.getTxn_ID());
			  mapList.put("receiptDate", prepaidRechargeEntity2.getTxn_Date());
			  mapList.put("rechargedAmount", prepaidRechargeEntity2.getRechargeAmount());
			  if(prepaidRechargeEntity2.getModeOfPayment()!=null){
			  if(!(prepaidRechargeEntity2.getModeOfPayment().equals("NB") || prepaidRechargeEntity2.getModeOfPayment().equals("DC") || prepaidRechargeEntity2.getModeOfPayment().equals("CC") || prepaidRechargeEntity2.getModeOfPayment().equals("PPI"))){
					//System.out.println(prepaidRechargeEntity2.getModeOfPayment());
					mapList.put("paymentMode", prepaidRechargeEntity2.getModeOfPayment());
				}else{
					mapList.put("paymentMode","Online");
				}
			  }
			  mapList.put("instrumentDate", prepaidRechargeEntity2.getTxn_Date());
			  mapList.put("status", prepaidRechargeEntity2.getStatus());
			  mapList.put("tallyStatus", prepaidRechargeEntity2.getTallyStatus());
			 /* if(prepaidRechargeEntity2.getBankName()!=null){
				  
			  mapList.put("bankName", prepaidRechargeEntity2.getBankName());
			  }*/
			  resultList.add(mapList);
		   }
		    
			return resultList;
	}
	
	@RequestMapping(value = "/prepaidPaymentsHistory/exportprepaidPaymentsHistoryData", method = {RequestMethod.POST,RequestMethod.GET})
	   public String exportPaymentHistoryFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
		 logger.info("in side exportPaymentHistoryFile");
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	       
			logger.info(request.getParameter("fromDate")); 
			logger.info(request.getParameter("toDate"));
			String fromDate=request.getParameter("fromDate");
			String toDate=request.getParameter("toDate");
			Date fromdate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
			Date todate=new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
		    List<PrepaidRechargeEntity>	prepaidRechargeEntity=prepaidRechargeService.getPaymentHistoryByMonth(fromdate, todate);

		   System.out.println("list size++++++++++"+prepaidRechargeEntity.size());
		
	               
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
	    	
	        header.createCell(0).setCellValue("Property No");
	        header.createCell(1).setCellValue("Person Name");
	        header.createCell(2).setCellValue("Consumer Id");
	        header.createCell(3).setCellValue("Amount");
	        header.createCell(4).setCellValue("Receipt No");
	        header.createCell(5).setCellValue("Receipt Date");
	        header.createCell(6).setCellValue("Payment Mode");
	        header.createCell(7).setCellValue("Instrument Date");
	        header.createCell(8).setCellValue("Status");
	        header.createCell(9).setCellValue("Tally Status");
	     
	       
	       
	        for(int j = 0; j<=9; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
	        }
	        
	        int count = 1;
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        for(PrepaidRechargeEntity prepaidRechargeEntity2:prepaidRechargeEntity){
	 	      // final Object[] values=(Object[]) iterator.next();
	    		
	    		XSSFRow row = sheet.createRow(count);
	    		
	    		 if(prepaidRechargeEntity2.getConsumer_Id()!=null){
	    			 int propertyId=prepaidMeterService.getPropertyId(prepaidRechargeEntity2.getConsumer_Id());
	    		      System.out.println("propID "+propertyId);
	    		row.createCell(0).setCellValue(prepaidMeterService.getPropertyNo(propertyId));
	    		 }
	    		  if(prepaidRechargeEntity2.getConsumer_Name()!=null){
	    			 
	    		row.createCell(1).setCellValue(prepaidRechargeEntity2.getConsumer_Name());
	    		
	    		  }
	    		 
	    		  if(prepaidRechargeEntity2.getConsumer_Id()!=null){
		    			 
	  	    		row.createCell(2).setCellValue(prepaidRechargeEntity2.getConsumer_Id());
	  	    		
	  	    		  }
	    		  if(prepaidRechargeEntity2.getRechargeAmount()!=0.0){
		    			 
	  	    		row.createCell(3).setCellValue(prepaidRechargeEntity2.getRechargeAmount());
	  	    		
	  	    		  }
	    		  if(prepaidRechargeEntity2.getTxn_ID()!=null){
		    			 
	  	    		row.createCell(4).setCellValue(prepaidRechargeEntity2.getTxn_ID());
	  	    		
	  	    		  }
	    		  if(prepaidRechargeEntity2.getTxn_Date()!=null){
		    			 
	  	    		row.createCell(5).setCellValue(sdf.format(prepaidRechargeEntity2.getTxn_Date()));
	  	    		
	  	    		  }
	    		  if(prepaidRechargeEntity2.getModeOfPayment()!=null){
	    			  if(!(prepaidRechargeEntity2.getModeOfPayment().equals("NB") || prepaidRechargeEntity2.getModeOfPayment().equals("DC") || prepaidRechargeEntity2.getModeOfPayment().equals("CC") || prepaidRechargeEntity2.getModeOfPayment().equals("PPI"))){
	    					//System.out.println(prepaidRechargeEntity2.getModeOfPayment());
	    	  	    		row.createCell(6).setCellValue(prepaidRechargeEntity2.getModeOfPayment());
	    				}else{
	    	  	    		row.createCell(6).setCellValue("Online");
	    				} 
	  	    		
	  	    		  }
	    		  if(prepaidRechargeEntity2.getTxn_Date()!=null){
		    			 
	  	    		row.createCell(7).setCellValue(sdf.format(prepaidRechargeEntity2.getTxn_Date()));
	  	    		
	  	    		  }
	    		  if(prepaidRechargeEntity2.getStatus()!=null){
		    			 
		  	    		row.createCell(8).setCellValue(prepaidRechargeEntity2.getStatus());
		  	    		
		  	    		  }
	    		  row.createCell(9).setCellValue(prepaidRechargeEntity2.getTallyStatus());
	    		  
	    		count ++;
			}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Payment History.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
	
	@RequestMapping(value="/collections/prepaidPaymentsHistory/getprepaidPaymentsHistoryDetails",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String detailedBillPopup(@RequestParam("paymentCollectionId") int paymentCollectionId){

		Address address = null;
		Contact contactMob  = null;
		Contact contactEmail = null;
		String billRefenceNoStr = "";
		String billSegmentStr = "";
		String billMonth = "";
		String billAmountStr = "";
		String str = "";
		String paymentmode="";
		System.out.println("coming in method.............");
		//BillingPaymentsEntity billingPaymentsEntity = paymentService.find(paymentCollectionId);
		PrepaidRechargeEntity prepaidRechargeEntity=prepaidRechargeService.find(paymentCollectionId);
		Account accountObj = accountService.find(prepaidRechargeEntity.getAccountId());
		if(prepaidRechargeEntity!=null){
			String addrQuery = "select obj from Address obj where obj.personId="+accountObj.getPerson().getPersonId()
					+" and obj.addressPrimary='Yes'";
			address = addressService.getSingleResult(addrQuery);

			String mobileQuery = "select obj from Contact obj where obj.personId="+accountObj.getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
			contactMob = contactService.getSingleResult(mobileQuery);
			String emailQuery = "select obj from Contact obj where obj.personId="+accountObj.getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Email'";
			contactEmail = contactService.getSingleResult(emailQuery);

			/*List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);

			if(segmentsList.size()>0){
				for(PaymentSegmentsEntity entity : segmentsList){
					if(entity!=null)
						billSegmentStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getBillSegment()+"</td>";
					    billRefenceNoStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getBillReferenceNo()+"</td>";
					    billMonth+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+DateFormatUtils.format(entity.getBillMonth(), "MMM yyyy")+"</td>";
					    billAmountStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getAmount()+"</td>";
				}
			}*/
			
			String paymentStatus = "";
			if(prepaidRechargeEntity.getStatus().equals("Token Generated")){
				paymentStatus+="<td style='padding: 0.2em; border: 1px solid #808080;color:green'><b><i>Token Generated</i></b></td>";
			}else if(prepaidRechargeEntity.getStatus().equals("Token Not Generated")){
				paymentStatus+="<td style='padding: 0.2em; border: 1px solid #808080;color:red'><b><i>Token Not Generated</i></b></td>";
			}
			
			if(prepaidRechargeEntity.getModeOfPayment().equals("Cheque") || prepaidRechargeEntity.getModeOfPayment().equals("DD") || prepaidRechargeEntity.getModeOfPayment().equals("RTGS/NEFT") || prepaidRechargeEntity.getModeOfPayment().equals("AdvancePayment")){
				paymentmode=prepaidRechargeEntity.getModeOfPayment();
			}else{
				paymentmode="Online";
			};
			/*String address1 = "";
			if(address!=null){
			if(address.getAddress1()!=null || !address.getAddress1().equals("")){
				address1+=address.getAddress1();
			}
			}*/
			
			String contactContentMobile = "";
			if(contactMob!=null){
			if(contactMob.getContactContent()!=null || !contactMob.getContactContent().equals("")){
				contactContentMobile+=contactMob.getContactContent();
			}
			}
			
			String contactContentEmail = "";
			if(contactMob!=null){
			if(contactEmail.getContactContent()!=null || !contactEmail.getContactContent().equals("")){
				contactContentEmail+=contactEmail.getContactContent();
			}
			}
			
			String lastName = "";
			if(accountObj.getPerson().getFirstName()!=null || !accountObj.getPerson().getFirstName().equals("")){
				lastName+=accountObj.getPerson().getLastName();
			}
			
			Property property = propertyService.find(accountObj.getPropertyId());

			String address1 = property.getProperty_No()+","+property.getBlocks().getBlockName();
			String city = "Gurgaon";

			str= ""
					+"<div id='myTab'>"
					+"<table id='tabs' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='resources/images/skyon.png' width='252px' /></td>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;vertical-align:middle' width='49%'><span style='vertical-align: middle;font-size: medium;'>"+ResourceBundle.getBundle("utils").getString("report.address")+"</span></td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"+accountObj.getPerson().getFirstName()+" "+ lastName +"</h4> </b> "
					+ "		<span id='addr'>"+address1+",</span> <br>"
					+ "		<span id='addr'>"+city+".</span> <br>"
					//+"		<span id='email'>"+contactContentMobile+"</span> , <span id='mobile'>"+contactContentEmail+"</span><br></td>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080; vertical-align: middle; border-left: 2px solid' colspan='2'>"
					+"		<table style='width: 100%;'>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Consumer ID</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' id='accno'>"+prepaidRechargeEntity.getConsumer_Id()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Receipt Number</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+prepaidRechargeEntity.getTxn_ID()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Receipt Date</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+DateFormatUtils.format(prepaidRechargeEntity.getTxn_Date(), "dd/MM/yyyy")+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Recharged Amount</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+prepaidRechargeEntity.getRechargeAmount()+"</td>"
					+"			</tr>"
					
					/*+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Excess Amount</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+prepaidRechargeEntity.getExcessAmount()+"</td>"
					+"			</tr>"*/
					
					/*+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Payment Type</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+prepaidRechargeEntity.getPaymentType()+"</td>"
					+"			</tr>"*/
					
					/*+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Part Payment</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+"NO"+"</td>"
					+"			</tr>"*/
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Payment Mode</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+paymentmode+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Payment Status</b></td>"
					+           paymentStatus
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"
					/*+"<tr style='background-color: black'>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan='3'>Payment Segments</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;' colspan='3'>"
					+"		<table style='width: 100%; text-align: center;'>"
					+"			<tr>"
					+"				<td style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Bill Segment</td>"
					+"				<td style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Bill Month</td>"
					+"				<td style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Bill Reference No</td>"
					+"				<td style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Bill Amount</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"+billSegmentStr+"</th>"
					+"						</tr>"
	                +				     billSegmentStr
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Name</th>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Value</th>"
					+"						</tr>"
					+ 						billMonth
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Name</th>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Value</th>"
					+"						</tr>"
					+						billRefenceNoStr
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+"						<tr>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Name</th>"
					+"							<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>Value</th>"
					+"						</tr>"
					+						billAmountStr
					+"					</table>"
					+"				</th>"
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"*/
					+"<tr style='background-color: black'>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Amount In Words : "+NumberToWord.convertNumberToWords((int)prepaidRechargeEntity.getRechargeAmount())+" Only</td>"
					
					+"</tr>"
					+"</table>"
					+"</div>";
		}
		return str;

	}
}
