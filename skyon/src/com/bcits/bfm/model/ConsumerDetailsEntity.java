package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CONSUMER_DETAILS")
public class ConsumerDetailsEntity {
    
	@Id
	@SequenceGenerator(name="CONSUMER_DETAILS_SEQ", sequenceName="CONSUMER_DETAILS_SEQ")
	@GeneratedValue(generator="CONSUMER_DETAILS_SEQ")
	@Column(name="CID", unique=true , nullable=false, precision=11, scale=0)
	private int cId;
	
	@Column(name="CONSUMER_ID")
	private String consumerId;
	
	@Column(name="FIRST_NAME")
	private String first_Name;
	
	@Column(name="LAST_NAME")
	private String last_Name;
	
	@Column(name="CATEGORY_NAME")
	private String category_Name;
	
	@Column(name="METER_NUMBER")
	private String meter_Id;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="SANCT_LOAD(KW)")
	private int sanct_Load;
	
	@Column(name="ADDRESS_COMMUNICATION")
	private String address_Communication;
	
	@Column(name="INSTALL_DATE")
	private Date install_Date;
	
	@Column(name="CONNECTION_DATE")
	private Date connection_Date;

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getFirst_Name() {
		return first_Name;
	}

	public void setFirst_Name(String first_Name) {
		this.first_Name = first_Name;
	}

	public String getLast_Name() {
		return last_Name;
	}

	public void setLast_Name(String last_Name) {
		this.last_Name = last_Name;
	}

	public String getCategory_Name() {
		return category_Name;
	}

	public void setCategory_Name(String category_Name) {
		this.category_Name = category_Name;
	}

	public String getMeter_Id() {
		return meter_Id;
	}

	public void setMeter_Id(String meter_Id) {
		this.meter_Id = meter_Id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSanct_Load() {
		return sanct_Load;
	}

	public void setSanct_Load(int sanct_Load) {
		this.sanct_Load = sanct_Load;
	}

	public String getAddress_Communication() {
		return address_Communication;
	}

	public void setAddress_Communication(String address_Communication) {
		this.address_Communication = address_Communication;
	}

	public Date getInstall_Date() {
		return install_Date;
	}

	public void setInstall_Date(Date install_Date) {
		this.install_Date = install_Date;
	}

	public Date getConnection_Date() {
		return connection_Date;
	}

	public void setConnection_Date(Date connection_Date) {
		this.connection_Date = connection_Date;
	}
	
	
}
