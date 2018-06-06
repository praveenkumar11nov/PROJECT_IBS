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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@SuppressWarnings("serial")
@Entity
@Table(name = "VENDOR_INVOICES")
@NamedQueries
({
	@NamedQuery(name="VendorInvoices.findAll",query="SELECT vin FROM VendorInvoices vin ORDER BY vin.viId DESC"),
	@NamedQuery(name = "VendorInvoice.UpdateStatus",query="UPDATE VendorInvoices v SET  v.status = :status WHERE v.viId = :viId"),
	@NamedQuery(name="VendorInvoices.readAllVCLineitemsId",query="SELECT vcl.vcId FROM VendorContractLineitems vcl"),
	@NamedQuery(name="VendorInvoices.getVendorContractStartDateBasedOnVcId",query="SELECT v.contractStartDate FROM VendorContracts v where v.vcId=:vcid"),
	@NamedQuery(name="VendorInvoices.findAllList",query="SELECT vin.viId,vc.vcId,vc.contractName,vc.contractNo,vin.invoiceNo,vin.invoiceDt,vin.invoiceTitle,vin.description,vin.status,vin.createdBy,vin.lastUpdatedBy,vin.lastUpdatedDt FROM VendorInvoices vin INNER JOIN vin.vendorContracts vc ORDER BY vin.viId DESC"),
	@NamedQuery(name="VendorInvoices.findById",query="SELECT vin FROM VendorInvoices vin WHERE vin.viId = :viId"),

})
public class VendorInvoices implements java.io.Serializable 
{
	@Id	  
    @SequenceGenerator(name = "vendorInvoicesSeq", sequenceName = "VENDOR_INVOICES_SEQ")
	@GeneratedValue(generator = "vendorInvoicesSeq")
	
	@Column(name = "VI_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int viId;
	
	@Column(name = "VC_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vcId;
	
	@Transient
	private String vendorInvoiceDetails;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VC_ID", nullable = false,insertable=false,updatable=false)
	private VendorContracts vendorContracts;
	
	@Column(name = "INVOICE_NO", nullable = false, length = 45)
	private String invoiceNo;
	
	@Column(name = "INVOICE_DT", nullable = false, length = 11)
	private Date invoiceDt;	
	
	@Column(name = "INVOICE_TITLE", length = 45)
	private String invoiceTitle;
	
	@Column(name = "DESCRIPTION", length = 225)
	private String description;
	
	@Column(name = "STATUS", nullable = false, length = 45)
	private String status;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
	
	///////////
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "VI_ID")
	private Set<VendorLineItems> vendorLineitems = new HashSet<VendorLineItems>(0);

	public Set<VendorLineItems> getVendorLineitems() {
		return vendorLineitems;
	}

	public void setVendorLineitems(Set<VendorLineItems> vendorLineitems) {
		this.vendorLineitems = vendorLineitems;
	}
	//////////
	
	public VendorInvoices(){}

	/** minimal constructor */
	public VendorInvoices(int viId, VendorContracts vendorContracts,String invoiceNo, Timestamp invoiceDt, String status) 
	{
		this.viId = viId;
		this.vendorContracts = vendorContracts;
		this.invoiceNo = invoiceNo;
		this.invoiceDt = invoiceDt;
		this.status = status;
	}

	/** full constructor */
	public VendorInvoices(int viId, VendorContracts vendorContracts,String invoiceNo, Timestamp invoiceDt, String invoiceTitle,String description, String status, String createdBy,
			               String lastUpdatedBy, Timestamp lastUpdatedDt)
	{
		this.viId = viId;
		this.vendorContracts = vendorContracts;
		this.invoiceNo = invoiceNo;
		this.invoiceDt = invoiceDt;
		this.invoiceTitle = invoiceTitle;
		this.description = description;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;		
	}
	
	public int getViId(){
		return this.viId;
	}
	public void setViId(int viId) {
		this.viId = viId;
	}
	public VendorContracts getVendorContracts() {
		return this.vendorContracts;
	}
	public void setVendorContracts(VendorContracts vendorContracts) {
		this.vendorContracts = vendorContracts;
	}
	public String getInvoiceNo() {
		return this.invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Date getInvoiceDt() {
		return this.invoiceDt;
	}
	public void setInvoiceDt(Date invoiceDt) {
		this.invoiceDt = invoiceDt;
	}	
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	public int getVcId() {
		return vcId;
	}
	public void setVcId(int vcId) {
		this.vcId = vcId;
	}
	public String getVendorInvoiceDetails() {
		return vendorInvoiceDetails;
	}
	public void setVendorInvoiceDetails(String vendorInvoiceDetails) {
		this.vendorInvoiceDetails = vendorInvoiceDetails;
	}
	
	@PrePersist
	protected void onCreate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	
	
}