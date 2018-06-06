package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ConciergeServices;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.facilityManagement.ConciergeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;

@Repository
public class ConciergeServiceImpl extends GenericServiceImpl<ConciergeServices> implements ConciergeService{
	
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@SuppressWarnings("serial")
	@Override
	public List<?> getResponse() {
		@SuppressWarnings("unchecked")
		List<ConciergeServices> list =entityManager.createNamedQuery("ConciergeServices.findAll").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final ConciergeServices record : list) {
			response.add(new HashMap<String, Object>() {{
				put("cssId", record.getCssId());
				put("serviceGroupName", record.getServiceGroupName());
				put("serviceName", record.getServiceName());
				put("serviceDescription", record.getServiceDescription());
				put("status", record.getStatus());
				
				java.util.Date startDateFromDb = record.getServiceStartDate();
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDt();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				java.sql.Date startDate = new java.sql.Date(startDateFromDb.getTime());
				
				
				put("serviceStartDate", startDate);
				if( record.getServiceEndDate() == null){}
				else{
					
					java.util.Date endDateFromDb = record.getServiceEndDate();
					java.sql.Date endDate = new java.sql.Date(endDateFromDb.getTime());
					put("serviceEndDate", endDate);
				}
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
				put("lastUpdatedDt", lastUpdatedDtSql);
			}});
		}
		return response;
	}
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ConciergeServices> getAll() {
		
		return entityManager.createNamedQuery("ConciergeServices.findActiveObject")
				.setParameter("status", "Active")
				.getResultList();
		
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public String getServiceNameBasedOnId(int cssId) {
		 List<String> csService =  entityManager.createNamedQuery("ConciergeServices.getServiceNameBasedOnId",
					String.class)
					.setParameter("cssId", cssId)
					.getResultList();
			Iterator<String> it=csService.iterator();
			while(it.hasNext()){
				
				return (String) it.next();
			}
			return null;
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getGroupNames() {
		return  entityManager.createNamedQuery("ConciergeServices.getGroupNames",String.class)
				.getResultList();
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getServiceNames() {
		return  entityManager.createNamedQuery("ConciergeServices.getServiceNames",String.class)
				.getResultList();
	}
	
	@Override
	public void setCsServiceStatus(int cssId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{

				entityManager.createNamedQuery("ConciergeServices.UpdateStatus")
				.setParameter("serviceStatus", "Active")
				.setParameter("cssId", cssId)
				.executeUpdate();
				out.write("Concierge Service is Activated");
			}
			
			else
			{

				entityManager.createNamedQuery("ConciergeServices.UpdateStatus")
				.setParameter("serviceStatus", "InActive")
				.setParameter("cssId", cssId)
				.executeUpdate();
				out.write("Concierge Service is Deactivated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	

	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public int getServiceIdBasedOnName(String serviceName) {
		List<Integer> csService =  entityManager.createNamedQuery("ConciergeServices.getServiceIdBasedOnName",
				Integer.class)
				.setParameter("serviceName", serviceName)
				.getResultList();
		Iterator<Integer> it=csService.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
	}
	
	@Override
	public void updateServiceEndDate(int serviceId, Date serviceEndDate) {
		
		entityManager.createNamedQuery("ConciergeServices.updateServiceEndDate")
		.setParameter("cssId", serviceId)
		.setParameter("serviceEndDate", serviceEndDate)
		.executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getDistinctCssIds() {
		
		return entityManager.createNamedQuery("ConciergeServices.getDistinctCssIds")
		.getResultList();
	}

}
