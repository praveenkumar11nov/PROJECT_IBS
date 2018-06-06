package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.ManPowerNotification;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.StoreOutward;
import com.bcits.bfm.model.UserGroup;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.patternMasterEntity.TransactionDetail;
import com.bcits.bfm.restTemplate.Client;
import com.bcits.bfm.restTemplate.ClientApproval;
import com.bcits.bfm.service.ManpowerNotificationService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentRepositoryService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.service.userManagement.ManPowerApprovalService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UserGroupService;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.RandomPasswordGenerator;
import com.bcits.bfm.util.ResetPasswordMail;
import com.bcits.bfm.util.SendMailForStaff;
import com.bcits.bfm.util.SendMailForUserDetails;
import com.bcits.bfm.util.SendPasswordRecovery;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.novell.ldap.LDAPException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * Controller which includes all the business logic concerned to this module
 * Module: User Management
 * 
 * @author Manjunath Krishnappa, Shashi, Ravi, Syed
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class UserManagementController {
	
	private static final Log logger = LogFactory.getLog(UserManagementController.class);
	
	@Value("${Unique.server.link.bfm}")
	private String bfmUrl;
	
	@Autowired
	private ManPowerApprovalService manPowerApprovalService;
	
	@Autowired
	private ContactService contactService;
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;	
	@Autowired
	private JsonResponse jsonErrorResponse;	
	@Autowired
	private UsersService usersService;
	@Autowired
	private GroupsService groupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private DesignationService designationService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PersonService personService;
	@Autowired
	private Validator validator;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DocumentRepositoryService documentRepoService;
	
	@Autowired
	private ManpowerNotificationService manpowerNotificationService;
	
	@Value("${Unique.conf.serverIp}")
	private String hostIp;
	
	@Value("${Unique.conf.Serverport}")
	private String portNo;
	
	@Value("${Unique.conf.AppVersion}")
	private String version;
	
	
	@Value("${Unique.conf.DeployVersion}")
	private String DeployVersion;
	

	
	

	@Autowired
	public ClientApproval clientApproval;
	
	@Autowired
	public Client client;
	
	/**
	 * Use Case: ----------------------------------------- Manage Users --------------------------------------------
	 */

	/**
	 * All the users instances will be send to the view
	 * 
	 * @param model
	 *            This will set message inside view
	 * @param request
	 *            Input from the view
	 * @return Control goes to respective view
	 * @since 0.1
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)	
	public String indexUsers(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", " System Security ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Manage Users", 2, request);
		model.addAttribute("contact", new Contact());
		return "users";
	}
	@RequestMapping(value = "/userApproval", method = RequestMethod.GET)	
	public String indexUsersApproval(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", " System Security ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Manage Users", 2, request);
		model.addAttribute("contact", new Contact());
		return "userApproval";
	}
	/**
	 * User Access Management module will be set to the view
	 * 
	 * @param model
	 *            This will set the view
	 * @param request
	 *            Input from the view
	 * @return Control goes to respective view
	 * @since 0.1
	 */
	@RequestMapping(value="/usermanagement")
	public String UserManagementIndex(ModelMap model, HttpServletRequest request)
	{
		model.addAttribute("ViewName", "User Management");
		breadCrumbService.addNode("nodeID", 0, request);
		return "usermanagement";
	}
	
	/**
	 * Fetching all the values from the Users table and its children
	 * 
	 * @return All users instances
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/read", method = RequestMethod.POST)
    public @ResponseBody Object readUsers(final Locale locale) 
	{
		try
		{
			
			return commonController.getSortedList(usersService.getAllUsersRequiredFilds(), "urId");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("readException", messageSource.getMessage("exception.project.exception", null, locale));
				}
			};
			jsonErrorResponse.setStatus("READ_EXCEPTION");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}
    }
	
	@RequestMapping(value = "/users/readApproval", method = RequestMethod.POST)
    public @ResponseBody Object readUsersApproval(final Locale locale, HttpServletRequest request) 
	{
		HttpSession session=request.getSession(false);
		String userId=(String) session.getAttribute("userId");
		logger.info("**************userId*******************"+userId);

		try
		{
			return commonController.getSortedList(usersService.getAllUserApproval(userId), "urId");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("readException", messageSource.getMessage("exception.project.exception", null, locale));
				}
			};
			jsonErrorResponse.setStatus("READ_EXCEPTION");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}
    }

	/**
	 * Updating Users table with new values and respective inserting/deleting
	 * from UserRoles and UserGroups tables
	 * 
	 * @param map
	 *            Input from view includes Updated User details
	 * @param users
	 *            user instance to be updated
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param model
	 *            Required to be there along with binding result for validation purpose         
	 * @param sessionStatus
	 *            Used to clear the session
	 * @return Updated Users instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/update", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object updateUsers(@RequestBody Map<String, Object> map, @ModelAttribute("usersNew") Users users, BindingResult bindingResult, ModelMap model, SessionStatus sessionStatus, final Locale locale) throws Exception 
	{
		try
		{
			users = usersService.getUserObject(map, "update", users);
			validator.validate(users, bindingResult);		
		}
		catch (Exception e)
		{		
			e.printStackTrace();
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("saveOrUpdateException", messageSource.getMessage(
							"exception.project.exception", null, locale));
				}
			};
			jsonErrorResponse.setStatus("SAVE_OR_UPDATE_EXCEPTION");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}

		if (bindingResult.hasErrors())
		{
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			return jsonErrorResponse;
		}
		else 
		{
			try
			{
				// -------------Db-------------------
				users.setPerson(personService.find(users.getPersonId()));
				users = usersService.update(users);	
				// -------------Ldap-------------------
				usersService.modifyToLdap(users, "update", null);	 
			}
			catch (Exception e)
			{
				e.printStackTrace();
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("saveOrUpdateException", messageSource.getMessage(
								"exception.project.exception", null, locale));
					}
				};
				jsonErrorResponse.setStatus("SAVE_OR_UPDATE_EXCEPTION");
				jsonErrorResponse.setResult(errorMapResponse);
				return jsonErrorResponse;
			}
			try
			{
				List<Map<String, Object>> usersList = usersService.getUserObjectAfterSaveOrUpdate(users);
				sessionStatus.setComplete();
				return usersList;
			}
			catch (Exception e)
			{			
				e.printStackTrace();
				jsonErrorResponse.setStatus("LOAD_EXCEPTION");
				return jsonErrorResponse;
			}
		}
	}
	
	/**
	 * Fetching all roles from Roles table in order to assign the roles for users
	 * 
	 * @return All roles for the dropdown
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getRoles", method = RequestMethod.GET)
	public @ResponseBody List<Role> getRoles()
	{		
		List<Role> newRoleList = new ArrayList<Role>();
		List<Role> roleList = roleService.getAll();
		for (Iterator<Role> iterator = roleList.iterator(); iterator.hasNext();)
		{
			Role newRole = new Role();
			Role oldRole = (Role) iterator.next();
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
			newRoleList.add(newRole);
		}
		
		return newRoleList;
	}

	/**
	 * Fetching all groups from Groups table in order to assign the roles for users
	 * 
	 * @return All groups for the dropdown
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getGroups", method = RequestMethod.GET)
	public @ResponseBody List<Groups> getGroups() 
	{
		logger.info("In getting the groups method");
		List<Groups> newGroupList = new ArrayList<Groups>();
		List<Groups> groupList = groupService.getAll();
		for (Iterator<Groups> iterator = groupList.iterator(); iterator.hasNext();)
		{
			Groups newGroup = new Groups();
			Groups oldGroup = (Groups) iterator.next();
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
			newGroupList.add(newGroup);
		}
		return newGroupList;
	}
	
	/**
	 * Fetching all active departments which have at least one active designation from Department table in order to assign for users
	 * 
	 * @return All active departments for the dropdown
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getDepartment", method = RequestMethod.GET)
	public @ResponseBody List<?> getDepartment() 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<?> deptList = designationService.getDistinctDepartments();
		Map<String, Object> deptMap = null;
		for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
 	       	deptMap = new HashMap<String, Object>();
 	       	deptMap.put("dept_Id", (Integer)values[0]);
 	       	deptMap.put("dept_Name", (String)values[1]);
 	       	result.add(deptMap);
 	     }
        return result;
	}
	
	/**
	 * Fetching all active designations from Designation table in order to assign for users
	 * 
	 * @return All active designations for the dropdown
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/users/getDesignation", method = RequestMethod.GET)
	public @ResponseBody List<?> getDesignation() 
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Designation record : designationService.getAllActiveDesignations()) {
	            result.add(new HashMap<String, Object>() {{
            	put("dn_Name", record.getDn_Name());
	            put("dept_Id", record.getDept_Id());
	            put("dept_Name", record.getDepartment().getDept_Name());
	            put("dn_Id", record.getDn_Id());
	            }});
	        }
	        return result;
	}
	
	/**
	 * Setting the status of the user as Active/Inactive, updating the LDAP and sending SMS and Email if exists
	 * 
	 * @param userId
	 *            This user id to change the status.
	 * @param operation
	 *            operation - Either Activate or De-Activate the user
	 * @param locale
	 *            Is required to fetch the status name based on locale from properties file          
	 * @return the status as a string after activation/de-activation for display purpose
	 * @since 0.1
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/users/UserStatus/{userId}/{operation}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object UserStatus(@PathVariable int userId, @PathVariable String operation, final Locale locale) throws JSONException, SQLException
	{		
	
		String host = client.getHost();
		String port = client.getPort();
		String serviceURL=client.getServiceURL();
		JsonResponse jsonResponse = new JsonResponse();
		
		
		String processHost="http://"+ host.trim()+":"+ port.trim()+"/"+ serviceURL.trim()+"/runtime/process-instances";
		if(operation.equalsIgnoreCase("activate"))
		{
			logger.info("Activation in process...");
			String emailId="";
			String mobileNo="";
			Users user = usersService.find(userId);
			
			Set<Role> rolesSet = user.getRoles();
			List<Role> roleList = new ArrayList<Role>(rolesSet);
			
			Set<Groups> groupsSet = user.getGroups();
			List<Groups> groupList = new ArrayList<Groups>(groupsSet);
			
			Users users = usersService.getUserInstanceBasedOnPersonId(user.getPersonId());
			Set<Contact> contact=users.getPerson().getContacts();
			Blob image=users.getPerson().getPersonImages();		
			
			for (Iterator iterator = contact.iterator(); iterator.hasNext();) {
				Contact contact2 = (Contact) iterator.next();
				if((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes")))
					emailId=contact2.getContactContent();
				if((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes")))
					mobileNo=contact2.getContactContent();
			}
			
			logger.info("EmailId of the person!!");
			
			NameValuePair[] pair = new NameValuePair[] {
					new BasicNameValuePair("urId", Integer.toString(user.getUrId())),
					new BasicNameValuePair("firstName", user.getPerson().getFirstName()),
					new BasicNameValuePair("urLoginName", user.getUrLoginName()),
					new BasicNameValuePair("staffType",user.getStaffType()),
					new BasicNameValuePair("vendorName",user.getVendors().getPerson().getFirstName()),
					new BasicNameValuePair("dept_Name",user.getDepartment().getDept_Name()),
					new BasicNameValuePair("dn_Name", user.getDesignation().getDn_Name()),
					new BasicNameValuePair("groups", groupList.get(0).getGr_name()),
					new BasicNameValuePair("roles", roleList.get(0).getRlName()),
					new BasicNameValuePair("emailId", emailId),
					new BasicNameValuePair("mobileNo",mobileNo)
					
					
			};
			
			
			clientApproval.approvalProcess(processHost.trim(),"AdvancedManPowerApprovalProcess", pair);
			
			jsonResponse.setResult("Record Sent for approval...");
		}
		else
		{
			jsonResponse.setResult("Unable to process request..pls contact Administrator");
		}
		return jsonResponse;
	}
	
	/**
	 * Resetting the password, updating the LDAP and sending SMS and Email if exists to the user
	 *	
	 * @param request
	 *            Request will hold the fields data
	 * @param locale
	 *            Is required to fetch the temporary password based on locale from properties file          
	 * @return the status as a string after activation/de-activation for display purpose
	 * @throws LDAPException 
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/resetPassword", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String resetPassword(HttpServletRequest request, final Locale locale) throws IOException, LDAPException
	{		
		Users users = usersService.getUserInstanceBasedOnPersonId(Integer.parseInt(request.getParameter("personId")));
		Set<Contact> contact=users.getPerson().getContacts();
		String email="";
		String mobile="";
		String name = "";
		if(users.getPerson().getTitle() != null)
			name = users.getPerson().getTitle().concat(" ");
		if((users.getPerson().getFirstName()) != null)
			name = name.concat(users.getPerson().getFirstName());
		else
			name = name.concat(users.getPerson().getLastName());
		
		String tempPass = RandomPasswordGenerator.generateRandomPassword();
		System.err.println("Contact size" +contact.size());
		if(contact.size() != 0)
		{
			for (Iterator<Contact> iterator = contact.iterator(); iterator.hasNext();) 
			{
				Contact contact2 = (Contact) iterator.next();
				if((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes")))
					email=contact2.getContactContent();
				if((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes")))
					mobile=contact2.getContactContent();
			}
			
			if((email != null) && (email.length() != 0))
			{
				// ----------Mail-------------------------
			/*	String message=messageSource.getMessage("Mail.users.resetSubject", null, locale);
				new Thread(new ResetPasswordMail(email, users.getUrLoginName(), tempPass, message, portNo, version, hostIp, name)).start();*/
	


				EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
				  try {
				  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
				} catch (Exception e) {
					e.printStackTrace();
				}
 
 
				  logger.info("BFM LINK--------"+bfmUrl);
		 String messageContent="<html>"
					+ "<style type=\"text/css\">"
					+ "table.hovertable {"
					+ "font-family: verdana,arial,sans-serif;"
					+ "font-size:11px;"
					+ "color:#333333;"
					+ "border-width: 1px;"
					+ "border-color: #999999;"
					+ "border-collapse: collapse;"
					+ "}"
					+ "table.hovertable th {"
					+ "background-color:#c3dde0;"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "table.hovertable tr {"
					+ "background-color:#88ab74;"
					+ "}"
					+ "table.hovertable td {"
					+ "border-width: 1px;"
					+ "padding: 8px;"
					+ "border-style: solid;"
					+ "border-color: #394c2e;"
					+ "}"
					+ "</style>"
					+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON ADMINISTRATION DEPARTMENT</h2> <br /> Dear "+ name + ", Welcome to SKYON <br/> <br/> "
					+ "Your Password has been successfully reset,Please use following UserName and Password to login. <br/> <br/>"
					+ "<table class=\"hovertable\" >" + "<tr>"
					+ "<td colspan='2'>Your Account Details are</td>"
					+ "</tr>" + "<tr>" + "<td> User Name :</td>"
					+ "<td>" + users.getUrLoginName() + "</td>"
					+ "</tr>" + "<tr>" + "<td> Password:</td>" + "<td>"
					+tempPass + "</td>" + "</tr>" + "</table>"
					+ "<br/><br/>"
					+ "<a href=http://"+ bfmUrl
					+ ">click to login</a></body></html>"
					+ "<br/><br/>"
					+ "<br/>Thanks,<br/>"
					+ "SKYON Administration Department <br/> <br/>"
					+"</body></html>";	
		 
		 
		 

              emailCredentialsDetailsBean.setToAddressEmail(email);
	          emailCredentialsDetailsBean.setMessageContent(messageContent);
 	          new Thread(new SendMailForStaff(emailCredentialsDetailsBean)).start();
				

				
			}
			if((mobile != null) && (mobile.length() >= 10))
			{
				// ----------SMS-------------------------
				/*String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
				String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);
				mobile = mobile.substring(mobile.length()-10, mobile.length());
				new Thread(new SendPasswordRecovery(name, mobile, users.getUrLoginName(), tempPass, gateWayUserName, gateWayPassword)).start();*/
				
				String userMessage = "Dear "+users.getPerson().getFirstName()+", Your Password as be rested successfully . Your account details are username : "+users.getUrLoginName()+" & password : "+tempPass+" - SKYON Administration Department.";	

				
				  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
				   
				    smsCredentialsDetailsBean.setNumber(mobile);
					smsCredentialsDetailsBean.setUserName(users.getPerson().getFirstName());
					smsCredentialsDetailsBean.setMessage(userMessage);
					
					new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
			}
		}
		if(((email != null) && (email.length() != 0)) || ((mobile != null) && (mobile.length() >= 10)))
		{
			// -------------Ldap-------------------
			String userName=users.getUrLoginName();
			ldapBusinessModel.resetPassword(userName, tempPass);
			if(((email != null) && (email.length() != 0)) && ((mobile != null) && (mobile.length() >= 10)))
				return ("Password reset successfully, Login credentials are sent to "+name+"  email ["+email+"] and mobile ["+mobile+"]");
			else if((email != null) && (email.length() != 0))
				return ("Password reset successfully, Login credentials are sent to "+name+" via email ["+email+"]");
			else
				return ("Password reset successfully, Login credentials are sent to "+name+" via mobile ["+mobile+"]");
		}
		else
			return ("Password cannot be reset for "+name+" since neither Email nor Mobile contacts are found");
	}
	
	/**
	 * Getting all users login names
	 *	         
	 * @return All users login names
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getLoginNames", method = RequestMethod.GET)
	public @ResponseBody List<?> getUserNewLoginNames() 
	{
		return 	usersService.getAllUsersLoginNames();
	}
	
	/**
	 * Getting all departments for filtering purpose
	 *	         
	 * @return All departments for filtering purpose
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getDepartmentList", method = RequestMethod.GET)
	public @ResponseBody List<String> getDepartmentList() 
	{
		List<String> result = new ArrayList<String>(); 
		List<?> deptList = designationService.getDistinctDepartments();
		for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
		{
		 	final Object[] values = (Object[]) iterator.next();
	       	result.add((String) values[1]);
	    }
	    return result;
	}
	
	/**
	 * Getting all designations for filtering purpose
	 *     
	 * @return All designations for filtering purpose
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getDesignationList", method = RequestMethod.GET)
	public @ResponseBody List<?> getDesignationNamesList()
	{
		List<String> result = new ArrayList<String>();
		for (Designation designation : designationService.getAll())
		 	result.add(designation.getDn_Name());
		return result;
	}
	 
	/**
	 * Getting all staff types for filtering purpose
	 *
	 * @param locale
	 *            Is required to fetch the staff types based on locale from properties file    	         
	 * @return All staff types filtering purpose
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getStaffTypeList", method = RequestMethod.GET)
	public @ResponseBody List<String> getStaffTypeList(final Locale locale) 
	{
		String[] staffTypes = messageSource.getMessage("values.staff.staffType", null, locale).split(",");
		List<String> result = new ArrayList<String>();
		for (String string : staffTypes)
			result.add(string);
		return result;
	}
	 
	/**
	 * Getting all staff types for selection form drop downs
	 *
	 * @param locale
	 *            Is required to fetch the staff types based on locale from properties file    	         
	 * @return All staff types for dropdown purpose
	 * @since 0.1
	 */ 
	@RequestMapping(value = "/users/getStaffTypeChecks", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getStaffTypeChecks(final Locale locale) 
	{		
		String[] personTypes = messageSource.getMessage("values.staff.staffType", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < personTypes.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", personTypes[i]);
			map.put("name", personTypes[i]);
			result.add(map);
		}
		return result;
	}
 
	/**
	 * Getting all person names based on type
	 *
	 * @param personType
	 *            Based on person type fetching all person names
	 * @return All person names based on type
	 * @since 0.1
	 */ 
	@RequestMapping(value = "/users/getPersonNamesList/{personType}", method = RequestMethod.GET)
	public @ResponseBody List<String> getPersonNamesList(@PathVariable String personType) 
	{
	 	List<String> result = new ArrayList<String>();
	 	List<Object> personNamesList = personService.getAllPersonNamesList(personType);
	 	for (Iterator<Object> i = personNamesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String)values[0]);
			if(((String)values[1]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[1]);
			}
			result.add(personName);
		}
	 	return result;
	} 
 
	@RequestMapping(value = "/users/ownerShipNameFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> ownerShipNameFilterUrl() {	
		List<AssetOwnerShip> list =personService.getOwnerShipNameFilterUrl();		
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[3]+" "+(String)values[4]);		
		}
		return result;
	}
	
	@RequestMapping(value = "/users/maintainanceOwnerNameUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> maintainanceOwnerNameUrl() {	
		List<AssetOwnerShip> list = personService.getOwnerShipNameFilterUrl();	 	
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[7]+" "+(String)values[8]);		
		}
		return result;
	}
	
	/**
	 * Getting all person full names based on type
	 *
	 * @param personType
	 *            Based on person type fetching all person full names
	 * @return All person full names based on type
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getPersonFullNamesList/{personType}", method = RequestMethod.GET)
	public @ResponseBody List<String> getPersonFullNamesList(@PathVariable String personType, final Locale locale) 
	{
		List<String> result = new ArrayList<String>();
	 	List<Object> personNamesList = personService.getAllPersonFullNamesList(personType);
	 	for (Iterator<Object> i = personNamesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String)values[0]);
			if(((String)values[2]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[2]);
			}
			if(((String)values[1]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[1]);
			}
			result.add(personName);
		}
	    return result;
	}	 
	 
	/**
	 * Getting all vendor names for filtering purpose
	 *
	 * @return All vendor names for filtering purpose
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getVendorNamesList", method = RequestMethod.GET)
	public @ResponseBody List<String> getVendorNamesList() 
	{
		List<String> result = new ArrayList<String>();
	 	List<Object> vendorNamesList = personService.getAllVendorNamesList();
	 	for (Iterator<Object> i = vendorNamesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			String personVendorName = "";
			personVendorName = personVendorName.concat((String)values[0]);
			if(((String)values[1]) != null)
			{
				personVendorName = personVendorName.concat(" ");
				personVendorName = personVendorName.concat((String)values[1]);
			}
			result.add(personVendorName);
		}
	    return result;
	}
	
	/**
	 * Getting status types for filtering purpose
	 *
	 * @param locale
	 *            Is required to fetch the status types based on locale from properties file 
	 * @return Status types for filtering purpose
	 * @since 0.1
	 */
	@RequestMapping(value = "/users/getStatusList", method = RequestMethod.GET)
	public @ResponseBody
	List<String>  getStatusList(final Locale locale) 
	{			
		String[] status = messageSource.getMessage("values.project.status", null, locale).split(",");
		List<String> result = new ArrayList<String>();
		for (String string : status)
			result.add(string);
	    return result;	
	}

	/**
	 * --------------------------------------------------- End --------------------------------------------
	 */

	/** -------------------- Manage Roles -------------------- */

	Role roleObj = null;
	
	/**
	 * To Read the Role Index Page
	 */
	@RequestMapping(value = "/role", method = RequestMethod.GET)	
	public String indexRoles(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Manage Roles", 2, request);
		model.addAttribute("contact", new Contact());
		return "role";
	}

	
	/**
	 * To Activate and De-activate Role Status
	 * @param rlId Role Id
	 * @param operation Updating Status of Role
	 * @param model Role Details
	 * @param request none
	 * @param response none
	 * @param locale System Locale
	 * @return 
	 */
	@RequestMapping(value = "/role/RoleStatus/{rlId}/{operation}", method = RequestMethod.POST)
	public String RoleStatus(@PathVariable int rlId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{		
		roleService.setRoleStatus(rlId, operation, response, messageSource, locale);
		Role role=roleService.find(rlId);
		if(operation.equalsIgnoreCase("activate")){
			List<String> al= roleService.getUserNamesBasedOnUserId(rlId);
			Iterator<String> it=al.iterator();
			while(it.hasNext()){
				ldapBusinessModel.memberToRoles(role.getRlName(), it.next());			
			}
			
		}
		else{
			List<String> al= roleService.getUserNamesBasedOnUserId(rlId);
			logger.info("Remove----1------");
			Iterator<String> it=al.iterator();
			while(it.hasNext()){
				
				ldapBusinessModel.removeMemberFromRoles(role.getRlName(), it.next());	
			}
			ldapBusinessModel.getMemberAttributesNext(role.getRlName());
		}
		return null;
	}
	
	
	/**
	 * @return Role Object
	 */
	@RequestMapping(value = "/role/read", method = RequestMethod.POST)
	public @ResponseBody
	List<Role> readRoles() {
		List<Role> results1 = roleService.findAllWithoutInvalidDefault();
	
		return results1;
	}
	
	
	/**
	 * @param id RoleId
	 * @return Active Users Object
	 */
	@RequestMapping(value = "/role/getUserIdBasedOnRoleId/{id}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readUserIdBasedOnRoleId(@PathVariable int id) 
	{
		List<Map<String, Object>> users =  new ArrayList<Map<String, Object>>();
		
		List<?> results1 = roleService.getUserIdBasedOnRoleId(id);
		for (Iterator<?> i = results1.iterator(); i.hasNext();) 
		{
			Object[] values = (Object[]) i.next();						
			Map<String, Object> usersMap = new HashMap<String, Object>();
			
			usersMap.put("urId", (Integer)values[1]);
			usersMap.put("urLoginName", (String)values[0]);	  
			users.add(usersMap);
			
		}		
		return users;
	}

	
	/**
	 * For Creating The Role
	 * @param map Key-Value pairs
	 * @param role Model Class
	 * @param bindingResult Getting Errors
	 * @param model Role Details
	 * @param sessionStatus
	 * @param locale System Locale 
	 * @return
	 */
	@RequestMapping(value = "/role/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createRoles(@RequestBody Map<String, Object> map,
			@ModelAttribute Role role, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		role = roleService.getRoleObject(map, "save",role);
		validator.validate(role, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}

		else if (!(roleService.roleNameExistence(role, "save"))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage(
							"Unique.role.rlName", null, locale));
				}
			};
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}

		else {
			role = roleService.save(role);
			sessionStatus.setComplete();

			ldapBusinessModel.addRoles(role.getRlName(),role.getRlDescription());
			return role;
		}

	}
	
	
	/**
	 * For updating The Role Details
	 * @param map Key-Value pairs
	 * @param role Model Class
	 * @param bindingResult Getting Errors
	 * @param model Role Details
	 * @param sessionStatus
	 * @param locale System Locale 
	 * @return
	 */
	@RequestMapping(value = "/role/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateRoles(@RequestBody Map<String, Object> map,
			@ModelAttribute Role role, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		role = roleService.getRoleObject(map, "update",role);
		validator.validate(role, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}

		else if (!(roleService.roleNameExistence(role, "update"))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage(
							"Unique.role.rlName", null, locale));
				}
			};
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);
			return jsonErrorResponse;
		}

		else {
			role = roleService.update(role);
			sessionStatus.setComplete();
			ldapBusinessModel.editRoles((String) map.get("rlName"));
			return role;
		}

	}
	
	/**
	* @return Role Names
	*/
	@RequestMapping(value = "/role/getRoleNamesFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getRoleNames()
	{ 
		  Set<String> result = new HashSet<String>();
		    for (Role role : roleService.findAllWithoutInvalidDefault())
			{	
		    	result.add(role.getRlName());		    	
			}
	        return result;
	    }
		
	/**
	* @return Role Description
	*/
	@RequestMapping(value = "/role/getRoleDescriptionFilter", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getRoleDescription()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (Role role : roleService.findAllWithoutInvalidDefault())
			{	
		    	result.add(role.getRlDescription());		    	
			}
	        return result;
	    }
	
	
	/**
	* @return Role CreatedBy
	*/
	@RequestMapping(value = "/role/getRoleCreatedByFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getRoleCreatedBy()
	{ 
		  Set<String> result = new HashSet<String>();
		    for (Role role : roleService.findAllWithoutInvalidDefault())
			{	
		    	result.add(role.getCreatedBy());		    	
			}
	        return result;
	    }
	
	/**
	 * @return Last Updated By
	 */
	@RequestMapping(value = "/role/getRoleUpdatedByFilter", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getRoleUpdatedBy()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (Role role : roleService.findAllWithoutInvalidDefault())
			{	
		    	result.add(role.getLastUpdatedBy());		    	
			}
	        return result;
	    }
	

	/**
	* @return Role Status
	*/
	@RequestMapping(value = "/role/getRoleStatusFilter", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getRoleStatus()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (Role role : roleService.findAllWithoutInvalidDefault())
			{	
		    	result.add(role.getRlStatus());		    	
			}
	        return result;
	    }
	
	
	/**
	  * this method is used to display the view page of Groups
     * @since           1.0
	 */
	
	@RequestMapping(value = "/groups")
	public String indexGroups(ModelMap model, HttpServletRequest request) {
		logger.info("In index group method");
		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Manage Groups", 2, request);
		return "groups";
	}
	
	@RequestMapping(value = "/groups/getGroupDescription", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getGroupDescription()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (Groups group : groupService.findAllWithoutInvalidDefault())
			{	
		    	result.add(group.getGr_description());		    	
			}
	        return result;
	    }
	
	   /** 
	 * this method used to add groups in ldap
	 * @param      grId: to find the group name from database
     * @param      gr_name  :group name will be unique
     * @param      Description   : contains information like (group is created or not)
     * @since           1.0
     */
	@RequestMapping(value = "/groups/GroupStatus/{grId}/{operation}", method = RequestMethod.POST)
	public String GroupStatus(@PathVariable int grId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{	
		logger.info("In group status change method");
		groupService.setGroupStatus(grId, operation, response, messageSource, locale);
		Groups groups=groupService.find(grId);
		
		if(operation.equalsIgnoreCase("activate")){
			
			ldapBusinessModel.addGroups(groups.getGr_name(),groups.getGr_description());
		}
		else{
			ldapBusinessModel.deleteGroups(groups.getGr_name());

		}
		return null;
	}

	
	
	/**
	 *  Reading the complete  details of Groups from database 
	 *  @see com.bcits.bfm.service.userManagement.groupService#findAllWithoutInvalidDefault()
	 *    */
	@RequestMapping(value = "/groups/read", method = RequestMethod.POST)
	public @ResponseBody
	List<Groups> readGroups() {

		logger.info("In read groups data method");
		/*return groupService.getAll();*/
		return groupService.findAllWithoutInvalidDefault();
		

	}

	/**
	 *  Reading the  Group Names from Groups table 
	 *  @see com.bcits.bfm.service.userManagement.groupService#getGroupNames()
     *  @return :list of group Name    
     */
	@RequestMapping(value = "/groups/readGroupName", method = RequestMethod.GET)
	public @ResponseBody
	List<Groups> readGroupName() {

		logger.info("In read group names method");
		return groupService.getGroupNames();

	}
	
	
	/** 
	  * Update the  group 
	  *  @see com.bcits.bfm.service.userManagement.groupService#getGroupObject(Map<String, Object> map, String type,Groups groups)
      * @return       updated group record      
      * @since           1.0
	 */
	@RequestMapping(value = "/groups/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateGroups(@RequestBody Map<String, Object> map,
			@ModelAttribute("groups") Groups groups,
			BindingResult bindingResult, ModelMap mod,
			SessionStatus sessionStatus, final Locale locale) {

		logger.info("In update group data method");
		groups = groupService.getGroupObject(map, "update", groups);

		validator.validate(groups, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}

		else if (!(groupService.checkGroupUnique(groups, "update"))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage(
							"Unique.groups.gr_name", null, locale));
				}
			};

			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} else {
			groups = groupService.update(groups);

			sessionStatus.setComplete();

			ldapBusinessModel.editGorups(groups.getGr_name(),groups.getGr_description());
			return groups;
		}

	}

	
	
	
	/** 
     * this method is used to create groups and to store in database
     * @return         : returns the created groups
     * @since           1.0
     */
	
	@RequestMapping(value = "/groups/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Object createGroups(@RequestBody Map<String, Object> map,
			@ModelAttribute("groups") Groups groups,
			BindingResult bindingResult, ModelMap mod,
			SessionStatus sessionStatus, final Locale locale) {
		
		logger.info("In create group method");
		groups = groupService.getGroupObject(map, "save", groups);

		validator.validate(groups, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}

		else if (!(groupService.checkGroupUnique(groups, "save"))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage(
							"Unique.groups.gr_name", null, locale));
				}
			};

			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} else {
			
			groups = groupService.save(groups);

			sessionStatus.setComplete();
			return groups;
		}

	}

	
	
	/** 
     * this method is used to delete the  groups from groups table
     * @param       gr_id :for each record there will be a unique group Id.
     * @since           1.0
     */
	@RequestMapping(value = "/groups/delete", method = RequestMethod.POST)
	public @ResponseBody
	Groups deleteGroups(@RequestBody Map<String, Object> model) {
		
		logger.info("In delete group method");
		Groups groupobj = new Groups();
		int gr_id = (Integer) model.get("gr_id");

		logger.info("id is deleted=" + gr_id);
		groupService.delete(gr_id);

		// Deleting Groups into LDAP Server
		//ldapBusinessModel.deleteGroups((String) model.get("gr_name"));

		return groupobj;
	}

	
	
	/** 
     * this method is used to declare the groups status(ACtive/Inactive)
     * @return         :status of the group
     * @since           1.0
     */
	@RequestMapping(value = "/groups/getdata", method = RequestMethod.GET)
	public @ResponseBody
	List<Groups> getData() {
		logger.info("In get the group data method");
		List<Groups> result = new ArrayList<Groups>();
		Groups groups1 = new Groups();
		groups1.setGr_status("Active");

		Groups groups2 = new Groups();
		groups2.setGr_status("InActive");

		result.add(groups1);
		result.add(groups2);
		return result;
	}

	/** -------------------- End -------------------- */

	/** -------------------- Manage User Roles -------------------- */

	DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date dt = new Date();
	Calendar cal = Calendar.getInstance();

	/**
	* For Reading The Roles 
	* @return Role
	*/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userroles/list", method = RequestMethod.GET)
	public @ResponseBody
	List<?> categoriesUserRoles() {
		List usr = userRoleService.getActiveUsers();
		for (Iterator iterator = usr.iterator(); iterator.hasNext();) {
			Role role = (Role) iterator.next();
			String roleName = role.getRlName();
			String status = role.getRlStatus();
			
			if(status.equals("Active"))
			{
				logger.info("From Active Users");
				role.setRlName(roleName.concat("  {Active}"));
			}
			else if(status.equals("Inactive"))
			{
				logger.info("From Inactive Users");
				role.setRlName(roleName.concat("  {Inactive}"));
			}
		}		
		return usr;
	}

	/**
	 * for Reading All The Users
	 * @param model Users
	 * @return users
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userroles/usr", method = RequestMethod.POST)
	public @ResponseBody
	List<Users> readUserRoles(ModelMap model) 
	{
		List<Users> usr = new ArrayList<Users>();
		Users users = null;		
		List ug = userGroupService.findUsersIdName();	
		for (Iterator i = ug.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();			
			 users = new Users();
			 users.setUrLoginName((String)values[0]);
			 users.setUrId((Integer)values[1]);
			 usr.add(users);
		}	
		return usr;
	}

	/**
	 * @param model Role
	 * @param request none
	 * @return Role Details
	 */
	@SuppressWarnings({ "serial", "rawtypes" })
	@RequestMapping(value = "/userroles", method = RequestMethod.GET)
	public String indexUserRoles(Model model, HttpServletRequest request) {
		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("User   Roles", 2, request);
		logger.info("In index user roles method");
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		for (final Role record : roleService.getAll()) {
			result1.add(new HashMap<String, Object>() {
				{
					put("value", record.getRlId());
					put("text", record.getRlName());
				}
			});
		}
		model.addAttribute("rolenames", result1);

		List ug = userGroupService.findUsersIdName();			
		List<Map<String, Object>> result2 = new ArrayList<Map<String, Object>>();		
		Map<String, Object> mapUg = null;		
		for (Iterator i = ug.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			 mapUg = new HashMap<String, Object>();			 
			 mapUg.put("value", (Integer)values[1]);
			 mapUg.put("text", (String)values[0]);			 
			 result2.add(mapUg);
		}
		model.addAttribute("name", result2);
		return "userroles";
	}

	/**
	* @return UserRole Object
	*/
	@RequestMapping(value = "/userroles/read", method = RequestMethod.POST)
	public @ResponseBody
	List<UserRole> readUserRoles() {
		List<UserRole> results1 = null;// userrole.getAll();
		return results1;
	}

	/**
	 * For Assigning Available Users To Roles
	 * @param urid UserId
	 * @param roleId RoleId
	 * @param response Update Status
	 * @return null
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/userroles/update/{urid}/{roleId}", method = RequestMethod.POST)
	public List<Users> updateUserRoles(@PathVariable int urid,
			@PathVariable int roleId, HttpServletResponse response)
			throws IOException {
		logger.info("coming to updateUserRole");
		logger.info("User Id >> " + urid);
		logger.info("Role Id >>" + roleId);

		String status = userRoleService.checkUser(urid, roleId);
		logger.info(status);

		PrintWriter out;
		List<UserRole> results1 = null;
		if (status.equalsIgnoreCase("NotExists"))
		{
			String userName = (String) SessionData.getUserDetails().get("userID");

			UserRole role = new UserRole();
			role.setUro_Rl_Id(roleId);
			role.setUro_Ur_Id(urid);
			role.setCreatedBy(userName);
			role.setLastUpdatedBy(userName);
			role.setLastUpdatedDt(new Timestamp(dt.getTime()));
			userRoleService.save(role);
			
				try {
					out = response.getWriter();
					out.write("User Added Successfully");
					// out.write(responsechar);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			String uname = usersService.find(urid).getUrLoginName();
			String rname = roleService.find(roleId).getRlName();
			logger.info(">>>>>Uname is:"+uname+">>>>>>Rname is:"+rname);
			
			try {
				ldapBusinessModel.memberToRoles(rname,uname);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			results1 = userRoleService.getRlId(roleId);
		}
		else{
			try {
				out = response.getWriter();
				out.write("User Already Exists");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<Users> users = userRoleService.SelectRole(roleId);
		return null;
	}

	/**
	 * For Un-Assigning The User From Role
	 * @param urid
	 * @param roleId
	 * @param response Update Status
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/userroles/delete/{urid}/{roleId}", method = RequestMethod.POST)
	public void  deleteUserRoles(@PathVariable int urid,
			@PathVariable int roleId, HttpServletResponse response)
			throws IOException {
		logger.info("coming to deleteUserFromRole");
		logger.info("User Id >> " + urid);
		logger.info("Role Id >>" + roleId);

		PrintWriter out;
		List<UserRole> results1 = null;
				
		
		Users user = usersService.find(urid);
		Set<Role> i = user.getRoles();
		int count = i.size();
		if(count>1)
		{
			userRoleService.DeleteUser(roleId, urid);
			String uname = usersService.find(urid).getUrLoginName();
			String rname = roleService.find(roleId).getRlName();
			
			try {
				out = response.getWriter();
				out.write("User Removed Successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//deleteing user to the role
			try {
				ldapBusinessModel.removeMemberFromRoles(rname,uname);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			results1 = userRoleService.getRlId(roleId);
			List<Users> users = userRoleService.SelectRole(roleId);		
		}
		else if(count<=1)
		{
			out = response.getWriter();
			out.write("User has only one Role, cannot Be Un-assigned");
		}
		
	}

	/**
	 * For Reading The Users Based On RoleId
	 * @param roleId
	 * @param response None
	 * @return Users
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userroles/{roleId}", method = RequestMethod.POST)
	public @ResponseBody
	List<Users> getUserRolesId(@PathVariable int roleId,HttpServletResponse response)
	{
		List<Users> usr = new ArrayList<Users>();
		Users users = null;		
		List ug = userRoleService.SelectRole(roleId);	
		for (Iterator i = ug.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();			
			 users = new Users();
			 users.setUrLoginName((String)values[0]);
			 users.setUrId((Integer)values[1]);
			 usr.add(users);
		}	
		return usr;
	}
	

	/**
	* @return Users
	*/
	@SuppressWarnings("unused")
	@RequestMapping(value = "/userroles/getdata", method = RequestMethod.GET)
	public @ResponseBody
	List<Users> getUserRolesData() {
		logger.info("Coming to getdata");
		List<Users> result = new ArrayList<Users>();
		Users object = null;
		return result;
	}
	
	/**
	* To Read The Groups Object 
	* @return Groups Object
	*/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/usergroups/list", method = RequestMethod.GET)
	public @ResponseBody
	List<?> categoriesUserGroups() 
	{
		logger.info("In categories user groups method");
		List usr = userGroupService.getActiveGroups();
		for (Iterator iterator = usr.iterator(); iterator.hasNext();) {
			Groups group = (Groups) iterator.next();
			String groupName = group.getGr_name();
			String status = group.getGr_status();
			
			if(status.equals("Active"))
			{
				group.setGr_name(groupName.concat("  {Active}"));
			}
			else if(status.equals("Inactive"))
			{
				group.setGr_name(groupName.concat("  {Inactive}"));
			}
		}		
		return usr;
	}

	
	/**
	* @param model UserGroups
	* @return Users
	*/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/usergroups/usr", method = RequestMethod.POST)
	public @ResponseBody
	List<Users> readUserGroups(ModelMap model)
	{	
        logger.info("In read user groups method");
		List<Users> usr = new ArrayList<Users>();
		Users users = null;		
		List ug = userGroupService.findUsersIdName();		
		for (Iterator i = ug.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			 users = new Users();
			 users.setUrLoginName((String)values[0]);
			 users.setUrId((Integer)values[1]);
			 usr.add(users);
		}	
		return usr;
	}

	/**
	* @param model UserGroups
	* @param request none
	* @return usergroup
	*/
	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value = "/usergroups", method = RequestMethod.GET)
	public String indexUserGroups(Model model, HttpServletRequest request) {
		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("User  Groups", 2, request);
		logger.info("In index user categories method");
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		for (final Groups record : groupService.getAll()) {
			result1.add(new HashMap<String, Object>() {
				{
					put("value", record.getGr_id());
					put("text", record.getGr_name());
				}
			});
		}
		model.addAttribute("groups", result1);

		List ug = userGroupService.findUsersIdName();			
		List<Map<String, Object>> result2 = new ArrayList<Map<String, Object>>();		
		Map<String, Object> mapUg = null;		
		for (Iterator i = ug.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			 mapUg = new HashMap<String, Object>();			 
			 mapUg.put("value", (Integer)values[1]);
			 mapUg.put("text", (String)values[0]);			 
			 result2.add(mapUg);
		}			
		model.addAttribute("name", result2);
		return "usergroup";
	}

	/**
	* @return UsergGroups
	*/
	@RequestMapping(value = "/usergroups/read", method = RequestMethod.POST)
	public @ResponseBody
	List<UserGroup> readUserGroups() {
		logger.info("In read users groups method");
		List<UserGroup> results1 = null;
		return results1;
	}

	/**
	 * For Assigning Group To User
	 * @param ur_id UserId
	 * @param gr_id GroupId
	 * @param response none
	 * @return null
	 * @throws IOException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/usergroups/update/{ur_id}/{gr_id}", method = RequestMethod.POST)
	public List<Users> updateUserGroups(@PathVariable int ur_id,@PathVariable int gr_id, HttpServletResponse response) throws IOException {
		
		logger.info("In update users groups method");
		String status = userGroupService.checkUser(ur_id, gr_id);
		List<UserGroup> results1 = null;
		
		PrintWriter out;
		if (status.equalsIgnoreCase("NotExists")) 
		{		
			String userName = (String) SessionData.getUserDetails().get("userID");			
			UserGroup ug = new UserGroup();
			ug.setUG_GR_ID(gr_id);
			ug.setUG_UR_ID(ur_id);
			ug.setCREATED_BY(userName);
			ug.setLAST_UPDATED_BY(userName);
			ug.setLAST_UPDATED_DT(new Timestamp(dt.getTime()));
			userGroupService.AddUser(ug);
			
			try {
				out = response.getWriter();
				out.write("User Added Successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String uname = usersService.find(ur_id).getUrLoginName();
			String gname = groupService.find(gr_id).getGr_name();
			 ldapBusinessModel.memberToGorups(gname,uname);
			results1 = userGroupService.findById(gr_id);
		}
		else{
			try {
				out = response.getWriter();
				out.write("User Already Exist");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<Users> users = userGroupService.SelectGroup(gr_id);
		return null;
	}
	
	/**
	 * For Un-Assigning The User From Group
	 * @param urid UserId
	 * @param roleId RoleId
	 * @param response Update Status
	 * @throws IOException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/usergroups/delete/{ur_id}/{gr_id}", method = RequestMethod.POST)
	public void deleteUserGroups(@PathVariable int ur_id,@PathVariable int gr_id, HttpServletResponse response)	throws IOException {
		
		logger.info("In delete user groups method");
		PrintWriter out;
		String status = userGroupService.checkUser(ur_id, gr_id);

		List<UserGroup> results1 = null;
		Users user = usersService.find(ur_id);
		Set<Groups> i = user.getGroups();
		int count = i.size();
		if(count>1)
		{
			userGroupService.DeleteUser(gr_id, ur_id);
			try {
				out = response.getWriter();
				out.write("User Removed Successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			String uname = usersService.find(ur_id).getUrLoginName();
			String gname = groupService.find(gr_id).getGr_name();
			try {
				ldapBusinessModel.removeMemberFromGorups(gname,uname);
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			results1 = userGroupService.findById(gr_id);
			List<Users> users = userGroupService.SelectGroup(gr_id);
		}
		else if(count<=1)
		{
			out = response.getWriter();
			out.write("User has only one Group, cannot Be Un-assigned");
		}
	}

	/**
	 * For Reading The Users Based On GroupId
	 * @param groupId GroupId
	 * @param response None
	 * @return Users
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/usergroups/{gr_id}", method = RequestMethod.POST)
	public @ResponseBody
	List<Users> getUserGroupsId(@PathVariable int gr_id,HttpServletResponse response) {
		
		logger.info("In get the users groups id's method");
		List<Users> usr = new ArrayList<Users>();
		Users users = null;		
		List ug = userGroupService.SelectGroup(gr_id);	
		for (Iterator i = ug.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();			
			 users = new Users();
			 users.setUrLoginName((String)values[0]);
			 users.setUrId((Integer)values[1]);
			 usr.add(users);
		}	
		return usr;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/usergrooups/getdata", method = RequestMethod.GET)
	public @ResponseBody
	List<Users> getUserGroupsData() {
		logger.info("In get the user groups data method");
		List<Users> result = new ArrayList<Users>();
		Users object = null;
		return result;
	}

	/** -------------------- End -------------------- */

	/** -------------------- Manage Designation -------------------- */

	/** 
     * this method is used to display the Designation View page
     * @since           1.0
     */
	@RequestMapping(value = "/designation", method = RequestMethod.GET)
	public String indexDesignation(ModelMap model, HttpServletRequest request) {
		logger.info("In indesx designation method");
		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Manage Designation", 2, request);

		return "designation";
	}	
	
	@RequestMapping(value = "/designation/getdesignationDescriptionForFilter", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getdesignationDescriptionForFilter()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (Designation designation : designationService.getAll())
			{	
		    	result.add(designation.getDn_Description());		    	
			}
	        return result;
	 }
	
	/** 
     * this method provides the Designation Status depend upon the Id
  
     * @param      dnId   : Designation Id is unique and provides uniqueness to all Designation record
     * @param       dr_Status: Designation Status will be Active/Inactive 
     * @return       returns the  status of designation
     * @since           1.0
     */
	@RequestMapping(value = "/designation/DesignationStatus/{dnId}/{operation}", method = RequestMethod.POST)
	public String DesignationStatus(@PathVariable int dnId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{	
		logger.info("In designation status change method");
		designationService.setDesignationStatus(dnId, operation, response, messageSource, locale);
		return null;
	}

	/** 
     * this method is used to read the  Designation record from Database
     * @return       returns the  designation record
     * @since           1.0
     */
	@RequestMapping(value = "/designation/designationNameForFilter", method = RequestMethod.GET)
	public @ResponseBody
	List<Designation> getDesignationNameForFilter() {
		List<Designation> results1 = designationService.geteDesignationsNameForFilter();
		return results1;
	
	}
	
	/** 
     *this method is used to read designation records available in database.
     * @return          : list of designation records.
     * @since           1.0
     */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/designation/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readDesignation() {

	logger.info("In read designation method");	
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
    for (final Designation record :designationService.getAll()) {
        result.add(new HashMap<String, Object>() 
        {{
        	put("dn_Id",record.getDn_Id());
        	put("dn_Name", record.getDn_Name());
        	put("dn_Description", record.getDn_Description());
        	Department d=departmentService.find(record.getDept_Id());
        	put("dept_Name", d.getDept_Name());
        	put("dept_Id",record.getDepartment().getDept_Id());
        	put("dr_Status", record.getDr_Status());
        	put("created_By", record.getCreated_By());
        	put("last_Updated_By", record.getLast_Updated_By());
        }});
    }	

	return result;
	}		

	
	@RequestMapping(value = "/ownerDocument/read/{personId}",method = RequestMethod.POST)
	public @ResponseBody List<DocumentRepository> readOwnerPropertyDocRepo(@PathVariable int personId) throws SQLException
	{
		Person p=personService.find(personId);
		DocumentRepository obj = null;
		List<DocumentRepository> list = new ArrayList<DocumentRepository>();
		for(DocumentRepository doc : documentRepoService.getAllOndrgroupId(p.getDrGroupId()))
		{

			obj = new DocumentRepository();
			obj.setApproved(doc.getApproved());
			obj.setDocumentNumber(doc.getDocumentNumber());
			obj.setDocumentName(doc.getDocumentName());
			obj.setDrId(doc.getDrId());
			obj.setDocumentType(doc.getDocumentType());
			obj.setDocumentFormat(doc.getDocumentFormat());
			obj.setCreatedBy(doc.getCreatedBy());
			obj.setDocumentDescription(doc.getDocumentDescription());
			list.add(obj);
		}
		return list;
	}
	
	/** 
     * this method is used to update the Designation record in Designation table
     * @return       returns the updated  designation record .
     * @since           1.0
     */
	@RequestMapping(value = "/designation/update", method = RequestMethod.POST)
	@SuppressWarnings({ "serial", "unused" })
	public @ResponseBody
	Object  updateDesignation(@RequestBody ArrayList<Map<String, Object>> models,Designation designation,BindingResult bindingResult, ModelMap mod,	SessionStatus sessionStatus, final Locale locale) {
		logger.info("In designation update method");
		String userid=(String) SessionData.getUserDetails().get("userID");
		
		//List<Designation> designations = new ArrayList<Designation>();

		for (Map<String, Object> model : models) {
			designation=designationService.getDesignationObject(model, "update", designation);			
			//designations.add(designation);
		}
		validator.validate(designation, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}
		else if (!(designationService.checkDesignationUnique(designation, "update"))) {			
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage("Unique.designation.dn_Name", null, locale));
				}
			};
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} else {
			designationService.update(designation);
			sessionStatus.setComplete();
			return readDesignation();
		}
	}
	
	/** 
     * this method is used to create/insert the Designation record in Designation table
     * @return       returns the newly generated   Designation record.
     * @since           1.0
     */
	@RequestMapping(value = "/designation/create", method = RequestMethod.POST)
	@SuppressWarnings("unused")
	public @ResponseBody
	Object createDesignation(@RequestBody ArrayList<Map<String, Object>> models,String type,Designation designation,BindingResult bindingResult, ModelMap mod,SessionStatus sessionStatus, final Locale locale) {
		
		logger.info("In create designation method");		
		String username=(String) SessionData.getUserDetails().get("userID");		
		List<Designation> designations = new ArrayList<Designation>();
		for (Map<String, Object> model : models) {		
			designation=designationService.getDesignationObject(model, "save", designation);
			designations.add(designation);
		}
		validator.validate(designation, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		} else if (!(designationService.checkDesignationUnique(designation, "save"))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage("Unique.designation.dn_Name", null, locale));
				}
			};

			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} else {
			
		 designationService.saveList(designations);
			sessionStatus.setComplete();
			return designations;
		}
	
	}

	/** 
     * this method is used to delete the Designation record from  Designation table
     * @param        DesignationId: Designation ID is unique and used to delete the record
     * @since           1.0
     */
	@RequestMapping(value = "/designation/destroy", method = RequestMethod.POST)
	public @ResponseBody
	List<Designation> deleteDesignation(@RequestBody ArrayList<Map<String, Object>> models) {
		
		logger.info("In delete designation method");
		List<Designation> designations = new ArrayList<Designation>();

		for (Map<String, Object> model : models) {
			Designation designation = new Designation();

			designation.setDn_Id((Integer) model.get("dn_Id"));

			designations.add(designation);
		}

		designationService.delete(designations);

		return designations;
	}
	
	/** 
     * it returns the all record of available department for designation module
     * @return          returns department  information like(Id,Name,Description,Status)
     * @since           1.0
     */
	@RequestMapping(value = "/designation/getdata", method = RequestMethod.GET)
	public @ResponseBody
	List<Department> getDesignationData() {
		logger.info("In get the designations method");
		List<Department> result = new ArrayList<Department>();
		Department object = null;
		for (final Department record : departmentService.getAllActiveDepartments()) {
			object = new Department();
			object.setDept_Id(record.getDept_Id());
			object.setDept_Name(record.getDept_Name());
			object.setDept_Desc(record.getDept_Desc());
			object.setDept_Status(record.getDept_Status());
			result.add(object);
		}

		return result;
	}

	/** -------------------- End -------------------- */

	/** -------------------- Manage Department -------------------- */

	Department obj = null;

	
	/** 
     * this method is used to display the view page of department
     * @since           1.0
     */
	@RequestMapping(value = "/department", method = RequestMethod.GET)
	public String indexDepartmant(Model model, HttpServletRequest request) {
		logger.info("In index department method");
		model.addAttribute("ViewName", "System Security");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("System Security", 1, request);
		breadCrumbService.addNode("Manage Department", 2, request);

		return "department";
	}
	
	@RequestMapping(value = "/department/getDepartmentDescriptionForFilter", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getDepartmentDescriptionForFilter()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (Department dept : departmentService.findAllWithoutInvalidDefault())
			{	
		    	result.add(dept.getDept_Desc());		    	
			}
	        return result;
	 }
	
	
	/** 
     * this method provides the Department Status depend upon the Id
  
     * @param       Department ID: Department Id is unique and provides uniqueness to all department record
     * @param       Department Status: Department Status will be Active/Inactive 
     * @return       returns the  status of department
     * @since           1.0
     */
	@RequestMapping(value = "/department/DepartmentStatus/{depId}/{operation}", method = RequestMethod.POST)
	public String DepartmentStatus(@PathVariable int depId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{	
		logger.info("In department status change method");
		departmentService.setDepartmentStatus(depId, operation, response, messageSource, locale);
		return null;
	}

	

	/** 
     * this method is used to read the department record 
     * @return       returns the   department record
     * @since           1.0
     */
	@RequestMapping(value = "/department/read", method = RequestMethod.POST)
	public @ResponseBody List<Department> readDepartmant() {
		logger.info("In read department method");
		List<Department> results1 = departmentService.findAllWithoutInvalidDefault();
		return results1;
	}
		
	
	@RequestMapping(value = "/department/departmentNameForFilter", method = RequestMethod.GET)
	public @ResponseBody
	List<Department> getDepartmentNameForFilter() {
		List<Department> results1 = departmentService.getDepartmentsNameForFilter();
		return results1;
	}
		
	
	/** 
     * this method is used to create/insert the department record in department table
     * @return       returns the newly generated   department record.
     * @since           1.0
     */
	@RequestMapping(value = "/department/create", method = RequestMethod.POST)
	@SuppressWarnings("serial")
	public @ResponseBody Object createDepartmant(@RequestBody Map<String, Object> map,@ModelAttribute("department") Department department,BindingResult bindingResult, ModelMap mod,SessionStatus sessionStatus, final Locale locale) {

		logger.info("In create department method");
		department = departmentService.getDepartmentObject(map, "save",department);
		validator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}else if (!(departmentService.checkDepartmentUnique(department, "save"))) {			
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage("Unique.department.dept_Name", null, locale));
				}
			};
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} else {
			department = departmentService.save(department);
			sessionStatus.setComplete();
			return department;
		}
	}

	
	
	/** 
     * this method is used to update the department record in department table
     * @return       returns the updated  department record .
     * @since           1.0
     */
	@RequestMapping(value = "/department/update", method = RequestMethod.POST)
	public @ResponseBody Object updateDepartmant(@RequestBody Map<String, Object> map,@ModelAttribute("department") Department department,BindingResult bindingResult, ModelMap mod,SessionStatus sessionStatus, final Locale locale) {
		
		logger.info("In update department method");
		department = departmentService.getDepartmentObject(map, "update",department);
		validator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}
		else if (!(departmentService.checkDepartmentUnique(department, "update")))  {
		
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", messageSource.getMessage("Unique.department.dept_Name", null, locale));
				}
			};

			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		}

		else {
			department = departmentService.update(department);
			sessionStatus.setComplete();
			return department;
		}
	}	

	
	/** 
     * this method is used to delete the department record from  department table
     * @param        dept_Id: department ID is unique and used to delete the record
     * @since           1.0
     */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/department/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Department deleteDepartmant(@RequestBody Map<String, Object> model,	SessionStatus ss) {
		
		logger.info("In delete department method");
		Department target = new Department();
		int dept_Id = (Integer) model.get("dept_Id");
		departmentService.remove(dept_Id);
		ss.setComplete();
		return null;
	}

	/** -------------------- End -------------------- */
	
	/** -------------------- Cron Job Code -------------------- */
	@SuppressWarnings({ "rawtypes", "unused"})
	@Scheduled(cron = "${scheduling.job.activitiSmsAndMailCron}")
	public void cronJobCodeForSmsAndMail(){
		
		String emailId = "";
		String mobileNo = "";
		
		List<Users> approvedUsersList = usersService.getAllUsersBasedOnStatus(); 
		
		if (!approvedUsersList.isEmpty()) {
			
			logger.info("approvedUsersList is Not empty...!");
			
		
		
		for (Users user : approvedUsersList) {
		
			Set<String> rolesList = new HashSet<String>();
			Set<String> groupsList = new HashSet<String>();
			
			Set<Role> rolesSet = user.getRoles();
			
			for (Role role : rolesSet) {
				rolesList.add(role.getRlName());
			}
			
			Set<Groups> groupSet = user.getGroups();
			for (Groups group : groupSet) {
				groupsList.add(group.getGr_name());
			}
			
			
			
			Set<Contact> contact=user.getPerson().getContacts();
			for (Iterator iterator = contact.iterator(); iterator.hasNext();) {
												
				String personPassword = RandomPasswordGenerator.generateRandomPassword();
				
				Contact contact2 = (Contact) iterator.next();
				if((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))){
					emailId=contact2.getContactContent();
					

					EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
					  try {
					  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					logger.info("************************** Login Name"+user.getUrLoginName()+"******************************");  
					 String messageContent="<html>"
								+ "<style type=\"text/css\">"
								+ "table.hovertable {"
								+ "font-family: verdana,arial,sans-serif;"
								+ "font-size:11px;"
								+ "color:#333333;"
								+ "border-width: 1px;"
								+ "border-color: #999999;"
								+ "border-collapse: collapse;"
								+ "}"
								+ "table.hovertable th {"
								+ "background-color:#c3dde0;"
								+ "border-width: 1px;"
								+ "padding: 8px;"
								+ "border-style: solid;"
								+ "border-color: #394c2e;"
								+ "}"
								+ "table.hovertable tr {"
								+ "background-color:#88ab74;"
								+ "}"
								+ "table.hovertable td {"
								+ "border-width: 1px;"
								+ "padding: 8px;"
								+ "border-style: solid;"
								+ "border-color: #394c2e;"
								+ "}"
								+ "</style>"
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA ADMINISTRATION DEPARTMENT.</h2> <br /> Dear "+ user.getPerson().getFirstName() + ", Welcome to SKYON RWA <br/> <br/> "
								+ "Please use following UserName and Password to login. <br/> <br/>"
								+ "<table class=\"hovertable\" >" + "<tr>"
								+ "<td colspan='2'>Your Account Details are</td>"
								+ "</tr>" + "<tr>" + "<td> User Name :</td>"
								+ "<td>" + user.getUrLoginName() + "</td>"
								+ "</tr>" + "<tr>" + "<td> Password:</td>" + "<td>"
								+ personPassword + "</td>" + "</tr>" + "</table>"
								+ "<br/><br/>"
								+ "<a href=http://"+ bfmUrl
								+ ">click to login</a></body></html>"
								+ "<br/><br/>"
								+ "<br/>Thanks,<br/>"
								+ "SKYON Administration Department <br/> <br/>"
								+"</body></html>";	
					
					    emailCredentialsDetailsBean.setToAddressEmail(emailId);
						emailCredentialsDetailsBean.setMessageContent(messageContent);
					
						new Thread(new SendMailForUserDetails(emailCredentialsDetailsBean)).start();
					
				}
				if((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))){
					mobileNo=contact2.getContactContent();
					
					    String userMessage = "Dear "+user.getPerson().getFirstName()+", Your account has been "+"Activated"+". Your account details are username : "+user.getUrLoginName()+" & password : "+personPassword+" - SKYON RWA  Administration services.";	
					
					    SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
					    smsCredentialsDetailsBean.setNumber(mobileNo);
						smsCredentialsDetailsBean.setUserName(user.getPerson().getFirstName());
						smsCredentialsDetailsBean.setMessage(userMessage);
						
						new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
					
					
					
				}
				       String result =ldapBusinessModel.addUsers(user.getUrLoginName(), personPassword, emailId, mobileNo,rolesList, groupsList);
			}
			
		}
		
		}
		
	}
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/users/updatestatus/{urId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String userStatus(@PathVariable int urId, @PathVariable String operation,HttpServletResponse response,HttpServletRequest req){		
		
		HttpSession session = req.getSession(false);
		String userName=(String) session.getAttribute("userId");
		
		String emailId="";
		String mobileNo="";		
		Users user = usersService.find(urId);
		String message = "";
		
		String emailId1=null;
		String mobileNo1=null;	
		Users users1 = usersService.getUserInstanceBasedOnPersonId(user.getPersonId());
		Set<Contact> contact1=users1.getPerson().getContacts();
		logger.info("**********operation inside a users*********"+operation);
		logger.info("*********User Contact Size****************"+contact1.size());
		
		for (Contact contact : contact1) {
			if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
				logger.info("****Email Found*********");
				emailId1=contact.getContactContent();
				
				
				
			}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
				logger.info("****Mobile NO Found*********");
				mobileNo1= contact.getContactContent();
               
             }

		}
		
		if((emailId1==null||mobileNo1==null)&&operation.equalsIgnoreCase("Inprogress"))
		{
			
			return "Cannot Activate Without a Primary Email and Mobile Number of an User";
		}
		int transId=manPowerApprovalService.getTransactionId();
		//usersService.setUserStatus(urId, operation, response);
		if(operation.equalsIgnoreCase("Inprogress"))
		{
		
			if(transId != 0){
			manPowerApprovalService.updatePersonTransId(urId,transId);
			logger.info("Activation in process...");
			usersService.setUserStatus(urId, operation, response);
			int personid=manPowerApprovalService.getpersonId(urId);
			int level=manPowerApprovalService.getLevel(personid);
			if(level == 0){
				Users users = usersService.getUserInstanceBasedOnPersonId(user.getPersonId());
				Set<Contact> contact=users.getPerson().getContacts();
				
				Blob image=users.getPerson().getPersonImages();		
				manPowerApprovalService.updateUserStatus(urId,"Active");
				String personPassword = RandomPasswordGenerator.generateRandomPassword();

				for (Iterator iterator = contact.iterator(); iterator.hasNext();) {
					Contact contact2 = (Contact) iterator.next();
					if((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))){
						emailId=contact2.getContactContent();
						EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
						  try {
						  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  
						 String messageContent="<html>"
									+ "<style type=\"text/css\">"
									+ "table.hovertable {"
									+ "font-family: verdana,arial,sans-serif;"
									+ "font-size:11px;"
									+ "color:#333333;"
									+ "border-width: 1px;"
									+ "border-color: #999999;"
									+ "border-collapse: collapse;"
									+ "}"
									+ "table.hovertable th {"
									+ "background-color:#c3dde0;"
									+ "border-width: 1px;"
									+ "padding: 8px;"
									+ "border-style: solid;"
									+ "border-color: #394c2e;"
									+ "}"
									+ "table.hovertable tr {"
									+ "background-color:#88ab74;"
									+ "}"
									+ "table.hovertable td {"
									+ "border-width: 1px;"
									+ "padding: 8px;"
									+ "border-style: solid;"
									+ "border-color: #394c2e;"
									+ "}"
									+ "</style>"
									+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON ADMINISTRATION DEPARTMENT</h2> <br /> Dear "+ user.getPerson().getFirstName() + ", Welcome to SKYON <br/> <br/> "
									+ "Please use following UserName and Password to login. <br/> <br/>"
									+ "<table class=\"hovertable\" >" + "<tr>"
									+ "<td colspan='2'>Your Account Details are</td>"
									+ "</tr>" + "<tr>" + "<td> User Name :</td>"
									+ "<td>" + user.getUrLoginName() + "</td>"
									+ "</tr>" + "<tr>" + "<td> Password:</td>" + "<td>"
									+ personPassword + "</td>" + "</tr>" + "</table>"
									+ "<br/><br/>"
									+ "<a href=http://"+ bfmUrl
									+ ">click to login</a></body></html>"
									+ "<br/><br/>"
									+ "<br/>Thanks,<br/>"
									+ "Skyon Administration Department <br/> <br/>"
									+"</body></html>";	

						     emailCredentialsDetailsBean.setToAddressEmail(emailId);
							 emailCredentialsDetailsBean.setMessageContent(messageContent);
						
							new Thread(new SendMailForStaff(emailCredentialsDetailsBean)).start();
					}
					if((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes"))){

						mobileNo=contact2.getContactContent();
						 String userMessage = "Dear "+user.getPerson().getFirstName()+", Your account has been "+"Activated"+". Your account details are username : "+user.getUrLoginName()+" & password : "+personPassword+" - SKYON  Administration services.";	

							
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						   
						    smsCredentialsDetailsBean.setNumber(mobileNo);
							smsCredentialsDetailsBean.setUserName(user.getPerson().getFirstName());
							smsCredentialsDetailsBean.setMessage(userMessage);
							
							new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
					}
				}
				
				Set<String> rolesList = new HashSet<String>();
				Set<String> groupsList = new HashSet<String>();
				
				Set<Role> rolesSet = user.getRoles();
				
				for (Role role : rolesSet) {
					rolesList.add(role.getRlName());
				}
				
				Set<Groups> groupSet = user.getGroups();
				for (Groups group : groupSet) {
					groupsList.add(group.getGr_name());
				}
				
				ldapBusinessModel.addUsers(user.getUrLoginName(), personPassword, emailId, mobileNo,rolesList, groupsList);
			}
			
			
			
			Set<Role> rolesSet = user.getRoles();
			List<Role> roleList = new ArrayList<Role>(rolesSet);
			
			Set<Groups> groupsSet = user.getGroups();
			List<Groups> groupList = new ArrayList<Groups>(groupsSet);
			
			Users users = usersService.getUserInstanceBasedOnPersonId(user.getPersonId());
			Set<Contact> contact=users.getPerson().getContacts();
			Blob image=users.getPerson().getPersonImages();		
			
			for (Iterator iterator = contact.iterator(); iterator.hasNext();) {
				Contact contact2 = (Contact) iterator.next();
				if((contact2.getContactType().equalsIgnoreCase("Email")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes")))
					emailId=contact2.getContactContent();
				if((contact2.getContactType().equalsIgnoreCase("Mobile")) && (contact2.getContactPrimary().equalsIgnoreCase("Yes")))
					mobileNo=contact2.getContactContent();
			}
			
			logger.info("EmailId of the person!!");
			
	
			
			if(level == 0){
				message = "User Activated Successfully...";
			}else{
				message = "Record Sent for approval...";
				
		       for (final TransactionDetail record : manpowerNotificationService.getDesignations(transId)) {

			    ManPowerNotification manPowerNotification=new ManPowerNotification();
					
			
				manPowerNotification.setRequestId(urId);
				manPowerNotification.setDesigId(record.getDnId());
				manPowerNotification.setRequestedBy(userName);
				manPowerNotification.setRequestedDate(user.getLastUpdatedDt());
				manPowerNotification.setManPowerStatus(0);
				manpowerNotificationService.save(manPowerNotification);
			}
			}
			}else{
				message = "WorkFlow is not Configured";
			}
		}
		
		else if(operation.equalsIgnoreCase("Inactive")){
			usersService.setUserStatus(urId, operation, response);
			Set<String> rolesList = new HashSet<String>();
			Set<String> groupsList = new HashSet<String>();
			
			Set<Role> roleSet = user.getRoles();
			
			for (Role role : roleSet) {
				rolesList.add(role.getRlName());
			}
			
			Set<Groups> groupSet = user.getGroups();
			for (Groups group : groupSet) {
				groupsList.add(group.getGr_name());
			}
			
			ldapBusinessModel.deleteUsers(user.getUrLoginName(), rolesList, groupsList);
			message = "User Deactivated Successfully...";
			int personid=manPowerApprovalService.getpersonId(urId);
			manPowerApprovalService.updateReqInLevel(personid,1);
			manPowerApprovalService.deleteApprovalData(urId);
		}
		else
		{
			message = "Unable to process request..pls contact Administrator";
			
			//jsonResponse.setResult("Unable to process request..pls contact Administrator");
		}
		
		
		return message;
	}


	@RequestMapping(value = "/customer/ProfileDetailsNew", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object ReadProfileNew(@ModelAttribute("users")Users users,HttpServletRequest req) {
		String user=req.getParameter("username");

		Users u=usersService.getUserInstanceByLoginName(user);
		List <Contact>c=contactService.getContactContent2(u.getPersonId());
	    Map<String,String> contactMap = new HashMap<>();	

	for (Contact contact : c) {
		
		if(contact.getContactType().equalsIgnoreCase("Email")){
			contactMap.put("email", contact.getContactContent());
		}else{
			contactMap.put("mobile", contact.getContactContent());
		}
		
	}
		
		return contactMap;
	}
	
	//roles and groups Filter
	@RequestMapping(value = "/users/rolesFilter", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Set<?> rolesNameFilter(){
		
		Set<String> data=new HashSet<String>();
		
		List<?> list=usersService.getRoleGroupFilter();
		
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
		{
			Object[] d=(Object[])iterator.next();
			data.add((String)d[0]);
			
			
			
		}
		
		
		return data;
	}
	
	@RequestMapping(value = "/users/groupsFilter", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Set<?> rolesNameFilter1(){
		
		Set<String> data=new HashSet<String>();
		
		List<?> list=usersService.getRoleGroupFilter();
		
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
		{
			Object[] d=(Object[])iterator.next();
			data.add((String)d[1]);
			
			
			
		}
		
		
		return data;
	}
	
	@RequestMapping(value = "/userTemplate/userTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSUsersFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> userTemplateEntities = usersService.getAllUsers();
	
               
        String sheetName = "Templates";//name of sheet

    	XSSFWorkbook wb = new XSSFWorkbook();
    	XSSFSheet sheet = wb.createSheet(sheetName) ;
    	
    	XSSFRow header = sheet.createRow(0);    	
    	
    	XSSFCellStyle style = wb.createCellStyle();
    	style.setWrapText(true);
    	XSSFFont font = wb.createFont();
    	font.setFontName(HSSFFont.FONT_ARIAL);
    	font.setFontHeightInPoints((short)10);
    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    	style.setFont(font);
    	
        header.createCell(0).setCellValue("Person Name");
        header.createCell(1).setCellValue("Login Name");
        header.createCell(2).setCellValue("Staff Type");
        header.createCell(3).setCellValue("Vendor Name");
        header.createCell(4).setCellValue("Department");        
        header.createCell(5).setCellValue("Designation");
        header.createCell(6).setCellValue("Roles");
        header.createCell(7).setCellValue("Groups");
        header.createCell(8).setCellValue("User Status");
        
        for(int j = 0; j<=8; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
        }
        
        int count = 1;
        XSSFCell cell = null;
        List<String> ls = new ArrayList<String>();
    	for (Object[] person :userTemplateEntities ) {
    		
    		if(ls.contains((String)person[1])){
    			continue;
    		}
    		ls.add((String)person[1]);
    		XSSFRow row = sheet.createRow(count);
    		
    		int value = count+1;
    		
    		String str="";
    		if((String)person[4]!=null){
				str=(String)person[4];
			}
              System.out.println((String)person[3]+" "+str);
    		row.createCell(0).setCellValue((String)person[3]+" "+str);
    		
    		row.createCell(1).setCellValue((String)person[1]);
    		
			
    		
    	    row.createCell(2).setCellValue((String)person[17]);
    		
    		row.createCell(3).setCellValue((String) person[20]+" "+(String) person[21]);
    		row.createCell(4).setCellValue((String) person[12]);
    		row.createCell(5).setCellValue((String) person[10]);
    		String str1="";
    		String roleStatus = (String) person[22];
			if (roleStatus.equalsIgnoreCase("Active"))
				str1=(((String) person[14]).concat("  {Active}"));
			else
				str1=(((String) person[14]).concat("  {Inactive}"));
    		row.createCell(6).setCellValue(str1);
    		String str2="";
    		String groupStatus = (String) person[23];
			if (groupStatus.equalsIgnoreCase("Active"))
				str2=(((String) person[16])
						.concat("  {Active}"));
			else
				str2=(((String) person[16])
						.concat("  {Inactive}"));
    		row.createCell(7).setCellValue(str2);
    		row.createCell(8).setCellValue((String)person[8]);
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"UsersTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/userPdfTemplate/userPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportUsersPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> userTemplateEntities = usersService.getAllUsers();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(10);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
       
    	
        
       
        table.addCell(new Paragraph("Image",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Login Name",font1));
        table.addCell(new Paragraph("Staff Type",font1));
        table.addCell(new Paragraph("Vendor Name",font1));
        table.addCell(new Paragraph("Department",font1));
        table.addCell(new Paragraph("Designation",font1));
        table.addCell(new Paragraph("Roles",font1));
        table.addCell(new Paragraph("Groups",font1));
        table.addCell(new Paragraph("User Status",font1));
      
        List<String> ls = new ArrayList<String>();
    	for (Object[] person :userTemplateEntities) {
    		
    		if(ls.contains((String)person[1])){
    			continue;
    		}
    		ls.add((String)person[1]);
    		
    		Blob blobImage = personService.getImage((Integer)person[2]);
    		int blobLength = 0;
    		byte[] blobAsBytes = null;
    		if (blobImage != null) {
    			blobLength = (int) blobImage.length();
    			blobAsBytes = blobImage.getBytes(1, blobLength);
    		}
    		 Image image2 = Image.getInstance(blobAsBytes);
    		 PdfPCell cell1 = new PdfPCell(image2,true);
    		 String str="";
     		if((String)person[4]!=null){
 				str=(String)person[4];
 			}
             PdfPCell cell2 = new PdfPCell(new Paragraph((String)person[3]+" "+str,font3));
       
        PdfPCell cell3 = new PdfPCell(new Paragraph((String)person[1],font3));
    
        PdfPCell cell4 = new PdfPCell(new Paragraph((String)person[17],font3));
       
        PdfPCell cell5 = new PdfPCell(new Paragraph((String) person[20]+" "+(String) person[21],font3));
        
        PdfPCell cell6 = new PdfPCell(new Paragraph((String) person[12],font3));
        
       
        
        PdfPCell cell7 = new PdfPCell(new Paragraph((String) person[10],font3));
        String str1="";
		String roleStatus = (String) person[22];
		if (roleStatus.equalsIgnoreCase("Active"))
			str1=(((String) person[14]).concat("  {Active}"));
		else
			str1=(((String) person[14]).concat("  {Inactive}"));
        
        PdfPCell cell8 = new PdfPCell(new Paragraph(str1,font3));
        
        String str2="";
		String groupStatus = (String) person[23];
		if (groupStatus.equalsIgnoreCase("Active"))
			str2=(((String) person[16])
					.concat("  {Active}"));
		else
			str2=(((String) person[16])
					.concat("  {Inactive}"));
        
        PdfPCell cell9 = new PdfPCell(new Paragraph(str2,font3));
        PdfPCell cell10 = new PdfPCell(new Paragraph((String)person[8],font3));
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        table.setWidthPercentage(100);
        float[] columnWidths = {8f, 14f, 12f, 10f, 14f, 13f, 15f, 10f, 10f , 12f};

        table.setWidths(columnWidths);
    		
		}
        
         document.add(table);
        document.close();

    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	baos.writeTo(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=\"UsersTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
}
