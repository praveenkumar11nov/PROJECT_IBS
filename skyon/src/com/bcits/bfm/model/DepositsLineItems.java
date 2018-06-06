package com.bcits.bfm.model;

import java.sql.Date;
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

@Entity
@Table(name="DEPOSITS_LINEITEMS") 
@NamedQueries({
	@NamedQuery(name = "DepositsLineItems.findAll", query = "SELECT d.ddId,d.typeOfServiceDeposit,d.transactionCode,d.category,d.amount,d.collectionType,d.instrumentNo,d.instrumentDate,tm.transactionName,d.paymentMode,d.bankName,dd.depositsId,d.status,d.loadChangeUnits,d.notes FROM DepositsLineItems d,TransactionMasterEntity tm INNER JOIN d.deposits dd where d.transactionCode=tm.transactionCode AND dd.depositsId=:depositsId ORDER BY d.ddId DESC"),
	@NamedQuery(name = "DepositsLineItems.getTransactionCodes", query = "SELECT DISTINCT tm.transactionCode,tm.transactionName,tm.typeOfService FROM TransactionMasterEntity tm WHERE tm.groupType='Deposit'"),
	@NamedQuery(name = "DepositsLineItems.getTransactionCodeList", query = "SELECT DISTINCT tm.transactionCode,tm.transactionName FROM TransactionMasterEntity tm WHERE tm.groupType='Deposit' AND tm.typeOfService=:typeOfService"),
	@NamedQuery(name = "DepositsLineItems.getAllLineItemsAmount", query = "SELECT SUM(dli.amount) FROM DepositsLineItems dli INNER JOIN dli.deposits d WHERE d.depositsId=:depositsId GROUP BY d.depositsId"),
	@NamedQuery(name = "DepositsLineItems.getDepositsObjByLineItemId", query = "SELECT d FROM DepositsLineItems dli INNER JOIN dli.deposits d WHERE dli.ddId=:ddId"),
	@NamedQuery(name = "DepositsLineItems.getSanctionedLoadDetails", query = "SELECT sp.serviceParameterId FROM ServiceParametersEntity sp INNER JOIN sp.serviceMastersEntity sm INNER JOIN sm.accountObj a INNER JOIN sp.serviceParameterMaster spm WHERE a.accountId=:accountId AND spm.spmName IN ('Sanctioned KW','Sanctioned HP','Sanctioned KVA')"),
	@NamedQuery(name = "DepositsLineItems.getRateForDepositFromRateSlab", query = "SELECT elrs.rate FROM ELRateSlabs elrs,ServiceMastersEntity sm INNER JOIN elrs.elRateMaster elr INNER JOIN sm.accountObj a INNER JOIN elr.elTariffMaster elt WHERE sm.elTariffID=elt.elTariffID AND a.accountId=:accountId AND sm.status='Active' AND elr.rateName=:rateName"),
	@NamedQuery(name = "DepositsLineItems.getServiceParameterObj", query = "SELECT sp.serviceParameterId FROM ServiceParametersEntity sp INNER JOIN sp.serviceMastersEntity sm INNER JOIN sm.accountObj a INNER JOIN sp.serviceParameterMaster spm WHERE a.accountId=:accountId AND sm.typeOfService=:typeOfService AND spm.spmName=:spmName"),
})
public class DepositsLineItems 
{
	@Id
	@Column(name="DD_ID")
	@SequenceGenerator(name = "depositsDetails_seq", sequenceName = "DEPOSITS_DETAILS_ID_SEQ") 
	@GeneratedValue(generator = "depositsDetails_seq") 
	private int ddId;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfServiceDeposit;
	
	@Transient
	private String loadEnhanced;
	
	@Transient
	private String sancationedLoadName;
	
	@Transient
	private String sancationedLoad;
	
	@Transient
	private double newSancationedLoad;
	
	@Column(name="TRANSACTION_CODE")
	private String transactionCode;
	
	@Transient
	private String transactionName;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="LOAD_CHANGE")
	private double loadChangeUnits;
	
	@Column(name="NOTES")
	private String notes;
	
	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name="COLLECTION_TYPE")
	private String collectionType;
	
	@Column(name="INSTRUMENT_NO")
	private String instrumentNo;
	
	@Column(name="INSTRUMENT_DATE")
	private Date instrumentDate;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "DEPOSITS_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Deposits deposits;

	public int getDdId() {
		return ddId;
	}

	public void setDdId(int ddId) {
		this.ddId = ddId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
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

	public Deposits getDeposits() {
		return deposits;
	}

	public void setDeposits(Deposits deposits) {
		this.deposits = deposits;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTypeOfServiceDeposit() {
		return typeOfServiceDeposit;
	}

	public void setTypeOfServiceDeposit(String typeOfServiceDeposit) {
		this.typeOfServiceDeposit = typeOfServiceDeposit;
	}

	public String getLoadEnhanced() {
		return loadEnhanced;
	}

	public void setLoadEnhanced(String loadEnhanced) {
		this.loadEnhanced = loadEnhanced;
	}

	public String getSancationedLoad() {
		return sancationedLoad;
	}

	public void setSancationedLoad(String sancationedLoad) {
		this.sancationedLoad = sancationedLoad;
	}

	public double getNewSancationedLoad() {
		return newSancationedLoad;
	}

	public void setNewSancationedLoad(double newSancationedLoad) {
		this.newSancationedLoad = newSancationedLoad;
	}

	public String getSancationedLoadName() {
		return sancationedLoadName;
	}

	public void setSancationedLoadName(String sancationedLoadName) {
		this.sancationedLoadName = sancationedLoadName;
	}

	public double getLoadChangeUnits() {
		return loadChangeUnits;
	}

	public void setLoadChangeUnits(double loadChangeUnits) {
		this.loadChangeUnits = loadChangeUnits;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
