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
	@NamedQuery(name = "SolidWasteRateMaster.findAll", query = "SELECT el FROM SolidWasteRateMaster el ORDER BY el.solidWasteRmId DESC"),
	@NamedQuery(name = "SolidWasteRateMaster.findActive", query = "SELECT el FROM SolidWasteRateMaster el where el.status=:status ORDER BY el.solidWasteRmId DESC"),
	@NamedQuery(name = "SolidWasteRateMaster.getWaterTariffNameByID", query = "SELECT el.solidWasteTariffName FROM  SolidWasteTariffMaster el WHERE el.solidWasteTariffId=:solidWasteTariffId"),
	@NamedQuery(name = "SolidWasteRateMaster.serviceEndDateUpdate", query = "UPDATE SolidWasteRateMaster el SET el.solidWasteValidTo = :solidWasteValidTo WHERE el.solidWasteRmId = :solidWasteRmId"),
	@NamedQuery(name = "SolidWasteRateMaster.setWaterRateMasterStatus", query = "UPDATE SolidWasteRateMaster el SET el.status = :status WHERE el.solidWasteRmId = :solidWasteRmId"),
//	@NamedQuery(name = "GasRateMaster.getWTRateMaster", query = "SELECT el FROM WTRateMaster el WHERE el.wtrmid = :wtrmid"),
	@NamedQuery(name = "SolidWasteRateMaster.findID", query = "SELECT el FROM SolidWasteRateMaster el WHERE el.solidWasteRmId = :solidWasteRmId"),
	@NamedQuery(name = "SolidWasteRateMaster.getSolidWasteRateMaster",query="SELECT sw From SolidWasteRateMaster sw WHERE sw.solidWasteTariffId =:solidWasteTariffId"),
	@NamedQuery(name = "SolidWasteRateMaster.getSolidWasteMinMaxDate", query = "SELECT MIN(sw.solidWasteValidFrom),MAX(sw.solidWasteValidTo) FROM SolidWasteRateMaster sw WHERE sw.solidWasteTariffId = :solidWasteTariffId and sw.solidWasteRateName =:solidWasteRateName"),
	@NamedQuery(name = "SolidWasteRateMaster.getSolidWasteRateMasterByIdName", query = "SELECT sw FROM SolidWasteRateMaster sw WHERE sw.solidWasteTariffId = :solidWasteTariffId and sw.solidWasteRateName =:solidWasteRateName"),
	
})


@Entity
@Table(name = "SOLID_WASTE_RATE_MASTER")
public class SolidWasteRateMaster 
{
	@Id
	@Column(name="SOLID_WASTE_RM_ID")
	@SequenceGenerator(name = "solidWasteRatemaster_seq", sequenceName = "SOLID_WASTE_RATEMASTER_SEQ")
	@GeneratedValue(generator = "solidWasteRatemaster_seq")
	private int solidWasteRmId;
	
	@Column(name="SOLID_WASTE_TARIFF_ID")
	private int solidWasteTariffId;
	
	@Column(name="SOLID_WASTE_RATE_NAME")
	private String solidWasteRateName;
	
	@Column(name="SOLID_WASTE_RATE_DESCRIPTION")
	private String solidWasteRateDescription;
	
	@Column(name="SOLID_WASTE_RATE_TYPE")
	private String solidWasteRateType;
	
	@Column(name="SOLID_WASTE_RATE_UOM")
	private String solidWasteRateUOM;
	
	@Column(name="SOLID_WASTE_VALID_FROM")
	private Date solidWasteValidFrom;
	
	@Column(name="SOLID_WASTE_VALID_TO")
	private Date solidWasteValidTo;
	
	@Column(name="STATUS", length = 1020)
	private String status;
	
	@Column(name="CREATED_BY", length = 1020)
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY", length = 1020)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDT;
	
	@Column(name="SOLID_WASTE_MIN_RATE")
	private Float solidWasteMinRate = 0.0f;
	
	@Column(name="SOLID_WASTE_MAX_RATE")
	private Float solidWasteMaxRate= 0.0f;
	
	@Transient
	private String solidWasteTariffName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOLID_WASTE_TARIFF_ID", insertable = false, updatable = false, nullable = false)
	private SolidWasteTariffMaster solidWasteTariffMaster;

	public int getSolidWasteRmId() {
		return solidWasteRmId;
	}

	public void setSolidWasteRmId(int solidWasteRmId) {
		this.solidWasteRmId = solidWasteRmId;
	}

	public int getSolidWasteTariffId() {
		return solidWasteTariffId;
	}

	public void setSolidWasteTariffId(int solidWasteTariffId) {
		this.solidWasteTariffId = solidWasteTariffId;
	}

	public String getSolidWasteRateName() {
		return solidWasteRateName;
	}

	public void setSolidWasteRateName(String solidWasteRateName) {
		this.solidWasteRateName = solidWasteRateName;
	}

	public String getSolidWasteRateDescription() {
		return solidWasteRateDescription;
	}

	public void setSolidWasteRateDescription(String solidWasteRateDescription) {
		this.solidWasteRateDescription = solidWasteRateDescription;
	}

	public String getSolidWasteRateType() {
		return solidWasteRateType;
	}

	public void setSolidWasteRateType(String solidWasteRateType) {
		this.solidWasteRateType = solidWasteRateType;
	}

	public String getSolidWasteRateUOM() {
		return solidWasteRateUOM;
	}

	public void setSolidWasteRateUOM(String solidWasteRateUOM) {
		this.solidWasteRateUOM = solidWasteRateUOM;
	}

	public Date getSolidWasteValidFrom() {
		return solidWasteValidFrom;
	}

	public void setSolidWasteValidFrom(Date solidWasteValidFrom) {
		this.solidWasteValidFrom = solidWasteValidFrom;
	}

	public Date getSolidWasteValidTo() {
		return solidWasteValidTo;
	}

	public void setSolidWasteValidTo(Date solidWasteValidTo) {
		this.solidWasteValidTo = solidWasteValidTo;
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

	public Float getSolidWasteMinRate() {
		return solidWasteMinRate;
	}

	public void setSolidWasteMinRate(Float solidWasteMinRate) {
		this.solidWasteMinRate = solidWasteMinRate;
	}

	public Float getSolidWasteMaxRate() {
		return solidWasteMaxRate;
	}

	public void setSolidWasteMaxRate(Float solidWasteMaxRate) {
		this.solidWasteMaxRate = solidWasteMaxRate;
	}

	public String getSolidWasteTariffName() {
		return solidWasteTariffName;
	}

	public void setSolidWasteTariffName(String solidWasteTariffName) {
		this.solidWasteTariffName = solidWasteTariffName;
	}

	public SolidWasteTariffMaster getSolidWasteTariffMaster() {
		return solidWasteTariffMaster;
	}

	public void setSolidWasteTariffMaster(
			SolidWasteTariffMaster solidWasteTariffMaster) {
		this.solidWasteTariffMaster = solidWasteTariffMaster;
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
