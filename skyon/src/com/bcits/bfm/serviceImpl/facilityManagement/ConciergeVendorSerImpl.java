package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;






import com.bcits.bfm.model.ConciergeVendorServices;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.ConciergeService;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorSer;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class ConciergeVendorSerImpl extends GenericServiceImpl<ConciergeVendorServices> implements ConciergeVendorSer{

	@Autowired
	private ConciergeService  conciergeService;
	
	@Autowired
	private ConciergeVendorsService conciergeVendorsService;
	
	@Autowired
	private PersonService personService;
	
	private static final Log logger = LogFactory
			.getLog(ParkingSlotsAllotmentServiceImpl.class);
	
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public List<?> getVendorServices() {
		
		@SuppressWarnings("unchecked")
		List<ConciergeVendorServices> list =entityManager.createNamedQuery("ConciergeVendorServices.findAll").getResultList();
		
	List<Map<String, Object>> conciergeVendorServices =  new ArrayList<Map<String, Object>>(); 
	for (final ConciergeVendorServices record : list) {
		
		logger.info(">>>>>>>>>>end date"+ record.getEndDate());
		int csvId = record.getCsvId();
		int cssId = record.getCssId();
		final String csService = conciergeService.getServiceNameBasedOnId(cssId);
		int personId = conciergeVendorsService.getPersonIdBasedOnVendorId(csvId);
		//final Person personRec = personService.getPersonBasedOnId(personId);
		List<String> names=personService.getFirstAndLastName(personId); 
		final String first;
		if(names.size()==2){
			 first = (String) names.get(0)+" "+(String) names.get(1);
		}
		else
			first=(String)names.get(0);
		conciergeVendorServices.add(new HashMap<String, Object>() {{
			put("vsId", record.getVsId());
			put("conciergeVendors", first);
			put("csvId", record.getCsvId());
			put("conciergeService", csService);
			put("cssId", record.getCssId());
			put("startDate", ConvertDate.TimeStampString(record.getStartDate()));
			if( record.getEndDate() == null ){}
			else
			put("endDate", ConvertDate.TimeStampString(record.getEndDate()));
			put("status", record.getStatus());
			put("createdBy", record.getCreatedBy());
		}});
	}
	return conciergeVendorServices;
	}
	
	
		@Override
		@Transactional(propagation = Propagation.SUPPORTS)
	public ConciergeVendorServices getVendorServiceObject(Map<String, Object> map, String type,ConciergeVendorServices conciergeVendorServices,HttpServletRequest request) throws ParseException {
			
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		if( type == "save" ){
			
			String serviceName = (String)map.get("conciergeService");
			int serviceId = conciergeService.getServiceIdBasedOnName(serviceName);
			conciergeVendorServices.setCsvId((Integer)map.get("conciergeVendors"));
			conciergeVendorServices.setCssId(serviceId);
			conciergeVendorServices.setStatus("InActive");
			conciergeVendorServices.setCreatedBy(userName);
			
		}
		if( type == "update" ){
			
			conciergeVendorServices.setVsId((Integer)map.get("vsId"));
			conciergeVendorServices.setCsvId((Integer)map.get("csvId"));
			conciergeVendorServices.setCssId((Integer)map.get("cssId"));
			conciergeVendorServices.setStatus((String)map.get("status"));
			conciergeVendorServices.setCreatedBy((String)map.get("createdBy"));
			
		}
		conciergeVendorServices.setStartDate(ConvertDate.formattedDate((String)map.get("startDate")));
		conciergeVendorServices.setEndDate(ConvertDate.formattedDate((String)map.get("endDate")));
		conciergeVendorServices.setLastUpdatedBy(userName);
		return conciergeVendorServices;
	}

		@Override
		public void vendorServiceStatus(int vsId, String operation,
				HttpServletResponse response, MessageSource messageSource,
				Locale locale) {
			try
			{
				PrintWriter out = response.getWriter();

				if(operation.equalsIgnoreCase("activate"))
				{

					entityManager.createNamedQuery("ConciergeVendorServices.UpdateStatus")
					.setParameter("serviceStatus", "Active")
					.setParameter("vsId", vsId)
					.executeUpdate();
					out.write("Vendor Service is Activated");
				}
				
				else
				{

					entityManager.createNamedQuery("ConciergeVendorServices.UpdateStatus")
					.setParameter("serviceStatus", "InActive")
					.setParameter("vsId", vsId)
					.executeUpdate();
					out.write("Vendor Service is Deactivated");
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}	
			
		}

		@Transactional(propagation = Propagation.SUPPORTS)
		@Override
		public List<Person> getVendorNames() {
			return  entityManager.createNamedQuery("ConciergeVendorServices.getVendorNames",Person.class)
					.getResultList();
		}

		@Transactional(propagation = Propagation.SUPPORTS)
		@Override
		public List<String> getServiceNames() {
			return  entityManager.createNamedQuery("ConciergeVendorServices.getServiceNames",String.class)
					.getResultList();
		}

		@Transactional(propagation = Propagation.SUPPORTS)
		@Override
		public List<Integer> getServiceIdsBasedOnVendorId(int cvId) {
			return entityManager.createNamedQuery("ConciergeVendorServices.getServiceNamesBasedOnVendorId",
					Integer.class)
					.setParameter("csvId", cvId)
					.getResultList();
			
		}

		@Transactional(propagation = Propagation.SUPPORTS)
		@SuppressWarnings("unchecked")
		@Override
		public List<ConciergeVendorServices> findAll() {
			return entityManager.createNamedQuery("ConciergeVendorServices.findAll").getResultList();
		}
		
		@Override
		public void updateServiceEndDate(int vendorServiceId, Date serviceEndDate) {
			
			entityManager.createNamedQuery("ConciergeVendorServices.updateServiceEndDate")
			.setParameter("vsId", vendorServiceId)
			.setParameter("serviceEndDate", serviceEndDate)
			.executeUpdate();
			
		}

		@Override
		public int getVendorServiceId(int csvId, int cssId) {
			
			List<Integer> list = entityManager.createNamedQuery("ConciergeVendorServices.getVendorServiceId",
			Integer.class)
			.setParameter("csvId", csvId)
			.setParameter("cssId", cssId)
			.getResultList();
			Iterator<Integer> it=list.iterator();
			while(it.hasNext()){
				
				return (int) it.next();
			}
			return 0;
		}

		
}
