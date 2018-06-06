package com.bcits.bfm.util;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

@Component
public class EmailCredentialsDetailsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailCredentialsDetailsBean()throws Exception{
		
	}
	
	private String toAddressEmail;
	
	private String messageContent;
	
	private String mailSubject;
	
	private final String mailId = ResourceBundle.getBundle("utils").getString("Email.gateWay.username").trim();
	
	private final String mailIdPwd = ResourceBundle.getBundle("utils").getString("Email.gateWay.password").trim();
	
	private final String mailSmtpHost = ResourceBundle.getBundle("utils").getString("mail.smtp.host");
	
	private final String mailsmtpstarttlsenable=ResourceBundle.getBundle("utils").getString("mail.smtp.starttls.enable");
	
	private final String mailsmtpsslenable=ResourceBundle.getBundle("utils").getString("mail.smtp.ssl.enable");
	
	
	public String getMailsmtpsslenable() {
		return mailsmtpsslenable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMailsmtpstarttlsenable() {
		return mailsmtpstarttlsenable;
	}

	private final String mailSmtpSocketFactoryPort = ResourceBundle.getBundle("utils").getString("mail.smtp.socketFactory.port");
	
	private final String mailSmtpSocketFactoryClass = ResourceBundle.getBundle("utils").getString("mail.smtp.socketFactory.class");
	
	private final String mailSmtpAuth = ResourceBundle.getBundle("utils").getString("mail.smtp.auth");
	
	private final String mailSmtpPort = ResourceBundle.getBundle("utils").getString("mail.smtp.port");
	
	private final String mailImapPort = ResourceBundle.getBundle("utils").getString("mail.imap.port");

	private final String mailImapHost = ResourceBundle.getBundle("utils").getString("mail.imap.host");

	private final String mailImapProtocol = ResourceBundle.getBundle("utils").getString("mail.store.protocol");
	
	private final InternetAddress fromAddress = new InternetAddress(mailId.trim(),ResourceBundle.getBundle("utils").getString("Email.gateWay.username"));
	
	private final String websiteUrl = ResourceBundle.getBundle("utils").getString("applicationWebsiteUrl");
	
	private final String websitePortalUrl = ResourceBundle.getBundle("utils").getString("applicationWebsitePortalUrl");

	public String getToAddressEmail() {
		return toAddressEmail;
	}

	public void setToAddressEmail(String toAddressEmail) {
		this.toAddressEmail = toAddressEmail;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public String getMailId() {
		return mailId.trim();
	}

	public String getMailIdPwd() {
		return mailIdPwd;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public String getMailSmtpSocketFactoryPort() {
		return mailSmtpSocketFactoryPort;
	}

	public String getMailSmtpSocketFactoryClass() {
		return mailSmtpSocketFactoryClass;
	}

	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public InternetAddress getFromAddress() {
		return fromAddress;
	}

	public String getMailImapPort() {
		return mailImapPort;
	}

	public String getMailImapHost() {
		return mailImapHost;
	}

	public String getMailImapProtocol() {
		return mailImapProtocol;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public String getWebsitePortalUrl() {
		return websitePortalUrl;
	}


	
}
