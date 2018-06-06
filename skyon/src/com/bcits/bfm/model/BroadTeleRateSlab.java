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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries({
	@NamedQuery(name = "BroadTeleRateSlab.rateSlabListByParentID", query = "SELECT el FROM BroadTeleRateSlab el WHERE el.broadTelrmId = :broadTelrmId  ORDER BY el.broadTelSlabNo"),
	@NamedQuery(name = "BroadTeleRateSlab.updateRateSlabStatusFromInnerGrid", query = "UPDATE BroadTeleRateSlab el SET el.status = :status WHERE el.broadTelersId = :broadTelersId"),
	//@NamedQuery(name = "BroadTeleRateSlab.findRateSlabByPrimayID", query = "SELECT el FROM GasRateSlab el WHERE el.gasrsId = :gasrsId"),
	//@NamedQuery(name = "BroadTeleRateSlab.getGasRateSlabGreaterThanUpdateSlabNoEq", query = "SELECT el FROM GasRateSlab el WHERE el.gasrmId = :gasrmId and el.gasSlabNo >= :gasSlabNo"),
	//@NamedQuery(name = "BroadTeleRateSlab.getGasRateSlabGreaterThanUpdateSlabNo", query = "SELECT el FROM GasRateSlab el WHERE el.gasrmId = :gasrmId and el.gasSlabNo > :gasSlabNo"),
})
@Entity
@Table(name="BROAD_TELE_RATE_SLAB")
public class BroadTeleRateSlab 
{
	@Id
	@SequenceGenerator(name = "broadTelrateslab_seq", sequenceName = "BROAD_TELE_RATESLAB_SEQ")
	@GeneratedValue(generator = "broadTelrateslab_seq")
	@Column(name="BROAD_TELE_RS_ID")
	private int broadTelersId;
	
    @Column(name="BROAD_TELE_RM_ID")
	private int broadTelrmId;

	@Column(name="BROAD_TELE_SLAB_NO")
	private int broadTelSlabNo;
	
	@Column(name="BROAD_TELE_SLAB_FROM")
	private Float broadTelSlabFrom =0.0f;
	
	@Column(name="BROAD_TELE_SLAB_TO")
	private Float broadTelSlabTo=0.0f;

	@Column(name="BROAD_TELE_RATE")
	private Float broadTelRate=0.0f;

	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());;
	
	@ManyToOne
	@JoinColumn(name="BROAD_TELE_RM_ID",insertable = false, updatable = false, nullable = false)
	private BroadTeleRateMaster broadTeleRateMaster;
	
	@Column(name="BROAD_TELE_SLAB_TYPE")
	private String broadTelSlabType;
	
	@Column(name="BROAD_TELE_SLAB_RATE_TYPE")
	private String broadTelSlabRateType;
	
	@Transient
	private String broadTelDummySlabFrom;
	
	@Transient
	private String broadTelDummySlabTo;

	public int getBroadTelersId() {
		return broadTelersId;
	}

	public void setBroadTelersId(int broadTelersId) {
		this.broadTelersId = broadTelersId;
	}

	public int getBroadTelrmId() {
		return broadTelrmId;
	}

	public void setBroadTelrmId(int broadTelrmId) {
		this.broadTelrmId = broadTelrmId;
	}

	public int getBroadTelSlabNo() {
		return broadTelSlabNo;
	}

	public void setBroadTelSlabNo(int broadTelSlabNo) {
		this.broadTelSlabNo = broadTelSlabNo;
	}

	public Float getBroadTelSlabFrom() {
		return broadTelSlabFrom;
	}

	public void setBroadTelSlabFrom(Float broadTelSlabFrom) {
		this.broadTelSlabFrom = broadTelSlabFrom;
	}

	public Float getBroadTelSlabTo() {
		return broadTelSlabTo;
	}

	public void setBroadTelSlabTo(Float broadTelSlabTo) {
		this.broadTelSlabTo = broadTelSlabTo;
	}

	public Float getBroadTelRate() {
		return broadTelRate;
	}

	public void setBroadTelRate(Float broadTelRate) {
		this.broadTelRate = broadTelRate;
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

	public BroadTeleRateMaster getBroadTeleRateMaster() {
		return broadTeleRateMaster;
	}

	public void setBroadTeleRateMaster(BroadTeleRateMaster broadTeleRateMaster) {
		this.broadTeleRateMaster = broadTeleRateMaster;
	}

	public String getBroadTelSlabType() {
		return broadTelSlabType;
	}

	public void setBroadTelSlabType(String broadTelSlabType) {
		this.broadTelSlabType = broadTelSlabType;
	}

	public String getBroadTelSlabRateType() {
		return broadTelSlabRateType;
	}

	public void setBroadTelSlabRateType(String broadTelSlabRateType) {
		this.broadTelSlabRateType = broadTelSlabRateType;
	}

	public String getBroadTelDummySlabFrom() {
		return broadTelDummySlabFrom;
	}

	public void setBroadTelDummySlabFrom(String broadTelDummySlabFrom) {
		this.broadTelDummySlabFrom = broadTelDummySlabFrom;
	}

	public String getBroadTelDummySlabTo() {
		return broadTelDummySlabTo;
	}

	public void setBroadTelDummySlabTo(String broadTelDummySlabTo) {
		this.broadTelDummySlabTo = broadTelDummySlabTo;
	}
	
}
