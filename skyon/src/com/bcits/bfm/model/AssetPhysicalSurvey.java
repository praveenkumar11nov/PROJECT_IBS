package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ASSET_PHYSICAL_SURVEY_MASTER")
@NamedQueries({
	@NamedQuery(name = "AssetPhysicalSurvey.findAll", query = "SELECT a FROM AssetPhysicalSurvey a ORDER BY a.apsmId DESC"),
	@NamedQuery(name = "AssetPhysicalSurvey.setStatus", query = "UPDATE AssetPhysicalSurvey a SET a.physicalSurveyStatus = :physicalSurveyStatus, a.surveyCompleteDate= :surveyCompleteDate WHERE a.apsmId = :apsmId"),
	@NamedQuery(name = "AssetPhysicalSurvey.setStatusStarted", query = "UPDATE AssetPhysicalSurvey a SET a.physicalSurveyStatus = :physicalSurveyStatus, a.surveyDate= :surveyDate WHERE a.apsmId = :apsmId"),
	@NamedQuery(name = "AssetPhysicalSurvey.findAllList", query = "SELECT a.apsmId,a.apsmDate,a.surveyDate,a.surveyName,a.surveyDescription,a.surveyCompleteDate,a.assetCatId,act.treeHierarchy,a.assetLocId,alc.treeHierarchy,a.assetCatIds,a.assetLocIds,a.physicalSurveyStatus,a.createdBy,a.lastUpdatedBy,a.lastUpdatedDate FROM AssetPhysicalSurvey a INNER JOIN a.assetCategoryTree act INNER JOIN a.assetLocationTree alc ORDER BY a.apsmId DESC"),

})
public class AssetPhysicalSurvey {

	private int apsmId;
	private Date apsmDate;
	private Date surveyDate;
	private Date surveyCompleteDate;
	private String physicalSurveyStatus;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int assetCatId;
	private int assetLocId;
	private String assetCatIds;
	private String assetLocIds;
	private String surveyName;
	private String surveyDescription;


	@Id
	@SequenceGenerator(name="SEQ_ASSET_PHYSICAL_SURVEY" ,sequenceName="ASSET_PHYSICAL_SURVEY_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_PHYSICAL_SURVEY")
	@Column(name="APSM_ID")
	public int getApsmId() {
		return apsmId;
	}
	public void setApsmId(int apsmId) {
		this.apsmId = apsmId;
	}

	@Column(name="APSM_DT")
	public Date getApsmDate() {
		return apsmDate;
	}
	public void setApsmDate(Date apsmDate) {
		this.apsmDate = apsmDate;
	}

	@Column(name="SURVEY_DT")
	public Date getSurveyDate() {
		return surveyDate;
	}
	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	@Column(name="SURVEY_COMPLETE_DT")
	public Date getSurveyCompleteDate() {
		return surveyCompleteDate;
	}
	public void setSurveyCompleteDate(Date surveyCompleteDate) {
		this.surveyCompleteDate = surveyCompleteDate;
	}

	@Column(name="STATUS")
	public String getPhysicalSurveyStatus() {
		return physicalSurveyStatus;
	}
	public void setPhysicalSurveyStatus(String physicalSurveyStatus) {
		this.physicalSurveyStatus = physicalSurveyStatus;
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
	

	@Column(name="ASSET_CATEGORY_ID")
	public int getAssetCatId() {
		return assetCatId;
	}
	public void setAssetCatId(int assetCatId) {
		this.assetCatId = assetCatId;
	}
	
	@Column(name="ASSET_LOCATION_ID")
	public int getAssetLocId() {
		return assetLocId;
	}
	public void setAssetLocId(int assetLocId) {
		this.assetLocId = assetLocId;
	}
	
	
	
	
	@Column(name="ASSET_CAT_IDS")
	public String getAssetCatIds() {
		return assetCatIds;
	}
	public void setAssetCatIds(String assetCatIds) {
		this.assetCatIds = assetCatIds;
	}
	
	@Column(name="ASSET_LOC_IDS")
	public String getAssetLocIds() {
		return assetLocIds;
	}
	public void setAssetLocIds(String assetLocIds) {
		this.assetLocIds = assetLocIds;
	}
	
	@Column(name="SURVEY_NAME")
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	
	@Column(name="SURVEY_DESCRIPTION")
	public String getSurveyDescription() {
		return surveyDescription;
	}
	public void setSurveyDescription(String surveyDescription) {
		this.surveyDescription = surveyDescription;
	}





	private AssetCategoryTree assetCategoryTree;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="ASSET_CATEGORY_ID",insertable=false, updatable=false)
	public AssetCategoryTree getAssetCategoryTree() {
		return assetCategoryTree;
	}
	public void setAssetCategoryTree(AssetCategoryTree assetCategoryTree) {
		this.assetCategoryTree = assetCategoryTree;
	}
	
	private AssetLocationTree assetLocationTree;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="ASSET_LOCATION_ID",insertable=false, updatable=false)
	public AssetLocationTree getAssetLocationTree() {
		return assetLocationTree;
	}
	public void setAssetLocationTree(AssetLocationTree assetLocationTree) {
		this.assetLocationTree = assetLocationTree;
	}
	
	
	private Set<AssetPhysicalSurveyReport> assetPhysicalSurveyReports = new HashSet<AssetPhysicalSurveyReport>(0);
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "APSM_ID", nullable = false)
	public Set<AssetPhysicalSurveyReport> getAssetPhysicalSurveyReports() {
		return assetPhysicalSurveyReports;
	}
	public void setAssetPhysicalSurveyReports(
			Set<AssetPhysicalSurveyReport> assetPhysicalSurveyReports) {
		this.assetPhysicalSurveyReports = assetPhysicalSurveyReports;
	}
	
	
	@PrePersist
	protected void onCreate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
		physicalSurveyStatus = "Created";
		java.util.Date date= new java.util.Date();
		apsmDate = new Timestamp(date.getTime());
	}

	
	@PreUpdate
	protected void onUpdate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}

}
