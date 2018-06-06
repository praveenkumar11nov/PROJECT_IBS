package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_SERVICE_HISTORY")
@NamedQueries({
	@NamedQuery(name = "AssetServiceHistory.findAll", query = "SELECT a FROM AssetServiceHistory a ORDER BY a.ashId DESC"),
	@NamedQuery(name = "AssetServiceHistory.findAllList", query = "SELECT a.ashId,a.assetId,ast.assetName,a.ashDate,a.problemDesc,a.serviceDesc,a.serviceType,a.servicedUnder,a.servicedBy,p.firstName,p.lastName,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate,amc.amcId,amc.costIncurred FROM AssetServiceHistory a INNER JOIN a.asset ast INNER JOIN a.servicedByPerson p INNER JOIN a.assetMaintenanceCost amc ORDER BY a.ashId DESC")

	})
public class AssetServiceHistory {
	
	private int ashId;
	private int assetId;
	private Date ashDate;
	private String problemDesc;
	private String serviceDesc;
	private String serviceType;
	private String servicedUnder;
	private String servicedBy;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_SERVICE_HISTORY" ,sequenceName="ASSET_SERVICE_HISTORY_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_SERVICE_HISTORY")
	@Column(name="ASH_ID")
	public int getAshId() {
		return ashId;
	}
	
	public void setAshId(int ashId) {
		this.ashId = ashId;
	}
	
	
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	
	@Column(name="ASH_DT")
	public Date getAshDate() {
		return ashDate;
	}
	public void setAshDate(Date ashDate) {
		this.ashDate = ashDate;
	}
	
	
	@Column(name="PROBLEM_DESCRIPTION")
	public String getProblemDesc() {
		return problemDesc;
	}
	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}
	
	
	@Column(name="SERVICE_DESCRIPTION")
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	
	
	@Column(name="SERVICE_TYPE")
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	@Column(name="SERVICED_UNDER")
	public String getServicedUnder() {
		return servicedUnder;
	}
	public void setServicedUnder(String servicedUnder) {
		this.servicedUnder = servicedUnder;
	}
	
	
	@Column(name="SERVICED_BY")
	public String getServicedBy() {
		return servicedBy;
	}
	public void setServicedBy(String servicedBy) {
		this.servicedBy = servicedBy;
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
	
	
	private Person servicedByPerson;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="SERVICED_BY",insertable=false, updatable=false)
	public Person getServicedByPerson() {
		return servicedByPerson;
	}

	public void setServicedByPerson(Person servicedByPerson) {
		this.servicedByPerson = servicedByPerson;
	}
	
	
	private AssetMaintenanceCost assetMaintenanceCost;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "assetServiceHistory")
	@JoinColumn(name = "ASH_ID")
	@PrimaryKeyJoinColumn(name = "ASH_ID", referencedColumnName = "ASH_ID")
	public AssetMaintenanceCost getAssetMaintenanceCost() {
		return assetMaintenanceCost;
	}

	public void setAssetMaintenanceCost(AssetMaintenanceCost assetMaintenanceCost) {
		this.assetMaintenanceCost = assetMaintenanceCost;
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
