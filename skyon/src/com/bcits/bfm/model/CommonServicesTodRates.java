package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;
@NamedQueries({
	@NamedQuery(name = "CommonServicesTodRates.findAll", query = "SELECT el FROM CommonServicesTodRates el WHERE el.csRsId = :csRsId ORDER BY el.csRsId"),
	@NamedQuery(name = "CommonServicesTodRates.updateTODRatesStatusFromInnerGrid", query = "UPDATE CommonServicesTodRates el SET el.status = :status WHERE el.cstiId = :cstiId"),
})
@Entity
@Table(name="CS_TOD_RATES")
public class CommonServicesTodRates 
{
	@Id
	@SequenceGenerator(name = "cstodrates_seq", sequenceName = "CS_TODRATES_SEQ")
	@GeneratedValue(generator = "cstodrates_seq")
	@Column(name="CS_TR_ID")
	private int cstiId;
	
	@Column(name="CS_RS_ID")
	private int csRsId;
	
	@Column(name="FROM_TIME")
	private Timestamp fromTime;
	
	@Column(name="TO_TIME")
	private Timestamp toTime;
	
	@Column(name="INCREMENTAL_RATE")
	private float incrementalRate;
	
	@Column(name="VALID_FROM")
	private Date todValidFrom;
	
	@Column(name="VALID_TO")
	private Date todValidTo;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
    @ManyToOne
	@JoinColumn(name = "CS_RS_ID", insertable = false, updatable = false, nullable = false)
	private CommonServicesRateSlab commonServicesRateSlab;
	
	@Column(name="TOD_RATE_TYPE")
	private String csTodRateType;

	public int getCstiId() {
		return cstiId;
	}

	public void setCstiId(int cstiId) {
		this.cstiId = cstiId;
	}

	public Timestamp getFromTime() {
		return fromTime;
	}

	public void setFromTime(Timestamp fromTime) {
		this.fromTime = fromTime;
	}

	public Timestamp getToTime() {
		return toTime;
	}

	public void setToTime(Timestamp toTime) {
		this.toTime = toTime;
	}

	public int getCsRsId() {
		return csRsId;
	}

	public void setCsRsId(int csRsId) {
		this.csRsId = csRsId;
	}

	public float getIncrementalRate() {
		return incrementalRate;
	}

	public void setIncrementalRate(float incrementalRate) {
		this.incrementalRate = incrementalRate;
	}

	public Date getTodValidFrom() {
		return todValidFrom;
	}

	public void setTodValidFrom(Date todValidFrom) {
		this.todValidFrom = todValidFrom;
	}

	public Date getTodValidTo() {
		return todValidTo;
	}

	public void setTodValidTo(Date todValidTo) {
		this.todValidTo = todValidTo;
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

	public CommonServicesRateSlab getCommonServicesRateSlab() {
		return commonServicesRateSlab;
	}

	public void setCommonServicesRateSlab(
			CommonServicesRateSlab commonServicesRateSlab) {
		this.commonServicesRateSlab = commonServicesRateSlab;
	}

	public String getCsTodRateType() {
		return csTodRateType;
	}

	public void setCsTodRateType(String csTodRateType) {
		this.csTodRateType = csTodRateType;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate() 
	 {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
}
