package com.bcits.bfm.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="PREPAID_GENUS_BILLDATA")
public class PrePaidGenusBilldata {
	
	@Id
	@SequenceGenerator(name="PREPAID_GENUS_BILLDATA_SEQ",sequenceName="PREPAID_GENUS_BILLDATA_SEQ")
	@GeneratedValue(generator="PREPAID_GENUS_BILLDATA_SEQ")
	
    @Column(name="MTR_ID",unique=true, nullable=false, precision=11, scale=0)
	private int mtrId;
	
	@Column(name="METER_SERIAL_NUMBER")
	private String meterSrNo;
	
	@Column(name="METER_DATE")
	private Date meterDT;
	
	@Column(name="READING_DATE")
	private Date readingDT;
	
	@Column(name="ACTIVE_ENERGY")
	private float  activeEnergy;
	
	@Column(name="MAX_DEMAND_KW")
	private float maxDemandKW;
	
	@Column(name="MDDT_KW")
	private String mDDTKW;
	
	@Column(name="MAX_DEMAND_KVA")
	private float maxDemandKVA;
	
	@Column(name="MDDT_KVA")
	private String mDDTKVA;
	
	@Column(name="POWER_ON_HOURS")
	private float poweronhours;
	
	@Column(name="PF")
	private String pf;
	
	@Column(name="ACTIVE_ENERGY_DG")
	private String activeEnergyDG;
	
	@Column(name="MAX_DEMAND_KWDG")
	private String maxDemandKWDG;
	
	@Column(name="MDDT_KWDG")
	private String mDDTKWDG;
	
	@Column(name="MAX_DEMAND_KVADG")
	private String maxDemandKVADG;
	
	@Column(name="MDDT_KVADG")
	private String mDDTKVADG;
	
	@Column(name="POWER_ON_HOURS_DG")
	private String poweronhoursDG;
	
	@Column(name="PF_DG")
	private String pFDG;

	public int getMtrId() {
		return mtrId;
	}

	public void setMtrId(int mtrId) {
		this.mtrId = mtrId;
	}

	public String getMeterSrNo() {
		return meterSrNo;
	}

	public void setMeterSrNo(String meterSrNo) {
		this.meterSrNo = meterSrNo;
	}

	public Date getMeterDT() {
		return meterDT;
	}

	public void setMeterDT(Date meterDT) {
		this.meterDT = meterDT;
	}

	public Date getReadingDT() {
		return readingDT;
	}

	public void setReadingDT(Date readingDT) {
		this.readingDT = readingDT;
	}

	public float getActiveEnergy() {
		return activeEnergy;
	}

	public void setActiveEnergy(float activeEnergy) {
		this.activeEnergy = activeEnergy;
	}

	public float getMaxDemandKW() {
		return maxDemandKW;
	}

	public void setMaxDemandKW(float maxDemandKW) {
		this.maxDemandKW = maxDemandKW;
	}

	public String getmDDTKW() {
		return mDDTKW;
	}

	public void setmDDTKW(String mDDTKW) {
		this.mDDTKW = mDDTKW;
	}

	public float getMaxDemandKVA() {
		return maxDemandKVA;
	}

	public void setMaxDemandKVA(float maxDemandKVA) {
		this.maxDemandKVA = maxDemandKVA;
	}

	public String getmDDTKVA() {
		return mDDTKVA;
	}

	public void setmDDTKVA(String mDDTKVA) {
		this.mDDTKVA = mDDTKVA;
	}

	public float getPoweronhours() {
		return poweronhours;
	}

	public void setPoweronhours(float poweronhours) {
		this.poweronhours = poweronhours;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getActiveEnergyDG() {
		return activeEnergyDG;
	}

	public void setActiveEnergyDG(String activeEnergyDG) {
		this.activeEnergyDG = activeEnergyDG;
	}

	public String getMaxDemandKWDG() {
		return maxDemandKWDG;
	}

	public void setMaxDemandKWDG(String maxDemandKWDG) {
		this.maxDemandKWDG = maxDemandKWDG;
	}

	public String getmDDTKWDG() {
		return mDDTKWDG;
	}

	public void setmDDTKWDG(String mDDTKWDG) {
		this.mDDTKWDG = mDDTKWDG;
	}

	public String getMaxDemandKVADG() {
		return maxDemandKVADG;
	}

	public void setMaxDemandKVADG(String maxDemandKVADG) {
		this.maxDemandKVADG = maxDemandKVADG;
	}

	public String getmDDTKVADG() {
		return mDDTKVADG;
	}

	public void setmDDTKVADG(String mDDTKVADG) {
		this.mDDTKVADG = mDDTKVADG;
	}

	public String getPoweronhoursDG() {
		return poweronhoursDG;
	}

	public void setPoweronhoursDG(String poweronhoursDG) {
		this.poweronhoursDG = poweronhoursDG;
	}

	public String getpFDG() {
		return pFDG;
	}

	public void setpFDG(String pFDG) {
		this.pFDG = pFDG;
	}

	@Override
	public String toString() {
		return "PrePaidGenusBilldata [mtrId=" + mtrId + ", meterSrNo=" + meterSrNo + ", meterDT=" + meterDT
				+ ", readingDT=" + readingDT + ", activeEnergy=" + activeEnergy + ", maxDemandKW=" + maxDemandKW
				+ ", mDDTKW=" + mDDTKW + ", maxDemandKVA=" + maxDemandKVA + ", mDDTKVA=" + mDDTKVA + ", poweronhours="
				+ poweronhours + ", pf=" + pf + ", activeEnergyDG=" + activeEnergyDG + ", maxDemandKWDG="
				+ maxDemandKWDG + ", mDDTKWDG=" + mDDTKWDG + ", maxDemandKVADG=" + maxDemandKVADG + ", mDDTKVADG="
				+ mDDTKVADG + ", poweronhoursDG=" + poweronhoursDG + ", pFDG=" + pFDG + "]";
	}
	
		
}
