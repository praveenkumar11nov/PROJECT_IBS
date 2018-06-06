package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

/**
 * VendorContracts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "VENDOR_CONTRACTS")
@NamedQueries
({
	@NamedQuery(name="VendorContracts.findAll",query="SELECT vc FROM VendorContracts vc ORDER BY vc.vcId DESC"),
	@NamedQuery(name = "VendorContracts.UpdateStatus",query="UPDATE VendorContracts vc SET  vc.status = :status WHERE vc.vcId = :vcId"),
	@NamedQuery(name = "VendorContracts.getVendorNamesAutoUrl", query = "SELECT p.personId, v.vendorId, p.firstName, p.lastName, p.personType FROM Person p, Vendors v WHERE p.personId = v.personId AND p.personStatus = 'Active'"),
	@NamedQuery(name="VendorContracts.checkForApprovedRequisitions",query = "SELECT r FROM Requisition r WHERE r.status='Approved'"),
	@NamedQuery(name="VendorContracts.getAllRequiredVendorContractsRecord",
	query="SELECT vc.vcId,vc.camCategoryId,vc.contractName,vc.description,vc.contractNo,vc.contractStartDate,vc.contractEndDate,vc.drGroupId,vc.invoicePayableDays,vc.vendorSla,vc.status,vc.createdBy,vc.lastUpdatedBy,v.vendorId,st.storeId,st.storeName,r.reqId,r.reqName,p.firstName,p.lastName,vc.renewalRequired FROM VendorContracts vc INNER JOIN vc.vendors v INNER JOIN vc.requisition r INNER JOIN vc.storeMaster st INNER JOIN v.person p WHERE vc.vcId!=1 ORDER BY vc.vcId DESC"),
	
	@NamedQuery(name="Person.getAllPersonOnvendorId",
	query="SELECT p FROM Person p WHERE p.personId IN (SELECT v.personId FROM Vendors v WHERE v.vendorId=:vendorId)"),	
	
	@NamedQuery(name="VendorContracts.getDataForCronScheduler",query="SELECT vc.vcId,vc.contractStartDate,vc.contractEndDate FROM VendorContracts vc WHERE vc.vcId!=1"),
	
	@NamedQuery(name="VendorContracts.getBudgetUOMForSelectedRequisition",query="SELECT SUM(rd.uomBudget) FROM RequisitionDetails rd WHERE rd.requisitionId = :reqIdInChild"),
})
public class VendorContracts
{
	@Id  
    @SequenceGenerator(name = "vendorContractsSeq", sequenceName = "VENDOR_CONTRACTS_SEQ")
	@GeneratedValue(generator = "vendorContractsSeq")
	
	@Column(name = "VC_ID")
	private int vcId;
	
	@Column(name="VENDOR_ID")
    private int vendorId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VENDOR_ID", insertable = false, updatable = false, nullable = false)
	private Vendors vendors;
	
	@Column(name="REQ_ID")
    private int reqId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REQ_ID", insertable = false, updatable = false, nullable = false)
	private Requisition requisition;	
	
	@Column(name="STORE_ID")
    private int storeId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_ID", insertable = false, updatable = false, nullable = false)
	private StoreMaster storeMaster;
	
	@Column(name = "CAM_CATEGORY_ID")
	private int camCategoryId;
	
	/*@Column(name = "CONTRACT_TYPE")
	private String contractType;*/
	
	@Column(name = "CONTRACT_NAME")
	private String contractName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CONTRACT_NO")
	private String contractNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_START_DATE")
	private Date contractStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_END_DATE")
	private Date contractEndDate;
	
	@Column(name = "DR_GROUP_ID")
	private int drGroupId;
	
	@Column(name = "INVOICE_PAYABLE_DAYS")
	private Integer invoicePayableDays;
	
	@Column(name = "VENDOR_SLA")
	private int vendorSla;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	@Column(name = "RENEWAL_REQUIRED")
	private String renewalRequired;
	
	@Transient
	private String vendorName;
	
	/*public String getVendorName() 
	{
		return vendorName;
	}	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}*/

	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "VC_ID")
	private Set<VendorContractLineitems> vcLineitems = new HashSet<VendorContractLineitems>(0);

	public Set<VendorContractLineitems> getVcLineitems() {
		return vcLineitems;
	}
	public void setVcLineitems(Set<VendorContractLineitems> vcLineitems) {
		this.vcLineitems = vcLineitems;
	}
	
	public int getVcId() {
		return this.vcId;
	}
	public void setVcId(int vcId) {
		this.vcId = vcId;
	}	
	public Vendors getVendors() {
		return this.vendors;
	}
	public void setVendors(Vendors vendors) {
		this.vendors = vendors;
	}	
	public Requisition getRequisition() {
		return this.requisition;
	}
	public void setRequisition(Requisition requisition) {
		this.requisition = requisition;
	}	
	public StoreMaster getStoreMaster() {
		return this.storeMaster;
	}
	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}	
	public int getCamCategoryId() {
		return this.camCategoryId;
	}
	public void setCamCategoryId(int camCategoryId) {
		this.camCategoryId = camCategoryId;
	}	
	/*public String getContractType() {
		return this.contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}*/	
	public String getContractName() {
		return this.contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getContractNo() {
		return this.contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}	
	public Date getContractStartDate() {
		return this.contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}	
	public Date getContractEndDate() {
		return this.contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}	
	public int getDrGroupId() {
		return this.drGroupId;
	}
	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}	
	public Integer getInvoicePayableDays() {
		return this.invoicePayableDays;
	}
	public void setInvoicePayableDays(Integer invoicePayableDays) {
		this.invoicePayableDays = invoicePayableDays;
	}	
	public int getVendorSla() {
		return this.vendorSla;
	}
	public void setVendorSla(int vendorSla) {
		this.vendorSla = vendorSla;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getCreatedBy() {
		return this.createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}	
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	public String getRenewalRequired() {
		return renewalRequired;
	}
	public void setRenewalRequired(String renewalRequired) {
		this.renewalRequired = renewalRequired;
	}
	
	@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
}