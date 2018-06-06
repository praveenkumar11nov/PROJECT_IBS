package com.bcits.bfm.model;

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

@Entity
@Table(name = "PT_ALERT")
@NamedQueries({
	//@NamedQuery(name = "PatrolTrackAlert.findAll", query = "SELECT pta FROM PatrolTrackAlert pta ORDER BY pta.ptaId"),
	@NamedQuery(name = "PatrolTrackAlert.findAllBasedOnPtId", query = "SELECT pta FROM PatrolTrackAlert pta WHERE pta.ptId=:ptId"),
	@NamedQuery(name = "PatrolTrackAlert.findAll", query = "SELECT pta.ptaId,pt.ptName,p.firstName,p.lastName,pta.message,pta.ptaDt FROM PatrolTrackAlert pta INNER JOIN pta.patrolTracks pt INNER JOIN pta.person p ORDER BY pta.ptaId"),
})

public class PatrolTrackAlert {
	
	
	@Id
	@SequenceGenerator(name = "ptalert_seq", sequenceName = "PTALERT_SEQ")
	@GeneratedValue(generator = "ptalert_seq")
	@Column(name = "PTA_ID")
	private int ptaId;
	
	@Column(name = "PT_ID")
	private int ptId;
	
	@Column(name = "PERSON_ID")
	private int personId;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "PTA_DT")
	private String ptaDt;
	
	@Column(name = "PTA_MOBILE_NO")
	private String ptaMobileNo;
	
	@Column(name = "PTA_EMAIL")
	private String ptaEmail;
	
	@OneToOne
	@JoinColumn(name = "PT_ID", insertable = false, updatable = false, nullable = false)
	private PatrolTracks patrolTracks;
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;

	public PatrolTrackAlert() {
		super();
	}

	

	public PatrolTrackAlert(int ptaId, int ptId, int personId, String message,
			String ptaDt, String ptaMobileNo, String ptaEmail,
			PatrolTracks patrolTracks, Person person) {
		super();
		this.ptaId = ptaId;
		this.ptId = ptId;
		this.personId = personId;
		this.message = message;
		this.ptaDt = ptaDt;
		this.ptaMobileNo = ptaMobileNo;
		this.ptaEmail = ptaEmail;
		this.patrolTracks = patrolTracks;
		this.person = person;
	}



	public int getPtaId() {
		return ptaId;
	}

	public void setPtaId(int ptaId) {
		this.ptaId = ptaId;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public String getPtaDt() {
		return ptaDt;
	}


	public void setPtaDt(String ptaDt) {
		this.ptaDt = ptaDt;
	}



	public String getPtaMobileNo() {
		return ptaMobileNo;
	}

	public void setPtaMobileNo(String ptaMobileNo) {
		this.ptaMobileNo = ptaMobileNo;
	}

	public String getPtaEmail() {
		return ptaEmail;
	}

	public void setPtaEmail(String ptaEmail) {
		this.ptaEmail = ptaEmail;
	}

	public PatrolTracks getPatrolTracks() {
		return patrolTracks;
	}

	public void setPatrolTracks(PatrolTracks patrolTracks) {
		this.patrolTracks = patrolTracks;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	

}
