package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="METER")  
@BatchSize(size = 10)
@NamedQueries({
	//@NamedQuery(name = "ElectricityMetersEntity.findAll", query = "SELECT el.elMeterId,el.typeOfServiceForMeters,el.meterSerialNo,el.meterType,el.meterOwnerShip,el.meterStatus,el.createdBy,a.accountId,a.accountNo,a.person.firstName,a.person.lastName,(select pp.property_No from Property pp where pp.propertyId = a.propertyId) FROM ElectricityMetersEntity el INNER JOIN el.account a ORDER BY el.elMeterId DESC"),
	@NamedQuery(name = "ElectricityMetersEntity.findAll", query = "SELECT el FROM ElectricityMetersEntity el ORDER BY el.elMeterId DESC"),
	@NamedQuery(name = "ElectricityMetersEntity.setMetersStatus", query = "UPDATE ElectricityMetersEntity el SET el.meterStatus = :status WHERE el.elMeterId = :elMeterId"),
	@NamedQuery(name = "ElectricityMetersEntity.getMeterType", query = "SELECT m.meterType From ElectricityMetersEntity m Where m.account.accountId=:accountId and m.typeOfServiceForMeters=:serviceType and m.meterStatus='In Service'"),
	@NamedQuery(name = "ElectricityMetersEntity.findPersonForFilters", query = "SELECT DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle FROM ElectricityMetersEntity em INNER JOIN em.account a INNER JOIN a.person p"),
	@NamedQuery(name = "ElectricityMetersEntity.getMeterByMeterSerialNo", query = "SELECT m From ElectricityMetersEntity m Where m.meterSerialNo=:meterSerialNo"),
	@NamedQuery(name = "ElectricityMetersEntity.getMeter", query = "SELECT m From ElectricityMetersEntity m Where m.account.accountId=:accountId and m.typeOfServiceForMeters=:typeOfService and meterStatus='In Service'"),
	@NamedQuery(name = "ElectricityMetersEntity.proPertyName", query = "SELECT (select pp.property_No from Property pp where pp.propertyId = a.propertyId) FROM ElectricityMetersEntity el INNER JOIN el.account a ORDER BY el.elMeterId DESC"),
	@NamedQuery(name = "ElectricityMetersEntity.proPertyNameById", query = "select pp.property_No from Property pp where pp.propertyId =:propertyId"),
})

/*MeterParametersEntity.getMeterType*/
public class ElectricityMetersEntity {

	@Id
	@Column(name="ELM_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "electricityMeter_seq", sequenceName = "ELECTRICITY_METER_SEQ") 
	@GeneratedValue(generator = "electricityMeter_seq") 
	private int elMeterId;
	
	@Transient
	private int accountId;
	
	@Transient
	private String property_No;
	
	@Transient
	private int servicePointId;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfServiceForMeters;
		
	@Column(name="METER_SL_NO")
	private String meterSerialNo;
	
	@Column(name="METER_TYPE")
	private String meterType;
	
	@Column(name="METER_OWNERSHIP")
	private String meterOwnerShip;
		
	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Account account;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Meter Status Sholud Not Be Empty")
	private  String meterStatus;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getElMeterId() {
		return elMeterId;
	}

	public void setElMeterId(int elMeterId) {
		this.elMeterId = elMeterId;
	}

	public String getTypeOfServiceForMeters() {
		return typeOfServiceForMeters;
	}

	public void setTypeOfServiceForMeters(String typeOfServiceForMeters) {
		this.typeOfServiceForMeters = typeOfServiceForMeters;
	}
	
	public String getMeterSerialNo() {
		return meterSerialNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setMeterSerialNo(String meterSerialNo) {
		this.meterSerialNo = meterSerialNo;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getMeterOwnerShip() {
		return meterOwnerShip;
	}

	public void setMeterOwnerShip(String meterOwnerShip) {
		this.meterOwnerShip = meterOwnerShip;
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

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getServicePointId() {
		return servicePointId;
	}

	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}

	public String getMeterStatus() {
		return meterStatus;
	}

	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}

	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}
	
}
