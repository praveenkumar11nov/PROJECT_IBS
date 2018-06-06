package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;

import com.bcits.bfm.util.SessionData;

@Entity
public class AverageCalculation {

	@Id
	@Column(name="AVG_ID")
	@SequenceGenerator(name = "avg_seq", sequenceName = "AVG_SEQ") 
	@GeneratedValue(generator = "avg_seq")
	private int avgId;
	
	@Column(name="ACCOUNT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int accountId;
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false, nullable = false)
    private Account accountObj;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfService;
	
	@Column(name="AVG_TYPE")
	private String avgType;
	
	@Column(name="AVG_DATE")
	private Date avgdate;
	
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	
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
