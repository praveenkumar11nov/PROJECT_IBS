package com.bcits.bfm.model;

public class GenericBillGeneration<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> 
{
	private T1 accoundId;
	private T2 typeOfService;
	private T3 uomValue;
	private T4 previousDate;
	private T5 presentDate;
	private T6 previousStatus;
	private T7 presentStatus;
	private T8 meterConstant;
	private T9 presentReading;
	private T10 previousReading;
	
	public T1 getAccoundId() {
		return accoundId;
	}
	public void setAccoundId(T1 accoundId) {
		this.accoundId = accoundId;
	}
	public T2 getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(T2 typeOfService) {
		this.typeOfService = typeOfService;
	}
	public T3 getUomValue() {
		return uomValue;
	}
	public void setUomValue(T3 uomValue) {
		this.uomValue = uomValue;
	}
	public T4 getPreviousDate() {
		return previousDate;
	}
	public void setPreviousDate(T4 previousDate) {
		this.previousDate = previousDate;
	}
	public T5 getPresentDate() {
		return presentDate;
	}
	public void setPresentDate(T5 presentDate) {
		this.presentDate = presentDate;
	}
	public T6 getPreviousStatus() {
		return previousStatus;
	}
	public void setPreviousStatus(T6 previousStatus) {
		this.previousStatus = previousStatus;
	}
	public T7 getPresentStatus() {
		return presentStatus;
	}
	public void setPresentStatus(T7 presentStatus) {
		this.presentStatus = presentStatus;
	}
	public T8 getMeterConstant() {
		return meterConstant;
	}
	public void setMeterConstant(T8 meterConstant) {
		this.meterConstant = meterConstant;
	}
	public T9 getPresentReading() {
		return presentReading;
	}
	public void setPresentReading(T9 presentReading) {
		this.presentReading = presentReading;
	}
	public T10 getPreviousReading() {
		return previousReading;
	}
	public void setPreviousReading(T10 previousReading) {
		this.previousReading = previousReading;
	}
}
