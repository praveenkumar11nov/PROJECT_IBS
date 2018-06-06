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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="CLASSIFIED_ACQ")
@NamedQueries({
	@NamedQuery(name="ClassifiedEntity.readALLData",query="SELECT c.cId,c.property_No,c.customer_Name,c.mobile_No,c.emailId,c.information  FROM ClassifiedEntity c")
})
public class ClassifiedEntity {

	@Id
	@SequenceGenerator(name="CLASSIFIED_ACQ_SEQ", sequenceName="CLASSIFIED_ACQ_SEQ")
	@GeneratedValue(generator="CLASSIFIED_ACQ_SEQ")
	@Column(name="CID",unique=true , nullable=false, precision=11, scale=0)
	private int cId;
	
	@Column(name="PROPERTY_NO")
	private String property_No;
	
	@Column(name="CUSTOMER_NAME")
	private String customer_Name;
	
	@Column(name="MOBILE_NO")
	private String mobile_No;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="INFORMATION")
	private String information;
 
	@Column(name="CREATED_BY")
	private String createdBy;
	
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public String getCustomer_Name() {
		return customer_Name;
	}

	public void setCustomer_Name(String customer_Name) {
		this.customer_Name = customer_Name;
	}

	public String getMobile_No() {
		return mobile_No;
	}

	public void setMobile_No(String mobile_No) {
		this.mobile_No = mobile_No;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
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

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}

	
	
}
