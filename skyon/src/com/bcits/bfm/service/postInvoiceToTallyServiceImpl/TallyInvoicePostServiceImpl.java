package com.bcits.bfm.service.postInvoiceToTallyServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyInvoicePostService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.tally.billinginvoices.BillingInvoices;
import com.tally.billinginvoices.BillingInvoicesForCAM;
import com.tally.billreceipt.BillReceiptPostSkyon;
import com.tally.billreceipt.BillReceiptTally;
import com.tally.billreceipt.CashReceiptTally;

@Service
public class TallyInvoicePostServiceImpl implements TallyInvoicePostService {

	@Autowired
	TariffCalculationService tariffCalculationService;
	
	/*@Override
	public String reponsePostInvoiceToTally(List<Map<String, Object>> mapsList,
			String billNumber, String billDate,String ledgerName,int billId,Date basicDateOfInvoice,String serviceType,String accountNo,String arrearsLedge) throws Exception {
		
		
		
		String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
		String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
		String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
		String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
		String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
		List<Map<String, Object>>  propertyMap=tariffCalculationService.getTallyDetailData(billId);
		
		String autoIncrementString = this.getNewString(billId);
		String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
		String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";

	
		BillingInvoices billInvoice=new BillingInvoices();
	
		String str=billInvoice.createBillInvoices(mapsList,remoteId,voucherKey,billId,
				billDate,ledgerName,billNumber,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword,
				tallyCompanyName,basicDateOfInvoice,propertyMap,serviceType,accountNo,arrearsLedge);
		
		
		return str;
	}
*/
	@Override
	public String reponsePostReceiptToTally(List<Map<String, Object>> mapsList,Date receiptDate, String receiptNumber,
												String bankAcNumberLedger,String bankName,String paymentMode,
												int paymentId,double checkAmount,Date cashReceiptDate) throws Exception {
		
		
		String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
		String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
		String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
		String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
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
		sb.append(paymentId);
		String autoId = sb.toString();
		if(bankName !=null){
			branchName = bankName;
		}
		String voucherType = "Receipt";
		String tansferMode = null;
		String remoteId = "6dd-00000034" + autoId;
		String voucherKey = "a31f:00000028" + autoId;
		BillReceiptPostSkyon receiptTally = new BillReceiptPostSkyon();
		CashReceiptTally cashReceiptTally=new CashReceiptTally();
	
		String catagoryName="Delhi Elctricity Departments";
		String state="Karnataka";
		String transactionType2=null;
		String receiptResponse=null;
		
	
		if(paymentMode.equalsIgnoreCase("Cheque")){
			String transactionType ="Cheque/DD";
			if (transactionType.equalsIgnoreCase("Cheque/DD")) {
				tansferMode = "Cheque/DD";
			}
			receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					paymentId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
					tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
			
		}
		if(paymentMode.equalsIgnoreCase("AdvancePayment")){
			String transactionType ="AdvancePayment";
			if (transactionType.equalsIgnoreCase("AdvancePayment")) {
				tansferMode = "AdvancePayment";
			}
			receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					paymentId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
					tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
			
		}
		
		if(paymentMode.equalsIgnoreCase("RTGS/NEFT")){
			String transactionType ="Inter Bank Transfer";
			if (transactionType.equalsIgnoreCase("Inter Bank Transfer")) {
				bankName="";
				branchName="";
				tansferMode = "NEFT";
			}
			receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
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
			
			System.out.println("mapsList = "+mapsList);
			System.out.println("cashReptDate : "+cashReptDate);
			System.out.println("paymentMode : "+paymentMode);
			System.out.println("paymentId = "+paymentId+" , transactionType : "+transactionType+",  branchName="+branchName);
			System.out.println("tansferMode = "+tansferMode+" ,catagoryName:"+catagoryName+" , state : "+state+" ,receiptNumber "+receiptNumber);
			System.out.println("checkAmount = "+checkAmount+" , bankLedger = "+bankLedger);
			receiptResponse=cashReceiptTally.createCashReceipt(remoteId, voucherKey,mapsList,
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
			receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					paymentId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,checkAmount,tallyCompanyName, tallyPortNumber,
					tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
			
		}if(paymentMode.equalsIgnoreCase("NB") || paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("PPI") || paymentMode.equalsIgnoreCase("Online") || paymentMode.equalsIgnoreCase("PaytmApps")){
			String transactionType = "Others";
			if (transactionType.equalsIgnoreCase("Others")) {
				tansferMode = "Others";
			}
			
			System.out.println("mapsList = "+mapsList);
			System.out.println("cashReptDate : "+cashReptDate);
			System.out.println("paymentMode : "+paymentMode);
			System.out.println("paymentId = "+paymentId+" , transactionType : "+transactionType+",  branchName="+branchName);
			System.out.println("tansferMode = "+tansferMode+" ,catagoryName:"+catagoryName+" , state : "+state+" ,receiptNumber "+receiptNumber);
			System.out.println("checkAmount = "+checkAmount+" , bankLedger = "+bankLedger);
			double rechargeAmt=0;//12 as admin charges removed 
			
			if(paymentMode.equalsIgnoreCase("Online")){
				rechargeAmt=checkAmount;
			}else{
				rechargeAmt=checkAmount+12;
			}
			
			receiptResponse =receiptTally.createBilllReceipt(remoteId, voucherKey,mapsList,
					recptDate, voucherType, paymentMode,
					paymentId, bankName, transactionType,
					transactionType2, branchName, tansferMode, catagoryName, state,
					receiptNumber,rechargeAmt,tallyCompanyName, tallyPortNumber,
					tallyIpAddress, tallyUserName, tallyPassword,bankLedger);
			
		}
		return receiptResponse;	
	}
	
  private String getNewString(int invoiceId) {
		
		StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(invoiceId);
		String autoIncrementString = sb.toString();
		
		 return autoIncrementString;
	}

public String responsePostInvoiceForCAM(List<Map<String, Object>> mapsList,
		String billNumber, String billDate, String salesLedger, int billId,
		Date basicDateOfInvoice, String serviceType, String accountNo,
		String arrearsLedge, List<Map<String,Object>> lineItemList,String propertyNumber) {
	
	String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
	String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
	String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
	String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
	String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
	List<Map<String, Object>>  propertyMap=tariffCalculationService.getTallyDetailData(billId);
	
	String autoIncrementString = this.getNewString(billId);
	String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
	String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";


	BillingInvoicesForCAM billInvoiceforCam=new BillingInvoicesForCAM();
	String str=null;

	
	

	
	   


	try {
		 str=billInvoiceforCam.createBillInvoicesForCAM(mapsList,remoteId,voucherKey,billId,
				billDate,salesLedger,billNumber,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword,
				tallyCompanyName,basicDateOfInvoice,propertyMap,serviceType,accountNo,arrearsLedge,lineItemList,propertyNumber);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	return str;
}

}
