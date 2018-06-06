package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="BILL")  
@NamedQueries({
	@NamedQuery(name = "ElectricityBillsEntity.readBillsMonthWise", query = "SELECT elb.elBillId,elb.typeOfService,elb.cbId,elb.accountId,elb.elBillDate,elb.postType,elb.billDate,elb.billDueDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.fromDate,elb.billType,elb.avgCount,elb.avgAmount,elb.advanceClearedAmount,elb.tallyStatus,elb.billNo FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billMonth) =:month and EXTRACT(year FROM elb.billMonth) =:year ORDER BY elb.elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.findAll", query = "SELECT elb.elBillId,elb.typeOfService,elb.cbId,elb.accountId,elb.elBillDate,elb.postType,elb.billDate,elb.billDueDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.fromDate,elb.billType,elb.avgCount,elb.avgAmount,elb.advanceClearedAmount,elb.tallyStatus FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billMonth) =:month and EXTRACT(year FROM elb.billMonth) =:year ORDER BY elb.elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.setBillStatus", query = "UPDATE ElectricityBillEntity elb SET elb.status = :status WHERE elb.elBillId = :elBillId"),
	@NamedQuery(name = "ElectricityBillsEntity.getBillEntityByAccountId", query = "SELECT elb FROM ElectricityBillEntity elb WHERE elb.typeOfService = :typeOfService and elb.accountId =:accountID and elb.billType='Normal' and elb.postType='Bill' and elb.status!='Cancelled'"),
	@NamedQuery(name = "ElectricityBillsEntity.getNumberBilld", query = "SELECT elb.typeOfService,count(elb.accountId),SUM(CASE WHEN elb.status IN('Paid') THEN 1 ELSE 0 END),COUNT(elb.cbId) FROM ElectricityBillEntity elb WHERE elb.status IN ('Paid','Posted','Approved') GROUP BY elb.typeOfService"),
	@NamedQuery(name = "ElectricityBillsEntity.checkForDuplicateMonth", query = "SELECT elb.elBillId FROM ElectricityBillEntity elb WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.accountId =:accountId and elb.typeOfService =:typeOfService"),
	@NamedQuery(name = "ElectricityBillsEntity.consolidatedBill",query = "select SUM(o.billAmount) from ElectricityBillEntity o where o.cbId =:cbId and o.billMonth =:billMonth"),
	@NamedQuery(name = "ElectricityBillsEntity.distinctCbId", query = "SELECT DISTINCT(elb.cbId),elb.accountId,a.accountNo,elb.billMonth,elb.typeOfService,p.firstName,p.lastName,(select SUM(o.billAmount) from ElectricityBillEntity o where o.cbId = elb.cbId and o.billMonth = elb.billMonth),elb.mailSent_Status FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p where (elb.cbId!=null or elb.cbId!='')"),
	@NamedQuery(name = "ElectricityBillsEntity.getBillEntityById", query = "SELECT elb FROM ElectricityBillEntity elb WHERE elb.elBillId = :elBillId"),
	@NamedQuery(name = "ElectricityBillsEntity.setApproveBill", query = "UPDATE ElectricityBillEntity el SET el.status = :status WHERE el.elBillId = :elBillId"),
	@NamedQuery(name = "ElectricityBillsEntity.getPreviousBill", query = "SELECT elb FROM ElectricityBillEntity elb WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.accountId =:accountId and elb.typeOfService =:typeOfService and elb.postType=:postType and (elb.status='Posted' or elb.status='Paid')"),
	@NamedQuery(name = "ElectricityBillsEntity.getCBID", query = "SELECT count(elb.elBillId) FROM ElectricityBillEntity elb WHERE elb.accountId =:accountId"),
	@NamedQuery(name = "ElectricityBillsEntity.findAllBillData", query = "SELECT elb FROM ElectricityBillEntity elb WHERE elb.status ='Approved' and elb.typeOfService =:serviceType and EXTRACT(month FROM elb.billMonth) =:month and EXTRACT(year FROM elb.billMonth) =:year"),
	//@NamedQuery(name = "ElectricityBillsEntity.findAllBillData", query = "SELECT elb FROM ElectricityBillEntity elb WHERE elb.status ='Approved'"),
	@NamedQuery(name = "ElectricityBillsEntity.approveAllBlls", query = "UPDATE ElectricityBillEntity el SET el.status = 'Approved' WHERE el.status = :status"),
    @NamedQuery(name = "BillEntity.getPreviousBillDate",query="SELECT MAX(b.billDate) FROM ElectricityBillEntity b WHERE b.accountObj.accountId=:accountId AND b.typeOfService=:serviceTypeList AND b.postType='Bill' and b.status!='Cancelled'"),
    @NamedQuery(name = "BillEntity.getPreviousddBillDate",query="SELECT MAX(b.billDate) FROM ElectricityBillEntity b WHERE b.accountObj.accountId=:accountId AND b.typeOfService=:serviceTypeList AND b.postType='Deposit'"),
	@NamedQuery(name = "ElectricityBillsEntity.getLastAvgBills", query = "SELECT elb FROM ElectricityBillEntity elb WHERE elb.accountId =:accountId and elb.typeOfService =:typeOfService and elb.postType=:postType and (elb.status='Posted' or elb.status='Paid')"),
	@NamedQuery(name = "ElectricityBillEntity.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM ElectricityBillEntity el,Account a WHERE el.accountId=a.accountId"),
	@NamedQuery(name = "ElectricityBillEntity.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from ElectricityBillEntity ont,Person p where ont.accountObj.personId = p.personId"),
	@NamedQuery(name = "ElectricityBillEntity.setStatusApproved", query = "UPDATE ElectricityBillEntity el SET el.status = :status WHERE el.status = 'Generated' and el.typeOfService =:serviceType and EXTRACT(month FROM el.billMonth) =:month and EXTRACT(year FROM el.billMonth) =:year"),
	//@NamedQuery(name = "ElectricityBillEntity.setStatusApproved", query = "UPDATE ElectricityBillEntity el SET el.status = :status WHERE el.status = 'Generated'"),
	@NamedQuery(name = "ElectricityBillEntity.findServiceTypes", query = "SELECT DISTINCT sm.typeOfService,a.accountId FROM ServiceMastersEntity sm INNER JOIN sm.accountObj a"),
	@NamedQuery(name = "ElectricityBillEntity.findServiceTypesForBackBill", query = "SELECT DISTINCT sm.typeOfService,a.accountId FROM ServiceMastersEntity sm INNER JOIN sm.accountObj a WHERE sm.typeOfService IN('Electricity','Water','Gas')"),
	@NamedQuery(name = "ElectricityBillEntity.getBillsNearToBillDueDate", query = "SELECT eb FROM ElectricityBillEntity eb WHERE eb.status='Posted' AND eb.postType='Bill' AND sysdate BETWEEN eb.billDueDate-2 AND eb.billDueDate"),
	@NamedQuery(name = "ElectricityBillEntity.getBillsOnBillDueDate", query = "SELECT eb FROM ElectricityBillEntity eb WHERE eb.status='Posted' AND eb.postType='Bill' AND TO_DATE(eb.billDueDate,'dd-mon-yyyy')=TO_DATE(sysdate,'dd-mon-yyyy')"),
	@NamedQuery(name = "ElectricityBillEntity.getBillsAfterBillDueDate", query = "SELECT eb FROM ElectricityBillEntity eb WHERE eb.status='Posted' AND eb.postType='Bill' AND TO_DATE(eb.billDueDate,'dd-mon-yyyy')<TO_DATE(sysdate,'dd-mon-yyyy')"),
	@NamedQuery(name = "ElectricityBillsEntity.getPreviousBillBack", query = "SELECT elb FROM ElectricityBillEntity elb WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.accountId =:accountId and elb.typeOfService =:typeOfService and elb.postType=:postType and elb.status!=:status)"),
	@NamedQuery(name = "ElectricityBillEntity.updateTallyStatus", query="UPDATE ElectricityBillEntity e SET e.tallyStatus = 'Posted' WHERE e.elBillId=:billId"),
	@NamedQuery(name = "ElectricityBillsEntity.getPreviousBillWithOutStatus", query = "SELECT elb FROM ElectricityBillEntity elb WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.accountId =:accountId and elb.typeOfService =:typeOfService and elb.postType=:postType and elb.status NOT IN('Generated','Cancelled')"),
	@NamedQuery(name = "ElectricityBillsEntity.getLastSixMontsBills", query = "SELECT ebe.billMonth,ebpe.elBillParameterValue,ebe.netAmount FROM  ElectricityBillParametersEntity ebpe inner join ebpe.electricityBillEntity ebe inner join ebpe.billParameterMasterEntity bpme where ebe.accountId=:accountId and ebe.typeOfService=:typeOfService and ebe.billDate<:billDate and ebe.status NOT IN ('Generated','Cancelled') and ebpe.billParameterMasterEntity.bvmId =(SELECT bpm.bvmId from BillParameterMasterEntity bpm where bpm.bvmName =:unitsString and bpm.serviceType=:typeOfService and bpm.status='Active') ORDER BY ebe.elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.getLastSixMontsBillsOthers", query = "SELECT ebe.billMonth,ebpe.elBillParameterValue,ebe.netAmount FROM  ElectricityBillParametersEntity ebpe inner join ebpe.electricityBillEntity ebe inner join ebpe.billParameterMasterEntity bpme where ebe.accountId=:accountId and ebe.typeOfService=:typeOfService and ebe.billDate<:billDate and ebe.status NOT IN ('Generated','Cancelled') ORDER BY ebe.elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.getLastSixBills", query = "SELECT eb FROM ElectricityBillEntity eb where eb.accountId=:accountId and eb.typeOfService=:typeOfService and eb.billDate<:billDate and eb.status NOT IN ('Generated','Canceled') ORDER BY eb.elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.getPreviousCAMDAte",query="SELECT MAX(b.billDate) FROM ElectricityBillEntity b WHERE b.accountId=:accountId AND b.typeOfService='CAM' AND b.postType='Bill'"),
	@NamedQuery(name = "ElectricityBillEntity.getLastSixMonthsCAMBills", query = "SELECT ebe.billMonth,ebe.netAmount FROM  ElectricityBillEntity ebe WHERE ebe.accountId=:accountId AND ebe.typeOfService=:typeOfService AND ebe.billDate<:billDate AND ebe.status NOT IN ('Generated','Cancelled') ORDER BY ebe.elBillId DESC"),
	@NamedQuery(name = "ElectricityBillEntity.getLineItemAmountBasedOnTransactionCode", query = "SELECT elb.balanceAmount FROM  ElectricityBillLineItemEntity elb WHERE elb.electricityBillEntity.elBillId=:elBillId AND elb.transactionCode=:transactionCode"),
	@NamedQuery(name = "ElectricityBillsEntity.downloadAllBills", query = "SELECT elb FROM ElectricityBillEntity elb WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.typeOfService =:typeOfService and elb.postType IN ('Bill','Pre Bill','Post Bill') and (elb.status='Posted' or elb.status='Paid' or elb.status='Approved' or elb.status='Generated')"),
//	@NamedQuery(name = "ElectricityBillEntity.updateBillDoc", query = "UPDATE ElectricityBillEntity elb SET elb.billDoc =:blob WHERE elb.elBillId = :elBillId"),
	@NamedQuery(name = "ElectricityBillsEntity.readBillsMIS", query = "SELECT b  FROM ElectricityBillEntity b WHERE b.billMonth BETWEEN TO_DATE('2015-01-01', 'yyyy-mm-dd') AND TO_DATE ('2015-12-31', 'yyyy-mm-dd') AND b.accountObj.accountId =:accountId AND b.status !='Cancelled'  ORDER BY billMonth DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.readBills", query = "SELECT b  FROM ElectricityBillEntity b WHERE EXTRACT(month FROM b.billDate) =:month and EXTRACT(year FROM b.billDate) =:year AND b.accountObj.accountId =:accountId AND b.status !='Cancelled'  ORDER BY billMonth DESC"),
	/*@NamedQuery(name = "ElectricityBillsEntity.readBillsMIS", query = "SELECT b  FROM ElectricityBillEntity b WHERE EXTRACT(month FROM b.billDate) =:month and EXTRACT(year FROM b.billDate) =:year AND b.accountObj.accountId =:accountId AND b.status !='Cancelled'  ORDER BY billMonth DESC"),*/
	@NamedQuery(name = "ServiceMastersEntity.getAllAccount", query = "SELECT a.accountId,a.accountNo,p.personId,p.firstName,p.lastName,pt.property_No FROM ServiceMastersEntity sm,Property pt INNER JOIN sm.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND sm.typeOfService in('CAM') ORDER BY sm.serviceMasterId DESC"),                         
	@NamedQuery(name="ElectricityBillsEntity.getBillUnits",query="SELECT el.elBillParameterValue FROM ElectricityBillParametersEntity el WHERE el.electricityBillEntity.elBillId=:elBillId AND el.billParameterMasterEntity.bvmName=:Units "),
	//@NamedQuery(name = "ServiceMastersEntity.getAllAccountWithMeter", query = "SELECT a.accountId,a.accountNo,p.personId,p.firstName,p.lastName,pt.property_No,em.meterSerialNo  FROM ServiceMastersEntity sm,Property pt,ElectricityMetersEntity em INNER JOIN sm.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND sm.accountObj.accountId=em.account.accountId AND sm.typeOfService in('Electricity','CAM') ORDER BY sm.serviceMasterId DESC"),
	@NamedQuery(name = "ServiceMastersEntity.getAllAccountWithMeter", query = "SELECT a.accountId,a.accountNo,p.personId,p.firstName,p.lastName,pt.property_No  FROM ServiceMastersEntity sm,Property pt INNER JOIN sm.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND sm.accountObj.accountId=a.accountId AND sm.typeOfService in('Electricity','CAM') ORDER BY sm.serviceMasterId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.readBillsMisAmr", query = "SELECT b  FROM ElectricityBillEntity b WHERE b.accountObj.accountId =:accountId AND b.status !='Cancelled' AND b.typeOfService='Electricity' ORDER BY elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.readBillsLine", query = "SELECT b  FROM ElectricityBillEntity b WHERE EXTRACT(month FROM b.billDate) =:month AND EXTRACT(year FROM b.billDate) =:year AND b.accountObj.accountId =:accountId AND b.status !='Cancelled' AND b.typeOfService IN('Electricity','CAM') ORDER BY elBillId DESC"),
	@NamedQuery(name = "ElectricityBillsEntity.BillingPayment", query = "SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billDueDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.fromDate,elb.billType,elb.avgCount,elb.avgAmount,elb.advanceClearedAmount FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year ORDER BY elb.elBillId DESC"),
    @NamedQuery(name="BillingPaymentsEntity.getBillPaymentDetails",query="SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId),(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId),elb.billDueDate FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 and elb.status !='Cancelled' ORDER BY elb.elBillId DESC "),
	@NamedQuery(name="BillingPaymentsEntity.getOpenInvoice",query="SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId),(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId),elb.billDueDate FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 and  elb.status NOT In ('Cancelled','Paid') ORDER BY elb.elBillId DESC "),
	@NamedQuery(name="ElectricityBillEntity.getAllFiftyBill",query="SELECT e from ElectricityBillEntity e where e.tallyStatus=:tallyStatus and e.typeOfService=:typeOfService and EXTRACT(month FROM e.billDate) =:month and EXTRACT(year FROM e.billDate) =:year and e.status NOT IN ('Cancelled','Generated')"),
	@NamedQuery(name="ElectricityBillEntity.getAllBill",query="SELECT elb.elBillId,elb.typeOfService,elb.accountId FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE elb.status ='Generated' AND elb.typeOfService = :serviceType AND EXTRACT(month FROM elb.billMonth) =:month and EXTRACT(year FROM elb.billMonth) =:year ORDER BY elb.elBillId DESC"),
	@NamedQuery(name="ElectricityBillEntity.fetchBillsDataBasedOnMonthAndServiceType",query="SELECT DISTINCT(b.elBillId),p.personId,a.propertyId,b.typeOfService FROM ElectricityBillEntity b INNER JOIN b.accountObj a INNER JOIN a.person p WHERE b.typeOfService=:typeOfService AND EXTRACT(month FROM b.billMonth)=:actualmonth AND EXTRACT(year FROM b.billMonth)=:year AND b.cbId IS NOT NULL AND b.status IN('Posted','Paid') AND b.mailSent_Status IS NULL"),
	
	@NamedQuery(name = "ElectricityBillEntity.searchNotGeneratedBillByServiceMonth", query = "SELECT s.accountObj.accountId, a.accountNo, s.typeOfService, p.firstName,p.lastName ,pt.property_No FROM ServiceMastersEntity s ,Property pt INNER JOIN s.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId and  s.typeOfService =:service and s.status='Active' and s.serviceEndDate=null and s.accountObj.accountId not IN (SELECT b.accountId FROM ElectricityBillEntity b WHERE EXTRACT(Month FROM b.billMonth)=:month AND EXTRACT(year FROM b.billMonth)=:year AND b.typeOfService=:service)"),


})

public class ElectricityBillEntity implements Comparable<ElectricityBillEntity> {

	@Id
	@Column(name="ELB_ID")
	@SequenceGenerator(name = "elBill_seq", sequenceName = "ELBILL_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "elBill_seq")
	private int elBillId;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfService;
	
	@Transient
	private double unitsBackBill;
	
	@Column(name="CB_ID")
	private String cbId;
	
	@Column(name="ACCOUNT_ID", unique=true, nullable=false, precision=11, scale=0)
	private int accountId;
	
	@OneToOne	 
	@JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Account accountObj;
	
	@Column(name="ELB_DT")
	private Timestamp elBillDate;
	
	@Column(name="POST_TYPE")
	private String postType;
	
	@Column(name="FROM_DATE")
	private Date fromDate;
	
	@Column(name="BILL_DATE")
	private Date billDate;
	
	@Column(name="BILL_DUE_DATE")
	private Date billDueDate;
	
	@Column(name="BILL_MONTH")
	private Date billMonth;
	
	@Column(name="ARREARS_AMOUNT")
	private Double arrearsAmount;
	
	@Column(name="BILL_AMOUNT")
	private Double billAmount;
	
	@Column(name="NET_AMOUNT")
	private Double netAmount;
	
	@Column(name="BILL_NO")
	private String billNo;
	
	@Column(name="AVG_UNITS")
	private double avgUnits;
	
	@Column(name="AVG_COUNT")
	private int avgCount;
	
	@Column(name="AVG_AMOUNT")
	private Double avgAmount;
	
	@Column(name="BILL_TYPE")
	private String billType;
	
	@Column(name="ADVANCE_CLEARED_AMOUNT")
	private double advanceClearedAmount;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@OneToMany(mappedBy="electricityBillEntity",fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Set<ElectricityBillLineItemEntity> billLineItemEntities = new HashSet<>();
	
	@Column(name="TALLY_STATUS")
	private String tallyStatus="Not Posted";
	
	@Column(name="MAILSENT_STATUS")
	private String mailSent_Status;
	
	@Transient
	private String propertyNumber;
	
	
	@Column(name="LATE_PAYMENT_AMOUNT")
	private Double latePaymentAmount;
	
	
	
	
	public Double getLatePaymentAmount() {
		return latePaymentAmount;
	}


	public void setLatePaymentAmount(Double latePaymentAmount) {
		this.latePaymentAmount = latePaymentAmount;
	}


	public String getPropertyNumber() {
		return propertyNumber;
	}


	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}


	public String getTallyStatus() {
		return tallyStatus;
	}


	public void setTallyStatus(String tallyStatus) {
		this.tallyStatus = tallyStatus;
	}


	public int getElBillId() {
		return elBillId;
	}


	public void setElBillId(int elBillId) {
		this.elBillId = elBillId;
	}

	public String getTypeOfService() {
		return typeOfService;
	}


	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}


	public String getCbId() {
		return cbId;
	}


	public void setCbId(String cbId) {
		this.cbId = cbId;
	}


	public int getAccountId() {
		return accountId;
	}


	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	public Account getAccountObj() {
		return accountObj;
	}


	public void setAccountObj(Account accountObj) {
		this.accountObj = accountObj;
	}


	public Timestamp getElBillDate() {
		return elBillDate;
	}


	public void setElBillDate(Timestamp elBillDate) {
		this.elBillDate = elBillDate;
	}


	public String getPostType() {
		return postType;
	}


	public void setPostType(String postType) {
		this.postType = postType;
	}


	public Date getBillDate() {
		return billDate;
	}


	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}


	public Date getBillDueDate() {
		return billDueDate;
	}


	public void setBillDueDate(Date billDueDate) {
		this.billDueDate = billDueDate;
	}


	public Date getBillMonth() {
		return billMonth;
	}


	public void setBillMonth(Date billMonth) {
		this.billMonth = billMonth;
	}


	public Double getArrearsAmount() {
		return arrearsAmount;
	}


	public void setArrearsAmount(Double arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}


	public Double getBillAmount() {
		return billAmount;
	}


	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}


	public Double getNetAmount() {
		return netAmount;
	}


	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}


	public String getBillNo() {
		return billNo;
	}


	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}


	public double getAdvanceClearedAmount() {
		return advanceClearedAmount;
	}


	public void setAdvanceClearedAmount(double advanceClearedAmount) {
		this.advanceClearedAmount = advanceClearedAmount;
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


	public Set<ElectricityBillLineItemEntity> getBillLineItemEntities() {
		return billLineItemEntities;
	}


	public void setBillLineItemEntities(
			Set<ElectricityBillLineItemEntity> billLineItemEntities) {
		this.billLineItemEntities = billLineItemEntities;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	


	public double getAvgUnits() {
		return avgUnits;
	}


	public void setAvgUnits(double avgUnits) {
		this.avgUnits = avgUnits;
	}


	public int getAvgCount() {
		return avgCount;
	}


	public void setAvgCount(int avgCount) {
		this.avgCount = avgCount;
	}

	public String getBillType() {
		return billType;
	}


	public void setBillType(String billType) {
		this.billType = billType;
	}
  

	public Double getAvgAmount() {
		return avgAmount;
	}


	public void setAvgAmount(Double avgAmount) {
		this.avgAmount = avgAmount;
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


	public double getUnitsBackBill() {
		return unitsBackBill;
	}


	public void setUnitsBackBill(double unitsBackBill) {
		this.unitsBackBill = unitsBackBill;
	}

	 @Override
		public int compareTo(ElectricityBillEntity o) {
			Integer elBillIdThis = this.elBillId;
			Integer objectBillId = o.getElBillId();
			return -(elBillIdThis.compareTo(objectBillId));
		}


	public String getMailSent_Status() {
		return mailSent_Status;
	}


	public void setMailSent_Status(String mailSent_Status) {
		this.mailSent_Status = mailSent_Status;
	}


	

}
