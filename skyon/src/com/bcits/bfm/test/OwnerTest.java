package com.bcits.bfm.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcits.bfm.model.Owner;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
public class OwnerTest 
{
	@Autowired
	private PersonService personService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private OwnerPropertyService ownerPropertyService;
	
	@Test
	public void testGetAllPerson() 
	{
/*		Person person = personService.find(189);*/
		
		Owner owner = ownerService.find(3);
		
		/*owner.setPersonId(189);
		owner.setCreatedBy("far");
		owner.setLastUpdatedBy("far");
		owner.setLastUpdatedDt(new Timestamp(new Date().getTime()));*/
		
		
		Set<OwnerProperty> ownerPropertySet = new HashSet<OwnerProperty>();
		OwnerProperty ownerProperty = new OwnerProperty();
		
		ownerProperty.setOwner(owner);
/*		
		ownerProperty.setPerson(owner.getPerson());*/
		
		ownerProperty.setPropertyId(33);
		ownerProperty.setVisitorRegReq("Y");
		//ownerProperty.setPropertyAquiredDate(new java.sql.Timestamp(new Date().getTime()));
		//ownerProperty.setPropertyRelingDate(new java.sql.Timestamp(new Date().getTime()));
		ownerProperty.setStatus("Yes");
		
		ownerPropertySet.add(ownerProperty);
		
		//owner.setOwnerProperty(ownerPropertySet);
		
		ownerService.save(owner);
		
	}

}
