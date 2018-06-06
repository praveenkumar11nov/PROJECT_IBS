package com.bcits.bfm.model;

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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@NamedQueries({
	@NamedQuery(name = "GasRateSlab.rateSlabListByParentID", query = "SELECT el FROM GasRateSlab el WHERE el.gasrmId = :gasrmId  ORDER BY el.gasSlabNo"),
	@NamedQuery(name = "GasRateSlab.updateRateSlabStatusFromInnerGrid", query = "UPDATE GasRateSlab el SET el.status = :status WHERE el.gasrsId = :gasrsId"),
	@NamedQuery(name = "GasRateSlab.findRateSlabByPrimayID", query = "SELECT el FROM GasRateSlab el WHERE el.gasrsId = :gasrsId"),
	@NamedQuery(name = "GasRateSlab.getGasRateSlabGreaterThanUpdateSlabNoEq", query = "SELECT el FROM GasRateSlab el WHERE el.gasrmId = :gasrmId and el.gasSlabNo >= :gasSlabNo"),
	@NamedQuery(name = "GasRateSlab.getGasRateSlabGreaterThanUpdateSlabNo", query = "SELECT el FROM GasRateSlab el WHERE el.gasrmId = :gasrmId and el.gasSlabNo > :gasSlabNo"),
	@NamedQuery(name = "GasRateSlab.getGasRateSlabListByRateMasterId", query = "SELECT gas FROM GasRateSlab gas WHERE gas.gasrmId = :gasrmId ORDER BY gas.gasSlabNo"),
	@NamedQuery(name = "GasRateSlabs.getGasRateSlabListByRateMasterId", query = "SELECT gas FROM GasRateSlab gas WHERE gas.gasrmId =:gasrmid ORDER BY gasrmId.gasSlabNo"),
})

@Entity
@Table(name="GAS_RATE_SLAB")
public class GasRateSlab
{
	@Id
	@SequenceGenerator(name = "gasrateslab_seq", sequenceName = "GAS_RATESLAB_SEQ")
	@GeneratedValue(generator = "gasrateslab_seq")
	@Column(name="GAS_RS_ID")
	private int gasrsId;
	
    @Column(name="GAS_RM_ID")
	private int gasrmId;

	@Column(name="GAS_SLAB_NO")
	private int gasSlabNo;
	
	@Column(name="GAS_SLAB_FROM")
	private Float gasSlabFrom =0.0f;
	
	@Column(name="GAS_SLAB_TO")
	private Float gasSlabTo=0.0f;

	@Column(name="GAS_RATE")
	private Float gasRate=0.0f;

	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	@ManyToOne
	@JoinColumn(name="GAS_RM_ID",insertable = false, updatable = false, nullable = false)
	private GasRateMaster gasRateMaster;
	
	@Column(name="GAS_SLAB_TYPE")
	private String gasSlabType;
	
	@Column(name="GAS_SLAB_RATE_TYPE")
	private String gasSlabRateType;
	
	@Transient
	private String gasDummySlabFrom;
	
	@Transient
	private String gasDummySlabTo;

	public int getGasrsId() {
		return gasrsId;
	}

	public void setGasrsId(int gasrsId) {
		this.gasrsId = gasrsId;
	}

	public int getGasrmId() {
		return gasrmId;
	}

	public void setGasrmId(int gasrmId) {
		this.gasrmId = gasrmId;
	}

	public int getGasSlabNo() {
		return gasSlabNo;
	}

	public void setGasSlabNo(int gasSlabNo) {
		this.gasSlabNo = gasSlabNo;
	}

	public Float getGasSlabFrom() {
		return gasSlabFrom;
	}

	public void setGasSlabFrom(Float gasSlabFrom) {
		this.gasSlabFrom = gasSlabFrom;
	}

	public Float getGasSlabTo() {
		return gasSlabTo;
	}

	public void setGasSlabTo(Float gasSlabTo) {
		this.gasSlabTo = gasSlabTo;
	}

	public Float getGasRate() {
		return gasRate;
	}

	public void setGasRate(Float gasRate) {
		this.gasRate = gasRate;
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

	public GasRateMaster getGasRateMaster() {
		return gasRateMaster;
	}

	public void setGasRateMaster(GasRateMaster gasRateMaster) {
		this.gasRateMaster = gasRateMaster;
	}

	public String getGasSlabType() {
		return gasSlabType;
	}

	public void setGasSlabType(String gasSlabType) {
		this.gasSlabType = gasSlabType;
	}

	public String getGasSlabRateType() {
		return gasSlabRateType;
	}

	public void setGasSlabRateType(String gasSlabRateType) {
		this.gasSlabRateType = gasSlabRateType;
	}

	public String getGasDummySlabFrom() {
		return gasDummySlabFrom;
	}

	public void setGasDummySlabFrom(String gasDummySlabFrom) {
		this.gasDummySlabFrom = gasDummySlabFrom;
	}

	public String getGasDummySlabTo() {
		return gasDummySlabTo;
	}

	public void setGasDummySlabTo(String gasDummySlabTo) {
		this.gasDummySlabTo = gasDummySlabTo;
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
