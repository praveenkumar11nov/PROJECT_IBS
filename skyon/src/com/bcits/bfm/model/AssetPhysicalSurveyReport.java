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
@Table(name="ASSET_PS")
@NamedQueries({
	@NamedQuery(name = "AssetPhysicalSurveyReport.findAll", query = "SELECT a FROM AssetPhysicalSurveyReport a WHERE a.apsmId =:apsmId ORDER BY a.apId DESC"),
	@NamedQuery(name = "AssetPhysicalSurveyReport.checkChildStatus", query = "SELECT a FROM AssetPhysicalSurveyReport a WHERE a.apsmId =:apsmId and a.assetCondition=' --- ' ORDER BY a.apId DESC")
	

})
public class AssetPhysicalSurveyReport {

	private int apId;
	private int apsmId;
	private int assetId;
	private String assetCondition;
	private String assetNotes;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;



	@Id
	@SequenceGenerator(name="SEQ_ASSET_PS" ,sequenceName="ASSET_PS_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_PS")
	@Column(name="AP_ID")
	public int getApId() {
		return apId;
	}
	public void setApId(int apId) {
		this.apId = apId;
	}	
	
	
	@Column(name="APSM_ID", insertable=false, updatable=false)
	public int getApsmId() {
		return apsmId;
	}
	
	public void setApsmId(int apsmId) {
		this.apsmId = apsmId;
	}

	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Column(name="ASSET_CONDITION_OK")
	public String getAssetCondition() {
		return assetCondition;
	}
	public void setAssetCondition(String assetCondition) {
		this.assetCondition = assetCondition;
	}
	
	@Column(name="ASSET_NOTES")
	public String getAssetNotes() {
		return assetNotes;
	}
	public void setAssetNotes(String assetNotes) {
		this.assetNotes = assetNotes;
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
	
	
	private AssetPhysicalSurvey assetPhysicalSurvey;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="APSM_ID",insertable=false, updatable=false)
	public AssetPhysicalSurvey getAssetPhysicalSurvey() {
		return assetPhysicalSurvey;
	}
	public void setAssetPhysicalSurvey(AssetPhysicalSurvey assetPhysicalSurvey) {
		this.assetPhysicalSurvey = assetPhysicalSurvey;
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
