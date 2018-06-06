package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ConciergeServices;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.CsServiceBooking;
import com.bcits.bfm.model.CsVendorServiceCharges;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.ConciergeService;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorsService;
import com.bcits.bfm.service.facilityManagement.CsServiceBookingService;
import com.bcits.bfm.service.facilityManagement.CsVendorServiceChargeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConciergeSMS;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.SessionData;

@Repository
public class CsServiceBookingServiceImpl extends GenericServiceImpl<CsServiceBooking> implements CsServiceBookingService {
	

	@Autowired
	private ConciergeVendorsService conciergeVendorsService;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private ConciergeService  conciergeService;
	
	@Autowired
	private CsVendorServiceChargeService csVendorServiceChargeService;
	
	@Autowired
	private ContactService contactService;
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public CsServiceBooking getBookingServiceObject(Map<String, Object> map, String type, CsServiceBooking csServiceBooking ,HttpServletRequest request)
	{
		
		if (type == "save")
		{			
			final Map<String,Object> ownerMap = (Map<String,Object>)map.get("ownerNames");
			final Map<String,Object> rateTypeMap = (Map<String,Object>)map.get("rateType");
			csServiceBooking.setOwnerId((Integer)ownerMap.get("ownerId"));
			csServiceBooking.setPropertyId((Integer)ownerMap.get("propertyId"));
			csServiceBooking.setVscId((Integer)rateTypeMap.get("vscId"));
			csServiceBooking.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			csServiceBooking.setServiceDelivered("No");
			
			/*vendors.setStatus((String)map.get("status"));*/
		}
		if (type == "update")
		{
			csServiceBooking.setSbId((Integer)map.get("sbId"));
			csServiceBooking.setVscId((Integer)map.get("vscId"));
			csServiceBooking.setServiceDelivered((String)map.get("serviceDelivered"));
			csServiceBooking.setOwnerId((Integer)map.get("ownerId"));
			csServiceBooking.setPropertyId((Integer)map.get("propertyId"));
			csServiceBooking.setCreatedBy((String)map.get("createdBy"));
			
		}
		//csServiceBooking.setOwnerId((Integer)ownerMap.get("ownerId"));
		//csServiceBooking.setPropertyId((Integer)ownerMap.get("propertyId"));
		csServiceBooking.setBookingDate(ConvertDate.formattedDate((String)map.get("bookingDate")));
		csServiceBooking.setVqId(0);
		csServiceBooking.setBookedBy((String)map.get("bookedBy"));
		csServiceBooking.setSbComments((String)map.get("sbComments"));
		csServiceBooking.setInvoiceRaised("null");
		csServiceBooking.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		return csServiceBooking;
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getBookedServices() {
		@SuppressWarnings("unchecked")
		List<CsServiceBooking> list =entityManager.createNamedQuery("CsServiceBooking.findAll").getResultList();
		
	List<Map<String, Object>> csServiceBooking =  new ArrayList<Map<String, Object>>(); 
	for (final CsServiceBooking record : list) {
		 Person personrec;
		personrec = personService.getPersonBasedOnOwnerId(record.getOwnerId());
		if( personrec == null ){
			personrec = personService.getPersonBasedOnTenantId(record.getOwnerId());
		}
		final ConciergeServices csServiceRec = csVendorServiceChargeService.getVendorServiceBasedOnServiceChargeID(record.getVscId());
		final CsVendorServiceCharges serviceChargeRec = csVendorServiceChargeService.getServiceChargeBasedOnId(record.getVscId());
		final String lastNm = personrec.getLastName();
		final String firsNm = personrec.getFirstName();
		final String type = personrec.getPersonType();
		csServiceBooking.add(new HashMap<String, Object>() {{
			put("sbId", record.getSbId());
			put("vendorServices", csServiceRec.getServiceName());
			if( serviceChargeRec.getVendorServices().getCsVendors().getPerson().getLastName() == null ){
				put("vendors", serviceChargeRec.getVendorServices().getCsVendors().getPerson().getFirstName());}
			else{
			put("vendors", serviceChargeRec.getVendorServices().getCsVendors().getPerson().getFirstName()+" "+serviceChargeRec.getVendorServices().getCsVendors().getPerson().getLastName());
			}
			put("csvId", serviceChargeRec.getVendorServices().getCsVendors().getCsvId());
			put("rateType", serviceChargeRec.getVendorRateType());
			put("rate", serviceChargeRec.getVendorRate());
			put("vscId", record.getVscId());
			if( lastNm == null ){
				put("ownerNames",firsNm+"["+lastNm+"]");
				//put("ownerNames",personrec.getFirstName());
			}
			else{
				put("ownerNames",firsNm+" "+lastNm+"["+type+"]" );
				//put("ownerNames",personrec.getFirstName()+" "+personrec.getLastName() );
			}
			put("ownerId", record.getOwnerId());
			put("propertyId", record.getPropertyId());
			put("bookedBy", record.getBookedBy());
			put("sbComments", record.getSbComments());
			put("bookingDate", ConvertDate.TimeStampString(record.getBookingDate()));
			put("serviceDelivered", record.getServiceDelivered());
			put("createdBy", record.getCreatedBy());
			put("lastUpdatedBy", record.getLastUpdatedBy());
		}});
	}
	return csServiceBooking;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CsServiceBooking> findAll() {
		return entityManager.createNamedQuery("CsServiceBooking.findAll").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setServiceBookingStatus(int sbId, String operation,HttpServletResponse response, MessageSource messageSource,Locale locale) {
		
		String vendorName;
		String ownerName;
		String serviceName;
		
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("Yes"))
			{
				entityManager.createNamedQuery("CsServiceBooking.UpdateStatus").setParameter("status", "Yes").setParameter("sbId", sbId).executeUpdate();
				CsServiceBooking csServiceBooking = (CsServiceBooking) entityManager.createNamedQuery("CsServiceBooking.getObject")
						.setParameter("sbId", sbId)
						.getSingleResult();
				 vendorName = csServiceBooking.getVendorServiceCharges().getVendorServices().getCsVendors().getPerson().getFirstName();
				 serviceName = csServiceBooking.getVendorServiceCharges().getVendorServices().getCsServices().getServiceName();
				 
				 Person personrec;
					personrec = personService.getPersonBasedOnOwnerId(csServiceBooking.getOwnerId());
					if( personrec == null ){
						personrec = personService.getPersonBasedOnTenantId(csServiceBooking.getOwnerId());
					}
				 
				 int personId = personrec.getPersonId();
				 if( personrec.getLastName() == null ){
					 ownerName = personrec.getFirstName();
				 }
				 else
					 ownerName = personrec.getFirstName()+" "+personrec.getLastName(); 
				 
				if( csServiceBooking.getServiceDelivered().equals("Yes") ){
					
					List<Contact> contact =  contactService.getContactBasedOnPersonId(personId);
					for (@SuppressWarnings("rawtypes")
					Iterator iterator = contact.iterator(); iterator
							.hasNext();) {
						Contact contact2 = (Contact) iterator.next();
						if( contact2.getContactType().equals("Mobile") && contact2.getContactPrimary().equals("Yes") ){
							String ownerMobileNo = contact2.getContactContent(); 
							if((ownerMobileNo != null) && (ownerMobileNo.length() >= 10)){
								
								String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
								String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);
								ownerMobileNo = ownerMobileNo.substring(ownerMobileNo.length()-10, ownerMobileNo.length());
								new Thread(new ConciergeSMS(vendorName, ownerName, serviceName, ownerMobileNo, gateWayUserName, gateWayPassword)).start();
								
							}
							
						}
						
					}
					
				}
				
				out.write("Now this service is in 'Delivered' status");
			}
			else
			{

				entityManager.createNamedQuery("CsServiceBooking.UpdateStatus").setParameter("status", "No").setParameter("sbId", sbId).executeUpdate();
				out.write("Now this service is still in 'Hold' status");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	

	}
		
	}
