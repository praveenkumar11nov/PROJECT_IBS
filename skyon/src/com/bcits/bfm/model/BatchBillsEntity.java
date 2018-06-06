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
@Table(name = "BATCH_BILLS")
@NamedQueries({
	@NamedQuery(name="BatchBillsEntity.getPostData",query="SELECT pb FROM BatchBillsEntity pb ORDER BY accountNo DESC")
	})
public class BatchBillsEntity 
{
	@Id
	@SequenceGenerator(name = "bsw_user_seq", sequenceName = "BATCH_BILLS_SEQ") 
	@GeneratedValue(generator = "bsw_user_seq") 
	@Column(name="ID")		
	private int id;
	
	@Column(name="ACCOUNT_NUMBER")
	private String accountNo;
	
	@Column(name="RECEIPT_NO")
	private String receiptNo;
	
	
	
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(name="SERVICE_TYPE")
	private String serviceType;
	
	@Column(name="PRESENT_READING_DATE")
	private String presentReadingDate;
	
	@Column(name="PRESENT_READING_STATUS")
	private String presentReadingStatus;

	@Column(name="PRESENT_READING")
	private String presentReading;
	
	@Column(name="DG_PR")
	private String DGpresentReading;
	
	@Column(name="PF_READING")
	private String PFReading;
	
	@Column(name="RECORDED_DEMAND")
	private String recordedDemand;
	
	@Column(name="TODI_1")
	private String TOD1;
	@Column(name="TODI_2")
	private String TOD2;
	@Column(name="TODI_3")
	private String TOD3;
	@Column(name="PREVIOUS_BILL_DATE")
	private String previousBillDate;
	
	@Column(name="PREVIOUS_STATUS")
	private String previousStatus;
	
	@Column(name="PREVIOUS_READING")
	private String previousReading;
	
	@Column(name="DG_PREVIOUS_READING")
	private String DGPreviousReading;
	@Column(name="METER_CONSTANT")
	private String meterConstant;
	@Column(name="DG_METER_CONSTANT")
	private String DGMeterConstant;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="PROPERTY")
	private String property;
	
	@Column(name="METER_NUMBER")
	private String meterNo;
	
	@Column(name="RATE")
	private String rate;
	
	@Column(name="PAYMENT_DUE_DATE")
	private String paymentDueDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getMeterNo() {
		return meterNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public String getMiscellanous() {
		return miscellanous;
	}
	public void setMiscellanous(String miscellanous) {
		this.miscellanous = miscellanous;
	}
	
	@Column(name="MISCELLANOUS")
	private String miscellanous;
	
	
	
	public String getDGpresentReading() {
		return DGpresentReading;
	}
	public void setDGpresentReading(String dGpresentReading) {
		DGpresentReading = dGpresentReading;
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
	public String getPresentReadingDate() {
		return presentReadingDate;
	}
	public void setPresentReadingDate(String presentReadingDate) {
		this.presentReadingDate = presentReadingDate;
	}
	public String getPresentReadingStatus() {
		return presentReadingStatus;
	}
	public void setPresentReadingStatus(String presentReadingStatus) {
		this.presentReadingStatus = presentReadingStatus;
	}
	public String getPresentReading() {
		return presentReading;
	}
	public void setPresentReading(String presentReading) {
		this.presentReading = presentReading;
	}
	public String getPFReading() {
		return PFReading;
	}
	public void setPFReading(String pFReading) {
		PFReading = pFReading;
	}
	public String getRecordedDemand() {
		return recordedDemand;
	}
	public void setRecordedDemand(String recordedDemand) {
		this.recordedDemand = recordedDemand;
	}
	public String getTOD1() {
		return TOD1;
	}
	public void setTOD1(String tOD1) {
		TOD1 = tOD1;
	}
	public String getTOD2() {
		return TOD2;
	}
	public void setTOD2(String tOD2) {
		TOD2 = tOD2;
	}
	public String getTOD3() {
		return TOD3;
	}
	public void setTOD3(String tOD3) {
		TOD3 = tOD3;
	}
	public String getPreviousBillDate() {
		return previousBillDate;
	}
	public void setPreviousBillDate(String previousBillDate) {
		this.previousBillDate = previousBillDate;
	}
	public String getPreviousStatus() {
		return previousStatus;
	}
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}
	public String getPreviousReading() {
		return previousReading;
	}
	public void setPreviousReading(String previousReading) {
		this.previousReading = previousReading;
	}
	public String getDGPreviousReading() {
		return DGPreviousReading;
	}
	public void setDGPreviousReading(String dGPreviousReading) {
		DGPreviousReading = dGPreviousReading;
	}
	public String getMeterConstant() {
		return meterConstant;
	}
	public void setMeterConstant(String meterConstant) {
		this.meterConstant = meterConstant;
	}
	public String getDGMeterConstant() {
		return DGMeterConstant;
	}
	public void setDGMeterConstant(String dGMeterConstant) {
		DGMeterConstant = dGMeterConstant;
	}
	

}
