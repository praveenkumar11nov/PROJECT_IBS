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
	@NamedQuery(name = "CommonServicesRateMaster.findAll", query = "SELECT el FROM CommonServicesRateMaster el ORDER BY el.csRmId DESC"),
	@NamedQuery(name = "CommonServicesRateMaster.findActive", query = "SELECT el FROM CommonServicesRateMaster el where el.status=:status ORDER BY el.csRmId DESC"),
	@NamedQuery(name = "CommonServicesRateMaster.getWaterTariffNameByID", query = "SELECT el.csTariffName FROM  CommonTariffMaster el WHERE el.csTariffID=:csTariffId"),
	@NamedQuery(name = "CommonServicesRateMaster.serviceEndDateUpdate", query = "UPDATE CommonServicesRateMaster el SET el.csValidTo = :csValidTo WHERE el.csRmId = :csRmId"),
	@NamedQuery(name = "CommonServicesRateMaster.setWaterRateMasterStatus", query = "UPDATE CommonServicesRateMaster el SET el.status = :status WHERE el.csTariffId = :csTariffId"),
	@NamedQuery(name = "CommonServicesRateMaster.getCommonServicesRateMaster", query = "SELECT cs FROM CommonServicesRateMaster cs WHERE cs.csTariffId = :csTariffId"),
	@NamedQuery(name = "CommonServicesRateMaster.getCommonServiceMinMaxDate", query = "SELECT MIN(cs.csValidFrom),MAX(cs.csValidTo) FROM CommonServicesRateMaster cs WHERE cs.csTariffId = :csTariffId and cs.csRateName =:csRateName"),
	@NamedQuery(name = "CommonServicesRateMaster.getCommonServiceMasterByIdName", query = "SELECT cs FROM CommonServicesRateMaster cs WHERE cs.csTariffId = :csTariffId and cs.csRateName =:csRateName"),
	@NamedQuery(name = "CommonServicesRateMaster.getTaxCommonServiceMaster", query = "SELECT cs FROM CommonServicesRateMaster cs WHERE cs.csRateName = :csRateName"),
	//@NamedQuery(name = "SolidWasteRateMaster.findID", query = "SELECT el FROM SolidWasteRateMaster el WHERE el.solidWasteRmId = :solidWasteRmId"),
})

@Entity
@Table(name = "CS_RATE_MASTER")
public class CommonServicesRateMaster
{
	@Id 
	@Column(name="CS_RM_ID")
	@SequenceGenerator(name = "csRatemaster_seq", sequenceName = "CS_RATEMASTER_SEQ")
	@GeneratedValue(generator = "csRatemaster_seq")
	private int csRmId;
	
	@Column(name="CS_TARIFF_ID")
	private int csTariffId;
	
	@Column(name="CS_RATE_NAME")
	private String csRateName;
	
	@Column(name="CS_RATE_DESCRIPTION")
	private String csRateDescription;
	
	@Column(name="CS_RATE_TYPE")
	private String csRateType;
	
	@Column(name="CS_RATE_UOM")
	private String csRateUOM;
	
	@Column(name="CS_VALID_FROM")
	private Date csValidFrom;
	
	@Column(name="CS_VALID_TO")
	private Date csValidTo;
	
	@Column(name="STATUS", length = 1020)
	private String status;
	
	@Column(name="CREATED_BY", length = 1020)
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY", length = 1020)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDT;
	
	@Column(name="CS_MIN_RATE")
	private Float csMinRate = 0.0f;
	
	@Column(name="CS_MAX_RATE")
	private Float csMaxRate= 0.0f;
	
	@Column(name="CS_TOD_TYPE")
	private String csTodType;
	
	@Transient
	private String csTariffName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CS_TARIFF_ID", insertable = false, updatable = false, nullable = false)
	private CommonTariffMaster commonTariffMaster;

	public int getCsRmId() {
		return csRmId;
	}

	public void setCsRmId(int csRmId) {
		this.csRmId = csRmId;
	}

	public int getCsTariffId() {
		return csTariffId;
	}

	public void setCsTariffId(int csTariffId) {
		this.csTariffId = csTariffId;
	}

	public String getCsRateName() {
		return csRateName;
	}

	public void setCsRateName(String csRateName) {
		this.csRateName = csRateName;
	}

	public String getCsRateDescription() {
		return csRateDescription;
	}

	public void setCsRateDescription(String csRateDescription) {
		this.csRateDescription = csRateDescription;
	}

	public String getCsRateType() {
		return csRateType;
	}

	public void setCsRateType(String csRateType) {
		this.csRateType = csRateType;
	}

	public String getCsRateUOM() {
		return csRateUOM;
	}

	public void setCsRateUOM(String csRateUOM) {
		this.csRateUOM = csRateUOM;
	}

	public Date getCsValidFrom() {
		return csValidFrom;
	}

	public void setCsValidFrom(Date csValidFrom) {
		this.csValidFrom = csValidFrom;
	}

	public Date getCsValidTo() {
		return csValidTo;
	}

	public void setCsValidTo(Date csValidTo) {
		this.csValidTo = csValidTo;
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

	public Float getCsMinRate() {
		return csMinRate;
	}

	public void setCsMinRate(Float csMinRate) {
		this.csMinRate = csMinRate;
	}

	public Float getCsMaxRate() {
		return csMaxRate;
	}

	public void setCsMaxRate(Float csMaxRate) {
		this.csMaxRate = csMaxRate;
	}

	public String getCsTariffName() {
		return csTariffName;
	}

	public void setCsTariffName(String csTariffName) {
		this.csTariffName = csTariffName;
	}

	public CommonTariffMaster getCommonTariffMaster() {
		return commonTariffMaster;
	}

	public void setCommonTariffMaster(CommonTariffMaster commonTariffMaster) {
		this.commonTariffMaster = commonTariffMaster;
	}

	public String getCsTodType() {
		return csTodType;
	}

	public void setCsTodType(String csTodType) {
		this.csTodType = csTodType;
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
