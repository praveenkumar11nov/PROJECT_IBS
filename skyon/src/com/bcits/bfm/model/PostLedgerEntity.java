package com.bcits.bfm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="POST_LEDGER")
public class PostLedgerEntity 
{
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="RECEIPT_NO")
	private String receiptNo;
	
	

	@Column(name="ACCOUNT_NO")
	private String accountNo;
	
	@Column(name="PROPERTY_NO")
	private String propertyNo;
	
	@Column(name="AMOUNT")
	private String amount;
	
	@Column(name="BALANCE")
	private String balance;
	
	@Column(name="DATE_D")
	private String date;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPropertyNo() {
		return propertyNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
