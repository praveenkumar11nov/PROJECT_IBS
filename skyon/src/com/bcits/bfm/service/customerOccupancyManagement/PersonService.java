package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.GenericService;

public interface PersonService extends GenericService<Person>
{

	/**
	 * Find all Person entities.
	 * 
	 * @return List<Person> all Person entities
	 */
	public List<Object[]> findAllStaff();
	
	public List<Object[]> getAllPerson();
	
	public List<Object[]> getAllTenant();
	
	public List<Object[]> getAllFamily();
	
	public List<Object[]> getAllDomestic();
	
	public List<Object[]> getAllVendor();
	
	public List<Person> findDistinct();

	public Person getPersonObject(Map<String, Object> map, String type,
			Person person);

	public List<Integer> getPersonIdBasedOnPersonName(String personName);
	
	public List<Integer> getPersonIdBasedOnPersonFullName(String fstName,String lstName);

	@SuppressWarnings("rawtypes")
	public List getAllPersonsRequiredFilds(String personType);

	public Integer getPersonIdBasedOnCreatedByAndLastUpdatedDt(String createdBy, Timestamp lastUpdatedDt);
	
	public List<Person> getStaffNames(String type);
	
	List<Person> getCsVendorNames(String type);

	public Person getPersonInstanceBasedOnCreatedByAndLastUpdatedDt(
			String createdBy);
	
	@SuppressWarnings("rawtypes")
	public List getAllPersonList(List personList);

	public List<Map<String, Object>> getPersonFullDetails(Person person);

	public List<Object> getAllPersonNamesList(String personType);

	public List<Person> getPersonDetails();
	
	public List<Person> getAllFamilyDetails();
	
	public List<Object> getAllVendorNamesList();
	
	public List<Person> getAllOwnerRecord();

	public List<Map<String, Object>> getUserPerson();
	
	public List<Object[]> findAllUserPerson();
	
	public List<Person> getPersonDetailsBasedOnTypes();

	public List<?> getAllPersonDetailsBasedOnPersonType(String personType);

	public void updatePersonType(Person person, String personType);
	
    public List<String> getPersonStyle();
	
	public List<String> getPersonTitleList();
	
	public List<String> getPersonFirstName();
	
	public List<String> getPersonLanguages();
	
	public List<String> getPersonMiddleName();
	
	public List<String> getPersonLastName();
	
	public List<String> getLanguage();
	
	public List<Person> getAllVendorDetails();

	public void uploadImageOnId(int personId,Blob files);

	public Blob getImage(int personId);

	public List<?> getAllOwnersUnderProperty(String personType, int propertyId);

	public List<?> getAllFamilyUnderProperty(String personType, int propertyId);

	public List<?> getAllTenantUnderProperty(String personType, int propertyId);

	public List<?> getAllDomesticHelpUnderProperty(String personType,
			int propertyId);

	
	public List<Person> getStaffNames(int personId);
	/*public List<Person> getAllPerson(int personId);*/

	public Object getSinglePerson(int personId);
	
	public Person getPersonBasedOnId(int personId);

	public List<String> getAllAttributeValues(String personType, String attribute);

	public String setPersonStatus(int personId, String operation, String personType);
	
	public List<Person> getAllOwnersOnPropetyId(int propertyId);
	
	public List<Person> getAllTenantOnPropetyId(int propertyId);
	
	public List<Person> getAllDomesticOnPropetyId(int propertyId);

	public List<Object> getAllPersonRequiredFieldsBasedOnPersonStyle(String personStyle);

	List<?> getAllAccountPersonDetailsBasedOnPersonType(String personType);

	List<Object> getAllPersonFullNamesList(String personType);

	public List<?> getAllPetsOnProperytId(String petType, int propertyId);
	
	public List<Person> getOwnersBasedOnType(String type);
	
	Person getPersonBasedOnOwnerId(int ownerId);
	
	Person getPersonBasedOnTenantId(int tenantId);

	public  List<Object[]> getStaffAndVendorName();

	public  List<Object[]> getStaffOrVendorName(String personType);

	public Person getPersonNewObject(Person person, String operation);

	List<?> getAllPersonRequiredDetailsBasedOnPersonType(String personType);

	public String updateKycCompliant(String kycCompliant, int personId);

	List<Object[]> getOwnersBasedOnBlock();
	
	int getGroupIdBasedOnPersonId(int personId);

	List<Object[]> getTenantsBasedOnBlock();

	List<Object[]> findAllStaff1();

	public List<Person> getContactNameBasedOnPersonId(int personId);

	public Integer findMaxId();

	public List<String> getFirstAndLastName(int personId);

	public int updatePerson(int transId, int levels, int personid);

	public Object getSinglePersonId(int name);

	public List<String> getLanguageNew();

	public List<AssetOwnerShip> getOwnerShipNameFilterUrl();

	public Object[] findBaseOnPersonId(int personId);

	public List<Integer> getAllPersonIdList();
	public String getPropertyNoBasedOnPersonId(int personId);

}