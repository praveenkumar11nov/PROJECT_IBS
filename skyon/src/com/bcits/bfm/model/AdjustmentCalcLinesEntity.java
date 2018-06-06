package com.bcits.bfm.model;

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
@Table(name="ADJ_CALC_LINES")  
@NamedQueries({
	@NamedQuery(name = "AdjustmentCalcLinesEntity.findAllById", query = "SELECT acl.calcLineId,acl.transactionCode,acl.amount,pa.adjustmentId,tm.transactionName FROM AdjustmentCalcLinesEntity acl,TransactionMasterEntity tm INNER JOIN acl.paymentAdjustmentEntity pa where tm.transactionCode=acl.transactionCode AND acl.paymentAdjustmentEntity.adjustmentId=:adjustmentId ORDER BY acl.calcLineId DESC"),
	@NamedQuery(name = "AdjustmentCalcLinesEntity.getTransactionCodes", query = "SELECT DISTINCT tm.transactionCode,tm.transactionName FROM TransactionMasterEntity tm WHERE tm.typeOfService=:typeOfService AND tm.transactionCode NOT IN (SELECT aj.transactionCode FROM AdjustmentCalcLinesEntity aj WHERE aj.paymentAdjustmentEntity.adjustmentId=:adjustmentId)"),
	@NamedQuery(name = "AdjustmentCalcLinesEntity.getLastAdjustmentData", query = "SELECT acl FROM AdjustmentCalcLinesEntity acl INNER JOIN acl.paymentAdjustmentEntity pa WHERE pa.accountId=:accountId AND acl.transactionCode=:transactionCode AND pa.adjustmentLedger=:adjustmentLedger AND pa.clearedStatus='No' AND pa.status='Posted'"),
	@NamedQuery(name = "AdjustmentCalcLinesEntity.getTotalAmountBasedOnAdjustmentId", query = "SELECT SUM(acl.amount) FROM AdjustmentCalcLinesEntity acl WHERE acl.paymentAdjustmentEntity.adjustmentId=:adjustmentId"),
})
public class AdjustmentCalcLinesEntity {

	@Id
	@SequenceGenerator(name = "adjustmentCalcLines_seq", sequenceName = "ADJUSTMENT_CALC_LINES_SEQ") 
	@GeneratedValue(generator = "adjustmentCalcLines_seq") 
	@Column(name="ADJCL_ID")
	private int calcLineId;
	
	@Transient
	private int adjustmentId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ADJ_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private PaymentAdjustmentEntity paymentAdjustmentEntity;
	
	@Column(name="TR_CODE", unique=true, nullable=false, precision=11, scale=0)
	private String transactionCode;
	
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

	public int getCalcLineId() {
		return calcLineId;
	}

	public void setCalcLineId(int calcLineId) {
		this.calcLineId = calcLineId;
	}

	public PaymentAdjustmentEntity getPaymentAdjustmentEntity() {
		return paymentAdjustmentEntity;
	}

	public void setPaymentAdjustmentEntity(
			PaymentAdjustmentEntity paymentAdjustmentEntity) {
		this.paymentAdjustmentEntity = paymentAdjustmentEntity;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(int adjustmentId) {
		this.adjustmentId = adjustmentId;
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
