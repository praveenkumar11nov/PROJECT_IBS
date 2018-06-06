package com.bcits.bfm.model;



import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name="ADVANCE_BILL") 
@NamedQueries({
	@NamedQuery(name = "AdvanceBill.findAll", query = "SELECT elb.abBillId,elb.typeOfService,elb.accountId,elb.abBillDate,elb.abEndDate,elb.abBillAmount,elb.abBillNo,elb.status,elb.postType,elb.noMonth,elb.units,tm.transactionName,a.accountNo,p.firstName,p.lastName,(select pr.property_No from  Property pr "
			+ "where pr.propertyId = a.propertyId),(select pr.blocks.blockName from  Property pr "+ "where pr.propertyId = a.propertyId) FROM AdvanceBill elb,TransactionMasterEntity tm  INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE tm.transactionCode=elb.transactionCode ORDER BY elb.abBillId DESC"),
	@NamedQuery(name = "AdvanceBill.setApproveBill", query = "UPDATE AdvanceBill el SET el.status = :status WHERE el.abBillId = :elBillId"),
	@NamedQuery(name = "AdvanceBill.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from AdvanceBill ab INNER JOIN ab.accountObj a INNER JOIN a.person p"),
})
public class AdvanceBill {
	@Id
	@Column(name="AB_ID")
	@SequenceGenerator(name = "ABBILL_SEQ", sequenceName = "ABBILL_SEQ") 
	@GeneratedValue(generator = "ABBILL_SEQ")
	private int abBillId;
	
	@Transient
	private String property_No;
	
	@Column(name="SERVICE_TYPE")
	private String typeOfService;
	
	@Column(name="ACCOUNT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int accountId;
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Account accountObj;
	
	
	
	@Column(name="POST_TYPE")
	private String postType;
	
	@Column(name="NO_MONTH")
	private Integer noMonth;
	
	@Column(name="UNITS")
	private Float units;

	@Column(name="TR_CODE")
    private String transactionCode;

	@Column(name="AB_DT")
	private Date abBillDate;
	
	@Column(name="AB_END_DATE")
	private Date abEndDate;	
	
	@Column(name="AB_AMOUNT")
	private Double abBillAmount;
	
	@Column(name="AB_BILL_NO")
	private String abBillNo;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	
	

	public Float getUnits() {
		return units;
	}

	public void setUnits(Float units) {
		this.units = units;
	}

	public Integer getNoMonth() {
		return noMonth;
	}

	public void setNoMonth(Integer noMonth) {
		this.noMonth = noMonth;
	}
	
	public int getAbBillId() {
		return abBillId;
	}

	public void setAbBillId(int abBillId) {
		this.abBillId = abBillId;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Account getAccountObj() {
		return accountObj;
	}

	public void setAccountObj(Account accountObj) {
		this.accountObj = accountObj;
	}

	

	public Date getAbBillDate() {
		return abBillDate;
	}

	public void setAbBillDate(Date abBillDate) {
		this.abBillDate = abBillDate;
	}

	public Date getAbEndDate() {
		return abEndDate;
	}

	public void setAbEndDate(Date abEndDate) {
		this.abEndDate = abEndDate;
	}

	public Double getAbBillAmount() {
		return abBillAmount;
	}

	public void setAbBillAmount(Double abBillAmount) {
		this.abBillAmount = abBillAmount;
	}

	public String getAbBillNo() {
		return abBillNo;
	}

	public void setAbBillNo(String abBillNo) {
		this.abBillNo = abBillNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	
	
	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 public String getProperty_No() {
			return property_No;
		}

		public void setProperty_No(String property_No) {
			this.property_No = property_No;
		}
	
}
