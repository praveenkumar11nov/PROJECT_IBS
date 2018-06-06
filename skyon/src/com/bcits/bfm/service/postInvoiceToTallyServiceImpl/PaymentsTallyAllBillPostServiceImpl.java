package com.bcits.bfm.service.postInvoiceToTallyServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.representation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.Envelope;
import com.ResponseTally;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.service.transactionMaster.TransactionMasterService;
import com.envelope.body.data.collection.Voucher;
import com.envelope.body.data.collection.ledgerlist.AllLedgerEntriesList;
import com.main.tally.CommonTally;
import com.tally.billreceipt.BillReceiptTally;
import com.tally.billreceipt.CashReceiptTally;

@Service
public class PaymentsTallyAllBillPostServiceImpl {
	
	
	
	Logger logger=Logger.getLogger(PaymentsTallyAllBillPostServiceImpl.class);
	
	@Autowired
	TariffCalculationService tariffCalculationService;
	@Autowired
	private TransactionMasterService transactionMasterService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private AccountService accountService;


	  ArrayList<Map<String,Object>> arraylist=new ArrayList<Map<String,Object>>();
		Map<String,Object> hashmap=new HashMap<String, Object>();
		//HttpSession session=null;
		int size=0;
		int count= 0;
		int invoiceCount=0;
		Map<String,Object>  obj=null;
		
	
		public String generatexmlForPaymentsPosting(Date monthDate) {
			  count=0;
			  size=0;
			  logger.info("*****Number of loop*******"+count+"*****and Size ****"+size);
			  List<Map<String,Object>> reportList=new ArrayList<Map<String,Object>>();
			 // List<BillingPaymentsEntity> objList=transactionMasterService.getFiftyRecord();
			  List<BillingPaymentsEntity> objList=paymentService.getAllRecordsForCAM(monthDate);;
			  String xmlFile="";
			
					try
					{
						size=objList.size();
						for(BillingPaymentsEntity billingPaymentsEntity:objList)
						{
							String receiptNumber		= null;
							String bankAcNumberLedger = null;
							String bankName			= null;
							String paymentMode		= null;
							List<Map<String, Object>> mapsList = new ArrayList<>();
							int paymentCollectionId=billingPaymentsEntity.getPaymentCollectionId();

							Date receiptDate	 	= null;
							double checkAmount 	= 0.0;
							Date cashReceiptDate = null;
							try
							{
								BillingPaymentsEntity paymentsEntity = paymentService.find(paymentCollectionId);
								System.err.println("=================>paymentsEntity="+paymentsEntity);

								cashReceiptDate	   = paymentsEntity.getReceiptDate();
								receiptDate		   = paymentsEntity.getReceiptDate();
								receiptNumber	   = paymentsEntity.getReceiptNo();
								bankAcNumberLedger = ResourceBundle.getBundle("application").getString("bankLedger");
								bankName		   = paymentsEntity.getBankName();
								paymentMode		   = paymentsEntity.getPaymentMode();
								checkAmount		   = paymentsEntity.getPaymentAmount();

								String billSegmentCircle="";
								if(paymentsEntity.getPaymentType().equals("Pay Bill"))
								{
									List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentCollectionId);
									for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) 
									{
										logger.info("Instrument Data====>"+paymentSegmentsEntity.getBillingPaymentsEntity().getInstrumentDate());
										Map<String, Object> map = new LinkedHashMap<>();
										map.put("accountNo", propertyService.find(accountService.find(paymentsEntity.getAccountId()).getPropertyId()).getProperty_No());
										map.put("againstBillRefNo",paymentSegmentsEntity.getBillReferenceNo());
										if(paymentSegmentsEntity.getBillSegment().equals("Electricity"))
										{
											billSegmentCircle="Elecricity Charges";
										}
										if(paymentSegmentsEntity.getBillSegment().equals("CAM"))
										{
											billSegmentCircle="Cam Charges";
										}
										map.put("billSegmentCircle",billSegmentCircle);
										map.put("billAmount", paymentSegmentsEntity.getAmount());
										map.put("instrumentDate",new SimpleDateFormat("YYYYMMdd").format( paymentSegmentsEntity.getBillingPaymentsEntity().getInstrumentDate()));
										map.put("instrumentNumber", paymentSegmentsEntity.getBillingPaymentsEntity().getInstrumentNo());
										mapsList.add(map);
									}
								}

							}  
							catch(Exception e)
							{
								// e.printStackTrace();
								logger.info("Exception  message"+e.getMessage());
							}
							String str=this.reponsePostReceiptToTally(mapsList, receiptDate, receiptNumber,bankAcNumberLedger,
									bankName,paymentMode,paymentCollectionId,checkAmount,cashReceiptDate);
							if(str!=null){
								paymentService.upDateTallyStatus(paymentCollectionId,"XML Generated");
							}
							xmlFile+=str+"\n";
						}
					}
					catch(Exception e)
					{
						logger.info("inside post method catch block");
						e.printStackTrace();;
					}
					
					
			return xmlFile;
		}

		public String reponsePostReceiptToTally(List<Map<String, Object>> mapsList,Date receiptDate, String receiptNumber,
													String bankAcNumberLedger,String bankName,String paymentMode,
													int paymentId,double checkAmount,Date cashReceiptDate) throws Exception {
			
			
			String tallyPortNumber	= ResourceBundle.getBundle("application").getString("tallyPortNo");
			String tallyIpAddress	= ResourceBundle.getBundle("application").getString("tallyIPAddress");
			String tallyUserName	= ResourceBundle.getBundle("application").getString("tallyUserName");
			String tallyPassword	= ResourceBundle.getBundle("application").getString("tallyPassword");
			String tallyCompanyName = ResourceBundle.getBundle("application").getString("tallyCompanyName");
			String bankLedger		= ResourceBundle.getBundle("application").getString("bankLedger");
			
			String cashReptDate=null;
			String recptDate=null;
			String branchName=null;
			if(receiptDate != null){
				recptDate=new SimpleDateFormat("YYYYMMdd").format(receiptDate);
			}
			if(cashReceiptDate != null){
				cashReptDate=new SimpleDateFormat("YYYYMMdd").format(cashReceiptDate);
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(paymentId);
			String autoId = sb.toString();
			if(bankName !=null){
				branchName = bankName;
			}
			String voucherType = "Receipt";
			String tansferMode = null;
			String remoteId = "6dd-00000034" + autoId;
			String voucherKey = "a31f:00000028" + autoId;
			//BillReceiptTally receiptTally = new BillReceiptTally();
			//CashReceiptTally cashReceiptTally=new CashReceiptTally();
		
			String catagoryName="Delhi Elctricity Departments";
			String state="Karnataka";
			String transactionType2=null;
			String receiptResponse=null;
			
			System.out.println("Payement mode is========>"+paymentMode);
			
			if(paymentMode.equalsIgnoreCase("Cheque")){
				String transactionType ="Cheque/DD";
				if (transactionType.equalsIgnoreCase("Cheque/DD")) {
					tansferMode = "Cheque/DD";
				}
				receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
						recptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}if(paymentMode.equalsIgnoreCase("RTGS/NEFT")){
				String transactionType ="Inter Bank Transfer";
				if (transactionType.equalsIgnoreCase("Inter Bank Transfer")) {
					tansferMode = "NEFT";
				}
				receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
						recptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
			if(paymentMode.equalsIgnoreCase("Cash")){
				String transactionType = "Cash";
				if (transactionType.equalsIgnoreCase("Cash")) {
					tansferMode = "Cash";
				}
				receiptResponse=this.createCashReceipt(remoteId, voucherKey,mapsList,
						cashReptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}if(paymentMode.equalsIgnoreCase("DD")){
				String transactionType = "Cheque/DD";
				if (transactionType.equalsIgnoreCase("Cheque/DD")) {
					tansferMode = "Cheque/DD";
				}
				receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
						recptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}if(paymentMode.equalsIgnoreCase("Online")){
				String transactionType = "Others";
				if (transactionType.equalsIgnoreCase("Others")) {
					bankName="";
					branchName="";
					tansferMode = "Others";
				}
				receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
						recptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
			if(paymentMode.equalsIgnoreCase("AdvancePayment")){
				String transactionType = "AdvancePayment";
				if (transactionType.equalsIgnoreCase("Cash")) {
					tansferMode = "Cash";
				}
				receiptResponse=this.createCashReceipt(remoteId, voucherKey,mapsList,
						cashReptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
			if(paymentMode.equalsIgnoreCase("NB") || paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("PPI"))
			{
				String transactionType = "Others";
				if (transactionType.equalsIgnoreCase("Cash")) {
					tansferMode = "Cash";
				}
				receiptResponse=this.createCashReceipt(remoteId, voucherKey,mapsList,
						cashReptDate, voucherType, paymentMode,
						paymentId, bankName, transactionType,
						transactionType2, branchName, tansferMode, catagoryName, state,
						receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
						tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
				
			}
		return receiptResponse;
			
	}


		

		public  String createBilllReceipt(String remoteId, String voucherKey,List<Map<String, Object>> list,
				String receiptDate, String voucherType, String paymentMode,
				int paymentId, String bankName,String transactionType,
				String transactionType2, String branchName, String tansferMode,String  catagoryName, String state,
				String receiptNumber,double checkAmount,String tallyCompanyName,String tallyPortNumber,
				String tallyIpAddress,String  tallyUserName, String tallyPassword,String bankLedger)throws Exception{
			
			
			String xmlInventories="";
			paymentMode=StringEscapeUtils.escapeXml(paymentMode);
			bankName=StringEscapeUtils.escapeXml(bankName);
			branchName=StringEscapeUtils.escapeXml(branchName);
			receiptNumber=StringEscapeUtils.escapeXml(receiptNumber);
			tallyUserName=StringEscapeUtils.escapeXml(tallyUserName);
			transactionType=StringEscapeUtils.escapeXml(transactionType);
			bankLedger=StringEscapeUtils.escapeXml(bankLedger);
			
			StringBuilder sb = new StringBuilder();
			List<String> list1=this.getBillAllocationDetails(list,catagoryName,checkAmount);
			for (int i = 0; i < list1.size(); i++) {
				sb.append(list1.get(i));
				xmlInventories = sb.toString();
			}
			
			String  xmlBank=this.getBankAllocationDetails(list,paymentMode,checkAmount,receiptDate,catagoryName,transactionType,tansferMode,bankLedger,xmlInventories,bankName);
			String xmlHeader=this.getEnvelopeDetails(remoteId,voucherKey,list,receiptDate,receiptNumber,tallyUserName,catagoryName,tallyCompanyName);
			String xmlFooter=this.getXmlFooter(tallyCompanyName);
		
			String postReceiptXml=new StringBuilder(xmlHeader).toString()+new StringBuilder(xmlInventories).toString()+new StringBuilder(xmlBank).toString()+new StringBuilder(xmlFooter).toString();
			return postReceiptXml;
		}

		public String getXmlFooter(String tallyCompanyName) {
			String xmlFooter="</VOUCHER>"
				    +"</TALLYMESSAGE>"
						+"<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
									     +"<COMPANY>"
									      +"<REMOTECMPINFO.LIST MERGE=\"Yes\">"
									       +"<REMOTECMPNAME>"+tallyCompanyName+"</REMOTECMPNAME>"
									       +"<REMOTECMPSTATE>Haryana</REMOTECMPSTATE>"
									      +"</REMOTECMPINFO.LIST>"
									     +"</COMPANY>"
						 +"</TALLYMESSAGE>"
				   +"</REQUESTDATA>"
				  +"</IMPORTDATA>"
				 +"</BODY>"
				+"</ENVELOPE>";
	    return xmlFooter;
			
		}

		public String getEnvelopeDetails(String remoteId, String voucherKey,
				List<Map<String, Object>> list, String receiptDate,
				String receiptNumber, String tallyUserName, String catagoryName,
				String tallyCompanyName) {
			
			String partyLedger="";
			String paymentFavouring="";
			List<List<String>> bankListReceipt = this.getReceiptDetails(list);
			for (int i = 0; i < bankListReceipt.size(); i++) {
				partyLedger=bankListReceipt.get(i).get(0).trim();
				paymentFavouring=bankListReceipt.get(i).get(2);
				
			}
			Random rand=new Random();
			int masterId=rand.nextInt(5)+59619;
			int alterId=rand.nextInt(5)+98567;
			Random rand2=new Random();
			int voucher=rand2.nextInt(5)+6092389;
			
			Random rand1=new Random();
			int voucherNo=rand1.nextInt(5)+3000;
			String xml ="<ENVELOPE>"
			 +"<HEADER>"
			  +"<TALLYREQUEST>Import Data</TALLYREQUEST>"
			  + "</HEADER> <BODY><IMPORTDATA> <REQUESTDESC> <REPORTNAME>Vouchers</REPORTNAME>"
			    +"<STATICVARIABLES><SVCURRENTCOMPANY>"+tallyCompanyName+"</SVCURRENTCOMPANY></STATICVARIABLES></REQUESTDESC>"
			   +"<REQUESTDATA>"
			    +"<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
			    +"<VOUCHER REMOTEID=\""+remoteId+"\""
			    		+ " VCHKEY=\""+voucherKey+"\"" 
			    		+ " VCHTYPE=\"Receipt\" "
			    		+ " ACTION=\"Create\""
			    		+ " OBJVIEW=\"Accounting Voucher View\">" 
			      +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"><OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS></OLDAUDITENTRYIDS.LIST>"
			      +"<DATE>"+receiptDate+"</DATE>"
			      +"<GUID>"+remoteId+"</GUID>"
			      +"<NARRATION>BEING PAYMENT RECEIVED AGAINST "+paymentFavouring+"</NARRATION>"
			      +"<VOUCHERTYPENAME>Receipt</VOUCHERTYPENAME>"
			      +"<VOUCHERNUMBER>"+voucherNo+"</VOUCHERNUMBER>"
			      +"<PARTYLEDGERNAME>"+partyLedger+"</PARTYLEDGERNAME>"
			      +"<CSTFORMISSUETYPE/>"
			      +"<CSTFORMRECVTYPE/>"
			      +"<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>"
			      +"<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"
			      +"<VCHGSTCLASS/>"
			      +"<ENTEREDBY>"+tallyUserName+"</ENTEREDBY>"
			      +"<DIFFACTUALQTY>No</DIFFACTUALQTY>"
			      +"<AUDITED>No</AUDITED>"
			      +"<FORJOBCOSTING>No</FORJOBCOSTING>"
			      +"<ISOPTIONAL>No</ISOPTIONAL>"
			      +"<EFFECTIVEDATE>"+receiptDate+"</EFFECTIVEDATE>"
			      +"<ISFORJOBWORKIN>No</ISFORJOBWORKIN>"
			      +"<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>"
			      +"<USEFORINTEREST>No</USEFORINTEREST>"
			      +"<USEFORGAINLOSS>No</USEFORGAINLOSS>"
			      +"<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>"
			      +"<USEFORCOMPOUND>No</USEFORCOMPOUND>"
			      +"<ALTERID>"+alterId+"</ALTERID>"
			      +"<EXCISEOPENING>No</EXCISEOPENING>"
			      +"<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>"
			      +"<ISCANCELLED>No</ISCANCELLED>"
			      +"<HASCASHFLOW>Yes</HASCASHFLOW>"
			      +"<ISPOSTDATED>No</ISPOSTDATED>"
			      +"<USETRACKINGNUMBER>No</USETRACKINGNUMBER>"
			      +"<ISINVOICE>No</ISINVOICE>"
			      +"<MFGJOURNAL>No</MFGJOURNAL>"
			      +"<HASDISCOUNTS>No</HASDISCOUNTS>"
			      +"<ASPAYSLIP>No</ASPAYSLIP>"
			      +"<ISCOSTCENTRE>No</ISCOSTCENTRE>"
			      +"<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>"
			      +"<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>"
			      +"<ISBLANKCHEQUE>No</ISBLANKCHEQUE>"
			      +"<ISVOID>No</ISVOID>"
			      +"<ISONHOLD>No</ISONHOLD>"
			      +"<ISDELETED>No</ISDELETED>"
			      +"<ASORIGINAL>No</ASORIGINAL>"
			      +"<VCHISFROMSYNC>No</VCHISFROMSYNC>"
			      +"<MASTERID>"+masterId+"</MASTERID>"
			      +"<VOUCHERKEY>"+voucher+"</VOUCHERKEY>";
			
			return xml;
		
		}

		public String getBankAllocationDetails(List<Map<String, Object>> list,
				String paymentMode, double checkAmount, String receiptDate,
				String catagoryName, String transactionType, String tansferMode,
				String bankLedger,String xmlInventories,String bankName) throws JAXBException, IOException {
			// TODO Auto-generated method stub
			String partyLedger="";
			double amount=0.00;
			double totalCheckAmount=0.0000;
			String paymentFavouring=null;
			String instrumentDate=null;
			String instrumentNumber=null;
			String tarnsferMode=null;
			
			List<List<String>> bankListReceipt = this.getReceiptDetails(list);
			for (int i = 0; i < bankListReceipt.size(); i++) {
				partyLedger=bankListReceipt.get(i).get(0).trim();
			
				paymentFavouring=bankListReceipt.get(i).get(2).trim();
				amount=Double.parseDouble(bankListReceipt.get(i).get(3).trim());
				instrumentDate=bankListReceipt.get(i).get(4).trim();
				instrumentNumber=bankListReceipt.get(i).get(5).trim();
			
			}
			 //CommonTally commonTally = new CommonTally();
		     Voucher voucher = this.getInventories("<VOUCHER> "+xmlInventories+"</VOUCHER>");
		     List<AllLedgerEntriesList> allLedgerEntriesLists =voucher.getAllLedgerEntriesLists();
			for (AllLedgerEntriesList allLedgerEntriesList : allLedgerEntriesLists) {
					String amount1=allLedgerEntriesList.getAmount();
					totalCheckAmount+=Double.parseDouble(amount1.trim());
				}
				
			checkAmount=totalCheckAmount;
			String billName = UUID.randomUUID().toString();
			if(transactionType.equalsIgnoreCase("Inter Bank Transfer")){
				tarnsferMode="<TRANSFERMODE>NEFT</TRANSFERMODE>";
			}else{
				tarnsferMode="";
			}
			String bankLed=  "<ALLLEDGERENTRIES.LIST>"
						   +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
						   		+ "<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
						   + "</OLDAUDITENTRYIDS.LIST>"
						   +"<LEDGERNAME>"+bankLedger+"</LEDGERNAME>"
						   +"<GSTCLASS/>"
					       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
					       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
					       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
					       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
					       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
					      +"<AMOUNT>-"+checkAmount+"</AMOUNT>"
					      /* +"<AMOUNT>-"+totalAmount+"</AMOUNT>"*/
					       +"<BANKALLOCATIONS.LIST>"
							        +"<DATE>"+receiptDate+"</DATE>"
							        +"<INSTRUMENTDATE>"+instrumentDate+"</INSTRUMENTDATE>"
							        +"<NAME>"+billName+"</NAME>"
							        +"<TRANSACTIONTYPE>"+transactionType+"</TRANSACTIONTYPE>"
							        +"<BANKNAME>"+bankName+"</BANKNAME>"
							        +"<BANKBRANCHNAME></BANKBRANCHNAME>"
									+"<PAYMENTFAVOURING>"+partyLedger+"</PAYMENTFAVOURING>"
									+new StringBuilder(tarnsferMode).toString()
							        +"<INSTRUMENTNUMBER>"+instrumentNumber+"</INSTRUMENTNUMBER>"
							        +"<UNIQUEREFERENCENUMBER>"+instrumentNumber+"</UNIQUEREFERENCENUMBER>"
							        +"<STATUS>No</STATUS>"
							        +"<PAYMENTMODE>Transacted</PAYMENTMODE>"
							        +"<BANKPARTYNAME>"+partyLedger+"</BANKPARTYNAME>"
							        +"<ISCONNECTEDPAYMENT>No</ISCONNECTEDPAYMENT>"
							        +"<ISSPLIT>No</ISSPLIT>"
							        +"<ISCONTRACTUSED>No</ISCONTRACTUSED>"
							        +"<CHEQUEPRINTED> 1</CHEQUEPRINTED>"
							        +"<AMOUNT>-"+checkAmount+"</AMOUNT>"
							        /*+"<AMOUNT>-"+totalAmount+"</AMOUNT>"*/
						   +" </BANKALLOCATIONS.LIST>"
				     +"</ALLLEDGERENTRIES.LIST>";
			return bankLed;
		
		}

		public Voucher getInventories(String xml)throws JAXBException, IOException{
			System.err.println("xml ############# "+xml);
			JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
			Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
			InputSource inputSource = new InputSource(new ByteArrayInputStream(xml.getBytes()));
			inputSource.setEncoding("UTF-8");
			Voucher voucher = (Voucher) jaxUnmarshaller.unmarshal(inputSource);
			return voucher;
			
		}

		public List<String> getBillAllocationDetails(
				List<Map<String, Object>> list, String catagoryName,double checkAmount) {
			List<List<String>> listReceipt = this.getReceiptDetails(list);
			String billAllocationListXml="";
			
			String allledgerEntriesList="";
			double amount=0.0000;
			List<String> billLisrt=new ArrayList<String>();
		
			String ledgerName="";
			for (int i = 0; i < listReceipt.size(); i++) {
				ledgerName=listReceipt.get(i).get(0);
				System.out.println("========ledger name==="+ledgerName);
				//totalAmount=totalAmount+Double.parseDouble(listReceipt.get(i).get(3).trim());
		
			}
		
			
			
			billAllocationListXml="<BILLALLOCATIONS.LIST>"
									+"<NAME>bill</NAME>"
									+"<BILLTYPE>Agst Ref</BILLTYPE>"
									+"<TDSDEDUCTEEISSPECIALRATE>No</TDSDEDUCTEEISSPECIALRATE>"
									+"<AMOUNT>"+checkAmount+"</AMOUNT>"
								+"</BILLALLOCATIONS.LIST>";
			
			allledgerEntriesList="<ALLLEDGERENTRIES.LIST>"
								   +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"><OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS></OLDAUDITENTRYIDS.LIST>"
							       +"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
							       +"<GSTCLASS/>"
							       +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
							       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
							       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
							       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
							       +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
							       +"<AMOUNT>"+checkAmount+"</AMOUNT>"
							       +new StringBuilder(billAllocationListXml).toString()
							   +"</ALLLEDGERENTRIES.LIST>";
		
		billLisrt.add(allledgerEntriesList);
			
			return billLisrt;
			
		}


		public List<List<String>> getReceiptDetails(List<Map<String, Object>> list) {
			
			List<List<String>> listValue=new ArrayList<List<String>>();
			for (Map<String, Object> map : list) {
				String key=null;
				Object value=null;
				String ledgerName=null;		
				String paymentFavouring=null;
				double checkAmountPaid=0.0000;
				String invoiceNumber=null;
				String instrumentDate=null;
				String instrumentNumber=null;
				List<String> listString=new ArrayList<String>();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					key = entry.getKey();
					value = (Object) entry.getValue();
					if(key.equalsIgnoreCase("accountNo") && (String)value != null){
						ledgerName=(String)value;
						ledgerName=StringEscapeUtils.escapeXml(ledgerName);
					}if(key.equalsIgnoreCase("againstBillRefNo") && (String)value != null){
						invoiceNumber=(String)value;
						invoiceNumber=StringEscapeUtils.escapeXml(invoiceNumber);
					}if(key.equalsIgnoreCase("billSegmentCircle") && (String)value != null){
						paymentFavouring=(String)value;
						paymentFavouring=StringEscapeUtils.escapeXml(paymentFavouring);
					}
					if(key.equalsIgnoreCase("billAmount") && (Double) value != 0.0){
					  Double checkAmount=(Double) value;
					  checkAmountPaid=Double.valueOf(checkAmount);
					}if(key.equalsIgnoreCase("instrumentDate") && (String)value!=null){
						instrumentDate=(String)value;
					}if(key.equalsIgnoreCase("instrumentNumber") && (String)value!=null){
						instrumentNumber=StringEscapeUtils.escapeXml((String)value);
					}
					
				}
				if(ledgerName != null){
					listString.add(ledgerName);
					listString.add(invoiceNumber);
					listString.add(paymentFavouring);
					listString.add(String.valueOf(checkAmountPaid));
					listString.add(instrumentDate);
					listString.add(instrumentNumber);
					listValue.add(listString);
					
				}
				
			}
			
			return listValue;
		}

		public String createCashReceipt(String remoteId, String voucherKey,List<Map<String, Object>> list,
				String receiptDate, String voucherType, String paymentMode,
				int paymentId, String bankName,String transactionType,
				String transactionType2, String branchName, String tansferMode,String  catagoryName, String state,
				String receiptNumber,double checkAmount,String tallyCompanyName,String tallyPortNumber,
				String tallyIpAddress,String  tallyUserName, String tallyPassword,String bankLedger) throws Exception{
			
			
			paymentMode=StringEscapeUtils.escapeXml(paymentMode);
			bankName=StringEscapeUtils.escapeXml(bankName);
			branchName=StringEscapeUtils.escapeXml(branchName);
			receiptNumber=StringEscapeUtils.escapeXml(receiptNumber);
			tallyUserName=StringEscapeUtils.escapeXml(tallyUserName);
			transactionType=StringEscapeUtils.escapeXml(transactionType);
			bankLedger=StringEscapeUtils.escapeXml(bankLedger);
			String xmlInventories=null;
			
			StringBuilder sb = new StringBuilder();
			List<String> list1=this.getBillAllocationDetails(list,catagoryName,checkAmount);
			for (int i = 0; i < list1.size(); i++) {
				sb.append(list1.get(i));
				xmlInventories = sb.toString();
			}
			String  xmlBank=this.getBankAllocationDetails1(list,paymentMode,checkAmount,receiptDate,catagoryName,transactionType,tansferMode,bankLedger);
			String xmlHeader=this.getEnvelopeDetails1(remoteId,voucherKey,list,receiptDate,receiptNumber,tallyUserName,catagoryName,tallyCompanyName,bankLedger);
			String xmlFooter=this.getXmlFooter(tallyCompanyName);
			String postReceiptXml=new StringBuilder(xmlHeader).toString()+new StringBuilder(xmlInventories).toString()+new StringBuilder(xmlBank).toString()+new StringBuilder(xmlFooter).toString();

		return postReceiptXml;
		
		}

	

		public String getEnvelopeDetails1(String remoteId, String voucherKey,
				List<Map<String, Object>> list, String receiptDate,
				String receiptNumber, String tallyUserName, String catagoryName,
				String tallyCompanyName,String bankLedger) {
			
			String partyLedger="";
			String paymentFavouring="";
			List<List<String>> bankListReceipt = this.getReceiptDetails(list);
			for (int i = 0; i < bankListReceipt.size(); i++) {
				partyLedger=bankListReceipt.get(i).get(0).trim();
				paymentFavouring=bankListReceipt.get(i).get(2);
				
			}
			
			Random rand=new Random();
			int masterId=rand.nextInt(5)+38619;
			int alterId=rand.nextInt(5)+78567;
			int voucher=rand.nextInt()+692389;
			String xml ="<ENVELOPE>"
			 +"<HEADER>"
			  +"<TALLYREQUEST>Import Data</TALLYREQUEST>"
			  + "</HEADER> <BODY><IMPORTDATA> <REQUESTDESC> <REPORTNAME>Vouchers</REPORTNAME>"
			    +"<STATICVARIABLES><SVCURRENTCOMPANY>"+tallyCompanyName+"</SVCURRENTCOMPANY></STATICVARIABLES></REQUESTDESC>"
			   +"<REQUESTDATA>"
			    +"<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
			    +"<VOUCHER REMOTEID=\""+remoteId+"\""
			    		+ " VCHKEY=\""+voucherKey+"\"" 
			    		+ "VCHTYPE=\"Contra\" "
			    		+ "ACTION=\"Create\""
			    		+ "OBJVIEW=\"Accounting Voucher View\">" 
			      +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"><OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS></OLDAUDITENTRYIDS.LIST>"
			      +"<DATE>"+receiptDate+"</DATE>"
			      +"<GUID>"+remoteId+"</GUID>"
			      +"<NARRATION>BEING PAYMENT RECEIVED AGAINST "+paymentFavouring+"</NARRATION>"
			      
			      +"<VOUCHERTYPENAME>Contra</VOUCHERTYPENAME>"
			      +"<VOUCHERNUMBER>"+receiptNumber+"</VOUCHERNUMBER>"
			      +"<PARTYLEDGERNAME>"+bankLedger+"</PARTYLEDGERNAME>"
			      +"<CSTFORMISSUETYPE/>"
			      +"<CSTFORMRECVTYPE/>"
			      +"<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>"
			      +"<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"
			      +"<VCHGSTCLASS/>"
			     +" <ENTEREDBY>"+tallyUserName+"</ENTEREDBY>"
			      +"<DIFFACTUALQTY>No</DIFFACTUALQTY>"
			      +"<AUDITED>No</AUDITED>"
			      +"<FORJOBCOSTING>No</FORJOBCOSTING>"
			      +"<ISOPTIONAL>No</ISOPTIONAL>"
			      +"<EFFECTIVEDATE>"+receiptDate+"</EFFECTIVEDATE>"
			      +"<ISFORJOBWORKIN>No</ISFORJOBWORKIN>"
			      + "<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>"
			      + "<USEFORINTEREST>No</USEFORINTEREST>"
			      + "<USEFORGAINLOSS>No</USEFORGAINLOSS>"
			      +"<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>"
			      +"<USEFORCOMPOUND>No</USEFORCOMPOUND>"
			      +"<ALTERID>"+alterId+"</ALTERID>"
			      +"<EXCISEOPENING>No</EXCISEOPENING>"
			      +"<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>"
			      +"<ISCANCELLED>No</ISCANCELLED>"
			      +"<HASCASHFLOW>Yes</HASCASHFLOW>"
			      +"<ISPOSTDATED>No</ISPOSTDATED>"
			      +"<USETRACKINGNUMBER>No</USETRACKINGNUMBER>"
			      +"<ISINVOICE>No</ISINVOICE>"
			      +"<MFGJOURNAL>No</MFGJOURNAL>"
			      +"<HASDISCOUNTS>No</HASDISCOUNTS>"
			      +"<ASPAYSLIP>No</ASPAYSLIP>"
			      +"<ISCOSTCENTRE>Yes</ISCOSTCENTRE>"
			      +"<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>"
			      +"<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>"
			      +"<ISBLANKCHEQUE>No</ISBLANKCHEQUE>"
			      +"<ISVOID>No</ISVOID>"
			      +"<ISONHOLD>No</ISONHOLD>"
			      +"<ISDELETED>No</ISDELETED>"
			      +"<ASORIGINAL>No</ASORIGINAL>"
			      +"<VCHISFROMSYNC>No</VCHISFROMSYNC>"
			      +"<MASTERID>"+masterId+"</MASTERID>"
			      +"<VOUCHERKEY>"+voucher+"</VOUCHERKEY>";
			
			return xml;
		
		}

		public String getBankAllocationDetails1(List<Map<String, Object>> list,
				String paymentMode, double checkAmount, String receiptDate,
				String catagoryName, String transactionType, String tansferMode,String bankLedger) {
			
			String partyLedger="";
			double  amount=0.0000;
			String paymentFavouring=null;
			String instrumentDate=null;
			String instrumentNumber=null;
			List<List<String>> bankListReceipt = this.getReceiptDetails(list);
			for (int i = 0; i < bankListReceipt.size(); i++) {
				partyLedger=bankListReceipt.get(i).get(0).trim();
			
				paymentFavouring=bankListReceipt.get(i).get(2).trim();
				amount=Double.parseDouble(bankListReceipt.get(i).get(3).trim());
				instrumentDate=bankListReceipt.get(i).get(4).trim();
				instrumentNumber=bankListReceipt.get(i).get(5).trim();
			}
			String bankLed=  "<ALLLEDGERENTRIES.LIST>"
						   +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"><OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
						   +"<LEDGERNAME>"+bankLedger+"</LEDGERNAME>"
						   +"<GSTCLASS/>"
					       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
					       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
					       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
					       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
					       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
					       +"<AMOUNT>-"+checkAmount+"</AMOUNT>"
					       /*+"<AMOUNT>-"+totalAmount+"</AMOUNT>"*/
					       +"<BANKALLOCATIONS.LIST>"
					        +"<DATE>"+instrumentDate+"</DATE>"
					        +"<INSTRUMENTDATE>"+instrumentDate+"</INSTRUMENTDATE>"
					        +"<NAME>"+instrumentNumber+"</NAME>"
					        +"<TRANSACTIONTYPE>"+transactionType+"</TRANSACTIONTYPE>"
					        +"<CHEQUECROSSCOMMENT>A/c Payee</CHEQUECROSSCOMMENT>"
					        +"<INSTRUMENTNUMBER>"+instrumentNumber+"</INSTRUMENTNUMBER>"
					        +"<UNIQUEREFERENCENUMBER>"+instrumentNumber+"</UNIQUEREFERENCENUMBER>"
					        +"<STATUS>No</STATUS>"
					        +"<PAYMENTMODE>Transacted</PAYMENTMODE>"
					        +"<ISCONNECTEDPAYMENT>No</ISCONNECTEDPAYMENT>"
					        +"<ISSPLIT>No</ISSPLIT>"
					        +"<ISCONTRACTUSED>No</ISCONTRACTUSED>"
					        +"<AMOUNT>-"+checkAmount+"</AMOUNT>"
					        /*+"<AMOUNT>-"+totalAmount+"</AMOUNT>"*/
					      +" </BANKALLOCATIONS.LIST>"
				     +"</ALLLEDGERENTRIES.LIST>";
			return bankLed;
		
		}


	
}
