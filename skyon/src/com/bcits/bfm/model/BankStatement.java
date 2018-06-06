package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

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
@Table(name="BANK_STATEMENT")
@BatchSize(size = 10)
@NamedQueries
({
	@NamedQuery(name="BankStatement.findAll",query="SELECT b FROM BankStatement b ORDER BY b.bsId DESC"),
})
public class BankStatement implements java.io.Serializable
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	 @SequenceGenerator(name = "bankStatementSeq", sequenceName = "BANK_STATEMENT_SEQ")
	 @GeneratedValue(generator = "bankStatementSeq")
	 
	 @Column(name = "BS_ID", unique = true, nullable = false, precision = 11, scale = 0)
	 private int bsId;
	 
	 @Column(name = "BANK_NAME", unique = true, nullable = false, precision = 11, scale = 0)
	 private String bankName;
	 
	 @Column(name="ACCOUNT_NO", nullable=false, length=200)
	 private Long accountNo;
	 
	 @Column(name = "TX_DATE", length = 7)
	 private Date txDate;
	 
	 @Column(name = "DESCRIPTION", length = 100)
	 private String description;
	 
	 @Column(name = "CREDIT")
	 private Double credit;
	 
	 @Column(name = "DEBIT")
	 private Double debit;
	 
	 @Column(name = "BALANCE")
	 private Double balance;
	 
	 @Column(name = "STATUS")
	 private String status;
	 
	 @Column(name = "CREATED_BY")
	 private String createdBy;
	 
	 @Column(name = "LAST_UPDATED_BY")
	 private String lastUpdatedBy;
	 
	 @Column(name = "LAST_UPDATED_DT", length = 11)
	 private Timestamp lastUpdatedDt;
	 
	 public BankStatement(){}
	 
	 /** minimal constructor */
	 public BankStatement(int bsId, String bankName) 
	 {
			this.bsId = bsId;
			this.bankName = bankName;
	 }

	 /** full constructor */
	 public BankStatement(int bsId, String bankName, Long accountNo,
				Date txDate, String description, Double credit,
				Double debit, Double balance, String status,
				String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt) {
			this.bsId = bsId;
			this.bankName = bankName;
			this.accountNo = accountNo;
			this.txDate = txDate;
			this.description = description;
			this.credit = credit;
			this.debit = debit;
			this.balance = balance;
			this.status = status;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDt = lastUpdatedDt;
		}

	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Double getDebit() {
		return debit;
	}
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
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
	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}	 
	
	@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
}
