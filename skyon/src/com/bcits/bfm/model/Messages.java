package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGES")
@NamedQueries({
	@NamedQuery(name = "Messages.userInbox",query="SELECT m FROM Messages m WHERE m.usr_id IN (:usrId) AND m.msg_status = :msgStatus ORDER BY m.lastUpdatedDate DESC")	,
	@NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m ORDER BY m.msg_id DESC"),
	@NamedQuery(name = "Messages.updateInboxAsTrash", query = "UPDATE Messages m SET m.msg_status = :msgStatus WHERE m.msg_id = :msgId"),
	@NamedQuery(name = "Messages.getReadStatusBasedOnId",query="SELECT m.read_status FROM Messages m WHERE m.msg_id = :msgId"),
	@NamedQuery(name = "Messages.updateReadStatus", query = "UPDATE Messages m SET m.read_status = :status WHERE m.msg_id = :msgId"),
	@NamedQuery(name = "Messages.getFromUsers",query="SELECT m.fromUser FROM Messages m WHERE m.usr_id IN (:userId) AND m.msg_status = :msgStatus"),
	@NamedQuery(name = "Messages.getToUsers",query="SELECT m.toUser FROM Messages m WHERE m.usr_id IN (:userId) AND m.msg_status = :msgStatus")	,
})

public class Messages {
	
	@Id
	@SequenceGenerator(name = "MESSAGES_SEQ", sequenceName = "MESSAGES_SEQ")
	@GeneratedValue(generator = "MESSAGES_SEQ")
	@Column(name="MSG_ID")
	private int msg_id;
	
	@Column(name="USR_ID")
	private String usr_id;
	
	@Column(name="FROMUSER")
	private String fromUser;
	
	@Column(name="TOUSER")
	private String toUser;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@Column(name="MESSAGE")
	private String message;
	
	@Column(name="MSG_STATUS")
	private String msg_status;
	
	@Column(name="READ_STATUS")
	private int read_status;
	
	@Column(name="CC")
	private String ccField;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDate;
	
	@Column(name = "NOTIFICATION_TYPE")
	private String notificationType;
	
	/*@Column(name="SELECT")
	private String select;*/
	
	//private String userName;
	
	/*@OneToOne
	@JoinColumn(name="TOUSER" , insertable = false, updatable = false, nullable = false)
	private Users users;
	*/
	
	/*
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}*/
	
	
	public int getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
	public String getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}
	
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getCcField() {
		return ccField;
	}
	public void setCcField(String ccField) {
		this.ccField = ccField;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	
	/*public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	*/
	
	
}
