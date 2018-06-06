package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="PREPAID_SERVICE_MASTER")
@NamedQueries({
	@NamedQuery(name="PrepaidServiceMaster.getData",query="SELECT ps.serviceId,ps.service_Name,ps.fromDate,ps.toDate,ps.status  FROM PrepaidServiceMaster ps"),
	@NamedQuery(name="PrepaidServiceMaster.getDropService",query="SELECT ps.serviceId,ps.service_Name FROM PrepaidServiceMaster ps"),
	@NamedQuery(name="PrepaidServiceMaster.setStatus",query="UPDATE PrepaidServiceMaster p SET p.status=:status Where p.serviceId=:serviceId"),
	@NamedQuery(name="PrepaidServiceMaster.minMaxDate",query="SELECT MIN(ps.fromDate),MAX(ps.toDate) FROM PrepaidServiceMaster ps where ps.serviceId=:serviceId  and ps.status='Active'")
})
public class PrepaidServiceMaster {

	@Id
	@SequenceGenerator(name="PREPAID_SERVICE_MASTER_SEQ",sequenceName="PREPAID_SERVICE_MASTER_SEQ")
	@GeneratedValue(generator="PREPAID_SERVICE_MASTER_SEQ")
	@Column(name="SID")
	private int serviceId;
	
	@Column(name="SERVICE_NAME")
	private String service_Name;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FROM_DATE")
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TO_DATE")
	private Date toDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Column(name="STATUS")
	private String status;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getService_Name() {
		return service_Name;
	}

	public void setService_Name(String service_Name) {
		this.service_Name = service_Name;
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
	
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
}
