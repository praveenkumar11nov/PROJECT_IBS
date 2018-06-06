package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name = "STORE_ITEM_LEDGER")
@NamedQueries({
	@NamedQuery(name = "StoreItemLedger.findAllRequiredStoreItemLedgers", query = "SELECT s.silId, s.storeId, s.imId, s.silDt, "
			+ "s.imUom, s.imBalance, s.storeEntryFrom, s.createdBy, s.lastUpdatedBy, s.lastUpdatedDt, "
			+ "sm.storeName, i.imName, u.uom, sm.storeLocation, i.imType FROM StoreItemLedger s INNER JOIN s.storeMaster sm INNER JOIN s.itemMaster i INNER JOIN s.unitOfMeasurement u ORDER BY s.silId DESC"),
	@NamedQuery(name = "StoreItemLedger.findRequiredAllStoresFromItemLedger", query = "SELECT DISTINCT sm FROM StoreItemLedger s INNER JOIN s.storeMaster sm WHERE s.imBalance > 0.0"),
	@NamedQuery(name = "StoreItemLedger.findByItemMaster", query = "SELECT model FROM StoreItemLedger model WHERE model.itemMaster=:itemMaster"),
	@NamedQuery(name = "StoreItemLedger.getBanceQuantity", query = "SELECT model FROM StoreItemLedger model WHERE model.imId=:itemMaster AND model.storeId=:storeId AND model.imUom=:imUom"),
	@NamedQuery(name = "StoreItemLedger.findByUOM", query = "SELECT model FROM StoreItemLedger model WHERE model.itemMaster=:itemMaster AND model.storeId=:storeId"),
	@NamedQuery(name = "StoreItemLedger.getObject", query = "SELECT model FROM StoreItemLedger model WHERE model.storeId=:storeMaster AND model.imId=:itemMaster"),
	@NamedQuery(name = "StoreItemLedger.getStoreItemLedgerList", query = "SELECT s from StoreItemLedger s WHERE s.imId=:itemId"),

})
public class StoreItemLedger 
{
	private int silId;
	private StoreMaster storeMaster;
	private int storeId;
	private ItemMaster itemMaster;
	private int imId;
	private Timestamp silDt;
	private int imUom;
	private UnitOfMeasurement unitOfMeasurement;
	private double imBalance;
	private String storeEntryFrom;
	private String createdBy;
	private String lastUpdatedBy;
	@Access(AccessType.FIELD)
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	private String storeName;
	private String imName;
	private String uom;
	private String storeLocation;
	private String imType;
	
	private Set<StoreItemLedgerDetails> storeItemLedgerDetailsSet = new HashSet<StoreItemLedgerDetails>(0);
	
	@Id
	@SequenceGenerator(name="STORE_ITEM_LEDGER_SEQ",sequenceName="STORE_ITEM_LEDGER_SEQ")
	@GeneratedValue(generator="STORE_ITEM_LEDGER_SEQ")
	@Column(name = "SIL_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getSilId() {
		return this.silId;
	}

	public void setSilId(int silId) {
		this.silId = silId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_ID", insertable = false, updatable = false, nullable = false)
	public StoreMaster getStoreMaster() {
		return this.storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}
	
	@Column(name = "STORE_ID")
	public int getStoreId()
	{
		return storeId;
	}
	
	public void setStoreId(int storeId)
	{
		this.storeId = storeId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID", insertable = false, updatable = false, nullable = false)
	public ItemMaster getItemMaster() {
		return this.itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}
	
	@Column(name = "IM_ID")
	public int getImId()
	{
		return imId;
	}
	
	public void setImId(int imId)
	{
		this.imId = imId;
	}

	@Column(name = "SIL_DT", length = 11)
	public Timestamp getSilDt() {
		return this.silDt;
	}

	public void setSilDt(Timestamp silDt) {
		this.silDt = silDt;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_UOM", insertable = false, updatable = false, nullable = false)
	public UnitOfMeasurement getUnitOfMeasurement()
	{
		return unitOfMeasurement;
	}
	
	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement)
	{
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
	@Column(name = "IM_UOM")
	public int getImUom()
	{
		return imUom;
	}
	
	public void setImUom(int imUom)
	{
		this.imUom = imUom;
	}

	@Column(name = "IM_QUANTITY")
	public double getImBalance() {
		return this.imBalance;
	}

	public void setImBalance(double imBalance) {
		this.imBalance = imBalance;
	}

	@Column(name = "STORE_ENTRY_FROM", length = 45)
	public String getStoreEntryFrom() {
		return this.storeEntryFrom;
	}

	public void setStoreEntryFrom(String storeEntryFrom) {
		this.storeEntryFrom = storeEntryFrom;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
	
	@Transient
	public String getStoreName()
	{
		return storeName;
	}
	
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	
	@Transient
	public String getImName()
	{
		return imName;
	}
	
	public void setImName(String imName)
	{
		this.imName = imName;
	}
	
	@Transient
	public String getUom()
	{
		return uom;
	}
	
	public void setUom(String uom)
	{
		this.uom = uom;
	}
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SIL_ID")
	public Set<StoreItemLedgerDetails> getStoreItemLedgerDetailsSet()
	{
		return storeItemLedgerDetailsSet;
	}
	
	public void setStoreItemLedgerDetailsSet(
			Set<StoreItemLedgerDetails> storeItemLedgerDetailsSet)
	{
		this.storeItemLedgerDetailsSet = storeItemLedgerDetailsSet;
	}
	
	@Transient 
	public String getStoreLocation()
	{
		return storeLocation;
	}
	
	public void setStoreLocation(String storeLocation)
	{
		this.storeLocation = storeLocation;
	}
	
	@Transient 
	public String getImType()
	{
		return imType;
	}
	
	public void setImType(String imType)
	{
		this.imType = imType;
	}
}
