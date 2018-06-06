package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_MC")
@NamedQueries({
	@NamedQuery(name = "AssetMaintenanceCost.findAll", query = "SELECT a FROM AssetMaintenanceCost a WHERE a.assetId =:assetId  ORDER BY a.amcId DESC"),
	//@NamedQuery(name = "AssetWarranty.getAllField", query = "SELECT a.awId,a.ashtId,a.warrantyFromDate,a.warrantyToDate,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate FROM AssetWarranty a WHERE a.ashtId =:ashtId  ORDER BY a.awId DESC"),
	//@NamedQuery(name = "AssetMaintenanceCost.getAllField", query = "SELECT a FROM AssetMaintenanceCost a WHERE a.ashId =:ashId  ORDER BY a.amcId DESC")
	@NamedQuery(name = "AssetMaintenanceCost.getAmListBasedOnAmId", query = "SELECT mc FROM AssetMaintenanceCost mc WHERE mc.amcId=:amcId"),
	@NamedQuery(name = "AssetMaintenanceCost.deleteAssetBasedOnAmcId", query = "DELETE FROM AssetMaintenanceCost mc where mc.amcId=:amcId")

})
public class AssetMaintenanceCost {
	
	private int amcId;
	//private int ashId;
	private Date amcDate;
	private int costIncurred;
	private String mcType;
	private String createdBy;
	private String updatedBy;
	private Date lastUpdatedDate;
	private int assetId;
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_MC" ,sequenceName="ASSET_MASTER_MC")
	@GeneratedValue(generator="SEQ_ASSET_MC")
	@Column(name="AMC_ID")
	public int getAmcId() {
		return amcId;
	}
	public void setAmcId(int amcId) {
		this.amcId = amcId;
	}
	
	/*@Column(name="ASH_ID",insertable=false, updatable=false, nullable = true)
	public int getAshId() {
		return ashId;
	}
	public void setAshId(int ashId) {
		this.ashId = ashId;
	}*/
	
	
	@Column(name="AMC_DT")
	public Date getAmcDate() {
		return amcDate;
	}

	public void setAmcDate(Date amcDate) {
		this.amcDate = amcDate;
	}
	
	@Column(name="COST_INCURRED")
	public int getCostIncurred() {
		return costIncurred;
	}
	public void setCostIncurred(int costIncurred) {
		this.costIncurred = costIncurred;
	}
	
	@Column(name="MC_TYPE")
	public String getMcType() {
		return mcType;
	}
	public void setMcType(String mcType) {
		this.mcType = mcType;
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
	
	private AssetServiceHistory assetServiceHistory;

	@OneToOne(targetEntity = AssetServiceHistory.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="ASH_ID", nullable = true)
	 public AssetServiceHistory getAssetServiceHistory() {
		return assetServiceHistory;
	}
	public void setAssetServiceHistory(AssetServiceHistory assetServiceHistory) {
		this.assetServiceHistory = assetServiceHistory;
	}
	
	
	
	
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
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
