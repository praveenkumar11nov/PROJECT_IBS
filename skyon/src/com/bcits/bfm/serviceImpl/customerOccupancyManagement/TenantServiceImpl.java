package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TenantServiceImpl extends GenericServiceImpl<Tenant> implements TenantSevice{

	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonStyle() {
		
		return entityManager.createNamedQuery("Tenant.getPersonStyle")
				.setParameter("personType", "%Tenant%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonTitleList() {
		
		return entityManager.createNamedQuery("Tenant.getPersonTitle")
				.setParameter("personType", "%Tenant%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonFirstName() {
		
		return entityManager.createNamedQuery("Tenant.getPersonFirstName")
				.setParameter("personType", "%Tenant%")
				.getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonMiddleName() {
		
		return entityManager.createNamedQuery("Tenant.getPersonMiddleName")
				.setParameter("personType", "%Tenant%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonLastName() {
		
		return entityManager.createNamedQuery("Tenant.getPersonLastName")
				.setParameter("personType", "%Tenant%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getLanguage() {
		
		return entityManager.createNamedQuery("Tenant.getLanguage")
				.setParameter("personType", "%Tenant%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllTenantDetails() {
		
		return entityManager.createNamedQuery("Tenant.getAllTenantDetails")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getTenantIdByInstanceOfPersonId(int personId) {
			 
			 List<Integer> tenantId =  entityManager.createNamedQuery("Tenant.getTenantIdByInstanceOfPersonId",
						Integer.class)
						.setParameter("personId", personId)
						.getResultList();
				Iterator<Integer> it=tenantId.iterator();
				while(it.hasNext()){
					
					return (int) it.next();
				}
				return 0;
	}

	@Override
	public Integer getData(int propertyId) {
		// TODO Auto-generated method stub
		return (Integer)entityManager.createNamedQuery("TenantBasedOn.property").setParameter("tenantId", propertyId).getSingleResult();
	}

	@Override
	public List<String> getContactDetailsByPersonId(int propertyId) {
		return entityManager.createNamedQuery("Tenant.getContactDetailsByPersonId").setParameter("propertyId", propertyId).getResultList();
	}

	@Override
	public String getTenantStatusByPropertyId(int propertyId) {
		try
		{
		return (String) entityManager.createNamedQuery("Tenant.getTenantStatusByPropertyId").setParameter("propertyId", propertyId).getSingleResult();
		}
		catch(Exception e)
		{
		 return null;	
		}
	}

	@Override
	public String getTenantFirstNameByPropertyId(int propertyId) {
		return (String) entityManager.createNamedQuery("Tenant.getFirstNameByPersonId").setParameter("propertyId", propertyId).getSingleResult();
	}
	}

