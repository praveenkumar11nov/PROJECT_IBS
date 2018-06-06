package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="PREPAID_METER_HISTORY")
@NamedQueries({
	@NamedQuery(name="OldMeterHistoryEntity.readMeterData",query="SELECT om FROM OldMeterHistoryEntity om ORDER BY om.hid DESC")
})

public class OldMeterHistoryEntity {

	@Id
	@SequenceGenerator(name="PREPAID_METER_HISTORY_SEQ",sequenceName="PREPAID_METER_HISTORY_SEQ" )
	@GeneratedValue(generator="PREPAID_METER_HISTORY_SEQ")
	@Column(name="OMID")
	private int hid;
	
	@Column(name="PROPERTY_NO")
	private String propertyNo;
	

	@Column(name="PERSON_NAME")
	private String personName;
	
	@Column(name="METER_NUMBER")
	private String meterNumber;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Column(name="EB_INITIAL_READING")
	private double initailReading;
	
	@Column(name="SERVICE_START_DATE")
	private Date serviceStartDate;
	
	@Column(name="DG_READING")
	private double dgReading;
	
	@Column(name="BALANCE")
	private double balance;
	
	@Column(name="SERVICE_END_DATE")
	private Date serviceEndDate;
	
	@Column(name="COUNSUMER_ID")
	private String consumerId;

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	
	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
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

	public double getInitailReading() {
		return initailReading;
	}

	public void setInitailReading(double initailReading) {
		this.initailReading = initailReading;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public double getDgReading() {
		return dgReading;
	}

	public void setDgReading(double dgReading) {
		this.dgReading = dgReading;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	
	

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
}
