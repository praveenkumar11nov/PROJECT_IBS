package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="SERVICE_BOOKING")
@NamedQueries({
	
	
	@NamedQuery(name="ServiceBooking.findAllData",query="select c from ServiceBooking c"),
	@NamedQuery(name="ServiceBooking.findAllByUsername",query="select c from ServiceBooking c WHERE c.createdBy=:createdBy"),
	@NamedQuery(name = "ServiceBooking.UpdateStatus",query="UPDATE ServiceBooking t SET  t.action= :status WHERE t.bId= :bId"),
	@NamedQuery(name = "ServiceBookings.UpdateStatus",query="UPDATE ServiceBooking t SET  t.bookaction= :status WHERE t.bId= :bId"),
	@NamedQuery(name="ServiceBoooking.updateStatus",query="UPDATE ServiceBooking u SET u.serviceStatus = :serviceStatus WHERE u.action='Closed'"),
	@NamedQuery(name="ServiceBoookings.updateStatus",query="UPDATE ServiceBooking u SET u.serviceStatus = :serviceStatus WHERE u.action='Opened'"),

	@NamedQuery(name="ServiceBoookings.updateStatusBook",query="UPDATE ServiceBooking u SET u.serviceStatus = :serviceStatus WHERE u.bookaction='Rejected'"),
	@NamedQuery(name="ServiceBoooking.updateStatusBook",query="UPDATE ServiceBooking u SET u.serviceStatus = :serviceStatus WHERE u.bookaction='Approved'"),
	})
public class ServiceBooking {

	@Id
	@SequenceGenerator(name = "service_seq", sequenceName = "SERVICE_SEQ") 
	@GeneratedValue(generator = "service_seq")
	@Column(name = "B_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int bId;
	
	@Column(name = "S_ID")
	private int sId;

	@Column(name = "SERVICE_STATUS")
	private String serviceStatus;
	
	@Column(name = "ACTION")
	private String action;
	
	@Column(name = "BLOCK_NO")
	private String blocks;

	@Column(name = "PROPERTY")
	private int property;

	@Column(name = "NAME")
	private int name;
	
	@Column(name = "START_TIME")
	private String startTime;
	
	@Column(name = "END_TIME")
	private String endTime;

	@Column(name = "CRFEATED_BY")
	private String createdBy;
	
	@Column(name = "BOOK_ACTION")
	private String bookaction;

	@Transient
	private Timestamp createdDate;

	

	
	@Column(name = "DATE_NEW")
	private String date;

	
	
	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	
	

	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}



	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBookaction() {
		return bookaction;
	}

	public void setBookaction(String bookaction) {
		this.bookaction = bookaction;
	}

	

	


	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public String getBlocks() {
		return blocks;
	}

	public void setBlocks(String blocks) {
		this.blocks = blocks;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

}
