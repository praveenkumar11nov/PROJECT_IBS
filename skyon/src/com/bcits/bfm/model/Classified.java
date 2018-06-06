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
@Table(name="CLASSIFIED")
@NamedQueries({
	@NamedQuery(name="Classified.findAllCheckedData",query="SELECT c.id, c.apartmentNo, c.sellerName, c.emailId, c.phoneNo, c.description FROM Classified c WHERE c.status ='checked'"),
	
})
public class Classified{
	
	@Id
	@SequenceGenerator(name ="CLASSIFIED_SEQ", sequenceName = "CLASSIFIED_SEQ") 
	@GeneratedValue(generator = "CLASSIFIED_SEQ") 
	@Column(name="ID", unique=true)
	private int id;
	
	@Column(name="APARTMENT_NO")
	private String apartmentNo;
	
	@Column(name="SELLER_NAME")
	private String sellerName;
	
	@Column(name="EMAI_ID")
	private String  emailId;
	
	@Column(name="PHONE_NO")
	private String phoneNo;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="STATUS")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getApartmentNo() {
		return apartmentNo;
	}

	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

}
