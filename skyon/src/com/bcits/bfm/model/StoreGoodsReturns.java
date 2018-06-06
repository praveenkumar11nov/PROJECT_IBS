package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	 @NamedQuery(name="StoreGoodsReturns.findAllStoreGoodsReturns",query="SELECT s.sgrId, s.storeId, s.imId, s.uomId, "
			+ "s.returnedToVendorId, s.returnedByStaffId, s.itemReturnQuantity, s.reasonForReturn, s.dateOfReturn, "
			+ "s.createdBy, s.lastUpdatedBy, s.lastUpdatedDt, sm.storeName, i.imName, u.uom, "
			+ "p1.title, p1.firstName, p1.lastName, p2.title, p2.firstName, p2.lastName, s.jcId,jcd.jobNo,jcd.jobName,jcd.jobGroup,sm.storeLocation,i.imType,i.imGroup,u.code,u.baseUom "
			+ "FROM StoreGoodsReturns s INNER JOIN s.storeMaster sm INNER JOIN s.itemMaster i "
			+ "INNER JOIN s.unitOfMeasurement u INNER JOIN s.returnedToVendor v INNER JOIN v.person p1 "
			+ "INNER JOIN s.returnedByStaff us INNER JOIN us.person p2 INNER JOIN s.jcMaterials j INNER JOIN j.jobCards jcd ORDER BY s.sgrId DESC"),
	 //@NamedQuery(name="StoreGoodsReturns.setStoreGoodsReturnStatus",query="UPDATE StoreGoodsReturns s SET s.sgrStatus = 'Active' WHERE s.sgrId = :sgrId"),
	})
@Table(name = "STORE_GOODS_RETURNS")
public class StoreGoodsReturns 
{
	private int sgrId;
	private Vendors returnedToVendor;
	private Users returnedByStaff;
	private JcMaterials jcMaterials;
	private StoreMaster storeMaster;
	private UnitOfMeasurement unitOfMeasurement;
	private ItemMaster itemMaster;
	private Double itemReturnQuantity;
	private String reasonForReturn;
	private Date dateOfReturn;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	private int returnedToVendorId;
	private int returnedByStaffId;
	private int jcId;
	private int storeId;
	private int uomId;
	private int imId;
	
	private String returnedToVendorName;
	private String returnedByStaffName;
	private String jobNo;
	private String storeName;
	private String uom;
	private String imName;

	@Id
	@SequenceGenerator(name="STORE_GOODS_RETURNS_SEQ",sequenceName="STORE_GOODS_RETURNS_SEQ")
	@GeneratedValue(generator="STORE_GOODS_RETURNS_SEQ")
	@Column(name = "SGR_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getSgrId() 
	{
		return this.sgrId;
	}

	public void setSgrId(int sgrId) 
	{
		this.sgrId = sgrId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RETURNED_TO_VENDOR_ID", insertable = false, updatable = false, nullable = false)
	public Vendors getReturnedToVendor()
	{
		return returnedToVendor;
	}
	
	public void setReturnedToVendor(Vendors returnedToVendor)
	{
		this.returnedToVendor = returnedToVendor;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RETURNED_BY_STAFF_ID", insertable = false, updatable = false, nullable = false)
	public Users getReturnedByStaff()
	{
		return returnedByStaff;
	}
	
	public void setReturnedByStaff(Users returnedByStaff)
	{
		this.returnedByStaff = returnedByStaff;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JC_ID", insertable = false, updatable = false, nullable = false)
	public JcMaterials getJcMaterials() {
		return this.jcMaterials;
	}

	public void setJcMaterials(JcMaterials jcMaterials) {
		this.jcMaterials = jcMaterials;
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
	@JoinColumn(name = "IM_UOM", insertable = false, updatable = false, nullable = false)
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

	@Column(name = "ITEM_RETURN_QUANTITY")
	public Double getItemReturnQuantity() 
	{
		return this.itemReturnQuantity;
	}

	public void setItemReturnQuantity(Double itemReturnQuantity) 
	{
		this.itemReturnQuantity = itemReturnQuantity;
	}

	@Column(name = "REASON_FOR_RETURN")
	public String getReasonForReturn()
	{
		return this.reasonForReturn;
	}

	public void setReasonForReturn(String reasonForReturn) 
	{
		this.reasonForReturn = reasonForReturn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_RETURN")
	public Date getDateOfReturn() 
	{
		return this.dateOfReturn;
	}

	public void setDateOfReturn(Date dateOfReturn) 
	{
		this.dateOfReturn = dateOfReturn;
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

	@Column(name = "RETURNED_TO_VENDOR_ID")
	public int getReturnedToVendorId()
	{
		return returnedToVendorId;
	}

	public void setReturnedToVendorId(int returnedToVendorId)
	{
		this.returnedToVendorId = returnedToVendorId;
	}

	@Column(name = "RETURNED_BY_STAFF_ID")
	public int getReturnedByStaffId()
	{
		return returnedByStaffId;
	}

	public void setReturnedByStaffId(int returnedByStaffId)
	{
		this.returnedByStaffId = returnedByStaffId;
	}

	@Column(name = "JC_ID")
	public int getJcId()
	{
		return jcId;
	}

	public void setJcId(int jcId)
	{
		this.jcId = jcId;
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

	@Column(name = "IM_UOM")
	public int getUomId()
	{
		return uomId;
	}

	public void setUomId(int uomId)
	{
		this.uomId = uomId;
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

	@Transient
	public String getReturnedToVendorName()
	{
		return returnedToVendorName;
	}

	public void setReturnedToVendorName(String returnedToVendorName)
	{
		this.returnedToVendorName = returnedToVendorName;
	}

	@Transient
	public String getReturnedByStaffName()
	{
		return returnedByStaffName;
	}
	
	public void setReturnedByStaffName(String returnedByStaffName)
	{
		this.returnedByStaffName = returnedByStaffName;
	}

	@Transient
	public String getJobNo()
	{
		return jobNo;
	}

	public void setJobNo(String string)
	{
		this.jobNo = string;
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
	public String getUom()
	{
		return uom;
	}

	public void setUom(String uom)
	{
		this.uom = uom;
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
	
}