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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "VENDOR_SERVICES")
@NamedQueries({ 	
	@NamedQuery(name = "ConciergeVendorServices.findAll", query = "SELECT cvs FROM ConciergeVendorServices cvs ORDER BY cvs.vsId DESC"),
	@NamedQuery(name = "ConciergeVendorServices.UpdateStatus",query="UPDATE ConciergeVendorServices cvs SET  cvs.status = :serviceStatus WHERE cvs.vsId = :vsId"),
	@NamedQuery(name = "ConciergeVendorServices.getVendorNames", query = "SELECT p FROM Person p,ConciergeVendors cv,ConciergeVendorServices cvs WHERE cvs.csvId = cv.csvId AND cv.personId = p.personId"),
	@NamedQuery(name = "ConciergeVendorServices.getServiceNames", query = "SELECT cs.serviceName FROM ConciergeServices cs,ConciergeVendorServices cvs WHERE cvs.cssId = cs.cssId"),
	@NamedQuery(name = "ConciergeVendorServices.getServiceNamesBasedOnVendorId", query = "SELECT cvs.cssId FROM ConciergeVendorServices cvs WHERE cvs.csvId =:csvId "),
	@NamedQuery(name = "ConciergeVendorServices.getVendorService",query="SELECT cvs FROM ConciergeVendorServices cvs,ConciergeServices cs WHERE cvs.vsId =:vsId AND cvs.cssId = cs.cssId "),
	@NamedQuery(name = "ConciergeVendorServices.updateServiceEndDate", query = "UPDATE ConciergeVendorServices cvs SET cvs.endDate = :serviceEndDate WHERE cvs.vsId = :vsId"),
	@NamedQuery(name = "ConciergeVendorServices.getVendorServiceId",query="SELECT cvs.vsId FROM ConciergeVendorServices cvs WHERE cvs.csvId =:csvId AND cvs.cssId =:cssId "),
	
})
public class ConciergeVendorServices {
	
	
	
	// Fields

		private int vsId;
		private ConciergeServices csServices;
		private ConciergeVendors csVendors;
		private int csvId;
		private int cssId;
		private Timestamp startDate;
		private Timestamp endDate;
		private String status;
		private String createdBy;
		private String lastUpdatedBy;
		private Timestamp lastUpdatedDt;
		/*private Set<VendorServiceCharges> vendorServiceChargeses = new HashSet<VendorServiceCharges>(
				0);
		private Set<ServiceRequest> serviceRequests = new HashSet<ServiceRequest>(0);*/

		// Constructors

		/** default constructor */
		public ConciergeVendorServices() {
		}

		/** minimal constructor */
		public ConciergeVendorServices(int vsId, ConciergeServices csServices,
				ConciergeVendors csVendors, String status) {
			this.vsId = vsId;
			this.csServices = csServices;
			this.csVendors = csVendors;
			this.status = status;
		}

		/** full constructor */
		public ConciergeVendorServices(int vsId, ConciergeServices csServices,
				ConciergeVendors csVendors, Timestamp startDate, Timestamp endDate, String status,
				String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt) {
			this.vsId = vsId;
			this.csServices = csServices;
			this.csVendors = csVendors;
			this.startDate = startDate;
			this.endDate = endDate;
			this.status = status;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDt = lastUpdatedDt;
		}

		// Property accessors
		@Id
		@SequenceGenerator(name="VENDORSERVICE_SEQ" ,sequenceName="VENDORSERVICE_SEQ")
		@GeneratedValue(generator="VENDORSERVICE_SEQ")
		@Column(name = "VS_ID")
		public int getVsId() {
			return this.vsId;
		}

		public void setVsId(int vsId) {
			this.vsId = vsId;
		}

		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "CSS_ID",insertable=false,updatable=false)
		public ConciergeServices getCsServices() {
			return this.csServices;
		}

		public void setCsServices(ConciergeServices csServices) {
			this.csServices = csServices;
		}

		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "CSV_ID",insertable=false,updatable=false)
		public ConciergeVendors getCsVendors() {
			return this.csVendors;
		}

		public void setCsVendors(ConciergeVendors csVendors) {
			this.csVendors = csVendors;
		}

		
		@Column(name = "START_DATE")
		public Timestamp getStartDate() {
			return this.startDate;
		}

		public void setStartDate(Timestamp startDate) {
			this.startDate = startDate;
		}

	
		@Column(name = "END_DATE")
		public Timestamp getEndDate() {
			return this.endDate;
		}

		public void setEndDate(Timestamp endDate) {
			this.endDate = endDate;
		}

		@Column(name = "STATUS", nullable = false, length = 45)
		public String getStatus() {
			return this.status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Column(name = "CREATED_BY", length = 45)
		public String getCreatedBy() {
			return this.createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		@Column(name = "LAST_UPDATED_BY", length = 45)
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
		@Column(name = "CSS_ID", length = 11)
		public int getCssId() {
			return cssId;
		}

		public void setCssId(int cssId) {
			this.cssId = cssId;
		}
		@Column(name = "CSV_ID", length = 11)
		public int getCsvId() {
			return csvId;
		}

		public void setCsvId(int csvId) {
			this.csvId = csvId;
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
			
		/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vendorServices")
		public Set<VendorServiceCharges> getVendorServiceChargeses() {
			return this.vendorServiceChargeses;
		}

		public void setVendorServiceChargeses(
				Set<VendorServiceCharges> vendorServiceChargeses) {
			this.vendorServiceChargeses = vendorServiceChargeses;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vendorServices")
		public Set<ServiceRequest> getServiceRequests() {
			return this.serviceRequests;
		}

		public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
			this.serviceRequests = serviceRequests;
		}
*/

}
