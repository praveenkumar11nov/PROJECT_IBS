package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.bcits.bfm.model.Person;
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
public class CustomerOnboardController {
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CustomerOnboardController.class);

	
	@RequestMapping(value = "/custonboardindex", method = RequestMethod.GET)
	public String indexCustomerOnBoard(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		List<String> list = new ArrayList<String>();
		list.add("Male");
		list.add("Female");
		
		model.addAttribute("model",new Person());
		model.addAttribute("sex",list);
		
		return "cob/cobform";
	}
	
	/*@RequestMapping(value = "/servicetasks", method = RequestMethod.GET)
	public String testBoard(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		model.addAttribute("ViewName", "Service Parameter Master");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service Parameter Master", 1, request);
		
		
		return "servicetasks/servicetasks";
	}*/

	
}
