package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * Requisition entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "REQUISITIONS")
@NamedQueries
({
	@NamedQuery(name="Requisition.findAll",query="SELECT r FROM Requisition r WHERE r.reqId!=1 AND r.reqId!=2 ORDER BY r.reqId DESC"),
	@NamedQuery(name="ReadVendors.findAll",query="SELECT p.personId,p.firstName, p.lastName FROM Vendors v INNER JOIN v.person p"),
	@NamedQuery(name = "Requisition.UpdateStatus",query="UPDATE Requisition r SET  r.status = :status WHERE r.reqId = :reqId"),
	@NamedQuery(name = "Requisition.getRequisitionName", query = "SELECT r.reqName FROM Requisition r"),	
    @NamedQuery(name="Req.getDepartmentDeatils",query="SELECT d.dept_Id,p.personId,p.firstName,p.lastName,u.urLoginName,d.dept_Name FROM Department d,Users u,Person p WHERE u.deptId=d.dept_Id AND u.personId=p.personId AND p.personId!=1"),
    @NamedQuery(name="Req.getDepartmentDeatilsEqualToOne",query="SELECT d.dept_Id,p.personId,p.firstName,p.lastName,u.urLoginName,d.dept_Name FROM Department d,Users u,Person p WHERE u.deptId=d.dept_Id AND u.personId=p.personId AND p.personId=1"),
    //for reading requisitionDetails if reqId found in child also
    @NamedQuery(name="Requisition.readReqDetailsIfReqIdExists",query="SELECT r FROM Requisition r,RequisitionDetails rd WHERE r.reqId = rd.requisitionId"),
    @NamedQuery(name="RequisitionDetails.getReqTypeBasedOnReqId",query="SELECT r.reqType,r.reqDate FROM Requisition r where r.reqId=:reqid"),
    
    @NamedQuery(name="Requisition.getAllRequiredRequisitionFields",
    query="select p.firstName,p.lastName,p.personId,v.vendorId,d.dept_Id,d.dept_Name, r.reqId, r.reqName,r.reqDescription, r.reqType, r.reqDate, r.reqByDate,r.drGroupId,r.reqVendorQuoteRequisition,r.status,r.createdBy,r.lastUpdatedBy,r.lastUpdatedDt,vp.firstName,vp.lastName,st.storeId,st.storeName From Requisition r INNER JOIN r.department d INNER JOIN r.person p INNER JOIN r.vendors v INNER JOIN v.person vp INNER JOIN r.storeMaster st ORDER BY r.reqId DESC"),
	
	@NamedQuery(name="Requisition.findParentIdExistenceinChild",query="SELECT rd.requisitionId FROM Requisition r,RequisitionDetails rd WHERE rd.requisitionId=:idVal"),
	@NamedQuery(name="Requisition.getManpowerCount",query = "select rd from RequisitionDetails rd, Requisition r where rd.requisitionId = r.reqId and r.reqType = 'Manpower' and r.reqByDate>=SYSDATE and rd.rdQuantity > rd.reqFulfilled"),
	@NamedQuery(name="Requisition.getManpowerReqVC",query = "select vc from VendorContracts vc where vc.requisition.reqType = 'Manpower' and vc.requisition.status='PO Placed' and vc.requisition.reqByDate >= SYSDATE and vc.status='Approved'"),
	@NamedQuery(name="Requisition.getManpowerReq",query = "select r from Requisition r where r.reqType = 'Manpower' and r.status='Approved' "),
	@NamedQuery(name="Requisition.getReqDetails",query = "select rd from RequisitionDetails rd where rd.rdQuantity > rd.reqFulfilled and rd.requisitionId=:reqId"),
	@NamedQuery(name="Req.getDummyDepartmentDeatils",query="SELECT d.dept_Id,p.personId,p.firstName,p.lastName,u.urLoginName,d.dept_Name FROM Department d,Users u,Person p WHERE u.deptId=d.dept_Id AND u.personId=p.personId AND p.personId=1"),
	@NamedQuery(name="Req.getCountRecords",query="SELECT COUNT(*) FROM Users u"),
	@NamedQuery(name="REQUISTAION.AllDaTa",query="select s.storeId,s.storeName,r.reqId,r.reqName,r.reqDescription,p.personId,p.firstName,p.lastName,d.dept_Id,d.dept_Name,r.reqDate,r.reqByDate,r.reqType,r.reqVendorQuoteRequisition,v.vendorId,v.person.firstName,v.person.lastName,r.status,r.createdBy,r.lastUpdatedBy,r.lastUpdatedDt from Requisition r inner join r.storeMaster s inner join r.person p inner join r.department d inner join r.vendors v  WHERE r.reqId!=1 AND r.reqId!=2 "),
	
})

public class Requisition implements java.io.Serializable {

	// Fields
	@Id
	@SequenceGenerator(name = "requisitionseq", sequenceName = "REQUISITION_SEQ")
	@GeneratedValue(generator = "requisitionseq")
	
	@Column(name = "REQ_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int reqId;
	
	//@Min(value=1, message = "Property Cannot Be Empty")
	@Column(name="PERSON_ID", unique=true, nullable=false, precision=11, scale=0)
    private int personId;
	 
	 @OneToOne	 
	 @JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
    private Person person;
	 
	 @Column(name="DEPT_ID", unique=true, nullable=false, precision=11, scale=0)
	 private int dept_Id;
		 
	 @OneToOne	 
	 @JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
	 private Department department;
	
	@Column(name = "REQ_DATE", length = 7)
	private Date reqDate;
	
	@Column(name = "REQ_TYPE", nullable = false, length = 45)
	private String reqType;
	
	@Column(name = "REQ_BY_DATE", length = 7)
	private Date reqByDate;
	
	@Column(name = "DR_GROUP_ID", precision = 11, scale = 0)
	private int drGroupId;
	
	@Column(name = "REQ_VENDOR_QUOTE_REQUISITION", length = 5)
	private String reqVendorQuoteRequisition;
	
	/*@Column(name = "RECOMMENDED_VENDOR_ID", length = 50)
	private String recommendedVendorId;*/
	
	@Column(name="VENDOR_ID", unique=true, nullable=false, precision=11, scale=0)
    private int vendorId;
	
	@OneToOne	 
	@JoinColumn(name = "VENDOR_ID", insertable = false, updatable = false, nullable = false)
	private Vendors vendors;	
	
	
	@Column(name = "STATUS", nullable = false, length = 45)
	private String status;
	
	@Column(name = "CREATED_BY", length = 45)
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY", length = 45)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
	
	@Column(name = "REQ_NAME", nullable = false, length = 45)
	private String reqName;
	
	@Column(name = "REQ_DESCRIPTION", nullable = false, length = 45)
	private String reqDescription;
	
	@Column(name="STORE_ID", unique=true, nullable=false, precision=11, scale=0)
    private int storeId;
	
	@OneToOne	 
	@JoinColumn(name = "STORE_ID", insertable = false, updatable = false, nullable = false)
	private StoreMaster storeMaster;
	
	// Constructors
	/** default constructor */
	public Requisition() {
	}

	/** full constructor */
	public Requisition(int reqId, int personId, int dept_Id,Date reqDate,String reqType,Date reqByDate,
			int drGroupId,String reqVendorQuoteRequisition,int vendorId,int camCategoryId,
					   String status, String createdBy,String lastUpdatedBy,Timestamp lastUpdatedDt) 
	{
		this.reqId = reqId;
		this.personId = personId;
		this.dept_Id = dept_Id;
		this.reqDate = reqDate;
		//this.reqType = reqType;
		this.reqByDate = reqByDate;
		this.drGroupId = drGroupId;
		this.reqVendorQuoteRequisition = reqVendorQuoteRequisition;
		this.vendorId = vendorId;		
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	public int getReqId() {
		return this.reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public int getDept_Id() {
		return dept_Id;
	}
	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Date getReqDate() {
		return this.reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}	
	public String getReqType() {
		return this.reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}	
	public Date getReqByDate() {
		return this.reqByDate;
	}
	public void setReqByDate(Date reqByDate) {
		this.reqByDate = reqByDate;
	}	
	public int getDrGroupId() {
		return this.drGroupId;
	}
	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}	
	public String getReqVendorQuoteRequisition() {
		return this.reqVendorQuoteRequisition;
	}
	public void setReqVendorQuoteRequisition(String reqVendorQuoteRequisition) {
		this.reqVendorQuoteRequisition = reqVendorQuoteRequisition;
	}	
	/*public String getRecommendedVendorId() {
		return this.recommendedVendorId;
	}

	public void setRecommendedVendorId(String recommendedVendorId) {
		this.recommendedVendorId = recommendedVendorId;
	}*/
	
	
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int ids) {
		this.vendorId = ids;
	}
	public Vendors getVendors() {
		return vendors;
	}
	public void setVendors(Vendors vendors) {
		this.vendors = vendors;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
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
		return lastUpdatedDt;
	}	
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	public String getReqName() {
		return reqName;
	}
	public void setReqName(String reqName) {
		this.reqName = reqName;
	}
	public String getReqDescription() {
		return reqDescription;
	}
	public void setReqDescription(String reqDescription) {
		this.reqDescription = reqDescription;
	}

	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public StoreMaster getStoreMaster() {
		return storeMaster;
	}
	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}	
	
	@PrePersist
	protected void onCreate()
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	
	@PreUpdate
	protected void onUpdate() {
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	
	
	/*@PrePersist
	protected void onCreate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}
	@PreUpdate
	protected void onUpdate() {
		createdBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		lastUpdatedDt = new Timestamp(new Date().getTime());
	}*/
}