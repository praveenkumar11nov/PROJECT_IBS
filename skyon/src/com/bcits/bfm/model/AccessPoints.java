package com.bcits.bfm.model;

import java.sql.Date;
import java.util.Calendar;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name="ACCESS_POINTS")
@NamedQueries({
	@NamedQuery(name = "AccessPoint.findAll", query = "SELECT ap FROM AccessPoints ap ORDER BY ap.apId DESC"),
	@NamedQuery(name = "AccessPoint.getAllApCodes", query = "SELECT ap.apCode FROM AccessPoints ap"),
	@NamedQuery(name = "AccessPoints.getAccessPointIdBasedOnName", query = "SELECT ap.apId FROM AccessPoints ap WHERE ap.apName=:apName"),
	@NamedQuery(name = "AccessPoints.getPointName", query = "SELECT ap.apName FROM AccessPoints ap WHERE ap.apId=:apId"),
})
public class AccessPoints {
	
	private int apId;
	private String apType;
	private String apName;
	private String apLocation;
	private String apDescription;
	private String apCode;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	
	
	@Id
	@SequenceGenerator(name="SEQ_ACCESS_POINT" ,sequenceName="ACCESS_POINT_SEQ")
	@GeneratedValue(generator="SEQ_ACCESS_POINT")
    @Column(name="ACCESS_POINT_ID")
	public int getApId() {
		return apId;
	}
	
	
	public void setApId(int apId) {
		this.apId = apId;
	}
	
	@Column(name="AP_TYPE")
	@NotNull(message="Access Point Type is required")
	public String getApType() {
		return apType;
	}
	
	
	public void setApType(String apType) {
		this.apType = apType;
	}
	
	@Size(min = 1, max = 100, message = "Invalid Access Point Name")
	@Column(name="AP_NAME")
	@NotNull(message="Access Point Name is required")
	public String getApName() {
		return apName;
	}
	
	
	public void setApName(String apName) {
		this.apName = apName;
	}
	
	@Size(min = 0, max = 100, message = "Invalid Location")
	@Column(name="AP_LOCATION")
	@NotNull(message="Access Point Location is required")
	public String getApLocation() {
		return apLocation;
	}
	
	
	public void setApLocation(String apLocation) {
		this.apLocation = apLocation;
	}
	
	@Size(min = 0, max = 400, message = "Description can have maximum {max} letters")
	@Column(name="DESCRIPTION")
	public String getApDescription() {
		return apDescription;
	}
	
	
	public void setApDescription(String apDescription) {
		this.apDescription = apDescription;
	}
	
	
	
	@Size(min = 1, max = 100, message = "Invalid Access Point Code")
	@Column(name="AP_CODE")
	public String getApCode() {
		return apCode;
	}


	public void setApCode(String apCode) {
		this.apCode = apCode;
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
		return lastUpdatedBy;
	}
	
	
	public void setUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name="LAST_UPDATED_DT")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
