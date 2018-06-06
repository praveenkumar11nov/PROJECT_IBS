package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.City;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Country;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.ProjectLocation;
import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.StaffExperience;
import com.bcits.bfm.model.StaffNotices;
import com.bcits.bfm.model.StaffTraining;
import com.bcits.bfm.model.State;
import com.bcits.bfm.model.UserRole;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.VendorsManagement.RequisitionService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.CityService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.CountryService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.StateService;
import com.bcits.bfm.service.facilityManagement.ManpowerService;
import com.bcits.bfm.service.facilityManagement.PatrolTrackService;
import com.bcits.bfm.service.facilityManagement.StaffExperienceService;
import com.bcits.bfm.service.facilityManagement.StaffNoticesService;
import com.bcits.bfm.service.facilityManagement.StaffTrainingService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.novell.ldap.LDAPException;

/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Manpower Management 
 * Use Case : Manpower Master, Staff Experience, Staff Training, Staff Notices
 * 
 * @author Aizaz
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class ManpowerMangementController {
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ManpowerService manpowerService;
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	@Autowired
	private UsersService usersService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private DesignationService designationService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PersonService personService;
	@Autowired
	private DrGroupIdService drGroupIdService;
	@Autowired
	private Validator validator;
	@Autowired
	private StaffNoticesService staffNoticesService;
	@Autowired
	private StaffTrainingService staffTrainingService;
	@Autowired
	private StaffExperienceService staffExperienceService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PatrolTrackService patrolTrackService;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private DateTimeCalender dateTimeCalender;
	@Autowired
	private CountryService countryService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private CommonService commonService;
	@Autowired
	private RequisitionService requisitionService;
	
	private static final Log logger = LogFactory.getLog(ManpowerMangementController.class);


	/**
	 * Index Method to the manpower view
	 * 
	 * @param model 		ModelMap Object
	 * @param request		HttpServletRequest Object
	 * @param locale		System Locale
	 * @return		Redirect to manpower view
	 */
	@RequestMapping(value = "/manpowerindex", method = RequestMethod.GET)
	public String indexManpower(ModelMap model, HttpServletRequest request,	final Locale locale) {
		logger.info("Manpower Index");
		model.addAttribute("ViewName", "Man Power");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manpower Resource", 1, request);
		breadCrumbService.addNode("Manage Staff Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		model.addAttribute("personStyle",commonController.populateCategories("personStyle", locale));
		model.addAttribute("title",	commonController.populateCategories("title", locale));
		model.addAttribute("maritalStatus",	commonController.populateCategories("maritalStatus", locale));
		model.addAttribute("sex",commonController.populateCategories("sex", locale));
		model.addAttribute("nationality",commonController.populateCategories("nationality", locale));
		model.addAttribute("bloodGroup",commonController.populateCategories("bloodGroup", locale));
		model.addAttribute("meCategory",commonController.populateCategories("meCategory", locale));

		return "manpower/manpower";
	}

	/**
	 * Index method to the Staff Experience 
	 * 
	 * @param model 	ModelMap Object
	 * @param request	HttpServletRequest Object
	 * @return redirect to the staff experience view
	 */
	@RequestMapping(value = "/manpowerse", method = RequestMethod.GET)
	public String indexStaffExperience(ModelMap model,HttpServletRequest request) {
		logger.info("Staff Experience Index");
		model.addAttribute("ViewName", "Man Power");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manpower  Resource", 1, request);
		breadCrumbService.addNode("Manage Staff Experience", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "manpower/staffexperience";
	}


	/**
	 * Index method to the Staff Notice 
	 * 
	 * @param model 	ModelMap Object
	 * @param request	HttpServletRequest Object
	 * @return redirect to the staff notices view
	 */
	@RequestMapping(value = "/manpowersn", method = RequestMethod.GET)
	public String indexStaffNotice(ModelMap model, HttpServletRequest request) {
		logger.info("Staff Notices Index");
		model.addAttribute("ViewName", "Man Power");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manpower Resource", 1, request);
		breadCrumbService.addNode("Manage Staff Notices", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "manpower/staffnotices";
	}


	/**
	 * Index method to the Staff Training 
	 * 
	 * @param model 	ModelMap Object
	 * @param request	HttpServletRequest Object
	 * @return redirect to the staff training view
	 */
	@RequestMapping(value = "/manpowerst", method = RequestMethod.GET)
	public String indexStaffTraining(ModelMap model, HttpServletRequest request) {
		logger.info("Staff Training Index");
		model.addAttribute("ViewName", "Man Power");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manpower Resource", 1, request);
		breadCrumbService.addNode("Manage Staff Training", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "manpower/stafftrainings";
	}

	
	/** 
	  *  Reading Staff
	  *
	  * @return          List of Staff for the view
	  * @since           1.0
	  */
	@RequestMapping(value = "/manpower/read", method = RequestMethod.POST)
	public @ResponseBody
	List<Map<String, Object>> readManpower() {
		logger.info("Read All Staff");
		List<Map<String, Object>> usersObj = personService.getUserPerson();
		return commonController.getSortedList(usersObj, "urId");
	}

	
	/** 
	  *  Creating new Staff with user and staff information provided in the view
	  *
	  * @param person  		Object of the model Person  
	  * @param users  		Object of the model Users  
	  * @param locale		System locale
	  * @param	bindingResult	Binding result object
	  * @param map  		Map Object to read the value from view  
	  * @return          	List of Staff
	  * @since           	1.0
	  */
	@RequestMapping(value = "/manpower/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Object createUsers(@RequestBody Map<String, Object> map,@ModelAttribute("manpower") Person person, Users users,	BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale) {
		logger.info("Manpower Create");
		int personId = 0;
		if (map.get("personId") instanceof Integer && (Integer)map.get("personId")!=0) {
			personId = (Integer) map.get("personId");
			person = personService.find(personId);
		} else {
			person = personService.getPersonObject(map, "save", person);
			drGroupIdService.save(new DrGroupId(person.getCreatedBy(), person.getLastUpdatedDt()));
			person.setDrGroupId(drGroupIdService.getNextVal(person.getCreatedBy(), person.getLastUpdatedDt()));
			person.setPersonStyle("Individual");
			person.setPersonType("Staff");
			person.setWorkNature("None");;
			person.setOccupation("None");
		}
		users = usersService.getUserObject(map, "save", users);
		users.setPerson(person);
		JsonResponse errorResponse = new JsonResponse();
		validator.validate(person, bindingResult);
		validator.validate(users, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
		
			return errorResponse;
		} else if (!(usersService.checkUserLoginNameUniqueness(users, "save"))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {{
					put("invalid", messageSource.getMessage("Unique.users.urLoginName", null, locale));
			}};
			errorResponse.setStatus("INVALID");
			errorResponse.setResult(errorMapResponse);
		
			return errorResponse;
		} else {
			try {
				if (map.get("personId") instanceof Integer) {
					users = usersService.update(users);
					personService.updatePersonType(person, "Staff");
				} else {
					users = usersService.save(users);
				}
				List<Map<String, Object>> userlist = manpowerService.setAllFeildforManpower(users);
				users.setUrId(usersService.getUserInstanceByLoginName(users.getUrLoginName()).getUrId());
				sessionStatus.setComplete();
				person.setPersonImages(null);
				return userlist;
			} catch (Exception e) {
				e.printStackTrace();
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {{
						put("exception", messageSource.getMessage("Unique.users.savingException", null, locale));
				}};
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;
			}
		}
	}

	/** 
	  *  Updating Staff with user and staff information provided in the view
	  *
	  * @param person  		Object of the model Person  
	  * @param users  		Object of the model Users  
	  * @param locale		System locale
	  * @param	bindingResult	Binding result object
	  * @param map  		Map Object to read the value from view  
	  * @return          	List of Staff
	  * @since           	1.0
	  */
	@RequestMapping(value = "/manpower/update", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody
	Object updateUsers(@RequestBody Map<String, Object> map,@ModelAttribute("manpower") Person person, Users users,BindingResult bindingResult, ModelMap model,	SessionStatus sessionStatus, final Locale locale) {
		logger.info("Manpower Update");
		JsonResponse errorResponse = new JsonResponse();
		try {
			person = personService.getPersonObject(map, "update", person);
			users = usersService.getUserObject(map, "update", users);
			users.setPerson(person);
			validator.validate(person, bindingResult);
			validator.validate(users, bindingResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
		
			return errorResponse;
		} else {
			try {
				users = usersService.update(users);
				usersService.modifyToLdap(users, "update", null);
				List<Map<String, Object>> userlist = manpowerService.setAllFeildforManpower(users);
				users.setUrId(usersService.getUserInstanceByLoginName(users.getUrLoginName()).getUrId());
				sessionStatus.setComplete();
				users.getPerson().setPersonImages(null);
				return userlist;
			} catch (Exception e) {
				e.printStackTrace();
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {{
						put("exception", messageSource.getMessage("Unique.users.savingException", null, locale));
				}};
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;
			}
		}
	}

	/** 
	  *  Read all the staff name and id for the drop-downs in the view
	  *
	  *	@param	request					
	  * @return          	List of Staff Name and Id
	  * @since           	1.0
	  */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/getMPerson", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllPersonNames(HttpServletRequest request) {
		logger.info("method : getAllPersonNames");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Object[] manpower : personService.findAllStaff()) {
			result.add(new HashMap<String, Object>() {{
					put("pn_Name", (String) manpower[1] + " " + (String) manpower[2]);
					put("personId", manpower[0]);
			}});
		}
		return result;
	}

	/** 
	  *  Read all the distinct staff name for the filters 
	  *
	  * @param result  		List of Staff Names
	  * @return          	List of Staff Names
	  * @since           	1.0
	  */
	@RequestMapping(value = "/manpower/getPerson", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getPersonNamesFilterList() {
		logger.info("method : getPersonNamesFilterList");
		List<String> result = new ArrayList<String>();
		for (Object[] person : personService.findAllStaff()) {
			result.add((String) person[1] + " " + (String) person[2]);
		}
		return result;
	}
	
	
	/**
	 * Create a new experience record for the staff
	 * 
	 * @param personId  	Staff Id
	 * @param startDate  	start date for formatting
	 * @param endDate  		end date for formatting
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/experience/create/{personId}", method = RequestMethod.GET)
	public @ResponseBody List<?> createStaffExperience(@PathVariable int personId,@ModelAttribute("staffexperience") StaffExperience staffExperience, BindingResult bindingResult,HttpServletRequest request) throws ParseException {

		logger.info("method : createStaffExperience");
		String startDate = request.getParameter("startDate");
		staffExperience.setStartDate(dateTimeCalender.getDateToStore(startDate));
		String endDate = request.getParameter("endDate");
		staffExperience.setEndDate(dateTimeCalender.getDateToStore(endDate));
		staffExperience.setPersonId(personId);
		staffExperience.setWorkDesc(staffExperience.getWorkDesc().trim());
		staffExperienceService.save(staffExperience);
		return expDetails(personId);
	}
	
	/**
	 * Update Experience record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @param startDate  	start date for formatting
	 * @param endDate  		end date for formatting
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/experience/update/{personId}", method = RequestMethod.GET)
	public @ResponseBody List<?> updateStaffExperience(@ModelAttribute("staffexperience") StaffExperience staffExperience, @PathVariable int personId,	BindingResult bindingResult,HttpServletRequest request) throws ParseException {

		logger.info("method : updateStaffExperience");
		String startDate = request.getParameter("startDate");
		staffExperience.setStartDate(dateTimeCalender.getDateToStore(startDate));
		String endDate = request.getParameter("endDate");
		staffExperience.setEndDate(dateTimeCalender.getDateToStore(endDate));
		staffExperience.setPersonId(personId);
		staffExperience.setWorkDesc(staffExperience.getWorkDesc().trim());
		staffExperienceService.update(staffExperience);
		return expDetails(personId);
	}

	/**
	 * Delete Experience record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/experience/destroy", method = {RequestMethod.POST ,RequestMethod.GET}, produces = "application/json", consumes = "application/json" )
	public @ResponseBody
	StaffExperience deleteStaffExperience(@ModelAttribute("staffexperience") StaffExperience staffExperience, HttpServletRequest request) {
		
		logger.info("method : deleteStaffExperience");
		staffExperienceService.delete(staffExperience.getSeId());
		staffExperience.setLastUpdateDate(null);
		staffExperience.setStartDate(null);
		staffExperience.setEndDate(null);
		return staffExperience;
	}
	
	/**
	 * Read all the Experience record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/experience/expDetails/{personId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> expDetails(@PathVariable int personId) {
		
		logger.info("method : expDetails");
		List<Map<String, Object>> experience = new ArrayList<Map<String, Object>>();
		for (final StaffExperience record : staffExperienceService.findById(personId)) {experience.add(new HashMap<String, Object>() {{
					put("seId", record.getSeId());
					put("pwSlno", record.getPwSlno());
					put("company", record.getCompany());
					put("designation", record.getDesignation());
					java.util.Date startDateUtil =  record.getStartDate();
					java.sql.Date startDateSql = new java.sql.Date(startDateUtil.getTime());
					java.util.Date endDateUtil =  record.getEndDate();
					java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());			
					put("startDate",startDateSql);
					put("endDate", endDateSql);
					put("workDesc", record.getWorkDesc());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					if(record.getLastUpdateDate()!=null){
						java.util.Date lastUpdateDtUtil = record.getLastUpdateDate();
						java.sql.Date lastUpdateDtSql = new java.sql.Date(lastUpdateDtUtil.getTime());
						put("lastUpdateDate", lastUpdateDtSql);
					}
					put("createdBy", record.getCreatedBy());
				}
			});
		}
		return experience;
	}

	/**
	 * Read all the Staff as a master for the Staff experience
	 * 
	 * @return          	List of Staff
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/experience/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readStaffExperience() {
		logger.info("method : readStaffExperience");
		List<Map<String, Object>> manpower = new ArrayList<Map<String, Object>>();
		for (final Object[] record : personService.findAllStaff()) {
			manpower.add(new HashMap<String, Object>() {{
					put("personId", record[0]);
					put("pn_Name", (String) record[1] + " "	+ (String) record[2]);
					put("dob", record[3]);
					put("occu", record[4]);
					put("lang", record[5]);
					put("personType", record[6]);
					put("title", record[8]);
					put("fatherName", record[9]);
					put("designation", record[10]);
					put("department", record[11]);
					put("staffType", record[12]);
					put("loginName", record[13]);
					put("userStatus", record[14]);
					put("roles", record[15]);
					put("groups", "Default");
					List<StaffExperience> lis = staffExperienceService
							.findById((Integer) record[0]);
					int diffMonth = 0;
					int diffYear = 0;
					for (int i = 0; i < lis.size(); i++) {
						Calendar startCalendar = new GregorianCalendar();
						startCalendar.setTime(lis.get(i).getStartDate());
						Calendar endCalendar = new GregorianCalendar();
						endCalendar.setTime(lis.get(i).getEndDate());
						diffYear = endCalendar.get(Calendar.YEAR)
								- startCalendar.get(Calendar.YEAR);
						diffMonth = diffMonth + diffYear * 12
								+ endCalendar.get(Calendar.MONTH)
								- startCalendar.get(Calendar.MONTH);
					}
					if (diffMonth == 0) {
						put("total_exp", "Fresher");
					} else {
						int years = diffMonth / 12;
						int remainingMonths = diffMonth % 12;
						put("total_exp", years + " years, " + remainingMonths
								+ " months");
					}
				}
			});
		}
		return manpower;
	}
	
	/**
	 * Create a new notice record for the staff
	 * 
	 * @param personId  	Staff Id
	 * @param snDate  		Notice Date for formatting
	 * @param snActionDate  Notice Action Date for formatting
	 * @param personId  	Staff Id
	 * @return          	List of Staff's notice records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/notice/create/{personId}", method =RequestMethod.GET)
	public @ResponseBody List<?> createStaffNotice(@ModelAttribute("staffnotices") StaffNotices staffNotice,@PathVariable int personId, BindingResult bindingResult,HttpServletRequest request) throws ParseException {

		logger.info("method : createStaffNotice");
		String snDate = request.getParameter("snDate");
		staffNotice.setSnDate(dateTimeCalender.getDateToStore(snDate));
		String snActionDate = request.getParameter("snActionDate");
		staffNotice.setSnActionDate(dateTimeCalender.getDateToStore(snActionDate));
		staffNotice.setDescription(staffNotice.getDescription().trim());
		staffNoticesService.save(staffNotice);
		return noticeDetails(personId);
	}
	
	/**
	 * Update a new notice record for the staff
	 * 
	 * @param personId  	Staff Id
	 * @param snDate  		Notice Date for formatting
	 * @param snActionDate  Notice Action Date for formatting
	 * @return          	List of Staff's notice records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/notice/update/{personId}", method = RequestMethod.GET)
	public @ResponseBody List<?> updateStaffNotice(@ModelAttribute("staffnotices") StaffNotices staffNotice,@PathVariable int personId, BindingResult bindingResult,HttpServletRequest request) throws ParseException {

		logger.info("method : updateStaffNotice");
		staffNotice.setPersonId(personId);
		String snDate = request.getParameter("snDate");
		staffNotice.setSnDate(dateTimeCalender.getDateToStore(snDate));
		String snActionDate = request.getParameter("snActionDate");
		staffNotice.setSnActionDate(dateTimeCalender.getDateToStore(snActionDate));
		staffNotice.setDescription(staffNotice.getDescription().trim());
		staffNoticesService.update(staffNotice);
		return noticeDetails(personId);
	}
	
	/**
	 * Delete Notice record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @return          	List of Staff's notice records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/notice/destroy", method = RequestMethod.GET)
	public @ResponseBody
	StaffNotices deleteStaffNotices(@ModelAttribute("staffnotices") StaffNotices staffNotice) {
		
		logger.info("method : deleteStaffNotices");
		staffNoticesService.delete(staffNotice.getSnId());
		staffNotice.setLastUpdatedDate(null);
		staffNotice.setSnActionDate(null);
		staffNotice.setSnDate(null);
		return staffNotice;
	}
	
	/**
	 * Read all the Notice record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @return          	List of Staff's notice records
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/notice/noticeDetails/{personId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> noticeDetails(@PathVariable int personId) {

		logger.info("method : noticeDetails");
		List<Map<String, Object>> manpower = new ArrayList<Map<String, Object>>();
		for (final StaffNotices record : staffNoticesService.findById(personId)) {
			manpower.add(new HashMap<String, Object>() {{
					if(record.getLastUpdatedDate()!=null){
						java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
						java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
						put("lastUpdatedDate", lastUpdatedDtSql);
					}
					java.util.Date snDateUtil =  record.getSnDate();
					java.sql.Date snDateSql = new java.sql.Date(snDateUtil.getTime());
					java.util.Date snActionDateUtil =  record.getSnActionDate();
					java.sql.Date snActionDateSql = new java.sql.Date(snActionDateUtil.getTime());
					put("snId", record.getSnId());
					put("noticeType", record.getNoticeType());
					put("description", record.getDescription());
					put("snActionDate", snActionDateSql);
					put("snAction", record.getSnAction());
					put("snDate", snDateSql);
					put("lastUpdatedBy", record.getLastUpdateBy());
					put("createdBy", record.getCreatedBy());
					put("personId", record.getPerson().getPersonId());
					put("personName", record.getPerson().getFirstName() + " "+ record.getPerson().getLastName());
				}
			});
		}
		return manpower;
	}
	
	/**
	 * Read all the Staff as a master for the Staff notice
	 * 
	 * @return          	List of Staff
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/notice/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readStaffNotices() {
		
		logger.info("method : readStaffNotices");
		List<Map<String, Object>> manpower = new ArrayList<Map<String, Object>>();
		for (final Object[] record : personService.findAllStaff()) {
			manpower.add(new HashMap<String, Object>() {{
					put("personId", record[0]);
					put("personName", (String) record[1] + " "	+ (String) record[2]);
					put("dob", record[3]);
					put("occu", record[4]);
					put("lang", record[5]);
					put("personType", record[6]);
					put("title", record[8]);
					put("fatherName", record[9]);
					put("designation", record[10]);
					put("department", record[11]);
					put("staffType", record[12]);
					put("loginName", record[13]);
					put("userStatus", record[14]);
					put("roles", record[15]);
					put("groups", "Default");
					List<Object[]> lis = staffNoticesService
							.getCountOfNotices((Integer) record[0]);
					for (Object[] result : lis) {
						String name = (String) result[0];
						int count = ((Number) result[1]).intValue();
						if (name.equalsIgnoreCase("Appreciation"))
							put("appr", count);
						if (name.equalsIgnoreCase("Incident"))
							put("incident", count);
						if (name.equalsIgnoreCase("Warning"))
							put("warning", count);
					}
				}
			});
		}
		return manpower;
	}
	
	/**
	 * Upload notice document of the staff
	 * 
	 * @param snId			Staff Notice Id
	 * @param docType		Document Type
	 * @param blob			Document to upload 
	 * @return          	none
	 * @since           	1.0
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/manpower/notice/upload", method = RequestMethod.POST)
	public @ResponseBody
	void uploadNoticeDocument(@RequestParam MultipartFile files,	HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {
		
		logger.info("method : uploadNoticeDocument");
		int snId = Integer.parseInt(request.getParameter("snId"));
		String docType = request.getParameter("type");
		Blob blob;
		blob = Hibernate.createBlob(files.getInputStream());
		staffNoticesService.uploadNoticeOnId(snId, blob , docType);
	}

	
	/**
	 * Download notice document of the staff
	 * 
	 * @param snId			Staff Notice Id
	 * @return          	document or get redirect to file-not-found error page 
	 * @since           	1.0
	 */
	@RequestMapping("/manpower/notice/download/{snId}")
	public String downloadNotice(@PathVariable("snId") Integer snId,HttpServletResponse response) throws Exception {
		
		logger.info("method : downloadNotice");		
		StaffNotices sn = staffNoticesService.find(snId);
		if (sn.getNoticeDocument() != null) {
			try {
				response.setHeader("Content-Disposition","inline;filename=\"" + sn.getPerson().getFirstName()+ " " + sn.getPerson().getLastName() + " - "+ sn.getNoticeType() + " Notice"+sn.getNoticeDocumentType().toLowerCase());
				OutputStream out = response.getOutputStream();
				if(StringUtils.equalsIgnoreCase(".doc", sn.getNoticeDocumentType()) || StringUtils.equalsIgnoreCase(".docx", sn.getNoticeDocumentType()))
					response.setContentType("application/msword");
				else if(StringUtils.equalsIgnoreCase(".xls", sn.getNoticeDocumentType()) || StringUtils.equalsIgnoreCase(".xlsx", sn.getNoticeDocumentType()))
					response.setContentType("application/vnd.ms-excel");
				else
					response.setContentType(sn.getNoticeDocumentType());
				IOUtils.copy(sn.getNoticeDocument().getBinaryStream(), out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return "filenotfound";
		}
	}

	/**
	 * Create Training record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @param fromDate  	training start date for formatting
	 * @param toDate  		training end date for formatting
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/train/create/{personId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createStaffTraining(@PathVariable int personId,@ModelAttribute("stafftraining") StaffTraining staffTraining,BindingResult bindingResult,HttpServletRequest request) throws ParseException {

		logger.info("method : createStaffTraining");	
		String fromDate = request.getParameter("fromDate");
		staffTraining.setFromDate(dateTimeCalender.getDateToStore(fromDate));
		String toDate = request.getParameter("toDate");
		staffTraining.setToDate(dateTimeCalender.getDateToStore(toDate));
		staffTraining.setPersonId(personId);
		staffTraining.setTrainingDesc(staffTraining.getTrainingDesc().trim());
		staffTrainingService.save(staffTraining);
		return trainingDetails(personId);
	}
	
	
	@RequestMapping(value = "/manpower/stafftrainingNameUnique/{className}/{attribute}/{attribute1}/{SelectedRowId}", method = { RequestMethod.GET,	RequestMethod.POST })
	public @ResponseBody List<?> getUniqueTrainingName(@PathVariable String className,@PathVariable String attribute,@PathVariable String attribute1,@PathVariable int SelectedRowId) throws Exception {
		logger.info("In billing parameter getUniqueParamName method");
		return staffTrainingService.getUniqueTrainingName(className,attribute,attribute1,SelectedRowId);
	}

	/**
	 * Update Training record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @param fromDate  	training from date for formatting
	 * @param toDate  		training to date for formatting
	 * @return          	List of Staff's training records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/train/update/{personId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateStaffTraining(@PathVariable int personId,@ModelAttribute("stafftraining") StaffTraining staffTraining,	BindingResult bindingResult,HttpServletRequest request) throws ParseException {

		logger.info("method : updateStaffTraining");
		String fromDate = request.getParameter("fromDate");
		staffTraining.setFromDate(dateTimeCalender.getDateToStore(fromDate));
		String toDate = request.getParameter("toDate");
		staffTraining.setToDate(dateTimeCalender.getDateToStore(toDate));
		staffTraining.setPersonId(personId);
		staffTraining.setTrainingDesc(staffTraining.getTrainingDesc().trim());
		staffTraining = staffTrainingService.update(staffTraining);
		return trainingDetails(personId);
	}
	
	/**
	 * Create Training record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @param fromDate  	start date for formatting
	 * @param toDate  		end date for formatting
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/manpower/train/destroy", method = RequestMethod.GET)
	public @ResponseBody
	StaffTraining deleteStaffTraining(@ModelAttribute("stafftraining") StaffTraining staffTraining) {
		
		logger.info("method : deleteStaffTraining");
		staffTrainingService.delete(staffTraining.getStId());
		staffTraining.setLastUpdatedDate(null);
		staffTraining.setFromDate(null);
		staffTraining.setToDate(null);
		return staffTraining;
	}

	/**
	 * Read all the Staff as a master for the Staff training
	 * 
	 * @return          	List of Staff
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/train/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readStaffTraining() {
		
		logger.info("method : readStaffTraining");
		List<Map<String, Object>> manpower = new ArrayList<Map<String, Object>>();
		for (final Object[] record : personService.findAllStaff()) {
			manpower.add(new HashMap<String, Object>() {{
					put("personId", record[0]);
					put("pn_Name", (String) record[1] + " "	+ (String) record[2]);
					put("dob", record[3]);
					put("occu", record[4]);
					put("lang", record[5]);
					put("personType", record[6]);
					put("title", record[8]);
					put("fatherName", record[9]);
					put("designation", record[10]);
					put("department", record[11]);
					put("staffType", record[12]);
					put("loginName", record[13]);
					put("userStatus", record[14]);
					put("roles", record[15]);
					put("groups", "Default");
				}
			});
		}
		return manpower;
	}
	
	/**
	 * Read all the Training record of the Staff
	 * 
	 * @param personId  	Staff Id
	 * @return          	List of Staff's training records
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/train/expDetails/{personId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> trainingDetails(@PathVariable int personId) {
		
		logger.info("method : trainingDetails");
		List<Map<String, Object>> training = new ArrayList<Map<String, Object>>();
		for (final StaffTraining record : staffTrainingService.findById(personId)) {
			training.add(new HashMap<String, Object>() {{
					java.util.Date fromDateUtil =  record.getFromDate();
					java.sql.Date fromDateSql = new java.sql.Date(fromDateUtil.getTime());
					java.util.Date toDateUtil =  record.getToDate();
					java.sql.Date toDateSql = new java.sql.Date(toDateUtil.getTime());		
					put("stId", record.getStId());
					put("ptSlno", record.getPtSlno());
					put("trainingName", record.getTrainingName());
					put("trainedBy", record.getTrainedBy());
					put("fromDate", fromDateSql);
					put("toDate", toDateSql);
					put("trainingDesc", record.getTrainingDesc());
					put("certificationAch", record.getCertificationAch());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					if(record.getLastUpdatedDate()!=null){
						java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
						java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
						put("lastUpdatedDate", lastUpdatedDtSql);
					}
					put("createdBy", record.getCreatedBy());
				}
			});
		}
		return training;
	}
	
	
	/**
	 * Upload training document of the staff
	 * 
	 * @param stId			Staff Training Id
	 * @param docType		Document Type
	 * @param blob			Document to upload 
	 * @return          	none
	 * @since           	1.0
	 */
	@SuppressWarnings({ "deprecation" })
	@RequestMapping(value = "/manpower/training/upload", method = RequestMethod.POST)
	public @ResponseBody void uploadTrainingCertificate(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {
		
		logger.info("method : uploadTrainingCertificate");
		int stId = Integer.parseInt(request.getParameter("stId"));
		String docType = request.getParameter("type");
		Blob blob;
		blob = Hibernate.createBlob(files.getInputStream());
		staffTrainingService.uploadCertificateOnId(stId, blob,docType);
	}
	
	/**
	 * Download training document of the staff
	 * 
	 * @param stId			Staff Training Id
	 * @return          	training document or redirect to the file-not-found error page
	 * @since           	1.0
	 */
	@RequestMapping("/manpower/train/download/{stId}")
	public String downloadTrainigCertificate(@PathVariable("stId") Integer stId, HttpServletResponse response)throws Exception {
		StaffTraining st = staffTrainingService.find(stId);
		if (st.getTrainingDocument() != null) {
			try {
				response.setHeader("Content-Disposition", "inline;filename=\""
						+ st.getPerson().getFirstName() + " "
						+ st.getPerson().getLastName()
						+ " - Training Cerificate"+st.getTrainingDocumentType());
				OutputStream out = response.getOutputStream();
				
				if(StringUtils.equalsIgnoreCase(".doc", st.getTrainingDocumentType()) || StringUtils.equalsIgnoreCase(".docx", st.getTrainingDocumentType()))
					response.setContentType("application/msword");
				else if(StringUtils.equalsIgnoreCase(".xls", st.getTrainingDocumentType()) || StringUtils.equalsIgnoreCase(".xlsx", st.getTrainingDocumentType()))
					response.setContentType("application/vnd.ms-excel");
				else
					response.setContentType(st.getTrainingDocumentType());
				IOUtils.copy(st.getTrainingDocument().getBinaryStream(), out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return "filenotfound";
		}
	}
	
	
	/**
	 * Read all Person with person type vendor and staff for drop-down
	 * 
	 * @param result		List of all vendor and staff
	 * @return          	List of all vendor and staff
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/getStaffAndVendorName", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getStaffAndVendorName() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		for (final Object[] assetLocDropDown : personService.getStaffAndVendorName()) {
			result.add(new HashMap<String, Object>() {{
				put("personName", (String)assetLocDropDown[1]+" "+(String)assetLocDropDown[2]);
				put("personId", (Integer)assetLocDropDown[0]);
				put("personType", (String)assetLocDropDown[3]);
				put("contactType", (String)assetLocDropDown[4]);
				put("contactContent", (String)assetLocDropDown[5]);
			}});
		}
		return result;
	}
	
	/**
	 * Read all Person with person type vendor or staff for drop-down
	 * 
	 * @param result		List of all vendor and staff
	 * @return          	List of all vendor and staff
	 * @since           	1.0
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/getStaffOrVendorName/{personType}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getStaffOrVendorName(@PathVariable String personType) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		for (final Object[] assetLocDropDown : personService.getStaffOrVendorName(personType)) {
			result.add(new HashMap<String, Object>() {{
				put("personName", (String)assetLocDropDown[1]+" "+(String)assetLocDropDown[2]);
				put("personId", (Integer)assetLocDropDown[0]);
				put("personType", (String)assetLocDropDown[3]);
				put("contactType", (String)assetLocDropDown[4]);
				put("contactContent", (String)assetLocDropDown[5]);
			}});
		}
		return result;
	}
	
	/**
	 * Read all contact details related to the address
	 * 
	 * @param addressId		Address Id
	 * @param contact		List of contact belongs to the given address
	 * @return          	List of all vendor and staff
	 * @since           	1.0
	 */	
	@RequestMapping(value = "/manpower/getContacts", method = RequestMethod.POST)
	public @ResponseBody
	List<Contact> getContacts(@RequestParam("addressId") int addressId,	HttpServletRequest request, HttpServletResponse response) {
		List<Contact> contact = contactService.getContactsOnAddressId(addressId);
		return contact;
	}

	/**
	 * Upload image for the staff and change the image in LDAP as well
	 * 
	 * @param personId		Staff Id
	 * @param file			Document File as a MultipartFile
	 * @param blob			Document to upload
	 * @return          	none
	 * @since           	1.0
	 */	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/person/upload/personImage", method = RequestMethod.POST)
	public @ResponseBody
	void save(@RequestParam MultipartFile files, HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {

		int personId = Integer.parseInt(request.getParameter("personId"));
		Blob blob;
		blob = Hibernate.createBlob(files.getInputStream());
		personService.uploadImageOnId(personId, blob);
		byte[] imsgeBytes = files.getBytes();
		List<String> list = usersService.findbyPersonId(personId);
		List<String> attributesList = new ArrayList<String>();
		attributesList.add("status");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("personId", personId);
		List<?> listofStatus = commonService.selectQuery("Users",attributesList, params);
		if(listofStatus.size()>0 && listofStatus!=null){
			if (listofStatus.get(0).toString().equalsIgnoreCase("Active")) {
				ldapBusinessModel.setImage(list.get(0).toString(), imsgeBytes);
			}
		}
		//return "";
	}

	/**
	 * Read image of the staff
	 * 
	 * @param personId		Staff Id
	 * @param blobImage		Image in BLOB Type
	 * @return          	Displays the image in the screen
	 * @since           	1.0
	 */	
	@RequestMapping("/person/getpersonimage/{personId}")
	public String getImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int personId)	throws LDAPException, Exception {

		response.setContentType("image/jpeg");
		Blob blobImage = personService.getImage(personId);
		int blobLength = 0;
		byte[] blobAsBytes = null;
		if (blobImage != null) {
			blobLength = (int) blobImage.length();
			blobAsBytes = blobImage.getBytes(1, blobLength);
		}
		OutputStream ot = response.getOutputStream();
		try {
			ot.write(blobAsBytes);
			ot.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

	
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public String indexAddress(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Address");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manage persons", 1, request);
		return "com/address";
	}

	/**
	 * Read All the Addresses of the persons
	 * 
	 * @param personId		Person Id
	 * @return          	List of all addresses of the person
	 * @since           	1.0
	 */	
	@RequestMapping(value = "/address/read/{personId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> readAddress(@PathVariable int personId) {
		return addressService.findAllAddressesBasedOnPersonID(personId);
	}

	
	/**
	 * Create Address record of the Person
	 * 
	 * @param personId  	Person Id
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/address/create/{personId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createAddress(@PathVariable int personId,@RequestBody Map<String, Object> map,@ModelAttribute("address") Address address,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale) {
		JsonResponse errorResponse = new JsonResponse();
		if (map.get("addressPrimary").equals("Yes")) {
			List<Address> listOfAddr = addressService.checkForUniquePrimary(personId, "Yes");
			if (listOfAddr.size() > 0) {
				errorResponse.setStatus("PRIMARY");
				errorResponse.setResult(bindingResult.getAllErrors());
				BfmLogger.logger.info("Binding result: " + bindingResult);
				return errorResponse;
			}
		}
		address = addressService.getAddressObject(map, "save", address);
		Contact contact1 = setContactFeilds(personId, map, "emailContent",	"Email");
		Contact contact2 = setContactFeilds(personId, map, "phoneContent",	"Mobile");

		validator.validate(contact1, bindingResult);
		validator.validate(contact2, bindingResult);
		validator.validate(address, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: " + bindingResult);
			return errorResponse;
		} else {
			try {
				Person person = new Person();
				person = personService.find(personId);
				Set<Address> addressSet = person.getAddresses();
				addressSet.add(address);
				person.setAddresses(addressSet);
				personService.update(person);
				int addressId = addressService.getAddressIdBasedOnCreatedByAndLastUpdatedDt(address.getCreatedBy(),	address.getLastUpdatedDt());
				person.setAddresses(null);
				contact1.setAddressId(addressId);
				contact2.setAddressId(addressId);
				Set<Contact> contactSet1 = person.getContacts();
				contactSet1.add(contact1);
				person.setContacts(contactSet1);
				Set<Contact> contactSet2 = person.getContacts();
				contactSet2.add(contact2);
				person.setContacts(contactSet2);
				personService.update(person);
				sessionStatus.setComplete();
			} catch (Exception e) {
				e.printStackTrace();
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorResponse);
				return errorResponse;
			}
		}
		address.setAddressSeasonFrom(null);
		address.setAddressSeasonTo(null);
		address.setLastUpdatedDt(null);
		return address;
	}

	/**
	 * Support Method to set the values for contact details while adding address
	 * 
	 * @param personId  	Person Id
	 * @return          	List of Staff's experience records
	 * @see					createAddress()
	 * @since           	1.0
	 */
	public Contact setContactFeilds(int personId, Map<String, Object> map,
			String content, String type) {
		Contact contact = new Contact();
		contact.setPersonId(personId);
		contact.setContactLocation((String) map.get("addressLocation"));
		contact.setContactType(type);
		contact.setContactPrimary((String) map.get("addressPrimary"));
		contact.setContactContent((String) map.get(content));
		contact.setCreatedBy((String) SessionData.getUserDetails()
				.get("userID"));
		contact.setLastUpdatedBy((String) SessionData.getUserDetails().get(
				"userID"));
		java.util.Date date1 = new java.util.Date();
		contact.setLastUpdatedDt(new Timestamp(date1.getTime()));

		List<Contact> listOfcontact = contactService.checkForUniquePrimary(
				personId, type, "Yes");
		if (listOfcontact.size() > 0) {
			contact.setContactPrimary("No");
		} else {
			contact.setContactPrimary("Yes");
		}

		return contact;
	}

	/**
	 * Update Address record of the Person
	 * 
	 * @param personId  	Person Id
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = "/address/update/{personId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object updateAddress(@RequestBody Map<String, Object> map,
			@ModelAttribute("address") Address address,
			BindingResult bindingResult, @PathVariable int personId,
			ModelMap model, SessionStatus sessionStatus, final Locale locale)
			throws Exception {
		JsonResponse errorResponse = new JsonResponse();
		String addressPrimary = addressService.getAddressPrimary((Integer) map
				.get("addressId"));
		if (map.get("addressPrimary").equals("Yes")
				&& addressPrimary.equalsIgnoreCase("No")) {
			List<Address> listOfAddr = addressService.checkForUniquePrimary(
					(Integer) map.get("personId"), "Yes");
			if (listOfAddr.size() > 0) {
				errorResponse.setStatus("PRIMARY");
				errorResponse.setResult(bindingResult.getAllErrors());
				BfmLogger.logger.info("Binding result: " + bindingResult);

				return errorResponse;
			}
		}

		address = addressService.getAddressObject(map, "update", address);
		validator.validate(address, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("binding result: " + bindingResult);

			return errorResponse;
		}

		else {
			address = addressService.update(address);
			sessionStatus.setComplete();
			return address;
		}
	}

	/**
	 * Delete Address record of the Person
	 * 
	 * @param personId  	Person Id
	 * @return          	List of Staff's experience records
	 * @since           	1.0
	 */
	@RequestMapping(value = {"/conciergeVendor/address/delete","/manpower/address/delete","/owner/address/delete","/tenant/address/delete","/dom/address/delete","/family/address/delete"}, method = RequestMethod.POST)
	public @ResponseBody
	Object deleteAddress(@RequestBody Map<String, Object> map,
			@ModelAttribute("address") Address address,
			BindingResult bindingResult, SessionStatus sessionStatus) {
		addressService.delete((int) map.get("addressId"));
		contactService.updateAddressiIdContact((int) map.get("addressId"));
		sessionStatus.setComplete();
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus("DELETE");
		jsonResponse.setResult("Deleted Successfully");
		return jsonResponse;
	}

	
	/**
	 * Read all Address Location for drop-down
	 * 
	 * @param locale System Locale
	 * @return  address location list
	 */
	@RequestMapping(value = "/address/addressLocationChecks", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, String>> getAddressLocation(final Locale locale) {

		String[] addressLocation = messageSource.getMessage(
				"values.address.addressLocation", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < addressLocation.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", addressLocation[i]);
			map.put("name", addressLocation[i]);

			result.add(map);
		}

		return result;

	}

	/**
	 * Read all address primary for drop down
	 * 
	 * @param locale	System Locale
	 * @return  address primary list
	 */
	@RequestMapping(value = "/address/addressPrimaryChecks", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, String>> getAddressPrimary(final Locale locale) {

		String[] addressPrimary = messageSource.getMessage(
				"values.address.addressPrimary", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < addressPrimary.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", addressPrimary[i]);
			map.put("name", addressPrimary[i]);

			result.add(map);
		}

		return result;

	}

	/**
	 * Read all countries
	 * 
	 * @return List of all countries
	 */
	@RequestMapping(value = "/address/getCountry", method = RequestMethod.GET)
	public @ResponseBody
	List<Country> getCountry() {
		List<Country> list = countryService.findAll();
		return list;
	}

	/**
	 * Read all states
	 * 
	 * @return List of all states
	 */
	@RequestMapping(value = "/address/getState", method = RequestMethod.GET)
	public @ResponseBody
	List<State> getState() {
		return stateService.findAll();
	}

	/**
	 * Read all Project Location
	 * 
	 * @return List of all Project Location
	 */
	@RequestMapping(value = "/address/getProjectLocation", method = RequestMethod.GET)
	public @ResponseBody
	List<ProjectLocation> getProjectLocation() {
		return stateService.findAllProjectLocation();
	}

	/**
	 * Read All cities
	 * 
	 * @return List of all cities
	 */
	@RequestMapping(value = "/address/getCity", method = RequestMethod.GET)
	public @ResponseBody
	List<City> getCity() {
		return cityService.findAll();
	}

	/**
	 * Read all address season for drop-down
	 * 
	 * @param locale System Locale
	 * @return list of all address season
	 */
	@RequestMapping(value = "/address/addressSeasonChecks", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, String>> getAddressSeason(final Locale locale) {

		String[] addressSeason = messageSource.getMessage(
				"values.address.addressSeason", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < addressSeason.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", addressSeason[i]);
			map.put("name", addressSeason[i]);

			result.add(map);
		}

		return result;

	}

	
	/**
	 * Index to the Contact
	 * 
	 * @param model 	ModelMap Object 
	 * @param request	HttpServletRequest object
	 * @return redirect to the contact view page
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String indexContact(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Contact");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manage contact", 1, request);
		return "com/contact";
	}

	
	/**
	 * Read all contact of the person
	 * 
	 * @param personId Person Id
	 * 
	 * @return List of contacts
	 * 
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/contact/read/{personId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> readContact(@PathVariable int personId) {

		List<Map<String, Object>> contact = new ArrayList<Map<String, Object>>();
		for (final Contact contactList : contactService
				.findAllContactsBasedOnPersonID(personId)) {
			contact.add(new HashMap<String, Object>() {
				{
					put("contactId", contactList.getContactId());
					put("contactLocation", contactList.getContactLocation());
					put("personId", contactList.getPersonId());
					put("contactType", contactList.getContactType());
					put("contactPrimary", contactList.getContactPrimary());
					put("contactContent", contactList.getContactContent());
					put("contactPrefferedTime",
							contactList.getContactPreferredTime());
					if (contactList.getContactSeasonFrom() != null
							&& contactList.getContactSeasonTo() != null) {
						put("contactSeason", true);
					} else {
						put("contactSeason", false);
					}
					if(contactList.getContactSeasonFrom()!=null)
						put("contactSeasonFrom", ConvertDate.TimeStampString(contactList.getContactSeasonFrom()));
					if(contactList.getContactSeasonTo()!=null)
						put("contactSeasonTo", ConvertDate.TimeStampString(contactList.getContactSeasonTo()));
					put("addressId", contactList.getAddressId());
					put("createdBy", contactList.getCreatedBy());
					put("lastUpdatedBy", contactList.getLastUpdatedBy());
					put("lastUpdatedDt", contactList.getLastUpdatedDt());
				}
			});
		}
		return contact;
	}

	/**
	 * Create new contact record for the person
	 * 
	 * @param personId 		Person Id
	 * @param map 			Contact Details form the view
	 * @param contact		Contact Object
	 * @param bindingResult	Binding Result Object
	 * @param model			ModelMAp Object
	 * @param sessionStatus	Session Status
	 * @param locale		System Locale
	 * @return				Contact Object
	 */
	@RequestMapping(value = "/contact/create/{personId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createContact(@PathVariable int personId,
			@RequestBody Map<String, Object> map,
			@ModelAttribute("contact") Contact contact,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {
		JsonResponse errorResponse = new JsonResponse();
		if (map.get("contactPrimary").equals("Yes")) {
			List<Contact> listOfcontact = contactService.checkForUniquePrimary(
					personId, (String) map.get("contactType"),
					(String) map.get("contactPrimary"));
			if (listOfcontact.size() > 0) {
				errorResponse.setStatus("PRIMARY");
				errorResponse.setResult(bindingResult.getAllErrors());
				BfmLogger.logger.info("Binding result: " + bindingResult);
				return errorResponse;
			}
		}
		String mobregex = "^[0-9]{1,45}$";
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if ((map.get("contactType").equals("Mobile")
				|| map.get("contactType").equals("HomePhone") || map.get(
				"contactType").equals("Intercom"))
				&& !((String) map.get("contactContent")).matches(mobregex)) {
			bindingResult.reject("contactType",
					"Invalid " + map.get("contactType") + " Number");
			;
		} else if (map.get("contactType").equals("Email")
				&& !((String) map.get("contactContent")).matches(emailreg)) {
			bindingResult.reject("contactType", "Invalid Email Address");
			;
		}

		contact = contactService.getContactObject(map, "save", contact);
		validator.validate(contact, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: " + bindingResult);
			return errorResponse;
		} else {
			Person person = new Person();
			person = personService.find(personId);
			/*
			 * person =
			 * personService.getPersonInstanceBasedOnCreatedByAndLastUpdatedDt
			 * (address.getCreatedBy());
			 */
			Set<Contact> contactSet = person.getContacts();
			contactSet.add(contact);
			person.setContacts(contactSet);
			personService.update(person);
			sessionStatus.setComplete();
			contact.setContactId(contactService
					.getContactIdBasedOnCreatedByAndLastUpdatedDt(
							contact.getCreatedBy(), contact.getLastUpdatedDt()));

			return contact;
		}

	}

	/**
	 * Update contact information of the person
	 * 
	 * @param map				Contact Details
	 * @param contact			Contact Object
	 * @param bindingResult		Binding Result Object
	 * @param model				ModelMap Object
	 * @param sessionStatus		Session Status Object
	 * @param locale			System Locale
	 * @return					Contact Object
	 * @throws Exception
	 */
	@RequestMapping(value = "/contact/update/{personId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object updateContact(@RequestBody Map<String, Object> map,@ModelAttribute("contact") Contact contact,BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) throws Exception {

		JsonResponse errorResponse = new JsonResponse();
		String queryCheckPrim = "SELECT c From Contact c WHERE c.contactId = "+(Integer) map.get("contactId");
		List<Contact> checkPrimExists = contactService.executeSimpleQuery(queryCheckPrim);
		
		if(!checkPrimExists.get(0).getContactPrimary().equalsIgnoreCase("Yes")){
			if(map.get("contactPrimary").equals("Yes")){
				String query = "SELECT c From Contact c WHERE c.personId = "+(Integer) map.get("personId")+" and c.contactType ='"+(String) map.get("contactType")+"' and c.contactPrimary='Yes'";
				List<Contact> contactPrimaryList = contactService.executeSimpleQuery(query);
				if(contactPrimaryList.size()>0){
					errorResponse.setStatus("PRIMARY");
					errorResponse.setResult(bindingResult.getAllErrors());
					BfmLogger.logger.info("Binding result: " + bindingResult);
					return errorResponse;
				}
			}
		}
		String mobregex = "^[0-9]{1,45}$";
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if ((map.get("contactType").equals("Mobile")
				|| map.get("contactType").equals("HomePhone") || map.get(
				"contactType").equals("Intercom"))
				&& !((String) map.get("contactContent")).matches(mobregex)) {
			bindingResult.reject("contactType",
					"Invalid " + map.get("contactType") + " Number");
			;
		} else if (map.get("contactType").equals("Email")
				&& !((String) map.get("contactContent")).matches(emailreg)) {
			bindingResult.reject("contactType", "Invalid Email Address");
			;
		}

		contact = contactService.getContactObject(map, "update", contact);
		validator.validate(contact, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: " + bindingResult);

			return errorResponse;
		} else {
			contact = contactService.update(contact);
			sessionStatus.setComplete();
			return contact;
		}
	}

	/**
	 * Delete contact on person it
	 * 
	 * @param map 				Person Details
	 * @param contact			Contact Object
	 * @param bindingResult		Binding Result Object
	 * @param sessionStatus		Session Status
	 * 
	 * @return contact object
	 */
	@RequestMapping(value = {"/conciergeVendor/contact/delete","/manpower/contact/delete","/owner/contact/delete","/tenant/contact/delete","/dom/contact/delete","/family/contact/delete","/vendors/contact/delete"}, method = RequestMethod.POST)
	public @ResponseBody
	Contact deleteContact(@RequestBody Map<String, Object> map,
			@ModelAttribute("contact") Contact contact,
			BindingResult bindingResult, SessionStatus sessionStatus) {
		contactService.delete((int) map.get("contactId"));
		sessionStatus.setComplete();
		return contact;
	}

	
	/**
	 * Read all contact location fro drop-down
	 * 
	 * @param locale System Locale
	 * @return contact Location
	 */
	@RequestMapping(value = "/contact/contactLocationChecks", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, String>> getContactLocation(final Locale locale) {

		String[] contactLocation = messageSource.getMessage(
				"values.contact.contactLocation", null, locale).split(",");

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;

		for (int i = 0; i < contactLocation.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", contactLocation[i]);
			map.put("name", contactLocation[i]);

			result.add(map);
		}

		return result;

	}

	
	/**
	 * @param personId Person Id
	 * @return get addresses for drop-down i contact
	 */
	@RequestMapping(value = "/contact/addressforcontact/{personId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAddressforContact(@PathVariable int personId) {
		return addressService.findAllAddressesBasedOnPersonID(personId);
	}

	/**
	 * Reading Contact type into drop-down
	 * @param locale System Locale
	 * @return Contact types
	 */
	@RequestMapping(value = "/contact/contactTypeChecks", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, String>> getContactType(final Locale locale) {

		String[] contactType = messageSource.getMessage("values.contact.contactType", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < contactType.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", contactType[i]);
			map.put("name", contactType[i]);
			result.add(map);
		}

		return result;

	}

	/**
	 * Reading contact primary
	 * 
	 * @param locale System locale
	 * @return contact primary
	 */
	@RequestMapping(value = "/contact/contactPrimaryChecks", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, String>> getContactPrimary(final Locale locale) {

		String[] contactPrimary = messageSource.getMessage(	"values.contact.contactPrimary", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < contactPrimary.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", contactPrimary[i]);
			map.put("name", contactPrimary[i]);

			result.add(map);
		}
		return result;

	}

	/**
	 * Read all content of the contact
	 * 
	 * @return contact contents
	 */
	@RequestMapping(value = "/contact/getAllContactContent", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getAllContactContent() {
		return contactService.getAllContactContent();
	}
	
	/*@RequestMapping(value = "/manpower/getmanpowerreq", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getManpowerReqAll() {
		return requisitionService.getManpowerRequisition();
	}
	*/
	@RequestMapping(value = "/manpower/getmanpowerreqvc", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getManpowerReqVC() {
		return requisitionService.getManpowerRequisitionVC();
	}
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/manpower/getmanpowerreq", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getManpowerReqAll(HttpServletRequest request) {
	
		return requisitionService.getManpowerRequisition();
	}
	
	@RequestMapping(value = "/manpower/getmanpowerreqdetails/{reqId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getManpowerDetailsReq(@PathVariable int reqId) {
		return requisitionService.getManpowerRequisitionDetails(reqId);
	}
	
	@RequestMapping(value = "/manPowerTemplate/manPowerTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSStaffsFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> userTemplateEntities = personService.findAllUserPerson();
	
               
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
    	
    	header.createCell(0).setCellValue("Staff Id");
    	header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Person Name");
        header.createCell(3).setCellValue("Login Name");
        header.createCell(4).setCellValue("Category Type");
        header.createCell(5).setCellValue("Vendor Name");
        header.createCell(6).setCellValue("Department");        
        header.createCell(7).setCellValue("Designation");
        header.createCell(8).setCellValue("Roles");
        header.createCell(9).setCellValue("User Status");
        header.createCell(10).setCellValue("Requisition");
        
        for(int j = 0; j<=10; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:K200"));
        }
        
        int count = 1;
       
        List<String> ls = new ArrayList<String>();
        String str1="";
       
    	for (Object[] person :userTemplateEntities ) {
    		
    		if(ls.contains((String)person[1])){
    			continue;
    		}
    		ls.add((String)person[1]);
    		XSSFRow row = sheet.createRow(count);
    		
    	    String str="";
    		if((String)person[25]!=null){
				str=(String)person[25];
			}
    		
    		
            row.createCell(0).setCellValue("IR/00"+(Integer)person[19]);
            row.createCell(1).setCellValue((String)person[22]);
    		row.createCell(2).setCellValue((String)person[23]+" "+str);
    		
    		row.createCell(3).setCellValue((String)person[1]);
    		
			
    		
    	    row.createCell(4).setCellValue((String)person[14]);
    		
    		row.createCell(5).setCellValue((String) person[17]+" "+(String) person[18]);
    		row.createCell(6).setCellValue((String) person[9]);
    		row.createCell(7).setCellValue((String) person[7]);
    	
    		int uid=(Integer)person[0];
    		List<Role> roleList = roleService.getAll(uid);
   
    		for(Role r:roleList){
    			String role = r.getRlName();
    			String roleStatus =r.getRlStatus();
    			if (roleStatus.equalsIgnoreCase("Active"))
    				str1+=role.concat("  {Active}")+",";
    			else
    				str1+=role.concat("  {Inactive}")+",";
    			}
    
    		row.createCell(8).setCellValue(str1);
    		str1="";
    	
    		row.createCell(9).setCellValue((String)person[5]);
    		row.createCell(10).setCellValue((String)person[49]);
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"StaffTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
	@RequestMapping(value = "/manPowerPdfTemplate/manPowerPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportStaffsPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> userTemplateEntities = personService.findAllUserPerson();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(12);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
       
        table.addCell(new Paragraph("Image",font1));
        table.addCell(new Paragraph("Staff Id",font1));
        table.addCell(new Paragraph("Title",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Login Name",font1));
        table.addCell(new Paragraph("Category Type",font1));
        table.addCell(new Paragraph("Vendor Name",font1));
        table.addCell(new Paragraph("Department",font1));
        table.addCell(new Paragraph("Designation",font1));
        table.addCell(new Paragraph("Roles",font1));
        table.addCell(new Paragraph("User Status",font1));
        table.addCell(new Paragraph("Requisition",font1));
      
        List<String> ls = new ArrayList<String>();
    	for (Object[] person :userTemplateEntities) {
    		
    		if(ls.contains((String)person[1])){
    			continue;
    		}
    		ls.add((String)person[1]);
    		
    		Blob blobImage = personService.getImage((Integer)person[19]);
    		int blobLength = 0;
    		byte[] blobAsBytes = null;
    		if (blobImage != null) {
    			blobLength = (int) blobImage.length();
    			blobAsBytes = blobImage.getBytes(1, blobLength);
    		}
    		 Image image2 = Image.getInstance(blobAsBytes);
    		 PdfPCell cell1 = new PdfPCell(image2,true);
    		 String str="";
     		if((String)person[25]!=null){
 				str=(String)person[25];
 			}
             PdfPCell cell2 = new PdfPCell(new Paragraph("IR/00"+(Integer)person[19],font3));
       
        PdfPCell cell3 = new PdfPCell(new Paragraph((String)person[22],font3));
    
        PdfPCell cell4 = new PdfPCell(new Paragraph((String)person[23]+" "+str,font3));
       
        PdfPCell cell5 = new PdfPCell(new Paragraph((String)person[1],font3));
        
        PdfPCell cell6 = new PdfPCell(new Paragraph((String)person[14],font3));
        
       
        
        PdfPCell cell7 = new PdfPCell(new Paragraph((String) person[17]+" "+(String) person[18],font3));
        PdfPCell cell8 = new PdfPCell(new Paragraph((String) person[9],font3));
        PdfPCell cell9 = new PdfPCell(new Paragraph((String) person[7],font3));
         String str1="";
        int uid=(Integer)person[0];
		List<Role> roleList = roleService.getAll(uid);

		for(Role r:roleList){
			String role = r.getRlName();
			String roleStatus =r.getRlStatus();
			if (roleStatus.equalsIgnoreCase("Active"))
				str1+=role.concat("  {Active}")+",";
			else
				str1+=role.concat("  {Inactive}")+",";
			}

		 PdfPCell cell10 = new PdfPCell(new Paragraph(str1,font3));
		  str1="";
	
        PdfPCell cell11 = new PdfPCell(new Paragraph((String)person[5],font3));
        PdfPCell cell12 = new PdfPCell(new Paragraph((String)person[49],font3));
       

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
        table.addCell(cell11);
        table.addCell(cell12);
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 14f, 7f, 16f, 15f, 18f, 16f, 16f, 16f , 10f, 13f , 16f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"StaffTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
}
