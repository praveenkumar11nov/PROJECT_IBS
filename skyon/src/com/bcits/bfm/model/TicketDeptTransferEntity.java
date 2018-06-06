package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="TICKET_DEPT_TRANSFER")  
@NamedQueries({
	@NamedQuery(name = "TicketDeptTransferEntity.findAllById", query = "SELECT tdt.deptTransId,tdt.ticketId,tdt.dept_Id,tdt.comments,tdt.prevDeptId,tdt.transferDate,tdt.createdBy,tdt.lastUpdatedBy,tdt.lastUpdatedDT,do.dept_Name,pdo.dept_Name,ot.ticketNumber,ot.priorityLevel,ot.issueSubject,ot.issueDetails,ot.ticketStatus,ot.ticketCreatedDate,ot.topicId,hto.topicName,ot.typeOfTicket,ot.personId FROM TicketDeptTransferEntity tdt INNER JOIN tdt.departmentObj do INNER JOIN tdt.prevDepartmentObj pdo INNER JOIN tdt.openNewTicketEntity ot INNER JOIN ot.helpTopicObj hto WHERE tdt.openNewTicketEntity.ticketId = :ticketId ORDER BY tdt.deptTransId DESC"),
	@NamedQuery(name = "TicketDeptTransferEntity.findAllData", query = "SELECT tdt.deptTransId,tdt.ticketId,tdt.dept_Id,tdt.comments,tdt.prevDeptId,tdt.transferDate,tdt.createdBy,tdt.lastUpdatedBy,tdt.lastUpdatedDT,do.dept_Name,pdo.dept_Name,ot.ticketNumber,ot.priorityLevel,ot.issueSubject,ot.issueDetails,ot.ticketStatus,ot.ticketCreatedDate,ot.topicId,hto.topicName,ot.typeOfTicket,ot.personId FROM TicketDeptTransferEntity tdt INNER JOIN tdt.departmentObj do INNER JOIN tdt.prevDepartmentObj pdo INNER JOIN tdt.openNewTicketEntity ot INNER JOIN ot.helpTopicObj hto  WHERE EXTRACT(month from tdt.transferDate)=(select EXTRACT(month from tdt.transferDate) from TicketDeptTransferEntity tdt where tdt.transferDate = (select MAX(tdt.transferDate) from TicketDeptTransferEntity tdt)) AND EXTRACT(year from tdt.transferDate)=(select EXTRACT(year from tdt.transferDate) from TicketDeptTransferEntity tdt where tdt.transferDate = (select MAX(tdt.transferDate) from TicketDeptTransferEntity tdt)) ORDER BY tdt.deptTransId DESC"),
	@NamedQuery(name = "TicketDeptTransferEntity.findAllByAscOrder", query = "SELECT bli FROM TicketDeptTransferEntity bli WHERE bli.openNewTicketEntity.ticketId = :ticketId ORDER BY bli.deptTransId ASC"),
	@NamedQuery(name = "TicketDeptTransferEntity.deptIdUpdate", query = "UPDATE OpenNewTicketEntity ope SET ope.dept_Id = :dept_Id WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "TicketDeptTransferEntity.updateTicketDeptAcceptanceStatus", query = "UPDATE OpenNewTicketEntity ope SET ope.deptAcceptanceStatus = null WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "TicketDeptTransferEntity.updateTicketStatus", query = "UPDATE OpenNewTicketEntity ope SET ope.ticketStatus = 'Open' WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "TicketDeptTransferEntity.updateTicketLastResponse", query = "UPDATE OpenNewTicketEntity ope SET ope.lastResonse = null WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "TicketDeptTransferEntity.helpTopicUpdate", query = "UPDATE OpenNewTicketEntity ope SET ope.topicId = :topicId WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "TicketDeptTransferEntity.findAllDataByMonth", query = "SELECT tdt.deptTransId,tdt.ticketId,tdt.dept_Id,tdt.comments,tdt.prevDeptId,tdt.transferDate,tdt.createdBy,tdt.lastUpdatedBy,tdt.lastUpdatedDT,do.dept_Name,pdo.dept_Name,ot.ticketNumber,ot.priorityLevel,ot.issueSubject,ot.issueDetails,ot.ticketStatus,ot.ticketCreatedDate,ot.topicId,hto.topicName,ot.typeOfTicket,ot.personId FROM TicketDeptTransferEntity tdt INNER JOIN tdt.departmentObj do INNER JOIN tdt.prevDepartmentObj pdo INNER JOIN tdt.openNewTicketEntity ot INNER JOIN ot.helpTopicObj hto WHERE TRUNC(tdt.transferDate) BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD') AND TO_DATE(:toDate,'YYYY-MM-DD') ORDER BY tdt.deptTransId DESC"),
})
public class TicketDeptTransferEntity {

	@Id
	@Column(name="DEPT_TRANS_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "ticketDeptTransfer_seq", sequenceName = "TICKET_DEPT_TRANSFER_SEQ") 
	@GeneratedValue(generator = "ticketDeptTransfer_seq")
	private int deptTransId;
	
	@Column(name="TICKET_ID")
	private int ticketId;
	
	@Transient
	private int topicId;
	
	@ManyToOne
    @JoinColumn(name = "TICKET_ID",insertable = false, updatable = false, nullable = false)
	private OpenNewTicketEntity openNewTicketEntity;
	
	@Column(name="DEPT_ID", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message="Department is not selected")
	private int dept_Id;
	
	@OneToOne	 
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
    private Department departmentObj;
	
	@Column(name="COMMENTS")
	@NotEmpty(message="Department comments are required")
	private String comments;
	
	@Column(name="PREV_DEPT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int prevDeptId;
	
	@OneToOne	 
	@JoinColumn(name = "PREV_DEPT_ID", insertable = false, updatable = false, nullable = false)
    private Department prevDepartmentObj;
	
	@Column(name="TRANSFER_DT")
	private Timestamp transferDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
				
	public int getDeptTransId() {
		return deptTransId;
	}

	public void setDeptTransId(int deptTransId) {
		this.deptTransId = deptTransId;
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

	public int getDept_Id() {
		return dept_Id;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Timestamp transferDate) {
		this.transferDate = transferDate;
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

	public int getPrevDeptId() {
		return prevDeptId;
	}

	public void setPrevDeptId(int prevDeptId) {
		this.prevDeptId = prevDeptId;
	}

	public Department getPrevDepartmentObj() {
		return prevDepartmentObj;
	}

	public void setPrevDepartmentObj(Department prevDepartmentObj) {
		this.prevDepartmentObj = prevDepartmentObj;
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

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
}
