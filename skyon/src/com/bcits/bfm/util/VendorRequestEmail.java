package com.bcits.bfm.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorRequestEmail implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(MailRoomMessenger.class);
	private final String receiverAddress;
	private final String username;
	
	public VendorRequestEmail(String receiverAddress, String username) 
	{
		super();
		System.out.println("***************\n\n"+receiverAddress+username);
		this.receiverAddress = receiverAddress;
		this.username = username;
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
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverAddress));
			message.setSubject("Vendor request Notification");
			
			message.setContent("HELLO", username);
			/*message.setContent("<html>"
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Vendor Request Notifiaction.</h2> <br /> Dear "+username+", <br/> <br/> "
								+"Good day. This email is to inform you that your request will be processed shortly <b>"+"</b><br/>"								
								+"Thank you<br/><br/>"
								
								+ "<br/>Regards,<br/><br/>"
								+ "IREO Admin,<br/>"
								+"IREO<br/><br/> This is an auto generated Email.Please dont revert back"
								+"</body></html>",
						"text/html; charset=ISO-8859-1");*/
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException((e));
		}
		
	}
}
