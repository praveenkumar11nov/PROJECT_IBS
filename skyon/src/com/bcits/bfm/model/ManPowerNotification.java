package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MANPOWER_NOTIFICATION")
@NamedQueries({ 
	//@NamedQuery(name = "ManPowerNotification.findAllList", query = "SELECT mpn.manPowerId,mpn.requestId,mpn.desigId,mpn.requestedBy,mpn.requestedDate,mpn.manPowerStatus FROM ManPowerNotification mpn WHERE mpn.desigId=:designationId"),
	@NamedQuery(name = "ManPowerNotification.findAllList", query = "SELECT mpn.manPowerId,mpn.requestId,mpn.desigId,mpn.requestedBy,mpn.requestedDate,mpn.manPowerStatus FROM ManPowerNotification mpn WHERE mpn.requestId IN(SELECT u.urId FROM Users u,TransactionDetail t JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g WHERE p.transId=t.tId AND t.lNum=p.reqInLevel AND mpn.desigId=t.dnId AND u.status=:status1 AND p.personType LIKE '%Staff%')"),
	@NamedQuery(name = "ManPowerNotification.updateManPowerNotificationStatus", query = "UPDATE ManPowerNotification mpn SET mpn.manPowerStatus=1 WHERE mpn.manPowerId=:manPowerId AND mpn.desigId=:desigId"),
	@NamedQuery(name = "ManPowerNotification.getDesignations", query = "SELECT t from TransactionDetail t where t.tId=:transId "),

})


public class ManPowerNotification {

	@Id
	@SequenceGenerator(name = "manpower_notification_seq", sequenceName = "MANPOWER_NOTIFICATION_SEQ")
	@GeneratedValue(generator = "manpower_notification_seq")
	@Column(name = "MANPOWER_ID")
	private int manPowerId;
	
	@Column(name="REQUEST_ID")
	private int requestId;
	
	@Column(name = "DESIG_ID")
	private int desigId;

	@Column(name="REQUESTED_BY")
	private String requestedBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name="REQUESTED_DATE")
	private Date requestedDate;
		
	@Column(name="STATUS")
	private int manPowerStatus;

	public int getManPowerId() {
		return manPowerId;
	}

	public void setManPowerId(int manPowerId) {
		this.manPowerId = manPowerId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getDesigId() {
		return desigId;
	}

	public void setDesigId(int desigId) {
		this.desigId = desigId;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public int getManPowerStatus() {
		return manPowerStatus;
	}

	public void setManPowerStatus(int manPowerStatus) {
		this.manPowerStatus = manPowerStatus;
	}

}
