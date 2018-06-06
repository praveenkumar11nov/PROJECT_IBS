package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

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
@Table(name="SERVICE_BOOKINGS")
@NamedQueries({
	@NamedQuery(name = "CsServiceBooking.findAll", query = "SELECT csb FROM CsServiceBooking csb ORDER BY csb.sbId DESC"),
	@NamedQuery(name = "CsServiceBooking.UpdateStatus",query="UPDATE CsServiceBooking csb SET  csb.serviceDelivered = :status WHERE csb.sbId = :sbId"),
	@NamedQuery(name = "CsServiceBooking.getObject", query = "SELECT csb FROM CsServiceBooking csb WHERE csb.sbId=:sbId"),
	
})

public class CsServiceBooking {
	
	// Fields

		private int sbId;
		private int vscId;
		private int ownerId;
		private int propertyId;
		private CsVendorServiceCharges vendorServiceCharges;
		private int vqId;
		/*private VendorQuotes vendorQuotes;*/
		/*private Owner owner;*/
		private Property property;
		private Timestamp bookingDate;
		private String bookedBy;
		private String sbComments;
		private String serviceDelivered;
		private String invoiceRaised;
		private String createdBy;
		private String lastUpdatedBy;
		private Timestamp lastUpdatedDt;
		/*private Set<ServiceInvoices> serviceInvoiceses = new HashSet<ServiceInvoices>(
				0);*/

		// Constructors

		/** default constructor */
		public CsServiceBooking() {
		}

		/** minimal constructor */
		public CsServiceBooking(int sbId,
				CsVendorServiceCharges vendorServiceCharges,Owner owner) {
			this.sbId = sbId;
			this.vendorServiceCharges = vendorServiceCharges;
			//this.owner = owner;
		}

		/** full constructor */
		public CsServiceBooking(int sbId,
				CsVendorServiceCharges vendorServiceCharges,
				 Timestamp bookingDate,
				String bookedBy, String sbComments, String serviceDelivered,
				String invoiceRaised, String createdBy, String lastUpdatedBy,
				Timestamp lastUpdatedDt) {
			this.sbId = sbId;
			this.vendorServiceCharges = vendorServiceCharges;
			//this.owner = owner;
			this.bookingDate = bookingDate;
			this.bookedBy = bookedBy;
			this.sbComments = sbComments;
			this.serviceDelivered = serviceDelivered;
			this.invoiceRaised = invoiceRaised;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDt = lastUpdatedDt;
		}

		// Property accessors
		@Id
		@SequenceGenerator(name = "SERVICEBOOKING_SEQ", sequenceName = "SERVICEBOOKING_SEQ")
		@GeneratedValue(generator = "SERVICEBOOKING_SEQ")
		@Column(name = "SB_ID", unique = true, nullable = false, precision = 11, scale = 0)
		public int getSbId() {
			return this.sbId;
		}

		public void setSbId(int sbId) {
			this.sbId = sbId;
		}
		@Column(name = "VSC_ID", length = 45)
		public int getVscId() {
			return vscId;
		}

		public void setVscId(int vscId) {
			this.vscId = vscId;
		}
		@Column(name = "OWNER_ID", length = 45)
		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}
		@Column(name = "PROPERTY_ID")
		public int getPropertyId() {
			return propertyId;
		}

		public void setPropertyId(int propertyId) {
			this.propertyId = propertyId;
		}

		@Column(name="VQ_ID")
		public int getVqId() {
			return vqId;
		}

		public void setVqId(int vqId) {
			this.vqId = vqId;
		}

		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "VSC_ID", nullable = false,insertable = false, updatable = false)
		public CsVendorServiceCharges getVendorServiceCharges() {
			return this.vendorServiceCharges;
		}

		public void setVendorServiceCharges(
				CsVendorServiceCharges vendorServiceCharges) {
			this.vendorServiceCharges = vendorServiceCharges;
		}


		/*@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "OWNER_ID", nullable = false , insertable = false, updatable = false)
		public Owner getOwner() {
			return this.owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}*/
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "PROPERTY_ID", nullable = false , insertable = false, updatable = false)
		public Property getProperty() {
			return property;
		}

		public void setProperty(Property property) { 
			this.property = property;
		}

		@Column(name = "SB_DT")
		public Timestamp getBookingDate() {
			return this.bookingDate;
		}

		public void setBookingDate(Timestamp bookingDate) {
			this.bookingDate = bookingDate;
		}

		@Column(name = "BOOKED_BY", length = 45)
		public String getBookedBy() {
			return this.bookedBy;
		}

		public void setBookedBy(String bookedBy) {
			this.bookedBy = bookedBy;
		}

		@Column(name = "SB_COMMENTS", length = 225)
		public String getSbComments() {
			return this.sbComments;
		}

		public void setSbComments(String sbComments) {
			this.sbComments = sbComments;
		}

		@Column(name = "SERVICE_DELIVERED", length = 225)
		public String getServiceDelivered() {
			return this.serviceDelivered;
		}

		public void setServiceDelivered(String serviceDelivered) {
			this.serviceDelivered = serviceDelivered;
		}

		@Column(name = "INVOICE_RAISED", length = 225)
		public String getInvoiceRaised() {
			return this.invoiceRaised;
		}

		public void setInvoiceRaised(String invoiceRaised) {
			this.invoiceRaised = invoiceRaised;
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
/*
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "serviceBookings")
		public Set<ServiceInvoices> getServiceInvoiceses() {
			return this.serviceInvoiceses;
		}

		public void setServiceInvoiceses(Set<ServiceInvoices> serviceInvoiceses) {
			this.serviceInvoiceses = serviceInvoiceses;
		}*/
		
		
		@PrePersist
		protected void onCreate() {
			createdBy = (String) SessionData.getUserDetails().get("userID");
			lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
			lastUpdatedDt = new Timestamp(new Date().getTime());
		}
		@PreUpdate
		protected void onUpdate() {
			createdBy = (String) SessionData.getUserDetails().get("userID");
			lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
			lastUpdatedDt = new Timestamp(new Date().getTime());
		}

}
