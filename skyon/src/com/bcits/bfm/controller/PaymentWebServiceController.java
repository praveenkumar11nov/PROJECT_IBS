package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.PaymentSegmentCalcLines;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.accountsManagement.ElectricitySubLedgerService;
import com.bcits.bfm.service.advanceCollection.AdvanceCollectionService;
import com.bcits.bfm.service.advanceCollection.AdvancePaymentService;
import com.bcits.bfm.service.advanceCollection.ClearedPaymentsService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentCalcLineService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentSegmentCalcLinesService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentSegmentService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.deposits.DepositsLineItemsService;
import com.bcits.bfm.service.deposits.DepositsService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.NumberToWord;

@Controller
public class PaymentWebServiceController {
	
	private static final Log logger = LogFactory.getLog(PaymentWebServiceController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private AdjustmentService adjustmentService; 
	
	@Autowired
	private PaymentSegmentService paymentSegmentService; 
	
	@Autowired
	private ElectricityBillLineItemService billLineItemService;
	
	@Autowired
	private PaymentSegmentCalcLinesService paymentSegmentCalcLinesService;
	
	@Autowired
	private AdjustmentCalcLineService adjustmentCalcLineService;
	
	@Autowired
	private ElectricityLedgerService electricityLedgerService;
	
	@Autowired
	private ElectricitySubLedgerService electricitySubLedgerService;
	
	@Autowired
	private ElectricityBillsService electricityBillsService;
	
	@Autowired
	private DepositsService depositsService;
	
	@Autowired
	DepositsLineItemsService depositsLineItemsService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AdvanceCollectionService advanceCollectionService;
	
	@Autowired
	private AdvancePaymentService advancePaymentService;
	
	@Autowired
	private ContactService contactService;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ClearedPaymentsService clearedPaymentsService;
	
	@RequestMapping(value = "/paymentWebService/billingPaymentCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody HashMap<String, Object> savePaymentDetails(HttpServletRequest req,HttpServletResponse response,@RequestBody String body){
		
		logger.info("Inside payment details save method");
		
		HashMap<String, Object> paymentDetails = new HashMap<>();
		
		int accountId = 0;
		String paymentType = null;
		String partPayment = null;
		String disputeFlag = null;
		String paymentMode = null;
		String instrumentDate = null;
		String paymentComponets = null;
		String bankName = null;
		double billAmount = 0.0;
		String instrumentNo = null;
		double excessAmount = 0.0;
		
		try {
			JSONArray jsonArray = new JSONArray(body);
			for (int i = 0; i < jsonArray.length(); i++)
			  {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.get("accountId")!=null)
				{
					accountId = jsonObject.getInt("accountId");
				}
				
				if(jsonObject.get("paymentType")!=null)
				{
					paymentType = jsonObject.getString("paymentType");
				}
				
				if(jsonObject.get("partPayment")!=null)
				{
					partPayment = jsonObject.getString("partPayment");
				}
				
				if(jsonObject.get("disputeFlag")!=null)
				{
					disputeFlag = jsonObject.getString("disputeFlag");
				}
				
				if(jsonObject.get("paymentMode")!=null)
				{
					paymentMode = jsonObject.getString("paymentMode");
				}
				
				if(jsonObject.get("instrumentDate")!=null)
				{
					instrumentDate = jsonObject.getString("instrumentDate");
				}
				
				if(jsonObject.get("paymentComponets")!=null)
				{
					paymentComponets = jsonObject.getString("paymentComponets");
				}
				
				if(jsonObject.get("instrumentNo")!=null)
				{
					instrumentNo = jsonObject.getString("instrumentNo");
				}
				
				if(jsonObject.get("bankName")!=null)
				{
					bankName = jsonObject.getString("bankName");
				}
				
				if(jsonObject.get("billAmount")!=null)
				{
					billAmount = jsonObject.getDouble("billAmount");
				}
				
				if(jsonObject.get("excessAmount")!=null)
				{
					excessAmount = jsonObject.getDouble("excessAmount");
				}
				
			  }
			
			BillingPaymentsEntity billingPaymentsEntity = new BillingPaymentsEntity();
			
			billingPaymentsEntity.setReceiptNo(genrateRecieptNumber());
			billingPaymentsEntity.setPaymentAmount(billAmount);
			billingPaymentsEntity.setExcessAmount(excessAmount);
			billingPaymentsEntity.setPostedToGl("No");
			billingPaymentsEntity.setStatus("Created");
			billingPaymentsEntity.setPaymentDate(new Timestamp(new Date().getTime()));
			billingPaymentsEntity.setReceiptDate(new java.sql.Date(new Date().getTime()));
			
			if(!paymentMode.equals("Cash")){
				billingPaymentsEntity.setInstrumentDate(dateTimeCalender.getDateToStore(instrumentDate));
			}
			billingPaymentsEntity.setPaymentMode(paymentMode);
			billingPaymentsEntity.setAccountId(accountId);
			billingPaymentsEntity.setPartPayment(partPayment);
			billingPaymentsEntity.setPaymentType(paymentType);
			billingPaymentsEntity.setDisputeFlag(disputeFlag);
			billingPaymentsEntity.setPaymentComponets(paymentComponets);
			billingPaymentsEntity.setBankName(bankName);
			billingPaymentsEntity.setInstrumentNo(instrumentNo);
			
			//paymentService.update(billingPaymentsEntity);
			paymentsCodeSave(billingPaymentsEntity);
			
			paymentDetails.put("accountId", accountId);
			paymentDetails.put("paymentCollectionId", billingPaymentsEntity.getPaymentCollectionId());
			paymentDetails.put("paymentDate", billingPaymentsEntity.getPaymentDate());
			paymentDetails.put("receiptDate", billingPaymentsEntity.getReceiptDate());
			paymentDetails.put("receiptNo", billingPaymentsEntity.getReceiptNo());
			paymentDetails.put("paymentMode", billingPaymentsEntity.getPaymentMode());
			paymentDetails.put("bankName", billingPaymentsEntity.getBankName());
			paymentDetails.put("instrumentDate", billingPaymentsEntity.getInstrumentDate());
			paymentDetails.put("instrumentNo", billingPaymentsEntity.getInstrumentNo());
			paymentDetails.put("paymentAmount", billingPaymentsEntity.getPaymentAmount());
			paymentDetails.put("postedToGl", billingPaymentsEntity.getPostedToGl());
			paymentDetails.put("postedGlDate", billingPaymentsEntity.getPostedGlDate());
			paymentDetails.put("status", billingPaymentsEntity.getStatus());
			paymentDetails.put("createdBy", billingPaymentsEntity.getCreatedBy());
			paymentDetails.put("partPayment", billingPaymentsEntity.getPartPayment());
			paymentDetails.put("disputeFlag", billingPaymentsEntity.getDisputeFlag());
			paymentDetails.put("paymentType", billingPaymentsEntity.getPaymentType());
			paymentDetails.put("excessAmount", excessAmount);
			
		
	}catch(Exception e){
		try {
			PrintWriter out;
			out = response.getWriter();
			out.write("There seems to be Some Problem Out there.Please Try Again");
			e.printStackTrace();
			return null;
		} catch (IOException e1){
			e1.printStackTrace();
		}
	}
    	return paymentDetails;
	}
	
	@Async
	private void paymentsCodeSave(BillingPaymentsEntity billingPaymentsEntity){
		
		String componentList = billingPaymentsEntity.getPaymentComponets();
		if(billingPaymentsEntity.getPartPayment().equals("Yes")){
			String arry[] = componentList.split(",");
			
			if(billingPaymentsEntity.getLatePaymentAmount()>0){
				
				PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
				
				segmentsEntity.setBillMonth(new java.sql.Date(new Date().getTime()));
				segmentsEntity.setBillSegment("Late Payment");
				segmentsEntity.setStatus("Created");
				segmentsEntity.setBillReferenceNo(billingPaymentsEntity.getReceiptNo());		
				segmentsEntity.setAmount(billingPaymentsEntity.getLatePaymentAmount());
				segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
				paymentSegmentService.save(segmentsEntity);	
			}
			
			double paidAmount = billingPaymentsEntity.getPaymentAmount()-billingPaymentsEntity.getLatePaymentAmount();
			for(String str:arry){
				
				if(paidAmount>0 && paidAmount!=0){
					
					ElectricityLedgerEntity ledgerEntity = electricityLedgerService.find(Integer.parseInt(str));
					
					if(paidAmount>=ledgerEntity.getBalance()){
						
						PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
						
						segmentsEntity.setBillMonth(ledgerEntity.getLedgerDate());
						segmentsEntity.setBillSegment(ledgerEntity.getLedgerType().replace(" Ledger", ""));
						segmentsEntity.setStatus("Created");
						segmentsEntity.setBillReferenceNo(ledgerEntity.getPostReference());		
						segmentsEntity.setAmount(ledgerEntity.getBalance());
						
						segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
						paymentSegmentService.save(segmentsEntity);
						
						paidAmount = paidAmount-ledgerEntity.getBalance();
					}else{
						
						PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
						
						segmentsEntity.setBillMonth(ledgerEntity.getLedgerDate());
						segmentsEntity.setBillSegment(ledgerEntity.getLedgerType().replace(" Ledger", ""));
						segmentsEntity.setStatus("Created");
						segmentsEntity.setBillReferenceNo(ledgerEntity.getPostReference());		
						segmentsEntity.setAmount(paidAmount);
						
						segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
						paymentSegmentService.save(segmentsEntity);
						
						paidAmount = paidAmount-paidAmount;
					}
				}
				
			}
			
		}else if(billingPaymentsEntity.getPaymentType().equals("Pay Bill") && billingPaymentsEntity.getPartPayment().equals("No")){
			ElectricityLedgerEntity electricityLedgerEntity = paymentSegmentService.getAccountDetails(billingPaymentsEntity.getAccountId(),billingPaymentsEntity.getTypeOfService());
			segmentSavingPayBillCode(electricityLedgerEntity,billingPaymentsEntity);
			
		} else if(billingPaymentsEntity.getPaymentType().equals("Pay Deposit")){
			
			List<ElectricityBillEntity> list = paymentSegmentService.getAccountDetailsBasedOnDeposit(billingPaymentsEntity.getAccountId());
			segmentSavingCode(list,billingPaymentsEntity);
		} else if(billingPaymentsEntity.getPaymentType().equals("Pay Advance Bill"))
		{
			List<AdvanceBill> list = paymentSegmentService.getAccountDetailsBasedOnAdvanceBill(billingPaymentsEntity.getAccountId());
			for (AdvanceBill advanceBill : list)
			{
				PaymentSegmentsEntity segmentsEntity=new PaymentSegmentsEntity();
				segmentsEntity.setBillMonth(advanceBill.getAbBillDate());
				segmentsEntity.setBillSegment(advanceBill.getTypeOfService());
				segmentsEntity.setStatus("Created");
				segmentsEntity.setBillReferenceNo(advanceBill.getAbBillNo());		
				segmentsEntity.setAmount(advanceBill.getAbBillAmount());
				segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
				paymentSegmentService.save(segmentsEntity);
				
				PaymentSegmentCalcLines segmentCalcLines=new PaymentSegmentCalcLines();
				segmentCalcLines.setTransactionCode(advanceBill.getTransactionCode());
				segmentCalcLines.setAmount(advanceBill.getAbBillAmount());
				segmentCalcLines.setPaymentSegmentsEntity(segmentsEntity);
				paymentSegmentCalcLinesService.save(segmentCalcLines);
				
			}
		}
	}
	
	@Async
	private void segmentSavingPayBillCode(ElectricityLedgerEntity electricityLedgerEntity,BillingPaymentsEntity billingPaymentsEntity) {
		
		if(billingPaymentsEntity.getLatePaymentAmount()>0){
			
			PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
			
			segmentsEntity.setBillMonth(new java.sql.Date(new Date().getTime()));
			segmentsEntity.setBillSegment("Late Payment");
			segmentsEntity.setStatus("Created");
			segmentsEntity.setBillReferenceNo(billingPaymentsEntity.getReceiptNo());		
			segmentsEntity.setAmount(billingPaymentsEntity.getLatePaymentAmount());
			segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
			paymentSegmentService.save(segmentsEntity);	
		}
		
		double paidAmount = billingPaymentsEntity.getPaymentAmount()-billingPaymentsEntity.getLatePaymentAmount()-billingPaymentsEntity.getExcessAmount();
		if(electricityLedgerEntity!=null && paidAmount>0){
			
			PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
			
			segmentsEntity.setBillMonth(electricityLedgerEntity.getLedgerDate());
			segmentsEntity.setBillSegment(electricityLedgerEntity.getLedgerType().replace(" Ledger", ""));
			segmentsEntity.setStatus("Created");
			segmentsEntity.setBillReferenceNo(electricityLedgerEntity.getPostReference());		
			segmentsEntity.setAmount(electricityLedgerEntity.getBalance());
			
			segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
			paymentSegmentService.save(segmentsEntity);
		}
	}
	
	@Async
	private void segmentSavingCode(List<ElectricityBillEntity> list,BillingPaymentsEntity billingPaymentsEntity) {
		
		if(billingPaymentsEntity.getLatePaymentAmount()>0){
			
			PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
			
			segmentsEntity.setBillMonth(new java.sql.Date(new Date().getTime()));
			segmentsEntity.setBillSegment("Late Payment");
			segmentsEntity.setStatus("Created");
			segmentsEntity.setBillReferenceNo(billingPaymentsEntity.getReceiptNo());		
			segmentsEntity.setAmount(billingPaymentsEntity.getLatePaymentAmount());
			segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
			paymentSegmentService.save(segmentsEntity);	
		}
		
		for (ElectricityBillEntity electricityBillEntity : list) {
			
			PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
			
			segmentsEntity.setBillMonth(electricityBillEntity.getBillDate());
			segmentsEntity.setBillSegment(electricityBillEntity.getTypeOfService());
			segmentsEntity.setStatus("Created");
			segmentsEntity.setBillReferenceNo(electricityBillEntity.getBillNo());		
			segmentsEntity.setAmount(billingPaymentsEntity.getPaymentAmount()-billingPaymentsEntity.getLatePaymentAmount());
			segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
			paymentSegmentService.save(segmentsEntity);	
		
		}
	}
	
	
	public String genrateRecieptNumber() throws Exception {  
		/*Random generator = new Random();  
		generator.setSeed(System.currentTimeMillis());  
		   
		int num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		throw new Exception("Unable to generate PIN at this time..");  
		}  
		}  
		return "RC"+num; */
	   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int nextSeqVal = paymentService.autoGeneratedReceiptNoNumber();	 
		
		return "RC"+year+nextSeqVal; 
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/billingPayments/getPaymentDetails/{paymentCollectionId}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Map<String, Object> detailedBillPopup(@PathVariable int paymentCollectionId){

		Map<String, Object> paymentDetails = new HashMap<>();
		List<Map<String,Object>> segmentList = new ArrayList<Map<String,Object>>(); 
		
		Address address = null;
		Contact contactMob  = null;
		Contact contactEmail = null;
		String billRefenceNoStr = "";
		String billSegmentStr = "";
		String billMonth = "";
		String billAmountStr = "";
		String str = "";
		BillingPaymentsEntity billingPaymentsEntity = paymentService.find(paymentCollectionId);
		Account accountObj = accountService.find(billingPaymentsEntity.getAccountId());
		if(billingPaymentsEntity!=null){
			String addrQuery = "select obj from Address obj where obj.personId="+accountObj.getPerson().getPersonId()
					+" and obj.addressPrimary='Yes'";
			address = addressService.getSingleResult(addrQuery);

			String mobileQuery = "select obj from Contact obj where obj.personId="+accountObj.getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Mobile'";
			contactMob = contactService.getSingleResult(mobileQuery);
			String emailQuery = "select obj from Contact obj where obj.personId="+accountObj.getPerson().getPersonId()
					+" and obj.contactPrimary='Yes' and obj.contactType='Email'";
			contactEmail = contactService.getSingleResult(emailQuery);

			List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);

			if(segmentsList.size()>0){
				for(PaymentSegmentsEntity entity : segmentsList){
					Map<String, Object> segmentDetails = new HashMap<>();
					if(entity!=null)
						billSegmentStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getBillSegment()+"</td>";
					    billRefenceNoStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getBillReferenceNo()+"</td>";
					    billMonth+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+DateFormatUtils.format(entity.getBillMonth(), "MMM yyyy")+"</td>";
					    billAmountStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getAmount()+"</td>";
					    
					    segmentDetails.put("billSegment", entity.getBillSegment());
					    segmentDetails.put("billRefenceNo", entity.getBillReferenceNo());
					    segmentDetails.put("billMonth", entity.getBillMonth());
					    segmentDetails.put("billAmount", entity.getAmount());
					    
					    segmentList.add(segmentDetails);
				}
			}
			
			String paymentStatus = "";
			if(billingPaymentsEntity.getStatus().equals("Posted")){
				paymentStatus+="<td style='padding: 0.2em; border: 1px solid #808080;color:green'><b><i>Paid</i></b></td>";
			}else if(billingPaymentsEntity.getStatus().equals("Cancelled")){
				paymentStatus+="<td style='padding: 0.2em; border: 1px solid #808080;color:red'><b><i>Cancelled</i></b></td>";
			}
			
			String address1 = "";
			if(address!=null){
			if(address.getAddress1()!=null || !address.getAddress1().equals("")){
				address1+=address.getAddress1();
			}
			}
			
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

			str= ""
					+"<div id='myTab'>"
					+"<table id='tabs' style='width: 100%; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;'>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='/bfm_acq/resources/images/Ireo_Logo.jpg' height='100px' width='300px' /></td>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;vertical-align:middle' width='49%'>Orchid Centre, DLF Golf Course Rd,<br> IILM Institute, Sector 53, <br>Gurgaon, Haryana<br> 0124 475 4000 </td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;background: black; color: white; font-weight: bolder;' colspan='3'>Customer Details</td>"
					+"</tr>"
					+"<tr>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;padding-left: 25px' width='49%' ><b> <h4 id='name'>"+accountObj.getPerson().getFirstName()+" "+ lastName +"</h4> </b> "
					+ "		<span id='addr'>"+address1+"</span> <br>"
					+"		<span id='email'>"+contactContentMobile+"</span> , <span id='mobile'>"+contactContentEmail+"</span><br></td>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080; vertical-align: middle; border-left: 2px solid' colspan='2'>"
					+"		<table style='width: 100%;'>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Account Number</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' id='accno'>"+accountObj.getAccountNo()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Receipt Number</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+billingPaymentsEntity.getReceiptNo()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Receipt Date</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+DateFormatUtils.format(billingPaymentsEntity.getReceiptDate(), "dd/MM/yyyy")+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Paid Amount</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+billingPaymentsEntity.getPaymentAmount()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Payment Type</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+billingPaymentsEntity.getPaymentType()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Part Payment</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+billingPaymentsEntity.getPartPayment()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Payment Mode</b></td>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+billingPaymentsEntity.getPaymentMode()+"</td>"
					+"			</tr>"
					+"			<tr>"
					+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Payment Status</b></td>"
					+           paymentStatus
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"
					+"<tr style='background-color: black'>"
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
	                +				     billSegmentStr
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+ 						billMonth
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+						billRefenceNoStr
					+"					</table>"
					+"				</th>"
					+"				<th style='font-weight: bold;padding: 0.2em; border: 1px solid #808080;'>"
					+"					<table style='width: 100%;'>"
					+						billAmountStr
					+"					</table>"
					+"				</th>"
					+"			</tr>"
					+"		</table>"
					+"	</td>"
					+"</tr>"
					+"<tr style='background-color: black'>"
					+"	<td style='padding: 0.2em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Amount In Words : "+NumberToWord.convertNumberToWords((int)billingPaymentsEntity.getPaymentAmount())+" Only</td>"
					
					+"</tr>"
					+"</table>"
					+"</div>";
			
			
			paymentDetails.put("personName", accountObj.getPerson().getFirstName()+" "+ lastName);
			paymentDetails.put("address", address1);
			paymentDetails.put("email", contactContentEmail);
			paymentDetails.put("mobile", contactContentMobile);
			paymentDetails.put("accountNo", accountObj.getAccountNo());
			paymentDetails.put("receiptNo", billingPaymentsEntity.getReceiptNo());
			paymentDetails.put("receiptDate", billingPaymentsEntity.getReceiptDate());
			paymentDetails.put("paymentAmount", billingPaymentsEntity.getPaymentAmount());
			paymentDetails.put("paymentType", billingPaymentsEntity.getPaymentType());
			paymentDetails.put("partPayment", billingPaymentsEntity.getPartPayment());
			paymentDetails.put("paymentMode", billingPaymentsEntity.getPaymentMode());
			paymentDetails.put("paymentStatus", paymentStatus);
			paymentDetails.put("excessAmount", billingPaymentsEntity.getExcessAmount());
			paymentDetails.put("segmentList", segmentList);
		}
		return paymentDetails;

	}
}
