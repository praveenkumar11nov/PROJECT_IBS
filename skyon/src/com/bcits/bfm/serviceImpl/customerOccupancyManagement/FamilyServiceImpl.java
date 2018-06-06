package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Family;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.FamilyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class FamilyServiceImpl extends GenericServiceImpl<Family> implements FamilyService{

	 @Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public int getFamilyIdByInstanceOfPersonId(int personId) {
		 
		 List<Integer> perId =  entityManager.createNamedQuery("Family.getFamilyIdByInstanceOfPersonId",
					Integer.class)
					.setParameter("personId", personId)
					.getResultList();
			Iterator<Integer> it=perId.iterator();
			while(it.hasNext()){
				
				return (int) it.next();
			}
			return 0;
}
	 
	 @Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<Person> getAllFamilyDetails() {
			
			return entityManager.createNamedQuery("Family.getAllFamilyDetails")
					.getResultList();
	 }
	 @Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<String> getPersonStyle() {
			
			return entityManager.createNamedQuery("Family.getPersonStyle")
					.setParameter("personType", "%FamilyMember%")
					.getResultList();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<String> getPersonTitleList() {
			
			return entityManager.createNamedQuery("Family.getPersonTitle")
					.setParameter("personType", "%FamilyMember%")
					.getResultList();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<String> getPersonFirstName() {
			
			return entityManager.createNamedQuery("Family.getPersonFirstName")
					.setParameter("personType", "%FamilyMember%")
					.getResultList();
		}
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<String> getPersonMiddleName() {
			
			return entityManager.createNamedQuery("Family.getPersonMiddleName")
					.setParameter("personType", "%FamilyMember%")
					.getResultList();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<String> getPersonLastName() {
			
			return entityManager.createNamedQuery("Family.getPersonLastName")
					.setParameter("personType", "%FamilyMember%")
					.getResultList();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<String> getLanguage() {
			
			return entityManager.createNamedQuery("Family.getLanguage")
					.setParameter("personType", "%FamilyMember%")
					.getResultList();
		}
}
