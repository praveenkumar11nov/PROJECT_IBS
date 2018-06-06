package com.bcits.bfm.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class HelpDeskMailSender implements Runnable {
	
	private final String toAddressEmail;
	private final String messageContent;
	private final String helpDeskMailId;
	private final String helpDeskMailPwd;	
	private final String mailSmtpHost;
	private final String mailSmtpSocketFactoryPort;
	private final String mailSmtpSocketFactoryClass;
	private final String mailSmtpAuth;
	private final String mailSmtpPort;
	private final InternetAddress fromAddress;
	private final String mailSubject;
	private String mailsmtpstarttlsenable;
	private String mailsmtpsslenable;
	
	public HelpDeskMailSender(EmailCredentialsDetailsBean emailCredentialsDetailsBean) {
		this.toAddressEmail=emailCredentialsDetailsBean.getToAddressEmail();
		this.messageContent=emailCredentialsDetailsBean.getMessageContent();
		this.helpDeskMailId=emailCredentialsDetailsBean.getMailId();
		this.helpDeskMailPwd=emailCredentialsDetailsBean.getMailIdPwd();
		this.mailSmtpHost=emailCredentialsDetailsBean.getMailSmtpHost();
		this.mailSmtpSocketFactoryPort=emailCredentialsDetailsBean.getMailSmtpSocketFactoryPort();
		this.mailSmtpSocketFactoryClass=emailCredentialsDetailsBean.getMailSmtpSocketFactoryClass();
		this.mailSmtpAuth=emailCredentialsDetailsBean.getMailSmtpAuth();
		this.mailSmtpPort=emailCredentialsDetailsBean.getMailSmtpPort();
		this.fromAddress=emailCredentialsDetailsBean.getFromAddress();
		this.mailSubject=emailCredentialsDetailsBean.getMailSubject();
		this.mailsmtpstarttlsenable=emailCredentialsDetailsBean.getMailsmtpstarttlsenable();
		this.mailsmtpsslenable=emailCredentialsDetailsBean.getMailsmtpsslenable();
	}
	
	@Override
	public void run()
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", mailSmtpHost);
		props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
		props.put("mail.smtp.socketFactory.class",mailSmtpSocketFactoryClass);
		props.put("mail.smtp.auth", mailSmtpAuth);
		props.put("mail.smtp.port", mailSmtpPort);
		
		
		
		Properties props1 = new Properties();
	       props1.put("mail.transport.protocol", "smtps");
	       props1.put("mail.smtps.host", mailSmtpHost);
	       props1.put("mail.smtps.auth", mailSmtpAuth);
	       props1.put("mail.smtp.starttls.enable", mailsmtpstarttlsenable);
	       props1.put("mail.smtp.ssl.enable", mailsmtpsslenable);
	       
	       
	       Session mailSession = Session.getDefaultInstance(props1);	     
	       Transport transport;
	   
		try {
			transport = mailSession.getTransport();
			
			System.out.println("************************    Host : " + mailSmtpHost   	 + "************************");
			System.out.println("************************    Port : " + mailSmtpPort  	 + "************************");
			System.out.println("************************    Port : " + helpDeskMailPwd   + "************************");
			System.out.println("************************    From : " + helpDeskMailId    + "************************");
			System.out.println("************************      To : " + toAddressEmail 	 + "************************");
			System.out.println("************************ Subject : " + mailSubject       + "************************");
			System.out.println("************************ Content : " + messageContent    + "************************");
			
	       MimeMessage message = new MimeMessage(mailSession);
	       
	       message.setSubject(mailSubject);
	       Multipart mp = new MimeMultipart();
	       MimeBodyPart htmlPart = new MimeBodyPart();
	       htmlPart.setContent(messageContent, "text/html; charset=utf-8");
	       mp.addBodyPart(htmlPart);	    	
	       message.setContent(mp);	
	       Address[] from = InternetAddress.parse(helpDeskMailId);//Your domain email
	       message.addFrom(from);
	       message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddressEmail)); //Send email To (Type email ID that you want to send)
	       transport.connect(mailSmtpHost, Integer.parseInt(mailSmtpPort), helpDeskMailId, helpDeskMailPwd);
	       transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
	       transport.close();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	/*	Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(helpDeskMailId,helpDeskMailPwd);
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddressEmail));
			message.setSubject(mailSubject);
			message.setText("Hi "+openNewTicketService.getPersons(newTicketEntity.getPersonId()).getFirstName()+",\n"+"\tYour ticket "+newTicketEntity.getTicketNumber()+" created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" is in "+ openNewTicketService.getDepartements(newTicketEntity.getDept_Id()).getDept_Name()+" department.");
            
			message.setContent(messageContent,"text/html; charset=ISO-8859-1");				
			
			Transport.send(message); 
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}*/
	}
}
