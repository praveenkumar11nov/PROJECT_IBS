package com.bcits.bfm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
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

import com.bcits.bfm.model.Department;
import com.bcits.bfm.service.userManagement.DepartmentService;


/*	Public methods in DepartmentService
 * 
get(int)
getAll()
save(Department)
getDepartmentObject(Map<String, Object>, String, Department)
remove(int)
checkDepartmentUnique(Department, String)
setDepartmentStatus(int, String, HttpServletResponse, MessageSource, Locale)
getDepartmentDetails(int)
getDepartmentIdBasedOnDepartmentName(String)
getDepartmentIdBasedOnName(String)
findAllWithoutInvalidDefault()
getAllActiveDepartments()
getDepartmentsNameForFilter()
getDepartmentNameBasedOnId(int)

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
public class DepartmentTest 
{

	@Autowired
	private DepartmentService departmentServiceImpl;
	
	@Value("${Tester.dummy.dept_Id}")
	private int dept_Id;
	
	@Value("${Tester.dummy.dept_Id_delete}")
	private int dept_id_delete;
	
	@Value("${Tester.dummy.dept_name}")
	private String dept_name;

	@BeforeTransaction
	@Test
	public void testDepartementById()
	{
		Department department = departmentServiceImpl.get(dept_Id);
		assertNotNull(department);
	}
	
	@BeforeTransaction
	@Test
	public void testGetAllDepartements()
	{
		List<Department> alldepartment =  departmentServiceImpl.getAll();
		assertNotNull(alldepartment);
		
	}
	
	@Test
	public void testSaveDepartement()
	{
        Department department =new Department();
        department.setDept_Name("UnitTest");
		department.setDept_Desc("Unit testing Departemensts");
	    department.setDept_Status("Active");
		department.setCreatedBy("Admin");
		department.setLastUpdatedBy("Admin");
		department.setLastUpdatedDt(new Timestamp(0));
		
		int count = departmentServiceImpl.getAll().size();
		departmentServiceImpl.save(department);
		assertEquals(count+1,departmentServiceImpl.getAll().size());
	}
	
/*	@Test
	public void testGetDepartmentObject() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dept_Id", 1342);
		map.put("dept_Name", "Unit Test Departement");
		map.put("dept_Desc", "Unit Test Departement");
		map.put("dept_Status", "Inactive");
		map.put("createdBy", "Admin");

		Department department = new Department();
		department.setDept_Id(1342);
		department.setDept_Name("UnitTest");
		department.setDept_Desc("Unit testing Departemensts");
		department.setDept_Status("Active");
		department.setCreatedBy("Admin");
		department.setLastUpdatedBy("Admin");
		department.setLastUpdatedDt(new Timestamp(0));

		Department department1 = departmentServiceImpl.getDepartmentObject(map,"update", department);
		assertNotNull(department1);
	}*/
	
	@Test
	public void removeDepartementById()
	{
		departmentServiceImpl.remove(dept_id_delete);
		Department department = departmentServiceImpl.get(dept_id_delete);
		assertNull(department);
	}
	
	@Test
	public void testCheckDepartmentUnique()
	{
		assertTrue(departmentServiceImpl.checkDepartmentUnique(new Department(),"update"));
	}
	
	@Test
	public void testSetDepartmentStatus() {
		MockHttpServletResponse response = new MockHttpServletResponse();
		MessageSource messageSource = null;
		Locale locale = null;
		departmentServiceImpl.setDepartmentStatus(dept_Id, "activate",response, messageSource, locale);
	}
	
	@Test
	public void testGetDepartmentDetails()
	{
		List<Department> alldepartment =  departmentServiceImpl.getDepartmentDetails(dept_Id);
		assertNotNull(alldepartment);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDepartmentIdBasedOnDepartmentName()
	{ 
		List<Integer> integers = departmentServiceImpl.getDepartmentIdBasedOnDepartmentName(dept_name);
		assertNotNull(integers);
	}
	
/*	@Test
	public void testGetDepartmentIdBasedOnName()
	{
		int i = departmentServiceImpl.getDepartmentIdBasedOnName(dept_name);
		assertNotNull(i);
	}*/
	
	@BeforeTransaction
	@Test
	public void testFindAllWithoutInvalidDefault()
	{
		List<Department> alldepartment =  departmentServiceImpl.findAllWithoutInvalidDefault();
		assertNotNull(alldepartment);
	}
	
	@BeforeTransaction
	@Test 
	public void testGetAllActiveDepartments()
	{
		List<Department> alldepartment =  departmentServiceImpl.getAllActiveDepartments();
		assertNotNull(alldepartment);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDepartmentsNameForFilter()
	{
		List<Department> alldepartment =  departmentServiceImpl.getDepartmentsNameForFilter();
		assertNotNull(alldepartment);
	}
	
	@BeforeTransaction
	@Test
	public void testGetDepartmentNameBasedOnId()
	{
		String string = departmentServiceImpl.getDepartmentNameBasedOnId(dept_Id);
		assertNotNull(string);
	}
	
	
}
