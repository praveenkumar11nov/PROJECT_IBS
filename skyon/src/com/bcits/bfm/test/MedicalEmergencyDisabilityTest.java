package com.bcits.bfm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.MedicalEmergencyDisability;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.MedicalEmergencyDisabilityService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
@Transactional
public class MedicalEmergencyDisabilityTest
{
	@Autowired
	private MedicalEmergencyDisabilityService medicalEmergencyDisabilityService;
	@Autowired 
	private PersonService personService;
	
	@Test
	@BeforeTransaction
	public void testGetAllMedicalEmergencyDisability() 
	{
		List<MedicalEmergencyDisability> emergencyDisabilities =  medicalEmergencyDisabilityService.findAllMedicalEmergencyDisabilityBasedOnPersonID(6);
		assertNotNull(emergencyDisabilities);
	}
	
	@Test
	public void testInsertMedicalEmergencyDisability()
	{
		long count = medicalEmergencyDisabilityService.countAll(null);
		
		MedicalEmergencyDisability medicalEmergencyDisability = new MedicalEmergencyDisability();
		medicalEmergencyDisability.setCreatedBy("Admin");
		medicalEmergencyDisability.setDescription("Description");
		medicalEmergencyDisability.setDisabilityType("Blood Presure");
		medicalEmergencyDisability.setLastUpdatedBy("Admin");
		medicalEmergencyDisability.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		medicalEmergencyDisability.setMeCategory("Emergency");
		medicalEmergencyDisability.setMeHospitalAddress("Address");
		medicalEmergencyDisability.setMeHospitalContact("9999999999, 7894561230");
		medicalEmergencyDisability.setMeHospitalName("Hospital Name");
		
		Person person = personService.find(6);
		Set<MedicalEmergencyDisability> medicalEmergencyDisabilities = person.getMedicalEmergencyDisabilities();
		
		medicalEmergencyDisabilities.add(medicalEmergencyDisability);
		person.setMedicalEmergencyDisabilities(medicalEmergencyDisabilities);
		
		personService.update(person);
		assertEquals(count+1,medicalEmergencyDisabilityService.countAll(null));
		
	}
	
	@Test
	public void testUpdateMedicalEmergencyDisability()
	{
		MedicalEmergencyDisability medicalEmergencyDisability = medicalEmergencyDisabilityService.find(2);
		medicalEmergencyDisability.setMeCategory("Disability");
		medicalEmergencyDisabilityService.update(medicalEmergencyDisability);
		assertEquals("Disability",medicalEmergencyDisabilityService.find(2).getMeCategory());
	}
}
