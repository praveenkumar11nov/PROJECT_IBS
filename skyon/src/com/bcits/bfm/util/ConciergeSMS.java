package com.bcits.bfm.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

public class ConciergeSMS implements Runnable {
	
	private final String vendorName;
	private final String ownerName;
	private final String serviceName;
	private final String ownerMobileNo;
	private final String gateWayUserName;
	private final String gateWayPassword;
	
	
	
	
	public ConciergeSMS(String vendorName, String ownerName,String serviceName,
			String ownerMobileNo, String gateWayUserName, String gateWayPassword) {
		super();
		this.vendorName = vendorName;
		this.ownerName = ownerName;
		this.serviceName = serviceName;
		this.ownerMobileNo = "91"+ownerMobileNo;
		this.gateWayUserName = gateWayUserName;
		this.gateWayPassword = gateWayPassword;
	}

	@Override
	public void run()
	{
		
		BfmLogger.logger.info("vendorName  "+vendorName + "ownerName  "+ownerName +"serviceName  "+serviceName );
		
		
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
		 post.addParameter("msisdn", ownerMobileNo);
		// From ##Field##- Dear ##Field##, Your service ##Field## is successfully delivered to you.Thank you for using our service.
		 post.addParameter("msg","From "+vendorName+"- Dear "+ownerName+", Your service "+serviceName+" is successfully delivered to you.Thank you for using our service.");
		 /*post.addParameter("msg", "Dear "+name+", Your account has been "+status+". Your account details are username : "+userId+" & password : "+password+" - IREO-BFM  Administration services.");*/
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
