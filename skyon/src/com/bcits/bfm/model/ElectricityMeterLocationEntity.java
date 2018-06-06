package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="METER_LOCATION")  
@NamedQueries({
	@NamedQuery(name = "ElectricityMeterLocationEntity.findAllById", query = "SELECT bli.elMeterLocationId,bli.meterFixedDate,bli.meterFixedBy,bli.intialReading,bli.finalReading,bli.locationStatus,a.accountId,a.accountNo,bli.meterReleaseDate,bli.dgFinalReading,bli.dgIntitalReading FROM ElectricityMeterLocationEntity bli INNER JOIN bli.account a WHERE bli.electricityMetersEntity.elMeterId = :elMeterId ORDER BY bli.elMeterLocationId DESC"),
	@NamedQuery(name = "ElectricityMeterLocationEntity.findAll", query = "SELECT el FROM ElectricityMeterLocationEntity el ORDER BY el.elMeterLocationId DESC"),
	@NamedQuery(name = "ElectricityMeterLocationEntity.setLocationStatus", query = "UPDATE ElectricityMeterLocationEntity el SET el.locationStatus = :locationStatus WHERE el.elMeterLocationId = :elMeterLocationId"),
	@NamedQuery(name = "ElectricityMeterLocationEntity.getAllAccuntNumbers", query = "SELECT  DISTINCT a.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM BillingWizardEntity bw INNER JOIN bw.accountObj a INNER JOIN a.person p WHERE bw.status='Approved' "),
	@NamedQuery(name = "ElectricityMeterLocationEntity.getServiceMasterObj", query = "SELECT sm FROM ServiceMastersEntity sm WHERE sm.accountObj.accountId=:accountId AND sm.typeOfService=:typeOfService"),
})
public class ElectricityMeterLocationEntity implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ELML_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "elMeterLocation_seq", sequenceName = "EL_METER_LOCATION_SEQ") 
	@GeneratedValue(generator = "elMeterLocation_seq")
	private int elMeterLocationId;
	
	/*@Column(name="ELM_ID")
	@NotNull(message = "Meter Id Should Not Be Empty")
	private int elMeterId;*/
	
	@Transient
	private int accountId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ELM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityMetersEntity electricityMetersEntity;
	
	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Account account;
	
	/*@Column(name="SP_ID")
	private int servicePointId;*/
	
	/*@OneToOne
	@JoinColumn(name = "SP_ID")
	private ServicePointEntity servicePointEntity;*/
	
	@Column(name="METER_FIXED_DATE")
	private Date meterFixedDate;
	
	@Column(name="RELEASE_DATE")
	private Date meterReleaseDate;
		
	@Column(name="METER_FIXED_BY")
	private String meterFixedBy;
		
	@Column(name="INITIAL_READING")
	private double intialReading;
	
	@Column(name="FINAL_READING")
	private double finalReading;
	
	@Column(name="DG_INITIAL_READING")
	private double dgIntitalReading;
	
	
	@Column(name="DG_FINAL_READING")
	private double dgFinalReading;
	
	@Column(name="STATUS")
	private  String locationStatus;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
		
	public int getElMeterLocationId() {
		return elMeterLocationId;
	}


	public void setElMeterLocationId(int elMeterLocationId) {
		this.elMeterLocationId = elMeterLocationId;
	}


	/*public int getElMeterId() {
		return elMeterId;
	}


	public void setElMeterId(int elMeterId) {
		this.elMeterId = elMeterId;
	}*/


	public ElectricityMetersEntity getElectricityMetersEntity() {
		return electricityMetersEntity;
	}


	public void setElectricityMetersEntity(
			ElectricityMetersEntity electricityMetersEntity) {
		this.electricityMetersEntity = electricityMetersEntity;
	}


	/*public int getServicePointId() {
		return servicePointId;
	}


	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}*/


	/*public ServicePointEntity getServicePointEntity() {
		return servicePointEntity;
	}


	public void setServicePointEntity(ServicePointEntity servicePointEntity) {
		this.servicePointEntity = servicePointEntity;
	}*/


	public Date getMeterFixedDate() {
		return meterFixedDate;
	}


	public void setMeterFixedDate(Date meterFixedDate) {
		this.meterFixedDate = meterFixedDate;
	}


	public String getMeterFixedBy() {
		return meterFixedBy;
	}


	public void setMeterFixedBy(String meterFixedBy) {
		this.meterFixedBy = meterFixedBy;
	}


	public double getIntialReading() {
		return intialReading;
	}


	public void setIntialReading(double intialReading) {
		this.intialReading = intialReading;
	}


	public double getFinalReading() {
		return finalReading;
	}


	public void setFinalReading(double finalReading) {
		this.finalReading = finalReading;
	}


	public String getLocationStatus() {
		return locationStatus;
	}


	public void setLocationStatus(String locationStatus) {
		this.locationStatus = locationStatus;
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


	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }


	public Date getMeterReleaseDate() {
		return meterReleaseDate;
	}


	public void setMeterReleaseDate(Date meterReleaseDate) {
		this.meterReleaseDate = meterReleaseDate;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public int getAccountId() {
		return accountId;
	}


	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	public double getDgFinalReading() {
		return dgFinalReading;
	}


	public void setDgFinalReading(double dgFinalReading) {
		this.dgFinalReading = dgFinalReading;
	}


	public double getDgIntitalReading() {
		return dgIntitalReading;
	}


	public void setDgIntitalReading(double dgIntitalReading) {
		this.dgIntitalReading = dgIntitalReading;
	}
	
}
