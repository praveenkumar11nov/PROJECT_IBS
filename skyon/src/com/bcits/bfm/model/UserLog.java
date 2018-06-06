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

/**
 * UserLog entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USER_LOG")
@NamedQueries({ 
		@NamedQuery(name="getAllUserDetails",query="select u from UserLog u where u.urLoginName=:userName order by u.ulId desc"),
		@NamedQuery(name="updateUserdetails",query="update UserLog u set u.logoutMethod=:logoutmethod ,u.ulSessionEnd=:sessionEnd,u.duration=:duration where u.ulSessionId=:sessionId "),
		@NamedQuery(name="UserLogFields",query="select ul.ulId,ul.urLoginName,ul.ulSessionStart,ul.ulSessionEnd,ul.previousLoginRole from UserLog ul where ul.urLoginName=:userName order by ul.ulId desc"),
		@NamedQuery(name="UserLog.getCount",query="select COUNT(*) from UserLog u where u.urLoginName=:userName"),

})

public class UserLog implements java.io.Serializable {

	

	// Fields
	@Id
	@SequenceGenerator(name = "USER_LOG_SEQ", sequenceName = "USER_LOG_SEQ")
	@GeneratedValue(generator = "USER_LOG_SEQ")
	@Column(name = "UL_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int ulId;
	
	
	@Column(name = "UL_SESSION_START", length = 7)
	private Timestamp ulSessionStart;
	
	
	@Column(name = "UL_SESSION_END", length = 7)
	private Timestamp ulSessionEnd;
	
	@Column(name = "UL_SESSION_ID", nullable = false, length = 45)
	private String ulSessionId;
	
	@Column(name = "UR_LOGIN_NAME", nullable = false, length = 45)
	private String urLoginName;
	
	@Column(name = "LOGOUT_METHOD", length = 45)
	private String logoutMethod;
	
	@Column(name = "PREVIOUS_LOGIN_ROLE", nullable = false, length = 45)
	private String previousLoginRole;
	
	@Column(name = "STATUS", length = 45)
	private String status;
	
	@Column(name = "SYSTEM_IP", length = 45)
	private String systemIp;
	
	@Column(name = "DURATION", length = 45)
	private String duration;

	// Constructors

	/** default constructor */
	public UserLog() {
	}

	/** minimal constructor */
	public UserLog(int ulId, String ulSessionId,Timestamp ulSessionStart,  String urLoginName,
			String previousLoginRole,String status,String systemIp,String duration ) {
		this.ulId = ulId;
		this.ulSessionId = ulSessionId;
		this.urLoginName = urLoginName;
		this.previousLoginRole = previousLoginRole;
		this.status=status;
		this.ulSessionStart=ulSessionStart;
		this.systemIp=systemIp;
		this.duration=duration;
	}

	public UserLog(Timestamp ulSessionEnd, String ulSessionId, String logoutMethod) {
		super();
		this.ulSessionEnd = ulSessionEnd;
		this.ulSessionId = ulSessionId;
		this.logoutMethod = logoutMethod;
	}

	// Property accessors
	
	public int getUlId() {
		return this.ulId;
	}

	public void setUlId(int ulId) {
		this.ulId = ulId;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getSystemIp() {
		return this.systemIp;
	}
	
	public void setSystemIp(String systemIp) {
		this.systemIp = systemIp;
	}

	
	public Timestamp getUlSessionStart() {
		return this.ulSessionStart;
	}

	public void setUlSessionStart(Timestamp ulSessionStart) {
		this.ulSessionStart = ulSessionStart;
	}

	
	public Timestamp getUlSessionEnd() {
		return this.ulSessionEnd;
	}

	public void setUlSessionEnd(Timestamp ulSessionEnd) {
		this.ulSessionEnd = ulSessionEnd;
	}

	
	public String getUlSessionId() {
		return this.ulSessionId;
	}

	public void setUlSessionId(String ulSessionId) {
		this.ulSessionId = ulSessionId;
	}

	
	public String getUrLoginName() {
		return this.urLoginName;
	}

	public void setUrLoginName(String urLoginName) {
		this.urLoginName = urLoginName;
	}

	
	public String getLogoutMethod() {
		return this.logoutMethod;
	}

	public void setLogoutMethod(String logoutMethod) {
		this.logoutMethod = logoutMethod;
	}

	
	public String getPreviousLoginRole() {
		return this.previousLoginRole;
	}

	public void setPreviousLoginRole(String previousLoginRole) {
		this.previousLoginRole = previousLoginRole;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}