package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * MaintainanceTypes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MAINTAINANCE_TYPES")
@NamedQueries({
	
	@NamedQuery(name = "MaintainanceTypes.getMaintainanceTypesForJobCalendar", query = "SELECT m FROM MaintainanceTypes m"),

})
public class MaintainanceTypes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int mtId;
	private String maintainanceType;
	private String description;
	private Timestamp mtDt;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	private Set<JobCards> jobCardses = new HashSet<JobCards>(0);
	
	private Set<JobCalender> jobCalenders = new HashSet<JobCalender>(0);
	private Set<AssetMaintenanceSchedule> assetMaintSchedules = new HashSet<AssetMaintenanceSchedule>(0);

	
	// Property accessors
	@Id
	@Column(name = "MT_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "MAINTENANCE_TYPES_SEQ", sequenceName = "MAINTENANCE_TYPES_SEQ")
	@GeneratedValue(generator = "MAINTENANCE_TYPES_SEQ")
	public int getMtId() {
		return this.mtId;
	}

	public void setMtId(int mtId) {
		this.mtId = mtId;
	}

	@Column(name = "MAINTAINANCE_TYPE", nullable = false, length = 45)
	@NotEmpty(message = "Maintenance Type Sholud Not Be Empty")
	@Size(max = 45, message = "Maintenance Type Must Be Max 45 Length Character")
	public String getMaintainanceType() {
		return this.maintainanceType;
	}

	public void setMaintainanceType(String maintainanceType) {
		this.maintainanceType = maintainanceType;
	}

	@Column(name = "DESCRIPTION", length = 200)
	@Size(max = 200, message = "Description Must Be Max 200 Length Character")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "MT_DT", nullable = false, length = 11)
	public Timestamp getMtDt() {
		return this.mtDt;
	}

	public void setMtDt(Timestamp mtDt) {
		this.mtDt = mtDt;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "maintainanceTypes")
	public Set<JobCards> getJobCardses() {
		return this.jobCardses;
	}

	public void setJobCardses(Set<JobCards> jobCardses) {
		this.jobCardses = jobCardses;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "maintainanceTypes")
	public Set<JobCalender> getJobCalenders() {
		return this.jobCalenders;
	}

	public void setJobCalenders(Set<JobCalender> jobCalenders) {
		this.jobCalenders = jobCalenders;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "maintainanceTypes")
	public Set<AssetMaintenanceSchedule> getAssetMaintSchedules() {
		return this.assetMaintSchedules;
	}

	public void setAssetMaintSchedules(
			Set<AssetMaintenanceSchedule> assetMaintSchedules) {
		this.assetMaintSchedules = assetMaintSchedules;
	}


}