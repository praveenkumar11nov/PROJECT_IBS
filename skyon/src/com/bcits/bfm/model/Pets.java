package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({
		@NamedQuery(name = "Pets.findAllPetsRequiredFields", query = "SELECT p.petId, p.petName, p.propertyId, p.drGroupId, p.petType, p.petStatus, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, pt.property_No,p.petSize,p.blockName,p.propertyId,p.breedName,p.petColor,p.petAge, p.petSex,p.typesOfVaccination,p.veterinarianName,p.veterinarianAddress,p.lastVaccinationDate,p.emergencyContact,pp.firstName,pp.lastName FROM Pets p INNER JOIN p.person pp, Property pt WHERE p.propertyId = pt.propertyId ORDER BY p.petId DESC"),
		@NamedQuery(name = "Pets.getPetIdBasedOnCreatedByAndLastUpdatedDt", query = "SELECT p.petId FROM Pets p WHERE p.lastUpdatedDt = :lastUpdatedDt AND p.createdBy = :createdBy"),
		@NamedQuery(name = "Pets.setPetStatus", query = "UPDATE Pets p SET p.petStatus = :petStatus WHERE p.petId = :petId"),
		@NamedQuery(name = "Pets.getPropertyNo", query = "SELECT DISTINCT(pt.property_No) FROM Pets p, Property pt WHERE p.propertyId = pt.propertyId"),
		//@NamedQuery(name = "Pets.getAllPetName", query = "SELECT DISTINCT p.petName FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllPetType", query = "SELECT DISTINCT(p.petType) FROM Pets p"),
		@NamedQuery(name = "Pets.getAllPropertyNumbers", query = "SELECT DISTINCT(pt.property_No) FROM Pets p, Property pt WHERE p.propertyId = pt.propertyId"),
		//@NamedQuery(name = "Pets.getAllPetSize", query = "SELECT DISTINCT(p.petSize) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllPetBreed", query = "SELECT DISTINCT(p.breedName) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllPetColor", query = "SELECT DISTINCT(p.petColor) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllPetAge", query = "SELECT DISTINCT(p.petAge) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllPetSex", query = "SELECT DISTINCT(p.petSex) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllUpdatedByNames", query = "SELECT DISTINCT(p.lastUpdatedBy) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllCreatedByNames", query = "SELECT DISTINCT(p.createdBy) FROM Pets p"),
		//@NamedQuery(name = "Pets.getAllVeterianame", query = "SELECT DISTINCT p.veterinarianName FROM Pets p"),
		@NamedQuery(name = "Pets.getAllBlockNames", query = "SELECT DISTINCT p.blockName FROM Pets p"),
		@NamedQuery(name = "Pets.getAllEmergencyContact", query = "SELECT DISTINCT(p.emergencyContact) FROM Pets p"),
		@NamedQuery(name = "Pet.findAll", query = "SELECT p FROM Pets p"),
		@NamedQuery(name="Pets.findAllPropertyPersonOwnerList",query="select op from OwnerProperty op INNER JOIN op.owner o INNER JOIN o.person p WHERE p.personStatus = 'Active'"),
		@NamedQuery(name="Pets.findAllPropertyPersonTenantList",query="select tp from TenantProperty tp INNER JOIN tp.tenantId t INNER JOIN t.person p WHERE p.personStatus = 'Active'"),
		@NamedQuery(name = "Pets.getPersonListForFileter", query = "select DISTINCT p.personId, p.firstName, p.lastName, p.personType, p.personStyle from Pets a INNER JOIN a.person p"),
		@NamedQuery(name = "Pets.getAllPetsBasedOnPropetyId", query = "SELECT p.petId, p.petName,p.breedName,p.petSize,p.petColor,p.petSex,p.petAge,p.emergencyContact,p.petStatus FROM Pets p WHERE p.propertyId = :propertyId ORDER BY p.petId DESC"),
		@NamedQuery(name = "Pets.findAllPets", query = "SELECT p FROM Pets p INNER JOIN p.person pp, Property pt WHERE p.propertyId = pt.propertyId ORDER BY p.petId DESC"),
})
@Table(name = "PETS")
public class Pets implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "petSeq", sequenceName = "PETS_SEQ")
	@GeneratedValue(generator = "petSeq")
	@Column(name = "PET_ID")
	private int petId;

	@Transient
	private String propertyNo;

	@Min(value = 1, message = "Property number is not selected")
	@Column(name = "PROPERTY_ID")
	private int propertyId;
	
	@Column(name = "DR_GROUP_ID")
	private int drGroupId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName="DR_GROUP_ID",name = "DR_GROUP_ID",insertable = false, updatable = false, nullable = false)
	private Person person;
	
	@Size(min = 1, max = 45, message = "Pet Name must have minimum {min} letter and maximum {max} letters")
	@Pattern(regexp = "^[a-zA-Z ]{1,45}$", message = "Pet Name can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed")
	@Column(name = "PET_NAME")
	private String petName;
	
	//@Size(min = 1, max = 45, message = "Pet type must have minimum {min} letter and maximum {max} letters")
	//@Pattern(regexp = "^[a-zA-Z ]{1,45}$", message = "Type of Pet can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed")
	@Column(name = "PET_TYPE")
	private String petType;

	@Column(name = "PET_SIZE")
	private String petSize;

	@NotEmpty(message = "Block name is not selected")
	@Column(name = "BLOCK_NAME")
	private String blockName;

	@Column(name = "PET_BREED")
	private String breedName;

	@Column(name = "PET_COLOR")
	private String petColor;

	@Column(name = "PET_AGE")
	private int petAge;

	@Column(name = "PET_SEX")
	private String petSex;

	@Past(message = "Date of Vaccination must be selected and should be in the past")
	@Column(name = "LAST_VACCINATION_DATE")
	private Date lastVaccinationDate;

	@Column(name = "TYPE_OF_VACCINATION")
	private String typesOfVaccination;

	@Column(name = "VETERINARIAN_NAME")
	private String veterinarianName;

	@Column(name = "VETERINARIAN_ADDRESS")
	private String veterinarianAddress;

	@Size(max = 10, message = "Emergency Contact Number maximum {max} numbers")
	@Column(name = "EMERGENCY_CONTACT")
	private String emergencyContact;

	@Column(name = "STATUS")
	private String petStatus;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;

	public Pets() {

	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public int getDrGroupId() {
		return drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
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

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getPetSize() {
		return petSize;
	}

	public void setPetSize(String petSize) {
		this.petSize = petSize;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getPetColor() {
		return petColor;
	}

	public void setPetColor(String petColor) {
		this.petColor = petColor;
	}

	public int getPetAge() {
		return petAge;
	}

	public void setPetAge(int petAge) {
		this.petAge = petAge;
	}

	public String getPetSex() {
		return petSex;
	}

	public void setPetSex(String petSex) {
		this.petSex = petSex;
	}

	public Date getLastVaccinationDate() {
		return lastVaccinationDate;
	}

	public void setLastVaccinationDate(Date lastVaccinationDate) {
		this.lastVaccinationDate = lastVaccinationDate;
	}

	public String getVeterinarianName() {
		return veterinarianName;
	}

	public void setVeterinarianName(String veterinarianName) {
		this.veterinarianName = veterinarianName;
	}

	public String getVeterinarianAddress() {
		return veterinarianAddress;
	}

	public void setVeterinarianAddress(String veterinarianAddress) {
		this.veterinarianAddress = veterinarianAddress;
	}

	public String getTypesOfVaccination() {
		return typesOfVaccination;
	}

	public void setTypesOfVaccination(String typesOfVaccination) {
		this.typesOfVaccination = typesOfVaccination;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
