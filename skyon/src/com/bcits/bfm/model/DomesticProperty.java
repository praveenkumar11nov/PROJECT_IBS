package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;


@SuppressWarnings("serial")
@Entity
@Table(name = "DH_PROPERTY")
@NamedQueries({
	@NamedQuery(name="DomesticProperty.getAllPropertyOnPersonId",query="SELECT dp FROM DomesticProperty dp,Property p, Domestic d where d.domesticId = dp.domasticId and p.propertyId = dp.propertyId and d.personId = :personId"),
	@NamedQuery(name = "DomesticProperty.getDomesticPropertyInstanceById", query = "SELECT dp from DomesticProperty dp where dp.domasticPropertyId=:domesticPropertyId"),
	@NamedQuery(name="DomesticProperty.getProprtyIdBasedOnDomesticPropertyId",query="SELECT dp.propertyId FROM DomesticProperty dp WHERE dp.domasticPropertyId = :domesticPropertyId"),
})


public class DomesticProperty implements java.io.Serializable {
	
	/** Class Fields */
	private int domasticPropertyId;
	private int domasticId;
	//private int propertyId;
	
	private String propertyNo;
	private Property property;
	private int propertyId;
	
	private String workNature;
	private Timestamp startDate;
	private Timestamp endDate;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	
	/** default constructor */
	public DomesticProperty() {
		super();
	}

	/** full constructor */
	public DomesticProperty(int domasticPropertyId, int domasticId,
			int propertyId, String workNature, Timestamp startDate, Timestamp endDate,
			String status, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt) {
		super();
		this.domasticPropertyId = domasticPropertyId;
		this.domasticId = domasticId;
		this.propertyId = propertyId;
		this.workNature = workNature;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	
	/*
	 * Property accessors
	 */
	
	public DomesticProperty(int domasticPropertyId, int domasticId,
			String propertyNo, Property property, int propertyId,
			String workNature, Timestamp startDate, Timestamp endDate, String status,
			String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt) {
		super();
		this.domasticPropertyId = domasticPropertyId;
		this.domasticId = domasticId;
		this.propertyNo = propertyNo;
		this.property = property;
		this.propertyId = propertyId;
		this.workNature = workNature;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Id
	@SequenceGenerator(name="domasticPropertySeq",sequenceName="DOMESTICPROPERTY_SEQ")
	@GeneratedValue(generator="domasticPropertySeq")
	@Column(name = "DH_PROPERTY_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getDomasticPropertyId() {
		return domasticPropertyId;
	}

	public void setDomasticPropertyId(int domasticPropertyId) {
		this.domasticPropertyId = domasticPropertyId;
	}

	@Column(name="DH_ID")
	public int getDomasticId() {
		return domasticId;
	}

	public void setDomasticId(int domasticId) {
		this.domasticId = domasticId;
	}

	/*@Min(value = 1, message = "'Property number is not selected'")
	@Column(name="PROPERTY_ID")
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}*/
	
	@Min(value = 1, message = "Property number is not selected")
	@Column(name="PROPERTY_ID")
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
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

	@Column(name="DH_WORKNATURE")
	public String getWorkNature() {
		return workNature;
	}

	public void setWorkNature(String workNature) {
		this.workNature = workNature;
	}

	@Column(name="DH_START_DATE")
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name="DH_END_DATE")
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
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROPERTY_ID", nullable = false)*/
	@OneToOne
	@JoinColumn(name = "PROPERTY_ID",insertable=false,updatable=false)
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
	
	
}
