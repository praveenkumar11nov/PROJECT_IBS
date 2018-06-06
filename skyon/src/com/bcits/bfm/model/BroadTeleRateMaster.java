package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@NamedQueries({
	@NamedQuery(name = "BroadTeleRateMaster.findAll", query = "SELECT el FROM BroadTeleRateMaster el ORDER BY el.broadTeleRmid DESC"),
	@NamedQuery(name = "BroadTeleRateMaster.findActive", query = "SELECT el FROM BroadTeleRateMaster el where el.status=:status ORDER BY el.broadTeleRmid DESC"),
	@NamedQuery(name = "BroadTeleRateMaster.getBroadTeleTariffNameByID", query = "SELECT el.broadTeleTariffName FROM  BroadTeleTariffMaster el WHERE el.broadTeleTariffId=:broadTeleTariffId"),
//	@NamedQuery(name = "BroadTeleRateMaster.serviceEndDateUpdate", query = "UPDATE GasRateMaster el SET el.gasValidTo = :gasValidTo WHERE el.gasrmid = :gasrmid"),
	@NamedQuery(name = "BroadTeleRateMaster.setBroadTeleRateMasterStatus", query = "UPDATE BroadTeleRateMaster el SET el.status = :status WHERE el.broadTeleRmid = :broadTeleRmid"),
//	@NamedQuery(name = "BroadTeleRateMaster.getWTRateMaster", query = "SELECT el FROM WTRateMaster el WHERE el.wtrmid = :wtrmid"),
	//@NamedQuery(name = "BroadTeleRateMaster.findID", query = "SELECT el FROM GasRateMaster el WHERE el.gasrmid = :gasrmid"),
	
})

@Entity
@Table(name = "BROAD_TELE_RATE_MASTER")
public class BroadTeleRateMaster
{

	@Id
	@Column(name="BROAD_TELE_RM_ID")
	@SequenceGenerator(name = "broadTeleratemaster_seq", sequenceName = "BROAD_TELE_RATEMASTER_SEQ")
	@GeneratedValue(generator = "broadTeleratemaster_seq")
	private int broadTeleRmid;
	
	@Column(name="BROAD_TELE_TARIFF_ID")
	private int broadTeleTariffId;
	
	@Column(name="BROAD_TELE_RATE_NAME")
	private String broadTeleRateName;
	
	@Column(name="BROAD_TELE_RATE_DESCRIPTION")
	private String broadTeleRateDescription;
	
	@Column(name="BROAD_TELE_RATE_TYPE")
	private String broadTeleRateType;
	
	@Column(name="BROAD_TELE_RATE_UOM")
	private String broadTeleRateUOM;
	
	@Column(name="BROAD_TELE_VALID_FROM")
	private Date broadTeleValidFrom;
	
	@Column(name="BROAD_TELE_VALID_TO")
	private Date broadTeleValidTo;
	
	@Column(name="STATUS", length = 1020)
	private String status;
	
	@Column(name="CREATED_BY", length = 1020)
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY", length = 1020)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDT;
	
	@Column(name="BROAD_TELE_MIN_RATE")
	private Float broadTeleMinRate = 0.0f;
	
	@Column(name="BROAD_TELE_MAX_RATE")
	private Float broadTeleMaxRate= 0.0f;
	
	@Transient
	private String broadTeleTariffName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BROAD_TELE_TARIFF_ID", insertable = false, updatable = false, nullable = false)
	private BroadTeleTariffMaster broadTeleTariffMaster;

	public int getBroadTeleRmid() {
		return broadTeleRmid;
	}

	public void setBroadTeleRmid(int broadTeleRmid) {
		this.broadTeleRmid = broadTeleRmid;
	}

	public int getBroadTeleTariffId() {
		return broadTeleTariffId;
	}

	public void setBroadTeleTariffId(int broadTeleTariffId) {
		this.broadTeleTariffId = broadTeleTariffId;
	}

	public String getBroadTeleRateName() {
		return broadTeleRateName;
	}

	public void setBroadTeleRateName(String broadTeleRateName) {
		this.broadTeleRateName = broadTeleRateName;
	}

	public String getBroadTeleRateDescription() {
		return broadTeleRateDescription;
	}

	public void setBroadTeleRateDescription(String broadTeleRateDescription) {
		this.broadTeleRateDescription = broadTeleRateDescription;
	}

	public String getBroadTeleRateType() {
		return broadTeleRateType;
	}

	public void setBroadTeleRateType(String broadTeleRateType) {
		this.broadTeleRateType = broadTeleRateType;
	}

	public String getBroadTeleRateUOM() {
		return broadTeleRateUOM;
	}

	public void setBroadTeleRateUOM(String broadTeleRateUOM) {
		this.broadTeleRateUOM = broadTeleRateUOM;
	}

	public Date getBroadTeleValidFrom() {
		return broadTeleValidFrom;
	}

	public void setBroadTeleValidFrom(Date broadTeleValidFrom) {
		this.broadTeleValidFrom = broadTeleValidFrom;
	}

	public Date getBroadTeleValidTo() {
		return broadTeleValidTo;
	}

	public void setBroadTeleValidTo(Date broadTeleValidTo) {
		this.broadTeleValidTo = broadTeleValidTo;
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

	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}

	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}

	public Float getBroadTeleMinRate() {
		return broadTeleMinRate;
	}

	public void setBroadTeleMinRate(Float broadTeleMinRate) {
		this.broadTeleMinRate = broadTeleMinRate;
	}

	public Float getBroadTeleMaxRate() {
		return broadTeleMaxRate;
	}

	public void setBroadTeleMaxRate(Float broadTeleMaxRate) {
		this.broadTeleMaxRate = broadTeleMaxRate;
	}

	public String getBroadTeleTariffName() {
		return broadTeleTariffName;
	}

	public void setBroadTeleTariffName(String broadTeleTariffName) {
		this.broadTeleTariffName = broadTeleTariffName;
	}

	public BroadTeleTariffMaster getBroadTeleTariffMaster() {
		return broadTeleTariffMaster;
	}

	public void setBroadTeleTariffMaster(BroadTeleTariffMaster broadTeleTariffMaster) {
		this.broadTeleTariffMaster = broadTeleTariffMaster;
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
	
}
