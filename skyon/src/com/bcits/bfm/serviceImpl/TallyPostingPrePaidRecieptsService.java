package com.bcits.bfm.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.Envelope;
import com.envelope.body.data.collection.Voucher;
import com.envelope.body.data.collection.ledgerlist.AllLedgerEntriesList;
import com.main.tally.CommonTally;

@Service
public class TallyPostingPrePaidRecieptsService {

	Logger logger=LoggerFactory.getLogger(TallyPostingPrePaidRecieptsService.class);

	/*public String generateXML(List<Map<String, Object>> mapsList, int ppRechargeId) {
		logger.info("PrepaidRecharge ID "+ppRechargeId);
		String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
		String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
		String autoIncrementString = this.getNewString(ppRechargeId);
		String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
		String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";
		Random rand=new Random();
		int masterId=rand.nextInt(5)+59619;
		int alterId=rand.nextInt(5)+98567;
		Random rand2=new Random();
		int voucher=rand2.nextInt(5)+6092389;
		
		Random rand1=new Random();
		int voucherNo=rand1.nextInt(5)+3000;
		
		String partyLedger="";
		Double adjustmentAmount=0.0;
		String adjustmentDate="";
		String ledgerHead="";
		for (Map<String, Object> map : mapsList) {
		   
			partyLedger=(String) map.get("propertyNumber");
			logger.info("property Number "+partyLedger);
			adjustmentAmount=(Double) map.get("rechargedAmount");
			Date adjDate=(Date) map.get("adjustmentDate");
			adjustmentDate=new SimpleDateFormat("YYYYMMdd").format(adjDate);
			ledgerHead=(String) map.get("adjustmentType");
		
		}
		
		String adjustmentxml="";
		
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
				      +"<DATE>"+adjustmentDate+"</DATE>"
				      +"<GUID>"+remoteId+"</GUID>"
				      +"<NARRATION>BEING PAYMENT RECEIVED AGAINST ELECTRICITY BILL</NARRATION>"
				      +"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME>"
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
				      +"<EFFECTIVEDATE>"+adjustmentDate+"</EFFECTIVEDATE>"
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
				      
				      if(adjustmentAmount>0)
				      {
				      
				       adjustmentxml="<ALLLEDGERENTRIES.LIST>"
				      +"<LEDGERNAME>"+ledgerHead+"</LEDGERNAME>"
				      +"<GSTCLASS/>"
				      +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
				      +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
				      +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
				      +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
				      +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
				      +"<AMOUNT>-"+adjustmentAmount+"</AMOUNT>"
				      +"</ALLLEDGERENTRIES.LIST>"
				      
				      +"<ALLLEDGERENTRIES.LIST>"
				      +"<LEDGERNAME>"+partyLedger+"</LEDGERNAME>"
				      +"<GSTCLASS/>"
				      +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
				      +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
				      +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
				      +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
				      +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
				      +"<AMOUNT>"+adjustmentAmount+"</AMOUNT>"
				      +"</ALLLEDGERENTRIES.LIST>";
				      }
				      else if(adjustmentAmount<0)
				      {
				    	  adjustmentxml="<ALLLEDGERENTRIES.LIST>"
				    	  +"<LEDGERNAME>"+partyLedger+"</LEDGERNAME>"
				          +"<GSTCLASS/>"
				          +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
				          +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
				          +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
				          +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
				          +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
				          +"<AMOUNT>"+adjustmentAmount+"</AMOUNT>"
				          +"</ALLLEDGERENTRIES.LIST>"
				          +"<ALLLEDGERENTRIES.LIST>"
				          
				          +"<LEDGERNAME>"+ledgerHead+"</LEDGERNAME>"
				          +"<GSTCLASS/>"
				          +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
				          +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
				          +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
				          +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
				          +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
				          +"<AMOUNT>"+-adjustmentAmount+"</AMOUNT>"
				          +"</ALLLEDGERENTRIES.LIST>";
				    	  
				      
				      }
				      
				      
				   String footer=   "</VOUCHER>"
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
		
		
		
		//CommonTally commonTally=new CommonTally();
		String completexml=xml+adjustmentxml+footer;
		return completexml;
	}*/
	private String getNewString(int invoiceId) {
		
		StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(invoiceId);
		String autoIncrementString = sb.toString();
		
		 return autoIncrementString;
	}

	
	public String generateXML(List<Map<String, Object>> mapsList, String receiptNumber, Date cashReceiptDate,
			Date receiptDate, String paymentMode, String bankAcNumberLedger, double checkAmount, String bankName,
			int ppRechargeId) throws JAXBException, IOException {
		String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
		String bankLedger=ResourceBundle.getBundle("application").getString("bankLedger");
		
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
		sb.append(ppRechargeId);
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
		
		System.out.println("Payement mode is========>"+paymentMode);//
		if(paymentMode.equalsIgnoreCase("Cheque")){
			String transactionType ="Cheque/DD";
			if (transactionType.equalsIgnoreCase("Cheque/DD")) {
				tansferMode = "Cheque/DD";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, bankLedger);
			
		}if(paymentMode.equalsIgnoreCase("RTGS/NEFT")){
			String transactionType ="Inter Bank Transfer";
			if (transactionType.equalsIgnoreCase("Inter Bank Transfer")) {
				tansferMode = "NEFT";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName,bankLedger);
			
		}
		if(paymentMode.equalsIgnoreCase("AdvancePayment")){
			String transactionType ="AdvancePayment";
			if (transactionType.equalsIgnoreCase("AdvancePayment")) {
				tansferMode = "AdvancePayment";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, bankLedger);
			
		}
		
		/*if(paymentMode.equalsIgnoreCase("Cash")){
			String transactionType = "Cash";
			if (transactionType.equalsIgnoreCase("Cash")) {
				tansferMode = "Cash";
			}
			receiptResponse=createCashReceipt(remoteId, voucherKey,mapsList,
					cashReptDate, voucherType, paymentMode,
					paymentId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
					tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
			
		}*/if(paymentMode.equalsIgnoreCase("DD")){
			String transactionType = "Cheque/DD";
			if (transactionType.equalsIgnoreCase("Cheque/DD")) {
				tansferMode = "Cheque/DD";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName,bankLedger);
			
		}if(paymentMode.equalsIgnoreCase("NB") || paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("PPI") || paymentMode.equalsIgnoreCase("Online")){
			String transactionType = "Others";
			if (transactionType.equalsIgnoreCase("Others")) {
				bankName="";
				branchName="";
				tansferMode = "Others";
			}
			paymentMode="Online";
			double checkamount1=checkAmount+12;
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkamount1,tallyCompanyName,bankLedger);
			
		}
	return receiptResponse;
		
	}
	/*======================================================XMl Generation for Cam Payments====================================*/
	public String generateXMLforCamPayments(List<Map<String, Object>> mapsList, String receiptNumber, Date cashReceiptDate,
			Date receiptDate, String paymentMode, String bankAcNumberLedger, double checkAmount, String bankName,
			int ppRechargeId) throws JAXBException, IOException {
		String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
		String bankLedger=ResourceBundle.getBundle("application").getString("bankLedger");
		
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
		sb.append(ppRechargeId);
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
		
		System.out.println("Payement mode is========>"+paymentMode);//
		if(paymentMode.equalsIgnoreCase("Cheque")){
			String transactionType ="Cheque/DD";
			if (transactionType.equalsIgnoreCase("Cheque/DD")) {
				tansferMode = "Cheque/DD";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, bankLedger);
			
		}if(paymentMode.equalsIgnoreCase("RTGS/NEFT")){
			String transactionType ="Inter Bank Transfer";
			if (transactionType.equalsIgnoreCase("Inter Bank Transfer")) {
				tansferMode = "NEFT";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName,bankLedger);
			
		}
		if(paymentMode.equalsIgnoreCase("AdvancePayment")){
			String transactionType ="AdvancePayment";
			if (transactionType.equalsIgnoreCase("AdvancePayment")) {
				tansferMode = "AdvancePayment";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, bankLedger);
			
		}
		if(paymentMode.equalsIgnoreCase("DD")){
			String transactionType = "Cheque/DD";
			if (transactionType.equalsIgnoreCase("Cheque/DD")) {
				tansferMode = "Cheque/DD";
			}
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName,bankLedger);
			
		}
		if(paymentMode.equalsIgnoreCase("NB") || paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("PPI") || paymentMode.equalsIgnoreCase("Online")){
			String transactionType = "Others";
			if (transactionType.equalsIgnoreCase("Others")) {
				bankName="";
				branchName="";
				tansferMode = "Others";
			}
			paymentMode="Online";
			double checkamount1=checkAmount+12;
			receiptResponse =this.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					ppRechargeId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkamount1,tallyCompanyName,bankLedger);
			
		}
	return receiptResponse;
		
	}
	/*======================================================================================================================*/

	private String createBilllReceipt(String remoteId, String voucherKey, List<Map<String, Object>> list,
			String receiptDate, String voucherType, String paymentMode, int ppRechargeId, String bankName,
			String transactionType, String transactionType2, String branchName, String tansferMode, String catagoryName,
			String state, String receiptNumber, double checkAmount, String tallyCompanyName, String bankLedger) throws JAXBException, IOException {
		String xmlInventories="";
		paymentMode=StringEscapeUtils.escapeXml(paymentMode);
		bankName=StringEscapeUtils.escapeXml(bankName);
		branchName=StringEscapeUtils.escapeXml(branchName);
		receiptNumber=StringEscapeUtils.escapeXml(receiptNumber);
		transactionType=StringEscapeUtils.escapeXml(transactionType);
		bankLedger=StringEscapeUtils.escapeXml(bankLedger);
		String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
		StringBuilder sb = new StringBuilder();
		List<String> list1=this.getBillAllocationDetails(list,catagoryName,checkAmount);
		for (int i = 0; i < list1.size(); i++) {
			sb.append(list1.get(i));
			xmlInventories = sb.toString();
		}
		System.err.println("xmlInventories "+xmlInventories);
		String  xmlBank=this.getBankAllocationDetails(list,paymentMode,checkAmount,receiptDate,catagoryName,transactionType,tansferMode,bankLedger,xmlInventories,bankName);
		String xmlHeader=this.getEnvelopeDetails(remoteId,voucherKey,list,receiptDate,receiptNumber,catagoryName,tallyCompanyName,tallyUserName);
		String xmlFooter=this.getXmlFooter(tallyCompanyName);
	
		String postReceiptXml=new StringBuilder(xmlHeader).toString()+new StringBuilder(xmlInventories).toString()+new StringBuilder(xmlBank).toString()+new StringBuilder(xmlFooter).toString();
		//FileUtils.writeStringToFile(new File("C:/Users/user51/Desktop/Tally Backup/Tally Xml/TallyReponse.xml"), postReceiptXml);
		//String receiptXml=FileUtils.readFileToString(new File("C:/Users/user60/Desktop/DoNotDelete/TallyReponse.xml"));
		return postReceiptXml;
			
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
		System.err.println("billLisrt "+billLisrt.size());
		return billLisrt;
		
	}
	
	public String getBankAllocationDetails(List<Map<String, Object>> list,
			String paymentMode, double checkAmount, String receiptDate,
			String catagoryName, String transactionType, String tansferMode,
			String bankLedger,String xmlInventories,String bankName) throws JAXBException, IOException {
		
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
		
			paymentFavouring=bankListReceipt.get(i).get(1).trim();
			amount=Double.parseDouble(bankListReceipt.get(i).get(2).trim());
			instrumentDate=bankListReceipt.get(i).get(3).trim();
			instrumentNumber=bankListReceipt.get(i).get(4).trim();
		
		}
		 
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
				      // +"<AMOUNT>-"+totalAmount+"</AMOUNT>"
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
						       // +"<AMOUNT>-"+totalAmount+"</AMOUNT>"
					   +" </BANKALLOCATIONS.LIST>"
			     +"</ALLLEDGERENTRIES.LIST>";
		return bankLed;
	
	}
	
	public String getEnvelopeDetails(String remoteId, String voucherKey,
			List<Map<String, Object>> list, String receiptDate,
			String receiptNumber,String catagoryName,
			String tallyCompanyName,String tallyUserName) {
		
		String partyLedger="";
		String paymentFavouring="";
		List<List<String>> bankListReceipt = this.getReceiptDetails(list);
		for (int i = 0; i < bankListReceipt.size(); i++) {
			partyLedger=bankListReceipt.get(i).get(0).trim();
			paymentFavouring=bankListReceipt.get(i).get(1);
			
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
public List<List<String>> getReceiptDetails(List<Map<String, Object>> list) {
		
		List<List<String>> listValue=new ArrayList<List<String>>();
		for (Map<String, Object> map : list) {
			String key=null;
			Object value=null;
			String ledgerName=null;		
			String paymentFavouring=null;
			double checkAmountPaid=0.0000;
			//String invoiceNumber=null;
			String instrumentDate=null;
			String instrumentNumber=null;
			List<String> listString=new ArrayList<String>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				key = entry.getKey();
				value = (Object) entry.getValue();
				if(key.equalsIgnoreCase("propertyNo") && (String)value != null){
					ledgerName=(String)value;
					ledgerName=StringEscapeUtils.escapeXml(ledgerName);
				}if(key.equalsIgnoreCase("billSegmentCircle") && (String)value != null){
					paymentFavouring=(String)value;
					paymentFavouring=StringEscapeUtils.escapeXml(paymentFavouring);
				}
				if(key.equalsIgnoreCase("billAmount") && (Double) value != 0.0){
				  Double checkAmount=(Double) value;
				  checkAmountPaid=Double.valueOf(checkAmount);
				}if(key.equalsIgnoreCase("instrumentDate") && (String)value!=null){
					instrumentDate=(String)value;
				}if(key.equalsIgnoreCase("instrumentNo") && (String)value!=null){
					instrumentNumber=StringEscapeUtils.escapeXml((String)value);
				}
				
			}
			if(ledgerName != null){
				listString.add(ledgerName);
				listString.add(paymentFavouring);
				listString.add(String.valueOf(checkAmountPaid));
				listString.add(instrumentDate);
				listString.add(instrumentNumber);
				listValue.add(listString);
				
			}
			
		}
		
		return listValue;
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
	
}
