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
import javax.validation.constraints.NotNull;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="EL_RATE_MASTER")
@NamedQueries({
	@NamedQuery(name = "ELRateMaster.findAll", query = "SELECT et.stateName,et.tariffName, el.elrmid,el.elTariffID,el.rateName,el.rateDescription,el.rateType,el.validFrom,el.validTo,el.rateUOM,el.minRate,el.status,el.maxRate,el.todType,el.elTariffID,el.rateNameCategory FROM ELRateMaster el,ELTariffMaster et where el.status=:status and el.elTariffID=et.elTariffID ORDER BY el.elrmid DESC"),
	@NamedQuery(name = "ELRateMaster.findAll1", query = "SELECT et.stateName,et.tariffName, el.elrmid,el.elTariffID,el.rateName,el.rateDescription,el.rateType,el.validFrom,el.validTo,el.rateUOM,el.minRate,el.status,el.maxRate,el.todType,el.elTariffID,el.rateNameCategory FROM ELRateMaster el,ELTariffMaster et  where el.elTariffID=et.elTariffID ORDER BY el.elrmid DESC"),
	@NamedQuery(name = "ELRateMaster.setELRateMasterStatus", query = "UPDATE ELRateMaster el SET el.status = :status WHERE el.elrmid = :elrmid"),
	@NamedQuery(name = "ELRateMaster.getRateDescriptionList", query = "SELECT DISTINCT(LOWER(el.rateDescription)) FROM ELRateMaster el"),
	@NamedQuery(name = "ELRateMaster.getRateTypeList", query = "SELECT DISTINCT(LOWER(el.rateType)) FROM ELRateMaster el"),
	@NamedQuery(name = "ELRateMaster.getRateUOMList", query = "SELECT DISTINCT(LOWER(el.rateUOM)) FROM ELRateMaster el"),
	@NamedQuery(name = "ELRateMaster.findMaxDate", query = "SELECT MAX(el.validTo) FROM ELRateMaster el"),
	
	@NamedQuery(name = "ELRateMaster.findMaxDateByRateNameTariffID", query = "SELECT ADD_MONTHS (MAX(el.validTo), 1) FROM ELRateMaster el WHERE el.rateName = :rateName and el.elTariffID = :elTariffID and el.elTariffMaster.stateName=:stateName and el.rateNameCategory=:category"),
	@NamedQuery(name = "ELRateMaster.findID", query = "SELECT el FROM ELRateMaster el WHERE el.elrmid = :elrmid"),
	@NamedQuery(name = "ELRateMaster.getMinMaxDate", query = "SELECT MIN(el.validFrom),MAX(el.validTo) FROM ELRateMaster el WHERE el.elTariffID = :tariffMasterId and el.rateName =:rateName"),
	@NamedQuery(name = "ELRateMaster.getRateMasterByIdName", query = "SELECT el FROM ELRateMaster el WHERE el.elTariffID = :elTariffID and el.rateName =:rateName"),
	
	//@NamedQuery(name = "ELRateMaster.getByTariffId", query = "SELECT el FROM ELRateMaster el WHERE el.elTariffID = :tariffMasterId and el.rateName =:rateName and el.validFrom <= :validFrom and el.validTo >= :validTo"),
	@NamedQuery(name = "ELRateMaster.getRateId",query = "SELECT el FROM ELRateMaster el WHERE el.elTariffID = :tariffMasterId and el.rateName =:rateName") ,
	@NamedQuery(name = "ELRateslab.getElrsId",query="SELECT e1 FROM ELRateSlabs e1 WHERE e1.elrmId=:elrmId AND e1.slabsNo=1 "), 
	@NamedQuery(name = "ELTodrates.getdetail",query="SELECT e1 FROM ELTodRates e1 WHERE e1.elrsId=:elrsId ORDER BY  e1.eltiId ASC"),
	@NamedQuery(name = "ELRateMaster.rateMasterEndDateUpdate", query = "UPDATE ELRateMaster el SET el.validTo = :validTo WHERE el.elrmid = :elrmid"),
	//@NamedQuery(name = "ELRateslab.getElrsIdSlabWise",query="SELECT e1 FROM ELRateSlabs e1 WHERE e1.elrmId=:elrmId ORDER BY e1.slabsNo ASC" ),
	@NamedQuery(name = "ELRateMaster.elTariffMastersListERP",query="SELECT e1 FROM ELRateMaster e1 WHERE e1.elTariffID=:elTariffID and e1.rateName=:rateName and e1.validFrom >=:validFrom and e1.validTo <=:validTo"),
	//@NamedQuery(name = "ELRateMaster.getELRateMasterListByName",query="SELECT e1 FROM ELRateMaster e1 WHERE e1.elTariffID=:elTariffID and e1.rateName=:rateName"),
	//@NamedQuery(name = "ELRateMaster.getELRateMasterListByName1",query="SELECT e1 FROM ELRateMaster e1 WHERE e1.elTariffID=:elTariffID"),
	@NamedQuery(name = "ELRateMaster.getRateMasterByRateName",query="SELECT e1 FROM ELRateMaster e1 WHERE e1.elTariffID=:elTariffID and e1.rateName=:rateName and e1.status='Active'"),
	@NamedQuery(name = "ELRateMaster.getRateMaster",query="SELECT el From ELRateMaster el WHERE el.elTariffID =:elTariffID  AND el.status='Active'"), 
	
	@NamedQuery(name = "ELRateMaster.getRateMasterEC",query="SELECT e1.elrmid FROM ELRateMaster e1 WHERE e1.elTariffID=:elTariffID and e1.rateName=:rateName and e1.status='Active'"),
	@NamedQuery(name = "ELRateMaster.getRateMasterDomesticGeneral",query="SELECT wt.wtrmid FROM WTRateMaster wt WHERE wt.wtTariffId=:wtTariffId and wt.wtRateName=:rateName and wt.wtRateMasterStatus='Active'"),
	@NamedQuery(name = "ELRateMaster.getRateMasterGas",query="SELECT gas.gasrmid FROM GasRateMaster gas WHERE gas.gasTariffId=:gasTariffId and gas.gasRateName=:rateName and gas.status='Active'"),
	@NamedQuery(name = "ELRateMaster.getRateMasterSW",query="SELECT sw.solidWasteRmId FROM SolidWasteRateMaster sw WHERE sw.solidWasteTariffId=:swTariffId and sw.solidWasteRateName=:rateName and sw.status='Active'"),
	@NamedQuery(name = "ELRateMaster.getRateMasterOT",query="SELECT ot.csRmId FROM CommonServicesRateMaster ot WHERE ot.csTariffId=:othersTariffId and ot.csRateName=:rateName and ot.status='Active'"),
	@NamedQuery(name = "ELRateMaster.getRateMasterByIdNameHariyana", query = "SELECT el FROM ELRateMaster el WHERE el.elTariffID = :elTariffID and el.rateName =:rateName and el.rateNameCategory =:category"),
	@NamedQuery(name = "ELRateMaster.getRateMasterByRateNameHariyan",query="SELECT e1 FROM ELRateMaster e1 WHERE e1.elTariffID=:elTariffID and e1.rateName=:rateName and e1.rateNameCategory=:rateNameCategory and e1.status='Active'"),
	@NamedQuery(name = "ELRateMaster.getMinMaxDate1", query = "SELECT MIN(el.validFrom),MAX(el.validTo) FROM ELRateMaster el WHERE el.elTariffID = :tariffMasterId and el.rateName =:rateName and el.rateNameCategory=:rateNameCategory"),
	@NamedQuery(name = "ELRateMaster.getRateMasterByIdName1", query = "SELECT el FROM ELRateMaster el WHERE el.elTariffID = :elTariffID and el.rateName=:rateName and el.rateNameCategory=:rateNameCategory"),
})   
public class ELRateMaster        
{
	@Id
	@Column(name="ELRM_ID")
	@SequenceGenerator(name = "elratemaster_seq", sequenceName = "ELRATEMASTER_SEQ")
	@GeneratedValue(generator = "elratemaster_seq")
	private int elrmid;
	
	@Column(name="EL_TARIFF_ID")
	private int elTariffID;
	
	@Column(name="RATE_NAME")
	private String rateName;
	
	@Column(name="CATEGORY")
	private String rateNameCategory;
	
	@Column(name="RATE_DESCRIPTION")
	private String rateDescription;
	
	@NotNull
	@Column(name="RATE_UNIT")
	private int rateUnit;
	
	@NotNull
	@Column(name="RATE_TYPE")
	private String rateType;
	
	@Column(name="RATE_UOM")
	private String rateUOM;
	
	@Column(name="VALID_FROM")
	private Date validFrom;
	
	@Column(name="VALID_TO")
	private Date validTo;
	
	@Column(name="STATUS", length = 1020)
	private String status;
	
	@Column(name="CREATED_BY", length = 1020)
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY", length = 1020)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDT;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EL_TARIFF_ID", insertable = false, updatable = false, nullable = false)
	private ELTariffMaster elTariffMaster;
	
	@Column(name="MIN_RATE")
	private Float minRate = 0.0f;
	
	@Column(name="MAX_RATE")
	private Float maxRate= 0.0f;
	
	@Column(name="TOD_TYPE")
	private String todType;

@Transient
private String tariffName;

@Transient
private String stateName;

	public int getElrmid() {
		return elrmid;
	}

	public void setElrmid(int elrmid) {
		this.elrmid = elrmid;
	}

	public int getElTariffID() {
		return elTariffID;
	}

	public void setElTariffID(int elTariffID) {
		this.elTariffID = elTariffID;
	}

	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public String getRateDescription() {
		return rateDescription;
	}

	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	public int getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(int rateUnit) {
		this.rateUnit = rateUnit;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateUOM() {
		return rateUOM;
	}

	public void setRateUOM(String rateUOM) {
		this.rateUOM = rateUOM;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
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

	public ELTariffMaster getElTariffMaster() {
		return elTariffMaster;
	}

	public void setElTariffMaster(ELTariffMaster elTariffMaster) {
		this.elTariffMaster = elTariffMaster;
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

	public Float getMinRate() {
		return minRate;
	}

	public void setMinRate(Float minRate) {
		this.minRate = minRate;
	}

	public Float getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Float maxRate) {
		this.maxRate = maxRate;
	}

	public String getTodType() {
		return todType;
	}

	public void setTodType(String todType) {
		this.todType = todType;
	}

	public String getTariffName() {
		return tariffName;
	}

	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
	}
	
	public String getStateName() {
	return stateName;
}

public void setStateName(String stateName) {
	this.stateName = stateName;
}

public String getRateNameCategory() {
	return rateNameCategory;
}

public void setRateNameCategory(String rateNameCategory) {
	this.rateNameCategory = rateNameCategory;
}



}
