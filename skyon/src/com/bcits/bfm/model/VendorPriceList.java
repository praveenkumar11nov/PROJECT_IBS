package com.bcits.bfm.model;

import java.sql.Date;

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

/**
 * VendorPricelist entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VENDOR_PRICELIST")
@NamedQueries
({
	@NamedQuery(name="VendorPriceList.findAll",query="SELECT v FROM VendorPriceList v ORDER BY v.vpId DESC"),
	@NamedQuery(name = "VendorPriceList.UpdateStatus",query="UPDATE VendorPriceList v SET  v.status = :status WHERE v.vpId = :vpId"),
})

public class VendorPriceList implements java.io.Serializable 
{
	@Id
	@SequenceGenerator(name = "vendorpricelistseq", sequenceName = "VENDORPRICELIST_SEQ")
	@GeneratedValue(generator = "vendorpricelistseq")
	@Column(name = "VP_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vpId;
	
	@Column(name="VENDOR_ID", unique=true, nullable=false, precision=11, scale=0)
	private int vendorId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VENDOR_ID",insertable = false, updatable = false, nullable = false)
	private Vendors vendors;
	 
	@Column(name="IM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int imId;
	 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID",insertable = false, updatable = false, nullable = false)
	private ItemMaster itemMaster;
	
	@Column(name="UOM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int uomId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UOM_ID",insertable = false, updatable = false, nullable = false)
	private UnitOfMeasurement unitOfMeasurement;
	
	@Column(name = "RATE", precision = 10, scale = 0)
	private Long rate;
	
	@Column(name = "VALID_FROM", nullable=false, length=11)
	private Date validFrom;
	
	@Column(name = "VALID_TO", nullable=false, length=11)
	private Date validTo;
	
	@Column(name = "DELIVERY_AT", precision = 10, scale = 0)
	private String deliveryAt;
	
	@Column(name = "PAYMENT_TERMS", precision = 10, scale = 0)
	private String paymentTerms;
	
	@Column(name = "INVOICE_PAYABLE_DAYS", precision = 10, scale = 0)
	private int Invoice_Payable_days;
	
	@Column(name = "ITEM_TYPE", precision = 10, scale = 0)
	private String itemType;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "STATUS",length = 45)
	private String status;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)	
	private String lastUpdatedBy;
	
	public VendorPriceList(){}

	/** minimal constructor */
	public VendorPriceList(int vpId, Vendors vendors, ItemMaster itemMaster) {
		this.vpId = vpId;
		this.vendors = vendors;
		this.itemMaster = itemMaster;
	}

	/** full constructor */
	public VendorPriceList(int vpId,Vendors vendors, ItemMaster itemMaster,Long rate, String createdBy, String lastUpdatedBy,String status)
	{
		this.vpId = vpId;
		this.vendors = vendors;
		this.itemMaster = itemMaster;
		this.rate = rate;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.status = status;
	}

	public int getVpId() 
	{
		return this.vpId;
	}
	public void setVpId(int vpId) 
	{
		this.vpId = vpId;
	}	
	public Vendors getVendors() 
	{
		return this.vendors;
	}
	public void setVendors(Vendors vendors) 
	{
		this.vendors = vendors;
	}	
	public ItemMaster getItemMaster() 
	{
		return this.itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) 
	{
		this.itemMaster = itemMaster;
	}	
	public Long getRate() 
	{
		return this.rate;
	}
	public void setRate(Long rate) 
	{
		this.rate = rate;
	}	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getDeliveryAt() {
		return deliveryAt;
	}
	public void setDeliveryAt(String deliveryAt) {
		this.deliveryAt = deliveryAt;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public int getInvoice_Payable_days() {
		return Invoice_Payable_days;
	}
	public void setInvoice_Payable_days(int invoice_Payable_days) {
		Invoice_Payable_days = invoice_Payable_days;
	}	
	public String getCreatedBy() 
	{
		return this.createdBy;
	}
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() 
	{
		return this.lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getImId() {
		return imId;
	}
	public void setImId(int imId) {
		this.imId = imId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}
	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
	@PrePersist
	protected void onCreate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
}