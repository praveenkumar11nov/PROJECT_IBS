package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ACCESS_REPOSITORY")
@NamedQueries({
	@NamedQuery(name = "AccessRepository.findAll", query = "SELECT a FROM AccessRepository a ORDER BY a.arId"),
	@NamedQuery(name = "AccessRepository.getAccessRepositoryIdBasedOnName", query = "SELECT a.arId FROM AccessRepository a WHERE a.arName=:arName"),
	@NamedQuery(name = "AccessRepository.getRepositoryName", query = "SELECT a.arName FROM AccessRepository a ORDER BY a.arId"),
	@NamedQuery(name = "AccessRepository.name", query = "SELECT a FROM AccessRepository a WHERE a.arId=:arName")
	
	
})
public class AccessRepository {
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "ACCESSrEPOSITORY_SEQ")
	@GeneratedValue(generator = "seq")
	@Column(name = "AR_ID")
	private int arId;
	
	@Column(name = "AR_NAME")
	private String arName;
	
	@Column(name = "AR_DESCRIPTION")
	private String arDescription;
	
	@Column(name = "AR_LOCATION")
	private  String arLoication;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private String lastUpdatedDt;

	public AccessRepository(int arId, String arName, String arDescription,
			String arLoication, String status, String createdBy,
			String lastUpdatedBy, String lastUpdatedDt) {
		super();
		this.arId = arId;
		this.arName = arName;
		this.arDescription = arDescription;
		this.arLoication = arLoication;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	
	public AccessRepository() {
		super();
	}


	public int getArId() {
		return arId;
	}

	public void setArId(int arId) {
		this.arId = arId;
	}

	public String getArName() {
		return arName;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}

	public String getArDescription() {
		return arDescription;
	}

	public void setArDescription(String arDescription) {
		this.arDescription = arDescription;
	}

	public String getArLoication() {
		return arLoication;
	}

	public void setArLoication(String arLoication) {
		this.arLoication = arLoication;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(String lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	
	

}
