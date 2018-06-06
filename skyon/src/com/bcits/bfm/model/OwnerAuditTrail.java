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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="OWNER_AUDIT_TRAIL_NEW")

@NamedQueries({
	
	@NamedQuery(name="OwnerAudit.findAllData",query="SELECT o FROM OwnerAuditTrail o ORDER BY o.id DESC"),
	@NamedQuery(name="OwnerAllFiltern.readAllDataFilter",query="SELECT  o FROM OwnerAuditTrail o ORDER BY o.id DESC"),
})
public class OwnerAuditTrail {

	@Id
	@SequenceGenerator(name = "CN_SEQ", sequenceName = "OWNER_AUDIT_TRAIL_NEW_SEQ")
	@GeneratedValue(generator = "CN_SEQ")
	@Column(name="ID")
	private int id;
	
	@Column(name="OWNER_NAME")
	private String ownerName;
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	@Column(name="UPDATED_FIELD")
	private String updated_field;
	
	@Column(name="OWNER_PREVOUS")
	private String owner_previous;
	
	@Column(name="OWNER_CURRENT")
	private String owner_current;

	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDate ;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="OWNER_ID_P")
	private int ownerId;
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdated_field() {
		return updated_field;
	}

	public void setUpdated_field(String updated_field) {
		this.updated_field = updated_field;
	}

	public String getOwner_previous() {
		return owner_previous;
	}

	public void setOwner_previous(String owner_previous) {
		this.owner_previous = owner_previous;
	}

	public String getOwner_current() {
		return owner_current;
	}

	public void setOwner_current(String owner_current) {
		this.owner_current = owner_current;
	}
	
	
	@PrePersist
	 protected void onCreate() {
		
		 java.util.Date date= new java.util.Date();
		  lastUpdatedDate = new Timestamp(date.getTime());
		  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	
}
