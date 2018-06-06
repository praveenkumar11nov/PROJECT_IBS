package com.bcits.bfm.model;


import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Faq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FAQ")

@NamedQueries({
	@NamedQuery(name = "Faq.updateDocument", query = "UPDATE Faq f SET f.faqDocument=:faqDocument WHERE f.faqId=:faqId")

})
public class Faq implements java.io.Serializable {

	// Fields

	private int faqId;
	private String faqType;
	private String faqSubject;
	private String faqContent;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private Blob faqDocument;


	// Property accessors
	@Id
	@Column(name = "FAQ_ID", unique = true, nullable = false, precision = 6, scale = 0)
	@SequenceGenerator(name = "FAQ_SEQUENCE", sequenceName = "FAQ_SEQUENCE")
	@GeneratedValue(generator = "FAQ_SEQUENCE")	
	public int getFaqId() {
		return this.faqId;
	}

	public void setFaqId(int faqId) {
		this.faqId = faqId;
	}

	@Column(name = "FAQ_TYPE", nullable = false, length = 200)
	@NotNull(message = "FAQ Type Should Not Be Empty")
	@Size(max = 200, message = "FAQ Type Must Be Max 200 Length Character")
	public String getFaqType() {
		return this.faqType;
	}

	public void setFaqType(String faqType) {
		this.faqType = faqType;
	}

	@Column(name = "FAQ_SUBJECT", nullable = false, length = 500)
	@NotNull(message = "FAQ Subject Should Not Be Empty")
	@Size(max = 500, message = "FAQ Subject Must Be Max 500 Length Character")
	public String getFaqSubject() {
		return this.faqSubject;
	}

	public void setFaqSubject(String faqSubject) {
		this.faqSubject = faqSubject;
	}

	@Column(name = "FAQ_CONTENT", nullable = false, length = 3000)
	@NotNull(message = "FAQ Content Should Not Be Empty")
	@Size(max = 3000, message = "FAQ Content Must Be Max 3000 Length Character")
	public String getFaqContent() {
		return this.faqContent;
	}

	public void setFaqContent(String faqContent) {
		this.faqContent = faqContent;
	}

	@Column(name = "CREATED_BY", nullable = true, length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", nullable = true, length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DATE", nullable = true, length = 11)
	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
    @Lob
    @Column(name="FAQ_DOCUMENT_FILE")

	public Blob getFaqDocument() {
		return faqDocument;
	}

	public void setFaqDocument(Blob faqDocument) {
		this.faqDocument = faqDocument;
	}
	
	

}