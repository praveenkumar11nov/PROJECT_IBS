package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.service.GenericService;
import com.bcits.bfm.model.Family;
import com.bcits.bfm.model.Person;

public interface FamilyService extends GenericService<Family> {
	
	public int getFamilyIdByInstanceOfPersonId(int personId);
	
	public List<Person> getAllFamilyDetails();
	
	public List<String> getPersonStyle();
	
	public List<String> getPersonTitleList();
	
	public List<String> getPersonFirstName();
	
	public List<String> getPersonMiddleName();
	
	public List<String> getPersonLastName();
	
	public List<String> getLanguage();
	
	
	
	


}
