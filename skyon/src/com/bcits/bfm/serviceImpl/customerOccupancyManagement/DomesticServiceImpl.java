package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Domestic;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.DomesticService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class DomesticServiceImpl extends GenericServiceImpl<Domestic> implements DomesticService{
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonStyle() {
		
		return entityManager.createNamedQuery("Domestic.getPersonStyle")
				.setParameter("personType", "%DomesticHelp%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonTitleList() {
		
		return entityManager.createNamedQuery("Domestic.getPersonTitle")
				.setParameter("personType", "%DomesticHelp%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonFirstName() {
		
		return entityManager.createNamedQuery("Domestic.getPersonFirstName")
				.setParameter("personType", "%DomesticHelp%")
				.getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonMiddleName() {
		
		return entityManager.createNamedQuery("Domestic.getPersonMiddleName")
				.setParameter("personType", "%DomesticHelp%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonLastName() {
		
		return entityManager.createNamedQuery("Domestic.getPersonLastName")
				.setParameter("personType", "%DomesticHelp%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getLanguage() {
		
		return entityManager.createNamedQuery("Domestic.getLanguage")
				.setParameter("personType", "%DomesticHelp%")
				.getResultList();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllDomesticDetails() {
		
		return entityManager.createNamedQuery("Domestic.getAllDomesticDetails")
				.getResultList();
	}
	
	 @Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public int getDomesticIdByInstanceOfPersonId(int personId) {
		 
		 List<Integer> perId =  entityManager.createNamedQuery("Domestic.getDomesticIdByInstanceOfPersonId",
					Integer.class)
					.setParameter("personId", personId)
					.getResultList();
			Iterator<Integer> it=perId.iterator();
			while(it.hasNext()){
				
				return (int) it.next();
			}
			return 0;
}
}
