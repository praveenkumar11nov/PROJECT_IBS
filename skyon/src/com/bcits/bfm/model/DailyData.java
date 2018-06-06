package com.bcits.bfm.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DailyData")
public class DailyData {

	private String mtrSrNo;
	private String balance;
	private String readingDT;
	private String daily_Consum_Amount;
	private String cum_Recharge_Amount;
	private String cum_FixChrg_Main;
	private String cum_FixChrg_Dg;
	private String cum_kWh_Main;
	private String cum_kWh_Dg;
	
	//@XmlElement(name="")
	public String getMtrSrNo() {
		return mtrSrNo;
	}
	
	@XmlElement(name="MtrSrNo")
	public void setMtrSrNo(String mtrSrNo) {
		this.mtrSrNo = mtrSrNo;
	}
	
	public String getBalance() {
		return balance;
	}
	
	@XmlElement(name="balance")
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getReadingDT() {
		return readingDT;
	}
	
	@XmlElement(name="readingdt")
	public void setReadingDT(String readingDT) {
		this.readingDT = readingDT;
	}
	public String getDaily_Consum_Amount() {
		return daily_Consum_Amount;
	}
	
	@XmlElement(name="ConsumedAmt")
	public void setDaily_Consum_Amount(String daily_Consum_Amount) {
		this.daily_Consum_Amount = daily_Consum_Amount;
	}
	public String getCum_Recharge_Amount() {
		return cum_Recharge_Amount;
	}
	
	@XmlElement(name="CumReChgAmt")
	public void setCum_Recharge_Amount(String cum_Recharge_Amount) {
		this.cum_Recharge_Amount = cum_Recharge_Amount;
	}
	public String getCum_FixChrg_Main() {
		return cum_FixChrg_Main;
	}
	
	@XmlElement(name="ComFixChgMain")
	public void setCum_FixChrg_Main(String cum_FixChrg_Main) {
		this.cum_FixChrg_Main = cum_FixChrg_Main;
	}
	public String getCum_FixChrg_Dg() {
		return cum_FixChrg_Dg;
	}
	
	@XmlElement(name="ComFixChgDg")
	public void setCum_FixChrg_Dg(String cum_FixChrg_Dg) {
		this.cum_FixChrg_Dg = cum_FixChrg_Dg;
	}
	public String getCum_kWh_Main() {
		return cum_kWh_Main;
	}
	
	@XmlElement(name="Cum_kWh")
	public void setCum_kWh_Main(String cum_kWh_Main) {
		this.cum_kWh_Main = cum_kWh_Main;
	}
	public String getCum_kWh_Dg() {
		return cum_kWh_Dg;
	}
	
	@XmlElement(name="dg_Cum_kwh")
	public void setCum_kWh_Dg(String cum_kWh_Dg) {
		this.cum_kWh_Dg = cum_kWh_Dg;
	}
	@Override
	public String toString() {
		return "InstantData [mtrSrNo=" + mtrSrNo + ", balance=" + balance + ", readingDT=" + readingDT
				+ ", daily_Consum_Amount=" + daily_Consum_Amount + ", cum_Recharge_Amount=" + cum_Recharge_Amount
				+ ", cum_FixChrg_Main=" + cum_FixChrg_Main + ", cum_FixChrg_Dg=" + cum_FixChrg_Dg + ", cum_kWh_Main="
				+ cum_kWh_Main + ", cum_kWh_Dg=" + cum_kWh_Dg + "]";
	}
	
}
