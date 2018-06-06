package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name="FR_CHANGE")  
@NamedQueries({
	@NamedQuery(name = "FRChange.findAll", query = "SELECT fr FROM FRChange fr ORDER BY fr.frId DESC"),
	@NamedQuery(name = "FRChange.findAccountNumbers",query="SELECT DISTINCT a.accountId,a.accountNo,p.personId,p.firstName,p.lastName,p.personType,p.personStyle FROM ServiceMastersEntity sm INNER JOIN sm.accountObj a INNER JOIN a.person p WHERE sm.status = 'Active'"),
	@NamedQuery(name = "FRChange.findServiceType",query="SELECT sm.typeOfService,a.accountId FROM ServiceMastersEntity sm INNER JOIN sm.accountObj a WHERE sm.typeOfService NOT IN('CAM','Others','Solid Waste','Telephone Broadband') AND sm.status = 'Active'"),
	@NamedQuery(name = "FRChange.findBillDateAndPreReading",query="SELECT MAX(el.elBillId) FROM ElectricityBillEntity el WHERE el.typeOfService =:serviceType AND el.accountId=:acId AND el.status != 'Cancelled'"),
	@NamedQuery(name = "FRChange.getPresentReading", query = "SELECT ebp.elBillParameterValue FROM ElectricityBillParametersEntity ebp INNER JOIN ebp.billParameterMasterEntity bpm WHERE ebp.electricityBillEntity.elBillId=:elBillId AND bpm.bvmName='Present reading'"),
	@NamedQuery(name = "FRChange.getDGPresentReading", query = "SELECT ebp.elBillParameterValue FROM ElectricityBillParametersEntity ebp INNER JOIN ebp.billParameterMasterEntity bpm WHERE ebp.electricityBillEntity.elBillId=:elBillId AND bpm.bvmName='Present  DG reading'"),
	@NamedQuery(name = "FRChange.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM FRChange fr INNER JOIN fr.account a"),
	@NamedQuery(name = "FRChange.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from FRChange fr INNER JOIN fr.account a INNER JOIN a.person p"),
})
public class FRChange {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name = "fr_change_seq", sequenceName = "FR_CHANGE_SEQ") 
	@GeneratedValue(generator = "fr_change_seq")
	private int frId;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID",insertable = false, updatable = false, nullable = false)
	private Account account;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfService;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	@Column(name = "PRESENT_READING")
	private double presentReading;
	
	@Column(name = "FINAL_READING")
	private double finalReading;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
		
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
		
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDate;
	
	@Column(name = "PRESENT_READING_DG")
	private Double presentReadingDg;
	
	@Column(name = "FINAL_READING_DG")
	private Double finalReadingDg;

	public Double getPresentReadingDg() {
		return presentReadingDg;
	}

	public void setPresentReadingDg(Double presentReadingDg) {
		this.presentReadingDg = presentReadingDg;
	}

	public Double getFinalReadingDg() {
		return finalReadingDg;
	}

	public void setFinalReadingDg(Double finalReadingDg) {
		this.finalReadingDg = finalReadingDg;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
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

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public double getPresentReading() {
		return presentReading;
	}

	public void setPresentReading(double presentReading) {
		this.presentReading = presentReading;
	}

	public double getFinalReading() {
		return finalReading;
	}

	public void setFinalReading(double finalReading) {
		this.finalReading = finalReading;
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

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDate = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	    status = "Inactive";
	}
	
	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDate = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
