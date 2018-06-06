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

import org.hibernate.annotations.BatchSize;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name="METER_PARAMETER_MASTER")
@BatchSize(size = 10)
@NamedQueries({
	@NamedQuery(name="MeterParameterMaster.findAll",query="SELECT mpm from MeterParameterMaster mpm ORDER BY mpm.mpmserviceType,mpm.mpmSequence ASC"),
	@NamedQuery(name = "MeterParameterMaster.UpdateStatus",query="UPDATE MeterParameterMaster m1 SET m1.status = :status WHERE m1.mpmId = :mpmId"),
	@NamedQuery(name="MeterParameterMaster.getNameAndValue",query="select mpm.mpmName,mp.elMeterParameterValue from ElectricityMeterParametersEntity mp, MeterParameterMaster mpm where mp.parameterMasterObj = mpm.mpmId and mp.electricityMetersEntity.elMeterId=:elMeterId"),
})
public class MeterParameterMaster implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="seqMeterParameterMaster", sequenceName="METER_SEQUENCE_MASTER_SEQ")
	@GeneratedValue(generator="seqMeterParameterMaster")
	@Column(name="MPM_ID", unique=true)
	private int mpmId;
	
	@Column(name="STATUS",length = 20)
	private String status;
	
	@Column(name="MPM_NAME")
	private String mpmName;
	
	@Column(name="MPM_DESCRIPTION")
	private String mpmDescription;
	
	@Column(name="MPM_SEQUENCE")
	private double mpmSequence;
	
	@Column(name="SERVICE_TYPE")
	private String mpmserviceType;
	
	@Column(name="MPM_DATA_TYPE")
	private String mpmDataType;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastupdatedBy;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_DT",nullable=false)
	private Timestamp lastupdatedDt;
	
	public MeterParameterMaster(){
		
	}

	public int getMpmId() {
		return mpmId;
	}

	public double getMpmSequence() {
		return mpmSequence;
	}

	public void setMpmSequence(double mpmSequence) {
		this.mpmSequence = mpmSequence;
	}

	public void setMpmId(int mpmId) {
		this.mpmId = mpmId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMpmName() {
		return mpmName;
	}

	public void setMpmName(String mpmName) {
		this.mpmName = mpmName;
	}

	public String getMpmDescription() {
		return mpmDescription;
	}

	public void setMpmDescription(String mpmDescription) {
		this.mpmDescription = mpmDescription;
	}

	

	public String getMpmserviceType() {
		return mpmserviceType;
	}

	public void setMpmserviceType(String mpmserviceType) {
		this.mpmserviceType = mpmserviceType;
	}

	public String getMpmDataType() {
		return mpmDataType;
	}

	public void setMpmDataType(String mpmDataType) {
		this.mpmDataType = mpmDataType;
	}

	public String getLastupdatedBy() {
		return lastupdatedBy;
	}

	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getLastupdatedDt() {
		return lastupdatedDt;
	}

	public void setLastupdatedDt(Timestamp lastupdatedDt) {
		this.lastupdatedDt = lastupdatedDt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@PrePersist
	protected void onCreate() 
	{
		lastupdatedDt = new Timestamp(new Date().getTime());
		lastupdatedBy=(String) SessionData.getUserDetails().get("userID");
		createdBy=(String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastupdatedDt = new Timestamp(new Date().getTime());
		lastupdatedBy=(String) SessionData.getUserDetails().get("userID");
	}
	
	
}
