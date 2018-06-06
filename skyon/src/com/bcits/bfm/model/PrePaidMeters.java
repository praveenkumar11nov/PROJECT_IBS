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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;
@Entity
@Table(name="PREPAID_METERS")
@NamedQueries({
	@NamedQuery(name = "PrePaidMeters.getPropertyNo", query = "select p.property_No from Property p Where p.propertyId =:propertyId"),
	@NamedQuery(name = "PrePaidMeters.getBlockName",query="SELECT  b.blockName FROM Property p INNER JOIN p.blocks b  Where b.blockId=p.blockId AND p.propertyId =:propertyId"),
    @NamedQuery(name = "PrePaidMeters.FindAll",query="SELECT p.mId,p.personId,p.propertyId,p.meterNumber,p.initailReading,p.readingDate,p.dgReading,p.balance,p.consumerId FROM PrePaidMeters p ORDER BY p.mId DESC"),
    @NamedQuery(name="PrePaidMeters.getAccountId",query="SELECT a.accountId FROM Account a Where a.propertyId =:propertyId"),
    @NamedQuery(name="PrePaidMeters.getOwnerDetails",query="select DISTINCT p.firstName,p.lastName from Person p Where p.personId =:personId AND p.personStatus = 'Active'"),
	@NamedQuery(name="PrePaidMeters.getmeterNo",query="select p.meterNumber from PrePaidMeters p"),
	@NamedQuery(name="PrePaidMeters.getPersonId",query="select p.personId from PrePaidMeters p where p.propertyId =:propertyId"),
	@NamedQuery(name="PrePaidMeters.getAllProp",query="SELECT DISTINCT(p.propertyId),p.property_No FROM Property p ,PrePaidMeters pp  where p.propertyId=pp.propertyId AND p.blockId=:blockId"),
    @NamedQuery(name="PrePaidMeters.getInitialReading",query="SELECT p.initailReading FROM PrePaidMeters p where p.meterNumber=:mtrSrNo"),
	@NamedQuery(name="PrePaidMeters.getMtrNo",query="select p.meterNumber from PrePaidMeters p where p.mId!=:prepaidId"),
	@NamedQuery(name="PrePaidMeters.getReadingDate",query="select p.readingDate,p.initailReading,p.dgReading,p.balance from PrePaidMeters p where p.propertyId=:propertyId"),
	
	
	@NamedQuery(name="PrePaidMeters.getPropOnlyforChargesCalcu",query="SELECT DISTINCT(p.propertyId),p.property_No FROM Property p ,PrePaidMeters pp ,PrepaidCalcuStoreEntity pc where p.propertyId=pp.propertyId AND pc.propertyId=p.propertyId  AND p.blockId=:blockId"),
	@NamedQuery(name="PrePaidMeters.getPropOnlyforBill",query="SELECT DISTINCT(p.propertyId),p.property_No FROM Property p ,PrePaidMeters pp ,PrepaidBillDetails pb where p.propertyId=pp.propertyId AND pb.propertyId=p.propertyId  AND p.blockId=:blockId"),
	@NamedQuery(name="PrePaidMeters.checkConsumerId",query="SELECT count(p) FROM PrePaidMeters p Where p.consumerId=:consumerId"),
	@NamedQuery(name="PrePaidMeters.getConsumerIds",query="select p.consumerId from PrePaidMeters p where p.mId!=:prepaidId"),
	@NamedQuery(name="PrePaidMeters.getGenusData",query="SELECT DISTINCT(p.propertyId),p.property_No FROM Property p ,PrePaidMeters pp  where p.propertyId=pp.propertyId  AND p.blockId=:blockId")



})
public class PrePaidMeters {
	@Id
    @SequenceGenerator(name="PREPAID_METERS_SEQ",sequenceName="PREPAID_METERS_SEQ")
	@GeneratedValue(generator="PREPAID_METERS_SEQ")
	@Column(name="MID", unique=true, nullable=false, precision=11, scale=0)
	private int mId;
	
	
	@Column(name="PROPERTY_ID")
	private int propertyId;
	

	@Column(name="PERSON_ID")
	private int personId;
	
	@Column(name="METER_NUMBER")
	private String meterNumber;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Column(name="INITIAL_READING")
	private double initailReading;
	
	public double getInitailReading() {
		return initailReading;
	}

	public void setInitailReading(double initailReading) {
		this.initailReading = initailReading;
	}

	@Column(name="READING_DATE")
	private Date readingDate;
	
	@Column(name="DG_READING")
	private double dgReading;
	
	@Column(name="BALANCE")
	private double balance;
    
	@Column(name="CONSUMER_ID")
	private String consumerId;
	
	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
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

	


	public Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
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

	@Override
	public String toString() {
		return "PrePaidMeters [mId=" + mId + ", propertyId=" + propertyId + ", personId=" + personId + ", meterNumber="
				+ meterNumber + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDT="
				+ lastUpdatedDT + ", initailReading=" + initailReading + ", readingDate=" + readingDate + ", dgReading="
				+ dgReading + ", balance=" + balance + ", consumerId=" + consumerId + "]";
	}
	 
	 
}
