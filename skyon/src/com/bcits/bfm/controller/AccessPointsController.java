package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.AccessPoints;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.facilityManagement.AccessPointsService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;


/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Access Point Management 
 * Use Case : Access Point
 * 
 * @author Aizaz
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class AccessPointsController {
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private AccessPointsService accessPointsService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CommonService commonService;
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AccessPointsController.class);

	/**
	 * Index method of the Access Point
	 * 
	 * @param model						ModelMap Object
	 * @param request					HttpServletRequest Object
	 * @return							Redirect to the access point view
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(value = "/accesspointindex", method = RequestMethod.GET)
	public String indexAccessPoints(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		 model.addAttribute("ViewName", "Customer Occupancy");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Access Points Management", 2, request);
		model.addAttribute("username",
				SessionData.getUserDetails().get("userID"));

		return "accesspoints/accessPoints";
	}	
	
	/**
	 * Read All Access Points
	 * @return List of Access Points
	 */
	@RequestMapping(value = "/accesspoint/read", method = RequestMethod.POST)
	public @ResponseBody
	List<AccessPoints> readAllAccessPoints() {
		
		List<AccessPoints> ap = accessPointsService.findAll();
		return ap;
	}

	/**
	 * Create New Access Point
	 * 
	 * @param ape				Access Point Object
	 * @param bindingResult		Binding REsult Object
	 * @param model				ModelMap Object
	 * @param sessionStatus		Session Status Object
	 * @param locale			System Locale
	 * @return					Object of Access Point
	 */							
	@RequestMapping(value = "/accesspoint/create", method = RequestMethod.GET)
	public @ResponseBody
	Object createAccessPoints(@ModelAttribute("accesspoint")AccessPoints ape, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		
		JsonResponse errorResponse = new JsonResponse();
		validator.validate(ape, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
		
			return errorResponse;
		}
		accessPointsService.save(ape);
		return ape;
	}
	
	/** 
	 * Update the existing access point   
	

	 * 
	 * @param ape				Access Point Object
	 * @param bindingResult		Binding REsult Object
	 * @param model				ModelMap Object
	 * @param sessionStatus		Session Status Object
	 * @param locale			System Locale
	 * @return					Object of Access Point
	 */		
	@RequestMapping(value = "/accesspoint/update", method = RequestMethod.GET)
	public @ResponseBody
	Object editAccessPoints(@ModelAttribute("accesspoint")AccessPoints ape, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		
		JsonResponse errorResponse = new JsonResponse();
		validator.validate(ape, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
	
			return errorResponse;
		}
		accessPointsService.update(ape);
		return ape;
	}
	
	/**
	 * Delete Access Point
	 * 
	 * @param ape 		Access POint Object
	 * @param ss  		SessionStatus Object
	 * @return			Access POint Object
	 */
	@RequestMapping(value = "/accesspoint/destroy", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteStaffTraining(SessionStatus ss,HttpServletRequest request) {
		JsonResponse errorResponse = new JsonResponse();
		try{
			accessPointsService.delete(Integer.parseInt(request.getParameter("apId")));
		}
		catch(Exception e){
			
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		errorResponse.setStatus("destroy");
		
		ss.setComplete();
		return errorResponse;
	}
	
	/**
	 * 
	 * Read All Access Point Code
	 * @return List of Access Point Code
	 */
	@RequestMapping(value = "/accesspoint/getAllApcode", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getUserNewLoginNames() {
		return 	accessPointsService.findAllApcode();
	}
	
	/**
	 * Access POint Filter Method
	 * 
	 * @param feild 		Attribute
	 * @return				List of Attributes
	 */
	@RequestMapping(value = "/accesspoint/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		
		return 	(List<?>) commonService.selectQuery("AccessPoints", attributeList, null);
	}
	
	@RequestMapping(value="/accesspoint/actype",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Set<?> getFilter()
	{
		Set<String> data=new HashSet<String>();
		
		for(AccessPoints a:accessPointsService.findAll())
		{
			data.add(a.getApType());
		}
		return data;
			
	}
	
	
 
	@RequestMapping(value="/accesspoint/location",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Set<?> getFilter1()
	{
		Set<String> data=new HashSet<String>();
		
		for(AccessPoints a:accessPointsService.findAll())
		{
			data.add(a.getApLocation());
		}
		return data;
			
	}
	
}
