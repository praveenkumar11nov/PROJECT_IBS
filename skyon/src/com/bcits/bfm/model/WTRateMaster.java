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
	@NamedQuery(name = "WTRateMaster.findAll", query = "SELECT el FROM WTRateMaster el ORDER BY el.wtrmid DESC"),
	@NamedQuery(name = "WTRateMaster.findActive", query = "SELECT el FROM WTRateMaster el where el.wtRateMasterStatus=:wtRateMasterStatus ORDER BY el.wtrmid DESC"),
	@NamedQuery(name = "WTRateMaster.getWaterTariffNameByID", query = "SELECT el.tariffName FROM  WTTariffMaster el WHERE el.wtTariffId=:wtTariffId"),
	@NamedQuery(name = "WTRateMaster.setWaterRateMasterStatus", query = "UPDATE WTRateMaster el SET el.wtRateMasterStatus = :wtRateMasterStatus WHERE el.wtrmid = :wtrmid"),
	@NamedQuery(name = "WTRateMaster.getWTRateMaster", query = "SELECT el FROM WTRateMaster el WHERE el.wtrmid = :wtrmid"),
	@NamedQuery(name = "WTRateMaster.findID", query = "SELECT el FROM WTRateMaster el WHERE el.wtrmid = :wtrmid"),
	@NamedQuery(name = "WTRateMaster.getWaterRateMaster",query="SELECT wt From WTRateMaster wt WHERE wt.wtTariffId =:wtTariffId"),
	@NamedQuery(name = "WTRateMaster.getMinMaxDate", query = "SELECT MIN(el.wtValidFrom),MAX(el.wtValidTo) FROM WTRateMaster el WHERE el.wtTariffId = :wtTariffId and el.wtRateName =:wtRateName"),
	@NamedQuery(name = "WTRateMaster.getWaterRateMasterByIdName", query = "SELECT el FROM WTRateMaster el WHERE el.wtTariffId = :wtTariffId and el.wtRateName =:wtRateName"),
})

@Entity
@Table(name = "WT_RATE_MASTER")
public class WTRateMaster 
{
	@Id
	@Column(name="WT_RM_ID")
	@SequenceGenerator(name = "wtratemaster_seq", sequenceName = "WT_RATEMASTER_SEQ")
	@GeneratedValue(generator = "wtratemaster_seq")
	private int wtrmid;
	
	@Column(name="WT_TARIFF_ID")
	private int wtTariffId;
	
	@Column(name="WT_RATE_NAME")
	private String wtRateName;
	
	@Column(name="WT_RATE_DESCRIPTION")
	private String wtRateDescription;
	
	@Column(name="WT_RATE_TYPE")
	private String wtRateType;
	
	@Column(name="WT_RATE_UOM")
	private String wtRateUOM;
	
	@Column(name="WT_VALID_FROM")
	private Date wtValidFrom;
	
	@Column(name="WT_VALID_TO")
	private Date wtValidTo;
	
	@Column(name="WT_STATUS", length = 1020)
	private String wtRateMasterStatus;
	
	@Column(name="WT_CREATED_BY", length = 1020)
	private String wtCreatedBy;
	
	@Column(name="WT_LAST_UPDATED_BY", length = 1020)
	private String wtLastUpdatedBy;
	
	@Column(name="WT_LAST_UPDATED_DT", length = 11)
	private Timestamp wtLastUpdatedDT;
	
	@Column(name="WT_MIN_RATE")
	private Float wtMinRate = 0.0f;
	
	@Column(name="WT_MAX_RATE")
	private Float wtMaxRate= 0.0f;
	
	@Transient
	private String wtTariffName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WT_TARIFF_ID", insertable = false, updatable = false, nullable = false)
	private WTTariffMaster wtTariffMaster;

	public String getWtTariffName() {
		return wtTariffName;
	}

	public void setWtTariffName(String wtTariffName) {
		this.wtTariffName = wtTariffName;
	}

	public int getWtrmid() {
		return wtrmid;
	}

	public void setWtrmid(int wtrmid) {
		this.wtrmid = wtrmid;
	}

	public int getWtTariffId() {
		return wtTariffId;
	}

	public void setWtTariffId(int wtTariffId) {
		this.wtTariffId = wtTariffId;
	}

	public String getWtRateName() {
		return wtRateName;
	}

	public void setWtRateName(String wtRateName) {
		this.wtRateName = wtRateName;
	}

	public String getWtRateDescription() {
		return wtRateDescription;
	}

	public void setWtRateDescription(String wtRateDescription) {
		this.wtRateDescription = wtRateDescription;
	}

	public String getWtRateType() {
		return wtRateType;
	}

	public void setWtRateType(String wtRateType) {
		this.wtRateType = wtRateType;
	}

	public String getWtRateUOM() {
		return wtRateUOM;
	}

	public void setWtRateUOM(String wtRateUOM) {
		this.wtRateUOM = wtRateUOM;
	}

	public Date getWtValidFrom() {
		return wtValidFrom;
	}

	public void setWtValidFrom(Date wtValidFrom) {
		this.wtValidFrom = wtValidFrom;
	}

	public Date getWtValidTo() {
		return wtValidTo;
	}

	public void setWtValidTo(Date wtValidTo) {
		this.wtValidTo = wtValidTo;
	}

	public String getWtRateMasterStatus() {
		return wtRateMasterStatus;
	}

	public void setWtRateMasterStatus(String wtRateMasterStatus) {
		this.wtRateMasterStatus = wtRateMasterStatus;
	}

	public String getWtCreatedBy() {
		return wtCreatedBy;
	}

	public void setWtCreatedBy(String wtCreatedBy) {
		this.wtCreatedBy = wtCreatedBy;
	}

	public String getWtLastUpdatedBy() {
		return wtLastUpdatedBy;
	}

	public void setWtLastUpdatedBy(String wtLastUpdatedBy) {
		this.wtLastUpdatedBy = wtLastUpdatedBy;
	}

	public Timestamp getWtLastUpdatedDT() {
		return wtLastUpdatedDT;
	}

	public void setWtLastUpdatedDT(Timestamp wtLastUpdatedDT) {
		this.wtLastUpdatedDT = wtLastUpdatedDT;
	}

	public Float getWtMinRate() {
		return wtMinRate;
	}

	public void setWtMinRate(Float wtMinRate) {
		this.wtMinRate = wtMinRate;
	}

	public Float getWtMaxRate() {
		return wtMaxRate;
	}

	public void setWtMaxRate(Float wtMaxRate) {
		this.wtMaxRate = wtMaxRate;
	}
	
	public WTTariffMaster getWtTariffMaster() {
		return wtTariffMaster;
	}

	public void setWtTariffMaster(WTTariffMaster wtTariffMaster) {
		this.wtTariffMaster = wtTariffMaster;
	}

	@PrePersist
	 protected void onCreate() {
	  wtLastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  wtCreatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate() 
	 {
	 wtLastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }

}
