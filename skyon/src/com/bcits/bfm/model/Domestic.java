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
 * Entity which includes attributes and their getters and setters mapping with 'DOMESTIC_HELP' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "DOMESTIC_HELP")
@NamedQueries({ 
	@NamedQuery(name = "Domestic.getPersonStyle", query = "SELECT p.personStyle FROM Person p,Domestic d WHERE p.personType LIKE :personType AND p.personId = d.personId "),
	@NamedQuery(name = "Domestic.getPersonTitle", query = "SELECT p.title FROM Person p ,Domestic d WHERE p.personType LIKE :personType AND p.personId = d.personId "),
	@NamedQuery(name = "Domestic.getPersonFirstName", query = "SELECT p.firstName FROM Person p ,Domestic d WHERE p.personType LIKE :personType AND p.personId = d.personId "),
	@NamedQuery(name = "Domestic.getPersonMiddleName", query = "SELECT p.middleName FROM Person p ,Domestic d WHERE p.personType LIKE :personType AND p.personId = d.personId "),
	@NamedQuery(name = "Domestic.getPersonLastName", query = "SELECT p.lastName FROM Person p ,Domestic d WHERE p.personType LIKE :personType AND p.personId = d.personId "),
	@NamedQuery(name = "Domestic.getLanguage", query = "SELECT p.languagesKnown FROM Person p ,Domestic d WHERE p.personType LIKE :personType AND p.personId = d.personId "),
	@NamedQuery(name="Domestic.getAllDomesticDetails",query="SELECT p FROM Domestic d ,Person p WHERE p.personId = d.personId "),
	@NamedQuery(name = "Domestic.getDomesticIdByInstanceOfPersonId", query = "SELECT d.domesticId FROM Domestic d WHERE d.personId=:personId"),
	@NamedQuery(name="Domestic.getDometsicIdBasedOnPersonId",query="SELECT d.domesticId FROM Domestic d WHERE d.personId = :personId"),
	@NamedQuery(name="Domestic.getDomesticIdBasedOnPersonId",query="SELECT d.domesticId FROM Domestic d WHERE d.personId = :personId"),
	@NamedQuery(name = "Domestic.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, d.domesticHelpStatus FROM Person p, Domestic d WHERE p.personId = d.personId ORDER BY d.domesticId DESC"),		
	@NamedQuery(name = "Family.setFamilyStatus", query = "UPDATE Family f SET f.familyStatus = :familyStatus WHERE f.personId = :personId"),
	@NamedQuery(name = "Domestic.setDomesticHelpStatus", query = "UPDATE Domestic d SET d.domesticHelpStatus = :domesticHelpStatus, d.lastUpdatedBy = :lastUpdatedBy, d.lastUpdatedDt = :lastUpdatedDt WHERE d.personId = :personId"),
	@NamedQuery(name = "Domestic.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, d.domesticId FROM Person p INNER JOIN p.domesticHelp d WHERE d.domesticHelpStatus LIKE 'Active'"),
})

public class Domestic
{
	private int domesticId;
	private int personId;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String domesticHelpStatus;
	private Person person;
	
	// Property accessors
	
	/**
	 * Gets the Id of this domestic help.
	 * 
	 * @return This domestic help Id.
	 * @since 0.1
	 */
	@Id
	@SequenceGenerator(name = "domesticSeq", sequenceName = "DOMESTIC_SEQ")
	@GeneratedValue(generator = "domesticSeq")
	@Column(name = "DH_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getDomesticId() 
	{
		return domesticId;
	}

	/**
	 * Changes the Id of this domestic help.
	 * 
	 * @param domesticId
	 *            This domestic help new Id.
	 * @since 0.1
	 */
	public void setDomesticId(int domesticId) 
	{
		this.domesticId = domesticId;
	}

	/**
	 * Gets this domestic help created by user name.
	 * 
	 * @return Created by user name of this domestic help.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}
	
	/**
	 * Changes the user name who created this domestic help.
	 * 
	 * @param createdBy
	 *            New user name who created this domestic help.
	 * @since 0.1
	 */
	@Column(name = "CREATED_BY", length = 50)
	public String getCreatedBy()
	{
		return this.createdBy;
	}

	/**
	 * Gets this domestic help last updated by user name.
	 * 
	 * @return Last updated by user name of this domestic help.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_BY", length = 50)
	public String getLastUpdatedBy() 
	{
		return this.lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this domestic help.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this domestic help.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets this domestic help last updated date time.
	 * 
	 * @return Last updated date time of this domestic help.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt()
	{
		return this.lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this domestic help.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this domestic help.
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
	 * @return Person id of this domestic help.
	 * @since 0.1
	 */
	@OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson()
	{
		return this.person;
	}

	/**
	 * Changes the person id of this domestic help.
	 * 
	 * @param personId
	 *            New person id of this domestic help.
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
	public String getDomesticHelpStatus()
	{
		return domesticHelpStatus;
	}
	
	/**
	 * Changes the status of this domestic help.
	 * 
	 * @param domesticHelpStatus
	 *            New status of this domestic help.
	 * @since 0.1
	 */
	public void setDomesticHelpStatus(String domesticHelpStatus)
	{
		this.domesticHelpStatus = domesticHelpStatus;
	}		
}
