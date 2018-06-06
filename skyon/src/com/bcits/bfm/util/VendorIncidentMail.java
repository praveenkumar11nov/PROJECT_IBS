package com.bcits.bfm.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorIncidentMail implements Runnable 
{
	static Logger logger = LoggerFactory.getLogger(VendorIncidentMail.class);
	
	private final String receiverAddress;
	private final String fullName;
	private final String contractName;
	private final String contractNumber;
	
	
	public VendorIncidentMail(String receiverAddress,String fullName,String contractName,String contractNumber)
	{
		this.receiverAddress = receiverAddress;
		this.fullName = fullName;		
		this.contractName = contractName;
		this.contractNumber = contractNumber;		
	}
	@Override
	public void run() 
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator()
		{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication("operatorireo@gmail.com", "ireo_123");
					}
				});
				  try
				  {

					  
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@ireo.com"));		
			
			
			logger.info("Receiver Address"+receiverAddress);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverAddress));

			message.setSubject("Vendor Incident Alert");
			//message.setContent("HELLO PERSON", receiverAddress);
			
			message.setContent(
						"<html>"						   
							+ "<style type=\"text/css\">"
							+ "table.hovertable {"
							+ "font-family: verdana,arial,sans-serif;"
							+ "font-size:11px;"
							+ "color:#333333;"
							+ "border-width: 1px;"
							+ "border-color: #999999;"
							+ "border-collapse: collapse;"
							+ "}"
							+ "table.hovertable th {"
							+ "background-color:#c3dde0;"
							+ "border-width: 1px;"
							+ "padding: 8px;"
							+ "border-style: solid;"
							+ "border-color: #394c2e;"
							+ "}"
							+ "table.hovertable tr {"
							+ "background-color:#88ab74;"
							+ "}"
							+ "table.hovertable td {"
							+ "border-width: 1px;"
							+ "padding: 8px;"
							+ "border-style: solid;"
							+ "border-color: #394c2e;"
							+ "}"
							+ "</style>"
							+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Vendor Incident.</h2> <br /> Dear "+fullName+", <br/> <br/> "
							+"This email is to inform you about the Vendor service for the Contract <br/>Contract Name : <b>"+contractName+"</b><br/>"
							+"Contract Number : <b>"+contractNumber+"</b><br/>"
							+ "could not meet our expectations  <br/><br/>"
							+"Thank you<br/><br/>"
							
							+ "<br/>Sincerely,<br/><br/>"
							+ "Manager,<br/>"
							+"IREO Admin Services,<br/>"
							+"<br/> This is an auto generated Email.Please dont revert back"
							+"</body></html>",
							"text/html; charset=ISO-8859-1");
			
			
			Transport.send(message);
			logger.info("EMAIL SENT SUCCESSFULLY FROM MAIL CLASS");
	}
				  catch(Exception e){
					  e.printStackTrace();
				  }
	}
}
