package com.bcits.bfm.model;

import java.sql.Blob;
import java.sql.Timestamp;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 * Property entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PROPERTY")
@NamedQueries({
/*
  query changed for ( Property.findAll and Property.getPropertyDataBasedOnBlockId ----> ,PrePaidMeters pm where p.propertyId=pm.propertyId is added)
*/		@NamedQuery(name = "Property.findAll", query = "select p from Property p    order by p.propertyId DESC"),
	    @NamedQuery(name = "Property.findAllNotification", query = "select p from Property p,PrePaidMeters pm where p.propertyId=pm.propertyId  order by p.propertyId DESC"),
		@NamedQuery(name = "Property.count", query = "select count(p.propertyId) from Property p"),
		@NamedQuery(name = "Property.findpropertyId", query = "select p.propertyId from Property p where p.propertyName=:propertyName"),
		@NamedQuery(name = "Property.findpropertyIdBasedOnPropertyNo", query = "select p.propertyId from Property p where p.property_No=:property_No"),
		@NamedQuery(name = "Property.getProprtyNoBasedOnPropertyId", query = "SELECT p.property_No FROM Property p WHERE p.propertyId=:propertyId"),
		@NamedQuery(name = "Property.getPropertyNo", query = "SELECT pr FROM Person p, Property pr WHERE p.personType IN (:owner, :tenant) "),
		@NamedQuery(name = "Property.getProprtyIdBasedOnPropertyNo", query = "SELECT p.propertyId FROM Property p WHERE p.property_No=:propertyNo"),
		@NamedQuery(name = "Property.getPropertyNoBasedOnTenant", query = "SELECT pr FROM Person p , Owner o , OwnerProperty op , Property pr WHERE p.personId = o.personId "
				+ "AND o.ownerId = OP.ownerId AND OP.propertyId = PR.propertyId AND p.personType IN (:owner, :tenant) "),
		@NamedQuery(name = "Property.propertyNameForFilter", query = "select p.property_No from Property p"),
		@NamedQuery(name="Property.findAllOnIds",query="SELECT p FROM Property p WHERE p.projectId = :projectId AND p.property_No= :property_No AND p.blockId = :blockId"),
		@NamedQuery(name="Property.getFamilyMembersBasedOnPersonId",query="SELECT p.personId,p.firstName,p.lastName,fp.fpRelationship,p.middleName FROM FamilyProperty fp, Family f, Person p WHERE fp.familyId = f.familyId AND f.personId = p.personId AND fp.ownerId = :personId"),
		@NamedQuery(name="Property.getPersonContactDetails",query="SELECT c FROM Contact c WHERE c.personId = :personId"),
		@NamedQuery(name="Property.getPropertyNameBasedOnPropertyId",query="select pr.property_No from Property pr WHERE pr.propertyId=:propertyId"),
		@NamedQuery(name="Property.getBlockIdBasedOnPropertyId",query="select pr.blockId from Property pr WHERE pr.propertyId=:propertyId"),
		@NamedQuery(name="Property.uploadPropertyOnId", query = "UPDATE Property p SET p.propertyDocument= :propertyDocument , p.propertyDocumentType=:docType  WHERE p.propertyId= :propertyId"),
		@NamedQuery(name="Property.getPropertyDataBasedOnBlockId", query = "SELECT p FROM Property p,PrePaidMeters pm where p.propertyId=pm.propertyId and p.blockId=:blockId"),
		@NamedQuery(name="Property.findAllforParking", query = "select model from Property model"),
		@NamedQuery(name="Property.getPropertyListBasedOnPropertyNo", query = "select p from Property p WHERE p.property_No LIKE :propertyNo"),
		@NamedQuery(name="Property.getPossesionDate", query = "SELECT p.tenancyHandoverDate FROM Property p WHERE p.propertyId=:propertyId"),
		@NamedQuery(name="Property.getPropertyNoBasedOnPersonId",query="select pr.property_No from Property pr, Person per, OwnerProperty o, Owner ow where per.personId=:personId and o.propertyId=pr.propertyId and per.personId=ow.personId and ow.ownerId=o.ownerId"),
		@NamedQuery(name="Property.getPropertyByBlockId",query="SELECT p FROM Property p WHERE p.blockId=:blockId and p.status IN ('Sold/UnOccupied','Sold') "),


		@NamedQuery(name="Property.getPropertyByPropertyID",query="select p from Property p where p.propertyId=:propertyId")
		

})
public class Property implements java.io.Serializable {

	// Fields
	@Id 
	@SequenceGenerator(name = "PROPERTY_SEQ", sequenceName = "PROPERTY_SEQ")
	@GeneratedValue(generator = "PROPERTY_SEQ")
	@Column(name = "PROPERTY_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int propertyId;

	@Column(name = "BLOCK_ID", length = 1)
	private int blockId;

	@Size(min = 1, message = "Property Number is not empty")
	@Column(name = "PROPERTY_NO", precision = 11, scale = 0)
	private String property_No;

	@Column(name = "PROPERTY_NAME", length = 100)
	private String propertyName;

	@Column(name = "PROPERTY_FLOOR", precision = 2, scale = 0)
	private int property_Floor;

	@Column(name = "STATUS", length = 45)
	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name = "TENANCY_HANDOVER_DATE", length = 7)
	private Date tenancyHandoverDate;

	@Column(name = "PROPERTY_BILLABLE", length = 1)
	private String propertyBillable;

	@Column(name = "NO_OF_PARKINGSLOTS", precision = 3, scale = 0)
	private int no_of_ParkingSlots;
	// private int dr_Group_Id;
	@Column(name = "PROJECT_ID", precision = 11, scale = 0)
	private int projectId;

	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="DR_GROUP_ID", precision=11, scale=0)
    private int drGroupId;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;

	/*@Column(name = "BLOCK_ID")
	private int blockId;*/
	
	@Column(name = "AREA_TYPE")
	private String areaType;
	
	@Column(name = "AREA")
	private int area;
	
	@Lob
    @Column(name="PROPERTY_DOC")
	private Blob propertyDocument;
	
	@Column(name="PROPERTY_DOC_TYPE")
	private String propertyDocumentType;
	
	@OneToOne
	@JoinColumn(name = "PROJECT_ID", insertable = false, updatable = false, nullable = false)
	private Project project;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BLOCK_ID",insertable = false, updatable = false, nullable = false)	
	private Blocks blocks;

	// private Timestamp lastUpdatedDt;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Property() {
		// TODO Auto-generated constructor stub
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Column(name = "PROPERTY_TYPE", length = 45)
	private String propertyType;

	public Blocks getBlocks() {
		return blocks;
	}

	public void setBlocks(Blocks blocks) {
		this.blocks = blocks;
	}
	
	
	
	
	public Property(int propertyId, String propertyType, String property_No,
			String propertyName, int property_Floor, String status,
			Date tenancyHandoverDate, String propertyBillable,
			int no_of_ParkingSlots, int projectId, String createdBy,
			String lastUpdatedBy) {
		super();
		this.propertyId = propertyId;
		this.propertyType = propertyType;
		this.property_No = property_No;
		this.propertyName = propertyName;
		this.property_Floor = property_Floor;
		this.status = status;
		this.tenancyHandoverDate = tenancyHandoverDate;
		this.propertyBillable = propertyBillable;
		this.no_of_ParkingSlots = no_of_ParkingSlots;
		this.projectId = projectId;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getProperty_Floor() {
		return property_Floor;
	}

	public void setProperty_Floor(int property_Floor) {
		this.property_Floor = property_Floor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTenancyHandoverDate() {
		return tenancyHandoverDate;
	}

	public void setTenancyHandoverDate(Date tenancyHandoverDate) {
		this.tenancyHandoverDate = tenancyHandoverDate;
	}

	public String getPropertyBillable() {
		return propertyBillable;
	}

	public void setPropertyBillable(String propertyBillable) {
		this.propertyBillable = propertyBillable;
	}

	public int getNo_of_ParkingSlots() {
		return no_of_ParkingSlots;
	}

	public void setNo_of_ParkingSlots(int no_of_ParkingSlots) {
		this.no_of_ParkingSlots = no_of_ParkingSlots;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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

	/*public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}*/

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public Blob getPropertyDocument() {
		return propertyDocument;
	}

	public void setPropertyDocument(Blob propertyDocument) {
		this.propertyDocument = propertyDocument;
	}

	public String getPropertyDocumentType() {
		return propertyDocumentType;
	}

	public void setPropertyDocumentType(String propertyDocumentType) {
		this.propertyDocumentType = propertyDocumentType;
	}
	
	public int getDrGroupId() {
		return drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
}