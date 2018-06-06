package com.bcits.bfm.util;

import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorContractRenewalSMS implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(VendorContractRenewalSMS.class);
	private final String number;
	private final String fullName;
	private final String subject;
	private final String contractName;
	private final Date contractEndDate;
	private final String gateWayUserName;
	private final String gateWayPassword;
	
	
	public VendorContractRenewalSMS(String number,String fullName,String subject,String contractName,Date contractEndDate,String gateWayUserName,String gateWayPassword)
	{
		this.number = "91"+number;
		this.fullName = fullName;
		this.subject = subject;
		this.contractName = contractName;
		this.contractEndDate = contractEndDate;
		this.gateWayUserName = gateWayUserName;
		this.gateWayPassword = gateWayPassword;
	}

	@Override
	public void run()
	{
		 HttpClient client=null;
		 PostMethod post=null;
		 String sURL;
		 client = new HttpClient(new MultiThreadedHttpConnectionManager());
		 client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);//set
		 sURL = " http://smslane.com/vendorsms/pushsms.aspx";
		 post = new PostMethod(sURL);
		 //give all in string
		 post.addParameter("user", gateWayUserName);
		 post.addParameter("password", gateWayPassword);
		 post.addParameter("msisdn", number);
		 post.addParameter("msg", "Dear "+fullName+", Contract renewal alert for<br>"+contractName+"<br/>"+contractEndDate+" - IREO-BFM  Administration services.");
		 post.addParameter("sid","CESCTX");
		 post.addParameter("fl", "0");
		 post.addParameter("GWID", "2");
		  //PUSH the URL 
		 String sent = "";
		 try 
			{
				int statusCode = client.executeMethod(post);
				if (statusCode != HttpStatus.SC_OK) 
					BfmLogger.logger.error("Method failed: " + post.getStatusLine());
				else
					BfmLogger.logger.info("************* MESSAGE SENT SUCCESSFULY *************");
				BfmLogger.logger.info("statusCode --------"+statusCode);
				BfmLogger.logger.info("line 1 ------------"+post.getStatusLine().toString());
				BfmLogger.logger.info("SC_OK -------------"+HttpStatus.SC_OK);
				BfmLogger.logger.info("line 2 ------------"+post.getResponseBodyAsString());
				
				if(post.getResponseBodyAsString().contains("Failed"))
					BfmLogger.logger.info("Failed ------------"+sent);
				else
					BfmLogger.logger.info("Success ------------"+sent);
				
				
				sent=post.getResponseBodyAsString().toString();
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				post.releaseConnection();
			}

	}
}
