package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.bcits.bfm.util.SessionData;

import net.sf.oval.constraint.MatchPattern;

@Entity
@NamedQueries({
	
	@NamedQuery(name="MeterReader.findAllMeterReaders",query="SELECT m FROM MeterReader m ORDER BY m.mrId DESC"),
	@NamedQuery(name="MeterReader.setMrStatus",query="UPDATE MeterReader m SET m.mrStatus = :mrStatus WHERE m.mrId = :mrId")
})
@Table(name = "METER_READERS")
public class MeterReader
{
	private int mrId;
	private int personId;
	private String mrNo;
	private String mrStatus;
	private String createdBy;
	private String lastUpdatedBy;
	@Access(AccessType.FIELD)
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	private Person person;

	@Id
	@SequenceGenerator(name="METER_READER_SEQ",sequenceName="METER_READER_SEQ")
	@GeneratedValue(generator="METER_READER_SEQ")
	@Column(name = "MR_ID")
	public int getMrId()
	{
		return mrId;
	}

	public void setMrId(int mrId)
	{
		this.mrId = mrId;
	}

	@Column(name = "PERSON_ID")
	@net.sf.oval.constraint.Min(value = 1, message = "Min.MeterReader.personId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.MeterReader.personId")
	public int getPersonId()
	{
		return personId;
	}

	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	@net.sf.oval.constraint.Size(min = 1, max = 50, message = "Size.MeterReader.mrNo")
	@MatchPattern( pattern = "^[a-zA-Z0-9]{1,50}$", message = "MatchPattern.MeterReader.mrNo")
	@net.sf.oval.constraint.NotNull(message = "NotNull.MeterReader.mrNo")
	@Column(name = "MR_NO")
	public String getMrNo()
	{
		return mrNo;
	}

	public void setMrNo(String mrNo)
	{
		this.mrNo = mrNo;
	}

	@net.sf.oval.constraint.Size(min=1, message = "Size.MeterReader.mrStatus")
	@MatchPattern( pattern = "Active|Inactive", message = "MatchPattern.MeterReader.mrStatus")
	@Column(name = "STATUS")
	public String getMrStatus()
	{
		return mrStatus;
	}

	public void setMrStatus(String mrStatus)
	{
		this.mrStatus = mrStatus;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}
	
	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
	
}
