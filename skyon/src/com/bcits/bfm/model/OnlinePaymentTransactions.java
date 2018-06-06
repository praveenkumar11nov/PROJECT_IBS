package com.bcits.bfm.model;

import java.sql.Timestamp;

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
@Table(name="ONLINE_PAYMENT_TRANSACTIONS") 
@NamedQueries({
	@NamedQuery(name="OnlinePaymentTransactions.commonFilterForAccountNumbersUrl",query="select DISTINCT(o.accountEntity.accountNo) from OnlinePaymentTransactions o"),
	
	@NamedQuery(name="OnlinePaymentTransactions.commonFilterForPropertyNumbersUrl",query="select DISTINCT(pt.property_No) from OnlinePaymentTransactions o,Property pt INNER JOIN o.accountEntity ac INNER JOIN ac.person p WHERE ac.propertyId=pt.propertyId"),
	@NamedQuery(name="OnlinePaymentTransactions.commonFilterForPaymentStatusUrl",query="select DISTINCT(o.payment_status) from OnlinePaymentTransactions o"),
	@NamedQuery(name="OnlinePaymentTransactions.commonFilterForPayUMoneyIdUrl",query="select DISTINCT(o.payumoney_id) from OnlinePaymentTransactions o"),
	@NamedQuery(name="OnlinePaymentTransactions.commonFilterTransactionIdUrl",query="select DISTINCT(o.transaction_id) from OnlinePaymentTransactions o"),
	@NamedQuery(name="OnlinePaymentTransactions.commonFilterForServiceTypeUrl",query="select DISTINCT(o.service_type) from OnlinePaymentTransactions o"),
	@NamedQuery(name="OnlinePaymentTransactions.getTransactionDetails",query="select count(*),(select count(*) from OnlinePaymentTransactions m WHERE UPPER(m.payment_status) LIKE '%SUCCESS%' ),(select count(*) from OnlinePaymentTransactions m1  WHERE UPPER(m1.payment_status) LIKE '%FAIL%') from OnlinePaymentTransactions a group by 1"),


})
public class OnlinePaymentTransactions {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name = "PAYMENT_TRANSACTIONS_SEQ", sequenceName = "PAYMENT_TRANSACTIONS_SEQ") 
	@GeneratedValue(generator = "PAYMENT_TRANSACTIONS_SEQ")
	private int id;
	
	@Column(name="MERCHANT_ID")
	private String merchantId;
	
	@Column(name="PAYUMONEY_ID")
	private String payumoney_id;
	
	@Column(name="TRANSACTION_ID")
	private String transaction_id;
	
	@Column(name="SERVICE_TYPE")
	private String service_type;
	
	@Column(name="CBID")
	private String cbid;
	
	@Column(name="PAYMENT_STATUS")
	private String payment_status;
	
	@Column(name="CREATED_DATE")
	private Timestamp created_date;	
	
	@Column(name="TX_AMOUNT")
	private String tx_amount;	
	
	@Column(name="ACCOUNT_ID")
	private Integer account_id;
	
	@Column(name="ORDER_ID")
	private String orderId;	
	
	@Column(name="RESPMSG")
	private String failedReason;	


	@OneToOne
	@JoinColumn(name="ACCOUNT_ID",insertable=false,updatable=false,nullable=true)
	private Account accountEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayumoney_id() {
		return payumoney_id;
	}

	public void setPayumoney_id(String payumoney_id) {
		this.payumoney_id = payumoney_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getCbid() {
		return cbid;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public String getTx_amount() {
		return tx_amount;
	}

	public void setTx_amount(String tx_amount) {
		this.tx_amount = tx_amount;
	}

	public Integer getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}

	public Account getAccountEntity() {
		return accountEntity;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public void setAccountEntity(Account accountEntity) {
		this.accountEntity = accountEntity;
	}

}
