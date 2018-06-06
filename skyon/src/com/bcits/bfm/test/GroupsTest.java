package com.bcits.bfm.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.service.userManagement.GroupsService;

/*	Public methods in GroupService
 * 
 getAll()
 count()
 getByName(String)
 checkNameExistence(Groups)
 findAll()
 findById(int)
 checkGroupUnique(Groups, String)
 getGroupObject(Map<String, Object>, String, Groups)
 AddUser(UserGroup)
 DeleteUser(int, int)
 setGroupStatus(int, String, HttpServletResponse, MessageSource, Locale)
 getGroupNames()
 findAllWithoutInvalidDefault()
 getAllActiveGroups()
 getGroupIdBasedOnGroupName(String)
 *
 */

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
@Transactional
public class GroupsTest {

	@Autowired
	private GroupsService groupsServiceImpl;

	@Value("${Tester.dummy.group_name}")
	String group_name;

	@Value("${Tester.dummy.UG_GR_ID}")
	int UG_GR_ID;

	@Value("${Tester.dummy.UG_UR_ID}")
	int UG_UR_ID;

	@Value("${Tester.dummy.group_id}")
	int group_id;

	@Test
	public void testGetAll() {
		List<Groups> groups = groupsServiceImpl.getAll();
		assertNotNull(groups);

	}

	@Test
	public void tesCount() {
		int i = groupsServiceImpl.count();
		assertNotNull(i);
	}

	@Test
	public void testGetByName() {
		List<Groups> groups = groupsServiceImpl.getByName(group_name);
		assertNotNull(groups);
	}

	@Test
	public void testCheckNameExistence() {
		List<Groups> groups = groupsServiceImpl
				.checkNameExistence(new Groups());
		assertNotNull(groups);
	}

	/*
	 * @Test public void testFindAll() { List<UserGroup> userGroups =
	 * groupsServiceImpl.findAll(); assertNotNull(userGroups); }
	 */

	@Test
	public void testFindById() {
		List<UserGroup> groups = groupsServiceImpl.findById(UG_GR_ID);
		assertNotNull(groups);
	}

	@Test
	public void testCheckGroupUnique() {
		assertTrue(groupsServiceImpl.checkGroupUnique(new Groups(), "update"));
	}

	/*
	 * @Test public void testGetGroupObject() { Map<String, Object> map = new
	 * HashMap<String, Object>(); map.put("gr_id", 1342); map.put("gr_name",
	 * "Unit Test Departement"); map.put("gr_description",
	 * "Unit Test Departement"); map.put("gr_status", "Inactive");
	 * map.put("created_by", "Admin");
	 * 
	 * Groups groups = new Groups(); groups.setGr_id(0);
	 * groups.setGr_name("JUnit group");
	 * groups.setGr_description("JUnit group"); groups.setGr_status("Active");
	 * groups.setCreated_by("Admin"); groups.setLast_Updated_by("Admin");
	 * 
	 * Groups groups2 = groupsServiceImpl.getGroupObject(map, "save", groups);
	 * assertNotNull(groups2);
	 * 
	 * }
	 */

	@Test
	public void testAddUser() {
		groupsServiceImpl.AddUser(new UserGroup());
	}

	@Test
	public void testDeleteUser() {
		groupsServiceImpl.DeleteUser(UG_UR_ID, UG_GR_ID);
	}

	@Test
	public void testSetGroupStatus() {
		MockHttpServletResponse response = new MockHttpServletResponse();
		MessageSource messageSource = null;
		Locale locale = null;
		groupsServiceImpl.setGroupStatus(group_id, "activate", response,
				messageSource, locale);
	}

	@Test
	public void testGetGroupNames() {
		List<Groups> groups = groupsServiceImpl.getGroupNames();
		assertNotNull(groups);
	}

	@Test
	public void testFindAllWithoutInvalidDefault() {
		List<Groups> groups = groupsServiceImpl.findAllWithoutInvalidDefault();
		assertNotNull(groups);
	}

	@Test
	public void testGetAllActiveGroups() {
		List<Groups> groups = groupsServiceImpl.getAllActiveGroups();
		assertNotNull(groups);
	}

	@Test
	public void testGetGroupIdBasedOnGroupName() {
		List<Integer> integers = groupsServiceImpl
				.getGroupIdBasedOnGroupName(group_name);
		assertNotNull(integers);
	}
}
