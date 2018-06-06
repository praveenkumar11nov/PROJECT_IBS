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
@Table(name="TICKET_POST_REPLY")  
@NamedQueries({
	@NamedQuery(name = "TicketPostReplyEntity.findAllById", query = "SELECT bli FROM TicketPostReplyEntity bli WHERE bli.openNewTicketEntity.ticketId = :ticketId ORDER BY bli.postReplyId DESC"),
	@NamedQuery(name = "TicketPostReplyEntity.findAllByAscOrder", query = "SELECT bli FROM TicketPostReplyEntity bli WHERE bli.openNewTicketEntity.ticketId = :ticketId ORDER BY bli.postReplyId ASC"),
	@NamedQuery(name = "TicketPostReplyEntity.ticketStatusUpdate", query = "UPDATE TicketPostReplyEntity el SET el.ticketStatus = :ticketStatus WHERE el.postReplyId = :postReplyId"),
   // @NamedQuery(name=  "TicketPostReplyEntity.ticketMisReport",query="SELECT  p.property_No,do.dept_Name,ho.normalSLA,ho.topicName,ot.ticketNumber,ot.ticketCreatedDate,ot.ticketClosedDate,ot.ticketUpdateDate,ot.ticketStatus,ot.createdBy,ot. TicketPostReplyEntity tp INNER JOIN openNewTicketEntity ot INNER JOIN ot.propertyObj p INNER JOIN ot.departmentObj do INNER JOIN ot.helpTopicObj ho ")

})
public class TicketPostReplyEntity {

	@Id
	@Column(name="POST_REPLY_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "ticketPostReply_seq", sequenceName = "TICKET_POST_REPLY_SEQ") 
	@GeneratedValue(generator = "ticketPostReply_seq")
	private int postReplyId;
	
	@Column(name="TICKET_ID")
	private int ticketId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID",insertable = false, updatable = false, nullable = false)
	private OpenNewTicketEntity openNewTicketEntity;
	
	@Column(name="RESPONSE")
	@NotEmpty(message="Response is required")
	private String response;
	
	@Column(name="RESPONSE_DT", unique=true, nullable=false, precision=11, scale=0)
	private Timestamp responseDate;
			
	@Column(name="STATUS")
	private  String ticketStatus;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
		
	public int getPostReplyId() {
		return postReplyId;
	}

	public void setPostReplyId(int postReplyId) {
		this.postReplyId = postReplyId;
	}

	public OpenNewTicketEntity getOpenNewTicketEntity() {
		return openNewTicketEntity;
	}

	public void setOpenNewTicketEntity(OpenNewTicketEntity openNewTicketEntity) {
		this.openNewTicketEntity = openNewTicketEntity;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Timestamp getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Timestamp responseDate) {
		this.responseDate = responseDate;
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
	  responseDate =  new Timestamp(new java.util.Date().getTime());
	  
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
}
