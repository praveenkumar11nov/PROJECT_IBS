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

@Entity
@Table(name = "PERSON_ACCESS_CARDS")
@NamedQueries({
	@NamedQuery(name="personAccessCards.getOnPersonId",query="SELECT pac FROM PersonAccessCards pac WHERE pac.personId = :personId"),
	@NamedQuery(name = "PersonAccessCards.findPersonAccessCards", query = "SELECT pac FROM PersonAccessCards pac ORDER BY pac.personacId DESC"),
	@NamedQuery(name="PersonAccess.Size",query="select p from PersonAccessCards p where p.acId=:acId"),
	@NamedQuery(name="personAccessCards.readAccessCardsForUniqe",query="select p.acId from PersonAccessCards p"),
	
})
public class PersonAccessCards 
{
	
	@Id
	@SequenceGenerator(name = "personaccesscard_seq", sequenceName = "PERSON_ACCESSCARD_SEQ")
	@GeneratedValue(generator = "personaccesscard_seq")
	@Column(name = "PAC_ID")
	private int personacId;
	
	@Column(name = "AC_ID")
	private int acId;
	
	 
	
	/*@Column(name = "AC_START_DATE")
	private Date acStartDate;
	
	@Column(name = "AC_END_DATE")
	private Date acEndDate;*/

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	@Column(name = "PERSON_ID")
	private int personId;

	public int getPersonacId() {
		return personacId;
	}

	public void setPersonacId(int personacId) {
		this.personacId = personacId;
	}

	public int getAcId() {
		return acId;
	}

	public void setAcId(int acId) {
		this.acId = acId;
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

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
/*	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AC_ID", insertable = false, updatable = false, nullable = false)
	private AccessCards ar;

	public AccessCards getAr() {
		return ar;
	}

	public void setAr(AccessCards ar) {
		this.ar = ar;
	}*/
	
	
	
	
	
	
}
