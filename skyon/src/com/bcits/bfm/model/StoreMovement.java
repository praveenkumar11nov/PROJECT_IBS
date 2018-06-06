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
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	@NamedQuery(name="StoreMovement.findAllStoreMovements",query="SELECT s.stmId, s.toStoreId, s.fromStoreId, s.vcId, s.imId, s.uomId,"
			+ " s.itemQuantity, s.itemManufacturer, s.itemExpiryDate, s.warrantyValidTill, s.specialInstructions, "
			+ "s.partNo, s.createdBy, s.lastUpdatedBy, s.lastUpdatedDt, "
			+ "ts.storeName, fs.storeName, v.contractName, i.imName, u.uom, "
			+ "ts.storeLocation, fs.storeLocation, i.imType, i.imGroup, u.code, u.baseUom "
			+ "FROM StoreMovement s INNER JOIN s.toStoreMaster ts INNER JOIN s.fromStoreMaster fs INNER JOIN s.vendorContracts v "
			+ "INNER JOIN s.itemMaster i INNER JOIN s.unitOfMeasurement u ORDER BY s.stmId DESC")
	//@NamedQuery(name="StoreMovement.setStoreMovementStatus",query="UPDATE StoreMovement s SET s.stmStatus = 'Active' WHERE s.stmId = :stmId")
})
@Table(name = "STORE_MOVEMENT")
public class StoreMovement implements Cloneable
{
	private int stmId;
	private UnitOfMeasurement unitOfMeasurement;
	private ItemMaster itemMaster;
	private StoreMaster toStoreMaster;
	private StoreMaster fromStoreMaster;
	private VendorContracts vendorContracts;
	private int toStoreId;
	private int fromStoreId;
	private Double itemQuantity;
	private String itemManufacturer;
	private Date itemExpiryDate;
	private Date warrantyValidTill;
	private String specialInstructions;
	private String partNo;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	private int imId;
	private int uomId;
	private int vcId;
	
	private String imName;
	private String uom;
	
	private String toStoreName;
	private String fromStoreName;
	private String contractName;
	
	private String toStoreLocation;
	private String fromStoreLocation;
	private String imType;
	private String imGroup;
	private String code;
	private String baseUom;
	
	@Id
	@SequenceGenerator(name="STORE_MOVEMENT_SEQ",sequenceName="STORE_MOVEMENT_SEQ")
	@GeneratedValue(generator="STORE_MOVEMENT_SEQ")
	@Column(name = "STM_ID")
	public int getStmId() 
	{
		return this.stmId;
	}

	public void setStmId(int stmId) 
	{
		this.stmId = stmId;
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
	@JoinColumn(name = "TO_STORE_ID", insertable = false, updatable = false, nullable = false)
	public StoreMaster getToStoreMaster()
	{
		return toStoreMaster;
	}
	
	public void setToStoreMaster(StoreMaster toStoreMaster)
	{
		this.toStoreMaster = toStoreMaster;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FROM_STORE_ID", insertable = false, updatable = false, nullable = false)
	public StoreMaster getFromStoreMaster()
	{
		return fromStoreMaster;
	}
	
	public void setFromStoreMaster(StoreMaster fromStoreMaster)
	{
		this.fromStoreMaster = fromStoreMaster;
	}
	
	@Column(name = "TO_STORE_ID")
	public int getToStoreId() 
	{
		return this.toStoreId;
	}

	public void setToStoreId(int toStoreId) 
	{
		this.toStoreId = toStoreId;
	}

	@Column(name = "FROM_STORE_ID")
	public int getFromStoreId() 
	{
		return this.fromStoreId;
	}

	public void setFromStoreId(int fromStoreId) 
	{
		this.fromStoreId = fromStoreId;
	}

	@Column(name = "ITEM_QUANTITY")
	public Double getItemQuantity() 
	{
		return this.itemQuantity;
	}

	public void setItemQuantity(Double itemQuantity) 
	{
		this.itemQuantity = itemQuantity;
	}

	@Column(name = "ITEM_MANUFACTURER")
	public String getItemManufacturer() 
	{
		return this.itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer) 
	{
		this.itemManufacturer = itemManufacturer;
	}

	@Column(name = "ITEM_EXPIRY_DATE")
	public Date getItemExpiryDate() 
	{
		return this.itemExpiryDate;
	}

	public void setItemExpiryDate(Date itemExpiryDate) 
	{
		this.itemExpiryDate = itemExpiryDate;
	}

	@Column(name = "WARRANTY_VALID_TILL")
	public Date getWarrantyValidTill() 
	{
		return this.warrantyValidTill;
	}

	public void setWarrantyValidTill(Date warrantyValidTill) 
	{
		this.warrantyValidTill = warrantyValidTill;
	}

	@Column(name = "SPECIAL_INSTRUCTIONS")
	public String getSpecialInstructions() 
	{
		return this.specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) 
	{
		this.specialInstructions = specialInstructions;
	}

	@Column(name = "PART_NO")
	public String getPartNo() 
	{
		return this.partNo;
	}

	public void setPartNo(String partNo) 
	{
		this.partNo = partNo;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() 
	{
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
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

	@Column(name = "IM_ID")
	public int getImId()
	{
		return imId;
	}

	public void setImId(int imId)
	{
		this.imId = imId;
	}

	@Column(name = "UOM_ID")
	public int getUomId()
	{
		return uomId;
	}

	public void setUomId(int uomId)
	{
		this.uomId = uomId;
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
	public String getToStoreName()
	{
		return toStoreName;
	}
	
	public void setToStoreName(String toStoreName)
	{
		this.toStoreName = toStoreName;
	}
	
	@Transient
	public String getFromStoreName()
	{
		return fromStoreName;
	}
	
	public void setFromStoreName(String fromStoreName)
	{
		this.fromStoreName = fromStoreName;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VC_ID", insertable = false, updatable = false, nullable = false)
	public VendorContracts getVendorContracts()
	{
		return vendorContracts;
	}

	public void setVendorContracts(VendorContracts vendorContracts)
	{
		this.vendorContracts = vendorContracts;
	}

	@Column(name = "VC_ID")
	public int getVcId()
	{
		return vcId;
	}

	public void setVcId(int vcId)
	{
		this.vcId = vcId;
	}

	@Transient
	public String getContractName()
	{
		return contractName;
	}

	public void setContractName(String contractName)
	{
		this.contractName = contractName;
	}

	@Transient
	public String getToStoreLocation()
	{
		return toStoreLocation;
	}

	@Transient
	public void setToStoreLocation(String toStoreLocation)
	{
		this.toStoreLocation = toStoreLocation;
	}

	@Transient
	public String getFromStoreLocation()
	{
		return fromStoreLocation;
	}

	public void setFromStoreLocation(String fromStoreLocation)
	{
		this.fromStoreLocation = fromStoreLocation;
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
	
	@Transient
	public String getImGroup()
	{
		return imGroup;
	}
	
	public void setImGroup(String imGroup)
	{
		this.imGroup = imGroup;
	}
	
	@Transient
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	@Transient
	public String getBaseUom()
	{
		return baseUom;
	}
	
	public void setBaseUom(String baseUom)
	{
		this.baseUom = baseUom;
	}
	
	public Object clone()throws CloneNotSupportedException
	{  
		return super.clone();  
	}
}

