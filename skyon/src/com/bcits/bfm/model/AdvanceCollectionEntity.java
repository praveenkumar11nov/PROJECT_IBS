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
@Table(name="ADVANCE_COLLECTION") 
@NamedQueries({
	@NamedQuery(name = "AdvanceCollectionEntity.findAll", query = "SELECT ac.advCollId,a.accountNo,a.accountId,pt.property_No,ac.totalAmount,ac.status,ac.amountCleared,ac.balanceAmount,tm.transactionName,p.firstName,p.lastName FROM AdvanceCollectionEntity ac INNER JOIN ac.accountObj a INNER JOIN a.person p,Property pt,TransactionMasterEntity tm WHERE tm.transactionCode=ac.transactionCode AND pt.propertyId=a.propertyId ORDER BY ac.advCollId DESC"),
	@NamedQuery(name = "AdvanceCollectionEntity.setAdvanceCollectionStatus", query = "UPDATE AdvanceCollectionEntity d SET d.status = :status WHERE d.advCollId = :advCollId"),
	@NamedQuery(name = "AdvanceCollectionEntity.getAllAccountNumbers", query = "SELECT DISTINCT a.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,pt.property_No FROM Account a,Property pt INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND a.accountStatus='Active'"),
	@NamedQuery(name = "AdvanceCollectionEntity.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM AdvanceCollectionEntity ac INNER JOIN ac.accountObj a"),
	@NamedQuery(name = "AdvanceCollectionEntity.commonFilterForPropertyNoUrl", query = "SELECT DISTINCT pt.property_No FROM AdvanceCollectionEntity ac INNER JOIN ac.accountObj a,Property pt WHERE pt.propertyId=a.propertyId"),
	@NamedQuery(name = "AdvanceCollectionEntity.commonFilterForTransactionNameUrl", query = "SELECT DISTINCT tm.transactionName FROM AdvanceCollectionEntity ac,TransactionMasterEntity tm WHERE ac.transactionCode=tm.transactionCode"),
	@NamedQuery(name = "AdvanceCollectionEntity.testUniqueAccount",query="SELECT ac FROM AdvanceCollectionEntity ac INNER JOIN ac.accountObj a WHERE a.accountId = :accountId"),
	@NamedQuery(name = "AdvanceCollectionEntity.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from AdvanceCollectionEntity ac INNER JOIN ac.accountObj a INNER JOIN a.person p"),
})
public class AdvanceCollectionEntity 
{
	@Id
	@Column(name="ADVCOLL_ID")
	@SequenceGenerator(name = "advcoll_seq", sequenceName = "ADVCOLL_ID_SEQ") 
	@GeneratedValue(generator = "advcoll_seq") 
	private int advCollId;
	
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
	
	@Column(name="TOTAL_CLEARED")
	private double amountCleared;
	
	@Column(name="BALANCE_AMOUNT")
	private double balanceAmount;
	
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
	
	public int getAdvCollId() {
		return advCollId;
	}

	public void setAdvCollId(int advCollId) {
		this.advCollId = advCollId;
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

	public double getAmountCleared() {
		return amountCleared;
	}

	public void setAmountCleared(double amountCleared) {
		this.amountCleared = amountCleared;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
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
