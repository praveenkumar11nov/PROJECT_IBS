package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PREPAID_BILL_HISTORY")

public class PrepaidBillEntity {

	@Id
	@SequenceGenerator(name="PREPAID_BILL_HISTORY_SEQ",sequenceName="PREPAID_BILL_HISTORY_SEQ")
	@GeneratedValue(generator="PREPAID_BILL_HISTORY_SEQ")
	@Column(name="BILLID")
	private int billId;
	
	private String name;
	private String propertyNo;
	private String address;
	private String secondaryAddress;
	private double previousBal;
	private double rechargeAmt;
	private double consumptionAmt;
	private double closingBal;
	private double mainUnit;
	private double dgUnit;
	private double elRate;
	private double dgRate;
	private String mainAmt;
	private double dgAmt;
	private long totalAmt;
	private String serviceName;
	private String chargeName;
	private double rate;
	private String cbName;
	private double calculatedAmt;
	private double totalAmt1;
	private long grossconsumption;
	private String city;
	private int area;
	private String billNo;
	private Date billDate;
	private String billingPeriod;
	private String mtrNo;
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPropertyNo() {
		return propertyNo;
	}
	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSecondaryAddress() {
		return secondaryAddress;
	}
	public void setSecondaryAddress(String secondaryAddress) {
		this.secondaryAddress = secondaryAddress;
	}
	public double getPreviousBal() {
		return previousBal;
	}
	public void setPreviousBal(double previousBal) {
		this.previousBal = previousBal;
	}
	public double getRechargeAmt() {
		return rechargeAmt;
	}
	public void setRechargeAmt(double rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}
	public double getConsumptionAmt() {
		return consumptionAmt;
	}
	public void setConsumptionAmt(double consumptionAmt) {
		this.consumptionAmt = consumptionAmt;
	}
	public double getClosingBal() {
		return closingBal;
	}
	public void setClosingBal(double closingBal) {
		this.closingBal = closingBal;
	}
	public double getMainUnit() {
		return mainUnit;
	}
	public void setMainUnit(double mainUnit) {
		this.mainUnit = mainUnit;
	}
	public double getDgUnit() {
		return dgUnit;
	}
	public void setDgUnit(double dgUnit) {
		this.dgUnit = dgUnit;
	}
	public double getElRate() {
		return elRate;
	}
	public void setElRate(double elRate) {
		this.elRate = elRate;
	}
	public double getDgRate() {
		return dgRate;
	}
	public void setDgRate(double dgRate) {
		this.dgRate = dgRate;
	}
	public String getMainAmt() {
		return mainAmt;
	}
	public void setMainAmt(String mainAmt) {
		this.mainAmt = mainAmt;
	}
	public double getDgAmt() {
		return dgAmt;
	}
	public void setDgAmt(double dgAmt) {
		this.dgAmt = dgAmt;
	}
	public long getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(long totalAmt) {
		this.totalAmt = totalAmt;
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getCbName() {
		return cbName;
	}
	public void setCbName(String cbName) {
		this.cbName = cbName;
	}
	public double getCalculatedAmt() {
		return calculatedAmt;
	}
	public void setCalculatedAmt(double calculatedAmt) {
		this.calculatedAmt = calculatedAmt;
	}
	public double getTotalAmt1() {
		return totalAmt1;
	}
	public void setTotalAmt1(double totalAmt1) {
		this.totalAmt1 = totalAmt1;
	}
	public long getGrossconsumption() {
		return grossconsumption;
	}
	public void setGrossconsumption(long grossconsumption) {
		this.grossconsumption = grossconsumption;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getBillingPeriod() {
		return billingPeriod;
	}
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public String getMtrNo() {
		return mtrNo;
	}
	public void setMtrNo(String mtrNo) {
		this.mtrNo = mtrNo;
	}
	
	
}
