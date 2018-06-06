package com.bcits.bfm.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="ONLINE_PAYTM_TRANSACTION")
@NamedQueries({
	@NamedQuery(name="BankTransactionHistory.getPayTimeDetails",query="SELECT b.id,b.txn_ID,b.mid,b.txn_Date,b.txn_Amount,b.payment_Mode,b.cust_ID,b.issuing_Bank,b.status,b.settled_Amt,b.commission_Amt,b.settled_Date FROM BankTransactionHistory b "),

})
public class BankTransactionHistory {
	@Id
	@SequenceGenerator(name = "online_paytm_transaction_seq", sequenceName = "ONLINE_PAYTM_TRANSACTION_SEQ")
	@GeneratedValue(generator = "online_paytm_transaction_seq")

	@Column(name="ID", unique=true, nullable=false, precision=11, scale=0)
 private Integer id;
	
	@Column(name="TXN_ID")
  private String txn_ID;
	
	@Column(name="MID")
  private String mid;
	
	@Column(name="TXN_DATE")
  private Date txn_Date;
	
	@Column(name="TXN_CHANNEL")
  private String txn_Channel;
	
	@Column(name="TXN_AMOUNT")
  private String txn_Amount;
	
	@Column(name="CURRENT_TYPE")
  private	String currency_Type;
	
	@Column(name="ORDER_ID")
  private String order_Id;
	
	@Column(name="RESPONSE_CODE")
  private	String response_Code;
	
	@Column(name="PAYMENT_MODE")
  private String payment_Mode;
	
  @Column(name="BANK_TXN_ID")
  private String bank_Txn_ID;
	
	@Column(name="ACCOUNT_NO")
  private	String cust_ID;
	
	@Column(name="CC_DD_LAST_4")
  private String CC_DC_Last4;
	
	@Column(name="ISSUING_BANK")
  private String issuing_Bank;
	
	@Column(name="BANK_GATEWAY")
  private String bank_gateway;
	
	@Column(name="STATUS")
  private String status;
	
	@Column(name="SETTLED_AMOUNT")
  private String settled_Amt;
	
	@Column(name="STATUS_TYPE")
  private	String status_type;

	@Column(name="COMMISION_AMOUNT")
  private String commission_Amt;
	
	@Column(name="SERVICE_TXN")
  private String service_Tax;
	
	@Column(name="SETTLED_DATE")
  private Date settled_Date;
	
	@Column(name="WEBSITE")
  private String website;
	
	@Column(name="BASE_AMOUNT")
  private String base_Amount;
	
	@Column(name="TXN_UPDATED_DATE")
  private Date txn_Updated_Date;
	
	@Column(name="ACCOUNT_ID")
  private String account_Id;	
  
/*	@OneToOne
	@JoinColumn(name="ACCOUNT_ID",insertable=false,updatable=false,nullable=true)
	private Account accountEntity;*/
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTxn_ID() {
		return txn_ID;
	}

	public void setTxn_ID(String txn_ID) {
		this.txn_ID = txn_ID;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Date getTxn_Date() {
		return txn_Date;
	}

	public void setTxn_Date(Date txn_Date) {
		this.txn_Date = txn_Date;
	}

	public String getTxn_Channel() {
		return txn_Channel;
	}

	public void setTxn_Channel(String txn_Channel) {
		this.txn_Channel = txn_Channel;
	}

	public String getTxn_Amount() {
		return txn_Amount;
	}

	public void setTxn_Amount(String txn_Amount) {
		this.txn_Amount = txn_Amount;
	}

	public String getCurrency_Type() {
		return currency_Type;
	}

	public void setCurrency_Type(String currency_Type) {
		this.currency_Type = currency_Type;
	}

	public String getOrder_Id() {
		return order_Id;
	}

	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}

	public String getResponse_Code() {
		return response_Code;
	}

	public void setResponse_Code(String response_Code) {
		this.response_Code = response_Code;
	}

	public String getPayment_Mode() {
		return payment_Mode;
	}

	public void setPayment_Mode(String payment_Mode) {
		this.payment_Mode = payment_Mode;
	}

	public String getBank_Txn_ID() {
		return bank_Txn_ID;
	}

	public void setBank_Txn_ID(String bank_Txn_ID) {
		this.bank_Txn_ID = bank_Txn_ID;
	}

	public String getCust_ID() {
		return cust_ID;
	}

	public void setCust_ID(String cust_ID) {
		this.cust_ID = cust_ID;
	}

	public String getCC_DC_Last4() {
		return CC_DC_Last4;
	}

	public void setCC_DC_Last4(String cC_DC_Last4) {
		CC_DC_Last4 = cC_DC_Last4;
	}

	public String getIssuing_Bank() {
		return issuing_Bank;
	}

	public void setIssuing_Bank(String issuing_Bank) {
		this.issuing_Bank = issuing_Bank;
	}

	public String getBank_gateway() {
		return bank_gateway;
	}

	public void setBank_gateway(String bank_gateway) {
		this.bank_gateway = bank_gateway;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_type() {
		return status_type;
	}

	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}

	public String getCommission_Amt() {
		return commission_Amt;
	}

	public void setCommission_Amt(String commission_Amt) {
		this.commission_Amt = commission_Amt;
	}

	public String getSettled_Amt() {
		return settled_Amt;
	}

	public void setSettled_Amt(String settled_Amt) {
		this.settled_Amt = settled_Amt;
	}

	public String getService_Tax() {
		return service_Tax;
	}

	public void setService_Tax(String service_Tax) {
		this.service_Tax = service_Tax;
	}

	public Date getSettled_Date() {
		return settled_Date;
	}

	public void setSettled_Date(Date settled_Date) {
		this.settled_Date = settled_Date;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBase_Amount() {
		return base_Amount;
	}

	public void setBase_Amount(String base_Amount) {
		this.base_Amount = base_Amount;
	}

	public Date getTxn_Updated_Date() {
		return txn_Updated_Date;
	}

	public void setTxn_Updated_Date(Date txn_Updated_Date) {
		this.txn_Updated_Date = txn_Updated_Date;
	}

	public String getAccount_Id() {
		return account_Id;
	}

	public void setAccount_Id(String account_Id) {
		this.account_Id = account_Id;
	}

	@Override
	public String toString() {
		return "BankTransactionHistory [id=" + id + ", txn_ID=" + txn_ID + ", mid=" + mid + ", txn_Date=" + txn_Date
				+ ", txn_Channel=" + txn_Channel + ", txn_Amount=" + txn_Amount + ", currency_Type=" + currency_Type
				+ ", order_Id=" + order_Id + ", response_Code=" + response_Code + ", payment_Mode=" + payment_Mode
				+ ", bank_Txn_ID=" + bank_Txn_ID + ", cust_ID=" + cust_ID + ", CC_DC_Last4=" + CC_DC_Last4
				+ ", issuing_Bank=" + issuing_Bank + ", bank_gateway=" + bank_gateway + ", status=" + status
				+ ", settled_Amt=" + settled_Amt + ", status_type=" + status_type + ", commission_Amt=" + commission_Amt
				+ ", service_Tax=" + service_Tax + ", settled_Date=" + settled_Date + ", website=" + website
				+ ", base_Amount=" + base_Amount + ", txn_Updated_Date=" + txn_Updated_Date + ", account_Id="
				+ account_Id + "]";
	}

	

	
}
