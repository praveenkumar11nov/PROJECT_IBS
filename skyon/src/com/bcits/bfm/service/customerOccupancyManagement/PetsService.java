package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Pets;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.GenericService;

public interface PetsService extends GenericService<Pets>
{

	public List<?> findAllPetsRequiredFields();
	
	public List<Object[]> findAllPets();

	public Pets getPetObject(Map<String, Object> map, String string, Pets pets);

	public int getPetIdBasedOnCreatedByAndLastUpdatedDt(String createdBy, Timestamp lastUpdatedDt);

	List<Map<String, Object>> getPetsFullDetails(Pets pets);

	void setPetStatus(int petId, String operation, HttpServletResponse response);

	public List<Integer> getPropertyNo();

	public Set<String> getAllPetName();

	public List<String> getAllPetType();
	
	public List<String> getAllPetSize();

	List<Pets> getAll();

	List<String> getAllPropertyNumbers();

	List<Integer> getAllPetAge();

	Set<String> getAllPetColor();

	Set<String> getAllPetBreed();

	List<String> getAllPetSex();

	List<String> getAllUpdatedByNames();

	List<String> getAllCreatedByNames();

	Set<String> getAllVeterianame();

	Set<String> getAllBlockNames();
	
	Set<String> getAllEmergencyContact();

	List<OwnerProperty> findAllPropertyPersonOwnerList();

	List<TenantProperty> findAllPropertyPersonTenantList();

	List<Person> getPersonListForFileter();
}