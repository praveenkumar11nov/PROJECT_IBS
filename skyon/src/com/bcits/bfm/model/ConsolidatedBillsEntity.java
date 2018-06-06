package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;
@Entity
@Table(name="CONSOLIDATED_BILLS")  
@NamedQueries({
	
})
public class ConsolidatedBillsEntity {

	@Id
	@Column(name="CB_ID")
	@SequenceGenerator(name = "consolidatedBillsSeq", sequenceName = "CONSOLIDATED_BILLS_SEQ") 
	@GeneratedValue(generator = "consolidatedBillsSeq")
	private int cbId;
	
	@Column(name="CB_DATE")
	private Date consolidatedDate;
	
	@Column(name="CB_BILL_MONTH")
	private Date billMonth;
	
	@Column(name="CB_AMOUNT")
	private double billAmount;
	
	@Column(name="CB_DUE_DATE")
	private Date billDueDate;
	
	@Column(name="CB_AGE")
	private int age;
	
	@Column(name="CB_PAYMENT")
	private double payments;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getCbId() {
		return cbId;
	}


	public void setCbId(int cbId) {
		this.cbId = cbId;
	}


	public Date getConsolidatedDate() {
		return consolidatedDate;
	}


	public void setConsolidatedDate(Date consolidatedDate) {
		this.consolidatedDate = consolidatedDate;
	}


	public Date getBillMonth() {
		return billMonth;
	}


	public void setBillMonth(Date billMonth) {
		this.billMonth = billMonth;
	}


	public double getBillAmount() {
		return billAmount;
	}


	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}


	public Date getBillDueDate() {
		return billDueDate;
	}


	public void setBillDueDate(Date billDueDate) {
		this.billDueDate = billDueDate;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public double getPayments() {
		return payments;
	}


	public void setPayments(double payments) {
		this.payments = payments;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}


	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}


	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
}
