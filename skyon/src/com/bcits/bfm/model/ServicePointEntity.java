package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="SERVICE_POINT")  
@NamedQueries({
	@NamedQuery(name = "ServicePointEntity.findAll", query = "SELECT el FROM ServicePointEntity el WHERE srId=:srId ORDER BY el.servicePointId DESC"),
	@NamedQuery(name = "ServicePointEntity.setServicePointStatus", query = "UPDATE ServicePointEntity el SET el.status = :status WHERE el.servicePointId = :servicePointId"),
	@NamedQuery(name = "ServicePointEntity.getAllPropertyNumbers", query = "SELECT DISTINCT(pt.property_No) FROM ServicePointEntity sp, Property pt WHERE sp.propertyId = pt.propertyId"),
	@NamedQuery(name = "ServicePointEntity.getAllBlockNames", query = "SELECT DISTINCT(b.blockName) FROM ServicePointEntity sp, Property pt, Blocks b WHERE sp.propertyId = pt.propertyId AND pt.blockId = b.blockId"),
    @NamedQuery(name="ServicePointInstructionsEntity.getonSrId",query="SELECT e FROM ServicePointEntity e WHERE e.srId=:srId"),
    @NamedQuery(name = "ServicePointEntity.findAllServiceTypes", query = "SELECT sp.servicePointId,sp.typeOfService FROM ServicePointEntity sp ORDER BY sp.servicePointId DESC")

})
public class ServicePointEntity {

	@Id
	@Column(name="SP_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "servicePoint_seq", sequenceName = "SERVICE_POINT_SEQ") 
	@GeneratedValue(generator = "servicePoint_seq") 
	private int servicePointId;
	
	@Column(name="PROPERTY_ID", unique=true, nullable=false, precision=11, scale=0)
	private int propertyId;
	
   @Column(name="SR_ID")
	private Integer srId;
   
   public Integer getSrId() {
	return srId;
}

public void setSrId(Integer srId) {
	this.srId = srId;
}

public ServiceRoute getServiceRoute() {
	return serviceRoute;
}

public void setServiceRoute(ServiceRoute serviceRoute) {
	this.serviceRoute = serviceRoute;
}

@ManyToOne
	@JoinColumn(name="SR_ID",insertable = false, updatable = false, nullable = false)
   private ServiceRoute serviceRoute;
	
	@OneToOne	 
	@JoinColumn(name = "PROPERTY_ID", insertable = false, updatable = false, nullable = false)
    private Property propertyObj;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfService;
	
	@Column(name="SERVICE_POINT_NAME")
	private String servicePointName;
	
	@Column(name="SERVICE_METERED")
	private String serviceMetered;
	
	@Column(name="SP_COMMISSION_DATE")
	private Date commissionDate;
	
	@Column(name="SP_DECOMMISSION_DATE")
	private Date deCommissionDate;
	
	@Column(name="SP_LOCATION")
	private String serviceLocation;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Service Point Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;

	public int getServicePointId() {
		return servicePointId;
	}

	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public String getServiceMetered() {
		return serviceMetered;
	}

	public void setServiceMetered(String serviceMetered) {
		this.serviceMetered = serviceMetered;
	}

	public Date getCommissionDate() {
		return commissionDate;
	}

	public void setCommissionDate(Date commissionDate) {
		this.commissionDate = commissionDate;
	}

	public Date getDeCommissionDate() {
		return deCommissionDate;
	}

	public void setDeCommissionDate(Date deCommissionDate) {
		this.deCommissionDate = deCommissionDate;
	}

	public String getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
		
	public Property getPropertyObj() {
		return propertyObj;
	}

	public void setPropertyObj(Property propertyObj) {
		this.propertyObj = propertyObj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}

	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}
	
	public String getServicePointName() {
		return servicePointName;
	}

	public void setServicePointName(String servicePointName) {
		this.servicePointName = servicePointName;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }
}
