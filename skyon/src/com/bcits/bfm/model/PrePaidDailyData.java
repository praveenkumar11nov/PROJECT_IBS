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
@Table(name="PREPAID_DAILY_DATA")
@NamedQueries({
	@NamedQuery(name="PrePaidMeters.getConsumerIDs",query="SELECT p.mId,p.meterNumber FROM PrePaidMeters p"),
	@NamedQuery(name="PrePaidDailyData.lowBalanceStatus",query="SELECT DISTINCT p.instId,p.balance,p.mtrSrNo,pp.personId,p.cum_Recharge_Amount,pp.propertyId FROM PrePaidDailyData p ,PrePaidMeters pp Where p.mtrSrNo=pp.meterNumber AND p.instId IN (SELECT MAX(p.instId) From PrePaidDailyData p,PrePaidMeters pp Where p.mtrSrNo=pp.meterNumber GROUP BY p.mtrSrNo) AND p.balance<=1000"),
	@NamedQuery(name="PrePaidDailyData.getConsumptionData",query="SELECT p.instId,p.mtrSrNo,p.readingDT,p.balance,p.daily_Cons_Amt,p.cum_Fixed_Charg_Main,p.cum_Fixed_Charg_Dg,p.cum_Recharge_Amount,p.cum_Kwh_Main,p.cum_Kwh_Dg,p.daily_cum_Kwh_Main,p.daily_cum_Kwh_Dg  FROM PrePaidDailyData p  Where TO_DATE(p.readingDT, 'dd/MM/yyyy')>=TO_DATE(:fromDate, 'dd/MM/yyyy') AND TO_DATE(p.readingDT, 'dd/MM/yyyy')<=TO_DATE(:toDate, 'dd/MM/yyyy') AND p.mtrSrNo=:consumerid ORDER BY TO_DATE(p.readingDT, 'dd/MM/yyyy') "),
	@NamedQuery(name="PrePaidDailyData.getConsumptionDayWiseForAll",query="SELECT p.instId,p.mtrSrNo,p.readingDT,p.balance,p.daily_Cons_Amt,p.cum_Fixed_Charg_Main,p.cum_Fixed_Charg_Dg,p.cum_Recharge_Amount,p.cum_Kwh_Main,p.cum_Kwh_Dg FROM PrePaidDailyData p  Where TO_DATE(p.readingDT, 'dd/MM/yyyy')>=TO_DATE(:fromDate, 'dd/MM/yyyy') AND TO_DATE(p.readingDT, 'dd/MM/yyyy')<=TO_DATE(:toDate, 'dd/MM/yyyy') ORDER BY TO_DATE(p.readingDT, 'dd/MM/yyyy') DESC"),
	@NamedQuery(name="PrePaidDailyData.getMAxReadingDate",query="SELECT pd  FROM PrePaidDailyData pd Where to_date(pd.readingDT,'dd/MM/yyyy') IN (SELECT MAX(to_date(pd.readingDT,'dd/MM/yyyy')) FROM PrePaidDailyData pd Where pd.mtrSrNo=:mtrSrNo GROUP BY pd.mtrSrNo)"),
	@NamedQuery(name="PrePaidDailyData.getMAxminDate",query="SELECT min(pd.readingDT),max(pd.readingDT) FROM PrePaidDailyData pd where pd.mtrSrNo=:mtrSrNo AND to_date(pd.readingDT,'dd/MM/yyyy')>=to_date(:fromDate,'dd/MM/yyyy') AND to_date(pd.readingDT,'dd/MM/yyyy')<=to_date(:toDate,'dd/MM/yyyy') ORDER BY pd.instId ASC"),
	@NamedQuery(name="PrePaidDailyData.getMAxminBalance",query="SELECT pd.balance FROM PrePaidDailyData pd  WHERE pd.readingDT=(SELECT max(pd.readingDT) from PrePaidDailyData pd WHERE pd.mtrSrNo=:mtrSrNo   AND to_date(pd.readingDT,'dd/MM/yyyy')>=to_date(:fromDate,'dd/MM/yyyy') AND to_date(pd.readingDT,'dd/MM/yyyy')<=to_date(:toDate,'dd/MM/yyyy') ) AND pd.mtrSrNo=:mtrSrNo "),
	@NamedQuery(name="PrePaidDailyData.getRechargeAmt",query="SELECT sum(pp.cum_Recharge_Amount1) FROM PrePaidDailyData pp where pp.mtrSrNo=:mtrSrNo AND to_date(pp.readingDT,'dd/MM/yyyy')>=to_date(:fromDate,'dd/MM/yyyy') AND to_date(pp.readingDT,'dd/MM/yyyy')<=to_date(:toDate,'dd/MM/yyyy') ORDER BY pp.instId ASC"),
	@NamedQuery(name="PrePaidDailyData.getminMaxBAL",query="SELECT p.balance FROM PrePaidDailyData p where p.mtrSrNo=:mtrSrNo and to_date(p.readingDT,'dd/MM/yyyy')=to_date(:fromDate,'dd/MM/yyyy')"),
	@NamedQuery(name="PrePaidDailyData.getPreviousData",query="select p from PrePaidDailyData p where p.mtrSrNo=:mtrSrNo and to_date(p.readingDT,'dd/MM/yyyy')=to_date(:fromDate,'dd/MM/yyyy')"),
	@NamedQuery(name="PrePaidDailyData.getCountofRecords",query="SELECT count(p) FROM PrePaidDailyData p WHERE p.mtrSrNo=:mtrSrNo AND to_date(p.readingDT,'dd/MM/yyyy')>=to_date(:fromDate,'dd/MM/yyyy') AND to_date(p.readingDT,'dd/MM/yyyy')<=to_date(:toDate,'dd/MM/yyyy')"),
	@NamedQuery(name="PrePaidDailyData.getminReadingDate",query="SELECT MIN(TO_DATE(p.readingDT,'dd/MM/yyyy')) FROM PrePaidDailyData p WHERE p.mtrSrNo=:mtrSrNo ORDER BY  p.instId"),
	@NamedQuery(name="PrePaidDailyData.checkPreviousData",query="select count(p) from PrePaidDailyData p where p.mtrSrNo=:mtrSrNo and to_date(p.readingDT,'dd/MM/yyyy')=to_date(:fromDate,'dd/MM/yyyy')"),
	@NamedQuery(name="PrePaidDailyData.GenusMtrNoNotGettingExistence",query="select count(p) from GenusMtrNoNotGetting p where p.meterNo=:mtrSrNo and to_date(p.readingDate,'dd/MM/yyyy')=to_date(:fromDate,'dd/MM/yyyy')")
})
public class PrePaidDailyData {
	@Id
	@SequenceGenerator(name="PREPAID_DAILY_DATA_SEQ", sequenceName="PREPAID_DAILY_DATA_SEQ")
	@GeneratedValue(generator="PREPAID_DAILY_DATA_SEQ")
	@Column(name="INSTANT_ID",nullable=false, unique=true,precision=11,scale=0)
	private int instId;
	
	@Column(name="METER_SR_NO")
	private String mtrSrNo;
	
	@Column(name="BALANCE")
	private String balance;
	
	@Column(name="DAILY_CONSUMPTION_AMOUNT")
	private String daily_Cons_Amt;
	
	@Column(name="READING_DATE_TIME")
	private String readingDT;
	
	@Column(name="CUM_FIXED_CHARGE_MAIN")
	private String cum_Fixed_Charg_Main;
	
	@Column(name="CUM_FIXED_CHARGE_DG")
	private String cum_Fixed_Charg_Dg;
	
	@Column(name="CUSTOMER_RECHRGE_AMOUNT")
	private String cum_Recharge_Amount;
	
	@Column(name="CUM_KWH_MAIN")
	private String cum_Kwh_Main;
	
	@Column(name="CUM_KWH_DG")
	private String cum_Kwh_Dg;

	@Column(name="DAILY_CUM_KWH_MAIN")
	private String daily_cum_Kwh_Main;
	
	@Column(name="DAILY_CUM_KWH_DG")
	private String daily_cum_Kwh_Dg;
	
	@Column(name="CUM_RECHARGE_AMT")
	private String cum_Recharge_Amount1;
	
	@Column(name="DAILY_CUM_FIXED_CHARGE_MAIN")
	private String daily_cum_Fixed_Charg_Main;
	
	@Column(name="DAILY_CUM_FIXED_CHARGE_DG")
	private String daily_cum_Fixed_Charg_Dg;
	
	
	
	public String getDaily_cum_Fixed_Charg_Main() {
		return daily_cum_Fixed_Charg_Main;
	}

	public void setDaily_cum_Fixed_Charg_Main(String daily_cum_Fixed_Charg_Main) {
		this.daily_cum_Fixed_Charg_Main = daily_cum_Fixed_Charg_Main;
	}

	public String getDaily_cum_Fixed_Charg_Dg() {
		return daily_cum_Fixed_Charg_Dg;
	}

	public void setDaily_cum_Fixed_Charg_Dg(String daily_cum_Fixed_Charg_Dg) {
		this.daily_cum_Fixed_Charg_Dg = daily_cum_Fixed_Charg_Dg;
	}

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public String getMtrSrNo() {
		return mtrSrNo;
	}

	public void setMtrSrNo(String mtrSrNo) {
		this.mtrSrNo = mtrSrNo;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getDaily_Cons_Amt() {
		return daily_Cons_Amt;
	}

	public void setDaily_Cons_Amt(String daily_Cons_Amt) {
		this.daily_Cons_Amt = daily_Cons_Amt;
	}

	public String getReadingDT() {
		return readingDT;
	}

	public void setReadingDT(String readingDT) {
		this.readingDT = readingDT;
	}

	public String getCum_Fixed_Charg_Main() {
		return cum_Fixed_Charg_Main;
	}

	public void setCum_Fixed_Charg_Main(String cum_Fixed_Charg_Main) {
		this.cum_Fixed_Charg_Main = cum_Fixed_Charg_Main;
	}

	public String getCum_Fixed_Charg_Dg() {
		return cum_Fixed_Charg_Dg;
	}

	public void setCum_Fixed_Charg_Dg(String cum_Fixed_Charg_Dg) {
		this.cum_Fixed_Charg_Dg = cum_Fixed_Charg_Dg;
	}

	public String getCum_Recharge_Amount() {
		return cum_Recharge_Amount;
	}

	public void setCum_Recharge_Amount(String cum_Recharge_Amount) {
		this.cum_Recharge_Amount = cum_Recharge_Amount;
	}

	public String getCum_Kwh_Main() {
		return cum_Kwh_Main;
	}

	public void setCum_Kwh_Main(String cum_Kwh_Main) {
		this.cum_Kwh_Main = cum_Kwh_Main;
	}

	public String getCum_Kwh_Dg() {
		return cum_Kwh_Dg;
	}

	public void setCum_Kwh_Dg(String cum_Kwh_Dg) {
		this.cum_Kwh_Dg = cum_Kwh_Dg;
	}

	public String getDaily_cum_Kwh_Main() {
		return daily_cum_Kwh_Main;
	}

	public void setDaily_cum_Kwh_Main(String daily_cum_Kwh_Main) {
		this.daily_cum_Kwh_Main = daily_cum_Kwh_Main;
	}

	public String getDaily_cum_Kwh_Dg() {
		return daily_cum_Kwh_Dg;
	}

	public void setDaily_cum_Kwh_Dg(String daily_cum_Kwh_Dg) {
		this.daily_cum_Kwh_Dg = daily_cum_Kwh_Dg;
	}

	public String getCum_Recharge_Amount1() {
		return cum_Recharge_Amount1;
	}

	public void setCum_Recharge_Amount1(String cum_Recharge_Amount1) {
		this.cum_Recharge_Amount1 = cum_Recharge_Amount1;
	}
	
   
}
