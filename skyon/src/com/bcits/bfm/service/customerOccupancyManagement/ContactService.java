package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.service.GenericService;

public interface ContactService extends GenericService<Contact> 
{
	
	public List<Contact> findAll();

	public Contact getContactObject(Map<String, Object> map, String type,
			Contact contact);

	public Integer getContactIdBasedOnCreatedByAndLastUpdatedDt(String createdBy,
			Timestamp lastUpdatedDt);

	public List<Contact> findAllContactsBasedOnPersonID(int personId);
	@SuppressWarnings("rawtypes")
	public List getContactContent(int personId);



    public	int getContactId(int contactId);

	int updateContactContent(int contactId, String phno);

	public List<Contact> checkForUniquePrimary(int personId, String contactType,
			String contactPrimary);

	public String getContactPrimary(Integer integer, String string);

	public List<Contact> getContactsOnAddressId(int addressId);

	public void updateAddressiIdContact(int aId);

	public List<String> getAllContactContent();

	public List<?> findAllEmailAddresses();
	
	List<Contact> getContactBasedOnPersonId( int personId );

	public List<Contact> findContactsByParentID(String username);

	public List<Contact> getContactEmailBasedOnPersonId(int personId);

	public List<Contact> getContactPhoneBasedOnPersonId(int personId);


	public List<Contact> getContactListBasedOnContactContent(
			String contactContent);


	public List<Contact> getContactContent2(int personId);


	
}
