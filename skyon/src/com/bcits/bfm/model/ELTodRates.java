package com.bcits.bfm.model;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="EL_TOD_RATES")
@NamedQueries({
	@NamedQuery(name = "ELTodRates.findAll", query = "SELECT el FROM ELTodRates el WHERE el.elrsId = :elrsId ORDER BY el.elrsId"),
	@NamedQuery(name = "ELTodRates.updateTODRatesStatusFromInnerGrid", query = "UPDATE ELTodRates el SET el.status = :status WHERE el.eltiId = :eltiId"),
	@NamedQuery(name = "ELTodRates.getRateSlab", query = "SELECT MAX(el.todValidTo) FROM ELTodRates el WHERE el.elrsId = :elrsId"),
	@NamedQuery(name = "ELTodRates.getmaxTimeStamp", query = "SELECT MAX(el.toTime) FROM ELTodRates el WHERE el.elrsId = :elrsId"),
	@NamedQuery(name = "ELTodRates.getMaxIncrementalRate", query = "SELECT MAX(el.incrementalRate) FROM ELTodRates el WHERE el.elrsId = :elrsId"),
	@NamedQuery(name = "ELTodRates.getELTodRatesListByRateSlabId", query = "SELECT el FROM ELTodRates el WHERE el.elrsId = :elrsId"),
    @NamedQuery(name="ELtodRates.getDateCount",query=" SELECT COUNT(e.todValidFrom) FROM ELTodRates e WHERE e.elrsId=:elrsId"),

})
public class ELTodRates 
{
	@Id
	@SequenceGenerator(name = "eltodrates_seq", sequenceName = "ELTODRATES_SEQ")
	@GeneratedValue(generator = "eltodrates_seq")
	@Column(name="ELTR_ID")
	private int eltiId;
	
	@Column(name="ELRS_ID")
	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreGoodsReceipt.vcId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceipt.vcId")
	private int elrsId;
	
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
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ELRS_ID", insertable = false, updatable = false, nullable = false)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ELRateSlabs elRateSlabs;
	
	@Column(name="TOD_RATE_TYPE")
	private String todRateType;
	

	public ELRateSlabs getElRateSlabs() {
		return elRateSlabs;
	}

	public void setElRateSlabs(ELRateSlabs elRateSlabs) {
		this.elRateSlabs = elRateSlabs;
	}

	public int getEltiId() {
		return eltiId;
	}

	public void setEltiId(int eltiId) {
		this.eltiId = eltiId;
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
	
	public int getElrsId() {
		return elrsId;
	}

	public void setElrsId(int elrsId) {
		this.elrsId = elrsId;
	}

	
	public String getTodRateType() {
		return todRateType;
	}

	public void setTodRateType(String todRateType) {
		this.todRateType = todRateType;
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
