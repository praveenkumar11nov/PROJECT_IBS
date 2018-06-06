package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.service.GenericService;

public interface TenantSevice extends GenericService<Tenant>  {
	
	public List<String> getPersonStyle();
	
	public List<String> getPersonTitleList();
	
	public List<String> getPersonFirstName();
	
	public List<String> getPersonMiddleName();
	
	public List<String> getPersonLastName();
	
	public List<String> getLanguage();
	
	public List<Person> getAllTenantDetails();
	
	public int getTenantIdByInstanceOfPersonId(int personId);

	public Integer getData(int propertyId);
	
	public List<String> getContactDetailsByPersonId(int propertyId);

	public String getTenantStatusByPropertyId(int propertyId);
	
	public String getTenantFirstNameByPropertyId(int propertyId);

}
