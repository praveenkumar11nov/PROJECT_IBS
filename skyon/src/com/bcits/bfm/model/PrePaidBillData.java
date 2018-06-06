package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class PrePaidBillData
{
	private String mtrSrNo;
	private Date meterDT;
	private Date readingDT;
	private float  activeEnergy;
	private float maxDemandKW;
	private Date mDDTKW;
	private float maxDemandKVA;
	private Date mDDTKVA;
	private float poweronhours;
	private String pf;
	private String activeEnergyDG;
	private String maxDemandKWDG;
	private String mDDTKWDG;
	private String maxDemandKVADG;
	private String mDDTKVADG;
	private String poweronhoursDG;
	private String pFDG;
	
	public String getMtrSrNo() {
		return mtrSrNo;
	}
	@XmlElement(name="MtrSrNo")
	public void setMtrSrNo(String mtrSrNo) {
		this.mtrSrNo = mtrSrNo;
	}

	public Date getMeterDT() {
		return meterDT;
	}
	@XmlElement(name="MeterDT")
    @XmlJavaTypeAdapter(DateAdapter.class)
	public void setMeterDT(Date meterDT) {
		this.meterDT = meterDT;
	}
	public Date getReadingDT() {
		return readingDT;
	}
	@XmlElement(name="ReadingDT")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setReadingDT(Date readingDT) {
		this.readingDT = readingDT;
	}
	public float getActiveEnergy() {
		return activeEnergy;
	}
	@XmlElement(name="ActiveEnergy")
	public void setActiveEnergy(float activeEnergy) {
		this.activeEnergy = activeEnergy;
	}
	public float getMaxDemandKW() {
		return maxDemandKW;
	}
	@XmlElement(name="MaxDemandKW")
	public void setMaxDemandKW(float maxDemandKW) {
		this.maxDemandKW = maxDemandKW;
	}
	public Date getmDDTKW() {
		return mDDTKW;
	}
	@XmlElement(name="MDDTKW")
	public void setmDDTKW(Date mDDTKW) {
		this.mDDTKW = mDDTKW;
	}
	public float getMaxDemandKVA() {
		return maxDemandKVA;
	}
	@XmlElement(name="MaxDemandKVA")
	public void setMaxDemandKVA(float maxDemandKVA) {
		this.maxDemandKVA = maxDemandKVA;
	}
	public Date getmDDTKVA() {
		return mDDTKVA;
	}
	@XmlElement(name="MDDTKVA")
	public void setmDDTKVA(Date mDDTKVA) {
		this.mDDTKVA = mDDTKVA;
	}
	public float getPoweronhours() {
		return poweronhours;
	}
	@XmlElement(name="poweronhours")
	public void setPoweronhours(float poweronhours) {
		this.poweronhours = poweronhours;
	}
	public String getPf() {
		return pf;
	}
	@XmlElement(name="PF")
	public void setPf(String pf) {
		this.pf = pf;
	}
	public String getActiveEnergyDG() {
		return activeEnergyDG;
	}
	@XmlElement(name="ActiveEnergyDG")
	public void setActiveEnergyDG(String activeEnergyDG) {
		this.activeEnergyDG = activeEnergyDG;
	}
	public String getMaxDemandKWDG() {
		return maxDemandKWDG;
	}
	@XmlElement(name="MaxDemandKWDG")
	public void setMaxDemandKWDG(String maxDemandKWDG) {
		this.maxDemandKWDG = maxDemandKWDG;
	}
	public String getmDDTKWDG() {
		return mDDTKWDG;
	}
	@XmlElement(name="MDDTKWDG")
	public void setmDDTKWDG(String mDDTKWDG) {
		this.mDDTKWDG = mDDTKWDG;
	}
	public String getMaxDemandKVADG() {
		return maxDemandKVADG;
	}
	@XmlElement(name="MaxDemandKVADG")
	public void setMaxDemandKVADG(String maxDemandKVADG) {
		this.maxDemandKVADG = maxDemandKVADG;
	}
	public String getmDDTKVADG() {
		return mDDTKVADG;
	}
	@XmlElement(name="MDDTKVADG")
	public void setmDDTKVADG(String mDDTKVADG) {
		this.mDDTKVADG = mDDTKVADG;
	}
	public String getPoweronhoursDG() {
		return poweronhoursDG;
	}
	@XmlElement(name="poweronhoursDG")
	public void setPoweronhoursDG(String poweronhoursDG) {
		this.poweronhoursDG = poweronhoursDG;
	}
	public String getpFDG() {
		return pFDG;
	}
	@XmlElement(name="PFDG")
	public void setpFDG(String pFDG) {
		this.pFDG = pFDG;
	}
	@Override
	public String toString() {
		return "PrePaidBillData [mtrSrNo=" + mtrSrNo + ", meterDT=" + meterDT + ", readingDT=" + readingDT
				+ ", activeEnergy=" + activeEnergy + ", maxDemandKW=" + maxDemandKW + ", mDDTKW=" + mDDTKW
				+ ", maxDemandKVA=" + maxDemandKVA + ", mDDTKVA=" + mDDTKVA + ", poweronhours=" + poweronhours + ", pf="
				+ pf + ", activeEnergyDG=" + activeEnergyDG + ", maxDemandKWDG=" + maxDemandKWDG + ", mDDTKWDG="
				+ mDDTKWDG + ", maxDemandKVADG=" + maxDemandKVADG + ", mDDTKVADG=" + mDDTKVADG + ", poweronhoursDG="
				+ poweronhoursDG + ", pFDG=" + pFDG + "]";
	}
	
	
}
