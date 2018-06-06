package com.bcits.bfm.model;

import java.sql.Blob;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name="ASSET_MASTER")
@NamedQueries({
	@NamedQuery(name = "Asset.findAll", query = "SELECT a FROM Asset a ORDER BY a.assetId DESC"),
	@NamedQuery(name = "Asset.getAllAssetOnCatId", query = "SELECT a.assetId,a.assetName FROM Asset a WHERE a.assetCatId =:assetCatId AND a.assetStatus = 'Approved'"),
	@NamedQuery(name = "Asset.getAllAssetOnLocId", query = "SELECT a.assetId,a.assetName FROM Asset a WHERE a.assetLocId =:assetLocId AND a.assetStatus = 'Approved'"),
	@NamedQuery(name = "Asset.getAllAssetsForAll", query = "SELECT a.assetId,a.assetName FROM Asset a WHERE a.assetStatus = 'Approved'"),
	@NamedQuery(name = "Asset.getAssetsonCatIdAndLocId", query = "SELECT a FROM Asset a WHERE (a.assetCatId=:assetCatId OR a.assetLocId =:assetLocId) AND a.assetStatus = 'Approved'"),
	@NamedQuery(name = "Asset.setStatus", query = "UPDATE Asset a SET a.assetStatus = :assetStatus WHERE a.assetId = :assetId"),
	@NamedQuery(name = "Asset.uploadAssetOnId",query = "UPDATE Asset a SET a.assetDocument= :assetDocument , a.assetDocumentType=:assetDocumentType  WHERE a.assetId= :assetId" ),
	@NamedQuery(name = "Asset.findAllList", query = "SELECT a.assetId,a.assetName,a.assetDesc,a.assetPoDetail,a.assetCatId,act.treeHierarchy,a.assetType,a.ownerShip,a.assetLocId,alt.treeHierarchy,a.assetGeoTag,a.assetCondition,a.drGroupId,a.assetNotes,a.assetManufacturer,cc.name,cc.ccId,md.mtDpIt,d.dept_Name,a.assetYear,a.lastUpdatedDate,a.assetModelNo,a.assetManufacturerSlNo,a.assetTag,a.assetVendorId,a.useFullLife,a.assetStatus,p.firstName,p.lastName,a.createdBy,a.updatedBy,a.purchaseDate,a.warrantyTill,a.assetLifeExpiry,a.supplier,(SELECT CONCAT(p.firstName, NVL(p.lastName, '')) FROM AssetOwnerShip ao INNER JOIN ao.ownerShipPerson p WHERE ao.assetId=a.assetId),(SELECT CONCAT(p1.firstName, NVL(p1.lastName, '')) FROM AssetOwnerShip ao INNER JOIN ao.maintainanceOwnerPerson p1 WHERE ao.assetId=a.assetId) FROM Asset a INNER JOIN a.assetCategoryTree act INNER JOIN a.assetLocationTree alt INNER JOIN a.costCenter cc INNER JOIN a.maintainanceDepartment md INNER JOIN md.department d INNER JOIN a.vendor v INNER JOIN v.person p ORDER BY a.assetId DESC"),
	@NamedQuery(name = "Asset.assetsAndInventary", query = "Select count(*),(select count(*) from StoreMaster m Where m.storeStatus='Active'),(select count(*) from StoreMaster m1 Where m1.storeStatus='Inactive') from Asset a group by 1"),
    @NamedQuery(name = "Asset.getDataForViewReport", query = "SELECT a.assetId,a.assetName,a.assetDesc,a.assetPoDetail,a.assetCatId,act.treeHierarchy,a.assetType,a.ownerShip,a.assetLocId,alt.treeHierarchy,a.assetGeoTag,a.assetCondition,a.drGroupId,a.assetNotes,a.assetManufacturer,cc.name,cc.ccId,md.mtDpIt,d.dept_Name,a.assetYear,a.lastUpdatedDate,a.assetModelNo,a.assetManufacturerSlNo,a.assetTag,a.assetVendorId,a.useFullLife,a.assetStatus,p.firstName,p.lastName,a.createdBy,a.updatedBy,a.purchaseDate,a.warrantyTill,a.assetLifeExpiry,a.supplier,(SELECT CONCAT(p.firstName, NVL(p.lastName, '')) FROM AssetOwnerShip ao INNER JOIN ao.ownerShipPerson p WHERE ao.assetId=a.assetId),(SELECT CONCAT(p1.firstName, NVL(p1.lastName, '')) FROM AssetOwnerShip ao INNER JOIN ao.maintainanceOwnerPerson p1 WHERE ao.assetId=a.assetId) FROM Asset a INNER JOIN a.assetCategoryTree act INNER JOIN a.assetLocationTree alt INNER JOIN a.costCenter cc INNER JOIN a.maintainanceDepartment md INNER JOIN md.department d INNER JOIN a.vendor v INNER JOIN v.person p WHERE a.assetStatus ='Approved' ORDER BY a.assetId DESC"),

})
public class Asset {
	
	private int assetId;
	private String assetName;
	private String assetDesc;
	private String assetPoDetail;
	private int assetCatId;
	private String assetType;
	private String ownerShip;
	private int assetLocId;
	private String assetGeoTag;
	private String assetCondition;
	private int drGroupId;
	private String assetNotes;
	private String assetManufacturer;
	private Date assetYear;
	private String assetModelNo;
	private String assetManufacturerSlNo;
	private String assetTag;
	private int assetVendorId;
	private String useFullLife;
	private String assetStatus;
	private String createdBy;
	private String updatedBy;
	private Date lastUpdatedDate;
	
	private Date purchaseDate;
	private Date warrantyTill;
	private String supplier;
	
	private Blob assetDocument;
	private String assetDocumentType;
	
	private Date assetLifeExpiry;
	
	private int ccId;
	private CostCenter costCenter;
	private MaintainanceDepartment maintainanceDepartment;
	
	
	@Id
	@SequenceGenerator(name="SEQ_ASSET_MASTER" ,sequenceName="ASSET_MASTER_SEQ")
	@GeneratedValue(generator="SEQ_ASSET_MASTER")
	@Column(name="AM_ID")
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Size(min = 1, max = 50, message = "Asset name can have maximum {max} letters")
	@Column(name="ASSET_NAME")
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	@Size(min = 0, max = 500, message = "Asset Description can have maximum {max} letters")
	@Column(name="ASSET_DESCRIPTION")
	public String getAssetDesc() {
		return assetDesc;
	}
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}
	
	@Size(min = 0, max = 225, message = "Asset PO Details can have maximum {max} letters")
	@Column(name="ASSET_PO_DETAILS")
	public String getAssetPoDetail() {
		return assetPoDetail;
	}
	public void setAssetPoDetail(String assetPoDetail) {
		this.assetPoDetail = assetPoDetail;
	}
	
	@NotNull(message = "Please select Category")
	@Column(name="ASSET_CATEGORY_ID")
	public int getAssetCatId() {
		return assetCatId;
	}
	public void setAssetCatId(int assetCatId) {
		this.assetCatId = assetCatId;
	}
	
	@Size(min = 0, max = 45, message = "Asset Type can have maximum {max} letters")
	@Column(name="ASSET_TYPE")
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	
	@Size(min = 1, max = 45, message = "Asset Ownership can have maximum {max} letters")
	@Column(name="ASSET_OWNERSHIP")
	public String getOwnerShip() {
		return ownerShip;
	}
	public void setOwnerShip(String ownerShip) {
		this.ownerShip = ownerShip;
	}
	
	@NotNull
	@Column(name="ASSET_LOCATION_ID")
	public int getAssetLocId() {
		return assetLocId;
	}
	public void setAssetLocId(int assetLocId) {
		this.assetLocId = assetLocId;
	}
	
	@Size(min = 0, max = 45, message = "Asset Geo Tag can have maximum {max} letters")
	@Column(name="ASSET_GEO_TAG")
	public String getAssetGeoTag() {
		return assetGeoTag;
	}
	public void setAssetGeoTag(String assetGeoTag) {
		this.assetGeoTag = assetGeoTag;
	}
	
	@Size(min = 0, max = 45, message = "Asset Condition can have maximum {max} letters")
	@Column(name="ASSET_CONDITION")
	public String getAssetCondition() {
		return assetCondition;
	}
	public void setAssetCondition(String assetCondition) {
		this.assetCondition = assetCondition;
	}
	
	@Column(name="DR_GROUP_ID")
	public int getDrGroupId() {
		return drGroupId;
	}
	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}
	
	@Size(min = 0, max = 500, message = "Asset Notes can have maximum {max} letters")
	@Column(name="ASSET_NOTES")
	public String getAssetNotes() {
		return assetNotes;
	}
	public void setAssetNotes(String assetNotes) {
		this.assetNotes = assetNotes;
	}
	
	@Size(min = 0, max = 100, message = "Asset Manufacturer can have maximum {max} letters")
	@Column(name="ASSET_MANUFACTURER")
	public String getAssetManufacturer() {
		return assetManufacturer;
	}
	public void setAssetManufacturer(String assetManufacturer) {
		this.assetManufacturer = assetManufacturer;
	}
	
	@Column(name="ASSET_YEAR")
	public Date getAssetYear() {
		return assetYear;
	}
	public void setAssetYear(Date assetYear) {
		this.assetYear = assetYear;
	}
	
	@Size(min = 0, max = 100, message = "Asset Model Number can have maximum {max} letters")
	@Column(name="ASSET_MODEL_NO")
	public String getAssetModelNo() {
		return assetModelNo;
	}
	public void setAssetModelNo(String assetModelNo) {
		this.assetModelNo = assetModelNo;
	}
	
	@Size(min = 0, max = 100, message = "Asset Manufacturer Sl No. can have maximum {max} letters")
	@Column(name="ASSET_MANUFACTURER_SNO")
	public String getAssetManufacturerSlNo() {
		return assetManufacturerSlNo;
	}
	public void setAssetManufacturerSlNo(String assetManufacturerSlNo) {
		this.assetManufacturerSlNo = assetManufacturerSlNo;
	}
	
	@Size(min = 0, max = 100, message = "Asset Tag can have maximum {max} letters")
	@Column(name="ASSET_TAG")
	public String getAssetTag() {
		return assetTag;
	}
	public void setAssetTag(String assetTag) {
		this.assetTag = assetTag;
	}
	
	@Column(name="ASSET_VENDOR_ID")
	public int getAssetVendorId() {
		return assetVendorId;
	}
	public void setAssetVendorId(int assetVendorId) {
		this.assetVendorId = assetVendorId;
	}
	
	@Column(name="ASSET_USEFUL_LIFE")
	public String getUseFullLife() {
		return useFullLife;
	}
	public void setUseFullLife(String useFullLife) {
		this.useFullLife = useFullLife;
	}
	
	@NotNull
	@Column(name="STATUS")
	public String getAssetStatus() {
		return assetStatus;
	}
	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="LAST_UPDATED_DT")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Column(name="ASSET_PURCHASE_DATE")
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	@Column(name="WARRANTY_TILL")
	public Date getWarrantyTill() {
		return warrantyTill;
	}
	public void setWarrantyTill(Date warrantyTill) {
		this.warrantyTill = warrantyTill;
	}

	
	@Column(name="SUPPLIER")
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name="ASSET_DOCUMENT")
	public Blob getAssetDocument() {
		return assetDocument;
	}
	public void setAssetDocument(Blob assetDocument) {
		this.assetDocument = assetDocument;
	}
	
	@Column(name="ASSET_DOC_TYPE")
	public String getAssetDocumentType() {
		return assetDocumentType;
	}
	public void setAssetDocumentType(String assetDocumentType) {
		this.assetDocumentType = assetDocumentType;
	}
	

	@Column(name="LIFE_EXPIRY")
	public Date getAssetLifeExpiry() {
		return assetLifeExpiry;
	}
	public void setAssetLifeExpiry(Date assetLifeExpiry) {
		this.assetLifeExpiry = assetLifeExpiry;
	}
	
	
	
	private AssetCategoryTree assetCategoryTree;


	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="ASSET_CATEGORY_ID",insertable=false, updatable=false)
	public AssetCategoryTree getAssetCategoryTree() {
		return assetCategoryTree;
	}
	public void setAssetCategoryTree(AssetCategoryTree assetCategoryTree) {
		this.assetCategoryTree = assetCategoryTree;
	}
	
	private AssetLocationTree assetLocationTree;

	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="ASSET_LOCATION_ID",insertable=false, updatable=false)
	public AssetLocationTree getAssetLocationTree() {
		return assetLocationTree;
	}
	public void setAssetLocationTree(AssetLocationTree assetLocationTree) {
		this.assetLocationTree = assetLocationTree;
	}

	
	private Vendors vendor;


	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="ASSET_VENDOR_ID",insertable=false, updatable=false)
	public Vendors getVendor() {
		return vendor;
	}
	public void setVendor(Vendors vendor) {
		this.vendor = vendor;
	}
	
	
	@Column(name="CC_ID")
	public int getCcId() {
		return ccId;
	}
	public void setCcId(int ccId) {
		this.ccId = ccId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="CC_ID",insertable=false, updatable=false)
	public CostCenter getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="MT_DP_IT")
	public MaintainanceDepartment getMaintainanceDepartment() {
		return maintainanceDepartment;
	}
	public void setMaintainanceDepartment(
			MaintainanceDepartment maintainanceDepartment) {
		this.maintainanceDepartment = maintainanceDepartment;
	}
	
	/*@PrePersist
	protected void onCreate() {
		updatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
		assetStatus = "Created";
	}

	@PreUpdate
	protected void onUpdate() {
		updatedBy = (String) SessionData.getUserDetails().get("userID");
	}*/
	
	
	@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		updatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDate = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		updatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDate = new Timestamp(new Date().getTime());
	}

	
}
