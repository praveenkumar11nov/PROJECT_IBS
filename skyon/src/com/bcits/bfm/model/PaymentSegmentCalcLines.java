package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="PS_CALC_LINES")  
@NamedQueries({
	@NamedQuery(name = "PaymentSegmentCalcLines.getLineItemData", query = "SELECT bl FROM ElectricityBillLineItemEntity bl where bl.electricityBillEntity.elBillId=:elBillId ORDER BY bl.elBillLineId ASC"),
	@NamedQuery(name = "PaymentSegmentCalcLines.findAllById", query = "SELECT psc FROM PaymentSegmentCalcLines psc INNER JOIN psc.paymentSegmentsEntity ps WHERE psc.paymentSegmentsEntity.paymentSegmentId=:paymentCollectionId ORDER BY psc.calcLinesId ASC"),
	@NamedQuery(name = "PaymentSegmentCalcLines.getTransactionName", query = "SELECT tm.transactionName FROM TransactionMasterEntity tm WHERE tm.transactionCode=:transactionCode"),
	@NamedQuery(name = "PaymentSegmentCalcLines.findAllByIdMIS", query = "SELECT psc.amount FROM PaymentSegmentCalcLines psc  WHERE psc.paymentSegmentsEntity.billingPaymentsEntity.paymentCollectionId=:paymentCollectionId AND psc.transactionCode=:transactionCode"),

})
public class PaymentSegmentCalcLines {

	@Id
	@SequenceGenerator(name = "psCalcLines_seq", sequenceName = "PS_CALC_LINES_SEQ") 
	@GeneratedValue(generator = "psCalcLines_seq") 
	@Column(name="PSCL_ID")
	private int calcLinesId;

	@OneToOne
    @JoinColumn(name = "PS_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private PaymentSegmentsEntity paymentSegmentsEntity;
	
	@Column(name="TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name="TRANSACTION_SUB_CODE")
	private String transactionSubCode;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="STATUS")
	private String status;
		
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	public int getCalcLinesId() {
		return calcLinesId;
	}

	public void setCalcLinesId(int calcLinesId) {
		this.calcLinesId = calcLinesId;
	}

	public PaymentSegmentsEntity getPaymentSegmentsEntity() {
		return paymentSegmentsEntity;
	}

	public void setPaymentSegmentsEntity(PaymentSegmentsEntity paymentSegmentsEntity) {
		this.paymentSegmentsEntity = paymentSegmentsEntity;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionSubCode() {
		return transactionSubCode;
	}

	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
