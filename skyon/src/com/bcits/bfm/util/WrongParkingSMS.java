package com.bcits.bfm.util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class WrongParkingSMS implements Runnable
{
	private final String number;
	private final String userName;
	private final String message;
	private final String gateWayUserName;
	private final String gateWayPassword;
	private final String gateWayURL;
	private final String smsGatewaySender;
	private final String smsGatewayRoute;
	private final String smsGatewayMsgType;
	
	public WrongParkingSMS(SmsCredentialsDetailsBean smsCredentialsDetailsBean)
	{
		this.number = "91"+smsCredentialsDetailsBean.getNumber();
		this.userName = smsCredentialsDetailsBean.getUserName();
		this.message = smsCredentialsDetailsBean.getMessage();
		this.gateWayUserName = smsCredentialsDetailsBean.getSmsGatewayUsername();
		this.gateWayPassword = smsCredentialsDetailsBean.getSmsGatewayPwd();
		this.gateWayURL = smsCredentialsDetailsBean.getSmsGatewayURL();
		this.smsGatewaySender= smsCredentialsDetailsBean.getSmsGatewaySender();
		this.smsGatewayRoute = smsCredentialsDetailsBean.getSmsGatewayRoute();
		this.smsGatewayMsgType = smsCredentialsDetailsBean.getSmsGatewayMsgType();
	}

	@Override
	public void run()
	{
		 HttpClient client=null;
		 GetMethod post=null;
		 String sURL;
		 client = new HttpClient(new MultiThreadedHttpConnectionManager());
		 client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);//set
		 sURL = gateWayURL;
		 post = new GetMethod(sURL);
		 //give all in string
		 post.setQueryString(new NameValuePair[] { 
			     new NameValuePair("uname", gateWayUserName),
			     new NameValuePair("password", gateWayPassword),
			     new NameValuePair("sender", smsGatewaySender),
			     new NameValuePair("receiver", number),
			     new NameValuePair("route", smsGatewayRoute),
			     new NameValuePair("msgtype", smsGatewayMsgType),
			     new NameValuePair("sms", message)
			     
			 });
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

