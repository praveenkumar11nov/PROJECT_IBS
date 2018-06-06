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
	@NamedQuery(name="StoreAdjustments.findAllStoreAdjustments",query="SELECT s FROM StoreAdjustments s ORDER BY s.saId DESC"),
	@NamedQuery(name="StoreAdjustments.getRequiredStoreContracts",query="SELECT DISTINCT s.storeId FROM StoreAdjustments s INNER JOIN s.vendorContracts v WHERE v.vcId = 1")
	})
@Table(name = "STORE_ADJUSTMENTS")
public class StoreAdjustments 
{
	private int saId;
	private StoreMaster storeMaster;
	private Users users;
	private UnitOfMeasurement unitOfMeasurement;
	private ItemMaster itemMaster;
	private VendorContracts vendorContracts;
	private Timestamp saDt;
	private int saNumber;
	private Double itemQuantity;
	private Timestamp ledgerUpdateDt;
	private String reasonForAdjustment;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	private int storeId;
	private int approvedByStaffId;
	private int imId;
	private int uomId;
	private int vcId;
	
	private String storeName;
	private String personName;
	private String imName;
	private String uom;
	private String contractName;
	
	private Date saDate;
	private String saTime;
	
	private String shippingDocumentNumber;
	private String partNo;
	private Double rate;
	private String itemManufacturer;
    private Date itemManufactureDate;
    
    private String contractStatus;
	
	@Id
	@SequenceGenerator(name="STORE_ADJUSTMENTS_SEQ",sequenceName="STORE_ADJUSTMENTS_SEQ")
	@GeneratedValue(generator="STORE_ADJUSTMENTS_SEQ")
	@Column(name = "SA_ID")
	public int getSaId() 
	{
		return this.saId;
	}

	public void setSaId(int saId) 
	{
		this.saId = saId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_ID", insertable = false, updatable = false, nullable = false)
	public StoreMaster getStoreMaster()
	{
		return storeMaster;
	}
	
	public void setStoreMaster(StoreMaster storeMaster)
	{
		this.storeMaster = storeMaster;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPROVED_BY_STAFF_ID", insertable = false, updatable = false, nullable = false)
	public Users getUsers()
	{
		return users;
	}
	
	public void setUsers(Users users)
	{
		this.users = users;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_UOM", insertable = false, updatable = false, nullable = false)
	public UnitOfMeasurement getUnitOfMeasurement() 
	{
		return this.unitOfMeasurement;
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
	@JoinColumn(name = "VC_ID", insertable = false, updatable = false, nullable = true)
	public VendorContracts getVendorContracts()
	{
		return vendorContracts;
	}
	
	@Override
	public String toString() {
		return "StoreAdjustments [saId=" + saId + ", storeMaster="
				+ storeMaster + ", users=" + users + ", unitOfMeasurement="
				+ unitOfMeasurement + ", itemMaster=" + itemMaster
				+ ", vendorContracts=" + vendorContracts + ", saDt=" + saDt
				+ ", saNumber=" + saNumber + ", itemQuantity=" + itemQuantity
				+ ", ledgerUpdateDt=" + ledgerUpdateDt
				+ ", reasonForAdjustment=" + reasonForAdjustment
				+ ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastUpdatedDt=" + lastUpdatedDt
				+ ", storeId=" + storeId + ", approvedByStaffId="
				+ approvedByStaffId + ", imId=" + imId + ", uomId=" + uomId
				+ ", vcId=" + vcId + ", storeName=" + storeName
				+ ", personName=" + personName + ", imName=" + imName
				+ ", uom=" + uom + ", contractName=" + contractName
				+ ", saDate=" + saDate + ", saTime=" + saTime
				+ ", shippingDocumentNumber=" + shippingDocumentNumber
				+ ", partNo=" + partNo + ", rate=" + rate
				+ ", itemManufacturer=" + itemManufacturer
				+ ", itemManufactureDate=" + itemManufactureDate
				+ ", contractStatus=" + contractStatus + "]";
	}

	public void setVendorContracts(VendorContracts vendorContracts)
	{
		this.vendorContracts = vendorContracts;
	}

	@Column(name = "SA_DT")
	public Timestamp getSaDt() 
	{
		return this.saDt;
	}

	public void setSaDt(Timestamp saDt) 
	{
		this.saDt = saDt;
	}

	@Column(name = "SA_NUMBER")
	public int getSaNumber() 
	{
		return this.saNumber;
	}

	public void setSaNumber(int saNumber) 
	{
		this.saNumber = saNumber;
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
	
	@Column(name = "LEDGER_UPDATE_DT")
	public Timestamp getLedgerUpdateDt() 
	{
		return this.ledgerUpdateDt;
	}

	public void setLedgerUpdateDt(Timestamp ledgerUpdateDt)
	{
		this.ledgerUpdateDt = ledgerUpdateDt;
	}
	
	@Column(name = "REASON_FOR_ADJUSTMENT")
	public String getReasonForAdjustment()
	{
		return reasonForAdjustment;
	}
	
	public void setReasonForAdjustment(String reasonForAdjustment)
	{
		this.reasonForAdjustment = reasonForAdjustment;
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
	public Timestamp getLastUpdatedDt() 
	{
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
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
	
	@Column(name = "APPROVED_BY_STAFF_ID")
	public int getApprovedByStaffId()
	{
		return approvedByStaffId;
	}

	public void setApprovedByStaffId(int approvedByStaffId)
	{
		this.approvedByStaffId = approvedByStaffId;
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

	@Column(name = "IM_UOM")
	public int getUomId()
	{
		return uomId;
	}

	public void setUomId(int uomId)
	{
		this.uomId = uomId;
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
	public String getStoreName()
	{
		return storeName;
	}
	
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	
	@Transient
	public String getPersonName()
	{
		return personName;
	}

	public void setPersonName(String personName)
	{
		this.personName = personName;
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
	public Date getSaDate()
	{
		return saDate;
	}

	public void setSaDate(Date saDate)
	{
		this.saDate = saDate;
	}

	@Transient
	public String getSaTime()
	{
		return saTime;
	}

	public void setSaTime(String saTime)
	{
		this.saTime = saTime;
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
	public String getShippingDocumentNumber()
	{
		return shippingDocumentNumber;
	}

	public void setShippingDocumentNumber(String shippingDocumentNumber)
	{
		this.shippingDocumentNumber = shippingDocumentNumber;
	}

	@Transient
	public String getPartNo()
	{
		return partNo;
	}

	public void setPartNo(String partNo)
	{
		this.partNo = partNo;
	}
	
	@Transient
	public Double getRate()
	{
		return rate;
	}

	public void setRate(Double rate)
	{
		this.rate = rate;
	}

	@Transient
	public String getItemManufacturer()
	{
		return itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer)
	{
		this.itemManufacturer = itemManufacturer;
	}

	@Transient
	public Date getItemManufactureDate()
	{
		return itemManufactureDate;
	}

	public void setItemManufactureDate(Date itemManufactureDate)
	{
		this.itemManufactureDate = itemManufactureDate;
	}
	
	@Transient
	public String getContractStatus()
	{
		return contractStatus;
	}
	
	public void setContractStatus(String contractStatus)
	{
		this.contractStatus = contractStatus;
	}
	
}