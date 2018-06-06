package com.bcits.bfm.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "HELPDESK_CUSTOMER_ITEMS")
@NamedQueries({
	@NamedQuery(name="CustomerItems.readAllCustomerItems",query="SELECT c FROM CustomerItemsEntity c WHERE c.cid=:cid"),
	@NamedQuery(name="CustomerItemsEntity.findQuantity",query="SELECT c.itemQuantity FROM CustomerItemsEntity c where c.cid=:cid"),
})
public class CustomerItemsEntity {
	

	@Id
	@SequenceGenerator(name="customer_items_seq",sequenceName="CUSTOMER_ITEM_SEQ")
	@GeneratedValue(generator="customer_items_seq")
	@Column(name = "CC_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int ccid;
	
	@Column(name = "C_ID")
	private int cid;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_QUANTITY")
	private double itemQuantity;
	
	@Column(name = "ITEM_PRICE")
	private float itemPrice;
	
	@Column(name = "ITEM_TOTAL_PRICE")
	private float itemTotalPrice;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "C_ID", insertable = false, updatable = false)
	private CustomerEntity customerEntity;
	
	@Column(name = "UOM")
	public String uom;

public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

public int getCcid() {
		return ccid;
	}

	public void setCcid(int ccid) {
		this.ccid = ccid;
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

	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public float getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(float itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}

}
