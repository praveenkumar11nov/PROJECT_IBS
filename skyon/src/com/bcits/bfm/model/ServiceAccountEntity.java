package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ACCOUNT_SA")  
@NamedQueries({
	@NamedQuery(name = "ServiceAccountEntity.findAllById", query = "SELECT bli FROM ServiceAccountEntity bli WHERE bli.serviceMastersEntity.serviceMasterId = :serviceMasterId ORDER BY bli.serviceAccoutId DESC"),
	@NamedQuery(name = "ServiceAccountEntity.findAll", query = "SELECT el FROM ServiceAccountEntity el ORDER BY el.serviceAccoutId DESC"),
	@NamedQuery(name="ServiceAccountEntity.updateAccountStatusFromInnerGrid",query="UPDATE ServiceAccountEntity a SET a.status = :status WHERE a.serviceAccoutId = :serviceAccoutId"),
	@NamedQuery(name = "ServiceAccountEntity.getServiceAccount", query = "SELECT el FROM ServiceMastersEntity el where el.serviceMasterId = :serviceMasterId"),
	@NamedQuery(name = "ServiceAccountEntity.ledgerEndDateUpdate", query = "UPDATE ServiceAccountEntity el SET el.ledgerEndDate = :ledgerEndDate WHERE el.serviceAccoutId = :serviceAccoutId")
})
public class ServiceAccountEntity {
	@Id
	@Column(name="ASA_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "serviceAccout_seq", sequenceName = "ACCOUNT_SA_SEQ") 
	@GeneratedValue(generator = "serviceAccout_seq")
	private int serviceAccoutId;
	
	@Column(name="SM_ID")
	@NotNull(message = "Service Master Id Should Not Be Empty")
	private int serviceMasterId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "SM_ID",insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ServiceMastersEntity serviceMastersEntity;
	
	@Column(name="ACCOUNT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int accountId;
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Account account;
	
	@Column(name="SERVICE_TYPE")
	private String typeOfService;
	
	@Column(name="SERVICE_LEDGER")
	private String serviceLedger;
	
	@Column(name="LEDGER_START_DATE")
	private Date ledgerStartDate;
	
	@Column(name="LEDGER_END_DATE")
	private Date ledgerEndDate;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Service Account Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getServiceAccoutId() {
		return serviceAccoutId;
	}

	public void setServiceAccoutId(int serviceAccoutId) {
		this.serviceAccoutId = serviceAccoutId;
	}

	public int getServiceMasterId() {
		return serviceMasterId;
	}

	public void setServiceMasterId(int serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}

	public ServiceMastersEntity getServiceMastersEntity() {
		return serviceMastersEntity;
	}

	public void setServiceMastersEntity(ServiceMastersEntity serviceMastersEntity) {
		this.serviceMastersEntity = serviceMastersEntity;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public String getServiceLedger() {
		return serviceLedger;
	}

	public void setServiceLedger(String serviceLedger) {
		this.serviceLedger = serviceLedger;
	}

	public Date getLedgerStartDate() {
		return ledgerStartDate;
	}

	public void setLedgerStartDate(Date ledgerStartDate) {
		this.ledgerStartDate = ledgerStartDate;
	}

	public Date getLedgerEndDate() {
		return ledgerEndDate;
	}

	public void setLedgerEndDate(Date ledgerEndDate) {
		this.ledgerEndDate = ledgerEndDate;
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

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
}
