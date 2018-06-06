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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "OT_CONSUMPTIONS")

@NamedQueries({
	@NamedQuery(name = "OTConsumptions.findAll", query = "SELECT ot FROM OTConsumptions  ot where ot.otherInstallation.id=:id ORDER BY ot.otId DESC"),
	@NamedQuery(name = "OTConsumptions.getAllChildIds", query = "SELECT ot.otId FROM OTConsumptions  ot where ot.otherInstallation.id=:id "),
	@NamedQuery(name = "OTConsumptions.getBillEntityByAccountId", query = "SELECT MAX(ot.presentReading) FROM OTConsumptions ot WHERE ot.otherInstallation.meteredStatus = :typeOfService and ot.otherInstallation.id =:accountID"),
	@NamedQuery(name = "OTConsumptions.getMaxConsuptionId", query = "SELECT MAX(ot.otId) FROM OTConsumptions ot WHERE ot.otherInstallation.id=:installId"),
	
})
public class OTConsumptions {

		@Id
		@SequenceGenerator(name = "ot_consumptions_seq", sequenceName = "OT_CONSUMPTIONS_SEQ") 
		@GeneratedValue(generator = "ot_consumptions_seq") 
		
		@Column(name = "ID")	
		private int otId;
		
		@Column(name = "INSTAL_ID")
		private int id;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "INSTAL_ID", nullable = false,insertable=false,updatable=false)
		@Fetch(FetchMode.SELECT)
	    @BatchSize(size = 10)
		private OtherInstallation otherInstallation;
		
		@Temporal(TemporalType.DATE)
		@Column(name = "MONTH")
		 private Date oTMonth;

		@Column(name = "PREVIOUS_READING")
		private Double previousReading;
		
		@Column(name = "PRESENT_READING")
		private Double presentReading;
		
		@Column(name = "METER_CONSTANT")
		private Double meterConstant;
		
		@Column(name = "UNITS")
		private int units;
		
		@Column(name = "STATUS")
		private String status;
		
		@Column(name = "CREATED_BY")
		private String createdBy;
			
			
		@Column(name = "LAST_UPDATED_BY")
		private String lastUpdatedBy;
			
		@Column(name = "LAST_UPDATED_DT")
		private Timestamp lastUpdatedDate;

		   @Transient
          private Integer capacity;
          
		   @Transient
          private Integer phase;
          
		   @Transient
          private Integer hour;
		
		public Integer getCapacity() {
			return capacity;
		}

		public void setCapacity(Integer capacity) {
			this.capacity = capacity;
		}

		public Integer getPhase() {
			return phase;
		}

		public void setPhase(Integer phase) {
			this.phase = phase;
		}

		public Integer getHour() {
			return hour;
		}

		public void setHour(Integer hour) {
			this.hour = hour;
		}

		public int getOtId() {
			return otId;
		}

		public void setOtId(int otId) {
			this.otId = otId;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public OtherInstallation getOtherInstallation() {
			return otherInstallation;
		}

		public void setOtherInstallation(OtherInstallation otherInstallation) {
			this.otherInstallation = otherInstallation;
		}


		public double getPreviousReading() {
			return previousReading;
		}

		public void setPreviousReading(double previousReading) {
			this.previousReading = previousReading;
		}

		public double getPresentReading() {
			return presentReading;
		}

		public void setPresentReading(double presentReading) {
			this.presentReading = presentReading;
		}

		public double getMeterConstant() {
			return meterConstant;
		}

		public void setMeterConstant(double meterConstant) {
			this.meterConstant = meterConstant;
		}

		public int getUnits() {
			return units;
		}

		public void setUnits(int units) {
			this.units = units;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getLastUpdatedBy() {
			return lastUpdatedBy;
		}

		public void setLastUpdatedBy(String lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}

		public Timestamp getLastUpdatedDate() {
			return lastUpdatedDate;
		}

		public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}
		
       
		
		public Date getoTMonth() {
			return oTMonth;
		}

		public void setoTMonth(Date oTMonth) {
			this.oTMonth = oTMonth;
		}
		
		@PrePersist
		protected void onCreate() 
		{
			lastUpdatedDate = new Timestamp(new Date().getTime());
		    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		    createdBy = (String) SessionData.getUserDetails().get("userID");
		    status = "Inactive";
		}
		
		@PreUpdate
		protected void onUpdate() 
		{
			lastUpdatedDate = new Timestamp(new Date().getTime());
			lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		}
		
		
		
		
	
}
