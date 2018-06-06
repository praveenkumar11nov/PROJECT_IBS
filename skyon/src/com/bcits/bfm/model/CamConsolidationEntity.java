package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="CAM_CONSOLIDATION")  
@NamedQueries({
	@NamedQuery(name = "CamConsolidationEntity.findAll", query = "SELECT cl.ccId,cl.camName,cl.fromDate,cl.toDate,cl.noOfFlats,cl.totalSqft,cl.ratePerFlat,cl.ratePerSqft,cl.noFlatsBilled,cl.billed,cl.blanceAmount,cl.status,cl.chargesType,cl.fixedPerSqft,cl.toBeBilled,cl.paidAmount,cl.rebateRate FROM CamConsolidationEntity cl ORDER BY cl.ccId DESC"),
	@NamedQuery(name = "CamConsolidationEntity.getNoOfFlats", query = "SELECT SUM(b.numOfProperties) FROM Blocks b"),
	@NamedQuery(name = "CamConsolidationEntity.getTotalSQFT", query = "SELECT SUM(p.area) FROM Property p"),
	@NamedQuery(name = "CamConsolidationEntity.getTotalAmountBetweenDates", query = "SELECT SUM(cl.creditAmount) FROM CamLedgerEntity cl WHERE cl.ledgerDate BETWEEN :fromDate and :toDate"),
	@NamedQuery(name = "CamConsolidationEntity.getHeadsData", query = "SELECT cl FROM CamLedgerEntity cl WHERE cl.status='Approved' AND cl.ledgerDate BETWEEN :fromDate and :toDate"),
	@NamedQuery(name = "CamConsolidationEntity.getCamSubLedgerData", query = "SELECT cl FROM CamSubLedgerEntity cl WHERE cl.camLedgerEntity.camLedgerid=:camLedgerid"),
	@NamedQuery(name = "CamConsolidationEntity.getCalcBasis", query = "SELECT tm.calculationBasis FROM TransactionMasterEntity tm WHERE tm.transactionCode=:transactionCode"),
	@NamedQuery(name = "CamConsolidationEntity.getPersonIdFromOwnerProperty", query = "SELECT p.personId FROM OwnerProperty op INNER JOIN op.owner o INNER JOIN o.person p WHERE op.propertyId=:propertyId"),
	@NamedQuery(name = "CamConsolidationEntity.getPersonIdFromTenantProperty", query = "SELECT p.personId FROM TenantProperty tp INNER JOIN tp.tenantId t INNER JOIN t.person p WHERE tp.propertyId=:propertyId"),
	@NamedQuery(name = "CamConsolidationEntity.getAreaOfProperty", query = "SELECT p.area FROM Property p WHERE p.propertyId=:propertyId"),
	@NamedQuery(name = "CamConsolidationEntity.setCamBillsStatus", query = "UPDATE CamConsolidationEntity bp SET bp.status = :status WHERE bp.ccId = :ccId"),
	@NamedQuery(name = "CamConsolidationEntity.setCamLedgerPostedBill", query = "UPDATE CamLedgerEntity bp SET bp.postedToBill = :postedToBill WHERE bp.ccId =:ccId"),
	@NamedQuery(name = "CamConsolidationEntity.setCamLedgerBillPostedDate", query = "UPDATE CamLedgerEntity bp SET bp.postedBillDate = :postedBillDate WHERE bp.ccId =:ccId"),
	@NamedQuery(name = "CamConsolidationEntity.setCamLedgerStatus", query = "UPDATE CamLedgerEntity cl SET cl.status = :status WHERE cl.ccId = :ccId"),
	@NamedQuery(name = "CamConsolidationEntity.readAccountNumbers", query = "SELECT  DISTINCT a.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,a.propertyId FROM BillingWizardEntity bw INNER JOIN bw.accountObj a INNER JOIN a.person p INNER JOIN bw.serviceMastersEntity sm WHERE bw.status='Approved' AND sm.typeOfService='CAM' AND a.accountStatus='Active'"),
	@NamedQuery(name = "CamConsolidationEntity.findAllAccountsOfCamService", query = "SELECT DISTINCT(a.accountId) FROM BillingWizardEntity bw INNER JOIN bw.accountObj a WHERE a.accountStatus='Active' AND bw.status='Approved' AND bw.serviceMastersEntity.typeOfService='CAM'"),
	@NamedQuery(name = "CamConsolidationEntity.isEmptyCamConsolidationEntity", query = "SELECT cm.ccId FROM CamConsolidationEntity cm"),
	@NamedQuery(name = "CamConsolidationEntity.getFromDateFromCamLedger", query = "SELECT cl.ledgerDate FROM CamLedgerEntity cl  WHERE cl.status='Approved' AND cl.ledgerDate IN (SELECT MIN(cl.ledgerDate) FROM CamLedgerEntity cl)"),
	@NamedQuery(name = "CamConsolidationEntity.getCamConsolidationMaxId", query = "SELECT MAX(cm.ccId) FROM CamConsolidationEntity cm WHERE cm.status='Posted'"),
	@NamedQuery(name = "CamConsolidationEntity.getTransactionNameBasedOnCode", query = "SELECT tm.transactionName FROM TransactionMasterEntity tm WHERE tm.transactionCode=:transactionCode AND tm.typeOfService='CAM'"),
	//@NamedQuery(name = "CamConsolidationEntity.getLastBillObj", query = "SELECT MAX(elb.elBillId) FROM ElectricityBillEntity elb WHERE elb.accountId =:accountId AND elb.typeOfService =:typeOfService AND elb.postType=:postType AND (elb.status='Posted' OR elb.status='Paid')"),
	@NamedQuery(name = "CamConsolidationEntity.getLastBillObj", query = "SELECT MAX(elb.elBillId) FROM ElectricityBillEntity elb WHERE elb.accountId =:accountId AND elb.typeOfService =:typeOfService AND elb.postType IN ('Bill','Post Bill','Pre Bill') AND (elb.status='Posted' OR elb.status='Paid')"),
	@NamedQuery(name = "CamConsolidationEntity.getServiceMasterObj", query = "SELECT MAX(sm.serviceMasterId) FROM ServiceMastersEntity sm WHERE sm.accountObj.accountId =:accountId AND sm.typeOfService =:typeOfService AND sm.status='Active'"),
	@NamedQuery(name = "CamConsolidationEntity.getPreviousLedgerPayments", query = "SELECT el FROM ElectricityLedgerEntity el WHERE el.accountId=:accountId AND el.transactionCode =:transactionCode and el.postType='PAYMENT' order by el.elLedgerid DESC"),
	@NamedQuery(name = "CamConsolidationEntity.getTotalBillLineItemAmount", query = "SELECT SUM(bli.balanceAmount) FROM ElectricityBillLineItemEntity bli INNER JOIN bli.electricityBillEntity elb WHERE elb.elBillId=:elBillId  AND bli.transactionCode NOT IN ('CAM_INTEREST')"),
	//@NamedQuery(name = "CamConsolidationEntity.getTotalCamRates", query = "SELECT SUM(tm.camRate) FROM TransactionMasterEntity tm WHERE tm.typeOfService='CAM' AND tm.transactionCode NOT IN('CAM','CAM_ROF','CAM_INTEREST','CAM_SERVICE_TAX','CAM_ECESS','CAM_SHECESS','CAM_PARKING_SLOT','CAM_SWB_CESS_TAX','CAM_KRISHI_KALYAN_CESS_TAX')"),
	@NamedQuery(name = "CamConsolidationEntity.getTotalCamRates", query = "SELECT SUM(tm.camRate) FROM TransactionMasterEntity tm WHERE tm.typeOfService='CAM' AND tm.transactionCode NOT IN('CAM','CAM_ROF','CAM_INTEREST','CAM_SGST_TAX','CAM_CGST_TAX','CAM_PARKING_SLOT','CAM_KRISHI_KALYAN_CESS_TAX','CAM_SERVICE_TAX','CAM_SWB_CESS_TAX','CAM_SGST_Late_Pay','CAM_CGST_Late_Pay')"),
	@NamedQuery(name = "CamConsolidationEntity.getNoOfParkingSlots", query = "SELECT pt.no_of_ParkingSlots FROM Property pt WHERE pt.propertyId=:propertyId"),
	@NamedQuery(name = "CamConsolidationEntity.getAddressDetailsForMail", query = "SELECT addr.address1,c.cityName FROM Address addr,City c WHERE c.cityId=addr.cityId AND addr.addressPrimary='Yes' AND addr.personId=:personId"),
	@NamedQuery(name = "CamConsolidationEntity.getContactDetailsForMail", query = "SELECT c.contactType,c.contactContent FROM Contact c WHERE c.contactPrimary='Yes' AND c.personId=:personId"),
	@NamedQuery(name = "CamConsolidationEntity.getParkingPerSlotAmount", query = "SELECT tm.camRate FROM TransactionMasterEntity tm WHERE tm.transactionCode=:transactionCode"),
	@NamedQuery(name = "CamConsolidationEntity.getParameterValueBasedOnParameterName", query = "SELECT elbp.elBillParameterValue FROM ElectricityBillParametersEntity elbp INNER JOIN elbp.billParameterMasterEntity epm WHERE elbp.electricityBillEntity.elBillId=:elBillId AND epm.serviceType='CAM' AND epm.bvmName=:bvmName"),
	@NamedQuery(name = "CamConsolidationEntity.getParkingNos", query = "SELECT ps.psSlotNo FROM ParkingSlotsAllotment psa INNER JOIN psa.parkingSlots ps INNER JOIN psa.property pt WHERE pt.propertyId=:propertyId"),
})
public class CamConsolidationEntity {

	@Id
	@Column(name="CC_ID")
	@SequenceGenerator(name = "camConsolidation_seq", sequenceName = "CAM_CONSOLIDATION_SEQ") 
	@GeneratedValue(generator = "camConsolidation_seq") 
	private int ccId;
	
	@Transient
	private String calculationBased;
	
	@Column(name="CAM_NAME")
	private String camName;
	
	@Column(name="CHARGES_TYPE")
	private String chargesType;
	
	@Column(name="FROM_DATE")
	private Date fromDate;
	
	@Column(name="TO_DATE")
	private Date toDate;
	
	@Column(name="NO_OF_FLATS")
	private int noOfFlats;
	
	@Column(name="TOTAL_SQFT")
	private int totalSqft;
	
	@Column(name="RATE_PER_FLAT")
	private double ratePerFlat ;
	
	@Column(name="RATE_PER_SQFT")
	private double ratePerSqft;
	
	@Column(name="FIXED_PER_SQFT")
	private double fixedPerSqft;
	
	@Column(name="NO_FLATS_BILLED")
	private int noFlatsBilled;
	
	@Column(name="RATE_PER_REBATE")
	private double rebateRate;
	
	@Column(name="BILLED")
	private double billed; 
	
	@Column(name="TO_BE_BILLED")
	private double toBeBilled;
	
	@Column(name="PAID_AMOUNT")
	private double paidAmount;
	
	@Column(name="BALANCE_AMOUNT")
	private double blanceAmount;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;

	public int getCcId() {
		return ccId;
	}

	public void setCcId(int ccId) {
		this.ccId = ccId;
	}

	public double getBilled() {
		return billed;
	}

	public void setBilled(double billed) {
		this.billed = billed;
	}
	
	public String getCamName() {
		return camName;
	}

	public void setCamName(String camName) {
		this.camName = camName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public int getNoOfFlats() {
		return noOfFlats;
	}

	public void setNoOfFlats(int noOfFlats) {
		this.noOfFlats = noOfFlats;
	}

	public int getTotalSqft() {
		return totalSqft;
	}

	public void setTotalSqft(int totalSqft) {
		this.totalSqft = totalSqft;
	}

	public double getRatePerFlat() {
		return ratePerFlat;
	}

	public void setRatePerFlat(double ratePerFlat) {
		this.ratePerFlat = ratePerFlat;
	}

	public double getRatePerSqft() {
		return ratePerSqft;
	}

	public void setRatePerSqft(double ratePerSqft) {
		this.ratePerSqft = ratePerSqft;
	}

	public int getNoFlatsBilled() {
		return noFlatsBilled;
	}

	public void setNoFlatsBilled(int noFlatsBilled) {
		this.noFlatsBilled = noFlatsBilled;
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
	
	public String getChargesType() {
		return chargesType;
	}

	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}

	public double getFixedPerSqft() {
		return fixedPerSqft;
	}

	public void setFixedPerSqft(double fixedPerSqft) {
		this.fixedPerSqft = fixedPerSqft;
	}

	public double getToBeBilled() {
		return toBeBilled;
	}

	public void setToBeBilled(double toBeBilled) {
		this.toBeBilled = toBeBilled;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getBlanceAmount() {
		return blanceAmount;
	}

	public void setBlanceAmount(double blanceAmount) {
		this.blanceAmount = blanceAmount;
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

	public String getCalculationBased() {
		return calculationBased;
	}

	public void setCalculationBased(String calculationBased) {
		this.calculationBased = calculationBased;
	}

	public double getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(double rebateRate) {
		this.rebateRate = rebateRate;
	}
	
}
