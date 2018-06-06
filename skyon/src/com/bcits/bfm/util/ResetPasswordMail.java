package com.bcits.bfm.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ResetPasswordMail implements Runnable {
	
	private String receiverAddress;
	private String userId;
	private String password;
	private String messageSubject;
	private String hostIp;
	private String portNo;
	private String version;
	private String personName;
	//private String senderEmailId;
	//private String senderPassword;	
	
	
	public ResetPasswordMail(String receiverAddress, String userId, String password,
			String messageSubject, String portNo, String version, String hostIp, String personName) {
		super();
		this.receiverAddress = receiverAddress;
		this.userId = userId;
		this.password = password;
		this.messageSubject = messageSubject;
		this.hostIp=hostIp;
		this.portNo=portNo;
		this.version=version;
		this.personName = personName;
	}
	
	@Override
	public void run()
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"operatorireo@gmail.com", "ireo_123");
						
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@ireo.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiverAddress));

			message.setSubject(messageSubject);
			/*
			 * message.setContent("<html><body>UserName:" + userId +
			 * "<br/>Password:" + password + "<br/><a href=" +
			 * "http://192.168.10.141:8080/bfm" + ">click</a></body></html>",
			 * "text/html; charset=ISO-8859-1");
			 */

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
							+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">IREO ADMINISTRATION DEPARTMENT.</h2> <br /> Dear "+ personName + ", Welcome to IREO <br/> <br/> "
							+ "Your Password has been successfully reset, Please use following UserName and Password to login. <br/> <br/>"
							+ "<table class=\"hovertable\" >" + "<tr>"
							+ "<td colspan='2'>Your Account Details are</td>"
							+ "</tr>" + "<tr>" + "<td> User Name :</td>"
							+ "<td>" + userId + "</td>" +

							"</tr>" + "<tr>" + "<td> Password:</td>" + "<td>"
							+ password + "</td>" + "</tr>" + "</table>"
							+ "<br/><br/>"
							/*+ "<a href=http://192.168.10.141:8080/bfm"*/
							+ "<a href=http://"+ hostIp +":"+ portNo +"/"+ version
							+ "><u>click here to login</u></a></body></html>"
							+ "<br/><br/>"
							+ "<br/>Thanks,<br/>"
							+ "IREO Administration Services. <br/> <br/>",
					"text/html; charset=ISO-8859-1");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException((e));
		}
		
	}


}
