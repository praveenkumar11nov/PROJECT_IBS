package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CONSUMPTION_NOT_GETTINGMTR")
public class GenusMtrNoNotGetting {

	@Id
	@SequenceGenerator(name="CONSUMPTION_NOT_GETTINGMTR_SEQ",sequenceName="CONSUMPTION_NOT_GETTINGMTR_SEQ")
	@GeneratedValue(generator="CONSUMPTION_NOT_GETTINGMTR_SEQ")
	@Column(name="GID")
	private int gid;
	
	@Column(name="METER_NUMBER")
	private String meterNo;
	
	@Column(name="READING_DATE")
	private String readingDate;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="GENUSSTATUS")
	private String status;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
}
