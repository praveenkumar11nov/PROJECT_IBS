package com.bcits.bfm.model;

import java.sql.Blob;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;







@Entity
@Table(name = "HELPDESK_CUSTOMER")
@NamedQueries({
	@NamedQuery(name="CustomerEntity.findAllData",query="SELECT c FROM CustomerEntity c where ordertype='order' AND  template='true'"),
	@NamedQuery(name="CustomerEntity.update",query="UPDATE CustomerEntity c SET c.totalQuantity=:totalQuantity  where c.cid=:cid "),
	@NamedQuery(name="CustomerEntity.findQuantity",query="SELECT SUM(c.totalQuantity) FROM CustomerEntity c where c.cid=:cid"),
	@NamedQuery(name = "Customer.UpdateStatus", query = "UPDATE CustomerEntity p SET  p.customerStatus = :CStatus WHERE p.cid = :cId"),
	@NamedQuery(name = "Customer.uploadImageOnId", query = "UPDATE CustomerEntity p SET p.customerImage= :customerImage, p.lastUpdatedDate = :lastUpdatedDate WHERE p.cid= :cid" ),
	@NamedQuery(name = "Customer.getImage", query= "SELECT p.customerImage FROM CustomerEntity p WHERE p.cid= :cid"),	
	@NamedQuery(name = "CustomerOrder.UpdateStatus", query = "UPDATE CustomerEntity p SET  p.customerStatus = :COStatus WHERE p.cid = :cId"),
	@NamedQuery(name="Customer.findEmail",query="SELECT a FROM CustomerEntity a WHERE a.custEmail=:custEmail"),
	
	@NamedQuery(name = "CustomerEntity.setStatus", query = "UPDATE CustomerEntity a SET a.lastUpdatedDate = :lastUpdatedDate, a.customerStatus = :customerStatus WHERE a.cid = :cid"),

	@NamedQuery(name = "Customer.getPersonDetails", query = "SELECT b FROM CustomerEntity b WHERE b.custEmail =:user "),
	@NamedQuery(name = "Customer.getPersonId", query = "SELECT b FROM CustomerEntity b WHERE b.custNum =:custNum "),
	@NamedQuery(name = "Customer.updateVisitorImage", query = "Update CustomerEntity v SET v.customerImage = :custImage WHERE v.cid = :cid"),
	@NamedQuery(name="CustomerEntity.findAllDatas",query="SELECT c FROM CustomerEntity c where c.storeid=:storeid AND ordertype='order'"),
	@NamedQuery(name="CustomerEntity.findAllEnquiry",query="SELECT c FROM CustomerEntity c where c.storeid=:storeid AND ordertype='enquiry'"),
	@NamedQuery(name = "Customer.getDetails", query = "SELECT b FROM CustomerEntity b WHERE b.custEmail =:custEmail "),
	@NamedQuery(name = "CustomerEntity.UserData", query = "SELECT b FROM CustomerEntity b WHERE b.custEmail =:user "),
	@NamedQuery(name = "CustomerEntity.findAllOrders", query = "SELECT b FROM CustomerEntity b WHERE b.customerStatus =:status  AND ordertype='order'"),
	@NamedQuery(name = "CustomerEntity.findAllEnquirys", query = "SELECT b FROM CustomerEntity b WHERE b.customerStatus =:status  AND ordertype='enquiry'"),
	@NamedQuery(name="CustomerEntity.Delete",query="DELETE FROM  CustomerEntity d WHERE d.cid=:cid "),
	
})
public class CustomerEntity {
	
@Id
@SequenceGenerator(name="customer_entity_seq",sequenceName="CUSTOMER_ENTITY_SEQ")
@GeneratedValue(generator="customer_entity_seq")
@Column(name = "CUST_ID")
private int cid;

@Column(name = "CUST_NAME")
private String custName;

@Column(name = "CUST_NUM")
private String custNum;

@Column(name = "CUST_EMAIL")
private String custEmail;
 
@Column(name = "CREATED_DATE")
private Date createdDate; 

@Column(name = "LAST_UPDATED_DT")
private  Date lastUpdatedDate; 




@Column(name = "PERSON_ID")
private int personId;





@Column(name = "TEMPLATE_STATUS")
private String template;


@Column(name = "ORDER_ADDRESS")
private String orderAddress;

public String getTemplate() {
	return template;
}

public void setTemplate(String template) {
	this.template = template;
}



public String getOrderAddress() {
	return orderAddress;
}

public void setOrderAddress(String orderAddress) {
	this.orderAddress = orderAddress;
}

public int getStoreid() {
	return storeid;
}

public int getPersonId() {
	return personId;
}

public void setPersonId(int personId) {
	this.personId = personId;
}

public void setStoreid(int storeid) {
	this.storeid = storeid;
}

public String getStorename() {
	return storename;
}

public void setStorename(String storename) {
	this.storename = storename;
}

@Column(name = "STORE_ID")
private int storeid;

@Column(name = "STORE_NAME")
private String storename;

public Date getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}

@Lob
@Column(name = "CUSTOMER_IMAGE")
private Blob customerImage;






public Blob getCustomerImage() {
	return customerImage;
}

public void setCustomerImage(Blob customerImage) {
	this.customerImage = customerImage;
}

public String getItemsList() {
	return itemsList;
}

public void setItemsList(String itemsList) {
	this.itemsList = itemsList;
}

/*public String getCustomerorderStatus() {
	return customerorderStatus;
}

public void setCustomerorderStatus(String customerorderStatus) {
	this.customerorderStatus = customerorderStatus;
}
*/
@Column(name = "TOTAL_QUANTITY")
private float totalQuantity;
  
@Column(name = "TOTAL_PRICE")
private float totalPrice;

@Column(name = "CUSTOMER_ORDER_STATUS")
private String customerStatus;


/*@Column(name = "CUSTOMER_STATUS1")
private String customerorderStatus;*/

@Column(name = "ITEMS_LIST")
private String itemsList;

@Column(name = "ORDER_TYPE")
private String ordertype;




public String getOrdertype() {
	return ordertype;
}

public void setOrdertype(String ordertype) {
	this.ordertype = ordertype;
}

public String getCustomerStatus() {
	return customerStatus;
}

public void setCustomerStatus(String customerStatus) {
	this.customerStatus = customerStatus;
}

public String getCustEmail() {
	return custEmail;
}

public void setCustEmail(String custEmail) {
	this.custEmail = custEmail;
}

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

public String getCustName() {
	return custName;
}

public void setCustName(String custName) {
	this.custName = custName;
}

public String getCustNum() {
	return custNum;
}

public void setCustNum(String custNum) {
	this.custNum = custNum;
}


public float getTotalQuantity() {
	return totalQuantity;
}

public void setTotalQuantity(float quantity1) {
	this.totalQuantity = quantity1;
}



public float getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(float totalPrice) {
	this.totalPrice = totalPrice;
}


@PrePersist  
public void setCreationDate() {  
    this.createdDate = new Date(new java.util.Date().getTime()); 
    this.lastUpdatedDate = new Date(new java.util.Date().getTime()); 
   
  
}  
/*@PreUpdate  
public void setChangeDate() {
	this.lastUpdatedDate = new Date(new java.util.Date().getTime()); 
	this.createdDate = new Date(new java.util.Date().getTime()); 
}*/

public Date getLastUpdatedDate() {
	return lastUpdatedDate;
}

public void setLastUpdatedDate(Date lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
}



/** 
 * Sets updatedDate and lastupdatedDate before update 
 */  

	

}
