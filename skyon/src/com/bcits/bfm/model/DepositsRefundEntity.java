package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="DEPOSIT_REFUND") 
@NamedQueries({
	@NamedQuery(name = "DepositsRefundEntity.findAll", query = "SELECT dr.refundId,dr.refundAmount,dr.loadChangeUnits,dr.notes,dr.paymentThrough,dr.instrumentNo,dr.instrumentDate,dr.bankName,dd.depositsId,dr.refundDate FROM DepositsRefundEntity dr INNER JOIN dr.deposits dd WHERE dd.depositsId=:depositsId ORDER BY dr.refundId DESC"),
})
public class DepositsRefundEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@SequenceGenerator(name = "depositsRefund_seq", sequenceName = "DEPOSIT_REFUND_SEQ") 
	@GeneratedValue(generator = "depositsRefund_seq") 
	private int refundId;
	
	@Transient
	private String refundType;
	
	@Transient
	private String sancationedLoadName;
	
	@Transient
	private String sancationedLoadRefund;
	
	@Transient
	private double newSancationedLoadRefund;
	
	@Column(name="REFUND_AMOUNT")
	private double refundAmount;
	
	@Column(name="LOAD_CHANGE")
	private double loadChangeUnits;
	
	@Column(name="NOTES")
	private String notes;
	
	@Column(name="PAYMENT_TROUGH")
	private String paymentThrough;
	
	@Column(name="INSTRUMENT_NO")
	private String instrumentNo;
	
	@Column(name="INSTRUMENT_DATE")
	private Date instrumentDate;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="REFUND_DATE")
	private Date refundDate;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "DEPOSITS_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Deposits deposits;
	
	public int getRefundId() {
		return refundId;
	}

	public void setRefundId(int refundId) {
		this.refundId = refundId;
	}
	
	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getSancationedLoadName() {
		return sancationedLoadName;
	}

	public void setSancationedLoadName(String sancationedLoadName) {
		this.sancationedLoadName = sancationedLoadName;
	}
	
	public String getSancationedLoadRefund() {
		return sancationedLoadRefund;
	}

	public void setSancationedLoadRefund(String sancationedLoadRefund) {
		this.sancationedLoadRefund = sancationedLoadRefund;
	}

	public double getNewSancationedLoadRefund() {
		return newSancationedLoadRefund;
	}

	public void setNewSancationedLoadRefund(double newSancationedLoadRefund) {
		this.newSancationedLoadRefund = newSancationedLoadRefund;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public double getLoadChangeUnits() {
		return loadChangeUnits;
	}

	public void setLoadChangeUnits(double loadChangeUnits) {
		this.loadChangeUnits = loadChangeUnits;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPaymentThrough() {
		return paymentThrough;
	}

	public void setPaymentThrough(String paymentThrough) {
		this.paymentThrough = paymentThrough;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
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

	public Deposits getDeposits() {
		return deposits;
	}

	public void setDeposits(Deposits deposits) {
		this.deposits = deposits;
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
