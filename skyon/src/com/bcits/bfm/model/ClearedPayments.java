package com.bcits.bfm.model;

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
import javax.persistence.OneToOne;
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
@Table(name="CLEARED_PAYMENT") 
@NamedQueries({
	//@NamedQuery(name = "ClearedPayments.findAll", query = "SELECT d.cpId,d.adjId,d.billId,d.amount,d.clearedDate,dd.advCollId FROM ClearedPayments d INNER JOIN d.advanceCollectionEntity dd where dd.advCollId=:advCollId ORDER BY d.cpId DESC"),
	@NamedQuery(name = "ClearedPayments.findAll", query = "SELECT DISTINCT d.cpId,pa.adjustmentId,d.billId,d.amount,d.clearedDate,dd.advCollId,pa.adjustmentNo FROM ClearedPayments d INNER JOIN d.paymentAdjustmentEntity pa INNER JOIN d.advanceCollectionEntity dd where dd.advCollId=:advCollId ORDER BY d.cpId DESC"),
})
public class ClearedPayments 
{
	@Id
	@Column(name="CP_ID")
	@SequenceGenerator(name ="clearedPayments_seq", sequenceName = "CLEAREDPAYMENT_ID_SEQ") 
	@GeneratedValue(generator = "clearedPayments_seq") 
	private int cpId;
	
	/*@Column(name="ADJ_ID")
	private int adjId;*/
	
	@OneToOne
    @JoinColumn(name = "ADJ_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private PaymentAdjustmentEntity paymentAdjustmentEntity;
	
	@Transient
	private String adjNo;
	
	@Column(name="BILL_ID")
	private String billId;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="CLEARED_DATE")
	private Date clearedDate;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ADVCOLL_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private AdvanceCollectionEntity advanceCollectionEntity;

	public int getCpId() {
		return cpId;
	}

	public void setCpId(int cpId) {
		this.cpId = cpId;
	}

	/*public int getAdjId() {
		return adjId;
	}

	public void setAdjId(int adjId) {
		this.adjId = adjId;
	}*/
	
	public String getAdjNo() {
		return adjNo;
	}

	public PaymentAdjustmentEntity getPaymentAdjustmentEntity() {
		return paymentAdjustmentEntity;
	}

	public void setPaymentAdjustmentEntity(
			PaymentAdjustmentEntity paymentAdjustmentEntity) {
		this.paymentAdjustmentEntity = paymentAdjustmentEntity;
	}

	public void setAdjNo(String adjNo) {
		this.adjNo = adjNo;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getClearedDate() {
		return clearedDate;
	}

	public void setClearedDate(Date clearedDate) {
		this.clearedDate = clearedDate;
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

	public AdvanceCollectionEntity getAdvanceCollectionEntity() {
		return advanceCollectionEntity;
	}

	public void setAdvanceCollectionEntity(
			AdvanceCollectionEntity advanceCollectionEntity) {
		this.advanceCollectionEntity = advanceCollectionEntity;
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
