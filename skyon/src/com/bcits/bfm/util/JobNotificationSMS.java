package com.bcits.bfm.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

public class JobNotificationSMS implements Runnable{
	
	private final String receiverAddress;
	private final String Ownername;
	private final String assetName;
	private final String notificationType;
	private final String jobName;
	private final String notification;
	private final String gateWayUserName;
	private final String gateWayPassword;

	public JobNotificationSMS(String receiverAddress, String ownername,
			String assetName, String notificationType, String jobName,String notification,
			String gateWayUserName, String gateWayPassword) {
		super();
		this.receiverAddress = receiverAddress;
		this.Ownername = ownername;
		this.assetName = assetName;
		this.notificationType = notificationType;
		this.jobName = jobName;
		this.notification = notification;
		this.gateWayUserName = gateWayUserName;
		this.gateWayPassword = gateWayPassword;
	}



	@Override
	public void run() {
		HttpClient client = null;
		PostMethod post = null;
		String sURL;
		client = new HttpClient(new MultiThreadedHttpConnectionManager());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);// set
		sURL = " http://smslane.com/vendorsms/pushsms.aspx";
		post = new PostMethod(sURL);
		// give all in string
		post.addParameter("user", gateWayUserName);
		post.addParameter("password", gateWayPassword);
		post.addParameter("msisdn", "91" + receiverAddress);
		post.addParameter("msg","Dear "+Ownername+", ");
		post.addParameter("sid", "CESCTX");
		post.addParameter("fl", "0");
		post.addParameter("GWID", "2");
		// PUSH the URL
		String sent = "";
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode != HttpStatus.SC_OK)
				BfmLogger.logger.error("Method failed: " + post.getStatusLine());
			else
				BfmLogger.logger.info("************* MESSAGE SENT SUCCESSFULY *************");
			BfmLogger.logger.info("statusCode --------" + statusCode);
			BfmLogger.logger.info("line 1 ------------"	+ post.getStatusLine().toString());
			BfmLogger.logger.info("SC_OK -------------" + HttpStatus.SC_OK);
			BfmLogger.logger.info("line 2 ------------"	+ post.getResponseBodyAsString());

			if (post.getResponseBodyAsString().contains("Failed"))
				BfmLogger.logger.info("Failed ------------" + sent);
			else
				BfmLogger.logger.info("Success ------------" + sent);

			sent = post.getResponseBodyAsString().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
	}
}
