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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JobNotification entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JOB_NOTIFICATION")
@NamedQueries({
	@NamedQuery(name = "JobCards.UpdateJobNotificationStatus", query = "UPDATE JobNotification jobNotification SET  jobNotification.status = :Status WHERE jobNotification.jnId = :jnId"),
	@NamedQuery(name = "JobCards.readJobNotification", query = "SELECT jobNotification FROM JobNotification jobNotification WHERE jobNotification.jobCards=:jcId"),
	@NamedQuery(name = "JobNotification.findJobNotificationList", query = "SELECT jn FROM JobNotification jn"),
	@NamedQuery(name = "JobNotification.deleteJobObjective", query = "DELETE FROM JobNotification jn WHERE jn.jnId=:jnId"),
})
public class JobNotification implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Fields

	private int jnId;
	private Property property;	
	private JobCards jobCards;
	private Blocks block;
	private int blockId;
	private String notificationType;
	private String notification;
	private String notify;
	private int notificationMembers;
	private Timestamp jnDt;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String status;

	
	// Property accessors
	@Id
	@Column(name = "JN_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_NOTIFICATION_SEQ", sequenceName = "JOB_NOTIFICATION_SEQ")
	@GeneratedValue(generator = "JOB_NOTIFICATION_SEQ")
	public int getJnId() {
		return this.jnId;
	}

	public void setJnId(int jnId) {
		this.jnId = jnId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPERTY_ID", nullable = true)
	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@Column(name = "BLOCK_ID", nullable = true,insertable=false,updatable=false)
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JC_ID", nullable = false)
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BLOCK_ID", nullable = false)
	public Blocks getBlocks() {
		return this.block;
	}

	public void setBlocks(Blocks block) {
		this.block = block;
	}

	@Column(name = "NOTIFICATION_TYPE", nullable = false, length = 45)
	public String getNotificationType() {
		return this.notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	@Column(name = "NOTIFICATION", length = 500)
	public String getNotification() {
		return this.notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	@Column(name = "NOTIFY", nullable = false, length = 45)
	public String getNotify() {
		return this.notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	@Column(name = "NOTIFICATION_MEMBERS", length = 6)
	public int getNotificationMembers() {
		return this.notificationMembers;
	}

	public void setNotificationMembers(int notificationMembers) {
		this.notificationMembers = notificationMembers;
	}

	@Column(name = "JN_DT", nullable = false, length = 11)
	public Timestamp getJnDt() {
		return this.jnDt;
	}

	public void setJnDt(Timestamp jnDt) {
		this.jnDt = jnDt;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Column(name = "STATUS", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}