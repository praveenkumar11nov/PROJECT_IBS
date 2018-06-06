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


@Entity
@Table(name="VENDOR_COMMENTS_RATINGS")
@NamedQueries({
	@NamedQuery(name = "CsVendorCommentsRating.findAll", query = "SELECT cvcr FROM CsVendorCommentsRating cvcr WHERE cvcr.personId=:personId ORDER BY cvcr.vcrId DESC"),

})
public class CsVendorCommentsRating {
	
	// Fields

		private int vcrId;
		private int csvId;
		private int ownerId;
		private int personId;
		private ConciergeVendors csVendors;
		private Owner owner;
		private String comments;
		private int ratings;
		private String createdBy;
		private String lastUpdatedBy;
		private Timestamp lastUpdatedDt;

		// Constructors

		/** default constructor */
		public CsVendorCommentsRating() {
		}

		/** minimal constructor */
		public CsVendorCommentsRating(int vcrId, ConciergeVendors csVendors, Owner owner,
				Byte ratings) {
			this.vcrId = vcrId;
			this.csVendors = csVendors;
			this.owner = owner;
			this.ratings = ratings;
		}

		/** full constructor */
		public CsVendorCommentsRating(int vcrId, ConciergeVendors csVendors, Owner owner,
				String comments, Byte ratings, String createdBy,
				String lastUpdatedBy, Timestamp lastUpdatedDt) {
			this.vcrId = vcrId;
			this.csVendors = csVendors;
			this.owner = owner;
			this.comments = comments;
			this.ratings = ratings;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDt = lastUpdatedDt;
		}

		// Property accessors
		@Id
		@SequenceGenerator(name = "VENDORCOMMENTSRATING_SEQ", sequenceName = "VENDORCOMMENTSRATING_SEQ")
		@GeneratedValue(generator = "VENDORCOMMENTSRATING_SEQ")
		@Column(name = "VCR_ID", unique = true, nullable = false, precision = 11, scale = 0)
		public int getVcrId() {
			return this.vcrId;
		}
		
		public void setVcrId(int vcrId) {
			this.vcrId = vcrId;
		}
		@Column(name="CSV_ID")
		public int getCsvId() {
			return csvId;
		}

		public void setCsvId(int csvId) {
			this.csvId = csvId;
		}
		@Column(name="OWNER_ID")
		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "CSV_ID",insertable=false,updatable=false)
		public ConciergeVendors getCsVendors() {
			return this.csVendors;
		}

		public void setCsVendors(ConciergeVendors csVendors) {
			this.csVendors = csVendors;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "OWNER_ID", insertable=false,updatable=false)
		public Owner getOwner() {
			return this.owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}

		@Column(name = "COMMENTS", length = 225)
		public String getComments() {
			return this.comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		@Column(name = "RATINGS", nullable = false, precision = 2, scale = 0)
		public int getRatings() {
			return this.ratings;
		}

		public void setRatings(int ratings) {
			this.ratings = ratings;
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
	
		@Column(name="PERSON_ID")
		public int getPersonId()
		{
			return personId;
		}

		public void setPersonId(int personId)
		{
			this.personId = personId;
		}
	
	

}
