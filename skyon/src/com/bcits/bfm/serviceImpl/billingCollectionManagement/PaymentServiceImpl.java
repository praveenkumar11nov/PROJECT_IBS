package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.controller.BillController;
import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.PaymentSegmentCalcLines;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.tariffManagement.ELRateMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class PaymentServiceImpl extends GenericServiceImpl<BillingPaymentsEntity> implements PaymentService  {
	@Autowired
	private ELRateMasterService rateMasterService;
	
	@Autowired
	private BillController billController;
	
	@Autowired
	private CamConsolidationService camConsolidationService;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<BillingPaymentsEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("BillingPaymentsEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("paymentCollectionId", (Integer)values[0]);
				paymentMap.put("paymentDate", (Timestamp)values[1]);
				paymentMap.put("receiptDate", (Date)values[2]);
				paymentMap.put("receiptNo", (String)values[3]);
				paymentMap.put("paymentMode", (String)values[4]);
				paymentMap.put("bankName", (String)values[5]);
				paymentMap.put("instrumentDate", (Date)values[6]);
				paymentMap.put("instrumentNo", (String)values[7]);
				paymentMap.put("paymentAmount", (Double)values[8]);
				paymentMap.put("postedToGl", (String)values[9]);
				paymentMap.put("postedGlDate", (Timestamp)values[10]);
				paymentMap.put("status", (String)values[11]);
				paymentMap.put("createdBy", (String)values[12]);
				paymentMap.put("accountId", (Integer)values[13]);
				paymentMap.put("accountNo", (String)values[14]);
				paymentMap.put("partPayment", (String)values[15]);
				paymentMap.put("disputeFlag", (String)values[16]);
				paymentMap.put("paymentType", (String)values[17]);
				
				String personName = "";
				personName = personName.concat((String)values[18]);
				if((String)values[19] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[19]);
				}
				paymentMap.put("personName", personName);
				paymentMap.put("excessAmount", (Double)values[20]);
				paymentMap.put("tallyStatus", (String)values[21]);
				paymentMap.put("property_No", (String)values[22]);
			    result.add(paymentMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setCollectionPaymentStatus(int paymentCollectionId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			BillingPaymentsEntity paymentsEntity = find(paymentCollectionId);
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("BillingPaymentsEntity.setCollectionPaymentStatus").setParameter("status", "Approved").setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID")).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPaymentSegmentStatus").setParameter("status", "Approved").setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				out.write("Payment details are approved");
			}
			else if(paymentsEntity.getStatus().equals("Posted")) {
				out.write("Payment details are already posted");
			}else{
				entityManager.createNamedQuery("BillingPaymentsEntity.setCollectionPaymentStatus").setParameter("status", "Posted").setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID")).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPostedToGl").setParameter("postedToGl", "Yes").setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPostedGlDate").setParameter("postedGlDate", new Timestamp(new java.util.Date().getTime())).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPostedLedgerDate").setParameter("postedLedgerDate", new Timestamp(new java.util.Date().getTime())).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPaymentSegmentStatus").setParameter("status", "Posted").setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				
				List<PaymentSegmentsEntity> segmentList = entityManager.createNamedQuery("BillingPaymentsEntity.getSegmentTypeList").setParameter("paymentCollectionId", paymentCollectionId).getResultList();
				for (PaymentSegmentsEntity paymentSegmentsEntity : segmentList) {
					entityManager.createNamedQuery("BillingPaymentsEntity.setSegmentPostedToLedger").setParameter("postedToLedger", paymentSegmentsEntity.getBillSegment()+" Ledger").setParameter("paymentCollectionId", paymentCollectionId).setParameter("billSegment", paymentSegmentsEntity.getBillSegment()).executeUpdate();
				}
				
				out.write("Payment details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setBillEntityStatus(int accountId,String serviceType,String operation,HttpServletResponse response){
		entityManager.createNamedQuery("BillingPaymentsEntity.setBillEntityStatus").setParameter("status", "Paid").setParameter("accountId", accountId).setParameter("serviceType", serviceType).executeUpdate();
	}

	@Override
	public void paymentStatusUpdate(int paymentCollectionId, HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			BillingPaymentsEntity paymentsEntity = find(paymentCollectionId);
			
			if(paymentsEntity.getStatus().equals("Created"))
			{
				entityManager.createNamedQuery("BillingPaymentsEntity.setCollectionPaymentStatus").setParameter("status", "Cancelled").setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID")).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				out.write("Payment cancelled successfully");
			}else if(paymentsEntity.getStatus().equals("Cancelled")) {
				out.write("Already cancelled the payment");
			}else{
				out.write("Once approved and posted payment cann't be cancelled");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<?> getAllAccuntNumbers() {
		return getAccountNumbersData(entityManager.createNamedQuery("BillingPaymentsEntity.getAllAccuntNumbers").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountNumbersData(List<?> accountNumbers){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> accountNumberMap = null;
		 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				accountNumberMap = new HashMap<String, Object>();
				
				accountNumberMap.put("accountId", (Integer)values[0]);
				accountNumberMap.put("accountNo", (String)values[1]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if(((String)values[4]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				
				accountNumberMap.put("personId", (Integer)values[2]);
				accountNumberMap.put("personName", personName);
				accountNumberMap.put("personType", (String)values[5]);
				accountNumberMap.put("personStyle", (String)values[6]);
				accountNumberMap.put("property_No", (String)values[7]);
				accountNumberMap.put("propertyId", (Integer)values[8]);
			
			result.add(accountNumberMap);
	     }
     return result;
	}

	@Override
	public List<?> getConsolidatedAmount() {
		String queryString = "SELECT ACCOUNT_ID,SUM(BALANCE) FROM (SELECT ACCOUNT_ID,LEDGER_TYPE,BALANCE,ROW_NUMBER() OVER (PARTITION BY ACCOUNT_ID,LEDGER_TYPE ORDER BY ELL_ID DESC) AS rn FROM LEDGER WHERE POST_TYPE NOT IN ('ARREARS','INIT') AND LEDGER_TYPE!='Common Ledger') WHERE rn = 1 GROUP BY ACCOUNT_ID";
		final Query query = this.entityManager.createNativeQuery(queryString);
		return getConsolidatedAmount(query.getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getConsolidatedAmount(List<?> consolidatedEntites){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> consolidatedMap = null;
		 for (Iterator<?> iterator = consolidatedEntites.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				consolidatedMap = new HashMap<String, Object>();
				
				consolidatedMap.put("cbId","Total - "+ ((BigDecimal)values[1]).doubleValue());
				consolidatedMap.put("billAmount", ((BigDecimal)values[1]).doubleValue()>0?((BigDecimal)values[1]).doubleValue():0);
				consolidatedMap.put("accountId", ((BigDecimal)values[0]).intValue());
			
			result.add(consolidatedMap);
	     }
     return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillingPaymentsEntity> getPaymentCollectionID() {
		return entityManager.createNamedQuery("BillingPaymentsEntity.getPaymentCollectionID").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSegmentsEntity> getPaymentSegmentsList(int paymentCollectionId) {
		return entityManager.createNamedQuery("BillingPaymentsEntity.getPaymentSegmentsList").setParameter("paymentCollectionId", paymentCollectionId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSegmentCalcLines> getPaymentSegmentCalcLinesList(int paymentSegmentId,String transactionCode) {
		return entityManager.createNamedQuery("BillingPaymentsEntity.getPaymentSegmentCalcLinesList").setParameter("paymentSegmentId", paymentSegmentId).setParameter("transactionCode", transactionCode).getResultList();
	}
	
	@Override
	public void setStatusApproved(HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
		    entityManager.createNamedQuery("BillingPaymentsEntity.setStatusApproved").setParameter("status", "Approved").executeUpdate();
		    entityManager.createNamedQuery("BillingPaymentsEntity.setStatusApprovedPaymentSegments").setParameter("status", "Approved").executeUpdate();
		    out.write("Payment details are approved");
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setStatusPostedLedger(int paymentCollectionId, HttpServletResponse response) {
		try
		{
			@SuppressWarnings("unused")
			PrintWriter out = response.getWriter();
			BillingPaymentsEntity paymentsEntity = find(paymentCollectionId);
			
			if(paymentsEntity.getStatus().equals("Posted")) {
				//out.write("Payment details are already posted");
			}else{
				entityManager.createNamedQuery("BillingPaymentsEntity.setCollectionPaymentStatus").setParameter("status", "Posted").setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID")).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPostedToGl").setParameter("postedToGl", "Yes").setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPostedGlDate").setParameter("postedGlDate", new Timestamp(new java.util.Date().getTime())).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPostedLedgerDate").setParameter("postedLedgerDate", new Timestamp(new java.util.Date().getTime())).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				entityManager.createNamedQuery("BillingPaymentsEntity.setPaymentSegmentStatus").setParameter("status", "Posted").setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
				
				List<PaymentSegmentsEntity> segmentList = entityManager.createNamedQuery("BillingPaymentsEntity.getSegmentTypeList").setParameter("paymentCollectionId", paymentCollectionId).getResultList();
				for (PaymentSegmentsEntity paymentSegmentsEntity : segmentList) {
					entityManager.createNamedQuery("BillingPaymentsEntity.setSegmentPostedToLedger").setParameter("postedToLedger", paymentSegmentsEntity.getBillSegment()+" Ledger").setParameter("paymentCollectionId", paymentCollectionId).setParameter("billSegment", paymentSegmentsEntity.getBillSegment()).executeUpdate();
				}
				
				//out.write("Payment details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<?> getListOfServicesAmounts(int accountId) {
		return getAmountDetails(entityManager.createNamedQuery("BillingPaymentsEntity.getListOfServicesAmounts").setParameter("accountId", accountId).getResultList());
	}
	
	public List<?> getAmountDetails(List<?> listOfserviceAmounts){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> consolidatedMap = null;
		 for (Iterator<?> iterator = listOfserviceAmounts.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				consolidatedMap = new HashMap<String, Object>();

				consolidatedMap.put("elBillId",(Integer)values[0]);
				consolidatedMap.put("typeOfService",((String)values[1]).replace(" Ledger", "")+" - "+(Double)values[2]);
				consolidatedMap.put("amount", (Double)values[2]);

				result.add(consolidatedMap);
	     }
    return result;
	}
	
	@Override
	public List<?> accountNumberBasedOnPayDeposit() {
		return getAccountNumberPayDepositData(entityManager.createNamedQuery("BillingPaymentsEntity.accountNumberBasedOnPayDeposit").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountNumberPayDepositData(List<?> accountNumbers){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> accountNumberMap = null;
		 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				accountNumberMap = new HashMap<String, Object>();
				
				accountNumberMap.put("accountId", (Integer)values[0]);
				accountNumberMap.put("accountNo", (String)values[1]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if(((String)values[4]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				
				accountNumberMap.put("personId", (Integer)values[2]);
				accountNumberMap.put("personName", personName);
				accountNumberMap.put("personType", (String)values[5]);
				accountNumberMap.put("personStyle", (String)values[6]);
				accountNumberMap.put("propertyId", (Integer)values[7]);
			
			result.add(accountNumberMap);
	     }
     return result;
	}
	
	@Override
	public List<?> getConsolidatedAmountBasedOnDeposit() {
		return getConsolidatedAmountPayDeposit(entityManager.createNamedQuery("BillingPaymentsEntity.getConsolidatedAmountBasedOnDeposit").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getConsolidatedAmountPayDeposit(List<?> consolidatedEntites){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> consolidatedMap = null;
		 for (Iterator<?> iterator = consolidatedEntites.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				consolidatedMap = new HashMap<String, Object>();
				
				consolidatedMap.put("cbId","Total - "+(Double)values[1]);
				consolidatedMap.put("billAmount", (Double)values[1]);
				consolidatedMap.put("accountId", (Integer)values[0]);
			
			result.add(consolidatedMap);
	     }
     return result;
	}
	
	@Override
	public List<?> accountNumberBasedOnPayAdvanceBill() {
		return getAccountNumberPayAdvanceBillData(entityManager.createNamedQuery("BillingPaymentsEntity.accountNumberBasedOnPayAdvanceBill").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountNumberPayAdvanceBillData(List<?> accountNumbers){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> accountNumberMap = null;
		 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				accountNumberMap = new HashMap<String, Object>();
				
				accountNumberMap.put("accountId", (Integer)values[0]);
				accountNumberMap.put("accountNo", (String)values[1]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if(((String)values[4]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				
				accountNumberMap.put("personId", (Integer)values[2]);
				accountNumberMap.put("personName", personName);
				accountNumberMap.put("personType", (String)values[5]);
				accountNumberMap.put("personStyle", (String)values[6]);
				accountNumberMap.put("propertyId", (Integer)values[7]);
			
			result.add(accountNumberMap);
	     }
     return result;
	}
	
	@Override
	public List<?> getConsolidatedAmountBasedOnPayAdvance() {
		return getConsolidatedAmountPayDepositData(entityManager.createNamedQuery("BillingPaymentsEntity.getConsolidatedAmountBasedOnPayAdvance").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getConsolidatedAmountPayDepositData(List<?> consolidatedEntites){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> consolidatedMap = null;
		 for (Iterator<?> iterator = consolidatedEntites.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				consolidatedMap = new HashMap<String, Object>();
				
				consolidatedMap.put("cbId","Total - "+(Double)values[1]);
				consolidatedMap.put("billAmount", (Double)values[1]);
				consolidatedMap.put("accountId", (Integer)values[0]);
			
			result.add(consolidatedMap);
	     }
     return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSegmentCalcLines> getPaymentSegmentCalcLinesListForDepositType(int paymentSegmentId) {
		return entityManager.createNamedQuery("BillingPaymentsEntity.getPaymentSegmentCalcLinesListForDepositType").setParameter("paymentSegmentId", paymentSegmentId).getResultList();
	}
	
	@Override
	public void setAdvanceBillEntityStatus(int accountId,String abBillNo,String operation,HttpServletResponse response){
		entityManager.createNamedQuery("BillingPaymentsEntity.setAdvanceBillEntityStatus").setParameter("status", "Paid").setParameter("accountId", accountId).setParameter("abBillNo", abBillNo).executeUpdate();
	}

	@Override
	public void cancelPaymentsNotPosted(HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write("Cancelled payment details are cann't be posted");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("BillingPaymentsEntity.commonFilterForAccountNumbersUrl").getResultList());
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("BillingPaymentsEntity.findPersonForFilters").getResultList();
		return details;
	}

	@Override
	public List<?> getAllReconciliations() {
		return getAllReconciliationDetails(entityManager.createNamedQuery("BillingPaymentsEntity.getAllReconciliations").getResultList());
	}
	
	private List<?> getAllReconciliationDetails(List<?> reconciliations){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> reconciliationMap = null;
		 for (Iterator<?> iterator = reconciliations.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				reconciliationMap = new HashMap<String, Object>();
				
				reconciliationMap.put("reconciliationDate", (Date)values[0]);
				reconciliationMap.put("totalReceipts", (Long)values[1]);
				reconciliationMap.put("collectionAmount", (Double)values[2]);
				if(values[3]!=null){
					reconciliationMap.put("remittanceAmount", (Double)values[3]);
					reconciliationMap.put("diffAmount", (Double)values[2]-(Double)values[3]);
				}
			
			result.add(reconciliationMap);
	     }
      return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> checkForNotPostedPaymentsAccounts(int accountId,String paymentType,String typeOfService) {
		return entityManager.createNamedQuery("BillingPaymentsEntity.checkForNotPostedAccounts").setParameter("accountId", accountId).setParameter("paymentType", paymentType).setParameter("typeOfService", typeOfService).getResultList();
	}

	@Override
	public BillingPaymentsEntity getPamentDetals(int accountId, Date fromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		try
		{
			return entityManager.createNamedQuery("BillingPaymentsEntity.getPamentDetals",BillingPaymentsEntity.class).setParameter("accountId", accountId).setParameter("month", month).setParameter("year", year).setMaxResults(1).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
	}

	@Override
	public Long getListBasedOnStatus(String className, String attribute,
			String attribute1) {
		final StringBuffer queryString = new StringBuffer("SELECT COUNT(*) FROM ");
		queryString.append(className+" bpm WHERE bpm."+attribute+"=");
	    queryString.append("'"+attribute1+"'");
		
	    final Query query = this.entityManager.createQuery(queryString.toString(),Long.class);
	    return (Long) query.getSingleResult();	
	}

	@Override
	public Double getExcessAmount(int accountId, Date fromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		try
		{
			return entityManager.createNamedQuery("BillingPaymentsEntity.getExcessAmount",Double.class).setParameter("accountId", accountId).setParameter("month", month).setParameter("year", year).setMaxResults(1).getSingleResult();
		}catch(Exception exception)
		{
			return null;
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<?> latePaymentAmount(int accountId, java.util.Date reciptedate,String typeOfService) {
	
   List<ElectricityBillEntity> billenity=entityManager.createQuery("SELECT el FROM ElectricityBillEntity el WHERE el.accountId='"+accountId+"' AND el.status='Posted' AND el.typeOfService='"+typeOfService+"'  ORDER BY el.elBillId DESC  ").setMaxResults(1).getResultList();
	System.out.println("billenity:::::::::::"+billenity.size()+"::::::::::::::"+typeOfService);
   
   Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
 java.util.Date   date1 =reciptedate;
 List<Float> lateAmount=new ArrayList<>();
   for (ElectricityBillEntity electricityBillEntity : billenity) {       
		          
		       Date duedate=electricityBillEntity.getBillDueDate();
		      // String typeOfService=electricityBillEntity.getTypeOfService();
		       if(date1.compareTo(duedate)>0){
	                
	          if (typeOfService.equalsIgnoreCase("Electricity")) {
	        	  float latepaymentamount=0f;
	        	  try{
	        	  latepaymentamount=electricityBillEntity.getLatePaymentAmount().floatValue();
	        	  }catch(NullPointerException e){
	        		  latepaymentamount=0f;  
	        	  }
				if (latepaymentamount==0f) {
					Integer tariffId = (Integer) entityManager
							.createQuery(
									"SELECT sm.elTariffID FROM ServiceMastersEntity sm WHERE sm.accountObj.accountId='"
											+ accountId
											+ "' AND sm.typeOfService='"
											+ typeOfService + "' ")
							.getSingleResult();
					int rateMasterID = rateMasterService.getRateMasterEC(
							tariffId, "Rate of interest");
					latepaymentamount = Math.round(billController
							.interestCalculationEL(rateMasterID,
									electricityBillEntity.getNetAmount()
											.floatValue(),
									electricityBillEntity));
				}
				lateAmount.add(latepaymentamount);
			}else{
				  float latepaymentamount=0f;
	        	  try{
	        	  latepaymentamount=electricityBillEntity.getLatePaymentAmount().floatValue();
	        	  }catch(NullPointerException e){
	        		  latepaymentamount=0f;  
	        	  }
				
				if (latepaymentamount==0f) {
					double camInterstRate = camConsolidationService
							.getParkingPerSlotAmount("CAM_INTEREST");
					latepaymentamount = Math.round(billController
							.interestCalculationCam(
									electricityBillEntity.getNetAmount(),
									electricityBillEntity, camInterstRate));
				}
				lateAmount.add(latepaymentamount);
			}
		       }else if(date1.compareTo(duedate)<0){
		    	   lateAmount.add(0f);
	                
	            }else{
	                
	                lateAmount.add(0f);
	            }
		       
	           }
   
   if(billenity.size()==0){
	   lateAmount.add(0f);  
   }
 
   
   return lateAmount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentSegmentCalcLines> getPaymentSegmentCalcLinesListWithLatePayment(
			int paymentSegmentId) {
		return entityManager.createNamedQuery("BillingPaymentsEntity.getPaymentSegmentCalcLinesListWithLatePayment").setParameter("paymentSegmentId", paymentSegmentId).getResultList();
	}

	@Override
	public List<?> getAllPaymentDetail() {
		return getAllpaymentDetails(entityManager.createNamedQuery("BillingPaymentsEntity.findBillingPaymentDetail").getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getAllpaymentDetails(List<?> billEntities){
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				String paymentMode=(String) values[4];
				if(paymentMode.equalsIgnoreCase("RTGS/NEFT") && paymentMode  !=null){
					paymentMap.put("paymentCollectionId", (Integer)values[0]);
					paymentMap.put("paymentDate", (Timestamp)values[1]);
					paymentMap.put("receiptDate", (Date)values[2]);
					paymentMap.put("receiptNo", (String)values[3]);
					paymentMap.put("paymentMode", (String)values[4]);
					paymentMap.put("bankName", (String)values[5]);
					paymentMap.put("instrumentDate", (Date)values[6]);
					paymentMap.put("instrumentNo", (String)values[7]);
					paymentMap.put("paymentAmount", (Double)values[8]);
					paymentMap.put("postedToGl", (String)values[9]);
					paymentMap.put("postedGlDate", (Timestamp)values[10]);
					paymentMap.put("status", (String)values[11]);
					paymentMap.put("createdBy", (String)values[12]);
					paymentMap.put("accountId", (Integer)values[13]);
					paymentMap.put("accountNo", (String)values[14]);
					paymentMap.put("partPayment", (String)values[15]);
					paymentMap.put("disputeFlag", (String)values[16]);
					paymentMap.put("paymentType", (String)values[17]);
					String personName = "";
					personName = personName.concat((String)values[18]);
					if((String)values[19] != null)
					{
						personName = personName.concat(" ");
						personName = personName.concat((String)values[19]);
					}
					paymentMap.put("personName", personName);
					paymentMap.put("excessAmount", (Double)values[20]);
					paymentMap.put("tallyStatus", (String)values[21]);
					paymentMap.put("property_No", (String)values[22]);
					paymentMap.put("blockName", (String)values[23]);
				    result.add(paymentMap);	
				}
			
			
		
	     }
		 
		 return result;
	}

	@Override
	public List<?> getAllBillPaymentDetail( java.util.Date monthToShow) {
		 Calendar cal = Calendar.getInstance();
	 	 cal.setTime(monthToShow);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);
		 

		return getAllBillpaymentDetails(entityManager.createNamedQuery("BillingPaymentsEntity.getBillPaymentDetails").setParameter("month", montOne).setParameter("year", year).getResultList());
		
	}

@SuppressWarnings("rawtypes")
private List getAllBillpaymentDetails(List<?> billEntities){
//	SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId)(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId) FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 ORDER BY elb.elBillId DESC		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				    paymentMap.put("typeOfService", (String)values[1]);
				    paymentMap.put("billAmount", (Double)values[6]);
				    
				    paymentMap.put("accountNo", (String)values[9]);
				    paymentMap.put("netAmount", (Double)values[11]);
				    paymentMap.put("arrearsAmount", (Double)values[10]);
				    
				    String personName = "";
					personName = personName.concat((String)values[12]);
					if((String)values[13] != null)
					{
						personName = personName.concat(" ");
						personName = personName.concat((String)values[13]);
					}
					paymentMap.put("personName", personName);
					paymentMap.put("propertyNo", (String)values[15]);
					paymentMap.put("blocks", (String)values[16]);
					
				    result.add(paymentMap);	
				
	     }
		 
		 return result;
	}

@Override
public List<?> getAllBillDetailsWithoutmonth() {
	Date maxmonth=null;
	int month=0;
	int montOne=0;
	int year=0;
	try {
	maxmonth=(Date) entityManager.createQuery("SELECT MAX(b.billMonth) FROM ElectricityBillEntity b").getSingleResult();
	} catch (NoResultException e ) {
	}
	if(maxmonth == null){
		Calendar cal = Calendar.getInstance();
	 	 cal.setTime(new java.util.Date());
		 month = cal.get(Calendar.MONTH);
		 montOne = month +1;
		 year = cal.get(Calendar.YEAR);
	}else{
		System.out.println("::::::::::maxmonth::::::"+maxmonth);
		Calendar cal = Calendar.getInstance();
	 	 cal.setTime(maxmonth);
		 month = cal.get(Calendar.MONTH);
		 montOne = month +1;
		 year = cal.get(Calendar.YEAR);
		 System.out.println("monthOne::::::"+montOne+"year::::::::"+year);
	}
	 

	return getAllBillpaymentDetailsWithoutMonth(entityManager.createNamedQuery("BillingPaymentsEntity.getBillPaymentDetails").setParameter("month", montOne).setParameter("year", year).getResultList());
}
@SuppressWarnings("rawtypes")
private List getAllBillpaymentDetailsWithoutMonth(List<?> billEntities){
//	SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId)(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId) FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 ORDER BY elb.elBillId DESC		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				    paymentMap.put("typeOfService", (String)values[1]);
				    paymentMap.put("billAmount", (Double)values[6]);
				    
				    paymentMap.put("accountNo", (String)values[9]);
				    paymentMap.put("netAmount", (Double)values[11]);
				    paymentMap.put("arrearsAmount", (Double)values[10]);
				    
				    String personName = "";
					personName = personName.concat((String)values[12]);
					if((String)values[13] != null)
					{
						personName = personName.concat(" ");
						personName = personName.concat((String)values[13]);
					}
					paymentMap.put("personName", personName);
					paymentMap.put("property_No", (String)values[15]);
					paymentMap.put("blockName", (String)values[16]);
					
				    result.add(paymentMap);	
				
	     }
		 
		 return result;
	}
@Override
public List<?> getAllDepositDetails() {
	
	return getAllDepositDetails(entityManager.createNamedQuery("Deposits.findAll").getResultList());
}


@SuppressWarnings("rawtypes")
private List getAllDepositDetails(List<?> billEntities){
	
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 Map<String, Object> paymentMap = null;
	 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			paymentMap = new HashMap<String, Object>();
			//@NamedQuery(name = "Deposits.findAll", query = "SELECT d.depositsId,a.accountNo,a.accountId,pt.property_No,d.totalAmount,d.status,d.depositType,d.refundAmount,d.balance,p.firstName,p.lastName FROM Deposits d,Property pt INNER JOIN d.accountObj a INNER JOIN a.person p WHERE a.propertyId=pt.propertyId ORDER BY d.depositsId DESC"),	
			
			
				paymentMap.put("depositsId", (Integer)values[0]);
				paymentMap.put("accountNo", (String)values[1]);
				//paymentMap.put("paymentDate", (Timestamp)values[1]);
				//paymentMap.put("receiptDate", (Date)values[2]);
				//paymentMap.put("receiptNo", (String)values[3]);
				//paymentMap.put("paymentMode", (String)values[4]);
				//paymentMap.put("bankName", (String)values[5]);
				//paymentMap.put("instrumentDate", (Date)values[6]);
				//paymentMap.put("instrumentNo", (String)values[7]);
				paymentMap.put("refundAmount", (Double)values[7]);
				paymentMap.put("totalAmount", (Double)values[4]);
				
				/*paymentMap.put("postedToGl", (String)values[9]);
				paymentMap.put("postedGlDate", (Timestamp)values[10]);
				paymentMap.put("status", (String)values[11]);
				paymentMap.put("createdBy", (String)values[12]);
				paymentMap.put("accountId", (Integer)values[13]);
				
				paymentMap.put("partPayment", (String)values[15]);
				paymentMap.put("disputeFlag", (String)values[16]);
				paymentMap.put("paymentType", (String)values[17]);*/
				String personName = "";
				personName = personName.concat((String)values[9]);
				if((String)values[10] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[10]);
				}
				paymentMap.put("personName", personName);
				/*paymentMap.put("excessAmount", (Double)values[20]);
				paymentMap.put("tallyStatus", (String)values[21]);*/
				paymentMap.put("property_No", (String)values[3]);
				//paymentMap.put("blockName", (String)values[22]);
				
			//	@NamedQuery(name = "DepositsLineItems.getOnDepositId", query = "SELECT d.ddId,d.typeOfServiceDeposit,d.transactionCode,d.category,d.amount,d.collectionType,d.instrumentNo,d.instrumentDate,tm.transactionName,d.paymentMode,d.bankName,dd.depositsId,d.status,d.loadChangeUnits,d.notes FROM DepositsLineItems d,TransactionMasterEntity tm INNER JOIN d.deposits dd where d.transactionCode=tm.transactionCode AND dd.depositsId=:depositsId ORDER BY d.ddId DESC"),
		List list=entityManager.createNamedQuery("DepositsLineItems.getOnDepositId").setParameter("depositsId",(Integer)values[0]).getResultList();
		for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
			Object[] object = (Object[]) iterator2.next();
			paymentMap.put("paymentMode", (String)object[9]);
			paymentMap.put("bankName", (String)object[10]);
			paymentMap.put("instrumentNo", (String)object[6]);
			break;
		}	   
		
		result.add(paymentMap);	
			}
		
		
	
     
	 
	 return result;
}

@Override
public List<?> getAllPaymentDetailforPaymentGateway() {
	return getAllpaymentDetailsforPaymentGateway(entityManager.createNamedQuery("BillingPaymentsEntity.findBillingPaymentDetail").getResultList());
}
@SuppressWarnings("rawtypes")
private List getAllpaymentDetailsforPaymentGateway(List<?> billEntities){
	
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 Map<String, Object> paymentMap = null;
	 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			paymentMap = new HashMap<String, Object>();
			
			String paymentMode=(String) values[4];
			if(paymentMode.equalsIgnoreCase("Payment Gateway") && paymentMode  !=null){
				paymentMap.put("paymentCollectionId", (Integer)values[0]);
				paymentMap.put("paymentDate", (Timestamp)values[1]);
				paymentMap.put("receiptDate", (Date)values[2]);
				paymentMap.put("receiptNo", (String)values[3]);
				paymentMap.put("paymentMode", (String)values[4]);
				paymentMap.put("bankName", (String)values[5]);
				paymentMap.put("instrumentDate", (Date)values[6]);
				paymentMap.put("instrumentNo", (String)values[7]);
				paymentMap.put("paymentAmount", (Double)values[8]);
				paymentMap.put("postedToGl", (String)values[9]);
				paymentMap.put("postedGlDate", (Timestamp)values[10]);
				paymentMap.put("status", (String)values[11]);
				paymentMap.put("createdBy", (String)values[12]);
				paymentMap.put("accountId", (Integer)values[13]);
				paymentMap.put("accountNo", (String)values[14]);
				paymentMap.put("partPayment", (String)values[15]);
				paymentMap.put("disputeFlag", (String)values[16]);
				paymentMap.put("paymentType", (String)values[17]);
				String personName = "";
				personName = personName.concat((String)values[18]);
				if((String)values[19] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[19]);
				}
				paymentMap.put("personName", personName);
				paymentMap.put("excessAmount", (Double)values[20]);
				paymentMap.put("tallyStatus", (String)values[21]);
				paymentMap.put("property_No", (String)values[22]);
				//paymentMap.put("blockName", (String)values[22]);
			    result.add(paymentMap);	
			}
		
		
	
     }
	 
	 return result;
}

@Override
public List<?> getAllBillPaymentDetailAccountWise(java.util.Date monthToShow) {
	Calendar cal = Calendar.getInstance();
	 cal.setTime(monthToShow);
	 int month = cal.get(Calendar.MONTH);
	 int montOne = month +1;
	 int year = cal.get(Calendar.YEAR);
	 

	return getAllBillpaymentDetailsAccountWise(entityManager.createNamedQuery("BillingPaymentsEntity.getBillPaymentDetails").setParameter("month", montOne).setParameter("year", year).getResultList());
	
}

@SuppressWarnings("rawtypes")
private List getAllBillpaymentDetailsAccountWise(List<?> billEntities){
	
	
//SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId)(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId) FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 ORDER BY elb.elBillId DESC		
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 Map<String, Object> paymentMap = null;
	 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			paymentMap = new HashMap<String, Object>();
			
			    paymentMap.put("typeOfService", (String)values[1]);
			    paymentMap.put("billAmount", (Double)values[6]);
			    
			    paymentMap.put("accountNo", (String)values[9]);
			    paymentMap.put("netAmount", (Double)values[11]);
			    paymentMap.put("arrearsAmount", (Double)values[10]);
			    double totalbillamount=((Double)values[6]+(Double)values[10]);
			    paymentMap.put("totalbillamount", totalbillamount);
			    String personName = "";
				personName = personName.concat((String)values[12]);
				if((String)values[13] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[13]);
				}
				paymentMap.put("personName", personName);
				 paymentMap.put("propertyNo", (String)values[15]);
				 paymentMap.put("blocks", (String)values[16]);
				System.out.println("accountno::"+(String)values[9]+":::typeOfService:::"+(String)values[1]+"::billNo::"+(String)values[7]);
				String status=(String)values[8];
				System.out.println("::::::::::::status:::::::"+status);
				if(status.equalsIgnoreCase("Paid")){
				Double paymentAmount=(Double) entityManager.createNamedQuery("BillingPaymentEntity.getPaymentAmount").setParameter("billNo", (String)values[7]).getSingleResult();	
				System.out.println("payment Amount::::::::::"+paymentAmount);
				paymentMap.put("paymentAmount", paymentAmount);
				Double dueAmount=(totalbillamount-paymentAmount);
				paymentMap.put("dueAmount", dueAmount);
				}else{
					paymentMap.put("paymentAmount",0);
					paymentMap.put("dueAmount",totalbillamount);
				}
				
				
			    result.add(paymentMap);	
			
		
		
	
    }
	 
	 return result;
}

@Override
public List<?> getAllPaymentDetailforOtherServices() {
	return getAllpaymentDetailsforotherServices(entityManager.createNamedQuery("BillingPaymentsEntity.findPaymentDetailbasedOnServiceType").getResultList());
}
@SuppressWarnings("rawtypes")
private List getAllpaymentDetailsforotherServices(List<?> billEntities){
	//SELECT d.paymentAmount,d.paymentDate,d.receiptDate,d.receiptNo,d.paymentMode,d.bankName,
	//d.instrumentDate,d.instrumentNo,p.firstName,p.lastName FROM BillingPaymentsEntity d,Account a 
	//INNER JOIN a.person p WHERE a.accountId=d.accountId And d.status !='Cancelled' And d.paymentCollectionId IN (SELECT p.billingPaymentsEntity.paymentCollectionId FROM PaymentSegmentsEntity p WHERE p.billReferenceNo In(Select b.billNo FROM ElectricityBillEntity b WHERE b.status='Paid' AND b.typeOfService='Others') )") 
	
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 Map<String, Object> paymentMap = null;
	 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			    paymentMap = new HashMap<String, Object>();
			    
				
				paymentMap.put("paymentDate", (Timestamp)values[1]);
				paymentMap.put("receiptDate", (Date)values[2]);
				paymentMap.put("receiptNo", (String)values[3]);
				paymentMap.put("paymentMode", (String)values[4]);
				paymentMap.put("bankName", (String)values[5]);
				paymentMap.put("instrumentDate", (Date)values[6]);
				paymentMap.put("instrumentNo", (String)values[7]);
				paymentMap.put("paymentAmount", (Double)values[0]);				
				String personName = "";
				personName = personName.concat((String)values[8]);
				if((String)values[9] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[9]);
				}
				paymentMap.put("personName", personName);
				
			    result.add(paymentMap);	
			}
		
	 return result;
}

@Override
public List<?> getAllOpenResidentInvoice(java.util.Date monthToShow) {
	Calendar cal = Calendar.getInstance();
	 cal.setTime(monthToShow);
	 int month = cal.get(Calendar.MONTH);
	 int montOne = month +1;
	 int year = cal.get(Calendar.YEAR);
	 

	return getAllOpenResidentInvoiceAccountWise(entityManager.createNamedQuery("BillingPaymentsEntity.getOpenInvoice").setParameter("month", montOne).setParameter("year", year).getResultList());
	
}

@SuppressWarnings("rawtypes")
private List getAllOpenResidentInvoiceAccountWise(List<?> billEntities){
	
	
//SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId)(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId) FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 ORDER BY elb.elBillId DESC		
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 Map<String, Object> paymentMap = null;
	 for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			paymentMap = new HashMap<String, Object>();
			
			    paymentMap.put("typeOfService", (String)values[1]);
			    paymentMap.put("billAmount", (Double)values[6]);
			    
			    paymentMap.put("accountNo", (String)values[9]);
			    paymentMap.put("netAmount", (Double)values[11]);
			    paymentMap.put("arrearsAmount", (Double)values[10]);
			    double totalbillamount=((Double)values[6]+(Double)values[10]);
			    paymentMap.put("totalbillamount", totalbillamount);
			    String personName = "";
				personName = personName.concat((String)values[12]);
				if((String)values[13] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[13]);
				}
				paymentMap.put("personName", personName);
				 paymentMap.put("propertyNo", (String)values[15]);
				 paymentMap.put("blocks", (String)values[16]);
				System.out.println("accountno::"+(String)values[9]+":::typeOfService:::"+(String)values[1]+"::billNo::"+(String)values[7]);
				String status=(String)values[8];
				System.out.println("::::::::::::status:::::::"+status);
				if(status.equalsIgnoreCase("Paid")){
				Double paymentAmount=(Double) entityManager.createNamedQuery("BillingPaymentEntity.getPaymentAmount").setParameter("billNo", (String)values[7]).getSingleResult();	
				System.out.println("payment Amount::::::::::"+paymentAmount);
				paymentMap.put("paymentAmount", paymentAmount);
				Double dueAmount=(totalbillamount-paymentAmount);
				paymentMap.put("dueAmount", dueAmount);
				}else{
					paymentMap.put("paymentAmount",0);
					paymentMap.put("dueAmount",totalbillamount);
				}
				System.out.println("billDate"+ (Timestamp)values[3]);
				System.out.println("billdueDate"+ (Date)values[17]);
				String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);		
				 paymentMap.put("billDate", tcDate);
				 Date date = (Date)values[17];  // wherever you get this
				 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				 String text = df.format(date);
				 
				 paymentMap.put("billdueDate",text );
			    result.add(paymentMap);	
			
		
   }
	 
	 return result;
}

@SuppressWarnings("unchecked")
@Override
public List<BillingPaymentsEntity> getPaymentDataBasedOnMonth(int montOne,int year) {
	
	return entityManager.createNamedQuery("BillingPaymentsEntity.getPaymentDataBasedOnMonth").setParameter("month", montOne).setParameter("year", year).getResultList();
}

@Override
public List<?> getAllOpenPaymentDueData(java.util.Date monthToShow) {

Calendar cal = Calendar.getInstance();
cal.setTime(monthToShow);
int month = cal.get(Calendar.MONTH);
int montOne = month +1;
int year = cal.get(Calendar.YEAR);
return getAllOpenResidentDueData(entityManager.createNamedQuery("BillingPaymentsEntity.getOpenInvoice").setParameter("month", montOne).setParameter("year", year).getResultList());

}

@SuppressWarnings("rawtypes")
private List getAllOpenResidentDueData(List<?> billEntities){


//SELECT elb.elBillId,elb.typeOfService,elb.accountId,elb.elBillDate,elb.billDate,elb.billMonth,elb.billAmount,elb.billNo,elb.status,a.accountNo,elb.arrearsAmount,elb.netAmount,p.firstName,p.lastName,elb.billType,(select pr.property_No from  Property pr where pr.propertyId = a.propertyId)(select p.blocks.blockName FROM Property p WHERE p.propertyId = a.propertyId) FROM ElectricityBillEntity elb INNER JOIN elb.accountObj a INNER JOIN a.person p WHERE EXTRACT(month FROM elb.billDate) =:month and EXTRACT(year FROM elb.billDate) =:year and elb.netAmount > 0 ORDER BY elb.elBillId DESC		
List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
Map<String, Object> paymentMap = null;
for (Iterator<?> iterator =billEntities.iterator(); iterator.hasNext();)
	{
		final Object[] values = (Object[]) iterator.next();
		paymentMap = new HashMap<String, Object>();
		
		    paymentMap.put("typeOfService", (String)values[1]);
		    paymentMap.put("billAmount", (Double)values[6]);
		    
		    paymentMap.put("accountNo", (String)values[9]);
		    paymentMap.put("netAmount", (Double)values[11]);
		    paymentMap.put("arrearsAmount", (Double)values[10]);
		    double totalbillamount=((Double)values[6]+(Double)values[10]);
		    paymentMap.put("totalbillamount", totalbillamount);
		    String personName = "";
			personName = personName.concat((String)values[12]);
			if((String)values[13] != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[13]);
			}
			paymentMap.put("personName", personName);
			 paymentMap.put("propertyNo", (String)values[15]);
			 paymentMap.put("blocks", (String)values[16]);
			System.out.println("accountno::"+(String)values[9]+":::typeOfService:::"+(String)values[1]+"::billNo::"+(String)values[7]);
			String status=(String)values[8];
			System.out.println("::::::::::::status:::::::"+status);
			if(status.equalsIgnoreCase("Paid")){
			Double paymentAmount=(Double) entityManager.createNamedQuery("BillingPaymentEntity.getPaymentAmount").setParameter("billNo", (String)values[7]).getSingleResult();	
			System.out.println("payment Amount::::::::::"+paymentAmount);
			paymentMap.put("paymentAmount", paymentAmount);
			Double dueAmount=(totalbillamount-paymentAmount);
			paymentMap.put("dueAmount", dueAmount);
			}else{
				paymentMap.put("paymentAmount",0);
				paymentMap.put("dueAmount",totalbillamount);
			}
			System.out.println("billDate"+ (Timestamp)values[3]);
			System.out.println("billdueDate"+ (Date)values[17]);
			String	tcDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[3]);		
			 paymentMap.put("billDate", tcDate);
			 paymentMap.put("billdueDate", (Date)values[17]);
		    result.add(paymentMap);	
		
	
}

return result;
}

@Override
public List<?> getAllPropertyNo() {
	return entityManager.createNamedQuery("BillingPaymentsEntity.getAllPropertyNo").getResultList();
}

@Override
public void setDepositBillEntityStatus(int accountId,String billNo,String operation,HttpServletResponse response) {
	entityManager.createNamedQuery("BillingPaymentsEntity.setDepositBillEntityStatus").setParameter("status", "Paid").setParameter("accountId", accountId).setParameter("billNo", billNo).executeUpdate();
}

@Override
public List<?> getAllAccuntNumbersBasedOnProperty(int propertyId) {
	return getAccountNumbersData(entityManager.createNamedQuery("BillingPaymentsEntity.getAllAccuntNumbersBasedOnProperty").setParameter("propertyId", propertyId).getResultList());
}

@SuppressWarnings("unchecked")
@Override
public List<BillingPaymentsEntity> searchPaymentDataByMonth(java.util.Date fromDateVal, java.util.Date toDateVal) {
	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	String s1 = formatter.format(fromDateVal);
	String s2 = formatter.format(toDateVal);
	return getAllDetails(entityManager.createNamedQuery("BillingPaymentsEntity.searchPaymentDataByMonth").setParameter("fromDateVal", s1).setParameter("toDateVal", s2).getResultList());
}

@Override
public int autoGeneratedReceiptNoNumber() {
	return ((BigDecimal)entityManager.createNativeQuery("SELECT PAYMENT_RECEIPT_NUM_SEQ.nextval FROM dual").getSingleResult()).intValue();
}

@Override
public List<?> getConsolidatedAmountBasedOnServiceType(String serviceType,int accountId) {
	String ledgerType=serviceType+" Ledger";
	String queryString = "SELECT ACCOUNT_ID,BALANCE FROM (SELECT ACCOUNT_ID,LEDGER_TYPE,BALANCE,ROW_NUMBER() OVER (PARTITION BY ACCOUNT_ID,LEDGER_TYPE ORDER BY ELL_ID DESC) AS rn FROM LEDGER WHERE POST_TYPE NOT IN ('ARREARS','INIT') AND LEDGER_TYPE ='"+ledgerType+"') WHERE rn = 1 AND ACCOUNT_ID='"+accountId+"'";
	final Query query = this.entityManager.createNativeQuery(queryString);
	return getConsolidatedAmount(query.getResultList());
}

/*===========================================Get Data for CAM payments===========================================*/
@SuppressWarnings("unchecked")
@Override
public List<BillingPaymentsEntity> getAllRecordsForCAM(java.util.Date fromMonth) {
	Calendar  cal=Calendar.getInstance();
	cal.setTime(fromMonth);
	int m1=cal.get(Calendar.MONTH);
	int month=m1+1;
	int year=cal.get(Calendar.YEAR);
	try{
		return entityManager.createNamedQuery("BillingPaymentsEntity.getAllCamPaymentRecordsForTally").setParameter("month", month).setParameter("year", year).getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Override
public void upDateTallyStatus(int paymentCollectionId, String tallyStatus) {
	try{
		entityManager.createNamedQuery("BillingPaymentsEntity.updateTallyStatusInCamPayments").setParameter("tallyStatus", tallyStatus).setParameter("paymentCollectionId", paymentCollectionId).executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
	}
	
}

@SuppressWarnings("unchecked")
@Override
public List<PaymentAdjustmentEntity> getFiftyRecordsForTally(java.util.Date monthDate) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(monthDate);
	int montOne = cal.get(Calendar.MONTH);
	int month =montOne +1;
	int year = cal.get(Calendar.YEAR);
	
	System.out.println("montOne : "+montOne);
	System.out.println("year : "+year);
//	  return entityManager.createNamedQuery("ElectricityBillEntity.getAllFiftyBill").setParameter("tallyStatus", "Not Posted").setParameter("typeOfService", serviceType).setParameter("month", montOne).setParameter("year", year).setMaxResults(50).getResultList();
	try{
	return entityManager.createNamedQuery("PaymentAdjustmentEntity.getAllFiftyBill").setParameter("tallyStatus", "Not Posted").setParameter("month", month).setParameter("year", year).setMaxResults(50).getResultList();
		
		
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
}

@Override
public List<BillingPaymentsEntity> getFiftyRecordsPaymentsForTally(java.util.Date monthDate) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(monthDate);
	int montOne = cal.get(Calendar.MONTH);
	int month =montOne +1;
	int year = cal.get(Calendar.YEAR);
	
	System.out.println("montOne : "+montOne);
	System.out.println("year : "+year);
	try{
//	return entityManager.createNamedQuery("PaymentAdjustmentEntity.getAllFiftyBill").setParameter("tallyStatus", "Not Posted").setParameter("month", month).setParameter("year", year).setMaxResults(50).getResultList();
	return entityManager.createNamedQuery("BillingPaymentsEntity.getAllFiftyBill").setParameter("tallyStatus", "Not Posted").setParameter("month", month).setParameter("year", year).setMaxResults(50).getResultList();
		
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
}

/*=================================================================================================================*/

}