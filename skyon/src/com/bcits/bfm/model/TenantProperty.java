package com.bcits.bfm.model;

import java.sql.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

@Entity
@Table(name = "TENANT_PROPERTY")

@NamedQueries({
	@NamedQuery(name="TenantProperty.getAllPropertyOnPersonId",query="SELECT tp FROM TenantProperty tp,Property p,Tenant t WHERE t.tenantId = tp.tenantId and p.propertyId = tp.propertyId and t.personId = :personId"),
	@NamedQuery(name="TenantProperty.getTenantPropertyBasedOnId", query="SELECT tp FROM TenantProperty tp WHERE tp.tenantPropertyId= :tenantPropertyId "),
	@NamedQuery(name="tenProperty.getProprtyIdBasedOntenPropertyId",query="SELECT tp.propertyId FROM TenantProperty tp WHERE tp.tenantPropertyId = :tenId"),
	@NamedQuery(name="TenantProperty.findTenantPropertyBasedOnProperty",query="select model from TenantProperty model where model.propertyId=:property"),
	
})

public class TenantProperty {
	
	/** Class Fields */
	private int tenantPropertyId;
	private Tenant tenantId;

	private String propertyNo;
	
	private Property property;
	
	private int propertyId;
	
	private int groupId;
	private Timestamp startDate;
	private Timestamp endDate;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
/*--------added 4 upload exel-----*/
	private String visitorRegReq;
	private String residential;
	private String primaryOwner;
/*--------------------------------*/
	
	/** default constructor */
	public TenantProperty() {
		super();
	}

	/** full constructor */
	
	
	/*
	 * Property accessors
	 */
	
	@Id
	@SequenceGenerator(name="tenantPropertySeq",sequenceName="TENANTPROPERTY_SEQ")
	@GeneratedValue(generator="tenantPropertySeq")
	@Column(name = "TENANT_PROPERTY_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getTenantPropertyId() {
		return tenantPropertyId;
	}
	public TenantProperty(int tenantPropertyId, Tenant tenantId,
			String propertyNo, Property property, int propertyId, int groupId,
			Timestamp startDate, Timestamp endDate, String status, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		super();
		this.tenantPropertyId = tenantPropertyId;
		this.tenantId = tenantId;
		this.propertyNo = propertyNo;
		this.property = property;
		this.propertyId = propertyId;
		this.groupId = groupId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}
	@OneToOne
	@JoinColumn(name = "PROPERTY_ID",insertable=false,updatable=false)
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	
	@Min(value = 1, message = "Property number is not selected")
	@Column(name="PROPERTY_ID")
	public int getPropertyId() {
		return propertyId;
	}

	public void setTenantPropertyId(int tenantPropertyId) {
		this.tenantPropertyId = tenantPropertyId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TENANT_ID")	
	public Tenant getTenantId() {
		return tenantId;
	}

	public void setTenantId(Tenant tenantId) {
		this.tenantId = tenantId;
	}
	
	@Transient
	public String getPropertyNo()
	{
		return propertyNo;
	}
	
	public void setPropertyNo(String propertyNo)
	{
		this.propertyNo = propertyNo;
	}

	@Column(name="DR_GROUP_ID")
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name="TENANCY_START_DATE")
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name="TENANCY_END_DATE")
	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name="LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}
	
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
/*------------------------------------- added while doing upload functionality ----------------------*/
	@Column(name = "VISITOR_REG_REQ")
	public String getVisitorRegReq() {
		return this.visitorRegReq;
	}

	public void setVisitorRegReq(String visitorRegReq) {
		this.visitorRegReq = visitorRegReq;
	}
	
	@Column(name = "RESIDENTIAL")
	public String getResidential() {
		return residential;
	}

	public void setResidential(String residential) {
		this.residential = residential;
	}
	
	@Column(name = "PRIMARY_OWNER", length = 1)
	public String getPrimaryOwner() {
		return primaryOwner;
	}

	public void setPrimaryOwner(String primaryOwner) {
		this.primaryOwner = primaryOwner;
	}
/*----------------------------------------------------------------------------------------------------*/	
	@Override
	public int hashCode(){
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(this.tenantPropertyId);
	    buffer.append(this.tenantId);
	    buffer.append(this.propertyNo);
	    buffer.append(this.propertyId);
	    buffer.append(this.groupId);
	    buffer.append(this.startDate);
	    buffer.append(this.endDate);
	    buffer.append(this.status);
	    buffer.append(this.createdBy);
	    buffer.append(this.lastUpdatedBy);
	    
	    return buffer.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass())return false;
	    TenantProperty tenantProperty = (TenantProperty)object;
	    if(this.hashCode()== tenantProperty.hashCode())return true;
	   return false;
	}
	
	

}
