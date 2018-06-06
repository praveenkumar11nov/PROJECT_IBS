package com.bcits.bfm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "PATROL_TRACKS")
@Entity

@NamedQueries({ 
	@NamedQuery(name = "PatrolTracks.UpdateStatus",query="UPDATE PatrolTracks p SET p.status = :ptStatus WHERE p.ptId = :ptId"),
	@NamedQuery(name = "PatrolTracks.findAll", query = "SELECT p FROM PatrolTracks p ORDER BY p.ptId DESC"),
	@NamedQuery(name = "PatrolTracks.PatrolTrackInstanceById", query = "SELECT p from PatrolTracks p where p.ptId=:ptId"),
	@NamedQuery(name = "PatrolTracks.getAllPatrolTrackNames", query = "SELECT p.ptName FROM PatrolTracks p"),
	@NamedQuery(name = "PatrolTracks.getPatrolTrackIdBasedOnName", query = "SELECT p.ptId FROM PatrolTracks p WHERE p.ptName=:ptName"),
	@NamedQuery(name = "PatrolTracks.getStatusList", query = "SELECT p.status FROM PatrolTracks p ORDER BY p.ptId DESC"),
	@NamedQuery(name = "PatrolTracks.ptNames", query = "SELECT p.ptName FROM PatrolTracks p WHERE p.ptName=:ptName "),
	@NamedQuery(name = "PatrolTracks.findActiveTracks", query = "SELECT p FROM PatrolTracks p WHERE  p.status=:status ORDER BY p.ptId DESC"),
	@NamedQuery(name = "PatrolTracks.getStatusBasedOnId", query = "SELECT p.status FROM PatrolTracks p WHERE p.ptId=:ptId "),
	
})

public class PatrolTracks {
	
	/**************Fields***************/
	@Id
	@SequenceGenerator(name = "PATROL_TRACKS_SEQ", sequenceName = "PATROL_TRACKS_SEQ")
	@GeneratedValue(generator = "PATROL_TRACKS_SEQ")
	@Column(name = "PT_ID")
	private int ptId;
	
	@NotNull
	@Size(min = 2, max = 50)
	@Column(name = "PT_NAME")
	private String ptName;
	
	@NotEmpty(message = "'Description' cannot be a empty")
	@Column(name = "PT_DESCRIPTION")
	private String description;
	
	@NotEmpty(message = "'From Time' is not selected")
	@Column(name = "PT_VALID_TIME_FROM")
	private String validTimeFrom;
	
	@NotEmpty(message = "'To Time' is not selected")
	@Column(name = "PT_VALID_TIME_TO")
	private String validTimeTo;
	
	@Column(name = "PT_ADMIN_ALERT_MOBILE_NO" , precision = 20, scale = 0 )
	private String adminAlertMobileNo;
	
	@Column(name = "PT_ADMIN_ALERT_EMAIL_ID")
	private String adminAlertEmailId;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private String lastUpdatedDt;

	/****Default constructor****/

	public PatrolTracks() {
		super();
	}

	
	public PatrolTracks(int ptId, String ptName, String description,
			String validTimeFrom, String validTimeTo,
			String adminAlertMobileNo, String adminAlertEmailId, String status,
			String createdBy, String lastUpdatedBy, String lastUpdatedDt) {
		super();
		this.ptId = ptId;
		this.ptName = ptName;
		this.description = description;
		this.validTimeFrom = validTimeFrom;
		this.validTimeTo = validTimeTo;
		this.adminAlertMobileNo = adminAlertMobileNo;
		this.adminAlertEmailId = adminAlertEmailId;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}


/******Property accessors*********/

	/**
	 * Changes the Id of this Patroltrack.
	 * 
	 * @param ptId
	 *            This Patroltrack's new Id.
	 * @since 0.1
	 */
	public int getPtId() {
		return ptId;
	}


	public void setPtId(int ptId) {
		this.ptId = ptId;
	}
	
	
	public String getPtName() {
		return ptName;
	}


	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getValidTimeFrom() {
		return validTimeFrom;
	}


	public void setValidTimeFrom(String validTimeFrom) {
		this.validTimeFrom = validTimeFrom;
	}

	
	public String getValidTimeTo() {
		return validTimeTo;
	}



	public void setValidTimeTo(String validTimeTo) {
		this.validTimeTo = validTimeTo;
	}


	
	public String getAdminAlertMobileNo() {
		return adminAlertMobileNo;
	}



	public void setAdminAlertMobileNo(String adminAlertMobileNo) {
		this.adminAlertMobileNo = adminAlertMobileNo;
	}

	/**
	 * Getting mobile number of the person who has created this Patroltrack
	 * 
	 * @param ptId
	 *            This Patroltrack's new Id.
	 * @since 0.1
	 */

	public String getAdminAlertEmailId() {
		return adminAlertEmailId;
	}
	/**
	 * Setting mobile number of the person who has creating this patroltrack
	 * 
	 * 
	 * @since 0.1
	 */


	public void setAdminAlertEmailId(String adminAlertEmailId) {
		this.adminAlertEmailId = adminAlertEmailId;
	}

	/**
	 * Getting status of this Patroltrack
	 * 
	 * 
	 * @since 0.1
	 */

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * Gets the name of the person who has created this Patroltrack.
	 * 
	 * @return The name of the person who has created this Patroltrack.
	 * @since 0.1
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set the name of the person who has created this Patroltrack.
	 * 
	 * @param createdBy
	 *            Name of the person who is created this Patroltrack.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * Gets the name of the person who has updated this PatrolTrack previously.
	 * 
	 * @return Name of the person who has updated this User previously.
	 * @since 0.1
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * Set the name of the person who has updated this Patroltrack.
	 * 
	 * @param lastUpdatedBy
	 *            Name of the person who is updating this Patroltrack.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets the date when this patroltrack is created/last updated.
	 * 
	 * @return Date when the patroltrack is last updated.
	 * @since 0.1
	 */
	public String getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	/**
	 * Set the Date when this patroltrack is created/updated.
	 * 
	 * @param lastUpdatedDt
	 *            This is last updated date.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(String lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Override
	public int hashCode(){
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(this.createdBy);
	    buffer.append(this.lastUpdatedBy);
	    buffer.append(this.ptId);
	    buffer.append(this.ptName);
	    buffer.append(this.description);
	    buffer.append(this.validTimeFrom);
	    buffer.append(this.validTimeTo);
	    buffer.append(this.adminAlertMobileNo);
	    buffer.append(this.adminAlertEmailId);
	    buffer.append(this.status);
	    return buffer.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass())return false;
	    PatrolTracks patrolTracks = (PatrolTracks)object;
	    if(this.hashCode()== patrolTracks.hashCode())return true;
	   return false;
	}
	
}
