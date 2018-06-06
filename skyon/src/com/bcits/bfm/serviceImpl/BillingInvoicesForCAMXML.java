package com.bcits.bfm.serviceImpl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.restlet.Response;
import org.restlet.representation.Representation;

import com.ResponseTally;
import com.main.tally.CommonTally;

public class BillingInvoicesForCAMXML {
	
	
	double totalamount=0.0;
	
	public String createBillInvoicesForCAM1(List<Map<String, Object>> itemsList,String remoteId,String voucherKey,int billId,
			String billDate,String salesLedger,String billNumber,String tallyCompanyName,Date basicDateOfInvoice,List<Map<String ,Object>> propertyMap,String serviceType,String partyLedger,String arrearsLedge,List<Map<String,Object>> lineItemList,String propertyNumber) throws Exception
	{
		
		String x=null;
		String XMLSales=null;
		
		List<String> xml=getAllLedgerEntries(lineItemList);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<xml.size();i++)
		{
			sb.append(xml.get(i));
			x = sb.toString();
		}
		
		DecimalFormat df2 = new DecimalFormat("###.####");
		double roundOff=Double.valueOf(df2.format(Math.round(totalamount)));
		double roundOffAmount=Double.valueOf(df2.format((totalamount-roundOff)));
		String roundoff="";
		if(roundOff > totalamount ){
			System.out.println("inside first one");
			//roundOffAmount=-(roundOffAmount);
        	double number1 =-(roundOffAmount);
        	String num1=String.format("%.4f", number1);
        	//double rounndOff1=Double.parseDouble(num1);
        	
        	System.out.println("inside first one num1"+num1);
			roundoff="<LEDGERENTRIES.LIST>"
		             +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
		               +" <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
			         +"</OLDAUDITENTRYIDS.LIST>"
		             +"<LEDGERNAME>Round off</LEDGERNAME>"
		             +"<GSTCLASS/>"
		             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
		             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
		             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
		             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
		             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
		            +" <AMOUNT>"+num1+"</AMOUNT>"
	            +"</LEDGERENTRIES.LIST>";
		}else if(roundOff < totalamount){
			//roundOffAmount=-(roundOffAmount);
			System.out.println("=========inside second one"+roundOffAmount);
			roundoff="<LEDGERENTRIES.LIST>"
		             +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
		               +" <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
			         +"</OLDAUDITENTRYIDS.LIST>"
		             +"<LEDGERNAME>Round off</LEDGERNAME>"
		             +"<GSTCLASS/>"
		             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
		             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
		             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
		             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
		             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
		            +" <AMOUNT>-"+roundOffAmount+"</AMOUNT>"
	            +"</LEDGERENTRIES.LIST>";
		}else{
			System.out.println("inside third one==="+roundOffAmount);
			roundOffAmount=0.0000;
			roundoff="<LEDGERENTRIES.LIST>"
		             +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
		               +" <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
			         +"</OLDAUDITENTRYIDS.LIST>"
		             +"<LEDGERNAME>Round off</LEDGERNAME>"
		             +"<GSTCLASS/>"
		             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
		             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
		             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
		             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
		             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
		            +" <AMOUNT>"+roundOffAmount+"</AMOUNT>"
	            +"</LEDGERENTRIES.LIST>";
		}
		
		Random rand=new Random();
		int masterId=rand.nextInt(5)+billId;
		int alterId=rand.nextInt(3)+billId;
		int voucherId=rand.nextInt(5)+billId;
		String firstXml ="<ENVELOPE>"
							 +"<HEADER>"
							  +"<TALLYREQUEST>Import Data</TALLYREQUEST>"
							 +"</HEADER>"
							 +"<BODY>"
							  +"<IMPORTDATA>"
							   +"<REQUESTDESC>"
							    +"<REPORTNAME>Vouchers</REPORTNAME>"
							    +"<STATICVARIABLES>"
							     +"<SVCURRENTCOMPANY>"+tallyCompanyName+"</SVCURRENTCOMPANY>"
							    +"</STATICVARIABLES>"
							   +"</REQUESTDESC>"
							   +"<REQUESTDATA>"
							    +"<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
							     +"<VOUCHER REMOTEID=\""+remoteId+"\""
											+"VCHKEY=\""+voucherKey+"\"" 
											+"VCHTYPE=\"Journal\""
											+"ACTION=\"Create\"" 
											+"OBJVIEW=\"Accounting Voucher View\">"
							      +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							       +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
							      +"</OLDAUDITENTRYIDS.LIST>"
							      +"<DATE>"+billDate+"</DATE>"
							      +"<GUID>"+remoteId+"</GUID>"
							      +"<NARRATION>BEING CAM BILL RAISED TO RESIDENTS</NARRATION>"
							      +"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME>"
							      +"<VOUCHERNUMBER>1</VOUCHERNUMBER>"
							      +"<PARTYLEDGERNAME>"+propertyNumber+"</PARTYLEDGERNAME>"
							      +"<CSTFORMISSUETYPE/>"
							      +"<CSTFORMRECVTYPE/>"
							      +"<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>"
							      +"<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"
							      +"<VCHGSTCLASS/>"
							      +"<DIFFACTUALQTY>No</DIFFACTUALQTY>"
							      +"<AUDITED>No</AUDITED>"
							      +"<FORJOBCOSTING>No</FORJOBCOSTING>"
							      +"<ISOPTIONAL>No</ISOPTIONAL>"
							      +"<EFFECTIVEDATE>"+billDate+"</EFFECTIVEDATE>"
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
							      +"<HASCASHFLOW>No</HASCASHFLOW>"
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
							      +"<VOUCHERKEY>"+voucherId+"</VOUCHERKEY>"
							       +"<ALLLEDGERENTRIES.LIST>"
							       +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							        +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
							       +"</OLDAUDITENTRYIDS.LIST>"
							       +"<LEDGERNAME>"+propertyNumber+"</LEDGERNAME>"
							       +"<GSTCLASS/>"
							       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
							       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
							       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
							       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
							       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
							       +"<AMOUNT>-"+roundOff+"</AMOUNT>"
							       +"</ALLLEDGERENTRIES.LIST>";
		
		String 	lastXml= "</VOUCHER>"
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
		XMLSales=new StringBuilder(firstXml).toString()+ new StringBuilder(x).toString()+new StringBuilder(roundoff).toString()+new StringBuilder(lastXml).toString();
		//FileUtils.writeStringToFile(new File("C:/Users/TEMP.DESKTOP-FTP0PFN/Desktop/TallyReponse/TallyReponse.xml"), XMLSales);
		/*CommonTally commonTally=new CommonTally();
		org.restlet.Request request=commonTally.getAllRequest(XMLSales,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword);
		Response response=commonTally.getTallyReponse(request);
		Representation output = commonTally.executeRequest(response);
		String loadCmp="";
		if(tallyCompanyName.equalsIgnoreCase("Rwa Test Company")){
			loadCmp="Please start 'TallyERP9' server and load company '"+tallyCompanyName+"'.";
		}else{
			loadCmp="Please start 'TallyERP9' server and load company'"+tallyCompanyName+"'.";
		}
		if(output== null){
			return loadCmp;
		}else{
			String outputTest = output.getText();
			
			ResponseTally responseTally=commonTally.getResponse(outputTest);
			String lineerror=responseTally.getLineerror();
			int created=responseTally.getCreated();
			int altered=responseTally.getAltered();
			int error=responseTally.getErrors();
			String dateIssue="Request can not be proceeed";
			String success="CAM Successfully Posted To Tally";
			String errorsinline="Please load the company '"+tallyCompanyName+"' in tally server";
			
			if(created==1 || (altered==1 || altered==2)){
				return success;
			}else if((error==0 || error==1) && created == 0 && lineerror == null){
				return dateIssue;
			}else if((error==1 || error==2) && lineerror != null){
				return lineerror;
			}
			else if(lineerror.equalsIgnoreCase("Could not set &apos;SVCurrentCompany&apos; to &apos;"+tallyCompanyName+"&apos;")){
				return errorsinline;
			}else{
				return lineerror;
			}
			
		}*/
		return XMLSales;
	}

	private List<String> getAllLedgerEntries(List<Map<String,Object>> itemList) {
		// TODO Auto-generated method stub
		String key=null;
		String ledgerName=null;
		double amount=0.0;
		String xml=null;
		List<String> allInventoriesList=new ArrayList<String>();
		System.out.println("===============itemList inside tally"+itemList.size());
		for(Map<String, Object> maplist:itemList)
		{
			key=(String) maplist.get("transactionCode");
			
			if(key.equalsIgnoreCase("CAM_RATE"))
			{
				ledgerName="CAM Collection";
				amount=(double) maplist.get("balanceAmount");
				totalamount=totalamount+amount;
				
				xml="<ALLLEDGERENTRIES.LIST>"
				         +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
			             +"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
			             +"<GSTCLASS/>"
			             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
			             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
			             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
			             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
			             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
			             +"<AMOUNT>"+amount+"</AMOUNT>"
			             +"</ALLLEDGERENTRIES.LIST>";
				allInventoriesList.add(xml);
			}
			if(key.equalsIgnoreCase("CAM_INTEREST"))
			{
				ledgerName="Late payment charges";
				amount=(double) maplist.get("balanceAmount");
				totalamount=totalamount+amount;
				
				xml="<ALLLEDGERENTRIES.LIST>"
				         +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
			             +"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
			             +"<GSTCLASS/>"
			             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
			             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
			             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
			             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
			             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
			             +"<AMOUNT>"+amount+"</AMOUNT>"
			             +"</ALLLEDGERENTRIES.LIST>";
				allInventoriesList.add(xml);
			}
			if(key.equalsIgnoreCase("CAM_CGST_TAX"))
			{
				ledgerName="CGST @9%(Output)";
				amount=(double) maplist.get("balanceAmount");
				totalamount=totalamount+amount;
				
				xml="<ALLLEDGERENTRIES.LIST>"
				         +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
			             +"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
			             +"<GSTCLASS/>"
			             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
			             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
			             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
			             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
			             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
			             +"<AMOUNT>"+amount+"</AMOUNT>"
			             +"</ALLLEDGERENTRIES.LIST>";
				allInventoriesList.add(xml);
			}
			if(key.equalsIgnoreCase("CAM_CGST_Late_Pay")){
				ledgerName="CAM CGST Late Pay @9%";
				amount=(double) maplist.get("balanceAmount");
				totalamount=totalamount+amount;
				
				xml="<ALLLEDGERENTRIES.LIST>"
						+"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
						+"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
						+"<GSTCLASS/>"
						+"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
						+"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
						+"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
						+"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
						+"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
						+"<AMOUNT>"+amount+"</AMOUNT>"
						+"</ALLLEDGERENTRIES.LIST>";
				allInventoriesList.add(xml);
				
			}
			if(key.equalsIgnoreCase("CAM_SGST_TAX"))
			{
				ledgerName="SGST @9%(Output)";
				amount=(double) maplist.get("balanceAmount");
				totalamount=totalamount+amount;
				
				xml="<ALLLEDGERENTRIES.LIST>"
				         +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
			             +"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
			             +"<GSTCLASS/>"
			             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
			             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
			             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
			             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
			             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
			             +"<AMOUNT>"+amount+"</AMOUNT>"
			             +"</ALLLEDGERENTRIES.LIST>";
				allInventoriesList.add(xml);
			}
			if(key.equalsIgnoreCase("CAM_SGST_Late_Pay")){
				ledgerName="CAM SGST Late Pay @9%";
				amount=(double) maplist.get("balanceAmount");
				totalamount=totalamount+amount;
				
				xml="<ALLLEDGERENTRIES.LIST>"
				         +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
			             +"<LEDGERNAME>"+ledgerName+"</LEDGERNAME>"
			             +"<GSTCLASS/>"
			             +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
			             +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
			             +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
			             +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
			             +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
			             +"<AMOUNT>"+amount+"</AMOUNT>"
			             +"</ALLLEDGERENTRIES.LIST>";
				allInventoriesList.add(xml);
				
			}
			if(key.equalsIgnoreCase("CAM_ROF"))
			{
				ledgerName="Round off";
				amount=(double) maplist.get("balanceAmount");
			}
			if(key.equalsIgnoreCase("CAM_PARKING_SLOT"))
			{
				
			}
		}

		return allInventoriesList;
	}
	
	public String createXMLforPrepaidBill(String serviceType,String salesLedger,String tallyCompanyName,String personName,String accountNo,
										  String meterNo,String propertyNo,Double totalAmount,Double camBill,Double sbCess,Double kkCess,
										  Double sTax,String remoteId,String voucherKey,String fromDate,String toDate) throws Exception{
		
	//	String x=null;
		String XMLSales=null;
		String date= fromDate;
		String effectiveDate = fromDate;
		//String masterId = "";
		//String alterId = "";
		Random rand=new Random();
		String masterId  = rand.nextInt(5)+meterNo;
		String alterId   = rand.nextInt(3)+meterNo;
		String voucherId = rand.nextInt(5)+meterNo;
		
	//	List<String> xml=getAllLedgerEntries(lineItemList);
		StringBuilder sb = new StringBuilder();
		/*for(int i=0;i<xml.size();i++)
		{
			sb.append(xml.get(i));
			x = sb.toString();
		}
		*/

		DecimalFormat df2 = new DecimalFormat("##.##");
		 String totalAmountST = df2.format(totalAmount);
		 String camBillST = df2.format(camBill);
		 String sbCessST = df2.format(sbCess);
		 String kkCessST = df2.format(kkCess);
		 String sTaxST = df2.format(sTax);
		 Double rountOffamt=totalAmount-(camBill+sbCess+kkCess+sTax);
		 System.out.println("rountOffamt ============ "+rountOffamt);
		 String roundOffAmount=rountOffamt.toString();
		 System.out.println("roundOffAmount = "+roundOffAmount);
		
		String firstXml ="<ENVELOPE>"
							 +"<HEADER>"
							  +"<TALLYREQUEST>Import Data</TALLYREQUEST>"
							 +"</HEADER>"
							 +"<BODY>"
							  +"<IMPORTDATA>"
							   +"<REQUESTDESC>"
							    +"<REPORTNAME>Vouchers</REPORTNAME>"
							    +"<STATICVARIABLES>"
							     +"<SVCURRENTCOMPANY>"+tallyCompanyName+"</SVCURRENTCOMPANY>"
							    +"</STATICVARIABLES>"
							   +"</REQUESTDESC>"
							   +"<REQUESTDATA>"
							    +"<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
							     +"<VOUCHER REMOTEID=\""+remoteId+"\""
											+"VCHKEY=\""+voucherKey+"\"" 
											+"VCHTYPE=\"Journal\""
											+"ACTION=\"Create\"" 
											+"OBJVIEW=\"Accounting Voucher View\">"
							      +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							       +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
							      +"</OLDAUDITENTRYIDS.LIST>"
							      +"<DATE>"+date+"</DATE>" 									//Bill Date
							      +"<GUID>"+remoteId+"</GUID>"
							      +"<NARRATION>BEING CAM BILL RAISED TO RESIDENTS</NARRATION>"
							      +"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME>"
							      +"<VOUCHERNUMBER>1</VOUCHERNUMBER>"
							      +"<PARTYLEDGERNAME>"+propertyNo+"</PARTYLEDGERNAME>"
							      +"<CSTFORMISSUETYPE/>"
							      +"<CSTFORMRECVTYPE/>"
							      +"<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>"
							      +"<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"
							      +"<VCHGSTCLASS/>"
							      +"<DIFFACTUALQTY>No</DIFFACTUALQTY>"
							      +"<AUDITED>No</AUDITED>"
							      +"<FORJOBCOSTING>No</FORJOBCOSTING>"
							      +"<ISOPTIONAL>No</ISOPTIONAL>"
							      +"<EFFECTIVEDATE>"+effectiveDate+"</EFFECTIVEDATE>" 		 //EFFECTIVE Bill Date
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
							      +"<HASCASHFLOW>No</HASCASHFLOW>"
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
							      +"<VOUCHERKEY>"+voucherId+"</VOUCHERKEY>"
							 
							      +"<ALLLEDGERENTRIES.LIST>"
							      +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							      +" <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
           						 +" </OLDAUDITENTRYIDS.LIST>"
           						 +" <LEDGERNAME>"+propertyNo+"</LEDGERNAME>"
           						  +"<GSTCLASS/>"
           						  +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
           						  +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
           						  +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
           						  +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
           						  +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
           						  +"<AMOUNT>-"+totalAmountST+"</AMOUNT>"
           						 +" </ALLLEDGERENTRIES.LIST>"
           						  
           						 +" <ALLLEDGERENTRIES.LIST>"
           						 +" <OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
           						+" <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
           						  +"</OLDAUDITENTRYIDS.LIST>"
           						 +" <LEDGERNAME>CamAmount</LEDGERNAME>"
           						 +" <GSTCLASS/>"
           						  +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
           						  +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
           						  +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
           						  +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
           						  +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
           						  +"<AMOUNT>"+camBillST+"</AMOUNT>"
           						  +"</ALLLEDGERENTRIES.LIST>"
           						  
           						  +"<ALLLEDGERENTRIES.LIST>"
           						  +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
           						  +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
           						  +"</OLDAUDITENTRYIDS.LIST>"
           						  +"<LEDGERNAME>KrishiKalyan@0.5%</LEDGERNAME>"
           						  +"<GSTCLASS/>"
           						  +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
           						  +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
           						  +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
           						  +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
           						  +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
           						  +"<AMOUNT>"+kkCessST+"</AMOUNT>"
           						  +"</ALLLEDGERENTRIES.LIST>"
           						  
           						  +"<ALLLEDGERENTRIES.LIST>"
								  +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
								  +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
           						  +"</OLDAUDITENTRYIDS.LIST>"
           						  +"<LEDGERNAME>SwachhBharatCess@0.5%</LEDGERNAME>"
           						  +"<GSTCLASS/>"
           						  +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
           						  +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
           						 +" <REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
           						  +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
           						  +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
           						  +"<AMOUNT>"+sbCessST+"</AMOUNT>"
           						  +"</ALLLEDGERENTRIES.LIST>"
           						  
           						  +"<ALLLEDGERENTRIES.LIST>"
           						  +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
           						  +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
           						  +"</OLDAUDITENTRYIDS.LIST>"
           						  +"<LEDGERNAME>ServiceTax@14%</LEDGERNAME>"
           						  +"<GSTCLASS/>"
           						  +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
           						  +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
           						  +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
           						  +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
           						  +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
           						  +"<AMOUNT>"+sTaxST+"</AMOUNT>"
           						  +"</ALLLEDGERENTRIES.LIST>"
           						  
           						  +"<ALLLEDGERENTRIES.LIST>"
           						  +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
           						  +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
           						  +"</OLDAUDITENTRYIDS.LIST>"
           						  +"<LEDGERNAME>Round Off</LEDGERNAME>"
           						  +"<GSTCLASS/>"
           						  +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
           						  +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
           						  +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
           						  +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
           						  +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
           						  +"<AMOUNT>"+roundOffAmount+"</AMOUNT>"
           						  +"</ALLLEDGERENTRIES.LIST>";
							      		
						/*		+"<ALLLEDGERENTRIES.LIST>"
								+"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
								+"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
								+"</OLDAUDITENTRYIDS.LIST>"
								+"<LEDGERNAME>CamAmount</LEDGERNAME>"					//CamAmount
								+"<GSTCLASS/>"
								+"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
								+"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
								+"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
								+"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
								+"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
								+"<AMOUNT>"+camBillST+"</AMOUNT>"
								+"</ALLLEDGERENTRIES.LIST>"
		
							       +"<ALLLEDGERENTRIES.LIST>"
							       +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							        +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
							       +"</OLDAUDITENTRYIDS.LIST>"
							       +"<LEDGERNAME>KrishiKalyan@0.5%</LEDGERNAME>"		//KrishiKalyan@0.5%
							       +"<GSTCLASS/>"
							       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
							       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
							       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
							       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
							       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
							       +"<AMOUNT>"+kkCessST+"</AMOUNT>"
							       +"</ALLLEDGERENTRIES.LIST>"
		
							       +"<ALLLEDGERENTRIES.LIST>"
							       +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							       +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
							       +"</OLDAUDITENTRYIDS.LIST>"
							       +"<LEDGERNAME>SwachhBharatCess@0.5%</LEDGERNAME>"     //SwachhBharatCess@0.5%
							       +"<GSTCLASS/>"
							       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
							       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
							       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
							       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
							       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
							       +"<AMOUNT>"+sbCessST+"</AMOUNT>"
							       +"</ALLLEDGERENTRIES.LIST>"
		
							       +"<ALLLEDGERENTRIES.LIST>"
							       +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
							       +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
							       +"</OLDAUDITENTRYIDS.LIST>"
							       +"<LEDGERNAME>ServiceTax@14%</LEDGERNAME>"				//ServiceTax@14%
							       +"<GSTCLASS/>"
							       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
							       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
							       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
							       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
							       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
							       +"<AMOUNT>"+sTaxST+"</AMOUNT>"
							       +"</ALLLEDGERENTRIES.LIST>"
							       
                                   +"<ALLLEDGERENTRIES.LIST>"
                                   +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"
                                   +"<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"
                                   +"</OLDAUDITENTRYIDS.LIST>"
                                   +"<LEDGERNAME></LEDGERNAME>"				//TotalAmount
                                   +"<GSTCLASS/>"
                                   +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
                                   +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
                                   +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
                                   +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
                                   +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
                                   +"<AMOUNT>-"+totalAmountST+"</AMOUNT>"
                                   +"</ALLLEDGERENTRIES.LIST>";*/
		
		String 	lastXml= "</VOUCHER>"
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
		XMLSales=new StringBuilder(firstXml).toString()+new StringBuilder(lastXml).toString();
		//FileUtils.writeStringToFile(new File("C:/SkyOn_TallyResponse/TallyBill.xml"), XMLSales);
		/*CommonTally commonTally=new CommonTally();
		org.restlet.Request request=commonTally.getAllRequest(XMLSales,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword);
		Response response=commonTally.getTallyReponse(request);
		Representation output = commonTally.executeRequest(response);
		String loadCmp="";
		if(tallyCompanyName.equalsIgnoreCase("Rwa Test Company")){
			loadCmp="Please start 'TallyERP9' server and load company '"+tallyCompanyName+"'.";
		}else{
			loadCmp="Please start 'TallyERP9' server and load company'"+tallyCompanyName+"'.";
		}
		if(output== null){
			return loadCmp;
		}else{
			String outputTest = output.getText();
			
			ResponseTally responseTally=commonTally.getResponse(outputTest);
			String lineerror=responseTally.getLineerror();
			int created=responseTally.getCreated();
			int altered=responseTally.getAltered();
			int error=responseTally.getErrors();
			String dateIssue="Request can not be proceeed";
			String success="CAM Successfully Posted To Tally";
			String errorsinline="Please load the company '"+tallyCompanyName+"' in tally server";
			
			if(created==1 || (altered==1 || altered==2)){
				return success;
			}else if((error==0 || error==1) && created == 0 && lineerror == null){
				return dateIssue;
			}else if((error==1 || error==2) && lineerror != null){
				return lineerror;
			}
			else if(lineerror.equalsIgnoreCase("Could not set &apos;SVCurrentCompany&apos; to &apos;"+tallyCompanyName+"&apos;")){
				return errorsinline;
			}else{
				return lineerror;
			}
			
		}*/
		return XMLSales;
	}
}
