package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity which includes attributes and their getters and setters mapping with 'ARMS' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Arms.findAllArmsBasedOnPersonID", query = "SELECT a FROM Arms a WHERE a.personId = :personId ORDER BY a.armsId DESC"),
	@NamedQuery(name = "Arms.getArmsIdBasedOnCreatedByAndLastUpdatedDt", query = "SELECT a.armsId FROM Arms a WHERE a.createdBy = :createdBy AND a.lastUpdatedDt = :lastUpdatedDt")
})
public class Arms
{
	@Id
	@Column(name = "ARMS_ID")
	@SequenceGenerator(name = "armsSeq", sequenceName = "ARMS_SEQ")
	@GeneratedValue(generator = "armsSeq")
	private int armsId;
	
	@Column(name = "PERSON_ID", insertable=false, updatable=false)
	private int personId;
	
	@Size(min = 1, max = 45, message = "{Size.Arms.typeOfArm}")
	@Pattern(regexp = "^[a-zA-Z0-9 ]{1,45}$", message = "{Pattern.Arms.typeOfArm}")
	@Column(name = "TYPE_OF_ARM")
	private String typeOfArm;
	
	@Size(max = 45, message = "{Size.Arms.armMake}")
	@Pattern(regexp = "^[a-zA-Z0-9 ]{0,45}$", message = "{Pattern.Arms.armMake}")
	@Column(name = "ARM_MAKE")
	private String armMake;
	
	@Size(max = 45, message = "{Size.Arms.licenceNo}")
	/*@Pattern(regexp = "^[a-zA-Z0-9]{0,45}$", message = "{Pattern.Arms.licenceNo}")*/
	@Column(name = "LICENCE_NO")
	private String licenceNo;
	
	@Column(name = "LICENCE_VALIDITY")
	private Date licenceValidity;
	
	@Column(name = "DR_GROUP_ID")
	private int drGroupId;
	
	@Size(max = 45, message = "{Size.Arms.issuingAuthority}")
	@Pattern(regexp ="^[a-zA-Z ]{0,45}$", message = "{Pattern.Arms.issuingAuthority}")
	@Column(name = "ISSUING_AUTHORITY")
	private String issuingAuthority;
	
	@Column(name = "NO_OF_ROUNDS")
	private int noOfRounds;
	
	@Size(min = 1, message = "{Size.Arms.createdBy}")
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Size(min = 1, message = "{Size.Arms.lastUpdatedBy}")
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;

	/**
	 * Gets the Id of this arms.
	 * 
	 * @return This arms Id.
	 * @since 0.1
	 */
	public int getArmsId()
	{
		return armsId;
	}

	/**
	 * Changes the Id of this arms.
	 * 
	 * @param armsId
	 *            This arms new Id.
	 * @since 0.1
	 */
	public void setArmsId(int armsId)
	{
		this.armsId = armsId;
	}

	/**
	 * Gets the person Id whom this arms belongs to.
	 * 
	 * @return Person Id whom this arms belongs to.
	 * @since 0.1
	 */
	public int getPersonId()
	{
		return personId;
	}

	/**
	 * Changes the person id whom this arms belongs to.
	 * 
	 * @param personId
	 *            New person Id whom this arms belongs to.
	 * @since 0.1
	 */
	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	/**
	 * Gets this arms type.
	 * 
	 * @return Type of this arm
	 * @since 0.1
	 */
	public String getTypeOfArm()
	{
		return typeOfArm;
	}

	/**
	 * Changes the type this arms.
	 * 
	 * @param typeOfArm
	 *            New type of this arms.
	 * @since 0.1
	 */
	public void setTypeOfArm(String typeOfArm)
	{
		this.typeOfArm = typeOfArm;
	}

	/**
	 * Gets this arms make.
	 * 
	 * @return Make of this arm
	 * @since 0.1
	 */
	public String getArmMake()
	{
		return armMake;
	}

	/**
	 * Changes the make this arms.
	 * 
	 * @param armMake
	 *            New make of this arms.
	 * @since 0.1
	 */
	public void setArmMake(String armMake)
	{
		this.armMake = armMake;
	}

	/**
	 * Gets this arms licence No.
	 * 
	 * @return Licence No of this arm
	 * @since 0.1
	 */
	public String getLicenceNo()
	{
		return licenceNo;
	}

	/**
	 * Changes the licence No this arms.
	 * 
	 * @param licenceNo
	 *            New licence No of this arms.
	 * @since 0.1
	 */
	public void setLicenceNo(String licenceNo)
	{
		this.licenceNo = licenceNo;
	}

	/**
	 * Gets this arms licence validity.
	 * 
	 * @return Licence validity of this arm
	 * @since 0.1
	 */
	public Date getLicenceValidity()
	{
		return licenceValidity;
	}

	/**
	 * Changes the licence validity this arms.
	 * 
	 * @param licenceValidity
	 *            New licence validity of this arms.
	 * @since 0.1
	 */
	public void setLicenceValidity(Date licenceValidity)
	{
		this.licenceValidity = licenceValidity;
	}

	/**
	 * Gets this arms group id.
	 * 
	 * @return Group id of this arm
	 * @since 0.1
	 */
	public int getDrGroupId()
	{
		return drGroupId;
	}

	/**
	 * Changes the group id this arms.
	 * 
	 * @param drGroupId
	 *            New group id of this arms.
	 * @since 0.1
	 */
	public void setDrGroupId(int drGroupId)
	{
		this.drGroupId = drGroupId;
	}

	/**
	 * Gets this arms issuing authority.
	 * 
	 * @return Issuing authority of this arm
	 * @since 0.1
	 */
	public String getIssuingAuthority()
	{
		return issuingAuthority;
	}

	/**
	 * Changes the issuing authority this arms.
	 * 
	 * @param issuingAuthority
	 *            New issuing authority of this arms.
	 * @since 0.1
	 */
	public void setIssuingAuthority(String issuingAuthority)
	{
		this.issuingAuthority = issuingAuthority;
	}

	/**
	 * Gets this arms no of rounds.
	 * 
	 * @return No of rounds of this arm
	 * @since 0.1
	 */
	public int getNoOfRounds()
	{
		return noOfRounds;
	}

	/**
	 * Changes the no of rounds this arms.
	 * 
	 * @param noOfRounds
	 *            New no of rounds of this arms.
	 * @since 0.1
	 */
	public void setNoOfRounds(int noOfRounds)
	{
		this.noOfRounds = noOfRounds;
	}

	/**
	 * Gets this arms created by user name.
	 * 
	 * @return Created by user name of this arm
	 * @since 0.1
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Changes the user name who created this arms.
	 * 
	 * @param createdBy
	 *            New user name who created this arms.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets this arms last updated by user name.
	 * 
	 * @return Last updated by user name of this arm.
	 * @since 0.1
	 */
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this arms.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this arms.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets this arms last updated date time.
	 * 
	 * @return Last updated date time of this arm.
	 * @since 0.1
	 */
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this arms.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this arms.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
}
