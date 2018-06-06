package com.bcits.bfm.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.AdjustmentCalcLinesEntity;
import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Deposits;
import com.bcits.bfm.model.DepositsLineItems;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.PaymentSegmentCalcLines;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.CommonService;
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
import com.bcits.bfm.service.postInvoiceToTallyService.TallyAdjustmentPostService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyInvoicePostService;
//import com.bcits.bfm.service.postInvoiceToTallyServiceImpl.TallyAllAdjustmentsPostServiceImpl;
import com.bcits.bfm.service.postInvoiceToTallyServiceImpl.TallyAllBillPostServiceImpl;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.NumberToWord;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Billing Collections
 * Use Case : Payments
 * 
 * @author Ravi Shankar Reddy
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class BillingPaymentManagementController {

	static Logger logger = LoggerFactory.getLogger(MeterStatusController.class);

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private AdjustmentService adjustmentService; 
	
	@Autowired
	private PaymentSegmentService paymentSegmentService; 
	
	@Autowired
	private PropertyService propertyService;
	
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
	private TallyInvoicePostService tallyInvoicePostService;
	
	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private TallyAllBillPostServiceImpl tallyAllBillPostServiceImpl;
	
	@Autowired
    private TallyAdjustmentPostService tallyAdjustmentPostService;
	/*
	@Autowired
	private TallyAllAdjustmentsPostServiceImpl tallyAllAdjustmentsPostServiceImpl;
	*/
	
	
	@RequestMapping("/billingPayments")
	public String billingPayments(HttpServletRequest request, Model model) {
		logger.info("In billing payments method");
		model.addAttribute("ViewName", "Cash Management");	
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Cash Management", 1, request);
		breadCrumbService.addNode("Manage Payments", 2, request);
		return "billingPayments/billingPayments";
	}
	
	@RequestMapping("/paymentAdjustment")
	public String paymentAdjustment(HttpServletRequest request, Model model) {
		logger.info("In payment adjustment method");
		model.addAttribute("ViewName", "Cash Management");	
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Cash Management", 1, request);
		breadCrumbService.addNode("Manage Adjustments", 2, request);
		return "billingPayments/paymentAdjustment";
	}
	
	@RequestMapping("/reconciliation")
	public String reconciliation(HttpServletRequest request, Model model) {
		logger.info("In reconciliation method");
		model.addAttribute("ViewName", "Cash Management");	
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Cash Management", 1, request);
		breadCrumbService.addNode("Manage Reconciliation", 2, request);
		return "billingPayments/reconciliation";
	}
	
	@RequestMapping(value = "/billingPayments/getAllBankNames", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getAllChecks() {
		
		String[] bankNamesArray = ResourceBundle.getBundle("utils").getString("BillingPayments.bankNames").split(",");
		List<String> bankNames=new ArrayList<String>();
		Collections.addAll(bankNames, bankNamesArray);
		
		/*bankNames.add("Andhra Bank");
		bankNames.add("Bank of Baroda");
		bankNames.add("Bank of India");
		bankNames.add("Canara Bank");
		bankNames.add("Central Bank of India");
		bankNames.add("Dena Bank");
		bankNames.add("IDBI Bank");
		bankNames.add("Indian Bank");
		bankNames.add("Indian Overseas Bank");
		bankNames.add("Oriental Bank of Commerce");
		bankNames.add("Punjab National Bank");
		bankNames.add("Syndicate Bank");
		bankNames.add("UCO Bank");
		bankNames.add("Union Bank of India");
		bankNames.add("Vijaya Bank");
		bankNames.add("State Bank of India");
		bankNames.add("State Bank of Hyderabad");
		bankNames.add("State Bank of Indore");
		bankNames.add("Axis Bank");
		bankNames.add("Federal Bank");
		bankNames.add("HDFC Bank");
		bankNames.add("ICICI Bank");
		bankNames.add("ING Vysya Bank");
		bankNames.add("Karnataka Bank");
		bankNames.add("Karur Vysya Bank");
		bankNames.add("Kotak Mahindra Bank");
		bankNames.add("South Indian Bank");
		bankNames.add("YES Bank");
		bankNames.add("Citi Bank");
		bankNames.add("HSBC Bank");*/
		
		Collections.sort(bankNames);
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> iterator = bankNames.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			map = new HashMap<String, String>();
			map.put("bankName", string);
			//map.put("bankName", string);
			result.add(map);
		}
		return result;
	}
	
	 // ****************************** Payments Read,Create,Delete methods ********************************//
	@RequestMapping(value = "/billingPayments/billingPaymentRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<BillingPaymentsEntity> readPayments() {
		logger.info("In read payments method");
		List<BillingPaymentsEntity> paymentsEntities = paymentService.findAll();
		return paymentsEntities;
	}

	@RequestMapping(value = "/billingPayments/billingPaymentCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object savePaymentDetails(@ModelAttribute("billingPaymentsEntity") BillingPaymentsEntity billingPaymentsEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws Exception {

		logger.info("---------------- In save payments details method --------------------------");
		billingPaymentsEntity.setReceiptNo(genrateRecieptNumber());
		billingPaymentsEntity.setPaymentAmount(billingPaymentsEntity.getPaymentAmount());
		billingPaymentsEntity.setExcessAmount(billingPaymentsEntity.getExcessAmount());
		billingPaymentsEntity.setPostedToGl("No");
		billingPaymentsEntity.setStatus("Created");
		billingPaymentsEntity.setTallyStatus("Not Posted");
		billingPaymentsEntity.setPaymentDate(new Timestamp(new Date().getTime()));
		billingPaymentsEntity.setReceiptDate(dateTimeCalender.getDateToStore(req.getParameter("receiptDate")));
		
		if(!billingPaymentsEntity.getPaymentMode().equals("Cash")){
			billingPaymentsEntity.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
		}else{
			billingPaymentsEntity.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
			billingPaymentsEntity.setInstrumentNo(billingPaymentsEntity.getInstrumentNo());
			billingPaymentsEntity.setBankName(billingPaymentsEntity.getBankName());
		}
		billingPaymentsEntity.setAccountId(billingPaymentsEntity.getAccountId());
		
		validator.validate(billingPaymentsEntity, bindingResult);
		
		paymentService.save(billingPaymentsEntity);
		if(billingPaymentsEntity.getPaymentAmount()>0){
			paymentsCodeSave(billingPaymentsEntity);
		}
		sessionStatus.setComplete();
		return billingPaymentsEntity;
		
	}
	
	@Async
	private void paymentsCodeSave(BillingPaymentsEntity billingPaymentsEntity){
		
		if(billingPaymentsEntity.getPartPayment().equals("Yes")){
			
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
			ElectricityLedgerEntity electricityLedgerEntity = paymentSegmentService.getAccountDetails(billingPaymentsEntity.getAccountId(),billingPaymentsEntity.getTypeOfService());
			if(electricityLedgerEntity!=null && paidAmount>0){
				
				PaymentSegmentsEntity segmentsEntity = new PaymentSegmentsEntity();
				
				segmentsEntity.setBillMonth(electricityLedgerEntity.getLedgerDate());
				segmentsEntity.setBillSegment(electricityLedgerEntity.getLedgerType().replace(" Ledger", ""));
				segmentsEntity.setStatus("Created");
				segmentsEntity.setBillReferenceNo(electricityLedgerEntity.getPostReference());		
				segmentsEntity.setAmount(paidAmount);
				
				segmentsEntity.setBillingPaymentsEntity(billingPaymentsEntity);
				paymentSegmentService.save(segmentsEntity);
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
		
		/*double paidAmount = billingPaymentsEntity.getPaymentAmount()-billingPaymentsEntity.getLatePaymentAmount()-billingPaymentsEntity.getExcessAmount();
		for (ElectricityLedgerEntity ledgerEntity : list) {
			
			if(paidAmount>0 && paidAmount!=0){
				
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
		}*/
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

	@RequestMapping(value = "/billingPayments/billingPaymentUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editMeterStatus(@ModelAttribute("billingPaymentsEntity") BillingPaymentsEntity billingPaymentsEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In edit payments details method");
		BillingPaymentsEntity temp=paymentService.find(billingPaymentsEntity.getPaymentCollectionId());			
		billingPaymentsEntity.setPaymentDate(temp.getPaymentDate());
		billingPaymentsEntity.setPostedGlDate(temp.getPostedGlDate());
		billingPaymentsEntity.setReceiptDate(dateTimeCalender.getDateToStore(req.getParameter("receiptDate")));
		billingPaymentsEntity.setInstrumentDate(dateTimeCalender.getDateToStore(req.getParameter("instrumentDate")));
		
		validator.validate(billingPaymentsEntity, bindingResult);
		paymentService.update(billingPaymentsEntity);
		
		return billingPaymentsEntity;
	}

	@RequestMapping(value = "/billingPayments/billingPaymentDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deletePaymentDetsails(@ModelAttribute("billingPaymentsEntity") BillingPaymentsEntity billingPaymentsEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete payment details method");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			paymentService.delete(billingPaymentsEntity.getPaymentCollectionId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return billingPaymentsEntity;
	}
	
	@RequestMapping(value = "/billingPayments/setCollectionPaymentStatus/{paymentCollectionId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setCollectionPaymentStatus(@PathVariable int paymentCollectionId,@PathVariable String operation, HttpServletResponse response) {
		
		logger.info("In help topic status change method");
		if (operation.equalsIgnoreCase("activate")){
			paymentService.setCollectionPaymentStatus(paymentCollectionId, operation, response);
		}else{
			synchronized (this) {
				postPaymentCode(paymentCollectionId,operation,response);
			}
		}
			
	}
	
	public void postPaymentCode(int paymentCollectionId,String operation,HttpServletResponse response){
		
		BillingPaymentsEntity billingPaymentsEntity = paymentService.find(paymentCollectionId);
		
		if(billingPaymentsEntity.getStatus().equals("Approved")){
			
			if(billingPaymentsEntity.getPaymentType().equals("Pay Bill")){
				
					List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);
					
					for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
						
						if(paymentSegmentsEntity.getBillSegment().equalsIgnoreCase("Late Payment")){
							
							//Late Payment Amount Bill Ledger Code
							int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),billingPaymentsEntity.getTypeOfService()+" Ledger");

							ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
							ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();

							ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue()) + 1);
							ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());					
							
							String serviceType = billingPaymentsEntity.getTypeOfService();							
							if(serviceType.equals("Electricity")){
								ledgerEntity.setTransactionCode("EL");
							}
							if(serviceType.equals("Gas")){
								ledgerEntity.setTransactionCode("GA");
							}
							if(serviceType.equals("Solid Waste")){
								ledgerEntity.setTransactionCode("SW");
							}
							if(serviceType.equals("Water")){
								ledgerEntity.setTransactionCode("WT");
							}
							if(serviceType.equals("Others")){
								ledgerEntity.setTransactionCode("OT");
							}
							if(serviceType.equals("CAM")){
								ledgerEntity.setTransactionCode("CAM");
							}
							if(serviceType.equals("Telephone Broadband")){
								ledgerEntity.setTransactionCode("TEL");
							}							
							ledgerEntity.setLedgerType(serviceType+" Ledger");
							ledgerEntity.setPostType("BILL");

							int currentYear = Calendar.getInstance().get(Calendar.YEAR);							
							Calendar calLast = Calendar.getInstance();
							int lastYear = calLast.get(Calendar.YEAR)-1;

							ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
							ledgerEntity.setLedgerDate(paymentSegmentsEntity.getBillMonth());
							ledgerEntity.setAmount(paymentSegmentsEntity.getAmount());
							ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() + ledgerEntity.getAmount());
							ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntity.setStatus("Approved");
							ledgerEntity.setRemarks("Bill for late payment penality amount");

							electricityLedgerService.save(ledgerEntity);
							
							//Late Payment Amount Payment Ledger Code
							int lastTransactionLedgerIdPayment = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),billingPaymentsEntity.getTypeOfService()+" Ledger");
							ElectricityLedgerEntity lastTransactionLedgerEntityPayment = electricityLedgerService.find(lastTransactionLedgerIdPayment);
							
							ElectricityLedgerEntity ledgerEntityPayment = new ElectricityLedgerEntity();
							
							ledgerEntityPayment.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
							ledgerEntityPayment.setAccountId(billingPaymentsEntity.getAccountId());
							ledgerEntityPayment.setLedgerType(serviceType+" Ledger");
							ledgerEntityPayment.setPostType("PAYMENT");
							if(serviceType.equals("Electricity")){
								ledgerEntityPayment.setTransactionCode("EL");
							}
							if(serviceType.equals("Gas")){
								ledgerEntityPayment.setTransactionCode("GA");
							}
							if(serviceType.equals("Solid Waste")){
								ledgerEntityPayment.setTransactionCode("SW");
							}
							if(serviceType.equals("Water")){
								ledgerEntityPayment.setTransactionCode("WT");
							}
							if(serviceType.equals("Others")){
								ledgerEntityPayment.setTransactionCode("OT");
							}
							if(serviceType.equals("CAM")){
								ledgerEntityPayment.setTransactionCode("CAM");
							}
							if(serviceType.equals("Telephone Broadband")){
								ledgerEntityPayment.setTransactionCode("TEL");
							}

							ledgerEntityPayment.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntityPayment.setPostReference(billingPaymentsEntity.getReceiptNo());
							ledgerEntityPayment.setLedgerDate(paymentSegmentsEntity.getBillMonth());
							ledgerEntityPayment.setAmount(-paymentSegmentsEntity.getAmount());
							ledgerEntityPayment.setBalance(lastTransactionLedgerEntityPayment.getBalance() - paymentSegmentsEntity.getAmount());
							ledgerEntityPayment.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntityPayment.setStatus("Approved");
							ledgerEntityPayment.setRemarks("Payment for late payment penality amount");
							
							electricityLedgerService.save(ledgerEntityPayment);
							
						}else{
							
							int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment()+" Ledger");
							ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
							
							ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
							
							ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
							ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());
							String segmentType = paymentSegmentsEntity.getBillSegment();
							ledgerEntity.setLedgerType(segmentType+" Ledger");
							ledgerEntity.setPostType("PAYMENT");
							
							if(segmentType.equals("Electricity")){
								ledgerEntity.setTransactionCode("EL");
							}
							if(segmentType.equals("Gas")){
								ledgerEntity.setTransactionCode("GA");
							}
							if(segmentType.equals("Solid Waste")){
								ledgerEntity.setTransactionCode("SW");
							}
							if(segmentType.equals("Water")){
								ledgerEntity.setTransactionCode("WT");
							}
							if(segmentType.equals("Others")){
								ledgerEntity.setTransactionCode("OT");
							}
							if(segmentType.equals("CAM")){
								ledgerEntity.setTransactionCode("CAM");
							}
							if(segmentType.equals("Telephone Broadband")){
								ledgerEntity.setTransactionCode("TEL");
							}
							
							int currentYear = Calendar.getInstance().get(Calendar.YEAR);
							
							Calendar calLast = Calendar.getInstance();
							int lastYear = calLast.get(Calendar.YEAR)-1;

							ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
							ledgerEntity.setLedgerDate(paymentSegmentsEntity.getBillMonth());
							ledgerEntity.setAmount(-paymentSegmentsEntity.getAmount());
							ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() - paymentSegmentsEntity.getAmount());
							ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntity.setStatus("Approved");
							ledgerEntity.setRemarks("Utility Bills Payment");
							
							electricityLedgerService.save(ledgerEntity);
							
							List<PaymentAdjustmentEntity> paymentAdjustmentList = adjustmentService.getAllAdjustmentByAccountId(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment()+" Ledger");
							
							if(!paymentAdjustmentList.isEmpty()){
								PaymentAdjustmentEntity paymentAdjustmentEntity = paymentAdjustmentList.get(0);
								paymentAdjustmentEntity.setClearedStatus("Yes");
								
								adjustmentService.update(paymentAdjustmentEntity);
							}
							/*==================================== Changed ================================================*/
							/*  if(billingPaymentsEntity.getPartPayment().equalsIgnoreCase("Yes")){
									
								}else{
									paymentService.setBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment(), operation, response);
								}	*/
							/*==============================================================================================*/
							paymentService.setBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment(), operation, response);
						}
						
					  }
			  
			}else if(billingPaymentsEntity.getPaymentType().equals("Pay Advance Bill")){
				
				List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);
				
				for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
					
					int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment()+" Ledger");
					ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
					
					ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
					
					ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
					ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());
					String segmentType = paymentSegmentsEntity.getBillSegment();
					ledgerEntity.setLedgerType(segmentType+" Ledger");
					ledgerEntity.setPostType("PAYMENT");
					
					if(segmentType.equals("Electricity")){
						ledgerEntity.setTransactionCode("EL");
					}
					if(segmentType.equals("Gas")){
						ledgerEntity.setTransactionCode("GA");
					}
					if(segmentType.equals("Solid Waste")){
						ledgerEntity.setTransactionCode("SW");
					}
					if(segmentType.equals("Water")){
						ledgerEntity.setTransactionCode("WT");
					}
					if(segmentType.equals("Others")){
						ledgerEntity.setTransactionCode("OT");
					}
					if(segmentType.equals("CAM")){
						ledgerEntity.setTransactionCode("CAM");
					}
					if(segmentType.equals("Telephone Broadband")){
						ledgerEntity.setTransactionCode("TEL");
					}
					
					int currentYear = Calendar.getInstance().get(Calendar.YEAR);
					
					Calendar calLast = Calendar.getInstance();
					int lastYear = calLast.get(Calendar.YEAR)-1;

					ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
					ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
					ledgerEntity.setLedgerDate(paymentSegmentsEntity.getBillMonth());
					ledgerEntity.setAmount(-paymentSegmentsEntity.getAmount());
					ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() - paymentSegmentsEntity.getAmount());
					ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
					ledgerEntity.setStatus("Approved");
					ledgerEntity.setRemarks("Advance Bills Payment");
					
					electricityLedgerService.save(ledgerEntity);
						
					paymentService.setAdvanceBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillReferenceNo(), operation, response);
				  }
		  
				
			} else if(billingPaymentsEntity.getPaymentType().equals("Pay Deposit")){
				
				List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);
				
				for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
					
					Deposits deposits = new Deposits();
					
					List<Deposits> uniqueAccount = depositsService.testUniqueAccount(billingPaymentsEntity.getAccountId());
					if(uniqueAccount.isEmpty()){
						
						deposits.setAccountObj(accountService.find(billingPaymentsEntity.getAccountId()));
						deposits.setTotalAmount(paymentSegmentsEntity.getAmount());
						
						depositsService.save(deposits);
						
					} else{
						deposits=uniqueAccount.get(0);
						deposits.setTotalAmount(deposits.getTotalAmount()+paymentSegmentsEntity.getAmount());
						deposits.setBalance(deposits.getTotalAmount()-deposits.getRefundAmount());
						depositsService.update(deposits);
					}
					
					List<PaymentSegmentCalcLines> paymentSegmentCalcLinesList = paymentService.getPaymentSegmentCalcLinesListForDepositType(paymentSegmentsEntity.getPaymentSegmentId());
					for (PaymentSegmentCalcLines paymentSegmentCalcLine : paymentSegmentCalcLinesList) {
						DepositsLineItems depositsLineItems = new DepositsLineItems();
						
						depositsLineItems.setAmount(paymentSegmentCalcLine.getAmount());
						depositsLineItems.setTypeOfServiceDeposit(paymentSegmentsEntity.getBillSegment());
						depositsLineItems.setTransactionCode(paymentSegmentCalcLine.getTransactionCode());
						depositsLineItems.setCategory("Deposit");
						depositsLineItems.setPaymentMode(billingPaymentsEntity.getPaymentMode());
						depositsLineItems.setCollectionType("Counter");
						depositsLineItems.setInstrumentNo(billingPaymentsEntity.getInstrumentNo());
						depositsLineItems.setInstrumentDate(billingPaymentsEntity.getInstrumentDate());
						depositsLineItems.setBankName(billingPaymentsEntity.getBankName());
						depositsLineItems.setDeposits(deposits);
						
						depositsLineItemsService.save(depositsLineItems);
						
					}
						
					paymentService.setDepositBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillReferenceNo(), operation, response);
				  }
			}
			paymentService.setCollectionPaymentStatus(paymentCollectionId, operation, response);
			
			if(billingPaymentsEntity.getExcessAmount()>0){
				excessAmountSavingCode(billingPaymentsEntity);
			}
			
		} else if(billingPaymentsEntity.getStatus().equals("Cancelled")) {
			paymentService.cancelPaymentsNotPosted(response);
		}
		
	}
	
	public void excessAmountSavingCode(BillingPaymentsEntity billingPaymentsEntity){
		
		int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(), billingPaymentsEntity.getTypeOfService()+" Ledger");
		ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
		
		
        ElectricityLedgerEntity ledgerEntity1 = new ElectricityLedgerEntity();
		
        ledgerEntity1.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
        ledgerEntity1.setAccountId(billingPaymentsEntity.getAccountId());
        ledgerEntity1.setPostType("TOTALAMOUNT");
		
		String serviceType1 = billingPaymentsEntity.getTypeOfService();							
		if(serviceType1.equals("Electricity")){
			ledgerEntity1.setTransactionCode("EL");
		}
		if(serviceType1.equals("Gas")){
			ledgerEntity1.setTransactionCode("GA");
		}
		if(serviceType1.equals("Solid Waste")){
			ledgerEntity1.setTransactionCode("SW");
		}
		if(serviceType1.equals("Water")){
			ledgerEntity1.setTransactionCode("WT");
		}
		if(serviceType1.equals("Others")){
			ledgerEntity1.setTransactionCode("OT");
		}
		if(serviceType1.equals("CAM")){
			ledgerEntity1.setTransactionCode("CAM");
		}
		if(serviceType1.equals("Telephone Broadband")){
			ledgerEntity1.setTransactionCode("TEL");
		}							
		ledgerEntity1.setLedgerType(serviceType1+" Ledger");
		
		int currentYear1 = Calendar.getInstance().get(Calendar.YEAR);		
		Calendar calLast1 = Calendar.getInstance();
		int lastYear1 = calLast1.get(Calendar.YEAR)-1;

		ledgerEntity1.setLedgerPeriod(lastYear1+"-"+currentYear1);
		ledgerEntity1.setPostReference(billingPaymentsEntity.getReceiptNo());
		ledgerEntity1.setLedgerDate(new java.sql.Date(new Date().getTime()));
		ledgerEntity1.setAmount(billingPaymentsEntity.getPaymentAmount());
		ledgerEntity1.setBalance(0);
		ledgerEntity1.setPostedBillDate(new Timestamp(new Date().getTime()));
		ledgerEntity1.setStatus("Approved");
		ledgerEntity1.setRemarks("Total Paid Amount");
		
		electricityLedgerService.save(ledgerEntity1);
		
		
		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
		
		ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
		ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());
		ledgerEntity.setPostType("PAYMENT");
		
		String serviceType = billingPaymentsEntity.getTypeOfService();							
		if(serviceType.equals("Electricity")){
			ledgerEntity.setTransactionCode("EL");
		}
		if(serviceType.equals("Gas")){
			ledgerEntity.setTransactionCode("GA");
		}
		if(serviceType.equals("Solid Waste")){
			ledgerEntity.setTransactionCode("SW");
		}
		if(serviceType.equals("Water")){
			ledgerEntity.setTransactionCode("WT");
		}
		if(serviceType.equals("Others")){
			ledgerEntity.setTransactionCode("OT");
		}
		if(serviceType.equals("CAM")){
			ledgerEntity.setTransactionCode("CAM");
		}
		if(serviceType.equals("Telephone Broadband")){
			ledgerEntity.setTransactionCode("TEL");
		}							
		ledgerEntity.setLedgerType(serviceType+" Ledger");
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);		
		Calendar calLast = Calendar.getInstance();
		int lastYear = calLast.get(Calendar.YEAR)-1;

		ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
		ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
		ledgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
		ledgerEntity.setAmount(-billingPaymentsEntity.getExcessAmount());
		ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() - billingPaymentsEntity.getExcessAmount());
		ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
		ledgerEntity.setStatus("Approved");
		ledgerEntity.setRemarks("Excess Amount Payment");
		
		electricityLedgerService.save(ledgerEntity);
		
		
        
	}
	
	@RequestMapping(value = "/billingPayments/paymentStatusUpdate/{paymentCollectionId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void ledgerEndDateUpdate(@PathVariable int paymentCollectionId, HttpServletResponse response)
	{		
		paymentService.paymentStatusUpdate(paymentCollectionId, response);
	}
	
	// ****************************** Billing payment collection Filters Data methods ********************************/

   @RequestMapping(value = "/billingPayments/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getPaymentsContentsForFilter(@PathVariable String feild) {
		logger.info("In  billing payment collection use case filters Method");
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("BillingPaymentsEntity",attributeList, null));
				
		return set;
	}
   
   @RequestMapping(value = "/billingPayments/accountNumberAutocomplete", method = RequestMethod.GET)
	public @ResponseBody List<?> accountNumberAutocomplete() {
		return paymentService.getAllAccuntNumbers();
	}
   
   @RequestMapping(value = "/billingPayments/getConsolidatedAmount", method = RequestMethod.GET)
	public @ResponseBody List<?> getConsolidatedAmount() {
		return paymentService.getConsolidatedAmount();
	}
   
   @RequestMapping(value = "/billingPayments/getConsolidatedAmountOnAccount/{serviceType}/{accountId}", method = {RequestMethod.GET,RequestMethod.POST})
  	public @ResponseBody List<?> getConsolidatedAmountOnAccount(@PathVariable String serviceType,@PathVariable int accountId) {
  		return paymentService.getConsolidatedAmountBasedOnServiceType(serviceType,accountId);
  	}
  
   
   // ****************************** Payment Segments Read,Create,Delete methods ********************************//

  	@RequestMapping(value = "/paymentSegments/paymentSegmentRead/{paymentCollectionId}", method = {RequestMethod.GET, RequestMethod.POST })
  	public @ResponseBody List<PaymentSegmentsEntity> readPaymentSegmets(@PathVariable int paymentCollectionId) {
  		logger.info("In read payment segment method");
  		List<PaymentSegmentsEntity> segmentsEntities = paymentSegmentService.findAllById(paymentCollectionId);
  		return segmentsEntities;
  	}
  	
  	@RequestMapping(value = "/paymentSegments/paymentSegmentUpdate/{paymentCollectionId}", method = RequestMethod.GET)
  	public @ResponseBody Object editPaymentSegmets(@ModelAttribute("segmentsEntity") PaymentSegmentsEntity segmentsEntity,BindingResult bindingResult,@PathVariable int paymentCollectionId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

  		logger.info("In edit payment segment method");
  		  		
  		validator.validate(segmentsEntity, bindingResult);
  		paymentSegmentService.update(segmentsEntity);
  		
  		return segmentsEntity;
  	}

  	@RequestMapping(value = "/paymentSegments/paymentSegmentDestroy", method = RequestMethod.GET)
  	public @ResponseBody Object deletePaymentSegmets(@ModelAttribute("segmentsEntity") PaymentSegmentsEntity segmentsEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
  		logger.info("In delete payment segment method");
  		JsonResponse errorResponse = new JsonResponse();
  		
  		try {
  			paymentSegmentService.delete(segmentsEntity.getPaymentSegmentId());
  		} catch (Exception e) {
  			errorResponse.setStatus("CHILD");
  			return errorResponse;
  		}
  		ss.setComplete();
  		return segmentsEntity;
  	}
  	
 // ****************************** Payment Segment collection Filters Data methods ********************************/

    @RequestMapping(value = "/paymentSegments/filter/{feild}", method = RequestMethod.GET)
 	public @ResponseBody Set<?> getPaymentSegmentContentsForFilter(@PathVariable String feild) {
 		logger.info("In  payment segment use case filters Method");
 		List<String> attributeList = new ArrayList<String>();
 		attributeList.add(feild);
 		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("PaymentSegmentsEntity",attributeList, null));
 				
 		return set;
 	}
    
 // ****************************** Payment Segment Calculation Lines Read,Create,Delete methods ********************************//

   	@RequestMapping(value = "/paymentSegments/paymentSegmentCalcLinesRead/{paymentCollectionId}", method = {RequestMethod.GET, RequestMethod.POST })
   	public @ResponseBody List<PaymentSegmentCalcLines> readPaymentSegmetsCalcLines(@PathVariable int paymentCollectionId) {
   		logger.info("In read payment segment method");
   		List<PaymentSegmentCalcLines> segmentCalcLines = paymentSegmentCalcLinesService.findAllById(paymentCollectionId);
   		return segmentCalcLines;
   	}
   	
   	// ****************************** Payment Adjustment Read,Create,Delete methods ********************************//
   
   	@RequestMapping(value = "/paymentAdjustments/paymentAdjustmentRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<PaymentAdjustmentEntity> readAdjustments() {
		logger.info("In read adjustments method");
		List<PaymentAdjustmentEntity> adjustmentEntities = adjustmentService.findAll();
		return adjustmentEntities;
	}

	@RequestMapping(value = "/paymentAdjustments/paymentAdjustmentCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveAdjustmentsDetails(@ModelAttribute("adjustmentEntity") PaymentAdjustmentEntity adjustmentEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws Exception {

		logger.info("In save adjustments details method");
		
		adjustmentEntity.setAdjustmentNo(genrateAdjustmentNumber());
		adjustmentEntity.setAccountId(adjustmentEntity.getAccountId());
		adjustmentEntity.setPostedToGl("No");
		adjustmentEntity.setStatus("Created");
		adjustmentEntity.setRemarks(req.getParameter("remarks")); 
		adjustmentEntity.setJvDate(new Timestamp(new Date().getTime()));
		adjustmentEntity.setAdjustmentDate(dateTimeCalender.getDateToStore(req.getParameter("adjustmentDate")));
		adjustmentEntity.setClearedStatus("No");
		adjustmentEntity.setTallystatus("Not Posted");
		
		validator.validate(adjustmentEntity, bindingResult);
		adjustmentService.save(adjustmentEntity);
		sessionStatus.setComplete();
		return adjustmentEntity;
		
	}
	
	@RequestMapping(value = "/paymentAdjustments/paymentAdjustmentUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editAdjustmentDetails(@ModelAttribute("adjustmentEntity") PaymentAdjustmentEntity adjustmentEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In edit adjustments details method");
		adjustmentEntity.setAccountId(adjustmentEntity.getAccountId());
		PaymentAdjustmentEntity temp=adjustmentService.find(adjustmentEntity.getAdjustmentId());			
		adjustmentEntity.setJvDate(temp.getJvDate());
		adjustmentEntity.setPostedGlDate(temp.getPostedGlDate());
		adjustmentEntity.setAdjustmentDate(dateTimeCalender.getDateToStore(req.getParameter("adjustmentDate")));
		
		validator.validate(adjustmentEntity, bindingResult);
		adjustmentService.update(adjustmentEntity);
		
		return adjustmentEntity;
	}

	@RequestMapping(value = "/paymentAdjustments/paymentAdjustmentDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteAdjustmentsDetsails(@ModelAttribute("adjustmentEntity") PaymentAdjustmentEntity adjustmentEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete adjustment details method");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			adjustmentService.delete(adjustmentEntity.getAdjustmentId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return adjustmentEntity;
	}
	
	@RequestMapping(value = "/paymentAdjustments/setAdjustmentStatus/{adjustmentId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void setAdjustmentStatus(@PathVariable int adjustmentId,@PathVariable String operation, HttpServletResponse response) {
		
		logger.info("In adjustment status change method");
		if (operation.equalsIgnoreCase("activate")){
			adjustmentService.setAdjustmentStatus(adjustmentId, operation, response);
		} else{
			synchronized (this) {
				postAdjustmentCode(adjustmentId,operation,response);
			}
		}
	}
	
	public void postAdjustmentCode(int adjustmentId,String operation,HttpServletResponse response){
		
		PaymentAdjustmentEntity adjustmentEntity = adjustmentService.find(adjustmentId);
		
		int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(adjustmentEntity.getAccountId(),adjustmentEntity.getAdjustmentLedger());
		ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
		
		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
		ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(adjustmentEntity.getAccountId()).intValue())+1);
		ledgerEntity.setAccountId(adjustmentEntity.getAccountId());
		String segmentType = adjustmentEntity.getAdjustmentLedger();
		ledgerEntity.setLedgerType(segmentType);
		ledgerEntity.setPostType("ADJUSTMENT");
		
		if(segmentType.equals("Electricity Ledger")){
			ledgerEntity.setTransactionCode("EL");
		}
		if(segmentType.equals("Gas Ledger")){
			ledgerEntity.setTransactionCode("GA");
		}
		if(segmentType.equals("CAM Ledger")){
			ledgerEntity.setTransactionCode("CAM");
		}
		if(segmentType.equals("Solid Waste Ledger")){
			ledgerEntity.setTransactionCode("SW");
		}
		if(segmentType.equals("Water Ledger")){
			ledgerEntity.setTransactionCode("WT");
		}
		if(segmentType.equals("Common Ledger")){
			ledgerEntity.setTransactionCode("OT");
		}
		if(segmentType.equals("Telephone Broadband Ledger")){
			ledgerEntity.setTransactionCode("TEL");
		}
			
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			
		Calendar calLast = Calendar.getInstance();
		int lastYear = calLast.get(Calendar.YEAR)-1;
		ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
		ledgerEntity.setPostReference(adjustmentEntity.getAdjustmentNo());
		ledgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
		ledgerEntity.setLedgerDate(adjustmentEntity.getAdjustmentDate());
		ledgerEntity.setAmount(adjustmentEntity.getAdjustmentAmount());
		
		if(adjustmentEntity.getAdjustmentType().equalsIgnoreCase("Miscellaneous")){
			ledgerEntity.setBalance((lastTransactionLedgerEntity.getBalance()) - 0);//Miscellaneous Amount Not Added In Ledger Balnace
		}else{
			ledgerEntity.setBalance((lastTransactionLedgerEntity.getBalance()) - (adjustmentEntity.getAdjustmentAmount()));
		}
		
		
		ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
		ledgerEntity.setStatus("Approved");
		ledgerEntity.setRemarks(adjustmentEntity.getAdjustmentType()+" - Adjusting utility bills amount");
		
		electricityLedgerService.save(ledgerEntity);
		
		adjustmentService.setAdjustmentStatus(adjustmentId, operation, response);
		
	}
	
	
	// ****************************** Adjustments Filters Data methods ********************************/

	   @RequestMapping(value = "/paymentAdjustments/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> getAdjustmentsContentsForFilter(@PathVariable String feild) {
			logger.info("In  adjustment use case filters Method");
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("PaymentAdjustmentEntity",attributeList, null));
					
			return set;
		}
	   
	   // ****************************** Adjustment Calculation Line Read,Create,Delete methods ********************************//

	  	@RequestMapping(value = "/paymentAdjustments/adjustmentCalcLineRead/{adjustmentId}", method = {RequestMethod.GET, RequestMethod.POST })
	  	public @ResponseBody List<AdjustmentCalcLinesEntity> readAdjustmentCalcLine(@PathVariable int adjustmentId) {
	  		logger.info("In read adjustment calc line method");
	  		List<AdjustmentCalcLinesEntity> calcLinesEntities = adjustmentCalcLineService.findAllById(adjustmentId);
	  		return calcLinesEntities;
	  	}
	  	
	  	@RequestMapping(value = "/paymentAdjustments/adjustmentCalcLineCreate/{adjustmentId}", method = RequestMethod.GET)
	  	public @ResponseBody Object saveAdjustmentCalcLine(@ModelAttribute("adjustmentCalcLinesEntity") AdjustmentCalcLinesEntity adjustmentCalcLinesEntity,BindingResult bindingResult,@PathVariable int adjustmentId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

	  		logger.info("In save adjustment calc line method");
	  		PaymentAdjustmentEntity adjustmentEntity = adjustmentService.find(adjustmentId);
	  		adjustmentCalcLinesEntity.setPaymentAdjustmentEntity(adjustmentEntity);
	  		
	  		validator.validate(adjustmentCalcLinesEntity, bindingResult);
	  		adjustmentEntity.setAdjustmentAmount(adjustmentEntity.getAdjustmentAmount()+adjustmentCalcLinesEntity.getAmount());
	  		adjustmentService.update(adjustmentEntity);
	  		adjustmentCalcLineService.save(adjustmentCalcLinesEntity);
	  		
	  		return adjustmentCalcLinesEntity;
	  	}
	  	
	  	@RequestMapping(value = "/paymentAdjustments/adjustmentCalcLineUpdate/{adjustmentId}", method = RequestMethod.GET)
	  	public @ResponseBody Object editAdjustmentCalcLine(@ModelAttribute("adjustmentCalcLinesEntity") AdjustmentCalcLinesEntity adjustmentCalcLinesEntity,BindingResult bindingResult,@PathVariable int adjustmentId, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

	  		logger.info("In edit adjustment calc line method");
	  		PaymentAdjustmentEntity paymentAdjustmentEntity = adjustmentService.find(adjustmentId);
	  		adjustmentCalcLinesEntity.setPaymentAdjustmentEntity(paymentAdjustmentEntity);  		
	  		validator.validate(adjustmentCalcLinesEntity, bindingResult);
	  		adjustmentCalcLineService.update(adjustmentCalcLinesEntity);
	  		
	  		paymentAdjustmentEntity.setAdjustmentAmount(adjustmentCalcLineService.getTotalAmountBasedOnAdjustmentId(adjustmentId));
	  		
	  		adjustmentService.update(paymentAdjustmentEntity);
	  		
	  		return adjustmentCalcLinesEntity;
	  	}

	  	@RequestMapping(value = "/paymentAdjustments/adjustmentCalcLineDestroy", method = RequestMethod.GET)
	  	public @ResponseBody Object deleteAdjustmentCalcLine(@ModelAttribute("adjustmentCalcLinesEntity") AdjustmentCalcLinesEntity adjustmentCalcLinesEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
	  		logger.info("In delete adjustment calc line method");
	  		JsonResponse errorResponse = new JsonResponse();
	  		
	  		try {
	  			PaymentAdjustmentEntity paymentAdjustmentEntity = adjustmentService.find(adjustmentCalcLinesEntity.getAdjustmentId());
	  			paymentAdjustmentEntity.setAdjustmentAmount(paymentAdjustmentEntity.getAdjustmentAmount()-adjustmentCalcLinesEntity.getAmount());
	  			
	  			adjustmentCalcLineService.delete(adjustmentCalcLinesEntity.getCalcLineId());
	  			
	  			adjustmentService.update(paymentAdjustmentEntity);
	  			
	  		} catch (Exception e) {
	  			errorResponse.setStatus("CHILD");
	  			return errorResponse;
	  		}
	  		ss.setComplete();
	  		return adjustmentCalcLinesEntity;
	  	}
	  	
	 // ****************************** Adjustment Calculation Line Filters Data methods ********************************/

	    @RequestMapping(value = "/paymentAdjustments/adjustmentCalcLine/filter/{feild}", method = RequestMethod.GET)
	 	public @ResponseBody Set<?> getAdjustmentCalcLineContentsForFilter(@PathVariable String feild) {
	 		logger.info("In adjustment calc line use case filters Method");
	 		List<String> attributeList = new ArrayList<String>();
	 		attributeList.add(feild);
	 		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("AdjustmentCalcLinesEntity",attributeList, null));
	 				
	 		return set;
	 	}
	    
	   @RequestMapping(value = "/paymentAdjustments/getTransactionCodes/{typeOfService}/{adjustmentId}", method = RequestMethod.GET)
		public @ResponseBody List<?> getTransactionCodes(@PathVariable String typeOfService,@PathVariable int adjustmentId) 
		{
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
			List<?> transactionCodeList = adjustmentCalcLineService.getTransactionCodes(typeOfService.trim(),adjustmentId);
			Map<String, Object> codeMap = null;
			for (Iterator<?> iterator = transactionCodeList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				codeMap = new HashMap<String, Object>();
				codeMap.put("transactionCode", (String)values[0]);
				codeMap.put("transactionName", (String)values[1]);
	 	       	result.add(codeMap);
	 	     }
	        return result;
		}
	   
	   @RequestMapping(value = "/billingPayments/approveCollection", method = { RequestMethod.GET, RequestMethod.POST })
		public void approveCollection(HttpServletResponse response)	{		
			paymentService.setStatusApproved(response);
		}
	   
	   @RequestMapping(value = "/billingpayments/getListBasedOnStatus/{className}/{attribute}/{attribute1}", method = { RequestMethod.GET,	RequestMethod.POST })
		public @ResponseBody Object getListBasedOnStatus(@PathVariable String className,@PathVariable String attribute,@PathVariable String attribute1) throws Exception {
			logger.info("In billing parameter getUniqueParamName method");
			return paymentService.getListBasedOnStatus(className,attribute,attribute1);
		}
	   
	   
	   
	   @RequestMapping(value = "/billingPayments/postCollectionLedger", method = { RequestMethod.GET, RequestMethod.POST })
	   public void postCollectionLedger(HttpServletResponse response){		
		   synchronized (this) {
			   postAllPaymentsCode(response);
		   }
		}
	   
	   public void postAllPaymentsCode(HttpServletResponse response){
		   
		   List<BillingPaymentsEntity> paymentsEntities = paymentService.getPaymentCollectionID();
		   for (BillingPaymentsEntity billingPaymentsEntity : paymentsEntities) {
			   
			   if(billingPaymentsEntity.getPaymentType().equals("Pay Bill")){
					
					List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(billingPaymentsEntity.getPaymentCollectionId());
					
					for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
						
						if(paymentSegmentsEntity.getBillSegment().equalsIgnoreCase("Late Payment")){
							
							//Late Payment Amount Bill Ledger Code
							int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),billingPaymentsEntity.getTypeOfService()+" Ledger");

							ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
							ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();

							ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue()) + 1);
							ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());					
							
							String serviceType = billingPaymentsEntity.getTypeOfService();							
							if(serviceType.equals("Electricity")){
								ledgerEntity.setTransactionCode("EL");
							}
							if(serviceType.equals("Gas")){
								ledgerEntity.setTransactionCode("GA");
							}
							if(serviceType.equals("Solid Waste")){
								ledgerEntity.setTransactionCode("SW");
							}
							if(serviceType.equals("Water")){
								ledgerEntity.setTransactionCode("WT");
							}
							if(serviceType.equals("Others")){
								ledgerEntity.setTransactionCode("OT");
							}
							if(serviceType.equals("CAM")){
								ledgerEntity.setTransactionCode("CAM");
							}
							if(serviceType.equals("Telephone Broadband")){
								ledgerEntity.setTransactionCode("TEL");
							}							
							ledgerEntity.setLedgerType(serviceType+" Ledger");
							ledgerEntity.setPostType("BILL");

							int currentYear = Calendar.getInstance().get(Calendar.YEAR);							
							Calendar calLast = Calendar.getInstance();
							int lastYear = calLast.get(Calendar.YEAR)-1;

							ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
							ledgerEntity.setLedgerDate(paymentSegmentsEntity.getBillMonth());
							ledgerEntity.setAmount(paymentSegmentsEntity.getAmount());
							ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() + ledgerEntity.getAmount());
							ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntity.setStatus("Approved");
							ledgerEntity.setRemarks("Bill for late payment penality amount");

							electricityLedgerService.save(ledgerEntity);
							
							//Late Payment Amount Payment Ledger Code
							int lastTransactionLedgerIdPayment = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),billingPaymentsEntity.getTypeOfService()+" Ledger");
							ElectricityLedgerEntity lastTransactionLedgerEntityPayment = electricityLedgerService.find(lastTransactionLedgerIdPayment);
							
							ElectricityLedgerEntity ledgerEntityPayment = new ElectricityLedgerEntity();
							
							ledgerEntityPayment.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
							ledgerEntityPayment.setAccountId(billingPaymentsEntity.getAccountId());
							ledgerEntityPayment.setLedgerType(serviceType+" Ledger");
							ledgerEntityPayment.setPostType("PAYMENT");
							if(serviceType.equals("Electricity")){
								ledgerEntityPayment.setTransactionCode("EL");
							}
							if(serviceType.equals("Gas")){
								ledgerEntityPayment.setTransactionCode("GA");
							}
							if(serviceType.equals("Solid Waste")){
								ledgerEntityPayment.setTransactionCode("SW");
							}
							if(serviceType.equals("Water")){
								ledgerEntityPayment.setTransactionCode("WT");
							}
							if(serviceType.equals("Others")){
								ledgerEntityPayment.setTransactionCode("OT");
							}
							if(serviceType.equals("CAM")){
								ledgerEntityPayment.setTransactionCode("CAM");
							}
							if(serviceType.equals("Telephone Broadband")){
								ledgerEntityPayment.setTransactionCode("TEL");
							}

							ledgerEntityPayment.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntityPayment.setPostReference(billingPaymentsEntity.getReceiptNo());
							ledgerEntityPayment.setLedgerDate(paymentSegmentsEntity.getBillMonth());
							ledgerEntityPayment.setAmount(-paymentSegmentsEntity.getAmount());
							ledgerEntityPayment.setBalance(lastTransactionLedgerEntityPayment.getBalance() - paymentSegmentsEntity.getAmount());
							ledgerEntityPayment.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntityPayment.setStatus("Approved");
							ledgerEntityPayment.setRemarks("Payment for late payment penality amount");
							
							electricityLedgerService.save(ledgerEntityPayment);
							
						}else{
							
							int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment()+" Ledger");
							ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
							
							ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
							
							ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
							ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());
							String segmentType = paymentSegmentsEntity.getBillSegment();
							ledgerEntity.setLedgerType(segmentType+" Ledger");
							ledgerEntity.setPostType("PAYMENT");
							
							if(segmentType.equals("Electricity")){
								ledgerEntity.setTransactionCode("EL");
							}
							if(segmentType.equals("Gas")){
								ledgerEntity.setTransactionCode("GA");
							}
							if(segmentType.equals("Solid Waste")){
								ledgerEntity.setTransactionCode("SW");
							}
							if(segmentType.equals("Water")){
								ledgerEntity.setTransactionCode("WT");
							}
							if(segmentType.equals("Others")){
								ledgerEntity.setTransactionCode("OT");
							}
							if(segmentType.equals("CAM")){
								ledgerEntity.setTransactionCode("CAM");
							}
							if(segmentType.equals("Telephone Broadband")){
								ledgerEntity.setTransactionCode("TEL");
							}
							
							int currentYear = Calendar.getInstance().get(Calendar.YEAR);
							
							Calendar calLast = Calendar.getInstance();
							int lastYear = calLast.get(Calendar.YEAR)-1;

							ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
							ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
							ledgerEntity.setLedgerDate(paymentSegmentsEntity.getBillMonth());
							ledgerEntity.setAmount(-paymentSegmentsEntity.getAmount());
							ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() - paymentSegmentsEntity.getAmount());
							ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
							ledgerEntity.setStatus("Approved");
							ledgerEntity.setRemarks("Utility Bills Payment");
							
							electricityLedgerService.save(ledgerEntity);
							
							List<PaymentAdjustmentEntity> paymentAdjustmentList = adjustmentService.getAllAdjustmentByAccountId(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment()+" Ledger");
							
							if(!paymentAdjustmentList.isEmpty()){
								PaymentAdjustmentEntity paymentAdjustmentEntity = paymentAdjustmentList.get(0);
								paymentAdjustmentEntity.setClearedStatus("Yes");
								
								adjustmentService.update(paymentAdjustmentEntity);
							}
							
							paymentService.setBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment(), "", response);
						}
						
					  }
			  
			}else if(billingPaymentsEntity.getPaymentType().equals("Pay Advance Bill")){
				
				List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(billingPaymentsEntity.getPaymentCollectionId());
				
				for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
					
					int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillSegment()+" Ledger");
					ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
					
					ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
					
					ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(billingPaymentsEntity.getAccountId()).intValue())+1);
					ledgerEntity.setAccountId(billingPaymentsEntity.getAccountId());
					String segmentType = paymentSegmentsEntity.getBillSegment();
					ledgerEntity.setLedgerType(segmentType+" Ledger");
					ledgerEntity.setPostType("PAYMENT");
					
					if(segmentType.equals("Electricity")){
						ledgerEntity.setTransactionCode("EL");
					}
					if(segmentType.equals("Gas")){
						ledgerEntity.setTransactionCode("GA");
					}
					if(segmentType.equals("Solid Waste")){
						ledgerEntity.setTransactionCode("SW");
					}
					if(segmentType.equals("Water")){
						ledgerEntity.setTransactionCode("WT");
					}
					if(segmentType.equals("Others")){
						ledgerEntity.setTransactionCode("OT");
					}
					if(segmentType.equals("CAM")){
						ledgerEntity.setTransactionCode("CAM");
					}
					if(segmentType.equals("Telephone Broadband")){
						ledgerEntity.setTransactionCode("TEL");
					}
					
					int currentYear = Calendar.getInstance().get(Calendar.YEAR);
					
					Calendar calLast = Calendar.getInstance();
					int lastYear = calLast.get(Calendar.YEAR)-1;

					ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
					ledgerEntity.setPostReference(billingPaymentsEntity.getReceiptNo());
					ledgerEntity.setLedgerDate(paymentSegmentsEntity.getBillMonth());
					ledgerEntity.setAmount(-paymentSegmentsEntity.getAmount());
					ledgerEntity.setBalance(lastTransactionLedgerEntity.getBalance() - paymentSegmentsEntity.getAmount());
					ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
					ledgerEntity.setStatus("Approved");
					ledgerEntity.setRemarks("Advance Bills Payment");
					
					electricityLedgerService.save(ledgerEntity);
						
					paymentService.setAdvanceBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillReferenceNo(), "", response);
				  }
		  
				
			} else if(billingPaymentsEntity.getPaymentType().equals("Pay Deposit")){
				
				List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(billingPaymentsEntity.getPaymentCollectionId());
				
				for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
					
					Deposits deposits = new Deposits();
					
					List<Deposits> uniqueAccount = depositsService.testUniqueAccount(billingPaymentsEntity.getAccountId());
					if(uniqueAccount.isEmpty()){
						
						deposits.setAccountObj(accountService.find(billingPaymentsEntity.getAccountId()));
						deposits.setTotalAmount(paymentSegmentsEntity.getAmount());
						
						depositsService.save(deposits);
						
					} else{
						deposits=uniqueAccount.get(0);
						deposits.setTotalAmount(deposits.getTotalAmount()+paymentSegmentsEntity.getAmount());
						deposits.setBalance(deposits.getTotalAmount()-deposits.getRefundAmount());
						depositsService.update(deposits);
					}
					
					List<PaymentSegmentCalcLines> paymentSegmentCalcLinesList = paymentService.getPaymentSegmentCalcLinesListForDepositType(paymentSegmentsEntity.getPaymentSegmentId());
					for (PaymentSegmentCalcLines paymentSegmentCalcLine : paymentSegmentCalcLinesList) {
						DepositsLineItems depositsLineItems = new DepositsLineItems();
						
						depositsLineItems.setAmount(paymentSegmentCalcLine.getAmount());
						depositsLineItems.setTypeOfServiceDeposit(paymentSegmentsEntity.getBillSegment());
						depositsLineItems.setTransactionCode(paymentSegmentCalcLine.getTransactionCode());
						depositsLineItems.setCategory("Deposit");
						depositsLineItems.setPaymentMode(billingPaymentsEntity.getPaymentMode());
						depositsLineItems.setCollectionType("Counter");
						depositsLineItems.setInstrumentNo(billingPaymentsEntity.getInstrumentNo());
						depositsLineItems.setInstrumentDate(billingPaymentsEntity.getInstrumentDate());
						depositsLineItems.setBankName(billingPaymentsEntity.getBankName());
						depositsLineItems.setDeposits(deposits);
						
						depositsLineItemsService.save(depositsLineItems);
						
					}
						
					paymentService.setDepositBillEntityStatus(billingPaymentsEntity.getAccountId(),paymentSegmentsEntity.getBillReferenceNo(), "", response);
				  }
			}
			   paymentService.setStatusPostedLedger(billingPaymentsEntity.getPaymentCollectionId(),response);
			
			if(billingPaymentsEntity.getExcessAmount()>0){
				excessAmountSavingCode(billingPaymentsEntity);
			}
			   
		   }
		   
	   }
	   
	   @RequestMapping(value = "/paymentAdjustments/approveAdjustment", method = { RequestMethod.GET, RequestMethod.POST })
		public void approveAdjustment(HttpServletResponse response)	{		
			adjustmentService.setStatusApproved(response);
		}
	   
	   @RequestMapping(value = "/paymentAdjustments/postAdjustmentLedger", method = { RequestMethod.GET, RequestMethod.POST })
		public void postAdjustmentLedger(HttpServletResponse response){		
		   synchronized (this) {
			   postAllAdjustmentsCode(response);
		   }		   
		}
	   
	   public void postAllAdjustmentsCode(HttpServletResponse response){
		   
		   List<PaymentAdjustmentEntity> adjustmentEntities = adjustmentService.getAdjustmentId();
		   for (PaymentAdjustmentEntity adjustmentEntity : adjustmentEntities) {
			   
			    int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(adjustmentEntity.getAccountId(),adjustmentEntity.getAdjustmentLedger());
				ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);
				
				ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
				ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(adjustmentEntity.getAccountId()).intValue())+1);
				ledgerEntity.setAccountId(adjustmentEntity.getAccountId());
				String segmentType = adjustmentEntity.getAdjustmentLedger();
				ledgerEntity.setLedgerType(segmentType);
				ledgerEntity.setPostType("ADJUSTMENT");
				
				if(segmentType.equals("Electricity Ledger")){
					ledgerEntity.setTransactionCode("EL");
				}
				if(segmentType.equals("Gas Ledger")){
					ledgerEntity.setTransactionCode("GA");
				}
				if(segmentType.equals("CAM Ledger")){
					ledgerEntity.setTransactionCode("CAM");
				}
				if(segmentType.equals("Solid Waste Ledger")){
					ledgerEntity.setTransactionCode("SW");
				}
				if(segmentType.equals("Water Ledger")){
					ledgerEntity.setTransactionCode("WT");
				}
				if(segmentType.equals("Common Ledger")){
					ledgerEntity.setTransactionCode("OT");
				}
				if(segmentType.equals("Telephone Broadband Ledger")){
					ledgerEntity.setTransactionCode("TEL");
				}
					
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
					
				Calendar calLast = Calendar.getInstance();
				int lastYear = calLast.get(Calendar.YEAR)-1;
				ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
				ledgerEntity.setPostReference(adjustmentEntity.getAdjustmentNo());
				ledgerEntity.setLedgerDate(new java.sql.Date(new Date().getTime()));
				ledgerEntity.setLedgerDate(adjustmentEntity.getAdjustmentDate());
				ledgerEntity.setAmount(adjustmentEntity.getAdjustmentAmount());
				ledgerEntity.setBalance((lastTransactionLedgerEntity.getBalance()) - (adjustmentEntity.getAdjustmentAmount()));
				ledgerEntity.setPostedBillDate(new Timestamp(new Date().getTime()));
				ledgerEntity.setStatus("Approved");
				ledgerEntity.setRemarks(adjustmentEntity.getAdjustmentType()+" - Adjusting utility bills amount");
				
				electricityLedgerService.save(ledgerEntity);
				
				adjustmentService.setStatusPostedLedger(adjustmentEntity.getAdjustmentId(),response);
			}
		   
	   }
	   
	   @RequestMapping(value = "/billingPayments/getListOfServicesAmounts/{accountId}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<?> getListOfServicesAmounts(@PathVariable int accountId)	{		
			return paymentService.getListOfServicesAmounts(accountId);
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
	   
	   @RequestMapping(value = "/paymentAdjustments/getAllPaidAccountNumbers", method = RequestMethod.GET)
		public @ResponseBody List<?> getAllPaidAccountNumbers() {
			return adjustmentService.getAllPaidAccountNumbers();
		}
	   
	   @RequestMapping(value = "/billingPayments/accountNumberBasedOnPayDeposit", method = RequestMethod.GET)
		public @ResponseBody List<?> accountNumberBasedOnPayDeposit() {
			return paymentService.accountNumberBasedOnPayDeposit();
		}
	   
	   @RequestMapping(value = "/billingPayments/getConsolidatedAmountBasedOnDeposit", method = RequestMethod.GET)
		public @ResponseBody List<?> getConsolidatedAmountBasedOnDeposit() {
			return paymentService.getConsolidatedAmountBasedOnDeposit();
		}
	   
	   @RequestMapping(value = "/billingPayments/accountNumberBasedOnPayAdvanceBill", method = RequestMethod.GET)
		public @ResponseBody List<?> accountNumberBasedOnPayAdvanceBill() {
			return paymentService.accountNumberBasedOnPayAdvanceBill();
		}
	   
	   @RequestMapping(value = "/billingPayments/getConsolidatedAmountBasedOnPayAdvance", method = RequestMethod.GET)
		public @ResponseBody List<?> getConsolidatedAmountBasedOnPayAdvance() {
			return paymentService.getConsolidatedAmountBasedOnPayAdvance();
		}
	   
	   @SuppressWarnings("unused")
	@RequestMapping(value = "/collections/billingPayments/getPaymentDetails", method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody
		String detailedBillPopup(@RequestParam("paymentCollectionId") int paymentCollectionId){

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
						if(entity!=null)
							billSegmentStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getBillSegment()+"</td>";
						    billRefenceNoStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getBillReferenceNo()+"</td>";
						    billMonth+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+DateFormatUtils.format(entity.getBillMonth(), "MMM yyyy")+"</td>";
						    billAmountStr+="<tr><td style='padding: 0.2em; border: 1px solid #808080;'>"+entity.getAmount()+"</td>";
					}
				}
				
				String paymentStatus = "";
				if(billingPaymentsEntity.getStatus().equals("Posted")){
					paymentStatus+="<td style='padding: 0.2em; border: 1px solid #808080;color:green'><b><i>Paid</i></b></td>";
				}else if(billingPaymentsEntity.getStatus().equals("Cancelled")){
					paymentStatus+="<td style='padding: 0.2em; border: 1px solid #808080;color:red'><b><i>Cancelled</i></b></td>";
				}
				
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
						+"	<td style='padding: 0.2em; border: 1px solid #808080;' width='50%' colspan='2'><img id='eye' src='resources/images/grandArch2.png' width='252px' /></td>"
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
						+"				<td style='padding: 0.2em; border: 1px solid #808080;' align='center'><b>Excess Amount</b></td>"
						+"				<td style='padding: 0.2em; border: 1px solid #808080;' >"+billingPaymentsEntity.getExcessAmount()+"</td>"
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
						+"	<td style='padding: 0.2em; border: 1px solid #808080;backgound: black; color: white; font-weight: bolder;' colspan=3>Amount In Words : "+NumberToWord.convertNumberToWords((int)billingPaymentsEntity.getPaymentAmount())+" Only</td>"
						
						+"</tr>"
						+"</table>"
						+"</div>";
			}
			return str;

		}
	   
	   @RequestMapping(value = "/billingPayments/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForAccountNumbersUrl() {
			return paymentService.commonFilterForAccountNumbersUrl();
		}
		
		@RequestMapping(value = "/paymentAdjustments/commonFilterForAccountNumbersUrl", method = RequestMethod.GET)
		public @ResponseBody Set<String> commonFilterForAccountNumbersAdjustmentUrl() {
			return adjustmentService.commonFilterForAccountNumbersAdjustmentUrl();
		}
		
		@RequestMapping(value = "/billingPayments/getPersonListForFileter", method = RequestMethod.GET)
		public @ResponseBody List<?> readPersonNamesInLedger() {
			logger.info("In person data filter method");
			List<?> accountPersonList = paymentService.findPersonForFilters();
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
		
		// ****************************** Reconciliation Read,Create,Delete methods ********************************//

		@RequestMapping(value = "/reconciliation/reconciliationRead", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<?> reconciliationRead() {
			logger.info("In read reconciliation method");
			List<?> reconciliations = paymentService.getAllReconciliations();
			return reconciliations;
		}
		
		@RequestMapping(value = "/billingPayments/checkForNotPostedAccounts/{accountId}/{paymentType}/{typeOfService}", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object checkForNotPostedPaymentsAccounts(@PathVariable int accountId,@PathVariable String paymentType,@PathVariable String typeOfService) {
			String str = paymentType.replace("_", " ");
			List<Integer> paymentIdList = paymentService.checkForNotPostedPaymentsAccounts(accountId,str,typeOfService);
			
			String result = "";
			if(!paymentIdList.isEmpty()){
				result = result+"false";
			}else{
				result = result+"true";
			}
			return result;
		}
		
		@RequestMapping(value = "/paymentAdjustments/checkForNotPostedAccounts/{accountId}", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object checkForNotPostedAdjustmentsAccountsIn(@PathVariable int accountId) {
			
			List<Integer> adjustmentIdList = adjustmentService.checkForNotPostedAdjustmentsAccountsIn(accountId);
			
			String result = "";
			if(!adjustmentIdList.isEmpty()){
				result = result+"false";
			}
			return result;
		}
		
		
		 @RequestMapping(value = "/collections/billingPayments/postToTally", method = {RequestMethod.POST,RequestMethod.GET})
	        public @ResponseBody String postToTally(@RequestParam("paymentCollectionId") int paymentCollectionId) throws Exception{


	           logger.info("----------------------- Posting payment to tally ---------------------");
	           BillingPaymentsEntity paymentsEntity = paymentService.find(paymentCollectionId);
	           Date cashReceiptDate=paymentsEntity.getReceiptDate();
	           Date receiptDate=paymentsEntity.getReceiptDate();
	           String receiptNumber=paymentsEntity.getReceiptNo();
	           String bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
	           String bankName=paymentsEntity.getBankName();
	           String paymentMode=paymentsEntity.getPaymentMode();

	           double checkAmount=paymentsEntity.getPaymentAmount();

	           String billSegmentCircle="";
	           List<Map<String, Object>> mapsList = new ArrayList<>();
	           if(paymentsEntity.getPaymentType().equals("Pay Bill")){
	        	   System.out.println("paymentCollectionId : "+paymentCollectionId);
	               List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);
	               
	               for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) 
	               {
	                   logger.info("******Instrument Date************************************");
	                   Map<String, Object> map = new LinkedHashMap<>();
	                   map.put("accountNo", propertyService.find(accountService.find(paymentsEntity.getAccountId()).getPropertyId()).getProperty_No());
	                   map.put("againstBillRefNo",paymentSegmentsEntity.getBillReferenceNo());
	                   
					   if (paymentSegmentsEntity.getBillSegment().equals("Electricity")) {
							billSegmentCircle = "Elecricity Bill";
						}
					   if (paymentSegmentsEntity.getBillSegment().equals("CAM")) {
							billSegmentCircle = "Cam Bill";
						}
	                   map.put("billSegmentCircle",billSegmentCircle);
	                   map.put("billAmount", paymentSegmentsEntity.getAmount());
	                   map.put("instrumentDate",new SimpleDateFormat("YYYYMMdd").format(paymentsEntity.getInstrumentDate()));
	                   map.put("instrumentNumber", paymentsEntity.getInstrumentNo());

	                   
	                   
	                System.err.println(propertyService.find(accountService.find(paymentsEntity.getAccountId()).getPropertyId()).getProperty_No());
	                System.err.println(paymentSegmentsEntity.getBillReferenceNo());
	                System.err.println(billSegmentCircle);
	                System.err.println(paymentSegmentsEntity.getAmount());
	                System.err.println(new SimpleDateFormat("YYYYMMdd").format(paymentsEntity.getInstrumentDate()));
	                System.err.println(paymentsEntity.getInstrumentNo());
	                   

	                   
	                   System.out.println("accountNo = "+propertyService.find(accountService.find(paymentsEntity.getAccountId()).getPropertyId()).getProperty_No());
	                   System.out.println("instrumentNumber = "+paymentsEntity.getInstrumentNo());

	                   mapsList.add( map);
	            }
	           }

	           String str=tallyInvoicePostService.reponsePostReceiptToTally(mapsList, receiptDate, receiptNumber,bankAcNumberLedger,
	        		   		bankName,paymentMode,paymentCollectionId,checkAmount,cashReceiptDate);

	            if(str.equalsIgnoreCase("Receipt Successfully Posted To Tally")){
	            	transactionMasterService.setReceiptTallyStatusUpdate(paymentCollectionId);
	            }

	            return "("+new StringBuilder(receiptNumber).toString()+") "+str;

	       } 
		
		 
		 @SuppressWarnings("unused")
		@RequestMapping(value = "/billingPayments/postCollectionToTally", method = { RequestMethod.GET, RequestMethod.POST })
		   public @ResponseBody String postCollectionToTally(HttpServletResponse response,HttpServletRequest request) throws Exception
			{
                    
			 
			                   logger.info("inside payment post all to tally method==========");
			    	           Date cashReceiptDate;
			    	           Date receiptDate;
			    	           String receiptNumber;
			    	           String bankAcNumberLedger;
			    	           String bankName;
			    	           String paymentMode;
			    	           double checkAmount;
			                   
			                   
			                   

				String presentdate=request.getParameter("presentdate");
				logger.info("presentdate===="+presentdate);
				Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
		//		List<PaymentAdjustmentEntity> objList=paymentService.getFiftyRecordsForTally(monthDate);
				List<BillingPaymentsEntity> objList=paymentService.getFiftyRecordsPaymentsForTally(monthDate);
				System.err.println("size = "+objList.size());
				String str="";
				int count=0;
				for(BillingPaymentsEntity billingPaymentsEntity : objList)
				{
					int paymentCollectionId=billingPaymentsEntity.getPaymentCollectionId();
			          List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);
			          BillingPaymentsEntity paymentsEntityyy = paymentService.find(paymentCollectionId);
			            cashReceiptDate=paymentsEntityyy.getReceiptDate();
	    	            receiptDate=paymentsEntityyy.getReceiptDate();
	    	            receiptNumber=paymentsEntityyy.getReceiptNo();
	    	            bankAcNumberLedger=ResourceBundle.getBundle("application").getString("bankLedger");
	    	            bankName=paymentsEntityyy.getBankName();
	    	            paymentMode=paymentsEntityyy.getPaymentMode();
	    	            checkAmount=paymentsEntityyy.getPaymentAmount();
			    
		               List<Map<String, Object>> mapsList = new ArrayList<>();
		               for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) 
		               {
		                   logger.info("******Instrument Date************************************");
		                   String billSegmentCircle="";
		                   Map<String, Object> map = new LinkedHashMap<>();
		                   map.put("accountNo", propertyService.find(accountService.find(billingPaymentsEntity.getAccountId()).getPropertyId()).getProperty_No());
		                   map.put("againstBillRefNo",paymentSegmentsEntity.getBillReferenceNo());
		                   
						   if (paymentSegmentsEntity.getBillSegment().equals("Electricity")) {
								billSegmentCircle = "Elecricity Bill";
							}
						   if (paymentSegmentsEntity.getBillSegment().equals("CAM")) {
								billSegmentCircle = "Cam Bill";
							}
		                   map.put("billSegmentCircle",billSegmentCircle);
		                   map.put("billAmount", paymentSegmentsEntity.getAmount());
		                   map.put("instrumentDate",new SimpleDateFormat("YYYYMMdd").format(billingPaymentsEntity.getInstrumentDate()));
		                   map.put("instrumentNumber", billingPaymentsEntity.getInstrumentNo());
		                   
		                   
		                System.err.println(propertyService.find(accountService.find(billingPaymentsEntity.getAccountId()).getPropertyId()).getProperty_No());
		                System.err.println(paymentSegmentsEntity.getBillReferenceNo());
		                System.err.println(billSegmentCircle);
		                System.err.println(paymentSegmentsEntity.getAmount());
		                System.err.println(new SimpleDateFormat("YYYYMMdd").format(billingPaymentsEntity.getInstrumentDate()));
		                System.err.println(billingPaymentsEntity.getInstrumentNo());
		                   
		                   mapsList.add( map);
		            }
		               String responseFromTally=tallyInvoicePostService.reponsePostReceiptToTally(mapsList, receiptDate, receiptNumber,bankAcNumberLedger,
		        		   		bankName,paymentMode,paymentCollectionId,checkAmount,cashReceiptDate);
		               if(responseFromTally.equalsIgnoreCase("Receipt Successfully Posted To Tally"))
						{  
							count=count+1;
							transactionMasterService.setReceiptTallyStatusUpdate(paymentCollectionId);
							
						}
				 }	
						System.out.println("count : "+count);
						if(count>0)
						{
							System.out.println("Inside if");
   						    return  Integer.toString(count)+" Receipt Posted To Tally";
						}else
						{
							System.out.println("Inside else");
							return "No Receipt Posted To Tally";
						}
		          

		           
				}
				
			
	   
			 
			 /*		
			  HttpSession session=request.getSession(false);
			  String fromDate = "";
				String toDate = "";
				if(request.getParameter("fromDate")!=null){
					fromDate = request.getParameter("fromDate");
				}
				if(request.getParameter("toDate")!=null){
					toDate = request.getParameter("toDate");
				}
			
					Date fromDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
					Date toDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
			 logger.info("----------- Post all paymets to tally those approved ----------------------");
			 List<Map<String,Object>> result=  tallyAllBillPostServiceImpl.post(session,fromDateVal);
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
				
			   
			}*/
		 
		@RequestMapping(value = "/bill/getlatePaymentAmount", method = { RequestMethod.GET,
				RequestMethod.POST })
		public @ResponseBody
		List<?> getPreviousBillDate(HttpServletRequest req, HttpServletResponse response)
				throws ParseException {
			
			
			
			int accountId = Integer.parseInt(req.getParameter("accountId"));
			Date receiptDate=new SimpleDateFormat("MM/dd/yyyy").parse(req.getParameter("rdate"));
			String typeOfService=req.getParameter("typeOfService");
			
					logger.info("reciptedate::::::::"+receiptDate);

			
			return paymentService.latePaymentAmount(accountId,receiptDate,typeOfService);
					
		}

	
		
		//Audit Report:Account Statement by Flat
		
		@SuppressWarnings({ "serial" })
		@RequestMapping(value ="/payment/accountStatementByFlat/{month}/{data}", method = RequestMethod.POST)
		public @ResponseBody
		List<?> accountStatementByFlat(@PathVariable String month,@PathVariable int data, HttpServletRequest req,Model model) throws java.text.ParseException {
			model.addAttribute("selectByMonth",month);
						
			List<Map<String, Object>> paymentMap =  new ArrayList<Map<String, Object>>();    
			if(data==47){
			
					Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
					Calendar cal = Calendar.getInstance();
					cal.setTime(monthToShow);
					int month1 = cal.get(Calendar.MONTH);
					int montOne = month1 + 1;
					int year = cal.get(Calendar.YEAR);
					logger.info("montOne" + montOne);
					logger.info("year" + year);
					
					List<BillingPaymentsEntity> billingPaymentData=paymentService.getPaymentDataBasedOnMonth(montOne,year);
					final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			    	sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
					
					for (final BillingPaymentsEntity paymentData : billingPaymentData) {
						paymentMap.add(new HashMap<String, Object>() {{    
							
							put("receiptDate",sdfDate.format(paymentData.getReceiptDate()));
						    put("receiptNo", paymentData.getReceiptNo());
							put("paymentMode", paymentData.getPaymentMode());
							put("paymentAmount",paymentData.getPaymentAmount());
							put("status", paymentData.getStatus());
							
							Account account=accountService.find(paymentData.getAccountId());
							put("accountNo", account.getAccountNo());
						
							
							List<PaymentSegmentsEntity> segmentsEntities = paymentSegmentService.findAllByCollectionId(paymentData.getPaymentCollectionId());
					
							if(!(segmentsEntities.isEmpty())){
							put("serviceType",segmentsEntities.get(0).getBillSegment());
							put("billNo", segmentsEntities.get(0).getBillReferenceNo());
							}
				
					}
				});
			}
		}
			return paymentMap;

	}
		
		
		
		@RequestMapping(value = "/accountStatementByFlat/exportExcel/{id}/{month}", method = {RequestMethod.POST,RequestMethod.GET})
		public String staffBioLog(HttpServletRequest request,HttpServletResponse response,@PathVariable int id,@PathVariable String month) throws IOException, java.text.ParseException
		{
			
				ServletOutputStream servletOutputStream=null;
				if(id==47)//Account Statement by Flat
				{
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Account Statement by Flat "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			
		        String sheetName = "Account Statement by Flat";//name of sheet

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
		    	
		    	header.createCell(0).setCellValue("Sl NO");
		        header.createCell(1).setCellValue("Account No");
		        header.createCell(2).setCellValue("Receipt No");
		        header.createCell(3).setCellValue("Receipt Date");
		        header.createCell(4).setCellValue("Bill No"); 
		        header.createCell(5).setCellValue("Service Type"); 
		        header.createCell(6).setCellValue("Payment Amount");
		        header.createCell(7).setCellValue("Payment Mode");
		        header.createCell(8).setCellValue("Status");				        
		      
		        for(int j = 0; j<=8; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		        }
		        
		        int count = 1;
		        
		    	Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
				Calendar cal = Calendar.getInstance();
				cal.setTime(monthToShow);
				int month1 = cal.get(Calendar.MONTH);
				int montOne = month1 + 1;
				int year = cal.get(Calendar.YEAR);
				logger.info("montOne" + montOne);
				logger.info("year" + year);
				
				List<BillingPaymentsEntity> billingPaymentData=paymentService.getPaymentDataBasedOnMonth(montOne,year);
				SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		    	sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
				for (final BillingPaymentsEntity paymentData : billingPaymentData) {
					
					List<PaymentSegmentsEntity> segmentsEntities = paymentSegmentService.findAllByCollectionId(paymentData.getPaymentCollectionId());
					XSSFRow row = sheet.createRow(count);
					row.createCell(0).setCellValue(String.valueOf(count));
					Account account=accountService.find(paymentData.getAccountId());
					row.createCell(1).setCellValue(account.getAccountNo());
					row.createCell(2).setCellValue(paymentData.getReceiptNo());
					if(paymentData.getReceiptDate()!=null){
					row.createCell(3).setCellValue(sdfDate.format(paymentData.getReceiptDate()));
					}
					row.createCell(4).setCellValue(segmentsEntities.get(0).getBillReferenceNo());
					row.createCell(5).setCellValue(segmentsEntities.get(0).getBillSegment());
					row.createCell(6).setCellValue(paymentData.getPaymentAmount());
					row.createCell(7).setCellValue(paymentData.getPaymentMode());
					row.createCell(8).setCellValue(paymentData.getStatus());
				
					count++;
					}
			
		
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		    	

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"Account Statement by Flat("+month+").xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				
			
			}
			
			return null;
		}

		
		//Resident Receipt
		@SuppressWarnings({ "serial" })
		@RequestMapping(value ="/resident/residentReceipt/{month}/{data}", method = RequestMethod.POST)
		public @ResponseBody
		List<?> residentReceipt(@PathVariable String month,@PathVariable int data, HttpServletRequest req,Model model) throws java.text.ParseException {
			model.addAttribute("selectByMonth",month);
						
			List<Map<String, Object>> paymentMap =  new ArrayList<Map<String, Object>>();    
			if(data==50){
			
					Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
					Calendar cal = Calendar.getInstance();
					cal.setTime(monthToShow);
					int month1 = cal.get(Calendar.MONTH);
					int montOne = month1 + 1;
					int year = cal.get(Calendar.YEAR);
					logger.info("montOne" + montOne);
					logger.info("year" + year);
					
					List<BillingPaymentsEntity> billingPaymentData=paymentService.getPaymentDataBasedOnMonth(montOne,year);
					
					
					for (final BillingPaymentsEntity paymentData : billingPaymentData) {
						paymentMap.add(new HashMap<String, Object>() {{    
							
							
							
							put("instructionBank",paymentData.getBankName());
						    put("instructionNo", paymentData.getInstrumentNo());
							put("instructionDate", paymentData.getInstrumentDate());
							put("receiptDate",paymentData.getReceiptDate());
						    put("receiptNo", paymentData.getReceiptNo());
							put("paymentMode", paymentData.getPaymentMode());
							put("payAmount",paymentData.getPaymentAmount());
							Account account=accountService.find(paymentData.getAccountId());
							put("accountNo", account.getAccountNo());
							if(account.getPerson().getLastName()!=null){
							put("personNames", account.getPerson().getFirstName()+" "+account.getPerson().getLastName());
							}
							else{
							put("personNames", account.getPerson().getFirstName());
							}
							
							if(account!=null){
							List<Property> list=propertyService.executeSimpleQuery("SELECT p FROM Property p WHERE p.propertyId="+account.getPropertyId());
							if(!(list.isEmpty())){
							put("blockName",list.get(0).getBlocks().getBlockName());
							put("flatNo",list.get(0).getProperty_No());
							}
							}
						
				
					}
				});
			}
		}
			return paymentMap;

	}
		
		@RequestMapping(value = "/residentReceipt/exportExcel/{id}/{month}", method = {RequestMethod.POST,RequestMethod.GET})
		public String residentReceiptExcel(HttpServletRequest request,HttpServletResponse response,@PathVariable int id,@PathVariable String month) throws IOException, java.text.ParseException
		{
			
				ServletOutputStream servletOutputStream=null;
				if(id==50)//Resident Receipt
				{
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Resident Receipt"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			
		        String sheetName = "Resident Receipt";//name of sheet

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
		    	
		    	header.createCell(0).setCellValue("Sl NO");
		        header.createCell(1).setCellValue("Block Name");
		        header.createCell(2).setCellValue("Flat No");
		        header.createCell(3).setCellValue("Person Name");
		        header.createCell(4).setCellValue("Instrument No"); 
		        header.createCell(5).setCellValue("Instrument Date"); 
		        header.createCell(6).setCellValue("Bank Name");
		        header.createCell(7).setCellValue("Account Number");
		        header.createCell(8).setCellValue("Payment Mode");
		        header.createCell(9).setCellValue("Receipt No");
		        header.createCell(10).setCellValue("Posted Date");
		        header.createCell(11).setCellValue("Payment Amont");	
		      
		        for(int j = 0; j<=11; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		        }
		        
		        int count = 1;
		        
		    	Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
				Calendar cal = Calendar.getInstance();
				cal.setTime(monthToShow);
				int month1 = cal.get(Calendar.MONTH);
				int montOne = month1 + 1;
				int year = cal.get(Calendar.YEAR);
				logger.info("montOne" + montOne);
				logger.info("year" + year);
				
				List<BillingPaymentsEntity> billingPaymentData=paymentService.getPaymentDataBasedOnMonth(montOne,year);
				SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		    	sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
				for (final BillingPaymentsEntity paymentData : billingPaymentData) {
					
					XSSFRow row = sheet.createRow(count);
					row.createCell(0).setCellValue(String.valueOf(count));
				
					
					Account account=accountService.find(paymentData.getAccountId());
					if(account.getPerson().getLastName()!=null){
						row.createCell(3).setCellValue( account.getPerson().getFirstName()+" "+account.getPerson().getLastName());
					}
					else{
						row.createCell(3).setCellValue(account.getPerson().getFirstName());
					}
					
					row.createCell(4).setCellValue(paymentData.getInstrumentNo());
					
					if(paymentData.getInstrumentDate()!=null){
						row.createCell(5).setCellValue(paymentData.getInstrumentDate());
					}
					if(paymentData.getBankName()!=null && paymentData.getBankName()!="Select"){
					row.createCell(6).setCellValue(paymentData.getBankName());
					}
					row.createCell(7).setCellValue(account.getAccountNo());
					row.createCell(8).setCellValue(paymentData.getPaymentMode());
					row.createCell(9).setCellValue(paymentData.getReceiptNo());
					if(paymentData.getReceiptDate()!=null){
					row.createCell(10).setCellValue(sdfDate.format(paymentData.getReceiptDate()));
					}
					row.createCell(11).setCellValue(paymentData.getPaymentAmount());
					
					if(account!=null){
						List<Property> list=propertyService.executeSimpleQuery("SELECT p FROM Property p WHERE p.propertyId="+account.getPropertyId());
					if(!(list.isEmpty())){
						row.createCell(1).setCellValue(list.get(0).getBlocks().getBlockName());
						row.createCell(2).setCellValue(list.get(0).getProperty_No());
					}
					}
					
					
					count++;
					}
			
		
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		    	

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"Resident Receipt("+month+").xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				servletOutputStream.flush();
				servletOutputStream.close();
				
				
		}

		return null;
	}

	@RequestMapping(value = "/billingPayments/getAllPropertyNo", method = RequestMethod.GET)
	public @ResponseBody List<?> getAllPropertyNo() {

		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> propertyMap = null;
		 for (Iterator<?> iterator = paymentService.getAllPropertyNo().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				propertyMap = new HashMap<String, Object>();
				
				propertyMap.put("propertyId", (Integer)values[0]);
				propertyMap.put("propertyNo", (String)values[1]);
			    result.add(propertyMap);
	     }
     return result;
	}
	


	 @RequestMapping(value = "/collections/adjustment/postToTally", method = {RequestMethod.POST,RequestMethod.GET})

	   public @ResponseBody String postAdjustmentToTally(@RequestParam("adjustmentId") int adjustmentId) throws Exception{
			 
			 logger.info("inside adjustment post to tally method=========="+adjustmentId);
			 
			String response= tallyAdjustmentPostService.reponsePostAdjustmentToTally(adjustmentId);
			if(response.equalsIgnoreCase("Adjustment Successfully Posted To Tally"))
			{
				adjustmentService.updateTallyStatus(adjustmentId);
				
			}
			 
			 return response;
			 

		 }
	

		 
	   

	   @RequestMapping(value = "/adjustment/postCollectionToTally", method = {RequestMethod.POST,RequestMethod.GET})
	    public @ResponseBody String postAllAdjustmentToTally(HttpServletRequest req,HttpServletResponse response) throws Exception{
			 
			 logger.info("inside adjustment post all to tally method==========");
			 

				String serviceType=req.getParameter("serviceType");
				String presentdate=req.getParameter("presentdate");
				logger.info("service type===="+serviceType);
				logger.info("presentdate===="+presentdate);
				Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
				List<PaymentAdjustmentEntity> objList=paymentService.getFiftyRecordsForTally(monthDate);
				System.err.println("size = "+objList.size());
				String str="";
				int count=0;
				for(PaymentAdjustmentEntity paymentAdjustmentEntity : objList)
				{
					int adjustmentId = paymentAdjustmentEntity.getAdjustmentId();
					String responseFromTally= tallyAdjustmentPostService.reponsePostAdjustmentToTally(adjustmentId);
					if(responseFromTally.equalsIgnoreCase("Adjustment Successfully Posted To Tally"))
					{  
						count=count+1;
						adjustmentService.updateTallyStatus(adjustmentId);
						
					}
				}
				System.out.println("count : "+count);
				if(count>0)
				{
					System.out.println("Inside if");
					str=Integer.toString(count);
				return  count+" Adjustments Posted To Tally";
				}else
				{
					System.out.println("Inside else");
					return "No Adjustments Posted To Tally";
				}
			
	   }
	   
	 /*    @RequestMapping(value = "/adjustment/postCollectionToTally", method = {RequestMethod.POST,RequestMethod.GET})
	    public @ResponseBody String postAllAdjustmentToTally(HttpServletRequest req,HttpServletResponse response) throws Exception{
			 
			 logger.info("inside adjustment post all to tally method==========");
			 

				String serviceType=req.getParameter("serviceType");
				String presentdate=req.getParameter("presentdate");
				logger.info("service type===="+serviceType);
				logger.info("presentdate===="+presentdate);
				Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
				.parse(presentdate);
				HttpSession session=req.getSession(false);
	//			List<Map<String,Object>> result=tallyAllInvoicePostServiceImpl.post(session,serviceType,monthDate);
				
				
				List<Map<String,Object>> result=tallyAllAdjustmentsPostServiceImpl.post(monthDate);
						
				int count=0;
		   	 for (Map<String, Object> map : result) {
			    	
		   	  for (Map.Entry<String, Object> entry : map.entrySet()) {
		   	        if(entry.getKey().equalsIgnoreCase("success"))
		   	        {
		   	        	count++;
		   	        Object value = entry.getValue();
		   	        System.out.println( "=========value=="+value);
		   	        
		   	        
		   	        }
		   	        if(entry.getKey().equalsIgnoreCase("server"))
		   	        {
		   	        	return "Please start 'TallyERP9' server and load company";
		   	        }
		   	        
		   	      
		   	    
		   	  }
		   	 }
		   	
			if(count>0)
			{
				return count+ "Bill Successfully Posted To Tally";
			}
			//return null;
			
			return "no! Bill  Posted To Tally";
				
			 
		 }  */
	   
	   @RequestMapping(value = "/billingPayments/getAccountsBasedOnseletedProperty/{propertyId}", method = RequestMethod.GET)
		public @ResponseBody List<?> accountNumberAutocomplete(@PathVariable int propertyId) {
			return paymentService.getAllAccuntNumbersBasedOnProperty(propertyId);
		}
	   
	   @RequestMapping(value = "/billingPayments/searchPaymentDataByMonth", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody List<?> searchPaymentDataByMonth(HttpServletRequest req) {
			
			String fromDate = "";
			String toDate = "";
			if(req.getParameter("fromDate")!=null){
				fromDate = req.getParameter("fromDate");
			}
			if(req.getParameter("toDate")!=null){
				toDate = req.getParameter("toDate");
			}
			List<BillingPaymentsEntity> paymentsEntities = new ArrayList<>();
			try {
				Date fromDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
				Date toDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
				paymentsEntities = paymentService.searchPaymentDataByMonth(fromDateVal,toDateVal);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return paymentsEntities;
		}	  
		
		@RequestMapping(value = "/paymentAdjustments/searchAdjustmentDataByMonth", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody List<?> searchAdjustmentDataByMonth(HttpServletRequest req) {
			
			String fromDate = "";
			String toDate = "";
			if(req.getParameter("fromDate")!=null){
				fromDate = req.getParameter("fromDate");
			}
			if(req.getParameter("toDate")!=null){
				toDate = req.getParameter("toDate");
			}
			List<PaymentAdjustmentEntity> adjustmentEntities = new ArrayList<>();
			try {
				Date fromDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
				Date toDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
				adjustmentEntities = adjustmentService.searchAdjustmentDataByMonth(fromDateVal,toDateVal);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return adjustmentEntities;
		}	
		
		@RequestMapping(value = "/exportPaymentsToExcel", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportPaymentsToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException
		{
			System.err.println("************* Inside BillingPaymentManagementController.exportPaymentsToExcel() Method *************");
			String resp = request.getParameter("date");
			String[] date = resp.split("/");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
			String payments = "select p.CP_RECEIPT_NO,a.ACCOUNT_NO,(PR.FIRST_NAME||PR.LAST_NAME)AS NAME,pro.PROPERTY_NO,p.CP_RECEIPT_DATE,p.CP_AMOUNT,p.INSTRUMENT_NO"
							+ " FROM PAYMENTS p,ACCOUNT a,PROPERTY pro,PERSON pr WHERE p.ACCOUNT_ID=a.ACCOUNT_ID AND a.PROPERTYID=pro.PROPERTY_ID"
							+ " AND a.PERSON_ID=PR.PERSON_ID AND EXTRACT(month FROM p.CP_RECEIPT_DATE) IN("+date[0]+") AND "
							+ " EXTRACT(year FROM p.CP_RECEIPT_DATE)="+date[1]+" AND p.STATUS NOT IN 'Cancelled' ORDER BY p.CP_RECEIPT_DATE DESC";
			
			List<Object[]> paymentRecords = entityManager.createNativeQuery(payments).getResultList();
			System.err.println("paymentRecords="+paymentRecords); 
			
			
	        String sheetName = "Templates";//name of sheet

	    	XSSFWorkbook wb = new XSSFWorkbook();
	    	XSSFSheet sheet = wb.createSheet(sheetName) ;
	    	XSSFRow header  = sheet.createRow(0);    	
	    	
	    	XSSFCellStyle style = wb.createCellStyle();
	    	style.setWrapText(true);
	    	XSSFFont font = wb.createFont();
	    	font.setFontName(HSSFFont.FONT_ARIAL);
	    	font.setFontHeightInPoints((short)10);
	    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    	style.setFont(font);
	    	
	    	header.createCell(0).setCellValue("RECEIPT_NO");
	        header.createCell(1).setCellValue("ACCOUNT_NO");
	        header.createCell(2).setCellValue("NAME");
	        header.createCell(3).setCellValue("PROPERTY_NO");
	        header.createCell(4).setCellValue("RECEIPT_DATE");
	        header.createCell(5).setCellValue("AMOUNT");
	        header.createCell(6).setCellValue("INSTRUMENT_NO");
	    
	        for(int j = 0; j<7; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
	        }
	        
	        int count = 1;
	    	for (Object[] paymentRecord :paymentRecords ) 
	    	{
	    		String str = "";
	    		if((String)paymentRecord[6]!=null){
	    			str=(String)paymentRecord[6];
	    		}else{
	    			str="NA";
	    		}
	    		
	    		XSSFRow row = sheet.createRow(count);
	    		row.createCell(0).setCellValue(paymentRecord[0].toString());
	    		row.createCell(1).setCellValue(paymentRecord[1].toString());
	    		row.createCell(2).setCellValue(paymentRecord[2].toString());
	    		row.createCell(3).setCellValue(paymentRecord[3].toString());
	    		row.createCell(4).setCellValue(paymentRecord[4].toString());
	    		row.createCell(5).setCellValue(paymentRecord[5].toString());
	    		row.createCell(6).setCellValue(paymentRecord[6].toString());
	    		count ++;
	    	}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"PaymentRecords.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
		@RequestMapping(value = "/exportAdjustmentsToExcel", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportAdjustmentsToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException
		{
			System.err.println("************* Inside BillingPaymentManagementController.exportPaymentsToExcel() Method *************");
			String resp = request.getParameter("date");
			String[] date = resp.split("/");
			
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
			String payments = "SELECT a.ACCOUNT_NO,(PR.FIRST_NAME||PR.LAST_NAME)AS NAME,pro.PROPERTY_NO,adj.JV_DATE,"
					+ " adj.JV_NO,adj.ADJUSTMENT_TYPE,adj.JV_AMOUNT,ADJ.REMARKS FROM ADJUSTMENTS adj,ACCOUNT a,PROPERTY pro,PERSON pr"
					+ " WHERE adj.ACCOUNT_ID=a.ACCOUNT_ID AND a.PROPERTYID=pro.PROPERTY_ID AND a.PERSON_ID=PR.PERSON_ID AND"
					+ " EXTRACT(month FROM adj.JV_DATE) IN("+date[0]+") AND EXTRACT(year FROM adj.JV_DATE)="+date[1]+" AND "
					+ "adj.STATUS NOT IN 'Cancelled' ORDER BY ADJ.JV_DATE DESC";
			
			List<Object[]> paymentRecords = entityManager.createNativeQuery(payments).getResultList();
			System.err.println("paymentRecords="+paymentRecords); 
			
			
	        String sheetName = "Templates";//name of sheet

	    	XSSFWorkbook wb = new XSSFWorkbook();
	    	XSSFSheet sheet = wb.createSheet(sheetName) ;
	    	XSSFRow header  = sheet.createRow(0);    	
	    	
	    	XSSFCellStyle style = wb.createCellStyle();
	    	style.setWrapText(true);
	    	XSSFFont font = wb.createFont();
	    	font.setFontName(HSSFFont.FONT_ARIAL);
	    	font.setFontHeightInPoints((short)10);
	    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    	style.setFont(font);
	    	
	    	header.createCell(0).setCellValue("ACCOUNT_NO");
	        header.createCell(1).setCellValue("NAME");
	        header.createCell(2).setCellValue("PROPERTY NO");
	        header.createCell(3).setCellValue("ADJUSTMENT_DATE");    
	        header.createCell(4).setCellValue("ADJUSTMENT_NO");
	        header.createCell(5).setCellValue("ADJUSTMENT_TYPE");
	        header.createCell(6).setCellValue("AMOUNT");
	        header.createCell(7).setCellValue("REMARKS");
	    
	        for(int j = 0; j<8; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
	        }
	        
	        int count = 1;
	    	for (Object[] paymentRecord :paymentRecords ) 
	    	{
	    		XSSFRow row = sheet.createRow(count);
	    		row.createCell(0).setCellValue(paymentRecord[0].toString());
	    		row.createCell(1).setCellValue(paymentRecord[1].toString());
	    		row.createCell(2).setCellValue(paymentRecord[2].toString());
	    		row.createCell(3).setCellValue(paymentRecord[3].toString());
	    		row.createCell(4).setCellValue(paymentRecord[4].toString());
	    		row.createCell(5).setCellValue(paymentRecord[5].toString());
	    		row.createCell(6).setCellValue(paymentRecord[6].toString());
	    		row.createCell(7).setCellValue(paymentRecord[7].toString());
	    		count ++;
	    	}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"PaymentRecords.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
}
