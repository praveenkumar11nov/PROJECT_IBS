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
 * JcTeam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JC_TEAM")
@NamedQueries({
	@NamedQuery(name = "JobCards.readJobTeam", query = "SELECT jcTeam FROM JcTeam jcTeam WHERE jcTeam.jobCards=:jcId")
})
public class JcTeam implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int jctId;
	private Department department;
	private JobCards jobCards;
	private Person person;
	private Timestamp jctStartDt;
	private Timestamp jctEndDt;
	private String jctHours;
	private String jctWorktime;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public JcTeam() {
	}
	
	/** full constructor */
	public JcTeam(int jctId, Department department, JobCards jobCards,
			Person person, Timestamp jctStartDt, Timestamp jctEndDt,
			String jctHours, String jctWorktime, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		this.jctId = jctId;
		this.department = department;
		this.jobCards = jobCards;
		this.person = person;
		this.jctStartDt = jctStartDt;
		this.jctEndDt = jctEndDt;
		this.jctHours = jctHours;
		this.jctWorktime = jctWorktime;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	// Property accessors
	@Id
	@Column(name = "JCT_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_TEAM", sequenceName = "JOB_TEAM")
	@GeneratedValue(generator = "JOB_TEAM")
	public int getJctId() {
		return this.jctId;
	}

	public void setJctId(int jctId) {
		this.jctId = jctId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JCT_DEPT", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JC_ID", nullable = false)
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JCT_STAFF_ID", nullable = false)
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "JCT_START_DT", nullable = false, length = 11)
	public Timestamp getJctStartDt() {
		return this.jctStartDt;
	}

	public void setJctStartDt(Timestamp jctStartDt) {
		this.jctStartDt = jctStartDt;
	}

	@Column(name = "JCT_END_DT", length = 11)
	public Timestamp getJctEndDt() {
		return this.jctEndDt;
	}

	public void setJctEndDt(Timestamp jctEndDt) {
		this.jctEndDt = jctEndDt;
	}

	@Column(name = "JCT_HOURS", nullable = false)
	public String getJctHours() {
		return this.jctHours;
	}

	public void setJctHours(String jctHours) {
		this.jctHours = jctHours;
	}

	@Column(name = "JCT_WORKTIME", nullable = false, length = 45)
	public String getJctWorktime() {
		return this.jctWorktime;
	}

	public void setJctWorktime(String jctWorktime) {
		this.jctWorktime = jctWorktime;
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