package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="SP_INSTRUCTIONS")  
@NamedQueries({
	@NamedQuery(name = "ServicePointInstructionsEntity.findAllById", query = "SELECT si.spInstructionId,si.instructionDate,si.alert,si.instructions,si.status,si.createdBy,sr.srId FROM ServicePointInstructionsEntity si INNER JOIN si.serviceRoute sr WHERE sr.srId = :srId ORDER BY si.spInstructionId DESC"),
	@NamedQuery(name = "ServicePointInstructionsEntity.findAll", query = "SELECT el FROM ServicePointInstructionsEntity el ORDER BY el.spInstructionId DESC"),
	@NamedQuery(name = "ServicePointInstructionsEntity.setServicePointStatus", query = "UPDATE ServicePointInstructionsEntity el SET el.status = :status WHERE el.spInstructionId = :srId"),
	@NamedQuery(name="ServicePointInstructionsEntity.updateInstructionStatusFromInnerGrid",query="UPDATE ServicePointInstructionsEntity a SET a.status = :status WHERE a.spInstructionId = :spInstructionId")
})
public class ServicePointInstructionsEntity {

	@Id
	@Column(name="SPI_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "spInstructions_seq", sequenceName = "SP_INSTRUCTIONS_SEQ") 
	@GeneratedValue(generator = "spInstructions_seq")
	private int spInstructionId;
	
	@Column(name="SR_ID")
	private int srId;
	
	@ManyToOne
    @JoinColumn(name = "SR_ID",insertable = false, updatable = false, nullable = false)
	private ServiceRoute serviceRoute;
	
	@Column(name="SPI_DT")
	private Date instructionDate;
	
	@Column(name="ALERT")
	private String alert;
	
	@Column(name="INSTRUCTIONS")
	private String instructions;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Instruction Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	public int getSrId() {
		return srId;
	}

	public void setSrId(int srId) {
		this.srId = srId;
	}

	public int getSpInstructionId() {
		return spInstructionId;
	}

	public void setSpInstructionId(int spInstructionId) {
		this.spInstructionId = spInstructionId;
	}

	public ServiceRoute getServiceRoute() {
		return serviceRoute;
	}

	public void setServiceRoute(ServiceRoute serviceRoute) {
		this.serviceRoute = serviceRoute;
	}

	public Date getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(Date instructionDate) {
		this.instructionDate = instructionDate;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	
	/*public int getServicePointId() {
		return servicePointId;
	}

	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}*/

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
