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
	@NamedQuery(name = "GasRateMaster.findAll", query = "SELECT el FROM GasRateMaster el ORDER BY el.gasrmid DESC"),
	@NamedQuery(name = "GasRateMaster.findActive", query = "SELECT el FROM GasRateMaster el where el.status=:status ORDER BY el.gasrmid DESC"),
	@NamedQuery(name = "GasRateMaster.getWaterTariffNameByID", query = "SELECT el.gastariffName FROM  GasTariffMaster el WHERE el.gasTariffId=:gasTariffId"),
	@NamedQuery(name = "GasRateMaster.serviceEndDateUpdate", query = "UPDATE GasRateMaster el SET el.gasValidTo = :gasValidTo WHERE el.gasrmid = :gasrmid"),
	@NamedQuery(name = "GasRateMaster.setWaterRateMasterStatus", query = "UPDATE GasRateMaster el SET el.status = :status WHERE el.gasrmid = :gasrmid"),
//	@NamedQuery(name = "GasRateMaster.getWTRateMaster", query = "SELECT el FROM WTRateMaster el WHERE el.wtrmid = :wtrmid"),
	@NamedQuery(name = "GasRateMaster.getGasRateMaster",query="SELECT gas From GasRateMaster gas WHERE gas.gasTariffId =:gasTariffId"),
	@NamedQuery(name = "GasRateMaster.findID", query = "SELECT el FROM GasRateMaster el WHERE el.gasrmid = :gasrmid"),
	@NamedQuery(name = "GasRateMaster.getGasMinMaxDate", query = "SELECT MIN(el.gasValidFrom),MAX(el.gasValidTo) FROM GasRateMaster el WHERE el.gasTariffId = :gasTariffId and el.gasRateName =:gasRateName"),
	@NamedQuery(name = "GasRateMaster.getGasRateMasterByIdName", query = "SELECT el FROM GasRateMaster el WHERE el.gasTariffId = :gasTariffId and el.gasRateName =:gasRateName"),
})

@Entity
@Table(name = "GAS_RATE_MASTER")
public class GasRateMaster 
{
	@Id
	@Column(name="GAS_RM_ID")
	@SequenceGenerator(name = "gasratemaster_seq", sequenceName = "GAS_RATEMASTER_SEQ")
	@GeneratedValue(generator = "gasratemaster_seq")
	private int gasrmid;
	
	@Column(name="GAS_TARIFF_ID")
	private int gasTariffId;
	
	@Column(name="GAS_RATE_NAME")
	private String gasRateName;
	
	@Column(name="GAS_RATE_DESCRIPTION")
	private String gasRateDescription;
	
	@Column(name="GAS_RATE_TYPE")
	private String gasRateType;
	
	@Column(name="GAS_RATE_UOM")
	private String gasRateUOM;
	
	@Column(name="GAS_VALID_FROM")
	private Date gasValidFrom;
	
	@Column(name="GAS_VALID_TO")
	private Date gasValidTo;
	
	@Column(name="STATUS", length = 1020)
	private String status;
	
	@Column(name="CREATED_BY", length = 1020)
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY", length = 1020)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDT;
	
	@Column(name="GAS_MIN_RATE")
	private Float gasMinRate = 0.0f;
	
	@Column(name="GAS_MAX_RATE")
	private Float gasMaxRate= 0.0f;
	
	@Transient
	private String gasTariffName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GAS_TARIFF_ID", insertable = false, updatable = false, nullable = false)
	private GasTariffMaster gasTariffMaster;

	public int getGasrmid() {
		return gasrmid;
	}

	public void setGasrmid(int gasrmid) {
		this.gasrmid = gasrmid;
	}

	public int getGasTariffId() {
		return gasTariffId;
	}

	public void setGasTariffId(int gasTariffId) {
		this.gasTariffId = gasTariffId;
	}

	public String getGasRateName() {
		return gasRateName;
	}

	public void setGasRateName(String gasRateName) {
		this.gasRateName = gasRateName;
	}

	public String getGasRateDescription() {
		return gasRateDescription;
	}

	public void setGasRateDescription(String gasRateDescription) {
		this.gasRateDescription = gasRateDescription;
	}

	public String getGasRateType() {
		return gasRateType;
	}

	public void setGasRateType(String gasRateType) {
		this.gasRateType = gasRateType;
	}

	public String getGasRateUOM() {
		return gasRateUOM;
	}

	public void setGasRateUOM(String gasRateUOM) {
		this.gasRateUOM = gasRateUOM;
	}

	public Date getGasValidFrom() {
		return gasValidFrom;
	}

	public void setGasValidFrom(Date gasValidFrom) {
		this.gasValidFrom = gasValidFrom;
	}

	public Date getGasValidTo() {
		return gasValidTo;
	}

	public void setGasValidTo(Date gasValidTo) {
		this.gasValidTo = gasValidTo;
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

	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}

	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Float getGasMinRate() {
		return gasMinRate;
	}

	public void setGasMinRate(Float gasMinRate) {
		this.gasMinRate = gasMinRate;
	}

	public Float getGasMaxRate() {
		return gasMaxRate;
	}

	public void setGasMaxRate(Float gasMaxRate) {
		this.gasMaxRate = gasMaxRate;
	}

	public String getGasTariffName() {
		return gasTariffName;
	}

	public void setGasTariffName(String gasTariffName) {
		this.gasTariffName = gasTariffName;
	}

	public GasTariffMaster getGasTariffMaster() {
		return gasTariffMaster;
	}

	public void setGasTariffMaster(GasTariffMaster gasTariffMaster) {
		this.gasTariffMaster = gasTariffMaster;
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
