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

import org.hibernate.annotations.BatchSize;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="LEDGER")  
@BatchSize(size = 10)
@NamedQueries({
	@NamedQuery(name = "ElectricityLedgerEntity.findAll", query = "SELECT el.elLedgerid,el.accountId,el.ledgerPeriod,el.ledgerDate,el.postType,el.postReference,el.transactionCode,el.amount,el.balance,el.postedBillDate,el.transactionSequence,el.ledgerType,el.accountNo,el.transactionName,el.firstName,el.lastName,el.personType,el.personId,el.property_No,el.remarks,el.personName FROM LedgerViewEntity el WHERE EXTRACT(month FROM el.ledgerDate)=(SELECT EXTRACT(month FROM el.ledgerDate) FROM LedgerViewEntity el WHERE el.ledgerDate=(SELECT MAX(el.ledgerDate) FROM LedgerViewEntity el)) AND EXTRACT(year FROM el.ledgerDate)=(SELECT EXTRACT(year FROM el.ledgerDate) FROM LedgerViewEntity el WHERE el.ledgerDate=(SELECT MAX(el.ledgerDate) FROM LedgerViewEntity el)) ORDER BY el.elLedgerid DESC"),
	@NamedQuery(name = "ElectricityLedgerEntity.setELRateMasterStatus", query = "UPDATE ElectricityLedgerEntity el SET el.status = :status WHERE el.elLedgerid = :elLedgerid"),
	@NamedQuery(name = "ElectricityLedgerEntity.getLedgerSequence", query = "SELECT COUNT(accountId) FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId"),
	@NamedQuery(name = "ElectricityLedgerEntity.getLastLedgerTransactionAmount", query = "SELECT MAX(elLedgerid) FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId AND el.ledgerType=:ledgerType AND el.postType!='ARREARS'"),
	@NamedQuery(name = "ElectricityLedgerEntity.getPreviousLedger", query = "SELECT el FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId and  EXTRACT(month FROM el.ledgerDate) =:month and EXTRACT(year FROM el.ledgerDate) =:year and el.transactionCode =:transactionCode  order by el.elLedgerid DESC"),//and el.postType NOT IN('ADJUSTMENT','BILL')
	@NamedQuery(name = "ElectricityLedgerEntity.getPropertyNoForTenant", query = "SELECT p.property_No FROM ElectricityLedgerEntity el,Account a,TenantProperty tp INNER JOIN tp.tenantId t INNER JOIN t.person ps INNER JOIN tp.property p  WHERE p.propertyId=a.propertyId AND el.accountId=a.accountId AND ps.personId=:personId"),
	@NamedQuery(name = "ElectricityLedgerEntity.getPropertyNoForOwner", query = "SELECT p.property_No FROM OwnerProperty op INNER JOIN op.owner o INNER JOIN o.person ps INNER JOIN op.property p  WHERE ps.personId=:personId"),
	@NamedQuery(name = "ElectricityLedgerEntity.getLastLedgerBillAreears", query = "SELECT MAX(elLedgerid) FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId AND el.ledgerType=:typeOfService AND el.postType!='ARREARS'"),
	@NamedQuery(name = "ElectricityLedgerEntity.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM ElectricityLedgerEntity el,Account a WHERE el.accountId=a.accountId"),
	@NamedQuery(name = "ElectricityLedgerEntity.commonFilterForPropertyNumbersUrl", query = "SELECT DISTINCT p.property_No FROM Property p,Account a WHERE p.propertyId=a.propertyId"),	
	@NamedQuery(name = "ElectricityLedgerEntity.getPreviousArrearsLedger", query = "SELECT el FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId and  EXTRACT(month FROM el.ledgerDate) =:month and EXTRACT(year FROM el.ledgerDate) =:year and el.transactionCode =:transactionCode and el.postType='ARREARS' order by el.elLedgerid DESC"),
	//@NamedQuery(name = "ElectricityLedgerEntity.getPreviousLedgerPayments", query = "SELECT el FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId and  EXTRACT(month FROM el.postedBillDate) =:month and EXTRACT(year FROM el.postedBillDate) =:year and el.transactionCode =:transactionCode and el.postType='PAYMENT' order by el.elLedgerid DESC"),
	@NamedQuery(name = "ElectricityLedgerEntity.getPreviousLedgerPayments", query = "SELECT el FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId and  EXTRACT(month FROM el.postedBillDate) >=:month and EXTRACT(year FROM el.postedBillDate) =:year and el.transactionCode =:transactionCode and el.postType='PAYMENT' order by el.elLedgerid DESC"),
	@NamedQuery(name = "ElectricityLedgerEntity.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from ElectricityLedgerEntity el,Account a INNER JOIN a.person p WHERE el.accountId=a.accountId"),
	@NamedQuery(name = "ElectricityLedgerEntity.getTeleBroadbandLedgerDetails", query = "SELECT el FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId AND el.ledgerType=:ledgerType AND el.postType='INIT'"),
	@NamedQuery(name = "ElectricityLedgerEntity.getLastLedgerBasedOnAccountId", query = "SELECT MAX(elLedgerid) FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId AND el.postType!='ARREARS'"),
	
	@NamedQuery(name = "ElectricityLedgerEntity.findOnAccountWise", query = "SELECT el.elLedgerid,el.accountId,el.ledgerPeriod,el.ledgerDate,el.postType,el.postReference,el.transactionCode,el.amount,el.balance,el.postedBillDate,el.transactionSequence,el.ledgerType,a.accountNo,tm.transactionName,p.firstName,p.lastName,p.personType,p.personId,pt.property_No,el.remarks FROM ElectricityLedgerEntity el,Account a,TransactionMasterEntity tm,Property pt INNER JOIN a.person p WHERE a.propertyId=pt.propertyId AND el.accountId=a.accountId AND  el.accountId=:accountId AND tm.transactionCode=el.transactionCode  and el.postedBillDate >= TO_DATE(:strDate, 'YYYY-MM-DD') and el.postedBillDate<= TO_DATE(:pesentDate, 'YYYY-MM-DD') ORDER BY el.elLedgerid DESC"),
	@NamedQuery(name = "ElectricityLedgerEntity.searchLedgerDataByMonth", query = "SELECT el.elLedgerid,el.accountId,el.ledgerPeriod,el.ledgerDate,el.postType,el.postReference,el.transactionCode,el.amount,el.balance,el.postedBillDate,el.transactionSequence,el.ledgerType,el.accountNo,el.transactionName,el.firstName,el.lastName,el.personType,el.personId,el.property_No,el.remarks,el.personName FROM LedgerViewEntity el WHERE TRUNC(el.ledgerDate) BETWEEN TO_DATE(:fromDateVal,'YYYY-MM-DD') AND TO_DATE(:toDateVal,'YYYY-MM-DD') ORDER BY el.elLedgerid DESC"),
})
public class ElectricityLedgerEntity {
	
	@Id
	@Column(name="ELL_ID")
	@SequenceGenerator(name = "elLedger_seq", sequenceName = "ELLEDGER_SEQ") 
	@GeneratedValue(generator = "elLedger_seq") 
	private int elLedgerid;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;
	
	@Column(name="ELL_PERIOD")
	private String ledgerPeriod;
	
	@Column(name="ELL_DT")
	private Date ledgerDate;
	
	@Column(name="POST_TYPE")
	private String postType;
	
	@Column(name="POST_REF")
	private String postReference;
	
	@Column(name="TR_CODE")
	private String transactionCode ;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="BALANCE")
	private double balance;
	
	@Column(name="POSTED_TO_BILL")
	private String postedToBill;
	
	@Column(name="POSTED_TO_BILL_DT")
	private Timestamp postedBillDate;  
	
	@Column(name="POSTED_TO_GL")
	private String postedToGl;    
	
	@Column(name="POSTED_TO_GL_DT")
	private Date postedGlDate;  
	
	@Column(name="TRANSACTION_SEQUENCE")
	private int transactionSequence;
	
	@Column(name="LEDGER_TYPE")
	private String ledgerType;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Column(name="REMARKS")
	private String remarks;

	public int getElLedgerid() {
		return elLedgerid;
	}

	public void setElLedgerid(int elLedgerid) {
		this.elLedgerid = elLedgerid;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getLedgerPeriod() {
		return ledgerPeriod;
	}

	public void setLedgerPeriod(String ledgerPeriod) {
		this.ledgerPeriod = ledgerPeriod;
	}

	public Date getLedgerDate() {
		return ledgerDate;
	}

	public void setLedgerDate(Date ledgerDate) {
		this.ledgerDate = ledgerDate;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getPostedToBill() {
		return postedToBill;
	}

	public void setPostedToBill(String postedToBill) {
		this.postedToBill = postedToBill;
	}

	public Timestamp getPostedBillDate() {
		return postedBillDate;
	}

	public void setPostedBillDate(Timestamp postedBillDate) {
		this.postedBillDate = postedBillDate;
	}

	public String getPostedToGl() {
		return postedToGl;
	}

	public void setPostedToGl(String postedToGl) {
		this.postedToGl = postedToGl;
	}

	public Date getPostedGlDate() {
		return postedGlDate;
	}

	public void setPostedGlDate(Date postedGlDate) {
		this.postedGlDate = postedGlDate;
	}

	public int getTransactionSequence() {
		return transactionSequence;
	}

	public void setTransactionSequence(int transactionSequence) {
		this.transactionSequence = transactionSequence;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
