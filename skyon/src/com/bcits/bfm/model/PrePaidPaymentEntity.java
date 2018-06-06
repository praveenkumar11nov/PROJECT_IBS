package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name="PREPAID_PAYMENT_ADJUSTMENT")
@NamedQueries({
	@NamedQuery(name="PrePaidPaymentEntity.getPropertyNum",query="select p.propertyId,p.property_No from Property p ,PrePaidMeters pp Where p.propertyId=pp.propertyId"),
	@NamedQuery(name="PrePaidPaymentEntity.getMeterNum",query="select pp.mId,pp.meterNumber,pp.propertyId from PrePaidMeters pp"),
	@NamedQuery(name="PrePaidPaymentEntity.getAll",query="select pa.adjustmentId,pa.consumerId,pa.propertyId,pa.adjustmentDate,pa.adjustmentNo,pa.adjustmentAmount,pa.adjustmentType,pa.status,pa.remarks,pa.instrumentDate,pa.instrumentNo,pa.bankName,pa.meterNumber from PrePaidPaymentEntity pa ORDER BY pa.adjustmentId DESC"),
	@NamedQuery(name="PrePaidPaymentEntity.getInstrumentDetails",query="select p.instrumentNo,p.instrumentDate from PrePaidPaymentEntity p where p.adjustmentNo=:receiptNumber"),
	
})
public class PrePaidPaymentEntity {
	@Id
	@SequenceGenerator(name="PREPAID_PAYMENT_ADJUSTMENT_SEQ",sequenceName="PREPAID_PAYMENT_ADJUSTMENT_SEQ")
	@GeneratedValue(generator="PREPAID_PAYMENT_ADJUSTMENT_SEQ")
	@Column(name="AID")
    private int adjustmentId;
	
	@Column(name="CONSUMER_ID")
	private String consumerId;
	
	@Column(name="PROPERTY_ID")
	private int propertyId;
	
	@Transient
	private String property_No;
	
	@Column(name="JV_DATE")
	private Date adjustmentDate;
	
	@Column(name="JV_NO")
	private String adjustmentNo;
	
	
	@Column(name="JV_AMOUNT")
	private double adjustmentAmount;
	
	@Column(name="INSTRUMENT_DATE")
	private Date instrumentDate;
	
	@Column(name="INSTRUMENT_NUMBER")
	private String instrumentNo;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	@Column(name="ADJUSTMENT_TYPE")
	private String adjustmentType;

	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="METER_NUMBER")
	private String meterNumber;

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(int adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public Date getAdjustmentDate() {
		return adjustmentDate;
	}

	public void setAdjustmentDate(Date adjustmentDate) {
		this.adjustmentDate = adjustmentDate;
	}

	public String getAdjustmentNo() {
		return adjustmentNo;
	}

	public void setAdjustmentNo(String adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
	}

	public double getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
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
	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "PrePaidPaymentEntity [adjustmentId=" + adjustmentId + ", consumerId=" + consumerId + ", propertyId="
				+ propertyId + ", property_No=" + property_No + ", adjustmentDate=" + adjustmentDate + ", adjustmentNo="
				+ adjustmentNo + ", adjustmentAmount=" + adjustmentAmount + ", instrumentDate=" + instrumentDate
				+ ", instrumentNo=" + instrumentNo + ", bankName=" + bankName + ", status=" + status + ", createdBy="
				+ createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDT=" + lastUpdatedDT
				+ ", adjustmentType=" + adjustmentType + ", remarks=" + remarks + "]";
	}	

}
