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

import org.hibernate.validator.constraints.NotEmpty;

/**
 * VisitorMaster entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VISITOR_MASTER")
@NamedQueries({

		@NamedQuery(name = "Visitor.FilterVisitorName", query = "select distinct(v.vmName) from Visitor v"),
		@NamedQuery(name = "Visitor.FilterVisitorAddress", query = "select v.vmFrom from Visitor v"),
		@NamedQuery(name = "Visitor.FilterVisitorContactNo", query = "select distinct(v.vmContactNo) from Visitor v"),
		@NamedQuery(name = "allVisitorRecords", query = "select v from Visitor v order by v.vmId desc"),
		@NamedQuery(name = "Checkexistence", query = "select vm.vmFrom from Visitor vm where vm.vmId=:vmId"),
		@NamedQuery(name = "updateVisitorMaster", query = "update Visitor vm set  vm.document=:document where vm.vmId=:vmId"),
		@NamedQuery(name = "Visitor.getId", query = "select vm.vmId from Visitor vm where vm.vmName=:vmName AND vm.vmContactNo=:vmContactNo order by vmId desc"),
		@NamedQuery(name = "Visitor.getVisitorIdBasedOnLastUpdatedDt", query = "select vm.vmId from Visitor vm where vm.vmLastUpdatedDt=:vmLastUpdatedDt AND vm.vmCreatedBy=:vmCreatedBy"),
		@NamedQuery(name = "Visitor.updateVisitorDocument", query = "Update Visitor v SET v.document = :document, v.documentNameType=:documentNameType WHERE v.vmId = :vmId"),
		@NamedQuery(name = "Visitor.getImage", query = "SELECT p.image FROM Visitor p WHERE p.vmId= :vmId"),
		@NamedQuery(name = "Visitor.getImageBasedOnVisitorName", query = "SELECT p.image FROM Visitor p WHERE p.vmName= :vmName"),
		@NamedQuery(name = "Visitor.updateVisitorImage", query = "Update Visitor v SET v.image = :image WHERE v.vmId = :vmId"),
		@NamedQuery(name = "Visitor.FindById", query = "select vm from Visitor vm where vm.vmId=:vmId"),
		@NamedQuery(name = "Visitor.updateAddress", query = "update Visitor v set v.vmFrom=:vmFrom where v.vmId=:vmId"),
		
		@NamedQuery(name = "Visitor.visitorDetails", query = "select count(*),(select count(*) from VisitorVisits v where v.vvstatus='OUT'),(select count(*) from VisitorVisits v1 where v1.vvstatus='IN') from Visitor vm group by 1"),
		/*@NamedQuery(name="",query="SELECT v.vmName,v.vmContactNo,v.vmFrom FROM Visitor v,VisitorVisits vv")*/

})
public class Visitor implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "VM_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "VISITOR_MASTER_SEQ", sequenceName = "VISITOR_MASTER_SEQ")
	@GeneratedValue(generator = "VISITOR_MASTER_SEQ")
	private int vmId;

	@Column(name = "VM_NAME", nullable = false, length = 20)
	/*
	 * @Pattern(regexp = "^[a-zA-Z]+[ a-zA-Z]*[a-zA-Z]$", message =
	 * "visitor name field can not allow special symbols except(_ .) ")
	 */
	private String vmName;

	@Column(name = "VM_FROM", length = 100)
	@NotEmpty(message = "'Address' can not be Empty")
	private String vmFrom;

	@Column(name = "VM_CONTACT_NO", nullable = false, length = 15)
	private String vmContactNo;

	@Column(name = "DR_GROUP_ID", precision = 11, scale = 0)
	private int drGroupId;

	@Column(name = "VM_CREATED_BY", precision = 11, scale = 0)
	private String vmCreatedBy;
	
	@Column(name = "PRE_REGISTERED_VISITOR", precision = 11, scale = 0)
	private String preRegistereduser;
	
	@Column(name = "VISITOR_PASSWORD", precision = 11, scale = 0)
	private String visitorPassword;

	@Column(name = "VM_LAST_UPDATED_BY", precision = 11, scale = 0)
	private String vmLastUpdatedBy;

	@NotEmpty(message = "'Visitor Gender' is not selected")
	@Column(name = "GENDER", length = 45)
	private String gender;

	@Column(name = "DOCUMENT")
	@Lob
	private Blob document;

	@Column(name = "IMAGE")
	@Lob
	private Blob image;

	@Column(name = "DOCUMENT_NAME_TYPE", length = 50)
	private String documentNameType;

	public String getDocumentNameType() {
		return documentNameType;
	}

	public void setDocumentNameType(String documentNameType) {
		this.documentNameType = documentNameType;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	@Column(name = "VM_LAST_UPDATED_DT")
	private Timestamp vmLastUpdatedDt;

	public Timestamp getVmLastUpdatedDt() {
		return vmLastUpdatedDt;
	}

	public void setVmLastUpdatedDt(Timestamp lastUpdateDt) {
		this.vmLastUpdatedDt = lastUpdateDt;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Blob getDocument() {
		return document;
	}

	public void setDocument(Blob document) {
		this.document = document;
	}

	public Visitor() {
	}

	/** minimal constructor */
	public Visitor(int vmId, String vmName, String vmContactNo) {
		this.vmId = vmId;
		this.vmName = vmName;
		this.vmContactNo = vmContactNo;
	}


	public int getVmId() {
		return this.vmId;
	}

	public Visitor(int vmId, String vmName, String vmFrom, String vmContactNo,
			int drGroupId, String createdBy, String lastUpdatedBy,
			String gender, Blob document, Blob image, Timestamp lastUpdateDt) {
		super();
		this.vmId = vmId;
		this.vmName = vmName;
		this.vmFrom = vmFrom;
		this.vmContactNo = vmContactNo;
		this.drGroupId = drGroupId;
		this.vmCreatedBy = createdBy;
		this.vmLastUpdatedBy = lastUpdatedBy;
		this.gender = gender;
		this.document = document;
		this.image = image;
		this.vmLastUpdatedDt = lastUpdateDt;
	}

	public void setVmId(int vmId) {
		this.vmId = vmId;
	}

	public String getVmName() {
		return this.vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getVmFrom() {
		return this.vmFrom;
	}

	public void setVmFrom(String vmFrom) {
		this.vmFrom = vmFrom;
	}

	public String getVmContactNo() {
		return this.vmContactNo;
	}

	public void setVmContactNo(String vmContactNo) {
		this.vmContactNo = vmContactNo;
	}

	public int getDrGroupId() {
		return this.drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}

	public String getVmCreatedBy() {
		return this.vmCreatedBy;
	}

	public void setVmCreatedBy(String createdBy) {
		this.vmCreatedBy = createdBy;
	}

	public String getVmLastUpdatedBy() {
		return this.vmLastUpdatedBy;
	}

	public void setVmLastUpdatedBy(String lastUpdatedBy) {
		this.vmLastUpdatedBy = lastUpdatedBy;
	}
	
	public String getPreRegistereduser() {
		return preRegistereduser;
	}
	public void setPreRegistereduser(String preRegistereduser) {
		this.preRegistereduser = preRegistereduser;
	}
	public String getVisitorPassword() {
		return visitorPassword;
	}
	public void setVisitorPassword(String visitorPassword) {
		this.visitorPassword = visitorPassword;
	}

}