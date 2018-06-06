package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="PREPAID_RECHARGE")
@NamedQueries({
	//@NamedQuery(name="PrepaidRechargeEntity.getMeterSerialNo",query="SELECT m.meterSerialNo From ElectricityMetersEntity m Where m.account.accountId=(SELECT a.accountId  FROM Account a WHERE a.propertyId = :propertyId) AND m.typeOfServiceForMeters='Electricity'"),
	@NamedQuery(name="PrepaidRechargeEntity.getAccountIDBasedOnMeterNo",query="SELECT m.account.accountId From ElectricityMetersEntity m Where m.meterSerialNo=:meterSerialNo"),
	@NamedQuery(name="PrepaidRechargeEntity.findALL",query="SELECT p.preRechargeId,p.txn_ID,p.txn_Date,p.consumer_Id FROM PrepaidRechargeEntity p Where p.status = 'Token Generated' AND p.preRechargeId IN(SELECT MAX(p.preRechargeId) from PrepaidRechargeEntity p WHERE p.status='Token Generated' GROUP BY p.consumer_Id)"),
	@NamedQuery(name="PrepaidRechargeEntity.getIDBasedOnTxnId",query="SELECT p.preRechargeId FROM PrepaidRechargeEntity p Where p.txn_ID=:txn_ID"),
	@NamedQuery(name="PrepaidRechargeEntity.getAccStByMonth",query="SELECT p.preRechargeId,p.rechargeAmount,p.accountId,p.modeOfPayment,p.txn_ID,p.txn_Date,p.typeOfService,p.rechargeDate FROM PrepaidRechargeEntity p Where p.accountId =:accountId AND TRUNC(p.txn_Date) BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD') AND TO_DATE(:toDate,'YYYY-MM-DD')"),
	@NamedQuery(name="PrepaidRechargeEntity.readDataForPaymentHtry",query="SELECT pr FROM PrepaidRechargeEntity pr ORDER BY pr.txn_Date DESC"),
	@NamedQuery(name="PrepaidRechargeEntity.getPaymentHistoryByDate",query="SELECT pr FROM PrepaidRechargeEntity pr Where TRUNC(pr.txn_Date) BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') AND TO_DATE(:toDate, 'YYYY-MM-DD')  ORDER BY pr.txn_Date DESC"),
	@NamedQuery(name="PrepaidRechargeEntity.getAllRecordForTally",query="SELECT pp FROM PrepaidRechargeEntity pp where EXTRACT(month FROM pp.txn_Date)=:month AND EXTRACT(year FROM pp.txn_Date)=:year AND pp.tallyStatus = 'Not Posted'"),
	
//	@NamedQuery(name="PrepaidRechargeEntity.getAllRecordForTally",query="SELECT pp FROM PrepaidRechargeEntity pp where EXTRACT(month FROM pp.txn_Date)>=:month AND EXTRACT(year FROM pp.txn_Date)>=:year AND EXTRACT(month FROM pp.txn_Date)<=:monthtoDate AND EXTRACT(year FROM pp.txn_Date)<=:yeartoDate AND pp.tallyStatus = 'Not Posted'"),
	
	@NamedQuery(name="PrepaidRechargeEntity.updateTallyStatus",query="UPDATE PrepaidRechargeEntity pp SET pp.tallyStatus =:tallyStatus where pp.preRechargeId=:preRechargeId"),
	@NamedQuery(name="PrepaidRechargeEntity.getAllTallyPosting",query="SELECT pr FROM PrepaidRechargeEntity pr where pr.preRechargeId=:preRechargeId"),
	//@NamedQuery(name="PrepaidRechargeEntity.getFiftyRecords",query="SELECT pr FROM PrepaidRechargeEntity pr where pr.")
	@NamedQuery(name="PrepaidRechargeEntity.getAll",query="SELECT pr FROM PrepaidRechargeEntity pr")

})
public class PrepaidRechargeEntity {
	
	@Id
	@Column(name="PRE_RECID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "rechargeId_seq", sequenceName = "PREPAID_RECHARGE_SEQ") 
	@GeneratedValue(generator = "rechargeId_seq") 
	private int preRechargeId;
	
	@Transient
	private int elmID;
	
	@OneToOne
	@JoinColumn(name = "EL_METER_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityMetersEntity elmeter;
	
	@Column(name="RECHATGE_AMOUNT")
	private double rechargeAmount;
	
	@Column(name="TOKEN_NO")
	private String tokenNo;
	
	@Column(name="RECHARGE_DATE")
	private Date rechargeDate;

	@Column(name="ACCOUNT_ID")
	private Integer accountId;
	
	/*@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Account account;*/
	
	@Column(name="MODE_OF_PAYMENT")
	private String modeOfPayment;

	@Column(name="MERCHANT_ID")
	private String merchantId;

	@Column(name="TRANSACTION_ID")
	private String txn_ID;
	
	@Column(name="TRANSACTION_DATE")
	private Date txn_Date;
	
	@Column(name="CONSUMER_NAME")
	private String consumer_Name;
	
	@Column(name="FATHER_NAME")
	private String father_Name;
	
	@Column(name="SERVICE_TYPE")
	private String typeOfService;
	
	@Column(name="CONSUMER_ID")
	private String consumer_Id;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="TALLY_STATUS")
	private String tallyStatus;
	
	@Column(name="METER_NUMBER")
	private String meterNumber;
	
	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getPreRechargeId() {
		return preRechargeId;
	}

	public void setPreRechargeId(int preRechargeId) {
		this.preRechargeId = preRechargeId;
	}

	public ElectricityMetersEntity getElmeter() {
		return elmeter;
	}

	public void setElmeter(ElectricityMetersEntity elmeter) {
		this.elmeter = elmeter;
	}

	public double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getTokenNo() {
		return tokenNo;
	}

	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}

	public Date getRechargeDate() {
		return rechargeDate;
	}

	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTxn_ID() {
		return txn_ID;
	}

	public void setTxn_ID(String txn_ID) {
		this.txn_ID = txn_ID;
	}

	public Date getTxn_Date() {
		return txn_Date;
	}

	public void setTxn_Date(Date txn_Date) {
		this.txn_Date = txn_Date;
	}

	public String getConsumer_Name() {
		return consumer_Name;
	}

	public void setConsumer_Name(String consumer_Name) {
		this.consumer_Name = consumer_Name;
	}

	public String getFather_Name() {
		return father_Name;
	}

	public void setFather_Name(String father_Name) {
		this.father_Name = father_Name;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public String getConsumer_Id() {
		return consumer_Id;
	}

	public void setConsumer_Id(String consumer_Id) {
		this.consumer_Id = consumer_Id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getElmID() {
		return elmID;
	}

	public void setElmID(int elmID) {
		this.elmID = elmID;
	}

	public String getTallyStatus() {
		return tallyStatus;
	}

	public void setTallyStatus(String tallyStatus) {
		this.tallyStatus = tallyStatus;
	}

	

	
}
