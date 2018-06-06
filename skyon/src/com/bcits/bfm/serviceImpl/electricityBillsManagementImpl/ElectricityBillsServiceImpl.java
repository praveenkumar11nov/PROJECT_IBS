package com.bcits.bfm.serviceImpl.electricityBillsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
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
//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.controller.BillController;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public  class ElectricityBillsServiceImpl extends GenericServiceImpl<ElectricityBillEntity> implements ElectricityBillsService {
	
	@Autowired
	BillController billController;
	
	List<?> result=null;
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityBillEntity> findALL() {
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
		
		
		return getAllDetailsList(entityManager.createNamedQuery("ElectricityBillsEntity.findAll").setParameter("month", montOne).setParameter("year", year).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> billEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("elBillId", (Integer)values[0]);	
				billsMap.put("typeOfService",(String)values[1]);
				billsMap.put("cbId", (String)values[2]);	
				billsMap.put("accountId",(Integer)values[3]);
				billsMap.put("billDueDate",(Date)values[7]);
				billsMap.put("billMonth", (Date)values[8]);
				billsMap.put("billAmount", (Double)values[9]);
				billsMap.put("billNo", (String)values[10]);
				billsMap.put("status", (String)values[11]);
				billsMap.put("accountNumber", (String)values[12]);
				billsMap.put("arrearsAmount", (Double)values[13]);
				billsMap.put("netAmount", (Double)values[14]);
				billsMap.put("fromDate", (Date)values[17]);
				billsMap.put("billType", (String)values[18]);
				billsMap.put("avgCount", (Integer)values[19]);
				billsMap.put("avgAmount", (Double)values[20]);
				billsMap.put("advanceClearedAmount", (Double)values[21]);
				billsMap.put("tallyStatus", (String)values[22]);
				billsMap.put("postType",(String)values[5]);				
				billsMap.put("elBillDate",(Timestamp)values[4]);
				
				
				if(((String)values[5]).equalsIgnoreCase("Pre Bill")){
					billsMap.put("billDate",(Date)values[17]);
				}else{
					billsMap.put("billDate",(Date)values[6]);
				}
				
				
				
				String queryString = "Select p.property_No from Property p where p.propertyId=(Select a.propertyId from Account a where a.accountId ="+(Integer)values[3]+")";
				billsMap.put("propertyNumber", entityManager.createQuery(queryString).getSingleResult());				
				String personName = "";
				personName = personName.concat((String)values[15]);
				if((String)values[16] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[16]);
				}
				billsMap.put("personName", personName);
			
			result.add(billsMap);
	     }
      return result;
	}
/*	@Override
	public List<ElectricityBillEntity> getBillEntityByAccountId(String typeOfService) {
		return entityManager.createNamedQuery("ElectricityBillsEntity.getBillEntityByAccountId",ElectricityBillEntity.class).setParameter("typeOfService", typeOfService).getResultList();
	}*/

	@Override
	public List<?> find() {
		return entityManager.createNamedQuery("ElectricityBillsEntity.getNumberBilld").getResultList();
	}

	@Override
	public Integer checkForDuplicateMonth(int accountID, String typeOfService,int month, int year) {
		return (Integer) entityManager.createNamedQuery("ElectricityBillsEntity.checkForDuplicateMonth").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setParameter("month",month).setParameter("year", year).getSingleResult();
	}
	
	@Override
	public void setBillStatus(int elBillId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			ElectricityBillEntity billEntity = find(elBillId);
			if(operation.equalsIgnoreCase("activate"))
			{
				if(!billEntity.getPostType().equals("Deposit")){
					entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Approved").setParameter("elBillId", elBillId).executeUpdate();
					out.write("Bill details are approved");
				}else{
					entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Posted").setParameter("elBillId", elBillId).executeUpdate();
					out.write("Bill details are Posted");
				}
			}
			else if(billEntity.getStatus().equals("Posted")) {
				out.write("Bill details are already posted");
			}else{
				entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Posted").setParameter("elBillId", elBillId).executeUpdate();
				out.write("Bill details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<ElectricityBillEntity> getBillEntityByAccountId(int accountID,String typeOfService) {
		return entityManager.createNamedQuery("ElectricityBillsEntity.getBillEntityByAccountId",ElectricityBillEntity.class).setParameter("typeOfService", typeOfService).setParameter("accountID", accountID).getResultList();
	}

	@Override
	public ElectricityBillEntity getBillEntityById(int elBillId) {
		return entityManager.createNamedQuery("ElectricityBillsEntity.getBillEntityById",ElectricityBillEntity.class).setParameter("elBillId", elBillId).setMaxResults(1).getSingleResult();
	}

	@Override
	public void setApproveBill(int elBillId, String operation,HttpServletResponse response) 
	{
		try
		{
			PrintWriter out = response.getWriter();
			if(operation.equalsIgnoreCase("Generated"))
			{
				entityManager.createNamedQuery("ElectricityBillsEntity.setApproveBill").setParameter("status", "Approved").setParameter("elBillId", elBillId).executeUpdate();
				out.write("Bill Approved Successfully");
			}
			else
			{
				out.write("Bill Already Approved");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ElectricityBillEntity getPreviousBill(int accountId,String typeOfService, java.util.Date previousBillDate,String postType)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		int month = cal.get(Calendar.MONTH);
		int montOne =month +1;
		int year = cal.get(Calendar.YEAR);
		try
		{
			return entityManager.createNamedQuery("ElectricityBillsEntity.getPreviousBill",ElectricityBillEntity.class).setParameter("typeOfService", typeOfService).setParameter("accountId", accountId).setParameter("month", montOne).setParameter("year", year).setParameter("postType", postType).setMaxResults(1).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
		
	}

	@Override
	public double getConsolidatedBill(String cbId,Date billMonth) {
		return (double) entityManager.createNamedQuery("ElectricityBillsEntity.consolidatedBill").setParameter("cbId", cbId).setParameter("billMonth", billMonth).getSingleResult();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> distinctCbId(){
		return entityManager.createNamedQuery("ElectricityBillsEntity.distinctCbId").getResultList();
	}

	@Override
	public Long getCBID(int accountId, java.util.Date previousBillDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		return entityManager.createNamedQuery("ElectricityBillsEntity.getCBID",Long.class).setParameter("accountId", accountId).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityBillEntity> findAllBillData(java.util.Date presentdate,String serviceType)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(presentdate);
		int month = cal.get(Calendar.MONTH);
		int montOne =month +1;
		int year = cal.get(Calendar.YEAR);
		return entityManager.createNamedQuery("ElectricityBillsEntity.findAllBillData").setParameter("serviceType", serviceType).setParameter("month", montOne).setParameter("year", year).getResultList();
	}

	public void cancelApproveBill(int elBillId, String operation,HttpServletResponse response,int accountId,String serviceType)
	{
		try
		{
			PrintWriter out = response.getWriter();
			if(operation.equalsIgnoreCase("Generated") || operation.equalsIgnoreCase("Approved"))
			{
				entityManager.createNamedQuery("ElectricityBillsEntity.setApproveBill").setParameter("status", "Cancelled").setParameter("elBillId", elBillId).executeUpdate();
				deleteLedger(accountId,serviceType+" Ledger");
				out.write("Bill cancelled successfully");
			}
			else
			{
				out.print("Bill is already posted you cannot cancel");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void cancelAllBillsByMonth(HttpServletResponse response,String serviceType,java.util.Date presentDate) throws IOException
	{
				PrintWriter out = response.getWriter();
				Calendar cal = Calendar.getInstance();
				cal.setTime(presentDate);
				int month = cal.get(Calendar.MONTH);
				int montOne =month +1;
				int year = cal.get(Calendar.YEAR);
				synchronized (this) {
				
				List<?> electricityBills=entityManager.createNamedQuery("ElectricityBillEntity.getAllBill").setParameter("serviceType", serviceType).setParameter("month", montOne).setParameter("year", year).getResultList();
				System.out.println("Sizeeeeeeeee"+electricityBills.size());
				for (Iterator<?> iterator = electricityBills.iterator(); iterator.hasNext();) {
					final Object[] values = (Object[]) iterator.next();
					entityManager.createNamedQuery("ElectricityBillsEntity.setApproveBill").setParameter("status", "Cancelled").setParameter("elBillId", (Integer)values[0]).executeUpdate();
					deleteLedger((Integer)values[2],(String)values[1]+" Ledger");
					
				}
				/*entityManager.createNamedQuery("ElectricityBillsEntity.setApproveBill").setParameter("status", "Cancelled").setParameter("elBillId", elBillId).executeUpdate();
				deleteLedger(accountId,serviceType+" Ledger");*/
				out.write("Bill cancelled successfully");
																																																																																																																																																																																																																																																																																																																																																																																																																																																						
				}
				//out.print("Bill is already posted you cannot cancel");
			
		 
		
	}
	

	private void deleteLedger(int accountId, String ledgerType) {
		
		String postType=(String) entityManager.createQuery("SELECT  a.postType  FROM ElectricityLedgerEntity a Where a.elLedgerid IN (SELECT MAX(a.elLedgerid)FROM ElectricityLedgerEntity a WHERE a.accountId='"+accountId+"' and a.ledgerType='"+ledgerType+"') ").getSingleResult();
		
		if(postType.equalsIgnoreCase("ARREARS")){
		
		entityManager.createQuery("DELETE  FROM ElectricitySubLedgerEntity a Where a.electricityLedgerEntity.elLedgerid IN (SELECT MAX(a.elLedgerid)FROM ElectricityLedgerEntity a WHERE a.accountId='"+accountId+"' and a.ledgerType='"+ledgerType+"' and a.postType='ARREARS') ").executeUpdate();
		entityManager.createQuery("DELETE  FROM ElectricityLedgerEntity a Where a.elLedgerid IN (SELECT MAX(a.elLedgerid)FROM ElectricityLedgerEntity a WHERE a.accountId='"+accountId+"' and a.ledgerType='"+ledgerType+"' and a.postType='ARREARS') ").executeUpdate();
		}
	}


	@Override
	public void approveAllBlls(String status)
	{
		entityManager.createNamedQuery("ElectricityBillsEntity.approveAllBlls").setParameter("status", status).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override 
	public List<BigDecimal> getLastAvgBills(int accountId,String typeOfService, java.util.Date previousBillDate,String postType, int avgCount) {
		String queryString = "SELECT ELB_ID  FROM (SELECT ELB_ID, rank() over (order by ELB_ID desc) rnk FROM BILL WHERE ACCOUNT_ID="+accountId+" and TYPE_OF_SERVICE='"+typeOfService+"' and POST_TYPE='"+postType+"' and STATUS!='Cancelled' ) WHERE rnk <="+avgCount+"";
		final Query query = this.entityManager.createNativeQuery(queryString);
		return query.getResultList();
		 
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("ElectricityBillEntity.commonFilterForAccountNumbersUrl").getResultList());
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("ElectricityBillEntity.findPersonForFilters").getResultList();
		return details;
	}

	@Override
	public void setStatusApproved(HttpServletResponse response,java.util.Date presentdate,String serviceType) {
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(presentdate);
			int month = cal.get(Calendar.MONTH);
			int montOne =month +1;
			int year = cal.get(Calendar.YEAR);
		    entityManager.createNamedQuery("ElectricityBillEntity.setStatusApproved").setParameter("status", "Approved").setParameter("serviceType", serviceType).setParameter("month", montOne).setParameter("year", year).executeUpdate();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void setElectricityBillStatusAsPosted(int elBillId,HttpServletResponse response) {
		entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Posted").setParameter("elBillId", elBillId).executeUpdate();
	}

	@Override
	public List<?> findServiceTypes() {
		List<?> serviceTypesList = entityManager.createNamedQuery("ElectricityBillEntity.findServiceTypes").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> serviceMap = null;
		for (Iterator<?> iterator = serviceTypesList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceMap = new HashMap<String, Object>();				
								
				serviceMap.put("typeOfService", (String)values[0]);	
				serviceMap.put("accountId",(Integer)values[1]);
			
			result.add(serviceMap);
	     }
     return result;
	}
	
	@Override
	public List<?> findServiceTypesForBackBill() {
		List<?> serviceTypesList = entityManager.createNamedQuery("ElectricityBillEntity.findServiceTypesForBackBill").getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> serviceMap = null;
		for (Iterator<?> iterator = serviceTypesList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceMap = new HashMap<String, Object>();				
								
				serviceMap.put("typeOfService", (String)values[0]);	
				serviceMap.put("accountId",(Integer)values[1]);
			
			result.add(serviceMap);
	     }
     return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityBillEntity> getBillsNearToBillDueDate() {
		return entityManager.createNamedQuery("ElectricityBillEntity.getBillsNearToBillDueDate").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityBillEntity> getBillsOnBillDueDate() {
		return entityManager.createNamedQuery("ElectricityBillEntity.getBillsOnBillDueDate").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityBillEntity> getBillsAfterBillDueDate() {
		return entityManager.createNamedQuery("ElectricityBillEntity.getBillsAfterBillDueDate").getResultList();
	}

	@Override
	public ElectricityBillEntity getPreviousBillWithOutStatus(int accountId,String typeOfService, Date billDate, String postType) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(billDate);
		int month = cal.get(Calendar.MONTH);
		int montOne =month +1;
		int year = cal.get(Calendar.YEAR);
		
		try
		{
			return entityManager.createNamedQuery("ElectricityBillsEntity.getPreviousBillWithOutStatus",ElectricityBillEntity.class).setParameter("typeOfService", typeOfService).setParameter("accountId", accountId).setParameter("month", montOne).setParameter("year", year).setParameter("postType", postType).setMaxResults(1).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
		
	}

	@Override
	public List<?> getLastSixMontsBills(int accountId, String typeOfService,Date billDate,String unitsString) {
		return entityManager.createNamedQuery("ElectricityBillsEntity.getLastSixMontsBills").setParameter("typeOfService", typeOfService).setParameter("accountId", accountId).setParameter("billDate", billDate).setParameter("unitsString", unitsString).setMaxResults(6).getResultList();
	}
	
	@Override
	 public List<?> getLastSixMonthsCAMBills(int accountId,String typeOfService, Date billDate) {
	  return entityManager.createNamedQuery("ElectricityBillEntity.getLastSixMonthsCAMBills").setParameter("typeOfService", typeOfService).setParameter("accountId", accountId).setParameter("billDate", billDate).setMaxResults(6).getResultList();
	 }

	@Override
	public double getLineItemAmountBasedOnTransactionCode(int elBillId,	String transactionCode) {
		try{
			return (Double)entityManager.createNamedQuery("ElectricityBillEntity.getLineItemAmountBasedOnTransactionCode").setParameter("elBillId", elBillId).setParameter("transactionCode", transactionCode).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}
	
	@Override
	public List<?> getLastSixMontsBillsOthers(int accountId,String typeOfService, Date billDate, String string) {
		return entityManager.createNamedQuery("ElectricityBillsEntity.getLastSixMontsBillsOthers").setParameter("typeOfService", typeOfService).setParameter("accountId", accountId).setParameter("billDate", billDate).setMaxResults(6).getResultList();
	}

	@Override
	public List<ElectricityBillEntity> downloadAllBills(String serviceType,java.util.Date currntMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currntMonth);
		int month = cal.get(Calendar.MONTH);
		int montOne =month +1;
		int year = cal.get(Calendar.YEAR);
		try
		{
			return entityManager.createNamedQuery("ElectricityBillsEntity.downloadAllBills",ElectricityBillEntity.class).setParameter("typeOfService", serviceType).setParameter("month", montOne).setParameter("year", year).getResultList();
		}
		catch(Exception exception)
		{
			return null;
		}
	}

	@Override
	public String getBillingConfigValue(String configName, String status) {
		try { 
			return entityManager.createNamedQuery("BillingConfiguration.getBillingConfigValue",String.class).setParameter("configName", configName).setParameter("status", status).getSingleResult();
		} catch (Exception e) {
		return null;
		}
      }

	@Override
	public List<?> readBillsMonthWise(java.util.Date monthToShow) {
		 Calendar cal = Calendar.getInstance();
	 	 cal.setTime(monthToShow);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);
		 return getAllDetailsListMothWise(entityManager.createNamedQuery("ElectricityBillsEntity.readBillsMonthWise").setParameter("month", montOne).setParameter("year", year).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsListMothWise(List<?> billEntities){
		

		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("elBillId", (Integer)values[0]);	
				billsMap.put("typeOfService",(String)values[1]);
				billsMap.put("cbId", (String)values[2]);	
				billsMap.put("accountId",(Integer)values[3]);
				System.out.println(":::::::::elBillDate:::::: "+(Timestamp)values[4]);				
				String S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)values[4]);
				 System.out.println("::::::timestapdate:::"+S);
		//		String formattedTime = output.format(d);
				billsMap.put("elBillDate",S);
						
				billsMap.put("billDueDate",(Date)values[7]);
				String billMonth = new SimpleDateFormat("MMM yyyy",Locale.UK).format((Date)values[8]);
				 System.out.println("::::::timestapdate:::"+S);
				System.out.println("::::::::::(Date)values[8]::::::::::::"+(Date)values[8]);
				billsMap.put("billMonth", billMonth);
				billsMap.put("billAmount", (Double)values[9]);
				billsMap.put("billNo", (String)values[10]);
				billsMap.put("status", (String)values[11]);
				billsMap.put("accountNumber", (String)values[12]);
				billsMap.put("arrearsAmount", (Double)values[13]);
				billsMap.put("netAmount", (Double)values[14]);
				billsMap.put("billType", (String)values[18]);
				billsMap.put("avgCount", (Integer)values[19]);
				billsMap.put("avgAmount", (Double)values[20]);
				billsMap.put("advanceClearedAmount", (Double)values[21]);
				billsMap.put("tallyStatus", (String)values[22]);
				billsMap.put("billDate",(Date)values[6]);
				billsMap.put("fromDate", (Date)values[17]);
				billsMap.put("postType",(String)values[5]);
				//billsMap.put("elBillDate",(Timestamp)values[4]);
				if(((String)values[5]).equalsIgnoreCase("Pre Bill")){
					billsMap.put("billDate",(Date)values[17]);
				}else{
					billsMap.put("billDate",(Date)values[6]);
				}
				
				
				String queryString = "Select p.property_No from Property p where p.propertyId=(Select a.propertyId from Account a where a.accountId ="+(Integer)values[3]+")";
				billsMap.put("propertyNumber", entityManager.createQuery(queryString).getSingleResult());				
				String personName = "";
				personName = personName.concat((String)values[15]);
				if((String)values[16] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[16]);
				}
				billsMap.put("personName", personName);
			 
			result.add(billsMap);
	     }
     return result;
	}
	@Override
	public void updateBillDoc(int elBillId,Blob blob) {
		entityManager.createNamedQuery("ElectricityBillEntity.updateBillDoc").setParameter("elBillId", elBillId).setParameter("blob", blob).executeUpdate();
	}

	@Override
	public Blob getBlog(int elBillId) {
		try {
			return entityManager.createNamedQuery("BillDocument.getBlog",Blob.class).setParameter("elBillId", elBillId).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findALLBills() {
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
		return entityManager.createNamedQuery("ElectricityBillsEntity.findAll").setParameter("month", montOne).setParameter("year", year).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> readBillMonthWise(java.util.Date monthToShow) {
		Calendar cal = Calendar.getInstance();
	 	 cal.setTime(monthToShow);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);
		 
		return entityManager.createNamedQuery("ElectricityBillsEntity.readBillsMonthWise").setParameter("month", montOne).setParameter("year", year).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public @ResponseBody List<?> getAllUnitDetails2(String month1) throws ParseException {
		java.util.Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month1);
		Calendar cal = Calendar.getInstance();
	 	 cal.setTime(monthToShow);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);
		 System.out.println("month--------------------------------"+montOne);
		 System.out.println("years--------------------------------"+year);
		List<?> billEntities=entityManager.createNamedQuery("ServiceMastersEntity.getAllAccount").getResultList();
		System.out.println("+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+billEntities.size());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("personId", (Integer)values[2]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				billsMap.put("personName", personName);	
				System.out.println("::::::::::personname::::::::::::"+personName);
				billsMap.put("accountId",(Integer)values[0]);
				billsMap.put("accountNo",(String)values[1]);
				//billsMap.put("netAmount", (Double)values[1]);
				//billsMap.put("billMonth", (Date)values[2]);
				//billsMap.put("netAmount", (Double)values[10]);
				billsMap.put("propertyno", (String)values[5]);
				System.out.println((String)values[5]);
				/*List<Integer> billmonthlist=new ArrayList<>();*/
				List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBills").setParameter("month",month).setParameter("year",year).setParameter("accountId", (Integer)values[0]).getResultList();
				System.out.println("size:::::::::::::::::"+billdetail.size());
				for (ElectricityBillEntity electricityBillEntity : billdetail) {
					System.out.println(":::::::::::::::::"+electricityBillEntity.getBillMonth());
					billsMap.put("serviceType", electricityBillEntity.getTypeOfService());
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));
					billsMap.put("consumption", getUnits(electricityBillEntity.getElBillId(),"Units"));
					
					/*System.out.println("amount billlllllllllllllllllllllllllllll"+electricityBillEntity.getBillAmount());*/
					/*Date billmonth=electricityBillEntity.getBillMonth();*/
				/*int month1=	billmonth.getMonth()+1;*/
				/*billmonthlist.add(month);*/
				System.out.println("month:::::::");
				
					
				}
				
				
				
			 
			result.add(billsMap);
	     }
		 
		 System.out.println();
		 return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public @ResponseBody List<?> getAllBillDetails2(String month1) throws java.text.ParseException  {
		java.util.Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month1);
		Calendar cal = Calendar.getInstance();
	 	 cal.setTime(monthToShow);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);
		List<?> billEntities=entityManager.createNamedQuery("ServiceMastersEntity.getAllAccount").getResultList();
		System.out.println("+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+billEntities.size());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("personId", (Integer)values[2]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				billsMap.put("personName", personName);	
				System.out.println("::::::::::personname::::::::::::"+personName);
				billsMap.put("accountId",(Integer)values[0]);
				billsMap.put("accountNo",(String)values[1]);
				billsMap.put("propertyno", (String)values[5]);
				System.out.println((String)values[5]);
				List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBills").setParameter("month",montOne).setParameter("year",year).setParameter("accountId", (Integer)values[0]).getResultList();
				System.out.println("size:::::::::::::::::"+billdetail.size());
				for (ElectricityBillEntity electricityBillEntity : billdetail) {
					System.out.println(":::::::::::::::::"+electricityBillEntity.getBillMonth());
					billsMap.put("serviceType", electricityBillEntity.getTypeOfService());
					billsMap.put("billAmount", electricityBillEntity.getBillAmount());
				}
			result.add(billsMap);
	     }
		 
		 System.out.println();
		 return result;
	}
	
	@Override
	public List<?> getAllBillDetailsMIS() {
		return	getAllBillDetails(entityManager.createNamedQuery("ServiceMastersEntity.getAllAccount").getResultList());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	private List getAllBillDetails(List<?> billEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("personId", (Integer)values[2]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				billsMap.put("personName", personName);	
				System.out.println("::::::::::petsonname::::::::::::"+personName);
				billsMap.put("accountId",(Integer)values[0]);
				billsMap.put("accountNo",(String)values[1]);
				//billsMap.put("netAmount", (Double)values[1]);
				//billsMap.put("billMonth", (Date)values[2]);
				//billsMap.put("netAmount", (Double)values[1]);
				billsMap.put("propertyno", (String)values[5]);
				System.out.println((String)values[5]);
				List<Integer> billmonthlist=new ArrayList<>();
				List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBillsMIS").setParameter("accountId", (Integer)values[0]).getResultList();
				System.out.println(":::::::::::::::::"+billdetail.size());
				for (ElectricityBillEntity electricityBillEntity : billdetail) {
					System.out.println(":::::::::::::::::"+electricityBillEntity.getBillMonth());
					billsMap.put("serviceType", electricityBillEntity.getTypeOfService());
					billsMap.put("billAmount", electricityBillEntity.getBillAmount());
					billsMap.put("consumption", getUnits(electricityBillEntity.getElBillId(),"Units"));
					Date billmonth=electricityBillEntity.getBillMonth();
				int month=	billmonth.getMonth()+1;
				billmonthlist.add(month);
				System.out.println("month:::::::");
				if(month==1){
					billsMap.put("jan", electricityBillEntity.getBillAmount());
				}else if(month==2){
					billsMap.put("feb", electricityBillEntity.getBillAmount());
				}else if(month==3){
					billsMap.put("march", electricityBillEntity.getBillAmount());
				}else if(month==4){
					billsMap.put("april", electricityBillEntity.getBillAmount());
				}
				else if(month==5){
					billsMap.put("may", electricityBillEntity.getBillAmount());
				}else if(month==6){
					billsMap.put("june", electricityBillEntity.getBillAmount());
				}else if(month==7){
					billsMap.put("july", electricityBillEntity.getBillAmount());
				}else if(month==8){
					billsMap.put("august", electricityBillEntity.getBillAmount());
				}else if(month==9){
					billsMap.put("sepetember", electricityBillEntity.getBillAmount());
				}else if(month==10){
					billsMap.put("october", electricityBillEntity.getBillAmount());
				}
				else if(month==11){
					billsMap.put("november", electricityBillEntity.getBillAmount());
				}
				else if(month==12){
					billsMap.put("december", electricityBillEntity.getBillAmount());
				}
					
				}
				
				
				
			 
			result.add(billsMap);
	     }
      return result;
	}

	@Override
	public List<?> getAllUnitDetailsMIS() {
		return	getAllUnitDetails(entityManager.createNamedQuery("ServiceMastersEntity.getAllAccount").getResultList());
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation", "unused", "unchecked" })
	private List getAllUnitDetails(List<?> billEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("personId", (Integer)values[2]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				billsMap.put("personName", personName);	
				System.out.println("::::::::::petsonname::::::::::::"+personName);
				billsMap.put("accountId",(Integer)values[0]);
				billsMap.put("accountNo",(String)values[1]);
				//billsMap.put("netAmount", (Double)values[1]);
				//billsMap.put("billMonth", (Date)values[2]);
				//billsMap.put("netAmount", (Double)values[1]);
				String bill=null;
				Float billunit=null;
				billsMap.put("propertyno", (String)values[5]);
				System.out.println((String)values[5]);
				List<Integer> billmonthlist=new ArrayList<>();
				List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBillsMIS").setParameter("accountId", (Integer)values[0]).getResultList();
				System.out.println(":::::::::::::::::"+billdetail.size());
				for (ElectricityBillEntity electricityBillEntity : billdetail) {
					System.out.println(":::::::::::::::::"+electricityBillEntity.getBillMonth());
					Date billmonth=electricityBillEntity.getBillMonth();
					billsMap.put("serviceType", electricityBillEntity.getTypeOfService());
				int month=	billmonth.getMonth()+1;
				billmonthlist.add(month);
				System.out.println("month:::::::");
				if(month==1){			
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));
					billsMap.put("jan", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==2){					
					
					billsMap.put("feb", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==3){
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("march",getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==4){
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("april", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}
				else if(month==5){			
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));
					billsMap.put("may", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==6){
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));										
					billsMap.put("june",getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==7){
					
					billsMap.put("july", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==8){
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("august", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}else if(month==9){					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("september", getUnits(electricityBillEntity.getElBillId(),"Units"));
        			}else if(month==10){					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("october", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}
				else if(month==11){
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("november", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}
				else if(month==12){
					
					System.out.println("bill"+getUnits(electricityBillEntity.getElBillId(),"Units"));					
					billsMap.put("december", getUnits(electricityBillEntity.getElBillId(),"Units"));
				}
					
				}
				
				
				
			 
			result.add(billsMap);
	     }
      return result;
	}
	public float getUnits(Integer elbillId,String Units){
		String bill=null;
		Float billUnit=0.0f;
		try {
			bill=(String) entityManager.createNamedQuery("ElectricityBillsEntity.getBillUnits").setParameter("elBillId",  elbillId).setParameter("Units", Units).getSingleResult();
		System.out.println(":::::::::accounId:::::::::::"+bill);
		} catch (NoResultException e) {
		}
		if(bill==null){
			billUnit=0.0f;
		}else{
			billUnit=Float.parseFloat(bill);
		}
	return billUnit;
	}

	@Override
	public List<?> getAllAmrDateDetails() {
		 return	 getAllUAmrBillDetails(entityManager.createNamedQuery("ServiceMastersEntity.getAllAccountWithMeter").getResultList());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private List getAllUAmrBillDetails(List<?> billEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("personId", (Integer)values[2]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				billsMap.put("personName", personName);	
				System.out.println("::::::::::petsonname::::::::::::"+personName);
				billsMap.put("accountId",(Integer)values[0]);
				billsMap.put("accountNo",(String)values[1]);
				//billsMap.put("meterSrNo",(String)values[6]);
				//System.out.println("meterSrNo"+(String)values[6]);
				//billsMap.put("netAmount", (Double)values[1]);
				//billsMap.put("billMonth", (Date)values[2]);
				//billsMap.put("netAmount", (Double)values[1]);
				String bill=null;
				Float billunit=null;
				billsMap.put("propertyno", (String)values[5]);
				System.out.println((String)values[5]);
				List<Integer> billmonthlist=new ArrayList<>();
				List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBillsMisAmr").setParameter("accountId", (Integer)values[0]).setMaxResults(1).getResultList();
				System.out.println(":::::::::::::::::"+billdetail.size());
				for (ElectricityBillEntity electricityBillEntity : billdetail) {
					System.out.println("electricityBillEntity.getFromDate();"+electricityBillEntity.getFromDate());
					System.out.println(":::::::::::::::::::::::"+electricityBillEntity.getBillDate());
					billsMap.put("presentBillDate",electricityBillEntity.getBillDate());
					billsMap.put("prevBillDate", electricityBillEntity.getFromDate());
					Float previousreading=getUnits(electricityBillEntity.getElBillId(), "Previous reading");
					Float presentreading=getUnits(electricityBillEntity.getElBillId(), "Present reading");
					Float previousdgreading=getUnits(electricityBillEntity.getElBillId(), "Previous DG reading");
					Float presentdgreading=getUnits(electricityBillEntity.getElBillId(), "Present  DG reading");
					billsMap.put("prevBillUnit",previousreading);
					billsMap.put("presentBillUnit", presentreading);
					billsMap.put("prevBilldgUnit",previousdgreading);
					billsMap.put("presentBilldgUnit", presentdgreading);
				
				}
					
				
				
				
				
			 
			result.add(billsMap);
	     }
      return result;
}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<?> getAllBillLinItemDetails2(String month1)
			throws ParseException {
		java.util.Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month1);
		Calendar cal = Calendar.getInstance();
	 	 cal.setTime(monthToShow);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);
		 
		 System.out.println("3rd report ########################  ==========");
		 System.out.println("month============="+month);
		 System.out.println("montOne============="+montOne);
		 System.out.println("year============="+year);
		List<?> billEntities=entityManager.createNamedQuery("ServiceMastersEntity.getAllAccountWithMeter").getResultList();
		
		 System.out.println("3rd report ########################=============---------"+" GetAllAccountWithMeter query... "+billEntities);
		 
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("personId", (Integer)values[2]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if((String)values[4] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				billsMap.put("personName", personName);	
				System.out.println("::::::::::petsonname::::::::::::"+personName);
				billsMap.put("accountId",(Integer)values[0]);
				billsMap.put("accountNo",(String)values[1]);
				
				String bill=null;
				Float billunit=null;
				billsMap.put("propertyno", (String)values[5]);
				System.out.println((String)values[5]);
				List<Integer> billmonthlist=new ArrayList<>();
				List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBillsLine").setParameter("month", montOne).setParameter("year", year).setParameter("accountId", (Integer)values[0]).setMaxResults(1).getResultList();
				System.out.println(":::::::::::::::::--------"+billdetail.size());
				
				System.out.println("3rd customerwise detailed billing report----- "+"ElectricityBillsEntity.readBillsLine---------------------"+billdetail);
				for (ElectricityBillEntity electricityBillEntity : billdetail) {							
					System.out.println(":::::::::::::::::::::::"+electricityBillEntity.getBillDate());							
					billsMap.put("month", electricityBillEntity.getBillDate());
					Double energycharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_EC");
					System.out.println("::::::::::energycharge::::::"+energycharge);
					Double roundoff=getBillLineItem(electricityBillEntity.getElBillId(), "EL_ROF");
					billsMap.put("head1",energycharge);
					billsMap.put("head5", roundoff);
					System.out.println("::::::::::roundoff::::::"+roundoff);
					Double dgcharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_DG");
					Double arrear=getBillLineItem(electricityBillEntity.getElBillId(), "BILL_ARR");
					//Double arrear=getBillLineItem(electricityBillEntity.getElBillId(), "BILL_ARR");
					
					Double latepaymentcharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_LPC");
					billsMap.put("head2",dgcharge);
					billsMap.put("head4",electricityBillEntity.getArrearsAmount());
					billsMap.put("head3",latepaymentcharge);
				}
					
				
				
				
				
			 
			result.add(billsMap);
	     }
      return result;
	}

	@Override
	public List<?> getAllBillLinItemDetails() {
		
	
		 return	 getBillLineItemDetails(entityManager.createNamedQuery("ServiceMastersEntity.getAllAccountWithMeter").getResultList());
			}
			
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
    private List getBillLineItemDetails(List<?> billEntities){
				
				 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				 Map<String, Object> billsMap = null;
				 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						billsMap = new HashMap<String, Object>();				
											
						billsMap.put("personId", (Integer)values[2]);
						
						String personName = "";
						personName = personName.concat((String)values[3]);
						if((String)values[4] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)values[4]);
						}
						billsMap.put("personName", personName);	
						System.out.println("::::::::::petsonname::::::::::::"+personName);
						billsMap.put("accountId",(Integer)values[0]);
						billsMap.put("accountNo",(String)values[1]);
						
						String bill=null;
						Float billunit=null;
						billsMap.put("propertyno", (String)values[5]);
						System.out.println((String)values[5]);
						List<Integer> billmonthlist=new ArrayList<>();
						List<ElectricityBillEntity> billdetail=entityManager.createNamedQuery("ElectricityBillsEntity.readBillsMisAmr").setParameter("accountId", (Integer)values[0]).setMaxResults(1).getResultList();
						System.out.println(":::::::::::::::::"+billdetail.size());
						for (ElectricityBillEntity electricityBillEntity : billdetail) {							
							System.out.println(":::::::::::::::::::::::"+electricityBillEntity.getBillDate());							
							billsMap.put("month", electricityBillEntity.getBillDate());
							Double energycharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_EC");
							System.out.println("::::::::::energycharge::::::"+energycharge);
							Double roundoff=getBillLineItem(electricityBillEntity.getElBillId(), "EL_ROF");
							billsMap.put("head1",energycharge);
							billsMap.put("head5", roundoff);
							System.out.println("::::::::::roundoff::::::"+roundoff);
							Double dgcharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_DG");
							Double arrear=getBillLineItem(electricityBillEntity.getElBillId(), "BILL_ARR");
							//Double arrear=getBillLineItem(electricityBillEntity.getElBillId(), "BILL_ARR");
							
							Double latepaymentcharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_LPC");
							billsMap.put("head2",dgcharge);
							billsMap.put("head4",electricityBillEntity.getArrearsAmount());
							billsMap.put("head3",latepaymentcharge);
						}
							
						
						
						
						
					 
					result.add(billsMap);
			     }
		      return result;

}
    public Double getBillLineItem(Integer elbillId,String trCode){
		Double bill=null;
		
		try {
			bill=(Double) entityManager.createNamedQuery("ElectricityBillLineItemEntity.findBalanceTransactionCode").setParameter("elBillId",  elbillId).setParameter("transactionCode", trCode).getSingleResult();
		System.out.println(":::::::::bill:::::::::::"+bill);
		} catch (NoResultException e) {
		}
		if(bill==null){
			bill=0.0;
		}else{
			bill=bill;
		}
	return bill;
	}

	@Override
	public List<?> getAllBillPaymentDetails() {
	
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
				String status=(String)values[11];
				if (!status.equalsIgnoreCase("Cancelled")) {
					//paymentMap.put("paymentCollectionId", (Integer)values[0]);
					String S = new SimpleDateFormat("dd/MM/yyyy", Locale.UK)
							.format((Timestamp) values[1]);
					//rateMaster.put("readingDate", S);
					paymentMap.put("month", S);
					paymentMap.put("collectionAmount", (Double) values[8]);
					//paymentMap.put("accountId", (Integer)values[13]);
					paymentMap.put("accountNo", (String) values[14]);
					paymentMap.put("partPayment", (String) values[15]);
					String personName = "";
					personName = personName.concat((String) values[18]);
					if ((String) values[19] != null) {
						personName = personName.concat(" ");
						personName = personName.concat((String) values[19]);
					}
					paymentMap.put("personName", personName);
					//paymentMap.put("excessAmount", (Double)values[20]);
					paymentMap.put("propertyno", (String) values[22]);
					Double BilledAmount = ((Double) values[8] - (Double) values[20]);
					paymentMap.put("monthBilledAmount", BilledAmount);
					result.add(paymentMap);
					Double taxAmount = getBillPaymentDetail(
							(Integer) values[0], "EL_TAX");
					Double arrearAmount = getBillPaymentDetail(
							(Integer) values[0], "BILL_ARR");
					Double interestAmount = getBillPaymentDetail(
							(Integer) values[0], "EL_INTEREST");
					paymentMap.put("vat", taxAmount);
					paymentMap.put("arrearsTax", arrearAmount);
					paymentMap.put("interest", interestAmount);
				}
	     }
      return result;
	}
	@SuppressWarnings("unchecked")
	public Double getBillPaymentDetail(Integer paymentCollectionId,String trCode){
		Double bill=0.0;
		List<Double> payemntItem=null;
		
		try {
			payemntItem= entityManager.createNamedQuery("PaymentSegmentCalcLines.findAllByIdMIS").setParameter("paymentCollectionId",  paymentCollectionId).setParameter("transactionCode", trCode).getResultList();
		
			for (Double double1 : payemntItem) {
				bill=(bill+double1);
			}
		} catch (NoResultException e) {
		}
		if(bill==0){
			bill=0.0;
		}else{
			bill=bill;
		}
	return bill;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityBillEntity> getFiftyRecordsForTally(String serviceType,java.util.Date currntMonth ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currntMonth);
		int montOne = cal.get(Calendar.MONTH);
		int month =montOne +1;
		int year = cal.get(Calendar.YEAR);
		String typeOfService = serviceType;
		try
		{
		  List<ElectricityBillEntity> list50Bill = entityManager.createNamedQuery("ElectricityBillEntity.getAllFiftyBill").setParameter("tallyStatus", "Not Posted").setParameter("typeOfService", typeOfService).setParameter("month", month).setParameter("year", year).setMaxResults(50).getResultList();
		  System.err.println("list50Bill="+list50Bill); 
		  return list50Bill;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ElectricityBillEntity> downloadAllBillsOnAccountNo(
			String serviceType, java.util.Date monthDate,
			java.util.Date fromDate, int accNo) {
	
		 
		
        String strDate = new SimpleDateFormat("YYYY-MM-dd").format(fromDate);
	   String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(monthDate);
		
	   try
		{
			return entityManager.createNamedQuery("ElectricityBillsEntity.downloadBillONAccount",ElectricityBillEntity.class).setParameter("typeOfService", serviceType).setParameter("strDate", strDate).setParameter("pesentDate", pesentDate).setParameter("accNo", accNo).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	   
		
	}

	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<?> readDuplicateBillMonthWise(java.util.Date monthToShow,String serviceType) {
		
		Calendar cal = Calendar.getInstance();
	 	cal.setTime(monthToShow);
		int month = cal.get(Calendar.MONTH);
		int montOne = month +1;
		int year = cal.get(Calendar.YEAR);
    	String date=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

    	String queryString="SELECT * FROM BILL WHERE BILL.BILL_MONTH=TO_DATE('"+date+"', 'yyyy-mm-dd') AND TYPE_OF_SERVICE='"+serviceType+"' AND STATUS !='Cancelled' AND ACCOUNT_ID IN (select ACCOUNT_ID from BILL WHERE BILL.BILL_MONTH=TO_DATE('"+date+"', 'yyyy-mm-dd') AND TYPE_OF_SERVICE='"+serviceType+"' AND STATUS !='Cancelled' group by ACCOUNT_ID having count (ACCOUNT_ID) > 1)";		 
    	Query query= this.entityManager.createNativeQuery(queryString,ElectricityBillEntity.class);
    	List<ElectricityBillEntity> billdata=query.getResultList();

    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	Map<String, Object> billsMap = null;
    	for (ElectricityBillEntity electricityBillEntity : billdata)
    	{
		
		billsMap = new HashMap<String, Object>();				
							
		billsMap.put("elBillId", electricityBillEntity.getElBillId());	
		billsMap.put("typeOfService",electricityBillEntity.getTypeOfService());
		billsMap.put("cbId", electricityBillEntity.getCbId());	
		billsMap.put("accountId",electricityBillEntity.getAccountId());
		billsMap.put("billDueDate",electricityBillEntity.getBillDueDate());
		billsMap.put("billMonth", electricityBillEntity.getBillMonth());
		billsMap.put("billAmount", electricityBillEntity.getBillAmount());
		billsMap.put("billNo", electricityBillEntity.getBillNo());
		billsMap.put("status", electricityBillEntity.getStatus());
		billsMap.put("accountNumber", electricityBillEntity.getAccountObj().getAccountNo());
		billsMap.put("arrearsAmount", electricityBillEntity.getArrearsAmount());
		billsMap.put("netAmount", electricityBillEntity.getNetAmount());
		billsMap.put("fromDate", electricityBillEntity.getFromDate());
		billsMap.put("billType", electricityBillEntity.getBillType());
		billsMap.put("avgCount", electricityBillEntity.getAvgCount());
		billsMap.put("avgAmount", electricityBillEntity.getAvgAmount());
		billsMap.put("advanceClearedAmount", electricityBillEntity.getAdvanceClearedAmount());
		billsMap.put("tallyStatus", electricityBillEntity.getTallyStatus());
		billsMap.put("postType",electricityBillEntity.getPostType());
		String S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format(electricityBillEntity.getElBillDate());
		billsMap.put("elBillDate",S);
		
		
		if((electricityBillEntity.getPostType()).equalsIgnoreCase("Pre Bill")){
			billsMap.put("billDate",electricityBillEntity.getBillDate());
		}else{
			billsMap.put("billDate",electricityBillEntity.getBillDate());
		}
		
		
		
		String queryString1 = "Select p.property_No from Property p where p.propertyId=(Select a.propertyId from Account a where a.accountId ="+electricityBillEntity.getAccountId()+")";
		billsMap.put("propertyNumber", entityManager.createQuery(queryString1).getSingleResult());				
		String personName = "";
		personName = personName.concat(electricityBillEntity.getAccountObj().getPerson().getFirstName());
		if(electricityBillEntity.getAccountObj().getPerson().getLastName() != null)
		{
			personName = personName.concat(" ");
			personName = personName.concat(electricityBillEntity.getAccountObj().getPerson().getLastName());
		}
		billsMap.put("personName", personName);
	
	result.add(billsMap);
}
return result;
}

	@Override
	public List<?> fetchBillsDataBasedOnMonthAndServiceType(int actualmonth,int year, String serviceType) {
		return entityManager.createNamedQuery("ElectricityBillEntity.fetchBillsDataBasedOnMonthAndServiceType").setParameter("typeOfService", serviceType).setParameter("actualmonth", actualmonth).setParameter("year", year).setMaxResults(50).getResultList();
	}

	@Override
	public void setElectricityBillStatusAsPaid(int elBillId,HttpServletResponse response) {
		entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Paid").setParameter("elBillId", elBillId).executeUpdate();
	}

	@Override
	public void setBillStatusAsPaid(int elBillId, String operation,	HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			ElectricityBillEntity billEntity = find(elBillId);
			if(operation.equalsIgnoreCase("activate"))
			{
				if(!billEntity.getPostType().equals("Deposit")){
					entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Approved").setParameter("elBillId", elBillId).executeUpdate();
					out.write("Bill details are approved");
				}else{
					entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Paid").setParameter("elBillId", elBillId).executeUpdate();
					out.write("Bill details are posted and Paid");
				}
			}
			else if(billEntity.getStatus().equals("Posted")) {
				out.write("Bill details are already posted");
			}else{
				entityManager.createNamedQuery("ElectricityBillsEntity.setBillStatus").setParameter("status", "Paid").setParameter("elBillId", elBillId).executeUpdate();
				out.write("Bill details are posted and paid");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<ElectricityBillEntity> findBasedOnAccountId(Integer accountId,
			String typeOfService) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT e FROM ElectricityBillEntity e WHERE e.elBillId IN (SELECT MAX(e.elBillId) FROM ElectricityBillEntity e WHERE e.accountId='"+accountId+"' AND e.typeOfService='"+typeOfService+"' AND e.status IN ('Posted','Paid')) ").getResultList();
	}

	@Override
	public void updateMailSent_Status(int elBillId,String typeOfService,String status) {
		entityManager.createQuery("Update ElectricityBillEntity o set o.mailSent_Status=:status Where o.elBillId=:elBillId AND o.typeOfService =:typeOfService").setParameter("status", status).setParameter("elBillId", elBillId).setParameter("typeOfService", typeOfService).executeUpdate();
		
		
	}

	@Override
	public List<Object[]> getCo_OwnerDetails(int propertyId) {
		return entityManager.createNamedQuery("Owner.getCo_OwnerDetails").setParameter("propertyId", propertyId).getResultList();
	}

	@Override
	public List<?> saveLatePaymentWise(java.util.Date date1,
			String serviceType) {

		Calendar cal = Calendar.getInstance();
	 	cal.setTime(date1);
		int month = cal.get(Calendar.MONTH);
		int montOne = month +1;
		int year = cal.get(Calendar.YEAR);
    	String date=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		String queryString="SELECT * FROM BILL WHERE BILL.BILL_MONTH=TO_DATE('"+date+"', 'yyyy-mm-dd') AND TYPE_OF_SERVICE='"+serviceType+"' AND STATUS !='Cancelled'";		 
    	Query query= this.entityManager.createNativeQuery(queryString,ElectricityBillEntity.class);
    	List<ElectricityBillEntity> billdata=query.getResultList();
    	if(serviceType.equalsIgnoreCase("Electricity")){
    	for (ElectricityBillEntity electricityBillEntity : billdata) {
    		double	latePaymentAnount = billController.interestCalculationEL(1335, electricityBillEntity.getNetAmount().floatValue(),electricityBillEntity);	
    		
    		entityManager.createQuery("Update ElectricityBillEntity o set o.latePaymentAmount=:latePaymentAnount Where o.elBillId=:elBillId ").setParameter("latePaymentAnount", latePaymentAnount).setParameter("elBillId", electricityBillEntity.getElBillId()).executeUpdate();
		}
    	}else if(serviceType.equalsIgnoreCase("CAM")){
    		for (ElectricityBillEntity electricityBillEntity : billdata) {
        		double	latePaymentAnount =billController.latepaymentAmountCam(electricityBillEntity.getArrearsAmount()+electricityBillEntity.getBillAmount(),electricityBillEntity, 2)	;	
        		
        		entityManager.createQuery("Update ElectricityBillEntity o set o.latePaymentAmount=:latePaymentAnount Where o.elBillId=:elBillId ").setParameter("latePaymentAnount", latePaymentAnount).setParameter("elBillId", electricityBillEntity.getElBillId()).executeUpdate();
    		}
    		
    		
    	}
		return null;
	}	

	
	/*------------------------------Not Generated Bill Module (Vijju)--------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchNotGeneratedBillMonth(String service, int month, int year) {
		return entityManager.createNamedQuery("ElectricityBillEntity.searchNotGeneratedBillByServiceMonth").setParameter("service", service).setParameter("month", month).setParameter("year", year).getResultList();
	}
	
	//*************************************************** Temprorary method to generate XML **********************************
		@Override
		public List<Map<String, String>> generateAllDetails() 
		{		
		   String query=" SELECT PM.PROPERTY_ID,p.PROPERTY_NO,a.ACCOUNT_ID,a.ACCOUNT_NO,PM.PERSON_ID," +
					    " (pr.FIRST_NAME|| ' ' ||pr.LAST_NAME)AS Person_Name,pm.METER_NUMBER " +
					    " FROM PROPERTY p ,	PERSON pr ,	PREPAID_METERS pm , ACCOUNT a " +
					    " WHERE pm.PERSON_ID=pr.PERSON_ID AND pm.PROPERTY_ID=p.PROPERTY_ID AND PM.PERSON_ID=a.PERSON_ID " +
					    " ORDER BY PR.PERSON_ID ";
		   
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			List<?> gotAllDetail =  entityManager.createNativeQuery(query).getResultList();
			
			 Map<String, String> record = null;
			 for (Iterator<?> iterator = gotAllDetail.iterator(); iterator.hasNext();)
			 {
					final Object[] values = (Object[]) iterator.next();
					record = new HashMap<String, String>();
					
					record.put("propertyId", values[0]+""	);
					record.put("propertyNo", values[1]+""	);
					record.put("accountId",  values[2]+""	);
					record.put("accountNo",  values[3]+""	);
					record.put("personId", 	 values[4]+""	);
					record.put("personName", values[5]+""	);
					record.put("meterNo",    values[6]+""	);
				
					result.add(record);
		     }
			 return result;
		   
		  //return entityManager.createNativeQuery(query).getResultList(); 
		}
		
		@Override
		public String getPrepaidDailyData(String meterNo,String fromDate,String toDate) 
		{		
			try 
			{
				String query = "SELECT NVL(PDD.CUM_FIXED_CHARGE_DG,0) FROM PREPAID_DAILY_DATA pdd " 
						+ " WHERE TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy')="
						+ " ( " 
						+ "  SELECT MAX(TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy')) from PREPAID_DAILY_DATA  pdd " 
						+ "  WHERE TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy')>=TO_DATE('"+fromDate+"','dd/MM/yyyy')AND " 
						+ "  TO_DATE(PDD.READING_DATE_TIME,'dd/MM/yyyy')<=TO_DATE('"+toDate+"','dd/MM/yyyy')AND " 
						+ "  PDD.METER_SR_NO="+meterNo
						+ " ) " 
						+ " AND PDD.METER_SR_NO="+meterNo;
				
			   return  entityManager.createNativeQuery(query).getSingleResult()+"";	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("*****************************Data not available for MeterNo="+meterNo+"*****************************");
				return "0.00";
			}
			
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<ElectricityBillEntity> getAllRecordsForTally(String serviceType1, java.util.Date currntMonth) {
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(currntMonth);
			int month = cal.get(Calendar.MONTH);
			int montOne =month +1;
			int year = cal.get(Calendar.YEAR);
			  return entityManager.createNamedQuery("ElectricityBillEntity.getAllFiftyBill").setParameter("tallyStatus", "Not Posted").setParameter("typeOfService", serviceType1).setParameter("month", montOne).setParameter("year", year).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
			return null;
		}
		
		/*=====================================================================================================*/
		@Override
		public List<?> fetch200BillsData(int actualmonth,int year, String serviceType) {
			return entityManager.createNamedQuery("ElectricityBillEntity.fetchBillsDataBasedOnMonthAndServiceType").setParameter("typeOfService", serviceType).setParameter("actualmonth", actualmonth).setParameter("year", year).setMaxResults(200).getResultList();
		}
}
