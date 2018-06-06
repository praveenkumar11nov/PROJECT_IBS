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
@Table(name = "HELPDESK_CUSTOMER_TEMPLATE")
/*@NamedQueries({
	@NamedQuery(name="CustomerItems.readAllCustomerItems",query="SELECT c FROM CustomerItemsEntity c WHERE c.cid=:cid"),
	@NamedQuery(name="CustomerItemsEntity.findQuantity",query="SELECT c.itemQuantity FROM CustomerItemsEntity c where c.cid=:cid"),
})*/
public class CustomerTemplate {
	

	@Id
	@SequenceGenerator(name="customer_template_seq",sequenceName="CUSTOMER_TEMPLATE_SEQ")
	@GeneratedValue(generator="customer_template_seq")
	@Column(name = "CT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int ctid;
	
	@Column(name = "CC_ID")
	private int ccid;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_QUANTITY")
	private double itemQuantity;
	
	@Column(name = "ITEM_PRICE")
	private int itemPrice;
	
	public int getCtid() {
		return ctid;
	}

	public void setCtid(int ctid) {
		this.ctid = ctid;
	}

	
	public int getCcid() {
		return ccid;
	}

	public void setCcid(int ccid) {
		this.ccid = ccid;
	}

	public CustomerItemsEntity getCustomerItemsEntity() {
		return customerItemsEntity;
	}

	public void setCustomerItemsEntity(CustomerItemsEntity customerItemsEntity) {
		this.customerItemsEntity = customerItemsEntity;
	}

	@Column(name = "ITEM_TOTAL_PRICE")
	private double itemTotalPrice;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CC_ID", insertable = false, updatable = false)
	private CustomerItemsEntity customerItemsEntity;
	
	@Column(name = "UOM")
	public String uom;

public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
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

	

}
