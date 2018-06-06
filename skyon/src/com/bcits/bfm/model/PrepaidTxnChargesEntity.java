package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bcits.bfm.util.SessionData;
@Entity
@Table(name="PREPAID_SERVICE_CHARGES_MASTER")
@NamedQueries({
	@NamedQuery(name="PrepaidTxnChargesEntity.getData",query="SELECT pt FROM PrepaidTxnChargesEntity pt where pt.sid=:serviceId ORDER BY pt.transactionId"),
	@NamedQuery(name="PrepaidTxnChargesEntity.getcbId" ,query="SELECT pc.cid  FROM PrepaidCalculationBasisEntity pc where pc.sId =:serviceId"),
	@NamedQuery(name="PrepaidTxnChargesEntity.getCharges",query="SELECT pt FROM PrepaidTxnChargesEntity pt where pt.sid IN (SELECT p.serviceId FROM PrepaidServiceMaster p where p.service_Name=:service_Name)")
	
})
public class PrepaidTxnChargesEntity {
	
	@Id
	@SequenceGenerator(name="PREPAID_SERVICE_CHARGES_SEQ",sequenceName="PREPAID_SERVICE_CHARGES_SEQ")
	@GeneratedValue(generator="PREPAID_SERVICE_CHARGES_SEQ")
	@Column(name="TRANSACTION_ID")
	private int transactionId;
	
	/*@Column(name="SERVICE_NAME")
	private String service_Name;*/
	
	@Column(name="SID")
	private int sid;
	
	@Column(name="CHARGE_NAME")
	private String charge_Name;
	
	@Column(name="CHARGE_TYPE")
	private String charge_Type;
	
	@Column(name="RATE")
	private double rate;
	
	/*@Temporal(TemporalType.DATE)
	@Column(name="FROM_DATE")
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TO_DATE")
	private Date toDate;*/
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Column(name="CID")
	private int cId;
	
	@Column(name="VALID_FROM")
	private Date validFrom;
	
	@Column(name="VALID_TO")
	private Date validTo;
	
	@OneToOne
	@JoinColumn(name = "SID", insertable = false, updatable = false, nullable = false)
	private PrepaidServiceMaster prepaidServiceMaster;
	
	@OneToOne
	@JoinColumn(name = "CID", insertable = false, updatable = false, nullable = false)
	private PrepaidCalculationBasisEntity cBasisEntity;

	

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getCharge_Type() {
		return charge_Type;
	}

	public void setCharge_Type(String charge_Type) {
		this.charge_Type = charge_Type;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public PrepaidServiceMaster getPrepaidServiceMaster() {
		return prepaidServiceMaster;
	}

	public void setPrepaidServiceMaster(PrepaidServiceMaster prepaidServiceMaster) {
		this.prepaidServiceMaster = prepaidServiceMaster;
	}

	public PrepaidCalculationBasisEntity getcBasisEntity() {
		return cBasisEntity;
	}

	public void setcBasisEntity(PrepaidCalculationBasisEntity cBasisEntity) {
		this.cBasisEntity = cBasisEntity;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCharge_Name() {
		return charge_Name;
	}

	public void setCharge_Name(String charge_Name) {
		this.charge_Name = charge_Name;
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
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
}
