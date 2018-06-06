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

import org.springframework.format.annotation.DateTimeFormat;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_WARRANTY")
@NamedQueries({
	@NamedQuery(name = "AssetWarranty.findAll", query = "SELECT a FROM AssetWarranty a WHERE a.assetId =:assetId  ORDER BY a.awId DESC"),
	//@NamedQuery(name = "AssetWarranty.getAllField", query = "SELECT a.awId,a.assetId,a.warrantyFromDate,a.warrantyToDate,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate FROM AssetWarranty a WHERE a.assetId =:assetId  ORDER BY a.awId DESC"),
	@NamedQuery(name = "AssetWarranty.getAllField", query = "SELECT a FROM AssetWarranty a WHERE a.assetId =:assetId  ORDER BY a.awId DESC"),
	@NamedQuery(name = "AssetWarranty.getUsersListBasedOnDeptId", query = "SELECT obj FROM Users obj WHERE obj.department=:department"),
	@NamedQuery(name = "AssetWarranty.getAssetWarrantyListBwDates", query = "SELECT o FROM AssetWarranty o WHERE o.warrantyToDate BETWEEN ADD_MONTHS(SYSDATE, 0) AND ADD_MONTHS(SYSDATE, 1)"),
})
public class AssetWarranty {
	
	private int awId;
	private int assetId;
	private Date warrantyFromDate;
	private Date warrantyToDate;
	private String warrantyType;
	private String warrantyValid;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_WARRANTY" ,sequenceName="ASSET_WARRANTY_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_WARRANTY")
	@Column(name="AW_ID")
	public int getAwId() {
		return awId;
	}
	public void setAwId(int awId) {
		this.awId = awId;
	}
	
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Column(name="WARRANTY_FROM_DATE")
	@DateTimeFormat(pattern="dd/mm/yy")
	public Date getWarrantyFromDate() {
		return warrantyFromDate;
	}
	public void setWarrantyFromDate(Date warrantyFromDate) {
		this.warrantyFromDate = warrantyFromDate;
	}
	
	@Column(name="WARRANTY_TO_DATE")
	@DateTimeFormat(pattern="dd/mm/yy")
	public Date getWarrantyToDate() {
		return warrantyToDate;
	}
	public void setWarrantyToDate(Date warrantyToDate) {
		this.warrantyToDate = warrantyToDate;
	}
	
	@Column(name="WARRANTY_TYPE")
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}
	
	@Column(name="WARRANTY_VALID")
	public String getWarrantyValid() {
		return warrantyValid;
	}
	public void setWarrantyValid(String warrantyValid) {
		this.warrantyValid = warrantyValid;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
	

	/*@PrePersist
	protected void onCreate() {
	   lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	   createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}*/
}
