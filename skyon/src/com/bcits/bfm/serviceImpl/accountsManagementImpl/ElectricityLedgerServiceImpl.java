package com.bcits.bfm.serviceImpl.accountsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.LedgerViewEntity;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DataSourceRequest;
import com.bcits.bfm.util.DataSourceRequest.GroupDescriptor;
import com.bcits.bfm.util.DataSourceResult;

@Repository
public class ElectricityLedgerServiceImpl extends GenericServiceImpl<ElectricityLedgerEntity> implements ElectricityLedgerService {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityLedgerEntity> findALL() {
		
		return getAllDetails(entityManager.createNamedQuery("ElectricityLedgerEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAllDetails(List<?> ledgerList){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> wizardMap = null;
		 for (Iterator<?> iterator = ledgerList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				wizardMap = new HashMap<String, Object>();
								
				wizardMap.put("elLedgerid", (Integer)values[0]);	
				wizardMap.put("accountId", (Integer)values[1]);
				wizardMap.put("ledgerPeriod", (String)values[2]);	
				wizardMap.put("ledgerDate", (Date)values[3]);
				wizardMap.put("postType", (String)values[4]);
				wizardMap.put("postReference", (String)values[5]);
				wizardMap.put("transactionCode", (String)values[6]);	
				wizardMap.put("amount", (Double)values[7]);
				wizardMap.put("balance", (Double)values[8]);	
				wizardMap.put("postedBillDate", (String)values[9]);
				wizardMap.put("transactionSequence", (Integer)values[10]);
				wizardMap.put("ledgerType", (String)values[11]);
				wizardMap.put("accountNo", (String)values[12]);
				wizardMap.put("transactionName", (String)values[13]);
				wizardMap.put("property_No", (String)values[18]);
				wizardMap.put("remarks", (String)values[19]);
				wizardMap.put("personName", (String)values[20]);
				
				/*String personType = (String)values[16];
				int personId = (Integer)values[17];
				if(personType.equals("Tenant")){
					wizardMap.put("property_No",getPropertyNoForTenant(personId));
				}else if(personType.equals("Owner")){
					wizardMap.put("property_No",getPropertyNoForOwner(personId));
				}*/
						
			result.add(wizardMap);
	     }
      return result;
	}

	@Override
	public String getPropertyNoForTenant(int personId) {
		return (String)entityManager.createNamedQuery("ElectricityLedgerEntity.getPropertyNoForTenant").setParameter("personId", personId).getSingleResult();
	}
	
	@Override
	public String getPropertyNoForOwner(int personId) {
		return (String)entityManager.createNamedQuery("ElectricityLedgerEntity.getPropertyNoForOwner").setParameter("personId", personId).getSingleResult();
	}

	@Override
	public void setElectricityLedgerStatus(int elLedgerid, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricityLedgerEntity.setELRateMasterStatus").setParameter("status", "Active").setParameter("elLedgerid", elLedgerid).executeUpdate();
				out.write("Ledger active");
			}
			else
			{
				entityManager.createNamedQuery("ElectricityLedgerEntity.setELRateMasterStatus").setParameter("status", "Inactive").setParameter("elLedgerid", elLedgerid).executeUpdate();
				out.write("Ledger inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Long getLedgerSequence(int accountId) {
		return (Long)entityManager.createNamedQuery("ElectricityLedgerEntity.getLedgerSequence").setParameter("accountId", accountId).getSingleResult();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getLastLedgerTransactionAmount(int accountId,String typeOfService) {
		try{
			return (Integer)entityManager.createNamedQuery("ElectricityLedgerEntity.getLastLedgerTransactionAmount").setParameter("accountId", accountId).setParameter("ledgerType", typeOfService).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public ElectricityLedgerEntity getPreviousLedger(int accountId,java.util.Date previousBillDate,String transactionCode) 
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		int month = cal.get(Calendar.MONTH);
		int montOne = month +1;
		int year = cal.get(Calendar.YEAR);
		try
		{
			return entityManager.createNamedQuery("ElectricityLedgerEntity.getPreviousLedger",ElectricityLedgerEntity.class).setParameter("accountId", accountId).setParameter("month", montOne).setParameter("year", year).setParameter("transactionCode", transactionCode).setMaxResults(1).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getLastLedgerBillAreears(int accountId,String typeOfService) {
		return (Integer)entityManager.createNamedQuery("ElectricityLedgerEntity.getLastLedgerBillAreears").setParameter("accountId", accountId).setParameter("typeOfService", typeOfService+" Ledger").getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("ElectricityLedgerEntity.commonFilterForAccountNumbersUrl").getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForPropertyNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("ElectricityLedgerEntity.commonFilterForPropertyNumbersUrl").getResultList());
	}
	
	@Override
	public ElectricityLedgerEntity getPreviousArrearsLedger(int accountId,java.util.Date previousBillDate, String transactionCode)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		int month = cal.get(Calendar.MONTH);
		int montOne = month +1;
		int year = cal.get(Calendar.YEAR);
		try
		{
			return entityManager.createNamedQuery("ElectricityLedgerEntity.getPreviousArrearsLedger",ElectricityLedgerEntity.class).setParameter("accountId", accountId).setParameter("month", montOne).setParameter("year", year).setParameter("transactionCode", transactionCode).setMaxResults(1).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
	}
	
	@Override
	 public BigDecimal getLastArrearsLedgerBasedOnPayment(int accountId, java.util.Date previousBillDate,String transactionCode) {
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(previousBillDate);

	  try
	  {
	   String queryString = "SELECT ELL_ID  FROM (SELECT ELL_ID, rank() over (order by ELL_ID desc) rnk FROM LEDGER WHERE ACCOUNT_ID="+accountId+" AND TR_CODE ='"+transactionCode+"' AND POST_TYPE='ARREARS') WHERE rnk = 2";
	   final Query query = this.entityManager.createNativeQuery(queryString);
	   return (BigDecimal) query.getSingleResult();
	  }
	  catch(Exception exception)
	  {
	   return null;
	  }
	 }

	@Override
	public List<ElectricityLedgerEntity> getPreviousLedgerPayments(int accountId, java.util.Date previousBillDate,String transactionCode)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(previousBillDate);
		int month = cal.get(Calendar.MONTH);
		int montOne = month +1;
		int year = cal.get(Calendar.YEAR);
		return entityManager.createNamedQuery("ElectricityLedgerEntity.getPreviousLedgerPayments",ElectricityLedgerEntity.class).setParameter("accountId", accountId).setParameter("month", montOne).setParameter("year", year).setParameter("transactionCode", transactionCode).getResultList();
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("ElectricityLedgerEntity.findPersonForFilters").getResultList();
		return details;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityLedgerEntity> getTeleBroadbandLedgerDetails(	int accountId, String typeOfService) {
		try{
			return entityManager.createNamedQuery("ElectricityLedgerEntity.getTeleBroadbandLedgerDetails").setParameter("accountId", accountId).setParameter("ledgerType", typeOfService).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getLastLedgerBasedOnAccountId(int accountId) {
		return (Integer)entityManager.createNamedQuery("ElectricityLedgerEntity.getLastLedgerBasedOnAccountId").setParameter("accountId", accountId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityLedgerEntity> searchLedgerDataByMonth(java.util.Date fromDateVal, java.util.Date toDateVal) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = formatter.format(fromDateVal);
		String s2 = formatter.format(toDateVal);
		return getAllDetails(entityManager.createNamedQuery("ElectricityLedgerEntity.searchLedgerDataByMonth").setParameter("fromDateVal", s1).setParameter("toDateVal", s2).getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataSourceResult getList(DataSourceRequest request) {
		
		Session session = entityManager.unwrap(Session.class);
		Class<?> clazz = LedgerViewEntity.class;
		Criteria criteria = session.createCriteria(clazz);
		DataSourceResult dataSourceResult = request.toDataSourceResult(criteria,clazz,session);		
		List<GroupDescriptor> groups = request.getGroup();  		
        if (groups != null && !groups.isEmpty()) {        	
        	dataSourceResult.setData(request.group((List<LedgerViewEntity>)criteria.list(), session,clazz));
        } else {
        	criteria.addOrder(Order.desc("elLedgerid"));
        	//criteria.setMaxResults(10000);
        	dataSourceResult.setData((List<LedgerViewEntity>)criteria.list());
        }
        return dataSourceResult;
	}

	@Override
	public List<LedgerViewEntity> getLedgerViewByAccountId(int accountId,java.util.Date fromDate,java.util.Date toDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int month = cal.get(Calendar.MONTH);
		int fromMonth =month +1;
		int fromYear = cal.get(Calendar.YEAR);
		cal.setTime(toDate);
		int Month=cal.get(Calendar.MONTH);;
		int toMonth=Month+1;
		int toYear=cal.get(Calendar.YEAR);
		
		return entityManager.createNamedQuery("LedgerViewEntity.getLedgerByAccountId").setParameter("accountId", accountId).setParameter("fromMonth", fromMonth).setParameter("fromYear", fromYear).setParameter("toMonth", toMonth).setParameter("toYear", toYear).getResultList();
	}

	
}
