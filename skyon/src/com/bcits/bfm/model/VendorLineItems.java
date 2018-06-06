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
@Table(name = "VI_LINEITEMS")
@NamedQueries
({
	@NamedQuery(name="VendorLineItems.findAll",query="SELECT vl FROM VendorLineItems vl ORDER BY vl.vilId DESC"),
	@NamedQuery(name="VendorLineItems.findVendorLineItemsBasedOnVendorInvoice",query="SELECT v FROM VendorLineItems v where v.viId = :viId"),	
	@NamedQuery(name="VendorContracts.getStoreIdBasedOnVcId", query = "SELECT stgr.stgrId FROM StoreGoodsReceipt stgr  WHERE stgr.vcId=:vcid"),
	@NamedQuery(name="VendorContracts.readStoreData",query="SELECT str.rate,str.itemQuantity,str.imId,str.uomId FROM StoreGoodsReceipt st,StoreGoodsReceiptItems str WHERE str.stgrId = st.stgrId AND st.storeId = :storeid AND str.sgriStatus LIKE 'Active'"),
	//@NamedQuery(name="VendorLineItems.getMaxCount",query="SELECT COUNT(*) FROM VendorLineItems"),   
})
public class VendorLineItems implements java.io.Serializable 
{
	@Id
	@SequenceGenerator(name = "vendorLineItemsseq", sequenceName = "VENDOR_LINEITEMS_SEQ")
	@GeneratedValue(generator = "vendorLineItemsseq")
	@Column(name = "VIL_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vilId;
	
	@Column(name = "VI_ID", insertable=false, updatable=false)
	private int viId;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VI_ID", nullable = false,insertable = false,updatable = false)
	private VendorInvoices vendorInvoices;*/
	
	@Column(name = "IM_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int imId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID", nullable = false,insertable = false,updatable = false)
	private ItemMaster itemMaster;
	
	@Column(name = "VIL_SLNO", length = 45)
	private int vilSlno;
	
	@Column(name = "QUANTITY", precision = 10, scale = 0)
	private Double quantity;
	
	@Column(name = "RATE", precision = 10, scale = 0)
	private Double rate;
	
	@Column(name = "AMOUNT", precision = 11, scale = 0)
	private int amount;
	
	@Column(name = "REQ_TYPE", nullable = false, length = 45)
	private String reqtype;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
		
	public VendorLineItems(){}
	public VendorLineItems(int vilId, VendorInvoices vendorInvoices,ItemMaster itemMaster) 
	{
		this.vilId = vilId;
		this.itemMaster = itemMaster;
	}
	public VendorLineItems(int vilId, VendorInvoices vendorInvoices,ItemMaster itemMaster, int vilSlno, Double quantity, Double rate,
			int amount, String createdBy, String lastUpdatedBy,Timestamp lastUpdatedDt) 
	{
		this.vilId = vilId;
		this.itemMaster = itemMaster;
		this.vilSlno = vilSlno;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	public int getVilId() {
		return this.vilId;
	}
	public void setVilId(int vilId) {
		this.vilId = vilId;
	}
	public ItemMaster getItemMaster() {
		return this.itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}	
	public int getVilSlno() {
		return this.vilSlno;
	}
	public void setVilSlno(int vilSlno) {
		this.vilSlno = vilSlno;
	}	
	public Double getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getRate() {
		return this.rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}	
	public int getAmount() {
		return this.amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
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
	public int getViId() {
		return viId;
	}
	public void setViId(int viId) {
		this.viId = viId;
	}
	public int getImId() {
		return imId;
	}
	public void setImId(int imId) {
		this.imId = imId;
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
	public String getReqtype() {
		return reqtype;
	}
	public void setReqtype(String reqtype) {
		this.reqtype = reqtype;
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