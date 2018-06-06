package com.bcits.bfm.serviceImpl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.restlet.Response;
import org.restlet.representation.Representation;

import com.ResponseTally;
import com.bcits.bfm.model.TallyLineItems;
import com.envelope.body.data.collection.Voucher;
import com.envelope.body.data.collection.ledgerlist.AllLedgerEntriesList;
import com.main.tally.CommonTally;



public class BillingInvoicesXML {
	
	public String createBillInvoicesforEB(List<Map<String, Object>> itemsList,String remoteId,String voucherKey,int billId,
			String billDate,String salesLedger,String billNumber,String tallyCompanyName,Date basicDateOfInvoice,List<Map<String ,Object>> propertyMap,String serviceType,String partyLedger,String arrearsLedge,List<Map<String,Object>> lineItemList) throws Exception{
		
		
		double amount=0.0000;
		//String y = null;
		salesLedger=StringEscapeUtils.escapeXml(salesLedger);
		billNumber=StringEscapeUtils.escapeXml(billNumber);
		//tallyUserName=StringEscapeUtils.escapeXml(tallyUserName);
		//tallyPassword=StringEscapeUtils.escapeXml(tallyPassword);
		serviceType=StringEscapeUtils.escapeXml(serviceType);
		partyLedger=StringEscapeUtils.escapeXml(partyLedger);
		
		String propertNumber=null;
		String firstXml=null;
		String lastXml=null;
		String XMLSales=null;
		String balanceAmount=null;
		
		Random rand=new Random();
		int masterId=rand.nextInt(5)+03;
		int alterId=rand.nextInt(3)+012;
		int voucherId=rand.nextInt(5)+1806592;
		String x = null;
		double arrearamount=0.0000;
		String str=null;
		List<List<TallyLineItems>> tallLineItems=this.getListItem(itemsList);
		for (List<TallyLineItems> list : tallLineItems) {
			for (TallyLineItems tallyLineItems : list) {
				arrearamount=tallyLineItems.getArrearsAmount();
			}
			
		}
		
		if (arrearamount < 0.0) {
			str = new BillingArrearInvoices().createArrearBillInvoices(
					itemsList, remoteId, voucherKey, billId, billDate,
					salesLedger, billNumber,tallyCompanyName,
					basicDateOfInvoice, propertyMap, serviceType, partyLedger,
					arrearsLedge);
               System.out.println("in side arrearamount < 0.0");
			/*if (str.equalsIgnoreCase("Arrear Bill Successfully Posted To Tally")) {
				System.out.println("Response" + str);
			} else {
				System.out.println("Failed response" + str);
			}*/
		}if(arrearamount > 0.0){
			str = new BillingArrearDrInvoices().createArrearBillInvoices(
					itemsList, remoteId, voucherKey, billId, billDate,
					salesLedger, billNumber,tallyCompanyName,
					basicDateOfInvoice, propertyMap, serviceType, partyLedger,
					arrearsLedge);
			System.out.println("in side arrearamount > 0.0");
/*
			if (str.equalsIgnoreCase("Arrear Bill Successfully Posted To Tally")) {
				System.out.println("Response" + str);
			} else {
				System.out.println("Failed response" + str);
			}*/
			
		}
		
		
		List<String> xml = this.manipulateInventoryItems(tallLineItems,salesLedger,serviceType,partyLedger,billNumber,arrearsLedge,lineItemList);
		//StringBuilder sb = new StringBuilder();
	/*	for (List<String> list : allInventoriesXML) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				x = sb.toString();
			}
			y = x;
		}*/
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<xml.size();i++)
		{
			sb.append(xml.get(i));
			x = sb.toString();
		}
		
		CommonTally commonTally=new CommonTally();
		Voucher voucher= commonTally.getInventories( new StringBuilder("<VOUCHER> ")+ new StringBuilder(x).toString()+ new StringBuilder("</VOUCHER>"));
	   
	    List<AllLedgerEntriesList> allLedgerEntriesLists =voucher.getAllLedgerEntriesLists();
		for (AllLedgerEntriesList allLedgerEntriesList : allLedgerEntriesLists) {
				String amount1=allLedgerEntriesList.getAmount();
				amount+=Double.parseDouble(amount1.trim());
			}
	     
		List<List<String>>  acccountDetails=this.getAccountDetails(propertyMap);
		for (int i = 0; i < acccountDetails.size(); i++) {
			propertNumber=acccountDetails.get(i).get(3);
		}
		
		DecimalFormat df2 = new DecimalFormat("###.####");
		double roundOff=Double.valueOf(df2.format(Math.round(amount)));
		double roundOffAmount=Double.valueOf(df2.format((amount-roundOff)));
		String roundoff="";
		if(roundOff > amount ){
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
		}else if(roundOff < amount){
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
		
		 firstXml ="<ENVELOPE>"
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
							      +"<NARRATION>BEING ELECTRICITY BILL RAISED TO RESIDENT</NARRATION>"
							      +"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME>"
							      +"<VOUCHERNUMBER>1</VOUCHERNUMBER>"
							      +"<PARTYLEDGERNAME>"+propertNumber+"</PARTYLEDGERNAME>"
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
							       +"<LEDGERNAME>"+propertNumber+"</LEDGERNAME>"
							       +"<GSTCLASS/>"
							       +"<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
							       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
							       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
							       +"<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"
							       +"<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>"
							     /*  +"<AMOUNT>-"+amount+"</AMOUNT>"
							      +"</ALLLEDGERENTRIES.LIST>"*/;
		 if(amount<0){
			 amount=-(amount);
			 balanceAmount= "<AMOUNT>"+amount+"</AMOUNT>"
							 +"<BILLALLOCATIONS.LIST>"
						       +"<NAME>"+billNumber+"</NAME>"
						       +"<BILLTYPE>Agst Ref</BILLTYPE>"
						       +"<TDSDEDUCTEEISSPECIALRATE>No</TDSDEDUCTEEISSPECIALRATE>"
						       +"<AMOUNT>"+amount+"</AMOUNT>"
						       +"<INTERESTCOLLECTION.LIST></INTERESTCOLLECTION.LIST>"
						    +"</BILLALLOCATIONS.LIST>"
				      	+"</ALLLEDGERENTRIES.LIST>";
		 }else{
			 balanceAmount= "<AMOUNT>-"+roundOff+"</AMOUNT>"
							 +"<BILLALLOCATIONS.LIST>"
						       +"<NAME>"+billNumber+"</NAME>"
						       +"<BILLTYPE>Agst Ref</BILLTYPE>"
						       +"<TDSDEDUCTEEISSPECIALRATE>No</TDSDEDUCTEEISSPECIALRATE>"
						       +"<AMOUNT>"+roundOff+"</AMOUNT>"
						       +"<INTERESTCOLLECTION.LIST></INTERESTCOLLECTION.LIST>"
						    +"</BILLALLOCATIONS.LIST>"
				      	+"</ALLLEDGERENTRIES.LIST>";
		 }
		
		firstXml=new StringBuilder(firstXml).toString()+new StringBuilder(balanceAmount).toString();
		lastXml= "</VOUCHER>"
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
		
		System.err.println("near XML Sales");
		XMLSales=new StringBuilder(firstXml).toString()+ new StringBuilder(x).toString()+new StringBuilder(roundoff)+ new StringBuilder(lastXml).toString();
		String XMLFinalResult=null;
		if(str!=null){
		XMLFinalResult=str+XMLSales;
		}else{
		 XMLFinalResult=XMLSales;
		}
		//String receiptXml=FileUtils.readFileToString(new File("C:\\Users\\user60\\Desktop\\DoNotDelete\\TallyReponse.xml"));
		/*org.restlet.Request request=commonTally.getAllRequest(XMLSales,tallyPortNumber,tallyIpAddress,tallyUserName,tallyPassword);
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
			 System.out.println("===============tally responseeeeeeee"+outputTest);
			
			ResponseTally responseTally=commonTally.getResponse(outputTest);
			System.out.println("tally response============"+responseTally);
			String lineerror=responseTally.getLineerror();
			int created=responseTally.getCreated();
			int altered=responseTally.getAltered();
			int error=responseTally.getErrors();
			String dateIssue="Request can not be proceeed";
			String success="Bill Successfully Posted To Tally";
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
		return XMLFinalResult;
		
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

	public List<String> manipulateInventoryItems(
			List<List<TallyLineItems>> tallLineItems,String salesLedger,String serviceType,String partyLedger,String billNumber,String arrearsLedge,List<Map<String,Object>> lineItemList) {
				
		
	/*	List<List<String>> setList=new ArrayList<List<String>>();
		if(!tallLineItems.isEmpty()){
			for (List<TallyLineItems> tallyLineItems : tallLineItems) {
				List<String> allInventoriesList=new ArrayList<String>();
				for (TallyLineItems items : tallyLineItems) {
					double amount=items.getAmount();
					String totalCollectionXml="<ALLLEDGERENTRIES.LIST>"
											       +"<OLDAUDITENTRYIDS.LIST TYPE=\"Number\"> <OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS> </OLDAUDITENTRYIDS.LIST>"
											       +"<LEDGERNAME>"+salesLedger+"</LEDGERNAME>"
											       +"<GSTCLASS/>"
											       +"<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
											       +"<LEDGERFROMITEM>No</LEDGERFROMITEM>"
											       +"<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>"
											       +"<ISPARTYLEDGER>No</ISPARTYLEDGER>"
											       +"<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>"
											       +"<AMOUNT>"+amount+"</AMOUNT>"
											     +"</ALLLEDGERENTRIES.LIST>";
					
						
						allInventoriesList.add(totalCollectionXml);
				  }
					setList.add(allInventoriesList);
					
				}
			}
		
		
		return setList;*/
		String key=null;
		String ledgerName=null;
		double amount=0.0;
		String xml=null;
		
		List<String> allInventoriesList=new ArrayList<String>();
		for (Map<String, Object> itemList:lineItemList) {
			key=(String) itemList.get("transactionCode");
			if(key.equalsIgnoreCase("EL_EC")){
				ledgerName="Electricity Collection";
				amount=(double) itemList.get("balanceAmount");
				
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
			
			if(key.equalsIgnoreCase("EL_LPC")){
				ledgerName="Late Payment Charges";
				amount=(double) itemList.get("balanceAmount");
				
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
			
			if(key.equalsIgnoreCase("EL_DG")){
				ledgerName="DG charges";
				amount=(double) itemList.get("balanceAmount");
				
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
			
			
		}
		return allInventoriesList;
	}

	public List<List<TallyLineItems>> getListItem(List<Map<String, Object>> itemsList) {
		
		Object value =null;
		double arrearsAmount=0.0000;
		double amount=0.0000;
		String key="";
		List<List<TallyLineItems>> billItemList=new ArrayList<List<TallyLineItems>>();
		for (Map<String, Object> itemMap : itemsList) {
			TallyLineItems tallyLineItems=new TallyLineItems();
			List<TallyLineItems> tallyLineItems2=new ArrayList<TallyLineItems>();
			for (Map.Entry<String, Object> entry : itemMap.entrySet()) {

				key = entry.getKey();
				value = entry.getValue();

				if (key.equalsIgnoreCase("totalAmount")) {
					amount = (Double) value;
					tallyLineItems.setAmount(amount);
					
				}
				if (key.equalsIgnoreCase("arrearsAmount")) {
					
					arrearsAmount = (Double) value;
					tallyLineItems.setArrearsAmount(arrearsAmount);

				}

			}
			tallyLineItems2.add(tallyLineItems);
			billItemList.add(tallyLineItems2);
		}
		
		 return billItemList;
		
		
	}

}
