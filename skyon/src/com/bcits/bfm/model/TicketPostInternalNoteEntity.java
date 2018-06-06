package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="TICKET_POST_INTERNAL_NOTE")  
@NamedQueries({
	@NamedQuery(name = "TicketPostInternalNoteEntity.findAllById", query = "SELECT bli FROM TicketPostInternalNoteEntity bli WHERE bli.openNewTicketEntity.ticketId = :ticketId ORDER BY bli.internalNoteID DESC"),
	@NamedQuery(name = "TicketPostInternalNoteEntity.findAllByAscOrder", query = "SELECT bli FROM TicketPostInternalNoteEntity bli WHERE bli.openNewTicketEntity.ticketId = :ticketId ORDER BY bli.internalNoteID ASC"),
	@NamedQuery(name = "TicketPostInternalNoteEntity.ticketStatusUpdateOnPostInternalNote", query = "UPDATE TicketPostInternalNoteEntity el SET el.ticketStatus = :ticketStatus WHERE el.internalNoteID = :internalNoteID"),
	@NamedQuery(name = "TicketPostInternalNoteEntity.getPersonIdBasedOnTicketId", query = "SELECT ont FROM OpenNewTicketEntity ont WHERE ont.ticketId = :ticketId AND ont.typeOfTicket!='Common Area'"),
})
public class TicketPostInternalNoteEntity {

	@Id
	@Column(name="INTERNAL_NOTE_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "ticketPostInternalNote_seq", sequenceName = "TICKET_POST_INTERNAL_NOTE_SEQ") 
	@GeneratedValue(generator = "ticketPostInternalNote_seq")
	private int internalNoteID;
	
	@Column(name="TICKET_ID")
	private int ticketId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID",insertable = false, updatable = false, nullable = false)
	private OpenNewTicketEntity openNewTicketEntity;
	
	@Column(name="INTERNAL_NOTE_TITLE")
	@NotEmpty(message="Internal note subject is required")
	private String internalNoteTitle;
	
	@Column(name="INTERNAL_NOTE_DETAILS")
	@NotEmpty(message="Internal note details is required")
	private String internalNoteDetails;
	
	@Column(name="INTERNAL_NOTE_CREATED_DT", unique=true, nullable=false, precision=11, scale=0)
	private Timestamp internalNoteCreatedDate;
			
	@Column(name="STATUS")
	private  String ticketStatus;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
			
	public int getInternalNoteID() {
		return internalNoteID;
	}

	public void setInternalNoteID(int internalNoteID) {
		this.internalNoteID = internalNoteID;
	}

	public OpenNewTicketEntity getOpenNewTicketEntity() {
		return openNewTicketEntity;
	}

	public void setOpenNewTicketEntity(OpenNewTicketEntity openNewTicketEntity) {
		this.openNewTicketEntity = openNewTicketEntity;
	}

	public String getInternalNoteTitle() {
		return internalNoteTitle;
	}

	public void setInternalNoteTitle(String internalNoteTitle) {
		this.internalNoteTitle = internalNoteTitle;
	}

	public String getInternalNoteDetails() {
		return internalNoteDetails;
	}

	public void setInternalNoteDetails(String internalNoteDetails) {
		this.internalNoteDetails = internalNoteDetails;
	}

	public Timestamp getInternalNoteCreatedDate() {
		return internalNoteCreatedDate;
	}

	public void setInternalNoteCreatedDate(Timestamp internalNoteCreatedDate) {
		this.internalNoteCreatedDate = internalNoteCreatedDate;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
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

	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}

	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
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
}
