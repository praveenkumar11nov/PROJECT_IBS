package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * OwnerProperty entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "OWNER_PROPERTY")
@NamedQueries({
	//@NamedQuery(name="OwnerProperty.getAllPropertyOnPersonId",query="SELECT op from OwnerProperty op join Owner o ON o.ownerId = op.ownerId join Person p ON o.personId = p.personId WHERE p.personId  = :personId")
	@NamedQuery(name="OwnerProperty.getAllPropertyOnPersonId",query="SELECT op FROM OwnerProperty op,Property p, Owner o where o.ownerId = op.ownerId and p.propertyId = op.propertyId and o.personId = :personId"),
	@NamedQuery(name="OwnerProperty.getAllPropertyOnOwnerId",query="SELECT op.propertyId FROM OwnerProperty op WHERE op.ownerId=:ownerId"),
	@NamedQuery(name="OwnerProperty.getPropertyOwnerShipStatus",query="SELECT COUNT(op.ownerId) FROM OwnerProperty op WHERE op.propertyId=:propertyId AND op.primaryOwner = 'Yes' "),
	@NamedQuery(name="OwnerProperty.checkPropertyAssigned",query="SELECT COUNT(op.ownerId) FROM OwnerProperty op WHERE op.propertyId=:propertyId AND op.ownerId = :ownerId "),
	@NamedQuery(name="OwnerProperty.AssignedPropertyToOwner",query="SELECT op FROM OwnerProperty op WHERE op.ownerId = :ownerId AND op.propertyId = :propertyId"),
	@NamedQuery(name="OwnerProperty.getOwnerType",query="select model from OwnerProperty model where model.property=:property"),
	@NamedQuery(name="OwnerProperty.getOwnerPropertyBasedOnPropertyIdAndOwnerId",query="select ow FROM OwnerProperty ow WHERE ow.propertyId=:propertyId"),
    @NamedQuery(name="OwnerProperty.findData",query="select o.ownerId,o.propertyId from OwnerProperty o "),
    
    
    @NamedQuery(name="OwnerProperty.chartData1",query="SELECT MIN(r.propertyAquiredDate),MAX(r.propertyAquiredDate) FROM OwnerProperty r WHERE r.status='Active' AND r.propertyAquiredDate IS NOT NULL"),
    @NamedQuery(name="OwnerProperty.chartData2",query="SELECT COUNT(*) FROM OwnerProperty r WHERE r.status='Active' AND r.propertyAquiredDate IS NOT NULL AND r.propertyAquiredDate BETWEEN TO_DATE(:date1,'YYYY-MM-DD') AND TO_DATE(:date2,'YYYY-MM-DD')"),
    
    
})
public class OwnerProperty implements java.io.Serializable {

	// Fields

	private int ownerPropertyId;
	private Property property;
	private Owner owner;
	private Long drGroupId;
	private String visitorRegReq;
	private Date propertyAquiredDate;
	private Date propertyRelingDate;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String propertyNo;
	private int propertyId;
	private int ownerId;
	private Person person;
	private String primaryOwner;
	private String residential;
	
	
	

	// Constructors
	@Column(name = "PRIMARY_OWNER", length = 1)
	public String getPrimaryOwner() {
		return primaryOwner;
	}

	public void setPrimaryOwner(String primaryOwner) {
		this.primaryOwner = primaryOwner;
	}

	/** default constructor */
	public OwnerProperty() {
	}

	/** minimal constructor */
	public OwnerProperty(int ownerPropertyId, Property property, Owner owner,
			Date propertyAquiredDate, String status) {
		this.ownerPropertyId = ownerPropertyId;
		this.property = property;
		this.owner = owner;
		this.propertyAquiredDate = propertyAquiredDate;
		this.status = status;
	}

	/** full constructor */
	public OwnerProperty(int ownerPropertyId, Property property, Owner owner,
			Long drGroupId, String visitorRegReq, Date propertyAquiredDate,
			Date propertyRelingDate, String status, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		this.ownerPropertyId = ownerPropertyId;
		this.property = property;
		this.owner = owner;
		this.drGroupId = drGroupId;
		this.visitorRegReq = visitorRegReq;
		this.propertyAquiredDate = propertyAquiredDate;
		this.propertyRelingDate = propertyRelingDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		
	}
	
	
	

	// Property accessors
	@Id
	@SequenceGenerator(name="ownerPropertySeq",sequenceName="OWNER_PROPERTY_SEQ")
	@GeneratedValue(generator="ownerPropertySeq")
	@Column(name = "OWNER_PROPERTY_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getOwnerPropertyId() {
		return this.ownerPropertyId;
	}

	public void setOwnerPropertyId(int ownerPropertyId) {
		this.ownerPropertyId = ownerPropertyId;
	}

	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROPERTY_ID", nullable = false)*/
	@OneToOne
	@JoinColumn(name = "PROPERTY_ID",insertable=false,updatable=false)
	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OWNER_ID", insertable=false,updatable=false)
	/*@OneToOne
	@JoinColumn(name = "OWNER_ID",insertable=false,updatable=false)*/
	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Column(name = "DR_GROUP_ID", precision = 11, scale = 0)
	public Long getDrGroupId() {
		return this.drGroupId;
	}

	public void setDrGroupId(Long drGroupId) {
		this.drGroupId = drGroupId;
	}
	
	@NotEmpty(message = "'Visitor Required' is empty")
	@Column(name = "VISITOR_REG_REQ", length = 1)
	public String getVisitorRegReq() {
		return this.visitorRegReq;
	}

	public void setVisitorRegReq(String visitorRegReq) {
		this.visitorRegReq = visitorRegReq;
	}

	 
	@Column(name = "PROPERTY_AQUIRED_DATE", nullable = false, length = 7)
	public Date getPropertyAquiredDate() {
		return this.propertyAquiredDate;
	}

	public void setPropertyAquiredDate(Date propertyAquiredDate) {
		this.propertyAquiredDate = propertyAquiredDate;
	}

	 
	@Column(name = "PROPERTY_RELING_DATE", length = 7)
	public Date getPropertyRelingDate() {
		return this.propertyRelingDate;
	}

	public void setPropertyRelingDate(Date propertyRelingDate) {
		this.propertyRelingDate = propertyRelingDate;
	}
	
	@Column(name = "STATUS", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	@Min(value = 1, message = "Property number is not selected")
	@Column(name="PROPERTY_ID")
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	
	@Transient
	public String getPropertyNo()
	{
		return propertyNo;
	}
	
	public void setPropertyNo(String propertyNo)
	{
		this.propertyNo = propertyNo;
	}
	
	@Column(name = "OWNER_ID", length = 11)
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	@Column(name = "RESIDENTIAL")
	public String getResidential() {
		return residential;
	}

	public void setResidential(String residential) {
		this.residential = residential;
	}
	
	
	
	
	

}