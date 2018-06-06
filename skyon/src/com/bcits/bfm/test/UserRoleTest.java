package com.bcits.bfm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.service.userManagement.UserRoleService;

/*	Public methods in UserRoleService
 * 
 findById(int)
 getRlId(int)
 checkUser(int, int)
 AddUser(UserRole)
 DeleteUser(int, int)
 SelectRole(int)
 getRoleNamelist(int)
 getUserRoleInstanceBasedOnUserIdAndRoleId(int, int)
 getActiveUsers()
 setAllPrevRolesBasedOnRoleId(int)
 removeAllRedundantUserRoleRecords()

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
public class UserRoleTest 
{
   
	@Autowired
	private UserRoleService userRoleService;
	
	@Value("${Tester.dummy.URO_ID}")
	private int URO_ID;
	
	@Value("${Tester.dummy.UR_ID}")
	private int UR_ID;
	
	@Value("${Tester.dummy.URO_UR_ID}")
	private int URO_UR_ID;
	
	@Value("${Tester.dummy.URO_RL_ID}")
	private int URO_RL_ID;

	@Value("${Tester.dummy.RL_ID}")
	private int RL_ID;

	
	@Test
	public void testFindById()
	{
		UserRole userRole = userRoleService.findById(URO_ID);
		assertEquals(URO_ID,userRole.getUro_Id());
	}
	@BeforeTransaction
	@Test
	public void testGetRlId() {
		List<UserRole> userRoles = userRoleService.getRlId(UR_ID);
		assertNotNull(userRoles);
	}

	@Test
	public void testCheckUser()
	{
		String string = userRoleService.checkUser(URO_UR_ID, URO_RL_ID);
		assertNotNull(string);
	}
	
	@Test
	public void testAddUser()
	{
		UserRole ug = new UserRole();
		userRoleService.AddUser(ug);
		
	}
	
	@Test
	public void testDeleteUser()
	{
		userRoleService.DeleteUser(URO_UR_ID, URO_RL_ID);
	}
	@BeforeTransaction
	@Test
	public void testSelectRole()
	{
		@SuppressWarnings("rawtypes")
		List list = userRoleService.SelectRole(URO_RL_ID);
		assertNotNull(list);
	}
	@BeforeTransaction
	@Test
	public void testGetRoleNamelist()
	{
		List<UserRole> userRoles = userRoleService.getRoleNamelist(URO_UR_ID);
		assertNotNull(userRoles);
	}
	@BeforeTransaction
	@Test
	public void testGetUserRoleInstanceBasedOnUserIdAndRoleId()
	{
		List<UserRole> userRoles = userRoleService.getUserRoleInstanceBasedOnUserIdAndRoleId(URO_UR_ID, URO_RL_ID);
		assertNotNull(userRoles);
	}
	@BeforeTransaction
	@Test
	public void testGetActiveUsers()
	{
		List<Role> roles = userRoleService.getActiveUsers();
		assertNotNull(roles);
	}
	
	@Test
	public void testSetAllPrevRolesBasedOnRoleId()
	{
		int i = userRoleService.setAllPrevRolesBasedOnRoleId(RL_ID);
		assertNotNull(i);
		/*assertEquals(RL_ID, i);*/
	}
	
/*	@Test
	public void testRemoveAllRedundantUserRoleRecords()
	{
		userRoleService.removeAllRedundantUserRoleRecords();
	}*/
}
