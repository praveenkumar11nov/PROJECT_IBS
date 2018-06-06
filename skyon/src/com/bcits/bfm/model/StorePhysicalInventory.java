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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	@NamedQuery(name="StorePhysicalInventory.findAllStorePhysicalInventory",query="SELECT s FROM StorePhysicalInventory s ORDER BY s.spiId DESC")
})
@Table(name = "STORE_PHYSICAL_INVENTORY")
public class StorePhysicalInventory
{
	private int spiId;
	private Timestamp spiDt;
	private Timestamp surveyDt;
	private Timestamp surveyCompleteDt;
	private String surveyName;
	private String surveyDescription;
	private String spiStatus;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String categoryIds;
	private Set<StorePhysicalInventoryReport> storePhysicalInventoryReportsSet = new HashSet<StorePhysicalInventoryReport>(0);
	@Id
	@SequenceGenerator(name="STORE_PHYSICAL_INVENTORY_SEQ",sequenceName="STORE_PHYSICAL_INVENTORY_SEQ")
	@GeneratedValue(generator="STORE_PHYSICAL_INVENTORY_SEQ")
	@Column(name = "SPI_ID")
	public int getSpiId()
	{
		return spiId;
	}
	public void setSpiId(int spiId)
	{
		this.spiId = spiId;
	}
	@Column(name = "SPI_DT")
	public Timestamp getSpiDt()
	{
		return spiDt;
	}
	public void setSpiDt(Timestamp spiDt)
	{
		this.spiDt = spiDt;
	}
	@Column(name = "SURVEY_DT")
	public Timestamp getSurveyDt()
	{
		return surveyDt;
	}
	public void setSurveyDt(Timestamp surveyDt)
	{
		this.surveyDt = surveyDt;
	}
	@Column(name = "SURVEY_COMPLETE_DT")
	public Timestamp getSurveyCompleteDt()
	{
		return surveyCompleteDt;
	}
	public void setSurveyCompleteDt(Timestamp surveyCompleteDt)
	{
		this.surveyCompleteDt = surveyCompleteDt;
	}
	@Column(name = "SURVEY_NAME")
	public String getSurveyName()
	{
		return surveyName;
	}
	public void setSurveyName(String surveyName)
	{
		this.surveyName = surveyName;
	}
	@Column(name = "SURVEY_DESCRIPTION")
	public String getSurveyDescription()
	{
		return surveyDescription;
	}
	public void setSurveyDescription(String surveyDescription)
	{
		this.surveyDescription = surveyDescription;
	}
	@Column(name = "STATUS")
	public String getSpiStatus()
	{
		return spiStatus;
	}
	public void setSpiStatus(String spiStatus)
	{
		this.spiStatus = spiStatus;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
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
	@Transient
	public String getCategoryIds()
	{
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds)
	{
		this.categoryIds = categoryIds;
	}
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPI_ID")
	public Set<StorePhysicalInventoryReport> getStorePhysicalInventoryReportsSet()
	{
		return storePhysicalInventoryReportsSet;
	}
	public void setStorePhysicalInventoryReportsSet(
			Set<StorePhysicalInventoryReport> storePhysicalInventoryReportsSet)
	{
		this.storePhysicalInventoryReportsSet = storePhysicalInventoryReportsSet;
	}
}
