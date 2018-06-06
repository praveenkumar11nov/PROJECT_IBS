package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * JcLabourtasks entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JC_LABOURTASKS")
@NamedQueries({
	@NamedQuery(name = "JobCards.readJobLabourTask", query = "SELECT jobLabourTask FROM JcLabourtasks jobLabourTask WHERE jobLabourTask.jobCards=:jcId")
})
public class JcLabourtasks implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int jclId;
	private JobCards jobCards;
	private String jclType;
	private String jclLabourdesc;
	private String jclHours;
	private String jclBillable;
	private int jclRate;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public JcLabourtasks() {
	}

	/** full constructor */
	public JcLabourtasks(int jclId, JobCards jobCards, String jclType,
			String jclLabourdesc, String jclHours, String jclBillable,
			int jclRate, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt) {
		this.jclId = jclId;
		this.jobCards = jobCards;
		this.jclType = jclType;
		this.jclLabourdesc = jclLabourdesc;
		this.jclHours = jclHours;
		this.jclBillable = jclBillable;
		this.jclRate = jclRate;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	// Property accessors
	@Id
	@Column(name = "JCL_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_LABOURTASK", sequenceName = "JOB_LABOURTASK")
	@GeneratedValue(generator = "JOB_LABOURTASK")	
	public int getJclId() {
		return this.jclId;
	}

	public void setJclId(int jclId) {
		this.jclId = jclId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JC_ID", nullable = false)
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@Column(name = "JCL_TYPE", nullable = false, length = 45)
	@NotEmpty(message="Labour Type is Required")
	public String getJclType() {
		return this.jclType;
	}

	public void setJclType(String jclType) {
		this.jclType = jclType;
	}

	@Column(name = "JCL_LABOURDESC", length = 500)
	public String getJclLabourdesc() {
		return this.jclLabourdesc;
	}

	public void setJclLabourdesc(String jclLabourdesc) {
		this.jclLabourdesc = jclLabourdesc;
	}

	@Column(name = "JCL_HOURS", nullable = false, length = 45)
	@NotEmpty(message="Labour Hours is Required")
	public String getJclHours() {
		return this.jclHours;
	}

	public void setJclHours(String jclHours) {
		this.jclHours = jclHours;
	}

	@Column(name = "JCL_BILLABLE", nullable = false, length = 45)
	@NotEmpty(message="Labour Billable is Required")
	public String getJclBillable() {
		return this.jclBillable;
	}

	public void setJclBillable(String jclBillable) {
		this.jclBillable = jclBillable;
	}

	@Column(name = "JCL_RATE", nullable = false, precision = 10)
	public int getJclRate() {
		return this.jclRate;
	}

	public void setJclRate(int jclRate) {
		this.jclRate = jclRate;
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

}