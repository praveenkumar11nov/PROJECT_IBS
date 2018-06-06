package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
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

import com.bcits.bfm.model.AccessCards;
import com.bcits.bfm.model.AccessEvent;
import com.bcits.bfm.model.AccessPoints;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.PatrolSettings;
import com.bcits.bfm.model.PatrolTrackAlert;
import com.bcits.bfm.model.PatrolTrackPoints;
import com.bcits.bfm.model.PatrolTrackStaff;
import com.bcits.bfm.model.PatrolTracks;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.Personnel;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.StaffAttendanceGateOutAlert;
import com.bcits.bfm.model.StaffAttendanceSummary;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardSevice;
import com.bcits.bfm.service.customerOccupancyManagement.AccessRepositoryService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonAccessCardService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.AccessPointsService;
import com.bcits.bfm.service.facilityManagement.PatrolSettingsService;
import com.bcits.bfm.service.facilityManagement.PatrolTrackAlertService;
import com.bcits.bfm.service.facilityManagement.PatrolTrackPointService;
import com.bcits.bfm.service.facilityManagement.PatrolTrackService;
import com.bcits.bfm.service.facilityManagement.PatrolTrackStaffService;
import com.bcits.bfm.service.facilityManagement.StaffAttendanceGateOutAlertService;
import com.bcits.bfm.service.facilityManagement.StaffAttendanceSummaryService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SendMailForStaff;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module
 * Module: Time And Attendance Management
 * 
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class TimeAndAttendanceContoller {

	static Logger logger = LoggerFactory
			.getLogger(TimeAndAttendanceContoller.class);

	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	PatrolTrackService patrolTrackService;
	@Autowired
	AccessRepositoryService accessRepositoryService;
	@Autowired
	PatrolTrackPointService patrolTrackPointService;
	@Autowired
	private Validator validator;
	@Autowired
	private UsersService usersService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AccessCardSevice accessCardSevice;
	@Autowired
	private PatrolTrackStaffService patrolTrackStaffService;
	@Autowired
	private PersonService personService;
	@Autowired
	private StaffAttendanceSummaryService staffAttendanceSummaryService;
	@Autowired
	private PatrolTrackAlertService patrolTrackAlertService;
	@Autowired
	private StaffAttendanceGateOutAlertService staffAttendanceGateOutAlertService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PatrolSettingsService patrolSettingsService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private PersonAccessCardService personAccessCardService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DesignationService designationService;
	@Autowired
	private AccessPointsService accessPointsService;

	@PersistenceContext(unitName = "MSSQLDataSourceAccessCards")
	private EntityManager msSQLAccessCardsEntityManager;

	@RequestMapping(value = "/time&AttendanceManagement")
	public String timeAttendanceManagementIndex(HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("ViewName", "Time & Attendance Management");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance Management", 1, request);
		return "timeAndAttendance/time&AttendanceManagement";
	}

	@RequestMapping(value = "/staff")
	public String staffIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Staff Details");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Staff Detaila", 1, request);
		return "timeAndAttendance/staff";
	}

	@RequestMapping(value = "/timeAndAttendanceManagement/patroltrackStatus/{ptId}/{operation}", method = RequestMethod.POST)
	public String PatrolTrackStatus(@PathVariable int ptId,
			@PathVariable String operation, ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			final Locale locale) {
		patrolTrackService.setPatrolTracktStatus(ptId, operation, response,
				messageSource, locale);
		return null;
	}

	@RequestMapping(value = "/timeAndAttendanceManagement/patroltarckPointStatus/{ptpId}/{operation}", method = RequestMethod.POST)
	public String PatrolTrackPointStatus(@PathVariable int ptpId,
			@PathVariable String operation, ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			final Locale locale) {
		patrolTrackPointService.setPatrolTracktPointStatus(ptpId, operation,
				response, messageSource, locale);
		return null;
	}

	@RequestMapping(value = "/timeAndAttendanceManagement/patroltrackStaffStatus/{ptsId}/{operation}", method = RequestMethod.POST)
	public String PatrolTrackStaffStatus(@PathVariable int ptsId,
			@PathVariable String operation, ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			final Locale locale) {
		patrolTrackStaffService.setPatrolTracktStaffStatus(ptsId, operation,
				response, messageSource, locale);
		return null;
	}

	@RequestMapping(value = "/timeAndAttendanceManagement/patrolRoleSettingStatus/{psId}/{operation}", method = RequestMethod.POST)
	public String patrolRoleSettingStatus(@PathVariable int psId,
			@PathVariable String operation, ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			final Locale locale) {
		patrolSettingsService.setPatrolRoleSettingStatus(psId, operation,
				response, messageSource, locale);
		return null;
	}

	@RequestMapping(value = "/timeAttdashboard")
	public String timeAttdashboardIndex(HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("ViewName", "Dashboard");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Dashboard", 1, request);
		return "timeAndAttendance/timeAttdashboard";
	}

	/**
	 * All the patrol track instances will be send to the view
	 * 
	 * @param model
	 *            This will set message inside view
	 * @param request
	 *            Input from the view
	 * @return Control goes to respective view
	 * @since 0.1
	 */
	@RequestMapping(value = "/patrolTracks")
	public String patrolTrackIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Patrol Tracks", 2, request);
		return "timeAndAttendance/patrolTracks";
	}

	/**
	 * Fetching all patrol track details from DB and send to the view
	 * 
	 * @return PatrolTrack object
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTracks/readPatrolTracks", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatrolTracks() {
		List<Map<String, Object>> patrolTracks = new ArrayList<Map<String, Object>>();
		for (final PatrolTracks record : patrolTrackService.findAll()) {
			patrolTracks.add(new HashMap<String, Object>() {
				{
					put("ptId", record.getPtId());
					put("ptName", record.getPtName());
					put("description", record.getDescription());
					put("validTimeFrom", record.getValidTimeFrom());
					put("validTimeTo", record.getValidTimeTo());
					put("status", record.getStatus());
				}
			});
		}
		return patrolTracks;
	}

	/**
	 * Reading all patrol track point details for particular patrol track area
	 * 
	 * @param ptId
	 *            Taking patrol track id from view
	 * 
	 * @return PatrolTrack point object
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTracks/patrolTrackPoint/read/{ptId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatroTrackPointhierachy(@PathVariable int ptId) {

		List<Map<String, Object>> patrolTrackPoints = new ArrayList<Map<String, Object>>();
		for (final PatrolTrackPoints record : patrolTrackPointService
				.findAllBasedOnPtId(ptId)) {
			patrolTrackPoints.add(new HashMap<String, Object>() {
				{
					put("ptpId", record.getPtpId());
					put("ptName", record.getPatrolTracks().getPtName());
					put("arName", record.getAccessPoints().getApName());
					put("ptpSequence", record.getPtpSequence());
					put("ptpVisitInterval", record.getPtpVisitInterval());
					put("status", record.getStatus());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDt", record.getLastUpdatedDt());
					put("createdBy", record.getCreatedBy());
					put("status", record.getStatus());

				}
			});
		}
		return patrolTrackPoints;

	}

	/**
	 * Reading all patrol track staff details for particular patrol track area
	 * 
	 * @param ptId
	 *            Taking patrol track id from view
	 * 
	 * @return PatrolTrack staff object
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTracks/patrolTrackStaff/read/{ptId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatrolTrackStaffHierarchy(@PathVariable int ptId) {

		List<Map<String, Object>> patrolTrackStaff = new ArrayList<Map<String, Object>>();
		for (final PatrolTrackStaff record : patrolTrackStaffService
				.findAllBasedOnPtId(ptId)) {

			final Person personRec = personService.getPersonBasedOnId(record
					.getSupervisorId());
			patrolTrackStaff.add(new HashMap<String, Object>() {
				{
					put("ptsId", record.getPtsId());
					if (record.getPerson().getLastName() == null) {
						put("firstName", record.getPerson().getFirstName());
					} else {
						put("firstName", record.getPerson().getFirstName()
								+ " " + record.getPerson().getLastName());
					}
					put("personId", record.getPersonId());
					final Personnel personCardNo=msSQLAccessCardsEntityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo",record.getAcId()).getSingleResult();
					put("acNo", personCardNo.getNonAbacardNumber());
					put("acId", record.getAcId());
					put("ptName", record.getPatrolTracks().getPtName());
					if (personRec.getLastName() == null) {
						put("supervisorName", personRec.getFirstName());
					} else {
						put("supervisorName", personRec.getFirstName() + " "
								+ personRec.getLastName());
					}
					put("supervisorId", record.getSupervisorId());
					put("fromDate",
							ConvertDate.TimeStampString(record.getFromDate()));
					put("toDate",
							ConvertDate.TimeStampString(record.getToDate()));
					put("status", record.getStatus());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDt", record.getLastUpdatedDt());
					put("createdBy", record.getCreatedBy());

				}
			});
		}
		return patrolTrackStaff;

	}

	/**
	 * Reading all patrol track alert details for particular patrol track area
	 * 
	 * @param ptId
	 *            Taking patrol track id from view
	 * 
	 * @return PatrolTrack alert object
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "patrolTracks/patrolTrackAlert/read/{ptId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatrolTrackAlertHierarchy(@PathVariable int ptId) {
		List<Map<String, Object>> patrolTrackAlert = new ArrayList<Map<String, Object>>();
		for (final PatrolTrackAlert record : patrolTrackAlertService
				.findAllBasedOnPtId(ptId)) {
			patrolTrackAlert.add(new HashMap<String, Object>() {
				{
					put("ptaId", record.getPtaId());
					put("ptName", record.getPatrolTracks().getPtName());
					if (record.getPerson().getLastName() == null) {
						put("firstName", record.getPerson().getFirstName());
					} else {
						put("firstName", record.getPerson().getFirstName()
								+ " " + record.getPerson().getLastName());
					}
					put("message", record.getMessage());
					put("ptaDt", record.getPtaDt());

				}
			});
		}
		return patrolTrackAlert;
	}

	/**
	 * Creating new patrol track
	 * 
	 * @param map
	 *            This Patrol track entity details coming from jsp as a map
	 * @param request
	 *            Input from the view
	 * @param response
	 *            Output from controller to jsp
	 * @param model
	 *            This will set message inside view
	 * 
	 * @return If any error will be there then return error response otherwise
	 *         return Patrol track object
	 * 
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTracks/createPatrolTrack", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createPatrolTrack(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model,
			@ModelAttribute("patrolTracks") PatrolTracks patrolTracks,
			BindingResult bindingResult, final Locale locale) {

		String ptName = (String) map.get("ptName");
		String ptValidTimeFrom = (String) map.get("validTimeFrom");
		String ptValidTimeTo = (String) map.get("validTimeTo");
		String ptDescription = (String) map.get("description");

		JsonResponse errorResponse = new JsonResponse();
		int count = 0;
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		Users userRecord = usersService.getUserInstanceByLoginName(userName);
		List<Contact> contactRec = contactService
				.findAllContactsBasedOnPersonID(userRecord.getPersonId());
		List<String> ptNames = patrolTrackService.getAllPatrolTrackNames();
		for (Iterator<String> iterator = ptNames.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.equalsIgnoreCase(ptName)) {
				count = 1;
				break;
			}
		}
		if (count == 1) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("duplicate", messageSource.getMessage(
							"PatrolTrack.duplicate.ptName", null, locale));
				}
			};
			errorResponse.setStatus("DUPLICATE");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
		if (count == 0) {
			String RE = "[0-9]{1,2}:[0-9]{1,2}?[ ](A|P)M(?!^[A-Za-z][0-9]$)";

			if ((!(boolean) (ptValidTimeFrom).matches(RE))
					&& (!(boolean) (ptValidTimeTo).matches(RE))) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("wrongFormate", messageSource.getMessage(
								"PatrolTrack.wrongFormat.times", null, locale));
					}
				};
				errorResponse.setStatus("WRONGFORMAT");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;

			} else if (!(boolean) (ptValidTimeFrom).matches(RE)) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("wrongFromTime", messageSource.getMessage(
								"PatrolTrack.wrongFromTime.time", null, locale));
					}
				};
				errorResponse.setStatus("WRONGFROMTIME");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;

			} else if (!(boolean) (ptValidTimeTo).matches(RE)) {

				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("wrongToTime", messageSource.getMessage(
								"PatrolTrack.wrongToTime.time", null, locale));
					}
				};
				errorResponse.setStatus("WRONGTOTIME");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;

			} else {
				if (ptValidTimeFrom.equals(ptValidTimeTo)) {

					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("sameTime", messageSource.getMessage(
									"PatrolTrack.sameTime.times", null, locale));
						}
					};
					errorResponse.setStatus("SAMETIME");
					errorResponse.setResult(errorMapResponse);
					return errorResponse;

				} else {
					patrolTracks.setValidTimeFrom(ptValidTimeFrom);
					patrolTracks.setValidTimeTo(ptValidTimeTo);
				}
			}

			patrolTracks.setPtName(ptName);
			patrolTracks.setDescription(ptDescription);

			if (contactRec.size() == 0) {
				patrolTracks.setAdminAlertMobileNo("");
				patrolTracks.setAdminAlertEmailId("");

			}
			if (contactRec.size() > 0) {
				for (Contact record : contactRec) {
					if (record.getContactType().equals("Mobile")) {

						patrolTracks.setAdminAlertMobileNo(record
								.getContactContent());
					}
					if (record.getContactType().equals("Email")) {

						patrolTracks.setAdminAlertEmailId(record
								.getContactContent());
					}
				}
			}
			patrolTracks.setStatus("InActive");
			patrolTracks.setCreatedBy(userName);
			patrolTracks.setLastUpdatedBy(userName);

			validator.validate(patrolTracks, bindingResult);

			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());

				return errorResponse;
			} else {
				patrolTrackService.save(patrolTracks);
				return patrolTracks;
			}
		}
		return patrolTracks;

	}

	/**
	 * Updating patroltrack
	 * 
	 * @param map
	 *            This Patrol  track entity details coming from jsp as a map
	 * @param request
	 *            Input from the view
	 * @param response
	 *            Output from controller to jsp
	 * @param model
	 *            This will set message inside view
	 * 
	 * @return If any error will be there then return error response otherwise
	 *         return Patrol track object
	 * 
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTracks/updatePatrolTrack", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object updatePatrolTrack(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model,
			@ModelAttribute("patrolTracks") PatrolTracks patrolTracks,
			BindingResult bindingResult, final Locale locale) {

		String RE = "[0-9]{1,2}:[0-9]{1,2}?[ ](A|P)M";
		JsonResponse errorResponse = new JsonResponse();
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		int ptId = (Integer) map.get("ptId");
		PatrolTracks patrolTrackRecord = patrolTrackService
				.getPatrolTrackInstanceById(ptId);

		String ptName = (String) map.get("ptName");
		String ptDescription = (String) map.get("description");
		String ptValidTimeFrom = (String) map.get("validTimeFrom");
		String ptValidTimeTo = (String) map.get("validTimeTo");
		String status = (String) map.get("status");

		if (!(boolean) (ptValidTimeFrom).matches(RE)
				&& !(boolean) (ptValidTimeTo).matches(RE)) {

			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("wrongFormate", messageSource.getMessage(
							"PatrolTrack.wrongFormat.times", null, locale));
				}
			};
			errorResponse.setStatus("INVALIDFORMAT");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}

		else if (!(boolean) (ptValidTimeFrom).matches(RE)) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("wrongFromTime", messageSource.getMessage(
							"PatrolTrack.wrongFromTime.time", null, locale));
				}
			};
			errorResponse.setStatus("FROMTIME");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;

		} else if (!(boolean) (ptValidTimeTo).matches(RE)) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("wrongToTime", messageSource.getMessage(
							"PatrolTrack.wrongToTime.time", null, locale));
				}
			};
			errorResponse.setStatus("TOTIME");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;

		}
		if (ptValidTimeFrom.equals(ptValidTimeTo)) {

			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("sameTime", messageSource.getMessage(
							"PatrolTrack.sameTime.times", null, locale));
				}
			};
			errorResponse.setStatus("SAMETIMEE");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;

		}
		patrolTracks.setValidTimeFrom(ptValidTimeFrom);
		patrolTracks.setValidTimeTo(ptValidTimeTo);
		patrolTracks.setPtId(ptId);
		patrolTracks.setPtName(ptName);
		patrolTracks.setDescription(ptDescription);
		patrolTracks.setAdminAlertMobileNo(patrolTrackRecord
				.getAdminAlertMobileNo());
		patrolTracks.setAdminAlertEmailId(patrolTrackRecord
				.getAdminAlertEmailId());
		patrolTracks.setStatus(status);
		patrolTracks.setCreatedBy(patrolTrackRecord.getCreatedBy());
		patrolTracks.setLastUpdatedBy(userName);

		validator.validate(patrolTracks, bindingResult);
		if (bindingResult.hasErrors()) {

			errorResponse.setStatus("FAILUPDATE");
			errorResponse.setResult(bindingResult.getAllErrors());

			return errorResponse;
		} else {
			patrolTrackService.update(patrolTracks);
			return patrolTracks;
		}

		// return patrolTracks;
	}

	/**
	 * Deleting patroltrack
	 * 
	 * @param map
	 *            This Patrol track entity details coming from jsp as a map
	 * 
	 * @return If any error will be there then return error response otherwise
	 *         return Patroltrack object
	 * 
	 * @since 0.1
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTracks/deletePatrolTracks", method = RequestMethod.POST)
	public @ResponseBody
	Object deletePatrolTracks(@RequestBody Map<String, Object> map,
			@ModelAttribute("patrolTracks") PatrolTracks patrolTracks,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		int ptId = (Integer) map.get("ptId");

		String status = patrolTrackService.getStatusBasedOnId(ptId);
		if (status.equals("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("deleteerror", messageSource.getMessage(
							"PatrolTrack.deleteerror", null, locale));
				}
			};
			errorResponse.setStatus("DELETEERROR");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;

		}

		else {

			try {
				patrolTrackService.delete(ptId);
				return patrolTracks;

			} catch (Exception e) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("exception", messageSource.getMessage(
								"PatrolTrack.delete.error", null, locale));
					}
				};
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}
		}

	}

	@RequestMapping(value = "/patrolTracks/getPatrolTrackNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getPatrolTrackNames() {
		return patrolTrackService.getAllPatrolTrackNames();
	}

	@RequestMapping(value = "/patrolTracks/getStatusList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getStatusList() {

		Set<String> result = new HashSet<String>();
		result.add("Active");
		result.add("Inactive");
		return result;
	}

	@RequestMapping(value = "/PatrolTrackPoints")
	public String patrolTrackPointsIndex(HttpServletRequest request,
			ModelMap model) {

		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Patrol Track Points", 2, request);

		return "/timeAndAttendance/PatrolTrackPoints";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackPoints/getRepositoryData", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getRepositoryData() {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final AccessPoints record : accessPointsService.findAll()) {
			result.add(new HashMap<String, Object>() {
				{
					put("arName", record.getApName());
					put("arId", record.getApId());

				}
			});
		}
		return result;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackPoints/getPatroltrackName", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getPatroltrackName() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final PatrolTracks record : patrolTrackService.findActiveTracks()) {
			result.add(new HashMap<String, Object>() {
				{
					put("ptName", record.getPtName());
				}
			});
		}
		return result;

	}

	@RequestMapping(value = "/PatrolTrackPoints/getSequenceList", method = RequestMethod.GET)
	public @ResponseBody
	Set<Integer> getSequenceList() {

		Set<Integer> result = new HashSet<Integer>();
		for (Integer sequences : patrolTrackPointService.getSequences()) {
			result.add(sequences);
		}
		return result;
	}

	@RequestMapping(value = "/PatrolTrackPoints/getTimeIntervalList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getTimeIntervalList() {

		Set<String> result = new HashSet<String>();
		for (String timeIntevals : patrolTrackPointService.getTimeIntervals()) {
			result.add(timeIntevals);
		}
		return result;
	}

	@RequestMapping(value = "/PatrolTrackPoints/getStatusList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getStatusSet() {

		Set<String> result = new HashSet<String>();
		result.add("Active");
		result.add("Inactive");
		return result;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackPoints/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatrolTrackPoints() {

		logger.info("=======================  Inside Patrol Track Point ==============================");

		List<Map<String, Object>> patrolTrackPoints = new ArrayList<Map<String, Object>>();
		List<?> patrolTrackPointList = patrolTrackPointService.findAll();

		for (final Iterator<?> i = patrolTrackPointList.iterator(); i.hasNext();) {
			patrolTrackPoints.add(new HashMap<String, Object>() {
				{

					final Object[] values = (Object[]) i.next();

					put("ptpId", (Integer) values[0]);
					put("ptName", (String) values[1]);
					put("arName", (String) values[2]);
					put("arId", (Integer) values[3]);
					put("ptpSequence", (Integer) values[4]);
					put("ptpVisitInterval", (String) values[5]);
					put("status", (String) values[6]);
					put("lastUpdatedBy", (String) values[7]);
					put("lastUpdatedDt", (String) values[8]);
					put("createdBy", (String) values[9]);

				}
			});
		}
		return patrolTrackPoints;

	}

	@RequestMapping(value = "/PatrolTrackPoints/getPatrolTrackNamesList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getPatrolTrackNamesList() {
		Set<String> result = new HashSet<String>();
		List<?> patrolTrackPointList = patrolTrackPointService.findAll();

		for (final Iterator<?> i = patrolTrackPointList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			result.add((String) values[1]);
		}
		return result;
	}

	@RequestMapping(value = "/PatrolTrackPoints/getAccessRepositoryNamesList", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAccessRepositoryNamesList() {

		List<String> result = new ArrayList<String>();
		List<?> patrolTrackPointList = patrolTrackPointService.findAll();

		for (final Iterator<?> i = patrolTrackPointList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			result.add((String) values[2]);
		}
		return result;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackPoints/create", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createPatrolTrackPoints(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("patrolTrackPoints") PatrolTrackPoints patrolTrackPoints,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		String RE = "[0-9]{1,2}:[0-9]{1,2}";
		DateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSX");
		TimeZone tx = TimeZone.getTimeZone("Asia/Calcutta");
		Date d1;

		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");

		int ptId = 0;
		int arId = 0;
		String intervalTime = null;

		String ptName = (String) map.get("ptName");
		ptId = patrolTrackService.getPatrolTrackIdBasedOnName(ptName);
		patrolTrackPoints.setPtId(ptId);
		arId = accessPointsService.getAccesspointIdBasedOnName((String) map
				.get("arName"));
		patrolTrackPoints.setArId(arId);
		String ptpVisitInterval = (String) map.get("ptpVisitInterval");
		if (ptpVisitInterval == "") {

			if (!(boolean) (ptpVisitInterval).matches(RE)) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("wrongTimeInterval", messageSource.getMessage(
								"PatrolTrackPoint.wrongTimeInterval.time",
								null, locale));
					}
				};
				errorResponse.setStatus("WRONGTIMEINTERVAL");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;

			}
		}
		if (ptpVisitInterval != "") {
			String formatIntervalTimeFrom = null;
			try {
				d1 = formatter.parse(ptpVisitInterval);
				formatter.setTimeZone(tx);
				formatIntervalTimeFrom = formatter.format(d1);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			intervalTime = formatIntervalTimeFrom.substring(11, 16);
			if (intervalTime.equals("00:00")) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("zeroTimeInterval", messageSource.getMessage(
								"PatrolTrackPoint.zeroTimeInterval.time", null,
								locale));
					}
				};
				errorResponse.setStatus("ZEROTIMEINTERVAL");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;

			} else
				patrolTrackPoints.setPtpVisitInterval(intervalTime);
		}

		int sequence = (Integer) map.get("ptpSequence");
		// String status = (String)map.get("status");
		patrolTrackPoints.setPtpSequence((Integer) map.get("ptpSequence"));
		patrolTrackPoints.setStatus("InActive");
		patrolTrackPoints.setCreatedBy(userName);
		patrolTrackPoints.setLastUpdatedBy(userName);

		validator.validate(patrolTrackPoints, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());

			return errorResponse;
		} else {
			int count = 0;
			List<PatrolTrackPoints> allPatrolTrackPoints = patrolTrackPointService
					.findAll();

			for (final Iterator<?> i = allPatrolTrackPoints.iterator(); i
					.hasNext();) {

				final Object[] values = (Object[]) i.next();

				if ((Integer) values[10] == ptId
						&& (Integer) values[4] == sequence) {

					count = 1;
					break;
				}
			}

			if (count == 1) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("invalid", messageSource.getMessage(
								"PatrolTrackPoint.unique.record", null, locale));
					}
				};
				errorResponse.setStatus("INVALID");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;

			} else if (count == 0) {
				patrolTrackPointService.save(patrolTrackPoints);
				return patrolTrackPoints;

			}
		}
		return patrolTrackPoints;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackPoints/update", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object updatePatrolTrackPoints(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("patrolTrackPoints") PatrolTrackPoints patrolTrackPoints,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {

		int arIdss = accessPointsService
				.getAccesspointIdBasedOnName((String) map.get("arName"));
		int ptpId = (Integer) map.get("ptpId");
		JsonResponse errorResponse = new JsonResponse();
		DateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSX");
		TimeZone tx = TimeZone.getTimeZone("Asia/Calcutta");
		Date d1;

		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		PatrolTrackPoints patrolTrackPointRecord = patrolTrackPointService
				.getPatrolTrackPointInstanceById(ptpId);
		patrolTrackPoints.setArId(accessRepositoryService
				.getAccessRepositoryIdBasedOnName((String) map.get("arName")));

		String intervalTime = (String) map.get("ptpVisitInterval");
		if (intervalTime == null) {
		}
		if (intervalTime != null) {

			if (intervalTime.length() == 5) {

				patrolTrackPoints.setPtpVisitInterval(intervalTime);
			}

			if (intervalTime.length() > 5) {
				String formateIntervalTime = null;
				try {
					d1 = formatter.parse(intervalTime);
					formatter.setTimeZone(tx);
					formateIntervalTime = formatter.format(d1);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				String ptpTimeInterval = formateIntervalTime.substring(11, 16);
				if (ptpTimeInterval.equals("00:00")) {
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("zeroTime", messageSource.getMessage(
									"PatrolTrackPoint.zeroTimeInterval.time",
									null, locale));
						}
					};
					errorResponse.setStatus("ZEROTIME");
					errorResponse.setResult(errorMapResponse);
					return errorResponse;

				} else
					patrolTrackPoints.setPtpVisitInterval(ptpTimeInterval);
			}
		}

		patrolTrackPoints.setPtpId(ptpId);
		patrolTrackPoints.setPtId(patrolTrackService
				.getPatrolTrackIdBasedOnName((String) map.get("ptName")));
		patrolTrackPoints.setArId(arIdss);
		patrolTrackPoints.setPtpSequence((Integer) map.get("ptpSequence"));
		patrolTrackPoints.setStatus((String) map.get("status"));
		patrolTrackPoints.setCreatedBy(patrolTrackPointRecord.getCreatedBy());
		patrolTrackPoints.setLastUpdatedBy(userName);

		validator.validate(patrolTrackPoints, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());

			return errorResponse;
		} else {
			int count = 0;
			List<PatrolTrackPoints> allPatrolTrackPoints = patrolTrackPointService
					.findPatrolPointExceptId(ptpId);
			for (Iterator<PatrolTrackPoints> iterator = allPatrolTrackPoints
					.iterator(); iterator.hasNext();) {
				PatrolTrackPoints patrolTrackPoints2 = (PatrolTrackPoints) iterator
						.next();
				if (patrolTrackPoints2.getPatrolTracks().getPtId() == patrolTrackService
						.getPatrolTrackIdBasedOnName((String) map.get("ptName"))
						&& patrolTrackPoints2.getPtpSequence() == (Integer) map
								.get("ptpSequence")) {

					count = 1;
					break;
				}
			}

			if (count == 1) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("updateinvalid", messageSource.getMessage(
								"PatrolTrackPoint.unique.record", null, locale));
					}
				};
				errorResponse.setStatus("UPDATEINVALID");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;

			} else if (count == 0) {
				patrolTrackPointService.update(patrolTrackPoints);
				return patrolTrackPoints;

			}
		}
		return patrolTrackPoints;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackPoints/delete", method = RequestMethod.POST)
	public @ResponseBody
	Object deletePatrolTrackPoints(@RequestBody Map<String, Object> map,
			final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		PatrolTrackPoints patrolTrackPoints = new PatrolTrackPoints();
		int ptpId = (Integer) map.get("ptpId");

		String status = patrolTrackPointService.getStatusBasedOnId(ptpId);
		if (status.equals("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("deletepointerror", messageSource.getMessage(
							"PatrolTrackPoint.deletepointerror", null, locale));
				}
			};
			errorResponse.setStatus("DELETEPOINTERROR");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}

		else {

			patrolTrackPointService.delete(ptpId);
			return patrolTrackPoints;

		}
	}

	@RequestMapping(value = "/patrolTrackStaff")
	public String patrolTrackStaffIndex(HttpServletRequest request,
			ModelMap model) {

		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Patrol Staff", 2, request);

		return "timeAndAttendance/patrolTrackStaff";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackStaff/getAccessCardNo", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAccessCardsNo() {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final PersonAccessCards record : personAccessCardService
				.findPersonAccessCards()) {

			/*final String acNo = accessCardSevice
					.getaccessCradNoBasedOnId(record.getAcId());*/
			
			final Personnel personCardNo=msSQLAccessCardsEntityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo",record.getAcId()).getSingleResult();

			result.add(new HashMap<String, Object>() {
				{
					put("acNo", personCardNo.getObjectIdLo());
					put("accessCardNo", personCardNo.getNonAbacardNumber());
					put("personId", record.getPersonId());
				}
			});
		}
		return result;
	}

	//Uniqeness
		@RequestMapping(value = "/comowner/accesscard/readAccessCardsForUniqe", method = RequestMethod.GET)
		public @ResponseBody List<?> readAccessCardsForUniqe() {	
			
		List<Integer> result = new ArrayList<>();
		List<Integer> res=personAccessCardService.readAccessCardsForUniqe();
		if(!(res.isEmpty())){
		
		for (final Iterator<?> i = res.iterator(); i.hasNext();) {
			
					int id=(Integer)i.next();
					result.add((Integer) msSQLAccessCardsEntityManager.createNamedQuery("Personnel.getAccessCardNoUniqe").setParameter("objectIdLo",id).getSingleResult());
				
					
				}
		}
			
		return result;
	  }
		
		
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackStaff/getStaffNames", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getStaffNames() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		for (final Person record : personService.getStaffNames("Staff")) {
			result.add(new HashMap<String, Object>() {
				{
					if (record.getLastName() == null) {
						put("firstName", record.getFirstName());
					} else {
						put("firstName",
								record.getFirstName() + " "
										+ record.getLastName());
					}
					put("personId", record.getPersonId());

				}
			});
		}
		return result;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/PatrolTrackStaff/getSupervisorNames", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getSupervisorNames() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		for (final Person record : personService.getStaffNames("Staff")) {

			result.add(new HashMap<String, Object>() {
				{
					if (record.getLastName() == null) {
						put("supervisorName", record.getFirstName());
					} else {
						put("supervisorName", record.getFirstName() + " "
								+ record.getLastName());
					}
					put("supervisorId", record.getPersonId());

				}
			});
		}
		return result;
	}

	@RequestMapping(value = "/PatrolTrackStaff/getStaffNameList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getStaffNameList() {

		Set<String> result = new HashSet<String>();
		List<?> patrolTrackStaffList = patrolTrackStaffService.findAll();
		for (final Iterator<?> i = patrolTrackStaffList.iterator(); i.hasNext();) {

			final Object[] values = (Object[]) i.next();
			if ((String) values[2] == null) {
				result.add((String) values[1]);
			} else {
				result.add((String) values[1] + " " + (String) values[2]);
			}
		}
		return result;
	}

	@RequestMapping(value = "/PatrolTrackStaff/getSupervisorNameList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getSupervisorNameList() {

		Set<String> result = new HashSet<String>();
		List<?> patrolTrackStaffList = patrolTrackStaffService.findAll();
		for (final Iterator<?> i = patrolTrackStaffList.iterator(); i.hasNext();) {

			final Object[] values = (Object[]) i.next();
			result.add((String) values[14]);
		}
		return result;
	}

	@RequestMapping(value = "/PatrolTrackStaff/getAccessCardsNoList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getAccessCardsNoList() {

		Set<String> result = new HashSet<String>();
		List<?> patrolTrackStaffList = patrolTrackStaffService.findAll();
		for (final Iterator<?> i = patrolTrackStaffList.iterator(); i.hasNext();) {

			final Object[] values = (Object[]) i.next();
			result.add(String.valueOf(values[4]));
		}

		return result;
	}

	@RequestMapping(value = "/PatrolTrackStaff/getPatrolTrackNamesList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getPatrolTrackNamesSet() {

		Set<String> result = new HashSet<String>();
		List<?> patrolTrackStaffList = patrolTrackStaffService.findAll();
		for (final Iterator<?> i = patrolTrackStaffList.iterator(); i.hasNext();) {

			final Object[] values = (Object[]) i.next();
			result.add((String) values[6]);
		}
		return result;
	}

	@RequestMapping(value = "/patrolTrackStaff/checkSupervisorName", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	void checkSupervisorName(HttpServletRequest request, HttpServletResponse res) {

		boolean supFlag = false;
		String name;

		String supervisorId = request.getParameter("supervisorId");
		String supervisorName = request.getParameter("supervisorName");
		List<Person> supervisorNames = personService.getStaffNames("Staff");

		for (Iterator<Person> iterator = supervisorNames.iterator(); iterator
				.hasNext();) {
			Person person = (Person) iterator.next();
			if (person.getLastName() == null) {
				name = person.getFirstName();
			} else {
				name = person.getFirstName() + " " + person.getLastName();
			}
			if (name.equals(supervisorName)) {
				supFlag = true;
				break;
			}
		}

		PrintWriter out;
		if (!supervisorId.equals(supervisorName)) {
			try {
				out = res.getWriter();
				if (supFlag == false) {

					out.write("NOTEXISTS");

				}
				if (supFlag == true) {

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/patrolTrackStaff/checkSupervisorName1", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	void checkSupervisorName1(HttpServletRequest request,
			HttpServletResponse res) {

		boolean supFlag = false;
		String name;

		String supervisorName = request.getParameter("supervisorName");
		String[] fullname = supervisorName.split(" ");
		List<Integer> personIds = personService
				.getPersonIdBasedOnPersonFullName(fullname[0], fullname[1]);

		if (personIds.contains(Integer.parseInt(supervisorName))) {

		} else {

			List<Person> supervisorNames = personService.getStaffNames("Staff");

			for (Iterator<Person> iterator = supervisorNames.iterator(); iterator
					.hasNext();) {
				Person person = (Person) iterator.next();
				if (person.getLastName() == null) {
					name = person.getFirstName();
				} else {
					name = person.getFirstName() + " " + person.getLastName();
				}
				if (name.equals(supervisorName)) {
					supFlag = true;
					break;
				}
			}

			PrintWriter out;

			try {
				out = res.getWriter();
				if (supFlag == false) {

					out.write("NAMENOTEXISTS");

				}
				if (supFlag == true) {

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/patrolTrackStaff/checkAcNo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	void checkAcNo(HttpServletRequest request, HttpServletResponse res) {

		boolean acNoFlag = false;
		int personId = Integer.parseInt(request.getParameter("personId"));
		String acNo = request.getParameter("acNo");
		List<AccessCards> accessCardsRec = accessCardSevice
				.getAcNosBasedOnpersonId(personId);
		for (Iterator<AccessCards> accessCards = accessCardsRec.iterator(); accessCards
				.hasNext();) {
			AccessCards accessCard = (AccessCards) accessCards.next();
			if (accessCard.getAcNo().equals(acNo)) {
				acNoFlag = true;
				break;
			}
		}

		PrintWriter out;
		try {
			if (acNoFlag == false) {
				out = res.getWriter();
				out.write("ACNONOTEXISTS");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTrackStaff/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatrolTrackStaff() {

		List<Map<String, Object>> patrolTrackStaff = new ArrayList<Map<String, Object>>();
		List<?> patrolTrackStaffList = patrolTrackStaffService.getPatrolTrackStaffData();

		for (final Iterator<?> i = patrolTrackStaffList.iterator(); i.hasNext();) {

			patrolTrackStaff.add(new HashMap<String, Object>() {
				{

					final Object[] values = (Object[]) i.next();

					put("ptsId", (Integer) values[0]);
					if ((String) values[2] == null) {
						put("firstName", (String) values[1]);
					} else {
						put("firstName", (String) values[1] + " "
								+ (String) values[2]);
					}
					put("personId", (Integer) values[3]);
					
					final Personnel personCardNo=msSQLAccessCardsEntityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo",(Integer) values[5]).getSingleResult();
					if(personCardNo!=null){
					put("acNo", String.valueOf(personCardNo.getNonAbacardNumber()));}
					put("acId", (Integer) values[5]);
					put("ptName", (String) values[6]);
					put("supervisorName", (String) values[14]);
					put("supervisorId", (Integer) values[7]);
					put("fromDate",
							ConvertDate.TimeStampString((Timestamp) values[8]));
					put("toDate",
							ConvertDate.TimeStampString((Timestamp) values[9]));
					put("status", (String) values[10]);
					put("lastUpdatedBy", (String) values[11]);
					put("lastUpdatedDt", (String) values[12]);
					put("createdBy", (String) values[13]);

				}
			});
		}
		return patrolTrackStaff;

	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTrackStaff/create", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createPatrolTrackStaff(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("patrolTrackStaff") PatrolTrackStaff patrolTrackStaff,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		int staffId = 0;
		int supervisorId = 0;
		int count = 0;
		if (map.get("firstName") instanceof java.lang.String) {
		}
		if (map.get("supervisorName") instanceof java.lang.String) {
		}

		if (map.get("firstName") instanceof java.lang.Integer) {
			staffId = (Integer) map.get("firstName");
			patrolTrackStaff.setPersonId(staffId);
		}
		if (map.get("supervisorName") instanceof java.lang.Integer) {
			supervisorId = (Integer) map.get("supervisorName");
			patrolTrackStaff.setSupervisorId(supervisorId);
		}
		String ptName = (String) map.get("ptName");
		int ptId = patrolTrackService.getPatrolTrackIdBasedOnName(ptName);
		/*String acNo = (String) map.get("acNo");
		int acId = accessCardSevice.getAccesCardIdBasedOnNo(acNo);*/
		int acId =  Integer.parseInt(map.get("acNo").toString());
		String toDate = (String) map.get("toDate");
		String[] spliSnToDate = toDate.split("T");

		@SuppressWarnings("unused")
		Date ptsValidTo = null;

		try {

			ptsValidTo = sdf.parse(spliSnToDate[0] + " " + spliSnToDate[1]);

		} catch (Exception e) {
			e.printStackTrace();
		}
		patrolTrackStaff.setPtId(ptId);
		patrolTrackStaff.setAcId(acId);
		patrolTrackStaff.setFromDate(ConvertDate.formattedDate((String) map
				.get("fromDate")));
		patrolTrackStaff.setToDate(ConvertDate.formattedDate((String) map
				.get("toDate")));
		patrolTrackStaff.setStatus("InActive");
		patrolTrackStaff.setCreatedBy(userName);
		patrolTrackStaff.setLastUpdatedBy(userName);

		validator.validate(patrolTrackStaff, bindingResult);

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());

			return errorResponse;
		}

		else {
			List<PatrolTrackStaff> allPatrolTrackStaff = patrolTrackStaffService
					.findStaffRecord(ptId, acId, staffId);
			if (allPatrolTrackStaff.size() > 0) {
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("invalid", messageSource.getMessage(
								"PatrolTrackStaff.unique.record", null, locale));
					}
				};
				errorResponse.setStatus("INVALID");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			} else if (count == 0) {
				patrolTrackStaffService.save(patrolTrackStaff);
				return patrolTrackStaff;

			}

		}
		return patrolTrackStaff;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/patrolTrackStaff/update", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object updatePatrolTrackStaff(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("patrolTrackStaff") PatrolTrackStaff patrolTrackStaff,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		int ptsId = (Integer) map.get("ptsId");
		String ptName = (String) map.get("ptName");
		int supervisorId=0;
		int acId=0;
		/*int supervisorId = (Integer) map.get("supervisorName");
		int supervisorId = (Integer) map.get("supervisorId");
		String acNo = (String) map.get("acNo");
		int acId = accessCardSevice.getAccesCardIdBasedOnNo(acNo);*/
		/*int acId =  Integer.parseInt(map.get("acId").toString());*/
		if(map.get("supervisorName") instanceof String){
			supervisorId=Integer.parseInt(map.get("supervisorId").toString());
		}else{
			supervisorId=Integer.parseInt(map.get("supervisorName").toString());
		}
		
		if(map.get("acNo") instanceof String){
			acId=Integer.parseInt(map.get("acId").toString());
		}else{
			acId=Integer.parseInt(map.get("acNo").toString());
		}
		int ptId = patrolTrackService.getPatrolTrackIdBasedOnName(ptName);
		PatrolTrackStaff patrolTrackStaffRecord = patrolTrackStaffService
				.getPatrolTrackStaffInstanceById(ptsId);

		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");

		String fromDate = (String) map.get("fromDate");
		String toDate = (String) map.get("toDate");
		String status = (String) map.get("status");
		String[] spliSnFromDate = fromDate.split("T");
		String[] spliSnToDate = toDate.split("T");

		Date ptsValidFrom = null;
		Date ptsValidTo = null;

		try {

			ptsValidFrom = sdf.parse(spliSnFromDate[0] + " "
					+ spliSnFromDate[1]);
			ptsValidTo = sdf.parse(spliSnToDate[0] + " " + spliSnToDate[1]);

		} catch (Exception e) {
			e.printStackTrace();
		}
		patrolTrackStaff.setPtsId((Integer) map.get("ptsId"));
		patrolTrackStaff.setPersonId((Integer) map.get("personId"));
		patrolTrackStaff.setSupervisorId(supervisorId);
		patrolTrackStaff.setAcId(acId);
		patrolTrackStaff.setPtId(ptId);
		patrolTrackStaff.setFromDate(ConvertDate.formattedDate((String) map
				.get("fromDate")));
		patrolTrackStaff.setToDate(ConvertDate.formattedDate((String) map
				.get("toDate")));
		patrolTrackStaff.setStatus(status);
		patrolTrackStaff.setCreatedBy(patrolTrackStaffRecord.getCreatedBy());
		patrolTrackStaff.setLastUpdatedBy(userName);

		patrolTrackStaffService.update(patrolTrackStaff);
		

		return patrolTrackStaff;

	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTrackStaff/delete", method = RequestMethod.POST)
	public @ResponseBody
	Object deletePatrolTrackStaff(
			@ModelAttribute("patrolTrackStaff") PatrolTrackStaff patrolTrackStaff,
			@RequestBody Map<String, Object> map, final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		int ptsId = (Integer) map.get("ptsId");

		String status = patrolTrackStaffService.getStatusBasedOnId(ptsId);
		if (status.equals("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("deletestafferror", messageSource.getMessage(
							"PatrolTrackStaff.deletestafferror", null, locale));
				}
			};
			errorResponse.setStatus("DELETESTAFFERROR");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;

		}

		else {
			patrolTrackStaffService.delete(ptsId);
			return readPatrolTrackStaff();

		}

	}

	@RequestMapping(value = "/staffAttendanceSummary")
	public String staffAttendanceSummaryIndex(HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Staff Attendance", 2, request);
		return "timeAndAttendance/staffAttendanceSummary";
	}

	@RequestMapping(value = "/staffAttendanceSummary/getDateList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getDateList() {

		Set<String> result = new HashSet<String>();
		for (StaffAttendanceSummary staffAttendanceSummary : staffAttendanceSummaryService
				.findAll()) {
			try {
				String sasDate = staffAttendanceSummary.getSasDate().toString();
				SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
				Date dt = input.parse(sasDate);
				String formattedDate = output.format(dt);
				result.add(formattedDate);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/staffAttendanceSummary/getStaffNameList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getStaffNameListFromAtt() {

		Set<String> result = new HashSet<String>();
		List<Personnel> personState = msSQLAccessCardsEntityManager
				.createNamedQuery("Personnel.findmsSql").getResultList();

		for (Iterator<?> i = personState.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			if (values[2] != null) {
				result.add((String) values[1] + " " + (String) values[2]);
			} else {
				result.add((String) values[1]);
			}
		}
		return result;

	}

	// ------------------------------Time And
	// Attendance------------------------------------------------------------------

	@SuppressWarnings({"unchecked"})
	@RequestMapping(value = "/staffAttendanceSummary/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readStaffAndAttendance() throws ParseException {
		
		
		
		List<?> StaffData = new ArrayList<>();
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		String currentMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		String month = currentMonth + " " + year;
		 
		logger.info("montOne" + montOne);
		logger.info("year" + year);
		StaffData = readStaffData(msSQLAccessCardsEntityManager
				.createNamedQuery("AccessEvent.readAttendanceMonthWise")
				.setParameter("month", montOne).setParameter("year", year)
				.getResultList(), month);
		return StaffData;
	
		/*
		 * List<Map<String, Object>> staffAttendanceSummary = new
		 * ArrayList<Map<String, Object>>();
		 * 
		 * List<Personnel>
		 * personAttendanceData=msSQLAccessCardsEntityManager.createNamedQuery
		 * ("Personnel.findmsSql").getResultList();
		 * 
		 * for (final Iterator<?> i = personAttendanceData.iterator();
		 * i.hasNext();) { staffAttendanceSummary.add(new HashMap<String,
		 * Object>() { { final Object[] values = (Object[])i.next();
		 * put("objectIdLo", (Integer)values[0]); if(values[2]!=null){
		 * put("staffName", (String)values[1]+" "+(String)values[2]); } else{
		 * put("staffName", (String)values[1]); } put("departmentName",
		 * (String)values[3]); put("accessCardNo", (Integer)values[4]);
		 * put("desigName", "Not Available"); DateFormat df = new
		 * SimpleDateFormat("MMMM yyyy");
		 * put("selectByMonth",df.format(values[5]) );
		 * 
		 * } }); } return staffAttendanceSummary;
		 */

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attendanceHistory/searchByMonth/{month}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> searcchByMonth(@PathVariable String month, HttpServletRequest req) {

		List<?> StaffData = new ArrayList<>();
		try {
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthToShow);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);
			logger.info("montOne" + montOne);
			logger.info("year" + year);
			StaffData = readStaffData(msSQLAccessCardsEntityManager
					.createNamedQuery("AccessEvent.readAttendanceMonthWise")
					.setParameter("month", montOne).setParameter("year", year)
					.getResultList(), month);
		} catch (ParseException e) {
			logger.info("Error while parsing date");
			e.printStackTrace();
		}
		return StaffData;
	}

	@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
	private List readStaffData(List<Integer> StaffData, final String month) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = StaffData.iterator(); i.hasNext();) {

			int personId = (Integer) i.next();
			logger.info("personId" + personId);
			final List<Personnel> personState = msSQLAccessCardsEntityManager
					.createNamedQuery("Personnel.findByObjectId")
					.setParameter("objectIdLo", personId).getResultList();
			if (!(personState.isEmpty())) {
				result.add(new HashMap<String, Object>() {
					{
						put("objectIdLo", personState.get(0).getObjectIdLo());
						put("accessCardNo", personState.get(0).getNonAbacardNumber());
						
						List<PersonAccessCards> pac=personAccessCardService.getPersonBasedOnAccessCard(personState.get(0).getObjectIdLo());
						if(!(pac.isEmpty())){
							
							List<Users> userForDesignation=	usersService.getDesigBasedOnPersonId(pac.get(0).getPersonId());
							if(!(userForDesignation.isEmpty())){
							
							put("desigName", userForDesignation.get(0).getDesignation().getDn_Name());
							put("departmentName", userForDesignation.get(0).getDepartment().getDept_Name());
							if (userForDesignation.get(0).getPerson().getLastName()!= null) {
								put("staffName", userForDesignation.get(0).getPerson().getFirstName()+ " " + userForDesignation.get(0).getPerson().getLastName());
							} else {
								put("staffName", userForDesignation.get(0).getPerson().getFirstName());
							}
							}
							else{
							put("desigName", "Not Available");
							if (personState.get(0).getLastName() != null) {
								put("staffName", personState.get(0).getFirstName()+ " " + personState.get(0).getLastName());
							} else {
								put("staffName", personState.get(0).getFirstName());
							}
							put("departmentName", personState.get(0).getDepartment());
							}
						}
						else{
							put("desigName", "Not Available");
							if (personState.get(0).getLastName() != null) {
								put("staffName", personState.get(0).getFirstName()+ " " + personState.get(0).getLastName());
							} else {
								put("staffName", personState.get(0).getFirstName());
							}
							put("departmentName", personState.get(0).getDepartment());
						}
						put("selectByMonth", month);
					}

				});
			}

		}
		return result;
	}

	@RequestMapping(value = "/viewAttendanceSummary/read/{objectId}/{month}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> searcchByMonth(@PathVariable int objectId,
			@PathVariable String month, HttpServletRequest req) {
		final Personnel personState = msSQLAccessCardsEntityManager
				.createNamedQuery("Personnel.findByObjectId", Personnel.class)
				.setParameter("objectIdLo", objectId).getSingleResult();
		List<?> StaffData = new ArrayList<>();
		try {
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthToShow);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);
			StaffData = readChildStaffData(
					msSQLAccessCardsEntityManager
							.createQuery(
									"SELECT MIN(ac.loginLogOut),MAX(ac.loginLogOut) FROM AccessEvent ac WHERE ac.personIdLo = "
											+ objectId
											+ " and EXTRACT(month FROM ac.loginLogOut) ="
											+ montOne
											+ " and EXTRACT(year FROM ac.loginLogOut) ="
											+ year
											+ " GROUP BY datediff(day,0, ac.loginLogOut)")
							.getResultList(), personState);
		} catch (ParseException e) {
			logger.info("Error while parsing date");
			e.printStackTrace();
		}
		return StaffData;
	}

	@SuppressWarnings({ "rawtypes", "serial", "static-access" })
	private List readChildStaffData(List<?> StaffData,
			final Personnel personState) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = StaffData.iterator(); i.hasNext();) {
			result.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[]) i.next();

					Date timeIn = (Timestamp) values[0];
					Date timeOut = (Timestamp) values[1];

					long diff = timeOut.getTime() - timeIn.getTime();
					
					
					final Double diffInHours = diff / ((double) 1000 * 60 * 60);
					String s = String.format("%.2f", diffInHours);
					final double diffInHours1 = (double) diffInHours
							.parseDouble(s);

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa",java.util.Locale.getDefault());
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

					put("sasDateput", sdf1.format(values[0]));
					put("timeIn", sdf.format(values[0]));
					put("timeOut", sdf.format(values[1]));
					put("timeSpent", diffInHours1);
					if (personState.getState() == 0) {
						put("timeOutSuccessfull", "IN");
					} else {
						put("timeOutSuccessfull", "OUT");
					}

				}
			});
		}
		return result;
	}

	@SuppressWarnings({ "static-access", "serial" })
	@RequestMapping(value = "/viewAttendanceSummary/read/{objectIdLo}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> viewAttendanceSummary(@PathVariable int objectIdLo)
			throws ParseException {

		final Personnel personState = msSQLAccessCardsEntityManager
				.createNamedQuery("Personnel.findByObjectId", Personnel.class)
				.setParameter("objectIdLo", objectIdLo).getSingleResult();
		List<Map<String, Object>> staffAttendanceSummary = new ArrayList<Map<String, Object>>();

		List<?> loginOut = msSQLAccessCardsEntityManager
				.createQuery(
						"SELECT MIN(ac.loginLogOut),MAX(ac.loginLogOut) FROM AccessEvent ac WHERE ac.personIdLo = "
								+ objectIdLo
								+ " GROUP BY datediff(day,0, ac.loginLogOut)")
				.getResultList();

		for (final Iterator<?> i = loginOut.iterator(); i.hasNext();) {
			staffAttendanceSummary.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[]) i.next();

					Date timeIn = (Timestamp) values[0];
					Date timeOut = (Timestamp) values[1];

					long diff = timeOut.getTime() - timeIn.getTime();
					final Double diffInHours = diff / ((double) 1000 * 60 * 60);
					String s = String.format("%.2f", diffInHours);
					final double diffInHours1 = (double) diffInHours
							.parseDouble(s);

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

					put("sasDateput", sdf1.format(values[0]));
					put("timeIn", sdf.format(values[0]));
					put("timeOut", sdf.format(values[1]));
					put("timeSpent", diffInHours1);
					if (personState.getState() == 0) {
						put("timeOutSuccessfull", "IN");
					} else {
						put("timeOutSuccessfull", "OUT");
					}

				}
			});
		}
		return staffAttendanceSummary;

	}

	@SuppressWarnings({ "static-access", "serial" })
	@RequestMapping(value = "/staffAttendanceSummary/readAverage", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAverage() {

		List<Map<String, Object>> staffAttendanceSummary = new ArrayList<Map<String, Object>>();
		for (final StaffAttendanceSummary record : staffAttendanceSummaryService
				.findAll()) {
			Date timeIn = record.getTimeIn();
			Date timeOut = record.getTimeOut();

			try {
				long diff = timeOut.getTime() - timeIn.getTime();
				final Double diffInHours = diff / ((double) 1000 * 60 * 60);
				String s = String.format("%.2f", diffInHours);
				final double diffInHours1 = (double) diffInHours.parseDouble(s);

				SimpleDateFormat timeInFormate = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat timeOutFormate = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");

				Date dt1 = timeInFormate.parse(timeIn.toString());
				final String totalTimeInFormat = timeOutFormate.format(dt1);
				Date dt2 = timeInFormate.parse(timeOut.toString());
				final String totalTimeOutFormat = timeOutFormate.format(dt2);

				staffAttendanceSummary.add(new HashMap<String, Object>() {
					{
						put("sasId", record.getSasId());
						if (record.getPerson().getLastName() == null) {
							put("firstName", record.getPerson().getFirstName());
						} else {
							put("firstName", record.getPerson().getFirstName()
									+ " " + record.getPerson().getLastName());
						}
						put("dn_Id", record.getDnId());
						put("dn_Name", record.getDesignation().getDn_Name());
						put("dept_Name", record.getDepartment().getDept_Name());
						put("dept_Id", record.getDeptId());
						put("sasDate", record.getSasDate());
						put("timeIn", totalTimeInFormat);
						put("timeOut", totalTimeOutFormat);
						put("timeSpent", diffInHours1);
						put("timeOutSuccessfull",
								record.getTimeOutSuccessfull());
					}
				});
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return staffAttendanceSummary;

	}

	@RequestMapping(value = "/staffAttendanceSummary/getInTimeList")
	public @ResponseBody
	Set<?> getInTimeList() {

		final Set<String> result = new HashSet<String>();
		for (final StaffAttendanceSummary record : staffAttendanceSummaryService
				.findAll()) {

			Date timeIn = record.getTimeIn();

			try {

				SimpleDateFormat timeInFormate = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat timeOutFormate = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");

				Date dt1 = timeInFormate.parse(timeIn.toString());
				final String totalTimeInFormat = timeOutFormate.format(dt1);
				result.add(totalTimeInFormat);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	@RequestMapping(value = "/staffAttendanceSummary/getOutTimeList")
	public @ResponseBody
	Set<?> getOutTimeList() {

		final Set<String> result = new HashSet<String>();

		for (final StaffAttendanceSummary record : staffAttendanceSummaryService
				.findAll()) {

			Date timeOut = record.getTimeOut();

			try {

				SimpleDateFormat timeInFormate = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat timeOutFormate = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");

				Date dt2 = timeInFormate.parse(timeOut.toString());
				final String totalTimeOutFormat = timeOutFormate.format(dt2);
				result.add(totalTimeOutFormat);

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/staffAttendanceSummary/getSpentTimeList")
	public @ResponseBody
	Set<?> getSpentTimeList() {

		final Set<String> result = new HashSet<String>();
		for (final StaffAttendanceSummary record : staffAttendanceSummaryService
				.findAll()) {
			Date timeIn = record.getTimeIn();
			Date timeOut = record.getTimeOut();

			try {
				long diff = timeOut.getTime() - timeIn.getTime();
				final Double diffInHours = diff / ((double) 1000 * 60 * 60);
				String s = String.format("%.2f", diffInHours);
				final double diffInHours1 = (double) diffInHours.parseDouble(s);
				result.add(diffInHours1 + " hrs");

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/staffAttendanceSummary/getAvgSpentTimeList")
	public @ResponseBody
	Set<?> getAvgSpentTimeList() {

		final Set<Double> result = new HashSet<Double>();
		for (final StaffAttendanceSummary record : staffAttendanceSummaryService
				.findAll()) {
			Date timeIn = record.getTimeIn();
			Date timeOut = record.getTimeOut();

			try {
				long diff = timeOut.getTime() - timeIn.getTime();
				final Double diffInHours = diff / ((double) 1000 * 60 * 60);
				String s = String.format("%.2f", diffInHours);
				final double diffInHours1 = (double) diffInHours.parseDouble(s);
				result.add(diffInHours1);

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@RequestMapping(value = "/staffAttendanceSummary/getDesignationList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getDesignationList() {
		Set<String> result = new HashSet<String>();

		List<Integer> desIdList = staffAttendanceSummaryService
				.getDistinctDesignationId();

		for (Iterator<Integer> iterator = desIdList.iterator(); iterator
				.hasNext();) {
			Integer desId = (Integer) iterator.next();

			String desName = designationService
					.getDesignationNameBasedOnId(desId);
			result.add(desName);

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/staffAttendanceSummary/getDepartmentList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getDepartmentList() {
		Set<String> result = new HashSet<String>();
		List<Personnel> personState = msSQLAccessCardsEntityManager
				.createNamedQuery("Personnel.findmsSql").getResultList();

		for (Iterator<?> i = personState.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			if (values[3] != null) {
				result.add((String) values[3]);
			}

		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/staffAttendanceSummary/accessCardNoUrl", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> accessCardNoUrl() {
		Set<String> result = new HashSet<String>();
		List<Personnel> personState = msSQLAccessCardsEntityManager
				.createNamedQuery("Personnel.findmsSql").getResultList();

		for (Iterator<?> i = personState.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			if (values[4] != null) {
				result.add(String.valueOf(values[4]));
			}

		}
		return result;
	}

	@RequestMapping(value = "/patrolTrackAlert")
	public String patrolTrackAlertIndex(HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Patrol Alerts", 2, request);
		return "timeAndAttendance/patrolTrackAlert";
	}

	@RequestMapping(value = "/patrolTrackAlert/getPatrolTrackNamesList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getPatrolTrackNamesFromAlert() {

		Set<String> result = new HashSet<String>();
		List<?> patrolTrackAlertList = patrolTrackAlertService.findAll();

		for (final Iterator<?> i = patrolTrackAlertList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			result.add((String) values[1]);
		}

		return result;
	}

	@RequestMapping(value = "/patrolTrackAlert/getStaffNameList", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getStaffNameList1() {

		Set<String> result = new HashSet<String>();
		List<?> patrolTrackAlertList = patrolTrackAlertService.findAll();

		for (final Iterator<?> i = patrolTrackAlertList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			if ((String) values[3] == null) {
				result.add((String) values[2]);
			} else {
				result.add((String) values[2] + " " + (String) values[3]);
			}
		}
		return result;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolTrackAlert/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPatrolTrackAlert() {
		List<Map<String, Object>> patrolTrackAlert = new ArrayList<Map<String, Object>>();
		List<?> patrolTrackAlertList = patrolTrackAlertService.findAll();

		for (final Iterator<?> i = patrolTrackAlertList.iterator(); i.hasNext();) {

			patrolTrackAlert.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[]) i.next();

					put("ptaId", (Integer) values[0]);
					put("ptName", (String) values[1]);
					if ((String) values[3] == null) {
						put("firstName", (String) values[2]);
					} else {
						put("firstName", (String) values[2] + " "
								+ (String) values[3]);
					}
					put("message", (String) values[4]);
					put("ptaDt", (String) values[5]);

				}
			});
		}
		return patrolTrackAlert;
	}

	@RequestMapping(value = "/staffAttendanceGateOutAlert")
	public String staffAttendanceGateOutAlertIndex(HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Gate Out Alerts", 2, request);
		return "timeAndAttendance/staffAttendanceGateOutAlert";
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/staffAttendanceGateOutAlert/read", method = RequestMethod.POST)
	public @ResponseBody List<?> reaStaffAttendanceGateOutAlert()
	{
		List<Map<String, Object>> staffAttendanceGateOutAlert =  new ArrayList<Map<String, Object>>(); 
		for (final StaffAttendanceGateOutAlert record : staffAttendanceGateOutAlertService.findAll()) {
			staffAttendanceGateOutAlert.add(new HashMap<String, Object>() {{
				put("sagaId", record.getSagaId());
				if( record.getPerson().getLastName() == null ){
					put("firstName", record.getPerson().getFirstName());
				}
				else{
				put("firstName", record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
				}
				put("message", record.getMessage());
				put("sagaDt", ConvertDate.TimeStampString(record.getSagaDt()));

			}});
		}
		return staffAttendanceGateOutAlert;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Scheduled(cron ="${scheduling.job.checkLoginLogOut}")
	public  void createCronAtMidNight() {
		
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month1 = cal.get(Calendar.MONTH);
		int montOne = month1 + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		logger.info("Month-----------"+montOne);
		logger.info("Year-----------"+year);
		logger.info("Day-----------"+day);
		List<AccessEvent> staffAttendanceGateOut = msSQLAccessCardsEntityManager.createQuery("SELECT DISTINCT(ac.personIdLo),MIN(ac.loginLogOut),MAX(ac.loginLogOut) FROM AccessEvent ac  WHERE EXTRACT(month FROM ac.loginLogOut) ="+ montOne+" AND EXTRACT(year FROM ac.loginLogOut) ="+ year+" AND EXTRACT(day FROM ac.loginLogOut) ="+ day+" AND ac.personIdLo != 0 GROUP BY datediff(day,0, ac.loginLogOut),ac.personIdLo").getResultList();
		String emailId="";
		String mobileNo="";	
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		if(!(staffAttendanceGateOut.isEmpty())){
		for (final Iterator<?> i = staffAttendanceGateOut.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			Date timeIn = (Timestamp) values[1];
			Date timeOut = (Timestamp) values[2];
			logger.info("timeIn-----------"+timeIn);
			logger.info("timeOut-----------"+timeOut);
			if (timeIn.getTime() == timeOut.getTime()) {
				logger.info("timeIn " + timeIn + "timeOut" + timeOut);
				

							int personId=(Integer) values[0];
							logger.info("personId"+personId);
						    List<Personnel> personnelData=msSQLAccessCardsEntityManager.createNamedQuery("Personnel.findByObjectId").setParameter("objectIdLo",personId).getResultList();
							if(!(personnelData.isEmpty())){
							List<PersonAccessCards> list =  staffAttendanceGateOutAlertService.getPersonAccessCardsByAcid(personId);
							
							if(!(list.isEmpty())){
							for (final PersonAccessCards record : list) {
							StaffAttendanceGateOutAlert staff=new StaffAttendanceGateOutAlert();
							
							staff.setMessage("You did not log out for the day");
							staff.setSagaDt((Timestamp)values[2]);
							staff.setPersonId(record.getPersonId());
							logger.info("----------------------------personId"+record.getPersonId());
							Person users = personService.getPersonBasedOnId(record.getPersonId());
							Set<Contact> contact=users.getContacts();
							

						for (Iterator iterator = contact.iterator(); iterator
								.hasNext();) {
							Contact contact2 = (Contact) iterator.next();
							if ((contact2.getContactType()
									.equalsIgnoreCase("Email"))
									&& (contact2.getContactPrimary()
											.equalsIgnoreCase("Yes"))) {
								emailId = contact2.getContactContent();
								staff.setSagaEmail(emailId);
								EmailCredentialsDetailsBean emailCredentialsDetailsBean = null;
								try {
									emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
								} catch (Exception e) {
									e.printStackTrace();
								}

								String messageContent = "<html><body>"
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON ADMINISTRATION DEPARTMENT</h2> <br /> Dear "
										+ users.getFirstName()
										+ ",<br/> <br/>You have not logout on "
										+ sdf1.format(values[2])
										+ " "
										+ "<br/>Thanks,<br/>"
										+ "Skyon Administration Department.<br/> <br/>"
										+ "</body></html>";

								emailCredentialsDetailsBean
										.setToAddressEmail(emailId);
								emailCredentialsDetailsBean
										.setMessageContent(messageContent);

								new Thread(new SendMailForStaff(
										emailCredentialsDetailsBean)).start();
							}
							if ((contact2.getContactType()
									.equalsIgnoreCase("Mobile"))
									&& (contact2.getContactPrimary()
											.equalsIgnoreCase("Yes"))) {

								mobileNo = contact2.getContactContent();
								staff.setSagaMobileNo(mobileNo);
								String userMessage = "Dear "
										+ users.getFirstName()
										+ ", You have not logout on "
										+ sdf1.format(values[2])
										+"From Skyon Administration Department.";

								SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();

								smsCredentialsDetailsBean.setNumber(mobileNo);
								smsCredentialsDetailsBean.setUserName(users
										.getFirstName());
								smsCredentialsDetailsBean
										.setMessage(userMessage);
								new Thread(new SendSMSForStatus(
										smsCredentialsDetailsBean)).start();
							}
						}
							
							staffAttendanceGateOutAlertService.save(staff);
							
						}
					}
			      }
				}
			}
		}
	}
					
	
	@RequestMapping(value = "/staffAttendanceGateOutAlert/getStaffNames")
	public @ResponseBody
	Set<?> getStaffNames2() {

		Set<String> result = new HashSet<String>();
		for (StaffAttendanceGateOutAlert staffAttendanceGateOutAlert : staffAttendanceGateOutAlertService
				.findAll()) {
			if (staffAttendanceGateOutAlert.getPerson().getLastName() == null) {
				result.add(staffAttendanceGateOutAlert.getPerson()
						.getFirstName());
			} else {
				result.add(staffAttendanceGateOutAlert.getPerson()
						.getFirstName()
						+ " "
						+ staffAttendanceGateOutAlert.getPerson().getLastName());
			}
		}
		return result;
	}

	@RequestMapping(value = "/patrolSettings")
	public String patrolSettingsIndex(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ViewName", "Time & Attendance");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Time & Attendance", 1, request);
		breadCrumbService.addNode("Manage Patrol Settings", 2, request);
		return "timeAndAttendance/patrolSettings";
	}

	/*
	 * @method fetching role details for multi selection dropdown
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolSettings/getrolenames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getRole() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Role record : roleService.getAllActiveRoles()) {
			result.add(new HashMap<String, Object>() {
				{
					put("rlName", record.getRlName());
					put("rlId", record.getRlId());
				}
			});
		}
		return result;

	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolSettings/read", method = RequestMethod.POST)
	public @ResponseBody
	Object read(final Locale locale) {
		List<Map<String, Object>> patrolSettings = new ArrayList<Map<String, Object>>();
		for (final PatrolSettings record : patrolSettingsService.findAll()) {
			int ids = record.getRlId();
			final String roleName = roleService.getRoleNameBasedOnId(ids);
			patrolSettings.add(new HashMap<String, Object>() {
				{
					put("rlId", roleName);
					put("psId", record.getPsId());
					put("status", record.getStatus());

				}
			});

		}

		return patrolSettings;

		// return roleList;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/patrolSettings/create", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object createRole(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model,
			@ModelAttribute("patrolSettings") PatrolSettings patrolSettings,
			BindingResult bindingResult, final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		int rlId = (Integer) map.get("rlId");
		int count = 0;
		List<Integer> dbRlIds = patrolSettingsService.getAllRoleIds();
		for (Iterator<Integer> iterator = dbRlIds.iterator(); iterator
				.hasNext();) {
			int rlIds = (Integer) iterator.next();
			if (rlIds == rlId) {
				count = 1;
				break;
			}
		}
		if (count == 1) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("existrole", messageSource.getMessage(
							"PatrolSettings.existrole.role", null, locale));
				}
			};
			errorResponse.setStatus("EXISTROLE");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		} else {
			patrolSettings.setRlId(rlId);
			patrolSettings.setStatus("InActive");
		}
		validator.validate(patrolSettings, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());

			return errorResponse;
		} else {
			patrolSettingsService.save(patrolSettings);
			return patrolSettings;
		}

	}

	@RequestMapping(value = "/patrolSettings/delete", method = RequestMethod.POST)
	public @ResponseBody
	PatrolSettings deleteRole(@RequestBody Map<String, Object> map)
			throws IOException {

		PatrolSettings patrolSettings = new PatrolSettings();
		int psId = (Integer) map.get("psId");
		patrolSettingsService.delete(psId);
		return patrolSettings;

	}

	@RequestMapping(value = "/patrolSettings/getPatrolRole", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getPatrolRole() {
		return patrolTrackService.getAllPatrolRoles();
	}

	@RequestMapping(value = "/patrolSettings/getrolenamesForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getrolenamesForFilter() {

		Set<String> result = new HashSet<String>();
		for (final PatrolSettings record : patrolSettingsService.findAll()) {
			result.add(record.getRole().getRlName());

		}
		return result;
	}
}
