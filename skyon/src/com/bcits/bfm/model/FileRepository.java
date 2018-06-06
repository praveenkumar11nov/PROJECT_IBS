package com.bcits.bfm.model;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * FileRepositoryMaster entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_REPOSITORY_MASTER")
@NamedQueries({

	@NamedQuery(name="FileRepository.findDocName",query="select fp.docName from FileRepository fp where fp.docName=:docName"),
	@NamedQuery(name="FileRepository.findName",query="select fp.docName from FileRepository fp"),
	@NamedQuery(name="FileRepository.findAll",query="select fp from FileRepository fp order by fp.frId desc"),
	@NamedQuery(name = "FileRepository.UpdateStatus",query="UPDATE FileRepository fp SET  fp.status = :status ,fp.lastUpdatedDt=:lastUpdatedDt WHERE fp.frId = :frId"),
	@NamedQuery(name="FileRepository.findIds",query="select  fp.frId from FileRepository fp where fp.frGroupId=:frGroupId"),
	@NamedQuery(name="FileRepository.DOC_TYPE",query="select distinct(fp.docType) from FileRepository fp  "),
	@NamedQuery(name="FileRepository.createdBy",query="select distinct(fp.createdby) from FileRepository fp  "),
	@NamedQuery(name="FileRepository.lastupdatedBy",query="select distinct(fp.lastupdatedBy) from FileRepository fp  "),
	@NamedQuery(name="FileRepository.findInfo",query="select  fp from FileRepository fp where fp.frGroupId=:frGroupId"),
	@NamedQuery(name="FileRepository.DownloadDocument",query="select fp.drFileBlob from FileRepository fp where fp.frId=:frId"),
	@NamedQuery(name="updateFileRepository",query="update FileRepository fr set  fr.drFileBlob=:drFileBlob,fr.docType=:docType where fr.frId=:frId"),
	@NamedQuery(name="FileRepository.checkDocument",query="select fp from FileRepository fp where fp.frId=:frId"),

	@NamedQuery(name="DocumentRepository.documentRepositoryDetails",query="select count(*) from DocumentRepository fp where fp.documentType='Property'"),

})


public class FileRepository implements java.io.Serializable { 

	// Fields
	@Id
	@Column(name = "FR_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name="SEQ_FILE_REPOSITORY_MASTER",sequenceName="FILE_REPOSITORY_MASTER_SEQ")
	@GeneratedValue(generator="SEQ_FILE_REPOSITORY_MASTER")
	private int frId;
	
	
	
	@Column(name = "FR_GROUP_ID")
	private int frGroupId;
	
	@Column(name = "DOC_NAME", nullable = false, length = 45)
	private String docName;
	
	@Column(name = "DOC_DESCRIPTION", length = 100)
	private String docDescription;
	
	//@NotEmpty( message = "'Document Type' is not selected")
	@Column(name = "DOC_TYPE", length = 45)
	private String docType;
	
	@Column(name = "FR_SEARCH_TAG", length = 45)
	private String frSearchTag;
	
	@Column(name = "STATUS", length = 45)
	private String status;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdby;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastupdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	@Column(name = "DR_FILE_BLOB")
	private Blob drFileBlob;

	@OneToOne
	@JoinColumn(name="FR_GROUP_ID",nullable=false,insertable=false,updatable=false)
	private FileRepositoryTree fileRepositoryTree;
	
	// Constructors

	/** default constructor */
	public FileRepository() {
	}

	/** minimal constructor */
	public FileRepository(int frId, FileRepositoryTree fileRepositoryTree,
			int frGroupId, String docName) {
		this.frId = frId;
		this.fileRepositoryTree = fileRepositoryTree;
		this.frGroupId = frGroupId;
		this.docName = docName;
	}

	
	

	// Property accessors

	public int getFrId() {
		return this.frId;
	}
	/** full constructor */
	public FileRepository(int frId, int frGroupId, String docName,
			String docDescription, String docType, String frSearchTag,
			String status, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt, Blob drFileBlob) {
		super();
		this.frId = frId;
		this.frGroupId = frGroupId;
		this.docName = docName;
		this.docDescription = docDescription;
		this.docType = docType;
		this.frSearchTag = frSearchTag;
		this.status = status;
		this.createdby = createdBy;
		this.lastupdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.drFileBlob = drFileBlob;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	
	

	
	public int getFrGroupId() {
		return this.frGroupId;
	}

	public FileRepositoryTree getFileRepositoryTree() {
		return fileRepositoryTree;
	}

	public void setFileRepositoryTree(FileRepositoryTree fileRepositoryTree) {
		this.fileRepositoryTree = fileRepositoryTree;
	}

	public void setFrGroupId(int frGroupId) {
		this.frGroupId = frGroupId;
	}

	
	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	
	public String getDocDescription() {
		return this.docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	
	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	
	public String getFrSearchTag() {
		return this.frSearchTag;
	}

	public void setFrSearchTag(String frSearchTag) {
		this.frSearchTag = frSearchTag;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdBy) {
		this.createdby = createdBy;
	}

	
	public String getLastupdatedBy() {
		return this.lastupdatedBy;
	}

	public void setLastupdatedBy(String lastUpdatedBy) {
		this.lastupdatedBy = lastUpdatedBy;
	}

	
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	
	public Blob getDrFileBlob() {
		return this.drFileBlob;
	}

	public void setDrFileBlob(Blob drFileBlob) {
		this.drFileBlob = drFileBlob;
	}

}