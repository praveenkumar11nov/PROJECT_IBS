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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="METER_PARAMETERS")  
@NamedQueries({
	@NamedQuery(name = "ElectricityMeterParametersEntity.findAllById", query = "SELECT mp.elMeterParameterId,mp.mpmId,mp.elMeterParameterValue,mp.elMasterParameterDataType,mp.notes,mp.elMeterParameterSequence,mp.status,mpm.mpmName FROM ElectricityMeterParametersEntity mp INNER JOIN mp.parameterMasterObj mpm WHERE mp.electricityMetersEntity.elMeterId = :elMeterId ORDER BY mp.elMeterParameterSequence ASC"),
	@NamedQuery(name = "ElectricityMeterParametersEntity.findAll", query = "SELECT el FROM ElectricityMeterParametersEntity el ORDER BY el.elMeterParameterId DESC"),
	@NamedQuery(name = "ElectricityMeterParametersEntity.updateParameterStatusFromInnerGrid",query="UPDATE ElectricityMeterParametersEntity a SET a.status = :status WHERE a.elMeterParameterId = :elMeterParameterId"),
	@NamedQuery(name = "ElectricityMeterParametersEntity.getMeterParameterNamesList",query="SELECT mp.mpmId,mp.mpmName FROM MeterParameterMaster mp WHERE mp.mpmserviceType=:typeOfService AND mp.status='Active' AND mp.mpmId NOT IN (SELECT empe.mpmId FROM ElectricityMeterParametersEntity empe WHERE empe.electricityMetersEntity.elMeterId=:meterId)"),
	@NamedQuery(name = "ElectricityMeterParametersEntity.getMeterParameter",query="Select em.elMeterParameterValue from ElectricityMeterParametersEntity em inner join em.electricityMetersEntity eme where em.electricityMetersEntity.elMeterId=:elMeterId and em.parameterMasterObj.mpmId = (Select mpm.mpmId from MeterParameterMaster mpm where mpm.mpmserviceType=:typeOfServiceForMeters and mpm.mpmName=:string and mpm.status='Active')"),
//	return (String) entityManager.createNamedQuery("MeterParameterMaster.getMeterParameter").setParameter("elMeterId", elMeterId).setParameter("accountId", accountId).setParameter("typeOfServiceForMeters", typeOfServiceForMeters).setParameter("string", string).getSingleResult();
})
public class ElectricityMeterParametersEntity { 

	@Id
	@Column(name="ELMP_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "elMeterParameters_seq", sequenceName = "EL_METER_PARAMETERS_SEQ") 
	@GeneratedValue(generator = "elMeterParameters_seq")
	private int elMeterParameterId;
	
	/*@Column(name="ELM_ID")
	@NotNull(message = "Meter Id Should Not Be Empty")
	private int elMeterId;*/
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ELM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityMetersEntity electricityMetersEntity;
	
	@Column(name="ELMP_MPM_ID")
	private int mpmId;
	
	@OneToOne
	@JoinColumn(name = "ELMP_MPM_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private MeterParameterMaster parameterMasterObj;
	
	@Column(name="ELMP_MPM_DATA_TYPE")
	private String elMasterParameterDataType;
	
	@Column(name="ELMP_VALUE")
	private String elMeterParameterValue;
	
	@Column(name="NOTES")
	private String notes;
	
	@Column(name="ELMP_SEQUENCE")
	private int elMeterParameterSequence;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Meter Parameter Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getElMeterParameterId() {
		return elMeterParameterId;
	}

	public void setElMeterParameterId(int elMeterParameterId) {
		this.elMeterParameterId = elMeterParameterId;
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
	
	public int getMpmId() {
		return mpmId;
	}


	public void setMpmId(int mpmId) {
		this.mpmId = mpmId;
	}


	public String getElMasterParameterDataType() {
		return elMasterParameterDataType;
	}


	public void setElMasterParameterDataType(String elMasterParameterDataType) {
		this.elMasterParameterDataType = elMasterParameterDataType;
	}


	public String getElMeterParameterValue() {
		return elMeterParameterValue;
	}


	public void setElMeterParameterValue(String elMeterParameterValue) {
		this.elMeterParameterValue = elMeterParameterValue;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public int getElMeterParameterSequence() {
		return elMeterParameterSequence;
	}


	public void setElMeterParameterSequence(int elMeterParameterSequence) {
		this.elMeterParameterSequence = elMeterParameterSequence;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
	
	public MeterParameterMaster getParameterMasterObj() {
		return parameterMasterObj;
	}


	public void setParameterMasterObj(MeterParameterMaster parameterMasterObj) {
		this.parameterMasterObj = parameterMasterObj;
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
	
}
