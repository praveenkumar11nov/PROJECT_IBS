package com.bcits.bfm.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Designation;
import com.bcits.bfm.service.userManagement.DesignationService;


/*	Public methods in DesignationService
 * 
 get(int)
 getAll()
 saveList(List<Designation>)
 updateList(List<Designation>)
 delete(List<Designation>)
 setDesignationStatus(int, String, HttpServletResponse, MessageSource, Locale)
 getDesignationDetails(int)
 getDesignationIdBasedOnDesignationName(String)
 getAllDesignationRequiredFields()
 getDesignationList(List)
 getDesignationObject(Map<String, Object>, String, Designation)
 getDistinctDepartments()
 checkDesignationUnique(Designation, String)
 setAllPrevDepartmentsBasedOnDeptId(int)
 getAllActiveDesignations()
 geteDesignationsNameForFilter()
 getDesignationNameBasedOnId(int)

 *
 */

/**
 *@RunWith(SpringJUnit4ClassRunner.class) means, tests are going to be able to get hold of instantiated beans as defined in the Spring context files
 *@ContextConfiguration tells the JUnit where to get the context files containing the bean definitions
 *@Transactional declare either at the class or method level for your tests, it performs Rollback for the transactions.
 *@BeforeTransaction  use this for non transactional methods, findAll() and findById() Transaction is not required.
 *  
 * */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
@Transactional
public class DesignationTest 
{
	@Autowired
	private DesignationService designationServiceImpl;
	
	@Value("${Tester.dummy.desg_id}")
	private int desg_Id;
	
	@Value("${Tester.dummy.dept_Id}")
	private int dept_Id;
	
	@Value("${Tester.dummy.desg_name}")
	String desg_name;
	
		
	DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date dt = new Date();
	Calendar cal = Calendar.getInstance();
	
	@BeforeTransaction
	@Test
	public void testGetId()
	{
		
	   Designation designation = designationServiceImpl.get(desg_Id);
		assertNotNull(designation);
	}
	
	@BeforeTransaction
	@Test
	public void testGetAll()
	{
		List<Designation> designations = designationServiceImpl.getAll();
		assertNotNull(designations);
	}
	
	@Test
	public void testSaveList()
	{
		List<Designation> designations = new ArrayList<Designation>();
		
		Designation designation = new Designation();
		designation.setDn_Name("Uint Test");
		designation.setDn_Description("JUnit testing");
		designation.setCreated_By("Admin");
		designation.setDr_Status("Active");
		designation.setLast_Updated_By("Admin");
		designation.setLastUpdatedDt(new Timestamp(0));
		designation.setDept_Id(dept_Id);
		
		designations.add(designation);
		designationServiceImpl.saveList(designations);
	}
	
	@Test
	public void testUpdateList()
	{
        List<Designation> designations = new ArrayList<Designation>();
		
		Designation designation = new Designation();
		designation.setDept_Id(0);
		designation.setDn_Name("Uint Test");
		designation.setDn_Description("JUnit testing");
		designation.setCreated_By("Admin");
		designation.setDr_Status("Active");
		designation.setLast_Updated_By("Admin");
		designation.setLastUpdatedDt(new Timestamp(0));
		designation.setDept_Id(dept_Id);
		
		designations.add(designation);
		designationServiceImpl.updateList(designations);;
	}
	
	@Test
	public void testDelete()
	{
		 List<Designation> designations = new ArrayList<Designation>();
			
			Designation designation = new Designation();
			designation.setDept_Id(0);
			designation.setDn_Name("Uint Test");
			designation.setDn_Description("JUnit testing");
			designation.setCreated_By("Admin");
			designation.setDr_Status("Active");
			designation.setLast_Updated_By("Admin");
			designation.setLastUpdatedDt(new Timestamp(0));
			designation.setDept_Id(dept_Id);
			
			designations.add(designation);
			designationServiceImpl.delete(designations);
	}
	
	@Test
	public void testSetDesignationStatus()
	{
		MockHttpServletResponse response = new MockHttpServletResponse();
		MessageSource messageSource = null;
		Locale locale = null;
		designationServiceImpl.setDesignationStatus(desg_Id, "activate", response, messageSource, locale);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDesignationDetails()
	{
		List<Designation> designations = designationServiceImpl.getDesignationDetails(desg_Id);
		assertNotNull(designations);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDesignationIdBasedOnDesignationName()
	{
		List<Integer> integers = designationServiceImpl.getDesignationIdBasedOnDesignationName(desg_name);
		assertNotNull(integers);
	}
	
	@SuppressWarnings("rawtypes")
	@BeforeTransaction
	@Test
	public void testGetAllDesignationRequiredFields()
	{
		List list = designationServiceImpl.getAllDesignationRequiredFields();
		assertNotNull(list);
	}
	
	@SuppressWarnings("rawtypes")
	@BeforeTransaction
	@Test
	public void testGetDesignationList()
	{
		     List designations = new ArrayList();
			
			Designation designation = new Designation();
			designation.setDept_Id(0);
			designation.setDn_Name("Uint Test");
			designation.setDn_Description("JUnit testing");
			designation.setCreated_By("Admin");
			designation.setDr_Status("Active");
			designation.setLast_Updated_By("Admin");
			designation.setLastUpdatedDt(new Timestamp(0));
			designation.setDept_Id(dept_Id);
			
			List list = designationServiceImpl.getDesignationList(designations);
			assertNotNull(list);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDistinctDepartments()
	{
		List<?> list = designationServiceImpl.getDistinctDepartments();
		assertNotNull(list);
	}

	@Test
	public void testCheckDesignationUnique()
	{
		assertTrue(designationServiceImpl.checkDesignationUnique(new Designation(), "update"));
	}
	
	@Test
	public void testSetAllPrevDepartmentsBasedOnDeptId()
	{
		int i = designationServiceImpl.setAllPrevDepartmentsBasedOnDeptId(dept_Id);
		assertNotNull(i);
	}
	@BeforeTransaction
	@Test
	public void testGetAllActiveDesignations()
	{
		List<Designation> designations = designationServiceImpl.getAllActiveDesignations();
		assertNotNull(designations);
	}
	
	@BeforeTransaction
	@Test
	public void testGeteDesignationsNameForFilter()
	{
		List<Designation> designations = designationServiceImpl.geteDesignationsNameForFilter();
		assertNotNull(designations);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDesignationNameBasedOnId()
	{
		String string = designationServiceImpl.getDesignationNameBasedOnId(desg_Id);
		assertNotNull(string);
	}
	
}
