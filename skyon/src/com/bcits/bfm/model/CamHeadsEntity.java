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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="CAM_HEADS")  
@NamedQueries({
	@NamedQuery(name = "CamHeadsEntity.findAllById", query = "SELECT ch.camHeadId,ch.groupName,ch.transactionCode,ch.calculationBasis,ch.amount,cc.ccId,tm.transactionName FROM CamHeadsEntity ch,TransactionMasterEntity tm INNER JOIN ch.camConsolidationEntity cc WHERE tm.transactionCode=ch.transactionCode AND cc.ccId=:ccId"),
	@NamedQuery(name = "CamHeadsEntity.getHeadData", query = "SELECT ch FROM CamHeadsEntity ch WHERE ch.camConsolidationEntity.ccId=:ccId"),
	@NamedQuery(name = "CamHeadsEntity.getHeadDataAmount", query = "SELECT sum(ch.amount) FROM CamHeadsEntity ch WHERE ch.camConsolidationEntity.ccId=:ccId"),
})
public class CamHeadsEntity {

	@Id
	@Column(name="CH_ID")
	@SequenceGenerator(name = "camHeads_seq", sequenceName = "CAM_HEADS_SEQ") 
	@GeneratedValue(generator = "camHeads_seq") 
	private int camHeadId;
	
	@OneToOne	 
    @JoinColumn(name = "CC_ID")
	private CamConsolidationEntity camConsolidationEntity;
	
	@Column(name="GROUP_TYPE")
	private String groupName;
	
	@Column(name="TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name="CALC_BASIS")
	private String calculationBasis;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;

	public int getCamHeadId() {
		return camHeadId;
	}

	public void setCamHeadId(int camHeadId) {
		this.camHeadId = camHeadId;
	}

	public CamConsolidationEntity getCamConsolidationEntity() {
		return camConsolidationEntity;
	}

	public void setCamConsolidationEntity(
			CamConsolidationEntity camConsolidationEntity) {
		this.camConsolidationEntity = camConsolidationEntity;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getCalculationBasis() {
		return calculationBasis;
	}

	public void setCalculationBasis(String calculationBasis) {
		this.calculationBasis = calculationBasis;
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
