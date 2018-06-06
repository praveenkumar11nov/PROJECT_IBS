package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.Personnel;
import com.bcits.bfm.service.customerOccupancyManagement.PersonAccessCardService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class PersonAccessCardServiceImpl extends GenericServiceImpl<PersonAccessCards> implements PersonAccessCardService
{

	@PersistenceContext(unitName="MSSQLDataSourceAccessCards")
	private EntityManager msSQLentityManager;
	
	@Override
	public PersonAccessCards getAccessCardsObject(Map<String, Object> map,String operation, PersonAccessCards personAccessCards) 
	{
		if(operation == "update")
		{
			personAccessCards.setPersonacId((Integer)map.get("personacId"));
			personAccessCards.setCreatedBy((String)map.get("createdBy"));
			personAccessCards.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		else if (operation == "save")
		{
			personAccessCards.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			personAccessCards.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));	
		}
		personAccessCards.setAcId((Integer) map.get("acId"));
		personAccessCards.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		return personAccessCards;
	}

	@Override
	@SuppressWarnings({ "unchecked", "serial" })
	public List<?> findOnPersonId(int personId) 
	{
		
		List<PersonAccessCards> personAccList = entityManager.createNamedQuery("personAccessCards.getOnPersonId").setParameter("personId", personId).getResultList();
		List<Map<String, Object>> permissionsMap =  new ArrayList<Map<String, Object>>();    

		for (final PersonAccessCards record : personAccList) 
		{
			permissionsMap.add(new HashMap<String, Object>() {{
			put("personacId",record.getPersonacId());	
			put("acId", record.getAcId());
			System.out.println("acid"+record.getAcId());
		    Personnel per=msSQLentityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo", record.getAcId()).getSingleResult();
			System.out.println("per.getNonAbacardNumber()"+per.getNonAbacardNumber());
			put("acNo", per.getNonAbacardNumber());
			put("createdBy",record.getCreatedBy());
			put("lastUpdatedBy",record.getLastUpdatedBy());
			}});
		}
		return permissionsMap;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PersonAccessCards> findPersonAccessCards() 
	{
		return entityManager.createNamedQuery("PersonAccessCards.findPersonAccessCards").getResultList();
	}

	@Override
	public List<Integer> readAccessCardsForUniqe() {
		
		return entityManager.createNamedQuery("personAccessCards.readAccessCardsForUniqe",Integer.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonAccessCards> getPersonBasedOnAccessCard(Integer objectIdLo) {
		return entityManager.createNamedQuery("PersonAccess.Size").setParameter("acId", objectIdLo).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonAccessCards> getPersonAccessCardBasedOnPersonId(
			int personId) {
		return entityManager.createNamedQuery("personAccessCards.getOnPersonId").setParameter("personId", personId).getResultList();
	}
}
