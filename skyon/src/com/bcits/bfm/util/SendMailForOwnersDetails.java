package com.bcits.bfm.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMailForOwnersDetails implements Runnable {
	
	static Logger logger = LoggerFactory.getLogger(SendMailForOwnersDetails.class);
	
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
	private final String mailImapHost;
	private final String mailImapPort;
	private final String mailImapProtocol;
	private final String mailSubject;
	
	public SendMailForOwnersDetails(EmailCredentialsDetailsBean emailCredentialsDetailsBean) {
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
		this.mailImapHost=emailCredentialsDetailsBean.getMailImapHost();
		this.mailImapPort=emailCredentialsDetailsBean.getMailImapPort();
		this.mailImapProtocol=emailCredentialsDetailsBean.getMailImapProtocol();
		this.mailSubject=emailCredentialsDetailsBean.getMailSubject();
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
		props.put("mail.imap.host", mailImapHost);
		props.put("mail.imap.port", mailImapPort);
		props.put("mail.store.protocol", mailImapProtocol);
	System.out.println("mailSmtpHost:::"+mailSmtpHost+" :::::ailSmtpAuth:::"+mailSmtpAuth);	
		
		Properties props1 = new Properties();
	       props1.put("mail.transport.protocol", "smtp");
	       props1.put("mail.smtps.host", mailSmtpHost);
	       props1.put("mail.smtps.auth", mailSmtpAuth);
	       
	       Session mailSession = Session.getDefaultInstance(props1);
	      // mailSession.setDebug(true);
	       Transport transport;
	  
		try {
			transport = mailSession.getTransport();
			int imapPort=Integer.parseInt(mailImapPort);
	       MimeMessage message = new MimeMessage(mailSession);
	       
	       message.setSubject(mailSubject);
	       Multipart mp = new MimeMultipart();
	       MimeBodyPart htmlPart = new MimeBodyPart();
	       htmlPart.setContent(messageContent, "text/html; charset=utf-8");
	       mp.addBodyPart(htmlPart);
	       
	     
	      // message.setContent(messageContent,"text/html; charset=ISO-8859-1");	
	       message.setContent(mp);	
	       Address[] from = InternetAddress.parse(helpDeskMailId);//Your domain email
	       message.addFrom(from);
	       message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddressEmail)); //Send email To (Type email ID that you want to send)

	       transport.connect(mailSmtpHost, Integer.parseInt(mailSmtpPort), helpDeskMailId, helpDeskMailPwd);
	       transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
	       Store store = mailSession.getStore(mailImapProtocol);
	       logger.info("store is opening");
			logger.info("*************Imap Host*******"+mailImapHost);
			logger.info("***********Imap Port*********"+mailImapPort);
			logger.info("************MailId**********"+helpDeskMailId);
			logger.info("************MailPwd**********"+helpDeskMailPwd);
			store.connect(mailImapHost.trim(),imapPort,helpDeskMailId.trim(), helpDeskMailPwd.trim());
			logger.info("connected");
			Message [] messages = new Message[1];
			messages[0] = message;
			Folder folder = store.getFolder("Sent Items");
			
			folder.open(Folder.READ_WRITE);
			logger.info("open the Folder");
			folder.appendMessages(messages);
			logger.info("message sent to Sent Items Folder");
			folder.close(false);
			store.close();
	       transport.close();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(helpDeskMailId,helpDeskMailPwd);
				}
			});
 
		try {
			int imapPort=Integer.parseInt(mailImapPort);
			Message message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddressEmail));
			
			message.setSubject(mailSubject);
			message.setContent(messageContent,"text/html; charset=ISO-8859-1");				
			Transport.send(message); 
			
	        Store store = session.getStore(mailImapProtocol);
			logger.info("store is opening");
			logger.info("*************Imap Host*******"+mailImapHost);
			logger.info("***********Imap Port*********"+mailImapPort);
			logger.info("************MailId**********"+helpDeskMailId);
			logger.info("************MailPwd**********"+helpDeskMailPwd);
			store.connect(mailImapHost.trim(),imapPort,helpDeskMailId.trim(), helpDeskMailPwd.trim());
			logger.info("connected");
			Message [] messages = new Message[1];
			messages[0] = message;
			Folder folder = store.getFolder("Sent Items");
			
			folder.open(Folder.READ_WRITE);
			logger.info("open the Folder");
			folder.appendMessages(messages);
			logger.info("message sent to Sent Items Folder");
			folder.close(false);
			store.close();
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}*/
	}

	public String getMailSubject() {
		return mailSubject;
	}
}


