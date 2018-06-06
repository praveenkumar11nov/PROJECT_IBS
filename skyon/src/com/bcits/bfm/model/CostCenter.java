package com.bcits.bfm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="COST_CENTER_MASTER")
@NamedQueries({
	@NamedQuery(name = "CostCenter.findAllList", query="SELECT c FROM CostCenter c"),
})
public class CostCenter {

	private int ccId;
	private String name;
	private String description;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;

	@Id
	@SequenceGenerator(name="SEQ_COST_CENTER" ,sequenceName="COST_CENTER_SEQ")
	@GeneratedValue(generator="SEQ_COST_CENTER")
	@Column(name="CC_ID")
	public int getCcId() {
		return ccId;
	}
	public void setCcId(int ccId) {
		this.ccId = ccId;
	}

	@Column(name="CC_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="CC_DESC")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="LAST_UPDATED_DT")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
