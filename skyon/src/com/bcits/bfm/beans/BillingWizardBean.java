package com.bcits.bfm.beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BillingWizardBean {
	
	// Customer Information
	private String blockName;
	
	private int propertyId;
	
	private int elTariffID;
	
	private String todApplicable;

	// Accounts Information Fields
	private int personId;
	
	private String accountNo;
	
	private String accountType;
	
	// Service Parameter Information Fields
	private int spmSequence;
	
	private String serviceType;
	
	private String spmName;
	
	private String spmdataType;
	
	private String spmDescription;
	
	// Electricity Ledger Information Fields
	private int accountId;
	
	private String ledgerPeriod;
	
	private Date ledgerDate;
	
	private String postType;
	
	private String postReference;
	
	private int tmId ;
	
	private double creditAmount;
	
	private double debitAmount;
	
	private double balance;
	
	private String postedToBill;
	
	private Date postedBillDate;  
	
	private String postedToGl;  
	
	private Date postedGlDate;
	
	private int transactionSequence;
	
	private String ledgerType;
	
	// Electricity Meters Information Fields
	private String meterSerialNo;
	
	private String meterType;
	
	private String meterOwnerShip;
	
	private int currentSpId;
	
	private int currentAccountId;
	
	private Date fixedDate;
	
	private String fixedBy;
	
	// Service Route Information Fields
	/*private int propertyId;*/
	
	private String routeName;
	
	private int srId;
	
	private String subRouteName;
	
	private int personIdServiceRoute;
	
	private Date readDay;
	
	private int billWindowFrom;
	
	private int billWindowTo;
	
	private String readCycle;
	
	private String routeDescription;
	
	private Timestamp lastReadDate;
	
	private String serviceRouteNodeType;
	
	private int elMeterId;
	
	private Date estimationDay;
	
	// Service Point Information Fields
	private int propertyIdServicePoint;
	
	private String typeOfService;
	
	private String serviceMetered;
	
	private Date commissionDate;
	
	private Date deCommissionDate;
	
	private String serviceLocation;
	
	private List<String> parameterValue;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public List<String> getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(List<String> parameterValue) {
		this.parameterValue = parameterValue;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getSpmSequence() {
		return spmSequence;
	}

	public void setSpmSequence(int spmSequence) {
		this.spmSequence = spmSequence;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSpmName() {
		return spmName;
	}

	public void setSpmName(String spmName) {
		this.spmName = spmName;
	}

	public String getSpmdataType() {
		return spmdataType;
	}

	public void setSpmdataType(String spmdataType) {
		this.spmdataType = spmdataType;
	}

	public String getSpmDescription() {
		return spmDescription;
	}

	public void setSpmDescription(String spmDescription) {
		this.spmDescription = spmDescription;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getLedgerPeriod() {
		return ledgerPeriod;
	}

	public void setLedgerPeriod(String ledgerPeriod) {
		this.ledgerPeriod = ledgerPeriod;
	}

	public Date getLedgerDate() {
		return ledgerDate;
	}

	public void setLedgerDate(Date ledgerDate) {
		this.ledgerDate = ledgerDate;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPostReference() {
		return postReference;
	}

	public void setPostReference(String postReference) {
		this.postReference = postReference;
	}

	public int getTmId() {
		return tmId;
	}

	public void setTmId(int tmId) {
		this.tmId = tmId;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getPostedToBill() {
		return postedToBill;
	}

	public void setPostedToBill(String postedToBill) {
		this.postedToBill = postedToBill;
	}

	public Date getPostedBillDate() {
		return postedBillDate;
	}

	public void setPostedBillDate(Date postedBillDate) {
		this.postedBillDate = postedBillDate;
	}

	public String getPostedToGl() {
		return postedToGl;
	}

	public void setPostedToGl(String postedToGl) {
		this.postedToGl = postedToGl;
	}

	public Date getPostedGlDate() {
		return postedGlDate;
	}

	public void setPostedGlDate(Date postedGlDate) {
		this.postedGlDate = postedGlDate;
	}

	public int getTransactionSequence() {
		return transactionSequence;
	}

	public void setTransactionSequence(int transactionSequence) {
		this.transactionSequence = transactionSequence;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}

	public String getMeterSerialNo() {
		return meterSerialNo;
	}

	public void setMeterSerialNo(String meterSerialNo) {
		this.meterSerialNo = meterSerialNo;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getMeterOwnerShip() {
		return meterOwnerShip;
	}

	public void setMeterOwnerShip(String meterOwnerShip) {
		this.meterOwnerShip = meterOwnerShip;
	}

	public int getCurrentSpId() {
		return currentSpId;
	}

	public void setCurrentSpId(int currentSpId) {
		this.currentSpId = currentSpId;
	}

	public int getCurrentAccountId() {
		return currentAccountId;
	}

	public void setCurrentAccountId(int currentAccountId) {
		this.currentAccountId = currentAccountId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getSubRouteName() {
		return subRouteName;
	}

	public void setSubRouteName(String subRouteName) {
		this.subRouteName = subRouteName;
	}

	public int getPersonIdServiceRoute() {
		return personIdServiceRoute;
	}

	public void setPersonIdServiceRoute(int personIdServiceRoute) {
		this.personIdServiceRoute = personIdServiceRoute;
	}

	public Date getReadDay() {
		return readDay;
	}

	public void setReadDay(Date readDay) {
		this.readDay = readDay;
	}

	public int getBillWindowFrom() {
		return billWindowFrom;
	}

	public void setBillWindowFrom(int billWindowFrom) {
		this.billWindowFrom = billWindowFrom;
	}

	public int getBillWindowTo() {
		return billWindowTo;
	}

	public void setBillWindowTo(int billWindowTo) {
		this.billWindowTo = billWindowTo;
	}

	public String getReadCycle() {
		return readCycle;
	}

	public void setReadCycle(String readCycle) {
		this.readCycle = readCycle;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}

	public Timestamp getLastReadDate() {
		return lastReadDate;
	}

	public void setLastReadDate(Timestamp lastReadDate) {
		this.lastReadDate = lastReadDate;
	}

	public String getServiceRouteNodeType() {
		return serviceRouteNodeType;
	}

	public void setServiceRouteNodeType(String serviceRouteNodeType) {
		this.serviceRouteNodeType = serviceRouteNodeType;
	}

	public int getElMeterId() {
		return elMeterId;
	}

	public void setElMeterId(int elMeterId) {
		this.elMeterId = elMeterId;
	}

	public Date getEstimationDay() {
		return estimationDay;
	}

	public void setEstimationDay(Date estimationDay) {
		this.estimationDay = estimationDay;
	}

	public int getPropertyIdServicePoint() {
		return propertyIdServicePoint;
	}

	public void setPropertyIdServicePoint(int propertyIdServicePoint) {
		this.propertyIdServicePoint = propertyIdServicePoint;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public String getServiceMetered() {
		return serviceMetered;
	}

	public void setServiceMetered(String serviceMetered) {
		this.serviceMetered = serviceMetered;
	}

	public Date getCommissionDate() {
		return commissionDate;
	}

	public void setCommissionDate(Date commissionDate) {
		this.commissionDate = commissionDate;
	}

	public Date getDeCommissionDate() {
		return deCommissionDate;
	}

	public void setDeCommissionDate(Date deCommissionDate) {
		this.deCommissionDate = deCommissionDate;
	}

	public String getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getFixedBy() {
		return fixedBy;
	}

	public void setFixedBy(String fixedBy) {
		this.fixedBy = fixedBy;
	}

	public Date getFixedDate() {
		return fixedDate;
	}

	public void setFixedDate(Date fixedDate) {
		this.fixedDate = fixedDate;
	}

	public int getSrId() {
		return srId;
	}

	public void setSrId(int srId) {
		this.srId = srId;
	}

	public int getElTariffID() {
		return elTariffID;
	}

	public void setElTariffID(int elTariffID) {
		this.elTariffID = elTariffID;
	}

	public String getTodApplicable() {
		return todApplicable;
	}

	public void setTodApplicable(String todApplicable) {
		this.todApplicable = todApplicable;
	}
	
}
