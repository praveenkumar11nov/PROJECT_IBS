package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "VENDORS")
@NamedQueries({
	@NamedQuery(name = "Vendors.getVendorInstanceBasedOnVendorId", query = "SELECT v from Vendors v WHERE v.vendorId = :vendorId"),
	@NamedQuery(name = "Vendors.findAllVendorBasedOnPersonID", query = "SELECT v from Vendors v WHERE v.personId=:personId"),
	@NamedQuery(name="Vendors.findAll",query="SELECT v FROM Vendors v ORDER BY v.vendorId DESC"),
	@NamedQuery(name="Vendors.findSingleVendorBasedOnPersonId",query="SELECT v.vendorId FROM Vendors v WHERE v.personId=:personId"),
	@NamedQuery(name="Vendors.updateVendorStatus",query="UPDATE Vendors v SET v.status = :vendorStatus WHERE v.vendorId = :vendorid"),
	
	@NamedQuery(name = "Vendors.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, v.vendorPersonStatus FROM Person p, Vendors v WHERE p.personId = v.personId AND v.vendorId!=1 ORDER BY v.vendorId DESC"),
			
	@NamedQuery(name = "Vendors.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
					+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
					+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, v.vendorId FROM Person p INNER JOIN p.vendors v WHERE v.vendorPersonStatus LIKE 'Active'"),
	@NamedQuery(name = "Vendors.setVendorPersonStatus", query = "UPDATE Vendors v SET v.vendorPersonStatus = :vendorPersonStatus, v.lastUpdatedBy = :lastUpdatedBy, v.lastUpdatedDt = :lastUpdatedDt WHERE v.personId = :personId"),			
	
	@NamedQuery(name="VendorsDetails.findAll",query="SELECT v FROM Vendors v WHERE v.personId = :personId "),
	@NamedQuery(name="Vendors.vendorsDetails",query="SELECT count(*),(select count(*)  from Vendors u WHERE u.vendorPersonStatus='Active'),(select count(*)  from Vendors p WHERE p.vendorPersonStatus='Inactive') FROM Vendors dr group by 1"),
	
})
public class Vendors
{
	@Id     
    @SequenceGenerator(name = "vendorSeq", sequenceName = "VENDOR_SEQ")
	@GeneratedValue(generator = "vendorSeq")
	@Column(name = "VENDOR_ID")
	private int vendorId;
	
	@Column(name ="PERSON_ID", insertable = false, updatable = false)
	private int personId;
	
	@OneToOne(targetEntity = Person.class,fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	private Person person;
	
	@Column(name = "PAN_NO")
	private String panNo;
	
	@Column(name = "CST_NO")
	private String cstNo;
	
	@Column(name = "STATE_TAX_NO")
	private String stateTaxNo;
	
	@Column(name = "SERVICE_TAX_NO")
	private String serviceTaxNo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy; 
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	@Column(name = "VENDOR_PERSON_STATUS")
	private String vendorPersonStatus;
	
	public int getVendorId()
	{
		return vendorId;
	}
	public void setVendorId(int vendorId)
	{
		this.vendorId = vendorId;
	}
	public int getPersonId()
	{
		return personId;
	}
	public void setPersonId(int personId)
	{
		this.personId = personId;
	}
	public String getCstNo()
	{
		return cstNo;
	}
	public void setCstNo(String cstNo)
	{
		this.cstNo = cstNo;
	}
	public String getStateTaxNo()
	{
		return stateTaxNo;
	}
	public void setStateTaxNo(String stateTaxNo)
	{
		this.stateTaxNo = stateTaxNo;
	}
	public String getServiceTaxNo()
	{
		return serviceTaxNo;
	}
	public void setServiceTaxNo(String serviceTaxNo)
	{
		this.serviceTaxNo = serviceTaxNo;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}	
	
	@JsonIgnore
	public Person getPerson()
	{
		return person;
	}
	public void setPerson(Person person)
	{
		this.person = person;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getVendorPersonStatus() {
		return vendorPersonStatus;
	}
	public void setVendorPersonStatus(String vendorPersonStatus) {
		this.vendorPersonStatus = vendorPersonStatus;
	}
	
	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
}
