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

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="METER_HISTORY")  
@NamedQueries({
	@NamedQuery(name = "MeterHistoryEntity.findAllById", query = "SELECT DISTINCT mh.meterHistoryId,sm.serviceMasterId,m.meterSerialNo,ml.meterFixedDate,ml.meterReleaseDate,ml.intialReading,ml.finalReading FROM MeterHistoryEntity mh INNER JOIN mh.electricityMeterLocationEntity ml INNER JOIN mh.electricityMetersEntity m INNER JOIN mh.serviceMastersEntity sm WHERE sm.serviceMasterId = :serviceMasterId ORDER BY mh.meterHistoryId DESC"),
})
public class MeterHistoryEntity {
	@Id
	@Column(name="ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "meterHistory_seq", sequenceName = "METER_HISTORY_SEQ") 
	@GeneratedValue(generator = "meterHistory_seq")
	private int meterHistoryId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "SM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ServiceMastersEntity serviceMastersEntity;
	
	@OneToOne
	@JoinColumn(name="ELM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityMetersEntity electricityMetersEntity;
	
	@OneToOne
	@JoinColumn(name="ELML_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityMeterLocationEntity electricityMeterLocationEntity;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getMeterHistoryId() {
		return meterHistoryId;
	}

	public void setMeterHistoryId(int meterHistoryId) {
		this.meterHistoryId = meterHistoryId;
	}

	public ServiceMastersEntity getServiceMastersEntity() {
		return serviceMastersEntity;
	}

	public void setServiceMastersEntity(ServiceMastersEntity serviceMastersEntity) {
		this.serviceMastersEntity = serviceMastersEntity;
	}

	public ElectricityMetersEntity getElectricityMetersEntity() {
		return electricityMetersEntity;
	}

	public void setElectricityMetersEntity(
			ElectricityMetersEntity electricityMetersEntity) {
		this.electricityMetersEntity = electricityMetersEntity;
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

	public ElectricityMeterLocationEntity getElectricityMeterLocationEntity() {
		return electricityMeterLocationEntity;
	}

	public void setElectricityMeterLocationEntity(
			ElectricityMeterLocationEntity electricityMeterLocationEntity) {
		this.electricityMeterLocationEntity = electricityMeterLocationEntity;
	}
}
