package com.bcits.bfm.model;

import java.sql.Timestamp;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

/**
 * @author usews56
 *
 */
@Entity
@Table(name="SUB_LEDGER")  
@NamedQueries({
	  @NamedQuery(name = "ElectricitySubLedgerEntity.findAllById", query = "SELECT els.elSubLedgerid,els.transactionCode,els.amount,els.balanceAmount,tm.transactionName FROM ElectricitySubLedgerEntity els,TransactionMasterEntity tm WHERE els.transactionCode=tm.transactionCode AND els.electricityLedgerEntity.elLedgerid = :elLedgerid ORDER BY els.elSubLedgerid ASC"),
	  @NamedQuery(name = "ElectricitySubLedgerEntity.findAllById1", query = "SELECT els FROM ElectricitySubLedgerEntity els WHERE els.electricityLedgerEntity.elLedgerid = :elLedgerid ORDER BY els.transactionCode"),
	  @NamedQuery(name = "ElectricitySubLedgerEntity.findAll", query = "SELECT els FROM ElectricitySubLedgerEntity els ORDER BY els.elSubLedgerid DESC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.updateSubLedgerStatusFromInnerGrid",query="UPDATE ElectricitySubLedgerEntity a SET a.status = :status WHERE a.elSubLedgerid = :elSubLedgerid"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForCollections",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like '%_CS_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getLastBalanceAmount",query="SELECT sl.balanceAmount FROM ElectricitySubLedgerEntity sl WHERE sl.elSubLedgerid=(SELECT MAX(sl.elSubLedgerid) FROM ElectricitySubLedgerEntity sl)"),
	  @NamedQuery(name = "ElectricitySubLedgerEntity.getLastSubLedger", query = "SELECT els FROM ElectricitySubLedgerEntity els WHERE els.electricityLedgerEntity.elLedgerid = :elLedgerid AND els.electricityLedgerEntity.transactionCode=:transactionCode ORDER BY els.elSubLedgerid ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForElectricity",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'EL_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForGas",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'GA_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForSolidWaste",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'SW_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForWater",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'WT_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForOthers",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'CM_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForCam",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'CAM_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodesForTb",query="select tr.transactionCode From TransactionMasterEntity tr where tr.typeOfService=:typeOfService AND tr.transactionCode like 'TEL_%' ORDER BY tr.transactionId ASC"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionCodes",query="select tr From TransactionMasterEntity tr where tr.typeOfService=:typeOfService"),
	  @NamedQuery(name="ElectricitySubLedgerEntity.getTransactionMasterForCam",query="select tr From TransactionMasterEntity tr where tr.transactionCode!='CAM_ROF' AND tr.typeOfService=:typeOfService AND tr.transactionCode like 'CAM_%' ORDER BY tr.transactionId ASC"),
})

public class ElectricitySubLedgerEntity {

	@Id
	@SequenceGenerator(name = "elSubLedger_seq", sequenceName = "EL_SUB_LEDGER_SEQ") 
	@GeneratedValue(generator = "elSubLedger_seq") 
	@Column(name="ELS_ID")
	private int elSubLedgerid;
	
	/*@Column(name="ELL_ID")
	private int elLedgerid;*/
	
	@OneToOne	 
    @JoinColumn(name = "ELL_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityLedgerEntity electricityLedgerEntity;
	
	/*@Column(name="TR_ID")
	private int transactionId;*/
	
	/*@OneToOne	 
	@JoinColumn(name = "TR_ID", insertable = false, updatable = false, nullable = false)
	private TransactionMasterEntity transactionMasterEntity;*/
	
	@Column(name="TR_CODE")
	private String transactionCode;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="BALANCE_AMOUNT")
	private double balanceAmount;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;

	public int getElSubLedgerid() {
		return elSubLedgerid;
	}

	public void setElSubLedgerid(int elSubLedgerid) {
		this.elSubLedgerid = elSubLedgerid;
	}

	/*public int getElLedgerid() {
		return elLedgerid;
	}

	public void setElLedgerid(int elLedgerid) {
		this.elLedgerid = elLedgerid;
	}*/
	
	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public ElectricityLedgerEntity getElectricityLedgerEntity() {
		return electricityLedgerEntity;
	}

	public void setElectricityLedgerEntity(
			ElectricityLedgerEntity electricityLedgerEntity) {
		this.electricityLedgerEntity = electricityLedgerEntity;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/*public int getElLedgerid() {
		return elLedgerid;
	}

	public void setElLedgerid(int elLedgerid) {
		this.elLedgerid = elLedgerid;
	}*/
	
}
