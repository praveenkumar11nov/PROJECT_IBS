package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="GAS_DISTRIBUTION_LOSSES")  
@NamedQueries({
	@NamedQuery(name = "GasDistributionLosses.findAll", query = "SELECT gdl.gdlid,gdl.month,gdl.mainMeterReading,gdl.subMeterSReading,gdl.lossUnits,gdl.lossPercentage,gdl.status FROM GasDistributionLosses gdl ORDER BY gdl.gdlid DESC"),
	@NamedQuery(name = "GasDistributionLosses.setDistributionLossesStatus", query = "UPDATE GasDistributionLosses el SET el.status = :status WHERE el.gdlid = :gdlid"),
    @NamedQuery(name="GasDistributionLosses.getElBillId",query="SELECT e.elBillId FROM ElectricityBillEntity e WHERE  e.typeOfService='Gas'  AND e.postType='Bill' AND e.status!='Cancelled' AND e.billType='Normal'  AND EXTRACT(month FROM e.billDate)=:month AND EXTRACT(year FROM e.billDate)=:year")
})
public class GasDistributionLosses 
{
	@Id
	@Column(name="GDL_ID")
	@SequenceGenerator(name = "gasDistributinLosses_seq", sequenceName = "GASDISTRIBUTINLOSSES_SEQ") 
	@GeneratedValue(generator = "gasDistributinLosses_seq")
	private int gdlid;
	
	@Column(name="MONTH")
	@Temporal(TemporalType.DATE)
	private java.util.Date month;
	
	@Column(name="MAIN_METER_READING")
	private float mainMeterReading;
	
	@Column(name="SUB_METER_READING")
	private float subMeterSReading;
	
	@Column(name="LOSS_UNITS")
	private float lossUnits;
	
	@Column(name="LOSS_PERCENTAGE")
	private float lossPercentage;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;

	
	public int getGdlid() {
		return gdlid;
	}

	public void setGdlid(int gdlid) {
		this.gdlid = gdlid;
	}

	

	public java.util.Date getMonth() {
		return month;
	}

	public void setMonth(java.util.Date month) {
		this.month = month;
	}

	public float getMainMeterReading() {
		return mainMeterReading;
	}

	public void setMainMeterReading(float mainMeterReading) {
		this.mainMeterReading = mainMeterReading;
	}

	public float getSubMeterSReading() {
		return subMeterSReading;
	}

	public void setSubMeterSReading(float subMeterSReading) {
		this.subMeterSReading = subMeterSReading;
	}

	public float getLossUnits() {
		return lossUnits;
	}

	public void setLossUnits(float lossUnits) {
		this.lossUnits = lossUnits;
	}

	public float getLossPercentage() {
		return lossPercentage;
	}

	public void setLossPercentage(float lossPercentage) {
		this.lossPercentage = lossPercentage;
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

}
