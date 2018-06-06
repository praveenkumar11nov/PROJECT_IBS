package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AccessCards;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.Personnel;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardSevice;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class AccessCardServiceImpl extends GenericServiceImpl<AccessCards> implements AccessCardSevice{

	@PersistenceContext(unitName="MSSQLDataSourceAccessCards")
	private EntityManager msSQLentityManager;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessCards> findAll() {
		return entityManager.createNamedQuery("AccessCards.findAll").getResultList();
	}

	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("rawtypes")
	public int getAccesCardIdBasedOnNo(String acNo) {
		
		List<Integer> accessRepositoryId =  entityManager.createNamedQuery("AccessCards.getAccesCardIdBasedOnNo",
				Integer.class)
				.setParameter("acNo", acNo)
				.getResultList();
	Iterator it=accessRepositoryId.iterator();
	while(it.hasNext()){
		
		return (int) it.next();
	}
	return 0;
	 
}

	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getaccessCradNoBasedOnId(int acId) {
		List<String> accessRepositoryId =  entityManager.createNamedQuery("AccessCards.getaccessCradNoBasedOnId",
				String.class)
				.setParameter("acId", acId)
				.getResultList();
	Iterator it=accessRepositoryId.iterator();
	while(it.hasNext()){
		
		return (String) it.next();
	}
	return "";
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public AccessCards getAccessCardsObject(Map<String, Object> map,
			String type, AccessCards accessCards) 
	{
		if(type == "update")
		{
			accessCards.setAcId((Integer) map.get("acId"));
			accessCards.setCreatedBy((String)map.get("createdBy"));
			accessCards.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		else if (type == "save")
		{
			accessCards.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			accessCards.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));	
		}
		accessCards.setAcNo((String)map.get("acNo"));
		accessCards.setStatus((String)map.get("status"));
		accessCards.setAcType((String)map.get("acType"));
		return accessCards;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findOnPersonId(int personId) 
	{
		return entityManager.createNamedQuery("AccessCards.findOnPersonId").setParameter("personId", personId).getResultList();
	}
	
	
	/*@Override
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findOnPersonIdForPermissions(int personId) 
	{
		
		List<?> list =  entityManager.createNamedQuery("AccessCards.findOnPersonIdForPermissions").setParameter("personId", personId).getResultList();
		
		List<Map<String, Object>> accessCardsList = new ArrayList<Map<String, Object>>();

		Map<String, Object> accessCardMap = null;

		for (Iterator i = list.iterator(); i.hasNext();) 
		{
			accessCardMap = new HashMap<String, Object>();

			final Object[] values = (Object[]) i.next();

			accessCardMap.put("acId", (Integer)values[0]);
			accessCardMap.put("acNo", (String)values[1]);
			accessCardsList.add(accessCardMap);
		}	
		
		return accessCardsList;
	}*/
	
	
	@SuppressWarnings({ "unchecked" })
	public List<?> findOnPersonIdForPermissions(int personId) 
	{
		
		List<PersonAccessCards> list =  entityManager.createNamedQuery("personAccessCards.getOnPersonId").setParameter("personId", personId).getResultList();
		
		List<Map<String, Object>> accessCardsList = new ArrayList<Map<String, Object>>();

		Map<String, Object> accessCardMap = null;
		for (final PersonAccessCards record : list) 
		{
			accessCardMap = new HashMap<String, Object>();
			accessCardMap.put("acId",record.getAcId());
		    Personnel per=msSQLentityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo", record.getAcId()).getSingleResult();
			System.out.println("per.getNonAbacardNumber()"+per.getNonAbacardNumber());
			accessCardMap.put("acNo", per.getNonAbacardNumber());
			accessCardsList.add(accessCardMap);
		}	
		
		return accessCardsList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getAcName(String acNo, int personId) {
		return entityManager.createNamedQuery("AccessCards.findAcNames")
				.setParameter("acNo", acNo)
				.setParameter("personId", personId)
				.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessCards> findAllAccessCards() 
	{
		return entityManager.createNamedQuery("AccessCards.findAllAccessCards").getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<?> findOnPersonIdForSystemSecurity(int personId) 
	{
		List<?> list =  entityManager.createNamedQuery("AccessCards.findOnPersonIdForSystemSecurityDisplay").setParameter("personId", personId).getResultList();
		
		List<Map<String, Object>> accessCardsList = new ArrayList<Map<String, Object>>();

		Map<String, Object> accessCardMap = null;

		for (Iterator i = list.iterator(); i.hasNext();) 
		{
			accessCardMap = new HashMap<String, Object>();

			final Object[] values = (Object[]) i.next();

			accessCardMap.put("acType", (String)values[0]);
			accessCardMap.put("acNo", (String)values[1]);
			accessCardMap.put("status", (String)values[2]);
			accessCardsList.add(accessCardMap);
		}	
		
		return accessCardsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAccessCardStatus(int acId, String operation,
			HttpServletResponse response) 
	{
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("AccessCards.UpdateStatus").setParameter("status", "Active").setParameter("acId", acId).executeUpdate();
				out.write("Access Card Activated");
			}
			else if(operation.equalsIgnoreCase("invalid"))
			{
				out.write("No Contact Records Found, User can't be activated");
			}
			else
			{
				List<PersonAccessCards> p=entityManager.createNamedQuery("PersonAccess.Size").setParameter("acId", acId).getResultList();
				
				if(p.size()>0)
				{
					out.write("Access Card Alredy In Use Cannot be Deactivated");
				}
				else
				{
				entityManager.createNamedQuery("AccessCards.UpdateStatus").setParameter("status", "Inactive").setParameter("acId", acId).executeUpdate();
				out.write("Access Card Deactivated");
				}
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<?> findAllAccessCardNumbers() {
		return entityManager.createNamedQuery("AccessCards.findAllAccessCardNumbers").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessCards> getAcNosBasedOnpersonId(int personId) {
		return entityManager.createNamedQuery("AccessCards.getAcNosBasedOnpersonId")
				.setParameter("personId", personId)
				.getResultList();
	}

	@Override
	public List<?> getCardAssignedStatusOnId(int acId) 
	{
		List<?> acList = entityManager.createNamedQuery("AccessCard.getCardAssignedStatusOnId")
				.setParameter("acId", acId)
				.getResultList();
		
		return getAllPersonDetailsBasedOnPropertyIdAndPersonType(acList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<?> getAllPersonDetailsBasedOnPropertyIdAndPersonType(List<?> acList) 
	{
		
		List acNewList = new ArrayList();
		for (Iterator<Object> i = (Iterator<Object>) acList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			Map<String, Object> acMap = new HashMap<String, Object>();
			acMap.put("acId", (Integer)values[0]);
			
			personName = (String)values[1];
			if(((String)values[2]) != null)
			   {
			    personName = personName.concat(" ");
			    personName = personName.concat((String)values[2]);
			   }
			  if(((String)values[3]) != null)
			   {
			    personName = personName.concat(" ");
			    personName = personName.concat((String)values[3]);
			   }
			 
			  acMap.put("personName", personName);
			  acMap.put("personType", (String)values[4]);
			  
			  String mobileNumber = "";
				List<Contact> contactList  = entityManager.createNamedQuery("Property.getPersonContactDetails").setParameter("personId", (Integer)values[5]).getResultList();
				if(contactList.size() > 0 && StringUtils.equalsIgnoreCase(contactList.get(0).getContactPrimary(), "Yes"))
				{
					for(Contact c :contactList)
					{
						mobileNumber = mobileNumber +"/"+ c.getContactContent();
					}
					
				}
				mobileNumber = mobileNumber.startsWith("/") ? mobileNumber.substring(1) : mobileNumber;
				if(mobileNumber == "")
				{
					mobileNumber = "No contact details";
				}
				acMap.put("contactContent", mobileNumber);
			
			
			acNewList.add(acMap);
		}	
		
		return acNewList;
	}


	@SuppressWarnings("serial")
	@Override
	public List<?> readAccessCardData() {
		
		List<?> readAccessCardData = entityManager.createNamedQuery("AccessCards.findAllList").getResultList();

		List<Map<String, Object>> readAccessCard = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = readAccessCardData.iterator(); i.hasNext();) {
			readAccessCard.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					
					put("acId", (Integer)values[0]);
					put("acType", (String)values[1]);
					put("acNo", (String)values[2]);
					put("status", (String)values[3]);
					put("createdBy", (String)values[4]);
					put("lastUpdatedBy", (String)values[5]);
					
				}
			});
		}
		return readAccessCard;
		
		
	}
	
	
}
