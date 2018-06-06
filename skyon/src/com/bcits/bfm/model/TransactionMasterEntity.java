package com.bcits.bfm.model;

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

import org.hibernate.annotations.BatchSize;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="TRANSACTION_MASTER") 
@BatchSize(size = 10)
@NamedQueries({
	@NamedQuery(name = "TransactionMasterEntity.findAll", query = "SELECT tm.transactionId,tm.transactionName,tm.description,tm.createdBy,tm.typeOfService,tm.transactionCode,tm.calculationBasis,tm.groupType,tm.camRate FROM TransactionMasterEntity tm ORDER BY tm.typeOfService,tm.transactionId ASC"),
	@NamedQuery(name = "TransactionMasterEntity.getTransationCodesByType", query = "SELECT tm.transactionCode FROM TransactionMasterEntity tm where tm.typeOfService=:typeOfService ORDER BY tm.transactionCode ASC"),
	@NamedQuery(name = "ElectricityBillLineItemEntity.getTaransactionCodeForOthers", query = "SELECT tm.transactionCode,tm.transactionName FROM TransactionMasterEntity tm where tm.typeOfService=:typeOfService and tm.transactionCode!='OT_TAX' and tm.transactionCode!='OT_TAX' and tm.transactionCode!='OT_INTEREST' and tm.transactionCode!='OT_TAX_INTEREST' and tm.transactionCode!='OT_ROF' and tm.transactionCode!='OT' ORDER BY tm.transactionCode ASC"),
	@NamedQuery(name = "TransactionMasterEntity.getTransationNameByCode", query = "SELECT tm.transactionName FROM TransactionMasterEntity tm where tm.transactionCode=:transactionCode ORDER BY tm.transactionCode ASC"),
	@NamedQuery(name = "TransactionMasterEntity.getTransationNameByCodeWithoutTaxAndRoundOff", query = "SELECT elb FROM ElectricityBillLineItemEntity elb INNER JOIN elb.electricityBillEntity eb WHERE eb.elBillId=:elBillId AND elb.transactionCode NOT IN('CAM_ROF','CAM_SERVICETAX')"),
	@NamedQuery(name = "TransactionMasterEntity.findAllTransaction", query = "SELECT tm FROM TransactionMasterEntity tm ORDER BY tm.typeOfService,tm.transactionId ASC"),
	
})
public class TransactionMasterEntity {

	@Id
	@SequenceGenerator(name = "transactionMaster_seq", sequenceName = "TRANSACTION_MASTER_SEQ") 
	@GeneratedValue(generator = "transactionMaster_seq") 
	@Column(name="TR_ID")
	private int transactionId;
	
	@Column(name="GROUP_TYPE")
	private String groupType;
	
	@Column(name="TR_NAME")
	private String transactionName;
	
	@Column(name="TR_CODE")
	private String transactionCode;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfService;
	
	@Column(name="CALC_BASIS")
	private String calculationBasis;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	@Column(name="CAM_RATE")
	private double camRate;
	
	public double getCamRate() {
		return camRate;
	}

	public void setCamRate(double camRate) {
		this.camRate = camRate;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
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

	public String getCalculationBasis() {
		return calculationBasis;
	}

	public void setCalculationBasis(String calculationBasis) {
		this.calculationBasis = calculationBasis;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}
