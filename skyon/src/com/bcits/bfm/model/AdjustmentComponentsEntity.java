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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ADJCONTROL_COMPONENTS")  
@NamedQueries({
	@NamedQuery(name = "AdjustmentComponentsEntity.findAllById", query = "SELECT acl.adjComId,acl.transactionCode,pa.pacId,tm.transactionName FROM AdjustmentComponentsEntity acl,TransactionMasterEntity tm INNER JOIN acl.adjustmentControlEntity pa where tm.transactionCode=acl.transactionCode AND pa.pacId=:pacId ORDER BY acl.adjComId DESC"),
	@NamedQuery(name = "AdjustmentComponentsEntity.getTransactionCodes", query = "SELECT DISTINCT tm.transactionCode,tm.transactionName FROM TransactionMasterEntity tm WHERE tm.typeOfService=:typeOfService"),
})
public class AdjustmentComponentsEntity {

	@Id
	@SequenceGenerator(name = "adjustmentComponents_seq", sequenceName = "ADJCONTROL_COMPONENTS_SEQ") 
	@GeneratedValue(generator = "adjustmentComponents_seq") 
	@Column(name="ADJ_COMP_ID")
	private int adjComId;
	
	@Transient
	private int pacId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "PAC_ID")
	private PaymentAdjustmentControlEntity adjustmentControlEntity;
	
	@Column(name="TR_CODE", unique=true, nullable=false, precision=11, scale=0)
	private String transactionCode;
	
	@Column(name="STATUS")
	private String status;
		
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());

	public int getAdjComId() {
		return adjComId;
	}

	public void setAdjComId(int adjComId) {
		this.adjComId = adjComId;
	}

	public int getPacId() {
		return pacId;
	}

	public void setPacId(int pacId) {
		this.pacId = pacId;
	}

	public PaymentAdjustmentControlEntity getAdjustmentControlEntity() {
		return adjustmentControlEntity;
	}

	public void setAdjustmentControlEntity(
			PaymentAdjustmentControlEntity adjustmentControlEntity) {
		this.adjustmentControlEntity = adjustmentControlEntity;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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
