package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="SERVICE_MASTER")  
@NamedQueries({
	@NamedQuery(name = "ServiceMastersEntity.findAll", query = "SELECT sm.serviceMasterId,a.accountId,a.accountNo,p.personId,p.firstName,p.lastName,sm.elTariffID,sm.gaTariffID,sm.wtTariffID,sm.swTariffID,sm.othersTariffID,sm.typeOfService,sm.serviceStartDate,sm.serviceEndDate,sm.status,sm.todApplicable,pt.property_No FROM ServiceMastersEntity sm,Property pt INNER JOIN sm.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId ORDER BY sm.serviceMasterId DESC"),
	@NamedQuery(name = "ServiceMastersEntity.findAccountNumbers",query="SELECT a FROM Account a WHERE a.accountStatus = 'Active'"),
	@NamedQuery(name = "ServiceMastersEntity.findPersons",query="select DISTINCT p.personId, p.firstName, p.lastName, p.personType, p.personStyle from Account a INNER JOIN a.person p WHERE a.accountStatus = 'Active'"),
	@NamedQuery(name = "ServiceMastersEntity.setServiceMasterStatus", query = "UPDATE ServiceMastersEntity el SET el.status = :status WHERE el.serviceMasterId = :serviceMasterId"),
	@NamedQuery(name = "ServiceMastersEntity.serviceEndDateUpdate", query = "UPDATE ServiceMastersEntity el SET el.serviceEndDate = :serviceEndDate , el.status='Inactive' WHERE el.serviceMasterId = :serviceMasterId"),
	@NamedQuery(name = "ServiceMastersEntity.findPersonBasedOnAccountIdForFilters", query = "select DISTINCT p.personId, p.firstName, p.lastName, p.personType, p.personStyle from ServiceMastersEntity se INNER JOIN se.accountObj a INNER JOIN a.person p"),
	@NamedQuery(name = "ServiceMastersEntity.getElectricityTariffList", query = "SELECT DISTINCT rm.elTariffID,el.tariffName FROM ELRateMaster rm INNER JOIN rm.elTariffMaster el WHERE el.elTariffID>2 AND el.tariffNodetype='Tariff Rate Node'"),
	@NamedQuery(name = "ServiceMastersEntity.getGasTariffList", query = "SELECT DISTINCT gm.gasTariffId,gt.gastariffName FROM GasRateMaster gm INNER JOIN gm.gasTariffMaster gt WHERE gt.gasTariffId>2"),
	@NamedQuery(name = "ServiceMastersEntity.getWaterTariffList", query = "SELECT DISTINCT wr.wtTariffId,wt.tariffName FROM WTRateMaster wr INNER JOIN wr.wtTariffMaster wt WHERE wt.wtTariffId>2"),
	@NamedQuery(name = "ServiceMastersEntity.getSolidWasteTariffList", query = "SELECT DISTINCT swr.solidWasteTariffId,swt.solidWasteTariffName FROM SolidWasteRateMaster swr INNER JOIN swr.solidWasteTariffMaster swt WHERE swt.solidWasteTariffId>2"),
	@NamedQuery(name = "ServiceMastersEntity.getOthersTariffList", query = "SELECT DISTINCT csr.csTariffId,ct.csTariffName FROM CommonServicesRateMaster csr INNER JOIN csr.commonTariffMaster ct WHERE ct.csTariffID>2"),
	@NamedQuery(name = "ServiceMastersEntity.getBroadBandTariffList", query = "SELECT DISTINCT btr.broadTeleTariffId,bt.broadTeleTariffName FROM BroadTeleRateMaster btr INNER JOIN btr.broadTeleTariffMaster bt WHERE bt.broadTeleTariffId>2"),
	@NamedQuery(name = "ServiceMastersEntity.getServiceMasterByAccountNumber", query = "SELECT el.elTariffID FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService and el.status='Active'"),
	@NamedQuery(name = "ServiceMastersEntity.getWaterTariffId", query = "SELECT el.wtTariffID FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService and el.status='Active'"),
	@NamedQuery(name = "ServiceMastersEntity.getGasTariffId", query = "SELECT el.gaTariffID FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService and el.status='Active'"),
	@NamedQuery(name = "ServiceMastersEntity.getSolidWasteTariffId", query = "SELECT el.swTariffID FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService and el.status='Active' "),
	@NamedQuery(name = "ServiceMastersEntity.getCommonServicesTariffId", query = "SELECT el.othersTariffID FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService and el.status='Active'"),
	@NamedQuery(name = "ServiceMastersEntity.getServiceMasterServicType", query = "SELECT el FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService AND el.serviceStartDate!=NULL AND el.status='Active'"),
	@NamedQuery(name="ServiceMasterEntity.getTodApplicable",query="SELECT el FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:typeOfService"),
    @NamedQuery(name="ServiceMasterEntity.getServiceStartDateWithNullStartDate",query="SELECT el.serviceStartDate FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:serviceTypeList"),
    @NamedQuery(name="ServiceMasterEntity.getServiceStartDate",query="SELECT el.serviceStartDate FROM ServiceMastersEntity el INNER JOIN el.accountObj a WHERE a.accountId =:accountId and el.typeOfService=:serviceTypeList AND el.serviceStartDate!=NULL"),
	@NamedQuery(name="ServiceMastersEntity.getServiceParameterBasedOnMasterId",query="SELECT sp.serviceParameterId FROM ServiceParametersEntity sp INNER JOIN sp.serviceMastersEntity sm INNER JOIN sp.serviceParameterMaster spm WHERE sm.serviceMasterId =:serviceMasterId AND spm.spmName='Average Unit'"),
	@NamedQuery(name = "ServiceMastersEntity.serviceParameterList", query = "SELECT spm.spmId,spm.spmName FROM ServiceParameterMaster spm WHERE spm.serviceType=:serviceType"),
	@NamedQuery(name="ServiceMastersEntity.getServiceParameterValueBasedOnMasterId",query="SELECT sp.serviceParameterValue FROM ServiceParametersEntity sp INNER JOIN sp.serviceMastersEntity sm INNER JOIN sp.serviceParameterMaster spm WHERE sm.serviceMasterId =:serviceMasterId AND spm.spmName='Average Unit'"),
	@NamedQuery(name="TariffCalc.findAccountDetailByServiceType",query="select sme.accountObj.accountId from ServiceMastersEntity sme where sme.accountObj.personId =:personid and sme.accountObj.propertyId=:propertyid and sme.typeOfService=:servicetype"),
})
public class ServiceMastersEntity{

	@Id
	@Column(name="SM_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "serviceMaster_seq", sequenceName = "SERVICE_MASTER_SEQ") 
	@GeneratedValue(generator = "serviceMaster_seq") 
	private int serviceMasterId;
	
	@Transient
	private String tariffName;
	
	@Transient
	private int accountId;
	
	@Transient
	private String accountNo;
	
	@Transient
	private String personName;
	
	@Transient
	private int personId;
	
	/*@Column(name="ACCOUNT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int accountId;*/
	
	@Column(name="TOD_APPLICABLE")
	private String todApplicable;
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Account accountObj;
	
	@Column(name="EL_TARIFF_ID")
	private int elTariffID;
	
	@Column(name="GA_TARIFF_ID")
	private int gaTariffID;
	
	@Column(name="WT_TARIFF_ID")
	private int wtTariffID;
	
	@Column(name="SW_TARIFF_ID")
	private int swTariffID;
	
	@Column(name="OTHERS_TARIFF_ID")
	private int othersTariffID;
	
	@Column(name="BROAD_TELE_TARIFF_ID")
	private Integer broadTeleTariffId;
	
	
	/*@OneToOne	 
	@JoinColumn(name = "EL_TARIFF_ID", insertable = false, updatable = false, nullable = false)
    private ELTariffMaster tariffMasterObj;*/
	
	@Column(name="SERVICE_TYPE")
	private String typeOfService;
		
	public Integer getBroadTeleTariffId() {
		return broadTeleTariffId;
	}

	public void setBroadTeleTariffId(Integer broadTeleTariffId) {
		this.broadTeleTariffId = broadTeleTariffId;
	}

	@Column(name="SERVICE_START_DATE")
	private Date serviceStartDate;
	
	@Column(name="SERVICE_END_DATE")
	private Date serviceEndDate;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Service Master Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getServiceMasterId() {
		return serviceMasterId;
	}

	public void setServiceMasterId(int serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}

	/*public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}*/

	public Account getAccountObj() {
		return accountObj;
	}

	public void setAccountObj(Account accountObj) {
		this.accountObj = accountObj;
	}

		
	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
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
	
	public int getElTariffID() {
		return elTariffID;
	}

	public void setElTariffID(int elTariffID) {
		this.elTariffID = elTariffID;
	}
	
	/*public ELTariffMaster getTariffMasterObj() {
		return tariffMasterObj;
	}

	public void setTariffMasterObj(ELTariffMaster tariffMasterObj) {
		this.tariffMasterObj = tariffMasterObj;
	}*/

	public int getGaTariffID() {
		return gaTariffID;
	}

	public void setGaTariffID(int gaTariffID) {
		this.gaTariffID = gaTariffID;
	}

	public int getWtTariffID() {
		return wtTariffID;
	}

	public void setWtTariffID(int wtTariffID) {
		this.wtTariffID = wtTariffID;
	}

	public int getSwTariffID() {
		return swTariffID;
	}

	public void setSwTariffID(int swTariffID) {
		this.swTariffID = swTariffID;
	}

	public int getOthersTariffID() {
		return othersTariffID;
	}

	public void setOthersTariffID(int othersTariffID) {
		this.othersTariffID = othersTariffID;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  java.util.Date date= new java.util.Date();
	  lastUpdatedDT = new Timestamp(date.getTime());
	 }

	public String getTariffName() {
		return tariffName;
	}

	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getTodApplicable() {
		return todApplicable;
	}

	public void setTodApplicable(String todApplicable) {
		this.todApplicable = todApplicable;
	}
	
	
}
