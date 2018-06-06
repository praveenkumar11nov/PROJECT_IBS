package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 */
@Entity
@Table(name = "ADDRESS")
@NamedQueries({ 	
	@NamedQuery(name = "Address.findAllAddressesBasedOnPersonID", query = "SELECT a.addressId, a.personId, a.addressLocation, a.addressPrimary, a.addressNo, a.address1, a.address2, a.address3, a.pincode, a.addressContactId, a.addressSeason, a.addressSeasonFrom, a.addressSeasonTo, a.createdBy, a.lastUpdatedBy, a.lastUpdatedDt, c.cityId, c.cityName, s.stateId, s.stateName, co.countryId, co.countryName FROM Address a,City c,Country co,State s WHERE a.personId = :personId and c.cityId = a.cityId and s.stateId = c.stateId and s.countryId = co.countryId ORDER BY a.addressId DESC"),
	@NamedQuery(name = "Address.getAddressIdBasedOnCreatedByAndLastUpdatedDt", query = "SELECT a.addressId FROM Address a WHERE a.createdBy = :createdBy AND a.lastUpdatedDt = :lastUpdatedDt"),
	@NamedQuery(name = "Address.updateContactId", query = "UPDATE Address a SET a.addressContactId =:contactId WHERE a.addressId =:addressId"),
	@NamedQuery(name = "Address.checkForUniquePrimary", query = "SELECT a.addressId From Address a WHERE a.personId = :personId and a.addressPrimary =:addrPrimary"),
	@NamedQuery(name = "Address.getAddressPrimary", query = "SELECT a.addressPrimary From Address a WHERE a.addressId = :addressId")
})
public class Address {

	// Fields    

	private int addressId;
	private int personId;
	private String addressLocation;
	private String addressPrimary;
	private String addressNo;
	private String address1;
	private String address2;
	private String address3;
	private int cityId;
	private int pincode;
	private int addressContactId;
	private String addressSeason;
	private Timestamp addressSeasonFrom;
	private Timestamp addressSeasonTo;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;

	/** default constructor */
	public Address() {
	}

	// Property accessors
	@Id 
	@SequenceGenerator(name = "addressSeq", sequenceName = "ADDRESS_SEQ")
	@GeneratedValue(generator = "addressSeq")
	@Column(name="ADDRESS_ID")
	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}  

	@NotEmpty(message="'Location' is not selected")
	@Column(name="ADDRESS_LOCATION")
	public String getAddressLocation() {
		return this.addressLocation;
	}

	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

	@NotEmpty(message="'primary' is not selected")
	@Column(name="ADDRESS_PRIMARY")
	public String getAddressPrimary() {
		return this.addressPrimary;
	}

	public void setAddressPrimary(String addressPrimary) {
		this.addressPrimary = addressPrimary;
	}


	@NotNull(message="Property Number is Required")
	@Size(min = 1, max = 45, message = "Property Number must have minimum {min} letter and maximum {max} letters")
	@Column(name="ADDRESS_NO")
	public String getAddressNo()
	{
		return addressNo;
	}

	public void setAddressNo(String addressNo)
	{
		this.addressNo = addressNo;
	}


	@Size(min = 1, max = 500, message = "Address 1 must have minimum {min} letter and maximum {max} letters")
	@Column(name="ADDRESS1")
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Size(min = 0, max = 300, message = "Address 2 can have maximum {max} letters")
	@Column(name="ADDRESS2")
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Size(min = 0, max = 300, message = "Address 3 can have maximum {max} letters")
	@Column(name="ADDRESS3")
	public String getAddress3() {
		return this.address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	@Min(value=1, message = "'City' not Selected")
	@Column(name="CITY_ID")
	public int getCityId()
	{
		return cityId;
	}

	public void setCityId(int cityId)
	{
		this.cityId = cityId;
	}


	
	//@Pattern(regexp = "^[0-9]{6}|[0-9]{3}[0-9]{3})$", message = "Invalid Pincode format")
	@Min(value=6, message = "Pincode must have minimum 6 letters")
	@Column(name="PINCODE")
	public int getPincode() {
		return this.pincode;
	}  


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	@Column(name="ADDRESS_CONTACT_ID")
	public int getAddressContactId() {
		return this.addressContactId;
	}

	public void setAddressContactId(int addressContactId) {
		this.addressContactId = addressContactId;
	}

	@Column(name="ADDRESS_SEASON")
	public String getAddressSeason() {
		return this.addressSeason;
	}

	public void setAddressSeason(String addressSeason) {
		this.addressSeason = addressSeason;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@Column(name="PERSON_ID", insertable=false, updatable=false)
	public int getPersonId()
	{
		return personId;
	}

	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	@Column(name="ADDRESS_SEASON_FROM")
	public Timestamp getAddressSeasonFrom()
	{
		return addressSeasonFrom;
	}

	public void setAddressSeasonFrom(Timestamp addressSeasonFrom)
	{
		this.addressSeasonFrom = addressSeasonFrom;
	}

	@Column(name="ADDRESS_SEASON_TO")
	public Timestamp getAddressSeasonTo()
	{
		return addressSeasonTo;
	}

	public void setAddressSeasonTo(Timestamp addressSeasonTo)
	{
		this.addressSeasonTo = addressSeasonTo;
	}

	/*@OneToOne
		@JoinColumn(name = "CITY_ID", insertable = false, updatable = false, nullable = false)
		private City city;



	   // Constructors  

	    public City getCity()
		{
			return city;
		}

		public void setCity(City city)
		{
			this.city = city;
		}*/

	@Override
	public int hashCode(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.address1);
		buffer.append(this.address2);
		buffer.append(this.address3);
		buffer.append(this.addressContactId);
		buffer.append(this.addressId);
		buffer.append(this.addressLocation);
		buffer.append(this.addressPrimary);
		buffer.append(this.addressSeason);
		buffer.append(this.cityId);
		buffer.append(this.createdBy);
		buffer.append(this.lastUpdatedBy);
		buffer.append(this.lastUpdatedDt);
		return buffer.toString().hashCode();
	}

	@Override
	public boolean equals(Object object){
		if (object == null) return false;
		if (object == this) return true;
		if (this.getClass() != object.getClass())return false;
		Address address = (Address)object;
		if(this.hashCode()== address.hashCode())return true;
		return false;
	}

}