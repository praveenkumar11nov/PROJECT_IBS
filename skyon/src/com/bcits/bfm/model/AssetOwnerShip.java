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
@Table(name="ASSET_OWNERSHIP")
@NamedQueries({
	@NamedQuery(name = "AssetOwnerShip.findAll", query = "SELECT a FROM AssetOwnerShip a ORDER BY a.aoId DESC"),
	@NamedQuery(name = "AssetOwnerShip.getByAllField", query = "SELECT a.aoId,a.assetId,a.ownerShip,a.maintainanceOwner,a.aoStartDate,a.aoEndDate,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate FROM AssetOwnerShip a ORDER BY a.aoId DESC"),
	@NamedQuery(name = "AssetOwnerShip.amcList", query = "SELECT a FROM AssetOwnerShip a WHERE a.assetId=:assetId"),
	@NamedQuery(name = "AssetOwnerShip.findAllList", query = "SELECT a.aoId,a.assetId,at.assetName,p.firstName,p.lastName,a.ownerShip,a.maintainanceOwner,mp.firstName,mp.lastName,a.aoStartDate,a.aoEndDate,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate FROM AssetOwnerShip a INNER JOIN a.asset at INNER JOIN a.ownerShipPerson p INNER JOIN a.maintainanceOwnerPerson mp ORDER BY a.aoId DESC"),

	})
public class AssetOwnerShip {
	
	private int aoId;
	private int assetId;
	private int ownerShip;
	private int maintainanceOwner;
	private java.util.Date aoStartDate;
	private java.util.Date aoEndDate;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_OWNERSHIP" ,sequenceName="ASSET_OWNERSHIP_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_OWNERSHIP")
	@Column(name="AO_ID")
	public int getAoId() {
		return aoId;
	}
	public void setAoId(int aoId) {
		this.aoId = aoId;
	}
	
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Column(name="OWNERSHIP")
	public int getOwnerShip() {
		return ownerShip;
	}
	public void setOwnerShip(int ownerShip) {
		this.ownerShip = ownerShip;
	}
	
	@Column(name="MAINTAINANCE_OWNER")
	public int getMaintainanceOwner() {
		return maintainanceOwner;
	}
	public void setMaintainanceOwner(int maintainanceOwner) {
		this.maintainanceOwner = maintainanceOwner;
	}
	
	@Column(name="AO_START_DT")
	public java.util.Date getAoStartDate() {
		return aoStartDate;
	}
	public void setAoStartDate(java.util.Date aoStartDate) {
		this.aoStartDate = aoStartDate;
	}
	
	@Column(name="AO_END_DT")
	public java.util.Date getAoEndDate() {
		return aoEndDate;
	}
	public void setAoEndDate(java.util.Date aoEndDate) {
		this.aoEndDate = aoEndDate;
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


	private Person ownerShipPerson;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="OWNERSHIP",insertable=false, updatable=false)
	public Person getOwnerShipPerson() {
		return ownerShipPerson;
	}
	public void setOwnerShipPerson(Person ownerShipPerson) {
		this.ownerShipPerson = ownerShipPerson;
	}
	
	
	private Person maintainanceOwnerPerson;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="MAINTAINANCE_OWNER",insertable=false, updatable=false)
	public Person getMaintainanceOwnerPerson() {
		return maintainanceOwnerPerson;
	}
	public void setMaintainanceOwnerPerson(Person maintainanceOwnerPerson) {
		this.maintainanceOwnerPerson = maintainanceOwnerPerson;
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
