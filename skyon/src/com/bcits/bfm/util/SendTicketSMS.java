package com.bcits.bfm.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendTicketSMS implements Runnable {

	private final String number;
	private final String userName;
	private final String message;
	private final String gateWayUserName;
	private final String gateWayPassword;
	private final String gateWayURL;
	private final String smsGatewaySender;
	private final String smsGatewayRoute;
	private final String smsGatewayMsgType;
	
	public SendTicketSMS(SmsCredentialsDetailsBean smsCredentialsDetailsBean)
	{
		this.number = "91"+smsCredentialsDetailsBean.getNumber();
		this.userName = smsCredentialsDetailsBean.getUserName();
		this.message = smsCredentialsDetailsBean.getMessage();
		this.gateWayUserName = smsCredentialsDetailsBean.getSmsGatewayUsername().trim();
		this.gateWayPassword = smsCredentialsDetailsBean.getSmsGatewayPwd().trim();
		this.gateWayURL = smsCredentialsDetailsBean.getSmsGatewayURL();
		this.smsGatewaySender = smsCredentialsDetailsBean.getSmsGatewaySender();
		this.smsGatewayRoute = smsCredentialsDetailsBean.getSmsGatewayRoute();
		this.smsGatewayMsgType = smsCredentialsDetailsBean.getSmsGatewayMsgType();
	}
	
	@Override
	public void run() {
		HttpClient client=null;
		 GetMethod get=null;
		 String sURL;
		 client = new HttpClient(new MultiThreadedHttpConnectionManager());
		 client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);//set
		 sURL =gateWayURL;
		 get = new GetMethod(sURL);
		 //give all in string
//		 post.addParameter("user", gateWayUserName);
//		 post.addParameter("password", gateWayPassword);
//		 post.addParameter("msisdn", number);
//		 post.addParameter("msg",message);
//		 post.addParameter("sid", smsGatewaySid);
//		 post.addParameter("fl", smsGatewayFL);
//		 post.addParameter("GWID", smsGatewayGwid);
		 /*post.addRequestHeader("uname", "ireo01");
		 post.addRequestHeader("password", "123456");
		 post.addRequestHeader("sender", "GACRWA");
		 post.addRequestHeader("receiver", "8123564302");
		 post.addRequestHeader("route", "TA");
		 post.addRequestHeader("msgtype", "1");
		 post.addRequestHeader("sms","Hello");
		 */
		 get.setQueryString(new NameValuePair[] { 
			     new NameValuePair("uname", gateWayUserName),
			     new NameValuePair("password", gateWayPassword),
			     new NameValuePair("sender", smsGatewaySender),
			     new NameValuePair("receiver", number),
			     new NameValuePair("route", smsGatewayRoute),
			     new NameValuePair("msgtype", smsGatewayMsgType),
			     new NameValuePair("sms", message)
			     
			 });
		 /*post.setParameter("uname", "ireo01");
		 post.setParameter("password", "123456");
		 post.setParameter("sender", "GACRWA");
		 post.setParameter("receiver", "8123564302");
		 post.setParameter("route", "TA");
		 post.setParameter("msgtype", "1");
		 post.setParameter("sms","Hello");*/
		  //PUSH the URL 
		 String sent = "";
		 try 
			{
			
			/* GetMethod method = new GetMethod("http://newsms.designhost.in/index.php/smsapi/httpapi/")*/; 
			
			 
		

			    
			    
				System.out.println("****************post**********"+get.getQueryString()+"--------"+get.getURI()+"*****");
			    int statusCode = client.executeMethod(get);
				System.out.println("*************statusCode*********"+statusCode);
				System.out.println("*************HttpStatus.SC_OK*********"+HttpStatus.SC_OK);
				if (statusCode != HttpStatus.SC_OK) 
					BfmLogger.logger.error("Method failed: " + get.getStatusLine());
				else
					BfmLogger.logger.info("************* MESSAGE SENT SUCCESSFULY *************");
				BfmLogger.logger.info("statusCode --------"+statusCode);
				BfmLogger.logger.info("line 1 ------------"+get.getStatusLine().toString());
				BfmLogger.logger.info("SC_OK -------------"+HttpStatus.SC_OK);
				BfmLogger.logger.info("line 2 ------------"+get.getResponseBodyAsString());
				
				if(get.getResponseBodyAsString().contains("Failed"))
					BfmLogger.logger.info("Failed ------------"+sent);
				else
					BfmLogger.logger.info("Success ------------"+sent);
				
				
				sent=get.getResponseBodyAsString().toString();
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				get.releaseConnection();
			}

	}

}
