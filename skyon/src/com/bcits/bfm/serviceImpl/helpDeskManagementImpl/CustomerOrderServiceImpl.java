package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerFeedback;
import com.bcits.bfm.service.helpDeskManagement.CustomerOrderService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CustomerOrderServiceImpl extends GenericServiceImpl<CustomerEntity> implements CustomerOrderService{

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> findAllData() {
		return getItemData(entityManager.createNamedQuery("CustomerEntity.findAllData").getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> findAllDatas(int storeid) {
		return getItemData(entityManager.createNamedQuery("CustomerEntity.findAllDatas").setParameter("storeid", storeid).getResultList());
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	private List getItemData(List<?> storemaster)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final CustomerEntity values = (CustomerEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("cid", values.getCid());
			 storeData.put("custName", values.getCustName());
			 storeData.put("custEmail",values.getCustEmail());		
			 storeData.put("custNum",values.getCustNum());
			 storeData.put("totalPrice", values.getTotalPrice());
			 storeData.put("totalQuantity",values.getTotalQuantity());		
			 storeData.put("orderAddress",values.getOrderAddress());
			 storeData.put("customerStatus", values.getCustomerStatus());
			 storeData.put("storeid",values.getStoreid());
			 storeData.put("storename",values.getStorename());
			 storeData.put("ordertype",values.getOrdertype());
			 storeData.put("personId",values.getPersonId());
			storeData.put("createdDate",values.getCreatedDate());
			storeData.put("lastUpdatedDate",(Timestamp)values.getLastUpdatedDate());
			  result.add(storeData); 
		 }
		 return result;
	}
	@Override
	public Double findQuantityBasedonId(int cid) {
		return	(Double) entityManager.createNamedQuery("CustomerEntity.findQuantity").setParameter("cid", cid).getSingleResult();
	}
	
	@Override
	public Double findQuantityBasedonItemId(int cid) {
		return	 (Double) entityManager.createNamedQuery("CustomerItemsEntity.findQuantity").setParameter("cid", cid).getSingleResult();
	}
	
	@Override
	public void update(CustomerEntity customerEntity, int cid,double totalQuantity) {
		
		entityManager.createNamedQuery("CustomerEntity.update").setParameter("cid", cid).setParameter("totalQuantity",totalQuantity).executeUpdate();
		
	}
	
	@Override
	public void setstoreStatus(int cid, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();	
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Customer.UpdateStatus").setParameter("CStatus", "Accepted").setParameter("cId", cid).executeUpdate();
				out.write("Customer Order Accepted");
			}
			else
			{
			   entityManager.createNamedQuery("Customer.UpdateStatus").setParameter("CStatus",  "Accept").setParameter("cId", cid).executeUpdate();
			   out.write("Customer Order not Accepted");				
			}			
		}
		catch(Exception e){
		   e.printStackTrace();
	    }
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadImageOnId(int cid,Blob customerImage) {
		entityManager.createNamedQuery("Customer.uploadImageOnId")
				.setParameter("cid", cid)
				.setParameter("customerImage", customerImage)
				.setParameter("lastUpdatedDate", (new Timestamp(new Date().getTime())))
				.executeUpdate();
		}
	
	
	@Override
	public Blob getImage(int cid) {
				
			return	(Blob) entityManager.createNamedQuery("Customer.getImage", Blob.class)
			    .setParameter("cid", cid)
			    .getSingleResult();
	}
	
	
	@Override
	public void setcustomerStatus(int cid, String operation,
			HttpServletResponse response) {
		CustomerEntity c=new CustomerEntity();
		try
		{
			PrintWriter out = response.getWriter();

			
			if(StringUtils.containsIgnoreCase(operation,"Opened"))
				entityManager.createNamedQuery("CustomerEntity.setStatus").setParameter("customerStatus", operation).setParameter("cid", cid).setParameter("lastUpdatedDate",c.getLastUpdatedDate()).executeUpdate();
			else if(StringUtils.containsIgnoreCase(operation,"Closed"))
				entityManager.createNamedQuery("CustomerEntity.setStatus").setParameter("customerStatus", operation).setParameter("cid", cid).setParameter("lastUpdatedDate",c.getLastUpdatedDate()).executeUpdate();
			else
				entityManager.createNamedQuery("CustomerEntity.setStatus").setParameter("customerStatus", operation).setParameter("cid", cid).setParameter("lastUpdatedDate",c.getLastUpdatedDate()).executeUpdate();
			
			out.write(operation);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> findEmail(String username) {
		return entityManager.createNamedQuery("CustomerOrder.findEmail").setParameter("username", username).getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> getPersonId(String custNum) {

		return entityManager.createNamedQuery("Customer.getPersonId")
				.setParameter("custNum", custNum)
				.getResultList();
	}
	@Override
	public void updateVisitorImage(int cid, Blob blob) {
		  
		entityManager.createNamedQuery("Customer.updateVisitorImage")
		  .setParameter("cid", cid).setParameter("custImage", blob)
		  .executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> findAllUserDetails(String user) {
		return readData(entityManager.createNamedQuery("Customer.getPersonDetails")
				.setParameter("user", user)
				.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findAllInformation(String user) {
		return readInfo(entityManager.createNamedQuery("Contact.getContactInformation")
				.setParameter("user", user)
				.getResultList());
	}
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	private List<?> readInfo(List<?> resultList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		
		
		Map<String, Object> storeData = null;
		//storeData.put("blockName",);
		for (Iterator<?> iterator = resultList.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("blockName", values[0]);
			 storeData.put("propertyId", values[1]);
			 storeData.put("propertyNo", values[2]);
			 storeData.put("personId", values[3]);
			
			  result.add(storeData); 
			  
		 }
		 return result;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	private List readData(List<?> storemaster)
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final CustomerEntity values = (CustomerEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("cid", values.getCid());
			 storeData.put("custName", values.getCustName());
			 storeData.put("custEmail",values.getCustEmail());		
			 storeData.put("custNum",values.getCustNum());
			 storeData.put("totalPrice", values.getTotalPrice());
			 storeData.put("totalQuantity",values.getTotalQuantity());		
			 storeData.put("customerStatus",values.getCustomerStatus());
			 storeData.put("storeid",values.getStoreid());
			 storeData.put("storename",values.getStorename());
			 storeData.put("ordertype",values.getOrdertype());
			 storeData.put("personId",values.getPersonId());
			 storeData.put("lastUpdatedDate",(Date)values.getLastUpdatedDate());
			
			
			  result.add(storeData); 
			  
		 }
		 return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> getdetails(String custEmail) {
		return readData(entityManager.createNamedQuery("Customer.getDetails")
				.setParameter("custEmail", custEmail)
				.getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> finduserDatas(String user) {
		return entityManager.createNamedQuery("CustomerEntity.UserData").setParameter("user", user).getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerFeedback> findAllFeedback(int id) {
		return entityManager.createNamedQuery("CustomerFeedback.UserData").setParameter("id", id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerEntity> findAllEnquiry(int storeid) {
		return getItemData(entityManager.createNamedQuery("CustomerEntity.findAllEnquiry").setParameter("storeid", storeid).getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> findAllOrders(String status) {
		
		return getItemDataOpen(entityManager.createNamedQuery("CustomerEntity.findAllOrders").setParameter("status", status).getResultList());
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	private List getItemDataOpen(List<?> storemaster) {
SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final CustomerEntity values = (CustomerEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("cid", values.getCid());
			 storeData.put("custName", values.getCustName());
			 storeData.put("custEmail",values.getCustEmail());		
			 storeData.put("custNum",values.getCustNum());
			 storeData.put("totalPrice", values.getTotalPrice());
			 storeData.put("totalQuantity",values.getTotalQuantity());		
			 storeData.put("orderAddress",values.getOrderAddress());
			 storeData.put("storeid",values.getStoreid());
			 storeData.put("storename",values.getStorename());
			 storeData.put("ordertype",values.getOrdertype());
			 storeData.put("personId",values.getPersonId());
			storeData.put("createdDate",values.getCreatedDate());
			storeData.put("lastUpdatedDate",(Timestamp)values.getLastUpdatedDate());
			  result.add(storeData); 
		 }
		 return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> findAllEnquirys(String status) {
		return getItemDataOpen(entityManager.createNamedQuery("CustomerEntity.findAllEnquirys").setParameter("status", status).getResultList());
	}
	@Override
	public void getDelete(int cid) {
		entityManager.createNamedQuery("CustomerEntity.Delete").setParameter("cid",cid).executeUpdate();
		
	}

}









