package com.bcits.bfm.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * StoreOutward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STORE_OUTWARD")
@NamedQueries({
	@NamedQuery(name = "StoreOutward.UpdateStatus", query = "UPDATE StoreOutward ps SET  ps.status = :psStatus WHERE ps.srId = :psId"),
	@NamedQuery(name = "StoreOutward.findAll", query = "SELECT so.srId,so.reasonForReturn,so.createdBy,so.status,im.imId,im.imName,sm.storeId,sm.storeName,p.firstName,p.lastName,pe.firstName,pe.lastName,u.urId,v.vendorId,uo.uomId,uo.uom,so.itemReturnQuantity FROM StoreOutward so INNER JOIN so.itemMaster im INNER JOIN so.storeMaster sm INNER JOIN so.users u INNER JOIN u.person p INNER JOIN so.vendors v INNER JOIN v.person pe INNER JOIN so.unitOfMeasurement uo"),
})
public class StoreOutward implements java.io.Serializable {
	
	// Fields
	
	private static final long serialVersionUID = 1L;
	
	private int srId;
	private Users users;
	private int userId;
	private Vendors vendors;
	private int vendorId;
	private StoreMaster storeMaster;
	private int storeMasterId;
	private UnitOfMeasurement unitOfMeasurement;
	private int uomId;
	private ItemMaster itemMaster;
	private int itemMasterId;
	private Double itemReturnQuantity;
	private String reasonForReturn;
	private Timestamp dateOfReturn;
	private String createdBy;
	private String lasUpdatedBy;	
	private String status;
	
	@Id     
    @SequenceGenerator(name = "STOCK_OUTWARD_SEQUENCE", sequenceName = "STOCK_OUTWARD_SEQUENCE")
	@GeneratedValue(generator = "STOCK_OUTWARD_SEQUENCE")
	@Column(name = "SR_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getSrId() {
		return this.srId;
	}

	public void setSrId(int srId) {
		this.srId = srId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RETURN_BY_STAFF_ID",insertable=false,updatable=false)
	@JsonIgnore
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	@Column(name = "RETURN_BY_STAFF_ID")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RETURN_BY_VENDOR_ID",insertable=false,updatable=false)
	public Vendors getVendors() {
		return this.vendors;
	}

	public void setVendors(Vendors vendors) {
		this.vendors = vendors;
	}

	@Column(name = "RETURN_BY_VENDOR_ID")
	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_ID",insertable=false,updatable=false)
	public StoreMaster getStoreMaster() {
		return this.storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}
	
	@Column(name = "STORE_ID")
	public int getStoreMasterId() {
		return storeMasterId;
	}

	public void setStoreMasterId(int storeMasterId) {
		this.storeMasterId = storeMasterId;
	}

	@Column(name = "IM_UOM")
	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
	}

	@Column(name = "IM_ID")
	public int getItemMasterId() {
		return itemMasterId;
	}

	public void setItemMasterId(int itemMasterId) {
		this.itemMasterId = itemMasterId;
	}
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_UOM",insertable=false,updatable=false)
	public UnitOfMeasurement getUnitOfMeasurement() {
		return this.unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID",insertable=false,updatable=false)
	public ItemMaster getItemMaster() {
		return this.itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	@Column(name = "ITEM_RETURN_QUANTITY", precision = 126, scale = 0)
	public Double getItemReturnQuantity() {
		return this.itemReturnQuantity;
	}

	public void setItemReturnQuantity(Double itemReturnQuantity) {
		this.itemReturnQuantity = itemReturnQuantity;
	}

	@Column(name = "REASON_FOR_RETURN", length = 500)
	public String getReasonForReturn() {
		return this.reasonForReturn;
	}

	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}

	@Column(name = "DATE_OF_RETURN", length = 11)
	public Timestamp getDateOfReturn() {
		return this.dateOfReturn;
	}

	public void setDateOfReturn(Timestamp dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAS_UPDATED_BY", length = 45)
	public String getLasUpdatedBy() {
		return this.lasUpdatedBy;
	}

	public void setLasUpdatedBy(String lasUpdatedBy) {
		this.lasUpdatedBy = lasUpdatedBy;
	}
	
	@Column(name = "STATUS", length = 45)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}