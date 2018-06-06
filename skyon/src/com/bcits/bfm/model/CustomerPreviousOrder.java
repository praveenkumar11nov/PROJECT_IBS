package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "HELPDESK_CUSTOMER_PRE_ORDER")
@NamedQueries({
	@NamedQuery(name = "CustomerPreOrder.findEmail", query = "SELECT b FROM CustomerPreviousOrder b WHERE b.personId =:personId "),
	
	
})
public class CustomerPreviousOrder {
	
@Id
@SequenceGenerator(name="customer_previous_seq",sequenceName="CUSTOMER_PREVIOUS_SEQ")
@GeneratedValue(generator="customer_previous_seq")
@Column(name = "CP_ID", unique = true, nullable = false, precision = 10, scale = 0)
private int cpid;

@Column(name = "PERSON_ID")
private int personId;

@Column(name = "STORE_ID")
private int storeId;

@Column(name = "STORE_NAME")
private String StoreName;

@Column(name = "ORDER_TYPE")
private String orderType;

@Column(name = "ORDER_STATUS")
private String orderstatus;



public String getOrderType() {
	return orderType;
}

public void setOrderType(String orderType) {
	this.orderType = orderType;
}

public String getOrderstatus() {
	return orderstatus;
}

public void setOrderstatus(String orderstatus) {
	this.orderstatus = orderstatus;
}

public int getStoreId() {
	return storeId;
}

public void setStoreId(int storeId) {
	this.storeId = storeId;
}

public String getStoreName() {
	return StoreName;
}

public void setStoreName(String storeName) {
	StoreName = storeName;
}

public int getPersonId() {
	return personId;
}

public void setPersonId(int personId) {
	this.personId = personId;
}

@Column(name = "CUS_NAME")
private String cusName;

@Column(name = "CUS_NUM")
private String cusNum;

@Column(name = "CREATED_DATE")
private Date created_date;

@Column(name = "CO_ID")
private int cid;

@Column(name = "ITEMS_NAME")
private String itemName;

@Column(name = "QUANTITY")
private double itemQuantity;

public Date getCreated_date() {
	return created_date;
}

public void setCreated_date(Date created_date) {
	this.created_date = created_date;
}

@Column(name = "PRICE")
private int itemPrice;

@Column(name = "TOTAL_PRICE")
private double itemTotalPrice;

@Column(name = "CUSTOMER_STATUS")
private String customerStatus;

public String getCustomerStatus() {
	return customerStatus;
}

public void setCustomerStatus(String customerStatus) {
	this.customerStatus = customerStatus;
}

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "CO_ID", insertable = false, updatable = false)
private CustomerEntity customerEntity;

@Column(name = "Email")
private String Email;

public String getEmail() {
	return Email;
}

public void setEmail(String email) {
	Email = email;
}

public int getCpid() {
	return cpid;
}

public void setCpid(int cpid) {
	this.cpid = cpid;
}

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

public String getItemName() {
	return itemName;
}

public void setItemName(String itemName) {
	this.itemName = itemName;
}

public double getItemQuantity() {
	return itemQuantity;
}

public void setItemQuantity(double itemQuantity) {
	this.itemQuantity = itemQuantity;
}

public int getItemPrice() {
	return itemPrice;
}

public void setItemPrice(int itemPrice) {
	this.itemPrice = itemPrice;
}

public double getItemTotalPrice() {
	return itemTotalPrice;
}

public void setItemTotalPrice(double itemTotalPrice) {
	this.itemTotalPrice = itemTotalPrice;
}

public CustomerEntity getCustomerEntity() {
	return customerEntity;
}

public void setCustomerEntity(CustomerEntity customerEntity) {
	this.customerEntity = customerEntity;
}



public String getCusName() {
	return cusName;
}

public void setCusName(String cusName) {
	this.cusName = cusName;
}

public String getCusNum() {
	return cusNum;
}

public void setCusNum(String cusNum) {
	this.cusNum = cusNum;
}


}


