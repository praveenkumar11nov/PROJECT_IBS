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
 * JcTools entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JC_TOOLS")
@NamedQueries({
	@NamedQuery(name = "JobCards.readJobTools", query = "SELECT jobTools FROM JcTools jobTools WHERE jobTools.jobCards=:jcId")
})
public class JcTools implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int jctoolId;
	private JobCards jobCards;
	private ToolMaster toolMaster;
	private String quantity;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public JcTools() {
	}	

	/** full constructor */
	public JcTools(int jctoolId, JobCards jobCards, ToolMaster toolMaster,
			String quantity, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt) {
		this.jctoolId = jctoolId;
		this.jobCards = jobCards;
		this.toolMaster = toolMaster;
		this.quantity = quantity;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	// Property accessors
	@Id
	@Column(name = "JCTOOL_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_TOOLS", sequenceName = "JOB_TOOLS")
	@GeneratedValue(generator = "JOB_TOOLS")	
	public int getJctoolId() {
		return this.jctoolId;
	}

	public void setJctoolId(int jctoolId) {
		this.jctoolId = jctoolId;
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
	@JoinColumn(name = "TM_ID", nullable = false)
	public ToolMaster getToolMaster() {
		return this.toolMaster;
	}

	public void setToolMaster(ToolMaster toolMaster) {
		this.toolMaster = toolMaster;
	}

	@Column(name = "QUANTITY", nullable = false, length = 45)
	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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