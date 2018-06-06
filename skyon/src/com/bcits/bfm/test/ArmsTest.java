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

import com.bcits.bfm.model.Arms;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.customerOccupancyManagement.ArmsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
@Transactional
public class ArmsTest
{
	@Autowired
	private ArmsService armsService;
	@Autowired 
	private PersonService personService;
	@Autowired
	private DrGroupIdService drGroupIdService;
	
	@Test
	@BeforeTransaction
	public void testGetAllArms() 
	{
		List<Arms> arms =  armsService.findAllArmsBasedOnPersonID(6);
		assertNotNull(arms);
	}
	
	@Test
	public void testInsertArms()
	{
		long count = armsService.countAll(null);
		
		Arms arms = new Arms();
		arms.setArmMake("Make");
		arms.setCreatedBy("Admin");
		arms.setIssuingAuthority("Authority");
		arms.setLastUpdatedBy("Admin");
		arms.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		arms.setLicenceNo("Licence123");
		arms.setLicenceValidity(new java.sql.Date(System.currentTimeMillis()));arms.setNoOfRounds(100);
		arms.setTypeOfArm("Pistol");
		drGroupIdService.save(new DrGroupId(arms.getCreatedBy(), arms.getLastUpdatedDt()));
		arms.setDrGroupId(drGroupIdService.getNextVal(arms.getCreatedBy(), arms.getLastUpdatedDt()));
		
		Person person = personService.find(6);
		Set<Arms> armsSet = person.getArms();
		
		armsSet.add(arms);
		person.setArms(armsSet);
		
		personService.update(person);
		assertEquals(count+1,armsService.countAll(null));
		
	}
	
	@Test
	public void testUpdateArms()
	{
		Arms arms = armsService.find(5);
		arms.setTypeOfArm("Pistol");
		armsService.update(arms);
		assertEquals("Pistol",armsService.find(5).getTypeOfArm());
	}
}
