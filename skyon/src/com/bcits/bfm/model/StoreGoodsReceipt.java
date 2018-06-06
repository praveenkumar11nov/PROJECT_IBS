package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = "StoreGoodsReceipt.findAllStoreGoodsReceipts", query = "SELECT s FROM StoreGoodsReceipt s ORDER BY s.stgrId DESC"),
	
	@NamedQuery(name = "StoreGoodsReceipt.getRequiredStoreGoodsReceipts", query = "SELECT s.stgrId, s.poRecDt, "
			+ "s.drGroupId, s.receivedByStaffId, s.checkedByStaffId, s.ledgerUpdateDt, s.createdBy, s.lastUpdatedBy, "
			+ "s.lastUpdatedDt, s.shippingDocumentNumber, s.vcId, s.storeId, "
			+ "v.contractName, sm.storeName, p1.firstName, p1.lastName, p2.firstName, p2.lastName, "
			+ "v.contractNo, sm.storeLocation "
			+ "FROM StoreGoodsReceipt s INNER JOIN s.vendorContracts v INNER JOIN s.storeMaster sm "
			+ "INNER JOIN s.receivedByStaff u1 INNER JOIN u1.person p1 INNER JOIN s.checkedByStaff u2 INNER JOIN u2.person p2 "
			+ "ORDER BY s.stgrId DESC"),
			
	@NamedQuery(name = "VendorContracts.getAllRequiredVCWhosHasChildrenVCLI", query = "SELECT v FROM VendorContracts v, VendorContractLineitems vcl WHERE v.vcId = vcl.vcId AND v.status LIKE 'Approved' AND vcl.reqType LIKE 'Item Supply' AND v.storeId NOT IN (SELECT DISTINCT sgr.storeId FROM StoreGoodsReceipt sgr WHERE sgr.vcId = v.vcId) ORDER BY v.vcId DESC"),
	@NamedQuery(name = "VendorContracts.getAllRequiredVCWhosHasChildrenVCLIIncludingDuplicates", query = "SELECT v FROM VendorContracts v, VendorContractLineitems vcl WHERE v.vcId = vcl.vcId AND v.status LIKE 'Approved' AND vcl.reqType LIKE 'Item Supply' ORDER BY v.vcId DESC"),
	@NamedQuery(name = "StoreGoodsReceipt.getVendorContractsWhoseItemsReqTypesAreAssetsAndConsumables", query = "SELECT DISTINCT A FROM VendorContracts A, VendorInvoices B, VendorLineItems C, RequisitionDetails D, Requisition E WHERE A.vcId= B.vcId AND B.viId= C.viId AND C.imId = D.imId AND D.requisitionId = E.reqId  ORDER BY A.vcId DESC"),
	@NamedQuery(name = "StoreGoodsReceipt.getItemsBasedOnVendorContractId", query = "SELECT B.imId, B.quantity, U.uomId FROM VendorContracts A, VendorContractLineitems B, RequisitionDetails C, Requisition D, ItemMaster I, UnitOfMeasurement U WHERE A.vcId= B.vcId AND B.imId = C.imId AND C.requisitionId = D.reqId AND C.imId = I.imId AND I.imId = U.imId AND U.baseUom = 'Yes' AND A.vcId = :vcId ORDER BY B.vclId DESC"),
	
	@NamedQuery(name = "StoreGoodsReceipt.getRequiredContractDetails", query = "SELECT DISTINCT so.storeId, so.vcId, v.contractName, v.contractNo FROM StoreGoodsReceipt so INNER JOIN so.vendorContracts v INNER JOIN so.storeGoodsReceiptItemsSet si WHERE si.sgriStatus LIKE 'Active' ORDER BY so.vcId"),
	@NamedQuery(name = "StoreGoodsReceipt.getRequiredContractDetailsBasedOnStoreIdAndImId", query = "SELECT DISTINCT so.storeId, so.vcId, v.contractName, v.contractNo FROM StoreGoodsReceipt so INNER JOIN so.vendorContracts v INNER JOIN so.storeGoodsReceiptItemsSet si WHERE si.sgriStatus LIKE 'Active' AND so.storeId = :storeId AND si.imId = :imId ORDER BY so.vcId"),
	@NamedQuery(name = "StoreGoodsReceipt.getAllRequiredItemsFieldsForStoreMovement", query = "SELECT DISTINCT so.vcId, si.imId, i.imName, i.imType, i.imGroup FROM StoreGoodsReceipt so INNER JOIN so.storeGoodsReceiptItemsSet si INNER JOIN si.itemMaster i WHERE si.sgriStatus LIKE 'Active' ORDER BY so.vcId"),
	@NamedQuery(name = "StoreGoodsReceipt.getRequiredStoreFields", query = "SELECT u.baseUom, u.uomConversion, si.itemQuantity, si.sgriId FROM StoreGoodsReceipt so INNER JOIN so.storeGoodsReceiptItemsSet si INNER JOIN si.unitOfMeasurement u WHERE si.sgriStatus LIKE 'Active' AND so.vcId = :vcId AND si.imId = :imId ORDER BY si.activationDt"),
	@NamedQuery(name = "StoreGoodsReceipt.getRequiredStoreItems", query = "SELECT u.baseUom, u.uomConversion, si.itemQuantity, si.sgriId, so.vcId, vc.contractName, vc.contractNo FROM StoreGoodsReceipt so INNER JOIN so.storeGoodsReceiptItemsSet si INNER JOIN si.unitOfMeasurement u INNER JOIN so.vendorContracts vc WHERE si.sgriStatus LIKE 'Active' AND so.storeId = :storeId AND si.imId = :imId ORDER BY si.activationDt"),
	@NamedQuery(name = "StoreGoodsReceipt.updateLedgerDate", query = "UPDATE StoreGoodsReceipt s SET s.ledgerUpdateDt = :ledgerUpdateDt WHERE s.stgrId = :stgrId"),
	@NamedQuery(name = "StoreGoodsReceipt.getDataForTree", query = "SELECT DISTINCT s.storeId, s.vcId, si.imId, sm.storeName, vc.contractName, im.imName FROM StoreGoodsReceipt s "
			+ "INNER JOIN s.storeGoodsReceiptItemsSet si INNER JOIN s.storeMaster sm INNER JOIN s.vendorContracts vc INNER JOIN si.itemMaster im WHERE si.sgriStatus LIKE 'Active'"),
	
	@NamedQuery(name = "StoreGoodsReceipt.find", query = "SELECT s.stgrId, s.poRecDt, "
			+ "s.drGroupId, s.receivedByStaffId, s.checkedByStaffId, s.ledgerUpdateDt, s.createdBy, s.lastUpdatedBy, "
			+ "s.lastUpdatedDt, s.shippingDocumentNumber, s.vcId, s.storeId, "
			+ "v.contractName, sm.storeName, p1.firstName, p1.lastName, p2.firstName, p2.lastName, "
			+ "v.contractNo, sm.storeLocation "
			+ "FROM StoreGoodsReceipt s INNER JOIN s.vendorContracts v INNER JOIN s.storeMaster sm "
			+ "INNER JOIN s.receivedByStaff u1 INNER JOIN u1.person p1 INNER JOIN s.checkedByStaff u2 INNER JOIN u2.person p2 "
			+ "WHERE s.stgrId = :stgrId")
})
@Table(name = "STORE_GOODS_RECEIPT")
public class StoreGoodsReceipt 
{
	// Fields

	private int stgrId;
	private VendorContracts vendorContracts;
	private StoreMaster storeMaster;
	private Timestamp poRecDt;
	private int drGroupId;
	private int receivedByStaffId;
	private int checkedByStaffId;
	private Timestamp ledgerUpdateDt;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	private String shippingDocumentNumber;
	
	private int vcId;
	private int storeId;
	
	private Date poRecDate;
	private String poRecTime;
	
	private Users receivedByStaff;
	private Users checkedByStaff;
	
	private String receivedByStaffName;
	private String checkedByStaffName;
	
	private String storeName;
	private String contractName;
	
	private String storeLocation;
	private String contractNo;
	
	private Set<StoreGoodsReceiptItems> storeGoodsReceiptItemsSet = new HashSet<StoreGoodsReceiptItems>(0);

	// Property accessors
	@Id
	@SequenceGenerator(name = "StoreGoodsReceiptSeq", sequenceName = "STORE_GOODS_RECEIPT_SEQ")
	@GeneratedValue(generator = "StoreGoodsReceiptSeq")
	@Column(name = "STGR_ID")
	public int getStgrId() 
	{
		return this.stgrId;
	}

	public void setStgrId(int stgrId) 
	{
		this.stgrId = stgrId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VC_ID", insertable = false, updatable = false, nullable = false)
	public VendorContracts getVendorContracts() 
	{
		return this.vendorContracts;
	}

	public void setVendorContracts(VendorContracts vendorContracts) 
	{
		this.vendorContracts = vendorContracts;
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

	@Column(name = "PO_REC_DT")
	public Timestamp getPoRecDt() 
	{
		return this.poRecDt;
	}

	public void setPoRecDt(Timestamp poRecDt) 
	{
		this.poRecDt = poRecDt;
	}

	@Column(name = "DR_GROUP_ID")
	public int getDrGroupId()
	{
		return this.drGroupId;
	}

	public void setDrGroupId(int drGroupId) 
	{
		this.drGroupId = drGroupId;
	}

	@Column(name = "RECEIVED_BY_STAFF_ID")
	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreGoodsReceipt.receivedByStaffId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceipt.receivedByStaffId")
	public int getReceivedByStaffId()
	{
		return receivedByStaffId;
	}
	
	public void setReceivedByStaffId(int receivedByStaffId)
	{
		this.receivedByStaffId = receivedByStaffId;
	}

	@Column(name = "CHECKED_BY_STAFF_ID")
	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreGoodsReceipt.checkedByStaffId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceipt.checkedByStaffId")
	public int getCheckedByStaffId()
	{
		return checkedByStaffId;
	}
	
	public void setCheckedByStaffId(int checkedByStaffId)
	{
		this.checkedByStaffId = checkedByStaffId;
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

	@Column(name = "CREATED_BY")
	public String getCreatedBy() 
	{
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
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
	
	@Column(name = "SHIPPING_DOCUMENT_NUMBER")
	public String getShippingDocumentNumber()
	{
		return shippingDocumentNumber;
	}
	
	public void setShippingDocumentNumber(String shippingDocumentNumber)
	{
		this.shippingDocumentNumber = shippingDocumentNumber;
	}
	
	@Column(name = "VC_ID")
	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreGoodsReceipt.vcId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceipt.vcId")
	public int getVcId()
	{
		return vcId;
	}

	public void setVcId(int vcId)
	{
		this.vcId = vcId;
	}

	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreGoodsReceipt.storeId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreGoodsReceipt.storeId")
	@Column(name = "STORE_ID")
	public int getStoreId()
	{
		return storeId;
	}

	public void setStoreId(int storeId)
	{
		this.storeId = storeId;
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
	public Date getPoRecDate()
	{
		return poRecDate;
	}

	public void setPoRecDate(Date poRecDate)
	{
		this.poRecDate = poRecDate;
	}

	@Transient
	public String getPoRecTime()
	{
		return poRecTime;
	}

	public void setPoRecTime(String poRecTime)
	{
		this.poRecTime = poRecTime;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RECEIVED_BY_STAFF_ID", insertable = false, updatable = false, nullable = false)
	public Users getReceivedByStaff()
	{
		return receivedByStaff;
	}
	
	public void setReceivedByStaff(Users receivedByStaff)
	{
		this.receivedByStaff = receivedByStaff;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHECKED_BY_STAFF_ID", insertable = false, updatable = false, nullable = false)
	public Users getCheckedByStaff()
	{
		return checkedByStaff;
	}
	
	public void setCheckedByStaff(Users checkedByStaff)
	{
		this.checkedByStaff = checkedByStaff;
	}

	@Transient
	public String getReceivedByStaffName()
	{
		return receivedByStaffName;
	}

	public void setReceivedByStaffName(String receivedByStaffName)
	{
		this.receivedByStaffName = receivedByStaffName;
	}

	@Transient
	public String getCheckedByStaffName()
	{
		return checkedByStaffName;
	}

	public void setCheckedByStaffName(String checkedByStaffName)
	{
		this.checkedByStaffName = checkedByStaffName;
	}
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "STGR_ID")
	public Set<StoreGoodsReceiptItems> getStoreGoodsReceiptItemsSet()
	{
		return storeGoodsReceiptItemsSet;
	}
	
	public void setStoreGoodsReceiptItemsSet(
			Set<StoreGoodsReceiptItems> storeGoodsReceiptItemsSet)
	{
		this.storeGoodsReceiptItemsSet = storeGoodsReceiptItemsSet;
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
	public String getContractNo()
	{
		return contractNo;
	}
	
	public void setContractNo(String contractNo)
	{
		this.contractNo = contractNo;
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
	
}