package com.bcits.bfm.model;

import java.sql.Date;

public class AMRData {
private int id;	
private Date readingDate;
private String blocksName;
private String propertyName;
private String personName;
private String accountNumber;
private Double presentReading;
private String meterNumber;
private Double presentDGReading;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public Date getReadingDate() {
	return readingDate;
}
public void setReadingDate(Date readingDate) {
	this.readingDate = readingDate;
}
public String getBlocksName() {
	return blocksName;
}
public void setBlocksName(String blocksName) {
	this.blocksName = blocksName;
}

public String getPersonName() {
	return personName;
}
public String getPropertyName() {
	return propertyName;
}
public void setPropertyName(String propertyName) {
	this.propertyName = propertyName;
}
public void setPersonName(String personName) {
	this.personName = personName;
}
public String getAccountNumber() {
	return accountNumber;
}
public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}

public String getMeterNumber() {
	return meterNumber;
}
public void setMeterNumber(String meterNumber) {
	this.meterNumber = meterNumber;
}
public Double getPresentReading() {
	return presentReading;
}
public void setPresentReading(Double presentReading) {
	this.presentReading = presentReading;
}
public Double getPresentDGReading() {
	return presentDGReading;
}
public void setPresentDGReading(Double presentDGReading) {
	this.presentDGReading = presentDGReading;
}

}
