package com.bcits.bfm.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BILL_DOC")
public class BillDocEntity 
{
	@Id
	@SequenceGenerator(name = "bsw_user_seq", sequenceName = "BILL_DOC_SEQ") 
	@GeneratedValue(generator = "bsw_user_seq") 
	@Column(name="BILLDOCID")
	private int id;
	
	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	@Column(name="BILLDOC")
	private Blob blob;
	
	@Column(name="BID")
	private int bid;
	

}
