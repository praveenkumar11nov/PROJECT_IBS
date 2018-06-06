package com.bcits.bfm.service.postInvoiceToTallyServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.service.PrepaidRechargeService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.postInvoiceToTallyService.TallyAdjustmentPostService;
import com.tally.adjustment.TallyAdjustmentPost;
import com.tally.adjustment.TallyAdjustmentPostForSkyon;



@Service
public class TallyAdjustmentPostServiceImpl implements TallyAdjustmentPostService {
	
	@Autowired
	private AdjustmentService adjustmentService; 
	
	@Autowired
	private PrepaidRechargeService prepaidRechargeService;

	@Override
	public String reponsePostAdjustmentToTally(int preRechargeId)
			throws Exception {

		String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
		String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
		String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
		String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
		String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");
		
		String autoIncrementString = this.getNewString(preRechargeId);
		String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
		String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";
		System.err.println("preRechargeId = "+preRechargeId);
		List<Map<String,Object>> adjustmentDetails=prepaidRechargeService.getTallyAdjustmentDetailData(preRechargeId);
		
		TallyAdjustmentPostForSkyon tallyadjustmentPost=new TallyAdjustmentPostForSkyon();
		String response=tallyadjustmentPost.createAdjustment(adjustmentDetails, remoteId, voucherKey, preRechargeId, tallyPortNumber, tallyIpAddress, tallyUserName, tallyPassword, tallyCompanyName);
		System.out.println("========================response"+response);
		
		
		
		return response;
	}
	
	@Override
	public String generateXmlAgustmentPosting(Date monthDate)  {
		List<PaymentAdjustmentEntity> adEntities=adjustmentService.getAllDataXMl(monthDate);
		String xmlFile="";
		for (PaymentAdjustmentEntity paymentAdjustmentEntity : adEntities) {
              String str=this.reponsePostAdjustmentToTallyXml(paymentAdjustmentEntity.getAdjustmentId());
              if(str!=null){
            	 
            	  xmlFile+=str+"\n";
              }
		}
		return xmlFile;
	}
	public String reponsePostAdjustmentToTallyXml(int adjustmentId){

		String tallyPortNumber=ResourceBundle.getBundle("application").getString("tallyPortNo");
		String tallyIpAddress=ResourceBundle.getBundle("application").getString("tallyIPAddress");
		String tallyUserName=ResourceBundle.getBundle("application").getString("tallyUserName");
		String tallyPassword=ResourceBundle.getBundle("application").getString("tallyPassword");
		String tallyCompanyName=ResourceBundle.getBundle("application").getString("tallyCompanyName");

		String autoIncrementString = this.getNewString(adjustmentId);
		String remoteId = "c9acc1de-7922-4396-8128-25ee27f99c92-0"+autoIncrementString+"0";
		String voucherKey = "c9acc1de-7922-4396-8128-25ee27f99c92-0000a44f:0"+autoIncrementString+"0";
		List<Map<String,Object>> adjustmentDetails=adjustmentService.getTallyAdjustmentDetailData(adjustmentId);

		//TallyAdjustmentPost tallyadjustmentPost=new TallyAdjustmentPost();
		String response=this.createAdjustmentXml(adjustmentDetails, remoteId, voucherKey, adjustmentId, tallyPortNumber, tallyIpAddress, tallyUserName, tallyPassword, tallyCompanyName);
		//System.out.println("========================response"+response);

          if(response!=null){
        	  adjustmentService.updateAdjustmentTallyStatus(adjustmentId);
          }

		return response;
	}
	public String createAdjustmentXml(List<Map<String, Object>> itemsList,String remoteId, String voucherKey, int billId, 
			String tallyPortNumber,String tallyIpAddress, String tallyUserName, String tallyPassword,
			String tallyCompanyName)
	{

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
		for (Map<String, Object> map : itemsList) {

			partyLedger=(String) map.get("propertyNumber");
			adjustmentAmount=(Double) map.get("adjustmentAmount");
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
		//FileUtils.writeStringToFile(new File("C:/Users/user51/Desktop/Tally Backup/Tally Xml/TallyReponse.xml"),completexml);
		return completexml;

	}
	  
	
	
	private String getNewString(int invoiceId) {
			
			StringBuilder sb = new StringBuilder();
				sb.append("");
				sb.append(invoiceId);
			String autoIncrementString = sb.toString();
			
			 return autoIncrementString;
		}

}
