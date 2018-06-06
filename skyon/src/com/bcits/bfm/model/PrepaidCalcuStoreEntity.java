package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PREAPID_CALCU_STORAGE")
@NamedQueries({
	@NamedQuery(name="PrepaidCalcuStoreEntity.featchData",query="SELECT pp FROM PrepaidCalcuStoreEntity pp ORDER BY pp.pcsId ASC"),
	@NamedQuery(name="PrePaidMeters.fetch",query="SELECT pm.personId,pm.propertyId FROM PrePaidMeters pm where pm.meterNumber=:meterNumber"),
	@NamedQuery(name="PrepaidCalcuStoreEntity.getMaxDate",query="SELECT MAX(TO_DATE(readingDate,'dd/MM/yyyy')) FROM PrepaidCalcuStoreEntity pc where pc.meterNo=:meterNo and pc.serviceName=:serviceName"),
	@NamedQuery(name="PrepaidCalcuStoreEntity.getALLCalcuData",query="SELECT p.meterNo, p.personId,p.area,p.serviceName,p.chargeName,p.rate,p.cbName,sum(p.daily_Units_Consumed),sum(p.daily_Consumption_Amt),min(p.readingDate),max(p.readingDate) FROM PrepaidCalcuStoreEntity p Where p.propertyId=:propertyId AND TO_DATE(p.readingDate,'dd/MM/yyyy')>=TO_DATE(:fromDate,'dd/MM/yyyy') AND TO_DATE(p.readingDate,'dd/MM/yyyy')<=TO_DATE(:toDate,'dd/MM/yyyy') GROUP BY p.meterNo, p.personId,p.area,p.serviceName,p.chargeName,p.rate,p.cbName ORDER BY p.serviceName"),
	@NamedQuery(name="PrepaidCalcuStoreEntity.getsumAmt",query="SELECT sum(p.daily_Consumption_Amt) FROM PrepaidCalcuStoreEntity p Where p.propertyId=:propertyId AND TO_DATE(p.readingDate,'dd/MM/yyyy')>=TO_DATE(:fromDate,'dd/MM/yyyy') AND TO_DATE(p.readingDate,'dd/MM/yyyy')<=TO_DATE(:toDate,'dd/MM/yyyy')"),
	@NamedQuery(name="PrepaidCalcuStoreEntity.getminMaxDate",query="SELECT MIN(to_date(p.readingDate,'dd/MM/yyyy')),MAX(to_date(p.readingDate,'dd/MM/yyyy')) FROM PrepaidCalcuStoreEntity p where p.propertyId=:propertyId AND to_date(p.readingDate,'dd/MM/yyyy')>=to_date(:fromDate,'dd/MM/yyyy') AND to_date(p.readingDate,'dd/MM/yyyy')<=to_date(:toDate,'dd/MM/yyyy') AND meterNo=:meterNo"),
	@NamedQuery(name="PrepaidCalcuStoreEntity.getExistence",query="select count(p) from PrepaidCalcuStoreEntity p where p.propertyId=:propertyId and TO_DATE(p.readingDate,'dd/MM/yyyy')=TO_DATE(:readingDate,'dd/MM/yyyy') and p.serviceName=:serviceName")
})
public class PrepaidCalcuStoreEntity {

	@Id
	@SequenceGenerator(name="PREAPID_CALCU_STORAGE_SEQ", sequenceName="PREAPID_CALCU_STORAGE_SEQ")
	@GeneratedValue(generator="PREAPID_CALCU_STORAGE_SEQ")
	@Column(name="PCSID")
	private int pcsId;
	
	@Column(name="PERSONID")
	private int personId;
	
	@Column(name="PROPERTYID") 
	private int propertyId;
	
	@Column(name="AREA")
	private int area;
	
	@Column(name="READING_DATE")
	private String readingDate;
	
	@Column(name="SERVICE_NAME")
	private String serviceName;
	
	@Column(name="CHARGE_NAME")
	private String chargeName;
	
	@Column(name="RATE")
	private String rate;
	
	@Column(name="CB_NAME")
	private String cbName;
	
	@Column(name="DAILY_UNITS_CONSUMED")
	private double daily_Units_Consumed;
	
	@Column(name="DAILY_CONSUMPTION_AMT")
	private double daily_Consumption_Amt;
	
	@Column(name="DAILY_RECHARGE_AMT")
	private long daily_Recharge_Amt;
	
	@Column(name="DAILY_BALANCE")
	private double daily_Balance;
	
	@Column(name="METER_NO")
	private String meterNo;

	public int getPcsId() {
		return pcsId;
	}

	public void setPcsId(int pcsId) {
		this.pcsId = pcsId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCbName() {
		return cbName;
	}

	public void setCbName(String cbName) {
		this.cbName = cbName;
	}

	public double getDaily_Units_Consumed() {
		return daily_Units_Consumed;
	}

	public void setDaily_Units_Consumed(double daily_Units_Consumed) {
		this.daily_Units_Consumed = daily_Units_Consumed;
	}

	public double getDaily_Consumption_Amt() {
		return daily_Consumption_Amt;
	}

	public void setDaily_Consumption_Amt(double daily_Consumption_Amt) {
		this.daily_Consumption_Amt = daily_Consumption_Amt;
	}

	public long getDaily_Recharge_Amt() {
		return daily_Recharge_Amt;
	}

	public void setDaily_Recharge_Amt(long daily_Recharge_Amt) {
		this.daily_Recharge_Amt = daily_Recharge_Amt;
	}

	public double getDaily_Balance() {
		return daily_Balance;
	}

	public void setDaily_Balance(double daily_Balance) {
		this.daily_Balance = daily_Balance;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

}
