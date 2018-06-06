package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	  @NamedQuery(name="StoreIssue.findAllStoreIssues",query="SELECT s FROM StoreIssue s ORDER BY s.striId DESC"),
})
@Table(name = "STORE_ISSUE")
public class StoreIssue
{
	private int striId;
	private JcMaterials jcMaterials;
	private StoreMaster storeMaster;
	private VendorContracts vendorContracts;
	private ItemMaster itemMaster;
	private UnitOfMeasurement unitOfMeasurement;
	private Double imQuantity;
	private Timestamp ledgerUpdateDt;
	private Timestamp striDt;
	private String reasonForIssue;
	private String createdBy;
	private String lastUpdatedBy;
	@Access(AccessType.FIELD)
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	private int jcId;
	private int storeId;
	private int vcId;
	private int imId;
	private int uomId;
	
	
	private String jobNo;
	private String storeName;
	private String contractName;
	private String imName;
	private String uom;
	
	private Date striDate;
	private String striTime;
	
	@Id
	@SequenceGenerator(name="STORE_ISSUE_SEQ",sequenceName="STORE_ISSUE_SEQ")
	@GeneratedValue(generator="STORE_ISSUE_SEQ")
	@Column(name = "STRI_ID")
	public int getStriId() {
		return this.striId;
	}

	public void setStriId(int striId) {
		this.striId = striId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JC_ID", insertable = false, updatable = false, nullable = false)
	public JcMaterials getJcMaterials() {
		return jcMaterials;
	}

	public void setJcMaterials(JcMaterials jcMaterials) {
		this.jcMaterials = jcMaterials;
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
	@JoinColumn(name = "VC_ID", insertable = false, updatable = false, nullable = false)
	public VendorContracts getVendorContracts()
	{
		return vendorContracts;
	}
	
	public void setVendorContracts(VendorContracts vendorContracts)
	{
		this.vendorContracts = vendorContracts;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID", insertable = false, updatable = false, nullable = false)
	public ItemMaster getItemMaster() {
		return this.itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	@Column(name = "STRI_DT")
	public Timestamp getStriDt() {
		return this.striDt;
	}

	public void setStriDt(Timestamp striDt) {
		this.striDt = striDt;
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

	@Column(name = "IM_QUANTITY")
	@net.sf.oval.constraint.Min(value = 0, message = "Min.StoreIssue.imQuantity")
    @net.sf.oval.constraint.Max(value = 999999999, message = "Max.StoreIssue.imQuantity")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreIssue.imQuantity")
	public Double getImQuantity() {
		return this.imQuantity;
	}

	public void setImQuantity(Double imQuantity) {
		this.imQuantity = imQuantity;
	}

	@Column(name = "REASON_FOR_ISSUE")
	public String getReasonForIssue()
	{
		return reasonForIssue;
	}
	
	public void setReasonForIssue(String reasonForIssue)
	{
		this.reasonForIssue = reasonForIssue;
	}
	
	@Column(name = "LEDGER_UPDATE_DT")
	public Timestamp getLedgerUpdateDt() {
		return this.ledgerUpdateDt;
	}

	public void setLedgerUpdateDt(Timestamp ledgerUpdateDt) {
		this.ledgerUpdateDt = ledgerUpdateDt;
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

	@Column(name = "LAST_UPDATED_DT")
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

	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreIssue.jcId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreIssue.jcId")
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
	
	@Column(name = "VC_ID")
	public int getVcId()
	{
		return vcId;
	}
	
	public void setVcId(int vcId)
	{
		this.vcId = vcId;
	}
	
	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreIssue.imId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreIssue.imId")
	@Column(name = "IM_ID")
	public int getImId()
	{
		return imId;
	}

	public void setImId(int imId)
	{
		this.imId = imId;
	}

	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreIssue.uomId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreIssue.uomId")
	@Column(name = "IM_UOM")
	public int getUomId()
	{
		return uomId;
	}

	public void setUomId(int uomId)
	{
		this.uomId = uomId;
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
	public String getContractName()
	{
		return contractName;
	}
	
	public void setContractName(String contractName)
	{
		this.contractName = contractName;
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
	public Date getStriDate()
	{
		return striDate;
	}

	public void setStriDate(Date striDate)
	{
		this.striDate = striDate;
	}

	@Transient
	public String getStriTime()
	{
		return striTime;
	}

	public void setStriTime(String striTime)
	{
		this.striTime = striTime;
	}

}