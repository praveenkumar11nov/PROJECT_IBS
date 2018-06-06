package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name="FEEDBACK_MASTER")
@NamedQueries({
	@NamedQuery(name = "FeedbackMaster.getAllIds", query = "select f.fMaster_d from FeedbackMaster f"),
	@NamedQuery(name = "FeedbackMaster.getMasterById", query = "select f from FeedbackMaster f where f.fMaster_d=:fMaster_d"),
	@NamedQuery(name = "FeedbackMaster.getMasterByTicketId", query = "select f from FeedbackMaster f where f.ticketId=:ticketId")
})
public class FeedbackMaster implements Serializable
{
	@Id
	@SequenceGenerator(name = "fmseq", sequenceName = "FMASTER_SEQ")
	@GeneratedValue(generator = "fmseq")
	@Column(name="FMASTER_ID")
	private int fMaster_d;
	
	@Column(name="FEED_DATE")
	private Date date;
	
	@Column(name="PERSON_ID", unique=true, nullable=false, precision=11, scale=0)
	private int personId;
	
	@OneToOne
	@JoinColumn(name="PERSON_ID", insertable = false, updatable = false, nullable = false)
	private Person person;
	
	@Column(name="TICKET_ID", unique=true, nullable=false, precision=11, scale=0)
	private int ticketId;
	
	@OneToOne
	@JoinColumn(name="TICKET_ID", insertable = false, updatable = false, nullable = false)
	private OpenNewTicketEntity openNewTicketEntity;
	
	@Column(name="LIKE_CONTACTED")
	private String like_Contacted;
	
	@Column(name="ADDITIONAL_COMMENTS")
	private String additional_Comments;

	public int getfMaster_d() {
		return fMaster_d;
	}

	public void setfMaster_d(int fMaster_d) {
		this.fMaster_d = fMaster_d;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public OpenNewTicketEntity getOpenNewTicketEntity() {
		return openNewTicketEntity;
	}

	public void setOpenNewTicketEntity(OpenNewTicketEntity openNewTicketEntity) {
		this.openNewTicketEntity = openNewTicketEntity;
	}

	public String getLike_Contacted() {
		return like_Contacted;
	}

	public void setLike_Contacted(String like_Contacted) {
		this.like_Contacted = like_Contacted;
	}

	public String getAdditional_Comments() {
		return additional_Comments;
	}

	public void setAdditional_Comments(String additional_Comments) {
		this.additional_Comments = additional_Comments;
	}

	
	
}

