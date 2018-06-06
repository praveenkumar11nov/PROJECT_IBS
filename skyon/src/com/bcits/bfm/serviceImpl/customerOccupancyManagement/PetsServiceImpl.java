package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Pets;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.customerOccupancyManagement.PetsService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class PetsServiceImpl extends GenericServiceImpl<Pets> implements PetsService
{

	@Autowired
	private PropertyService propertyService;
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.PetsService#findAll()
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> findAllPetsRequiredFields() 
	{
		List<?> petsRequiredFieldsList = entityManager.createNamedQuery("Pets.findAllPetsRequiredFields").getResultList();
		return getAllPetsRequiredFields(petsRequiredFieldsList);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Pets getPetObject(Map<String, Object> map, String type, Pets pets)
	{
		if(type == "update")
		{
			pets.setPetId((Integer) map.get("petId"));
			pets.setCreatedBy((String) map.get("createdBy"));
			pets.setPetStatus(((String)map.get("petStatus")));
			pets.setPropertyId((Integer)map.get("property_No"));
		
		}
		
		else if(type == "save")
		{
			pets.setCreatedBy((String) SessionData.getUserDetails().get("userID"));	
     		pets.setPetStatus("Inactive");
			
			

			if((map.get("property_No") instanceof java.lang.Integer)){ 
					pets.setPropertyId((Integer)map.get("property_No"));
			}
		}

		pets.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		pets.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		
		pets.setBlockName((String)map.get("blockName"));
		pets.setPetType((String)map.get("petType"));
		pets.setPetName((String)map.get("petName"));
		pets.setDrGroupId((Integer) map.get("drGroupId"));
		pets.setBreedName((String)map.get("breedName"));
		pets.setPetSize((String)map.get("petSize"));
		pets.setPetColor((String)map.get("petColor"));
		pets.setPetAge((Integer)map.get("petAge"));
		pets.setPetSex((String)map.get("petSex"));
		if(!(((String)map.get("dateOfVaccination")=="")||(String)map.get("dateOfVaccination")==null)){
			DateTimeCalender dateTimeCalender = new DateTimeCalender();		
		    pets.setLastVaccinationDate(dateTimeCalender.getDate1((String)map.get("dateOfVaccination")));
		}
		pets.setVeterinarianName((String)map.get("veterinarianName"));
		pets.setVeterinarianAddress((String)map.get("veterinarianAddress"));
		pets.setTypesOfVaccination((String)map.get("typesOfVaccination"));
		pets.setEmergencyContact((String)map.get("emergencyCareNumber"));

		return pets;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int getPetIdBasedOnCreatedByAndLastUpdatedDt(String createdBy,
			Timestamp lastUpdatedDt)
	{
		return entityManager.createNamedQuery("Pets.getPetIdBasedOnCreatedByAndLastUpdatedDt", Integer.class).setParameter("createdBy", createdBy).setParameter("lastUpdatedDt", lastUpdatedDt).getSingleResult();		
	}
	
	@Override
	public void setPetStatus(int petId, String operation, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Pets.setPetStatus").setParameter("petStatus", "Active").setParameter("petId", petId).executeUpdate();
				out.write("Pet Activated");
			}
			else
			{
				entityManager.createNamedQuery("Pets.setPetStatus").setParameter("petStatus", "Inactive").setParameter("petId", petId).executeUpdate();
				out.write("Pet Deactivated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	private List<?> getAllPetsRequiredFields(List<?> petsRequiredFieldsList)
	{
		List<Map<String, Object>> getAllPetsList = new ArrayList<Map<String, Object>>();

		Map<String, Object> petsMap = null;

		for (Iterator<?> i = petsRequiredFieldsList.iterator(); i.hasNext();) 
		{
			petsMap = new HashMap<String, Object>();

			final Object[] values = (Object[]) i.next();

			petsMap.put("petId", (Integer)values[0]);
			petsMap.put("petName", (String)values[1]);
			petsMap.put("propertyId", (Integer)values[2]);
			petsMap.put("drGroupId", (Integer)values[3]);
			petsMap.put("petType", (String)values[4]);
			petsMap.put("petStatus", (String)values[5]);
			petsMap.put("createdBy", (String)values[6]);
			petsMap.put("lastUpdatedBy", (String)values[7]);
			petsMap.put("lastUpdatedDt", (Timestamp)values[8]);
			petsMap.put("propertyNo", (String)values[9]);
			petsMap.put("petSize",(String)values[10]);
			petsMap.put("blockName",(String)values[11]);
			petsMap.put("property_No", (Integer)values[12]);
			petsMap.put("breedName", (String)values[13]);
			petsMap.put("petColor",(String)values[14]);			
			petsMap.put("petAge", (Integer)values[15]);			
			petsMap.put("petSex",(String)values[16]);
			petsMap.put("typesOfVaccination", (String)values[17]);			
			petsMap.put("veterinarianName", (String)values[18]);
			petsMap.put("veterinarianAddress", (String)values[19]);
			petsMap.put("dateOfVaccination", (java.sql.Date)values[20]);
			petsMap.put("emergencyCareNumber", (String)values[21]);
			
			String str="";
			if((String)values[23]!=null){
				str=(String)values[23];
			}
			
			petsMap.put("personName", (String)values[22]+" "+str);
			
			
			getAllPetsList.add(petsMap);
		}
		return getAllPetsList;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> getPetsFullDetails(Pets pets) {

		List<Map<String, Object>> personList = new ArrayList<Map<String, Object>>();

		Map<String, Object> petsMap = new HashMap<String, Object>();

		petsMap.put("petId", pets.getPetId());
		petsMap.put("petName", pets.getPetName());
		petsMap.put("propertyId", pets.getPropertyId());
		petsMap.put("drGroupId", pets.getDrGroupId());
		petsMap.put("petType", pets.getPetType());
		petsMap.put("petStatus", pets.getPetStatus());
		petsMap.put("createdBy", pets.getCreatedBy());
		petsMap.put("lastUpdatedBy", pets.getLastUpdatedBy());
		petsMap.put("lastUpdatedDt", pets.getLastUpdatedDt());
		petsMap.put("propertyNo", pets.getPropertyNo());
		petsMap.put("blockName", pets.getBlockName());
		petsMap.put("property_No", pets.getPropertyId());
		petsMap.put("breedName", pets.getBreedName());
		petsMap.put("petColor",pets.getPetColor());			
		petsMap.put("petAge", pets.getPetAge());			
		petsMap.put("petSex",pets.getPetSex());	
		petsMap.put("typesOfVaccination", pets.getTypesOfVaccination());			
		petsMap.put("veterinarianName", pets.getVeterinarianName());
		petsMap.put("veterinarianAddress", pets.getVeterinarianAddress());
		petsMap.put("dateOfVaccination", pets.getLastVaccinationDate());
		petsMap.put("dateOfVaccination", pets.getLastVaccinationDate());
		petsMap.put("emergencyCareNumber", pets.getEmergencyContact());
		
		String str="";
		if(pets.getPerson().getLastName()!=null){
			str=pets.getPerson().getLastName();
		}
		
		petsMap.put("personName", pets.getPerson().getFirstName()+" "+str);

		personList.add(petsMap);

		return personList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getPropertyNo()
	{
		return entityManager.createNamedQuery("Pets.getPropertyNo").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Set<String> getAllPetName(){
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("Pets.getAllPetName").getResultList());
		return set;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Set<String> getAllBlockNames(){
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("Pets.getAllBlockNames").getResultList());
		return set;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPetType()
	{
		return entityManager.createNamedQuery("Pets.getAllPetType").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPropertyNumbers()
	{
		return entityManager.createNamedQuery("Pets.getAllPropertyNumbers").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPetSize()
	{
		return entityManager.createNamedQuery("Pets.getAllPetSize").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Set<String> getAllPetBreed()
	{
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("Pets.getAllPetBreed").getResultList());
		return set;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Set<String> getAllPetColor()
	{
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("Pets.getAllPetColor").getResultList());
		return set;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getAllPetAge()
	{
		return entityManager.createNamedQuery("Pets.getAllPetAge").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPetSex()
	{
		return entityManager.createNamedQuery("Pets.getAllPetSex").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllUpdatedByNames()	{
		return entityManager.createNamedQuery("Pets.getAllUpdatedByNames").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllCreatedByNames()	{
		return entityManager.createNamedQuery("Pets.getAllCreatedByNames").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Set<String> getAllVeterianame()	{
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("Pets.getAllVeterianame").getResultList());
		return set;
	}
	
	public int count() {
		return getAll().size();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Pets> getAll() {
		return entityManager.createNamedQuery("Pet.findAll").getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public Set<String> getAllEmergencyContact() {
		Set<String> set=new HashSet<String>(entityManager.createNamedQuery("Pets.getAllEmergencyContact").getResultList());
		return set;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<OwnerProperty> findAllPropertyPersonOwnerList() {
		return entityManager.createNamedQuery("Pets.findAllPropertyPersonOwnerList").getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<TenantProperty> findAllPropertyPersonTenantList() {
		return entityManager.createNamedQuery("Pets.findAllPropertyPersonTenantList").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getPersonListForFileter() {
		return entityManager.createNamedQuery("Pets.getPersonListForFileter").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllPets() {

		return entityManager.createNamedQuery("Pets.findAllPetsRequiredFields").getResultList();
		
	}

	
}
