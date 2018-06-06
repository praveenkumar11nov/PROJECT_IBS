package com.bcits.bfm.model;

public class GenericClassForCalucaltion<T1, T2, T3, T4, T5>
{
	private T1 tariffName;
	private T2 rateName;
	private T3 consumptionsUnits;
	private T4 startDate;
	private T5 endDate;
	public T1 getTariffName() {
		return tariffName;
	}
	public void setTariffName(T1 tariffName) {
		this.tariffName = tariffName;
	}
	public T2 getRateName() {
		return rateName;
	}
	public void setRateName(T2 rateName) {
		this.rateName = rateName;
	}
	public T3 getConsumptionsUnits() {
		return consumptionsUnits;
	}
	public void setConsumptionsUnits(T3 consumptionsUnits) {
		this.consumptionsUnits = consumptionsUnits;
	}
	public T4 getStartDate() {
		return startDate;
	}
	public void setStartDate(T4 startDate) {
		this.startDate = startDate;
	}
	public T5 getEndDate() {
		return endDate;
	}
	public void setEndDate(T5 endDate) {
		this.endDate = endDate;
	}
}
