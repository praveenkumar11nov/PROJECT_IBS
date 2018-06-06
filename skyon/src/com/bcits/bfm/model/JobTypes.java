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
 * JobTypes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JOB_TYPES")
@NamedQueries({
	
	@NamedQuery(name="JobTypes.findAllJobTypesList",query="SELECT model FROM JobTypes model ORDER BY model.jtId DESC"),
})
public class JobTypes implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private int jtId;
	private String jtType;
	private String jtDescription;
	private int jtSla;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	private Set<AssetMaintenanceSchedule> assetMaintSchedules = new HashSet<AssetMaintenanceSchedule>(0);
	private Set<JobCards> jobCardses = new HashSet<JobCards>(0);
	private Set<JobCalender> jobCalenders = new HashSet<JobCalender>(0);
	

	// Property accessors
	@Id
	@Column(name = "JT_ID", unique = true, nullable = false, precision = 15, scale = 0)
	@SequenceGenerator(name = "JOB_TYPES_SEQ", sequenceName = "JOB_TYPES_SEQ")
	@GeneratedValue(generator = "JOB_TYPES_SEQ")
	public int getJtId() {
		return this.jtId;
	}

	public void setJtId(int jtId) {
		this.jtId = jtId;
	}

	@Column(name = "JT_TYPE", nullable = false, length = 50)
	@NotEmpty(message = "Job Type Sholud Not Be Empty")
	@Size(max = 50, message = "Job Type Must Be Max 50 Length Character")
	public String getJtType() {
		return this.jtType;
	}

	public void setJtType(String jtType) {
		this.jtType = jtType;
	}

	@Column(name = "JT_DESCRIPTION", length = 500)
	@Size(max = 500, message = "Description Must Be Max 500 Length Character")
	public String getJtDescription() {
		return this.jtDescription;
	}

	public void setJtDescription(String jtDescription) {
		this.jtDescription = jtDescription;
	}

	@Column(name = "JT_SLA", nullable = false, precision = 11, scale = 0)
	public int getJtSla() {
		return this.jtSla;
	}

	public void setJtSla(int jtSla) {
		this.jtSla = jtSla;
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
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true,mappedBy = "jobTypes")
	public Set<AssetMaintenanceSchedule> getAssetMaintSchedules() {
		return this.assetMaintSchedules;
	}

	public void setAssetMaintSchedules(
			Set<AssetMaintenanceSchedule> assetMaintSchedules) {
		this.assetMaintSchedules = assetMaintSchedules;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobTypes")
	public Set<JobCards> getJobCardses() {
		return this.jobCardses;
	}

	public void setJobCardses(Set<JobCards> jobCardses) {
		this.jobCardses = jobCardses;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jobTypes")
	public Set<JobCalender> getJobCalenders() {
		return this.jobCalenders;
	}

	public void setJobCalenders(Set<JobCalender> jobCalenders) {
		this.jobCalenders = jobCalenders;
	}
	
}