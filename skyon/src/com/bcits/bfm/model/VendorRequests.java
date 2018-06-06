package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

/**
 * VendorRequests entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VENDOR_REQUESTS")
@NamedQueries
({
	@NamedQuery(name="VendorRequests.findAll",query="SELECT vr FROM VendorRequests vr ORDER BY vr.vrId DESC"),
	@NamedQuery(name="VendorRequests.updateVendorRequestStatus",query="UPDATE VendorRequests vr SET vr.status = :newStatus,vr.replyNote = :replyNote WHERE vr.vrId = :id"),
	@NamedQuery(name="VendorRequests.findAllList",query="SELECT vr.vcId,vc.contractName,vc.contractNo,vr.vrId,vr.vendorId,p.firstName,p.lastName,vr.requestType,vr.requestNote,vr.replyNote,vr.status,vr.createdBy,vr.lastUpdatedBy,vr.lastUpdatedDt FROM VendorRequests vr INNER JOIN vr.vendorContracts vc INNER JOIN vr.vendors ve INNER JOIN ve.person p ORDER BY vr.vrId DESC"),

})

public class VendorRequests implements java.io.Serializable 
{
	@Id
	@SequenceGenerator(name = "vendorRequestsSeq", sequenceName = "VENDOR_REQUEST_SEQ")
	@GeneratedValue(generator = "vendorRequestsSeq")
	
	@Column(name = "VR_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vrId;
	
	@Column(name = "VC_ID")
	private int vcId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VC_ID", nullable = false,insertable = false,updatable = false)
	private VendorContracts vendorContracts;
	
	@Column(name = "VENDOR_ID")
	private int vendorId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VENDOR_ID", insertable = false, updatable = false, nullable = false)
	private Vendors vendors;
	
	@Column(name = "REQUEST_TYPE", nullable = false, length = 45)
	private String requestType;
	
	@Column(name = "REQUEST_NOTE", length = 500)
	private String requestNote;
	
	@Column(name = "REPLY_NOTE", length = 225)
	private String replyNote;
	
	@Column(name = "STATUS", nullable = false, length = 45)
	private String status;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
	
	@Transient
	private String vendorName;
	
	public VendorRequests(){}
	public VendorRequests(int vrId, Vendors vendors,VendorContracts vendorContracts, int vendorId, String requestType,String status) 
	{
		this.vrId = vrId;
		this.vendors = vendors;
		this.vendorContracts = vendorContracts;
		this.vendorId = vendorId;
		this.requestType = requestType;
		this.status = status;
	}

	public VendorRequests(int vrId, Vendors vendors,VendorContracts vendorContracts, int vendorId, String requestType,String requestNote,
						  String replyNote, String status,String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt) 
	{
		this.vrId = vrId;
		this.vendors = vendors;
		this.vendorContracts = vendorContracts;
		this.vendorId = vendorId;
		this.requestType = requestType;
		this.requestNote = requestNote;
		this.replyNote = replyNote;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}	
	public int getVrId() {
		return this.vrId;
	}
	public void setVrId(int vrId) {
		this.vrId = vrId;
	}
	public Vendors getVendors() {
		return this.vendors;
	}
	public void setVendors(Vendors vendors) {
		this.vendors = vendors;
	}	
	public VendorContracts getVendorContracts() {
		return this.vendorContracts;
	}
	public void setVendorContracts(VendorContracts vendorContracts) {
		this.vendorContracts = vendorContracts;
	}
	public int getVendorId() {
		return this.vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}	
	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}	
	public String getRequestNote() {
		return this.requestNote;
	}
	public void setRequestNote(String requestNote) {
		this.requestNote = requestNote;
	}	
	public String getReplyNote() {
		return this.replyNote;
	}
	public void setReplyNote(String replyNote) {
		this.replyNote = replyNote;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getCreatedBy() {
		return this.createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}	
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	@PrePersist
	protected void onCreate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	public int getVcId() {
		return vcId;
	}
	public void setVcId(int vcId) {
		this.vcId = vcId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}	
}