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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="TICKET_ASSIGN")  
@NamedQueries({
	@NamedQuery(name = "TicketAssignEntity.findAllById", query = "SELECT ta.assignId,ta.ticketId,ta.urId,ta.assignComments,ta.assignDate,ta.createdBy,ta.lastUpdatedBy,ta.lastUpdatedDT,p.firstName,p.lastName,uo.personId FROM TicketAssignEntity ta INNER JOIN ta.usersObj uo INNER JOIN uo.person p WHERE ta.ticketId = :ticketId ORDER BY ta.assignId DESC "),
	@NamedQuery(name = "TicketAssignEntity.findAllByAscOrder", query = "SELECT bli FROM TicketAssignEntity bli WHERE bli.openNewTicketEntity.ticketId = :ticketId ORDER BY bli.assignId ASC"),
	@NamedQuery(name = "TicketAssignEntity.findAllData", query = "SELECT ta.assignId,ta.ticketId,ta.urId,ta.assignComments,ta.assignDate,ta.createdBy,ta.lastUpdatedBy,ta.lastUpdatedDT,p.firstName,p.lastName,uo.personId,ont.ticketNumber,po.firstName,po.lastName,ont.priorityLevel,ont.issueSubject,ont.issueDetails,ont.ticketStatus,ont.ticketCreatedDate,ta.levelOFSLA,ont.typeOfTicket,ont.propertyId FROM TicketAssignEntity ta,Person po INNER JOIN ta.usersObj uo INNER JOIN uo.person p INNER JOIN ta.openNewTicketEntity ont WHERE po.personId=ont.personId AND ta.escalated ='Yes' ORDER BY ta.assignId DESC"),
	@NamedQuery(name="TicketAssignEntity.findUsers",query="SELECT u.deptId,u.personId,u.urId,u.urLoginName,deg.dn_Name,p.firstName,p.lastName FROM Users u INNER JOIN u.designation deg INNER JOIN u.person p WHERE u.deptId=:deptId AND u.status='Active'")
})
public class TicketAssignEntity {

	@Id
	@Column(name="ASSIGN_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "ticketAssign_seq", sequenceName = "TICKET_ASSIGN_SEQ") 
	@GeneratedValue(generator = "ticketAssign_seq")
	private int assignId;
	
	@Transient
	private String urLoginName; 
	
	@Column(name="TICKET_ID")
	private int ticketId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID",insertable = false, updatable = false, nullable = false)
	private OpenNewTicketEntity openNewTicketEntity;
	
	@Column(name="UR_ID", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message="User is not selected")
	private int urId;
	
	@OneToOne	 
	@JoinColumn(name = "UR_ID", insertable = false, updatable = false, nullable = false)
    private Users usersObj;
	
	@Column(name="ESCLATED")
	private String escalated;
	
	@Column(name="LEVEL_OF_SLA")
	private int levelOFSLA;
	
	@Column(name="COMMENTS")
	@NotEmpty(message="Assign comments is required")
	private String assignComments;
	
	@Column(name="ASSIGN_DT")
	private Timestamp assignDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	public int getAssignId() {
		return assignId;
	}

	public void setAssignId(int assignId) {
		this.assignId = assignId;
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

	public int getUrId() {
		return urId;
	}

	public void setUrId(int urId) {
		this.urId = urId;
	}

	public Users getUsersObj() {
		return usersObj;
	}

	public void setUsersObj(Users usersObj) {
		this.usersObj = usersObj;
	}

	public String getAssignComments() {
		return assignComments;
	}

	public void setAssignComments(String assignComments) {
		this.assignComments = assignComments;
	}

	public Timestamp getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Timestamp assignDate) {
		this.assignDate = assignDate;
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

	public String getUrLoginName() {
		return urLoginName;
	}

	public void setUrLoginName(String urLoginName) {
		this.urLoginName = urLoginName;
	}

	public String getEscalated() {
		return escalated;
	}

	public void setEscalated(String escalated) {
		this.escalated = escalated;
	}

	public int getLevelOFSLA() {
		return levelOFSLA;
	}

	public void setLevelOFSLA(int levelOFSLA) {
		this.levelOFSLA = levelOFSLA;
	}

	/*@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");	  
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }*/
}
