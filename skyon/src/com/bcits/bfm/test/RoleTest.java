package com.bcits.bfm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Role;
import com.bcits.bfm.service.userManagement.RoleService;



/*
Points to remember whil writing unit tests.

1)Do not use static members in a test class
2)Don’t make unnecessary assertions
3)Make each test independent to all the others
4)Don’t unit-test configuration settings
5)Name your unit tests clearly and consistently
6)Write tests for methods that have the fewest dependencies first, and work your way up
7)All Public methods, should have appropriate unit tests
8)Aim for each unit test method to perform exactly one assertion
9)Create unit tests that target exceptions
10)Use the most appropriate assertion methods.
11)Put assertion parameters in the proper order
12)Do not print anything out in unit tests
13)Do not write your own catch blocks that exist only to fail a test*/



/*	Public methods in RoleService
 * 
	save(Role role)
	update(Role role)
	getAll()
	findById(int)
	
 	findAllWithoutInvalidDefault()
	roleNameExistence(Role, String)
	getAllActiveRoles()
	getRoleIdBasedOnRoleName(String)

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
public class RoleTest {
	/**
	 * Injecting values int dummyId from Properties file
	 * */
	@Value("${Tester.dummy.id}")
	private int dummyId;
	
	@Autowired
	private RoleService roleserviceImpl;
	
/*	@Autowired
	private Role role;*/
	
	
	/**
     * Test to insert a role with dummy values into ROLES table  
     * */
	@Test(timeout=5)
	public void testInsertValidRole() {
	
		Role role=new Role();
		
		role.setRlId(0);
		role.setRlName("unitTst");
		role.setRlDescription("unitTst");
		role.setRlStatus("Active");
		role.setCreatedBy("Admin");
		role.setLastUpdatedBy("ND");
		role.setLastUpdatedDate(new Timestamp(0));
		
		
		int count = roleserviceImpl.count();
		roleserviceImpl.save(role);
		assertEquals(count+1,roleserviceImpl.count());
	}
	
	
	/**
     * Test to Update role with dummy RoleName  
     * */
	@Test
	public void testUpdateRole(){
		Role role = roleserviceImpl.findById(dummyId);
		role.setRlName("UnitTest");
		roleserviceImpl.update(role);
		assertEquals("UnitTest",role.getRlName());
		
	}
	
	
	/**
     * Test to fetch all roles from ROLES table  
     * */
	@BeforeTransaction
	@Test
	public void testFindAllRoles(){
		
		List<Role> allroles =  roleserviceImpl.getAll();
		assertNotNull(allroles);
	}
	
	
	/**
     * Test to find role object based on ID from ROLES table  
     * */
	@BeforeTransaction
	@Test
	public void testFindRoleById(){
		
		Role role = roleserviceImpl.findById(dummyId);
		assertNotNull(role);
	}
	
	


	/**
     * Test to find all roles without invalid default  
     * */
	@Test
	public void testFindAllWithoutInvalidDefault(){
		
		List<Role> allroles = roleserviceImpl.findAllWithoutInvalidDefault();
		assertNotNull(allroles);
		assertTrue(allroles!= null);
	}
	
	/**
     * Test to find  role Based on RoleName  
     * */
	@Test
	public void testGetRoleIdBasedOnRoleName(){
		
		List<Integer> roleid  = roleserviceImpl.getRoleIdBasedOnRoleName("Admin");
		assertNotNull(roleid);
		assertTrue(roleid!= null);
	}
	
	/**
     * Test to find RoleName existence in ROLES table  
     * */
	@Test
	public void testRoleNameExistence(){
		
		assertTrue(roleserviceImpl.roleNameExistence(new Role(),"Admin"));
	}
	
	/**
     * Test to get all Active roles from ROLES table  
     * */
	@Test()
	public void testGetAllActiveRoles(){
		List<Role> roles = roleserviceImpl.getAllActiveRoles();
		assertNotNull(roles);
	}
	


}
