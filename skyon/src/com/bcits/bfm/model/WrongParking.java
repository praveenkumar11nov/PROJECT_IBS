package com.bcits.bfm.model;

import java.sql.Blob;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * WrongParking entity. 
 * @author Manjunath Kotagi
 */
@Entity
@Table(name = "WRONG_PARKING")
@NamedQueries({
		@NamedQuery(name = "WrongParking.checkVehiclesExistence", query = "SELECT vehicles.vhId FROM Vehicles vehicles WHERE vehicles.vhRegistrationNo= :RegNo"),
		@NamedQuery(name = "WrongParking.getTagNumbers", query = "SELECT vehicles.vhTagNo FROM Vehicles vehicles WHERE vehicles.vhRegistrationNo= :RegNo"),
		@NamedQuery(name = "WrongParking.getContacts", query = "SELECT model FROM Contact model WHERE model.personId= :personId"),
		@NamedQuery(name = "WrongParking.UpdateStatus", query = "UPDATE WrongParking wp SET  wp.status = :wpStatus WHERE wp.wpId = :wpId"),
		@NamedQuery(name = "WrongParking.getRegistrationNumber", query = "SELECT model FROM WrongParking model WHERE  model.vehicleNo = :vehicleNo"),
		@NamedQuery(name = "WrongParking.readWrongParkingImage", query = "SELECT model FROM WrongParking model WHERE model.wpId=:wpId"),
		@NamedQuery(name="WrongParking.updateImage", query="Update WrongParking w SET w.image=:image WHERE w.wpId=:wpId"),
		@NamedQuery(name="WrongParking.update", query="SELECT w FROM WrongParking w"),
		@NamedQuery(name="WrongParking.findAll", query="select model from WrongParking model ORDER BY model.wpId DESC"),
		@NamedQuery(name="WrongParking.findAllList", query="SELECT wp.wpId,wp.vehicleNo,wp.vhTagNo,wp.psSlotNo,wp.wpDt,wp.actionTaken,wp.createdBy,wp.lastUpdatedBy,wp.lastUpdatedDt,wp.status FROM WrongParking wp ORDER BY wp.wpId DESC"),

		
})
public class WrongParking implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private int wpId;
	private Vehicles vehicles;
	private String psSlotNo;
	private Timestamp wpDt;
	private String vehicleNo;
	private String actionTaken;
	private String vhTagNo;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String status;
	private Blob image;
	// Constructors
	@Column(name="IMAGE")
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	/** default constructor */
	public WrongParking() {
	}

	/** full constructor */
	public WrongParking(int wpId, Vehicles vehicles, String psSlotNo,
			Timestamp wpDt, String vehicleNo, String actionTaken,
			String vhTagNo, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDt, String status) {
		super();
		this.wpId = wpId;
		this.vehicles = vehicles;
		this.psSlotNo = psSlotNo;
		this.wpDt = wpDt;
		this.vehicleNo = vehicleNo;
		this.actionTaken = actionTaken;
		this.vhTagNo = vhTagNo;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.status = status;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "WRONG_PARKING_SEQ", sequenceName = "WRONG_PARKING_SEQ")
	@GeneratedValue(generator = "WRONG_PARKING_SEQ")
	@Column(name = "WP_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getWpId() {
		return this.wpId;
	}

	public void setWpId(int wpId) {
		this.wpId = wpId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VH_ID", nullable = false)
	public Vehicles getVehicles() {
		return this.vehicles;
	}

	public void setVehicles(Vehicles vehicles) {
		this.vehicles = vehicles;
	}

	@Column(name = "PS_SLOT_NO", length = 45)
	@NotNull(message = "Parking Slot Should Not Be Empty")
	@Size(min = 2, max = 45, message = "Parking Slot Must Be 2 to 45 Length Character")
	public String getPsSlotNo() {
		return this.psSlotNo;
	}

	public void setPsSlotNo(String psSlotNo) {
		this.psSlotNo = psSlotNo;
	}

	@Column(name = "WP_DT", nullable = false, length = 7)
	public Timestamp getwpDt() {
		return this.wpDt;
	}

	public void setwpDt(Timestamp wpDt) {
		this.wpDt = wpDt;
	}

	@Column(name = "VEHICLE_NO", nullable = false, length = 45)
	@NotNull(message = "Vehicle Reg Number Should Not Be Empty")
	@Size(min = 2, max = 45, message = "Vehicle Reg Number Must Be 2 to 15 Length Character")
	public String getVehicleNo() {
		return this.vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@Column(name = "ACTION_TAKEN", length = 225)
	public String getActionTaken() {
		return this.actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	@Column(name = "VH_TAG_NO", length = 45)
	@NotNull(message = "Vehicle Tag Number Should Not Be Empty")
	@Size(min = 2, max = 45, message = "Vehicle Tag Number Must Be 2 to 45 Length Character")
	public String getVhTagNo() {
		return this.vhTagNo;
	}

	public void setVhTagNo(String vhTagNo) {
		this.vhTagNo = vhTagNo;
	}

	@Column(name = "CREATED_BY", length = 20)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 20)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Column(name = "STATUS", nullable = false, length = 20)
	@NotNull(message = "Status Should Not Be Empty")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}