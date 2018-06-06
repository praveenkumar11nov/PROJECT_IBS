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

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	
	@NamedQuery(name="AdjustmentMasterEntity.findAll",query="SELECT a.adjMasterId,a.adjName,a.status,a.description FROM AdjustmentMasterEntity a "),
	@NamedQuery(name = "AdjustmentMasterEntity.adjustmentMasterStatus", query = "UPDATE AdjustmentMasterEntity ac SET ac.status = :status WHERE adjMasterId.id = :id"),
	@NamedQuery(name="AdjustmentMasterEntity.getAllActiveAdjustmentName",query="SELECT a.adjMasterId,a.adjName  FROM AdjustmentMasterEntity a Where a.status='Active' "),
	
})
@Table(name = "ADJUSTMENTMASTER")
public class AdjustmentMasterEntity {
	@Id
	@SequenceGenerator(name="ADJUSTMENTMASTER_SEQ",sequenceName="ADJUSTMENTMASTER_SEQ")
	@GeneratedValue(generator="ADJUSTMENTMASTER_SEQ")
	@Column(name = "ADJ_MASTERID")
	private int adjMasterId;
	
	@Column(name = "ADJ_NAME")
	private String adjName;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAdjMasterId() {
		return adjMasterId;
	}

	public void setAdjMasterId(int adjMasterId) {
		this.adjMasterId = adjMasterId;
	}

	public String getAdjName() {
		return adjName;
	}

	public void setAdjName(String adjName) {
		this.adjName = adjName;
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

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
}
