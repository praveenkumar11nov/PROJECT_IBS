package com.bcits.bfm.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DOCUMENT_REPOSITORY")
@NamedQueries({
	@NamedQuery(name="DocumentRepository.findAllByPersonId",query="SELECT dr FROM DocumentRepository dr WHERE dr.drGroupId = :drGroupId AND dr.documentType = :documentType"),
	@NamedQuery(name="DocumentRepository.findAllBydrId",query="SELECT dr.documentFile FROM DocumentRepository dr WHERE dr.drId = :drId"),
	@NamedQuery(name="DocumentRepository.uploadDocumentOnDrId",query="UPDATE DocumentRepository dr SET dr.documentFile= :document WHERE dr.drId= :drId"),
	@NamedQuery(name="DocumentRepository.findAll",query="SELECT dr.drId,dr.drGroupId,p.lastName,p.firstName,dr.approved,dr.documentName,dr.documentNumber,dr.documentDescription,dr.documentType FROM DocumentRepository dr INNER JOIN dr.person p"),
	@NamedQuery(name="DocumentRepository.UpdateApproveStatus",query="UPDATE DocumentRepository dr SET dr.approved = :approved WHERE dr.drId = :drId"),
	@NamedQuery(name="DocumentRepository.getDocumentRepositoryObjectOnGroupId",query="SELECT dr FROM DocumentRepository dr,DocumentDefiner dd WHERE dr.drGroupId = :groupId And dr.documentName = dd.ddName AND dr.documentType= :personType AND dr.documentType = dd.ddType AND dd.ddOptional='No' "),
	@NamedQuery(name="DocumentRepository.getDocumentRepositoryObject",query="SELECT dr FROM DocumentRepository dr WHERE dr.drGroupId = :groupId AND dr.documentType= :personType  "),
	@NamedQuery(name="DocumentRepository.getMaximunId",query="SELECT max(dr.drGroupId) FROM DocumentRepository dr"),
	@NamedQuery(name="Dcumentrepoitry.getDocumentObjectBuid",query="SELECT d FROM DocumentRepository d WHERE d.drId=:drId "),
	@NamedQuery(name="DocumentRepository.getPersonFilterForDocumentUrl",query="SELECT CONCAT(p.firstName,NVL(p.lastName,'')) FROM DocumentRepository dr INNER JOIN dr.person p"),
	@NamedQuery(name="DocumentPers.NameFilter",query="select p.firstName,p.lastName from DocumentRepository d inner join d.person p")
})
public class DocumentRepository 
{
	@Id
	@SequenceGenerator(name = "drSeq", sequenceName = "DOCUMENT_REPOSITORY_SEQ")
	@GeneratedValue(generator = "drSeq")
	@Column(name = "DR_ID")
	private int drId;
	
	@Column(name = "DR_GROUP_ID")
	private int drGroupId;
	
	@Column(name="DR_FILE")
    @Lob
	private Blob documentFile;
	
	@Column(name="APPROVED")
	private String approved;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Date lastUpdatedDate;
	
	@Column(name = "DR_NAME")
	private String documentName;
	
	@Column(name = "DR_NUMBER")
	private String documentNumber;
	
	@Column(name = "DR_DESCRIPTION")
	private String documentDescription;
	
	@Column(name="DR_DOCTYPE")
	private String documentType;
	
	@Column(name="DR_DOCUMENT_FORMAT")
	private String documentFormat;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName="DR_GROUP_ID",name = "DR_GROUP_ID",insertable = false, updatable = false, nullable = false)
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getDocumentFormat() {
		return documentFormat;
	}

	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public int getDrId() {
		return drId;
	}

	public void setDrId(int drId) {
		this.drId = drId;
	}

	public int getDrGroupId() {
		return drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}

	public Blob getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(Blob documentFile) {
		this.documentFile = documentFile;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
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

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
