package com.bcits.bfm.model;

import java.sql.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name="LEDGER_VIEW")  
@BatchSize(size = 10)
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="LedgerViewEntity.getLedgerByAccountId",query="SELECT l FROM LedgerViewEntity l WHERE l.accountId=:accountId and EXTRACT(month FROM l.ledgerDate) BETWEEN :fromMonth and :toMonth and EXTRACT(year FROM l.ledgerDate) BETWEEN :fromYear and :toYear"),
	
})
public class LedgerViewEntity {

	@Id
	@Column(name="ELL_ID")
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
	
	@Column(name="POSTED_TO_BILL_DT")
	private String postedBillDate;  
	
	@Column(name="TRANSACTION_SEQUENCE")
	private int transactionSequence;
	
	@Column(name="LEDGER_TYPE")
	private String ledgerType;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACCOUNT_NO")
	private String accountNo;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="PERSON_TYPE")
	private String personType;
	
	@Column(name="PERSON_ID")
	private int personId;
	
	@Column(name="PROPERTY_NO")
	private String property_No;
	
	@Column(name="TR_NAME")
	private String transactionName;
	
	@Column(name="PERSON_NAME")
	private String personName;
	
	@Column(name="INSTRUMENT_NO")
	private String instrumentNo;
	
	@Column(name="BANK_ID")
	private String bankName;
	
	@Column(name="INSTRUMENT_DATE")
	private String instrumentDate;
	
	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

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

	public String getPostedBillDate() {
		return postedBillDate;
	}

	public void setPostedBillDate(String postedBillDate) {
		this.postedBillDate = postedBillDate;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
}
