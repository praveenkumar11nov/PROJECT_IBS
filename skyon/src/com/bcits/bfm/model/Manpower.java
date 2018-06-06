package com.bcits.bfm.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="MANPOWER_MASTER")
@NamedQueries({
	@NamedQuery(name = "Manpower.findAll", query = "SELECT m FROM Manpower m ORDER BY m.mnId DESC")
	})
public class Manpower {
	
	
	
	@Id
	@SequenceGenerator(name="SEQ_MANPOWER" ,sequenceName="MANPOWER_SEQ")
	@GeneratedValue(generator="SEQ_MANPOWER")
	@Column(name = "MN_ID")
	private int mnId;

	
	@Column(name = "FIRST_NAME")
	private String fname;
	
	@Column(name = "LAST_NAME")
	private String lname;	
	
	/*@Min(value = 1, message = "'Person' is not selected")
	@Column(name = "PERSON_ID", precision = 10, scale = 0)
	private int personId;*/
	
	
	@Min(value = 1, message = "'Department' is not selected")
	@Column(name = "DEPT_ID", precision = 10, scale = 0)
	private int deptId;

	@Min(value = 1, message = "'Designation' is not selected")
	@Column(name = "DN_ID", precision = 10, scale = 0)
	private int dnId;

	@Column(name = "UR_ID")
	private int urId;
	
	
	/*@OneToOne
	@JoinColumn(name = "UR_ID", insertable = false, updatable = false, nullable = false)
	private Users users;*/
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private String lastUpdatedDt;
	
//	@OneToOne
//	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
//	private Person person;
	
	@OneToOne
	@JoinColumn(name = "DN_ID", insertable = false, updatable = false, nullable = false)
	private Designation designation;

	@OneToOne
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
	private Department department;
	
	
	@Column(name = "WORK_SHIFT")
	private String workShift;
	
	@Column(name = "SUPERVISOR")
	private String supervisor;
	
	@Column(name = "EMP_STATUS")
	private String empStatus;

	public int getMnId() {
		return mnId;
	}

	public void setMnId(int mnId) {
		this.mnId = mnId;
	}

	/*public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}*/

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

	/*public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}*/

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

	public String getWorkShift() {
		return workShift;
	}

	public void setWorkShift(String workShift) {
		this.workShift = workShift;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	
	
	
	
	
	@Column(name = "UR_LOGIN_NAME")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z]+[._a-zA-Z0-9._]*[a-zA-Z0-9]$", message = "Login name field can not allow special symbols except(_ .)")
	private String urLoginName;
	
	
	@Email
	@Column(name = "EMAIL_ID")
	private String emailId;

	
	@Column(name = "MOBILE_NO", precision = 11, scale = 0)
	private long mobileNo;

	public String getUrLoginName() {
		return urLoginName;
	}

	public void setUrLoginName(String urLoginName) {
		this.urLoginName = urLoginName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getUrId() {
		return urId;
	}

	public void setUrId(int urId) {
		this.urId = urId;
	}

	/*public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}*/
	
	
	

}
