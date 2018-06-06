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

/**
 * JcObjectives entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JC_OBJECTIVES")
@NamedQueries({
	@NamedQuery(name = "JobCards.UpdateJobObjectiveStatus", query = "UPDATE JcObjectives jobObjective SET  jobObjective.jcObjectiveAch = :Status WHERE jobObjective.jcoId = :jcoId"),
	@NamedQuery(name = "JobCards.readJobObjective", query = "SELECT jobObjective FROM JcObjectives jobObjective WHERE jobObjective.jobCards=:jcId")
})
public class JcObjectives implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Fields

	private int jcoId;
	private JobCards jobCards;
	private String jcObjective;
	private String jcObjectiveAch;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public JcObjectives() {
	}
	

	/** full constructor */
	public JcObjectives(int jcoId, JobCards jobCards, String jcObjective,
			String jcObjectiveAch, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt) {
		this.jcoId = jcoId;
		this.jobCards = jobCards;
		this.jcObjective = jcObjective;
		this.jcObjectiveAch = jcObjectiveAch;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	// Property accessors
	@Id
	@Column(name = "JCO_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_OBJECTIVE_SEQUENCE", sequenceName = "JOB_OBJECTIVE_SEQUENCE")
	@GeneratedValue(generator = "JOB_OBJECTIVE_SEQUENCE")
	public int getJcoId() {
		return this.jcoId;
	}

	public void setJcoId(int jcoId) {
		this.jcoId = jcoId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JC_ID", nullable = false)
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@Column(name = "JC_OBJECTIVE", nullable = false, length = 500)
	public String getJcObjective() {
		return this.jcObjective;
	}

	public void setJcObjective(String jcObjective) {
		this.jcObjective = jcObjective;
	}

	@Column(name = "JC_OBJECTIVE_ACH", nullable = false, length = 500)
	public String getJcObjectiveAch() {
		return this.jcObjectiveAch;
	}

	public void setJcObjectiveAch(String jcObjectiveAch) {
		this.jcObjectiveAch = jcObjectiveAch;
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