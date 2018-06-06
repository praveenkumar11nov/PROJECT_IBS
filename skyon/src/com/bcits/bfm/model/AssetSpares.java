package com.bcits.bfm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_SPARES")
@NamedQueries({
	@NamedQuery(name = "AssetSpares.findAll", query = "SELECT a FROM AssetSpares a WHERE a.assetId =:assetId ORDER BY a.asId DESC"),
	@NamedQuery(name = "AssetSpares.getAssetSparesBasedOnId	", query = "SELECT a FROM AssetSpares a WHERE a.asId =:asId"),

})
public class AssetSpares {

	private int asId;
	private String partMake;
	private String partModelNumber;
	private Date partYear;
	private String partSlNumber;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int assetId;
	private int imId;

	@Id
	@SequenceGenerator(name="SEQ_ASSET_SPARES" ,sequenceName="ASSET_SPARES_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_SPARES")
	@Column(name="AS_ID")
	public int getAsId() {
		return asId;
	}
	public void setAsId(int asId) {
		this.asId = asId;
	}

	@Column(name="PART_MAKE")
	public String getPartMake() {
		return partMake;
	}
	public void setPartMake(String partMake) {
		this.partMake = partMake;
	}

	@Column(name="PART_MODEL_NO")
	public String getPartModelNumber() {
		return partModelNumber;
	}
	public void setPartModelNumber(String partModelNumber) {
		this.partModelNumber = partModelNumber;
	}

	@Column(name="PART_YEAR")
	public Date getPartYear() {
		return partYear;
	}
	public void setPartYear(Date partYear) {
		this.partYear = partYear;
	}

	@Column(name="PART_SL_NO")
	public String getPartSlNumber() {
		return partSlNumber;
	}
	public void setPartSlNumber(String partSlNumber) {
		this.partSlNumber = partSlNumber;
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

	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	
	@Column(name="IM_ID")
	public int getImId() {
		return imId;
	}
	public void setImId(int imId) {
		this.imId = imId;
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
	
	
	private ItemMaster itemMaster;
	
	@OneToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="IM_ID",insertable=false, updatable=false)
	public ItemMaster getItemMaster() {
		return itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
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

}
