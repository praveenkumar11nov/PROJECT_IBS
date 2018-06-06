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
@Table(name="PAYMENT_ADJUSTMENT_CONTROL")  
@NamedQueries({
	@NamedQuery(name = "PaymentAdjustmentControlEntity.findAll", query = "SELECT pa.pacId,pa.pacDate,pa.typeOfService,pa.status,pa.accountId,a.accountNo FROM PaymentAdjustmentControlEntity pa,Account a WHERE a.accountId=pa.accountId ORDER BY pa.pacId DESC"),
	@NamedQuery(name = "PaymentAdjustmentControlEntity.setAdjustmentControlStatus", query = "UPDATE PaymentAdjustmentControlEntity bp SET bp.status = :status WHERE bp.pacId = :pacId"),
	@NamedQuery(name = "PaymentAdjustmentControlEntity.getpacId", query = "SELECT bp FROM PaymentAdjustmentControlEntity bp WHERE bp.status='Approved'"),
	@NamedQuery(name = "PaymentAdjustmentControlEntity.getAdjusmentCalcItemList", query = "SELECT ps FROM AdjustmentComponentsEntity ps WHERE ps.adjustmentControlEntity.pacId=:pacId"),
	@NamedQuery(name = "PaymentAdjustmentControlEntity.getAllPaidAccountNumbers", query = "SELECT  DISTINCT eb.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM ElectricityBillEntity eb,Account a INNER JOIN a.person p WHERE eb.accountId = a.accountId"),
	@NamedQuery(name = "PaymentAdjustmentControlEntity.findServiceTypes", query = "SELECT a.accountId,a.accountNo,sm.typeOfService FROM ServiceMastersEntity sm INNER JOIN sm.accountObj a"),
})
public class PaymentAdjustmentControlEntity {

	@Id
	@SequenceGenerator(name = "paymentAdjustmentControl_seq", sequenceName = "PAYMENT_ADJUSTMENT_CONTROL_SEQ") 
	@GeneratedValue(generator = "paymentAdjustmentControl_seq") 
	@Column(name="PAC_ID")
	private int pacId;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;
	
	@Transient
	private String accountNo;
	
	@Column(name="PAC_DATE")
	private Date pacDate;
	
	@Column(name="SERVICE_TYPE")
	private String typeOfService;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());

	public int getPacId() {
		return pacId;
	}

	public void setPacId(int pacId) {
		this.pacId = pacId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getPacDate() {
		return pacDate;
	}

	public void setPacDate(Date pacDate) {
		this.pacDate = pacDate;
	}
	
	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
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
