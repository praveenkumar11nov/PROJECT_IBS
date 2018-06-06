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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "STAFF_EXP")
@NamedQueries({
	@NamedQuery(name = "StaffExperience.findAll", query = "SELECT r FROM StaffExperience r order by r.seId"),
	@NamedQuery(name = "StaffExperience.findById", query = "SELECT r FROM StaffExperience r  WHERE r.personId = :personId order by r.pwSlno DESC")
})
public class StaffExperience {

	@Id
	@Column(name = "SE_ID")
	@SequenceGenerator(name="SEQ_STAFF" ,sequenceName="STAFF_EXP_SEQ")
	@GeneratedValue(generator="SEQ_STAFF")
	private int seId;

	@Column(name = "PERSON_ID")
	private int personId;

	@Column(name = "PW_SL_NO")
	private int pwSlno;

	@Column(name = "COMPANY")
	private String company;

	@Size(min = 0, max = 300, message = "Designation can have maximum {max} letters")
	@Column(name = "DESIGNATION")
	private String designation;	
	
	@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;
	
	@NotNull(message="Invalid Timestamp Range")
	@Column(name = "FROM_DATE")
	private Date startDate;

	@Column(name = "TO_DATE")
	private Date endDate;

	@Size(min = 0, max = 300, message = "Description can have maximum {max} letters")
	@Column(name = "WORK_DESCRIPTION")
	private String workDesc;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Date lastUpdateDate;


	public int getSeId() {
		return seId;
	}

	public void setSeId(int seId) {
		this.seId = seId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getPwSlno() {
		return pwSlno;
	}

	public void setPwSlno(int pwSlno) {
		this.pwSlno = pwSlno;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWorkDesc() {
		return workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@PrePersist
	 protected void onCreate() {
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	 @PreUpdate
	 protected void onUpdate() {
		 lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }

	/*public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}*/
	
	
}
