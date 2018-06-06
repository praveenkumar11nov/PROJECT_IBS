package com.bcits.bfm.serviceImpl.electricityBillsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ElectricityBillLineItemEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ElectricityBillLineItemServiceImpl extends GenericServiceImpl<ElectricityBillLineItemEntity> implements ElectricityBillLineItemService {

	@Override
	@SuppressWarnings("unchecked")
	public List<ElectricityBillLineItemEntity> findALL() {
		return entityManager.createNamedQuery("ElectricityBillLineItemEntity.findAll").getResultList();
	}

	@Override
	public void setElectricityBillLineItemStatus(int elBillLineId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricityBillLineItemEntity.setBillLineItemStatus").setParameter("status", "Active").setParameter("elBillLineId", elBillLineId).executeUpdate();
				out.write("Line Item active");
			}
			else
			{
				entityManager.createNamedQuery("ElectricityBillLineItemEntity.setBillLineItemStatus").setParameter("status", "Inactive").setParameter("elBillLineId", elBillLineId).executeUpdate();
				out.write("Line Item inactive");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityBillLineItemEntity> findAllById(int elBillId) {
	
		return getAllDetailsList(entityManager.createNamedQuery("ElectricityBillLineItemEntity.findAllById").setParameter("elBillId", elBillId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> billLineItemEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsLineItemMap = null;
		 for (Iterator<?> iterator = billLineItemEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsLineItemMap = new HashMap<String, Object>();				
									
				billsLineItemMap.put("elBillLineId", (Integer)values[0]);	
				billsLineItemMap.put("transactionCode",(String)values[1]);
				billsLineItemMap.put("creditAmount", (Double)values[2]);	
				billsLineItemMap.put("debitAmount",(Double)values[3]);
				billsLineItemMap.put("balanceAmount",(Double)values[4]);
				billsLineItemMap.put("transactionName",(String)values[5]);
				
			result.add(billsLineItemMap);
	     }
      return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityBillLineItemEntity> findAllLineItemsById(int elBillId) {
		return entityManager.createNamedQuery("ElectricityBillLineItemEntity.findAllLineItemsById").setParameter("elBillId", elBillId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityBillLineItemEntity> findLineItemBasedOnTransactionCode(int elBillId, String transactionCode) {
		return entityManager.createNamedQuery("ElectricityBillLineItemEntity.findLineItemBasedOnTransactionCode").setParameter("elBillId", elBillId).setParameter("transactionCode", transactionCode).getResultList();
	}

	@Override
	public ElectricityBillLineItemEntity findByTransactionCode(int elBillId, String transactionCode) {
		try
		{
			return entityManager.createNamedQuery("ElectricityBillLineItemEntity.findByTransactionCode",ElectricityBillLineItemEntity.class).setParameter("transactionCode", transactionCode).setParameter("elBillId", elBillId).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
	}

	@Override
	public void deleteAllBill(int elbillId) {
		String sql =  "DELETE FROM ElectricityBillLineItemEntity ebl WHERE ebl.electricityBillEntity.elBillId = "+elbillId;
		String sql2 = "DELETE FROM ElectricityBillParametersEntity ebl WHERE ebl.electricityBillEntity.elBillId  = "+elbillId;
		//String sql3 = "DELETE FROM ElectricityBillEntity eb WHERE eb.elBillId = "+elbillId;
		Query query = entityManager.createQuery(sql);
		Query query1 = entityManager.createQuery(sql2);
		//Query query2 = entityManager.createQuery(sql3);
		
		query.executeUpdate();
		query1.executeUpdate();
	//	query2.executeUpdate();
		
	}

	@Override
	public List<ElectricityBillLineItemEntity> findAllLineItemsByIdUpdate(int elBillId) {
		return entityManager.createNamedQuery("ElectricityBillLineItemEntity.findAllLineItemsByIdUpdate",ElectricityBillLineItemEntity.class).setParameter("elBillId", elBillId).getResultList();
	}

	@Override
	public List<?> getTaransactionCodeForOthers(String billServiceType) {
		return entityManager.createNamedQuery("ElectricityBillLineItemEntity.getTaransactionCodeForOthers").setParameter("typeOfService", billServiceType).getResultList();
	}

	@Override
	public List<Map<String, Object>> findAllDetailsForCamPosting(int billId) {
		List<ElectricityBillLineItemEntity> lineItem=entityManager.createNamedQuery("ElectricityBillLineItemEntity.findAllDetailsForCamPosting").setParameter("elBillId", billId).getResultList();
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsLineItemMap = null;
		 for (Iterator<?> iterator = lineItem.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsLineItemMap = new HashMap<String, Object>();				
									
				billsLineItemMap.put("elBillLineId", (Integer)values[0]);	
				billsLineItemMap.put("transactionCode",(String)values[1]);
				billsLineItemMap.put("creditAmount", (Double)values[2]);	
				billsLineItemMap.put("debitAmount",(Double)values[3]);
				billsLineItemMap.put("balanceAmount",(Double)values[4]);
				/*billsLineItemMap.put("transactionName",(String)values[5]);*/
				
			result.add(billsLineItemMap);
	     }
		 
		 System.out.println("==========size of lineItem "+lineItem.size());
      return result;
	}

	/*@Override
	public ElectricityBillLineItemEntity findByTransactionCode(String transactionCode)
	{
		try
		{
			return entityManager.createNamedQuery("ElectricityBillLineItemEntity.findByTransactionCode",ElectricityBillLineItemEntity.class).setParameter("transactionCode", transactionCode).getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
		
	}*/
}
