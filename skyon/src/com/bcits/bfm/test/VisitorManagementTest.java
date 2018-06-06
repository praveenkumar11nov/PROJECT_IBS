package com.bcits.bfm.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcits.bfm.model.Visitor;
//import com.bcits.bfm.model.VisitorParking;
import com.bcits.bfm.model.VisitorVisits;
//import com.bcits.bfm.service.facilityManagement.VisitorParkingService;
import com.bcits.bfm.service.facilityManagement.VisitorService;
import com.bcits.bfm.service.facilityManagement.VisitorVisitsService;

/**
 * @RunWith(SpringJUnit4ClassRunner.class) means, tests are going to be able to
 *                                         get hold of instantiated beans as
 *                                         defined in the Spring context files
 * @ContextConfiguration tells the JUnit where to get the context files
 *                       containing the bean definitions
 * @Transactional declare either at the class or method level for your tests, it
 *                performs Rollback for the transactions.
 * @BeforeTransaction use this for non transactional methods, findAll() and
 *                    findById() Transaction is not required.
 * 
 * */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
public class VisitorManagementTest {

	@Autowired
	private VisitorService visitorService;

	/*@Autowired
	private VisitorParkingService visitorParkingService;*/

	@Autowired
	private VisitorVisitsService visitorVisitsService;

	/**
	 * Test to insert a visitor record into Visitor_Master,Visitor_Visits and
	 * Visitor_Parking tables
	 * */

	@SuppressWarnings("unused")
	@Test
	public void testInsertVisitorRecord() {
		Date date = new Date();

		

		
		
		Visitor visitor = new Visitor();
		VisitorVisits visitorVisits = new VisitorVisits();
		//VisitorParking visitorParking = new VisitorParking();

		visitor.setVmName("naveen sharma");
		visitor.setVmContactNo("9999776655");
		visitor.setVmFrom("Newyork");
		visitor.setVmLastUpdatedDt(new Timestamp(date.getTime()));
		visitor.setVmCreatedBy("ravi");
		visitor.setVmLastUpdatedBy("ravi");
		visitor.setVmId(0);
/*int visitorMaster_count=visitorService.count_VisitorMaster_count();
		visitorService.save(visitor);
		
		
		assertEquals(visitorMaster_count+1, visitorService.count_VisitorMaster_count());*/
		List<Integer> visitorId = visitorService.getVisitorId("naveen sharma",
				"9999776655");

		int id = visitorId.get(0);

		/*visitorVisits.setVvId(0);
		visitorVisits.setVmId(id);
		visitorVisits.setVmContactNo("9999776655");
		visitorVisits.setAcId(5);
		visitorVisits.setCategory("F&B");
		visitorVisits.setGender("male");
		visitorVisits.setPropertyId(230);
		visitorVisits.setCreatedBy("ravi");
		visitorVisits.setLastUpdatedBy("ravi");
		visitorVisits.setLastupdatedDt(new Timestamp(date.getTime()));
		visitorVisits.setVInDt(new Timestamp(date.getTime()));
		visitorVisits.setVPurpose("meeting");
		visitorVisits.setVvstatus("IN");*/
	//	int vv_visits_count = visitorVisitsService.count_visitorVisits();

		/*visitorVisitsService.save(visitorVisits);
		assertEquals(vv_visits_count + 1,
				visitorVisitsService.count_visitorVisits());*/

		/*visitorParking.setVpId(0);
		visitorParking.setVmId(id);
		visitorParking.setVmContactNo("9999776655");
		visitorParking.setPsId(603);
		visitorParking.setVpExpectedHours(3);
		visitorParking.setVpStatus("Required");
		visitorParking.setCreatedBy("ravi");
		visitorParking.setLastUpdatedBy("ravi");
		visitorParking.setLastUpdatedDt(new Timestamp(date.getTime()));
*/
		///int vp_parking_count = visitorParkingService.count_VisitorParking();
		/*visitorParkingService.save(visitorParking);
		assertEquals(vp_parking_count + 1,
				visitorParkingService.count_VisitorParking());*/

	}
	
	
	
	/**
	 * Test to update a visitor record into Visitor_Master,Visitor_Visits and
	 * Visitor_Parking tables
	 * */
	@SuppressWarnings("unused")
	@Test
	public void testupdateVisitorRecord(){
		
		Date date=new Date();
		
		
		//Visitor visitor=new Visitor(1559, "naveen sharma", "Newyork", "9999776655", 0, "ravi","ravi", new Timestamp(date.getTime()));
		//visitorService.update(visitor);
		//assertEquals("naveen sharma", visitor.getVmName());
	/*	
		VisitorVisits visitorVisits=new VisitorVisits(1256, 1559,"9999776655", 5, 230, "cricket match practice", new Timestamp(date.getTime()), new Timestamp(date.getTime()), "ravi", "ravi", "OUT", new Timestamp(date.getTime()), "male", "F&B");
		visitorVisitsService.update(visitorVisits);
		assertEquals("OUT", visitorVisits.getVvstatus());
		
		VisitorParking visitorParking=new VisitorParking(751, 1559, "9999776655", 5, "NotRequired", 603, "ravi", "ravi", new Timestamp(date.getTime()), null);
		visitorParkingService.update(visitorParking);*/
		//assertEquals("NotRequired", visitorParking.getVpStatus());
		
	}
	
	
	/**
	 * Test to find  a visitor record into Visitor_Master,Visitor_Visits and
	 *  tables based on Id
	 * */
	@Test
	public void testFindVisitorVisitbasedOnId(){
		/*int vmId=1559;
		List<VisitorVisits> vvlist=visitorVisitsService.findVisitorVisitsDetailsBasedOnId(vmId);
		assertNotNull(vvlist);*/
		
	}
	
	
	/**
	 * Test to Read  all visitor record from Visitor_Master,Visitor_Visits and Visitor_Parking
	 *  tables 
	 * */
@Test
public void testReadAllVisitorRecord(){
	
	/*List visitorlist=visitorParkingService.getVisitorAllRecordBasedOnId();
	assertNotNull(visitorlist);*/
}



/**
 * Test to read a visitor record from  Visitor_Master,Visitor_Visits  tables
 * */
@Test
public void testVisitorVisit()
{
	/*List visitorVisitList=visitorVisitsService.findAllVisitorVisits();
	assertNotNull(visitorVisitList);
	*/
}
	

/**
 * Test to update a Visitor Visits record In  Visitor_Visits  table
 * */

@SuppressWarnings("unused")
@Test
public void testUpdateVisitorVisits(){
	Date date=new Date();
	//VisitorVisits visitorVisits=new VisitorVisits(1256, 1558, "9999776655", 5, 230, "cricket match practice", new Timestamp(date.getTime()), new Timestamp(date.getTime()), "ravi", "ravi", "OUT", new Timestamp(date.getTime()), "male", "F&B");
	
}




}
