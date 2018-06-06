package com.bcits.bfm.model;

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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table(name = "FAMILY_PROPERTY")

@NamedQueries({
	@NamedQuery(name="FamilyProperty.getAllPropertyOnPersonId",query="SELECT fp FROM FamilyProperty fp,Property p, Family f where f.familyId = fp.familyId and p.propertyId = fp.propertyId and f.personId = :personId"),
	@NamedQuery(name="FamilyProperty.getFamilyPropertyBasedOnId", query="SELECT fp FROM FamilyProperty fp WHERE fp.familyPropertyId=:familyPropertyId"),
	@NamedQuery(name="FamilyProperty.findAll", query="SELECT fp FROM FamilyProperty fp ORDER BY fp.familyPropertyId DESC"),
	@NamedQuery(name="FamilyProperty.getPropertyNoBasedOnOwners", query="SELECT p FROM Property p,TenantProperty tp,OwnerProperty op WHERE p.propertyId = op.propertyId OR p.propertyId = tp.propertyId "),
	@NamedQuery(name="FamilyProperty.getOwnerIdBasedOnFamPropertyId",query="SELECT fp.ownerId FROM FamilyProperty fp WHERE fp.familyPropertyId = :famPropertyId"),
	@NamedQuery(name="FamilyProperty.getProprtyIdBasedOnFamPropertyId",query="SELECT fp.propertyId FROM FamilyProperty fp WHERE fp.familyPropertyId = :famPropertyId"),
})

public class FamilyProperty implements java.io.Serializable {
	
	/** Class Fields */
	private int familyPropertyId;
	private int familyId;
	//private int propertyId;
	
	private String propertyNo;
	private Property property;
	
	private int propertyId;
	
	private String fpRelationship;
	private Timestamp startDate;
	private Timestamp endDate;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	//private int personId;
	
	private int ownerId;
	
	
	
	/** default constructor */
	public FamilyProperty() {
		super();
	}
	
	/** full constructor */

	public FamilyProperty(int familyPropertyId, int familyId,
			int propertyId, String fpRelationship, Timestamp startDate,
			Timestamp endDate, String status, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		super();
		this.familyPropertyId = familyPropertyId;
		this.familyId = familyId;
		this.propertyId = propertyId;
		this.fpRelationship = fpRelationship;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}



	/*
	 * Property accessors
	 */
	@Id
	@SequenceGenerator(name="familyPropertySeq",sequenceName="FAMILYPROPERTY_SEQ")
	@GeneratedValue(generator="familyPropertySeq")
	@Column(name = "FAMILY_PROPERTY_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getFamilyPropertyId() {
		return familyPropertyId;
	}
	
	public void setFamilyPropertyId(int familyPropertyId) {
		this.familyPropertyId = familyPropertyId;
	}
	
	@Column(name="FAMILY_ID")
	public int getFamilyId() {
		return familyId;
	}
	
	/*@Min(value = 1, message = "'Property number is not selected'")
	@Column(name="PROPERTY_ID")
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}*/
	
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

	public void setFamilyId(int familyId) {
		this.familyId = familyId;
	}

	@Column(name="FP_RELATIONSHIP")
	public String getFpRelationship() {
		return fpRelationship;
	}
	
	public void setFpRelationship(String fpRelationship) {
		this.fpRelationship = fpRelationship;
	}
	
	@Column(name="FP_START_DATE")
	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="FP_END_DATE")
	public Timestamp getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name="LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}
	
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	/*@Column(name="PERSON_ID")
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}*/
	@NotNull(message="'Owner name' is required")
	@Column(name="PERSON_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	
	@OneToOne
	@JoinColumn(name = "PROPERTY_ID",insertable=false,updatable=false)
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	
	
	

}
