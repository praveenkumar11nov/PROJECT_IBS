package com.bcits.bfm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Area", schema = "dbo", catalog = "ContinuumDB")
public class Area implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer objectIdLo;
	private Integer objectIdHi;
	private String uiName;
	private Integer ownerIdHi;
	
	@Id
	@Column(name = "ObjectIdHi")
	public Integer getObjectIdHi() {
		return objectIdHi;
	}

	public void setObjectIdHi(Integer objectIdHi) {
		this.objectIdHi = objectIdHi;
	}

	private Integer ownerIdLo;
	private Integer deviceIdHi;
	private Integer deviceIdLo;
	private Boolean templateFlag;
	private Integer templateIdHi;
	private Integer templateIdLo;
	private String controllerName;
	private Short alarmGraphicPage;
	private Short deletePending;
	private String description;
	private Integer knownOccupCount;
	private Short state;
	private Short forceLock;

	

	@Column(name = "uiName", length = 128)
	public String getUiName() {
		return this.uiName;
	}
	
	@Id
	@Column(name = "ObjectIdLo")
	public Integer getObjectIdLo() {
		return objectIdLo;
	}

	public void setObjectIdLo(Integer objectIdLo) {
		this.objectIdLo = objectIdLo;
	}

	public void setUiName(String uiName) {
		this.uiName = uiName;
	}

	@Column(name = "OwnerIdHi")
	public Integer getOwnerIdHi() {
		return this.ownerIdHi;
	}

	public void setOwnerIdHi(Integer ownerIdHi) {
		this.ownerIdHi = ownerIdHi;
	}

	@Column(name = "OwnerIdLo")
	public Integer getOwnerIdLo() {
		return this.ownerIdLo;
	}

	public void setOwnerIdLo(Integer ownerIdLo) {
		this.ownerIdLo = ownerIdLo;
	}

	@Column(name = "DeviceIdHi")
	public Integer getDeviceIdHi() {
		return this.deviceIdHi;
	}

	public void setDeviceIdHi(Integer deviceIdHi) {
		this.deviceIdHi = deviceIdHi;
	}

	@Column(name = "DeviceIdLo")
	public Integer getDeviceIdLo() {
		return this.deviceIdLo;
	}

	public void setDeviceIdLo(Integer deviceIdLo) {
		this.deviceIdLo = deviceIdLo;
	}

	@Column(name = "TemplateFlag")
	public Boolean getTemplateFlag() {
		return this.templateFlag;
	}

	public void setTemplateFlag(Boolean templateFlag) {
		this.templateFlag = templateFlag;
	}

	@Column(name = "TemplateIdHi")
	public Integer getTemplateIdHi() {
		return this.templateIdHi;
	}

	public void setTemplateIdHi(Integer templateIdHi) {
		this.templateIdHi = templateIdHi;
	}

	@Column(name = "TemplateIdLo")
	public Integer getTemplateIdLo() {
		return this.templateIdLo;
	}

	public void setTemplateIdLo(Integer templateIdLo) {
		this.templateIdLo = templateIdLo;
	}

	@Column(name = "ControllerName", length = 16)
	public String getControllerName() {
		return this.controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	@Column(name = "AlarmGraphicPage")
	public Short getAlarmGraphicPage() {
		return this.alarmGraphicPage;
	}

	public void setAlarmGraphicPage(Short alarmGraphicPage) {
		this.alarmGraphicPage = alarmGraphicPage;
	}

	@Column(name = "DeletePending")
	public Short getDeletePending() {
		return this.deletePending;
	}

	public void setDeletePending(Short deletePending) {
		this.deletePending = deletePending;
	}

	@Column(name = "Description", length = 32)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "KnownOccupCount")
	public Integer getKnownOccupCount() {
		return this.knownOccupCount;
	}

	public void setKnownOccupCount(Integer knownOccupCount) {
		this.knownOccupCount = knownOccupCount;
	}

	@Column(name = "State")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(name = "ForceLock")
	public Short getForceLock() {
		return this.forceLock;
	}

	public void setForceLock(Short forceLock) {
		this.forceLock = forceLock;
	}

}