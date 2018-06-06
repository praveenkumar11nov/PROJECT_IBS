package com.bcits.bfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

/**
 * ItemMaster entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ITEM_MASTER")
@NamedQueries
({
	@NamedQuery(name="ItemMaster.findAll",query="SELECT t FROM ItemMaster t WHERE t.imId!=1 ORDER BY t.imId DESC"),
	@NamedQuery(name="ItemMaster.getIdMaxCount",query="SELECT MAX(im.imId) as imId From ItemMaster im"),
	@NamedQuery(name="ItemMaster.getAllAssetSpares",query="SELECT i.imId, i.imName,i.imGroup,i.imDescription FROM ItemMaster i WHERE i.imType='Asset Spares' AND i.imId NOT IN(select a.imId from AssetSpares a where a.assetId=:assetId) "),
	@NamedQuery(name = "ItemMaster.getItemNames", query = "SELECT im.imName FROM ItemMaster im"),
	@NamedQuery(name="ItemMaster.getDetailsFromLedgerAndItemMasterForCronScheduler",query="SELECT sil.imId,sil.imBalance,im.imId,im.imOptimal_Stock,sil.storeId,sil.imUom FROM StoreItemLedger sil INNER JOIN sil.itemMaster im WHERE im.imId!=1"),
	@NamedQuery(name="ItemMaster.updateReorderLevelStatus",query="UPDATE ItemMaster t SET t.reorderLevel = 'Yes' WHERE t.imId = :id"),	
	@NamedQuery(name="Item.ReadCreditAndDebitDataForGraph",query="SELECT s.createdDate,sum(s.quantity),to_char(s.createdDate,'MM') FROM StoreItemLedgerDetails s WHERE s.transactionType=:type GROUP BY s.createdDate"),
	@NamedQuery(name="Item.getReqId",query="SELECT r.reqId FROM Requisition r WHERE r.storeId = :storeId"),
	@NamedQuery(name="Item.getAllDetails",query="SELECT r.dept_Id,r.personId,r.vendorId,r.createdBy,r.lastUpdatedBy,r.reqVendorQuoteRequisition,r.reqName,r.reqDate,r.reqByDate,r.reqDescription FROM Requisition r WHERE r.reqId=:reqId"),
	@NamedQuery(name="ItemMaster.readCreditAndDebitDataBasedOnItemId",query="SELECT s.createdDate,sum(s.quantity),to_char(s.createdDate,'MM') FROM StoreItemLedgerDetails s WHERE s.silId=:id and s.transactionType = :type GROUP BY s.createdDate"),
    //@NamedQuery(name="ItemMaster.checkItemIdExistenceFromReqDetails",query="SELECT r.status FROM RequisitionDetails rd INNER JOIN rd.requisition r WHERE rd.imId=:itemId AND rd.uomId = :uomId"),
	@NamedQuery(name="ItemMaster.findItemTypes",query="SELECT DISTINCT(im.imType),im.imId,im.imGroup,im.imName From ItemMaster im"),

	
})
public class ItemMaster
{
	@Id
	@SequenceGenerator(name = "itemMasterseq", sequenceName = "ITEMMASTER_SEQ")
	@GeneratedValue(generator = "itemMasterseq")
	@Column(name = "IM_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int imId;	
	
	@Column(name = "IM_GROUP", length = 45)
	private String imGroup;	
	
	@Column(name = "IM_TYPE", nullable = false, length = 45)
	private String imType;	
	
	@Column(name = "IM_PURCHASE_UOM", length = 45)
	private String imPurchaseUom;	
		
	@Column(name = "UNITS_OF_MEASUREMENT_CLASS", length = 45)
	private String uomClass;
	
	@Column(name = "IM_NAME", length = 45)
	private String imName;
	
	@Column(name = "IM_DESCRIPTION", length = 45)
	private String imDescription;
	
	@Column(name = "IM_UOM_USABLE", length = 45)
	private String imUOM_Usable;
	
	@Column(name = "IM_OPTIMAL_STOCK", length = 45)
	private int imOptimal_Stock;
	
	@Column(name = "IM_UOM_ISSUE", length = 45)
	private String imUomIssue;
	
	@Column(name = "REORDER_LEVEL", length = 45)
	private String reorderLevel;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;	
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name="ITEM_CREATED_DATE")
	private Date imCreatedDate;
	
	@Transient
	private String uom;
	
	@Transient
	private String code;
	

	public ItemMaster(){}

	public ItemMaster(int imId, String imType) {
		this.imId = imId;
		this.imType = imType;
	}

	/** full constructor */
	public ItemMaster(int imId, String imGroup, String imType,String uomClass,String imName,String imDescription,String imUOM_Usable,int imOptimal_Stock,String createdBy, String lastUpdatedBy,String imPurchaseUom,String imUomIssue) 
	{
		this.imId = imId;
		this.imGroup = imGroup;
		this.imType = imType;
		this.imName = imName;
		this.imDescription = imDescription;
		this.imUOM_Usable = imUOM_Usable;
		this.imOptimal_Stock = imOptimal_Stock;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.uomClass = uomClass;
		this.imPurchaseUom = imPurchaseUom;
		this.imUomIssue = imUomIssue;
	}	
	
	public int getImId() {
		return this.imId;
	}
	public void setImId(int imId)
	{
		this.imId = imId;
	}	
	public String getImGroup() 
	{
		return this.imGroup;
	}
	public void setImGroup(String imGroup) 
	{
		this.imGroup = imGroup;
	}	
	public String getImType() 
	{
		return this.imType;
	}
	public void setImType(String imType) 
	{
		this.imType = imType;
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
	public String getImName() 
	{
		return imName;
	}
	public void setImName(String imName) 
	{
		this.imName = imName;
	}
	public String getImDescription() 
	{
		return imDescription;
	}
	public void setImDescription(String imDescription)
	{
		this.imDescription = imDescription;
	}
	public String getImUOM_Usable() 
	{
		return imUOM_Usable;
	}
	public void setImUOM_Usable(String imUOM_Usable) 
	{
		this.imUOM_Usable = imUOM_Usable;
	}
	public int getImOptimal_Stock() 
	{
		return imOptimal_Stock;
	}
	public void setImOptimal_Stock(int imOptimal_Stock) 
	{
		this.imOptimal_Stock = imOptimal_Stock;
	}
	public String getUomClass() {
		return uomClass;
	}
	public void setUomClass(String uomClass) {
		this.uomClass = uomClass;
	}
	public String getImPurchaseUom() {
		return imPurchaseUom;
	}
	public void setImPurchaseUom(String imPurchaseUom) {
		this.imPurchaseUom = imPurchaseUom;
	}
	public String getImUomIssue() {
		return imUomIssue;
	}
	public void setImUomIssue(String imUomIssue) {
		this.imUomIssue = imUomIssue;
	}	
	public String getReorderLevel() {
		return reorderLevel;
	}
	public void setReorderLevel(String reorderLevel) {
		this.reorderLevel = reorderLevel;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getImCreatedDate() {
		return imCreatedDate;
	}
	public void setImCreatedDate(Date imCreatedDate) {
		this.imCreatedDate = imCreatedDate;
	}
	
	@PrePersist
	protected void onCreate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
	}
	@PreUpdate
	protected void onUpdate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
}