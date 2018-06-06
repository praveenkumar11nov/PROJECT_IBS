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

import com.bcits.bfm.util.SessionData;

@SuppressWarnings("serial")
@Entity
@Table(name = "VI_PAYMENTS")
@NamedQueries
({
	@NamedQuery(name="VendorPayments.findAll",query="SELECT vp FROM VendorPayments vp ORDER BY vp.vipId DESC"),
	@NamedQuery(name="VendorPayments.findVendorPaymentsBasedOnVendorInvoice",query="SELECT vp FROM VendorPayments vp where vp.viId = :viId ORDER BY vp.vipId DESC"),
	
})
public class VendorPayments implements java.io.Serializable 
{
	@Id
	@SequenceGenerator(name = "vendorPaymentsSeq", sequenceName = "VENDOR_PAYMENTS_SEQ")
	@GeneratedValue(generator = "vendorPaymentsSeq")
	@Column(name = "VIP_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vipId;
	
	@Column(name = "VI_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int viId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VI_ID", nullable = false,insertable = false,updatable = false)
	private VendorInvoices vendorInvoices;
	
	@Column(name = "VIP_DT", nullable = false, length = 11)
	private Date vipDt;
	
	@Column(name = "VIP_PAY_TYPE", nullable = false, length = 45)
	private String vipPayType;
	
	@Column(name = "VIP_PAYAMOUNT", precision = 10)
	private Double vipPayamount;
	
	@Column(name = "VIP_PAY_BY", length = 45)
	private String vipPayBy;
	
	@Column(name = "VIP_PAYDETAILS", length = 225)
	private String vipPaydetails;
	
	@Column(name = "VIP_NOTES", length = 225)
	private String vipNotes;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
		
	public VendorPayments(){}

	public VendorPayments(int vipId, VendorInvoices vendorInvoices,Timestamp vipDt, String vipPayType) 
	{
		this.vipId = vipId;
		this.vendorInvoices = vendorInvoices;
		this.vipDt = vipDt;
		this.vipPayType = vipPayType;
	}

	public VendorPayments(int vipId, VendorInvoices vendorInvoices,Timestamp vipDt, String vipPayType, Double vipPayamount,String vipPayBy, String vipPaydetails, String vipNotes,
						  String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt) 
	{
		this.vipId = vipId;
		this.vendorInvoices = vendorInvoices;
		this.vipDt = vipDt;
		this.vipPayType = vipPayType;
		this.vipPayamount = vipPayamount;
		this.vipPayBy = vipPayBy;
		this.vipPaydetails = vipPaydetails;
		this.vipNotes = vipNotes;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	public int getVipId() {
		return this.vipId;
	}
	public void setVipId(int vipId) {
		this.vipId = vipId;
	}	
	public VendorInvoices getVendorInvoices() {
		return this.vendorInvoices;
	}
	public void setVendorInvoices(VendorInvoices vendorInvoices) {
		this.vendorInvoices = vendorInvoices;
	}	
	public Date getVipDt() {
		return this.vipDt;
	}
	public void setVipDt(Date vipDt) {
		this.vipDt = vipDt;
	}	
	public String getVipPayType() {
		return this.vipPayType;
	}
	public void setVipPayType(String vipPayType) {
		this.vipPayType = vipPayType;
	}	
	public Double getVipPayamount() {
		return this.vipPayamount;
	}
	public void setVipPayamount(Double vipPayamount) {
		this.vipPayamount = vipPayamount;
	}	
	public String getVipPayBy() {
		return this.vipPayBy;
	}
	public void setVipPayBy(String vipPayBy) {
		this.vipPayBy = vipPayBy;
	}	
	public String getVipPaydetails() {
		return this.vipPaydetails;
	}
	public void setVipPaydetails(String vipPaydetails) {
		this.vipPaydetails = vipPaydetails;
	}	
	public String getVipNotes() {
		return this.vipNotes;
	}
	public void setVipNotes(String vipNotes) {
		this.vipNotes = vipNotes;
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
	public int getViId() {
		return viId;
	}
	public void setViId(int viId) {
		this.viId = viId;
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