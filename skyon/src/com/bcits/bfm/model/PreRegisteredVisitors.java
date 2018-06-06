package com.bcits.bfm.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CP_VISITORS")
@NamedQueries
({
	@NamedQuery(name = "PreRegisteredVisitors.findAll", query = "select v from PreRegisteredVisitors v WHERE v.preRegistereduser='Yes' order by v.viId desc"),
	@NamedQuery(name="PreRegisteredVisitors.getVisitorsRequiredDetails",
    query="SELECT v.viId,v.visitorName,v.visitorContactNo,v.visitorFrom,v.gender,v.parkingRequired,v.preRegistereduser,v.status,p.propertyId,p.property_No,b.blockId,b.blockName,v.vehicleNo,v.fromTime,v.toTime,v.noOfVisitors,v.visitorPassword,v.createdBy,v.visitingDate FROM PreRegisteredVisitors v INNER JOIN v.property p INNER JOIN v.blocks b WHERE v.preRegistereduser='Yes'"),
    
	@NamedQuery(name = "SearchPreRegisteredVisitorBasedOnContactNo", query = "select v from PreRegisteredVisitors v where v.visitorContactNo=:visitorContactNo order by v.viId desc"),
	@NamedQuery(name="Visitor.getPropertyIdBasedOnPropertyNo",query="SELECT p.propertyId FROM Property p WHERE p.property_No=:propertyNo"),
	@NamedQuery(name="Visitor.getParkinSlotIdBasedOnPsSlotNo",query="SELECT p.psId FROM ParkingSlots p WHERE p.psSlotNo=:psSlotNo"),
	
	@NamedQuery(name = "PreVisitor.getReRegisteredImageForWizardView", query = "SELECT p.personImages FROM PreRegisteredVisitors p WHERE p.visitorContactNo= :visitorContactNo" ),
	@NamedQuery(name = "PreVisitor.getImageForPreRegisteredView", query = "SELECT p.personImages FROM PreRegisteredVisitors p WHERE p.viId= :viId" ),
})
public class PreRegisteredVisitors 
{
	@Id
	@Column(name = "VI_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private int viId;
	
	@Column(name = "BLOCK_ID", nullable = false, precision = 11, scale = 0)
	private int blockId;
	
	@OneToOne
	@JoinColumn(name = "BLOCK_ID", nullable = false, insertable = false, updatable = false)
	private Blocks blocks;	
	
	@Column(name = "PROPERTY_ID", nullable = false, precision = 11, scale = 0)
	private int propertyId;
	
	@OneToOne
	@JoinColumn(name = "PROPERTY_ID", nullable = false, insertable = false, updatable = false)
	private Property property;

	@Column(name = "VISITOR_NAME", nullable = false, length = 20)
	private String visitorName;
	
	@Column(name = "CONTACT_NO", nullable = false, length = 15)
	private String visitorContactNo;
	
	@Column(name="FROM_TIME", length=11)
    private String fromTime;
	
	@Column(name="TO_TIME", length=11)
    private String toTime;

	@Column(name = "PARKING_REQUIRED", nullable = false, length = 15)
	private String parkingRequired;
	
	@Column(name = "VEHICLE_NO", nullable = false, length = 15)
	private String vehicleNo;
	
	@Column(name = "VISITOR_PASSWORD", nullable = false, length = 15)
	private String visitorPassword;
	
	@Column(name = "VISITOR_FROM", length = 100)
	private String visitorFrom;
	
	@Column(name = "PRE_REGISTERED_VISITOR", precision = 11, scale = 0)
	private String preRegistereduser;

	@Column(name = "CREATED_BY", precision = 11, scale = 0)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", precision = 11, scale = 0)
	private String lastUpdatedBy;

	@Column(name = "GENDER", length = 45)
	private String gender;
	
	@Column(name = "NO_OF_VISITORS", length = 45)
	private int noOfVisitors;
	
	@Column(name = "STATUS", length = 45)
	private String status;
	
	@Column(name = "VISITING_DATE")
	private Date visitingDate;
	
	@Lob
    @Column(name="PERSON_IMAGE")
	private Blob personImages;

	public PreRegisteredVisitors(){}

	public int getViId() {
		return viId;
	}
	public void setViId(int viId) {
		this.viId = viId;
	}
	public int getBlockId() {
		return blockId;
	}
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	public Blocks getBlocks() {
		return blocks;
	}
	public void setBlocks(Blocks blocks) {
		this.blocks = blocks;
	}
	public int getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getVisitorContactNo() {
		return visitorContactNo;
	}
	public void setVisitorContactNo(String visitorContactNo) {
		this.visitorContactNo = visitorContactNo;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getParkingRequired() {
		return parkingRequired;
	}
	public void setParkingRequired(String parkingRequired) {
		this.parkingRequired = parkingRequired;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVisitorPassword() {
		return visitorPassword;
	}
	public void setVisitorPassword(String visitorPassword) {
		this.visitorPassword = visitorPassword;
	}
	public String getVisitorFrom() {
		return visitorFrom;
	}
	public void setVisitorFrom(String visitorFrom) {
		this.visitorFrom = visitorFrom;
	}
	public String getPreRegistereduser() {
		return preRegistereduser;
	}
	public void setPreRegistereduser(String preRegistereduser) {
		this.preRegistereduser = preRegistereduser;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getNoOfVisitors() {
		return noOfVisitors;
	}
	public void setNoOfVisitors(int noOfVisitors) {
		this.noOfVisitors = noOfVisitors;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getVisitingDate() {
		return visitingDate;
	}
	public void setVisitingDate(Date visitingDate) {
		this.visitingDate = visitingDate;
	}
	public Blob getPersonImages() {
		return personImages;
	}
	public void setPersonImages(Blob personImages) {
		this.personImages = personImages;
	}
}
