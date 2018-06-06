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
 * Entity which includes attributes and their getters and setters mapping with 'TENANT' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "TENANT")
@NamedQueries({ 
	@NamedQuery(name = "Tenant.getPersonStyle", query = "SELECT p.personStyle FROM Person p,Tenant t WHERE p.personType LIKE :personType AND p.personId = t.personId "),
	@NamedQuery(name = "Tenant.getPersonTitle", query = "SELECT p.title FROM Person p ,Tenant t WHERE p.personType LIKE :personType AND p.personId = t.personId "),
	@NamedQuery(name = "Tenant.getPersonFirstName", query = "SELECT p.firstName FROM Person p ,Tenant t WHERE p.personType LIKE :personType AND p.personId = t.personId "),
	@NamedQuery(name = "Tenant.getPersonMiddleName", query = "SELECT p.middleName FROM Person p ,Tenant t WHERE p.personType LIKE :personType AND p.personId = t.personId "),
	@NamedQuery(name = "Tenant.getPersonLastName", query = "SELECT p.lastName FROM Person p ,Tenant t WHERE p.personType LIKE :personType AND p.personId = t.personId "),
	@NamedQuery(name = "Tenant.getLanguage", query = "SELECT p.languagesKnown FROM Person p ,Tenant t WHERE p.personType LIKE :personType AND p.personId = t.personId "),
	@NamedQuery(name="Tenant.getAllTenantDetails",query="SELECT p FROM Tenant t ,Person p WHERE p.personId = t.personId "),
	@NamedQuery(name="Tenant.getTenantIdByInstanceOfPersonId",query="SELECT t.tenantId FROM Tenant t WHERE t.personId = :personId"),
	@NamedQuery(name="Tenant.getTenantIdBasedOnPersonId",query="SELECT t.tenantId FROM Tenant t WHERE t.personId = :personId"),
	@NamedQuery(name = "Tenant.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, t.tenantStatus FROM Person p, Tenant t WHERE p.personId = t.personId ORDER BY t.tenantId DESC"),
	@NamedQuery(name = "Tenant.setTenantStatus", query = "UPDATE Tenant t SET t.tenantStatus = :tenantStatus, t.lastUpdatedBy = :lastUpdatedBy, t.lastUpdatedDt = :lastUpdatedDt WHERE t.personId = :personId"),
	@NamedQuery(name = "Tenant.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, t.tenantId FROM Person p INNER JOIN p.tenant t WHERE t.tenantStatus LIKE 'Active'"),
    @NamedQuery(name="TenantBasedOn.property",query="select t.personId from Tenant t where t.tenantId=:tenantId"),
    @NamedQuery(name="Tenant.getContactDetailsByPersonId",query=" Select Distinct(c.contactId), c.contactType, c.contactContent From Contact c, TenantProperty tp, Tenant t Where c.personId=t.personId and t.tenantId=tp.tenantId and tp.propertyId=:propertyId"),
    @NamedQuery(name="Tenant.getTenantStatusByPropertyId",query="SELECT t.tenantStatus From Tenant t,TenantProperty tp Where t.tenantId= tp.tenantId and tp.propertyId=:propertyId"),
    @NamedQuery(name="Tenant.getFirstNameByPersonId",query="SELECT p.firstName From Person p,Tenant t,TenantProperty tp Where p.personId=t.personId and t.tenantId= tp.tenantId and tp.propertyId=:propertyId"),

	
	
	
    @NamedQuery(name="Tenant.getContactDetailsByPersonId1",query=" Select Distinct(c.contactId), c.contactType, c.contactContent From Contact c, TenantProperty tp, Tenant t Where c.personId=t.personId and t.tenantId=tp.tenantId and tp.propertyId=:propertyId"),
    @NamedQuery(name="Tenant.getTenantStatusByPropertyId1",query="SELECT t.tenantStatus From Tenant t,TenantProperty tp Where t.tenantId= tp.tenantId and tp.propertyId=:propertyId"),
    @NamedQuery(name="Tenant.getFirstNameByPersonId1",query="SELECT p.firstName From Person p,Tenant t,TenantProperty tp Where p.personId=t.personId and t.tenantId= tp.tenantId and tp.propertyId=:propertyId"),

    



})
public class Tenant
{
	private int tenantId;
	private int personId;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String tenantStatus;
	private Person person;

	/**
	 * Gets the Id of this tenant.
	 * 
	 * @return This tenant Id.
	 * @since 0.1
	 */
	@Id
	@SequenceGenerator(name = "tenantSeq", sequenceName = "TENANT_SEQ")
	@GeneratedValue(generator = "tenantSeq")
	@Column(name = "TENANT_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getTenantId() 
	{
		return tenantId;
	}

	/**
	 * Changes the Id of this tenant.
	 * 
	 * @param tenantId
	 *            This tenant new Id.
	 * @since 0.1
	 */
	public void setTenantId(int tenantId) 
	{
		this.tenantId = tenantId;
	}

	/**
	 * Gets this tenant person id.
	 * 
	 * @return Person id of this tenant.
	 * @since 0.1
	 */
	@Column(name = "PERSON_ID", insertable = false, updatable = false)
	public int getPersonId() 
	{
		return personId;
	}

	/**
	 * Changes the person id of this tenant.
	 * 
	 * @param personId
	 *            New person id of this tenant.
	 * @since 0.1
	 */
	public void setPersonId(int personId) 
	{
		this.personId = personId;
	}

	/**
	 * Gets this tenant created by user name.
	 * 
	 * @return Created by user name of this tenant.
	 * @since 0.1
	 */
	@Column(name = "CREATED_BY")
	public String getCreatedBy() 
	{
		return createdBy;
	}

	/**
	 * Changes the user name who created this tenant.
	 * 
	 * @param createdBy
	 *            New user name who created this tenant.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets this tenant last updated by user name.
	 * 
	 * @return Last updated by user name of this tenant.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy() 
	{
		return lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this tenant.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this tenant.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets this tenant last updated date time.
	 * 
	 * @return Last updated date time of this tenant.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt() 
	{
		return lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this tenant.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this tenant.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}

	/**
	 * Gets the person id of this tenant.
	 * 
	 * @return Person id of this tenant.
	 * @since 0.1
	 */
	@OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() 
	{
		return this.person;
	}

	/**
	 * Changes the person id of this tenant.
	 * 
	 * @param personId
	 *            New person id of this tenant.
	 * @since 0.1
	 */
	public void setPerson(Person person) 
	{
		this.person = person;
	}
	
	/**
	 * Gets this tenant status.
	 * 
	 * @return Status of this tenant.
	 * @since 0.1
	 */
	@Column(name = "STATUS")
	public String getTenantStatus()
	{
		return tenantStatus;
	}
	
	/**
	 * Changes the status of this tenant.
	 * 
	 * @param tenantStatus
	 *            New status of this tenant.
	 * @since 0.1
	 */
	public void setTenantStatus(String tenantStatus)
	{
		this.tenantStatus = tenantStatus;
	}		
}
