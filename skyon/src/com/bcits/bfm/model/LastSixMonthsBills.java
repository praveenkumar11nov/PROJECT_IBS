package com.bcits.bfm.model;

import java.util.Date;

public class LastSixMonthsBills {
private Date month;
private double units ;
private double amount;
private String billBases;

public Date getMonth() {
	return month;
}
public void setMonth(Date month) {
	this.month = month;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public double getUnits() {
	return units;
}
public void setUnits(double units) {
	this.units = units;
}
public String getBillBases() {
	return billBases;
}
public void setBillBases(String billBases) {
	this.billBases = billBases;
}
}
