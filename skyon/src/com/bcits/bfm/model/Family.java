package com.bcits.bfm.model;

import java.sql.Timestamp;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity which includes attributes and their getters and setters mapping with 'FAMILY' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "FAMILY")
@NamedQueries({ 
	@NamedQuery(name = "Family.getPersonStyle", query = "SELECT p.personStyle FROM Person p,Family f WHERE p.personType LIKE :personType AND p.personId = f.personId "),
	@NamedQuery(name = "Family.getPersonTitle", query = "SELECT p.title FROM Person p ,Family f WHERE p.personType LIKE :personType AND p.personId = f.personId "),
	@NamedQuery(name = "Family.getPersonFirstName", query = "SELECT p.firstName FROM Person p ,Family f WHERE p.personType LIKE :personType AND p.personId = f.personId "),
	@NamedQuery(name = "Family.getPersonMiddleName", query = "SELECT p.middleName FROM Person p ,Family f WHERE p.personType LIKE :personType AND p.personId = f.personId "),
	@NamedQuery(name = "Family.getPersonLastName", query = "SELECT p.lastName FROM Person p ,Family f WHERE p.personType LIKE :personType AND p.personId = f.personId "),
	@NamedQuery(name = "Family.getLanguage", query = "SELECT p.languagesKnown FROM Person p ,Family f WHERE p.personType LIKE :personType AND p.personId = f.personId "),
	@NamedQuery(name = "Family.getAllFamilyDetails",query="SELECT p FROM Family f , Person p  WHERE p.personId = f.personId "),
	@NamedQuery(name = "Family.getFamilyIdByInstanceOfPersonId", query = "SELECT f.familyId FROM Family f WHERE f.personId=:personId"),
	@NamedQuery(name = "Family.getFamilyIdBasedOnPersonId",query="SELECT f.familyId FROM Family f WHERE f.personId = :personId"),
	@NamedQuery(name = "Family.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, f.familyStatus,f.familyId FROM Person p, Family f WHERE p.personId = f.personId ORDER BY f.familyId DESC"),
	@NamedQuery(name = "Family.setFamilyStatusFromPerson", query = "UPDATE Family f SET f.familyStatus = :familyStatus, f.lastUpdatedBy = :lastUpdatedBy, f.lastUpdatedDt = :lastUpdatedDt WHERE f.personId = :personId"),
	@NamedQuery(name = "Family.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, f.familyId FROM Person p INNER JOIN p.family f WHERE f.familyStatus LIKE 'Active'"),
})

public class Family
{
	// Fields
	private int familyId;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private int personId;
	private String familyStatus;
	private Person person;
	
	// Property accessors

	/**
	 * Gets the Id of this family.
	 * 
	 * @return This family Id.
	 * @since 0.1
	 */
	@Id
	@SequenceGenerator(name = "familySeq", sequenceName = "FAMILY_SEQ")
	@GeneratedValue(generator = "familySeq")
	@Column(name = "FAMILY_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getFamilyId() 
	{
		return familyId;
	}

	/**
	 * Changes the Id of this family.
	 * 
	 * @param familyId
	 *            This family new Id.
	 * @since 0.1
	 */
	public void setFamilyId(int familyId)
	{
		this.familyId = familyId;
	}

	/**
	 * Gets this family created by user name.
	 * 
	 * @return Created by user name of this domestic help.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	/**
	 * Changes the user name who created this family.
	 * 
	 * @param createdBy
	 *            New user name who created this family.
	 * @since 0.1
	 */
	@Column(name = "CREATED_BY", length = 50)
	public String getCreatedBy() 
	{
		return this.createdBy;
	}

	/**
	 * Gets this family last updated by user name.
	 * 
	 * @return Last updated by user name of this family.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_BY", length = 50)
	public String getLastUpdatedBy() 
	{
		return this.lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this family.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this family.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets this family last updated date time.
	 * 
	 * @return Last updated date time of this family.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() 
	{
		return this.lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this family.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this family.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	/**
	 * Gets this domestic help person id.
	 * 
	 * @return Person id of this domestic help.
	 * @since 0.1
	 */
	@Column(name = "PERSON_ID", insertable = false, updatable = false)
	public int getPersonId() 
	{
		return personId;
	}

	/**
	 * Changes the person id of this domestic help.
	 * 
	 * @param personId
	 *            New person id of this domestic help.
	 * @since 0.1
	 */
	public void setPersonId(int personId) 
	{
		this.personId = personId;
	}	

	/**
	 * Gets this domestic help person.
	 * 
	 * @return Person of this domestic help.
	 * @since 0.1
	 */
	@OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
		return this.person;
	}

	/**
	 * Changes the person of this domestic help.
	 * 
	 * @param person
	 *            New person of this domestic help.
	 * @since 0.1
	 */
	public void setPerson(Person person) 
	{
		this.person = person;
	}
	
	/**
	 * Gets this domestic help status.
	 * 
	 * @return Status of this domestic help.
	 * @since 0.1
	 */
	@Column(name = "STATUS")
	public String getFamilyStatus()
	{
		return familyStatus;
	}
	
	/**
	 * Changes the status of this domestic help.
	 * 
	 * @param familyStatus
	 *            New status of this domestic help.
	 * @since 0.1
	 */
	public void setFamilyStatus(String familyStatus)
	{
		this.familyStatus = familyStatus;
	}			
}
