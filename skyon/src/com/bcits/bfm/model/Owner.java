package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
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

import com.bcits.bfm.util.SessionData;

/**
 * Entity which includes attributes and their getters and setters mapping with 'OWNER' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "OWNER")
@NamedQueries({
	@NamedQuery(name="Owner.getOwnerIdOnPersonId",query="SELECT o.ownerId FROM Owner o WHERE o.personId = :personId"),
	
	@NamedQuery(name = "Owner.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, o.ownerStatus FROM Person p, Owner o WHERE p.personId = o.personId ORDER BY o.ownerId DESC"),
	@NamedQuery(name = "Owner.setOwnerStatus", query = "UPDATE Owner o SET o.ownerStatus = :ownerStatus, o.lastUpdatedBy = :lastUpdatedBy, o.lastUpdatedDt = :lastUpdatedDt WHERE o.personId = :personId"),
	@NamedQuery(name = "Owner.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, o.ownerId FROM Person p INNER JOIN p.owner o WHERE o.ownerStatus LIKE 'Active'"),
    @NamedQuery(name="OwnerProp.ownerId",query="SELECT o.personId FROM Owner o WHERE o.ownerId=:ownerId"),
    @NamedQuery(name="Owners.AllPersonIds",query="select o.personId from Owner o"),
    @NamedQuery(name="Owner.ownerDetails",query="SELECT count(*),(select count(*)  from Owner u WHERE u.ownerStatus='Active'),(select count(*)  from Owner p WHERE p.ownerStatus='Inactive') FROM Owner dr group by 1"),
    @NamedQuery(name="Owner.getCo_OwnerDetails",query="SELECT p.firstName,p.middleName,p.lastName FROM Person p,Owner ow,OwnerProperty o where o.propertyId=:propertyId and o.ownerId=ow.ownerId and ow.personId=p.personId and o.primaryOwner='No'"),
})
public class Owner 
{
	// Fields

	private int ownerId;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private int personId;
	private String ownerStatus;
	private Person person;
	
	// Property accessors
	/**
	 * Gets the Id of this owner.
	 * 
	 * @return This owner Id.
	 * @since 0.1
	 */
	@Id
	@SequenceGenerator(name = "ownerSeq", sequenceName = "OWNER_SEQ")
	@GeneratedValue(generator = "ownerSeq")
	@Column(name = "OWNER_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getOwnerId() 
	{
		return this.ownerId;
	}

	/**
	 * Changes the Id of this owner.
	 * 
	 * @param ownerId
	 *            This owner new Id.
	 * @since 0.1
	 */
	public void setOwnerId(int ownerId) 
	{
		this.ownerId = ownerId;
	}

	/**
	 * Gets this owner created by user name.
	 * 
	 * @return Created by user name of this owner.
	 * @since 0.1
	 */
	@Column(name = "CREATED_BY", length = 50)
	public String getCreatedBy() 
	{
		return this.createdBy;
	}

	/**
	 * Changes the user name who created this owner.
	 * 
	 * @param createdBy
	 *            New user name who created this owner.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets this owner last updated by user name.
	 * 
	 * @return Last updated by user name of this owner.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_BY", length = 50)
	public String getLastUpdatedBy() 
	{
		return this.lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this owner.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this owner.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets this owner last updated date time.
	 * 
	 * @return Last updated date time of this owner.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this owner.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this owner.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	/**
	 * Gets this owner person id.
	 * 
	 * @return Person id of this owner.
	 * @since 0.1
	 */
	@Column(name = "PERSON_ID", insertable = false, updatable = false)
	public int getPersonId() {
		return personId;
	}

	/**
	 * Changes the person id of this owner.
	 * 
	 * @param personId
	 *            New person id of this owner.
	 * @since 0.1
	 */
	public void setPersonId(int personId) {
		this.personId = personId;
	}	
	
	/**
	 * Gets the person id of this owner.
	 * 
	 * @return Person id of this owner.
	 * @since 0.1
	 */
	@OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
		return this.person;
	}

	/**
	 * Changes the person id of this owner.
	 * 
	 * @param personId
	 *            New person id of this owner.
	 * @since 0.1
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Gets this owner status.
	 * 
	 * @return Status of this owner.
	 * @since 0.1
	 */
	@Column(name = "STATUS")
	public String getOwnerStatus()
	{
		return ownerStatus;
	}
	
	/**
	 * Changes the status of this owner.
	 * 
	 * @param ownerStatus
	 *            New status of this owner.
	 * @since 0.1
	 */
	public void setOwnerStatus(String ownerStatus)
	{
		this.ownerStatus = ownerStatus;
	}
	
	/**
	 * Sets the values for the following fields before saving the new record.
	 * 
	 * @since 0.1
	 */
	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	    ownerStatus = "Inactive";
	}

	/**
	 * Sets the values for the following fields before updating the record.
	 * 
	 * @since 0.1
	 */
	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
}