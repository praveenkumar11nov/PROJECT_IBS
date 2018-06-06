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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="SERVICE_POINT_EQUIPMENTS")  
@NamedQueries({
	@NamedQuery(name = "ServicePointEquipmentsEntity.findAllById", query = "SELECT bli FROM ServicePointEquipmentsEntity bli WHERE bli.servicePointEntity.servicePointId = :servicePointId "),
	@NamedQuery(name = "ServicePointEquipmentsEntity.findAll", query = "SELECT el FROM ServicePointEquipmentsEntity el ORDER BY el.spEquipmentId DESC"),
	@NamedQuery(name = "ServicePointEquipmentsEntity.setServicePointStatus", query = "UPDATE ServicePointEquipmentsEntity el SET el.status = :status WHERE el.spEquipmentId = :servicePointId"),
	@NamedQuery(name = "ServicePointEquipmentsEntity.getAllEquipmentTypes", query = "SELECT DISTINCT(p.equipmentType) FROM ServicePointEquipmentsEntity p"),
	@NamedQuery(name="ServicePointEquipmentsEntity.updateEquipmentStatusFromInnerGrid",query="UPDATE ServicePointEquipmentsEntity a SET a.status = :status WHERE a.spEquipmentId = :spEquipmentId")
})
public class ServicePointEquipmentsEntity {

	@Id
	@Column(name="SPE_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "spEquipments_seq", sequenceName = "SP_EQUIPMENTS_SEQ") 
	@GeneratedValue(generator = "spEquipments_seq")
	private int spEquipmentId;
	
	/*@Column(name="SP_ID")
	@NotNull(message = "Service Point Id Should Not Be Empty")
	private int servicePointId;*/
	
	@ManyToOne
    @JoinColumn(name = "SP_ID")
	private ServicePointEntity servicePointEntity;
	
	@Column(name="SPE_TYPE")
	private String equipmentType;
	
	@Column(name="SPE_COUNT")
	private int equipmentCount;
	
	@Column(name="INSTALL_DATE")
	private Date installDate;
	
	@Column(name="REMOVAL_DATE")
	private Date removalDate;
	
	@Column(name="COMMENTS")
	private  String comments;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Equipment Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());

	public int getSpEquipmentId() {
		return spEquipmentId;
	}

	public void setSpEquipmentId(int spEquipmentId) {
		this.spEquipmentId = spEquipmentId;
	}

	public ServicePointEntity getServicePointEntity() {
		return servicePointEntity;
	}

	public void setServicePointEntity(ServicePointEntity servicePointEntity) {
		this.servicePointEntity = servicePointEntity;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public int getEquipmentCount() {
		return equipmentCount;
	}

	public void setEquipmentCount(int equipmentCount) {
		this.equipmentCount = equipmentCount;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public Date getRemovalDate() {
		return removalDate;
	}

	public void setRemovalDate(Date removalDate) {
		this.removalDate = removalDate;
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
	
	/*public int getServicePointId() {
		return servicePointId;
	}

	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}*/	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
