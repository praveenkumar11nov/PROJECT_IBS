package com.bcits.bfm.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.UserLog;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.UserGroupService;
import com.bcits.bfm.service.userManagement.UserLogSerivce;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class UserProfileController {
	@Autowired
	private LdapBusinessModel ldapBusinessModel;

	@Autowired
	private PersonService personService;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private UserGroupService userGroupservice;

	@Autowired
	private UserLogSerivce userlogSerivce;

	@Autowired
	private UserRoleService userroleService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private CommonService commonService;

	Users users = null;
	int userId = 0;
	String username;

	/** 
     *this method is used to display myprofile page and to display groups ,roles assign to user.
     * @since           1.0
     */
	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public String UserProfileIndex(ModelMap model, HttpServletRequest request) {
		username = (String) SessionData.getUserDetails().get("userID");
		model.addAttribute("ViewName", "My Profile");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Profile", 1, request);
		try{
			users = usersService.getUserInstanceByLoginName(username);
		}catch(Exception e){
			return "/accessDenied";
		}

		model.addAttribute("LoginName", users.getUrLoginName());
		model.addAttribute("departmentID", users.getDeptId());
		int deptId = users.getDeptId();
		/*---------------------------------------------------------*/
		List<Department> listDepartment = departmentService
				.getDepartmentDetails(deptId);
		if (listDepartment.get(0).getDept_Name().equalsIgnoreCase("Invalid")) {
			model.addAttribute("DepartmentName", "No Department");
		} else {

			model.addAttribute("DepartmentName", listDepartment.get(0).getDept_Name());
		}
		/*---------------------------------------------------------*/
		int designationId = users.getDnId();
		List<Designation> listDesignation = designationService
				.getDesignationDetails(designationId);
		if (listDesignation.get(0).getDn_Name().equalsIgnoreCase("Invalid")) {
			model.addAttribute("DesignationName", "No Designation");
		} else {
			model.addAttribute("DesignationName", listDesignation.get(0).getDn_Name());
		}

		/*---------------------------------------------------------*/

		List<String> groups = ldapBusinessModel.getLoginGroups(username);
		model.addAttribute("groups", groups);

		/*---------------------------------------------------------*/
		List<String> roles = ldapBusinessModel.getLoginRoles(username);
		model.addAttribute("roles", roles);
		String contactContent = null;
		int personId = users.getPersonId();
		contactContent = (String) contactService.getContactContent(personId).get(0);
		model.addAttribute("contactcontent", contactContent);
		return "myprofile";
	}

	/** 
     *this method is used to update user details like(Contact Number and Email id) and to store in database.
     * @since           1.0
     */
	@RequestMapping(value = "/myprofile/update", method = RequestMethod.POST)
	public @ResponseBody
	String updateUsers(@RequestParam("email") String mailid,
			@RequestParam("mobileno") String number, Map<String, Object> map,
			HttpServletRequest request) {
		users = usersService.getUserInstanceByLoginName(username);
		int personId = users.getPersonId();
		int contactId = contactService.getContactId(personId);
		String phno = number;
		String mailId = mailid;
		if (phno.length() > 0 && mailId.length() == 0) {
			contactService.updateContactContent(contactId, phno);

		}
		if (mailId.length() > 0 && phno.length() == 0) {
			contactService.updateContactContent(contactId, mailId);
		}
		Users users = new Users();
		users.setUrId(users.getUrId());
		return null;
	}

	/** 
     *this method is used to upload image of user and to store in database.
     * @since           1.0
     */
	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		byte[] imsgeBytes = mpf.getBytes();

		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("userId");
		ldapBusinessModel.setImage(username, imsgeBytes);

		Blob blob;
		blob = Hibernate.createBlob(mpf.getInputStream());

		List<String> attributesList = new ArrayList<String>();
		attributesList.add("personId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("urLoginName", username);

		List<?> list = commonService
				.selectQuery("Users", attributesList, params);

		personService.uploadImageOnId((Integer) list.get(0), blob);

		return "redirect:/myprofile";
	}

	
	
	/** 
     *this method is used to find user log details of user from database.
     *@param	username	: username
     * @return              : returns vvId
     * @since                 1.0
     */
	@RequestMapping(value = "/myprofile/getUserLogDetails", method = RequestMethod.POST)
	public @ResponseBody
	List<?> getUserLogDetails() {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final UserLog record : userlogSerivce
				.findAllUserLogDetails(username)) {
			result.add(new HashMap<String, Object>() {
				{
					put("ulId", record.getUlId());
					put("urLoginName", record.getUrLoginName());
					put("logoutMethod", record.getLogoutMethod());
					put("systemIp", record.getSystemIp());
					put("duration", record.getDuration());
					final String timeformat = "dd/MM/yyyy HH:mm";
					SimpleDateFormat sdf = new SimpleDateFormat(timeformat);

					Timestamp intime = record.getUlSessionStart();

					if (intime != null) {

						put("ulSessionStart", sdf.format(intime));
					} else {

						put("ulSessionStart", null);

					}
					Timestamp outtime = record.getUlSessionEnd();
					if (outtime != null) {
						put("ulSessionEnd", sdf.format(outtime));
					} else {

						put("ulSessionEnd", null);
					}

				}
			});
		}

		return result;

	}

}
