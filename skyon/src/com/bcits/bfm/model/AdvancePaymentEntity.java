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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ADVANCE_PAYMENT") 
@NamedQueries({
	@NamedQuery(name = "AdvancePaymentEntity.findAll", query = "SELECT d.advPayId,d.receiptNo,d.amount,d.advPaymentDate,dd.advCollId FROM AdvancePaymentEntity d INNER JOIN d.advanceCollectionEntity dd where dd.advCollId=:advPayId ORDER BY d.advPayId DESC"),
})
public class AdvancePaymentEntity 
{
	@Id
	@Column(name="ADVPAY_ID")
	@SequenceGenerator(name = "advpay_seq", sequenceName = "ADVPAY_ID_SEQ") 
	@GeneratedValue(generator = "advpay_seq") 
	private int advPayId;
	
	@Column(name="RECEIPT_NUMBER")
	private String receiptNo;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="ADVPAYMENT_DATE")
	private Date advPaymentDate;
		
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
	
	public int getAdvPayId() {
		return advPayId;
	}

	public void setAdvPayId(int advPayId) {
		this.advPayId = advPayId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getAdvPaymentDate() {
		return advPaymentDate;
	}

	public void setAdvPaymentDate(Date advPaymentDate) {
		this.advPaymentDate = advPaymentDate;
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
