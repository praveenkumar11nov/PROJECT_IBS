package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Cacheable(true)
@Table(name="PAYMENTS")  
@NamedQueries({
	//@NamedQuery(name = "BillingPaymentsEntity.findAll", query = "SELECT bp.paymentCollectionId,bp.paymentDate,bp.receiptDate,bp.receiptNo,bp.paymentMode,bp.bankName,bp.instrumentDate,bp.instrumentNo,bp.paymentAmount,bp.postedToGl,bp.postedGlDate,bp.status,bp.createdBy,bp.accountId,a.accountNo,bp.partPayment,bp.disputeFlag,bp.paymentType,p.firstName,p.lastName,bp.excessAmount,bp.tallyStatus FROM BillingPaymentsEntity bp,Account a INNER JOIN a.person p WHERE a.accountId=bp.accountId ORDER BY bp.paymentCollectionId DESC"),
	@NamedQuery(name = "BillingPaymentsEntity.findAll", query = "SELECT bp.paymentCollectionId,bp.paymentDate,bp.receiptDate,"
			+ "bp.receiptNo,bp.paymentMode,bp.bankName,bp.instrumentDate,bp.instrumentNo,bp.paymentAmount,bp.postedToGl,"
			+ "bp.postedGlDate,bp.status,bp.createdBy,bp.accountId,a.accountNo,bp.partPayment,bp.disputeFlag,bp.paymentType,"
			+ "p.firstName,p.lastName,bp.excessAmount,bp.tallyStatus,(select pr.property_No from  Property pr "
			+ "where pr.propertyId = a.propertyId) FROM BillingPaymentsEntity bp,Account a INNER JOIN a.person p"
			+ " WHERE a.accountId=bp.accountId AND EXTRACT(month FROM bp.paymentDate)=(SELECT EXTRACT(month FROM bp.paymentDate) FROM BillingPaymentsEntity bp WHERE bp.paymentDate=(SELECT MAX(bp.paymentDate) FROM BillingPaymentsEntity bp)) AND EXTRACT(year FROM bp.paymentDate)=(SELECT EXTRACT(year FROM bp.paymentDate) FROM BillingPaymentsEntity bp WHERE bp.paymentDate=(SELECT MAX(bp.paymentDate) FROM BillingPaymentsEntity bp)) ORDER BY bp.paymentCollectionId DESC"),
	@NamedQuery(name = "BillingPaymentsEntity.setCollectionPaymentStatus", query = "UPDATE BillingPaymentsEntity bp SET bp.status = :status,bp.lastUpdatedBy=:lastUpdatedBy WHERE bp.paymentCollectionId = :paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.getAllAccuntNumbers", query = "SELECT  DISTINCT eb.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,pt.property_No,a.propertyId FROM ElectricityLedgerEntity eb,Property pt,Account a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND eb.accountId = a.accountId AND eb.postType NOT IN ('ARREARS','INIT') AND eb.ledgerType!='Common Ledger' AND eb.balance>0 AND eb.elLedgerid IN (SELECT max(elb.elLedgerid)FROM ElectricityLedgerEntity elb WHERE elb.accountId=eb.accountId GROUP BY elb.ledgerType)"),
	//@NamedQuery(name = "BillingPaymentsEntity.getConsolidatedAmount", query = "SELECT  eb.accountId,SUM(eb.netAmount) FROM ElectricityBillEntity eb WHERE eb.status='Posted' AND eb.postType='Bill' GROUP BY eb.accountId"),
	@NamedQuery(name = "BillingPaymentsEntity.setPostedToGl", query = "UPDATE BillingPaymentsEntity bp SET bp.postedToGl = :postedToGl WHERE bp.paymentCollectionId = :paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.setPostedGlDate", query = "UPDATE BillingPaymentsEntity bp SET bp.postedGlDate = :postedGlDate WHERE bp.paymentCollectionId = :paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.setPostedLedgerDate", query = "UPDATE PaymentSegmentsEntity bp SET bp.postedLedgerDate = :postedLedgerDate WHERE bp.billingPaymentsEntity.paymentCollectionId = :paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.setPaymentSegmentStatus", query = "UPDATE PaymentSegmentsEntity bp SET bp.status = :status WHERE bp.billingPaymentsEntity.paymentCollectionId = :paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.getSegmentTypeList", query = "SELECT bp FROM PaymentSegmentsEntity bp WHERE bp.billingPaymentsEntity.paymentCollectionId = :paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.setSegmentPostedToLedger", query = "UPDATE PaymentSegmentsEntity bp SET bp.postedToLedger = :postedToLedger WHERE bp.billingPaymentsEntity.paymentCollectionId = :paymentCollectionId AND bp.billSegment=:billSegment"),
	@NamedQuery(name = "BillingPaymentsEntity.getPaymentCollectionID", query = "SELECT bp FROM BillingPaymentsEntity bp WHERE bp.status='Approved' ORDER BY bp.paymentCollectionId DESC"),
	@NamedQuery(name = "BillingPaymentsEntity.getPaymentSegmentsList", query = "SELECT ps FROM PaymentSegmentsEntity ps WHERE ps.billingPaymentsEntity.paymentCollectionId=:paymentCollectionId ORDER BY ps.paymentSegmentId ASC"),
	@NamedQuery(name = "BillingPaymentsEntity.getPaymentSegmentCalcLinesList", query = "SELECT psc FROM PaymentSegmentCalcLines psc WHERE psc.paymentSegmentsEntity.paymentSegmentId=:paymentSegmentId AND psc.transactionCode=:transactionCode ORDER BY psc.calcLinesId DESC"),
	@NamedQuery(name = "BillingPaymentsEntity.setStatusApproved", query = "UPDATE BillingPaymentsEntity bp SET bp.status = :status WHERE bp.status ='Created'"),
	@NamedQuery(name = "BillingPaymentsEntity.setStatusApprovedPaymentSegments", query = "UPDATE PaymentSegmentsEntity ps SET ps.status = :status WHERE ps.status ='Created'"),
	@NamedQuery(name = "BillingPaymentsEntity.setBillEntityStatus", query = "UPDATE ElectricityBillEntity eb SET eb.status = :status WHERE eb.accountId=:accountId AND eb.status='Posted' AND eb.typeOfService=:serviceType"),
	@NamedQuery(name = "BillingPaymentsEntity.getListOfServicesAmounts", query = "SELECT elb.elLedgerid,elb.ledgerType,elb.balance FROM ElectricityLedgerEntity elb WHERE elb.accountId=:accountId AND elb.postType NOT IN ('ARREARS','INIT') AND elb.ledgerType!='Common Ledger' AND elb.elLedgerid IN (SELECT max(eb.elLedgerid)FROM ElectricityLedgerEntity eb WHERE eb.accountId=:accountId GROUP BY eb.ledgerType) ORDER BY elb.ledgerType ASC"),
	@NamedQuery(name = "BillingPaymentsEntity.accountNumberBasedOnPayDeposit", query = "SELECT  DISTINCT eb.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,a.propertyId FROM ElectricityBillEntity eb,Account a INNER JOIN a.person p WHERE eb.accountId = a.accountId AND eb.status='Posted' AND eb.postType='Deposit'"),
	@NamedQuery(name = "BillingPaymentsEntity.getConsolidatedAmountBasedOnDeposit", query = "SELECT  eb.accountId,SUM(eb.billAmount) FROM ElectricityBillEntity eb WHERE eb.status='Posted' AND eb.postType='Deposit' GROUP BY eb.accountId"),
	@NamedQuery(name = "BillingPaymentsEntity.accountNumberBasedOnPayAdvanceBill", query = "SELECT  DISTINCT eb.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,a.propertyId FROM AdvanceBill eb,Account a INNER JOIN a.person p WHERE eb.accountId = a.accountId AND eb.status='Approved' AND eb.postType='Advance Bill'"),
	@NamedQuery(name = "BillingPaymentsEntity.getConsolidatedAmountBasedOnPayAdvance", query = "SELECT  eb.accountId,SUM(eb.abBillAmount) FROM AdvanceBill eb WHERE eb.status='Approved' AND eb.postType='Advance Bill' GROUP BY eb.accountId"),
	@NamedQuery(name = "BillingPaymentsEntity.getPaymentSegmentCalcLinesListForDepositType", query = "SELECT psc FROM PaymentSegmentCalcLines psc WHERE psc.paymentSegmentsEntity.paymentSegmentId=:paymentSegmentId ORDER BY psc.calcLinesId DESC"),
	@NamedQuery(name = "BillingPaymentsEntity.setAdvanceBillEntityStatus", query = "UPDATE AdvanceBill eb SET eb.status = :status WHERE eb.accountId=:accountId AND eb.abBillNo=:abBillNo"),
	@NamedQuery(name = "BillingPaymentsEntity.commonFilterForAccountNumbersUrl", query = "SELECT DISTINCT a.accountNo FROM BillingPaymentsEntity el,Account a WHERE el.accountId=a.accountId"),
	@NamedQuery(name = "BillingPaymentsEntity.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from BillingPaymentsEntity bp,Account a INNER JOIN a.person p WHERE bp.accountId=a.accountId"),
	@NamedQuery(name = "BillingPaymentsEntity.getAllReconciliations", query = "SELECT bp.receiptDate,COUNT(*),SUM(bp.paymentAmount),(SELECT SUM(r.credit) FROM BankRemittance r WHERE r.txDate=bp.receiptDate) FROM BillingPaymentsEntity bp,BankRemittance r WHERE bp.status='Posted' AND r.txDate=bp.receiptDate GROUP BY bp.receiptDate"),
	@NamedQuery(name = "BillingPaymentsEntity.checkForNotPostedAccounts", query = "SELECT bp.paymentCollectionId FROM BillingPaymentsEntity bp WHERE bp.accountId=:accountId AND bp.paymentType=:paymentType AND bp.typeOfService =:typeOfService AND bp.status IN ('Created','Approved')"),
	@NamedQuery(name = "BillingPaymentsEntity.getPamentDetals", query = "SELECT bpe FROM BillingPaymentsEntity bpe WHERE EXTRACT(month FROM bpe.paymentDate) =:month and EXTRACT(year FROM bpe.paymentDate) =:year and bpe.accountId =:accountId and bpe.status='Posted'"),
	@NamedQuery(name = "BillingPaymentsEntity.getExcessAmount", query = "SELECT bpe.excessAmount FROM BillingPaymentsEntity bpe WHERE EXTRACT(month FROM bpe.paymentDate) =:month and EXTRACT(year FROM bpe.paymentDate) =:year and bpe.accountId =:accountId and bpe.status='Posted'"),
	@NamedQuery(name = "BillingPaymentsEntity.getPaymentSegmentCalcLinesListWithLatePayment", query = "SELECT psc FROM PaymentSegmentCalcLines psc WHERE psc.paymentSegmentsEntity.paymentSegmentId=:paymentSegmentId AND psc.transactionCode='EL_LPC' ORDER BY psc.calcLinesId DESC"),
	@NamedQuery(name = "BillingPaymentsEntity.findBillingPaymentDetail", query = "SELECT bp.paymentCollectionId,bp.paymentDate,bp.receiptDate,"
			+ "bp.receiptNo,bp.paymentMode,bp.bankName,bp.instrumentDate,bp.instrumentNo,bp.paymentAmount,bp.postedToGl,"
			+ "bp.postedGlDate,bp.status,bp.createdBy,bp.accountId,a.accountNo,bp.partPayment,bp.disputeFlag,bp.paymentType,"
			+ "p.firstName,p.lastName,bp.excessAmount,bp.tallyStatus,(select pr.property_No from  Property pr "
			+ " where pr.propertyId = a.propertyId),(select pa.blocks.blockName FROM Property pa WHERE pa.propertyId = a.propertyId) FROM BillingPaymentsEntity bp,Account a INNER JOIN a.person p"
			+ " WHERE a.accountId=bp.accountId ORDER BY bp.paymentCollectionId DESC"),
      @NamedQuery(name = "DepositsLineItems.getOnDepositId", query = "SELECT d.ddId,d.typeOfServiceDeposit,d.transactionCode,d.category,d.amount,d.collectionType,d.instrumentNo,d.instrumentDate,tm.transactionName,d.paymentMode,d.bankName,dd.depositsId,d.status,d.loadChangeUnits,d.notes FROM DepositsLineItems d,TransactionMasterEntity tm INNER JOIN d.deposits dd where d.transactionCode=tm.transactionCode AND dd.depositsId=:depositsId ORDER BY d.ddId DESC"),
      @NamedQuery(name="BillingPaymentEntity.getPaymentAmount",query="SELECT d.paymentAmount FROM BillingPaymentsEntity d WHERE d.status !='Cancelled' And d.paymentCollectionId IN (SELECT p.billingPaymentsEntity.paymentCollectionId FROM PaymentSegmentsEntity p WHERE p.billReferenceNo=:billNo )"),
      @NamedQuery(name="BillingPaymentsEntity.findPaymentDetailbasedOnServiceType",query="SELECT d.paymentAmount,d.paymentDate,d.receiptDate,d.receiptNo,d.paymentMode,d.bankName,d.instrumentDate,d.instrumentNo,p.firstName,p.lastName FROM BillingPaymentsEntity d,Account a INNER JOIN a.person p WHERE a.accountId=d.accountId And d.status !='Cancelled' And d.paymentCollectionId IN (SELECT p.billingPaymentsEntity.paymentCollectionId FROM PaymentSegmentsEntity p WHERE p.billReferenceNo In(Select b.billNo FROM ElectricityBillEntity b WHERE b.status='Paid' AND b.typeOfService='Others') )"),
  	  @NamedQuery(name ="BillingPaymentsEntity.getPaymentDataBasedOnMonth", query = "SELECT bpe FROM BillingPaymentsEntity bpe  WHERE EXTRACT(month FROM bpe.receiptDate) =:month and EXTRACT(year FROM bpe.receiptDate) =:year"),
  	  @NamedQuery(name="BillingPaymentsEntity.findFiftyRecordsForTally",query="SELECT bpe FROM BillingPaymentsEntity bpe  WHERE bpe.tallyStatus=:tallyStatus and bpe.status NOT IN('Cancelled','Created')  "),
  	  @NamedQuery(name="BillingPaymentsEntity.getAllPropertyNo",query="SELECT pt.propertyId,pt.property_No FROM Property pt"),
  	  @NamedQuery(name = "BillingPaymentsEntity.setDepositBillEntityStatus", query = "UPDATE ElectricityBillEntity eb SET eb.status = :status WHERE eb.accountId=:accountId AND eb.billNo=:billNo"),
  	  @NamedQuery(name = "BillingPaymentsEntity.getAllAccuntNumbersBasedOnProperty", query = "SELECT  DISTINCT eb.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle,pt.property_No,a.propertyId FROM ElectricityLedgerEntity eb,Property pt,Account a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND eb.accountId = a.accountId AND eb.postType NOT IN ('ARREARS','INIT') AND eb.ledgerType!='Common Ledger' AND eb.balance>0 AND a.propertyId=:propertyId AND eb.elLedgerid IN (SELECT max(elb.elLedgerid)FROM ElectricityLedgerEntity elb WHERE elb.accountId=eb.accountId GROUP BY elb.ledgerType)"),
  	  @NamedQuery(name = "BillingPaymentsEntity.searchPaymentDataByMonth", query = "SELECT bp.paymentCollectionId,bp.paymentDate,bp.receiptDate,"
			+ "bp.receiptNo,bp.paymentMode,bp.bankName,bp.instrumentDate,bp.instrumentNo,bp.paymentAmount,bp.postedToGl,"
			+ "bp.postedGlDate,bp.status,bp.createdBy,bp.accountId,a.accountNo,bp.partPayment,bp.disputeFlag,bp.paymentType,"
			+ "p.firstName,p.lastName,bp.excessAmount,bp.tallyStatus,(select pr.property_No from  Property pr "
			+ "where pr.propertyId = a.propertyId) FROM BillingPaymentsEntity bp,Account a INNER JOIN a.person p"
			+ " WHERE a.accountId=bp.accountId AND TRUNC(bp.paymentDate) BETWEEN TO_DATE(:fromDateVal,'YYYY-MM-DD') AND TO_DATE(:toDateVal,'YYYY-MM-DD') ORDER BY bp.paymentCollectionId DESC"),
	/*===================================XML generation for cam payments===============================*/
	@NamedQuery(name = "BillingPaymentsEntity.getAllCamPaymentRecordsForTally", query = "SELECT pp FROM BillingPaymentsEntity pp where EXTRACT(month FROM pp.receiptDate)=:month AND EXTRACT(year FROM pp.receiptDate)=:year AND pp.tallyStatus = 'Not Posted' and pp.status NOT IN('Cancelled','Created')"),
	@NamedQuery(name = "BillingPaymentsEntity.updateTallyStatusInCamPayments", query = "UPDATE BillingPaymentsEntity pp SET pp.tallyStatus =:tallyStatus where pp.paymentCollectionId=:paymentCollectionId"),
	@NamedQuery(name = "BillingPaymentsEntity.getAllFiftyBill", query = " SELECT pp from BillingPaymentsEntity pp where pp.tallyStatus =:tallyStatus and EXTRACT(month FROM pp.receiptDate) =:month and EXTRACT(year FROM pp.receiptDate) =:year and pp.status NOT IN ('Cancelled','Generated')"),
	//query="SELECT e from ElectricityBillEntity e where e.tallyStatus=:tallyStatus and e.typeOfService=:typeOfService and EXTRACT(month FROM e.billDate) =:month and EXTRACT(year FROM e.billDate) =:year and e.status NOT IN ('Cancelled','Generated')"),
	
})
public class BillingPaymentsEntity {

	@Id
	@SequenceGenerator(name = "billingPayments_seq", sequenceName = "BILLING_PAYMENTS_SEQ") 
	@GeneratedValue(generator = "billingPayments_seq") 
	@Column(name="CP_ID")
	private int paymentCollectionId;
	
	@Transient
	private String paymentComponets;
	
	@Transient
	private double latePaymentAmount;
	
	@Transient
	private double billAmount;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;
	
	@Transient
	private String accountNumber;
	
	@Transient
	private String property_No;
	
	@Column(name="TYPE_OF_SERVICE")
	private String typeOfService;
	
	@Column(name="DISPUTE_FLAG")
	private String disputeFlag;
	
	@Column(name="PART_PAYMENT")
	private String partPayment;
	
	@Column(name="PAYMENT_TYPE")
	private String paymentType;
	
	@Transient
	private int cbId;
	
	@Column(name="CP_DT")
	private Timestamp paymentDate;
	
	@Column(name="CP_RECEIPT_DATE")
	private Date receiptDate;
	
	@Column(name="CP_RECEIPT_NO")
	private String receiptNo;
	
	@Column(name="CP_PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name="BANK_ID")
	private String bankName;
	
	@Column(name="INSTRUMENT_DATE")
	private Date instrumentDate;
	
	@Column(name="INSTRUMENT_NO")
	private String instrumentNo;
	
	@Column(name="CP_AMOUNT")
	private double paymentAmount;
	
	@Column(name="EXCESS_AMOUNT")
	private double excessAmount;
	
	@Column(name="POSTED_TO_GL")
	private String postedToGl;
	
	@Column(name="POSTED_TO_GL_DT")
	private Timestamp postedGlDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="TALLY_STATUS")
	private String tallyStatus;
		
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	@OneToMany(mappedBy = "billingPaymentsEntity",fetch=FetchType.EAGER)  
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private Set<PaymentSegmentsEntity> segmentsEntities; 
	
	public String getTallyStatus() {
		return tallyStatus;
	}

	public void setTallyStatus(String tallyStatus) {
		this.tallyStatus = tallyStatus;
	}

	public int getPaymentCollectionId() {
		return paymentCollectionId;
	}

	public void setPaymentCollectionId(int paymentCollectionId) {
		this.paymentCollectionId = paymentCollectionId;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPostedToGl() {
		return postedToGl;
	}

	public void setPostedToGl(String postedToGl) {
		this.postedToGl = postedToGl;
	}

	public Timestamp getPostedGlDate() {
		return postedGlDate;
	}

	public void setPostedGlDate(Timestamp postedGlDate) {
		this.postedGlDate = postedGlDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Set<PaymentSegmentsEntity> getSegmentsEntities() {
		return segmentsEntities;
	}

	public void setSegmentsEntities(Set<PaymentSegmentsEntity> segmentsEntities) {
		this.segmentsEntities = segmentsEntities;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getCbId() {
		return cbId;
	}

	public void setCbId(int cbId) {
		this.cbId = cbId;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
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

	public String getDisputeFlag() {
		return disputeFlag;
	}

	public void setDisputeFlag(String disputeFlag) {
		this.disputeFlag = disputeFlag;
	}

	public String getPartPayment() {
		return partPayment;
	}

	public void setPartPayment(String partPayment) {
		this.partPayment = partPayment;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentComponets() {
		return paymentComponets;
	}

	public void setPaymentComponets(String paymentComponets) {
		this.paymentComponets = paymentComponets;
	}

	public double getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(double excessAmount) {
		this.excessAmount = excessAmount;
	}
	
	public String getProperty_No() {
		return property_No;
	}

	public void setProperty_No(String property_No) {
		this.property_No = property_No;
	}

	public BillingPaymentsEntity() {
		
	}

	public double getLatePaymentAmount() {
		return latePaymentAmount;
	}

	public void setLatePaymentAmount(double latePaymentAmount) {
		this.latePaymentAmount = latePaymentAmount;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}

}
