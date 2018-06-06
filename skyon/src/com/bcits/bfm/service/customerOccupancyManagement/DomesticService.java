package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.Domestic;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.GenericService;

public interface DomesticService extends GenericService<Domestic>{
	
	public List<String> getPersonTitleList();
	
	public List<String> getPersonFirstName();
	
	public List<String> getPersonMiddleName();
	
	public List<String> getPersonLastName();
	
	public List<String> getLanguage();

	List<String> getPersonStyle();
	
	public List<Person> getAllDomesticDetails();
	
	public int getDomesticIdByInstanceOfPersonId(int personId);

}
