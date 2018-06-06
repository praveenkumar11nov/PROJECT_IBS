package com.bcits.bfm.model;

import java.sql.Blob;
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
 * JobDocuments entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JOB_DOCUMENTS")
@NamedQueries({
	@NamedQuery(name = "JobCards.readJobDocuments", query = "SELECT model FROM JobDocuments model WHERE model.jobCards=:jcId"),
	@NamedQuery(name="JobDocuments.uploadDocumentOnDrId",query="UPDATE JobDocuments dr SET dr.jobDocument= :document WHERE dr.jobDocId= :jobDocId"),
})
public class JobDocuments implements java.io.Serializable {

	// Fields
	
	private static final long serialVersionUID = 1L;
	private int jobDocId;
	private JobCards jobCards;
	private String documentDescription;
	private Blob jobDocument;
	private String createdBy;
	private String updatedBy;
	private Timestamp lastUpdatedDate;
	private String documentName;
	private String documentType;

	// Constructors

	/** default constructor */
	public JobDocuments() {
	}

	/** full constructor */
	public JobDocuments(int jobDocId, JobCards jobCards,
			String documentDescription, Blob jobDocument, String createdBy,
			String updatedBy, Timestamp lastUpdatedDate, String documentName,
			String documentType) {
		this.jobDocId = jobDocId;
		this.jobCards = jobCards;
		this.documentDescription = documentDescription;
		this.jobDocument = jobDocument;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.documentName = documentName;
		this.documentType = documentType;
	}

	// Property accessors
	@Id
	@Column(name = "JOB_DOC_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_DOCUMENTS_SEQUENCE", sequenceName = "JOB_DOCUMENTS_SEQUENCE")
	@GeneratedValue(generator = "JOB_DOCUMENTS_SEQUENCE")
	public int getJobDocId() {
		return this.jobDocId;
	}

	public void setJobDocId(int jobDocId) {
		this.jobDocId = jobDocId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JC_ID", nullable = false)
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@Column(name = "DOCUMENT_DESCRIPTION", length = 500)
	public String getDocumentDescription() {
		return this.documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	@Column(name = "JOB_DOCUMENT", nullable = false)
	public Blob getJobDocument() {
		return this.jobDocument;
	}

	public void setJobDocument(Blob jobDocument) {
		this.jobDocument = jobDocument;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", length = 45)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Column(name = "DOCUMENT_NAME", nullable = false, length = 45)
	public String getDocumentName() {
		return this.documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Column(name = "DOCUMENT_TYPE", nullable = false, length = 45)
	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

}