package com.bcits.bfm.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JobCompletionNotificationMail implements Runnable {
	
	private final String receiverAddress;
	private final String Ownername;
	private final String dateOfcompletion;	
	private final int jobSla;
	private final String jobName;
	private final String jobNumber;	


	public JobCompletionNotificationMail(String receiverAddress, String ownername,String dateOfcompletion, int jobSla, String jobName, String jobNumber) {
		super();
		this.receiverAddress = receiverAddress;
		this.Ownername = ownername;
		this.dateOfcompletion = dateOfcompletion;
		this.jobSla = jobSla;
		this.jobName = jobName;
		this.jobNumber = jobNumber;
	}

	@Override
	public void run() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						"operatorireo@gmail.com", " ireo_123");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@ireo.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverAddress));

			message.setSubject("Job Notification");

			message.setContent(
					"<html>"
							+ "<style type=\"text/css\">"
							+ "table.hovertable {"
							+ "font-family: verdana,arial,sans-serif;"
							+ "font-size:11px;"
							+ "color:#333333;"
							+ "border-width: 1px;"
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
							+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">IREO MAINTENACE MANAGEMENT.</h2> <br/><br /> Dear "
							+ Ownername
							+ ",<br/> <br/> "
							+ "Please be advised that the Job Assigned to you is not completed whithin Expected SLA. <br/> <br/>"
							+ "<table class=\"hovertable\">"
							+ "<tr>"
							+ "<td colspan='2'>Your Job Details are</td>"
							+ "</tr><tr></tr>"
							+ "<tr>"
							+ "<td> Job Number </td>"
							+ "<td>"
							+ jobNumber
							+ "</td>"
							+

							"</tr>"
							+ "<tr>"
							+ "<td> Job Name </td>"
							+ "<td>"
							+ jobName
							+ "</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td> Job Expected SLA </td>"
							+ "<td>"
							+ jobSla
							+ "</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td> Job SLA </td>"
							+ "<td>"
							+ dateOfcompletion
							+ "</td>"
							+ "</tr>"
							+ "</table>"
							+ "<br/><br/>"
							+ "</body></html>"
							+ "<br/><br/>"
							+ "<br/>Thanks,<br/>"
							+ "IREO MAINTENACE MANAGEMENT. <br/> <br/> This is Auto Generated Mail, Please Don't Revert Back",
					"text/html; charset=ISO-8859-1");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException((e));
		}

	}

}
