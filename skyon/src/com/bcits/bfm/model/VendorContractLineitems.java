package com.bcits.bfm.model;
import java.sql.Timestamp;

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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@SuppressWarnings("serial")
@Entity
@Table(name = "VC_LINEITEMS")
@NamedQueries
({
	@NamedQuery(name="VendorContractLineitems.findAll",query="SELECT vc FROM VendorContractLineitems vc ORDER BY vc.vclId DESC"),
	@NamedQuery(name="VendorContractLineitems.getAllVCLineItemsBasedOnVcId",query="SELECT vcl FROM VendorContractLineitems vcl WHERE vcl.vcId = :vcId ORDER BY vcl.vclId DESC"),
	@NamedQuery(name="Requisition.getReqType",query="SELECT r.reqType FROM Requisition r WHERE r.reqId = :reqid"),
})
public class VendorContractLineitems implements java.io.Serializable
{	
	@Id
	@SequenceGenerator(name = "vcLineItemsSeq", sequenceName = "VC_LINEITEMS_SEQ")
	@GeneratedValue(generator = "vcLineItemsSeq")
	
	@Column(name = "VCL_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vclId;	
	
	@Column(name="VC_ID", insertable=false, updatable=false)
    private int vcId;
	
	@Column(name="IM_ID",unique=true, nullable=false, precision=11, scale=0)
    private int imId;
	
	@Column(name="UOM_ID",unique=true, nullable=false, precision=11, scale=0)
    private int uomId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UOM_ID", insertable = false, updatable = false, nullable = false)
	private UnitOfMeasurement unitOfMeasurement;	
	
	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}
	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID", insertable = false, updatable = false, nullable = false)
	private ItemMaster itemMaster;	
	
	@Column(name = "VCL_SLNO")
	private int vclSlno;
	
	@Column(name = "REQ_TYPE")
	private String reqType;
	
	@Column(name = "QUANTITY", precision = 11, scale = 0)
	private int quantity;
	
	@Column(name = "RATE", precision = 11, scale = 0)
	private int rate;
	
	@Column(name = "AMOUNT", precision = 11, scale = 0)
	private int amount;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
	
	@Transient
	private String imName;
		
	public int getVclId() {
		return this.vclId;
	}
	public void setVclId(int vclId) {
		this.vclId = vclId;
	}	
	public ItemMaster getItemMaster() {
		return this.itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}	
	public int getVclSlno() {
		return this.vclSlno;
	}
	public void setVclSlno(int vclSlno) {
		this.vclSlno = vclSlno;
	}	
	public int getQuantity() {
		return this.quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
	public int getRate() {
		return this.rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}	
	public int getAmount() {
		return this.amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCreatedBy() {
		return this.createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}	
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	public int getVcId() {
		return vcId;
	}
	public void setVcId(int vcId) {
		this.vcId = vcId;
	}
	public int getImId() {
		return imId;
	}
	public void setImId(int imId) {
		this.imId = imId;
	}
	public String getImName() {
		return imName;
	}
	public void setImName(String imName) {
		this.imName = imName;
	}
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	@PrePersist
	protected void onCreate() {
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		createdBy = (String) SessionData.getUserDetails().get("userID");
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
}