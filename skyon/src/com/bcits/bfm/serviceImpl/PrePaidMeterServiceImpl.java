package com.bcits.bfm.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.PrePaidMeters;
import com.bcits.bfm.model.PrepaidRechargeEntity;
import com.bcits.bfm.service.PrePaidMeterService;

@Repository
public class PrePaidMeterServiceImpl extends GenericServiceImpl<PrePaidMeters> implements PrePaidMeterService {

	@Override
	public List<?> getConsumerIDs() {
		System.out.println("in side recharge entity");
		try{
			return entityManager.createNamedQuery("PrePaidMeters.getConsumerIDs").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getPropertyNo(int propertyId) {
	try{
		return (String) entityManager.createNamedQuery("PrePaidMeters.getPropertyNo").setParameter("propertyId", propertyId).getSingleResult();
	}catch(Exception e){
		e.printStackTrace();
	}
		return null;
	}

	@Override
	public String getBlockName(int propertyId) {

      try{
    	  return (String) entityManager.createNamedQuery("PrePaidMeters.getBlockName").setParameter("propertyId", propertyId).getSingleResult();
      }catch(Exception e){
    	  e.printStackTrace();
      }
		return null;
	}

	@Override
	public List<?> FindAll() {
		try{
		return entityManager.createNamedQuery("PrePaidMeters.FindAll").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int accountId(int propertyId) {
		try{
			return (int) entityManager.createNamedQuery("PrePaidMeters.getAccountId").setParameter("propertyId", propertyId).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<?> getOwnerName(int personId) {
		try{
			return entityManager.createNamedQuery("PrePaidMeters.getOwnerDetails").setParameter("personId", personId).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMeterNumber() {
		try{
			return entityManager.createNamedQuery("PrePaidMeters.getmeterNo").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getPersonId(int propertyId) {
		try{
			return (int) entityManager.createNamedQuery("PrePaidMeters.getPersonId").setParameter("propertyId", propertyId).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getPropertyId(String mtr) {
		//System.out.println("mtr "+mtr);
		try{
			return (int) entityManager.createQuery("select p.propertyId from PrePaidMeters p where p.consumerId=:consumerID").setParameter("consumerID", mtr).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<?> getAllProp(int blockId) {
		try{
			List<?> propertyNumbersList = entityManager.createNamedQuery("PrePaidMeters.getAllProp").setParameter("blockId", blockId).getResultList();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> propertyNumberMap = null;
	        propertyNumberMap = new HashMap<String, Object>();        		
			/*propertyNumberMap.put("propertyId",0);
			propertyNumberMap.put("property_No", "All Property");			
			result.add(propertyNumberMap);*/
			
			for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					propertyNumberMap = new HashMap<String, Object>();				
									
					
					propertyNumberMap.put("propertyId",(Integer)values[0]);
					propertyNumberMap.put("property_No", (String)values[1]);	
								
				
				result.add(propertyNumberMap);
		     }
	     return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getMtrNo(int propid) {
		try{
			return (String) entityManager.createQuery("select p.meterNumber  from PrePaidMeters p where p.propertyId ="+propid).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public double getInitialReading(String mtrSrNo) {
		try{
			return (double) entityManager.createNamedQuery("PrePaidMeters.getInitialReading").setParameter("mtrSrNo", mtrSrNo).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<String> getMeterNumber(int prepaidId) {
		try{
			return entityManager.createNamedQuery("PrePaidMeters.getMtrNo").setParameter("prepaidId", prepaidId).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object[] getserviceStartDate(int propId) {
		try{
			return (Object[]) entityManager.createNamedQuery("PrePaidMeters.getReadingDate").setParameter("propertyId", propId).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getPropOnlyforChargesCalcu(int blockId) {
		
		try{
			List<?> propertyNumbersList = entityManager.createNamedQuery("PrePaidMeters.getPropOnlyforChargesCalcu").setParameter("blockId", blockId).getResultList();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> propertyNumberMap = null;
	        propertyNumberMap = new HashMap<String, Object>();        		
			/*propertyNumberMap.put("propertyId",0);
			propertyNumberMap.put("property_No", "All Property");			
			result.add(propertyNumberMap);*/
			
			for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					propertyNumberMap = new HashMap<String, Object>();				
									
					
					propertyNumberMap.put("propertyId",(Integer)values[0]);
					propertyNumberMap.put("property_No", (String)values[1]);	
								
				
				result.add(propertyNumberMap);
		     }
	     return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	
	}

	@Override
	public List<?> getPropForMailBill(int blockId) {
		try{
			List<?> propertyNumbersList = entityManager.createNamedQuery("PrePaidMeters.getPropOnlyforBill").setParameter("blockId", blockId).getResultList();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> propertyNumberMap = null;
	        propertyNumberMap = new HashMap<String, Object>();        		
			/*propertyNumberMap.put("propertyId",0);
			propertyNumberMap.put("property_No", "All Property");			
			result.add(propertyNumberMap);*/
			
			for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					propertyNumberMap = new HashMap<String, Object>();				
									
					
					propertyNumberMap.put("propertyId",(Integer)values[0]);
					propertyNumberMap.put("property_No", (String)values[1]);	
								
				
				result.add(propertyNumberMap);
		     }
	     return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getConsumerNumber(String consumerId) {
		try{
			return (long) entityManager.createNamedQuery("PrePaidMeters.checkConsumerId").setParameter("consumerId", consumerId).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<String> getConsumerIdNumber(int prepaidId) {
		try{
			return entityManager.createNamedQuery("PrePaidMeters.getConsumerIds").setParameter("prepaidId", prepaidId).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

/*	@Override
	public List<?> getTentantName(int propertyId, int personId) {
		try{
			return entityManager.createNamedQuery("PrePaidMeters.getTenents").setParameter("propertyId", propertyId).setParameter("personId", personId).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}*/

	@Override
	public List<?> getGenusData(int blockId) {
		
		try{
			List<?> propertyNumbersList = entityManager.createNamedQuery("PrePaidMeters.getGenusData").setParameter("blockId", blockId).getResultList();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> propertyNumberMap = null;
	        propertyNumberMap = new HashMap<String, Object>();        		
			/*propertyNumberMap.put("propertyId",0);
			propertyNumberMap.put("property_No", "All Property");			
			result.add(propertyNumberMap);*/
			
			for (Iterator<?> iterator = propertyNumbersList.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					propertyNumberMap = new HashMap<String, Object>();				
									
					
					propertyNumberMap.put("propertyId",(Integer)values[0]);
					propertyNumberMap.put("property_No", (String)values[1]);	
								
				
				result.add(propertyNumberMap);
		     }
	     return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	
	}

	@Override
	public long getmetersDetails(int personId, int propertyId) {
	String query="SELECT count(*) FROM PREPAID_METERS m WHERE m.PROPERTY_ID="+propertyId+" AND m.PERSON_ID="+personId+"";
	try{
	BigDecimal count= (BigDecimal) entityManager.createNativeQuery(query).getSingleResult();
		return count.longValue();
	}catch(Exception e){
		e.printStackTrace();
		return 2;
	}
	}

}
