package com.bcits.bfm.serviceImpl.userManagement;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.VendorsManagement.RequisitionDetailsService;
import com.bcits.bfm.service.VendorsManagement.RequisitionService;
import com.bcits.bfm.service.VendorsManagement.VendorContractsService;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UserGroupService;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.bcits.bfm.model.Users
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */

@Repository
public class UsersServiceImpl extends GenericServiceImpl<Users> implements
		UsersService {

	@Autowired
	private LdapBusinessModel ldapBusinessModel;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private GroupsService groupsService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private PersonService personService;

	@Autowired
	private VendorsService vendorsService;
	
	@Autowired
	private RequisitionService requisitionService;
	
	@Autowired
	private RequisitionDetailsService requisitionDetailsService;
	
	@Autowired
	private VendorContractsService vendorContractsService;
	
	
	private Logger logger=Logger.getLogger(UsersServiceImpl.class);
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> getAllUsersRequiredFilds() {
		List<?> usersList = entityManager.createNamedQuery(
				"Users.getAllUsersRequiredFilds").getResultList();

		return getUsersList(usersList);
	}


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> getAllUserApproval(String userId) {
		List<?> usersList = entityManager.createNamedQuery(
				"Users.getAllUserApproval").setParameter("status1","Inprogress").getResultList();

		return getUsersList1(usersList,userId);
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	private List<Map<String, Object>> getUsersList1(List<?> usersList,String userId) {
		
		int designationId=(int) entityManager.createNamedQuery(
				"Users.getDesignationId").setParameter("urLoginName",userId).getSingleResult();
		Map<Integer, Users> userMap = new HashMap<Integer, Users>();
		for (Iterator<?> i = usersList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			if(designationId==(int)values[29]){
				if (!(userMap.containsKey(values[0]))) {
				Users userObj = new Users();
				userObj.setUrId((Integer) values[0]);
				userObj.setUrLoginName((String) values[1]);

				Person person = new Person();
				person.setPersonId((Integer) values[2]);
				person.setFirstName((String) values[3]);
				person.setLastName((String) values[4]);
				person.setKycCompliant((String) values[26]);
				
				
				
				userObj.setPerson(person);

				userObj.setCreatedBy((String) values[5]);
				userObj.setLastUpdatedBy((String) values[6]);
				userObj.setLastUpdatedDt((Timestamp) values[7]);
				userObj.setStatus((String) values[8]);

				Designation designation = new Designation();
				designation.setDn_Id((Integer) values[9]);
				designation.setDn_Name((String) values[10]);
				userObj.setDesignation(designation);

				Department department = new Department();
				department.setDept_Id((Integer) values[11]);
				department.setDept_Name((String) values[12]);
				userObj.setDepartment(department);

				Set<Role> roleSet = new HashSet<Role>();
				Role role = new Role();
				role.setRlId((Integer) values[13]);
				String roleStatus = (String) values[22];
				if (roleStatus.equalsIgnoreCase("Active"))
					role.setRlName(((String) values[14]).concat("  {Active}"));
				else
					role.setRlName(((String) values[14]).concat("  {Inactive}"));
				role.setRlStatus(roleStatus);
				roleSet.add(role);

				userObj.setRoles(roleSet);

				Set<Groups> groupSet = new HashSet<Groups>();
				Groups groups = new Groups();
				groups.setGr_id((Integer) values[15]);
				String groupStatus = (String) values[23];
				if (groupStatus.equalsIgnoreCase("Active"))
					groups.setGr_name(((String) values[16])
							.concat("  {Active}"));
				else
					groups.setGr_name(((String) values[16])
							.concat("  {Inactive}"));
				groups.setGr_status(groupStatus);
				groupSet.add(groups);

				userObj.setGroups(groupSet);

				userObj.setStaffType((String) values[17]);

				userObj.setVendorId((Integer) values[18]);

				Vendors vendors = new Vendors();

				vendors.setVendorId((Integer) values[18]);
				Person personVendor = new Person();

				personVendor.setPersonId((Integer) values[19]);
				personVendor.setFirstName((String) values[20]);
				personVendor.setLastName((String) values[21]);
				vendors.setPerson(personVendor);

				userObj.setVendors(vendors);

				userMap.put((Integer) values[0], userObj);

			}

			else {
				Users userObj = userMap.get(values[0]);

				Set<Role> roleSet = userObj.getRoles();

				Role role = new Role();
				role.setRlId((Integer) values[13]);
				String roleStatus = (String) values[22];
				if (roleStatus.equalsIgnoreCase("Active"))
					role.setRlName(((String) values[14]).concat("  {Active}"));
				else
					role.setRlName(((String) values[14]).concat("  {Inactive}"));
				role.setRlStatus(roleStatus);

				if (!(roleSet.contains(role))) {
					roleSet.add(role);
				}

				userObj.setRoles(roleSet);

				Set<Groups> groupSet = userObj.getGroups();

				Groups groups = new Groups();
				groups.setGr_id((Integer) values[15]);
				String groupStatus = (String) values[23];
				if (groupStatus.equalsIgnoreCase("Active"))
					groups.setGr_name(((String) values[16])
							.concat("  {Active}"));
				else
					groups.setGr_name(((String) values[16])
							.concat("  {Inactive}"));
				groups.setGr_status(groupStatus);

				if (!(groupSet.contains(groups))) {
					groupSet.add(groups);
				}

				userObj.setGroups(groupSet);

			}
			}
		}

		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();

		Collection<Users> col = userMap.values();
		for (Iterator<Users> iterator = col.iterator(); iterator.hasNext();) {
			Map<String, Object> usersMap = new HashMap<String, Object>();

			Users userInstance = iterator.next();

			usersMap.put("urId", userInstance.getUrId());
			usersMap.put("urLoginName", userInstance.getUrLoginName());

			usersMap.put("personId", userInstance.getPerson().getPersonId());

			String personName = "";
			personName = personName.concat(userInstance.getPerson()
					.getFirstName());
			if ((userInstance.getPerson().getLastName()) != null) {
				personName = personName.concat(" ");
				personName = personName.concat(userInstance.getPerson()
						.getLastName());
			}
			usersMap.put("personName", personName);

			usersMap.put("vendorId", userInstance.getVendorId());

			String personVendorName = "";
			personVendorName = personVendorName.concat((userInstance
					.getVendors().getPerson().getFirstName()));
			if ((userInstance.getVendors().getPerson().getLastName()) != null) {
				personVendorName = personVendorName.concat(" ");
				personVendorName = personVendorName.concat(userInstance
						.getVendors().getPerson().getLastName());
			}

			usersMap.put("vendorName", personVendorName);

			usersMap.put("staffType", userInstance.getStaffType());

			usersMap.put("createdBy", userInstance.getCreatedBy());
			usersMap.put("lastUpdatedBy", userInstance.getLastUpdatedBy());
			java.util.Date lastUpdatedDtUtil = (Date)(userInstance.getLastUpdatedDt());
			java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
			usersMap.put("lastUpdatedDt", lastUpdatedDtSql);
			usersMap.put("status", userInstance.getStatus());

			usersMap.put("dn_Id", userInstance.getDesignation().getDn_Id());
			usersMap.put("dn_Name", userInstance.getDesignation().getDn_Name());
			usersMap.put("dept_Id", userInstance.getDepartment().getDept_Id());
			usersMap.put("dept_Name", userInstance.getDepartment()
					.getDept_Name());

			usersMap.put("roles", userInstance.getRoles());
			usersMap.put("rolesDummy", userInstance.getRoles());
			usersMap.put("groups", userInstance.getGroups());
			usersMap.put("groupsDummy", userInstance.getGroups());

			/*
			 * for (Iterator<Role> iterator2 =
			 * userInstance.getRoles().iterator(); iterator2.hasNext();) { Role
			 * role = (Role) iterator2.next(); usersMap.put("rlName",
			 * role.getRlName());
			 * 
			 * }
			 * 
			 * for (Iterator<Groups> iterator2 =
			 * userInstance.getGroups().iterator(); iterator2.hasNext();) {
			 * Groups group = (Groups) iterator2.next(); usersMap.put("gr_name",
			 * group.getGr_name());
			 * 
			 * }
			 */
			
			usersMap.put("kycCompliant", userInstance.getPerson().getKycCompliant());
			
			users.add(usersMap);
		}

		return users;
	}
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getAllStaffRecords() {
		List staffList = entityManager.createNamedQuery(
				"Users.getAllStaffRecords").getResultList();

		return getUsersList(staffList);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public boolean checkUserLoginNameUniqueness(Users users, String type) {
		List<String> userLoginNamesList = entityManager
				.createNamedQuery("Users.checkUserLoginNameUniqueness")
				.setParameter("userLoginName", users.getUrLoginName())
				.getResultList();

		if (type == "update") {
			if ((userLoginNamesList.size() > 0)) {
				if (userLoginNamesList.get(0).equals(
						find(users.getUrId()).getUrLoginName()))
					return true;
				else
					return false;
			} else
				return true;

		} else {
			if ((userLoginNamesList.size() > 0))
				return false;
			else
				return true;
		}

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getPersonId(String urLoginName) {

		return entityManager.createNamedQuery("Users.getPersonId")
				.setParameter("urLoginName", urLoginName).getFirstResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getAllUsersLoginNames() {
		return entityManager.createNamedQuery("Users.getAllUsersLoginNames")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Users getUserInstanceByLoginName(String userLoginName) {
		try{
		return entityManager.createNamedQuery("Users.getUserInstanceByLoginName",Users.class).setParameter("userLoginName", userLoginName).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null; 
		}
		
	}
	
	
	
	
	
	
	

	@Override
	public String setUserStatus(int userId, String operation, String mode, String name, String mobile, String email) 
	{
		if (operation.equalsIgnoreCase("activate")) 
		{
			entityManager.createNamedQuery("Users.UpdateStatus")
					.setParameter("status", "Active")
					.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
					.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
					.setParameter("urId", userId).executeUpdate();

			switch (mode) 
			{
				case "email":
					return (name
							+ " is Activated and credentials are sent via email ["
							+ email + "]");

				case "mobile":
					return (name
							+ " is Activated and credentials are sent via mobile ["
							+ mobile + "]");

				case "both":
					return (name
							+ " is Activated and credentials are sent via both email ["
							+ email + "] and mobile [" + mobile + "]");
				default : return name+" is Activated";
			}
			
		} 
		else if (operation.equalsIgnoreCase("invalid")) 
			return (name + " can't be activated since neither Email nor Mobile contacts are found");
		else 
		{
			entityManager.createNamedQuery("Users.UpdateStatus")
					.setParameter("status", "Inactive")
					.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
					.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
					.setParameter("urId", userId).executeUpdate();
			return (name + " is Deactivated");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Users getUserInstanceBasedOnPersonId(int personId) {
		return entityManager
				.createNamedQuery("Users.getUserInstanceBasedOnPersonId",
						Users.class).setParameter("personId", personId)
				.getSingleResult();
	}

	// -------------------------- utility -----------------------

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Users getUserObject(Map<String, Object> map, String type, Users users) {
		users = getBeanObject(map, type, users);
		users = getChildObjects(map, type, users);

		return users;
	}

	/**
	 * Getting User object with its children.
	 * 
	 * @param map
	 *            This User entity details coming from jsp as a map
	 * @param type
	 *            Either 'save' or 'update' string coming from getUserObject()
	 *            to check which operation to perform.
	 * @param users
	 *            Empty User object coming from getUserObject().
	 * @return User object with children objects setted
	 * @since 0.1
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	private Users getChildObjects(Map<String, Object> map, String type,
			Users users) {
		Set<UserRole> userRoles = new HashSet<UserRole>();

		if (map.get("roles") instanceof java.util.List) {
			List<Role> jsonCombinedRoles = (ArrayList<Role>) map.get("roles");

			if (!(jsonCombinedRoles.isEmpty()))
				userRoles.addAll(insertNewUserRoles(users, jsonCombinedRoles,
						type));
		}

		users.setUserRoles(userRoles);

		// -----------------------

		Set<UserGroup> userGroups = new HashSet<UserGroup>();

		if (map.get("groups") instanceof java.util.List) {
			List<Groups> jsonCombinedGroups = (ArrayList<Groups>) map
					.get("groups");

			if (!(jsonCombinedGroups.isEmpty()))
				userGroups.addAll(insertNewUserGroups(users,
						jsonCombinedGroups, type));
		}

		users.setUserGroups(userGroups);
		return users;
	}

	/**
	 * Setting new User Role objects.
	 * 
	 * @param users
	 * @param type
	 * 
	 * @param map
	 *            This User entity details coming from jsp as a map
	 * @param type
	 *            Either 'save' or 'update' string coming from getUserObject()
	 *            to check which operation to perform.
	 * @param users
	 *            Empty User object coming from getUserObject().
	 * @return User object with children objects setted
	 * @since 0.1
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	private Collection<? extends UserRole> insertNewUserRoles(Users users,
			List<Role> jsonCombinedRoles, String type) {
		Set<UserRole> userRoles = new HashSet<UserRole>();

		for (Iterator<Role> iterator = jsonCombinedRoles.iterator(); iterator
				.hasNext();) {
			UserRole userRole = new UserRole();
			userRole.setUro_Rl_Id((Integer) ((Map<String, Object>) iterator
					.next()).get("rlId"));
			if (type == "save")
				userRole.setCreatedBy((String) SessionData.getUserDetails()
						.get("userID"));
			else {
				List<UserRole> userRoleList = userRoleService
						.getUserRoleInstanceBasedOnUserIdAndRoleId(
								users.getUrId(), userRole.getUro_Rl_Id());
				if (userRoleList.size() > 0) {
					for (Iterator<UserRole> iterator2 = userRoleList.iterator(); iterator2
							.hasNext();) {
						UserRole userRole2 = iterator2.next();
						userRole.setCreatedBy(userRole2.getCreatedBy());

					}
				} else
					userRole.setCreatedBy((String) SessionData.getUserDetails()
							.get("userID"));

			}

			userRole.setLastUpdatedBy((String) SessionData.getUserDetails()
					.get("userID"));
			userRole.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			userRoles.add(userRole);
		}
		return userRoles;
	}

	/**
	 * Setting new User Group objects.
	 * 
	 * @param type
	 * @param users
	 * 
	 * @param map
	 *            This User entity details coming from jsp as a map
	 * @param type
	 *            Either 'save' or 'update' string coming from getUserObject()
	 *            to check which operation to perform.
	 * @param users
	 *            Empty User object coming from getUserObject().
	 * @return User object with children objects setted
	 * @since 0.1
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	private Collection<? extends UserGroup> insertNewUserGroups(Users users,
			List<Groups> jsonCombinedGroups, String type) {
		Set<UserGroup> userGroups = new HashSet<UserGroup>();

		for (Iterator<Groups> iterator = jsonCombinedGroups.iterator(); iterator
				.hasNext();) {
			UserGroup userGroup = new UserGroup();
			userGroup.setUG_GR_ID((Integer) ((Map<String, Object>) iterator
					.next()).get("gr_id"));
			if (type == "save")
				userGroup.setCREATED_BY((String) SessionData.getUserDetails()
						.get("userID"));
			else {

				List<UserGroup> userGroupList = userGroupService
						.getUserGroupInstanceBasedOnUserIdAndGroupId(
								users.getUrId(), userGroup.getUG_GR_ID());
				if (userGroupList.size() > 0) {
					for (Iterator<UserGroup> iterator2 = userGroupList
							.iterator(); iterator2.hasNext();) {
						UserGroup userGroup2 = iterator2.next();
						userGroup.setCREATED_BY(userGroup2.getCREATED_BY());

					}
				} else
					userGroup.setCREATED_BY((String) SessionData
							.getUserDetails().get("userID"));

			}

			userGroup.setLAST_UPDATED_BY((String) SessionData.getUserDetails()
					.get("userID"));
			userGroup.setLAST_UPDATED_DT(new Timestamp(new Date().getTime()));
			userGroups.add(userGroup);
		}
		return userGroups;
	}

	/**
	 * Getting User object with its children.
	 * 
	 * @param map
	 *            This User entity details coming from jsp as a map
	 * @param type
	 *            Either 'save' or 'update' string coming from getUserObject()
	 *            to check which operation to perform.
	 * @param users
	 *            User object with its children but without User details coming
	 *            from getUserObject().
	 * @return User object with children objects setted
	 * @since 0.1
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private Users getBeanObject(Map<String, Object> map, String type,
			Users users) {

		if (type.equals("update")) {
			users.setCreatedBy((String) map.get("createdBy"));

			users.setUrId((Integer) map.get("urId"));
			users.setStatus(((String) map.get("status")));

			if (!(((String) map.get("dept_Name")).length() == 0)) {
				String str = (String) map.get("dept_Name");

				String[] splitStr = str.split("  \\{");
				List<Integer> departmentId = departmentService
						.getDepartmentIdBasedOnDepartmentName(splitStr[0]);
				if (departmentId.size() > 0) {
					Department department = new Department();
					department.setDept_Id(departmentId.get(0));
					department.setDept_Name((String) map.get("dept_Name"));

					users.setDepartment(department);

					users.setDeptId(departmentId.get(0));
				}
			}

			Users oldUser = find(users.getUrId());
			logger.info("desg_Name--------------------"+map.get("dn_Name"));

			if (!(((String) map.get("dn_Name")).length() == 0)) {
				String str = (String) map.get("dn_Name");

				
				String[] splitStr = str.split("  \\{");
				logger.info("after Splitting----------"+splitStr[0].trim());
				if ((users.getDeptId() > 0)) {
					List<Integer> designationId = designationService
							.getDesignationIdBasedOnDesignationName(splitStr[0]
									.trim());

					logger.info("Desg_id------------------------"+designationId.size()+"----------"+designationId);
					if ((designationId.size() > 0)
							&& (designationId.get(0) == oldUser.getDnId())
							&& (users.getDeptId() == oldUser.getDeptId())) {
						Designation designation = new Designation();
						designation.setDn_Id(designationId.get(0));
						designation.setDn_Name((String) map.get("dn_Name"));

						users.setDesignation(designation);

						users.setDnId(designationId.get(0));
						logger.info("after Setting all Values--------------------------");
					}

					else if ((designationId.size() > 0)
							&& (!(oldUser.getDnId() == designationId.get(0)))) {
						Designation designation = new Designation();
						designation.setDn_Id(designationId.get(0));
						designation.setDn_Name((String) map.get("dn_Name"));

						users.setDesignation(designation);

						users.setDnId(designationId.get(0));
					}
				}
			}
			/*if(StringUtils.equalsIgnoreCase("Vendor", (String)map.get("staffType")))
					users.setVendorId((Integer) map.get("vendorId"));
			else
					users.setVendorId(1);*/
			users.setStaffType((String) map.get("staffType"));
			users.setPersonId((Integer) map.get("personId"));
		}

		else if (type.equals("save")) {
			
			RequisitionDetails requisitionDetails = requisitionDetailsService.find((Integer)map.get("rdId"));
			if(requisitionDetails!=null){
				users.setDepartment(requisitionDetails.getRequisition().getDepartment());
				users.setDeptId(requisitionDetails.getRequisition().getDept_Id());
				users.setDesignation(requisitionDetails.getDesignation());
				users.setDnId(requisitionDetails.getDn_Id());
				requisitionDetails.setReqFulfilled(requisitionDetails.getReqFulfilled()+1);
				requisitionDetailsService.update(requisitionDetails);
			}

			users.setCreatedBy((String) SessionData.getUserDetails().get(
					"userID"));
			users.setStatus("Inactive");

			/*if (!(((String) map.get("dept_Name")).length() == 0)) {
				String str = (String) map.get("dept_Name");

				String[] splitStr = str.split("  \\{");
				List<Integer> departmentId = departmentService
						.getDepartmentIdBasedOnDepartmentName(splitStr[0]);
				if (departmentId.size() > 0) {
					Department department = new Department();
					department.setDept_Id(departmentId.get(0));
					department.setDept_Name((String) map.get("dept_Name"));

					users.setDepartment(department);

					users.setDeptId(departmentId.get(0));
				}
			}

			if (!(((String) map.get("dn_Name")).length() == 0)) {

				String str = (String) map.get("dn_Name");

				String[] splitStr = str.split("  \\{");
				List<Integer> designationId = designationService
						.getDesignationIdBasedOnDesignationName(splitStr[0]);
				if (designationId.size() > 0) {
					Designation designation = new Designation();
					designation.setDn_Id(designationId.get(0));
					designation.setDn_Name((String) map.get("dn_Name"));

					users.setDesignation(designation);

					users.setDnId(designationId.get(0));
				}
			}*/

		/*	if(StringUtils.equalsIgnoreCase("Vendor Contract", (String)map.get("reqType"))){
				users.setVendorId((Integer) map.get("vendorId"));
			}
			else{
				users.setVendorId(1);
			}*/
			users.setStaffType((String) map.get("staffType"));
		}
		
	
		logger.info("REqusition Id------------------------------------------"+map.get("reqId")+"--------userId----------"+users.getUrId());
		
		int reqId=0;
		if(map.get("reqId")!=null)
		{
			reqId=(Integer)map.get("reqId");
		}
		else
		{
			Users u=find(users.getUrId());
			if(u!=null)
			{
			reqId=u.getReqId();
			logger.info("whn ReqId isEqual to 0-----"+reqId);
			}
		}
		Requisition requisition =requisitionService.find(reqId);
		if(requisition!=null){
			users.setReqId(requisition.getReqId());
		}
		if(StringUtils.equalsIgnoreCase("vendor", (String)map.get("reqType")) && requisition!=null){
			List<VendorContracts> contracts = vendorContractsService.executeSimpleQuery("select o from VendorContracts o where status='Approved' and o.reqId="+requisition.getReqId());
			if(contracts.size()>0){
				users.setVendorId(contracts.get(0).getVendorId());
			}else{
				users.setVendorId(1);
			}
		}else{
			users.setVendorId(1);
		}
		
		users.setUrLoginName((String) map.get("urLoginName"));
		users.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		users.setLastUpdatedDt(new Timestamp(new Date().getTime()));

		return users;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	private List<Map<String, Object>> getUsersList(List<?> usersList) {
		Map<Integer, Users> userMap = new HashMap<Integer, Users>();

		for (Iterator<?> i = usersList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			if (!(userMap.containsKey(values[0]))) {
				Users userObj = new Users();
				userObj.setUrId((Integer) values[0]);
				userObj.setUrLoginName((String) values[1]);

				Person person = new Person();
				person.setPersonId((Integer) values[2]);
				person.setFirstName((String) values[3]);
				person.setLastName((String) values[4]);
				person.setKycCompliant((String) values[26]);
				
				userObj.setPerson(person);

				userObj.setCreatedBy((String) values[5]);
				userObj.setLastUpdatedBy((String) values[6]);
				userObj.setLastUpdatedDt((Timestamp) values[7]);
				userObj.setStatus((String) values[8]);

				Designation designation = new Designation();
				designation.setDn_Id((Integer) values[9]);
				designation.setDn_Name((String) values[10]);
				userObj.setDesignation(designation);

				Department department = new Department();
				department.setDept_Id((Integer) values[11]);
				department.setDept_Name((String) values[12]);
				userObj.setDepartment(department);

				Requisition requisition=new Requisition();
				requisition.setReqId((Integer)values[27]);
				requisition.setReqName((String)values[28]);
				userObj.setRequisition(requisition);
				
				Set<Role> roleSet = new HashSet<Role>();
				Role role = new Role();
				role.setRlId((Integer) values[13]);
				String roleStatus = (String) values[22];
				if (roleStatus.equalsIgnoreCase("Active"))
					role.setRlName(((String) values[14]).concat("  {Active}"));
				else
					role.setRlName(((String) values[14]).concat("  {Inactive}"));
				role.setRlStatus(roleStatus);
				roleSet.add(role);

				userObj.setRoles(roleSet);

				Set<Groups> groupSet = new HashSet<Groups>();
				Groups groups = new Groups();
				groups.setGr_id((Integer) values[15]);
				String groupStatus = (String) values[23];
				if (groupStatus.equalsIgnoreCase("Active"))
					groups.setGr_name(((String) values[16])
							.concat("  {Active}"));
				else
					groups.setGr_name(((String) values[16])
							.concat("  {Inactive}"));
				groups.setGr_status(groupStatus);
				groupSet.add(groups);

				userObj.setGroups(groupSet);

				userObj.setStaffType((String) values[17]);

				userObj.setVendorId((Integer) values[18]);

				Vendors vendors = new Vendors();

				vendors.setVendorId((Integer) values[18]);
				Person personVendor = new Person();

				personVendor.setPersonId((Integer) values[19]);
				personVendor.setFirstName((String) values[20]);
				personVendor.setLastName((String) values[21]);
				vendors.setPerson(personVendor);

				userObj.setVendors(vendors);

				userMap.put((Integer) values[0], userObj);

			}

			else {
				Users userObj = userMap.get(values[0]);

				Set<Role> roleSet = userObj.getRoles();

				Role role = new Role();
				role.setRlId((Integer) values[13]);
				String roleStatus = (String) values[22];
				if (roleStatus.equalsIgnoreCase("Active"))
					role.setRlName(((String) values[14]).concat("  {Active}"));
				else
					role.setRlName(((String) values[14]).concat("  {Inactive}"));
				role.setRlStatus(roleStatus);

				if (!(roleSet.contains(role))) {
					roleSet.add(role);
				}

				userObj.setRoles(roleSet);

				Set<Groups> groupSet = userObj.getGroups();

				Groups groups = new Groups();
				groups.setGr_id((Integer) values[15]);
				String groupStatus = (String) values[23];
				if (groupStatus.equalsIgnoreCase("Active"))
					groups.setGr_name(((String) values[16])
							.concat("  {Active}"));
				else
					groups.setGr_name(((String) values[16])
							.concat("  {Inactive}"));
				groups.setGr_status(groupStatus);

				if (!(groupSet.contains(groups))) {
					groupSet.add(groups);
				}

				userObj.setGroups(groupSet);

			}
		}

		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();

		Collection<Users> col = userMap.values();
		for (Iterator<Users> iterator = col.iterator(); iterator.hasNext();) {
			Map<String, Object> usersMap = new HashMap<String, Object>();

			Users userInstance = iterator.next();

			usersMap.put("urId", userInstance.getUrId());
			usersMap.put("urLoginName", userInstance.getUrLoginName());

			usersMap.put("personId", userInstance.getPerson().getPersonId());

			String personName = "";
			personName = personName.concat(userInstance.getPerson()
					.getFirstName());
			if ((userInstance.getPerson().getLastName()) != null) {
				personName = personName.concat(" ");
				personName = personName.concat(userInstance.getPerson()
						.getLastName());
			}
			usersMap.put("personName", personName);

			usersMap.put("vendorId", userInstance.getVendorId());

			String personVendorName = "";
			personVendorName = personVendorName.concat((userInstance
					.getVendors().getPerson().getFirstName()));
			if ((userInstance.getVendors().getPerson().getLastName()) != null) {
				personVendorName = personVendorName.concat(" ");
				personVendorName = personVendorName.concat(userInstance
						.getVendors().getPerson().getLastName());
			}

			usersMap.put("vendorName", personVendorName);

			usersMap.put("staffType", userInstance.getStaffType());

			usersMap.put("createdBy", userInstance.getCreatedBy());
			usersMap.put("lastUpdatedBy", userInstance.getLastUpdatedBy());
			usersMap.put("lastUpdatedDt", userInstance.getLastUpdatedDt());
			usersMap.put("status", userInstance.getStatus());

			usersMap.put("dn_Id", userInstance.getDesignation().getDn_Id());
			usersMap.put("dn_Name", userInstance.getDesignation().getDn_Name());
			usersMap.put("dept_Id", userInstance.getDepartment().getDept_Id());
			usersMap.put("dept_Name", userInstance.getDepartment()
					.getDept_Name());

			usersMap.put("roles", userInstance.getRoles());
			usersMap.put("rolesDummy", userInstance.getRoles());
			usersMap.put("groups", userInstance.getGroups());
			usersMap.put("groupsDummy", userInstance.getGroups());

			usersMap.put("requisition", userInstance.getRequisition().getReqName());
			usersMap.put("reqId", userInstance.getRequisition().getReqId());
			/*
			 * for (Iterator<Role> iterator2 =
			 * userInstance.getRoles().iterator(); iterator2.hasNext();) { Role
			 * role = (Role) iterator2.next(); usersMap.put("rlName",
			 * role.getRlName());
			 * 
			 * }
			 * 
			 * for (Iterator<Groups> iterator2 =
			 * userInstance.getGroups().iterator(); iterator2.hasNext();) {
			 * Groups group = (Groups) iterator2.next(); usersMap.put("gr_name",
			 * group.getGr_name());
			 * 
			 * }
			 */
			
			usersMap.put("kycCompliant", userInstance.getPerson().getKycCompliant());
			
			users.add(usersMap);
		}

		return users;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> getUserObjectAfterSaveOrUpdate(Users users) {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();

		Map<String, Object> userMap = new HashMap<String, Object>();

		userMap.put("personId", users.getPersonId());

		Person person = personService.find(users.getPersonId());
		String personName = "";

		personName = personName.concat(person.getFirstName());
		if ((person.getLastName()) != null) {
			personName = personName.concat(" ");
			personName = personName.concat(person.getLastName());
		}

		userMap.put("personName", personName);

		userMap.put("urId", users.getUrId());
		userMap.put("status", users.getStatus());
		userMap.put("urLoginName", users.getUrLoginName());
		userMap.put("staffType", users.getStaffType());
		userMap.put("vendorId", users.getVendorId());

		Vendors vendors = vendorsService.getVendorInstanceBasedOnVendorId(users
				.getVendorId());

		String personVendorName = "";
		personVendorName = personVendorName.concat(vendors.getPerson()
				.getFirstName());
		if ((vendors.getPerson().getLastName()) != null) {
			personVendorName = personVendorName.concat(" ");
			personVendorName = personVendorName.concat(vendors.getPerson()
					.getLastName());
		}

		userMap.put("vendorName", personVendorName);

		Department dept = departmentService.find(users.getDepartment()
				.getDept_Id());

		userMap.put("dept_Name", dept.getDept_Name());

		Designation dn = designationService.find(users.getDesignation()
				.getDn_Id());
		userMap.put("dn_Name", dn.getDn_Name());

		userMap.put("lastUpdatedDt", users.getLastUpdatedDt());
		userMap.put("createdBy", users.getCreatedBy());
		userMap.put("lastUpdatedBy", users.getLastUpdatedBy());

		Set<Role> roles = new HashSet<Role>();
		Set<UserRole> userRoles = users.getUserRoles();
		for (Iterator<UserRole> iterator = userRoles.iterator(); iterator
				.hasNext();) {
			UserRole userRole = iterator.next();

			Role newRole = new Role();
			Role oldRole = roleService.find(userRole.getUro_Rl_Id());

			newRole.setCreatedBy(oldRole.getCreatedBy());
			newRole.setLastUpdatedBy(oldRole.getLastUpdatedBy());
			newRole.setLastUpdatedDate(oldRole.getLastUpdatedDate());
			newRole.setRlDescription(oldRole.getRlDescription());
			newRole.setRlId(oldRole.getRlId());
			String roleStatus = oldRole.getRlStatus();
			if (roleStatus.equalsIgnoreCase("Active"))
				newRole.setRlName(oldRole.getRlName().concat("  {Active}"));
			else
				newRole.setRlName(oldRole.getRlName().concat("  {Inactive}"));
			newRole.setRlStatus(roleStatus);

			roles.add(newRole);

		}

		
		userMap.put("roles", roles);
		userMap.put("rolesDummy", roles);

		Set<Groups> groups = new HashSet<Groups>();
		Set<UserGroup> userGroups = users.getUserGroups();
		for (Iterator<UserGroup> iterator = userGroups.iterator(); iterator
				.hasNext();) {
			UserGroup userGroup = iterator.next();

			Groups newGroup = new Groups();
			Groups oldGroup = groupsService.find(userGroup.getUG_GR_ID());
			newGroup.setCreated_by(oldGroup.getCreated_by());
			newGroup.setLast_Updated_by(oldGroup.getLast_Updated_by());
			newGroup.setGr_description(oldGroup.getGr_description());
			newGroup.setGr_id(oldGroup.getGr_id());
			String groupStatus = oldGroup.getGr_status();
			if (groupStatus.equalsIgnoreCase("Active"))
				newGroup.setGr_name(oldGroup.getGr_name().concat("  {Active}"));
			else
				newGroup.setGr_name(oldGroup.getGr_name()
						.concat("  {Inactive}"));
			newGroup.setGr_status(groupStatus);

			groups.add(newGroup);

		}

		userMap.put("groups", groups);
		userMap.put("groupsDummy", groups);

		userList.add(userMap);

		return userList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int setAllPrevDepartmentsBasedOnDeptId(int deptId) {
		return entityManager
				.createNamedQuery("Users.setAllPrevDepartmentsBasedOnDeptId")
				.setParameter("deptId", deptId).executeUpdate();

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int setAllPrevDesignationsBasedOnDnId(int dnId) {
		return entityManager
				.createNamedQuery("Users.setAllPrevDesignationsBasedOnDnId")
				.setParameter("dnId", dnId).executeUpdate();
	}

	@Override
	public void modifyToLdap(Users users, String type, String tempPass) {
		Set<String> roleNames = new HashSet<String>();

		if (!(users.getUserRoles().isEmpty())) {
			// create an iterator
			Iterator<UserRole> iterator = users.getUserRoles().iterator();

			// check values
			while (iterator.hasNext()) {
				roleNames.add(entityManager.find(Role.class,
						(iterator.next().getUro_Rl_Id())).getRlName());
			}
		}

		Set<String> groupNames = new HashSet<String>();

		if (!(users.getUserGroups().isEmpty())) {

			// create an iterator
			Iterator<UserGroup> iterator = users.getUserGroups().iterator();

			// check values
			while (iterator.hasNext()) {
				groupNames.add(entityManager.find(Groups.class,
						(iterator.next().getUG_GR_ID())).getGr_name());
			}
		}

		System.out.println("Roles " + roleNames + " Groups " + groupNames);

		Users user = find(users.getUrId());
		Set<Contact> contact = user.getPerson().getContacts();

		String email = "";
		String mobile = "";

		for (Iterator<Contact> iterator = contact.iterator(); iterator.hasNext();) {
			Contact contact2 = (Contact) iterator.next();
			if (contact2.getContactType().equalsIgnoreCase("Email")) {
				email = contact2.getContactContent();
			}
			if (contact2.getContactType().equalsIgnoreCase("Mobile")) {
				mobile = contact2.getContactContent();
			}
		}

		if (type == "save") {
			Blob image = users.getPerson().getPersonImages();
			if (email == "")
				email = "sample@mail.com";
			if (mobile == "")
				mobile = "0000000000";

			try {
				byte[] images = image.getBytes(1, (int) image.length());
				ldapBusinessModel.addUsers(users.getUrLoginName(), tempPass,
						email, mobile, roleNames, groupNames);
				ldapBusinessModel.setImage(users.getUrLoginName(), images);

			} catch (NullPointerException e) {
				System.out.println("image not found");
				ldapBusinessModel.addUsers(users.getUrLoginName(), tempPass,
						email, mobile, roleNames, groupNames);
				// e.printStackTrace();
			} catch (SQLException e) {
				e.getMessage();
			}
		}

		else if (type == "update") {
			// ldapBusinessModel.editUsers(users.getUrLoginName(),users.getEmailId(),
			// Long.toString(users.getMobileNo()), roleNames, groupNames);

			ldapBusinessModel.editUsers(find(users.getUrId()).getUrLoginName(),
					users.getUrLoginName(), email, mobile, roleNames,
					groupNames);
		}

		else if (type == "delete") {
			ldapBusinessModel.deleteUsers(users.getUrLoginName(), roleNames,
					groupNames);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> checkDepartmentExistence(int deptId) {
		return entityManager.createNamedQuery("Users.checkDepartmentExistence")
				.setParameter("deptId", deptId).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> checkDesignationExistence(int dnId) {
		return entityManager
				.createNamedQuery("Users.checkDesignationExistence")
				.setParameter("dnId", dnId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> findbyPersonId(int personId) {
		return entityManager.createNamedQuery("Users.getLoginNameByPersonId")
				.setParameter("personId", personId).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllUserIdsAndPersonNames() {
		return entityManager.createNamedQuery(
				"Users.getAllUserIdsAndPersonNames").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getLoginNameBasedOnId(int id) {
		return entityManager.createNamedQuery(
				"Users.getLoginNameBasedOnId",String.class)
				.setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> findAll() {
		final String queryString = "select model from Users model";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> findAllActiveUsers() {
		final String queryString = "select model from Users model WHERE model.status='Active'";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<Users> getAllUsersBasedOnStatus(){
		return entityManager.createNamedQuery("Users.getAllUsersBasedOnStatus").getResultList();
	}

	@Override
	public void setUserStatus(int urId, String operation,
			HttpServletResponse response) {
		try
		{
			//PrintWriter out = response.getWriter();

			
			if(StringUtils.containsIgnoreCase(operation, "Approved"))
				entityManager.createNamedQuery("Users.setStatus").setParameter("status", operation).setParameter("urId", urId).executeUpdate();
			else if(StringUtils.containsIgnoreCase(operation, "Completed"))
				entityManager.createNamedQuery("Users.setStatus").setParameter("status", operation).setParameter("urId", urId).executeUpdate();
			else
				entityManager.createNamedQuery("Users.setStatus").setParameter("status", operation).setParameter("urId", urId).executeUpdate();
			
			//out.write(operation);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateApprovalStatus(int urId,String status)
	{
		entityManager.createNamedQuery("Users.UpdateApprovalStatus").setParameter("urId", urId).setParameter("status", status).executeUpdate();
	}


	@Override
	public List<?> getRoleGroupFilter() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Users.roleFilter").getResultList();
	}



	@Override
	public Map<String, Object> getUserDetails() {
		List<?> user=entityManager.createNamedQuery("Users.userDetails").getResultList();
		Map<String,Object> userObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = user.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				userObj.put("count",(long)obj[0]);	
			}else{
				userObj.put("count",(long)0);	
			}
			if(obj[1]!=null){
				userObj.put("activeCount",(long)obj[1]);
			}else{
				userObj.put("activeCount",(long)0);
			}
			if(obj[2]!=null){
				userObj.put("inActiveCount",(long)obj[2]);
			}else{
				userObj.put("inActiveCount",(long)0);	
			}
			
			
			
			//userObj.put("staffType",obj[2]);
		}
		if(flag==0){
			userObj.put("count",(long)0);
			userObj.put("activeCount",(long)0);
			userObj.put("inActiveCount",(long)0);	
		}
		return userObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllUsers() {
		
		return entityManager.createNamedQuery("Users.getAllUsersRequiredFilds").getResultList();
				

	}


//	@Override
//	public Users getUserInstanceByLoginNameNew(String userLoginName) {
//		return entityManager.createNamedQuery("Users.getUserInstanceByLoginName",Users.class).setParameter("userLoginName", userLoginName).getSingleResult();
//	}



	@Override
	public Map<String, Object> getPersonDetails(String type) {
		List<?> person=entityManager.createNamedQuery("Person.personDetails").setParameter("type",type).getResultList();
		Map<String,Object> personObj=new HashMap<>();
		for (Iterator<?> i = person.iterator(); i.hasNext();) {
			Object[] obj=(Object[]) i.next();
			personObj.put("count",(long)obj[0]);
			personObj.put("activeCount",obj[1]);
			personObj.put("inActiveCount",obj[2]);
		}
		return personObj;
	}


	@Override
	public Map<String,Object> getParkingDetails() {
		List<?> parking=entityManager.createNamedQuery("ParkingSlots.parkingDetails").getResultList();
		Map<String,Object> parkingObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = parking.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				parkingObj.put("parkingSlot",(long)obj[0]);	
			}else{
				parkingObj.put("parkingSlot",(long)0);	
			}
			if(obj[1]!=null){
				parkingObj.put("ParkingAllocated",(long)obj[1]);
			}else{
				parkingObj.put("ParkingAllocated",(long)0);
			}
			if(obj[2]!=null){
				parkingObj.put("ParkingUnAllocated",(long)obj[2]);
			}else{
				parkingObj.put("ParkingUnAllocated",(long)0);	
			}
		
		}
		if(flag==0){
			parkingObj.put("parkingSlot",(long)0);
			parkingObj.put("ParkingAllocated",(long)0);
			parkingObj.put("ParkingUnAllocated",(long)0);
		}
		return parkingObj;
	}


	@Override
	public Map<String,Object> getVisitorDetails() {
		
		List<?> visitor=entityManager.createNamedQuery("Visitor.visitorDetails").getResultList();
		Map<String,Object> visitorObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = visitor.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				visitorObj.put("noVisitor",(long)obj[0]);	
			}else{
				visitorObj.put("noVisitor",(long)0);	
			}
			if(obj[1]!=null){
				visitorObj.put("visitorIn",(long)obj[1]);
			}else{
				visitorObj.put("visitorIn",(long)0);
			}
			if(obj[2]!=null){
				visitorObj.put("visitorOut",(long)obj[2]);
			}else{
				visitorObj.put("visitorOut",(long)0);	
			}
		
		}
		if(flag==0){
			visitorObj.put("noVisitor",(long)0);
			visitorObj.put("visitorIn",(long)0);
			visitorObj.put("visitorOut",(long)0);	
		}
		return visitorObj;
	}


	@Override
	public Map<String, Object> getCustomerDetails() {
		List<?> user=entityManager.createNamedQuery("Person.customerDetails").getResultList();
		Map<String,Object> userObj=new HashMap<>();
		for (Iterator<?> i = user.iterator(); i.hasNext();) {
			Object[] obj=(Object[]) i.next();
			userObj.put("count",obj[0]);
			userObj.put("activeCount",obj[1]);
			userObj.put("inActiveCount",obj[2]);
		}
		return userObj;
	}


	@Override
	public Map<String, Object> getFileDrawingDetails() {
		Long obj=(Long)entityManager.createNamedQuery("DocumentRepository.documentRepositoryDetails").getSingleResult();
		Map<String,Object> fileDrawingObj=new HashMap<>();
			if(obj!=null){
				fileDrawingObj.put("count",obj);	
			}else{
				fileDrawingObj.put("count",(long)0);	
			}
					
		
		return fileDrawingObj;
	}


	@Override
	public Map<String, Object> getAssetsInventaryDetails() {
		
		List<?> assetAndInventary=entityManager.createNamedQuery("Asset.assetsAndInventary").getResultList();
		Map<String,Object> assetAndInventaryObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = assetAndInventary.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				assetAndInventaryObj.put("count",(long)obj[0]);	
			}else{
				assetAndInventaryObj.put("count",(long)0);	
			}
			if(obj[1]!=null){
				assetAndInventaryObj.put("activeCount",(long)obj[1]);
			}else{
				assetAndInventaryObj.put("activeCount",(long)0);
			}
			if(obj[2]!=null){
				assetAndInventaryObj.put("inActiveCount",(long)obj[2]);
			}else{
				assetAndInventaryObj.put("inActiveCount",(long)0);	
			}
		
		}
		if(flag==0){
			assetAndInventaryObj.put("count",(long)0);	
			assetAndInventaryObj.put("activeCount",(long)0);
			assetAndInventaryObj.put("inActiveCount",(long)0);	
		}
		return assetAndInventaryObj;
	}


	@Override
	public Map<String, Object> getHelpDeskDetails() {
		List<?> helpDesk=entityManager.createNamedQuery("OpenNewTicketEntity.getHelpDeskDetails").getResultList();
		Map<String,Object> helpDeskObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = helpDesk.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				helpDeskObj.put("count",(long)obj[0]);	
			}else{
				helpDeskObj.put("count",(long)0);	
			}
			if(obj[1]!=null){
				helpDeskObj.put("activeCount",(long)obj[1]);
			}else{
				helpDeskObj.put("activeCount",(long)0);
			}
			if(obj[2]!=null){
				helpDeskObj.put("inActiveCount",(long)obj[2]);
			}else{
				helpDeskObj.put("inActiveCount",(long)0);	
			}
		
		}
		if(flag==0){
			helpDeskObj.put("count",(long)0);	
			helpDeskObj.put("activeCount",(long)0);
			helpDeskObj.put("inActiveCount",(long)0);	
		}
		return helpDeskObj;
	}


	@Override
	public List<Map<String, Object>> getChartData() throws ParseException {
		Object[] years =  (Object[]) entityManager.createNamedQuery("OwnerProperty.chartData1").getSingleResult();
		
		Calendar cal = Calendar.getInstance();
		if(years[0]!=null){
	    cal.setTime((Date) years[0]);
	    int startYear = cal.get(Calendar.YEAR);
	    int startMonth=cal.get(Calendar.MONTH);
		cal.setTime((Date) years[1]);
		int endYear=cal.get(Calendar.YEAR);
		 int endMonth=cal.get(Calendar.MONTH);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		
		
		Map<String,Object> chartdata=new HashMap<String,Object>();
		for(int i=startYear;i<=endYear;i++){
			final int year=i;
			chartdata.put("name", year);
			List<Long> data=new ArrayList<>();
				for(int j=1;j<=12;j++){
					final int month=j;
					String date1=year+"-"+month+"-01";
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/"+month+"/"+year);
					Calendar calendar = Calendar.getInstance();  
			        calendar.setTime(date);  

			        calendar.add(Calendar.MONTH, 1);  
			        calendar.set(Calendar.DAY_OF_MONTH, 1);  
			        calendar.add(Calendar.DATE, -1);  

			        Date lastDayOfMonth = calendar.getTime();  
			        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
					String date2=sdf.format(lastDayOfMonth);
						//Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/"+month+"/"+year);
						//Date date2=null;
						if(j==2){
							//date2= new SimpleDateFormat("dd/MM/yyyy").parse("28/"+month+"/"+year);
						}else
						 //date2= new SimpleDateFormat("dd/MM/yyyy").parse("31/"+month+"/"+year);
						System.out.println(date1);
						System.out.println(date2);
						Long count=(Long)entityManager.createNamedQuery("OwnerProperty.chartData2").setParameter("date1",date1).setParameter("date2",date2).getSingleResult();
					try{
							data.add((Long)count);
						}catch(Exception e){
							data.add((long) 0);	
						}
				}
				chartdata.put("data", data);
				result.add(chartdata);
		}
		return result;
		}
		else{
			return null;
		}
	}


	@Override
	public Map<String, Object> getOwnerDetails() {
		List<?> owner=entityManager.createNamedQuery("Owner.ownerDetails").getResultList();
		Map<String,Object> ownerObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = owner.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				ownerObj.put("count",(long)obj[0]);	
			}else{
				ownerObj.put("count",(long)0);	
			}
			if(obj[1]!=null){
				ownerObj.put("activeCount",(long)obj[1]);
			}else{
				ownerObj.put("activeCount",(long)0);
			}
			if(obj[2]!=null){
				ownerObj.put("inActiveCount",(long)obj[2]);
			}else{
				ownerObj.put("inActiveCount",(long)0);	
			}
		
		}
		if(flag==0){
			ownerObj.put("count",(long)0);
			ownerObj.put("activeCount",(long)0);
			ownerObj.put("inActiveCount",(long)0);
		}
		return ownerObj;
	}


	@Override
	public Map<String, Object> getVendorDetails() {
		List<?> owner=entityManager.createNamedQuery("Vendors.vendorsDetails").getResultList();
		Map<String,Object> ownerObj=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = owner.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				ownerObj.put("count",(long)obj[0]);	
			}else{
				ownerObj.put("count",(long)0);	
			}
			if(obj[1]!=null){
				ownerObj.put("activeCount",(long)obj[1]);
			}else{
				ownerObj.put("activeCount",(long)0);
			}
			if(obj[2]!=null){
				ownerObj.put("inActiveCount",(long)obj[2]);
			}else{
				ownerObj.put("inActiveCount",(long)0);	
			}
		
		}
		if(flag==0){
			ownerObj.put("count",(long)0);
			ownerObj.put("activeCount",(long)0);
			ownerObj.put("inActiveCount",(long)0);	
		}
		return ownerObj;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getDesigBasedOnPersonId(int personId) {
		
		return entityManager.createNamedQuery("Users.getUserInstanceBasedOnPersonId").setParameter("personId",personId).getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getPersonListBasedOnDeptDesigId(Integer deptId,Integer desigId) {
		return entityManager.createNamedQuery("Users.getPersonListBasedOnDeptDesigId").setParameter("deptId",deptId).setParameter("desigId",desigId).getResultList();

	}




//	@Override
//	public Users getUserInstanceByLoginNameNew(String userLoginName) {
//		return entityManager.createNamedQuery("Users.getUserInstanceByLoginName",Users.class).setParameter("userLoginName", userLoginName).getSingleResult();
//	}

}