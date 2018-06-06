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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="CAM_LEDGER")  
@NamedQueries({
	@NamedQuery(name = "CamLedgerEntity.findAll", query = "SELECT cl.camLedgerid,cl.camHeadProperty,cl.calculationBased,cl.postReference,cl.transactionCode,cl.creditAmount,cl.balance,cl.postedBillDate,cl.headBalance,tm.transactionName,cl.postType,cl.postedToBill,cl.ledgerDate,cl.status,cl.debitAmount FROM CamLedgerEntity cl,TransactionMasterEntity tm WHERE tm.transactionCode=cl.transactionCode ORDER BY cl.camLedgerid DESC"),
	@NamedQuery(name = "CamLedgerEntity.getLastTransactionCamLedgerId", query = "SELECT MAX(camLedgerid) FROM CamLedgerEntity cl WHERE cl.transactionCode=:transactionCode"),
	@NamedQuery(name = "CamLedgerEntity.getAllData", query = "SELECT cl FROM CamLedgerEntity cl"),
	@NamedQuery(name = "CamLedgerEntity.getCamHeadTest", query = "SELECT cl FROM CamLedgerEntity cl WHERE cl.camHeadProperty=:camHeadProperty"),
	@NamedQuery(name = "CamLedgerEntity.findAllLedgerBasedOnCCID", query = "SELECT cl FROM CamLedgerEntity cl WHERE cl.ccId=:ccId"),
	@NamedQuery(name = "CamLedgerEntity.getCamSetting", query = "SELECT cc FROM CamChargesEntity cc"),
	@NamedQuery(name = "CamLedgerEntity.getLastCamLedgerId", query = "SELECT MAX(camLedgerid) FROM CamLedgerEntity cl"),
	@NamedQuery(name = "CamLedgerEntity.commonFilterForCAMTransactions", query = "SELECT DISTINCT tm.transactionName FROM CamLedgerEntity cl,TransactionMasterEntity tm WHERE cl.transactionCode=tm.transactionCode"),
})
public class CamLedgerEntity {
	
	@Id
	@Column(name="CL_ID")
	@SequenceGenerator(name = "camLedger_seq", sequenceName = "CAM_LEDGER_SEQ") 
	@GeneratedValue(generator = "camLedger_seq") 
	private int camLedgerid;
	
	@Column(name="CC_ID")
	private int ccId;
	
	@Column(name="CAM_HEADS")
	private String camHeadProperty;
	
	@Column(name="CALCULATED_BASED_ON")
	private String calculationBased;
	
	@Column(name="POST_TYPE")
	private String postType;
	
	@Column(name="TRANSACTION_DATE")
	private Date ledgerDate;
	
	@Column(name="POSTED_TO_BILL")
	private String postedToBill;
	
	@Column(name="CL_PERIOD")
	private String ledgerPeriod;
	
	@Column(name="POST_REF")
	private String postReference;
	
	@Column(name="CREDIT")
	private double creditAmount;
	
	@Column(name="DEBIT")
	private double debitAmount;
	
	@Column(name="BALANCE")
	private double balance;
	
	@Column(name="HEAD_BALANCE")
	private double headBalance;
	
	@Column(name="POSTED_TO_BILL_DT")
	private Timestamp postedBillDate;  
	
	@Column(name="TR_CODE")
	private String transactionCode;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getCamLedgerid() {
		return camLedgerid;
	}

	public void setCamLedgerid(int camLedgerid) {
		this.camLedgerid = camLedgerid;
	}

	public String getLedgerPeriod() {
		return ledgerPeriod;
	}

	public void setLedgerPeriod(String ledgerPeriod) {
		this.ledgerPeriod = ledgerPeriod;
	}

	public String getPostReference() {
		return postReference;
	}

	public void setPostReference(String postReference) {
		this.postReference = postReference;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Timestamp getPostedBillDate() {
		return postedBillDate;
	}

	public void setPostedBillDate(Timestamp postedBillDate) {
		this.postedBillDate = postedBillDate;
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
	 
	public String getCamHeadProperty() {
		return camHeadProperty;
	}

	public void setCamHeadProperty(String camHeadProperty) {
		this.camHeadProperty = camHeadProperty;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPostedToBill() {
		return postedToBill;
	}

	public void setPostedToBill(String postedToBill) {
		this.postedToBill = postedToBill;
	}

	public Date getLedgerDate() {
		return ledgerDate;
	}

	public void setLedgerDate(Date ledgerDate) {
		this.ledgerDate = ledgerDate;
	}

	public int getCcId() {
		return ccId;
	}

	public void setCcId(int ccId) {
		this.ccId = ccId;
	}

	public String getCalculationBased() {
		return calculationBased;
	}

	public void setCalculationBased(String calculationBased) {
		this.calculationBased = calculationBased;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public double getHeadBalance() {
		return headBalance;
	}

	public void setHeadBalance(double headBalance) {
		this.headBalance = headBalance;
	}
	
}
