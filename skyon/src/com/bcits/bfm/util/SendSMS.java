package com.bcits.bfm.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendSMS implements Runnable
{
	private final String name;
	private final String status;
	private final String number;
	private final String userId;
	private final String password;
	private final String gateWayUserName;
	private final String gateWayPassword;
	
	
	public SendSMS(String name, String status, String mobile, String urLoginName, String tempPass, String gateWayUserName, String gateWayPassword)
	{
		this.name = name;
		this.status = status;
		number = "91"+mobile;
		userId = urLoginName;
		password = tempPass;
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
		 post.addParameter("msg", "Dear "+name+", Your account has been "+status+". Your account details are username : "+userId+" & password : "+password+" - IREO-BFM  Administration services.");
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
