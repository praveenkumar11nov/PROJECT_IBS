package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="DEPOSITS") 
@NamedQueries({
	@NamedQuery(name = "Deposits.findAll", query = "SELECT d.depositsId,a.accountNo,a.accountId,pt.property_No,d.totalAmount,d.status,d.depositType,d.refundAmount,d.balance,p.firstName,p.lastName FROM Deposits d,Property pt INNER JOIN d.accountObj a INNER JOIN a.person p WHERE a.propertyId=pt.propertyId ORDER BY d.depositsId DESC"),
	@NamedQuery(name = "Deposits.setDepositsStatus", query = "UPDATE Deposits d SET d.status = :status WHERE d.depositsId = :depositsId"),
	@NamedQuery(name = "Deposits.getAllAccountNumbers", query = "SELECT  DISTINCT a.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,pt.property_No FROM Account a,Property pt INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND a.accountStatus='Active'"),
	@NamedQuery(name = "Deposits.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM Deposits ac INNER JOIN ac.accountObj a"),
	@NamedQuery(name = "Deposits.commonFilterForPropertyNoUrl", query = "SELECT DISTINCT pt.property_No FROM Deposits ac INNER JOIN ac.accountObj a,Property pt WHERE pt.propertyId=a.propertyId"),
	@NamedQuery(name = "Deposits.testUniqueAccount",query="SELECT d FROM Deposits d INNER JOIN d.accountObj a WHERE a.accountId = :accountId"),
	@NamedQuery(name = "Deposits.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from Deposits d INNER JOIN d.accountObj a INNER JOIN a.person p"),
})
public class Deposits 
{
	@Id
	@Column(name="DEPOSITS_ID")
	@SequenceGenerator(name = "deposits_seq", sequenceName = "DEPOSITS_ID_SEQ") 
	@GeneratedValue(generator = "deposits_seq") 
	private int depositsId;
	
	@Transient
	private String accountNo;
	
	@Transient
	private int accountId;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Account accountObj;
	
	@Column(name="TOTAL_AMOUNT")
	private double totalAmount;
	
	@Column(name="DEPOSIT_TYPE")
	private String depositType;
	
	@Column(name="REFUND_AMOUNT")
	private double refundAmount;
	
	@Column(name="BALANCE")
	private double balance;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;

	public int getDepositsId() {
		return depositsId;
	}

	public void setDepositsId(int depositsId) {
		this.depositsId = depositsId;
	}
	
	public Account getAccountObj() {
		return accountObj;
	}

	public void setAccountObj(Account accountObj) {
		this.accountObj = accountObj;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
}
