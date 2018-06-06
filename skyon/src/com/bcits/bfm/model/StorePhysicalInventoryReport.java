package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	@NamedQuery(name = "StorePhysicalInventoryReport.getAllStorePhysicalInventoryReportsBasedOnSPIId", query = "SELECT s FROM StorePhysicalInventoryReport s WHERE s.spiId = :spiId ORDER BY s.spirId DESC")
})
@Table(name = "SPI_REPORT")
public class StorePhysicalInventoryReport
{
	private int spirId;
	private int spiId;
	private int storeId;
	private int imId;
	private int uomId;
	private StoreMaster storeMaster;
	private ItemMaster itemMaster;
	private UnitOfMeasurement unitOfMeasurement;
	private Double expectedBalance;
	private Double availableBalance;
	private String spiCondition;
	private String spiNotes;
	private String createdBy;
	private String lastUpdatedBy;
	private Date lastUpdatedDt;
	private String storeName;
	private String imName;
	private String uom;
	private Double unavailableBalance;
	@Id
	@SequenceGenerator(name="SPIR_SEQ" ,sequenceName="SPIR_SEQ")
	@GeneratedValue(generator="SPIR_SEQ")
	@Column(name="SPIR_ID")
	public int getSpirId()
	{
		return spirId;
	}
	public void setSpirId(int spirId)
	{
		this.spirId = spirId;
	}
	@Column(name="SPI_ID", insertable = false, updatable = false)
	public int getSpiId()
	{
		return spiId;
	}
	public void setSpiId(int spiId)
	{
		this.spiId = spiId;
	}
	@Column(name="STORE_ID")
	public int getStoreId()
	{
		return storeId;
	}
	public void setStoreId(int storeId)
	{
		this.storeId = storeId;
	}
	@Column(name="IM_ID")
	public int getImId()
	{
		return imId;
	}
	public void setImId(int imId)
	{
		this.imId = imId;
	}
	@Column(name="UOM_ID")
	public int getUomId()
	{
		return uomId;
	}
	public void setUomId(int uomId)
	{
		this.uomId = uomId;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_ID", insertable = false, updatable = false, nullable = false)
	public StoreMaster getStoreMaster() 
	{
		return this.storeMaster;
	}
	public void setStoreMaster(StoreMaster storeMaster) 
	{
		this.storeMaster = storeMaster;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID", insertable = false, updatable = false, nullable = false)
	public ItemMaster getItemMaster() 
	{
		return this.itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) 
	{
		this.itemMaster = itemMaster;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UOM_ID", insertable = false, updatable = false, nullable = false)
	public UnitOfMeasurement getUnitOfMeasurement()
	{
		return unitOfMeasurement;
	}
	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement)
	{
		this.unitOfMeasurement = unitOfMeasurement;
	}
	@Column(name="EXPECTED_BALANCE")
	public Double getExpectedBalance()
	{
		return expectedBalance;
	}
	public void setExpectedBalance(Double expectedBalance)
	{
		this.expectedBalance = expectedBalance;
	}
	@Column(name="AVAILABLE_BALANCE")
	public Double getAvailableBalance()
	{
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance)
	{
		this.availableBalance = availableBalance;
	}
	@Column(name="SPI_CONDITION")
	public String getSpiCondition()
	{
		return spiCondition;
	}
	public void setSpiCondition(String spiCondition)
	{
		this.spiCondition = spiCondition;
	}
	@Column(name="SPI_NOTES")
	public String getSpiNotes()
	{
		return spiNotes;
	}
	public void setSpiNotes(String spiNotes)
	{
		this.spiNotes = spiNotes;
	}
	@Column(name="CREATED_BY")
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	@Column(name="LAST_UPDATED_DT")
	public Date getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}
	public void setLastUpdatedDt(Date lastUpdatedDt)
	{
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
	@Transient
	public Double getUnavailableBalance()
	{
		return unavailableBalance;
	}
	public void setUnavailableBalance(Double unavailableBalance)
	{
		this.unavailableBalance = unavailableBalance;
	}
}
