package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@SuppressWarnings("serial")
@Entity
@Table(name="BILL_LINEITEM")  
@NamedQueries({
	//@NamedQuery(name = "CamSubLedgerEntity.findAllById", query = "SELECT els.camSubLedgerid,els.amount,els.balanceAmount,els.transactionCode,tm.transactionName FROM CamSubLedgerEntity els,TransactionMasterEntity tm WHERE tm.transactionCode=els.transactionCode AND els.camLedgerEntity.camLedgerid = :camLedgerid"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findAllById", query = "SELECT bli.elBillLineId,bli.transactionCode,bli.creditAmount,bli.debitAmount,bli.balanceAmount,tm.transactionName FROM ElectricityBillLineItemEntity bli,TransactionMasterEntity tm WHERE tm.transactionCode=bli.transactionCode and bli.electricityBillEntity.elBillId = :elBillId ORDER BY bli.elBillLineId ASC"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findAll", query = "SELECT bli FROM ElectricityBillLineItemEntity bli ORDER BY bli.elBillLineId DESC"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.setBillLineItemStatus", query = "UPDATE ElectricityBillLineItemEntity bli SET bli.status = :status WHERE bli.elBillLineId = :elBillLineId"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findAllLineItemsById", query = "SELECT bli FROM ElectricityBillLineItemEntity bli WHERE bli.electricityBillEntity.elBillId = :elBillId ORDER BY bli.elBillLineId ASC"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findLineItemBasedOnTransactionCode", query = "SELECT bli FROM ElectricityBillLineItemEntity bli WHERE bli.electricityBillEntity.elBillId = :elBillId AND bli.transactionCode=:transactionCode ORDER BY bli.elBillLineId ASC"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findByTransactionCode", query = "SELECT bli FROM ElectricityBillLineItemEntity bli WHERE bli.electricityBillEntity.elBillId = :elBillId AND bli.transactionCode=:transactionCode ORDER BY bli.elBillLineId ASC"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findAllLineItemsByIdUpdate", query = "SELECT bli FROM ElectricityBillLineItemEntity bli WHERE bli.electricityBillEntity.elBillId = :elBillId AND bli.transactionCode!='OT_TAX' and bli.transactionCode!='OT_INTEREST' and bli.transactionCode!='OT_TAX_INTEREST' ORDER BY bli.elBillLineId ASC"),
	  @NamedQuery(name = "ElectricityBillLineItemEntity.findBalanceTransactionCode", query = "SELECT bli.balanceAmount FROM ElectricityBillLineItemEntity bli WHERE bli.electricityBillEntity.elBillId = :elBillId AND bli.transactionCode=:transactionCode ORDER BY bli.elBillLineId ASC"),
	  @NamedQuery(name="ElectricityBillLineItemEntity.findAllDetailsForCamPosting",query="SELECT bli.elBillLineId,bli.transactionCode,bli.creditAmount,bli.debitAmount,bli.balanceAmount FROM ElectricityBillLineItemEntity bli  where bli.electricityBillEntity.elBillId = :elBillId ORDER BY bli.elBillLineId ASC")
})
public class ElectricityBillLineItemEntity implements Serializable{

	@Id
	@Column(name="ELBL_ID")
	@SequenceGenerator(name = "elBill_LineItem_seq", sequenceName = "ELBILL_LINEITEM_SEQ") 
	@GeneratedValue(generator = "elBill_LineItem_seq")
	private int elBillLineId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ELB_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityBillEntity electricityBillEntity;
	
	@Column(name="TR_CODE")
	private String transactionCode;
	
	@Column(name="CREDIT")
	private double creditAmount;
	
	@Column(name="DEBIT")
	private double debitAmount;
	
	@Column(name="BALANCE")
	private double balanceAmount;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Transient
	private String transactionName;

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public int getElBillLineId() {
		return elBillLineId;
	}

	public void setElBillLineId(int elBillLineId) {
		this.elBillLineId = elBillLineId;
	}

	/*public int getElBillId() {
		return elBillId;
	}

	public void setElBillId(int elBillId) {
		this.elBillId = elBillId;
	}*/

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
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

	public ElectricityBillEntity getElectricityBillEntity() {
		return electricityBillEntity;
	}

	public void setElectricityBillEntity(ElectricityBillEntity electricityBillEntity) {
		this.electricityBillEntity = electricityBillEntity;
	}

	
	public ElectricityBillLineItemEntity()
	{
		super();
	}

	public ElectricityBillLineItemEntity(String transactionCode,
			double creditAmount, double debitAmount, double balanceAmount,
			String status) {
		super();
		this.transactionCode = transactionCode;
		this.creditAmount = creditAmount;
		this.debitAmount = debitAmount;
		this.balanceAmount = balanceAmount;
		this.status = status;
	}
}
