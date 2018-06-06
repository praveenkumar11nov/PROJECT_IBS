package com.bcits.bfm.test;

import static org.junit.Assert.assertEquals;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.novell.ldap.LDAPException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
@Transactional
public class PersonTest
{
	@Autowired
	private PersonService personService;	
	@Autowired
	private DrGroupIdService drGroupIdService;
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	
	@SuppressWarnings("deprecation")
	@Test
	public void testInsertPerson() throws LDAPException, Exception 
	{
		long count = personService.countAll(null);
		
		Person person = new Person();
		person.setCreatedBy("Admin");
		person.setDob(new java.sql.Date(System.currentTimeMillis()));
		person.setFatherName("Father");
		person.setFirstName("FirstName");
		//person.setKycCompliant("No");
		person.setLanguagesKnown("Kannada,English");
		person.setLastName("LastName");
		person.setLastUpdatedBy("Admin");
		person.setMiddleName("MiddleName");
		person.setOccupation("Software Engineer");
		person.setPersonType("Owner");
		person.setSpouseName("SpouseName");
		person.setTitle("Mr");
		person.setBloodGroup("'A' Positive (A+)");
		person.setMaritalStatus("Married");
		person.setNationality("Indian");
		byte[] b = ldapBusinessModel.getImage("bcitsadmin");
		Blob blob = Hibernate.createBlob(b);
		person.setPersonImages(blob);
		person.setPersonStatus("Inactive");
		person.setPersonStyle("Individual");
		person.setSex("Male");
		person.setWorkNature("Private");
		person.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		drGroupIdService.save(new DrGroupId(person.getCreatedBy(), person.getLastUpdatedDt()));
		person.setDrGroupId(drGroupIdService.getNextVal(person.getCreatedBy(), person.getLastUpdatedDt()));
		
		personService.save(person);
		assertEquals(count+1,personService.countAll(null));
		
	}
	
	@Test
	public void testUpdatePerson()
	{
		Person person = personService.find(6);
		person.setFirstName("firstName");
		personService.update(person);
		assertEquals("firstName",personService.find(6).getFirstName());
	}
	
}
