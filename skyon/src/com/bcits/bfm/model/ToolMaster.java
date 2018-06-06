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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

/**
 * ToolMaster entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TOOL_MASTER")
@NamedQueries({
	
	@NamedQuery(name = "ToolMaster.getQuantityBasedOnName", query = "SELECT t.tmQuantity FROM ToolMaster t WHERE t.tmName=:toolname"),

})
public class ToolMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int tmId;
	private String tmName;
	private String description;
	private int tmQuantity;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private Set<JcTools> jcToolses = new HashSet<JcTools>(0);
	
	// Constructors

	/** default constructor */
	public ToolMaster() {
	}
	
	/** full constructor */
	public ToolMaster(int tmId, String tmName, String description,
			int tmQuantity, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt, Set<JcTools> jcToolses) {
		this.tmId = tmId;
		this.tmName = tmName;
		this.description = description;
		this.tmQuantity = tmQuantity;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.jcToolses = jcToolses;		
	}

	// Property accessors
	@Id
	@Column(name = "TM_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "TOOL_MASTER_SEQ", sequenceName = "TOOL_MASTER_SEQ")
	@GeneratedValue(generator = "TOOL_MASTER_SEQ")	
	public int getTmId() {
		return this.tmId;
	}

	public void setTmId(int tmId) {
		this.tmId = tmId;
	}

	@Column(name = "TM_NAME", nullable = false, length = 45)
	public String getTmName() {
		return this.tmName;
	}

	public void setTmName(String tmName) {
		this.tmName = tmName;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TM_QUANTITY", nullable = false, precision = 11, scale = 0)
	public int getTmQuantity() {
		return this.tmQuantity;
	}

	public void setTmQuantity(int tmQuantity) {
		this.tmQuantity = tmQuantity;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "toolMaster")
	public Set<JcTools> getJcToolses() {
		return this.jcToolses;
	}

	public void setJcToolses(Set<JcTools> jcToolses) {
		this.jcToolses = jcToolses;
	}
	
	@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}

}