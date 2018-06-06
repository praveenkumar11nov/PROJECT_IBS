package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.StoreMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StoreMasterServiceImp extends GenericServiceImpl<StoreMasterEntity> implements StoreMasterService {

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreMasterEntity> findAllData() {
	
		return getStoreData(entityManager.createNamedQuery("StoreMasterEntity.findAllData").getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List getStoreData(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final StoreMasterEntity values = (StoreMasterEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId", values.getsId());
			
			 storeData.put("storeName",values.getStoreName());		
			 storeData.put("storeNum",values.getStoreNum());
			 storeData.put("storeStatus",values.getStoreStatus());
			 storeData.put("storeDesc",values.getStoreDesc());
			 storeData.put("username",values.getUsername());
			 storeData.put("password",values.getPassword());
		     result.add(storeData); 
		 }
		 return result;
	}


	
	
	
	


	@Override
	public void setstoreStatus(int sId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();	
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Storemaster.UpdateStatus").setParameter("StStatus", "Active").setParameter("stId", sId).executeUpdate();
				out.write("Process Activated");
			}
			else
			{
			   entityManager.createNamedQuery("Storemaster.UpdateStatus").setParameter("StStatus",  "Inactive").setParameter("stId", sId).executeUpdate();
			   out.write("Process Deactivated");				
			}			
		}
		catch(Exception e){
		   e.printStackTrace();
	    }
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<StoreMasterEntity> findAllStoreNames() {
		try {
			final String queryString = "select model from StoreMasterEntity model ORDER BY model.sId DESC";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
		
			throw re;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<StoreMasterEntity> findAll() {
		return getStorenames(entityManager.createNamedQuery("StoreMasterEntity.findAllStoreNames").getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List getStorenames(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final StoreMasterEntity values = (StoreMasterEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId", values.getsId());
			 storeData.put("storeName",values.getStoreName());		
			
		     result.add(storeData); 
		 }
		 return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<StoreMasterEntity> findAllLoginDetails(String username, String password) {
		return getStorename(entityManager.createNamedQuery("Store.getLoginDetails")
				.setParameter("user", username).setParameter("pass", password)
				.getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List getStorename(List<?> storemaster)
	{
		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final StoreMasterEntity values = (StoreMasterEntity) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId", values.getsId());
			 storeData.put("storeName", values.getStoreName());
			 storeData.put("username",values.getUsername());		
			 storeData.put("password",values.getPassword());
			 storeData.put("storeNum",values.getStoreNum());
			 
		
			  result.add(storeData); 
			  
		 }
		 return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<StoreMasterEntity> findStoreMobile(int sId) {
		return entityManager.createNamedQuery("StoreMasterEntity.findAllStoreMobile").setParameter("sId", sId).getResultList();
		
	}
	
	
}
