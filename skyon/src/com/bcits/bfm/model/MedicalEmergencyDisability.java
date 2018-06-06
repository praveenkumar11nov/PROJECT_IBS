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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity which includes attributes and their getters and setters mapping with 'MEDICAL_EMERGENCY_DISABILITY' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "MEDICAL_EMERGENCY_DISABILITY")
@NamedQueries({
		@NamedQuery(name = "MedicalEmergencyDisability.findAllMedicalEmergencyDisabilityBasedOnPersonID", query = "SELECT med FROM MedicalEmergencyDisability med WHERE med.personId = :personId ORDER BY med.meId DESC"),
		@NamedQuery(name = "MedicalEmergencyDisability.getMedicalEmergencyDisabilityIdBasedOnCreatedByAndLastUpdatedDt", query = "SELECT med.meId FROM MedicalEmergencyDisability med WHERE med.createdBy = :createdBy AND med.lastUpdatedDt = :lastUpdatedDt")
})
public class MedicalEmergencyDisability
{
	private int meId;
	private int personId;
	private String meCategory;
	private String disabilityType;
	private String description;
	private String meHospitalName;
	private String meHospitalContact;
	private String meHospitalAddress;	
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	/**
	 * Gets the Id of this medical emergency disability.
	 * 
	 * @return This medical emergency disability Id.
	 * @since 0.1
	 */
	@Id
	@Column(name = "ME_ID")
	@SequenceGenerator(name = "medicalEmergencyDiaabilitySeq", sequenceName = "MEDICAL_EMERGENCY_SEQ")
	@GeneratedValue(generator = "medicalEmergencyDiaabilitySeq")
	public int getMeId()
	{
		return meId;
	}
	
	/**
	 * Changes the Id of this medical emergency disability.
	 * 
	 * @param armsId
	 *            This medical emergency disability new Id.
	 * @since 0.1
	 */
	public void setMeId(int meId)
	{
		this.meId = meId;
	}
	
	/**
	 * Gets the person Id whom this medical emergency disability belongs to.
	 * 
	 * @return Person Id whom this medical emergency disability belongs to.
	 * @since 0.1
	 */
	@Column(name = "PERSON_ID", insertable=false, updatable=false)
	public int getPersonId()
	{
		return personId;
	}
	
	/**
	 * Changes the person id whom this medical emergency disability belongs to.
	 * 
	 * @param personId
	 *            New person Id whom this medical emergency disability belongs to.
	 * @since 0.1
	 */
	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	/**
	 * Gets the category of this medical emergency disability.
	 * 
	 * @return Category of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(min = 1, message = "{Size.MedicalEmergencyDisability.meCategory}")
	@Column(name = "ME_CATEGORY")
	public String getMeCategory()
	{
		return meCategory;
	}
	
	/**
	 * Changes the category of this medical emergency disability.
	 * 
	 * @param meCategory
	 *            New category of this medical emergency disability.
	 * @since 0.1
	 */
	public void setMeCategory(String meCategory)
	{
		this.meCategory = meCategory;
	}
	
	/**
	 * Gets the disability type of this medical emergency disability.
	 * 
	 * @return Disability type of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(max = 45, message = "{Size.MedicalEmergencyDisability.disabilityType}")
	@Pattern( regexp = "^[a-zA-Z0-9 ]{0,45}$", message = "{Pattern.MedicalEmergencyDisability.disabilityType}")
	@Column(name = "DISABILITY_TYPE")
	public String getDisabilityType()
	{
		return disabilityType;
	}
	
	/**
	 * Changes the disability type of this medical emergency disability.
	 * 
	 * @param disabilityType
	 *            New disability type of this medical emergency disability.
	 * @since 0.1
	 */
	public void setDisabilityType(String disabilityType)
	{
		this.disabilityType = disabilityType;
	}
	
	/**
	 * Gets the description of this medical emergency disability.
	 * 
	 * @return Description of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(max = 225, message = "{Size.MedicalEmergencyDisability.description}")
	/*@Pattern( regexp = "^[a-zA-Z0-9 ]{0,225}$", message = "{Pattern.MedicalEmergencyDisability.description}")*/
	@Column(name = "DESCRIPTION")
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Changes the description of this medical emergency disability.
	 * 
	 * @param description
	 *            New description of this medical emergency disability.
	 * @since 0.1
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * Gets the hospital name of this medical emergency disability.
	 * 
	 * @return Hospital name of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(max = 45, message = "{Size.MedicalEmergencyDisability.meHospitalName}")
	@Pattern( regexp = "^[a-zA-Z0-9 ]{0,45}$", message = "{Pattern.MedicalEmergencyDisability.meHospitalName}")
	@Column(name = "ME_HOSPITAL_NAME")
	public String getMeHospitalName()
	{
		return meHospitalName;
	}

	/**
	 * Changes the hospital name of this medical emergency disability.
	 * 
	 * @param meHospitalName
	 *            New hospital name of this medical emergency disability.
	 * @since 0.1
	 */
	public void setMeHospitalName(String meHospitalName)
	{
		this.meHospitalName = meHospitalName;
	}

	/**
	 * Gets the hospital contact of this medical emergency disability.
	 * 
	 * @return Hospital contact of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(max = 225, message = "{Size.MedicalEmergencyDisability.meHospitalContact}")
	@Pattern( regexp = "^[0-9,+ ]{0,225}$", message = "{Pattern.MedicalEmergencyDisability.meHospitalContact}")
	@Column(name = "ME_HOSPITAL_CONTACT")
	public String getMeHospitalContact()
	{
		return meHospitalContact;
	}

	/**
	 * Changes the hospital contact of this medical emergency disability.
	 * 
	 * @param meHospitalContact
	 *            New hospital contact of this medical emergency disability.
	 * @since 0.1
	 */
	public void setMeHospitalContact(String meHospitalContact)
	{
		this.meHospitalContact = meHospitalContact;
	}

	/**
	 * Gets the hospital address of this medical emergency disability.
	 * 
	 * @return Hospital address of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(max = 225, message = "{Size.MedicalEmergencyDisability.meHospitalAddress}")
	@Column(name = "ME_HOSPITAL_ADDRESS")
	public String getMeHospitalAddress()
	{
		return meHospitalAddress;
	}

	/**
	 * Changes the hospital address of this medical emergency disability.
	 * 
	 * @param meHospitalAddress
	 *            New hospital address of this medical emergency disability.
	 * @since 0.1
	 */
	public void setMeHospitalAddress(String meHospitalAddress)
	{
		this.meHospitalAddress = meHospitalAddress;
	}


	/**
	 * Gets this medical emergency disability created by user name.
	 * 
	 * @return Created by user name of this medical emergency disability.
	 * @since 0.1
	 */
	@Size(min = 1, message = "{Size.MedicalEmergencyDisability.createdBy}")
	@Column(name = "CREATED_BY")
	public String getCreatedBy() 
	{
		return this.createdBy;
	}

	/**
	 * Changes the user name who created this medical emergency disability.
	 * 
	 * @param createdBy
	 *            New user name who created this medical emergency disability.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets this medical emergency disability last updated by user name.
	 * 
	 * @return Last updated by user name of this medical emergency disability
	 * @since 0.1
	 */
	@Size(min = 1, message = "{Size.MedicalEmergencyDisability.lastUpdatedBy}")
	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return this.lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this medical emergency disability.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this medical emergency disability.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	/**
	 * Gets this medical emergency disability last updated date time.
	 * 
	 * @return Last updated date time of this medical emergency disability.
	 * @since 0.1
	 */
	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}
	
	/**
	 * Changes the last updated date time this medical emergency disability.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this medical emergency disability.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}
}
