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
@Table(name="BILL_PARAMETERS_MASTER")
@BatchSize(size = 10)
@NamedQueries({
	@NamedQuery(name = "BillingParameterMaster.UpdateStatus",query="UPDATE BillingParameterMaster el SET el.status = :status WHERE el.bpmId = :bpmId"),
	@NamedQuery(name="BillingParameterMaster.findAll",query="SELECT bpm from BillingParameterMaster bpm ORDER BY bpm.bpmId DESC")
})
public class BillingParameterMaster implements java.io.Serializable{
	
	@Id
	@SequenceGenerator(name="seqBillingParameterMaster", sequenceName="BILL_PARAMETER_MASTER_SEQ")
	@GeneratedValue(generator="seqBillingParameterMaster")
	@Column(name="BVM_ID", unique=true)
	private int bpmId;
	
	@Column(name="STATUS", length = 20)
	private String status;
	
	@Column(name="BVM_SEQUENCE")
	private double bpmSequence;
	
	
	@Column(name="LAST_UPDATED_DT",nullable = false)
	private Timestamp lastupdatedDt;
	
	@Column(name="SERVICE_TYPE")
	private String bpmServiceType;
	
	@Column(name="BVM_NAME")
	private String bpmName;
	
	@Column(name="BVM_DATA_TYPE")
	private String bpmDataType;
	
	@Column(name="BVM_DESCRIPTION")
	private String bpmDescription;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastupdatedBy;
	
	private static final long serialVersionUID = 1L;
	
	public BillingParameterMaster(){
		
	}

	public int getBpmId() {
		return bpmId;
	}

	public void setBpmId(int bpmId) {
		this.bpmId = bpmId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getBpmSequence() {
		return bpmSequence;
	}

	public void setBpmSequence(double bpmSequence) {
		this.bpmSequence = bpmSequence;
	}

	public Timestamp getLastupdatedDt() {
		return lastupdatedDt;
	}

	public void setLastupdatedDt(Timestamp lastupdatedDt) {
		this.lastupdatedDt = lastupdatedDt;
	}

	public String getBpmServiceType() {
		return bpmServiceType;
	}

	public void setBpmServiceType(String bpmServiceType) {
		this.bpmServiceType = bpmServiceType;
	}

	public String getBpmName() {
		return bpmName;
	}

	public void setBpmName(String bpmName) {
		this.bpmName = bpmName;
	}

	public String getBpmDataType() {
		return bpmDataType;
	}

	public void setBpmDataType(String bpmDataType) {
		this.bpmDataType = bpmDataType;
	}

	public String getBpmDescription() {
		return bpmDescription;
	}

	public void setBpmDescription(String bpmDescription) {
		this.bpmDescription = bpmDescription;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastupdatedBy() {
		return lastupdatedBy;
	}

	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}
	@PrePersist
	protected void onCreate() 
	{
		lastupdatedDt = new Timestamp(new Date().getTime());
		lastupdatedBy=(String) SessionData.getUserDetails().get("userID");
		createdBy=(String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastupdatedDt = new Timestamp(new Date().getTime());
		lastupdatedBy=(String) SessionData.getUserDetails().get("userID");
	}

}
