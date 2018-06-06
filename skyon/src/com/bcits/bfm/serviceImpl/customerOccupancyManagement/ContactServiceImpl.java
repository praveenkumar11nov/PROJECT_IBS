package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

/**
 * A data access object (DAO) providing persistence and search support for
 * Address entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 */
@Repository
public class ContactServiceImpl extends GenericServiceImpl<Contact> implements ContactService
{
	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ContactServiceImpl.class);
	
	/** finding all Contact instances
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#findAll()
	 * @return List Of Contacts
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Contact> findAll() {
		try {
			return entityManager.createNamedQuery("Contact.findAll").getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	/** Read all the contacts on person id
	 * @param personId Person Id
	 * @return List of contact
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getContactContent(int personId){
		
		return entityManager.createNamedQuery("Contact.Content").setParameter("personId", personId).getResultList();
		
	}

	/** Read ContactId Based On CreatedBy And LastUpdatedDt
	 * @param createdBy Created By
	 * @param lastUpdatedDt Last Updated By
	 * @return Contact Id
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getContactIdBasedOnCreatedByAndLastUpdatedDt(String createdBy,
			Timestamp lastUpdatedDt) {
		return entityManager.createNamedQuery("Contact.getContactIdBasedOnCreatedByAndLastUpdatedDt", Integer.class).setParameter("createdBy", createdBy).setParameter("lastUpdatedDt", lastUpdatedDt).getSingleResult();		
	}
	
	/** Sets the information from view to object
	 * @param map	details from the view
	 * @param type 	Save or Update
	 * @param contact	Contact Object
	 * @return Contact Object
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Contact getContactObject(Map<String, Object> map, String type,
			Contact contact) {
		
		if(type == "update")
		{
			contact.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			contact.setContactId((Integer) map.get("contactId"));
			contact.setPersonId((Integer) map.get("personId"));
			contact.setCreatedBy((String) map.get("createdBy"));
		}
		else if (type == "save")
		{
			contact.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			contact.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		
		contact.setContactContent((String) map.get("contactContent"));
		contact.setContactLocation((String) map.get("contactLocation"));
		contact.setContactPreferredTime((String) map.get("contactPrefferedTime"));
		contact.setContactPrimary((String) map.get("contactPrimary"));
		//contact.setContactSeason((String) map.get("contactSeason"));
		contact.setContactType((String) map.get("contactType"));
		if(map.get("addressId") instanceof Integer && map.get("addressId")!=null ){
			contact.setAddressId((Integer)map.get("addressId"));
		}
		contact.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		
		if(map.get("contactSeason") instanceof java.lang.String || map.get("contactSeason") == null){

			//Do Nothing
		}
		else {
			if((boolean)map.get("contactSeason")){
				contact.setContactSeason("Yes");

				contact.setContactSeasonFrom(ConvertDate.formattedDate(map.get("contactSeasonFrom").toString()));
				contact.setContactSeasonTo(ConvertDate.formattedDate(map.get("contactSeasonTo").toString()));
			}
			else{
				contact.setContactSeason("No");
			}
		}
		return contact;

	}

	/** Support Method to convert date
	 * @param str 		date String
	 * @return			Sql Date
	 */
	@SuppressWarnings("unused")
	private java.sql.Date getDate(String str) {
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
		Calendar cal = Calendar.getInstance(tz);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setCalendar(cal);		
		try {
			cal.setTime(sdf.parse(str));
		} catch (ParseException e) {e.printStackTrace();}
		
		Date date = cal.getTime();
		
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		return sqlDate;
	}	
	
	
	/** find All Contacts Based On PersonID
	 * @param personId Person ID
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#findAllContactsBasedOnPersonID(int)
	 * @return List of Contact
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Contact> findAllContactsBasedOnPersonID(int personId) {
		return entityManager.createNamedQuery("Contact.findAllContactsBasedOnPersonID").setParameter("personId", personId).getResultList();
	}

	/** Get COntact Id
	 * @param personId Person Id
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#getContactId(int)
	 * @return contact id
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int  getContactId(int personId) {
		return entityManager.createNamedQuery("Contact.ContactId",Integer.class).setParameter("personId", personId).getSingleResult();
	}
	
	/** Update Contact Content
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#updateContactContent(int, java.lang.String)
	 * @param contactId Contact Id
	 * @param phno Phone Number
	 * @return none
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int  updateContactContent(int contactId,String phno) {
		return entityManager.createNamedQuery("UpadteContactContent").setParameter("contactId", contactId).setParameter("contactContent", phno).executeUpdate();
	}

	/** Check Unique primary
	 * @param personId Person Id
	 * @param contactType Contact Type
	 * @param contactPrimary Contact Primary
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#checkForUniquePrimary(int, java.lang.String, java.lang.String)
	 * @return List of Contacts
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Contact> checkForUniquePrimary(int personId, String contactType,
			String contactPrimary) {
		try {
			return entityManager.createNamedQuery("Contact.checkForUniquePrimary").setParameter("personId", personId).setParameter("contactType", contactType).setParameter("contactPrimary", contactPrimary).getResultList();
			
		} catch (RuntimeException re) {
			throw re;
		}
	}


	/** Get Contact Primary
	 * @param contactId Contact Id
	 * @param contactType Contact Type
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#getContactPrimary(java.lang.Integer, java.lang.String)
	 * @return String of Contact Primary
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getContactPrimary(Integer contactId, String contactType) {
		
		try {
			return entityManager.createNamedQuery("Contact.getContactPrimary",String.class).setParameter("contactId", contactId).setParameter("contactType", contactType).getSingleResult();
			
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/** Get Contacts on address id
	 * @param addressId Address Id
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#getContactsOnAddressId(int)
	 * @return List of Contact
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Contact> getContactsOnAddressId(int addressId) {
		List<Contact> al= entityManager.createNamedQuery("Contact.getContactsOnAddressId").setParameter("addressId", addressId).getResultList();
		return al;
	}

	/** Update Address Id
	 * @param aId Address Id
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#updateAddressiIdContact(int)
	 * @return none
	 */
	@Override
	public void updateAddressiIdContact(int aId) {
		entityManager.createNamedQuery("Contact.updateAddressiIdContact").setParameter("addressId", aId).executeUpdate();
		
	}

	
	/** Get All Contacts Contents
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#getAllContactContent()
	 * @return List of Contacts
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public List<String> getAllContactContent() 
	{ 
		return entityManager.createNamedQuery("Contact.getAllContactContent").getResultList();
	}


	/** Get All Email
	 * @see com.bcits.bfm.service.customerOccupancyManagement.ContactService#findAllEmailAddresses()
	 * @return List of email
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public List<?> findAllEmailAddresses() {
		return entityManager.createNamedQuery("Contact.findAllEmailAddresses").getResultList();
		
	}


	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public List<Contact> getContactBasedOnPersonId(int personId) {
		
		return entityManager.createNamedQuery("Contact.getContactBasedOnPersonId")
				.setParameter("personId", personId)
				.getResultList();
	}


	@Override
	public List<Contact> findContactsByParentID(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getContactEmailBasedOnPersonId(int personId) {

		return entityManager.createNamedQuery("Contact.getContactEmailBasedOnPersonId")
				.setParameter("personId", personId)
				.getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getContactPhoneBasedOnPersonId(int personId) {
		return entityManager.createNamedQuery("Contact.getContactPhoneBasedOnPersonId")
				.setParameter("personId", personId)
				.getResultList();
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getContactListBasedOnContactContent(
			String contactContent) {
		
		return entityManager.createNamedQuery("Contact.getContactListBasedOnContactContent")
				.setParameter("contactContent", contactContent)
				.getResultList();
	}



	/*@Override
	public Users getUserInstanceByLoginNameNew(String userLoginName) {
		// TODO Auto-generated method stub
		return null;
	}*/

	
	@Override
    public List getContactContent2(int personId){
		
		return entityManager.createNamedQuery("Contact.Content2").setParameter("personId", personId).getResultList();
		
	}

	
}
