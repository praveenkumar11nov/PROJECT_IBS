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

import net.sf.oval.constraint.MatchPattern;

@Entity
@NamedQueries({
	@NamedQuery(name="StoreGoodsReceiptItems.findAllStoreGoodsReceiptItems",query="SELECT s FROM StoreGoodsReceiptItems s WHERE s.stgrId = :stgrId ORDER BY s.sgriId DESC"),
	@NamedQuery(name="StoreGoodsReceiptItems.setStoreGoodsReceiptItemsStatus",query="UPDATE StoreGoodsReceiptItems s SET s.sgriStatus = 'Active', s.activationDt = :activationDt WHERE s.sgriId = :sgriId"),
	@NamedQuery(name="StoreGoodsReceiptItems.updateRequiredFields",query="UPDATE StoreGoodsReceiptItems s SET s.itemQuantity = :itemQuantity, s.receiptType = :receiptType WHERE s.sgriId = :sgriId")
})
@Table(name="STORE_GOODS_RECEIPT_ITEMS")
public class StoreGoodsReceiptItems 
{
     private int sgriId;
     private int stgrId;
     private ItemMaster itemMaster;
     private int imId;
     private int drGroupId;
     private Double itemQuantity;
     private String itemManufacturer;
     private Date itemManufactureDate;
     private Date itemExpiryDate;
     private Date warrantyValidTill;
     private String specialInstructions;
     private UnitOfMeasurement unitOfMeasurement;
     private int uomId;
     private String uomPurchase;
     private String uomIssue;
     private String partNo;
     private String createdBy;
     private String lastUpdatedBy;
     private Timestamp lastUpdatedDt;
     private String sgriStatus;
     private Timestamp activationDt;
     
     private String imType;
     private String receiptType;
     private Double rate;
     
    // private Double displayRate;
     
     private String imName;

    @Id 
	@SequenceGenerator(name="STORE_GOODS_RECEIPT_ITEMS_SEQ",sequenceName="STORE_GOODS_RECEIPT_ITEMS_SEQ")
	@GeneratedValue(generator="STORE_GOODS_RECEIPT_ITEMS_SEQ")
    @Column(name="SGRI_ID")
    public int getSgriId() 
    {
        return this.sgriId;
    }
    
    public void setSgriId(int sgriId) 
    {
        this.sgriId = sgriId;
    }

    @Column(name = "STGR_ID", insertable=false, updatable=false)
    public int getStgrId()
	{
		return stgrId;
	}
    
    public void setStgrId(int stgrId)
	{
		this.stgrId = stgrId;
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
    
    @Column(name = "IM_ID")
    public int getImId()
	{
		return imId;
	}
    
    public void setImId(int imId)
	{
		this.imId = imId;
	}
    
    @Column(name="DR_GROUP_ID")
    public int getDrGroupId() 
    {
        return this.drGroupId;
    }
    
    public void setDrGroupId(int drGroupId) 
    {
        this.drGroupId = drGroupId;
    }
    
    @Column(name="ITEM_QUANTITY")
	@net.sf.oval.constraint.Min(value = 0, message = "Min.StoreGoodsReceiptItems.itemQuantity")
    @net.sf.oval.constraint.Max(value = 999999999, message = "Max.StoreGoodsReceiptItems.itemQuantity")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceiptItems.itemQuantity")
    public Double getItemQuantity() 
    {
        return this.itemQuantity;
    }
    
    public void setItemQuantity(Double itemQuantity) 
    {
        this.itemQuantity = itemQuantity;
    }
    
    @net.sf.oval.constraint.Size(min = 1, max = 100, message = "Size.StoreGoodsReceiptItems.itemManufacturer")
    @net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceiptItems.itemManufacturer")
    @Column(name="ITEM_MANUFACTURER")
    public String getItemManufacturer() 
    {
        return this.itemManufacturer;
    }
    
    public void setItemManufacturer(String itemManufacturer)
    {
        this.itemManufacturer = itemManufacturer;
    }
    
	@net.sf.oval.constraint.Past(message = "Past.StoreGoodsReceiptItems.itemManufactureDate")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceiptItems.itemManufactureDate")
    @Column(name="ITEM_MANUFACTURE_DATE")
    public Date getItemManufactureDate() {
        return this.itemManufactureDate;
    }
    
    public void setItemManufactureDate(Date itemManufactureDate) 
    {
        this.itemManufactureDate = itemManufactureDate;
    }
    
    @Column(name="ITEM_EXPIRY_DATE")
    public Date getItemExpiryDate()
    {
        return this.itemExpiryDate;
    }
    
    public void setItemExpiryDate(Date itemExpiryDate) 
    {
        this.itemExpiryDate = itemExpiryDate;
    }
    
    @Column(name="WARRANTY_VALID_TILL")
    public Date getWarrantyValidTill() 
    {
        return this.warrantyValidTill;
    }
    
    public void setWarrantyValidTill(Date warrantyValidTill) 
    {
        this.warrantyValidTill = warrantyValidTill;
    }
    
    @net.sf.oval.constraint.Size(max = 225, message = "Size.StoreGoodsReceiptItems.specialInstructions")
    @Column(name="SPECIAL_INSTRUCTIONS")
    public String getSpecialInstructions() 
    {
        return this.specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions)
    {
        this.specialInstructions = specialInstructions;
    }
    
    @Transient
    @Column(name="UOM_PURCHASE")
    public String getUomPurchase() 
    {
        return this.uomPurchase;
    }
    
    public void setUomPurchase(String uomPurchase) 
    {
        this.uomPurchase = uomPurchase;
    }
    
    @Transient
    @Column(name="UOM_ISSUE")
    public String getUomIssue() 
    {
        return this.uomIssue;
    }
    
    public void setUomIssue(String uomIssue)
    {
        this.uomIssue = uomIssue;
    }
    
    @net.sf.oval.constraint.Size(min = 1, max = 100, message = "Size.StoreGoodsReceiptItems.partNo")
    @net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceiptItems.partNo")
    @Column(name="PART_NO")
    public String getPartNo() 
    {
        return this.partNo;
    }
    
    public void setPartNo(String partNo)
    {
        this.partNo = partNo;
    }
    
    @Column(name="CREATED_BY")
    public String getCreatedBy() 
    {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }
    
    @Column(name="LAST_UPDATED_BY") 
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
    
    @Transient
    public String getImType()
	{
		return imType;
	}
    
    public void setImType(String imType)
	{
		this.imType = imType;
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
       
    @Column(name = "UOM_ID")
    public int getUomId()
	{
		return uomId;
	}
       
    public void setUomId(int uomId)
	{
		this.uomId = uomId;
	} 
    
    @Column(name = "STATUS")
	@net.sf.oval.constraint.Size(min=1, message = "Size.StoreGoodsReceiptItems.sgriStatus")
	@MatchPattern( pattern = "Active|Inactive", message = "MatchPattern.StoreGoodsReceiptItems.sgriStatus")
    public String getSgriStatus()
	{
		return sgriStatus;
	}
    
    public void setSgriStatus(String sgriStatus)
	{
		this.sgriStatus = sgriStatus;
	}
    
    @Column(name = "ACTIVATION_DT")
    public Timestamp getActivationDt()
	{
		return activationDt;
	}
    
    public void setActivationDt(Timestamp activationDt)
	{
		this.activationDt = activationDt;
	}
    
    @Column(name = "RECEIPT_TYPE")
	public String getReceiptType()
	{
		return receiptType;
	}
	
	public void setReceiptType(String receiptType)
	{
		this.receiptType = receiptType;
	}
	
	@Column(name = "RATE")
	public Double getRate()
	{
		return rate;
	}
	
	public void setRate(Double rate)
	{
		this.rate = rate;
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
	public String getImName()
	{
		return imName;
	}
	
	public void setImName(String imName)
	{
		this.imName = imName;
	}
	
/*	@Transient
	public Double getDisplayRate()
	{
		return displayRate;
	}
	
	public void setDisplayRate(Double displayRate)
	{
		this.displayRate = displayRate;
	}*/
}