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
import javax.validation.constraints.Min;


@Entity
@Table(name = "PT_STAFF")
@NamedQueries({
	@NamedQuery(name = "PatrolTrackStaff.UpdateStatus",query="UPDATE PatrolTrackStaff ps SET  ps.status = :ptsStatus WHERE ps.ptsId = :ptsId"),
	//@NamedQuery(name = "PatrolTrackStaff.findAll", query = "SELECT ps FROM PatrolTrackStaff ps ORDER BY ps.ptsId DESC"),
	@NamedQuery(name = "PatrolTrackStaff.getPatrolTrackStaffInstanceById", query = "SELECT ps from PatrolTrackStaff ps where ps.ptsId=:ptsId"),
	@NamedQuery(name = "PatrolTrackStaff.findAllBasedOnPtId", query = "SELECT ps FROM PatrolTrackStaff ps WHERE ps.ptId=:ptId"),
	@NamedQuery(name = "PatrolTrackStaff.findStaffRecord", query = "SELECT ps FROM PatrolTrackStaff ps WHERE ps.ptId=:ptId AND ps.acId=:acId AND ps.personId=:staffId"),
	@NamedQuery(name = "PatrolTrackStaff.getStatusBasedOnId", query = "SELECT ps.status FROM PatrolTrackStaff ps WHERE ps.ptsId=:ptsId "),
	//@NamedQuery(name = "PatrolTrackStaff.findAll", query = "SELECT ps.ptsId,p.firstName,p.lastName,ps.personId,ac.acNo,ac.acId,pt.ptName,ps.supervisorId,ps.fromDate,ps.toDate,ps.status,ps.lastUpdatedBy,ps.lastUpdatedDt,ps.createdBy,(SELECT CONCAT(p1.firstName, NVL(p1.lastName, '')) FROM Person p1 WHERE p1.personId= ps.supervisorId) FROM PatrolTrackStaff ps INNER JOIN ps.patrolTracks pt INNER JOIN ps.person p INNER JOIN ps.accessCards ac  ORDER BY ps.ptsId DESC"),
	@NamedQuery(name = "PatrolTrackStaff.PatrolTrackStaffData", query = "SELECT ps.ptsId,p.firstName,p.lastName,ps.personId,ps.acId,ps.acId,pt.ptName,ps.supervisorId,ps.fromDate,ps.toDate,ps.status,ps.lastUpdatedBy,ps.lastUpdatedDt,ps.createdBy,(SELECT CONCAT(p1.firstName, NVL(p1.lastName, '')) FROM Person p1 WHERE p1.personId= ps.supervisorId) FROM PatrolTrackStaff ps INNER JOIN ps.patrolTracks pt INNER JOIN ps.person p  ORDER BY ps.ptsId DESC"),
	@NamedQuery(name = "PatrolTrackStaff.findAll", query = "SELECT ps.ptsId,p.firstName,p.lastName,ps.personId,ps.acId,ps.acId,pt.ptName,ps.supervisorId,ps.fromDate,ps.toDate,ps.status,ps.lastUpdatedBy,ps.lastUpdatedDt,ps.createdBy,(SELECT CONCAT(p1.firstName, NVL(p1.lastName, '')) FROM Person p1 WHERE p1.personId= ps.supervisorId) FROM PatrolTrackStaff ps INNER JOIN ps.patrolTracks pt INNER JOIN ps.person p  ORDER BY ps.ptsId DESC"),

})

public class PatrolTrackStaff {
	
	
	@Id
	@SequenceGenerator(name = "ptstaff_seq", sequenceName = "PATROLTRACKSTAFF_SEQ")
	@GeneratedValue(generator = "ptstaff_seq")
	@Column(name = "PTS_ID")
	private int ptsId;
	
	@Min(value = 1, message = "'PatrolTrack' should not contain empty and invalid name.")
	@Column(name = "PT_ID", precision = 11, scale = 0)
	private int ptId;
	
	@Min(value = 1, message = "'Staff Name' should not contain empty and invalid name.")
	@Column(name = "PERSON_ID", precision = 11, scale = 0)
	private int personId;
	
	@Min(value = 1, message = "'AccessCard Number' should not contain empty and invalid name.")
	@Column(name = "AC_ID", precision = 11, scale = 0)
	private int acId;
	
	@Column(name = "PTS_VALID_FROM")
	private Timestamp fromDate;
	
	@Column(name = "PTS_VALID_TO")
	private Timestamp toDate;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private String lastUpdatedDt;
	
	@OneToOne
	@JoinColumn(name = "PT_ID", insertable = false, updatable = false, nullable = false)
	private PatrolTracks patrolTracks;
	
	/*@OneToOne
	@JoinColumn(name = "AC_ID", insertable = false, updatable = false, nullable = false)
	private AccessCards accessCards;*/
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;
	
	@Min(value = 1, message = "'Supervisor Name' should not contain empty and invalid name.")
	@Column(name = "SUPERVISOR_ID", precision = 11, scale = 0)
	private int supervisorId;


	public PatrolTrackStaff() {
		super();
	}

	/*public PatrolTrackStaff(int ptsId, int ptId, int personId, int acId,
			Timestamp fromDate, Timestamp toDate, String status, String createdBy,
			String lastUpdatedBy, String lastUpdatedDt,
			PatrolTracks patrolTracks, AccessCards accessCards, Person person,
			int supervisorId) {
		super();
		this.ptsId = ptsId;
		this.ptId = ptId;
		this.personId = personId;
		this.acId = acId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.patrolTracks = patrolTracks;
		this.accessCards = accessCards;
		this.person = person;
		this.supervisorId = supervisorId;
	}*/





	public int getPtsId() {
		return ptsId;
	}

	public void setPtsId(int ptsId) {
		this.ptsId = ptsId;
	}

	public int getPtId() {
		return ptId;
	}

	public void setPtId(int ptId) {
		this.ptId = ptId;
	}


	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public int getAcId() {
		return acId;
	}

	public void setAcId(int acId) {
		this.acId = acId;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(String lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}


	public PatrolTracks getPatrolTracks() {
		return patrolTracks;
	}


	public void setPatrolTracks(PatrolTracks patrolTracks) {
		this.patrolTracks = patrolTracks;
	}

	/*public AccessCards getAccessCards() {
		return accessCards;
	}

	public void setAccessCards(AccessCards accessCards) {
		this.accessCards = accessCards;
	}*/

	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}
	
	/*@Override
	public int hashCode(){
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(this.createdBy);
	    buffer.append(this.lastUpdatedBy);
	    buffer.append(this.ptsId);
	    buffer.append(this.ptId);
	    buffer.append(this.personId);
	    buffer.append(this.acId);
	    buffer.append(this.fromDate);
	    buffer.append(this.toDate);
	    buffer.append(this.supervisorId);
	    buffer.append(this.status);
	    buffer.append(this.patrolTracks);
	    buffer.append(this.accessCards);
	    buffer.append(this.supervisorId);
	    
	    return buffer.toString().hashCode();
	}*/
	
	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass())return false;
	    PatrolTrackStaff patrolTrackStaff = (PatrolTrackStaff)object;
	    if(this.hashCode()== patrolTrackStaff.hashCode())return true;
	   return false;
	}
}
