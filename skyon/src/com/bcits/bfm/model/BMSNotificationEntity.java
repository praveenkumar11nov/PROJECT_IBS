package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "BMS_NOTIFICATION")
@NamedQueries({
	@NamedQuery(name = "BMSNotificationEntity.findAll", query = "SELECT bms.bmsNotifyId,bms.deptId,dpt.dept_Name,bms.dnId,d.dn_Name,bms.bmsElements,bms.notifyDate,bms.notifyStatus,bms.smsStatus,bms.mailStatus,bms.personList FROM BMSNotificationEntity bms INNER JOIN bms.designation d INNER JOIN d.department dpt ORDER BY bms.bmsNotifyId DESC"),

})
public class BMSNotificationEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BMS_NOTIFICATION_SEQ", sequenceName = "BMS_NOTIFICATION_SEQ")
	@GeneratedValue(generator = "BMS_NOTIFICATION_SEQ")
	@Column(name = "BMS_NTID")
	private int bmsNotifyId;
		
	@Column(name = "DEPT_ID", precision = 10, scale = 0)
	private int deptId;

	@Column(name = "DN_ID", precision = 10, scale = 0)
	private int dnId;
	
	@Column(name="TRENDLOG_NAME")
	private String bmsElements;
	
	@Column(name="NOTIFY_DATE")
	private Timestamp notifyDate;
	
	@Column(name="NOTIFY_STATUS")
	private String notifyStatus;
	
	@Column(name="SMS_STATUS")
	private String smsStatus;
	
	@Column(name="MAIL_STATUS")
	private String mailStatus;

	@OneToOne
	@JoinColumn(name = "DN_ID", insertable = false, updatable = false, nullable = false)
	private Designation designation;

	@OneToOne
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
	private Department department;
	
	@Column(name="PERSON_LIST")
	private String personList;
	
	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getBmsNotifyId() {
		return bmsNotifyId;
	}

	public void setBmsNotifyId(int bmsNotifyId) {
		this.bmsNotifyId = bmsNotifyId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getDnId() {
		return dnId;
	}

	public void setDnId(int dnId) {
		this.dnId = dnId;
	}

	public String getBmsElements() {
		return bmsElements;
	}

	public void setBmsElements(String bmsElements) {
		this.bmsElements = bmsElements;
	}

	public Timestamp getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Timestamp notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getPersonList() {
		return personList;
	}

	public void setPersonList(String personList) {
		this.personList = personList;
	}
	
	
}
