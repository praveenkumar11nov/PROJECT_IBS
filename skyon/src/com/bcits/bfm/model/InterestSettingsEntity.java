package com.bcits.bfm.model;

import java.io.Serializable;
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

import com.bcits.bfm.util.SessionData;

@NamedQueries({ 
	@NamedQuery(name = "InterestSettingsEntity.findAll", query = "SELECT ise.settingId,ise.interestBasedOn,ise.status FROM InterestSettingsEntity ise ORDER BY ise.settingId DESC"),
	@NamedQuery(name = "InterestSettingsEntity.findAllData", query = "SELECT ise FROM InterestSettingsEntity ise"),
})

@Entity
@Table(name = "INTEREST_SETTINGS")
public class InterestSettingsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "interestSettingsSeq", sequenceName = "INTEREST_SETTINGS_SEQ")
	@GeneratedValue(generator = "interestSettingsSeq")
	@Column(name = "ID")
	private int settingId;
	
	@Column(name="INTEREST_BASED")
	private String interestBasedOn;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public String getInterestBasedOn() {
		return interestBasedOn;
	}

	public void setInterestBasedOn(String interestBasedOn) {
		this.interestBasedOn = interestBasedOn;
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
	  lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	 }
	
}
