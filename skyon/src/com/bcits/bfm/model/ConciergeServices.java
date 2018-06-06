package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "CS_SERVICES")
@NamedQueries({ 
	@NamedQuery(name = "ConciergeServices.findAll", query = "SELECT cs FROM ConciergeServices cs ORDER BY cs.cssId DESC"),
	@NamedQuery(name = "ConciergeServices.getServiceNameBasedOnId", query = "SELECT cs.serviceName FROM ConciergeServices cs WHERE cs.cssId=:cssId"),
	@NamedQuery(name = "ConciergeServices.getGroupNames", query = "SELECT cs.serviceGroupName FROM ConciergeServices cs ORDER BY cs.cssId DESC"),
	@NamedQuery(name = "ConciergeServices.getServiceNames", query = "SELECT cs.serviceName FROM ConciergeServices cs ORDER BY cs.cssId DESC"),
	@NamedQuery(name = "ConciergeServices.UpdateStatus",query="UPDATE ConciergeServices cs SET  cs.status = :serviceStatus WHERE cs.cssId = :cssId"),
	@NamedQuery(name = "ConciergeServices.getServiceIdBasedOnName", query = "SELECT cs.cssId FROM ConciergeServices cs WHERE cs.serviceName=:serviceName"),
	@NamedQuery(name = "ConciergeServices.updateServiceEndDate", query = "UPDATE ConciergeServices cs SET cs.serviceEndDate = :serviceEndDate WHERE cs.cssId = :cssId"),
	@NamedQuery(name="ConciergeServices.getDistinctCssIds",query="SELECT DISTINCT cs.cssId FROM ConciergeServices cs,ConciergeVendorServices cvs WHERE cs.cssId = cvs.cssId AND cvs.status='Active'"),
	@NamedQuery(name = "ConciergeServices.findActiveObject", query = "SELECT cs FROM ConciergeServices cs WHERE cs.status=:status ORDER BY cs.cssId DESC"),
})
public class ConciergeServices {
	
		private int cssId;
		private String serviceGroupName;
		private String serviceName;
		private Date serviceStartDate;
		private Date serviceEndDate;
		private String serviceDescription;
		private String status;
		private String createdBy;
		private String lastUpdatedBy;
		private Date lastUpdatedDt;
		/*private Set<VendorServices> vendorServiceses = new HashSet<VendorServices>(
				0);
		private Set<ServiceRequest> serviceRequests = new HashSet<ServiceRequest>(0);*/

		// Constructors

		/** default constructor */
		public ConciergeServices() {
		}

		/** minimal constructor */
		public ConciergeServices(int cssId, String serviceGroupName, String serviceName,
				String status) {
			this.cssId = cssId;
			this.serviceGroupName = serviceGroupName;
			this.serviceName = serviceName;
			this.status = status;
		}

		/** full constructor */
		public ConciergeServices(int cssId, String serviceGroupName, String serviceName,
				String serviceDescription, Date serviceStartDate,
				Date serviceEndDate, String status, String createdBy,
				String lastUpdatedBy, Timestamp lastUpdatedDt) {
			this.cssId = cssId;
			this.serviceGroupName = serviceGroupName;
			this.serviceName = serviceName;
			this.serviceDescription = serviceDescription;
			this.serviceStartDate = serviceStartDate;
			this.serviceEndDate = serviceEndDate;
			this.status = status;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDt = lastUpdatedDt;
			/*this.vendorServiceses = vendorServiceses;
			this.serviceRequests = serviceRequests;*/
		}

		// Property accessors
		@Id
		@SequenceGenerator(name = "CSSERVICE_SEQ", sequenceName = "CSSERVICE_SEQ")
		@GeneratedValue(generator = "CSSERVICE_SEQ")
		@Column(name = "CSS_ID")
		public int getCssId() {
			return this.cssId;
		}

		public void setCssId(int cssId) {
			this.cssId = cssId;
		}

		@NotEmpty(message = "'Service Group Name' cannot be a empty")
		@Column(name = "SERVICE_GROUP_NAME", nullable = false, length = 45)
		public String getServiceGroupName() {
			return this.serviceGroupName;
		}

		public void setServiceGroupName(String serviceGroupName) {
			this.serviceGroupName = serviceGroupName;
		}

		@NotEmpty(message = "'Service Name' cannot be a empty")
		@Column(name = "SERVICE_NAME", nullable = false, length = 45)
		public String getServiceName() {
			return this.serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		@NotEmpty(message = "'Description' cannot be a empty")
		@Column(name = "SERVICE_DESCRIPTION", length = 225)
		public String getServiceDescription() {
			return this.serviceDescription;
		}

		public void setServiceDescription(String serviceDescription) {
			this.serviceDescription = serviceDescription;
		}

		
		@Temporal(TemporalType.DATE)
		@Column(name = "SERVICE_START_DATE", length = 7)
		public Date getServiceStartDate() {
			return this.serviceStartDate;
		}

		public void setServiceStartDate(Date serviceStartDate) {
			this.serviceStartDate = serviceStartDate;
		}

		
		@Column(name = "SERVICE_END_DATE")
		public Date getServiceEndDate() {
			return this.serviceEndDate;
		}

		public void setServiceEndDate(Date serviceEndDate) {
			this.serviceEndDate = serviceEndDate;
		}
		@NotEmpty(message = "'Status' cannot be a empty")
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
		public Date getLastUpdatedDt() {
			return this.lastUpdatedDt;
		}

		public void setLastUpdatedDt(Date lastUpdatedDt) {
			this.lastUpdatedDt = lastUpdatedDt;
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
		
		
		
		
		/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "csServices")
		public Set<VendorServices> getVendorServiceses() {
			return this.vendorServiceses;
		}

		public void setVendorServiceses(Set<VendorServices> vendorServiceses) {
			this.vendorServiceses = vendorServiceses;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "csServices")
		public Set<ServiceRequest> getServiceRequests() {
			return this.serviceRequests;
		}

		public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
			this.serviceRequests = serviceRequests;
		}*/

}
