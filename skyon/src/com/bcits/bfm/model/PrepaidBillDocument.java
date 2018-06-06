package com.bcits.bfm.model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;
@NamedQueries({
	@NamedQuery(name="PrepaidBillDocument.getBlog",query="select bd.billDoc from PrepaidBillDocument bd where bd.billNo=:billNo"),
	
	
})
@Entity
@Table(name="PREPAID_BILLDOC")
public class PrepaidBillDocument {
	@Id
	@Column(name="BILLDOC_ID")
	@SequenceGenerator(name = "PREPAID_BILLDOC_SEQ", sequenceName = "PREPAID_BILLDOC_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "PREPAID_BILLDOC_SEQ")
	private int billDocId;
	
	/*@OneToOne	 
	@JoinColumn(name = "ELB_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private ElectricityBillEntity electricityBillEntity;*/
	@Column(name="PROPERTY_ID")
	private int property_Id;
	
	@Column(name="BILL_MONTH")
	private Date bill_Month;
	 
	@Column(name="BILL_NO")
	private String billNo;  
	
	@Lob
	@Column(name="BILL_DOC")
	private Blob billDoc;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	
	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	  lastUpdatedDT = new Timestamp(new Date().getTime());
     
	 }
	/**
	 */
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  lastUpdatedDT = new Timestamp(new Date().getTime());
	 }


	public int getBillDocId() {
		return billDocId;
	}


	public void setBillDocId(int billDocId) {
		this.billDocId = billDocId;
	}


	public Blob getBillDoc() {
		return billDoc;
	}


	public void setBillDoc(Blob billDoc) {
		this.billDoc = billDoc;
	}

	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}


	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}
	public int getProperty_Id() {
		return property_Id;
	}
	public void setProperty_Id(int property_Id) {
		this.property_Id = property_Id;
	}
	public Date getBill_Month() {
		return bill_Month;
	}
	public void setBill_Month(Date bill_Month) {
		this.bill_Month = bill_Month;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public PrepaidBillDocument(int property_Id, Date bill_Month, String billNo, Blob billDoc) {
		super();
		this.property_Id = property_Id;
		this.bill_Month = bill_Month;
		this.billNo = billNo;
		this.billDoc = billDoc;
	}

	
	 
	 
}
