package com.bcits.bfm.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bcits.bfm.util.SessionData;



@Entity
@Table(name = "STAFF_NOTICES")
@NamedQueries({
	@NamedQuery(name = "StaffNotices.findAll", query = "SELECT s FROM StaffNotices s ORDER BY s.snId"),
	@NamedQuery(name = "StaffNotices.findById", query = "SELECT s FROM StaffNotices s  WHERE s.personId = :personId"),
	@NamedQuery(name = "StaffNotices.getCountOfNotices", query = "SELECT s.noticeType,count(*) FROM StaffNotices s  WHERE s.personId = :personId group by s.noticeType"),
	@NamedQuery(name = "StaffNotices.uploadNoticeOnId", query = "UPDATE StaffNotices s SET s.noticeDocument= :noticeDocument , s.noticeDocumentType=:noticeDocumentType  WHERE s.snId= :snId" )
})
public class StaffNotices {
	
	
	
	/*@SequenceGenerator(name="STAFF_NOTICE_SEQ",sequenceName="STAFF_NOTICE_SEQ")
	@GeneratedValue(generator="STAFF_NOTICE_SEQ")*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="staffnotice_seq_gen")
	@SequenceGenerator(name="staffnotice_seq_gen", sequenceName="STAFF_NOTICE_SEQ")
	@Column(name = "SN_ID")
	private int snId;	
	
	
	@Min(value = 1, message = "'Person' is not selected")
	@Column(name = "PERSON_ID", precision = 10, scale = 0)
	private int personId;
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;
	
	@Column(name = "NOTICE_TYPE")
	private String noticeType;
	
	@Size(min = 0, max = 300, message = "Description can have maximum {max} letters")
	@Column(name = "DESCRIPTION")
	private String description;
	
	/*@Column(name = "DR_GROUP_ID")
	private int drGroupId;*/
	
	@NotNull(message="Notice Date should not be greater than Action Date")
	@Column(name = "SN_DT")
	private Date snDate;
	
	@Size(min = 1, max = 100, message = "Notice Action can have maximum {max} letters")	
	@Column(name = "SN_ACTION")
	private String snAction;
	
	@Column(name = "SN_ACTION_DT")
	private Date snActionDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdateBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Date lastUpdatedDate;
	
	@Column(name = "NOTICE_DOC")
	private Blob noticeDocument;
	
	@Column(name = "NOTICE_DOC_TYPE")
	private String noticeDocumentType;
	
	/*@Column(name="CREATED_AT")
	private Date createdAt = Calendar.getInstance().getTime();
*/
	public int getSnId() {
		return snId;
	}

	public void setSnId(int snId) {
		this.snId = snId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

/*	public int getDrGroupId() {
		return drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}
*/
	public Date getSnDate() {
		return snDate;
	}

	public void setSnDate(Date snDate) {
		this.snDate = snDate;
	}

	public String getSnAction() {
		return snAction;
	}

	public void setSnAction(String snAction) {
		this.snAction = snAction;
	}

	public Date getSnActionDate() {
		return snActionDate;
	}

	public void setSnActionDate(Date snActionDate) {
		this.snActionDate = snActionDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Blob getNoticeDocument() {
		return noticeDocument;
	}

	public void setNoticeDocument(Blob noticeDocument) {
		this.noticeDocument = noticeDocument;
	}
	
	
	
	
	

	public String getNoticeDocumentType() {
		return noticeDocumentType;
	}

	public void setNoticeDocumentType(String noticeDocumentType) {
		this.noticeDocumentType = noticeDocumentType;
	}

	@PrePersist
	 protected void onCreate() {
		lastUpdateBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	 @PreUpdate
	 protected void onUpdate() {
		 lastUpdateBy = (String) SessionData.getUserDetails().get("userID");
	 }
}
