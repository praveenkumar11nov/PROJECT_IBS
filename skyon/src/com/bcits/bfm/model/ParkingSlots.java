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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * ParkingSlots entity. 
 * @author Manjunath Kotagi
 */
@Entity
@Table(name = "PARKING_SLOTS")
@NamedQueries({
		@NamedQuery(name = "ParkingSlot.UpdateStatus", query = "UPDATE ParkingSlots ps SET  ps.status = :psStatus WHERE ps.psId = :psId"),
		@NamedQuery(name = "ParkingSlot.getAll", query = "SELECT ps FROM ParkingSlots ps WHERE ps.status='true' AND ps.allocation_status='false' AND ps.psOwnership!='VISITORS'"),
		@NamedQuery(name = "ParkingSlotAllocation.setAllocationSlotStatus", query = "UPDATE ParkingSlots ps SET allocation_status=:status WHERE psId=:psId"),
		//@NamedQuery(name = "ParkingSlot.readNestedReadUrl", query = "SELECT psa FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots=:psId"),
		@NamedQuery(name = "ParkingSlot.readNestedReadUrl", query = "SELECT psa.property.property_No,psa.allotmentDateFrom,psa.allotmentDateTo,psa.psRent,psa.status FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots=:psId"),
		@NamedQuery(name = "ParkingSlots.readAll", query = "SELECT model FROM ParkingSlots model ORDER BY model.psId DESC"),
		@NamedQuery(name = "ParkingSlots.parkingDetails", query = "SELECT count(*),(select count(*) from ParkingSlots p Where p.allocation_status='true'),(select count(*) from ParkingSlots p1 Where p1.allocation_status='false') FROM ParkingSlots model group by 1"),

})
public class ParkingSlots implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private int psId;
	private String psSlotNo;
	private Timestamp psActiveDate;
	private String psOwnership;
	private String psParkingMethod;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String allocation_status;
	private Blocks blockId;

	/** default constructor */
	public ParkingSlots() {
	}

	/** full constructor */
	public ParkingSlots(int psId, String psSlotNo, Timestamp psActiveDate,
			String psOwnership, String psParkingMethod, String status,
			String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt,
			Blocks blockId) {
		this.psId = psId;
		this.psSlotNo = psSlotNo;
		this.psActiveDate = psActiveDate;
		this.psOwnership = psOwnership;
		this.psParkingMethod = psParkingMethod;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.blockId = blockId;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "PARKING_SLOTS_SEQ", sequenceName = "PARKING_SLOTS_SEQ")
	@GeneratedValue(generator = "PARKING_SLOTS_SEQ")
	@Column(name = "PS_ID", unique = true, nullable = false, precision = 5, scale = 0)
	public int getPsId() {
		return this.psId;
	}

	public void setPsId(int psId) {
		this.psId = psId;
	}

	@Column(name = "PS_SLOT_NO", unique = true)
	@NotEmpty(message = "Parking Slot Sholud Not Be Empty")
	@Size(max = 50, message = "Parking Slot Must Be Max 50 Length Character")
	public String getPsSlotNo() {
		return this.psSlotNo;
	}

	public void setPsSlotNo(String psSlotNo) {
		this.psSlotNo = psSlotNo;
	}

	@NotNull(message = "Active Date Should Not Be Empty")
	@Column(name = "PS_ACTIVE_DATE", nullable = false, length = 7)
	public Timestamp getPsActiveDate() {
		return this.psActiveDate;
	}

	public void setPsActiveDate(Timestamp psActiveDate) {
		this.psActiveDate = psActiveDate;
	}

	@Column(name = "PS_OWNERSHIP", nullable = false, length = 20)
	@NotNull(message = "OwnerShip Should Not Be Empty")
	public String getPsOwnership() {
		return this.psOwnership;
	}

	public void setPsOwnership(String psOwnership) {
		this.psOwnership = psOwnership;
	}

	@Column(name = "PS_PARKING_METHOD", nullable = true, length = 20)
	@NotEmpty(message = "Parking Method Sholud Not Be Empty")
	public String getPsParkingMethod() {
		return this.psParkingMethod;
	}

	public void setPsParkingMethod(String psParkingMethod) {
		this.psParkingMethod = psParkingMethod;
	}

	@Column(name = "STATUS", nullable = false, length = 20)
	@NotEmpty(message = "Parking Slot Status Sholud Not Be Empty")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 20)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", nullable = false, length = 20)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", nullable = false)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Column(name = "ALLOCATION_STATUS")
	@NotEmpty(message = "Parking Slot Allocation Status Sholud Not Be Empty")
	public String getAllocation_status() {
		return allocation_status;
	}

	public void setAllocation_status(String allocation_status) {
		this.allocation_status = allocation_status;
	}

	@NotNull(message = "Block Name Should Not Be Empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BLOCK_ID", nullable = false)
	public Blocks getBlockId() {
		return blockId;
	}

	public void setBlockId(Blocks blockId) {
		this.blockId = blockId;
	}
}