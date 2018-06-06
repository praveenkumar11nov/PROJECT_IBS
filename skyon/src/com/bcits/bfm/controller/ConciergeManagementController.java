package com.bcits.bfm.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.bcits.bfm.model.ConciergeServices;
import com.bcits.bfm.model.ConciergeVendorServices;
import com.bcits.bfm.model.ConciergeVendors;
import com.bcits.bfm.model.CsServiceBooking;
import com.bcits.bfm.model.CsVendorCommentsRating;
import com.bcits.bfm.model.CsVendorServiceCharges;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.ConciergeService;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorSer;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorsService;
import com.bcits.bfm.service.facilityManagement.CsServiceBookingService;
import com.bcits.bfm.service.facilityManagement.CsVendorCommentsRatingService;
import com.bcits.bfm.service.facilityManagement.CsVendorServiceChargeService;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module
 * Module: Concierge Management
 * 
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class ConciergeManagementController {

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private ConciergeService  conciergeService;
	
	@Autowired
	private DrGroupIdService drGroupIdService;
	
	@Autowired
	private JsonResponse errorResponse;

	@Autowired
	private ConciergeVendorSer  conciergeVendorSer;//for vendor service

	@Autowired
	private Validator validator;

	@Autowired
	private CommonController commonController;

	@Autowired
	private PersonService personService;

	@Autowired
	private ConciergeVendorsService conciergeVendorsService;//for concierge vendors

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CsVendorServiceChargeService csVendorServiceChargeService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private CsVendorCommentsRatingService csVendorCommentsRatingService;
	
	@Autowired
	private CsServiceBookingService csServiceBookingService;
	

	@Resource
	private ValidationUtil validationUtil;
	
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	private static final Log logger = LogFactory
			.getLog(ParkingSlotsAllotmentServiceImpl.class);

	@RequestMapping(value="/conciergedashboard")
	public String conciergeDashboardIndex(HttpServletRequest request,ModelMap model)
	{
		model.addAttribute("ViewName", "Dashboard");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Dashboard", 1, request);
		return "concierge/conciergedashboard";
	}
	@RequestMapping(value="/conciergeServices")
	public String conciergeServicesIndex(HttpServletRequest request,ModelMap model)
	{
		model.addAttribute("ViewName", "Concierge");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Concierge", 1, request);
		breadCrumbService.addNode("Manage Concierge Services", 2, request);
		return "concierge/conciergeServices";
	}
	@RequestMapping(value="/conciergeVendors")
	public String conciergeVendorsIndex(HttpServletRequest request,ModelMap model, final Locale locale)
	{
		model.addAttribute("ViewName", "Concierge");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Concierge", 1, request);
		breadCrumbService.addNode("Manage Concierge Vendors", 2, request);

		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("title", commonController.populateCategories("title", locale));
		model.addAttribute("sex", commonController.populateCategories("sex", locale));
		model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
		model.addAttribute("nationality", commonController.populateCategories("nationality", locale));
		model.addAttribute("workNature", commonController.populateCategories("workNature", locale));

		model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));

		return "concierge/conciergeVendors";
	}
	@RequestMapping(value="/vendorServices")
	public String conciergeVendorServicesIndex(HttpServletRequest request,ModelMap model)
	{
		model.addAttribute("ViewName", "Concierge");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Concierge", 1, request);
		breadCrumbService.addNode("Manage Concierge Vendor Services", 2, request);

		//model.addAttribute("ConciergeService", conciergeService.getAllServices());
		return "concierge/vendorServices";
	}
	@RequestMapping(value="/serviceBooking")
	public String serviceBookingIndex(HttpServletRequest request,ModelMap model)
	{
		model.addAttribute("ViewName", "Concierge");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Concierge", 1, request);
		breadCrumbService.addNode("Manage Service Booking", 2, request);

		//model.addAttribute("ConciergeService", conciergeService.getAllServices());
		return "concierge/serviceBooking";
	}
	
	
	/* ==================================  Concierge Service================================== */
	@RequestMapping(value = "/conciergeManagement/csServiceStatus/{cssId}/{operation}", method = RequestMethod.POST)
	public String csServiceStatus(@PathVariable int cssId,@PathVariable String operation,ModelMap model, 
			HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{		
		conciergeService.setCsServiceStatus(cssId, operation, response, messageSource, locale);
		return null;
	}
	@RequestMapping(value = "/csService/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readCsServices() {
		return conciergeService.getResponse();
	}

	@RequestMapping(value ="/csService/create",method = RequestMethod.GET)
	public @ResponseBody Object CreateCsService(@ModelAttribute("conciergeServices")ConciergeServices conciergeServices, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, HttpServletRequest req) throws ParseException
			{

		String startDate = req.getParameter("serviceStartDate");
		conciergeServices.setServiceStartDate(dateTimeCalender.getDateToStore(startDate));
		conciergeServices.setServiceEndDate(null);
		conciergeServices.setStatus("InActive");
		conciergeService.save(conciergeServices);
		return conciergeServices;

			}

	@RequestMapping(value = "/csService/update", method = RequestMethod.GET)
	public @ResponseBody
	Object updateCsService(@ModelAttribute("conciergeServices")ConciergeServices conciergeServices, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, HttpServletRequest req) throws ParseException{

		String startDate = req.getParameter("serviceStartDate");
		conciergeServices.setServiceStartDate(dateTimeCalender.getDateToStore(startDate));
		conciergeServices.setServiceEndDate(conciergeService.find(conciergeServices.getCssId()).getServiceEndDate());

		conciergeService.update(conciergeServices);
		return conciergeServices;
		
	}
	@RequestMapping(value = "/csService/updateServiceEndDt/{endDate}/{serviceId}",  method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void updateServiceEndDt(@PathVariable String endDate ,@PathVariable int serviceId, HttpServletRequest request , HttpServletResponse res) throws ParseException {
		
		java.util.Date date1 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(endDate);
		String dateStr ="";
		try{
		dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		java.sql.Date serviceEndDate =  dateTimeCalender.getDateFromString(dateStr);
		PrintWriter out = null;
		try {
			out = res.getWriter();
			conciergeService.updateServiceEndDate(serviceId,serviceEndDate);
			out.write("Success");
			
			
		} catch (Exception e) {
			
			out.write("false");
		}
		
	}

	@RequestMapping(value = "/csService/delete", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteService(@ModelAttribute("conciergeServices")ConciergeServices conciergeServices, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		try{
			conciergeService.delete(conciergeServices.getCssId());
			conciergeServices.setServiceStartDate(null);
			conciergeServices.setServiceEndDate(null);
			conciergeServices.setLastUpdatedDt(null);
			return conciergeServices;
		}
		catch (Exception e) {
			@SuppressWarnings("serial")
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
	/* ==================================  Concierge Vendors================================= */
	@RequestMapping(value = "/conciergeVendorPerson/create/{operation}/{personType}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object createConciergerVendorPerson(@PathVariable String operation, @PathVariable String personType, @ModelAttribute("person") Person person, 
			BindingResult bindingResult, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req)
	{
		
		HttpSession session = req.getSession(false);
		String userName = (String)session.getAttribute("userId");
		if( req.getParameter("existedPersonId") != "" ){
			int existedPersonId = Integer.parseInt(req.getParameter("existedPersonId"));
		if ( existedPersonId != 0 ) {
			
			person = personService.find(existedPersonId);
			personService.updatePersonType(person, personType);
			person = personService.find(existedPersonId);
			
			ConciergeVendors conciergeVendors = new ConciergeVendors();
			conciergeVendors.setPerson(person);
			//conciergeVendors.setPersonId(existedPersonId);
			conciergeVendors.setCstNo(req.getParameter("cstNo"));
			conciergeVendors.setStateTaxNo(req.getParameter("stateTaxNo"));
			conciergeVendors.setServiceTaxNo(req.getParameter("serviceTaxNo"));
			conciergeVendors.setStatus("Inactive");
			conciergeVendors.setCreatedBy(userName);
			conciergeVendors.setLastUpdatedBy(userName);
			conciergeVendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setConciergeVendors(conciergeVendors);
			
			personService.update(person);	
			person.setPersonImages(null);
			sessionStatus.setComplete();
			return personService.getPersonFullDetails(person);
			
			//conciergeVendorsService.save(conciergeVendors);
		}
		}
		else{
			try
			{
				//person.setPersonType(personType);
				person = personService.getPersonNewObject(person, operation);

				if(!((person.getPersonStyle()).equalsIgnoreCase("Individual")))
				{
					if((validationUtil.customValidateModelObject(person, "NonIndividual", locale)) != null)
						return validationUtil.customValidateModelObject(person, "NonIndividual", locale);
				}
				else
				{
					if((validationUtil.customValidateModelObject(person, "Individual", locale)) != null)
						return validationUtil.customValidateModelObject(person, "Individual", locale);
				}

				if(operation.equalsIgnoreCase("create"))
				{
					drGroupIdService.save(new DrGroupId(person.getCreatedBy(), person.getLastUpdatedDt()));
					person.setDrGroupId(drGroupIdService.getNextVal(person.getCreatedBy(), person.getLastUpdatedDt()));
					
					person = setChildSaveDetails(person, person.getPersonType(), req);
					personService.save(person);	
					//person.setPersonId(personService.getPersonIdBasedOnCreatedByAndLastUpdatedDt(person.getCreatedBy(), person.getLastUpdatedDt()));

				}
				
				person.setPersonImages(null);
				sessionStatus.setComplete();
				return personService.getPersonFullDetails(person);
			}

			catch (Exception e)
			{
				e.printStackTrace();
				return errorResponse.throwException(locale);
			}
		}
		
		return null;
		
	}
	private Person setChildSaveDetails(Person person, String personType, HttpServletRequest req)
	{
		
		
		ConciergeVendors conciergeVendors = new ConciergeVendors();
		conciergeVendors.setPerson(person);
		conciergeVendors.setCstNo(req.getParameter("cstNo"));
		conciergeVendors.setStateTaxNo(req.getParameter("stateTaxNo"));
		conciergeVendors.setServiceTaxNo(req.getParameter("serviceTaxNo"));
		conciergeVendors.setStatus("Inactive");
		conciergeVendors.setCreatedBy(person.getLastUpdatedBy());
		conciergeVendors.setLastUpdatedBy(person.getLastUpdatedBy());
		conciergeVendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		conciergeVendors.setPerson(person);
		person.setConciergeVendors(conciergeVendors);
		
		return person;
	}
	/* ==================================  Concierge Vendor Service================================== */
	@RequestMapping(value = "/conciergeManagement/vendorServiceStatus/{vsId}/{operation}", method = RequestMethod.POST)
	public String vendorServiceStatus(@PathVariable int vsId,@PathVariable String operation,ModelMap model, 
			HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{		
		conciergeVendorSer.vendorServiceStatus(vsId, operation, response, messageSource, locale);
		return null;
	}
	@RequestMapping(value = "/vendorServices/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readVendorServices()
	{
		return conciergeVendorSer.getVendorServices();

	}
	@RequestMapping(value = "/vendorServices/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object createVendorServices(@RequestBody Map<String, Object> map,HttpServletRequest request,
			HttpServletResponse response,ModelMap model,@ModelAttribute("conciergeVendorServices") ConciergeVendorServices conciergeVendorServices, 
			BindingResult bindingResult,final Locale locale) throws ParseException{
		
		JsonResponse errorResponse = new JsonResponse();
		int count = 0;
		String serviceName = (String)map.get("conciergeService");
		int serviceId = conciergeService.getServiceIdBasedOnName(serviceName);
		int csvId = (Integer)map.get("conciergeVendors");
		List<ConciergeVendorServices> list = conciergeVendorSer.findAll();
		for (Iterator<ConciergeVendorServices> iterator = list.iterator(); iterator.hasNext();) {
			ConciergeVendorServices conciergeVendorServices2 = (ConciergeVendorServices) iterator
					.next();
		if( conciergeVendorServices2.getCssId() == serviceId && conciergeVendorServices2.getCsvId() == csvId ){
			count = 1;
			break;
		}
			}
		if( count == 1 ){
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("createExists", messageSource.getMessage(
							"conciergeVendorServices.update.exists", null, locale));
				}
			};
			errorResponse.setStatus("CREATEEXIST");
			errorResponse.setResult(errorMapResponse);
			
			return errorResponse;
			
		}
		else{
		conciergeVendorServices = conciergeVendorSer.getVendorServiceObject(map, "save",conciergeVendorServices,request);
		validator.validate(conciergeVendorServices, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			

			return errorResponse;
		}
		else{
			conciergeVendorSer.save(conciergeVendorServices);
			return conciergeVendorServices;
		}
		}
	}
	@RequestMapping(value = "/vendorServices/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object updateVendorServices(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("conciergeVendorServices") ConciergeVendorServices conciergeVendorServices, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) throws ParseException{

		JsonResponse errorResponse = new JsonResponse();
		
		conciergeVendorServices = conciergeVendorSer.getVendorServiceObject(map, "update",conciergeVendorServices,request);

		validator.validate(conciergeVendorServices, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());

			return errorResponse;
		}
			conciergeVendorSer.update(conciergeVendorServices);
			return conciergeVendorServices;
	}
	@RequestMapping(value = "/vendorServices/updateCsVendorServiceEndDt/{vendorServiceEndDate}/{vendorServiceId}",  method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void updateCsVendorServiceEndDt(@PathVariable String vendorServiceEndDate ,@PathVariable int vendorServiceId, HttpServletRequest request , HttpServletResponse res) throws ParseException {
		
		
		java.util.Date date1 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(vendorServiceEndDate);
		String dateStr ="";
		try{
		dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		java.sql.Date serviceEndDate =  dateTimeCalender.getDateFromString(dateStr);
		PrintWriter out = null;
		try {
			out = res.getWriter();
			conciergeVendorSer.updateServiceEndDate(vendorServiceId,serviceEndDate);
			out.write("Success");
			
			
		} catch (Exception e) {
			
			out.write("false");
		}
	}

	@RequestMapping(value = "/vendorServices/delete", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteVendorServices(@RequestBody Map<String, Object> map,@ModelAttribute("conciergeVendorServices") ConciergeVendorServices conciergeVendorServices, BindingResult bindingResult,
			ModelMap model,final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		int vsId = (Integer)map.get("vsId");
		try{
			conciergeVendorSer.delete(vsId);
			return conciergeVendorServices;
		}
		catch (Exception e) {
			@SuppressWarnings("serial")
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
	/* ================================== Vendor Services : Charge ================================== */
	
	@RequestMapping(value = "/vendorServices/charge/read/{vsId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readServiceCharge(@PathVariable int vsId) {
		return csVendorServiceChargeService.getResponse(vsId);
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value ="/vendorServices/charge/create/{vsId}",method = RequestMethod.GET)
	public @ResponseBody Object CreateServiceCharge(@ModelAttribute("csVendorServiceCharges")CsVendorServiceCharges csVendorServiceCharges, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,  @PathVariable int vsId,HttpServletRequest req) throws ParseException
			{
		JsonResponse errorResponse = new JsonResponse();
		List<CsVendorServiceCharges> list = csVendorServiceChargeService.findAll();
		for (Iterator<CsVendorServiceCharges> iterator = list.iterator(); iterator.hasNext();) {
			CsVendorServiceCharges csVendorServiceCharges2 = (CsVendorServiceCharges) iterator
					.next();
			if( csVendorServiceCharges2.getCollectionMethod().equals(csVendorServiceCharges.getCollectionMethod()) && csVendorServiceCharges2.getVsId() == vsId && csVendorServiceCharges2.getVendorRateType().equals(csVendorServiceCharges.getVendorRateType())   ){
				
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("vendorRateDuplicate", messageSource.getMessage(
								"serviceCharge.vendorRateTyape.duplicate", null, locale));
					}
				};
				errorResponse.setStatus("VENDORRATEDUPLICATE");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}
		}
		csVendorServiceCharges.setVsId(vsId);
		csVendorServiceChargeService.save(csVendorServiceCharges);
		
		return csVendorServiceChargeService.getResponse(vsId);
		
			}

	@RequestMapping(value = "/vendorServices/charge/update/{vsId}", method = RequestMethod.GET)
	public @ResponseBody
	Object updateServiceCharge(@ModelAttribute("csVendorServiceCharges")CsVendorServiceCharges csVendorServiceCharges, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int vsId, HttpServletRequest req) throws ParseException{
		
		
		csVendorServiceChargeService.getChargeObjectExceptId(csVendorServiceCharges.getVscId());
		csVendorServiceCharges.setVscId(csVendorServiceCharges.getVscId());
		csVendorServiceChargeService.update(csVendorServiceCharges);
		return csVendorServiceChargeService.getResponse(vsId);
	}
	@RequestMapping(value = "/vendorServices/charge/delete", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteServiceCharge(@ModelAttribute("csVendorServiceCharges")CsVendorServiceCharges csVendorServiceCharges, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		
		JsonResponse errorResponse = new JsonResponse();
		try{
		csVendorServiceChargeService.delete(csVendorServiceCharges.getVscId());
		return csVendorServiceCharges;
		}
		catch (Exception e) {
			@SuppressWarnings("serial")
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
	
	/* ================================== Concierge Vendor : OtherDetails ================================== */
	
	@RequestMapping(value = "/conciergeVendors/otherDetails/read/{personId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readVendorOtherDetails(@PathVariable int personId) {
		
		return conciergeVendorsService.getResponse(personId);
	}
	@RequestMapping(value = "/conciergeVendors/otherDetails/update/{personId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateVendorOtherDetails(@ModelAttribute("conciergeVendors")ConciergeVendors conciergeVendors, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int personId, HttpServletRequest req) throws ParseException{
		
		HttpSession session = req.getSession(false);
		String userName = (String)session.getAttribute("userId");
		Person person = personService.find(personId);
		conciergeVendors.setPerson(person);
		conciergeVendors.setCstNo(req.getParameter("cstNo"));
		conciergeVendors.setStateTaxNo(req.getParameter("stateTaxNo"));
		conciergeVendors.setServiceTaxNo(req.getParameter("serviceTaxNo"));
		conciergeVendors.setStatus(req.getParameter("status"));
		conciergeVendors.setCreatedBy(person.getCreatedBy());
		conciergeVendors.setLastUpdatedBy(userName);
		conciergeVendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		person.setConciergeVendors(conciergeVendors);
		
		personService.update(person);	
		return conciergeVendorsService.getResponse(personId);
	}
	/* ================================== Vendor Service : Comment Rate ================================== */
	
	@RequestMapping(value = "/conciergeVendors/commentRate/read/{personId}", method = RequestMethod.POST)
	public @ResponseBody List<?> readVendorServices(@PathVariable int personId)
	{
		return csVendorCommentsRatingService.getResponse(personId);

	}
	@RequestMapping(value ="/conciergeVendors/commentRate/create/{personId}",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object CreateVendorComments(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("csVendorCommentsRating") CsVendorCommentsRating csVendorCommentsRating, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,@PathVariable int personId){
		
		logger.info(" personId "+ personId);
		csVendorCommentsRatingService.getVendorCommentsObject(map, "save",csVendorCommentsRating,personId,request);
		csVendorCommentsRatingService.save(csVendorCommentsRating);
		return csVendorCommentsRatingService.getResponse(personId);
		//return null;
		
			}
	@RequestMapping(value ="/conciergeVendors/commentRate/update/{personId}",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object updateVendorComments(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("csVendorCommentsRating") CsVendorCommentsRating csVendorCommentsRating, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,@PathVariable int personId){
		
		csVendorCommentsRatingService.getVendorCommentsObject(map, "update",csVendorCommentsRating,personId,request);
		csVendorCommentsRatingService.update(csVendorCommentsRating);
		return csVendorCommentsRatingService.getResponse(personId);
		
			}
	@RequestMapping(value = "/conciergeVendors/commentRate/delete", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteVendorComments(@RequestBody Map<String, Object> map,@ModelAttribute("csVendorCommentsRating") CsVendorCommentsRating csVendorCommentsRating, BindingResult bindingResult,
			ModelMap model,final Locale locale) {

		//JsonResponse errorResponse = new JsonResponse();
		int vcrId = (Integer)map.get("vcrId");
		csVendorCommentsRatingService.delete(vcrId);
		return csVendorCommentsRating;

	}
	
	/* ================================== Service Booking ================================== */
	@RequestMapping(value = "/conciergeManagement/serviceBookingStatus/{sbId}/{operation}", method = RequestMethod.POST)
	public String serviceBookingStatus(@PathVariable int sbId,@PathVariable String operation,ModelMap model, 
			HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{		
		csServiceBookingService.setServiceBookingStatus(sbId, operation, response, messageSource, locale);
		return null;
	}

	@RequestMapping(value = "/serviceBooking/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readBookedServices()
	{
		return csServiceBookingService.getBookedServices();

	}
	
	@RequestMapping(value = "/serviceBooking/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object bookService(@RequestBody Map<String, Object> map,HttpServletRequest request,
			HttpServletResponse response,ModelMap model,@ModelAttribute("csServiceBooking") CsServiceBooking csServiceBooking, 
			BindingResult bindingResult,final Locale locale) throws ParseException{
		
		
		csServiceBooking = csServiceBookingService.getBookingServiceObject(map, "save",csServiceBooking,request);
		csServiceBookingService.save(csServiceBooking);
		
		return readBookedServices();
	}
	@RequestMapping(value = "/serviceBooking/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object updateBookedService(@RequestBody Map<String, Object> map,HttpServletRequest request,
			HttpServletResponse response,ModelMap model,@ModelAttribute("csServiceBooking") CsServiceBooking csServiceBooking, 
			BindingResult bindingResult,final Locale locale) throws ParseException{
		
		
		csServiceBooking = csServiceBookingService.getBookingServiceObject(map, "update",csServiceBooking,request);
		csServiceBookingService.update(csServiceBooking);
		
		return readBookedServices();
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/serviceBooking/delete", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteBookedService(@RequestBody Map<String, Object> map,@ModelAttribute("csServiceBooking") CsServiceBooking csServiceBooking, BindingResult bindingResult,
			ModelMap model,final Locale locale) {

		JsonResponse errorResponse = new JsonResponse();
		int sbId = (Integer)map.get("sbId");
		try{
			csServiceBookingService.delete(sbId);
			return readBookedServices();
		}
		catch (Exception e) {
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
	
	/* ================================== Other:Related methods================================== */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/csService/getGroupNames", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getGroupNames() {

		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();        
		for (final String groupName : conciergeService.getGroupNames()) {

			result.add(new HashMap<String, Object>() 
					{{	            	
						put("serviceGroupName", groupName);

					}});
		}
		return result;
	}
	@RequestMapping(value = "/csService/getServiceNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getServiceNames() {
		return 	conciergeService.getServiceNames();
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vendorServices/getCsVendorNames", method = RequestMethod.GET)
	public @ResponseBody Set<?> getCsVendorNames()
	{
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();        
		for (final Person record : personService.getCsVendorNames("ConciergeVendor")) {
			result.add(new HashMap<String, Object>() 
					{{	    
						int csvId = conciergeVendorsService.getCsVendorIdBasedOnPersonId(record.getPersonId()).getCsvId();
						if( record.getLastName() == null ){
							put("conciergeVendors", record.getFirstName());
						}
						else
						put("conciergeVendors", record.getFirstName() + " "+record.getLastName());
						put("csvId", csvId);

					}});
		}
		return result;
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vendorServices/getCsServices", method = RequestMethod.GET)
	public @ResponseBody Set<?> getCsServices()
	{
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();        
		for (final ConciergeServices record : conciergeService.getAll()) {

			result.add(new HashMap<String, Object>() 
					{{	            	
						put("conciergeService",record.getServiceName());
						put("csGroupName",record.getServiceGroupName());
						put("cssId", record.getCssId());

					}});
		}
		return result;
	}
	@RequestMapping(value = "/csService/getServiceNameList", method = RequestMethod.GET)
	public @ResponseBody Set<?> getServiceNameList() {

		Set<String> result = new HashSet<String>();
		for (String serviceNames : conciergeService.getServiceNames())
		{
			result.add(serviceNames);
		}
		return result;
	}
	@RequestMapping(value = "/csService/getGroupNameList", method = RequestMethod.GET)
	public @ResponseBody Set<?> getGroupNameList() {

		Set<String> result = new HashSet<String>();
		for (String groupNames : conciergeService.getGroupNames())
		{
			result.add(groupNames);
		}
		return result;
	}
	@RequestMapping(value = "/vendorServices/getServiceNameList", method = RequestMethod.GET)
	public @ResponseBody Set<?> getVendorServiceNameList() {

		Set<String> result = new HashSet<String>();
		for (String serviceNames : conciergeVendorSer.getServiceNames())
		{
			result.add(serviceNames);
		}
		return result;
	}
	@RequestMapping(value = "/vendorServices/getVendorNameList", method = RequestMethod.GET)
	public @ResponseBody Set<?> getVendorNameList() {

		Set<String> result = new HashSet<String>();
		for (Person personRec : conciergeVendorSer.getVendorNames())
		{
			result.add(personRec.getFirstName()+" "+personRec.getLastName());
		}
		return result;
	}
	@RequestMapping(value = "/vendorServices/getServiceNamesBasedOnVendorId",method=RequestMethod.GET)
	public @ResponseBody
	List<?> getServiceNamesBasedOnVendorId(HttpServletRequest request,HttpServletResponse res) {
		List<String> result = new ArrayList<String>();
		//String service = request.getParameter("service");
		String cvIds = request.getParameter("vendorId");
		int csvId = Integer.parseInt(cvIds);
		for(int serviceIds : conciergeVendorSer.getServiceIdsBasedOnVendorId(csvId)){
			String serviceName = conciergeService.getServiceNameBasedOnId(serviceIds);
			result.add(serviceName);
		}
		return result;
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/conciergeVendors/commentRate/getOwnerNames", method = RequestMethod.GET)
	public @ResponseBody Set<?> getOwnerNames() {
		
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		
		//for template
		for (final Object[] record : personService.getOwnersBasedOnBlock()) {
			
			result.add(new HashMap<String, Object>() {{
				put("personId",record[0] );
				put("personType",record[1] );
				if( record[3] == null ){
					put("ownerNames",record[2]+"["+record[1]+"]");
				}
				else{
				put("ownerNames",record[2]+" "+record[3]+"["+record[1]+"]" );
				
				}
				put("ownerId",record[7] );
				put("propertyNumber","Property Number ["+record[4]+"]" );
				put("propertyId",record[5] );
				put("blockName","Block Name ["+record[6]+"]" );
				
				
			}});
		}
		for (final Object[] record : personService.getTenantsBasedOnBlock()) {
			
			result.add(new HashMap<String, Object>() {{
				put("personId",record[0] );
				put("personType",record[1] );
				if( record[3] == null ){
					put("ownerNames",record[2]+"["+record[1]+"]");
				}
				else{
				put("ownerNames",record[2]+" "+record[3]+"["+record[1]+"]" );
				
				}
				put("ownerId",record[7] );
				put("propertyNumber","Property Number ["+record[4]+"]" );
				put("propertyId",record[5] );
				put("blockName","Block Name ["+record[6]+"]" );
				
				
			}});
		}
		return result;
		
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/serviceBooking/getVendorService", method = RequestMethod.GET)
	public @ResponseBody Set<?> getVendorService() {
		
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();   
		List<Integer> csIds =  conciergeService.getDistinctCssIds();
		for (Iterator<Integer> iterator = csIds.iterator(); iterator.hasNext();) {
			final Integer cssId = (Integer) iterator.next();
			final String serviceName = conciergeService.getServiceNameBasedOnId(cssId);
			result.add(new HashMap<String, Object>() 
					{{	  
						put("vendorServices",serviceName);
						put("cssId",cssId);

					}});
			
		}
		
		return result;
		
		/*Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();     
		List<Integer> distinctVsIds = csVendorServiceChargeService.getDistinctServiceId();
		for (Iterator<Integer> iterator = distinctVsIds.iterator(); iterator.hasNext();) {
			Integer vsId = (Integer) iterator.next();       
			for (final ConciergeVendorServices serviceRec : csVendorServiceChargeService.getVendorService(vsId)) {
			if( result.size() > 0 ){
			for (Iterator<Map<String, Object>> iterator1 = result.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator1.next();
				if( !map.get("vendorServices").equals(serviceRec.getCsServices().getServiceName()) ){
					result.add(new HashMap<String, Object>() 
							{{	  
								
								put("vendorServices",serviceRec.getCsServices().getServiceName());
								put("vsId",serviceRec.getVsId());

							}});
					break;
				}
			}
				
			}
			else{
				result.add(new HashMap<String, Object>() 
						{{	  
							
							put("vendorServices",serviceRec.getCsServices().getServiceName());
							put("vsId",serviceRec.getVsId());

						}});
			}
			}
		}
		return result;*/
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/serviceBooking/getVendors", method = RequestMethod.GET)
	 public @ResponseBody List<?> getVendors(HttpServletRequest request,HttpServletResponse response) 
	 {
		  
		 List<ConciergeVendorServices> list = conciergeVendorSer.findAll();
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		/* result.add(new HashMap<String, Object>() {{
			  put("vendors", "Select");
			  put("csvId","");
		  }});*/
		   for (final ConciergeVendorServices record : list ) 
		   {
			   result.add(new HashMap<String, Object>() {{
				   if( record.getCsVendors().getPerson().getFirstName() == null ){
					   put("vendors",record.getCsVendors().getPerson().getFirstName());   
				   }
				   else{
				   put("vendors",record.getCsVendors().getPerson().getFirstName()+" "+record.getCsVendors().getPerson().getLastName());
				   }
				   put("vendorServices",record.getCsServices().getServiceName());
				   put("csvId",record.getCsvId());
				   put("cssId",record.getCssId());
					
		              
		             }}); 
		       }
		   return result;
}
	
	 @SuppressWarnings("serial")
		@RequestMapping(value = "/serviceBooking/getVendorRateType", method = RequestMethod.GET)
		 public @ResponseBody List<?> getVendorRateType(HttpServletRequest request,HttpServletResponse response) 
		 {
		 
		 String serviceName = request.getParameter("serviceName");
		 int csvId = Integer.parseInt(request.getParameter("csvId"));
		 int cssId = conciergeService.getServiceIdBasedOnName(serviceName);
		 int vsId = conciergeVendorSer.getVendorServiceId(csvId,cssId);
		 List<CsVendorServiceCharges> list = csVendorServiceChargeService.getServiceChargeBasedOnVendorServiceId(vsId);
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		 result.add(new HashMap<String, Object>() {{
			  put("rateType", "Select");
			  put("vscId","");
		  }});
		   for (final CsVendorServiceCharges record : list ) 
		   {
			   result.add(new HashMap<String, Object>() {{
				   
				   put("rateType",record.getVendorRateType());
				   put("vscId",record.getVscId());
				   put("collectionMethod","Collection Method "+"["+record.getCollectionMethod()+"]");
		              
		             }}); 
		       }
		   return result;
		 //return null;
		 
}
	 @RequestMapping(value = "/serviceBooking/getVendorRate/{vscId}", method = RequestMethod.GET)
		public @ResponseBody int getVendorRate(@PathVariable int vscId ) {
		 
		 return csVendorServiceChargeService.getVedorRateBasedOnVendorRateType(vscId);
		 
		}

	@RequestMapping(value = "/serviceBooking/getListForFiltering/{type}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getListForFiltering(@PathVariable String type) {
		
		if( type.equals("RateType")){
			Set<String> result = new HashSet<String>();
			for (CsServiceBooking csServiceBooking : csServiceBookingService.findAll())
			{
				result.add(csServiceBooking.getVendorServiceCharges().getVendorRateType());
			}

			return result;
			
		}
		if( type.equals("Vendors")){
			Set<String> result = new HashSet<String>();
			for (CsServiceBooking csServiceBooking : csServiceBookingService.findAll())
			{
				if( csServiceBooking.getVendorServiceCharges().getVendorServices().getCsVendors().getPerson().getLastName() == null ){
					result.add(csServiceBooking.getVendorServiceCharges().getVendorServices().getCsVendors().getPerson().getFirstName());
					
				}
				else{
				result.add(csServiceBooking.getVendorServiceCharges().getVendorServices().getCsVendors().getPerson().getFirstName()+" "+csServiceBooking.getVendorServiceCharges().getVendorServices().getCsVendors().getPerson().getLastName());
				}
			}

			return result;
			
		}
		if( type.equals("Services")){
			Set<String> result = new HashSet<String>();
			for (CsServiceBooking csServiceBooking : csServiceBookingService.findAll())
			{
				result.add(csServiceBooking.getVendorServiceCharges().getVendorServices().getCsServices().getServiceName());
			}

			return result;
			
		}
		if( type.equals("Status")){
		Set<String> result = new HashSet<String>();
		result.add("Yes");
		result.add("No");
		return result;
		}
		
		if( type.equals("OwnerNames")){
			Set<String> result = new HashSet<String>();       
			for (CsServiceBooking csServiceBooking : csServiceBookingService.findAll()){
				
				Person personrec = personService.getPersonBasedOnOwnerId(csServiceBooking.getOwnerId());
				if( personrec == null ){
					personrec = personService.getPersonBasedOnTenantId(csServiceBooking.getOwnerId());
				}
				
						if( personrec.getLastName() == null ){
							result.add(personrec.getFirstName()+"["+personrec.getPersonType()+"]");
						}
						else{
							result.add(personrec.getFirstName()+" "+personrec.getLastName()+"["+personrec.getPersonType()+"]");
						}
		}
		return result;
		}
		if( type.equals("BookedBy")){
			Set<String> result = new HashSet<String>();
			for (CsServiceBooking csServiceBooking : csServiceBookingService.findAll())
			{
				result.add(csServiceBooking.getBookedBy());
			}

			return result;
		}
		return null;

	}
	@RequestMapping(value = "/conciergeVendors/languageList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getLanguageList(final Locale locale) {

		Set<String> result = new HashSet<String>();
		String[] str = messageSource.getMessage("languagesKnown", null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
		//List<String> list = Arrays.asList(str);
		//Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			result.add(string);
			
		}
		List<String> str1 = personService.getPersonLanguages();
		for (Iterator<String> iterator = str1.iterator(); iterator.hasNext();) {
			
			String string = (String) iterator.next();
			result.add(string);
			
		}
		return result;
	}
	@RequestMapping(value = "/conciergeVendors/maritalStatusList", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getMaritalStatusList(final Locale locale) {
		Set<String> result = new HashSet<String>();
		String[] str = messageSource.getMessage("maritalStatus", null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
		//List<String> list = Arrays.asList(str);
		//Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			result.add(string);
			
		}
		return result;
	}
	@RequestMapping("/csService/getFormatedStartDate/{startDate}")
	public void getFormatedStartDate(@PathVariable("startDate") String startDate,HttpServletResponse response) throws ParseException {
		
		logger.info("startDate   "+ startDate);
	//	Date d = dateTimeCalender.getDateToStore(manuFacDate);
		PrintWriter out;
		String dateStr ="";
		try{
		java.util.Date date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(startDate);
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateStr = new SimpleDateFormat("dd/MM/yyyy").format(date);		
		Date d = getDateFromString(dateStr);
		logger.info("dateStr    "+ dateStr);
		logger.info("d    "+ d);
		out = response.getWriter();
			out.write(dateStr);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public java.sql.Date getDateFromString(String string)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date finalDate = null;
		
		try 
		{
		   finalDate = sdf.parse(string);
		} 
		
		catch (Exception e) 
		{
		   e.printStackTrace();
		}
		
		return new java.sql.Date(finalDate.getTime());
		//return finalDate;
	}
	
}
