package com.bcits.bfm.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="BILLING_PAYMENT_HELPDESK")
@NamedQueries({
	@NamedQuery(name="BillingAndPaymentHelpDesk.readAllData",query="select b.id, b.propertyNo,b.person_Name,b.helpTopic,b.issue_Details,b.otherHelpTopic,b.createdDate,b.status,b.remarks,b.reSolvedDate,b.mobileNo,b.emailId  from BillingAndPaymentHelpDesk b ")
})
public class BillingAndPaymentHelpDesk {

	@Id
	@SequenceGenerator(name = "billingPaymentHelpDesk", sequenceName = "BILLING_PAYMENT_HELPDESK_SEQ")
	@GeneratedValue(generator = "billingPaymentHelpDesk")

	@Column(name="ID", unique=true, nullable=false, precision=11, scale=0)
	private int id;
	
	@Column(name="PROPERTY_NO")
	private String propertyNo;
	
	@Column(name="PERSON_NAME")
	private String person_Name;
	
	@Column(name="HELP_TOPIC")
	private String helpTopic;
	
	@Column(name="ISSUE_DETAILS")
	private String issue_Details;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="OTHER_HELP_TOPIC")
	private String otherHelpTopic;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="RESOLVED_DATE")
	private Date reSolvedDate;
	
	@Column(name="MOBILE_NO")
	private String mobileNo;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPropertyNo() {
		return propertyNo;
	}
	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}
	public String getPerson_Name() {
		return person_Name;
	}
	public void setPerson_Name(String person_Name) {
		this.person_Name = person_Name;
	}
	
	
	public String getHelpTopic() {
		return helpTopic;
	}
	public void setHelpTopic(String helpTopic) {
		this.helpTopic = helpTopic;
	}
	public String getIssue_Details() {
		return issue_Details;
	}
	public void setIssue_Details(String issue_Details) {
		this.issue_Details = issue_Details;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getOtherHelpTopic() {
		return otherHelpTopic;
	}
	public void setOtherHelpTopic(String otherHelpTopic) {
		this.otherHelpTopic = otherHelpTopic;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getReSolvedDate() {
		return reSolvedDate;
	}
	public void setReSolvedDate(Date reSolvedDate) {
		this.reSolvedDate = reSolvedDate;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "BillingAndPaymentHelpDesk [id=" + id + ", propertyNo="
				+ propertyNo + ", person_Name=" + person_Name + ", helpTopic="
				+ helpTopic + ", issue_Details=" + issue_Details + ", status="
				+ status + ", remarks=" + remarks + ", otherHelpTopic="
				+ otherHelpTopic + ", createdDate=" + createdDate
				+ ", reSolvedDate=" + reSolvedDate + "]";
	}
	
	
}
