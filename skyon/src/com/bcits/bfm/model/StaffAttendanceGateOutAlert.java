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


@Entity
@Table(name = "STAFF_ATTENDANCE_GATEOUT_ALERT")
@NamedQueries({
	@NamedQuery(name = "StaffAttendanceGateOutAlert.findAll", query = "SELECT saga FROM StaffAttendanceGateOutAlert saga ORDER BY saga.sagaId")
	
})
public class StaffAttendanceGateOutAlert {
	
	@Id
	@SequenceGenerator(name = "gateout_seq", sequenceName = "GATEOUTALERT_SEQ")
	@GeneratedValue(generator = "gateout_seq")
	@Column(name = "SAGA_ID")
	private int sagaId;
	
	@Column(name = "PERSON_ID")
	private int personId;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "SAGA_DT")
	private Timestamp sagaDt;
	
	@Column(name = "SAGA_MOBILE_NO")
	private String sagaMobileNo;
	
	@Column(name = "SAGA_EMAIL")
	private String sagaEmail;
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;

	public StaffAttendanceGateOutAlert() {
		super();
	}

/*	public StaffAttendanceGateOutAlert(int sagaId, int personId,
			String message, String sagaDt, String sagaMobileNo,
			String sagaEmail, Person person) {
		super();
		this.sagaId = sagaId;
		this.personId = personId;
		this.message = message;
		this.sagaDt = sagaDt;
		this.sagaMobileNo = sagaMobileNo;
		this.sagaEmail = sagaEmail;
		this.person = person;
	}*/

	public int getSagaId() {
		return sagaId;
	}

	public void setSagaId(int sagaId) {
		this.sagaId = sagaId;
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

	public Timestamp getSagaDt() {
		return sagaDt;
	}

	public void setSagaDt(Timestamp sagaDt) {
		this.sagaDt = sagaDt;
	}

	public String getSagaMobileNo() {
		return sagaMobileNo;
	}

	public void setSagaMobileNo(String sagaMobileNo) {
		this.sagaMobileNo = sagaMobileNo;
	}

	public String getSagaEmail() {
		return sagaEmail;
	}

	public void setSagaEmail(String sagaEmail) {
		this.sagaEmail = sagaEmail;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	

}
