package com.bcits.bfm.model;

import java.util.Date;

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
@Table(name = "STAFF_ATTENDANCE_SUMMARY")

@NamedQueries({
	@NamedQuery(name = "StaffAttendanceSummary.findAll", query = "SELECT sas FROM StaffAttendanceSummary sas ORDER BY sas.sasId"),
	@NamedQuery(name = "StaffAttendanceSummary.findAllStaffDetails", query = "SELECT sas FROM StaffAttendanceSummary sas ORDER BY sas.sasDate"),
	@NamedQuery(name = "StaffAttendanceSummary.getInTimeList", query = "SELECT sas.timeIn FROM StaffAttendanceSummary sas ORDER BY sas.sasId"),
	@NamedQuery(name = "StaffAttendanceSummary.getOutTimeList", query = "SELECT sas.timeOut FROM StaffAttendanceSummary sas ORDER BY sas.sasId"),
	@NamedQuery(name = "StaffAttendanceSummary.getDistinctDepartmentId", query = "SELECT sas.deptId FROM StaffAttendanceSummary sas ORDER BY sas.sasId"),
	@NamedQuery(name = "StaffAttendanceSummary.getDistinctDesignationId", query = "SELECT sas.dnId FROM StaffAttendanceSummary sas ORDER BY sas.sasId")
})
public class StaffAttendanceSummary {
	
	@Id
	@SequenceGenerator(name = "attendance_seq", sequenceName = "STAFFATTENDANCESUMMARY_SEQ")
	@GeneratedValue(generator = "attendance_seq")
	@Column(name = "SAS_ID")
	private int sasId;
	
	@Column(name = "PERSON_ID", precision = 11, scale = 0)
	private int personId;
	
	@Column(name = "SAS_DATE")
	private String sasDate;
	
	@Column(name = "TIME_IN")
	private Date timeIn;
	
	@Column(name = "TIME_OUT")
	private Date timeOut;
	
	@Column(name = "TIME_OUT_SUCESSFUL")
	private String timeOutSuccessfull;
	
	@Min(value = 1, message = "'Department' is not selected")
	@Column(name = "DEPT_ID", precision = 10, scale = 0)
	private int deptId;

	@Min(value = 1, message = "'Designation' is not selected")
	@Column(name = "DN_ID", precision = 10, scale = 0)
	private int dnId;
	
	@OneToOne
	@JoinColumn(name = "DN_ID", insertable = false, updatable = false, nullable = false)
	private Designation designation;

	@OneToOne
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
	private Department department;
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;

	public StaffAttendanceSummary() {
		super();
	}

	public StaffAttendanceSummary(int sasId, int personId, String sasDate,
			Date timeIn, Date timeOut, String timeOutSuccessfull, int deptId,
			int dnId, Designation designation, Department department,
			Person person) {
		super();
		this.sasId = sasId;
		this.personId = personId;
		this.sasDate = sasDate;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.timeOutSuccessfull = timeOutSuccessfull;
		this.deptId = deptId;
		this.dnId = dnId;
		this.designation = designation;
		this.department = department;
		this.person = person;
	}

	public int getSasId() {
		return sasId;
	}

	public void setSasId(int sasId) {
		this.sasId = sasId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getSasDate() {
		return sasDate;
	}

	public void setSasDate(String sasDate) {
		this.sasDate = sasDate;
	}

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	public String getTimeOutSuccessfull() {
		return timeOutSuccessfull;
	}

	public void setTimeOutSuccessfull(String timeOutSuccessfull) {
		this.timeOutSuccessfull = timeOutSuccessfull;
	}


	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getDnId() {
		return dnId;
	}

	public void setDnId(int dnId) {
		this.dnId = dnId;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	

}
