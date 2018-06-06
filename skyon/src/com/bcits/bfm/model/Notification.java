package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CP_NOTIFICATION_TEMP")
@NamedQueries({
	
	@NamedQuery(name="Notification.findAll",query="SELECT n FROM Notification n WHERE n.user_id=:userId "),
	@NamedQuery(name="Notification.UpdateNotificationStatus",query="UPDATE Notification m SET m.read_status = :i WHERE m.cpNotId = :msgId"),

	@NamedQuery(name="Notification.userInbox",query="SELECT n FROM Notification n WHERE n.user_id IN(:userId) ORDER BY n.cpNotId DESC"),
	@NamedQuery(name="Notification.unReadMessages",query="SELECT n FROM Notification n WHERE n.user_id IN(:userId) AND n.read_status=:read_status ORDER BY n.cpNotId DESC"),
	
})

public class Notification {

	@Id
	@SequenceGenerator(name = "CPNOTIFY_SEQ", sequenceName = "CP_NOTIFICATION_SEQ")
	@GeneratedValue(generator = "CPNOTIFY_SEQ")
	@Column(name="CP_NOTIF_ID")
	private int cpNotId;
	
	@Column(name="USER_ID")
	private String user_id;
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Column(name="MESSAGES")
	private String message;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@Column(name="FROM_USER")
	private String fromUser;
	
	@Column(name="CP_NOTIF_STATUS")
	private String msg_status;
	
	@Column(name="READ_STATUS")
	private int read_status;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDate = new Timestamp(new Date().getTime());

	public int getCpNotId() {
		return cpNotId;
	}

	public void setCpNotId(int cpNotId) {
		this.cpNotId = cpNotId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getMsg_status() {
		return msg_status;
	}

	public void setMsg_status(String msg_status) {
		this.msg_status = msg_status;
	}

	public int getRead_status() {
		return read_status;
	}

	public void setRead_status(int read_status) {
		this.read_status = read_status;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@PrePersist
	 protected void onCreate() {
		
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDate = new Timestamp(date.getTime());
	 }
	
	
}
