package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AdjustmentCalcLinesEntity;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdjustmentServiceImpl extends GenericServiceImpl<PaymentAdjustmentEntity> implements AdjustmentService  {

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentAdjustmentEntity> findAll() {
		return getAllDetails(entityManager.createNamedQuery("PaymentAdjustmentEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> adjustmentEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> paymentMap = null;
		 for (Iterator<?> iterator = adjustmentEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				paymentMap = new HashMap<String, Object>();
				
				paymentMap.put("adjustmentId", (Integer)values[0]);
				paymentMap.put("jvDate", (Timestamp)values[1]);
				paymentMap.put("adjustmentDate", (Date)values[2]);
				paymentMap.put("adjustmentNo", (String)values[3]);
				paymentMap.put("adjustmentLedger", (String)values[4]);
				paymentMap.put("adjustmentAmount", (Double)values[5]);
				paymentMap.put("postedToGl", (String)values[6]);
				paymentMap.put("postedGlDate", (Timestamp)values[7]);
				paymentMap.put("status", (String)values[8]);
				paymentMap.put("accountId", (Integer)values[9]);
				paymentMap.put("accountNo", (String)values[10]);
				String personName = "";
				personName = personName.concat((String)values[11]);
				if((String)values[12] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[12]);
				}
				paymentMap.put("personName", personName);
				paymentMap.put("property_No",(String)values[13]);
				paymentMap.put("adjustmentType", (String)values[14]);
				paymentMap.put("tallystatus", (String)values[15]);
				paymentMap.put("remarks", (String)values[16]);
			
			result.add(paymentMap);
	     }
      return result;
	}
	
	@Override
	public void setAdjustmentStatus(int adjustmentId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			PaymentAdjustmentEntity adjustmentEntity = find(adjustmentId);
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setAdjustmentStatus").setParameter("status", "Approved").setParameter("clearedStatus", "No").setParameter("adjustmentId", adjustmentId).executeUpdate();
				out.write("Adjustment details are approved");
			}
			else if(adjustmentEntity.getStatus().equals("Posted")) {
				out.write("Adjustment details are already posted");
			}else{
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setAdjustmentStatus").setParameter("status", "Posted").setParameter("clearedStatus", "Yes").setParameter("adjustmentId", adjustmentId).executeUpdate();
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setPostedToGl").setParameter("postedToGl", "Yes").setParameter("adjustmentId", adjustmentId).executeUpdate();
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setPostedGlDate").setParameter("postedGlDate", new Timestamp(new java.util.Date().getTime())).setParameter("adjustmentId", adjustmentId).executeUpdate();
				out.write("Adjustment details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setStatusApproved(HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
		    entityManager.createNamedQuery("PaymentAdjustmentEntity.setStatusApproved").setParameter("status", "Approved").executeUpdate();
		    out.write("Adjustment details are approved");
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentAdjustmentEntity> getAdjustmentId() {
		return entityManager.createNamedQuery("PaymentAdjustmentEntity.getAdjustmentId").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdjustmentCalcLinesEntity> getAdjusmentCalcItemList(int adjustmentId,String transactionCode) {
		return entityManager.createNamedQuery("PaymentAdjustmentEntity.getAdjusmentCalcItemList").setParameter("adjustmentId", adjustmentId).setParameter("transactionCode", transactionCode).getResultList();
	}
	
	@SuppressWarnings("unused")
	@Override
	public void setStatusPostedLedger(int adjustmentId, HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			PaymentAdjustmentEntity adjustmentEntity = find(adjustmentId);
			
			if(adjustmentEntity.getStatus().equals("Posted")) {
				//out.write("Adjusment details are already posted");
			}else{
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setAdjustmentStatus").setParameter("status", "Posted").setParameter("clearedStatus", "No").setParameter("adjustmentId", adjustmentId).executeUpdate();
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setPostedToGl").setParameter("postedToGl", "Yes").setParameter("adjustmentId", adjustmentId).executeUpdate();
				entityManager.createNamedQuery("PaymentAdjustmentEntity.setPostedGlDate").setParameter("postedGlDate", new Timestamp(new java.util.Date().getTime())).setParameter("adjustmentId", adjustmentId).executeUpdate();
				
				//out.write("Adjustment details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<?> getAllPaidAccountNumbers() {
		return getAccountNumbersData(entityManager.createNamedQuery("PaymentAdjustmentEntity.getAllPaidAccountNumbers").getResultList());
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
			
			result.add(accountNumberMap);
	     }
     return result;
	}

	@Override
	public List<PaymentAdjustmentEntity> getAllAdjustmentByAccountId(int accountId,String adjustmentLedger) {
		return entityManager.createNamedQuery("PaymentAdjustmentEntity.getAllAdjustmentByAccountId",PaymentAdjustmentEntity.class).setParameter("accountId", accountId).setParameter("adjustmentLedger", adjustmentLedger).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersAdjustmentUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("PaymentAdjustmentEntity.commonFilterForAccountNumbersAdjustmentUrl").getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> checkForNotPostedAdjustmentsAccountsIn(int accountId) {
		return entityManager.createNamedQuery("PaymentAdjustmentEntity.checkForNotPostedAdjustmentsAccountsIn").setParameter("accountId", accountId).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> getTallyAdjustmentDetailData(int adjustmentId) {
		
		Map<String, Object> invoiceLineItemMap = null;
		List<Map<String, Object>> itemsList = new ArrayList<Map<String, Object>>();
		//entityManager.createQuery("SELECT pt.property_No FROM PaymentAdjustmentEntity pa,Account a,Property pt WHERE a.accountId=pa.accountId AND pt.propertyId=a.propertyId AND a.accountNo='AC142724'");
		List<Object> data=entityManager.createQuery("select pa.adjustmentDate,pa.adjustmentLedger,pa.adjustmentAmount,PA.adjustmentType,pa.accountId from PaymentAdjustmentEntity pa where pa.adjustmentId="+adjustmentId).getResultList();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			final Object[] object = (Object[]) iterator.next();
			
			invoiceLineItemMap = new HashMap<String, Object>();
			invoiceLineItemMap.put("adjustmentDate", (Date) object[0]);
			invoiceLineItemMap.put("adjustmentLedger", (String) object[1]);
			invoiceLineItemMap.put("adjustmentAmount", (Double) object[2]);
			invoiceLineItemMap.put("adjustmentType", (String) object[3]);
			List<Object> propertyNumber=entityManager.createQuery("select  p.property_No from Property p where p.propertyId=(select a.propertyId from Account a where a.accountId="+object[4]+")").getResultList();
			
			invoiceLineItemMap.put("propertyNumber",(String)propertyNumber.get(0));
			
			
			//List<Object> propertyName=entityManager.createQuery("select p.property_No from Property where p.propertyId="+propertyName[0]);
			
			
			itemsList.add(invoiceLineItemMap);			
			
		}
		
		
		return itemsList;
	}

	@Override
	public void updateTallyStatus(int adjustmentId) {
		
		//entityManager.createNamedQuery("PaymentAdjustmentEntity.updateTallyStatusForAdjustment").setParameter("adjustmentId", adjustmentId).setParameter("tallystatus", "Posted");
		entityManager.createQuery("UPDATE PaymentAdjustmentEntity pa SET pa.tallystatus='Posted' WHERE pa.adjustmentId="+adjustmentId+"").executeUpdate();
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentAdjustmentEntity> searchAdjustmentDataByMonth(java.util.Date fromDateVal, java.util.Date toDateVal) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = formatter.format(fromDateVal);
		String s2 = formatter.format(toDateVal);
		return getAllDetails(entityManager.createNamedQuery("PaymentAdjustmentEntity.searchAdjustmentDataByMonth").setParameter("fromDateVal", s1).setParameter("toDateVal", s2).getResultList());
	}

	@Override
	public int autoGeneratedAdjustmentNumber() {
		return ((BigDecimal)entityManager.createNativeQuery("SELECT ADJUSTMENT_NUM_SEQ.nextval FROM dual").getSingleResult()).intValue();
	}

	@Override
	public int getAccountIdByPropertyId(int propertyId,String serviceType) {
		Integer accountId=0;
		
		try
		{
			accountId= entityManager.createNamedQuery("PaymentAdjustmentEntity.getAccountIdByPropertyId",Integer.class).setParameter("propertyId", propertyId).setParameter("serviceType", serviceType).getSingleResult();
		}
		catch(NoResultException e)
		{
			accountId=0;
			
		}
		
		return accountId;
	}

	@Override
	public List<Property> getPropertyByBlockId(int blockId) {
		return entityManager.createNamedQuery("Property.getPropertyByBlockId").setParameter("blockId", blockId).getResultList();
	}

	@Override
	public List<Integer> getAllBlocksId() {
		return entityManager.createNamedQuery("Blocks.getAllBlocksId").getResultList();
	}
	
	@Override
	public void updateAdjustmentTallyStatus(int adjustmentId) {
		entityManager.createQuery("UPDATE PaymentAdjustmentEntity pa SET pa.tallystatus='XML Generated' WHERE pa.adjustmentId="+adjustmentId+"").executeUpdate();
	}
	
	@Override
	public List<PaymentAdjustmentEntity> getAllDataXMl(java.util.Date monthDate) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(monthDate);
		int m1=cal.get(Calendar.MONTH);
		int month=m1+1;
		int year=cal.get(Calendar.YEAR);
		try{
			return entityManager.createNamedQuery("PaymentAdjustmentEntity.ExportDataXml").setParameter("month", month).setParameter("year", year).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAdjustmentStatus(int accountId,java.sql.Date currentbilldate,java.sql.Date preBillDate) {
		java.sql.Date datec= currentbilldate;
		java.sql.Date datep= preBillDate;
		
		java.sql.Timestamp timestamp1 = new java.sql.Timestamp(datec.getTime());
		java.sql.Timestamp timestamp2 = new java.sql.Timestamp(datep.getTime());
		
		String date1=new SimpleDateFormat("yyyy-MM-dd").format(timestamp1);
		String date2=new SimpleDateFormat("yyyy-MM-dd").format(timestamp2);
		
		System.err.println("------------------------------getAdjustmentStatus Method------->timestamp="+timestamp1+" & "+date1); 
		System.err.println("------------------------------getAdjustmentStatus Method------->timestamp="+timestamp2+" & "+date2); 
		
		String total="SELECT SUM(JV_AMOUNT) FROM ADJUSTMENTS  WHERE ACCOUNT_ID='"+accountId+"' AND "
				+ "POSTED_TO_GL_DT BETWEEN (to_date('"+date2+"','yyyy-MM-dd'))AND(to_date('"+date1+"','yyyy-MM-dd'))";
		System.err.println("Adjustment Query====>"+total);
		try 
		{
			double amt=((BigDecimal) entityManager.createNativeQuery(total).getSingleResult()).doubleValue();
			System.err.println(amt);
			/*String balanceAdj = (String) entityManager.createNativeQuery(total).getSingleResult();
			System.err.println("Adjustment Balance====>"+balanceAdj);*/
			return String.valueOf(amt);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		
	}

	

}
