package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.ManpowerService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ManpowerServiceImpl extends GenericServiceImpl<Person> implements
ManpowerService {

	@Autowired
	private LdapBusinessModel ldapBusinessModel;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private PersonService personService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private GroupsService groupsService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private VendorsService vendorsService;

	private static final Log logger = LogFactory.getLog(ManpowerServiceImpl.class);


	/* Set the response for the manpower Create and Update method
	 * @see com.bcits.bfm.service.facilityManagement.ManpowerService#setAllFeildforManpower(com.bcits.bfm.model.Users)
	 */
	@Override
	public List<Map<String, Object>> setAllFeildforManpower(Users users) {
		logger.info("method : setAllFeildforManpower");
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("personId", users.getPerson().getPersonId());		
		Person person = personService.find(users.getPerson().getPersonId());
		String personName = "";
		personName = personName.concat(person.getFirstName());
		personName = personName.concat(" ");
		personName = personName.concat(person.getLastName());
		userMap.put("personName", personName);
		/* -------For Persons------ */
		userMap.put("firstName", person.getFirstName());
		userMap.put("lastName", person.getLastName());
		userMap.put("middleName", person.getMiddleName());
		userMap.put("fatherName", person.getFatherName());
		userMap.put("spouseName", person.getSpouseName());
		userMap.put("dob", person.getDob());
		userMap.put("occupation", person.getOccupation());
		userMap.put("languagesKnown", person.getLanguagesKnown());
		userMap.put("personStyle", person.getPersonStyle());
		userMap.put("personType", person.getPersonType());
		userMap.put("title", person.getTitle());
		userMap.put("drGroupId", person.getDrGroupId());
		userMap.put("kycCompliant", person.getKycCompliant());
		userMap.put("sex", person.getSex());
		userMap.put("maritalStatus", person.getMaritalStatus());
		userMap.put("nationality", person.getNationality());
		userMap.put("bloodGroup", person.getBloodGroup());
		userMap.put("personStatus", person.getPersonStatus());
		userMap.put("urId", users.getUrId());
		userMap.put("status", users.getStatus());
		userMap.put("urLoginName", users.getUrLoginName());
		//userMap.put("requisition", users.getRequisition().getReqName());
		userMap.put("staffType", users.getStaffType());
		userMap.put("vendorId", users.getVendorId());
		Vendors vendors = vendorsService.getVendorInstanceBasedOnVendorId(users.getVendorId());
		String personVendorName = "";
		personVendorName = personVendorName.concat(vendors.getPerson().getFirstName());
		personVendorName = personVendorName.concat(" ");
		if(vendors.getPerson().getLastName()!=null)
			personVendorName = personVendorName.concat(vendors.getPerson().getLastName());
		userMap.put("vendorName", personVendorName);
		Department dept = departmentService.find(users.getDepartment().getDept_Id());
		userMap.put("dept_Name", dept.getDept_Name());
		Designation dn = designationService.find(users.getDesignation().getDn_Id());	
		userMap.put("dn_Name", dn.getDn_Name());
		userMap.put("lastUpdatedDt", users.getLastUpdatedDt());
		userMap.put("createdBy", users.getCreatedBy());
		userMap.put("lastUpdatedBy", users.getLastUpdatedBy());
		Set<Role> roles = new HashSet<Role>();
		Set<UserRole> userRoles = users.getUserRoles();
		for (Iterator<UserRole> iterator = userRoles.iterator(); iterator.hasNext();)
		{
			UserRole userRole = (UserRole) iterator.next();
			Role newRole = new Role();
			Role oldRole = roleService.find(userRole.getUro_Rl_Id());
			newRole.setCreatedBy(oldRole.getCreatedBy());
			newRole.setLastUpdatedBy(oldRole.getLastUpdatedBy());
			newRole.setLastUpdatedDate(oldRole.getLastUpdatedDate());
			newRole.setRlDescription(oldRole.getRlDescription());
			newRole.setRlId(oldRole.getRlId());
			String roleStatus = oldRole.getRlStatus();
			if(roleStatus.equalsIgnoreCase("Active"))
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
		for (Iterator<UserGroup> iterator = userGroups.iterator(); iterator.hasNext();)
		{
			UserGroup userGroup = (UserGroup) iterator.next();
			Groups newGroup = new Groups();
			Groups oldGroup = groupsService.find(userGroup.getUG_GR_ID());
			newGroup.setCreated_by(oldGroup.getCreated_by());
			newGroup.setLast_Updated_by(oldGroup.getLast_Updated_by());
			newGroup.setGr_description(oldGroup.getGr_description());
			newGroup.setGr_id(oldGroup.getGr_id());
			String groupStatus = oldGroup.getGr_status();
			if(groupStatus.equalsIgnoreCase("Active"))
				newGroup.setGr_name(oldGroup.getGr_name().concat("  {Active}"));
			else
				newGroup.setGr_name(oldGroup.getGr_name().concat("  {Inactive}"));
			newGroup.setGr_status(groupStatus);
			groups.add(newGroup);
		}
		userMap.put("groups", groups);
		userMap.put("groupsDummy", groups);

		userList.add(userMap);

		return userList;
	}

}