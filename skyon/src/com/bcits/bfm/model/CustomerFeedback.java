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
@Table(name="CUSTOMER_FEEDBACK")
@NamedQueries({
	@NamedQuery(name="CustomerFeedback.UserData",query="SELECT c FROM CustomerFeedback c where c.storeid=:id"),
})
public class CustomerFeedback {

	@Id
	@SequenceGenerator(name = "customer_fedback_seq", sequenceName = "CUSTOMER_FEDBACK_SEQ")
	@GeneratedValue(generator = "customer_fedback_seq")
	@Column(name = "FID", unique = true, nullable = false, precision = 10, scale = 0)
	private int fid;
	
	@Column(name = "STORE_NAME")
	private String storename;
	
	@Column(name = "STORE_ID")
	private int storeid;
	
	@Column(name = "CUST_ID")
	private int custId;
	
	@Column(name = "CUST_EMAIL")
	private String CustEmail;
	
	@Column(name = "FEEDBACK")
	private String feedback;

	@Column(name = "USER_NAME")
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustEmail() {
		return CustEmail;
	}

	public void setCustEmail(String custEmail) {
		CustEmail = custEmail;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
}
