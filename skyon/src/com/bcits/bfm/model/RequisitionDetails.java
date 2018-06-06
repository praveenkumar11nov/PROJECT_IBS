package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="REQ_DETAIL")
@NamedQueries
({
	@NamedQuery(name="RequisitionDetails.findRequisitionDetails",query="SELECT r FROM RequisitionDetails r where r.requisitionId = :id"),
	@NamedQuery(name="RequisitionDetails.findRequisitionId",query="SELECT r.reqId FROM Requisition r"),
	@NamedQuery(name="ReqDetails.findAll",query="SELECT r FROM RequisitionDetails r ORDER BY r.rdId DESC"),
	@NamedQuery(name="ReqDetails.findReqDetailsBasedOnReqId",query="SELECT rd FROM RequisitionDetails rd where rd.requisitionId = :reqid"),
	
	@NamedQuery(name="RequisitionDetails.findAllRequiredRequisitionDetailsFields",
	query="SELECT rd.rdId,rd.requisitionId,rd.drGroupId,rd.rdSlno,rd.rdDescription,rd.rdQuantity,rd.createdBy,rd.lastUpdatedBy,im.imId,im.imName,um.uomId,um.uom,r1.reqType,d2.dn_Id,d2.dn_Name,rd.uomBudget,rd.reqFulfilled FROM RequisitionDetails rd INNER JOIN rd.itemMaster im INNER JOIN rd.unitOfMeasurement um INNER JOIN rd.requisition r1 INNER JOIN rd.designation d2 WHERE r1.reqId=:reqId"),
	
	@NamedQuery(name="Requisition.readDesignationForDepartment",
	query="SELECT d.dn_Id,d.dn_Name FROM Designation d INNER JOIN d.department dp WHERE dp.dept_Id = :deptId"),
	
	/*query="SELECT d.dn_Id FROM Designation d INNER JOIN Department dp d.department. WHERE d.dept_Id = :departmentId"),*/
	
})
public class RequisitionDetails implements java.io.Serializable 
{	
	@Id
	@SequenceGenerator(name = "requisitionDetailseq", sequenceName = "REQ_DETAILS_SEQ")
	@GeneratedValue(generator = "requisitionDetailseq")
	
	@NotNull(message="Req_DETID Cannot be empty")
	@Column(name = "RD_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int rdId;
	
	@Column(name="REQ_ID", unique=true, nullable=false, precision=11, scale=0)
	private int requisitionId;
	
	//@NotNull(message="Requisition be empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REQ_ID",insertable = false, updatable = false, nullable = false)
	private Requisition requisition;
	
	@Column(name = "DR_GROUP_ID", precision = 11, scale = 0)
	private int drGroupId;
	
	@Column(name = "RD_SLNO", length = 45)
	private int rdSlno;
	
	@Column(name="IM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int imId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IM_ID",insertable = false, updatable = false, nullable = false)
	private ItemMaster itemMaster;
	
	@Column(name = "RD_DESCRIPTION", length = 225)
	private String rdDescription;
	
	@Column(name="UOM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int uomId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UOM_ID",insertable = false, updatable = false, nullable = false)
	private UnitOfMeasurement unitOfMeasurement;
	
	@Column(name="DN_ID")
	private int dn_Id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DN_ID",insertable = false, updatable = false, nullable = false)
	private Designation designation;
	
	@NotNull
	@Column(name = "RD_QUANTITY", precision = 10, scale = 0)
	private int rdQuantity;
		
	@Column(name = "REQ_FULFILLED", precision = 10, scale = 0)
	private int reqFulfilled;
	
	@NotNull
	@Column(name = "BUDGET_UOM", precision = 10, scale = 0)
	private int uomBudget;
	
	/*@Column(name = "REQ_TYPE", nullable = false, length = 45)
	private String reqType;*/
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	public int getRdId() 
	{
		return this.rdId;
	}
	public void setRdId(int rdId) 
	{
		this.rdId = rdId;
	}	
	public Requisition getRequisition() 
	{
		return this.requisition;
	}
	public void setRequisition(Requisition requisition) 
	{
		this.requisition = requisition;
	}	
	public int getDrGroupId() 
	{
		return this.drGroupId;
	}
	public void setDrGroupId(int drGroupId) 
	{
		this.drGroupId = drGroupId;
	}	
	public int getRdSlno() 
	{
		return this.rdSlno;
	}
	public void setRdSlno(int rdSlno) 
	{
		this.rdSlno = rdSlno;
	}
	public String getRdDescription() 
	{
		return this.rdDescription;
	}
	public void setRdDescription(String rdDescription) 
	{
		this.rdDescription = rdDescription;
	}	
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}
	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	public int getRdQuantity() 
	{
		return this.rdQuantity;
	}
	public void setRdQuantity(int rdQuantity)
	{
		this.rdQuantity = rdQuantity;
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
	public int getImId() {
		return imId;
	}
	public void setImId(int imId) {
		this.imId = imId;
	}
	public ItemMaster getItemMaster() {
		return itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}
	public int getRequisitionId() {
		return requisitionId;
	}
	public void setRequisitionId(int requisitionId) {
		this.requisitionId = requisitionId;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public int getDn_Id() {
		return dn_Id;
	}
	public void setDn_Id(int dn_Id) {
		this.dn_Id = dn_Id;
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
	public int getUomBudget() {
		return uomBudget;
	}
	public void setUomBudget(int uomBudget) {
		this.uomBudget = uomBudget;
	}
	public int getReqFulfilled() {
		return reqFulfilled;
	}
	public void setReqFulfilled(int reqFulfilled) {
		this.reqFulfilled = reqFulfilled;
	}
	
	
}