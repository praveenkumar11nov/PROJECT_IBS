package com.bcits.bfm.model;

import java.util.Date;

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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_MP")
@NamedQueries({
	@NamedQuery(name = "AssetMaintenance.findAll", query = "SELECT a FROM AssetMaintenance a WHERE a.assetId =:assetId  ORDER BY a.ampId DESC"),
	//@NamedQuery(name = "AssetWarranty.getAllField", query = "SELECT a.awId,a.assetId,a.warrantyFromDate,a.warrantyToDate,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate FROM AssetWarranty a WHERE a.assetId =:assetId  ORDER BY a.awId DESC"),
	@NamedQuery(name = "AssetMaintenance.getAllField", query = "SELECT a FROM AssetMaintenance a WHERE a.assetId =:assetId  ORDER BY a.ampId DESC")

})
public class AssetMaintenance {
	
	private int ampId;
	private int assetId;
	private String maintainenceType;
	private String maintenanceDescription;
	private String periodicity;
	private Date lastMaintained;
	private String createdBy;
	private String updatedBy;
	private Date lastUpdatedDate;
	
	
	
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_MP" ,sequenceName="ASSET_MASTER_MP")
	@GeneratedValue(generator="SEQ_ASSET_MP")
	@Column(name="AMP_ID")
	public int getAmpId() {
		return ampId;
	}
	public void setAmpId(int ampId) {
		this.ampId = ampId;
	}
	
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Column(name="MAINTAINCE_TYPE")
	public String getMaintainenceType() {
		return maintainenceType;
	}
	public void setMaintainenceType(String maintainenceType) {
		this.maintainenceType = maintainenceType;
	}
	
	@Column(name="AMP_DESCRIPTION")
	public String getMaintenanceDescription() {
		return maintenanceDescription;
	}
	public void setMaintenanceDescription(String maintenanceDescription) {
		this.maintenanceDescription = maintenanceDescription;
	}
	
	@Column(name="PERIODOCITY")
	public String getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}
	
	@Column(name="LAST_MAINTAINED")
	public Date getLastMaintained() {
		return lastMaintained;
	}
	public void setLastMaintained(Date lastMaintained) {
		this.lastMaintained = lastMaintained;
	}
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="LAST_UPDATED_DT")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	private Asset asset;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="AM_ID",insertable=false, updatable=false)
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	
	@PrePersist
	 protected void onCreate() {
	    updatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	 @PreUpdate
	 protected void onUpdate() {
		 updatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	
}
