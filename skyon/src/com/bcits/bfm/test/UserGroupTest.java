package com.bcits.bfm.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.userManagement.UserGroupService;

/*
	Public methods in UserGroupService
 * 
findUsersIdName()
findById(int)
checkUser(int, int)
AddUser(UserGroup)
DeleteUser(int, int)
UpdateUser(String, int, int)
getActiveGroups()
SelectGroup(int)
getGroupNamelist(int)
getUserGroupInstanceBasedOnUserIdAndGroupId(int, int)
setAllPrevGroupsBasedOnGroupId(int)
removeAllRedundantUserGroupRecords()
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
public class UserGroupTest 
{
	@Autowired
	private UserGroupService userGroupService;
	
	
	@Value("${Tester.dummy.UG_GR_ID}")
	private int UG_GR_ID;
	
	@Value("${Tester.dummy.UG_UR_ID}")
	private int UG_UR_ID;
	
	
	DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date dt = new Date();
	Calendar cal = Calendar.getInstance();
	
	
	@Test
	public void testFindUsersIdName()
	{
		List<Users> users = userGroupService.findUsersIdName();
		assertNotNull(users);
	}
	
	@Test
	public void testFindUserGroupBId()
	{
		List<UserGroup> userGroups = userGroupService.findById(UG_GR_ID);
		assertNotNull(userGroups);
	}
	
	@Test
	public void testCheckUser()
	{
		String user =  userGroupService.checkUser(UG_UR_ID, UG_GR_ID);
		assertNotNull(user);
	}
	
	@Test
	public void testAddUser()
	{
		UserGroup ug = new UserGroup();
		ug.setUG_ID(0);
		ug.setUG_UR_ID(11531);
		ug.setUG_GR_ID(403);
		ug.setCREATED_BY("JUNITS");
		ug.setLAST_UPDATED_BY("JUNITS");
		ug.setLAST_UPDATED_DT(new Timestamp(0));
		userGroupService.AddUser(ug);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteUser()
	{
		List<Groups> ug = userGroupService.SelectGroup(90);
		if(ug.isEmpty())
		{
			Assert.assertTrue("Group Id Not found", false);
		}
		else
		{
			Assert.assertTrue("Group Id found",true);
		}		
	}
	
/*	@Test
	public void testUpdateUser()
	{
           String username = userGroupService.UpdateUser("Active", 45, 89);	
	}
	*/
	@BeforeTransaction
	@Test
	public void testGetActiveGroups()
	{
		List<Groups> groups = userGroupService.getActiveGroups();
		assertNotNull(groups);
	}
	@BeforeTransaction
	@Test
	public void testSelectGroup()
	{
		@SuppressWarnings("rawtypes")
		List list = userGroupService.SelectGroup(UG_GR_ID);
		assertNotNull(list);
	}
	@BeforeTransaction
	@Test
	public void testGetGroupNamelist()
	{
		List<UserGroup> userGroups = userGroupService.getGroupNamelist(UG_UR_ID);
		assertNotNull(userGroups);
	}
	@BeforeTransaction
	@Test
	public void testGetUserGroupInstanceBasedOnUserIdAndGroupId()
	{
		List<UserGroup> userGroups = userGroupService.getUserGroupInstanceBasedOnUserIdAndGroupId(UG_UR_ID, UG_GR_ID);
		assertNotNull(userGroups);
	}
	
	@Test
	public void testSetAllPrevGroupsBasedOnGroupId()
	{
		int i = userGroupService.setAllPrevGroupsBasedOnGroupId(UG_GR_ID);
		assertNotNull(i);
	}
/*
	@Test
	public void testRemoveAllRedundantUserGroupRecords()
	{
		userGroupService.removeAllRedundantUserGroupRecords();
	}*/
}
