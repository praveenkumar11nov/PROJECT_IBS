package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

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
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ADJUSTMENTS")  
@BatchSize(size = 10)
@NamedQueries({
	@NamedQuery(name = "PaymentAdjustmentEntity.findAll", query = "SELECT pa.adjustmentId,pa.jvDate,pa.adjustmentDate,pa.adjustmentNo,pa.adjustmentLedger,pa.adjustmentAmount,pa.postedToGl,pa.postedGlDate,pa.status,pa.accountId,a.accountNo,p.firstName,p.lastName,(select pr.property_No from  Property pr "
			+ "where pr.propertyId = a.propertyId),pa.adjustmentType,pa.tallystatus,pa.remarks FROM PaymentAdjustmentEntity pa,Account a INNER JOIN a.person p WHERE a.accountId=pa.accountId AND EXTRACT(month FROM pa.adjustmentDate)=(SELECT EXTRACT(month FROM pa.adjustmentDate) FROM PaymentAdjustmentEntity pa WHERE pa.adjustmentId=(SELECT MAX(pa.adjustmentId) FROM PaymentAdjustmentEntity pa)) AND EXTRACT(year FROM pa.adjustmentDate)=(SELECT EXTRACT(year FROM pa.adjustmentDate) FROM PaymentAdjustmentEntity pa WHERE pa.adjustmentId=(SELECT MAX(pa.adjustmentId) FROM PaymentAdjustmentEntity pa)) ORDER BY pa.adjustmentId DESC"),
	@NamedQuery(name = "PaymentAdjustmentEntity.setAdjustmentStatus", query = "UPDATE PaymentAdjustmentEntity bp SET bp.status = :status,bp.clearedStatus=:clearedStatus WHERE bp.adjustmentId = :adjustmentId"),
	@NamedQuery(name = "PaymentAdjustmentEntity.setPostedToGl", query = "UPDATE PaymentAdjustmentEntity bp SET bp.postedToGl = :postedToGl WHERE bp.adjustmentId = :adjustmentId"),
	@NamedQuery(name = "PaymentAdjustmentEntity.setPostedGlDate", query = "UPDATE PaymentAdjustmentEntity bp SET bp.postedGlDate = :postedGlDate WHERE bp.adjustmentId = :adjustmentId"),
	@NamedQuery(name = "PaymentAdjustmentEntity.setStatusApproved", query = "UPDATE PaymentAdjustmentEntity bp SET bp.status = :status WHERE bp.status ='Created'"),
	@NamedQuery(name = "PaymentAdjustmentEntity.getAdjustmentId", query = "SELECT bp FROM PaymentAdjustmentEntity bp WHERE bp.status='Approved' ORDER BY bp.adjustmentId DESC"),
	@NamedQuery(name = "PaymentAdjustmentEntity.getAdjusmentCalcItemList", query = "SELECT ps FROM AdjustmentCalcLinesEntity ps WHERE ps.paymentAdjustmentEntity.adjustmentId=:adjustmentId AND ps.transactionCode=:transactionCode ORDER BY ps.calcLineId"),
	@NamedQuery(name = "PaymentAdjustmentEntity.getAllPaidAccountNumbers", query = "SELECT  DISTINCT eb.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM ElectricityBillEntity eb,Account a INNER JOIN a.person p WHERE eb.accountId = a.accountId AND eb.status IN('Paid','Posted')"),
	@NamedQuery(name = "PaymentAdjustmentEntity.getAllAdjustmentByAccountId", query = "SELECT bp FROM PaymentAdjustmentEntity bp WHERE bp.accountId=:accountId and bp.status='Posted' AND bp.adjustmentLedger=:adjustmentLedger ORDER BY bp.adjustmentId DESC"),
	@NamedQuery(name = "PaymentAdjustmentEntity.commonFilterForAccountNumbersAdjustmentUrl", query = "SELECT DISTINCT a.accountNo FROM PaymentAdjustmentEntity el,Account a WHERE el.accountId=a.accountId"),
	@NamedQuery(name = "PaymentAdjustmentEntity.checkForNotPostedAdjustmentsAccountsIn", query = "SELECT pa.adjustmentId FROM PaymentAdjustmentEntity pa WHERE pa.accountId=:accountId AND pa.status IN ('Created','Approved')"),
	@NamedQuery(name = "PaymentAdjustmentEntity.searchAdjustmentDataByMonth", query = "SELECT pa.adjustmentId,pa.jvDate,pa.adjustmentDate,pa.adjustmentNo,pa.adjustmentLedger,pa.adjustmentAmount,pa.postedToGl,pa.postedGlDate,pa.status,pa.accountId,a.accountNo,p.firstName,p.lastName,(select pr.property_No from  Property pr "
			+ "where pr.propertyId = a.propertyId),pa.adjustmentType,pa.tallystatus,pa.remarks FROM PaymentAdjustmentEntity pa,Account a INNER JOIN a.person p WHERE a.accountId=pa.accountId AND TRUNC(pa.adjustmentDate) BETWEEN TO_DATE(:fromDateVal,'YYYY-MM-DD') AND TO_DATE(:toDateVal,'YYYY-MM-DD') ORDER BY pa.adjustmentId DESC"),
	/*@NamedQuery(name="PaymentAdjustmentEntity.getAccountIdByPropertyId",query="SELECT a.accountId FROM Account a ,Property p  WHERE  p.propertyId = a.propertyId and p.propertyId=:propertyId and a.accountStatus='Active' "),*/
	@NamedQuery(name="PaymentAdjustmentEntity.getAccountIdByPropertyId",query="select b.accountObj.accountId from BillingWizardEntity b WHERE b.accountObj.propertyId=:propertyId And serviceMastersEntity.typeOfService=:serviceType"),
	@NamedQuery(name = "PaymentAdjustmentEntity.getAllFiftyBill",query = "select pp from PaymentAdjustmentEntity pp WHERE pp.tallystatus =:tallyStatus and EXTRACT(month FROM pp.adjustmentDate) =:month and EXTRACT(year FROM pp.adjustmentDate) =:year and pp.status NOT IN ('Cancelled','Generated') and pp.tallystatus='Not Posted'"),
})
public class PaymentAdjustmentEntity {

	@Id
	@SequenceGenerator(name = "paymentAdjustment_seq", sequenceName = "PAYMENT_ADJUSTMENT_SEQ") 
	@GeneratedValue(generator = "paymentAdjustment_seq") 
	@Column(name="ADJ_ID")
	private int adjustmentId;
	
	@Column(name="JV_DT")
	private Timestamp jvDate;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;
	
	@Transient
	private String accountNo;
	
	@Transient
	private String property_No;
	
	@Column(name="JV_DATE")
	private Date adjustmentDate;
	
	@Column(name="JV_NO")
	private String adjustmentNo;
	
	@Column(name="JV_LEDGER")
	private String adjustmentLedger;
	
	@Column(name="JV_AMOUNT")
	private double adjustmentAmount;
	
	@Column(name="POSTED_TO_GL")
	private String postedToGl;
	
	@Column(name="POSTED_TO_GL_DT")
	private Timestamp postedGlDate;
	
	@Column(name="CLEARED_STATUS")
	private String clearedStatus;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	@Column(name="ADJUSTMENT_TYPE")
	private String adjustmentType;
	
	@Column(name="TALLY_STATUS")
	private String tallystatus;
	
	@Column(name="REMARKS")
	private String remarks;

	public String getTallystatus() {
		return tallystatus;
	}

	public void setTallystatus(String tallystatus) {
		this.tallystatus = tallystatus;
	}

	public int getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(int adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public Timestamp getJvDate() {
		return jvDate;
	}

	public void setJvDate(Timestamp jvDate) {
		this.jvDate = jvDate;
	}

	public Date getAdjustmentDate() {
		return adjustmentDate;
	}

	public void setAdjustmentDate(Date adjustmentDate) {
		this.adjustmentDate = adjustmentDate;
	}

	public String getAdjustmentNo() {
		return adjustmentNo;
	}

	public void setAdjustmentNo(String adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
	}

	public String getAdjustmentLedger() {
		return adjustmentLedger;
	}

	public void setAdjustmentLedger(String adjustmentLedger) {
		this.adjustmentLedger = adjustmentLedger;
	}

	public double getAdjustmentAmount() {
		return adjustmentAmount;
	}
	
	public void setAdjustmentAmount(double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPostedToGl() {
		return postedToGl;
	}

	public void setPostedToGl(String postedToGl) {
		this.postedToGl = postedToGl;
	}

	public Timestamp getPostedGlDate() {
		return postedGlDate;
	}

	public void setPostedGlDate(Timestamp postedGlDate) {
		this.postedGlDate = postedGlDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getClearedStatus() {
		return clearedStatus;
	}

	public void setClearedStatus(String clearedStatus) {
		this.clearedStatus = clearedStatus;
	}
	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
}
