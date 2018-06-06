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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="PAYMENT_SEGMENTS")  
@NamedQueries({
	@NamedQuery(name = "PaymentSegmentsEntity.findAllById", query = "SELECT ps FROM PaymentSegmentsEntity ps where ps.billingPaymentsEntity.paymentCollectionId=:paymentCollectionId ORDER BY ps.paymentSegmentId DESC"),
	@NamedQuery(name = "PaymentSegmentsEntity.findAll", query = "SELECT ps FROM PaymentSegmentsEntity ps"),
	@NamedQuery(name = "PaymentSegmentsEntity.getAccountDetails", query = "SELECT elb FROM ElectricityLedgerEntity elb WHERE elb.accountId=:accountId AND elb.postType NOT IN ('ARREARS','INIT') AND elb.ledgerType!='Common Ledger' AND elb.elLedgerid IN (SELECT max(eb.elLedgerid) FROM ElectricityLedgerEntity eb WHERE eb.accountId=:accountId AND eb.ledgerType=:ledgerType)"),
	@NamedQuery(name = "PaymentSegmentsEntity.getAccountDetailsBasedOnDeposit", query = "SELECT eb FROM ElectricityBillEntity eb WHERE eb.accountId=:accountId AND eb.status='Posted' AND eb.postType='Deposit'"),
	@NamedQuery(name = "PaymentSegmentsEntity.getAccountDetailsBasedOnAdvanceBill", query = "SELECT eb FROM AdvanceBill eb WHERE eb.accountId=:accountId AND eb.status='Approved' AND eb.postType='Advance Bill'")
})
public class PaymentSegmentsEntity {
	
	@Id
	@SequenceGenerator(name = "paymentSegments_seq", sequenceName = "PAYMENT_SEGMENTS_SEQ") 
	@GeneratedValue(generator = "paymentSegments_seq") 
	@Column(name="PS_ID")
	private int paymentSegmentId;
	
	/*@Column(name="CP_ID")
	private int paymentCollectionId;*/
	
	/*@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn*/
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CP_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private BillingPaymentsEntity billingPaymentsEntity;
	
	@Column(name="BILL_SEGMENT")
	private String billSegment;
	
	@Column(name="BILL_REF")
	private String billReferenceNo;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="POSTED_TO_LEDGER")
	private String postedToLedger;
	
	@Column(name="POSTED_TO_LEDGER_DT")
	private Timestamp postedLedgerDate;
	
	@Column(name="BILL_MONTH")
	private Date billMonth;
	
	@Column(name="STATUS")
	private String status;
		
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	public int getPaymentSegmentId() {
		return paymentSegmentId;
	}

	public void setPaymentSegmentId(int paymentSegmentId) {
		this.paymentSegmentId = paymentSegmentId;
	}

	/*public int getPaymentCollectionId() {
		return paymentCollectionId;
	}

	public void setPaymentCollectionId(int paymentCollectionId) {
		this.paymentCollectionId = paymentCollectionId;
	}*/

	public BillingPaymentsEntity getBillingPaymentsEntity() {
		return billingPaymentsEntity;
	}

	public void setBillingPaymentsEntity(BillingPaymentsEntity billingPaymentsEntity) {
		this.billingPaymentsEntity = billingPaymentsEntity;
	}

	public String getBillSegment() {
		return billSegment;
	}

	public void setBillSegment(String billSegment) {
		this.billSegment = billSegment;
	}

	public String getBillReferenceNo() {
		return billReferenceNo;
	}

	public void setBillReferenceNo(String billReferenceNo) {
		this.billReferenceNo = billReferenceNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPostedToLedger() {
		return postedToLedger;
	}

	public void setPostedToLedger(String postedToLedger) {
		this.postedToLedger = postedToLedger;
	}

	public Timestamp getPostedLedgerDate() {
		return postedLedgerDate;
	}

	public void setPostedLedgerDate(Timestamp postedLedgerDate) {
		this.postedLedgerDate = postedLedgerDate;
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

	public Date getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(Date billMonth) {
		this.billMonth = billMonth;
	}
}
