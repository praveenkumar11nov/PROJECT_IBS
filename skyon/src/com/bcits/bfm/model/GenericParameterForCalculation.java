package com.bcits.bfm.model;

import java.util.Date;

public class GenericParameterForCalculation
{
	private String tariffName;
	private String rateName;
	private float uomValue;
	private Date startDate;
	private Date endDate;
	
 public GenericParameterForCalculation(String tariffName,String rateName,float uomValue,Date startDate,Date endDate)
 {
	 this.tariffName=tariffName;
	 this.rateName=rateName;
	 this.uomValue=uomValue;
	 this.startDate=startDate;
	 this.endDate=endDate;
 }
	
	public String getTariffName() {
		return tariffName;
	}
	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public float getUomValue() {
		return uomValue;
	}
	public void setUomValue(float uomValue) {
		this.uomValue = uomValue;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/*public GenericParameterForCalculation getBillingCaclucationComponents()
	   {
		GenericParameterForCalculation calculation = new GenericParameterForCalculation(this.getTariffName(), this.getRateName(), this.getUomValue(), this.getStartDate(), this.getEndDate());
		return calculation;
	   }*/
}
