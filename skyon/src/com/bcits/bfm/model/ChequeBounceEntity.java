package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
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
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="PAYMENT_CHEQUE_BOUNCE")  
@NamedQueries({
	@NamedQuery(name = "ChequeBounceEntity.findAll", query = "SELECT cb.chequeBounceId,cb.accountId,cb.receiptNo,cb.chequeNo,cb.bankName,cb.chequeGivenDate,cb.bouncedDate,cb.chequeAmount,cb.penalityAmount,cb.status,a.accountNo,p.firstName,p.lastName,pt.property_No,cb.remarks,cb.bankCharges,cb.amountValid,cb.previousLateAmount FROM ChequeBounceEntity cb,Property pt INNER JOIN cb.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId ORDER BY cb.chequeBounceId DESC"),
	@NamedQuery(name = "ChequeBounceEntity.getAllReceiptNos", query = "SELECT bp.receiptNo FROM BillingPaymentsEntity bp WHERE bp.accountId=:accountId ORDER BY bp.paymentDate DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name = "ChequeBounceEntity.changeChequeBounceStatus", query = "UPDATE ChequeBounceEntity cb SET cb.status='Approved',cb.amountValid='Yes' WHERE cb.chequeBounceId=:chequeBounceId AND cb.status='Created'"),
	@NamedQuery(name = "ChequeBounceEntity.getChequeDetailsBasedOnChequeNumber", query = "SELECT bp.accountId,a.accountNo,bp.instrumentDate,bp.paymentAmount,bp.receiptNo FROM BillingPaymentsEntity bp,Account a WHERE a.accountId=bp.accountId AND bp.paymentMode='Cheque' AND bp.instrumentNo=:chequeNo AND bp.receiptNo=:receiptNo AND bp.status='Posted' AND bp.bankName=:bankName AND bp.instrumentDate IS NOT NULL AND bp.instrumentDate BETWEEN ADD_MONTHS(SYSDATE, -6) AND ADD_MONTHS(SYSDATE, 0)"),
	@NamedQuery(name = "ChequeBounceEntity.getPaymentIdBasedOnChequeBounce", query = "SELECT bp.paymentCollectionId FROM BillingPaymentsEntity bp WHERE bp.accountId=:accountId AND bp.receiptNo=:receiptNo AND bp.instrumentNo=:chequeNo AND bp.bankName=:bankName AND bp.instrumentDate IS NOT NULL"),
	@NamedQuery(name = "ChequeBounceEntity.updateChequeBounceDetailsStatusBasedOnBillsPosting", query = "UPDATE ChequeBounceEntity cb SET cb.amountValid='No' WHERE cb.accountId=:accountId AND cb.status='Approved' AND cb.amountValid='Yes'"),
	@NamedQuery(name = "ChequeBounceEntity.getCheckBounceDetailOnAccont", query = "SELECT DISTINCT(cb.receiptNo), cb.penalityAmount,cb.bankCharges,cb.previousLateAmount,el.ledgerType  FROM ChequeBounceEntity cb , ElectricityLedgerEntity el  Where el.postReference=cb.receiptNo AND cb.accountId=:accountID AND cb.amountValid ='Yes' AND el.ledgerType NOT IN ('Common Ledger')"),
})
@Cacheable(value=true)
public class ChequeBounceEntity {

	@Id
	@SequenceGenerator(name = "chequeBounce_seq", sequenceName = "PAYMENT_CHEQUE_BOUNCE_SEQ") 
	@GeneratedValue(generator = "chequeBounce_seq") 
	@Column(name="ID")
	private int chequeBounceId;
	
	@Transient
	private String accountNo;
	
	@Transient
	private String property_No;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    private Account accountObj;
	
	@Column(name="RECEIPT_NO")
	private String receiptNo;
	
	@Column(name="CHEQUE_NO")
	private String chequeNo;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="CHEQUE_GIVEN_DATE")
	private Date chequeGivenDate;
	
	@Column(name="BOUNCED_DATE")
	private Date bouncedDate;
	
	@Column(name="CHEQUE_AMOUNT")
	private double chequeAmount;
	
	@Column(name="PENALITY_AMOUNT")
	private double penalityAmount;
	
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="BANK_CHARGES")
	private double bankCharges;
	
	@Column(name="PENALITY_AMOUNT_VALID")
	private String amountValid;
	
	@Column(name="PREVIOUS_LATE_AMOUNT")
	private double previousLateAmount;
	
	public int getChequeBounceId() {
		return chequeBounceId;
	}

	public void setChequeBounceId(int chequeBounceId) {
		this.chequeBounceId = chequeBounceId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Account getAccountObj() {
		return accountObj;
	}

	public void setAccountObj(Account accountObj) {
		this.accountObj = accountObj;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getChequeGivenDate() {
		return chequeGivenDate;
	}

	public void setChequeGivenDate(Date chequeGivenDate) {
		this.chequeGivenDate = chequeGivenDate;
	}

	public Date getBouncedDate() {
		return bouncedDate;
	}

	public void setBouncedDate(Date bouncedDate) {
		this.bouncedDate = bouncedDate;
	}

	public double getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(double chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public double getPenalityAmount() {
		return penalityAmount;
	}

	public void setPenalityAmount(double penalityAmount) {
		this.penalityAmount = penalityAmount;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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
	  createdDate = new Timestamp(new java.util.Date().getTime());
	  lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	 }

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getBankCharges() {
		return bankCharges;
	}

	public void setBankCharges(double bankCharges) {
		this.bankCharges = bankCharges;
	}

	public String getAmountValid() {
		return amountValid;
	}

	public void setAmountValid(String amountValid) {
		this.amountValid = amountValid;
	}

	public double getPreviousLateAmount() {
		return previousLateAmount;
	}

	public void setPreviousLateAmount(double previousLateAmount) {
		this.previousLateAmount = previousLateAmount;
	}

}
