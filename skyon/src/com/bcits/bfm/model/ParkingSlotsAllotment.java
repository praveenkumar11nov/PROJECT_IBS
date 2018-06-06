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

/**
 * ParkingSlotsAllotment entity. 
 * @author Manjunath Kotagi
 */
@Entity
@Table(name = "PARKING_SLOTS_ALLOTMENT")
@NamedQueries({
		@NamedQuery(name = "ParkingSlotAllocation.UpdateStatus", query = "UPDATE ParkingSlotsAllotment psa SET  psa.status = :psaStatus WHERE psa.psaId = :psaId"),
		@NamedQuery(name = "ParkingSlotAllocation.UpdateStatusforAll", query = "SELECT psa FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots = :psId AND psa.status='true'"),
		@NamedQuery(name = "ParkingSlotAllocation.getStatusFromParkingSlot", query = "SELECT psa.property.property_No FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots.psId = :psId AND psa.status='true'"),
		@NamedQuery(name = "ParkingSlotAllocation.deleteParkingSlotsAllotment", query = "DELETE FROM ParkingSlotsAllotment t WHERE t.psaId=:psaId"),
		
		
		@NamedQuery(name = "ParkingSlotAllocation.checkParkingSlotExistence", query = "SELECT psa FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots= :slotNumber"),
		@NamedQuery(name = "ParkingSlotAllocation.checkParkingSlotStatus", query = "SELECT psa.status FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots= :slotNumber"),
		@NamedQuery(name = "ParkingSlotAllocation.getSlotStatus", query = "SELECT psa.status FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots= :psID"),
		@NamedQuery(name = "ParkingSlotAllocation.getExistingSlot", query = "SELECT psa FROM ParkingSlotsAllotment psa WHERE psa.parkingSlots= :psId"),
		@NamedQuery(name = "ParkingSlotAllotment.getPropertyIds", query = "SELECT psa.property FROM ParkingSlotsAllotment psa WHERE psa.status= :status"),
		@NamedQuery(name = "ParkingSlotsAllotment.findAll", query = "select model from ParkingSlotsAllotment model ORDER BY model.psaId DESC"),
		@NamedQuery(name = "ParkingSlotsAllotment.setAllocationStatusInParkingSlot", query = "SELECT PSA.parkingSlots FROM ParkingSlotsAllotment PSA WHERE PSA.psaId=:psaId"),
		@NamedQuery(name = "ParkingSlotsAllotment.findAllParkingSlot", query = "select m.psaId,m.parkingSlots.psSlotNo,m.parkingSlots.psId,m.property.property_No,m.property.propertyId,"
				+ "m.allotmentDateFrom,m.allotmentDateTo,m.psRent,m.psRentLastRevised,m.psRentLastRaised,m.createdBy,m.lastUpdatedBy,m.status,m.blocks.blockName,"
				+ "m.blocks.blockId from ParkingSlotsAllotment m ORDER BY m.psaId DESC"),
		@NamedQuery(name = "ParkingSlotsAllotment.findParkingSlotsAllotment", query = "SELECT psa FROM ParkingSlotsAllotment psa WHERE psa.property.propertyId= :propertyId"),
		@NamedQuery(name = "ParkingSlotsAllotment.findAllParkingAllocationSlot", query = "select m from ParkingSlotsAllotment m ORDER BY m.psaId DESC")

				

})
public class ParkingSlotsAllotment implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;

	private int psaId;
	private Blocks blocks;
	private Property property;
	private ParkingSlots parkingSlots;
	// private Owner owner;
	private Timestamp allotmentDateFrom;
	private Timestamp allotmentDateTo;
	private int psRent;
	private Timestamp psRentLastRevised;
	private Timestamp psRentLastRaised;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	// Constructors

	/** default constructor */
	public ParkingSlotsAllotment() {
	}

	/** full constructor */
	public ParkingSlotsAllotment(int psaId, Property property,
			ParkingSlots parkingSlots, Timestamp allotmentDateFrom,
			Timestamp allotmentDateTo, int psRent, Timestamp psRentLastRevised,
			Timestamp psRentLastRaised, String status, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt, Blocks blocks) {
		this.psaId = psaId;
		this.property = property;
		this.parkingSlots = parkingSlots;
		// this.owner = owner;
		this.allotmentDateFrom = allotmentDateFrom;
		this.allotmentDateTo = allotmentDateTo;
		this.psRent = psRent;
		this.psRentLastRevised = psRentLastRevised;
		this.psRentLastRaised = psRentLastRaised;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.blocks = blocks;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "PARKING_SLOTS_ALLOCATION_SEQ", sequenceName = "PARKING_SLOTS_ALLOCATION_SEQ")
	@GeneratedValue(generator = "PARKING_SLOTS_ALLOCATION_SEQ")
	@Column(name = "PSA_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getPsaId() {
		return this.psaId;
	}

	public void setPsaId(int psaId) {
		this.psaId = psaId;
	}

	// @NotNull(message="Property Should Not Be Empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROPERTY_ID", nullable = false)
	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@NotNull(message = "Parking Slots Should Not Be Empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PS_ID", nullable = false)
	public ParkingSlots getParkingSlots() {
		return this.parkingSlots;
	}

	public void setParkingSlots(ParkingSlots parkingSlots) {
		this.parkingSlots = parkingSlots;
	}

	@Column(name = "ALLOTMENT_DATE_FROM", length = 7)
	@NotNull(message = "Allocation From-Date Should Not Be Empty")
	public Timestamp getAllotmentDateFrom() {
		return this.allotmentDateFrom;
	}

	public void setAllotmentDateFrom(Timestamp allotmentDateFrom) {
		this.allotmentDateFrom = allotmentDateFrom;
	}

	@Column(name = "ALLOTMENT_DATE_TO", length = 7)
	public Timestamp getAllotmentDateTo() {
		return this.allotmentDateTo;
	}

	public void setAllotmentDateTo(Timestamp allotmentDateTo) {
		this.allotmentDateTo = allotmentDateTo;
	}

	@Column(name = "PS_RENT", precision = 10, scale = 0)
	public int getPsRent() {
		return this.psRent;
	}

	public void setPsRent(int psRent) {
		this.psRent = psRent;
	}

	@Column(name = "PS_RENT_LAST_REVISED", length = 7)
	public Timestamp getPsRentLastRevised() {
		return this.psRentLastRevised;
	}

	public void setPsRentLastRevised(Timestamp psRentLastRevised) {
		this.psRentLastRevised = psRentLastRevised;
	}

	@Column(name = "PS_RENT_LAST_RAISED", length = 7)
	public Timestamp getPsRentLastRaised() {
		return this.psRentLastRaised;
	}

	public void setPsRentLastRaised(Timestamp psRentLastRaised) {
		this.psRentLastRaised = psRentLastRaised;
	}

	@Column(name = "STATUS", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@NotNull(message = "Block Name Should Not Be Empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BLOCK_ID", nullable = false)
	public Blocks getBlocks() {
		return blocks;
	}

	public void setBlocks(Blocks blocks) {
		this.blocks = blocks;
	}

}