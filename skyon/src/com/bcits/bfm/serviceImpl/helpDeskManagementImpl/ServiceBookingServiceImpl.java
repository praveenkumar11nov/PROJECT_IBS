package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ClubHouseBooking;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.ServiceBooking;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.helpDeskManagement.ClubHouseService;
import com.bcits.bfm.service.helpDeskManagement.ServiceBookingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServiceBookingServiceImpl extends GenericServiceImpl<ServiceBooking> implements ServiceBookingService{

	@Autowired
	private ClubHouseService clubHouseService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Override
	public List<ServiceBooking> findAllData() {
	
		return getServiceData(entityManager.createNamedQuery("ServiceBooking.findAllData").getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List getServiceData(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final  ServiceBooking values = (ServiceBooking) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("sId", values.getsId());
			 storeData.put("bId", values.getbId());
			 storeData.put("serviceName", clubHouseService.findServiceName(values.getsId()));
			 
			 storeData.put("name",values.getName());		
			 storeData.put("blocks",values.getBlocks());
			 
	 //storeData.put("personName",personService.getSinglePersonId(values.getName()) );
 int id=values.getName();
			 
			 List<Person> names=personService.executeSimpleQuery("SELECT p FROM Person p WHERE p.personId='"+id+"' ");
			//  personService.getSinglePersonId(values.getName());
			 // storeData.put("firstName",personService.getSinglePersonId(values.getName()) );
			 String firstname=names.get(0).getFirstName();
			 String secondname=names.get(0).getLastName();
			 
			 String personname=firstname+" "+secondname;
			  
			 storeData.put("personName",personname );
			
			 
			 storeData.put("property",values.getProperty());
			 storeData.put("propertyName", propertyService.getPropertyNameBasedOnPropertyId(values.getProperty()));
			
			 storeData.put("startTime",values.getStartTime());
			 storeData.put("endTime",values.getEndTime());
			 storeData.put("date",values.getDate());
			 storeData.put("serviceStatus",values.getServiceStatus());
			 storeData.put("createdBy",values.getCreatedBy());
			 storeData.put("action",values.getAction());
			 storeData.put("bookaction",values.getBookaction());
		
			 
			 
		     result.add(storeData); 
		 }
		 return result;
	}


	@Override
	public List<ServiceBooking> findAll(String createdBy) {
		System.out.println("inside a Find All---------------------------------------------");
		
		
		
		return getServiceDatas(entityManager.createNamedQuery("ServiceBooking.findAllByUsername").setParameter("createdBy", createdBy).getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getServiceDatas(List<?> storemaster)
	{
		System.out.println("inside a read Method-------------------------------"+storemaster);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final  ServiceBooking values = (ServiceBooking) iterator.next();
			 storeData = new HashMap<String, Object>();
		
			 storeData.put("sId", values.getsId());
			 storeData.put("serviceName", clubHouseService.findServiceName(values.getsId()));
			 storeData.put("bId", values.getbId());
			 storeData.put("name",values.getName());
			 
			 
			 storeData.put("blocks",values.getBlocks());
			 storeData.put("property",values.getProperty());
			 storeData.put("propertyName", propertyService.getPropertyNameBasedOnPropertyId(values.getProperty()));
			 int id=values.getName();
			 
			 List<Person> names=personService.executeSimpleQuery("SELECT p FROM Person p WHERE p.personId='"+id+"' ");
			//  personService.getSinglePersonId(values.getName());
			 // storeData.put("firstName",personService.getSinglePersonId(values.getName()) );
			 String firstname=names.get(0).getFirstName();
			 String secondname=names.get(0).getLastName();
			 
			 String personname=firstname+" "+secondname;
			  
			 storeData.put("personName",personname );
			 storeData.put("startTime",values.getStartTime());
			 storeData.put("endTime",values.getEndTime());
			 storeData.put("date",values.getDate());
			 storeData.put("serviceStatus",values.getServiceStatus());
			 storeData.put("createdBy",values.getCreatedBy());
			 storeData.put("action",values.getAction());
			 
			
		     result.add(storeData); 
		 }
		 return result;
	}


	
	@Override
	public void setItemStatus(int bId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		
		try
		{
			PrintWriter out = response.getWriter();	
			if(operation.equalsIgnoreCase("Open"))
			{
				
				
				entityManager.createNamedQuery("ServiceBooking.UpdateStatus").setParameter("status", "Opened").setParameter("bId", bId).executeUpdate();
				out.write("Service Opened");
				
			}
			else
			{
				
			   entityManager.createNamedQuery("ServiceBooking.UpdateStatus").setParameter("status", "Closed").setParameter("bId", bId).executeUpdate();
			   out.write("Service Closed");				
			}
		
		}
		catch(Exception e){
		   e.printStackTrace();
	    }
	}

	@Override
	public void setItemStatuss(int bId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		
		try
		{
			PrintWriter out = response.getWriter();	
			if(operation.equalsIgnoreCase("Approve"))
			{
				
				
				entityManager.createNamedQuery("ServiceBookings.UpdateStatus").setParameter("status", "Approved").setParameter("bId", bId).executeUpdate();
				out.write("Booking Approved");
				
			}
			else
			{
				
			   entityManager.createNamedQuery("ServiceBookings.UpdateStatus").setParameter("status", "Rejected").setParameter("bId", bId).executeUpdate();
			   out.write("Booking Rejected");				
			}
					}
		catch(Exception e){
		   e.printStackTrace();
	    }
	}


	@Override
	public void updateserviceStatus(String serviceStatus) {
		entityManager.createNamedQuery("ServiceBoooking.updateStatus").setParameter("serviceStatus",serviceStatus).executeUpdate();
		
	}
	
	@Override
	public void updateserviceStatuss(String serviceStatus) {
		entityManager.createNamedQuery("ServiceBoookings.updateStatus").setParameter("serviceStatus",serviceStatus).executeUpdate();
		
	}


	@Override
	public void updateserviceStatusBook(String serviceStatus) {
		entityManager.createNamedQuery("ServiceBoooking.updateStatusBook").setParameter("serviceStatus",serviceStatus).executeUpdate();
		
	}
	
	@Override
	public void updateserviceStatusBooks(String serviceStatus) {
		entityManager.createNamedQuery("ServiceBoookings.updateStatusBook").setParameter("serviceStatus",serviceStatus).executeUpdate();
		
	}
	
	
	
}
