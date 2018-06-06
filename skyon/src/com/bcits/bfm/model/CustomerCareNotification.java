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

@Entity
@Table(name = "CUSTOMERCARE_NOTIFICATION")
@NamedQueries({ 	
	@NamedQuery(name = "CustomerCareNotification.findAll", query = "SELECT cn FROM CustomerCareNotification cn ORDER BY cn.cnId DESC"),
	

})
public class CustomerCareNotification {
	
	private int cnId;
	private String blockId;
	private String propertyId;
	private int viewStatus;
	private String message;
	private String subject;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDt;
	
	public CustomerCareNotification() {
		super();
	}

	public CustomerCareNotification(int cnId, String blockId,
			String propertyId, int viewStatus, String message, String subject,
			String createdBy, String lastUpdatedBy, Date lastUpdatedDt) {
		super();
		this.cnId = cnId;
		this.blockId = blockId;
		this.propertyId = propertyId;
		this.viewStatus = viewStatus;
		this.message = message;
		this.subject = subject;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}


	@Id     
    @SequenceGenerator(name = "cutNoti_seq", sequenceName = "CUSTOMERCARENOTIFICATION_SEQ")
	@GeneratedValue(generator = "cutNoti_seq")
    @Column(name="CN_ID")
	public int getCnId() {
		return cnId;
	}

	public void setCnId(int cnId) {
		this.cnId = cnId;
	}
	 @Column(name="BLOCK_ID")
	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	 @Column(name="PROPERTY_ID")
	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	 @Column(name="VIEW_STATUS")
	public int getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(int viewStatus) {
		this.viewStatus = viewStatus;
	}
	 @Column(name="MESSAGE")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@Column(name="SUBJECT")
	 public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	 @Column(name="LASTUPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	 @Column(name="LASTUPDATED_DATE")
	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	
	

}
