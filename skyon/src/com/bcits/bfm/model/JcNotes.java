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
 * JcNotes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JC_NOTES")
@NamedQueries({
	@NamedQuery(name = "JobCards.readJobNotes", query = "SELECT jcNotes FROM JcNotes jcNotes WHERE jcNotes.jobCards=:jcId")
})
public class JcNotes implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private int jcnId;
	private JobCards jobCards;
	private String notes;
	private Timestamp jcnDt;
	private int drGroupId;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public JcNotes() {
	}
	
	/** full constructor */
	public JcNotes(int jcnId, JobCards jobCards, String notes,
			Timestamp jcnDt, int drGroupId, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		this.jcnId = jcnId;
		this.jobCards = jobCards;
		this.notes = notes;
		this.jcnDt = jcnDt;
		this.drGroupId = drGroupId;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	// Property accessors
	@Id
	@Column(name = "JCN_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_NOTES", sequenceName = "JOB_NOTES")
	@GeneratedValue(generator = "JOB_NOTES")	
	public int getJcnId() {
		return this.jcnId;
	}

	public void setJcnId(int jcnId) {
		this.jcnId = jcnId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JC_ID", nullable = false)
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@Column(name = "NOTES", length = 500)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "JCN_DT", nullable = false, length = 11)
	public Timestamp getJcnDt() {
		return this.jcnDt;
	}

	public void setJcnDt(Timestamp jcnDt) {
		this.jcnDt = jcnDt;
	}

	@Column(name = "DR_GROUP_ID", nullable = false, precision = 11, scale = 0)
	public int getDrGroupId() {
		return this.drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
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