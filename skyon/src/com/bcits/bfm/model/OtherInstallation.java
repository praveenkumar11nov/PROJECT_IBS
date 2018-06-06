package com.bcits.bfm.model;

import java.io.Serializable;
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

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name = "OTHER_INSTALLATION")
@NamedQueries({
	//@NamedQuery(name = "OtherInstallation.findAll", query = "SELECT um.insId,um.insName,um.insLocation,um.meteredStatus,um.meteredConstant,um.status,um.createdBy,um.lastUpdatedBy FROM OtherInstallation um ORDER BY um.insId ASC"),
	@NamedQuery(name = "OtherInstallation.findAll", query = "SELECT oi.id,oi.insName,oi.insLocation,oi.meteredStatus,oi.meteredConstant,oi.status FROM OtherInstallation oi ORDER BY oi.id DESC"),
	@NamedQuery(name = "OtherInstallation.UpdateStatus",query="UPDATE OtherInstallation oi SET oi.status = :status WHERE oi.id = :id"),

	//@NamedQuery(name = "TransactionMasterEntity.getTransationCodesByType", query = "SELECT tm.transactionCode FROM TransactionMasterEntity tm where tm.typeOfService=:typeOfService ORDER BY tm.transactionCode ASC"),
	//@NamedQuery(name = "ElectricityBillLineItemEntity.getTaransactionCodeForOthers", query = "SELECT tm.transactionCode,tm.transactionName FROM TransactionMasterEntity tm where tm.typeOfService=:typeOfService and tm.transactionCode!='OT_TAX' and tm.transactionCode!='OT_TAX' and tm.transactionCode!='OT_INTEREST' and tm.transactionCode!='OT_TAX_INTEREST' and tm.transactionCode!='OT_ROF' and tm.transactionCode!='OT' ORDER BY tm.transactionCode ASC")
	
})
public class OtherInstallation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "other_installation_seq", sequenceName = "OTHER_INSTALLATION_SEQ") 
	@GeneratedValue(generator = "other_installation_seq") 
	@Column(name = "INSTAL_ID")	
	private int id;
	
	@Column(name = "NAME")
	private String insName;
	
	@Column(name = "LOCATION")
	private String insLocation;
	
	@Column(name = "METERED")
	private String meteredStatus;
	
	@Column(name = "METER_CONSTANT")
	private double meteredConstant;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
		
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
		
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDate = new Timestamp(new Date().getTime());

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}

	public String getInsLocation() {
		return insLocation;
	}

	public void setInsLocation(String insLocation) {
		this.insLocation = insLocation;
	}

	public String getMeteredStatus() {
		return meteredStatus;
	}

	public void setMeteredStatus(String meteredStatus) {
		this.meteredStatus = meteredStatus;
	}

	public double getMeteredConstant() {
		return meteredConstant;
	}

	public void setMeteredConstant(double meteredConstant) {
		this.meteredConstant = meteredConstant;
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

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
    @PrePersist
	protected void onCreate() 
	{
    	lastUpdatedDate = new Timestamp(new Date().getTime());
    	lastUpdatedBy=(String) SessionData.getUserDetails().get("userID");
		createdBy=(String) SessionData.getUserDetails().get("userID");
        this.status = "Inactive";

	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDate = new Timestamp(new Date().getTime());
		lastUpdatedBy=(String) SessionData.getUserDetails().get("userID");
	}
	
}
