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
@Table(name = "VENDOR_SERVICE_CHARGES")
@NamedQueries({
	@NamedQuery(name="CsVendorServiceCharges.findAll",query="SELECT cvsc FROM CsVendorServiceCharges cvsc WHERE cvsc.vsId=:vsId ORDER BY cvsc.vscId DESC"),
	@NamedQuery(name="CsVendorServiceCharges.getVendorService",query="SELECT cvsc FROM ConciergeVendorServices cvs,CsVendorServiceCharges cvsc,ConciergeServices cs WHERE cvsc.vsId = cvs.vsId AND cvs.cssId = cs.cssId "),
	@NamedQuery(name="CsVendorServiceCharges.getConciergeServices",query="SELECT cs FROM ConciergeVendorServices cvs,CsVendorServiceCharges cvsc,ConciergeServices cs WHERE cvsc.vscId=:vscId AND cvsc.vsId = cvs.vsId AND cvs.cssId = cs.cssId "),
	@NamedQuery(name="CsVendorServiceCharges.getDistinctServiceId",query="SELECT DISTINCT cvsc.vsId FROM CsVendorServiceCharges cvsc"),
	@NamedQuery(name="serviceCharges.getAll",query="SELECT cvsc FROM CsVendorServiceCharges cvsc ORDER BY cvsc.vscId DESC"),
	@NamedQuery(name="CsVendorServiceCharges.getVendorRate",query="SELECT cvsc FROM CsVendorServiceCharges cvsc WHERE cvsc.vsId=:vsId AND cvsc.vendorRateType=:rateType "),
	@NamedQuery(name="CsVendorServiceCharges.getServiceChargeBasedOnId",query="SELECT cvsc FROM CsVendorServiceCharges cvsc WHERE cvsc.vscId=:vscId"),
	@NamedQuery(name="CsVendorServiceCharges.getChargeObjectExceptId",query="SELECT cvsc FROM CsVendorServiceCharges cvsc WHERE cvsc.vscId !=:vscId ORDER BY cvsc.vscId DESC"),
	@NamedQuery(name="CsVendorServiceCharges.getServiceChargeBasedOnVendorServiceId",query="SELECT cvsc FROM CsVendorServiceCharges cvsc WHERE cvsc.vsId =:vsId ORDER BY cvsc.vscId DESC"),
	@NamedQuery(name="CsVendorServiceCharges.getVedorRateBasedOnVendorRateType",query="SELECT cvsc.vendorRate FROM CsVendorServiceCharges cvsc WHERE cvsc.vscId =:vscId "),
	
	
})


public class CsVendorServiceCharges {
	
	// Fields

		private int vscId;
		private ConciergeVendorServices vendorServices;
		private int vsId;
		private String collectionMethod;
		private String serviceRateType;
		private String srtPaymethod;
		private String serviceRateTypeNote;
		private String vendorRateType;
		private String vrtPaymethod;
		private String vendorRateTypeNote;
		private int serviceRate;
		private int vendorRate;
		private String createdBy;
		private String lastUpdatedBy;
		private Timestamp lastUpdatedDt;
		/*private Set<ServiceBookings> serviceBookingses = new HashSet<ServiceBookings>(
				0);
		private Set<ServiceRequest> serviceRequests = new HashSet<ServiceRequest>(0);*/

		// Constructors

		/** default constructor */
		public CsVendorServiceCharges() {
		}

		/** minimal constructor */
		public CsVendorServiceCharges(int vscId, ConciergeVendorServices vendorServices) {
			this.vscId = vscId;
			this.vendorServices = vendorServices;
		}

		/** full constructor */
		public CsVendorServiceCharges(int vscId, ConciergeVendorServices vendorServices,
				String collectionMethod, String serviceRateType,
				String srtPaymethod, String serviceRateTypeNote,
				String vendorRateType, String vrtPaymethod,
				String vendorRateTypeNote, int serviceRate, int vendorRate,
				String createdBy, String lastUpdatedBy,
				Timestamp lastUpdatedDt) {
			this.vscId = vscId;
			this.vendorServices = vendorServices;
			this.collectionMethod = collectionMethod;
			this.serviceRateType = serviceRateType;
			this.srtPaymethod = srtPaymethod;
			this.serviceRateTypeNote = serviceRateTypeNote;
			this.vendorRateType = vendorRateType;
			this.vrtPaymethod = vrtPaymethod;
			this.vendorRateTypeNote = vendorRateTypeNote;
			this.serviceRate = serviceRate;
			this.vendorRate = vendorRate;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDt = lastUpdatedDt;
			
		}

		// Property accessors
		@Id
		@SequenceGenerator(name="VENDORSERVICECHARGES_SEQ" ,sequenceName="VENDORSERVICECHARGES_SEQ")
		@GeneratedValue(generator="VENDORSERVICECHARGES_SEQ")
		@Column(name = "VSC_ID", unique = true, nullable = false, precision = 11, scale = 0)
		public int getVscId() {
			return this.vscId;
		}

		public void setVscId(int vscId) {
			this.vscId = vscId;
		}
		@Column(name = "VS_ID")
		public int getVsId() {
			return vsId;
		}

		public void setVsId(int vsId) {
			this.vsId = vsId;
		}

		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "VS_ID", insertable=false,updatable=false)
		public ConciergeVendorServices getVendorServices() {
			return this.vendorServices;
		}

		public void setVendorServices(ConciergeVendorServices vendorServices) {
			this.vendorServices = vendorServices;
		}

		@Column(name = "COLLECTION_METHOD", length = 45)
		public String getCollectionMethod() {
			return this.collectionMethod;
		}

		public void setCollectionMethod(String collectionMethod) {
			this.collectionMethod = collectionMethod;
		}

		@Column(name = "SERVICE_RATE_TYPE", length = 45)
		public String getServiceRateType() {
			return this.serviceRateType;
		}

		public void setServiceRateType(String serviceRateType) {
			this.serviceRateType = serviceRateType;
		}

		@Column(name = "SRT_PAYMETHOD", length = 45)
		public String getSrtPaymethod() {
			return this.srtPaymethod;
		}

		public void setSrtPaymethod(String srtPaymethod) {
			this.srtPaymethod = srtPaymethod;
		}

		@Column(name = "SERVICE_RATE_TYPE_NOTE", length = 225)
		public String getServiceRateTypeNote() {
			return this.serviceRateTypeNote;
		}

		public void setServiceRateTypeNote(String serviceRateTypeNote) {
			this.serviceRateTypeNote = serviceRateTypeNote;
		}

		@Column(name = "VENDOR_RATE_TYPE", length = 45)
		public String getVendorRateType() {
			return this.vendorRateType;
		}

		public void setVendorRateType(String vendorRateType) {
			this.vendorRateType = vendorRateType;
		}

		@Column(name = "VRT_PAYMETHOD", length = 45)
		public String getVrtPaymethod() {
			return this.vrtPaymethod;
		}

		public void setVrtPaymethod(String vrtPaymethod) {
			this.vrtPaymethod = vrtPaymethod;
		}

		@Column(name = "VENDOR_RATE_TYPE_NOTE", length = 225)
		public String getVendorRateTypeNote() {
			return this.vendorRateTypeNote;
		}

		public void setVendorRateTypeNote(String vendorRateTypeNote) {
			this.vendorRateTypeNote = vendorRateTypeNote;
		}

		@Column(name = "SERVICE_RATE", length = 45)
		public int getServiceRate() {
			return this.serviceRate;
		}

		public void setServiceRate(int serviceRate) {
			this.serviceRate = serviceRate;
		}

		@Column(name = "VENDOR_RATE", length = 45)
		public int getVendorRate() {
			return this.vendorRate;
		}

		public void setVendorRate(int vendorRate) {
			this.vendorRate = vendorRate;
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
		@PrePersist
		 protected void onCreate() {
			lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		  createdBy = (String) SessionData.getUserDetails().get("userID");
		 }

		 @PreUpdate
		 protected void onUpdate() {
			 lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		 }
		
		 public int hashCode(){
			    StringBuffer buffer = new StringBuffer();
			    buffer.append(this.vscId);
			    buffer.append(this.vsId);
			    buffer.append(this.collectionMethod);
			    buffer.append(this.serviceRateType);
			    buffer.append(this.srtPaymethod);
			    buffer.append(this.serviceRateTypeNote);
			    buffer.append(this.vendorRateType);
			    buffer.append(this.vrtPaymethod);
			    buffer.append(this.vendorRateTypeNote);
			    buffer.append(this.serviceRate);
			    buffer.append(this.vendorRate);
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
			    CsVendorServiceCharges csVendorServiceCharges = (CsVendorServiceCharges)object;
			    if(this.hashCode()== csVendorServiceCharges.hashCode())return true;
			   return false;
			}
	/*	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
			this.lastUpdatedDt = lastUpdatedDt;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vendorServiceCharges")
		public Set<ServiceBookings> getServiceBookingses() {
			return this.serviceBookingses;
		}

		public void setServiceBookingses(Set<ServiceBookings> serviceBookingses) {
			this.serviceBookingses = serviceBookingses;
		}

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vendorServiceCharges")
		public Set<ServiceRequest> getServiceRequests() {
			return this.serviceRequests;
		}

		public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
			this.serviceRequests = serviceRequests;
		}*/


}
