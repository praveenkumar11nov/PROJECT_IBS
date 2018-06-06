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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

/**
 * @author usews56
 *
 */
@Entity
@Table(name="CAM_SUB_LEDGER")  
@NamedQueries({
	 @NamedQuery(name = "CamSubLedgerEntity.transactionCodeList", query = "SELECT DISTINCT tm.transactionCode, tm.transactionName from TransactionMasterEntity tm WHERE tm.groupType =:camHeadProperty"),
	 /* @NamedQuery(name = "CamSubLedgerEntity.findAllById", query = "SELECT els.camSubLedgerid,els.amount,els.balanceAmount,els.transactionCode,tm.transactionName,cl.camLedgerid FROM CamSubLedgerEntity els,TransactionMasterEntity tm INNER JOIN els.camLedgerEntity cl WHERE tm.transactionCode=els.transactionCode AND cl.camLedgerid = :camLedgerid"),
	  @NamedQuery(name="CamSubLedgerEntity.updateAmountInCamLedger",query="UPDATE CamLedgerEntity a SET a.amount = :amount,a.balance=:balanceAmount WHERE a.camLedgerid = :camLedgerid"),
	  @NamedQuery(name = "CamSubLedgerEntity.getLastTransactionCamLedgerId", query = "SELECT MAX(camLedgerid) FROM CamLedgerEntity cl WHERE cl.camHeadProperty=(SELECT c.camHeadProperty FROM CamLedgerEntity c WHERE c.camLedgerid=:camLedgerid)"),
	  @NamedQuery(name = "CamSubLedgerEntity.getLastCamSubLedger", query = "SELECT els FROM CamSubLedgerEntity els WHERE els.camLedgerEntity.camLedgerid = :camLedgerid AND els.transactionCode=:transactionCode"),
	  @NamedQuery(name="CamSubLedgerEntity.getTotalSubLedgerAmountBasedOnCamLedgerId",query="SELECT SUM(csl.amount) FROM CamSubLedgerEntity csl WHERE csl.camLedgerEntity.camLedgerid = :camLedgerid"),*/
})
public class CamSubLedgerEntity {

	@Id
	@SequenceGenerator(name = "camSubLedger_seq", sequenceName = "CAM_SUB_LEDGER_SEQ") 
	@GeneratedValue(generator = "camSubLedger_seq") 
	@Column(name="CSL_ID")
	private int camSubLedgerid;
	
	@Transient
	private int camLedgerid;
	
	@OneToOne	 
    @JoinColumn(name = "CL_ID")
	private CamLedgerEntity camLedgerEntity;
	
	@Column(name="AMOUNT")
	private double amount;
	
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
	
	public int getCamSubLedgerid() {
		return camSubLedgerid;
	}

	public void setCamSubLedgerid(int camSubLedgerid) {
		this.camSubLedgerid = camSubLedgerid;
	}

	public CamLedgerEntity getCamLedgerEntity() {
		return camLedgerEntity;
	}

	public void setCamLedgerEntity(CamLedgerEntity camLedgerEntity) {
		this.camLedgerEntity = camLedgerEntity;
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

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public int getCamLedgerid() {
		return camLedgerid;
	}

	public void setCamLedgerid(int camLedgerid) {
		this.camLedgerid = camLedgerid;
	}

	
}
