package com.bcits.bfm.serviceImpl;

import java.sql.Blob;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.SendMailBillService;


@Repository

public class SendMailBillServiceImpl extends GenericServiceImpl<PrepaidBillDetails> implements SendMailBillService

{

	@Override
	public List<?> fetchBillsDataBasedOnMonthAndServiceType(int actualmonth,int year,int propId) {
		
		return entityManager.createNamedQuery("PrepaidBillDetails.fetchBillsDataBasedOnMonthAndPropId").setParameter("actualmonth", actualmonth).setParameter("year", year).setParameter("propertyId", propId).getResultList();
	}	

	
	@Override
	public Blob getBlog(String billNo) {
		try {
			return entityManager.createNamedQuery("PrepaidBillDocument.getBlog",Blob.class).setParameter("billNo", billNo).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getContactDetailsForMail(int personId) {
		return entityManager.createNamedQuery("PrepaidBillDetails.getContactDetailsForMail").setParameter("personId", personId).getResultList();
	}
	
	
	

	@Override
	public String getTenantStatusByPropertyId1(int propertyId) {
		try
		{
		return (String) entityManager.createNamedQuery("Tenant.getTenantStatusByPropertyId1").setParameter("propertyId", propertyId).getSingleResult();
		}
		catch(Exception e)
		{
		 e.printStackTrace();
		 return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getContactDetailsByPersonId1(int propertyId) {
		return entityManager.createNamedQuery("Tenant.getContactDetailsByPersonId1").setParameter("propertyId", propertyId).getResultList();
	}
	
	
	@Override
	public String getTenantFirstNameByPropertyId1(int propertyId) {
		System.out.println("Property id++++++++++++"+propertyId);
		return (String) entityManager.createNamedQuery("Tenant.getFirstNameByPersonId1").setParameter("propertyId", propertyId).getSingleResult();
	}
	
	
	
	
	@Override
	public Object[] getPersonNameBasedOnPersonId1(int personId) {
		try{
			return (Object[])entityManager.createNamedQuery("OpenNewTicketEntity.getPersonNameBasedOnPersonId1").setParameter("personId", personId).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	
	
	@Override
	//@Transactional(propagation = Propagation.SUPPORTS)
	public Users getUserInstanceByLoginName1(String userLoginName) {
		return entityManager.createNamedQuery("Users.getUserInstanceByLoginName1",Users.class).setParameter("userLoginName", userLoginName).getSingleResult();
		
	}
	
	

	public Object[] getPropertyDataBasedOnPropertyId1(int propertyId) {
		try{
			return (Object[])entityManager.createNamedQuery("OpenNewTicketEntity.getPropertyDataBasedOnPropertyId1").setParameter("propertyId", propertyId).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}


	@Override
	public int getUserIdBasedOnPersonId1(int personId) {
		try{
			return (int)entityManager.createNamedQuery("OpenNewTicketEntity.getUserIdBasedOnPersonId1").setParameter("personId", personId).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}
	
	
}
