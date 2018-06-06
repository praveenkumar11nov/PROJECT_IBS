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
	@NamedQuery(name="BillDocument.getBlog",query="select bd.billDoc from BillDocument bd where bd.electricityBillEntity.elBillId=:elBillId"),
	@NamedQuery(name = "ElectricityBillsEntity.downloadBillONAccount", query = "SELECT elb FROM ElectricityBillEntity elb  WHERE elb.billMonth BETWEEN TO_DATE(:strDate, 'YYYY-MM-DD') and TO_DATE(:pesentDate, 'YYYY-MM-DD') and  elb.typeOfService =:typeOfService and elb.postType='Bill' and elb.status !='Cancelled' and elb.accountId =:accNo "),
})
@Entity
@Table(name="BILLDOC")
public class BillDocument {
	@Id
	@Column(name="BILLDOC_ID")
	@SequenceGenerator(name = "billdoc_seq", sequenceName = "BILLDOC_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "billdoc_seq")
	private int billDocId;
	
	@OneToOne	 
	@JoinColumn(name = "ELB_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private ElectricityBillEntity electricityBillEntity;
	
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


	


	public ElectricityBillEntity getElectricityBillEntity() {
		return electricityBillEntity;
	}


	public void setElectricityBillEntity(ElectricityBillEntity electricityBillEntity) {
		this.electricityBillEntity = electricityBillEntity;
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




	public BillDocument(ElectricityBillEntity electricityBillEntity,
			Blob billDoc) {
		
		this.electricityBillEntity = electricityBillEntity;
		this.billDoc = billDoc;
	}
	 
	 
}
