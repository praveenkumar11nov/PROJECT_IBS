package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;


@Entity
@Table(name="CS_VENDORS")
@NamedQueries({
	@NamedQuery(name="ConciergeVendors.getCsVendorIdBasedOnPersonId",query="SELECT cv FROM ConciergeVendors cv WHERE cv.personId = :personid "),
	@NamedQuery(name="ConciergeVendors.getPersonIdBasedOnVendorId",query="SELECT cv.personId FROM ConciergeVendors cv WHERE cv.csvId = :vendorId "),
	
	@NamedQuery(name = "ConciergeVendors.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, c.status FROM Person p, ConciergeVendors c WHERE c.personId = p.personId ORDER BY c.csvId DESC"),
	
	@NamedQuery(name = "ConciergeVendors.setConciergeVendorStatus", query = "UPDATE ConciergeVendors c SET c.status = :status, c.lastUpdatedBy = :lastUpdatedBy, c.lastUpdatedDt = :lastUpdatedDt WHERE c.personId = :personId"),
	@NamedQuery(name="ConciergeVendors.findAll",query="SELECT cv FROM ConciergeVendors cv WHERE cv.personId = :personId ORDER BY cv.csvId DESC"),
	
	@NamedQuery(name = "ConciergeVendors.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, c.csvId FROM Person p INNER JOIN p.conciergeVendors c WHERE c.status LIKE 'Active'"),
		
	
})
public class ConciergeVendors {
	
	
	 // Fields    

    private int csvId;
    private Person person;
    private String cstNo;
    private String stateTaxNo;
    private String serviceTaxNo;
    private String status;
   // private String requestcounter;
    private String createdBy;
    private String lastUpdatedBy;
    private Timestamp lastUpdatedDt;
    
    private int personId;
    /*private Set<VendorQuotes> vendorQuoteses = new HashSet<VendorQuotes>(0);
    private Set<VendorServices> vendorServiceses = new HashSet<VendorServices>(0);
    private Set<VendorCommentsRatings> vendorCommentsRatingses = new HashSet<VendorCommentsRatings>(0);*/


   // Constructors

   /** default constructor */
   public ConciergeVendors() {
   }

	/** minimal constructor */
   public ConciergeVendors(int csvId, Person person, String status) {
       this.csvId = csvId;
       this.person = person;
       this.status = status;
   }
   
   /** full constructor */
   public ConciergeVendors(int csvId, Person person, String cstNo, String stateTaxNo, String serviceTaxNo, String status, String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt) {
       this.csvId = csvId;
       this.person = person;
       this.cstNo = cstNo;
       this.stateTaxNo = stateTaxNo;
       this.serviceTaxNo = serviceTaxNo;
       this.status = status;
      // this.requestcounter = requestcounter;
       this.createdBy = createdBy;
       this.lastUpdatedBy = lastUpdatedBy;
       this.lastUpdatedDt = lastUpdatedDt;
       /*this.vendorQuoteses = vendorQuoteses;
       this.vendorServiceses = vendorServiceses;
       this.vendorCommentsRatingses = vendorCommentsRatingses;*/
   }

  
   // Property accessors
   @Id 
   @SequenceGenerator(name = "CSVENDORS_SEQ", sequenceName = "CSVENDORS_SEQ")
	@GeneratedValue(generator = "CSVENDORS_SEQ")
   @Column(name="CSV_ID", unique=true, nullable=false, precision=11, scale=0)
   public int getCsvId() {
       return this.csvId;
   }
   
   public void setCsvId(int csvId) {
       this.csvId = csvId;
   }
   
   @Column(name="PERSON_ID", insertable = false, updatable = false)
	public int getPersonId() {
	return personId;
}

public void setPersonId(int personId) {
	this.personId = personId;
}

	@OneToOne(targetEntity = Person.class,
	fetch = FetchType.EAGER,
	cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
   public Person getPerson() {
       return this.person;
   }
   
   public void setPerson(Person person) {
	  
       this.person = person;
   }

   @Column(name="CST_NO", length=45)
   public String getCstNo() {
       return this.cstNo;
   }
   
   public void setCstNo(String cstNo) {
       this.cstNo = cstNo;
   }
   
   @Column(name="STATE_TAX_NO", length=45)
   public String getStateTaxNo() {
       return this.stateTaxNo;
   }
   
   public void setStateTaxNo(String stateTaxNo) {
       this.stateTaxNo = stateTaxNo;
   }
   
   @Column(name="SERVICE_TAX_NO", length=45)
   public String getServiceTaxNo() {
       return this.serviceTaxNo;
   }
   
   public void setServiceTaxNo(String serviceTaxNo) {
       this.serviceTaxNo = serviceTaxNo;
   }
   
   @Column(name="STATUS", nullable=false, length=45)
   public String getStatus() {
       return this.status;
   }
   
   public void setStatus(String status) {
       this.status = status;
   }
   
   
   @Column(name="CREATED_BY", length=45)
   public String getCreatedBy() {
       return this.createdBy;
   }
   
   public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;
   }
   
   @Column(name="LAST_UPDATED_BY", length=45)
   public String getLastUpdatedBy() {
       return this.lastUpdatedBy;
   }
   
   public void setLastUpdatedBy(String lastUpdatedBy) {
       this.lastUpdatedBy = lastUpdatedBy;
   }
   
   @Column(name="LAST_UPDATED_DT", length=11)
   public Timestamp getLastUpdatedDt() {
       return this.lastUpdatedDt;
   }
   
   public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
       this.lastUpdatedDt = lastUpdatedDt;
   }
   
   @PreUpdate
	 protected void onUpdate() {
		 lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
/*@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="csVendors")
   public Set<VendorQuotes> getVendorQuoteses() {
       return this.vendorQuoteses;
   }
   
   public void setVendorQuoteses(Set<VendorQuotes> vendorQuoteses) {
       this.vendorQuoteses = vendorQuoteses;
   }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="csVendors")

   public Set<VendorServices> getVendorServiceses() {
       return this.vendorServiceses;
   }
   
   public void setVendorServiceses(Set<VendorServices> vendorServiceses) {
       this.vendorServiceses = vendorServiceses;
   }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="csVendors")

   public Set<VendorCommentsRatings> getVendorCommentsRatingses() {
       return this.vendorCommentsRatingses;
   }
   
   public void setVendorCommentsRatingses(Set<VendorCommentsRatings> vendorCommentsRatingses) {
       this.vendorCommentsRatingses = vendorCommentsRatingses;
   }
*/
}
