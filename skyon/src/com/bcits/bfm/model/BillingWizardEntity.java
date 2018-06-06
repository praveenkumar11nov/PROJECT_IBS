package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="BILLING_WIZARD")  
@NamedQueries({
	@NamedQuery(name="BillingWizardEntity.findAll", query = "SELECT bw.wizardId,a.accountNo,a.accountId,p.firstName,p.lastName,bw.status,sm.typeOfService,pt.property_No FROM BillingWizardEntity bw,Property pt INNER JOIN bw.accountObj a INNER JOIN a.person p INNER JOIN bw.serviceMastersEntity sm WHERE pt.propertyId=a.propertyId ORDER BY bw.wizardId DESC"),
	@NamedQuery(name="BillingWizardEntity.findMeterNumbers", query = "SELECT m.elMeterId,m.meterSerialNo FROM ElectricityMetersEntity m where m.typeOfServiceForMeters=:serviceType AND m.account.accountId=null AND m.meterStatus='In Stock' ORDER BY m.meterSerialNo ASC"),
	@NamedQuery(name="BillingWizardEntity.approvedAccountNumberInBillingWizard",query="UPDATE BillingWizardEntity bw SET bw.status = :status WHERE bw.wizardId = :wizardId"),
	@NamedQuery(name="BillingWizardEntity.approvedAccountInAccounts",query="UPDATE Account a SET a.accountStatus = :status WHERE a.accountId = :accountId"),
	@NamedQuery(name="BillingWizardEntity.approvedMeter",query="UPDATE ElectricityMetersEntity m SET m.meterStatus = :status WHERE m.elMeterId = :elMeterId"),
	@NamedQuery(name="BillingWizardEntity.approvedServicePoint",query="UPDATE ServicePointEntity sp SET sp.status = :status WHERE sp.servicePointId = :servicePointId"),
	@NamedQuery(name="BillingWizardEntity.approvedServiceMaster",query="UPDATE ServiceMastersEntity sp SET sp.status = :status WHERE sp.serviceMasterId = :serviceMasterId"),
	@NamedQuery(name="BillingWizardEntity.getServiceRouteNames", query = "SELECT sr.srId,sr.routeName FROM ServiceRoute sr"),
	@NamedQuery(name="BillingWizardEntity.findServiceSubRouteNames", query = "SELECT sr.srId,sr.subRouteName FROM ServiceRoute sr"),
	@NamedQuery(name="BillingWizardEntity.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM BillingWizardEntity ac INNER JOIN ac.accountObj a"),
	@NamedQuery(name="BillingWizardEntity.commonFilterForPropertyNoUrl", query = "SELECT DISTINCT pt.property_No FROM BillingWizardEntity ac INNER JOIN ac.accountObj a,Property pt WHERE pt.propertyId=a.propertyId ORDER BY pt.property_No ASC"),
	@NamedQuery(name="BillingWizardEntity.approvedServiceMasterParameters",query="UPDATE ServiceParametersEntity sp SET sp.status = :status WHERE sp.serviceMastersEntity.serviceMasterId = :serviceMasterId"),
    @NamedQuery(name="BillingWizardEntity.getUnmetetedAccountId",query="SELECT b.serviceMetered FROM BillingWizardEntity b WHERE   b.serviceMastersEntity.typeOfService=:serviceTypeList AND b.accountObj.accountId=:accountId"),
    @NamedQuery(name="BillingWizardEntity.accountCheck", query = "SELECT sm.serviceMasterId FROM ServiceMastersEntity sm INNER JOIN sm.accountObj a WHERE sm.typeOfService=:service AND a.accountNo=:accountNumber"),
    @NamedQuery(name = "BillingWizardEntity.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle FROM BillingWizardEntity bw INNER JOIN bw.accountObj a INNER JOIN a.person p"),
    @NamedQuery(name = "BillingWizardEntity.getPossessionDate", query = "select p.tenancyHandoverDate from Property p where p.propertyId = (select a.propertyId from BillingWizardEntity bwe inner join bwe.accountObj a where bwe.wizardId=:wizardId)"),
      
})
public class BillingWizardEntity {

	@Id
	@Column(name="WIZARD_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "billingWizardSeq", sequenceName = "BILLING_WIZARD_SEQ") 
	@GeneratedValue(generator = "billingWizardSeq")
	private int wizardId;
		
	/*@Column(name="ACCOUNT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int accountId;*/
	
	@Transient
	private String accountStatus;
	
	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Account accountObj;
	
	/*@Column(name="SPM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int spmId;*/
	
	@OneToOne
	@JoinColumn(name = "SM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private ServiceMastersEntity serviceMastersEntity;
	
	@Transient
	private String serviceParameterStatus;
	
	/*@Column(name="ELL_ID", unique=true, nullable=false, precision=11, scale=0)
	private int elLedgerid;
	
	@OneToOne
	@JoinColumn(name = "ELL_ID", insertable = false, updatable = false, nullable = false)
    private ElectricityLedgerEntity ledgerEntity;*/
	
	/*@Column(name="ELM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int elMeterId;*/
	
	@OneToOne
	@JoinColumn(name = "ELM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private ElectricityMetersEntity metersEntity;
	
	@Transient
	private String meterStatus;
	
	@Column(name="STATUS")
	private String status;
	
	/*@Column(name="SR_ID", unique=true, nullable=false, precision=11, scale=0)
	private int srId;*/
	
	/*@OneToOne
	@JoinColumn(name = "SR_ID")
    private ServiceRoute serviceRoute;*/
	
	@Transient
	private String serviceRouteStatus;
	
	/*@Column(name="SP_ID", unique=true, nullable=false, precision=11, scale=0)
	private int servicePointId;*/
	
	/*@OneToOne
	@JoinColumn(name = "SP_ID")
    private ServicePointEntity servicePointEntity;*/
	
	@Transient
	private String servicePointStatus;
	
	@Column(name="METERED")
	private String serviceMetered;

	public int getWizardId() {
		return wizardId;
	}

	public void setWizardId(int wizardId) {
		this.wizardId = wizardId;
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

	/*public int getSpmId() {
		return spmId;
	}

	public void setSpmId(int spmId) {
		this.spmId = spmId;
	}*/

	/*public int getElLedgerid() {
		return elLedgerid;
	}

	public void setElLedgerid(int elLedgerid) {
		this.elLedgerid = elLedgerid;
	}

	public ElectricityLedgerEntity getLedgerEntity() {
		return ledgerEntity;
	}

	public void setLedgerEntity(ElectricityLedgerEntity ledgerEntity) {
		this.ledgerEntity = ledgerEntity;
	}*/

	/*public int getElMeterId() {
		return elMeterId;
	}

	public void setElMeterId(int elMeterId) {
		this.elMeterId = elMeterId;
	}*/

	public ElectricityMetersEntity getMetersEntity() {
		return metersEntity;
	}

	public ServiceMastersEntity getServiceMastersEntity() {
		return serviceMastersEntity;
	}

	public void setServiceMastersEntity(ServiceMastersEntity serviceMastersEntity) {
		this.serviceMastersEntity = serviceMastersEntity;
	}

	public void setMetersEntity(ElectricityMetersEntity metersEntity) {
		this.metersEntity = metersEntity;
	}

	/*public int getSrId() {
		return srId;
	}

	public void setSrId(int srId) {
		this.srId = srId;
	}*/

	/*public ServiceRoute getServiceRoute() {
		return serviceRoute;
	}

	public void setServiceRoute(ServiceRoute serviceRoute) {
		this.serviceRoute = serviceRoute;
	}*/

	/*public int getServicePointId() {
		return servicePointId;
	}

	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}*/

	/*public ServicePointEntity getServicePointEntity() {
		return servicePointEntity;
	}

	public void setServicePointEntity(ServicePointEntity servicePointEntity) {
		this.servicePointEntity = servicePointEntity;
	}*/

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getServiceParameterStatus() {
		return serviceParameterStatus;
	}

	public void setServiceParameterStatus(String serviceParameterStatus) {
		this.serviceParameterStatus = serviceParameterStatus;
	}

	public String getMeterStatus() {
		return meterStatus;
	}

	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}

	public String getServiceRouteStatus() {
		return serviceRouteStatus;
	}

	public void setServiceRouteStatus(String serviceRouteStatus) {
		this.serviceRouteStatus = serviceRouteStatus;
	}

	public String getServicePointStatus() {
		return servicePointStatus;
	}

	public void setServicePointStatus(String servicePointStatus) {
		this.servicePointStatus = servicePointStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceMetered() {
		return serviceMetered;
	}

	public void setServiceMetered(String serviceMetered) {
		this.serviceMetered = serviceMetered;
	}
	
}
