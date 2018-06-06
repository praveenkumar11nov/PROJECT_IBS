package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="DOCUMENT_DEFINER")
@NamedQueries({
	@NamedQuery(name="DocumentDefiner.findAll",query="SELECT dd FROM DocumentDefiner dd order by dd.ddId DESC"),
	@NamedQuery(name="DocumentDefiner.findAllByType",query="SELECT dd FROM DocumentDefiner dd WHERE dd.ddType = :ddType AND dd.status='Active'"),
	@NamedQuery(name="DocumentDefiner.getDocumentFormatOnPersonType",query="SELECT d.ddFormat FROM DocumentDefiner d WHERE d.ddType = :ddType AND d.ddId = :ddId"),
	@NamedQuery(name="DocumentDefiner.UpdateStatus",query="UPDATE DocumentDefiner d SET d.status = :status WHERE d.ddId = :ddId"),
	@NamedQuery(name="DocumentDefiner.getAllBasedOnTypeAndCondition",query="SELECT dd FROM DocumentDefiner dd WHERE dd.ddType = :ddType AND dd.ddOptional =:ddOptional"),
	
	
})
public class DocumentDefiner implements java.io.Serializable
{
	@Id
	@SequenceGenerator(name = "ddSeq", sequenceName = "DOCUMENTDEFINER_SEQ")
	@GeneratedValue(generator = "ddSeq")
	@Column(name = "DD_ID")
	private int ddId;
	
	@Column(name = "DD_TYPE")
	private String ddType;
	
	@Column(name = "DD_NAME")
	private String ddName;
	
	@Column(name = "DD_FORMAT")
	private String ddFormat;
	
	@Column(name = "DD_DESCRIPTION")
	private String ddDescription;
	
	@Column(name = "DD_OPTIONAL")
	private String ddOptional;
	
	@Column(name = "DD_START_DATE")
	private java.sql.Date ddStartDate;
	
	@Column(name = "DD_END_DATE")
	private java.sql.Date ddEndDate;
	
	@Column(name = "DD_REV_COMPLAINCE")
	private String ddRvComplaince;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Date lastUpdateDate;
	
	public int getDdId() {
		return ddId;
	}
	public void setDdId(int ddId) {
		this.ddId = ddId;
	}
	public String getDdType() {
		return ddType;
	}
	public void setDdType(String ddType) {
		this.ddType = ddType;
	}
	public String getDdName() {
		return ddName;
	}
	public void setDdName(String ddName) {
		this.ddName = ddName;
	}
	public String getDdFormat() {
		return ddFormat;
	}
	public void setDdFormat(String ddFormat) {
		this.ddFormat = ddFormat;
	}
	public String getDdDescription() {
		return ddDescription;
	}
	public void setDdDescription(String ddDescription) {
		this.ddDescription = ddDescription;
	}
	public String getDdOptional() {
		return ddOptional;
	}
	public void setDdOptional(String ddOptional) {
		this.ddOptional = ddOptional;
	}
	
	public java.sql.Date getDdStartDate() {
		return ddStartDate;
	}
	public void setDdStartDate(java.sql.Date ddStartDate) {
		this.ddStartDate = ddStartDate;
	}
	public java.sql.Date getDdEndDate() {
		return ddEndDate;
	}
	public void setDdEndDate(java.sql.Date ddEndDate) {
		this.ddEndDate = ddEndDate;
	}
	public String getDdRvComplaince() {
		return ddRvComplaince;
	}
	public void setDdRvComplaince(String ddRvComplaince) {
		this.ddRvComplaince = ddRvComplaince;
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
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	 
}
