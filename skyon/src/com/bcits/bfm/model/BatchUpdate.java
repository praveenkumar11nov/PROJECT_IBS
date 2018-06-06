package com.bcits.bfm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
public class BatchUpdate 
{

	private int batchUpdateId;
	
	private String accountNo;
	
	private String serviceType;
	
	@Temporal(TemporalType.DATE)
	private Date presentBillDate;
	
	private String presentStatus;
	
	private float presentReading;
	
	private float pfReading;
	
	private float recordedDemand;
	
	@Temporal(TemporalType.DATE)
	private Date previousBillDate;
	
	private String previousStatus;
	
	private float previousReading;
	
	private float units;
	
	private float averageUnits;
	
	private float meterConstant;
	
	private float tod1;
	
	private float tod2;
	
	private float tod3;
	
	private float dgMeterConstant;
	
	private float dgPreviousReading;
	
	private float dgPresentReading;
	
	private float dgUnits;
	
	private Date presentbilldateToExport;
	private int blockIdToExport;

	public Date getPresentbilldateToExport() {
		return presentbilldateToExport;
	}

	public void setPresentbilldateToExport(Date presentbilldateToExport) {
		this.presentbilldateToExport = presentbilldateToExport;
	}

	public int getBlockIdToExport() {
		return blockIdToExport;
	}

	public void setBlockIdToExport(int blockIdToExport) {
		this.blockIdToExport = blockIdToExport;
	}

	public int getBatchUpdateId() {
		return batchUpdateId;
	}

	public void setBatchUpdateId(int batchUpdateId) {
		this.batchUpdateId = batchUpdateId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getPresentBillDate() {
		return presentBillDate;
	}

	public void setPresentBillDate(Date presentBillDate) {
		this.presentBillDate = presentBillDate;
	}

	public String getPresentStatus() {
		return presentStatus;
	}

	public void setPresentStatus(String presentStatus) {
		this.presentStatus = presentStatus;
	}

	public float getPresentReading() {
		return presentReading;
	}

	public void setPresentReading(float presentReading) {
		this.presentReading = presentReading;
	}

	public float getPfReading() {
		return pfReading;
	}

	public void setPfReading(float pfReading) {
		this.pfReading = pfReading;
	}

	public float getRecordedDemand() {
		return recordedDemand;
	}

	public void setRecordedDemand(float recordedDemand) {
		this.recordedDemand = recordedDemand;
	}

	public Date getPreviousBillDate() {
		return previousBillDate;
	}

	public void setPreviousBillDate(Date previousBillDate) {
		this.previousBillDate = previousBillDate;
	}

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	public float getPreviousReading() {
		return previousReading;
	}

	public void setPreviousReading(float previousReading) {
		this.previousReading = previousReading;
	}

	public float getUnits() {
		return units;
	}

	public void setUnits(float units) {
		this.units = units;
	}

	public float getAverageUnits() {
		return averageUnits;
	}

	public void setAverageUnits(float averageUnits) {
		this.averageUnits = averageUnits;
	}

	public float getMeterConstant() {
		return meterConstant;
	}

	public void setMeterConstant(float meterConstant) {
		this.meterConstant = meterConstant;
	}

	public float getTod1() {
		return tod1;
	}

	public void setTod1(float tod1) {
		this.tod1 = tod1;
	}

	public float getTod2() {
		return tod2;
	}

	public void setTod2(float tod2) {
		this.tod2 = tod2;
	}

	public float getTod3() {
		return tod3;
	}

	public void setTod3(float tod3) {
		this.tod3 = tod3;
	}

	public float getDgMeterConstant() {
		return dgMeterConstant;
	}

	public void setDgMeterConstant(float dgMeterConstant) {
		this.dgMeterConstant = dgMeterConstant;
	}

	public float getDgPreviousReading() {
		return dgPreviousReading;
	}

	public void setDgPreviousReading(float dgPreviousReading) {
		this.dgPreviousReading = dgPreviousReading;
	}

	public float getDgPresentReading() {
		return dgPresentReading;
	}

	public void setDgPresentReading(float dgPresentReading) {
		this.dgPresentReading = dgPresentReading;
	}

	public float getDgUnits() {
		return dgUnits;
	}

	public void setDgUnits(float dgUnits) {
		this.dgUnits = dgUnits;
	}

}
