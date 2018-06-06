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
 * Vehicles entity. 
 * @author Manjunath Kotagi
 */
@Entity
@Table(name = "VEHICLES")
@NamedQueries({
		@NamedQuery(name = "Vehicles.checkVehiclesExistence", query = "SELECT vehicles FROM Vehicles vehicles WHERE vehicles.vhRegistrationNo= :RegistrationNo"),
		@NamedQuery(name = "Vehicles.UpdateStatus", query = "UPDATE Vehicles vehicles SET  vehicles.status = :Status WHERE vehicles.vhId = :vhId"),
		@NamedQuery(name = "Vehicle.getBasedOnTagNumber", query = "SELECT model FROM Vehicles model WHERE model.vhTagNo = :tagno"),
		@NamedQuery(name = "Vehicle.findAllVehicals", query = "SELECT m.vhId,m.person.firstName,m.person.lastName,m.person.personId,m.vhRegistrationNo,m.vhMake,m.vhModel,"
				+ "m.vhTagNo,m.vhStartDate,m.vhEndDate,m.slotType,m.validSlotsNo,m.createdBy,m.lastUpdatedBy,m.lastUpdatedDt,m.status,"
				+ "m.property.property_No,m.property.propertyId FROM Vehicles m ORDER BY m.vhId DESC"),
				@NamedQuery(name = "Vehicle.findAll", query = "SELECT model FROM Vehicles model ORDER BY model.vhId DESC"),
})
public class Vehicles implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private int vhId;
	private Person person;
	private Property property;
	private String vhRegistrationNo;
	private String vhMake;
	private String vhModel;
	private String vhTagNo;
	private Timestamp vhStartDate;
	private Timestamp vhEndDate;
	private String validSlotsNo;
	private int drGroupId;
	private String status;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private String slotType;

	// Constructors

	/** default constructor */
	public Vehicles() {
	}

	public Vehicles(int vhId, Person person, Property property,
			String vhRegistrationNo, String vhMake, String vhModel,
			String vhTagNo, Timestamp vhStartDate, Timestamp vhEndDate,
			String validSlotsNo, int drGroupId, String status,
			String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt,
			String slotType) {
		super();
		this.vhId = vhId;
		this.person = person;
		this.property = property;
		this.vhRegistrationNo = vhRegistrationNo;
		this.vhMake = vhMake;
		this.vhModel = vhModel;
		this.vhTagNo = vhTagNo;
		this.vhStartDate = vhStartDate;
		this.vhEndDate = vhEndDate;
		this.validSlotsNo = validSlotsNo;
		this.drGroupId = drGroupId;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.slotType = slotType;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "VEHICLES_SEQ", sequenceName = "VEHICLES_SEQ")
	@GeneratedValue(generator = "VEHICLES_SEQ")
	@Column(name = "VH_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getVhId() {
		return this.vhId;
	}

	public void setVhId(int vhId) {
		this.vhId = vhId;
	}
	
	@NotNull(message = "Slot Type Should Not Be Empty")
	@Column(name = "SLOT_TYPE", nullable = false, precision = 25, scale = 0)
	public String getSlotType() {
		return slotType;
	}

	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the property
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROPERTY_ID")
	@NotNull(message = "Property Number Should Not Be Empty")
	public Property getProperty() {
		return property;
	}

	/**
	 * @param property
	 *            the property to set
	 */
	public void setProperty(Property property) {
		this.property = property;
	}

	@Column(name = "VH_REGISTRATION_NO", nullable = false, length = 45)
	@NotNull(message = "Registration Number Should Not Be Empty")
	public String getVhRegistrationNo() {
		return this.vhRegistrationNo;
	}

	public void setVhRegistrationNo(String vhRegistrationNo) {
		this.vhRegistrationNo = vhRegistrationNo;
	}

	@Column(name = "VH_MAKE", length = 100)
	@NotNull(message = "Vehicle Make Should Not Be Empty")
	public String getVhMake() {
		return this.vhMake;
	}

	public void setVhMake(String vhMake) {
		this.vhMake = vhMake;
	}

	@Column(name = "VH_MODEL", nullable = false, length = 100)
	@NotNull(message = "Vehicle Model Should Not Be Empty")
	public String getVhModel() {
		return this.vhModel;
	}

	public void setVhModel(String vhModel) {
		this.vhModel = vhModel;
	}

	@Column(name = "VH_TAG_NO", length = 50)
	@NotNull(message = "Vehicle Tag Should Not Be Empty")
	// @Pattern(regexp = "^[A-Za-z]+[_]+[0-9]+$", message =
	// "Tag Number should Be Pattern of Letters_Digits (Ex:abc_123)")
	public String getVhTagNo() {
		return this.vhTagNo;
	}

	public void setVhTagNo(String vhTagNo) {
		this.vhTagNo = vhTagNo;
	}

	@Column(name = "VH_START_DATE", length = 7)
	@NotNull(message = "Vehicle Start Date Date Should Not Be Empty")
	public Timestamp getVhStartDate() {
		return this.vhStartDate;
	}

	public void setVhStartDate(Timestamp vhStartDate) {
		this.vhStartDate = vhStartDate;
	}

	@Column(name = "VH_END_DATE", length = 7)
	public Timestamp getVhEndDate() {
		return this.vhEndDate;
	}

	public void setVhEndDate(Timestamp vhEndDate) {
		this.vhEndDate = vhEndDate;
	}

	@Column(name = "VALID_SLOTS_NO", nullable = false, length = 100)
	@NotNull(message = "Slot Number Should Not Be Empty")
	// @Pattern(regexp = "^[A-Za-z]+[_]+[0-9]+$", message =
	// "Parking Slot Number should Be Pattern of Letters_Digits (Ex:abc_123)")
	public String getValidSlotsNo() {
		return this.validSlotsNo;
	}

	public void setValidSlotsNo(String validSlotsNo) {
		this.validSlotsNo = validSlotsNo;
	}

	@Column(name = "DR_GROUP_ID", precision = 11, scale = 0)
	public int getDrGroupId() {
		return this.drGroupId;
	}

	public void setDrGroupId(int drGroupId) {
		this.drGroupId = drGroupId;
	}

	@Column(name = "STATUS", nullable = false, length = 20)
	@NotNull(message = "Status Should Not Be Empty")
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

	@Column(name = "LAST_UPDATED_BY", length = 11)
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

}