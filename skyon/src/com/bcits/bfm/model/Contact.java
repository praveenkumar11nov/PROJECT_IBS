package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Contact entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CONTACT")
@NamedQueries({
	@NamedQuery(name="Contact.findAll",query="SELECT c FROM Contact c"),
	@NamedQuery(name = "Contact.findAllContactsBasedOnPersonID", query = "SELECT c FROM Contact c WHERE c.personId = :personId ORDER BY c.contactId DESC"),
	@NamedQuery(name = "Contact.getContactIdBasedOnCreatedByAndLastUpdatedDt", query = "SELECT c.contactId FROM Contact c WHERE c.createdBy = :createdBy AND c.lastUpdatedDt = :lastUpdatedDt"),
	@NamedQuery(name="Contact.Content",query="select c.contactContent from Contact c where c.personId=:personId "),
	@NamedQuery(name="UpadteContactContent",query="update Contact c set c.contactContent=:contactContent where c.contactId=:contactId"),
	@NamedQuery(name="Contact.ContactId",query="select c.contactId from Contact c where c.personId=:personId "),
	@NamedQuery(name = "Contact.checkForUniquePrimary", query = "SELECT c.contactId From Contact c WHERE c.personId = :personId and c.contactType =:contactType and c.contactPrimary =:contactPrimary"),
	@NamedQuery(name = "Contact.getContactPrimary", query = "SELECT c.contactPrimary From Contact c WHERE c.contactId = :contactId and c.contactType =:contactType"),
	@NamedQuery(name = "Contact.getContactsOnAddressId", query = "SELECT c From Contact c WHERE c.addressId= :addressId"),
	@NamedQuery(name = "Contact.updateAddressiIdContact", query = "UPDATE Contact c SET c.addressId =0 WHERE c.addressId =:addressId"),
	@NamedQuery(name = "Contact.getAllContactContent", query = "SELECT c.contactContent FROM Contact c "),
	@NamedQuery(name = "Contact.getContactBasedOnPersonId", query = "SELECT c FROM Contact C WHERE c.personId =:personId"),
	@NamedQuery(name="CustomerOrder.findEmail",query="SELECT a FROM Contact a WHERE a.contactContent=:username"),
	@NamedQuery(name = "Contact.getContactEmailBasedOnPersonId", query = "SELECT c FROM Contact C WHERE c.personId =:personId AND  c.contactPrimary='Yes' AND c.contactType='Email'"),
	@NamedQuery(name = "Contact.getContactPhoneBasedOnPersonId", query = "SELECT b FROM Contact b WHERE b.personId =:personId AND    b.contactPrimary='Yes' AND b.contactType='Mobile'"),
	@NamedQuery(name = "Contact.getContactInformation", query = "SELECT b.blockName,pt.propertyId,pt.property_No,p.personId FROM Contact c,Person p,OwnerProperty op INNER JOIN p.owner o INNER JOIN op.property pt INNER JOIN pt.blocks b WHERE o.ownerId=op.ownerId AND c.personId=p.personId AND c.contactPrimary='Yes' AND c.contactType='Email' AND c.contactContent=:user"),
	@NamedQuery(name="Contact.getContactListBasedOnContactContent",query="SELECT c FROM Contact c WHERE c.contactContent=:contactContent"),
	@NamedQuery(name="Contact.Content2",query="select c from Contact c where c.personId=:personId and c.contactPrimary='Yes'"),
	

})
public class Contact implements java.io.Serializable {

	// Fields

	private int contactId;
	private int personId;
	private int addressId;
	private String contactLocation;
	private String contactType;
	private String contactPrimary;
	private String contactContent;
	private String contactPreferredTime;
	private String contactSeason;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private Timestamp contactSeasonFrom;
	private Timestamp contactSeasonTo;

	// Constructors

	/** default constructor */
	public Contact() {
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "contactSeq", sequenceName = "CONTACT_SEQ")
	@GeneratedValue(generator = "contactSeq")
	@Column(name = "CONTACT_ID")
	public int getContactId() {
		return this.contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	@Column(name = "PERSON_ID", insertable=false, updatable=false)
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	
	@Column(name = "ADDRESS_ID")
	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	
	@NotEmpty(message="'Location' is not selected")
	@Column(name = "CONTACT_LOCATION", length = 45)
	public String getContactLocation() {
		return this.contactLocation;
	}

	public void setContactLocation(String contactLocation) {
		this.contactLocation = contactLocation;
	}

	@Column(name = "CONTACT_TYPE", nullable = false, length = 45)
	@NotEmpty(message = "'Contact Type' is required")
	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Column(name = "CONTACT_PRIMARY", length = 45)
	@NotEmpty(message = "'Contact Primary' is not Selected")
	public String getContactPrimary() {
		return this.contactPrimary;
	}

	public void setContactPrimary(String contactPrimary) {
		this.contactPrimary = contactPrimary;
	}

	
	@NotNull(message = "'Contact Content' is required")
	@Size(min = 1, max = 45, message = "Contact content can have maximum {max} letters")
	@Column(name = "CONTACT_CONTENT", length = 45)
	public String getContactContent() {
		return this.contactContent;
	}

	public void setContactContent(String contactContent) {
		this.contactContent = contactContent;
	}

	@Column(name = "CONTACT_PREFERRED_TIME", length = 50)
	public String getContactPreferredTime() {
		return this.contactPreferredTime;
	}

	public void setContactPreferredTime(String contactPreferredTime) {
		this.contactPreferredTime = contactPreferredTime;
	}

	@Column(name = "CONTACT_SEASON", length = 45)
	public String getContactSeason() {
		return this.contactSeason;
	}

	public void setContactSeason(String contactSeason) {
		this.contactSeason = contactSeason;
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
	
	
	@Column(name="CONTACT_SEASON_FROM")
	public Timestamp getContactSeasonFrom()
	{
		return contactSeasonFrom;
	}

	public void setContactSeasonFrom(Timestamp contactSeasonFrom)
	{
		this.contactSeasonFrom = contactSeasonFrom;
	}

	@Column(name="CONTACT_SEASON_TO")
	public Timestamp getContactSeasonTo()
	{
		return contactSeasonTo;
	}

	public void setContactSeasonTo(Timestamp contactSeasonTo)
	{
		this.contactSeasonTo = contactSeasonTo;
	}
	
	
	
	
	@Override
	public int hashCode(){
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(this.contactContent);
	    buffer.append(this.contactId);
	    buffer.append(this.contactLocation);
	    buffer.append(this.contactPreferredTime);
	    buffer.append(this.contactPrimary);
	    buffer.append(this.contactSeason);
	    buffer.append(this.contactType);
	    buffer.append(this.personId);
	    buffer.append(this.createdBy);
	    buffer.append(this.lastUpdatedBy);
	    buffer.append(this.lastUpdatedDt);
	    return buffer.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass())return false;
	    Contact contact = (Contact)object;
	    if(this.hashCode()== contact.hashCode())return true;
	   return false;
	}

}